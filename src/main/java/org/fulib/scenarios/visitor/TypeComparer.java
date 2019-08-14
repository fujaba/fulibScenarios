package org.fulib.scenarios.visitor;

import org.fulib.scenarios.ast.decl.ClassDecl;
import org.fulib.scenarios.ast.type.*;

public enum TypeComparer implements Type.Visitor<Type, TypeComparer.Result>
{
   INSTANCE;

   // =============== Classes ===============

   public enum Result
   {
      EQUAL, SUBTYPE, UNRELATED
   }

   // =============== Static Methods ===============

   public static boolean equals(Type a, Type b)
   {
      return a.accept(INSTANCE, b) == Result.EQUAL;
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
      return primitiveType == par ? Result.EQUAL : Result.UNRELATED;
   }

   @Override
   public Result visit(ClassType classType, Type par)
   {
      final ClassDecl classA = classType.getClassDecl();
      final ClassDecl classB = par.accept(ExtractClassDecl.INSTANCE, null);
      return classA == classB ? Result.EQUAL : Result.UNRELATED;
   }

   @Override
   public Result visit(ListType listType, Type par)
   {
      if (!(par instanceof ListType))
      {
         return Result.UNRELATED;
      }

      final Type elementTypeA = listType.getElementType();
      final Type elementTypeB = ((ListType) par).getElementType();
      return elementTypeA.accept(this, elementTypeB);
   }
}
