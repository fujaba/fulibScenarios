package org.fulib.scenarios.transform;

import org.fulib.scenarios.ast.Scenario;
import org.fulib.scenarios.ast.decl.Decl;
import org.fulib.scenarios.ast.decl.VarDecl;
import org.fulib.scenarios.ast.sentence.*;

import java.util.Map;

public class SymbolCollector
   implements Scenario.Visitor<Object, Object>, Sentence.Visitor<Object, Object>, Decl.Visitor<Object, Object>
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

   // --------------- Decl.Visitor ---------------

   @Override
   public Object visit(Decl decl, Object par)
   {
      throw new UnsupportedOperationException();
   }

   @Override
   public Object visit(VarDecl varDecl, Object par)
   {
      if (varDecl.getName() == null)
      {
         varDecl.setName(varDecl.getExpr().accept(Namer.INSTANCE, null));
      }
      this.symbolTable.put(varDecl.getName(), varDecl);
      return null;
   }

   // --------------- Sentence.Visitor ---------------

   @Override
   public Object visit(Sentence sentence, Object par)
   {
      return null;
   }

   @Override
   public Object visit(SentenceList sentenceList, Object par)
   {
      for (final Sentence item : sentenceList.getItems())
      {
         item.accept(this, par);
      }
      return null;
   }

   @Override
   public Object visit(ThereSentence thereSentence, Object par)
   {
      throw new UnsupportedOperationException();
   }

   @Override
   public Object visit(ExpectSentence expectSentence, Object par)
   {
      return null;
   }

   @Override
   public Object visit(DiagramSentence diagramSentence, Object par)
   {
      return null;
   }

   @Override
   public Object visit(HasSentence hasSentence, Object par)
   {
      return null;
   }

   @Override
   public Object visit(IsSentence isSentence, Object par)
   {
      isSentence.getDescriptor().accept(this, par);
      return null;
   }

   @Override
   public Object visit(CreateSentence createSentence, Object par)
   {
      throw new UnsupportedOperationException();
   }

   @Override
   public Object visit(CallSentence callSentence, Object par)
   {
      for (final Sentence sentence : callSentence.getBody())
      {
         sentence.accept(this, par);
      }
      return null;
   }
}
