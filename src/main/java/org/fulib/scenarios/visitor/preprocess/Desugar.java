package org.fulib.scenarios.visitor.preprocess;

import org.fulib.StrUtil;
import org.fulib.scenarios.ast.*;
import org.fulib.scenarios.ast.decl.Name;
import org.fulib.scenarios.ast.decl.ResolvedName;
import org.fulib.scenarios.ast.decl.UnresolvedName;
import org.fulib.scenarios.ast.decl.VarDecl;
import org.fulib.scenarios.ast.expr.Expr;
import org.fulib.scenarios.ast.expr.call.CallExpr;
import org.fulib.scenarios.ast.expr.call.CreationExpr;
import org.fulib.scenarios.ast.expr.collection.ListExpr;
import org.fulib.scenarios.ast.expr.primary.NameAccess;
import org.fulib.scenarios.ast.sentence.*;
import org.fulib.scenarios.visitor.Namer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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

   @Override
   public Sentence visit(SectionSentence sectionSentence, Object par)
   {
      final String processedComment = sectionSentence.getLevel().format(sectionSentence.getText().trim());
      return TemplateSentence.of(processedComment, Collections.emptyList());
   }

   @Override
   public Sentence visit(ThereSentence thereSentence, Object par)
   {
      final List<Sentence> result = new ArrayList<>();
      for (MultiDescriptor multiDesc : thereSentence.getDescriptors())
      {
         this.visit(multiDesc, result);
      }
      return new FlattenSentenceList(result);
   }

   private void visit(MultiDescriptor multiDesc, List<Sentence> result)
   {
      final List<String> names = this.getNames(multiDesc);
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

   private List<String> getNames(MultiDescriptor multiDesc)
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
   public Sentence visit(CreateSentence createSentence, Object par)
   {
      final List<Sentence> result = new ArrayList<>();
      this.visit(createSentence.getDescriptor(), result);
      return new FlattenSentenceList(result);
   }

   @Override
   public Sentence visit(CallSentence callSentence, Object par)
   {
      final CallExpr call = callSentence.getCall();
      call.setBody((SentenceList) call.getBody().accept(this, par));

      final String name = call.accept(Namer.INSTANCE, null);
      if (name == null)
      {
         return ExprSentence.of(call);
      }

      return WriteSentence.of(callSentence.getActor(), call, NameAccess.of(UnresolvedName.of(name, null)));
   }

   @Override
   public Sentence visit(TakeSentence takeSentence, Object par)
   {
      takeSentence.setActions((SentenceList) takeSentence.getActions().accept(this, par));
      return takeSentence;
   }

   @Override
   public Sentence visit(ConditionalSentence conditionalSentence, Object par)
   {
      conditionalSentence.setActions((SentenceList) conditionalSentence.getActions().accept(this, par));
      return conditionalSentence;
   }
}
