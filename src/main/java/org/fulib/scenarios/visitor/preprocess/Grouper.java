package org.fulib.scenarios.visitor.preprocess;

import org.fulib.scenarios.ast.CompilationContext;
import org.fulib.scenarios.ast.Scenario;
import org.fulib.scenarios.ast.ScenarioFile;
import org.fulib.scenarios.ast.ScenarioGroup;
import org.fulib.scenarios.ast.decl.Name;
import org.fulib.scenarios.ast.expr.call.CallExpr;
import org.fulib.scenarios.ast.sentence.*;
import org.fulib.scenarios.visitor.Namer;

import java.util.ArrayList;
import java.util.List;

public enum Grouper implements CompilationContext.Visitor<Object, Object>, ScenarioGroup.Visitor<Object, Object>,
                                  ScenarioFile.Visitor<Object, Object>, Scenario.Visitor<Object, Object>,
                                  Sentence.Visitor<Frame, Frame>
{
   INSTANCE;

   // =============== Constants ===============

   private static String ACTOR_TEST = "actor:<test>,";

   // =============== Static Methods ===============

   private static String actorKey(Name name)
   {
      return name == null ? ACTOR_TEST : "actor:" + name.accept(Namer.INSTANCE, null) + ",";
   }

   // =============== Methods ===============

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
      Frame top = new Frame();
      top.key = ACTOR_TEST;
      top.target = new ArrayList<>();

      /*final Frame result = */
      scenario.getBody().accept(this, top);

      scenario.getBody().setItems(top.target);
      return null;
   }

   // --------------- Sentence.Visitor ---------------

   @Override
   public Frame visit(Sentence sentence, Frame par)
   {
      return par.add(sentence);
   }

   @Override
   public Frame visit(SentenceList sentenceList, Frame par)
   {
      for (final Sentence item : sentenceList.getItems())
      {
         par = item.accept(this, par);
      }
      return par;
   }

   @Override
   public Frame visit(SectionSentence sectionSentence, Frame par)
   {
      return par.add(sectionSentence);
   }

   @Override
   public Frame visit(ThereSentence thereSentence, Frame par)
   {
      return par.add(ACTOR_TEST, thereSentence);
   }

   @Override
   public Frame visit(ExpectSentence expectSentence, Frame par)
   {
      return par.add(ACTOR_TEST, expectSentence);
   }

   @Override
   public Frame visit(DiagramSentence diagramSentence, Frame par)
   {
      return par.add(ACTOR_TEST, diagramSentence);
   }

   @Override
   public Frame visit(CreateSentence createSentence, Frame par)
   {
      return par.add(actorKey(createSentence.getActor()), createSentence);
   }

   @Override
   public Frame visit(CallSentence callSentence, Frame par)
   {
      final CallExpr callExpr = callSentence.getCall();
      final List<Sentence> sentences = callExpr.getBody().getItems();
      return par.add(actorKey(callSentence.getActor()), callSentence).push(actorKey(callExpr.getName()), sentences);
   }

   @Override
   public Frame visit(AnswerSentence answerSentence, Frame par)
   {
      return par.addLast(actorKey(answerSentence.getActor()), answerSentence);
   }

   @Override
   public Frame visit(WriteSentence writeSentence, Frame par)
   {
      return par.add(actorKey(writeSentence.getActor()), writeSentence);
   }

   @Override
   public Frame visit(AddSentence addSentence, Frame par)
   {
      return par.add(actorKey(addSentence.getActor()), addSentence);
   }

   @Override
   public Frame visit(RemoveSentence removeSentence, Frame par)
   {
      return par.add(actorKey(removeSentence.getActor()), removeSentence);
   }
}

class Frame
{
   String         key;
   List<Sentence> target;
   Frame          next;

   boolean keyMatches(String keys)
   {
      return keyMatches(this.key, keys);
   }

   static boolean keyMatches(String frameKeys, String keys)
   {
      int start = 0;
      int end;
      while ((end = keys.indexOf(',', start)) >= 0)
      {
         final int colon = keys.indexOf(':', start);
         assert colon < end;

         final String key = keys.substring(start, colon + 1); // including trailing :
         // final String keyValue = keys.substring(start, end + 1); // including trailing ,

         final int keyPos = frameKeys.indexOf(key);
         if (keyPos >= 0 && !frameKeys.regionMatches(keyPos, keys, start, end - start + 1)) // + 1 for trailing ,
         {
            // this frame specifies the key, but with a different value
            return false;
         }

         start = end + 1;
      }
      return true;
   }

   Frame add(Sentence sentence)
   {
      this.target.add(sentence);
      return this;
   }

   Frame add(String key, Sentence sentence)
   {
      return this.popIncompatible(key).add(sentence);
   }

   Frame addLast(String key, Sentence sentence)
   {
      return this.add(key, sentence).popToDefinition(key).pop();
   }

   Frame push(String key, List<Sentence> target)
   {
      final Frame newTop = new Frame();
      newTop.key = key;
      newTop.target = target;
      newTop.next = this;
      return newTop;
   }

   Frame pop()
   {
      return this.next;
   }

   Frame popIncompatible(String key)
   {
      Frame result = this;
      while (!keyMatches(key, result.key))
      {
         result = result.next;
      }
      return result;
   }

   Frame popToDefinition(String key)
   {
      Frame result = this;
      while (!result.key.contains(key))
      {
         result = result.next;
      }
      return result;
   }
}
