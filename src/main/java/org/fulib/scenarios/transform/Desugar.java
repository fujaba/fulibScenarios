package org.fulib.scenarios.transform;

import org.fulib.scenarios.ast.MultiDescriptor;
import org.fulib.scenarios.ast.NamedExpr;
import org.fulib.scenarios.ast.Scenario;
import org.fulib.scenarios.ast.ScenarioGroup;
import org.fulib.scenarios.ast.decl.Name;
import org.fulib.scenarios.ast.decl.ResolvedName;
import org.fulib.scenarios.ast.decl.VarDecl;
import org.fulib.scenarios.ast.expr.Expr;
import org.fulib.scenarios.ast.expr.call.CreationExpr;
import org.fulib.scenarios.ast.expr.collection.ListExpr;
import org.fulib.scenarios.ast.expr.primary.NameAccess;
import org.fulib.scenarios.ast.sentence.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Desugar implements ScenarioGroup.Visitor<Object, Object>, Scenario.Visitor<Object, Object>,
                                   Sentence.Visitor<Object, Collection<? extends Sentence>>
{
   private int tempID;

   private String tempName()
   {
      return "_t" + this.tempID++;
   }

   // --------------- ScenarioGroup.Visitor ---------------

   @Override
   public Object visit(ScenarioGroup scenarioGroup, Object par)
   {
      for (final Scenario scenario : scenarioGroup.getScenarios())
      {
         scenario.accept(this, par);
      }
      return null;
   }

   // --------------- Scenario.Visitor ---------------

   @Override
   public Object visit(Scenario scenario, Object par)
   {
      final List<Sentence> newSentences = scenario.getSentences().stream()
                                                  .flatMap(sentence -> sentence.accept(this, par).stream())
                                                  .collect(Collectors.toList());
      scenario.setSentences(newSentences);
      return null;
   }

   // --------------- Sentence.Visitor ---------------

   @Override
   public Collection<? extends Sentence> visit(Sentence sentence, Object par)
   {
      throw new UnsupportedOperationException();
   }

   @Override
   public Collection<? extends Sentence> visit(ThereSentence thereSentence, Object par)
   {
      final List<Sentence> result = new ArrayList<>();
      for (MultiDescriptor multiDesc : thereSentence.getDescriptors())
      {
         this.visit(multiDesc, result);
      }
      return result;
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

            final VarDecl temp = VarDecl.of(this.tempName(), null, attributeExpr);
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
      return Collections.singletonList(className);
   }

   @Override
   public Collection<? extends Sentence> visit(DiagramSentence diagramSentence, Object par)
   {
      return Collections.singletonList(diagramSentence);
   }

   @Override
   public Collection<? extends Sentence> visit(ExpectSentence expectSentence, Object par)
   {
      return Collections.singletonList(expectSentence);
   }

   @Override
   public Collection<? extends Sentence> visit(HasSentence hasSentence, Object par)
   {
      return Collections.singletonList(hasSentence);
   }

   @Override
   public Collection<? extends Sentence> visit(IsSentence isSentence, Object par)
   {
      return Collections.singletonList(isSentence);
   }

   @Override
   public Collection<? extends Sentence> visit(CreateSentence createSentence, Object par)
   {
      final List<Sentence> result = new ArrayList<>();
      this.visit(createSentence.getDescriptor(), result);
      return result;
   }

   @Override
   public Collection<? extends Sentence> visit(CallSentence callSentence, Object par)
   {
      return Collections.singletonList(callSentence);
   }
}
