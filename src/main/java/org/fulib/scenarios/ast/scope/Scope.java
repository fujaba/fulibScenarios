package org.fulib.scenarios.ast.scope;

import org.fulib.scenarios.ast.decl.Decl;
import org.fulib.scenarios.diagnostic.Marker;

import java.util.function.Function;

public interface Scope
{
   Decl resolve(String name);

   default <T extends Decl> T resolve(String name, Class<T> type, Function<? super String, ? extends T> create)
   {
      final Decl own = this.resolve(name);
      if (type.isInstance(own))
      {
         return (T) own;
      }

      throw new UnsupportedOperationException("create");
   }

   void report(Marker marker);
}
