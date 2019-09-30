package org.fulib.scenarios.ast.scope;

import org.fulib.scenarios.ast.ScenarioGroup;
import org.fulib.scenarios.ast.decl.ClassDecl;
import org.fulib.scenarios.ast.decl.Decl;

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
   public void add(Decl decl)
   {
      final ClassDecl classDecl = (ClassDecl) decl;
      classDecl.setGroup(this.group);
      this.group.getClasses().put(decl.getName(), classDecl);
   }
}
