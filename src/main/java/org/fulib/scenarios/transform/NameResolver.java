package org.fulib.scenarios.transform;

import org.fulib.scenarios.ast.NamedExpr;
import org.fulib.scenarios.ast.Scenario;
import org.fulib.scenarios.ast.ScenarioFile;
import org.fulib.scenarios.ast.ScenarioGroup;
import org.fulib.scenarios.ast.decl.*;
import org.fulib.scenarios.ast.expr.Expr;
import org.fulib.scenarios.ast.expr.access.AttributeAccess;
import org.fulib.scenarios.ast.expr.access.ExampleAccess;
import org.fulib.scenarios.ast.expr.call.CallExpr;
import org.fulib.scenarios.ast.expr.call.CreationExpr;
import org.fulib.scenarios.ast.expr.collection.CollectionExpr;
import org.fulib.scenarios.ast.expr.collection.ListExpr;
import org.fulib.scenarios.ast.expr.conditional.AttributeCheckExpr;
import org.fulib.scenarios.ast.expr.conditional.ConditionalExpr;
import org.fulib.scenarios.ast.expr.primary.NameAccess;
import org.fulib.scenarios.ast.expr.primary.NumberLiteral;
import org.fulib.scenarios.ast.expr.primary.PrimaryExpr;
import org.fulib.scenarios.ast.expr.primary.StringLiteral;
import org.fulib.scenarios.ast.sentence.*;

import java.util.HashMap;
import java.util.Map;

