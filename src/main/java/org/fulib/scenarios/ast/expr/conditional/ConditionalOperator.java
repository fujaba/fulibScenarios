package org.fulib.scenarios.ast.expr.conditional;

public enum ConditionalOperator
{
   // =============== Enum Constants ===============

   // equality
   IS("<lhs>.equals(<rhs>)", "assertEquals(<lhs>, <rhs>)"),
   IS_NOT("!<lhs>.equals(<rhs>)", "assertNotEquals(<lhs>, <rhs>)"),
   IS_SAME("<lhs> == <rhs>", "assertSame(<lhs>, <rhs>)"),
   IS_NOT_SAME("<lhs> != <rhs>", "assertNotSame(<lhs>, <rhs>)"),
   // comparison
   LT("<lhs> < <rhs>", null),
   NOT_LT("<lhs> >= <rhs>", null),
   LE("<lhs> <= <rhs>", null),
   GT("<lhs> > <rhs>", null),
   GE("<lhs> >= <rhs>", null),
   NOT_GT("<lhs> <= <rhs>", null),
   ;

   // =============== Fields ===============

   private final String op;
   private final String assertion;

   // =============== Constructors ===============

   ConditionalOperator(String op, String assertion)
   {
      this.op = op;
      this.assertion = assertion;
   }

   // =============== Properties ===============

   public String getOperatorFormat()
   {
      return this.op;
   }

   public String getAssertionFormat()
   {
      return this.assertion != null ? this.assertion : "assertTrue(" + this.op + ")";
   }
}
