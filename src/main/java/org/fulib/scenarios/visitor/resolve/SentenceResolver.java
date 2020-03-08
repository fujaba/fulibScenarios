package org.fulib.scenarios.visitor.resolve;

import org.apache.commons.text.similarity.LevenshteinDistance;
import org.fulib.StrUtil;
import org.fulib.builder.ClassModelBuilder;
import org.fulib.scenarios.ast.MultiDescriptor;
import org.fulib.scenarios.ast.NamedExpr;
import org.fulib.scenarios.ast.decl.*;
import org.fulib.scenarios.ast.expr.Expr;
import org.fulib.scenarios.ast.expr.access.AttributeAccess;
import org.fulib.scenarios.ast.expr.call.CallExpr;
import org.fulib.scenarios.ast.expr.call.CreationExpr;
import org.fulib.scenarios.ast.expr.collection.ListExpr;
import org.fulib.scenarios.ast.expr.operator.BinaryExpr;
import org.fulib.scenarios.ast.expr.operator.BinaryOperator;
import org.fulib.scenarios.ast.expr.primary.NameAccess;
import org.fulib.scenarios.ast.expr.primary.StringLiteral;
import org.fulib.scenarios.ast.pattern.Pattern;
import org.fulib.scenarios.ast.scope.ExtendingScope;
import org.fulib.scenarios.ast.scope.HidingScope;
import org.fulib.scenarios.ast.scope.Scope;
import org.fulib.scenarios.ast.sentence.*;
import org.fulib.scenarios.ast.type.ListType;
import org.fulib.scenarios.ast.type.PrimitiveType;
import org.fulib.scenarios.ast.type.Type;
import org.fulib.scenarios.diagnostic.Marker;
import org.fulib.scenarios.diagnostic.Position;
import org.fulib.scenarios.visitor.ExtractClassDecl;
import org.fulib.scenarios.visitor.Namer;
import org.fulib.scenarios.visitor.TypeConversion;

import java.util.*;
import java.util.function.Predicate;

import static org.fulib.scenarios.diagnostic.Marker.error;
import static org.fulib.scenarios.diagnostic.Marker.note;
import static org.fulib.scenarios.visitor.resolve.DeclResolver.resolveAssociation;

public enum SentenceResolver implements Sentence.Visitor<Scope, Sentence>
{
   INSTANCE;

   // --------------- Sentence.Visitor ---------------

   @Override
   public Sentence visit(SentenceList sentenceList, Scope par)
   {
      final Map<String, Decl> decls = new HashMap<>();
      final Scope scope = new ExtendingScope(decls, par);

      final List<Sentence> oldItems = sentenceList.getItems();
      final List<Sentence> newItems = new ArrayList<>(oldItems.size());
      for (final Sentence item : oldItems)
      {
         final Sentence resolved = item.accept(this, scope);
         resolved.accept(SymbolCollector.INSTANCE, decls);
         FlattenSentenceList.add(newItems, resolved);
      }
      sentenceList.setItems(newItems);

      return sentenceList;
   }

   @Override
   public Sentence visit(SectionSentence sectionSentence, Scope par)
   {
      return sectionSentence;
   }

   @Override
   public Sentence visit(ThereSentence thereSentence, Scope par)
   {
      return expand(thereSentence.getDescriptor(), par).accept(this, par);
   }

   private static Sentence expand(MultiDescriptor descriptor, Scope scope)
   {
      final List<Sentence> result = new ArrayList<>();
      expand(descriptor, result, scope);
      return new FlattenSentenceList(result);
   }

