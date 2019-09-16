package org.fulib.scenarios.ast.decl;

public class ResolvedNameDelegate
{
   public static String getValue(ResolvedName resolvedName)
   {
      return resolvedName.getDecl().getName();
   }
}
