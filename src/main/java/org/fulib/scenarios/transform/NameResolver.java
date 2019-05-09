package org.fulib.scenarios.transform;

import org.fulib.scenarios.ast.NamedExpr;
import org.fulib.scenarios.ast.Scenario;
import org.fulib.scenarios.ast.decl.*;
import org.fulib.scenarios.ast.expr.Expr;
import org.fulib.scenarios.ast.expr.access.AttributeAccess;
import org.fulib.scenarios.ast.expr.access.ExampleAccess;
import org.fulib.scenarios.ast.expr.call.CreationExpr;
import org.fulib.scenarios.ast.expr.conditional.AttributeCheckExpr;
import org.fulib.scenarios.ast.expr.conditional.ConditionalExpr;
import org.fulib.scenarios.ast.expr.primary.NameAccess;
import org.fulib.scenarios.ast.expr.primary.NumberLiteral;
import org.fulib.scenarios.ast.expr.primary.PrimaryExpr;
import org.fulib.scenarios.ast.expr.primary.StringLiteral;
import org.fulib.scenarios.ast.sentence.*;

import java.util.Map;

public class NameResolver
   implements Scenario.Visitor<Object, Object>, Sentence.Visitor<Object, Object>, Expr.Visitor<Object, Expr>,
                 Name.Visitor<Object, Name>
{
   private final Map<String, Decl> symbolTable;

   private VarDecl currentVar;

   public NameResolver(Map<String, Decl> symbolTable)
   {
      this.symbolTable = symbolTable;
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
         this.currentVar = var;
         var.setExpr(var.getExpr().accept(this, par));
      }
      this.currentVar = null;
      return null;
   }

   @Override
   public Object visit(ExpectSentence expectSentence, Object par)
   {
      expectSentence.getPredicates().replaceAll(it -> (ConditionalExpr) it.accept(this, par));
      return null;
   }

   @Override
   public Object visit(DiagramSentence diagramSentence, Object par)
   {
      diagramSentence.setObject(diagramSentence.getObject().accept(this, par));
      return null;
   }

   @Override
   public Object visit(HasSentence hasSentence, Object par)
   {
      hasSentence.setObject(hasSentence.getObject().accept(this, par));

      for (final NamedExpr namedExpr : hasSentence.getClauses())
      {
         namedExpr.setExpr(namedExpr.getExpr().accept(this, par));
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
      if (nameAccess.getName() instanceof UnresolvedName)
      {
         final UnresolvedName unresolvedName = (UnresolvedName) nameAccess.getName();
         final Decl target = this.symbolTable.get(unresolvedName.getValue());
         if (target == null || target == this.currentVar)
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

   @Override
   public Expr visit(ConditionalExpr conditionalExpr, Object par)
   {
      return conditionalExpr;
   }

   @Override
   public Expr visit(AttributeCheckExpr attributeCheckExpr, Object par)
   {
      attributeCheckExpr.setReceiver(attributeCheckExpr.getReceiver().accept(this, par));
      attributeCheckExpr.setValue(attributeCheckExpr.getValue().accept(this, par));
      return attributeCheckExpr;
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
