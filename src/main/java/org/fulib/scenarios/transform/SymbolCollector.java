package org.fulib.scenarios.transform;

import org.fulib.scenarios.ast.Scenario;
import org.fulib.scenarios.ast.decl.Decl;
import org.fulib.scenarios.ast.decl.VarDecl;
import org.fulib.scenarios.ast.sentence.ExpectSentence;
import org.fulib.scenarios.ast.sentence.Sentence;
import org.fulib.scenarios.ast.sentence.ThereSentence;

import java.util.Map;

public class SymbolCollector implements Scenario.Visitor<Object, Object>, Sentence.Visitor<Object, Object>
{
   private final Map<String, Decl> symbolTable;

   public SymbolCollector(Map<String, Decl> symbolTable)
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
      for (VarDecl var : thereSentence.getVars())
      {
         this.completeVarName(var);
         this.symbolTable.put(var.getName(), var);
      }
      return null;
   }

   private void completeVarName(VarDecl var)
   {
      if (var.getName() != null)
      {
         return;
      }

      var.setName(var.getExpr().accept(Namer.INSTANCE, null));
   }

   @Override
   public Object visit(ExpectSentence expectSentence, Object par)
   {
      return null;
   }
}
