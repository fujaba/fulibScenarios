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

   private boolean classDiagram;
   private boolean classDiagramSVG;

   private boolean objectDiagram;
   private boolean objectDiagramSVG;

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

   public boolean isClassDiagram()
   {
      return this.classDiagram;
   }

   public void setClassDiagram(boolean classDiagram)
   {
      this.classDiagram = classDiagram;
   }

   public boolean isClassDiagramSVG()
   {
      return this.classDiagramSVG;
   }

   public void setClassDiagramSVG(boolean classDiagramSVG)
   {
      this.classDiagramSVG = classDiagramSVG;
   }

   public boolean isObjectDiagram()
   {
      return this.objectDiagram;
   }

   public void setObjectDiagram(boolean objectDiagram)
   {
      this.objectDiagram = objectDiagram;
   }

   public boolean isObjectDiagramSVG()
   {
      return this.objectDiagramSVG;
   }

   public void setObjectDiagramSVG(boolean objectDiagramSVG)
   {
      this.objectDiagramSVG = objectDiagramSVG;
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

      options.addOption(new Option(null, "class-diagram", false, "generate class diagram as .png into model folder"));
      options.addOption(
         new Option(null, "class-diagram-svg", false, "generate class diagram as .svg into model folder"));

      options.addOption(new Option(null, "object-diagram", false,
                                   "append a statement to each test that generates an object diagram as .png"));
      options.addOption(new Option(null, "object-diagram-svg", false,
                                   "append a statement to each test that generates an object diagram as .svg"));

      return options;
   }

   public void readOptions(CommandLine cmd)
   {
      this.setModelDir(cmd.getOptionValue("modelDir", "src/main/java"));
      this.setTestDir(cmd.getOptionValue("testDir", "src/test/java"));
      this.getInputDirs().addAll(cmd.getArgList());
      this.setClassDiagram(cmd.hasOption("class-diagram"));
      this.setClassDiagramSVG(cmd.hasOption("class-diagram-svg"));
      this.setObjectDiagram(cmd.hasOption("object-diagram"));
      this.setObjectDiagramSVG(cmd.hasOption("object-diagram-svg"));
   }
}