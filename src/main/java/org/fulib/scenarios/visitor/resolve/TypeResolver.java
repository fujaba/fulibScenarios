package org.fulib.scenarios.visitor.resolve;

import org.fulib.scenarios.ast.scope.Scope;
import org.fulib.scenarios.ast.type.*;

import static org.fulib.scenarios.visitor.resolve.DeclResolver.resolveClass;

public enum TypeResolver implements Type.Visitor<Scope, Type>
{
   INSTANCE;

   // --------------- Type.Visitor ---------------

   @Override
   public Type visit(UnresolvedType unresolvedType, Scope par)
   {
      final String name = unresolvedType.getName();

      // potential primitive or wrapper type
      final String primitiveName = name.startsWith("java/lang/") ? name.substring(10) : name;
      final PrimitiveType primitive = PrimitiveType.fromJavaName(primitiveName);
      if (primitive != null)
      {
         return primitive;
      }

      return resolveClass(par, name, unresolvedType.getPosition()).getType();
   }

   @Override
   public Type visit(PrimitiveType primitiveType, Scope par)
   {
      return primitiveType;
   }

   @Override
   public Type visit(ClassType classType, Scope par)
   {
      return classType;
   }

   @Override
   public Type visit(ListType listType, Scope par)
   {
      listType.setElementType(listType.getElementType().accept(this, par));
      return listType;
   }
}
