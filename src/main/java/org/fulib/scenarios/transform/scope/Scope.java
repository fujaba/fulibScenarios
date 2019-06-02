package org.fulib.scenarios.transform.scope;

import org.fulib.scenarios.ast.decl.Decl;

public interface Scope
{
   Scope getOuter();

   Decl resolve(String name);

   void add(Decl decl);
}
