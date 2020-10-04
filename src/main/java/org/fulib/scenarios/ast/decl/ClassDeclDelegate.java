package org.fulib.scenarios.ast.decl;

import org.fulib.scenarios.ast.type.Type;
import org.fulib.scenarios.visitor.ExtractClassDecl;

import java.util.ArrayDeque;
import java.util.LinkedHashSet;
import java.util.Queue;
import java.util.Set;

public class ClassDeclDelegate
{
   public static Set<ClassDecl> getSuperClasses(ClassDecl classDecl)
   {
      Set<ClassDecl> superClasses = classDecl.getStoredSuperClasses();
      if (superClasses == null)
      {
         //noinspection SynchronizationOnLocalVariableOrMethodParameter
         synchronized (classDecl)
         {
            superClasses = classDecl.getStoredSuperClasses();
            if (superClasses == null)
            {
               superClasses = computeSuperClasses(classDecl);
               classDecl.setStoredSuperClasses(superClasses);
            }
         }
      }

      return superClasses;
   }

   /**
    * Computes the set of superclasses in breadth-first order.
    *
    * @param classDecl the class
    * @return the set of superclasses in breadth-first order.
    */
   private static Set<ClassDecl> computeSuperClasses(ClassDecl classDecl)
   {
      final Set<ClassDecl> result = new LinkedHashSet<>();
      final Queue<ClassDecl> queue = new ArrayDeque<>();

      queue.add(classDecl);

      while (!queue.isEmpty())
      {
         final ClassDecl c = queue.remove();
         if (!result.add(c))
         {
            continue;
         }

         Type superType = c.getSuperType();
         if (superType == null)
         {
            continue;
         }

         ClassDecl sup = superType.accept(ExtractClassDecl.INSTANCE, null);
         if (sup == null)
         {
            continue;
         }

         queue.add(sup);
         // interfaces...?
      }

      return result;
   }
}
