package org.fulib.scenarios.visitor.resolve;

import org.fulib.scenarios.ast.decl.Decl;
import org.fulib.scenarios.ast.decl.VarDecl;
import org.fulib.scenarios.ast.expr.Expr;
import org.fulib.scenarios.ast.expr.call.CallExpr;
import org.fulib.scenarios.ast.sentence.*;

import java.util.Map;

public enum SymbolCollector implements Sentence.Visitor<Map<String, Decl>, Object>
{
   INSTANCE;

   // --------------- Sentence.Visitor ---------------

   @Override
   public Object visit(Sentence sentence, Map<String, Decl> par)
   {
      return null;
   }

   @Override
   public Object visit(SentenceList sentenceList, Map<String, Decl> par)
   {
      if (!(sentenceList instanceof FlattenSentenceList))
      {
         // the sentence list encapsulates declarations, so does not expose them here.
         return null;
      }

      for (final Sentence sentence : sentenceList.getItems())
      {
         sentence.accept(this, par);
      }

      return null;
   }

   @Override
   public Object visit(IsSentence isSentence, Map<String, Decl> par)
   {
      final VarDecl varDecl = isSentence.getDescriptor();
      String name = varDecl.getName();

      this.addAnswerVar(par, varDecl, isSentence.getDescriptor().getExpr());

      par.put(name, varDecl);
      return null;
   }

   @Override
   public Object visit(AssignSentence assignSentence, Map<String, Decl> par)
   {
      this.addAnswerVar(par, assignSentence.getTarget(), assignSentence.getValue());
      return null;
   }

   @Override
   public Object visit(ExprSentence exprSentence, Map<String, Decl> par)
   {
      this.addAnswerVar(par, null, exprSentence.getExpr());
      return null;
   }

   private void addAnswerVar(Map<String, Decl> par, Decl varDecl, Expr expr)
   {
      if (!(expr instanceof CallExpr))
      {
         return;
      }

      if (varDecl != null)
      {
         par.put(NameResolver.ANSWER_VAR, varDecl);
      }
      else
      {
         par.remove(NameResolver.ANSWER_VAR);
      }
   }
}
