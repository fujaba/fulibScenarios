package org.fulib.scenarios.ast.expr.conditional;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public enum PredicateOperator
{
   // =============== Enum Constants ===============

   // empty
   IS_EMPTY("is empty", "are empty"),
   IS_NOT_EMPTY("is not empty", "are not empty"),
   ;

   // =============== Static Fields ===============

   public static final Map<String, PredicateOperator> nameMap;

   static
   {
      final PredicateOperator[] values = values();
      final Map<String, PredicateOperator> names = new HashMap<>(values.length);

      for (final PredicateOperator value : values)
      {
         names.put(value.getSingular(), value);
         names.put(value.getPlural(), value);
      }

      nameMap = Collections.unmodifiableMap(names);
   }

   // =============== Fields ===============

   private final String singular;
   private final String plural;

   // =============== Constructors ===============

   PredicateOperator(String singular, String plural)
   {
      this.singular = singular;
      this.plural = plural;
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
}