   private static void expand(MultiDescriptor multiDesc, List<Sentence> result, Scope scope)
   {
      final Type type = multiDesc.getType();
      final List<Name> names = getNames(multiDesc);
      final List<Decl> varDecls = new ArrayList<>(names.size());

      // collect variable declarations from names
      for (final Name name : names)
      {
         final String nameValue = name.getValue();

         final CreationExpr expr = CreationExpr.of(type, Collections.emptyList());
         expr.setPosition(name.getPosition());

         final VarDecl varDecl = VarDecl.of(nameValue, null, expr);
         varDecl.setPosition(name.getPosition());
         varDecls.add(varDecl);

         final IsSentence isSentence = IsSentence.of(varDecl);
         isSentence.setPosition(name.getPosition());
         result.add(isSentence);
      }

      // collect attribute assignments
      for (NamedExpr attribute : multiDesc.getAttributes())
      {
         final Name attributeName = attribute.getName();
         final Expr attributeExpr = attribute.getExpr();

         if (names.size() == 1)
         {
            // only one name anyway, easy

            final NameAccess object = NameAccess.of(ResolvedName.of(varDecls.get(0)));
            final HasSentence hasSentence = HasSentence.of(object, Collections.singletonList(attribute));
            result.add(hasSentence);
            continue;
         }

         final List<Expr> elements;
         if (attributeExpr instanceof ListExpr
             && (elements = ((ListExpr) attributeExpr).getElements()).size() == names.size())
         {
            // assigning one by one
            // e.g. Alice and Bob with credits 10 and 20
            // =>
            // alice.setCredits(10);
            // bob.setCredits(20);

            for (int i = 0; i < names.size(); i++)
            {
               final Expr object = NameAccess.of(ResolvedName.of(varDecls.get(i)));
               final NamedExpr partialAttribute = NamedExpr.of(attributeName, elements.get(i));
               final HasSentence hasSentence = HasSentence.of(object, Collections.singletonList(partialAttribute));
               result.add(hasSentence);
            }
         }
         else
         {
            // assigning the same expr to multiple attributes, we need a temporary
            // e.g. Alice and Bob with credits 10
            // =>
            // int _t0 = 10;
            // alice.setCredits(_t0);
            // bob.setCredits(_t0);

            final VarDecl temp = VarDecl.of("temp++", null, attributeExpr);
            result.add(IsSentence.of(temp));

            for (int i = 0; i < names.size(); i++)
            {
               final Expr object = NameAccess.of(ResolvedName.of(varDecls.get(i)));
               final Expr tempAccess = NameAccess.of(ResolvedName.of(temp));
               final NamedExpr partialAttribute = NamedExpr.of(attributeName, tempAccess);
               final HasSentence hasSentence = HasSentence.of(object, Collections.singletonList(partialAttribute));
               result.add(hasSentence);
            }
         }
      }
   }

   private static List<Name> getNames(MultiDescriptor multiDesc)
   {
      final List<Name> names = multiDesc.getNames();

      if (!names.isEmpty())
      {
         return names;
      }

      // user did not declare names, infer from attributes or class name
      outer:
      for (NamedExpr attribute : multiDesc.getAttributes())
      {
         final Expr expr = attribute.getExpr();
         if (expr instanceof ListExpr)
         {
            final List<Expr> elements = ((ListExpr) expr).getElements();
            final List<Name> result = new ArrayList<>(elements.size());

            for (final Expr element : elements)
            {
               final String potentialName = element.accept(Namer.INSTANCE, null);
               if (potentialName == null)
               {
                  continue outer;
               }

               result.add(unresolvedName(potentialName, element.getPosition()));
            }

            return result;
         }

         final String potentialName = expr.accept(Namer.INSTANCE, null);
         if (potentialName != null)
         {
            final Name name = unresolvedName(potentialName, expr.getPosition());
            return Collections.singletonList(name);
         }
      }

      final String className = multiDesc.getType().accept(Namer.INSTANCE, null);
      final String objectName = StrUtil.downFirstChar(className);
      final Name name = unresolvedName(objectName, multiDesc.getType().getPosition());
      return Collections.singletonList(name);
   }

   private static Name unresolvedName(String potentialName, Position position)
   {
      final Name name = UnresolvedName.of(potentialName, null);
      name.setPosition(position);
      return name;
   }

   @Override
   public Sentence visit(ExpectSentence expectSentence, Scope par)
   {
      expectSentence.getPredicates().replaceAll(predicate -> {
         final Expr resolved = predicate.accept(ExprResolver.INSTANCE, par);
         return ExprResolver.checkConditional(resolved, par);
      });
      return expectSentence;
   }

