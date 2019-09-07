package org.fulib.scenarios.visitor.preprocess;

import org.fulib.scenarios.ast.CompilationContext;
import org.fulib.scenarios.ast.Scenario;
import org.fulib.scenarios.ast.ScenarioFile;
import org.fulib.scenarios.ast.ScenarioGroup;
import org.fulib.scenarios.ast.expr.call.CallExpr;
import org.fulib.scenarios.ast.sentence.*;

import java.util.ArrayList;
import java.util.List;

public enum Desugar implements CompilationContext.Visitor<Object, Object>, ScenarioGroup.Visitor<Object, Object>,
                                  ScenarioFile.Visitor<Object, Object>, Scenario.Visitor<Object, Object>,
                                  Sentence.Visitor<Object, Sentence>
{
   INSTANCE;

   // --------------- CompilationContext.Visitor ---------------

   @Override
   public Object visit(CompilationContext compilationContext, Object par)
   {
      // no inter-group references, we can parallelize
      compilationContext.getGroups().values().parallelStream().forEach(it -> it.accept(this, null));
      return null;
   }

   // --------------- ScenarioGroup.Visitor ---------------

   @Override
   public Object visit(ScenarioGroup scenarioGroup, Object par)
   {
      for (final ScenarioFile file : scenarioGroup.getFiles().values())
      {
         file.accept(this, par);
      }
      return null;
   }

   // --------------- ScenarioFile.Visitor ---------------

   @Override
   public Object visit(ScenarioFile scenarioFile, Object par)
   {
      for (final Scenario scenario : scenarioFile.getScenarios().values())
      {
         scenario.accept(this, par);
      }
      return null;
   }

   // --------------- Scenario.Visitor ---------------

   @Override
   public Object visit(Scenario scenario, Object par)
   {
      scenario.setBody((SentenceList) scenario.getBody().accept(this, par));
      return null;
   }

   // --------------- Sentence.Visitor ---------------

   @Override
   public Sentence visit(Sentence sentence, Object par)
   {
      return sentence;
   }

   @Override
   public Sentence visit(SentenceList sentenceList, Object par)
   {
      final List<Sentence> oldItems = sentenceList.getItems();
      final List<Sentence> newItems = new ArrayList<>(oldItems.size());

      for (Sentence sentence : oldItems)
      {
         final Sentence result = sentence.accept(this, par);
         FlattenSentenceList.add(newItems, result);
      }

      sentenceList.setItems(newItems);
      return sentenceList;
   }

   // --------------- ActorSentence.Visitor ---------------

   @Override
   public Sentence visit(CallSentence callSentence, Object par)
   {
      final CallExpr call = callSentence.getCall();
      call.setBody((SentenceList) call.getBody().accept(this, par));
      return callSentence;
   }

   @Override
   public Sentence visit(TakeSentence takeSentence, Object par)
   {
      takeSentence.setBody(takeSentence.getBody().accept(this, par));
      return takeSentence;
   }

   // ---------------  ---------------

   @Override
   public Sentence visit(ConditionalSentence conditionalSentence, Object par)
   {
      conditionalSentence.setBody(conditionalSentence.getBody().accept(this, par));
      return conditionalSentence;
   }
}
