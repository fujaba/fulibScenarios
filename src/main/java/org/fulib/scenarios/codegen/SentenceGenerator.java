package org.fulib.scenarios.codegen;

import org.fulib.StrUtil;
import org.fulib.classmodel.Clazz;
import org.fulib.scenarios.ast.NamedExpr;
import org.fulib.scenarios.ast.decl.VarDecl;
import org.fulib.scenarios.ast.expr.conditional.ConditionalExpr;
import org.fulib.scenarios.ast.sentence.*;
import org.fulib.scenarios.transform.Namer;
import org.fulib.scenarios.transform.Typer;

public enum SentenceGenerator implements Sentence.Visitor<CodeGenerator, Object>
{
   INSTANCE;

   @Override
   public Object visit(Sentence sentence, CodeGenerator par)
   {
      return null;
   }

   @Override
   public Object visit(ThereSentence thereSentence, CodeGenerator par)
   {
      for (VarDecl varDecl : thereSentence.getVars())
      {
         varDecl.accept(DeclGenerator.INSTANCE, par);
      }
      return null;
   }

   @Override
   public Object visit(ExpectSentence expectSentence, CodeGenerator par)
   {
      for (ConditionalExpr expr : expectSentence.getPredicates())
      {
         par.emitIndent();
         expr.accept(AssertionGenerator.INSTANCE, par);
         par.bodyBuilder.append(";\n");
      }
      return null;
   }

   @Override
   public Object visit(DiagramSentence diagramSentence, CodeGenerator par)
   {
      par.addImport("org.fulib.FulibTools");

      par.emitIndent();
      par.bodyBuilder.append("FulibTools.objectDiagrams().dumpPng(");

      String dir = par.config.getInputDirs().get(0);
      dir += "/" + par.modelManager.getClassModel().getPackageName();

      par.emitStringLiteral(dir + "/" + diagramSentence.getFileName());

      par.bodyBuilder.append(", ");
      diagramSentence.getObject().accept(ExprGenerator.INSTANCE, par);
      par.bodyBuilder.append(");\n");

      return null;
   }

   @Override
   public Object visit(HasSentence hasSentence, CodeGenerator par)
   {
      final String className = hasSentence.getObject().accept(new Typer(null), null);
      final Clazz clazz = par.modelManager.haveClass(className);

      par.emitIndent();
      hasSentence.getObject().accept(ExprGenerator.INSTANCE, par);

      for (NamedExpr attribute : hasSentence.getClauses())
      {
         final String attributeName = attribute.getName().accept(Namer.INSTANCE, null);
         final String attributeType = attribute.getExpr().accept(new Typer(par.modelManager.getClassModel()), null);

         par.modelManager.haveAttribute(clazz, attributeName, attributeType);

         par.bodyBuilder.append(".set").append(StrUtil.cap(attributeName)).append("(");
         attribute.getExpr().accept(ExprGenerator.INSTANCE, par);
         par.bodyBuilder.append(")");
      }

      par.bodyBuilder.append(";\n");

      return null;
   }

   @Override
   public Object visit(IsSentence isSentence, CodeGenerator par)
   {
      isSentence.getDescriptor().accept(DeclGenerator.INSTANCE, par);
      return null;
   }
}
