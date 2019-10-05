package org.fulib.scenarios.ast.decl;

import org.fulib.scenarios.ast.CompilationContext;
import org.fulib.scenarios.ast.scope.GlobalScope;
import org.fulib.scenarios.ast.type.Type;
import org.fulib.scenarios.visitor.resolve.TypeResolver;

public class LazyType
{
   // =============== Fields ===============

   private volatile Type    type;
   private volatile boolean resolved;

   // =============== Methods ===============

   public Type get(CompilationContext context)
   {
      if (!this.resolved)
      {
         synchronized (this)
         {
            if (!this.resolved)
            {
               this.type = this.type.accept(TypeResolver.INSTANCE, new GlobalScope(context));
               this.resolved = true;
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
