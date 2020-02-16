package org.fulib.scenarios.ast.scope;

import org.fulib.scenarios.ast.decl.Decl;

import java.util.function.BiConsumer;

public class HidingScope extends DelegatingScope
{
   private final String name;

   public HidingScope(String name, Scope outer)
   {
      super(outer);
      this.name = name;
   }

   @Override
   public Decl resolve(String name)
   {
      return this.name.equals(name) ? null : super.resolve(name);
   }

   @Override
   public void list(BiConsumer<? super String, ? super Decl> consumer)
   {
      consumer.accept(this.name, null);
   }
}
