package org.fulib.scenarios.tool;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

import java.io.File;
import java.util.*;

public class Config
{
   // =============== Fields ===============

   private String       modelDir;
   private String       testDir;
   private List<String> inputDirs = new ArrayList<>();
   private List<String> classpath = new ArrayList<>();
   private Set<String>  imports   = new HashSet<>();

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

   public List<String> getClasspath()
   {
      return this.classpath;
   }

   public Set<String> getImports()
   {
      return this.imports;
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

      options.addOption(new Option("m", "modelDir", true, "model output directory, default: src/main/java"));

      options.addOption(new Option("t", "testDir", true, "test output directory, default: src/test/java"));

      final Option classpath = new Option("cp", "classpath", true,
                                          "a list of directories or jar files from which to load other scenarios, separated by '"
                                          + File.pathSeparatorChar + "'.");
      classpath.setArgs(Option.UNLIMITED_VALUES);
      classpath.setValueSeparator(File.pathSeparatorChar);
      options.addOption(classpath);

      final Option imports = new Option("i", "imports", true,
                                        "a list of packages to be automatically imported by each source file, separated by ','.");
      imports.setArgs(Option.UNLIMITED_VALUES);
      imports.setValueSeparator(',');
      options.addOption(imports);

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

      final String[] classpath = cmd.getOptionValues("classpath");
      if (classpath != null)
      {
         Collections.addAll(this.classpath, classpath);
      }

      final String[] imports = cmd.getOptionValues("imports");
      if (imports != null)
      {
         Collections.addAll(this.imports, imports);
      }
   }
}
