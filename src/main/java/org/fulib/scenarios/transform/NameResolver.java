package org.fulib.scenarios.transform;

import org.fulib.StrUtil;
import org.fulib.scenarios.ast.*;
import org.fulib.scenarios.ast.decl.*;
import org.fulib.scenarios.ast.expr.Expr;
import org.fulib.scenarios.ast.expr.access.AttributeAccess;
import org.fulib.scenarios.ast.expr.access.ExampleAccess;
import org.fulib.scenarios.ast.expr.access.ListAttributeAccess;
import org.fulib.scenarios.ast.expr.call.CallExpr;
import org.fulib.scenarios.ast.expr.call.CreationExpr;
import org.fulib.scenarios.ast.expr.collection.ListExpr;
import org.fulib.scenarios.ast.expr.conditional.AttributeCheckExpr;
import org.fulib.scenarios.ast.expr.conditional.ConditionalExpr;
import org.fulib.scenarios.ast.expr.conditional.ConditionalOperator;
import org.fulib.scenarios.ast.expr.conditional.ConditionalOperatorExpr;
import org.fulib.scenarios.ast.expr.primary.NameAccess;
import org.fulib.scenarios.ast.expr.primary.NumberLiteral;
import org.fulib.scenarios.ast.expr.primary.PrimaryExpr;
import org.fulib.scenarios.ast.expr.primary.StringLiteral;
import org.fulib.scenarios.ast.sentence.*;
import org.fulib.scenarios.ast.type.*;
import org.fulib.scenarios.parser.Identifiers;
import org.fulib.scenarios.transform.scope.DelegatingScope;
import org.fulib.scenarios.transform.scope.HidingScope;
import org.fulib.scenarios.transform.scope.Scope;

import java.util.*;
import java.util.stream.Collectors;

