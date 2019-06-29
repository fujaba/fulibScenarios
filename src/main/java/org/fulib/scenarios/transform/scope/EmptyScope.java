package org.fulib.scenarios.transform.scope;

import org.fulib.scenarios.ast.decl.Decl;

public enum EmptyScope implements Scope
{
   INSTANCE;

   @Override
   public Decl resolve(String name)
   {
      return null;
   }

   @Override
   public void add(Decl decl)
   {
   }
}
