package org.fulib.scenarios.visitor.codegen;

import org.fulib.scenarios.ast.expr.Expr;
import org.fulib.scenarios.ast.expr.conditional.ConditionalOperator;
import org.fulib.scenarios.ast.expr.conditional.ConditionalOperatorExpr;
import org.fulib.scenarios.ast.expr.conditional.PredicateOperatorExpr;
import org.fulib.scenarios.ast.type.ListType;
import org.fulib.scenarios.ast.type.PrimitiveType;
import org.fulib.scenarios.ast.type.Type;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum AssertionGenerator implements Expr.Visitor<CodeGenDTO, Object>
{
   INSTANCE;

   private static final Pattern METHOD_PATTERN = Pattern.compile("(\\.?\\w+)\\(");

   @Override
   public Object visit(Expr expr, CodeGenDTO par)
   {
      par.addImport("static org.junit.Assert.assertTrue");
      par.bodyBuilder.append("assertTrue(");
      expr.accept(ExprGenerator.INSTANCE, par);
      par.bodyBuilder.append(')');
      return null;
   }

   @Override
   public Object visit(ConditionalOperatorExpr coe, CodeGenDTO par)
   {
      final String template = getTemplate(coe.getOperator(), coe.getLhs(), coe.getRhs());

      if (template == null)
      {
         return this.visit((Expr) coe, par);
      }

      generateCondOp(coe, par, template);

      // imports
      final Matcher matcher = METHOD_PATTERN.matcher(template);
      while (matcher.find())
      {
         final String methodName = matcher.group(1);
         if (methodName.charAt(0) == '.')
         {
            continue;
         }
         if ("assertThat".equals(methodName))
         {
            par.addImport("static org.hamcrest.MatcherAssert.assertThat");
         }
         else if (methodName.startsWith("assert"))
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

   private static String getTemplate(ConditionalOperator op, Expr lhs, Expr rhs)
   {
      switch (op)
      {
      // @formatter:off
      case IS: return PrimitiveType.isNumeric(lhs.getType()) ? "assertEquals(<lhs>, <rhs>, 0)" : "assertEquals(<lhs>, <rhs>)";
      case IS_NOT: return PrimitiveType.isNumeric(lhs.getType()) ? "assertNotEquals(<lhs>, <rhs>, 0)" : "assertNotEquals(<lhs>, <rhs>)";
      case IS_SAME: return "assertSame(<lhs>, <rhs>)";
      case IS_NOT_SAME: return "assertNotSame(<lhs>, <rhs>)";

      case CONTAINS: return "assertThat(<lhs>, hasItem(<rhs>))";
      case NOT_CONTAINS: return "assertThat(<lhs>, not(hasItem(<rhs>)))";
      // @formatter:on
      default:
         return null;
      }
   }

   private static void generateCondOp(ConditionalOperatorExpr conditionalOperatorExpr, CodeGenDTO par, String format)
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
      return PrimitiveType.isNumeric(lhsType);
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
