package org.fulib.scenarios.library;

import org.fulib.scenarios.tool.ScenarioCompiler;

import java.io.File;
import java.io.IOException;

public class LibraryHelper
{
   public static void loadLibraries(ScenarioCompiler compiler)
   {
      for (String entry : compiler.getConfig().getClasspath())
      {
         loadLibrary(entry, compiler);
      }

      // load rt.jar (the jar file containing java.lang classes)

      // url  = file:/Library/.../rt.jar!/java/lang/Object.class
      // path =      ^^^^^^^^^^^^^^^^^^^
      final String url = Object.class.getResource("/java/lang/Object.class").getFile();
      final String path = url.substring(url.indexOf(':') + 1, url.indexOf(".jar!/") + 4);

      loadLibrary(path, compiler);
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
      final DirLibrary library = new DirLibrary(new File(src));
      compiler.getContext().getLibraries().add(library);
   }

   private static void loadJarLibrary(String src, ScenarioCompiler compiler)
   {
      try
      {
         final JarLibrary library = new JarLibrary(new File(src));
         compiler.getContext().getLibraries().add(library);
      }
      catch (IOException e)
      {
         compiler.getOut().println("warning: failed to load library: " + src);
         e.printStackTrace(compiler.getErr());
      }
   }
}
