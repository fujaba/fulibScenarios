package org.fulib.scenarios.visitor;

import org.fulib.scenarios.ast.decl.ClassDecl;
import org.fulib.scenarios.ast.type.*;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

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

   public static Set<ClassDecl> getCommonSuperClasses(Iterable<? extends ClassDecl> classDecls)
   {
      final Iterator<? extends ClassDecl> iterator = classDecls.iterator();
      if (!iterator.hasNext())
      {
         return Collections.emptySet();
      }

      final Set<ClassDecl> commonSuperClasses = new LinkedHashSet<>(iterator.next().getSuperClasses());
      while (iterator.hasNext())
      {
         commonSuperClasses.retainAll(iterator.next().getSuperClasses());
      }
      return commonSuperClasses;
   }

   public static ClassDecl getCommonSuperClass(Iterable<? extends ClassDecl> classDecls)
   {
      final Set<ClassDecl> commonClasses = getCommonSuperClasses(classDecls);
      if (commonClasses.isEmpty())
      {
         return null;
      }
      // since the set is sorted, the first is the most specific common super classes
      return commonClasses.iterator().next();
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
}
