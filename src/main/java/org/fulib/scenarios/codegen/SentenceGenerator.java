package org.fulib.scenarios.codegen;

import org.fulib.classmodel.Clazz;
import org.fulib.classmodel.FMethod;
import org.fulib.scenarios.ast.NamedExpr;
import org.fulib.scenarios.ast.expr.Expr;
import org.fulib.scenarios.ast.expr.conditional.ConditionalExpr;
import org.fulib.scenarios.ast.sentence.*;
import org.fulib.scenarios.transform.Namer;
import org.fulib.scenarios.transform.Typer;

import java.util.List;

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
      final Expr receiver = callSentence.getReceiver();
      final String methodName = callSentence.getName().accept(Namer.INSTANCE, null);
      final List<Sentence> body = callSentence.getBody();

      par.emitIndent();

      final String returnType;

      final Sentence lastSentence;
      if (!body.isEmpty() && (lastSentence = body.get(body.size() - 1)) instanceof AnswerSentence)
      {
         final Expr result = ((AnswerSentence) lastSentence).getResult();
         returnType = result.accept(new Typer(par.modelManager.getClassModel()), null);

         String varName = result.accept(Namer.INSTANCE, par);
         if (varName == null)
         {
            varName = methodName;
         }

         // TODO IsSentence does the same. We need a CallExpr and use desugar to create it and the method,
         //      but currently we don't have access to Typer that early. Waiting for #26.
         par.bodyBuilder.append(returnType).append(' ').append(varName).append(" = ");
      }
      else
      {
         returnType = "void";
      }

      if (receiver != null)
      {
         receiver.accept(ExprGenerator.INSTANCE, par);
      }
      else
      {
         par.bodyBuilder.append("this");
      }

      par.bodyBuilder.append('.');
      par.bodyBuilder.append(methodName);
      par.bodyBuilder.append("();\n");

      // generate method

      final CodeGenerator bodyGen = new CodeGenerator(par.config);
      bodyGen.modelManager = par.modelManager;
      bodyGen.testManager = par.testManager;

      if (receiver != null)
      {
         final String targetClassName = receiver.accept(new Typer(par.modelManager.getClassModel()), null);

         bodyGen.clazz = par.modelManager.haveClass(targetClassName);
      }
      else
      {
         bodyGen.clazz = par.clazz; // == test class // TODO not within recursive call
      }

      bodyGen.method = new FMethod().setClazz(bodyGen.clazz).writeName(methodName).writeReturnType(returnType);
      bodyGen.bodyBuilder = new StringBuilder();

      for (Sentence sentence : body)
      {
         sentence.accept(this, bodyGen);
      }

      bodyGen.method.setMethodBody(bodyGen.bodyBuilder.toString());

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
}
