package org.fulib.scenarios.ast.scope;

import org.fulib.scenarios.ast.decl.Decl;

public abstract class DelegatingScope implements Scope
{
   protected final Scope outer;

   public DelegatingScope(Scope outer)
   {
      this.outer = outer;
   }

   @Override
   public Decl resolve(String name)
   {
      return this.outer.resolve(name);
   }

   @Override
   public void add(Decl decl)
   {
      this.outer.add(decl);
   }
}
