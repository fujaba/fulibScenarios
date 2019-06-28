package org.fulib.scenarios.tool;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.fulib.scenarios.ast.ScenarioFile;
import org.fulib.scenarios.ast.ScenarioGroup;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.CodingErrorAction;
import java.util.Enumeration;
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
      final int bangIndex = classpathEntry.indexOf('!');
      if (bangIndex < 0)
      {
         return;
      }

      final int length = classpathEntry.length();
      final String src = classpathEntry.substring(0, bangIndex);

      int packageStart = bangIndex + 1;
      while (packageStart < length && classpathEntry.charAt(packageStart) == '/')
      {
         packageStart++;
      }

      int packageEnd = length;
      while (classpathEntry.charAt(packageEnd - 1) == '/')
      {
         packageEnd--;
      }

      final String packageDir = classpathEntry.substring(packageStart, packageEnd);

      if (src.endsWith(".jar"))
      {
         loadJarLibrary(src, packageDir, compiler);
      }
      else
      {
         loadDirLibrary(src, packageDir, compiler);
      }
   }

   private static void loadDirLibrary(String src, String packageDir, ScenarioCompiler compiler)
   {
      final File[] files = new File(src, packageDir).listFiles();
      if (files == null)
      {
         return;
      }

      final ScenarioGroup scenarioGroup = compiler.resolveGroup(packageDir);

      for (File file : files)
      {
         final String fileName = file.getName();
         if (file.isFile() && fileName.endsWith(".md"))
         {
            final ScenarioFile scenarioFile = compiler.parseScenario(file);
            if (scenarioFile != null)
            {
               final String name = fileName.substring(0, fileName.length() - 3);
               scenarioFile.setName(name);
               scenarioFile.setGroup(scenarioGroup);
               scenarioGroup.getFiles().put(name, scenarioFile);
            }
         }
      }
   }

   private static void loadJarLibrary(String src, String packageDir, ScenarioCompiler compiler)
   {
      final ScenarioGroup scenarioGroup = compiler.resolveGroup(packageDir);

      try (JarFile jarFile = new JarFile(src))
      {
         final Enumeration<JarEntry> entries = jarFile.entries();
         while (entries.hasMoreElements())
         {
            final JarEntry entry = entries.nextElement();
            final String entryName = entry.getName();
            if (!entryName.startsWith(packageDir) || !entryName.endsWith(".md"))
            {
               continue;
            }

            final int slashIndex = entryName.lastIndexOf('/');

            try (InputStream stream = jarFile.getInputStream(entry))
            {
               final ReadableByteChannel channel = Channels.newChannel(stream);
               final CharStream input = CharStreams.fromChannel(channel, 4096, CodingErrorAction.REPLACE,
                                                                src + '!' + entryName);

               final ScenarioFile file = compiler.parseScenario(input);
               if (file != null)
               {
                  final String scenarioName = entryName.substring(slashIndex + 1, entryName.length() - 3);
                  file.setName(scenarioName);
                  file.setGroup(scenarioGroup);
                  scenarioGroup.getFiles().put(scenarioName, file);
               }
            }
         }
      }
      catch (IOException e)
      {
         e.printStackTrace(compiler.getErr());
      }
   }
}
