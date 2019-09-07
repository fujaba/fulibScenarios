package org.fulib.scenarios.visitor.resolve;

import org.fulib.StrUtil;
import org.fulib.builder.ClassModelBuilder;
import org.fulib.scenarios.ast.MultiDescriptor;
import org.fulib.scenarios.ast.NamedExpr;
import org.fulib.scenarios.ast.decl.*;
import org.fulib.scenarios.ast.expr.Expr;
import org.fulib.scenarios.ast.expr.call.CallExpr;
import org.fulib.scenarios.ast.expr.call.CreationExpr;
import org.fulib.scenarios.ast.expr.collection.ListExpr;
import org.fulib.scenarios.ast.expr.conditional.ConditionalExpr;
import org.fulib.scenarios.ast.expr.primary.NameAccess;
import org.fulib.scenarios.ast.scope.DelegatingScope;
import org.fulib.scenarios.ast.scope.HidingScope;
import org.fulib.scenarios.ast.scope.Scope;
import org.fulib.scenarios.ast.sentence.*;
import org.fulib.scenarios.ast.type.ListType;
import org.fulib.scenarios.ast.type.PrimitiveType;
import org.fulib.scenarios.ast.type.Type;
import org.fulib.scenarios.visitor.ExtractClassDecl;
import org.fulib.scenarios.visitor.ExtractDecl;
import org.fulib.scenarios.visitor.Namer;
import org.fulib.scenarios.visitor.Typer;
import org.fulib.scenarios.visitor.describe.TypeDescriber;

import java.util.*;
import java.util.stream.Collectors;

import static org.fulib.scenarios.diagnostic.Marker.error;
import static org.fulib.scenarios.visitor.resolve.DeclResolver.resolveAssociation;
import static org.fulib.scenarios.visitor.resolve.DeclResolver.resolveAttributeOrAssociation;