   @Override
   public Sentence visit(PatternExpectSentence patternExpectSentence, Scope par)
   {
      final Map<String, Decl> decls = new HashMap<>();
      for (final Pattern pattern : patternExpectSentence.getPatterns())
      {
         pattern.setType(pattern.getType().accept(TypeResolver.INSTANCE, par));
   
         final Decl oldDecl = pattern.getName().getDecl();
         final VarDecl varDecl;
         if (oldDecl == null)
         {
            varDecl = VarDecl.of(pattern.getName().getValue(), pattern.getType(), null);
            pattern.setName(ResolvedName.of(varDecl));
            decls.put(varDecl.getName(), varDecl);
         }
      }

      final Scope scope = new ExtendingScope(decls, par);
      for (final Pattern pattern : patternExpectSentence.getPatterns())
      {
         pattern.getConstraints().replaceAll(c -> c.accept(ConstraintResolver.INSTANCE, scope));
      }

      return patternExpectSentence;
   }

   @Override
   public Sentence visit(DiagramSentence diagramSentence, Scope par)
   {
      diagramSentence.setObject(diagramSentence.getObject().accept(ExprResolver.INSTANCE, par));
      return diagramSentence;
   }

   @Override
   public Sentence visit(HasSentence hasSentence, Scope par)
   {
      final Expr receiver = hasSentence.getObject().accept(ExprResolver.INSTANCE, par);
      hasSentence.setObject(receiver);

      final Type receiverType = receiver.getType();
      if (receiverType == PrimitiveType.ERROR)
      {
         // recover from previous error
         return hasSentence;
      }

      final ClassDecl receiverClass = receiverType.accept(ExtractClassDecl.INSTANCE, null);
      if (receiverClass == null)
      {
         final Marker error = error(receiver.getPosition(), "has.subject.primitive", receiverType.getDescription());
         addStringLiteralTypoNotes(par, receiver, error);
         par.report(error);
         return hasSentence;
      }

      final String receiverName = receiver.accept(Namer.INSTANCE, null);
      final Scope scope = receiverName != null ? new HidingScope(receiverName, par) : par;

      for (final NamedExpr namedExpr : hasSentence.getClauses())
      {
         resolveHasNamedExpr(namedExpr, receiverClass, scope);
      }

      return hasSentence;
   }

   public static void addStringLiteralTypoNotes(Scope scope, Expr expr, Marker parent)
   {
      if (expr instanceof ListExpr)
      {
         final ListType type = (ListType) expr.getType();
         final Marker note = note(expr.getPosition(), "list.type", type.getElementType().getDescription());
         parent.note(note);
         for (final Expr element : ((ListExpr) expr).getElements())
         {
            addStringLiteralTypoNotes(scope, element, note);
         }
         return;
      }

      if (!(expr instanceof StringLiteral))
      {
         return;
      }

      final StringLiteral stringLiteral = (StringLiteral) expr;
      final String stringValue = stringLiteral.getValue();
      final String identifier = stringValue.replaceAll("\\s+", "");

      final Map<String, Decl> decls = new LinkedHashMap<>();
      scope.list(decls::putIfAbsent);

      decls.keySet().stream().filter(caseInsensitiveLevenshteinDistance(identifier, 2)).forEach(key -> {
         parent.note(note(expr.getPosition(), "stringliteral.typo", stringValue, key));
      });
   }

   static Predicate<String> caseInsensitiveLevenshteinDistance(String base, int threshold)
   {
      final String lowerBase = base.toLowerCase();
      final LevenshteinDistance levenshteinDistance = new LevenshteinDistance(2);
      return s -> {
         final int result = levenshteinDistance.apply(lowerBase, s);
         return 0 <= result && result <= threshold;
      };
   }

   static void resolveHasNamedExpr(NamedExpr namedExpr, ClassDecl objectClass, Scope scope)
   {
      final Name name = namedExpr.getName();
      final Name otherName = namedExpr.getOtherName();

      final Expr expr = namedExpr.getExpr().accept(ExprResolver.INSTANCE, scope);
      namedExpr.setExpr(expr);

      if (otherName == null)
      {
         resolveSimpleHasNamedExpr(namedExpr, objectClass, scope);
         return;
      }

      final String assocName = name.getValue();
      final Type exprType = expr.getType();
      final int cardinality = exprType instanceof ListType ? ClassModelBuilder.MANY : 1;
      final ClassDecl otherClass = exprType.accept(ExtractClassDecl.INSTANCE, null);
      final String otherAssocName = otherName.getValue();
      final int otherCardinality = namedExpr.getOtherMany() ? ClassModelBuilder.MANY : ClassModelBuilder.ONE;

      if (otherClass == null)
      {
         resolveSimpleHasNamedExpr(namedExpr, objectClass, scope);

         final Marker error = error(otherName.getPosition(), "attribute.reverse.name", otherAssocName,
                                    objectClass.getName(), assocName);
         addStringLiteralTypoNotes(scope, expr, error);
         scope.report(error);
         return;
      }

      final AssociationDecl assoc = resolveAssociation(scope, objectClass, assocName, cardinality, otherClass,
                                                       otherAssocName, otherCardinality, name.getPosition(),
                                                       otherName.getPosition(), expr);
      if (assoc != null)
      {
         final AssociationDecl other = assoc.getOther();
         namedExpr.setName(ResolvedName.of(assoc));
         if (other != null)
         {
            namedExpr.setOtherName(ResolvedName.of(other));
         }
      }
   }

