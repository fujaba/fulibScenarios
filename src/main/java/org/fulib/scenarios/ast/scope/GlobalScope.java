package org.fulib.scenarios.ast.scope;

import org.fulib.scenarios.ast.CompilationContext;
import org.fulib.scenarios.ast.ScenarioGroup;
import org.fulib.scenarios.ast.decl.ClassDecl;
import org.fulib.scenarios.ast.decl.Decl;
import org.fulib.scenarios.ast.decl.ExternalClassDecl;
import org.fulib.scenarios.ast.type.ClassType;
import org.fulib.scenarios.diagnostic.Marker;
import org.fulib.scenarios.library.ClassModelVisitor;
import org.fulib.scenarios.library.Library;
import org.fulib.scenarios.visitor.resolve.DeclResolver;
import org.objectweb.asm.ClassReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

public class GlobalScope implements Scope
{
   // =============== Fields ===============

   private final CompilationContext context;

   private final List<ScenarioGroup> importedGroups;

   // =============== Constructors ===============

   public GlobalScope(CompilationContext context)
   {
      this.context = context;
      this.importedGroups = context.getConfig().getImports().stream()
                                   .map(i -> DeclResolver.resolveGroup(context, i.replace('.', '/')))
                                   .collect(Collectors.toList());
   }

   // =============== Methods ===============

   @Override
   public void report(Marker marker)
   {
      throw new UnsupportedOperationException();
   }

   @Override
   public Decl resolve(String name)
   {
      final int nameSeparator = name.lastIndexOf('/');
      if (nameSeparator >= 0)
      {
         return this.resolveClass(name.substring(0, nameSeparator), name.substring(nameSeparator + 1), name);
      }

      // it's a simple name
      for (final ScenarioGroup importGroup : this.importedGroups)
      {
         final ClassDecl importedClass = resolveClass(importGroup, name, null);
         if (importedClass != null)
         {
            return importedClass;
         }
      }

      return null;
   }

   @Override
   public void list(BiConsumer<? super String, ? super Decl> consumer)
   {
      for (final ScenarioGroup group : this.importedGroups)
      {
         group.getClasses().forEach(consumer);
      }
   }

   private ClassDecl resolveClass(String packageDir, String simpleName, String fullName)
   {
      final ScenarioGroup group = DeclResolver.resolveGroup(this.context, packageDir);
      return resolveClass(group, simpleName, fullName);
   }

   // =============== Static Methods ===============

   private static ClassDecl resolveClass(ScenarioGroup group, String simpleName, String fullName)
   {
      return group.getClasses().computeIfAbsent(simpleName, s -> {
         return loadClass(group, fullName != null ? fullName : group.getPackageDir() + '/' + simpleName);
      });
   }

   private static ClassDecl loadClass(ScenarioGroup group, String fullName)
   {
      for (final Library library : group.getContext().getLibraries())
      {
         try (final InputStream input = library.loadClass(fullName))
         {
            if (input != null)
            {
               return loadClass(group, input);
            }
         }
         catch (IOException ex)
         {
            // TODO
            ex.printStackTrace();
         }
      }

      return null;
   }

   private static ClassDecl loadClass(ScenarioGroup group, InputStream data) throws IOException
   {
      final ExternalClassDecl classDecl = new ExternalClassDecl(group, null, null, new HashMap<>(), new HashMap<>(),
                                                                new ArrayList<>());
      classDecl.setType(ClassType.of(classDecl));
      classDecl.setExternal(true);

      final ClassReader reader = new ClassReader(data);
      reader.accept(new ClassModelVisitor(classDecl), ClassReader.SKIP_CODE);

      return classDecl;
   }
}
