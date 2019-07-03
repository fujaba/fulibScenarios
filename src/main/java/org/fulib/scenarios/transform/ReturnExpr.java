package org.fulib.scenarios.transform;

import org.fulib.scenarios.ast.expr.Expr;
import org.fulib.scenarios.ast.sentence.AnswerSentence;
import org.fulib.scenarios.ast.sentence.ConditionalSentence;
import org.fulib.scenarios.ast.sentence.Sentence;
import org.fulib.scenarios.ast.sentence.SentenceList;

import java.util.List;

public enum ReturnExpr implements Sentence.Visitor<Object, Expr>
{
   INSTANCE;

   @Override
   public Expr visit(Sentence sentence, Object par)
   {
      return null;
   }

   @Override
   public Expr visit(SentenceList sentenceList, Object par)
   {
      final List<Sentence> items = sentenceList.getItems();
      return items.isEmpty() ? null : items.get(items.size() - 1).accept(this, par);
   }

   @Override
   public Expr visit(AnswerSentence answerSentence, Object par)
   {
      return answerSentence.getResult();
   }

   @Override
   public Expr visit(ConditionalSentence conditionalSentence, Object par)
   {
      return conditionalSentence.getActions().accept(this, par);
   }
}
