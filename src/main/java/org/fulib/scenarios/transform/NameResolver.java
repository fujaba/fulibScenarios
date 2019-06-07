package org.fulib.scenarios.transform;

import org.fulib.StrUtil;
import org.fulib.scenarios.ast.NamedExpr;
import org.fulib.scenarios.ast.Scenario;
import org.fulib.scenarios.ast.ScenarioFile;
import org.fulib.scenarios.ast.ScenarioGroup;
import org.fulib.scenarios.ast.decl.*;
import org.fulib.scenarios.ast.expr.Expr;
import org.fulib.scenarios.ast.expr.access.AttributeAccess;
import org.fulib.scenarios.ast.expr.access.ExampleAccess;
import org.fulib.scenarios.ast.expr.call.CallExpr;
import org.fulib.scenarios.ast.expr.call.CreationExpr;
import org.fulib.scenarios.ast.expr.collection.CollectionExpr;
import org.fulib.scenarios.ast.expr.collection.ListExpr;
import org.fulib.scenarios.ast.expr.conditional.AttributeCheckExpr;
import org.fulib.scenarios.ast.expr.conditional.ConditionalExpr;
import org.fulib.scenarios.ast.expr.primary.NameAccess;
import org.fulib.scenarios.ast.expr.primary.NumberLiteral;
import org.fulib.scenarios.ast.expr.primary.PrimaryExpr;
import org.fulib.scenarios.ast.expr.primary.StringLiteral;
import org.fulib.scenarios.ast.sentence.*;
import org.fulib.scenarios.transform.scope.DelegatingScope;
import org.fulib.scenarios.transform.scope.HidingScope;
import org.fulib.scenarios.transform.scope.Scope;

import java.util.*;
import java.util.stream.Collectors;

