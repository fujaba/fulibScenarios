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
      for (String packageDir : compiler.getConfig().getImports())
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
            if (!file.isFile() || !fileName.endsWith(".md"))
            {
               continue;
            }

            final ScenarioFile scenarioFile = compiler.parseScenario(file);
            if (scenarioFile == null)
            {
               continue;
            }

            final String name = fileName.substring(0, fileName.length() - 3);
            scenarioFile.setExternal(true);
            scenarioFile.setName(name);
            scenarioFile.setGroup(scenarioGroup);
            scenarioGroup.getFiles().put(name, scenarioFile);
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
            loadJarEntry(src, compiler, jarFile, entry);
         }
      }
      catch (IOException e)
      {
         e.printStackTrace(compiler.getErr());
      }
   }

   private static void loadJarEntry(String src, ScenarioCompiler compiler, JarFile jarFile, JarEntry entry)
      throws IOException
   {
      final String entryName = entry.getName();

      for (final String packageDir : compiler.getConfig().getImports())
      {
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
               file.setExternal(true);
               file.setName(scenarioName);

               final ScenarioGroup scenarioGroup = compiler.resolveGroup(packageDir);
               file.setGroup(scenarioGroup);
               scenarioGroup.getFiles().put(scenarioName, file);
            }
         }
      }
   }
}
