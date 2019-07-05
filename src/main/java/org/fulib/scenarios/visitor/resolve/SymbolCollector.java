package org.fulib.scenarios.visitor.resolve;

import org.fulib.scenarios.ast.decl.Decl;
import org.fulib.scenarios.ast.decl.VarDecl;
import org.fulib.scenarios.ast.sentence.*;
import org.fulib.scenarios.visitor.Namer;

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
   public Object visit(IsSentence isSentence, Map<String, Decl> par)
   {
      final VarDecl varDecl = isSentence.getDescriptor();
      String name = varDecl.getName();
      if (name == null)
      {
         name = varDecl.getExpr().accept(Namer.INSTANCE, null);
      }

      final String origName = name;
      int num = 1;

      while (par.containsKey(name))
      {
         name = origName + num++;
      }

      varDecl.setName(name);
      par.put(name, varDecl);
      return null;
   }
}
