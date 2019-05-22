package org.fulib.scenarios.tool;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;

import javax.tools.ToolProvider;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public class JavaCompiler
{
   public static int javac(String classpath, Path outFolder, Path... sourceFolders) throws IOException
   {
      Files.createDirectories(outFolder);

      ArrayList<String> args = new ArrayList<>();

      Arrays.stream(sourceFolders).flatMap(JavaCompiler::collectJavaFiles).map(Path::toString).forEach(args::add);

      args.add("-d");
      args.add(outFolder.toString());
      if (classpath != null)
      {
         args.add("-classpath");
         args.add(classpath);
      }

      return ToolProvider.getSystemJavaCompiler().run(null, System.out, System.err, args.toArray(new String[0]));
   }

   public static boolean isJava(Path file)
   {
      return file.toString().endsWith(".java");
   }

   public static boolean isClass(Path file)
   {
      return file.toString().endsWith(".class");
   }

   public static Stream<Path> collectJavaFiles(Path sourceFolder)
   {
      try
      {
         return Files.walk(sourceFolder).filter(JavaCompiler::isJava);
      }
      catch (IOException e)
      {
         return Stream.empty();
      }
   }

   public static void deleteRecursively(Path dir)
   {
      try
      {
         Files.walk(dir).sorted(Comparator.reverseOrder()).forEach(file -> {
            try
            {
               Files.deleteIfExists(file);
            }
            catch (IOException ignored)
            {
            }
         });
      }
      catch (IOException ignored)
      {
      }
   }

   public static Result runTests(Path mainDir, Path testDir)
   {
      try (URLClassLoader classLoader = new URLClassLoader(
         new URL[] { mainDir.toUri().toURL(), testDir.toUri().toURL() }))
      {
         List<Class<?>> testClasses = new ArrayList<>();

         Files.walk(testDir).filter(JavaCompiler::isClass).sorted().forEach(path -> {
            final String relativePath = testDir.relativize(path).toString();
            final String className = relativePath.substring(0, relativePath.length() - ".class".length())
                                                 .replace('/', '.');
            try
            {
               final Class<?> testClass = Class.forName(className, true, classLoader);
               testClasses.add(testClass);
            }
            catch (ClassNotFoundException e)
            {
               throw new AssertionError(className + " should exist", e);
            }
         });

         return new JUnitCore().run(testClasses.toArray(new Class[0]));
      }
      catch (IOException ex)
      {
         throw new RuntimeException("failed to walk " + testDir.toString(), ex);
      }
   }
}
