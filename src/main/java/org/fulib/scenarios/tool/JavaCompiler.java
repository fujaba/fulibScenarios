package org.fulib.scenarios.tool;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import javax.tools.ToolProvider;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Stream;

public class JavaCompiler
{
   // --------------- File Filters ---------------

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

   // --------------- File Utilities ---------------

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

   // --------------- Tool Invocation ---------------

   public static int scenarioc(OutputStream out, OutputStream err, Path scenarioSrcDir, Path modelSrcDir,
      Path testSrcDir, String... args)
   {
      final List<String> finalArgs = new ArrayList<>(5 + args.length);
      finalArgs.add("-m");
      finalArgs.add(modelSrcDir.toString());
      finalArgs.add("-t");
      finalArgs.add(testSrcDir.toString());
      finalArgs.add(scenarioSrcDir.toString());
      Collections.addAll(finalArgs, args);
      return new ScenarioCompiler().run(null, out, err, finalArgs.toArray(new String[0]));
   }

   public static int javac(String classpath, Path outFolder, Path... sourceFolders) throws IOException
   {
      return javac(null, null, classpath, outFolder, sourceFolders);
   }

   public static int javac(OutputStream out, OutputStream err, String classpath, Path outFolder, Path... sourceFolders)
      throws IOException
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

      return ToolProvider.getSystemJavaCompiler().run(null, out, err, args.toArray(new String[0]));
   }

   public static Result runTests(Path mainClassesDir, Path testClassesDir)
   {
      try (URLClassLoader classLoader = new URLClassLoader(
         new URL[] { mainClassesDir.toUri().toURL(), testClassesDir.toUri().toURL() }))
      {
         List<Class<?>> testClasses = new ArrayList<>();

         Files.walk(testClassesDir).filter(JavaCompiler::isClass).sorted().forEach(path -> {
            final String relativePath = testClassesDir.relativize(path).toString();
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
         throw new RuntimeException("failed to walk " + testClassesDir.toString(), ex);
      }
   }

   public static int genCompileRun(//
      OutputStream out, OutputStream err,//
      Path srcDir, //
      Path modelSrcDir, Path testSrcDir,//
      Path modelClassesDir, Path testClassesDir,//
      String... scenariocArgs) throws Exception
   {
      // prevent dock icon on Mac
      try (FakeProperty ignored = new FakeProperty("apply.awt.UIElement", "true"))
      {
         final int scenarioc = scenarioc(out, err, srcDir, modelSrcDir, testSrcDir, scenariocArgs);
         if (scenarioc != 0)
         {
            return -1;
         }

         String classPath = System.getProperty("java.class.path");

         final int modelJavac = javac(out, err, classPath, modelClassesDir, modelSrcDir);
         if (modelJavac != 0)
         {
            return -2;
         }

         final String testClassPath = modelClassesDir + File.pathSeparator + classPath;
         final int testJavac = javac(out, err, testClassPath, testClassesDir, testSrcDir);
         if (testJavac != 0)
         {
            return -3;
         }

         // call all test methods
         final Result testResult = JavaCompiler.runTests(modelClassesDir, testClassesDir);

         for (final Failure failure : testResult.getFailures())
         {
            final PrintStream stream = new PrintStream(err, false, StandardCharsets.UTF_8.name());
            stream.print(failure.getTestHeader());
            stream.println("failed:");

            failure.getException().printStackTrace(stream);
            stream.flush();
         }

         return testResult.getFailureCount();
      }
   }

   // =============== Classes ===============

   // TODO move to Dyvil library
   private static class FakeProperty implements AutoCloseable
   {
      private final String key;
      private final String original;

      public FakeProperty(String key, String newValue)
      {
         this.key = key;
         this.original = System.getProperty(key);
         System.setProperty(key, newValue);
      }

      @Override
      public void close()
      {
         if (this.original == null)
         {
            System.clearProperty(this.key);
         }
         else
         {
            System.setProperty(this.key, this.original);
         }
      }
   }
}