package org.fulib.scenarios.ast.scope;

import org.fulib.scenarios.ast.decl.Decl;

public interface Scope
{
   Decl resolve(String name);

   void add(Decl decl);
}
