package org.fulib.scenarios.transform;

import org.fulib.scenarios.ast.NamedExpr;
import org.fulib.scenarios.ast.Scenario;
import org.fulib.scenarios.ast.ScenarioGroup;
import org.fulib.scenarios.ast.decl.*;
import org.fulib.scenarios.ast.expr.Expr;
import org.fulib.scenarios.ast.expr.access.AttributeAccess;
import org.fulib.scenarios.ast.expr.access.ExampleAccess;
import org.fulib.scenarios.ast.expr.call.CreationExpr;
import org.fulib.scenarios.ast.expr.primary.NameAccess;
import org.fulib.scenarios.ast.expr.primary.NumberLiteral;
import org.fulib.scenarios.ast.expr.primary.PrimaryExpr;
import org.fulib.scenarios.ast.expr.primary.StringLiteral;
import org.fulib.scenarios.ast.sentence.Sentence;
import org.fulib.scenarios.ast.sentence.ThereSentence;

import java.util.Map;

public class NameResolver implements ScenarioGroup.Visitor<Object, Object>, Scenario.Visitor<Object, Object>,
                                        Sentence.Visitor<Object, Object>, Expr.Visitor<Object, Expr>,
                                        Name.Visitor<Object, Name>
{
   private final Map<String, Decl> symbolTable;

   public NameResolver(Map<String, Decl> symbolTable)
   {
      this.symbolTable = symbolTable;
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
      for (final Sentence sentence : scenario.getSentences())
      {
         sentence.accept(this, par);
      }
      return null;
   }

   // --------------- Sentence.Visitor ---------------

   @Override
   public Object visit(Sentence sentence, Object par)
   {
      return null;
   }

   @Override
   public Object visit(ThereSentence thereSentence, Object par)
   {
      for (final VarDecl var : thereSentence.getVars())
      {
         var.setExpr(var.getExpr().accept(this, par));
      }
      return null;
   }

   // --------------- Expr.Visitor ---------------

   @Override
   public Expr visit(Expr expr, Object par)
   {
      return expr;
   }

   @Override
   public Expr visit(AttributeAccess attributeAccess, Object par)
   {
      attributeAccess.setReceiver(attributeAccess.getReceiver().accept(this, par));
      return attributeAccess;
   }

   @Override
   public Expr visit(ExampleAccess exampleAccess, Object par)
   {
      exampleAccess.setExpr(exampleAccess.getExpr().accept(this, par));
      return exampleAccess;
   }

   @Override
   public Expr visit(PrimaryExpr primaryExpr, Object par)
   {
      return primaryExpr;
   }

   @Override
   public Expr visit(NameAccess nameAccess, Object par)
   {
      final Name resolved = nameAccess.getName().accept(this, par);
      if (resolved instanceof UnresolvedName)
      {
         return StringLiteral.of(((UnresolvedName) resolved).getText());
      }
      nameAccess.setName(resolved);
      return nameAccess;
   }

   @Override
   public Expr visit(NumberLiteral numberLiteral, Object par)
   {
      return numberLiteral;
   }

   @Override
   public Expr visit(StringLiteral stringLiteral, Object par)
   {
      return stringLiteral;
   }

   @Override
   public Expr visit(CreationExpr creationExpr, Object par)
   {
      creationExpr.setClassName(creationExpr.getClassName().accept(this, par));
      for (final NamedExpr namedExpr : creationExpr.getAttributes())
      {
         namedExpr.setExpr(namedExpr.getExpr().accept(this, par));
      }
      return creationExpr;
   }

   // --------------- Name.Visitor ---------------

   @Override
   public Name visit(Name name, Object par)
   {
      return name;
   }

   @Override
   public Name visit(ResolvedName resolvedName, Object par)
   {
      return resolvedName;
   }

   @Override
   public Name visit(UnresolvedName unresolvedName, Object par)
   {
      final Decl decl = this.symbolTable.get(unresolvedName.getValue());
      return decl == null ? unresolvedName : ResolvedName.of(decl);
   }
}
