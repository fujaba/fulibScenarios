package org.fulib.scenarios.visitor.codegen;

import org.fulib.scenarios.ast.NamedExpr;
import org.fulib.scenarios.ast.expr.Expr;
import org.fulib.scenarios.ast.expr.conditional.ConditionalExpr;
import org.fulib.scenarios.ast.sentence.*;
import org.fulib.scenarios.ast.type.ListType;
import org.fulib.scenarios.ast.type.Type;
import org.fulib.scenarios.visitor.ExtractDecl;
import org.fulib.scenarios.visitor.Namer;
import org.fulib.scenarios.visitor.Typer;

import java.util.Iterator;
import java.util.List;

public enum SentenceGenerator implements Sentence.Visitor<CodeGenDTO, Object>
{
   INSTANCE;

   @Override
   public Object visit(Sentence sentence, CodeGenDTO par)
   {
      return null;
   }

   @Override
   public Object visit(SentenceList sentenceList, CodeGenDTO par)
   {
      for (final Sentence item : sentenceList.getItems())
      {
         item.accept(this, par);
      }
      return null;
   }

   @Override
   public Object visit(ExpectSentence expectSentence, CodeGenDTO par)
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
   public Object visit(DiagramSentence diagramSentence, CodeGenDTO par)
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
      case ".txt":
         toolClass = "org.fulib.scenarios.MockupTools";
         toolMethod = "MockupTools.htmlTool().dumpToString";

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
   public Object visit(HasSentence hasSentence, CodeGenDTO par)
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
   public Object visit(IsSentence isSentence, CodeGenDTO par)
   {
      isSentence.getDescriptor().accept(DeclGenerator.INSTANCE, par);
      return null;
   }

   @Override
   public Object visit(AnswerSentence answerSentence, CodeGenDTO par)
   {
      par.emitIndent();
      par.bodyBuilder.append("return ");
      answerSentence.getResult().accept(ExprGenerator.INSTANCE, par);
      par.bodyBuilder.append(";\n");

      return null;
   }

   @Override
   public Object visit(TakeSentence takeSentence, CodeGenDTO par)
   {
      par.emitIndent();
      par.bodyBuilder.append("for (final ");

      final Type elementType = takeSentence.getVarName().accept(ExtractDecl.INSTANCE, null).getType();
      final String varName = takeSentence.getVarName().accept(Namer.INSTANCE, null);

      par.bodyBuilder.append(elementType.accept(TypeGenerator.INSTANCE, par));
      par.bodyBuilder.append(' ');
      par.bodyBuilder.append(varName);
      par.bodyBuilder.append(" : ");

      takeSentence.getCollection().accept(ExprGenerator.INSTANCE, par);

      par.bodyBuilder.append(") {\n");

      par.indentLevel++;
      takeSentence.getBody().accept(this, par);
      par.indentLevel--;

      par.emitIndent();
      par.bodyBuilder.append("}\n");

      return null;
   }

   @Override
   public Object visit(ConditionalSentence conditionalSentence, CodeGenDTO par)
   {
      par.emitIndent();
      par.bodyBuilder.append("if (");
      conditionalSentence.getCondition().accept(ExprGenerator.INSTANCE, par);
      par.bodyBuilder.append(") {\n");

      par.indentLevel++;
      conditionalSentence.getBody().accept(this, par);
      par.indentLevel--;

      par.emitIndent();
      par.bodyBuilder.append("}\n");

      return null;
   }

   @Override
   public Object visit(AssignSentence assignSentence, CodeGenDTO par)
   {
      par.emitIndent();
      par.bodyBuilder.append(assignSentence.getTarget().getName());
      par.bodyBuilder.append(" = ");
      assignSentence.getValue().accept(ExprGenerator.INSTANCE, par);
      par.bodyBuilder.append(";\n");
      return null;
   }

   @Override
   public Object visit(ExprSentence exprSentence, CodeGenDTO par)
   {
      par.emitIndent();
      exprSentence.getExpr().accept(ExprGenerator.INSTANCE, par);
      par.bodyBuilder.append(";\n");
      return null;
   }

   @Override
   public Object visit(TemplateSentence templateSentence, CodeGenDTO par)
   {
      final List<Expr> exprs = templateSentence.getExprs();
      final Iterator<Expr> exprIterator = exprs.iterator();
      final String template = templateSentence.getTemplate();

      par.emitIndent();

      // substitute <%> and <*> in template for the expressions

      // https://stackoverflow.com/questions/2206378/how-to-split-a-string-but-also-keep-the-delimiters
      final String[] split = template.split("(?<=<[%*]>)|(?=<[%*]>)");

      for (String part : split)
      {
         switch (part)
         {
         case "<%>":
            exprIterator.next().accept(ExprGenerator.INSTANCE, par);
            break;
         case "<*>":
            exprIterator.next().accept(ExprGenerator.NO_LIST, par);
            break;
         default:
            par.bodyBuilder.append(part);
            break;
         }
      }

      par.bodyBuilder.append('\n');

      return null;
   }
}