public enum NameResolver implements CompilationContext.Visitor<Object, Object>, ScenarioGroup.Visitor<Object, Object>,
                                       ScenarioFile.Visitor<Scope, Object>, Scenario.Visitor<Scope, Object>,
                                       Sentence.Visitor<Scope, Sentence>, Type.Visitor<Scope, Type>,
                                       Expr.Visitor<Scope, Expr>, Name.Visitor<Scope, Name>
{
   INSTANCE;

   // =============== Constants ===============

   protected static final String ENCLOSING_CLASS = "<enclosing:class>";

   // =============== Methods ===============

   // --------------- CompilationContext.Visitor ---------------

   @Override
   public Object visit(CompilationContext compilationContext, Object par)
   {
      final Map<String, ScenarioGroup> groups = compilationContext.getGroups();
      final Set<ScenarioGroup> importedGroups = new HashSet<>();

      // first, process all groups that are imported.
      for (final String packageName : compilationContext.getConfig().getImports())
      {
         final String packageDir = packageName.replace('.', '/');
         final ScenarioGroup group = groups.get(packageDir);
         if (group != null)
         {
            group.accept(this, par);
            importedGroups.add(group);
         }
      }

      // then, process remaining groups.
      // since there are no more inter-group references, we can parallelize.
      groups.values().parallelStream().filter(o -> !importedGroups.contains(o)).forEach(it -> it.accept(this, null));
      return null;
   }

   // --------------- ScenarioGroup.Visitor ---------------

   @Override
   public Object visit(ScenarioGroup scenarioGroup, Object par)
   {
      final Scope scope = new Scope()
      {
         @Override
         public Decl resolve(String name)
         {
            return scenarioGroup.getClasses().get(name);
         }

         @Override
         public void add(Decl decl)
         {
            final ClassDecl classDecl = (ClassDecl) decl;
            classDecl.setGroup(scenarioGroup);
            scenarioGroup.getClasses().put(decl.getName(), classDecl);
         }
      };

      // first, process external files.
      for (final ScenarioFile file : scenarioGroup.getFiles().values())
      {
         if (file.getExternal())
         {
            file.accept(this, scope);
            file.getClassDecl().setFrozen(true);
         }
      }

      // freeze all classes created by external files.
      for (final ClassDecl classDecl : scenarioGroup.getClasses().values())
      {
         classDecl.setFrozen(true);
      }

      // now process non-external files.
      for (final ScenarioFile file : scenarioGroup.getFiles().values())
      {
         if (!file.getExternal())
         {
            file.accept(this, scope);
         }
      }
      return null;
   }

   // --------------- ScenarioFile.Visitor ---------------

   @Override
   public Object visit(ScenarioFile scenarioFile, Scope par)
   {
      final ScenarioGroup group = scenarioFile.getGroup();
      final String className = Identifiers.toUpperCamelCase(scenarioFile.getName()) + "Test";
      final ClassDecl classDecl = ClassDecl.of(group, className, null, new LinkedHashMap<>(), new LinkedHashMap<>(),
                                               new ArrayList<>());
      classDecl.setExternal(scenarioFile.getExternal());
      classDecl.setType(ClassType.of(classDecl));

      // group.getClasses().put(className, classDecl);
      scenarioFile.setClassDecl(classDecl);

      final Scope scope = new DelegatingScope(par)
      {
         @Override
         public Decl resolve(String name)
         {
            return className.equals(name) || ENCLOSING_CLASS.equals(name) ? classDecl : super.resolve(name);
         }
      };
      for (final Scenario scenario : scenarioFile.getScenarios().values())
      {
         scenario.accept(this, scope);
      }
      return null;
   }

   // --------------- Scenario.Visitor ---------------

   @Override
   public Object visit(Scenario scenario, Scope par)
   {
      final ClassDecl classDecl = scenario.getFile().getClassDecl();
      final String methodName = Identifiers.toLowerCamelCase(scenario.getName());
      final SentenceList body = scenario.getBody();
      final MethodDecl methodDecl = MethodDecl.of(classDecl, methodName, null, PrimitiveType.VOID, body);

      final ParameterDecl thisParam = ParameterDecl.of(methodDecl, "this", classDecl.getType());
      methodDecl.setParameters(Collections.singletonList(thisParam));

      classDecl.getMethods().add(methodDecl);
      scenario.setMethodDecl(methodDecl);

      body.accept(this, new DelegatingScope(par)
      {

         @Override
         public Decl resolve(String name)
         {
            if ("this".equals(name))
            {
               return thisParam;
            }
            if (methodName.equals(name))
            {
               return methodDecl;
            }
            return super.resolve(name);
         }
      });
      return null;
   }

   // --------------- Sentence.Visitor ---------------

   @Override
   public Sentence visit(SentenceList sentenceList, Scope par)
   {
      final Map<String, Decl> decls = new HashMap<>();
      final Scope scope = new DelegatingScope(par)
      {
         @Override
         public Decl resolve(String name)
         {
            final Decl decl = decls.get(name);
            return decl != null ? decl : super.resolve(name);
         }

         @Override
         public void add(Decl decl)
         {
            if (decl instanceof VarDecl)
            {
               decls.put(decl.getName(), decl);
               return;
            }
            super.add(decl);
         }
      };

      final List<Sentence> oldItems = sentenceList.getItems();
      final List<Sentence> newItems = new ArrayList<>(oldItems.size());
      for (final Sentence item : oldItems)
      {
         item.accept(SymbolCollector.INSTANCE, decls);
         final Sentence resolved = item.accept(this, scope);
         FlattenSentenceList.add(newItems, resolved);
      }
      sentenceList.setItems(newItems);

      return sentenceList;
   }

   @Override
   public Sentence visit(ExpectSentence expectSentence, Scope par)
   {
      expectSentence.getPredicates().replaceAll(it -> (ConditionalExpr) it.accept(this, par));
      return expectSentence;
   }

   @Override
   public Sentence visit(DiagramSentence diagramSentence, Scope par)
   {
      diagramSentence.setObject(diagramSentence.getObject().accept(this, par));
      return diagramSentence;
   }

   @Override
   public Sentence visit(HasSentence hasSentence, Scope par)
   {
      final Expr receiver = hasSentence.getObject().accept(this, par);
      hasSentence.setObject(receiver);

      final ClassDecl objectClass = resolveClass(par, receiver);
      final String name = receiver.accept(Namer.INSTANCE, null);
      final Scope scope = name != null ? new HidingScope(name, par) : par;

      for (final NamedExpr namedExpr : hasSentence.getClauses())
      {
         namedExpr.setExpr(namedExpr.getExpr().accept(this, scope));
         namedExpr.setName(resolveAttributeOrAssociation(objectClass, namedExpr.getName(), namedExpr.getExpr()));
      }

      return hasSentence;
   }

   @Override
   public Sentence visit(IsSentence isSentence, Scope par)
   {
      final VarDecl varDecl = isSentence.getDescriptor();
      varDecl.setExpr(varDecl.getExpr().accept(this, par));

      if (varDecl.getType() == null)
      {
         varDecl.setType(varDecl.getExpr().accept(Typer.INSTANCE, null));
      }
      return isSentence;
   }

   @Override
   public Sentence visit(AnswerSentence answerSentence, Scope par)
   {
      if (answerSentence.getActor() != null)
      {
         answerSentence.setActor(answerSentence.getActor().accept(this, par));
      }

      answerSentence.setResult(answerSentence.getResult().accept(this, par));
      return answerSentence;
   }

   @Override
   public Sentence visit(AddSentence addSentence, Scope par)
   {
      final Expr source = addSentence.getSource().accept(this, par);
      final Expr target = addSentence.getTarget();

      // TODO UnsupportedOperationException to diagnostic
      return target.accept(AddResolve.INSTANCE, source).accept(this, par);
   }

   @Override
   public Sentence visit(RemoveSentence removeSentence, Scope par)
   {
      final Expr source = removeSentence.getSource().accept(this, par);
      final Expr target = removeSentence.getTarget();

      // TODO UnsupportedOperationException to diagnostic
      return target.accept(RemoveResolve.INSTANCE, source).accept(this, par);
   }

   @Override
   public Sentence visit(ExprSentence exprSentence, Scope par)
   {
      exprSentence.setExpr(exprSentence.getExpr().accept(this, par));
      return exprSentence;
   }

   @Override
   public Sentence visit(TemplateSentence templateSentence, Scope par)
   {
      templateSentence.getExprs().replaceAll(it -> it.accept(this, par));
      return templateSentence;
   }

   // --------------- Type.Visitor ---------------

   @Override
   public Type visit(UnresolvedType unresolvedType, Scope par)
   {
      try
      {
         return PrimitiveType.valueOf(unresolvedType.getName());
      }
      catch (IllegalArgumentException unknownEnumConstant)
      {
         return resolveClass(par, unresolvedType.getName()).getType();
      }
   }

   @Override
   public Type visit(PrimitiveType primitiveType, Scope par)
   {
      return primitiveType;
   }

   @Override
   public Type visit(ClassType classType, Scope par)
   {
      return classType;
   }

   @Override
   public Type visit(ListType listType, Scope par)
   {
      listType.setElementType(listType.getElementType().accept(this, par));
      return listType;
   }

   // --------------- Expr.Visitor ---------------

   @Override
   public Expr visit(Expr expr, Scope par)
   {
      return expr;
   }

   @Override
   public Expr visit(AttributeAccess attributeAccess, Scope par)
   {
      final Expr receiver = attributeAccess.getReceiver().accept(this, par);
      attributeAccess.setReceiver(receiver);

      final Type receiverType = receiver.accept(Typer.INSTANCE, null);
      if (receiverType instanceof ListType)
      {
         final String attributeName = attributeAccess.getName().accept(Namer.INSTANCE, null);
         final Type elementType = ((ListType) receiverType).getElementType();
         final Name resolvedName = getAttributeOrAssociation(resolveClass(par, elementType), attributeName);
         return ListAttributeAccess.of(resolvedName, receiver);
      }

      attributeAccess.setName(getAttributeOrAssociation(par, receiver, attributeAccess.getName()));
      return attributeAccess;
   }

   @Override
   public Expr visit(ExampleAccess exampleAccess, Scope par)
   {
      exampleAccess.setExpr(exampleAccess.getExpr().accept(this, par));
      return exampleAccess;
   }

   @Override
   public Expr visit(PrimaryExpr primaryExpr, Scope par)
   {
      return primaryExpr;
   }

   @Override
   public Expr visit(NameAccess nameAccess, Scope par)
   {
      if (nameAccess.getName() instanceof UnresolvedName)
      {
         final UnresolvedName unresolvedName = (UnresolvedName) nameAccess.getName();
         final String unresolvedValue = unresolvedName.getValue();

         final Decl target = par.resolve(unresolvedValue);
         if (target == null)
         {
            return StringLiteral.of(unresolvedName.getText());
         }
         else
         {
            nameAccess.setName(ResolvedName.of(target));
         }
      }

      return nameAccess;
   }

   @Override
   public Expr visit(NumberLiteral numberLiteral, Scope par)
   {
      return numberLiteral;
   }

   @Override
   public Expr visit(StringLiteral stringLiteral, Scope par)
   {
      return stringLiteral;
   }

   @Override
   public Expr visit(CreationExpr creationExpr, Scope par)
   {
      creationExpr.setType(creationExpr.getType().accept(this, par));
      final ClassDecl classDecl = resolveClass(par, creationExpr.getType());

      for (final NamedExpr namedExpr : creationExpr.getAttributes())
      {
         namedExpr.setExpr(namedExpr.getExpr().accept(this, par));
         namedExpr.setName(resolveAttributeOrAssociation(classDecl, namedExpr.getName(), namedExpr.getExpr()));
      }
      return creationExpr;
   }

   @Override
   public Expr visit(CallExpr callExpr, Scope par)
   {
      final List<NamedExpr> arguments = callExpr.getArguments();
      Expr receiver = callExpr.getReceiver();
      if (receiver != null)
      {
         receiver = receiver.accept(this, par);
      }
      else
      {
         final Decl thisDecl = par.resolve("this");
         receiver = NameAccess.of(ResolvedName.of(thisDecl));
      }
      callExpr.setReceiver(receiver);

      for (final NamedExpr argument : arguments)
      {
         argument.setExpr(argument.getExpr().accept(this, par));
      }

      // generate method

      final ClassDecl receiverClass = resolveClass(par, callExpr.getReceiver());

      final String methodName = callExpr.getName().accept(Namer.INSTANCE, null);
      final MethodDecl method = resolveMethod(receiverClass, methodName);
      final List<ParameterDecl> parameters = method.getParameters();
      final boolean isNew = method.getType() == null;
      final Map<String, Decl> decls = new HashMap<>();

      if (isNew)
      {
         // this parameter
         final ParameterDecl thisParam = ParameterDecl.of(method, "this", receiverClass.getType());
         parameters.add(thisParam);
         decls.put("this", thisParam);
      }
      else
      {
         decls.put("this", parameters.get(0));

         // check if arguments and parameters match (by label)
         final String params = parameters.stream().skip(1).map(ParameterDecl::getName)
                                         .collect(Collectors.joining(" "));
         final String args = arguments.stream().map(NamedExpr::getName).map(n -> n.accept(Namer.INSTANCE, null))
                                      .collect(Collectors.joining(" "));

         if (!params.equals(args))
         {
            throw new IllegalStateException(
               "mismatching parameters and arguments:\nparameters: " + params + "\narguments : " + args);
         }
      }

      // references to the receiver name are replaced with 'this'
      final String receiverName = receiver.accept(Namer.INSTANCE, null);
      if (receiverName != null)
      {
         decls.put(receiverName, parameters.get(0));
      }

      // match arguments and parameters
      for (int i = 0; i < arguments.size(); i++)
      {
         final NamedExpr argument = arguments.get(i);
         final String name = argument.getName().accept(Namer.INSTANCE, null);
         final Expr expr = argument.getExpr();
         final ParameterDecl param;

         if (isNew)
         {
            final Type type = expr.accept(Typer.INSTANCE, null);
            param = ParameterDecl.of(method, name, type);
            parameters.add(param);
         }
         else
         {
            param = parameters.get(i + 1);
         }

         argument.setName(ResolvedName.of(param));
         decls.put(name, param);

         // references to the expression name refer to the parameter
         final String exprName = expr.accept(Namer.INSTANCE, null);
         if (exprName != null)
         {
            decls.put(exprName, param);
         }
      }

      final Scope scope = new DelegatingScope(par)
      {
         @Override
         public Decl resolve(String name)
         {
            final Decl decl = decls.get(name);
            return decl != null ? decl : super.resolve(name);
         }
      };
      callExpr.getBody().accept(this, scope);

      // set return type if necessary. has to happen after body resolution!
      if (isNew)
      {
         final Type returnType = callExpr.accept(Typer.INSTANCE, null);
         method.setType(returnType);
      }

      method.getBody().getItems().addAll(callExpr.getBody().getItems());

      return callExpr;
   }

   @Override
   public Expr visit(ConditionalExpr conditionalExpr, Scope par)
   {
      return conditionalExpr;
   }

   @Override
   public Expr visit(AttributeCheckExpr attributeCheckExpr, Scope par)
   {
      // extract
      final Expr receiver = attributeCheckExpr.getReceiver();
      final Expr expected = attributeCheckExpr.getValue();
      final Name attribute = attributeCheckExpr.getAttribute();

      // transform
      final AttributeAccess access = AttributeAccess.of(attribute, receiver);
      final ConditionalOperatorExpr condOp = ConditionalOperatorExpr.of(access, ConditionalOperator.IS, expected);

      // resolve
      return condOp.accept(this, par);
   }

   @Override
   public Expr visit(ConditionalOperatorExpr conditionalOperatorExpr, Scope par)
   {
      conditionalOperatorExpr.setLhs(conditionalOperatorExpr.getLhs().accept(this, par));
      conditionalOperatorExpr.setRhs(conditionalOperatorExpr.getRhs().accept(this, par));
      return conditionalOperatorExpr;
   }

   @Override
   public Expr visit(ListExpr listExpr, Scope par)
   {
      listExpr.getElements().replaceAll(it -> it.accept(this, par));
      return listExpr;
   }

   // --------------- Name.Visitor ---------------

   @Override
   public Name visit(Name name, Scope par)
   {
      return name;
   }

   @Override
   public Name visit(ResolvedName resolvedName, Scope par)
   {
      return resolvedName;
   }

   @Override
   public Name visit(UnresolvedName unresolvedName, Scope par)
   {
      final Decl decl = par.resolve(unresolvedName.getValue());
      return decl == null ? unresolvedName : ResolvedName.of(decl);
   }

   // =============== Static Methods ===============

   static ClassDecl getEnclosingClass(Scope scope)
   {
      return (ClassDecl) scope.resolve(ENCLOSING_CLASS);
   }

   static ClassDecl resolveClass(Scope scope, Expr expr)
   {
      final Type type = expr.accept(Typer.INSTANCE, null);
      return resolveClass(scope, type);
   }

   static ClassDecl resolveClass(Scope scope, Type type)
   {
      if (type instanceof ListType)
      {
         return resolveClass(scope, ((ListType) type).getElementType());
      }

      final String typeName = type.accept(Namer.INSTANCE, null);
      return resolveClass(scope, typeName);
   }

   static ClassDecl resolveClass(Scope scope, String name)
   {
      final Decl resolved = scope.resolve(name);
      if (resolved instanceof ClassDecl)
      {
         return (ClassDecl) resolved;
      }
      if (resolved != null)
      {
         throw new IllegalStateException("class name " + name + " resolved to " + resolved);
      }

      final ClassDecl decl = ClassDecl.of(null, name, null, new LinkedHashMap<>(), new LinkedHashMap<>(),
                                          new ArrayList<>());
      decl.setExternal(getEnclosingClass(scope).getExternal());
      decl.setType(ClassType.of(decl));
      scope.add(decl);
      return decl;
   }

   static MethodDecl resolveMethod(ClassDecl classDecl, String name)
   {
      for (final MethodDecl decl : classDecl.getMethods())
      {
         if (name.equals(decl.getName()))
         {
            return decl;
         }
      }

      if (classDecl.getFrozen())
      {
         throw new IllegalStateException("unresolved external method " + classDecl.getName() + "." + name);
      }

      final SentenceList body = SentenceList.of(new ArrayList<>());
      final MethodDecl decl = MethodDecl.of(classDecl, name, new ArrayList<>(), null, body);
      classDecl.getMethods().add(decl);
      return decl;
   }

   static Name resolveAttributeOrAssociation(ClassDecl classDecl, Name name, Expr rhs)
   {
      if (name.accept(ExtractDecl.INSTANCE, null) != null)
      {
         // already resolved
         return name;
      }

      return ResolvedName.of(resolveAttributeOrAssociation(classDecl, name.accept(Namer.INSTANCE, null), rhs));
   }

   static Decl resolveAttributeOrAssociation(ClassDecl classDecl, String attributeName, Expr rhs)
   {
      final AttributeDecl existingAttribute = classDecl.getAttributes().get(attributeName);
      if (existingAttribute != null)
      {
         return existingAttribute;
      }

      final AssociationDecl existingAssociation = classDecl.getAssociations().get(attributeName);
      if (existingAssociation != null)
      {
         return existingAssociation;
      }

      final Type attributeType = rhs.accept(Typer.INSTANCE, null);

      if (attributeType instanceof ListType)
      {
         // new value is multi-valued

         final Type otherType = ((ListType) attributeType).getElementType();
         if (otherType instanceof ClassType)
         {
            return resolveAssociation(classDecl, attributeName, ((ClassType) otherType).getClassDecl(), 2);
         }
         else
         {
            // was element type that we have no control over, e.g. List<String>
            return resolveAttribute(classDecl, attributeName, attributeType);
         }
      }
      else if (attributeType instanceof ClassType)
      {
         return resolveAssociation(classDecl, attributeName, ((ClassType) attributeType).getClassDecl(), 1);
      }
      else
      {
         return resolveAttribute(classDecl, attributeName, attributeType);
      }
   }

   static AttributeDecl resolveAttribute(ClassDecl classDecl, String name, Type type)
   {
      final AttributeDecl existing = classDecl.getAttributes().get(name);
      if (existing != null)
      {
         final Type existingType = existing.getType();
         if (!type.equals(existingType)) // TODO type equality
         {
            throw new IllegalStateException(
               "mismatched attribute type " + classDecl.getName() + "." + name + ": " + existingType + " vs "
               + type);
         }

         return existing;
      }

      if (classDecl.getFrozen())
      {
         throw new IllegalStateException("unresolved external attribute " + classDecl.getName() + "." + name);
      }

      final AttributeDecl attribute = AttributeDecl.of(classDecl, name, type);
      classDecl.getAttributes().put(name, attribute);
      return attribute;
   }

   static AssociationDecl resolveAssociation(ClassDecl classDecl, String name, ClassDecl otherClass, int cardinality)
   {
      final AssociationDecl existing = classDecl.getAssociations().get(name);
      if (existing != null)
      {
         if (existing.getTarget() != otherClass || existing.getCardinality() != cardinality)
         {
            throw new IllegalStateException(
               "mismatched association type " + classDecl.getName() + "." + name + ": " + cardinalityString(
                  existing.getCardinality()) + " " + existing.getTarget().getName() + " vs " + cardinalityString(
                  cardinality) + " " + otherClass.getName());
         }

         return existing;
      }

      if (classDecl.getFrozen())
      {
         throw new IllegalStateException("unresolved external association " + classDecl.getName() + "." + name);
      }

      final Type associationType = cardinality != 1 ? ListType.of(otherClass.getType()) : otherClass.getType();
      final AssociationDecl association = AssociationDecl
                                             .of(classDecl, name, cardinality, otherClass, associationType, null);
      final String otherName = StrUtil.downFirstChar(classDecl.getName());
      final AssociationDecl other = AssociationDecl
                                       .of(otherClass, otherName, 1, classDecl, classDecl.getType(), null);

      association.setOther(other);
      other.setOther(association);

      classDecl.getAssociations().put(association.getName(), association);
      otherClass.getAssociations().put(other.getName(), other);

      return association;
   }

   private static String cardinalityString(int cardinality)
   {
      return cardinality == 1 ? "one" : "many";
   }

   static Name getAttributeOrAssociation(Scope scope, Expr receiver, Name name)
   {
      if (name.accept(ExtractDecl.INSTANCE, null) != null)
      {
         return name;
      }

      return getAttributeOrAssociation(resolveClass(scope, receiver), name.accept(Namer.INSTANCE, null));
   }

   static Name getAttributeOrAssociation(ClassDecl receiverClass, String name)
   {
      final AttributeDecl attribute = receiverClass.getAttributes().get(name);
      if (attribute != null)
      {
         return ResolvedName.of(attribute);
      }

      final AssociationDecl association = receiverClass.getAssociations().get(name);
      if (association != null)
      {
         return ResolvedName.of(association);
      }

      throw new IllegalStateException("unresolved attribute or association " + receiverClass.getName() + "." + name);
   }
}