public enum SentenceResolver implements Sentence.Visitor<Scope, Sentence>
{
   INSTANCE;

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
      final String processedComment = sectionSentence.getLevel().format(sectionSentence.getText().trim());
      return TemplateSentence.of(processedComment, Collections.emptyList());
   }

   @Override
   public Sentence visit(ThereSentence thereSentence, Scope par)
   {
      final List<Sentence> result = new ArrayList<>();
      for (MultiDescriptor multiDesc : thereSentence.getDescriptors())
      {
         expand(multiDesc, result);
      }
      return new FlattenSentenceList(result).accept(this, par);
   }

   @Override
   public Sentence visit(ExpectSentence expectSentence, Scope par)
   {
      expectSentence.getPredicates().replaceAll(it -> (ConditionalExpr) it.accept(ExprResolver.INSTANCE, par));
      return expectSentence;
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

      final Type receiverType = receiver.accept(Typer.INSTANCE, null);
      final ClassDecl receiverClass = receiverType.accept(ExtractClassDecl.INSTANCE, null);
      if (receiverClass == null)
      {
         par.report(error(receiver.getPosition(), "has.subject.primitive",
                          receiverType.accept(TypeDescriber.INSTANCE, null)));
         return hasSentence;
      }

      final String receiverName = receiver.accept(Namer.INSTANCE, null);
      final Scope scope = receiverName != null ? new HidingScope(receiverName, par) : par;

      for (final NamedExpr namedExpr : hasSentence.getClauses())
      {
         this.resolveHasNamedExpr(namedExpr, receiverClass, scope);
      }

      return hasSentence;
   }

   private void resolveHasNamedExpr(NamedExpr namedExpr, ClassDecl objectClass, Scope scope)
   {
      final Name name = namedExpr.getName();
      final Name otherName = namedExpr.getOtherName();

      final Expr expr = namedExpr.getExpr().accept(ExprResolver.INSTANCE, scope);
      namedExpr.setExpr(expr);

      if (otherName == null)
      {
         namedExpr.setName(resolveAttributeOrAssociation(scope, objectClass, name, expr));
         return;
      }

      final String assocName = name.accept(Namer.INSTANCE, null);
      final Type exprType = expr.accept(Typer.INSTANCE, scope);
      final int cardinality = exprType instanceof ListType ? ClassModelBuilder.MANY : 1;
      final ClassDecl otherClass = exprType.accept(ExtractClassDecl.INSTANCE, null);
      final String otherAssocName = otherName.accept(Namer.INSTANCE, null);
      final int otherCardinality = namedExpr.getOtherMany() ? ClassModelBuilder.MANY : ClassModelBuilder.ONE;

      if (otherClass == null)
      {
         scope.report(error(otherName.getPosition(), "attribute.reverse.name", otherAssocName, objectClass.getName(),
                            assocName));
         return;
      }

      final AssociationDecl assoc = resolveAssociation(scope, objectClass, assocName, cardinality, otherClass,
                                                       otherAssocName, otherCardinality, name.getPosition(),
                                                       otherName.getPosition());
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

   @Override
   public Sentence visit(IsSentence isSentence, Scope par)
   {
      final VarDecl varDecl = isSentence.getDescriptor();
      final Expr expr = varDecl.getExpr().accept(ExprResolver.INSTANCE, par);
      final Type exprType = expr.accept(Typer.INSTANCE, null);

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
      if (existing != varDecl && existing instanceof VarDecl)
      {
         return AssignSentence.of((VarDecl) existing, expr);
      }

      varDecl.setName(name);
      varDecl.setExpr(expr);
      if (varDecl.getType() == null)
      {
         varDecl.setType(exprType);
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
      return expand(areSentence.getDescriptor()).accept(this, par);
   }

   // --------------- ActorSentence.Visitor ---------------

   private static Sentence expand(MultiDescriptor descriptor)
   {
      final List<Sentence> result = new ArrayList<>();
      expand(descriptor, result);
      return new FlattenSentenceList(result);
   }

   private static void expand(MultiDescriptor multiDesc, List<Sentence> result)
   {
      final List<String> names = getNames(multiDesc);
      final List<VarDecl> varDecls = new ArrayList<>(names.size());

      // collect variable declarations from names
      for (String name : names)
      {
         final CreationExpr expr = CreationExpr.of(multiDesc.getType(), Collections.emptyList());
         final VarDecl varDecl = VarDecl.of(name, null, expr);

         varDecls.add(varDecl);
         result.add(IsSentence.of(varDecl));
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

   private static List<String> getNames(MultiDescriptor multiDesc)
   {
      final List<String> names = multiDesc.getNames();

      if (!names.isEmpty())
      {
         return names;
      }

      // user did not declare names, infer from attributes or class name
      for (NamedExpr attribute : multiDesc.getAttributes())
      {
         if (attribute.getExpr() instanceof ListExpr)
         {
            final List<Expr> elements = ((ListExpr) attribute.getExpr()).getElements();
            final List<String> potentialNames = elements.stream().map(it -> it.accept(Namer.INSTANCE, null))
                                                        .collect(Collectors.toList());

            if (!potentialNames.contains(null))
            {
               return potentialNames;
            }
         }
         else
         {
            final String potentialName = attribute.getExpr().accept(Namer.INSTANCE, null);
            if (potentialName != null)
            {
               return Collections.singletonList(potentialName);
            }
         }
      }

      final String className = multiDesc.getType().accept(Namer.INSTANCE, null);
      final String objectName = StrUtil.downFirstChar(className);
      return Collections.singletonList(objectName);
   }

   @Override
   public Sentence visit(CreateSentence createSentence, Scope par)
   {
      return expand(createSentence.getDescriptor()).accept(this, par);
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

   private Sentence resolveAssignment(Sentence original, Scope par, Expr source, Expr target,
      Expr.Visitor<Expr, Sentence> resolve, String code)
   {
      final Sentence sentence = target.accept(resolve, source);
      if (sentence != null)
      {
         return sentence.accept(this, par);
      }

      par.report(error(original.getPosition(), code, target.getClass().getEnclosingClass().getSimpleName()));
      return original;
   }

   @Override
   public Sentence visit(WriteSentence writeSentence, Scope par)
   {
      // TODO maybe add .accept(ExprResolver.INSTANCE, par)
      final Expr source = writeSentence.getSource();
      final Expr target = writeSentence.getTarget();
      return this.resolveAssignment(writeSentence, par, source, target, AssignmentResolve.INSTANCE,
                                    "write.target.invalid");
   }

   @Override
   public Sentence visit(AddSentence addSentence, Scope par)
   {
      final Expr source = addSentence.getSource().accept(ExprResolver.INSTANCE, par);
      final Expr target = addSentence.getTarget();
      return this.resolveAssignment(addSentence, par, source, target, AddResolve.INSTANCE, "add.target.invalid");
   }

   @Override
   public Sentence visit(RemoveSentence removeSentence, Scope par)
   {
      final Expr source = removeSentence.getSource().accept(ExprResolver.INSTANCE, par);
      final Expr target = removeSentence.getTarget();
      return this.resolveAssignment(removeSentence, par, source, target, RemoveResolve.INSTANCE,
                                    "remove.target.invalid");
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

      final Type listType = collection.accept(Typer.INSTANCE, par);
      final Type type;
      if (listType instanceof ListType)
      {
         type = ((ListType) listType).getElementType();
      }
      else
      {
         if (listType != PrimitiveType.ERROR)
         {
            par.report(error(collection.getPosition(), "take.source.type", listType.accept(TypeDescriber.INSTANCE, null)));
         }
         type = PrimitiveType.ERROR;
      }

      final VarDecl varDecl = resolveVar(takeSentence, par, exampleName, type);
      final Scope scope = new DelegatingScope(par)
      {
         @Override
         public Decl resolve(String name)
         {
            return name.equals(varDecl.getName()) || name.equals(exampleName) ? varDecl : super.resolve(name);
         }
      };

      takeSentence.setBody(takeSentence.getBody().accept(this, scope));
      return takeSentence;
   }

   private static VarDecl resolveVar(TakeSentence takeSentence, Scope par, String exampleName, Type type)
   {
      final Name name = takeSentence.getVarName();
      final String varName;

      if (name != null)
      {
         final Decl decl = name.accept(ExtractDecl.INSTANCE, null);
         if (decl != null)
         {
            return (VarDecl) decl;
         }

         varName = name.accept(Namer.INSTANCE, null);
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
      conditionalSentence
         .setCondition((ConditionalExpr) conditionalSentence.getCondition().accept(ExprResolver.INSTANCE, par));
      conditionalSentence.setBody((SentenceList) conditionalSentence.getBody().accept(this, par));
      return conditionalSentence;
   }

   @Override
   public Sentence visit(AssignSentence assignSentence, Scope par)
   {
      assignSentence.setValue(assignSentence.getValue().accept(ExprResolver.INSTANCE, par));
      return assignSentence;
   }

   @Override
   public Sentence visit(ExprSentence exprSentence, Scope par)
   {
      exprSentence.setExpr(exprSentence.getExpr().accept(ExprResolver.INSTANCE, par));
      return exprSentence;
   }

   @Override
   public Sentence visit(TemplateSentence templateSentence, Scope par)
   {
      templateSentence.getExprs().replaceAll(it -> it.accept(ExprResolver.INSTANCE, par));
      return templateSentence;
   }
}
