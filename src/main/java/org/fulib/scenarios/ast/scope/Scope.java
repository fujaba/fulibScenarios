package org.fulib.scenarios.ast.scope;

import org.fulib.scenarios.ast.decl.Decl;
import org.fulib.scenarios.diagnostic.Marker;

public interface Scope
{
   Decl resolve(String name);

   void add(Decl decl);

   void report(Marker marker);
}
