package org.fulib.scenarios;

import org.fulib.scenarios.tool.JavaCompiler;
import org.junit.Ignore;
import org.junit.Test;

import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

@Ignore
public class TestCodeGen
{
   @Test
   public void testGenCompileRun() throws Exception
   {
      final Path outFolder = Paths.get("temp", "out");
      final Path testsFolder = Paths.get("temp", "tests");
      final Path modelFolder = Paths.get("temp", "model");

      Main.main(new String[] { "-m", "temp/model/", "-t", "temp/tests/", "src/test/scenarios/" });

      String classPath = System.getProperty("java.class.path");

      int returnCode = JavaCompiler.javac(classPath, outFolder, modelFolder);
      assertThat(returnCode, equalTo(0));

      int returnCode2 = JavaCompiler.javac(classPath, outFolder, testsFolder);
      assertThat(returnCode2, equalTo(0));

      // call all test methods
      URLClassLoader classLoader = URLClassLoader.newInstance(new URL[] { outFolder.toUri().toURL() });

      JavaCompiler.collectJavaFiles(testsFolder).forEach(file -> {
         try
         {
            String fullClassName = file.toString().split("\\.")[0].replaceAll("/", ".");
            fullClassName = fullClassName.substring("temp.tests.".length());
            Class<?> testClass = Class.forName(fullClassName, true, classLoader);
            Object testObject = testClass.getDeclaredConstructor().newInstance();
            Method testMethod = testClass.getMethod("test");
            testMethod.invoke(testObject);
            Logger.getGlobal().info(fullClassName + ".test() executed");
         }
         catch (Exception e)
         {
            throw new RuntimeException(e);
         }
      });
   }
}
