package org.fulib.scenarios;

import org.fulib.scenarios.tool.Tools;
import org.fulib.yaml.YamlIdMap;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;

@Ignore
public class TestCodeGen
{

   public class Car {
      String name;

      public String getName()
      {
         return name;
      }

      public void setName(String name)
      {
         this.name = name;
      }
   }

   @Test
   public void testShopExample() throws Exception
   {
      Path temp = Paths.get("temp");
      if (Files.exists(temp)) {
         // Files.walk(temp).forEach(path -> deleteFile(path));
      }
      final Path srcFolder = Paths.get("src", "test", "scenarios", "shop");
      final Path testOutFolder = Paths.get("temp", "out", "test");
      final Path modelOutFolder = Paths.get("temp", "out", "model");
      final Path testsFolder = Paths.get("temp", "shop", "test");
      final Path modelFolder = Paths.get("temp", "shop", "main");

      int result = Tools.genCompileRun(System.out, System.err, srcFolder, modelFolder, testsFolder, modelOutFolder,
            testOutFolder, "--class-diagram-svg");

      assertEquals(0, result);
   }



   @Test
   public void testHtmlDump() throws Exception
   {
      Path temp = Paths.get("temp");
      if (Files.exists(temp)) {
         // Files.walk(temp).forEach(path -> deleteFile(path));
      }
      final Path srcFolder = Paths.get("src", "test", "scenarios", "html");
      final Path testOutFolder = Paths.get("temp", "out", "test");
      final Path modelOutFolder = Paths.get("temp", "out", "model");
      final Path testsFolder = Paths.get("temp", "html", "test");
      final Path modelFolder = Paths.get("temp", "html", "main");

      int result = Tools.genCompileRun(System.out, System.err, srcFolder, modelFolder, testsFolder, modelOutFolder,
            testOutFolder, "--class-diagram-svg");

      assertEquals(0, result);
   }

   private void deleteFile(Path path)
   {
      try
      {
         Files.delete(path);
      }
      catch (IOException e)
      {
         // e.printStackTrace();
      }
   }

   @Test
   public void testYamlDump()
   {
      Car car = new Car();
      car.setName("Herbie");

      String text = new YamlIdMap(car.getClass().getPackage().getName())
            .encode(car);

      System.out.println(text);
   }


   @Test
   public void testGenCompileRun() throws Exception
   {
      final Path srcFolder = Paths.get("src", "main", "scenarios");
      final Path testOutFolder = Paths.get("temp", "out", "test");
      final Path modelOutFolder = Paths.get("temp", "out", "model");
      final Path testsFolder = Paths.get("temp", "tests");
      final Path modelFolder = Paths.get("temp", "model");

      assertEquals(0, Tools.genCompileRun(System.out, System.err, srcFolder, modelFolder, testsFolder, modelOutFolder,
                                          testOutFolder));
   }

   @Test
   public void testToOneAssoc() throws Exception
   {
      final Path srcFolder = Paths.get("src", "test", "scenarios", "ToOneAssoc");
      final Path testOutFolder = Paths.get("temp", "out", "test");
      final Path modelOutFolder = Paths.get("temp", "out", "model");
      final Path testsFolder = Paths.get("temp", "tests");
      final Path modelFolder = Paths.get("temp", "model");

      assertEquals(0, Tools.genCompileRun(System.out, System.err, srcFolder, modelFolder, testsFolder, modelOutFolder,
            testOutFolder));
   }
}
