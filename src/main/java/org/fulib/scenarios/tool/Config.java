package org.fulib.scenarios.tool;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

import java.util.ArrayList;
import java.util.List;

public class Config
{
   // =============== Fields ===============

   private String       modelDir;
   private String       testDir;
   private List<String> inputDirs = new ArrayList<>();

   // =============== Properties ===============

   public String getModelDir()
   {
      return this.modelDir;
   }

   public void setModelDir(String modelDir)
   {
      this.modelDir = modelDir;
   }

   public String getTestDir()
   {
      return this.testDir;
   }

   public void setTestDir(String testDir)
   {
      this.testDir = testDir;
   }

   public List<String> getInputDirs()
   {
      return this.inputDirs;
   }

   // =============== Methods ===============

   public Options createOptions()
   {
      final Options options = new Options();

      final Option modelDir = new Option("m", "modelDir", true, "model output directory, default: src/main/java");
      modelDir.setRequired(false);
      options.addOption(modelDir);

      final Option testDir = new Option("t", "testDir", true, "test output directory, default: src/test/java");
      testDir.setRequired(false);
      options.addOption(testDir);

      return options;
   }

   public void readOptions(CommandLine cmd)
   {
      this.setModelDir(cmd.getOptionValue("modelDir", "src/main/java"));
      this.setTestDir(cmd.getOptionValue("testDir", "src/test/java"));
      this.getInputDirs().addAll(cmd.getArgList());
   }
}