   private static void resolveSimpleHasNamedExpr(NamedExpr namedExpr, ClassDecl classDecl, Scope scope)
   {
      final Name name = namedExpr.getName();

      if (name.getDecl() != null)
      {
         // already resolved
         return;
      }

      final Expr expr = namedExpr.getExpr();
      final Decl decl = DeclResolver.resolveAttributeOrAssociation(scope, classDecl, name.getValue(), expr,
                                                                   name.getPosition());
      if (decl == null)
      {
         return;
      }

      namedExpr.setName(ResolvedName.of(decl));

      final Expr converted = TypeConversion.convert(expr, decl.getType());
      if (converted != null)
      {
         namedExpr.setExpr(converted);
      }
      // no extra error necessary, already reported by resolveAttributeOrAssociation
   }

   @Override
   public Sentence visit(IsSentence isSentence, Scope par)
   {
      final VarDecl varDecl = isSentence.getDescriptor();
      final Expr expr = varDecl.getExpr().accept(ExprResolver.INSTANCE, par);
      final Type exprType = expr.getType();

      if (exprType == PrimitiveType.VOID)
      {
         return ExprSentence.of(expr);
      }

      String name = varDecl.getName();

      if (name == null)
      {
         name = expr.accept(Namer.INSTANCE, null);
      }
      else if (name.contains("++"))
      {
         name = findUnique(name, par);
      }

      final Decl existing = par.resolve(name);
      if (existing != null)
      {
         par.report(error(varDecl.getPosition(), "variable.redeclaration", name)
                       .note(note(existing.getPosition(), "variable.declaration.first", name)));
      }

      varDecl.setName(name);

      if (varDecl.getType() == null)
      {
         varDecl.setType(exprType);
         varDecl.setExpr(expr);
      }
      else
      {
         varDecl.setExpr(checkAssignment(expr, varDecl, true, par));
      }
      return isSentence;
   }

   private static String findUnique(String name, Scope par)
   {
      final int index = name.indexOf("++");
      final String prefix = name.substring(0, index);
      final String suffix = name.substring(index + 2);

      for (int i = 1; ; i++)
      {
         final String numbered = prefix + i + suffix;
         if (par.resolve(numbered) == null)
         {
            return numbered;
         }
      }
   }

   @Override
   public Sentence visit(AreSentence areSentence, Scope par)
   {
      return expand(areSentence.getDescriptor(), par).accept(this, par);
   }

   // --------------- ActorSentence.Visitor ---------------

   @Override
   public Sentence visit(CreateSentence createSentence, Scope par)
   {
      return expand(createSentence.getDescriptor(), par).accept(this, par);
   }

   @Override
   public Sentence visit(CallSentence callSentence, Scope par)
   {
      final CallExpr call = callSentence.getCall();

      final String name = call.accept(Namer.INSTANCE, null);
      if (name == null)
      {
         return ExprSentence.of(call).accept(this, par);
      }

      final Name actor = callSentence.getActor();
      final NameAccess target = NameAccess.of(UnresolvedName.of(name, null));
      final WriteSentence writeSentence = WriteSentence.of(actor, call, target);
      return writeSentence.accept(this, par);
   }

   @Override
   public Sentence visit(AnswerSentence answerSentence, Scope par)
   {
      if (answerSentence.getActor() != null)
      {
         answerSentence.setActor(answerSentence.getActor().accept(NameResolver.INSTANCE, par));
      }

      answerSentence.setResult(answerSentence.getResult().accept(ExprResolver.INSTANCE, par));
      return answerSentence;
   }

