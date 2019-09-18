package org.fulib.scenarios.ast.type;

import org.fulib.scenarios.visitor.describe.TypeDescriber;

public class TypeDelegate
{
   public static String getDescription(Type type)
   {
      return type.accept(TypeDescriber.INSTANCE, null);
   }
}
