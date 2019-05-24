package org.fulib.scenarios.codegen;

import org.fulib.classmodel.Clazz;
import org.fulib.scenarios.ast.NamedExpr;
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
      final String sourceDir = par.config.getInputDirs().get(0);
      final String packageDir = par.modelManager.getClassModel().getPackageName();
      final String fileName = diagramSentence.getFileName();
      final String format = fileName.endsWith(".svg") ? "SVG" : "Png";
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
      final String className = hasSentence.getObject().accept(new Typer(null), null);
      final Clazz clazz = par.modelManager.haveClass(className);

      par.emitIndent();
      hasSentence.getObject().accept(ExprGenerator.INSTANCE, par);

      for (NamedExpr attribute : hasSentence.getClauses())
      {
         ExprGenerator.generateSetterCall(par, clazz, attribute);
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
      // TODO generate method

      par.emitIndent();

      callSentence.getReceiver().accept(ExprGenerator.INSTANCE, par);
      par.bodyBuilder.append('.');
      par.bodyBuilder.append(callSentence.getName().accept(Namer.INSTANCE, null));
      par.bodyBuilder.append("();\n");

      return null;
   }
}