   @Override
   public Sentence visit(WriteSentence writeSentence, Scope par)
   {
      final Expr source = writeSentence.getSource();
      final Expr target = writeSentence.getTarget();
      final Sentence sentence = target.accept(new AssignmentResolve(par), source);
      if (sentence != null)
      {
         return sentence;
      }

      par.report(
         error(target.getPosition(), "write.target.invalid", target.getClass().getEnclosingClass().getSimpleName()));
      return writeSentence;
   }

   @Override
   public Sentence visit(AddSentence addSentence, Scope par)
   {
      return resolveMutating(addSentence, true, BinaryOperator.PLUS, par, "add.source.type", "add.target.type",
                             "add.target.not.name");
   }

   @Override
   public Sentence visit(RemoveSentence removeSentence, Scope par)
   {
      return resolveMutating(removeSentence, true, BinaryOperator.MINUS, par, "remove.source.type",
                             "remove.target.type", "remove.target.not.name");
   }

   private static Sentence resolveMutating(MutatingSentence sentence, boolean allowLists, BinaryOperator operator,
      Scope par, String sourceTypeCode, String targetTypeCode, String targetShapeCode)
   {
      final Expr resolvedSource = sentence.getSource().accept(ExprResolver.INSTANCE, par);
      final Expr target = sentence.getTarget().accept(ExprResolver.INSTANCE, par);
      sentence.setTarget(target);

      final Type targetType = target.getType();
      if (allowLists && !PrimitiveType.isNumeric(targetType))
      {
         sentence.setSource(convertAsList(resolvedSource, target, sourceTypeCode, targetTypeCode, par));
         return sentence;
      }

      final Expr checkedSource = TypeConversion.convert(resolvedSource, targetType, par, sourceTypeCode);
      sentence.setSource(checkedSource);

      final Sentence compoundAssignment = compoundAssignment(target, operator, checkedSource, par,
                                                             sentence.getPosition());
      if (compoundAssignment != null)
      {
         return compoundAssignment;
      }

      par.report(error(target.getPosition(), targetShapeCode, target.getClass().getEnclosingClass().getSimpleName()));
      return sentence;
   }

   private static Expr convertAsList(Expr source, Expr target, String invalidSource, String invalidTarget, Scope par)
   {
      final Type targetType = target.getType();
      if (targetType == PrimitiveType.ERROR)
      {
         return source;
      }

      if (!(targetType instanceof ListType))
      {
         final Marker error = error(target.getPosition(), invalidTarget, targetType.getDescription());
         addStringLiteralTypoNotes(par, target, error);
         par.report(error);
         return source;
      }

      final Type elementType = ((ListType) targetType).getElementType();
      final Expr sourceAsElement = TypeConversion.convert(source, elementType);
      if (sourceAsElement != null)
      {
         return sourceAsElement;
      }

      final Expr sourceAsList = TypeConversion.convert(source, targetType);
      if (sourceAsList != null)
      {
         return sourceAsList;
      }

      final Type sourceType = source.getType();
      final Marker error = error(source.getPosition(), invalidSource, sourceType.getDescription(),
                                 targetType.getDescription());
      addStringLiteralTypoNotes(par, source, error);
      par.report(error);
      return source;
   }

   private static Sentence compoundAssignment(Expr lhs, BinaryOperator operator, Expr rhs, Scope par,
      Position position)
   {
      if (lhs instanceof AttributeAccess)
      {
         final AttributeAccess attributeAccess = (AttributeAccess) lhs;
         final Expr receiver = attributeAccess.getReceiver();
         final Type receiverType = receiver.getType();
         final Position receiverPos = receiver.getPosition();

         // <receiver>.<attribute>
         // ==>
         // var temp = <receiver>;
         // temp.set<attribute>(temp.get<attribute>() + <source>);

         final VarDecl temp = VarDecl.of(findUnique("temp++", par), receiverType, receiver);
         temp.setPosition(receiverPos);
         final IsSentence isSentence = IsSentence.of(temp);
         isSentence.setPosition(receiverPos);

         final NameAccess tempAccess = NameAccess.of(ResolvedName.of(temp));
         tempAccess.setPosition(receiverPos);

         attributeAccess.setReceiver(tempAccess);

         final BinaryExpr binaryOp = BinaryExpr.of(attributeAccess, operator, rhs);
         binaryOp.setPosition(position);
         final HasSentence hasSentence = HasSentence.of(tempAccess, Collections.singletonList(
            NamedExpr.of(attributeAccess.getName(), binaryOp)));
         hasSentence.setPosition(attributeAccess.getPosition());

         return new FlattenSentenceList(Arrays.asList(isSentence, hasSentence));
      }

      if (lhs instanceof NameAccess)
      {
         final Decl decl = ((NameAccess) lhs).getName().getDecl();
         final AssignSentence assignSentence = AssignSentence.of(decl, operator, rhs);
         assignSentence.setPosition(position);
         return assignSentence;
      }
      return null;
   }

