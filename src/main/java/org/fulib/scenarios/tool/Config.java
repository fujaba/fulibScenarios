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

   private final Map<String, String> diagramHandlers = new HashMap<>();

   {
      // default imports
      this.imports.add("java.lang");
      this.imports.add("org.fulib.mockups");

      this.diagramHandlers.put(".svg", "import(org.fulib.FulibTools).objectDiagrams().dumpSVG(%s, %s)");
      this.diagramHandlers.put(".html.png", "import(org.fulib.mockups.FulibMockups).mockupTool().dump(%s, %s)");
      this.diagramHandlers.put(".png", "import(org.fulib.FulibTools).objectDiagrams().dumpPng(%s, %s)");
      this.diagramHandlers.put(".yaml", "import(org.fulib.FulibTools).objectDiagrams().dumpYaml(%s, %s)");
      this.diagramHandlers.put(".html", "import(org.fulib.scenarios.MockupTools).htmlTool().dump(%s, %s)");
      this.diagramHandlers.put(".txt", "import(org.fulib.scenarios.MockupTools).htmlTool().dumpToString(%s, %s)");
   }

   private Set<String> decoratorClasses = new TreeSet<>();

   private boolean generateTables;

   private boolean classDiagram;
   private boolean classDiagramSVG;

   private boolean objectDiagram;
   private boolean objectDiagramSVG;

   private boolean dryRun;

   private boolean markerEndColumns;

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

   public Set<String> getDecoratorClasses()
   {
      return this.decoratorClasses;
   }

   public Set<String> getDiagramHandlerExtensions()
   {
      return this.diagramHandlers.keySet();
   }

   public String getDiagramHandler(String extension)
   {
      return this.diagramHandlers.get(extension);
   }

   public String getDiagramHandlerFromFile(String fileName)
   {
      int dotIndex = -1;

      while ((dotIndex = fileName.indexOf('.', dotIndex + 1)) >= 0)
      {
         final String extension = fileName.substring(dotIndex);
         final String handler = this.getDiagramHandler(extension);
         if (handler != null)
         {
            return handler;
         }
      }

      return null;
   }

   public boolean isGenerateTables()
   {
      return this.generateTables;
   }

   public void setGenerateTables(boolean generateTables)
   {
      this.generateTables = generateTables;
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

   public boolean isDryRun()
   {
      return this.dryRun;
   }

   public void setDryRun(boolean dryRun)
   {
      this.dryRun = dryRun;
   }

   public boolean isMarkerEndColumns()
   {
      return markerEndColumns;
   }

   public void setMarkerEndColumns(boolean markerEndColumns)
   {
      this.markerEndColumns = markerEndColumns;
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

      final Option decoratorClasses = new Option(null, "decorator-classes", true,
                                        "a list of decorator classes, separated by ','.");
      decoratorClasses.setArgs(Option.UNLIMITED_VALUES);
      decoratorClasses.setValueSeparator(',');
      options.addOption(decoratorClasses);

      options.addOption(new Option(null, "tables", false, "generate table classes for the model"));

      options.addOption(new Option(null, "class-diagram", false, "generate class diagram as .png into model folder"));
      options.addOption(
         new Option(null, "class-diagram-svg", false, "generate class diagram as .svg into model folder"));

      options.addOption(new Option(null, "object-diagram", false,
                                   "append a statement to each test that generates an object diagram as .png"));
      options.addOption(new Option(null, "object-diagram-svg", false,
                                   "append a statement to each test that generates an object diagram as .svg"));

      options.addOption(new Option(null, "dry-run", false, "only check the input files and do not run code generator"));

      options.addOption(
         new Option(null, "marker-end-columns", false, "include the column number where a marker ends in the output"));

      options.addOption(Option
                           .builder()
                           .longOpt("diagram-handlers")
                           .argName("<extension>=<method>")
                           .numberOfArgs(2)
                           .valueSeparator()
                           .desc("generate diagrams with the extension using the handler method")
                           .build());

      return options;
   }

   public void readOptions(CommandLine cmd)
   {
      this.setModelDir(cmd.getOptionValue("modelDir", "src/main/java"));
      this.setTestDir(cmd.getOptionValue("testDir", "src/test/java"));
      this.getInputDirs().addAll(cmd.getArgList());
      this.setGenerateTables(cmd.hasOption("tables"));
      this.setClassDiagram(cmd.hasOption("class-diagram"));
      this.setClassDiagramSVG(cmd.hasOption("class-diagram-svg"));
      this.setObjectDiagram(cmd.hasOption("object-diagram"));
      this.setObjectDiagramSVG(cmd.hasOption("object-diagram-svg"));
      this.setDryRun(cmd.hasOption("dry-run"));
      this.setMarkerEndColumns(cmd.hasOption("marker-end-columns"));

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

      final String[] decoratorClasses = cmd.getOptionValues("decorator-classes");
      if (decoratorClasses != null)
      {
         Collections.addAll(this.decoratorClasses, decoratorClasses);
      }

      for (final Map.Entry<Object, Object> entry : cmd.getOptionProperties("diagram-handlers").entrySet())
      {
         this.diagramHandlers.put(entry.getKey().toString(), entry.getValue().toString());
      }
   }
}
