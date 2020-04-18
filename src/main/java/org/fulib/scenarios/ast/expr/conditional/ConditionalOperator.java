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
   OR("or", "or"),
   AND("and", "and"),
   // equality
   IS("is", "are"),
   IS_NOT("is not", "are not"),
   IS_SAME("is the same as", "are the same as"),
   IS_NOT_SAME("is not the same as", "are not the same as"),
   // comparison
   LT("is less than", "are less than"),
   LE("is less equal", "are less equal"),
   NOT_LT("is not less than", "are not less than"),
   GT("is greater than", "are greater than"),
   GE("is greater equal", "are greater equal"),
   NOT_GT("is not greater than", "are not greater than"),
   // collection
   CONTAINS("contains", "contain"),
   NOT_CONTAINS("does not contain", "do not contain"),
   // IS_IN("is in", "<rhs>.contains(<lhs>)"),
   // IS_NOT_IN("is not in", "!<rhs>.contains(<lhs>)"),
   // string
   MATCHES("matches", "match"),
   ;

   // =============== Static Fields ===============

   public static final Map<String, ConditionalOperator> opMap = buildOpMap();

   static
   {
      LT.lhsType = PrimitiveType.NUMBER;
      LE.lhsType = PrimitiveType.NUMBER;
      NOT_LT.lhsType = PrimitiveType.NUMBER;
      GT.lhsType = PrimitiveType.NUMBER;
      GE.lhsType = PrimitiveType.NUMBER;
      NOT_GT.lhsType = PrimitiveType.NUMBER;
   }

   // =============== Fields ===============

   private final String singular;
   private final String plural;

   private Type lhsType;

   // =============== Constructors ===============

   ConditionalOperator(String singular, String plural)
   {
      this.singular = singular;
      this.plural = plural;
   }

   // =============== Static Methods ===============

   private static Map<String, ConditionalOperator> buildOpMap()
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
      return Collections.unmodifiableMap(map);
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

   public Type getLhsType()
   {
      return this.lhsType;
   }
}
