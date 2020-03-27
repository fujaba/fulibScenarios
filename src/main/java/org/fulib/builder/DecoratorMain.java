package org.fulib.builder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class DecoratorMain
{
   public static void decorate(ClassModelManager manager, Set<String> decoratorClassNames)
   {
      decorate(manager, getDecoratorClasses(decoratorClassNames));
   }

   public static void decorate(ClassModelManager manager, List<Class<? extends ClassModelDecorator>> decoratorClasses)
   {
      final String packageName = manager.getClassModel().getPackageName();
      final List<Class<? extends ClassModelDecorator>> filteredDecoratorClasses = getDecoratorClasses(packageName,
                                                                                                      decoratorClasses);

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
      return getDecoratorClasses(packageName, getDecoratorClasses(decoratorClassNames));
   }

   private static List<Class<? extends ClassModelDecorator>> getDecoratorClasses(String packageName,
      List<Class<? extends ClassModelDecorator>> decoratorClasses)
   {
      final Package thePackage = Package.getPackage(packageName);
      if (thePackage != null)
      {
         final ClassModelDecorators decorators = thePackage.getAnnotation(ClassModelDecorators.class);
         if (decorators != null)
         {
            return Arrays.asList(decorators.value());
         }
      }

      return decoratorClasses
         .stream()
         .filter(c -> packageName.equals(c.getPackage().getName()))
         .collect(Collectors.toList());
   }

   public static List<Class<? extends ClassModelDecorator>> getDecoratorClasses(Set<String> decoratorClassNames)
   {
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

         if (ClassModelDecorator.class.isAssignableFrom(decoratorClass))
         {
            result.add((Class<? extends ClassModelDecorator>) decoratorClass);
         }
      }

      return result;
   }
}