public enum NameResolver implements ScenarioGroup.Visitor<Object, Object>, ScenarioFile.Visitor<Object, Object>,
                                       Scenario.Visitor<Object, Object>, Sentence.Visitor<Scope, Object>,
                                       Expr.Visitor<Scope, Expr>, Name.Visitor<Scope, Name>
{
   INSTANCE;

   // --------------- ScenarioGroup.Visitor ---------------

   @Override
   public Object visit(ScenarioGroup scenarioGroup, Object par)
   {
      for (final ScenarioFile file : scenarioGroup.getFiles())
      {
         file.accept(this, par);
      }
      return null;
   }

   // --------------- ScenarioFile.Visitor ---------------

   @Override
   public Object visit(ScenarioFile scenarioFile, Object par)
   {
      for (final Scenario scenario : scenarioFile.getScenarios())
      {
         scenario.accept(this, par);
      }
      return null;
   }

   // --------------- Scenario.Visitor ---------------

   @Override
   public Object visit(Scenario scenario, Object par)
   {
      scenario.getBody().accept(this, new EmptyScope());
      return null;
   }

   // --------------- Sentence.Visitor ---------------

   @Override
   public Object visit(Sentence sentence, Scope par)
   {
      return null;
   }

   @Override
   public Object visit(SentenceList sentenceList, Scope par)
   {
      BasicScope scope = new BasicScope(par);

      for (final Sentence item : sentenceList.getItems())
      {
         item.accept(SymbolCollector.INSTANCE, scope.decls);
         item.accept(this, scope);
      }
      return null;
   }

   @Override
   public Object visit(ThereSentence thereSentence, Scope par)
   {
      throw new UnsupportedOperationException();
   }

   @Override
   public Object visit(ExpectSentence expectSentence, Scope par)
   {
      expectSentence.getPredicates().replaceAll(it -> (ConditionalExpr) it.accept(this, par));
      return null;
   }

   @Override
   public Object visit(DiagramSentence diagramSentence, Scope par)
   {
      diagramSentence.setObject(diagramSentence.getObject().accept(this, par));
      return null;
   }

   @Override
   public Object visit(HasSentence hasSentence, Scope par)
   {
      hasSentence.setObject(hasSentence.getObject().accept(this, par));

      final String name = hasSentence.getObject().accept(Namer.INSTANCE, null);
      final Scope scope = name != null ? new HidingScope(name, par) : par;

      for (final NamedExpr namedExpr : hasSentence.getClauses())
      {
         namedExpr.setExpr(namedExpr.getExpr().accept(this, scope));
      }

      return null;
   }

   @Override
   public Object visit(IsSentence isSentence, Scope par)
   {
      final VarDecl varDecl = isSentence.getDescriptor();
      varDecl.setExpr(varDecl.getExpr().accept(this, par));
      return null;
   }

   @Override
   public Object visit(CreateSentence createSentence, Scope par)
   {
      throw new UnsupportedOperationException();
   }

   @Override
   public Object visit(CallSentence callSentence, Scope par)
   {
      throw new UnsupportedOperationException();
   }

   @Override
   public Object visit(AnswerSentence answerSentence, Scope par)
   {
      if (answerSentence.getActor() != null)
      {
         answerSentence.setActor(answerSentence.getActor().accept(this, par));
      }

      answerSentence.setResult(answerSentence.getResult().accept(this, par));
      return null;
   }

   @Override
   public Object visit(ExprSentence exprSentence, Scope par)
   {
      exprSentence.setExpr(exprSentence.getExpr().accept(this, par));
      return null;
   }

   // --------------- Expr.Visitor ---------------

   @Override
   public Expr visit(Expr expr, Scope par)
   {
      return expr;
   }

   @Override
   public Expr visit(AttributeAccess attributeAccess, Scope par)
   {
      attributeAccess.setReceiver(attributeAccess.getReceiver().accept(this, par));
      return attributeAccess;
   }

   @Override
   public Expr visit(ExampleAccess exampleAccess, Scope par)
   {
      exampleAccess.setExpr(exampleAccess.getExpr().accept(this, par));
      return exampleAccess;
   }

   @Override
   public Expr visit(PrimaryExpr primaryExpr, Scope par)
   {
      return primaryExpr;
   }

   @Override
   public Expr visit(NameAccess nameAccess, Scope par)
   {
      if (nameAccess.getName() instanceof UnresolvedName)
      {
         final UnresolvedName unresolvedName = (UnresolvedName) nameAccess.getName();
         final String unresolvedValue = unresolvedName.getValue();

         final Decl target = par.resolve(unresolvedValue);
         if (target == null)
         {
            return StringLiteral.of(unresolvedName.getText());
         }
         else
         {
            nameAccess.setName(ResolvedName.of(target));
         }
      }

      return nameAccess;
   }

   @Override
   public Expr visit(NumberLiteral numberLiteral, Scope par)
   {
      return numberLiteral;
   }

   @Override
   public Expr visit(StringLiteral stringLiteral, Scope par)
   {
      return stringLiteral;
   }

   @Override
   public Expr visit(CreationExpr creationExpr, Scope par)
   {
      creationExpr.setClassName(creationExpr.getClassName().accept(this, par));
      for (final NamedExpr namedExpr : creationExpr.getAttributes())
      {
         namedExpr.setExpr(namedExpr.getExpr().accept(this, par));
      }
      return creationExpr;
   }

   @Override
   public Expr visit(CallExpr callExpr, Scope par)
   {
      final Expr receiver = callExpr.getReceiver();
      if (receiver != null)
      {
         callExpr.setReceiver(receiver.accept(this, par));
      }
      callExpr.getBody().accept(this, par);
      return callExpr;
   }

   @Override
   public Expr visit(ConditionalExpr conditionalExpr, Scope par)
   {
      return conditionalExpr;
   }

   @Override
   public Expr visit(AttributeCheckExpr attributeCheckExpr, Scope par)
   {
      attributeCheckExpr.setReceiver(attributeCheckExpr.getReceiver().accept(this, par));
      attributeCheckExpr.setValue(attributeCheckExpr.getValue().accept(this, par));
      return attributeCheckExpr;
   }

   @Override
   public Expr visit(CollectionExpr collectionExpr, Scope par)
   {
      throw new UnsupportedOperationException();
   }

   @Override
   public Expr visit(ListExpr listExpr, Scope par)
   {
      listExpr.getElements().replaceAll(it -> it.accept(this, par));
      return listExpr;
   }

   // --------------- Name.Visitor ---------------

   @Override
   public Name visit(Name name, Scope par)
   {
      return name;
   }

   @Override
   public Name visit(ResolvedName resolvedName, Scope par)
   {
      return resolvedName;
   }

   @Override
   public Name visit(UnresolvedName unresolvedName, Scope par)
   {
      final Decl decl = par.resolve(unresolvedName.getValue());
      return decl == null ? unresolvedName : ResolvedName.of(decl);
   }
}

interface Scope
{
   Decl resolve(String name);
}

class EmptyScope implements Scope
{
   @Override
   public Decl resolve(String name)
   {
      return null;
   }
}

class BasicScope implements Scope
{
   final Map<String, Decl> decls;

   final Scope outer;

   public BasicScope(Scope outer)
   {
      this.outer = outer;
      this.decls = new HashMap<>();
   }

   @Override
   public Decl resolve(String name)
   {
      Decl inner = this.decls.get(name);
      return inner != null ? inner : this.outer.resolve(name);
   }
}

class HidingScope implements Scope
{
   final String name;
   final Scope  outer;

   HidingScope(String name, Scope outer)
   {
      this.name = name;
      this.outer = outer;
   }

   @Override
   public Decl resolve(String name)
   {
      return this.name.equals(name) ? null : this.outer.resolve(name);
   }
}
