package org.fulib.scenarios.codegen;

import org.fulib.scenarios.ast.NamedExpr;
import org.fulib.scenarios.ast.expr.conditional.ConditionalExpr;
import org.fulib.scenarios.ast.sentence.*;

public enum SentenceGenerator implements Sentence.Visitor<CodeGenerator, Object>
{
   INSTANCE;

   @Override
   public Object visit(Sentence sentence, CodeGenerator par)
   {
      return null;
   }

   @Override
   public Object visit(SentenceList sentenceList, CodeGenerator par)
   {
      for (final Sentence item : sentenceList.getItems())
      {
         item.accept(this, par);
      }
      return null;
   }

   @Override
   public Object visit(ThereSentence thereSentence, CodeGenerator par)
   {
      throw new UnsupportedOperationException();
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
      // TODO determine from enclosing Scenario
      final String sourceDir = par.group.getSourceDir();
      final String packageDir = par.group.getPackageDir();
      final String fileName = diagramSentence.getFileName();
      String format = fileName.endsWith(".svg") ? "SVG" : "Png";

      if (fileName.endsWith("yaml")) {
         format = "Yaml";
      }

      String target = sourceDir + "/" + packageDir + "/" + fileName;
      target = target.replaceAll("\\\\", "/");

      par.addImport("org.fulib.FulibTools");

      par.emitIndent();
      par.bodyBuilder.append("FulibTools.objectDiagrams().dump").append(format).append("(");

      par.emitStringLiteral(target);

      par.bodyBuilder.append(", ");
      diagramSentence.getObject().accept(ExprGenerator.NO_LIST, par);
      par.bodyBuilder.append(");\n");

      return null;
   }

   @Override
   public Object visit(HasSentence hasSentence, CodeGenerator par)
   {
      par.emitIndent();
      hasSentence.getObject().accept(ExprGenerator.INSTANCE, par);

      for (NamedExpr attribute : hasSentence.getClauses())
      {
         ExprGenerator.generateSetterCall(par, attribute);
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

   @Override
   public Object visit(CreateSentence createSentence, CodeGenerator par)
   {
      throw new UnsupportedOperationException();
   }

   @Override
   public Object visit(CallSentence callSentence, CodeGenerator par)
   {
      throw new UnsupportedOperationException();
   }

   @Override
   public Object visit(AnswerSentence answerSentence, CodeGenerator par)
   {
      par.emitIndent();
      par.bodyBuilder.append("return ");
      answerSentence.getResult().accept(ExprGenerator.INSTANCE, par);
      par.bodyBuilder.append(";\n");

      return null;
   }

   @Override
   public Object visit(ExprSentence exprSentence, CodeGenerator par)
   {
      par.emitIndent();
      exprSentence.getExpr().accept(ExprGenerator.INSTANCE, par);
      par.bodyBuilder.append(";\n");
      return null;
   }
}
