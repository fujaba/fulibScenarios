package org.fulib.scenarios;

import org.fulib.Tools;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.logging.Logger;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

@Ignore
public class TestCodeGen
{
   @Test
   public void testGenCompileRun() throws MalformedURLException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException
   {
      Main.main(new String[]{"-m", "temp/model/", "-t", "temp/tests/", "src/test/scenarios/"});

      String classPath = System.getProperty("java.class.path");
      int returnCode = Tools.javac(classPath,"temp/out", "temp/model");
      assertThat(returnCode, equalTo(0));

      int returnCode2 = Tools.javac(classPath,"temp/out", "temp/tests");
      assertThat(returnCode2, equalTo(0));

      // call all test methods
      File classesDir = new File("temp/out");
      URLClassLoader classLoader = URLClassLoader.newInstance(new URL[]{classesDir.toURI().toURL()});

      ArrayList<String> fileList = Tools.collectJavaFiles("temp/tests");

      for (String file : fileList)
      {
         String fullClassName = file.split("\\.")[0].replaceAll("/", ".");
         fullClassName = fullClassName.substring("temp.tests.".length());
         Class<?> testClass = Class.forName(fullClassName, true, classLoader);
         Object testObject = testClass.getDeclaredConstructor().newInstance();
         Method testMethod = testClass.getMethod("test");
         testMethod.invoke(testObject);
         Logger.getGlobal().info(fullClassName + ".test() executed");
      }



   }
}
