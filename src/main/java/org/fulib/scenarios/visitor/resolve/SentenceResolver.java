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
import org.fulib.scenarios.visitor.ExtractDecl;
import org.fulib.scenarios.visitor.Namer;
import org.fulib.scenarios.visitor.Typer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.fulib.scenarios.visitor.resolve.NameResolver.*;

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

      final ClassDecl objectClass = resolveClass(par, receiver);
      final String name = receiver.accept(Namer.INSTANCE, null);
      final Scope scope = name != null ? new HidingScope(name, par) : par;

      for (final NamedExpr namedExpr : hasSentence.getClauses())
      {
         this.resolveHasNamedExpr(namedExpr, objectClass, scope);
      }

      return hasSentence;
   }

   private void resolveHasNamedExpr(NamedExpr namedExpr, ClassDecl objectClass, Scope scope)
   {
      final Expr expr = namedExpr.getExpr().accept(ExprResolver.INSTANCE, scope);
      namedExpr.setExpr(expr);

      if (namedExpr.getOtherName() == null)
      {
         namedExpr.setName(resolveAttributeOrAssociation(objectClass, namedExpr.getName(), expr));
         return;
      }

      final String assocName = namedExpr.getName().accept(Namer.INSTANCE, null);
      final int cardinality;
      final ClassDecl otherClass;
      final String otherName = namedExpr.getOtherName().accept(Namer.INSTANCE, null);
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
            throw new IllegalStateException("illegal reverse association name for attribute");
         }
      }
      else if (exprType instanceof ClassType)
      {
         cardinality = ClassModelBuilder.ONE;
         otherClass = ((ClassType) exprType).getClassDecl();
      }
      else
      {
         throw new IllegalStateException("illegal reverse association name for attribute");
      }

      final AssociationDecl assoc = resolveAssociation(objectClass, assocName, cardinality, otherClass, otherName,
                                                       otherCardinality);
      final AssociationDecl other = assoc.getOther();
      namedExpr.setName(ResolvedName.of(assoc));
      namedExpr.setOtherName(ResolvedName.of(other));
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

   @Override
   public Sentence visit(AddSentence addSentence, Scope par)
   {
      final Expr source = addSentence.getSource().accept(ExprResolver.INSTANCE, par);
      final Expr target = addSentence.getTarget();
      return target.accept(AddResolve.INSTANCE, source).accept(this, par);
   }

   @Override
   public Sentence visit(RemoveSentence removeSentence, Scope par)
   {
      final Expr source = removeSentence.getSource().accept(ExprResolver.INSTANCE, par);
      final Expr target = removeSentence.getTarget();
      return target.accept(RemoveResolve.INSTANCE, source).accept(this, par);
   }

   @Override
   public Sentence visit(TakeSentence takeSentence, Scope par)
   {
      takeSentence.setExample(takeSentence.getExample().accept(ExprResolver.INSTANCE, par));
      takeSentence.setCollection(takeSentence.getCollection().accept(ExprResolver.INSTANCE, par));

      final String exampleName = takeSentence.getExample().accept(Namer.INSTANCE, null);

      final VarDecl varDecl = resolveVar(takeSentence);
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

   private static VarDecl resolveVar(TakeSentence takeSentence)
   {
      final Type type = takeSentence.getExample().accept(Typer.INSTANCE, null);
      final Name name = takeSentence.getVarName();
      if (name != null)
      {
         final Decl decl = name.accept(ExtractDecl.INSTANCE, null);
         if (decl != null)
         {
            return (VarDecl) decl;
         }

         final String nameValue = name.accept(Namer.INSTANCE, null);
         final VarDecl varDecl = VarDecl.of(nameValue, type, null);
         takeSentence.setVarName(ResolvedName.of(varDecl));
         return varDecl;
      }

      final String exampleName = takeSentence.getExample().accept(Namer.INSTANCE, null);
      if (exampleName != null)
      {
         final VarDecl varDecl = VarDecl.of(exampleName, type, null);
         takeSentence.setVarName(ResolvedName.of(varDecl));
         return varDecl;
      }

      throw new UnsupportedOperationException("cannot infer loop variable name");
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
