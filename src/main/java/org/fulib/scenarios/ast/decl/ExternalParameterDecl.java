package org.fulib.scenarios.ast.decl;

import org.fulib.scenarios.ast.type.Type;

public class ExternalParameterDecl extends ParameterDecl.Impl
{
   // =============== Fields ===============

   private final LazyType type = new LazyType();

   // =============== Constructors ===============

   public ExternalParameterDecl()
   {
   }

   public ExternalParameterDecl(MethodDecl owner, String name, Type type)
   {
      // super constructor sets Impl.type field instead of calling setter.
      super(owner, name, null);
      this.setType(type);
   }

   // =============== Properties ===============

   @Override
   public Type getType()
   {
      return this.type.get(this.getOwner().getOwner().getGroup());
   }

   @Override
   public void setType(Type type)
   {
      this.type.set(type);
   }
}