public enum NameResolver implements ScenarioGroup.Visitor<Object, Object>, ScenarioFile.Visitor<Scope, Object>,
                                       Scenario.Visitor<Scope, Object>, Sentence.Visitor<Scope, Object>,
                                       Expr.Visitor<Scope, Expr>, Name.Visitor<Scope, Name>
{
   INSTANCE;

   // --------------- ScenarioGroup.Visitor ---------------

   @Override
   public Object visit(ScenarioGroup scenarioGroup, Object par)
   {
      final Scope scope = new Scope()
      {
         @Override
         public Scope getOuter()
         {
            return null;
         }

         @Override
         public <T> T getEnclosing(Class<T> type)
         {
            return type.isAssignableFrom(ScenarioGroup.class) ? (T) scenarioGroup : null;
         }

         @Override
         public Decl resolve(String name)
         {
            return scenarioGroup.getClasses().get(name);
         }

         @Override
         public void add(Decl decl)
         {
            scenarioGroup.getClasses().put(decl.getName(), (ClassDecl) decl);
         }
      };
      for (final ScenarioFile file : scenarioGroup.getFiles().values())
      {
         file.accept(this, scope);
      }
      return null;
   }

   // --------------- ScenarioFile.Visitor ---------------

   @Override
   public Object visit(ScenarioFile scenarioFile, Scope par)
   {
      final ScenarioGroup group = scenarioFile.getGroup();
      final String className = scenarioFile.getName().replaceAll("\\W", "") + "Test";
      final ClassDecl classDecl = ClassDecl
                                     .of(group, className, className, new LinkedHashMap<>(), new LinkedHashMap<>(),
                                         new ArrayList<>());

      // group.getClasses().put(className, classDecl);
      scenarioFile.setClassDecl(classDecl);

      final Scope scope = new DelegatingScope(par)
      {
         @Override
         public <T> T getEnclosing(Class<T> type)
         {
            if (type.isAssignableFrom(ClassDecl.class))
            {
               return (T) classDecl;
            }
            if (type.isAssignableFrom(ScenarioFile.class))
            {
               return (T) scenarioFile;
            }
            return super.getEnclosing(type);
         }

         @Override
         public Decl resolve(String name)
         {
            return className.equals(name) ? classDecl : super.resolve(name);
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
      final String methodName = "scenario" + scenario.getName().replaceAll("\\W", "");
      final SentenceList body = scenario.getBody();
      final MethodDecl methodDecl = MethodDecl.of(classDecl, methodName, null, "void", body);

      final ParameterDecl thisParam = ParameterDecl.of(methodDecl, "this", classDecl.getType());
      methodDecl.setParameters(Collections.singletonList(thisParam));

      classDecl.getMethods().add(methodDecl);
      scenario.setMethodDecl(methodDecl);

      body.accept(this, new DelegatingScope(par)
      {
         @Override
         public <T> T getEnclosing(Class<T> type)
         {
            if (type.isAssignableFrom(MethodDecl.class))
            {
               return (T) methodDecl;
            }
            if (type.isAssignableFrom(Scenario.class))
            {
               return (T) scenario;
            }
            return super.getEnclosing(type);
         }

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
   public Object visit(Sentence sentence, Scope par)
   {
      return null;
   }

   @Override
   public Object visit(SentenceList sentenceList, Scope par)
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
            decls.put(decl.getName(), decl);
         }
      };

      for (final Sentence item : sentenceList.getItems())
      {
         item.accept(SymbolCollector.INSTANCE, decls);
         item.accept(this, scope);
      }
      return null;
   }

   @Override
   public Object visit(ThereSentence thereSentence, Scope par)
   {
      throw new UnsupportedOperationException();
   }

   @Override
   public Object visit(ExpectSentence expectSentence, Scope par)
   {
      expectSentence.getPredicates().replaceAll(it -> (ConditionalExpr) it.accept(this, par));
      return null;
   }

   @Override
   public Object visit(DiagramSentence diagramSentence, Scope par)
   {
      diagramSentence.setObject(diagramSentence.getObject().accept(this, par));
      return null;
   }

   @Override
   public Object visit(HasSentence hasSentence, Scope par)
   {
      hasSentence.setObject(hasSentence.getObject().accept(this, par));

      final ClassDecl objectClass = resolveClass(par, hasSentence.getObject());
      final String name = hasSentence.getObject().accept(Namer.INSTANCE, null);
      final Scope scope = name != null ? new HidingScope(name, par) : par;

      for (final NamedExpr namedExpr : hasSentence.getClauses())
      {
         namedExpr.setExpr(namedExpr.getExpr().accept(this, scope));
         namedExpr.setName(resolveAttributeOrAssociation(objectClass, namedExpr.getName(), namedExpr.getExpr()));
      }

      return null;
   }

   @Override
   public Object visit(IsSentence isSentence, Scope par)
   {
      final VarDecl varDecl = isSentence.getDescriptor();
      varDecl.setExpr(varDecl.getExpr().accept(this, par));

      if (varDecl.getType() == null)
      {
         varDecl.setType(varDecl.getExpr().accept(Typer.INSTANCE, null));
      }
      return null;
   }

   @Override
   public Object visit(CreateSentence createSentence, Scope par)
   {
      throw new UnsupportedOperationException();
   }

   @Override
   public Object visit(CallSentence callSentence, Scope par)
   {
      throw new UnsupportedOperationException();
   }

   @Override
   public Object visit(AnswerSentence answerSentence, Scope par)
   {
      if (answerSentence.getActor() != null)
      {
         answerSentence.setActor(answerSentence.getActor().accept(this, par));
      }

      answerSentence.setResult(answerSentence.getResult().accept(this, par));
      return null;
   }

   @Override
   public Object visit(ExprSentence exprSentence, Scope par)
   {
      exprSentence.setExpr(exprSentence.getExpr().accept(this, par));
      return null;
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
      attributeAccess.setReceiver(attributeAccess.getReceiver().accept(this, par));
      attributeAccess
         .setName(getAttributeOrAssociation(par, attributeAccess.getReceiver(), attributeAccess.getName()));
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
      final String className = StrUtil.cap(creationExpr.getClassName().accept(Namer.INSTANCE, par));
      final ClassDecl classDecl = resolveClass(par, className);

      creationExpr.setClassName(ResolvedName.of(classDecl));

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
      final Expr receiver = callExpr.getReceiver();
      if (receiver != null)
      {
         callExpr.setReceiver(receiver.accept(this, par));
      }
      else
      {
         final Decl thisDecl = par.resolve("this");
         final NameAccess getThis = NameAccess.of(ResolvedName.of(thisDecl));
         callExpr.setReceiver(getThis);
      }
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

      // match arguments and parameters
      for (int i = 0; i < arguments.size(); i++)
      {
         final NamedExpr argument = arguments.get(i);
         final String name = argument.getName().accept(Namer.INSTANCE, null);
         final Expr expr = argument.getExpr();
         final ParameterDecl param;

         if (isNew)
         {
            final String type = expr.accept(Typer.INSTANCE, null);
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
         final String returnType = callExpr.accept(Typer.INSTANCE, null);
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
      attributeCheckExpr.setReceiver(attributeCheckExpr.getReceiver().accept(this, par));
      attributeCheckExpr.setValue(attributeCheckExpr.getValue().accept(this, par));
      attributeCheckExpr.setAttribute(
         getAttributeOrAssociation(par, attributeCheckExpr.getReceiver(), attributeCheckExpr.getAttribute()));
      return attributeCheckExpr;
   }

   @Override
   public Expr visit(CollectionExpr collectionExpr, Scope par)
   {
      throw new UnsupportedOperationException();
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

   static ClassDecl resolveClass(Scope scope, Expr expr)
   {
      return resolveClass(scope, expr.accept(Typer.INSTANCE, null));
   }

   static ClassDecl resolveClass(Scope scope, String name)
   {
      return resolveClass(scope.getEnclosing(ScenarioGroup.class), name);
   }

   static ClassDecl resolveClass(ScenarioGroup group, String name)
   {
      ClassDecl decl = group.getClasses().get(name);

      if (decl != null)
      {
         return decl;
      }

      decl = ClassDecl.of(group, name, name, new LinkedHashMap<>(), new LinkedHashMap<>(), new ArrayList<>());
      group.getClasses().put(name, decl);
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

      final String attributeType = rhs.accept(Typer.INSTANCE, null);
      final ClassDecl otherClass;

      if (attributeType.startsWith("List<"))
      {
         // new value is multi-valued

         final String otherType = attributeType.substring(5, attributeType.length() - 1); // strip List< and >
         otherClass = classDecl.getGroup().getClasses().get(otherType);

         if (otherClass == null)
         {
            // was element type that we have no control over, e.g. List<String>
            return resolveAttribute(classDecl, attributeName, attributeType);
         }
         else
         {
            return resolveAssociation(classDecl, attributeName, otherClass, 2);
         }
      }
      else if ((otherClass = classDecl.getGroup().getClasses().get(attributeType)) != null)
      {
         return resolveAssociation(classDecl, attributeName, otherClass, 1);
      }
      else
      {
         return resolveAttribute(classDecl, attributeName, attributeType);
      }
   }

   static AttributeDecl resolveAttribute(ClassDecl classDecl, String name, String type)
   {
      final AttributeDecl existing = classDecl.getAttributes().get(name);
      if (existing != null)
      {
         final String existingType = existing.getType();
         if (!type.equals(existingType))
         {
            throw new IllegalStateException(
               "mismatched attribute type " + classDecl.getName() + "." + name + ": " + existingType + " vs "
               + type);
         }

         return existing;
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

      final AssociationDecl association = AssociationDecl
                                             .of(classDecl, name, cardinality, otherClass, otherClass.getType(),
                                                 null);
      final AssociationDecl other = AssociationDecl
                                       .of(otherClass, classDecl.getName(), 1, classDecl, classDecl.getType(), null);

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

      throw new IllegalStateException("unresolved attribute " + receiverClass.getName() + "." + name);
   }
}
