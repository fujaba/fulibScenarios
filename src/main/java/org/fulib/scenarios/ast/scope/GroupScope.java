package org.fulib.scenarios.ast.scope;

import org.fulib.scenarios.ast.ScenarioGroup;
import org.fulib.scenarios.ast.decl.ClassDecl;
import org.fulib.scenarios.ast.decl.Decl;

import java.util.function.BiConsumer;
import java.util.function.Function;

public class GroupScope extends DelegatingScope
{
   // =============== Fields ===============

   private final ScenarioGroup group;

   // =============== Constructors ===============

   public GroupScope(Scope scope, ScenarioGroup group)
   {
      super(scope);
      this.group = group;
   }

   // =============== Methods ===============

   @Override
   public Decl resolve(String name)
   {
      final ClassDecl classDecl = this.group.getClasses().get(name);
      return classDecl != null ? classDecl : super.resolve(name);
   }

   @Override
   public void list(BiConsumer<? super String, ? super Decl> consumer)
   {
      this.group.getClasses().forEach(consumer);
      super.list(consumer);
   }

   @Override
   public <T extends Decl> T resolve(String name, Class<T> type, Function<? super String, ? extends T> create)
   {
      if (!ClassDecl.class.isAssignableFrom(type) || name.indexOf('/') >= 0)
      {
         return super.resolve(name, type, create);
      }

      final Decl superDecl = super.resolve(name);
      if (superDecl instanceof ClassDecl)
      {
         return (T) superDecl;
      }

      return (T) this.group.getClasses().computeIfAbsent(name, n -> {
         final ClassDecl classDecl = (ClassDecl) create.apply(name);
         classDecl.setGroup(this.group);
         return classDecl;
      });
   }
}
