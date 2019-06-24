package org.fulib.scenarios.codegen;

import org.fulib.scenarios.ast.NamedExpr;
import org.fulib.scenarios.ast.expr.Expr;
import org.fulib.scenarios.ast.expr.conditional.ConditionalExpr;
import org.fulib.scenarios.ast.sentence.*;
import org.fulib.scenarios.ast.type.ListType;
import org.fulib.scenarios.ast.type.Type;
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
   public Object visit(SentenceList sentenceList, CodeGenerator par)
   {
      for (final Sentence item : sentenceList.getItems())
      {
         item.accept(this, par);
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
      final String sourceDir = par.group.getSourceDir();
      final String packageDir = par.group.getPackageDir();
      final String fileName = diagramSentence.getFileName();
      String target = (sourceDir + "/" + packageDir + "/" + fileName).replace('\\', '/');

      final int dotIndex = fileName.lastIndexOf('.');
      if (dotIndex < 0)
      {
         throw new IllegalStateException("invalid file name '" + fileName + "' - missing extension");
      }

      final String extension = fileName.substring(dotIndex).toLowerCase();
      final String toolClass;
      final String toolMethod;
      switch (extension)
      {
      case ".svg":
         toolClass = "org.fulib.FulibTools";
         toolMethod = "FulibTools.objectDiagrams().dumpSVG";
         break;
      case ".png":
         toolClass = "org.fulib.FulibTools";
         toolMethod = "FulibTools.objectDiagrams().dumpPng";
         break;
      case ".yaml":
         toolClass = "org.fulib.FulibTools";
         toolMethod = "FulibTools.objectDiagrams().dumpYaml";
         break;
      case ".html":
         toolClass = "org.fulib.scenarios.MockupTools";
         toolMethod = "MockupTools.htmlTool().dump";
         break;
      default:
         throw new IllegalStateException("invalid file name '" + fileName + "' - unsupported extension");
      }

      par.addImport(toolClass);

      par.emitIndent();

      // method call
      par.bodyBuilder.append(toolMethod).append('(');
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

      final Expr receiver = hasSentence.getObject();
      final Type receiverType = receiver.accept(Typer.INSTANCE, null);

      receiver.accept(ExprGenerator.INSTANCE, par);

      if (receiverType instanceof ListType)
      {
         par.bodyBuilder.append(".forEach(it -> it");
      }

      for (NamedExpr attribute : hasSentence.getClauses())
      {
         ExprGenerator.generateSetterCall(par, attribute);
      }

      if (receiverType instanceof ListType)
      {
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
