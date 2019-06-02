package org.fulib.scenarios.transform.scope;

import org.fulib.scenarios.ast.decl.Decl;

import java.util.Map;

public class BasicScope extends DelegatingScope
{
   private final Map<String, Decl> decls;

   public BasicScope(Map<String, Decl> decls, Scope outer)
   {
      super(outer);
      this.decls = decls;
   }

   @Override
   public Decl resolve(String name)
   {
      Decl inner = this.decls.get(name);
      return inner != null ? inner : super.resolve(name);
   }

   @Override
   public void add(Decl decl)
   {
      this.decls.put(decl.getName(), decl);
   }
}
