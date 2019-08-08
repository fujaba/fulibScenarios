package org.fulib.scenarios.visitor.resolve;

import org.fulib.scenarios.ast.scope.Scope;
import org.fulib.scenarios.ast.type.*;

import static org.fulib.scenarios.visitor.resolve.NameResolver.resolveClass;

public enum TypeResolver implements Type.Visitor<Scope, Type>
{
   INSTANCE;

   // --------------- Type.Visitor ---------------

   @Override
   public Type visit(UnresolvedType unresolvedType, Scope par)
   {
      final String name = unresolvedType.getName();
      final PrimitiveType primitive = PrimitiveType.javaNameMap.get(name);

      // TODO handle non-null packageDir
      return primitive != null ? primitive : resolveClass(par, name, unresolvedType.getPosition()).getType();
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
