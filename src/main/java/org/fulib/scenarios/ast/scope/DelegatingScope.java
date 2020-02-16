package org.fulib.scenarios.ast.scope;

import org.fulib.scenarios.ast.decl.Decl;
import org.fulib.scenarios.diagnostic.Marker;

import java.util.function.BiConsumer;
import java.util.function.Function;

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
   public void list(BiConsumer<? super String, ? super Decl> consumer)
   {
      this.outer.list(consumer);
   }

   @Override
   public <T extends Decl> T resolve(String name, Class<T> type, Function<? super String, ? extends T> create)
   {
      return this.outer.resolve(name, type, create);
   }

   @Override
   public void report(Marker marker)
   {
      this.outer.report(marker);
   }
}
