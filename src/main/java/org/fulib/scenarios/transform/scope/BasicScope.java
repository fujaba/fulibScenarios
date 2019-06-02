package org.fulib.scenarios.transform.scope;

import org.fulib.scenarios.ast.decl.Decl;

import java.util.HashMap;
import java.util.Map;

public class BasicScope implements Scope
{
   private final Map<String, Decl> decls;

   private final Scope outer;

   public BasicScope(Scope outer)
   {
      this.outer = outer;
      this.decls = new HashMap<>();
   }

   public Map<String, Decl> getDecls()
   {
      return this.decls;
   }

   @Override
   public Scope getOuter()
   {
      return this.outer;
   }

   @Override
   public Decl resolve(String name)
   {
      Decl inner = this.decls.get(name);
      return inner != null ? inner : this.outer.resolve(name);
   }

   @Override
   public void add(Decl decl)
   {
      this.decls.put(decl.getName(), decl);
   }
}
