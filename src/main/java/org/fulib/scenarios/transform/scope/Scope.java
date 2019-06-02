package org.fulib.scenarios.transform.scope;

import org.fulib.scenarios.ast.decl.Decl;

public interface Scope
{
   <T> T getEnclosing(Class<T> type);

   Scope getOuter();

   Decl resolve(String name);

   void add(Decl decl);
}
