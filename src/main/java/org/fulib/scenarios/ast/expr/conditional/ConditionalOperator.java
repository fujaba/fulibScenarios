package org.fulib.scenarios.ast.expr.conditional;

import java.util.HashMap;
import java.util.Map;

public enum ConditionalOperator
{
   // =============== Enum Constants ===============

   // equality
   IS("is", "<lhs>.equals(<rhs>)", "assertEquals(<lhs>, <rhs>)"),
   IS_NOT("is not", "!<lhs>.equals(<rhs>)", "assertNotEquals(<lhs>, <rhs>)"),
   IS_SAME("is the same as", "<lhs> == <rhs>", "assertSame(<lhs>, <rhs>)"),
   IS_NOT_SAME("is not the same as", "<lhs> != <rhs>", "assertNotSame(<lhs>, <rhs>)"),
   // comparison
   LT("is less than", "<lhs> < <rhs>", null),
   NOT_LT("is not less than", "<lhs> >= <rhs>", null),
   LE("is less equal", "<lhs> <= <rhs>", null),
   GT("is greater than", "<lhs> > <rhs>", null),
   GE("is greater equal", "<lhs> >= <rhs>", null),
   NOT_GT("is not greater than", "<lhs> <= <rhs>", null),
   ;

   // =============== Static Fields ===============

   private static Map<String, ConditionalOperator> opMap;

   // =============== Fields ===============

   private final String op;
   private final String javaOperatorFormat;
   private final String assertion;

   // =============== Constructors ===============

   ConditionalOperator(String op, String javaOperatorFormat, String assertion)
   {
      this.op = op;
      this.javaOperatorFormat = javaOperatorFormat;
      this.assertion = assertion;
   }

   // =============== Static Methods ===============

   public static ConditionalOperator getByOp(String op)
   {
      if (opMap != null)
      {
         return opMap.get(op);
      }

      final ConditionalOperator[] values = values();
      opMap = new HashMap<>(values.length);
      for (ConditionalOperator operator : values)
      {
         opMap.put(operator.getOp(), operator);
      }
      return opMap.get(op);
   }

   // =============== Properties ===============

   public String getOp()
   {
      return this.op;
   }

   public String getOperatorFormat()
   {
      return this.javaOperatorFormat;
   }

   public String getAssertionFormat()
   {
      return this.assertion != null ? this.assertion : "assertTrue(" + this.javaOperatorFormat + ")";
   }
}