   @Override
   public Sentence visit(TakeSentence takeSentence, Scope par)
   {
      Expr example = takeSentence.getExample();
      final String exampleName;
      if (example != null)
      {
         example = example.accept(ExprResolver.INSTANCE, par);
         exampleName = example.accept(Namer.INSTANCE, null);
         takeSentence.setExample(example);
      }
      else
      {
         exampleName = null;
      }

      final Expr collection = takeSentence.getCollection().accept(ExprResolver.INSTANCE, par);
      takeSentence.setCollection(collection);

      final Type listType = collection.getType();
      final Type type;
      if (listType instanceof ListType)
      {
         type = ((ListType) listType).getElementType();
      }
      else
      {
         if (listType != PrimitiveType.ERROR)
         {
            par.report(error(collection.getPosition(), "take.source.type", listType.getDescription()));
         }
         type = PrimitiveType.ERROR;
      }

      final Decl varDecl = resolveVar(takeSentence, par, exampleName, type);
      final Map<String, Decl> decls = new HashMap<>();
      decls.put(varDecl.getName(), varDecl);
      decls.put(exampleName, varDecl);
      final Scope scope = new ExtendingScope(decls, par);

      takeSentence.setBody(takeSentence.getBody().accept(this, scope));
      return takeSentence;
   }

   private static Decl resolveVar(TakeSentence takeSentence, Scope par, String exampleName, Type type)
   {
      final Name name = takeSentence.getVarName();
      final String varName;

      if (name != null)
      {
         final Decl decl = name.getDecl();
         if (decl != null)
         {
            return decl;
         }

         varName = name.getValue();
      }
      else if (exampleName != null)
      {
         varName = exampleName;
      }
      else
      {
         varName = findUnique("i++", par);
      }

      final VarDecl varDecl = VarDecl.of(varName, type, null);
      takeSentence.setVarName(ResolvedName.of(varDecl));
      return varDecl;
   }

   // --------------- (end of ActorSentence.Visitor) ---------------

   @Override
   public Sentence visit(ConditionalSentence conditionalSentence, Scope par)
   {
      final Expr condition = conditionalSentence.getCondition();
      final Expr resolvedCondition = condition.accept(ExprResolver.INSTANCE, par);
      final Expr checkedCondition = ExprResolver.checkConditional(resolvedCondition, par);
      conditionalSentence.setCondition(checkedCondition);

      conditionalSentence.setBody(conditionalSentence.getBody().accept(this, par));
      return conditionalSentence;
   }

   @Override
   public Sentence visit(AssignSentence assignSentence, Scope par)
   {
      final Expr expr = assignSentence.getValue().accept(ExprResolver.INSTANCE, par);
      final Decl target = assignSentence.getTarget();

      assignSentence.setValue(checkAssignment(expr, target, false, par));
      return assignSentence;
   }

   private static Expr checkAssignment(Expr expr, Decl target, boolean isNew, Scope par)
   {
      final Type targetType = target.getType();
      final Expr converted = TypeConversion.convert(expr, targetType);

      if (converted != null)
      {
         return converted;
      }

      final Type exprType = expr.getType();
      final Marker error = error(expr.getPosition(), "assign.type", exprType.getDescription(), target.getName(),
                                 targetType.getDescription());
      if (!isNew)
      {
         error.note(note(target.getPosition(), "variable.declaration.first", target.getName()));
      }
      par.report(error);
      return expr;
   }

   @Override
   public Sentence visit(ExprSentence exprSentence, Scope par)
   {
      exprSentence.setExpr(exprSentence.getExpr().accept(ExprResolver.INSTANCE, par));
      return exprSentence;
   }
}
