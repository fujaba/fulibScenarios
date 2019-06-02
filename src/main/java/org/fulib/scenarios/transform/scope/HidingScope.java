package org.fulib.scenarios.transform.scope;

import org.fulib.scenarios.ast.decl.Decl;

public class HidingScope implements Scope
{
   private final String name;
   private final Scope  outer;

   public HidingScope(String name, Scope outer)
   {
      this.name = name;
      this.outer = outer;
   }

   @Override
   public Scope getOuter()
   {
      return this.outer;
   }

   @Override
   public Decl resolve(String name)
   {
      return this.name.equals(name) ? null : this.outer.resolve(name);
   }

   @Override
   public void add(Decl decl)
   {
      this.outer.add(decl);
   }
}
