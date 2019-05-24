package org.fulib.scenarios;

import org.fulib.scenarios.tool.Tools;
import org.junit.Ignore;
import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;

@Ignore
public class TestCodeGen
{
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
}
