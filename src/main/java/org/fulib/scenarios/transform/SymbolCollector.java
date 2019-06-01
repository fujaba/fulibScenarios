package org.fulib.scenarios.transform;

import org.fulib.scenarios.ast.decl.Decl;
import org.fulib.scenarios.ast.decl.VarDecl;
import org.fulib.scenarios.ast.sentence.*;

import java.util.Map;

public enum SymbolCollector implements Sentence.Visitor<Map<String, Decl>, Object>
{
   INSTANCE;

   // --------------- Sentence.Visitor ---------------

   @Override
   public Object visit(Sentence sentence, Map<String, Decl> par)
   {
      return null;
   }

   @Override
   public Object visit(SentenceList sentenceList, Map<String, Decl> par)
   {
      // the sentence list encapsulates declarations, so does not expose them here.
      return null;
   }

   @Override
   public Object visit(ThereSentence thereSentence, Map<String, Decl> par)
   {
      throw new UnsupportedOperationException();
   }

   @Override
   public Object visit(ExpectSentence expectSentence, Map<String, Decl> par)
   {
      return null;
   }

   @Override
   public Object visit(DiagramSentence diagramSentence, Map<String, Decl> par)
   {
      return null;
   }

   @Override
   public Object visit(HasSentence hasSentence, Map<String, Decl> par)
   {
      return null;
   }

   @Override
   public Object visit(IsSentence isSentence, Map<String, Decl> par)
   {
      final VarDecl varDecl = isSentence.getDescriptor();
      if (varDecl.getName() == null)
      {
         varDecl.setName(varDecl.getExpr().accept(Namer.INSTANCE, null));
      }
      par.put(varDecl.getName(), varDecl);
      return null;
   }

   @Override
   public Object visit(CreateSentence createSentence, Map<String, Decl> par)
   {
      throw new UnsupportedOperationException();
   }

   @Override
   public Object visit(CallSentence callSentence, Map<String, Decl> par)
   {
      throw new UnsupportedOperationException();
   }

   @Override
   public Object visit(AnswerSentence answerSentence, Map<String, Decl> par)
   {
      return null;
   }

   @Override
   public Object visit(ExprSentence exprSentence, Map<String, Decl> par)
   {
      return null;
   }
}
