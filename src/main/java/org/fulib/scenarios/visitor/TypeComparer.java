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

   public static Type getCommonSuperType(Type a, Type b)
   {
      final Result relation = a.accept(INSTANCE, b);
      switch (relation)
      {
      case EQUAL:
      case SUPERTYPE:
         return a;
      case SUBTYPE:
         return b;
      case UNRELATED:
         final ClassDecl classA = a.accept(ExtractClassDecl.INSTANCE, null);
         if (classA != null)
         {
            final ClassDecl classB = b.accept(ExtractClassDecl.INSTANCE, null);
            if (classB != null)
            {
               final ClassDecl superClass = getCommonSuperClass(classA, classB);
               if (superClass != null)
               {
                  return ClassType.of(superClass);
               }
            }
         }
         // fallthrough
      default:
         return PrimitiveType.OBJECT;
      }
   }

   public static boolean isSuperClass(ClassDecl a, ClassDecl b)
   {
      return b.getSuperClasses().contains(a);
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
      return first(getCommonSuperClasses(classDecls));
   }

   private static ClassDecl first(Set<ClassDecl> commonClasses)
   {
      if (commonClasses.isEmpty())
      {
         return null;
      }
      // since the set is sorted, the first is the most specific common super classes
      return commonClasses.iterator().next();
   }

   public static Set<ClassDecl> getCommonSuperClasses(ClassDecl a, ClassDecl b)
   {
      final Set<ClassDecl> superClasses = new LinkedHashSet<>(a.getSuperClasses());
      superClasses.retainAll(b.getSuperClasses());
      return superClasses;
   }

   public static ClassDecl getCommonSuperClass(ClassDecl a, ClassDecl b)
   {
      return first(getCommonSuperClasses(a, b));
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
      if (classB == null)
      {
         return Result.UNRELATED;
      }

      if (classA == classB)
      {
         return Result.EQUAL;
      }
      if (isSuperClass(classA, classB))
      {
         return Result.SUPERTYPE;
      }
      if (isSuperClass(classB, classA))
      {
         return Result.SUBTYPE;
      }
      return Result.UNRELATED;
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
