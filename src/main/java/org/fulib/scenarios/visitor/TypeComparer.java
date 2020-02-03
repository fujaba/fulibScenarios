package org.fulib.scenarios.visitor;

import org.fulib.scenarios.ast.decl.ClassDecl;
import org.fulib.scenarios.ast.type.*;

public enum TypeComparer implements Type.Visitor<Type, TypeComparer.Result>
{
   INSTANCE;

   // =============== Classes ===============

   public enum Result
   {
      EQUAL, SUBTYPE, SUPERTYPE, UNRELATED
   }

   // =============== Static Methods ===============

   public static boolean equals(Type a, Type b)
   {
      return a.accept(INSTANCE, b) == Result.EQUAL;
   }

   public static boolean isSuperType(Type a, Type b)
   {
      final Result relation = a.accept(INSTANCE, b);
      return relation == Result.EQUAL || relation == Result.SUPERTYPE;
   }

   // =============== Methods ===============

   @Override
   public Result visit(UnresolvedType unresolvedType, Type par)
   {
      return Result.UNRELATED;
   }

   @Override
   public Result visit(PrimitiveType primitiveType, Type par)
   {
      if (primitiveType == par)
      {
         return Result.EQUAL;
      }
      if (primitiveType == PrimitiveType.OBJECT)
      {
         return Result.SUPERTYPE;
      }

      return Result.UNRELATED;
   }

   @Override
   public Result visit(ClassType classType, Type par)
   {
      if (par == PrimitiveType.OBJECT)
      {
         return Result.SUBTYPE;
      }

      final ClassDecl classA = classType.getClassDecl();
      final ClassDecl classB = par.accept(ExtractClassDecl.INSTANCE, null);
      return classA == classB ? Result.EQUAL : Result.UNRELATED;
   }

   @Override
   public Result visit(ListType listType, Type par)
   {
      if (par == PrimitiveType.OBJECT)
      {
         return Result.SUBTYPE;
      }

      if (!(par instanceof ListType))
      {
         return Result.UNRELATED;
      }

      final Type elementTypeA = listType.getElementType();
      final Type elementTypeB = ((ListType) par).getElementType();
      return elementTypeA.accept(this, elementTypeB);
   }

   @Override
   public Result visit(DynamicType dynamicType, Type par)
   {
      return dynamicType.getBound().accept(this, par);
   }
}
