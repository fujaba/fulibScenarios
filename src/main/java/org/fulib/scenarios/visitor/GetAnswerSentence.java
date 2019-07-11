package org.fulib.scenarios.visitor;

import org.fulib.scenarios.ast.sentence.AnswerSentence;
import org.fulib.scenarios.ast.sentence.ConditionalSentence;
import org.fulib.scenarios.ast.sentence.Sentence;
import org.fulib.scenarios.ast.sentence.SentenceList;

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
      return conditionalSentence.getActions().accept(this, par);
   }
}
