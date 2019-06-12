package org.fulib.scenarios.codegen;

import org.fulib.StrUtil;
import org.fulib.scenarios.ast.decl.Name;
import org.fulib.scenarios.ast.expr.Expr;
import org.fulib.scenarios.ast.expr.access.AttributeAccess;
import org.fulib.scenarios.ast.expr.conditional.AttributeCheckExpr;
import org.fulib.scenarios.ast.expr.conditional.ConditionalExpr;
import org.fulib.scenarios.ast.expr.conditional.ConditionalOperator;
import org.fulib.scenarios.ast.expr.conditional.ConditionalOperatorExpr;
import org.fulib.scenarios.ast.type.Type;
import org.fulib.scenarios.transform.Namer;
import org.fulib.scenarios.transform.Typer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum AssertionGenerator implements ConditionalExpr.Visitor<CodeGenerator, Object>
{
   INSTANCE;

   private static final Pattern METHOD_PATTERN = Pattern.compile("(?!\\.)(\\w+)\\(");

   @Override
   public Object visit(ConditionalExpr conditionalExpr, CodeGenerator codeGen)
   {
      throw new UnsupportedOperationException();
   }

   @Override
   public Object visit(AttributeCheckExpr attributeCheckExpr, CodeGenerator codeGen)
   {
      final Expr receiver = attributeCheckExpr.getReceiver();
      final Name attributeName = attributeCheckExpr.getAttribute();

      codeGen.addImport("static org.junit.Assert.assertEquals");

      codeGen.emit("assertEquals(");

      attributeCheckExpr.getValue().accept(ExprGenerator.INSTANCE, codeGen);
      codeGen.emit(", ");

      receiver.accept(ExprGenerator.INSTANCE, codeGen);
      codeGen.emit(".get");
      codeGen.emit(StrUtil.cap(attributeName.accept(Namer.INSTANCE, null)));
      codeGen.emit("()");

      final Type type = AttributeAccess.of(attributeName, receiver).accept(Typer.INSTANCE, null);
      if (Typer.isNumeric(type))
      {
         codeGen.emit(", 0");
      }

      codeGen.emit(")");
      return null;
   }

   @Override
   public Object visit(ConditionalOperatorExpr conditionalOperatorExpr, CodeGenerator par)
   {
      final boolean numeric = isNumeric(conditionalOperatorExpr);
      final ConditionalOperator operator = conditionalOperatorExpr.getOperator();
      final String assertionFormat = numeric ? operator.getNumberAssertion() : operator.getObjectAssertion();
      generateCondOp(conditionalOperatorExpr, par, assertionFormat);

      // imports
      final Matcher matcher = METHOD_PATTERN.matcher(assertionFormat);
      while (matcher.find())
      {
         final String methodName = matcher.group(1);
         if (methodName.startsWith("assert"))
         {
            par.addImport("static org.junit.Assert." + methodName);
         }
         else
         {
            par.addImport("static org.hamcrest.CoreMatchers." + methodName);
         }
      }

      return null;
   }

   static void generateCondOp(ConditionalOperatorExpr conditionalOperatorExpr, CodeGenerator par, String format)
   {
      final int lhsIndex = format.indexOf("<lhs>");
      final int rhsIndex = format.indexOf("<rhs>");

      par.bodyBuilder.append(format, 0, lhsIndex); // before <lhs>
      conditionalOperatorExpr.getLhs().accept(ExprGenerator.INSTANCE, par);
      par.bodyBuilder.append(format, lhsIndex + 5, rhsIndex); // between
      conditionalOperatorExpr.getRhs().accept(ExprGenerator.INSTANCE, par);
      par.bodyBuilder.append(format, rhsIndex + 5, format.length()); // after <rhs>
   }

   static boolean isNumeric(ConditionalOperatorExpr conditionalOperatorExpr)
   {
      final Type lhsType = conditionalOperatorExpr.getLhs().accept(Typer.INSTANCE, null);
      final Type rhsType = conditionalOperatorExpr.getRhs().accept(Typer.INSTANCE, null);
      return Typer.isNumeric(lhsType) || Typer.isNumeric(rhsType);
   }
}
