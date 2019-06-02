package org.fulib.scenarios.transform.scope;

import org.fulib.scenarios.ast.ScenarioGroup;
import org.fulib.scenarios.ast.decl.ClassDecl;
import org.fulib.scenarios.ast.decl.Decl;

public class GroupScope implements Scope
{
   private final ScenarioGroup group;

   public GroupScope(ScenarioGroup group)
   {
      this.group = group;
   }

   @Override
   public <T> T getEnclosing(Class<T> type)
   {
      return type.isAssignableFrom(ScenarioGroup.class) ? (T) this.group : null;
   }

   @Override
   public Scope getOuter()
   {
      return null;
   }

   @Override
   public Decl resolve(String name)
   {
      return this.group.getClasses().get(name);
   }

   @Override
   public void add(Decl decl)
   {
      this.group.getClasses().put(decl.getName(), (ClassDecl) decl);
   }
}
