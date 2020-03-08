package org.fulib.scenarios.visitor.resolve;

import org.fulib.scenarios.ast.Scenario;
import org.fulib.scenarios.ast.decl.ClassDecl;
import org.fulib.scenarios.ast.decl.Decl;
import org.fulib.scenarios.ast.decl.ResolvedName;
import org.fulib.scenarios.ast.decl.VarDecl;
import org.fulib.scenarios.ast.expr.Expr;
import org.fulib.scenarios.ast.expr.call.CallExpr;
import org.fulib.scenarios.ast.expr.collection.ListExpr;
import org.fulib.scenarios.ast.expr.primary.NameAccess;
import org.fulib.scenarios.ast.pattern.Pattern;
import org.fulib.scenarios.ast.sentence.*;
import org.fulib.scenarios.ast.type.Type;
import org.fulib.scenarios.visitor.Namer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public enum SymbolCollector implements Sentence.Visitor<Map<String, Decl>, Object>
{
   INSTANCE;

   public static ListExpr getRoots(Scenario scenario)
   {
      final List<Sentence> sentences = scenario.getBody().getItems();
      if (sentences.isEmpty())
      {
         return null;
      }

      // collect top-level variables
      final Map<String, Decl> symbolTable = new TreeMap<>();
      for (final Sentence item : sentences)
      {
         item.accept(SymbolCollector.INSTANCE, symbolTable);
      }

      if (symbolTable.isEmpty())
      {
         return null;
      }

      final Map<String, ClassDecl> classes = scenario.getFile().getGroup().getClasses();
      final List<Expr> exprs = new ArrayList<>();
      for (Decl it : symbolTable.values())
      {
         // only add variables with types from the data model (i.e. exclude String, double, ... variables)
         final Type type = it.getType();
         final String typeName = type.accept(Namer.INSTANCE, null);
         if (classes.get(typeName) != null)
         {
            exprs.add(NameAccess.of(ResolvedName.of(it)));
         }
      }

      if (exprs.isEmpty())
      {
         return null;
      }

      return ListExpr.of(exprs);
   }

   // --------------- Sentence.Visitor ---------------

   @Override
   public Object visit(Sentence sentence, Map<String, Decl> par)
   {
      return null;
   }

   @Override
   public Object visit(SentenceList sentenceList, Map<String, Decl> par)
   {
      if (!(sentenceList instanceof FlattenSentenceList))
      {
         // the sentence list encapsulates declarations, so does not expose them here.
         return null;
      }

      for (final Sentence sentence : sentenceList.getItems())
      {
         sentence.accept(this, par);
      }

      return null;
   }

   @Override
   public Object visit(IsSentence isSentence, Map<String, Decl> par)
   {
      final VarDecl varDecl = isSentence.getDescriptor();
      String name = varDecl.getName();

      this.addAnswerVar(par, varDecl, isSentence.getDescriptor().getExpr());

      par.put(name, varDecl);
      return null;
   }

   @Override
   public Object visit(PatternExpectSentence patternExpectSentence, Map<String, Decl> par)
   {
      for (final Pattern pattern : patternExpectSentence.getPatterns())
      {
         final Decl decl = pattern.getName().getDecl();
         par.put(decl.getName(), decl);
      }
      return null;
   }

   @Override
   public Object visit(AssignSentence assignSentence, Map<String, Decl> par)
   {
      this.addAnswerVar(par, assignSentence.getTarget(), assignSentence.getValue());
      return null;
   }

   @Override
   public Object visit(ExprSentence exprSentence, Map<String, Decl> par)
   {
      this.addAnswerVar(par, null, exprSentence.getExpr());
      return null;
   }

   private void addAnswerVar(Map<String, Decl> par, Decl varDecl, Expr expr)
   {
      if (!(expr instanceof CallExpr))
      {
         return;
      }

      if (varDecl != null)
      {
         par.put(NameResolver.ANSWER_VAR, varDecl);
      }
      else
      {
         par.remove(NameResolver.ANSWER_VAR);
      }
   }
}
