package org.fulib.scenarios.visitor.preprocess;

import org.fulib.scenarios.ast.CompilationContext;
import org.fulib.scenarios.ast.Scenario;
import org.fulib.scenarios.ast.ScenarioFile;
import org.fulib.scenarios.ast.ScenarioGroup;
import org.fulib.scenarios.ast.decl.Name;
import org.fulib.scenarios.ast.expr.call.CallExpr;
import org.fulib.scenarios.ast.sentence.*;
import org.fulib.scenarios.diagnostic.Marker;
import org.fulib.scenarios.diagnostic.Position;
import org.fulib.scenarios.visitor.Namer;

import java.util.ArrayList;
import java.util.List;

import static org.fulib.scenarios.diagnostic.Marker.error;

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
      final TopFrame top = new TopFrame();
      top.file = scenario.getFile();
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
      return par.add(null, ACTOR_TEST, thereSentence);
   }

   @Override
   public Frame visit(ExpectSentence expectSentence, Frame par)
   {
      return par.add(null, ACTOR_TEST, expectSentence);
   }

   @Override
   public Frame visit(DiagramSentence diagramSentence, Frame par)
   {
      return par.add(null, ACTOR_TEST, diagramSentence);
   }

   // --------------- ActorSentence.Visitor ---------------

   @Override
   public Frame visit(ActorSentence actorSentence, Frame par)
   {
      final Name actor = actorSentence.getActor();
      return par.add(actor == null ? actorSentence.getPosition() : actor.getPosition(),
                     actorKey(actorSentence.getActor()), actorSentence);
   }

   @Override
   public Frame visit(CallSentence callSentence, Frame par)
   {
      final CallExpr callExpr = callSentence.getCall();
      final List<Sentence> sentences = callExpr.getBody().getItems();
      return this.visit((ActorSentence) callSentence, par).push(actorKey(callExpr.getName()), sentences);
   }

   @Override
   public Frame visit(AnswerSentence answerSentence, Frame par)
   {
      return this.visit((ActorSentence) answerSentence, par)
                 .pop(answerSentence.getPosition(), actorKey(answerSentence.getActor()));
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

   Frame add(Position position, String key, Sentence sentence)
   {
      return this.popIncompatible(position, key).add(sentence);
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

   Frame pop(Position position, String key)
   {
      return this.popToDefinition(position, key).pop();
   }

   private Frame popIncompatible(Position position, String key)
   {
      Frame result = this;
      while (!keyMatches(key, result.key))
      {
         if (result.next == null)
         {
            this.reportIncompatible(position, key);
            return this;
         }
         result = result.next;
      }
      return result;
   }

   private Frame popToDefinition(Position position, String key)
   {
      Frame result = this;
      while (!result.key.contains(key))
      {
         if (result.next == null)
         {
            this.reportIncompatible(position, key);
            return this;
         }
         result = result.next;
      }
      return result;
   }

   private void reportIncompatible(Position position, String key)
   {
      final int beginIndex = key.indexOf(':');
      final int endIndex = key.indexOf(',', beginIndex + 1);
      final String type = key.substring(0, beginIndex);
      final String value = key.substring(beginIndex + 1, endIndex);
      this.report(error(position, "frame.incompatible." + type, value));
   }

   void report(Marker marker)
   {
      this.next.report(marker);
   }
}

class TopFrame extends Frame
{
   ScenarioFile file;

   @Override
   void report(Marker marker)
   {
      this.file.getMarkers().add(marker);
   }
}
