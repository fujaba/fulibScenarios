package org.fulib.scenarios.ast.decl;

import org.fulib.scenarios.ast.ScenarioGroup;
import org.fulib.scenarios.ast.scope.EmptyScope;
import org.fulib.scenarios.ast.scope.GroupScope;
import org.fulib.scenarios.ast.type.Type;
import org.fulib.scenarios.visitor.resolve.TypeResolver;

public class LazyType
{
   // =============== Fields ===============

   private volatile Type    type;
   private volatile boolean resolved;

   // =============== Methods ===============

   public Type get(ScenarioGroup group)
   {
      if (!this.resolved)
      {
         synchronized (this)
         {
            if (!this.resolved)
            {
               this.type = this.type.accept(TypeResolver.INSTANCE, new GroupScope(EmptyScope.INSTANCE, group));
            }
         }
      }

      return this.type;
   }

   public void set(Type type)
   {
      synchronized (this)
      {
         this.type = type;
         this.resolved = false;
      }
   }
}
