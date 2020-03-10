package org.fulib.scenarios.ast.expr.conditional;

import org.fulib.scenarios.ast.type.PrimitiveType;
import org.fulib.scenarios.ast.type.Type;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public enum ConditionalOperator
{
   // =============== Enum Constants ===============

   // boolean
   OR("or", "or", "<lhs> || <rhs>"),
   AND("and", "and", "<lhs> && <rhs>"),
   // equality
   IS("is", "are", "<lhs> == <rhs>", "<lhs>.equals(<rhs>)", "assertEquals(<lhs>, <rhs>, 0)",
      "assertEquals(<lhs>, <rhs>)"),
   IS_NOT("is not", "are not", "<lhs> != <rhs>", "!<lhs>.equals(<rhs>)", "assertNotEquals(<lhs>, <rhs>, 0)",
          "assertNotEquals(<lhs>, <rhs>)"),
   IS_SAME("is the same as", "are the same as", null, "<lhs> == <rhs>", null, "assertSame(<lhs>, <rhs>)"),
   IS_NOT_SAME("is not the same as", "are not the same as", null, "<lhs> != <rhs>", null,
               "assertNotSame(<lhs>, <rhs>)"),
   // comparison
   LT("is less than", "are less than", "<lhs> < <rhs>", "<lhs>.compareTo(<rhs>) < 0"),
   NOT_LT("is not less than", "are not less than", "<lhs> >= <rhs>", "<lhs>.compareTo(<rhs>) >= 0"),
   LE("is less equal", "are less equal", "<lhs> <= <rhs>", "<lhs>.compareTo(<rhs>) <= 0"),
   GT("is greater than", "are greater than", "<lhs> > <rhs>", "<lhs>.compareTo(<rhs>)  > 0"),
   GE("is greater equal", "are greater equal", "<lhs> >= <rhs>", "<lhs>.compareTo(<rhs>) >= 0"),
   NOT_GT("is not greater than", "are not greater than", "<lhs> <= <rhs>", "<lhs>.compareTo(<rhs>) <= 0"),
   // collection
   CONTAINS("contains", "contain", "<lhs>.contains(<rhs>)", "<lhs>.contains(<rhs>)",
            "assertThat(<lhs>, hasItem(<rhs>))", "assertThat(<lhs>, hasItem(<rhs>))"),
   NOT_CONTAINS("does not contain", "do not contain", "!<lhs>.contains(<rhs>)", "!<lhs>.contains(<rhs>)",
                "assertThat(<lhs>, not(hasItem(<rhs>)))", "assertThat(<lhs>, not(hasItem(<rhs>)))"),
   // IS_IN("is in", "<rhs>.contains(<lhs>)"),
   // IS_NOT_IN("is not in", "!<rhs>.contains(<lhs>)"),
   // string
   MATCHES("matches", null, null, "<lhs>.matches(<rhs>)"),
   ;

   // =============== Static Fields ===============

   public static final Map<String, ConditionalOperator> opMap;

   static
   {
      final ConditionalOperator[] values = values();
      final Map<String, ConditionalOperator> map = new HashMap<>(values.length);
      for (ConditionalOperator operator : values)
      {
         map.put(operator.getSingular(), operator);
         final String plural = operator.getPlural();
         if (plural != null)
         {
            map.put(plural, operator);
         }
      }

      opMap = Collections.unmodifiableMap(map);

      LT.lhsType = PrimitiveType.NUMBER;
      LE.lhsType = PrimitiveType.NUMBER;
      NOT_LT.lhsType = PrimitiveType.NUMBER;
      GT.lhsType = PrimitiveType.NUMBER;
      GE.lhsType = PrimitiveType.NUMBER;
      NOT_GT.lhsType = PrimitiveType.NUMBER;

      MATCHES.lhsType = PrimitiveType.STRING;
   }

   // =============== Fields ===============

   private final String singular;
   private final String plural;
   private final String numberOperator;
   private final String objectOperator;
   private final String numberAssertion;
   private final String objectAssertion;

   private Type lhsType;

   // =============== Constructors ===============

   ConditionalOperator(String singular, String plural, String javaOperator)
   {
      this(singular, plural, javaOperator, javaOperator);
   }

   ConditionalOperator(String singular, String plural, String javaNumberOperator, String javaObjectOperator)
   {
      this(singular, plural, javaNumberOperator, javaObjectOperator, wrapAssert(javaNumberOperator),
           wrapAssert(javaObjectOperator));
   }

   ConditionalOperator(String singular, String plural, String numberOperator, String objectOperator,
      String numberAssertion, String objectAssertion)
   {
      this.singular = singular;
      this.plural = plural;
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
      return opMap.get(op);
   }

   // =============== Properties ===============

   public String getSingular()
   {
      return this.singular;
   }

   public String getPlural()
   {
      return this.plural;
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

   public Type getLhsType()
   {
      return this.lhsType;
   }
}
