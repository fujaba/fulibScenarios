package org.fulib.scenarios.visitor;

import org.fulib.scenarios.ast.sentence.*;

import java.util.List;

public enum GetAnswerSentence implements Sentence.Visitor<Object, AnswerSentence>
{
   INSTANCE;

   @Override
   public AnswerSentence visit(Sentence sentence, Object par)
   {
      return null;
   }

   @Override
   public AnswerSentence visit(SentenceList sentenceList, Object par)
   {
      final List<Sentence> items = sentenceList.getItems();
      return items.isEmpty() ? null : items.get(items.size() - 1).accept(this, par);
   }

   @Override
   public AnswerSentence visit(AnswerSentence answerSentence, Object par)
   {
      return answerSentence;
   }

   @Override
   public AnswerSentence visit(ConditionalSentence conditionalSentence, Object par)
   {
      return conditionalSentence.getBody().accept(this, par);
   }

   @Override
   public AnswerSentence visit(TakeSentence takeSentence, Object par)
   {
      return takeSentence.getBody().accept(this, par);
   }
}
