package org.fulib.scenarios.visitor.resolve;

import org.fulib.builder.ClassModelBuilder;
import org.fulib.scenarios.ast.NamedExpr;
import org.fulib.scenarios.ast.decl.*;
import org.fulib.scenarios.ast.expr.Expr;
import org.fulib.scenarios.ast.expr.conditional.ConditionalExpr;
import org.fulib.scenarios.ast.scope.DelegatingScope;
import org.fulib.scenarios.ast.scope.HidingScope;
import org.fulib.scenarios.ast.scope.Scope;
import org.fulib.scenarios.ast.sentence.*;
import org.fulib.scenarios.ast.type.ClassType;
import org.fulib.scenarios.ast.type.ListType;
import org.fulib.scenarios.ast.type.Type;
import org.fulib.scenarios.visitor.ExtractClassDecl;
import org.fulib.scenarios.visitor.ExtractDecl;
import org.fulib.scenarios.visitor.Namer;
import org.fulib.scenarios.visitor.Typer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.fulib.scenarios.diagnostic.Marker.error;
import static org.fulib.scenarios.visitor.resolve.NameResolver.resolveAssociation;
import static org.fulib.scenarios.visitor.resolve.NameResolver.resolveAttributeOrAssociation;

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
      final int cardinality;
      final ClassDecl otherClass;
      final String otherAssocName = otherName.accept(Namer.INSTANCE, null);
      final int otherCardinality = namedExpr.getOtherMany() ? ClassModelBuilder.MANY : ClassModelBuilder.ONE;

      final Type exprType = expr.accept(Typer.INSTANCE, scope);
      if (exprType instanceof ListType)
      {
         cardinality = ClassModelBuilder.MANY;

         final Type elementType = ((ListType) exprType).getElementType();
         if (elementType instanceof ClassType)
         {
            otherClass = ((ClassType) elementType).getClassDecl();
         }
         else
         {
            scope.report(error(otherName.getPosition(), "attribute.multi.reverse.name", otherAssocName, assocName));
            return;
         }
      }
      else if (exprType instanceof ClassType)
      {
         cardinality = ClassModelBuilder.ONE;
         otherClass = ((ClassType) exprType).getClassDecl();
      }
      else
      {
         scope.report(error(otherName.getPosition(), "attribute.reverse.name", otherAssocName, assocName));
         return;
      }

      final AssociationDecl assoc = resolveAssociation(scope, objectClass, assocName, cardinality, otherClass,
                                                       otherAssocName, otherCardinality, name.getPosition(),
                                                       otherName.getPosition());
      if (assoc != null)
      {
         final AssociationDecl other = assoc.getOther();
         namedExpr.setName(ResolvedName.of(assoc));
         namedExpr.setOtherName(ResolvedName.of(other));
      }
   }

   @Override
   public Sentence visit(IsSentence isSentence, Scope par)
   {
      final VarDecl varDecl = isSentence.getDescriptor();
      final Expr expr = varDecl.getExpr().accept(ExprResolver.INSTANCE, par);
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
         varDecl.setType(varDecl.getExpr().accept(Typer.INSTANCE, null));
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
                                    "sentence.write.invalid");
   }

   @Override
   public Sentence visit(AddSentence addSentence, Scope par)
   {
      final Expr source = addSentence.getSource().accept(ExprResolver.INSTANCE, par);
      final Expr target = addSentence.getTarget();
      return this.resolveAssignment(addSentence, par, source, target, AddResolve.INSTANCE, "sentence.add.invalid");
   }

   @Override
   public Sentence visit(RemoveSentence removeSentence, Scope par)
   {
      final Expr source = removeSentence.getSource().accept(ExprResolver.INSTANCE, par);
      final Expr target = removeSentence.getTarget();
      return this.resolveAssignment(removeSentence, par, source, target, RemoveResolve.INSTANCE,
                                    "sentence.remove.invalid");
   }

   @Override
   public Sentence visit(TakeSentence takeSentence, Scope par)
   {
      takeSentence.setExample(takeSentence.getExample().accept(ExprResolver.INSTANCE, par));
      takeSentence.setCollection(takeSentence.getCollection().accept(ExprResolver.INSTANCE, par));

      final String exampleName = takeSentence.getExample().accept(Namer.INSTANCE, null);

      final VarDecl varDecl = resolveVar(takeSentence, par);
      final Scope scope = new DelegatingScope(par)
      {
         @Override
         public Decl resolve(String name)
         {
            return name.equals(varDecl.getName()) || name.equals(exampleName) ? varDecl : super.resolve(name);
         }
      };

      takeSentence.setActions((SentenceList) takeSentence.getActions().accept(this, scope));
      return takeSentence;
   }

   private static VarDecl resolveVar(TakeSentence takeSentence, Scope par)
   {
      final Type type = takeSentence.getExample().accept(Typer.INSTANCE, null);
      final Name name = takeSentence.getVarName();
      final String varName;
      final String exampleName;

      if (name != null)
      {
         final Decl decl = name.accept(ExtractDecl.INSTANCE, null);
         if (decl != null)
         {
            return (VarDecl) decl;
         }

         varName = name.accept(Namer.INSTANCE, null);
      }
      else if ((exampleName = takeSentence.getExample().accept(Namer.INSTANCE, null)) != null)
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

   @Override
   public Sentence visit(ConditionalSentence conditionalSentence, Scope par)
   {
      conditionalSentence
         .setCondition((ConditionalExpr) conditionalSentence.getCondition().accept(ExprResolver.INSTANCE, par));
      conditionalSentence.setActions((SentenceList) conditionalSentence.getActions().accept(this, par));
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
