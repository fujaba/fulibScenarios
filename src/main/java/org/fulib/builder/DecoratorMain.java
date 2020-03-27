package org.fulib.builder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class DecoratorMain
{
   public static void decorate(ClassModelManager manager, Set<String> decoratorClassNames)
   {
      final String packageName = manager.getClassModel().getPackageName();
      final List<Class<? extends ClassModelDecorator>> decoratorClasses = getDecoratorClasses(packageName,
                                                                                              decoratorClassNames);

      RuntimeException failure = null;

      for (final Class<? extends ClassModelDecorator> decoratorClass : decoratorClasses)
      {
         try
         {
            final ClassModelDecorator decorator = decoratorClass.newInstance();
            decorator.decorate(manager);
         }
         catch (Exception e)
         {
            if (failure == null)
            {
               failure = new RuntimeException("class model decoration failed");
            }
            failure.addSuppressed(e);
         }
      }

      if (failure != null)
      {
         throw failure;
      }
   }

   private static List<Class<? extends ClassModelDecorator>> getDecoratorClasses(String packageName,
      Set<String> decoratorClassNames)
   {
      final Package thePackage = Package.getPackage(packageName);
      final ClassModelDecorators decorators = thePackage.getAnnotation(ClassModelDecorators.class);
      if (decorators != null)
      {
         return Arrays.asList(decorators.value());
      }

      final List<Class<? extends ClassModelDecorator>> result = new ArrayList<>();
      for (String decoratorClassName : decoratorClassNames)
      {
         final Class<?> decoratorClass;
         try
         {
            decoratorClass = Class.forName(decoratorClassName);
         }
         catch (ClassNotFoundException ignored)
         {
            continue;
         }

         final boolean isSamePackage = thePackage.equals(decoratorClass.getPackage());
         final boolean isDecoratorSubclass = ClassModelDecorator.class.isAssignableFrom(decoratorClass);
         if (isSamePackage && isDecoratorSubclass)
         {
            result.add((Class<? extends ClassModelDecorator>) decoratorClass);
         }
      }

      return result;
   }
}
