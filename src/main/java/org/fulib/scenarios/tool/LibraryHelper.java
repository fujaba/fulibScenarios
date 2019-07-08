package org.fulib.scenarios.tool;

import org.fulib.scenarios.ast.ScenarioGroup;
import org.fulib.scenarios.ast.decl.ClassDecl;
import org.fulib.scenarios.ast.type.ClassType;
import org.objectweb.asm.ClassReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class LibraryHelper
{
   public static void loadLibraries(ScenarioCompiler compiler)
   {
      for (String entry : compiler.getConfig().getClasspath())
      {
         loadLibrary(entry, compiler);
      }
   }

   private static void loadLibrary(String classpathEntry, ScenarioCompiler compiler)
   {
      if (classpathEntry.endsWith(".jar"))
      {
         loadJarLibrary(classpathEntry, compiler);
      }
      else
      {
         loadDirLibrary(classpathEntry, compiler);
      }
   }

   private static void loadDirLibrary(String src, ScenarioCompiler compiler)
   {
      for (String packageName : compiler.getConfig().getImports())
      {
         final String packageDir = packageName.replace('.', '/');
         final File[] files = new File(src, packageDir).listFiles();
         if (files == null)
         {
            return;
         }

         final ScenarioGroup scenarioGroup = compiler.resolveGroup(packageDir);

         for (File file : files)
         {
            if (!file.isFile())
            {
               continue;
            }

            loadFile(compiler, scenarioGroup, file);
         }
      }
   }

   private static void loadFile(ScenarioCompiler compiler, ScenarioGroup scenarioGroup, File file)
   {
      final String fileName = file.getName();
      if (fileName.endsWith(".class") && fileName.indexOf('$') < 0) // skip inner classes
      {
         try (final InputStream data = new FileInputStream(file))
         {
            loadClass(scenarioGroup, data);
         }
         catch (Exception e)
         {
            e.printStackTrace(compiler.getErr());
         }
      }
   }

   private static void loadJarLibrary(String src, ScenarioCompiler compiler)
   {
      try (JarFile jarFile = new JarFile(src))
      {
         final Enumeration<JarEntry> entries = jarFile.entries();
         while (entries.hasMoreElements())
         {
            final JarEntry entry = entries.nextElement();
            loadJarEntry(compiler, jarFile, entry);
         }
      }
      catch (IOException e)
      {
         e.printStackTrace(compiler.getErr());
      }
   }

   private static void loadJarEntry(ScenarioCompiler compiler, JarFile jarFile, JarEntry entry) throws IOException
   {
      final String entryName = entry.getName();
      if (!entryName.endsWith(".class") || entryName.indexOf('$') >= 0) // skip inner classes
      {
         return;
      }

      final int slashIndex = entryName.lastIndexOf('/');
      if (slashIndex < 0)
      {
         return;
      }

      // assert entryName.length() > 0 // because there is a '/' in there.
      final int beginIndex = entryName.charAt(0) == '/' ? 1 : 0; // stripping leading /
      final String packageDir = entryName.substring(beginIndex, slashIndex);

      final String packageName = packageDir.replace('/', '.');
      if (!compiler.getConfig().getImports().contains(packageName))
      {
         return;
      }

      final ScenarioGroup scenarioGroup = compiler.resolveGroup(packageDir);

      try (InputStream stream = jarFile.getInputStream(entry))
      {
         loadClass(scenarioGroup, stream);
      }
   }

   public static void loadClass(ScenarioGroup group, InputStream data) throws IOException
   {
      final ClassDecl classDecl = ClassDecl.of(group, null, null, new HashMap<>(), Collections.emptyMap(),
                                               new ArrayList<>());
      classDecl.setType(ClassType.of(classDecl));
      classDecl.setExternal(true);

      final ClassReader reader = new ClassReader(data);
      reader.accept(new ClassModelVisitor(classDecl), ClassReader.SKIP_CODE);

      group.getClasses().put(classDecl.getName(), classDecl);
   }
}
