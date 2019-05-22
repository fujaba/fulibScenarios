package org.fulib.scenarios;

import org.fulib.scenarios.tool.JavaCompiler;
import org.junit.Test;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

//@Ignore
public class TestCodeGen
{
   @Test
   public void testGenCompileRun() throws Exception
   {
      final Path testOutFolder = Paths.get("temp", "out", "test");
      final Path modelOutFolder = Paths.get("temp", "out", "model");
      final Path testsFolder = Paths.get("temp", "tests");
      final Path modelFolder = Paths.get("temp", "model");

      Main.main(new String[] { "-m", "temp/model/", "-t", "temp/tests/", "src/test/scenarios/" });

      String classPath = System.getProperty("java.class.path");

      int returnCode = JavaCompiler.javac(classPath, modelOutFolder, modelFolder);
      assertThat(returnCode, equalTo(0));

      final String testClassPath = modelOutFolder + File.pathSeparator + classPath;
      int returnCode2 = JavaCompiler.javac(testClassPath, testOutFolder, testsFolder);
      assertThat(returnCode2, equalTo(0));

      // call all test methods
      final Result testResult = JavaCompiler.runTests(modelOutFolder, testOutFolder);

      for (final Failure failure : testResult.getFailures())
      {
         System.err.println(failure.getTestHeader() + " failed:");
         failure.getException().printStackTrace();
      }

      assertThat(testResult.getFailureCount(), equalTo(0));
   }
}
