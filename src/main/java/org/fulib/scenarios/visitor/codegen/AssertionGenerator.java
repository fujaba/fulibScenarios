package org.fulib.scenarios.visitor.codegen;

import org.fulib.scenarios.ast.expr.Expr;
import org.fulib.scenarios.ast.expr.conditional.ConditionalExpr;
import org.fulib.scenarios.ast.expr.conditional.ConditionalOperator;
import org.fulib.scenarios.ast.expr.conditional.ConditionalOperatorExpr;
import org.fulib.scenarios.ast.expr.conditional.PredicateOperatorExpr;
import org.fulib.scenarios.ast.type.ListType;
import org.fulib.scenarios.ast.type.Type;
import org.fulib.scenarios.visitor.Typer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum AssertionGenerator implements ConditionalExpr.Visitor<CodeGenDTO, Object>
{
   INSTANCE;

   private static final Pattern METHOD_PATTERN = Pattern.compile("(?!\\.)(\\w+)\\(");

   @Override
   public Object visit(ConditionalOperatorExpr conditionalOperatorExpr, CodeGenDTO par)
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

   static void generateCondOp(ConditionalOperatorExpr conditionalOperatorExpr, CodeGenDTO par, String format)
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
      final Type lhsType = conditionalOperatorExpr.getLhs().getType();
      final Type rhsType = conditionalOperatorExpr.getRhs().getType();
      return Typer.isNumeric(lhsType) || Typer.isNumeric(rhsType);
   }

   @Override
   public Object visit(PredicateOperatorExpr predicateOperatorExpr, CodeGenDTO par)
   {
      final Expr lhs = predicateOperatorExpr.getLhs();
      switch (predicateOperatorExpr.getOperator())
      {
      case IS_NOT_EMPTY:
         if (lhs.getType() instanceof ListType)
         {
            par.addImport("static org.junit.Assert.assertFalse");
            par.bodyBuilder.append("assertFalse(");
            lhs.accept(ExprGenerator.INSTANCE, par);
            par.bodyBuilder.append(".isEmpty())");
         }
         else
         {
            par.addImport("static org.junit.Assert.assertNotNull");
            par.bodyBuilder.append("assertNotNull(");
            lhs.accept(ExprGenerator.INSTANCE, par);
            par.bodyBuilder.append(')');
         }
         return null;
      case IS_EMPTY:
         if (lhs.getType() instanceof ListType)
         {
            par.addImport("static org.junit.Assert.assertTrue");
            par.bodyBuilder.append("assertTrue(");
            lhs.accept(ExprGenerator.INSTANCE, par);
            par.bodyBuilder.append(".isEmpty())");
         }
         else
         {
            par.addImport("static org.junit.Assert.assertNull");
            par.bodyBuilder.append("assertNull(");
            lhs.accept(ExprGenerator.INSTANCE, par);
            par.bodyBuilder.append(')');
         }
         return null;
      }
      return null;
   }
}
