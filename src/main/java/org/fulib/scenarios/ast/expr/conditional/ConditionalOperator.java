package org.fulib.scenarios.ast.expr.conditional;

import java.util.HashMap;
import java.util.Map;

public enum ConditionalOperator
{
   // =============== Enum Constants ===============

   // boolean
   OR("or", null, "<lhs> || <rhs>"),
   AND("and", null, "<lhs> && <rhs>"),
   // equality
   IS("is", "<lhs> == <rhs>", "<lhs>.equals(<rhs>)", "assertEquals(<lhs>, <rhs>, 0)", "assertEquals(<lhs>, <rhs>)"),
   IS_NOT("is not", "<lhs> != <rhs>", "!<lhs>.equals(<rhs>)", "assertNotEquals(<lhs>, <rhs>, 0)",
          "assertNotEquals(<lhs>, <rhs>)"),
   IS_SAME("is the same as", null, "<lhs> == <rhs>", null, "assertSame(<lhs>, <rhs>)"),
   IS_NOT_SAME("is not the same as", null, "<lhs> != <rhs>", null, "assertNotSame(<lhs>, <rhs>)"),
   // comparison
   LT("is less than", "<lhs> < <rhs>", "<lhs>.compareTo(<rhs>) < 0"),
   NOT_LT("is not less than", "<lhs> >= <rhs>", "<lhs>.compareTo(<rhs>) >= 0"),
   LE("is less equal", "<lhs> <= <rhs>", "<lhs>.compareTo(<rhs>) <= 0"),
   GT("is greater than", "<lhs> > <rhs>", "<lhs>.compareTo(<rhs>)  > 0"),
   GE("is greater equal", "<lhs> >= <rhs>", "<lhs>.compareTo(<rhs>) >= 0"),
   NOT_GT("is not greater than", "<lhs> <= <rhs>", "<lhs>.compareTo(<rhs>) <= 0"),
   ;

   // =============== Static Fields ===============

   private static Map<String, ConditionalOperator> opMap;

   // =============== Fields ===============

   private final String op;
   private final String numberOperator;
   private final String objectOperator;
   private final String numberAssertion;
   private final String objectAssertion;

   // =============== Constructors ===============

   ConditionalOperator(String op, String javaNumberOperator, String javaObjectOperator)
   {
      this(op, javaNumberOperator, javaObjectOperator, wrapAssert(javaNumberOperator),
           wrapAssert(javaObjectOperator));
   }

   ConditionalOperator(String op, String numberOperator, String objectOperator, String numberAssertion,
      String objectAssertion)
   {
      this.op = op;
      this.numberOperator = numberOperator;
      this.objectOperator = objectOperator;
      this.numberAssertion = numberAssertion;
      this.objectAssertion = objectAssertion;
   }

   // =============== Static Methods ===============

   private static String wrapAssert(String javaObjectOperator)
   {
      return "assertTrue(" + javaObjectOperator + ")";
   }

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

   public String getNumberOperator()
   {
      return this.numberOperator;
   }

   public String getObjectOperator()
   {
      return this.objectOperator;
   }

   public String getNumberAssertion()
   {
      return this.numberAssertion;
   }

   public String getObjectAssertion()
   {
      return this.objectAssertion;
   }
}
