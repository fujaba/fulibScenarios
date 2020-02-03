package org.fulib.scenarios.visitor.describe;

import org.fulib.scenarios.ast.type.*;

public enum TypeDescriber implements Type.Visitor<Void, String>
{
   INSTANCE;

   @Override
   public String visit(PrimitiveType primitiveType, Void par)
   {
      return primitiveType.getJavaName();
   }

   @Override
   public String visit(UnresolvedType unresolvedType, Void par)
   {
      return unresolvedType.getName();
   }

   @Override
   public String visit(ClassType classType, Void par)
   {
      return classType.getClassDecl().getName();
   }

   @Override
   public String visit(ListType listType, Void par)
   {
      return "list of " + listType.getElementType().accept(this, par);
   }

   @Override
   public String visit(DynamicType dynamicType, Void par)
   {
      return "dynamic " + dynamicType.getBound().accept(this, par);
   }
}
