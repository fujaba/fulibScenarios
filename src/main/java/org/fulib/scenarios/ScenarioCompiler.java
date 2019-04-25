package org.fulib.scenarios;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.apache.commons.cli.*;
import org.fulib.scenarios.ast.Package;
import org.fulib.scenarios.ast.Scenario;
import org.fulib.scenarios.config.Config;
import org.fulib.scenarios.parser.ASTListener;
import org.fulib.scenarios.parser.ScenarioLexer;
import org.fulib.scenarios.parser.ScenarioParser;

import javax.lang.model.SourceVersion;
import javax.tools.Tool;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;

public class ScenarioCompiler implements Tool
{
   // =============== Constants ===============

   private static final String TOOL_NAME = "scenarioc";

   // =============== Fields ===============

   private Config config = new Config();

   private List<Package> packages = new ArrayList<>();

   // =============== Properties ===============

   @Override
   public Set<SourceVersion> getSourceVersions()
   {
      return Collections.singleton(SourceVersion.RELEASE_8);
   }

   // =============== Methods ===============

   @Override
   public int run(InputStream in, OutputStream out, OutputStream err, String... arguments)
   {
      if (!this.readArguments(arguments))
      {
         return 1;
      }

      return this.run();
   }

   boolean readArguments(String[] arguments)
   {
      Options options = this.config.createOptions();

      CommandLineParser parser = new DefaultParser();
      HelpFormatter formatter = new HelpFormatter();
      CommandLine cmd;

      try
      {
         cmd = parser.parse(options, arguments);
      }
      catch (ParseException e)
      {
         System.out.println(e.getMessage());
         formatter.printHelp(TOOL_NAME, options);

         return false;
      }

      this.config.readOptions(cmd);
      return true;
   }

   int run()
   {
      this.discover();
      return 0;
   }

   private void discover()
   {
      for (String inputDirName : this.config.getInputDirs())
      {
         final File inputDir = new File(inputDirName);
         if (inputDir.exists() && inputDir.isDirectory())
         {
            this.discover(inputDir, "");
         }
      }
   }

   private void discover(File srcDir, String packageName)
   {
      List<Scenario> scenarios = new ArrayList<>();

      for (File file : Objects.requireNonNull(srcDir.listFiles()))
      {
         if (file.isDirectory())
         {
            final String newPackage = packageName.isEmpty() ? file.getName() : packageName + "." + file.getName();
            this.discover(file, newPackage);
         }
         else if ("Register.md".equals(file.getName()))
         {
            // TODO handle Register.md
         }
         else if (file.getName().endsWith(".md"))
         {
            final Scenario scenario = this.parseScenario(file);
            if (scenario != null)
            {
               scenarios.add(scenario);
            }
         }
      }

      if (!scenarios.isEmpty())
      {
         this.packages.add(Package.of(packageName, scenarios));
      }
   }

   private Scenario parseScenario(File file)
   {
      // TODO use tool out and err streams

      final CharStream input;
      try {
         input = CharStreams.fromPath(file.toPath());
      }
      catch (IOException e)
      {
         System.err.println("failed to read scenario file " + file);
         e.printStackTrace();
         return null;
      }

      final ScenarioLexer lexer = new ScenarioLexer(input);
      // TODO custom error reporting
      final ScenarioParser parser = new ScenarioParser(new CommonTokenStream(lexer));
      final ScenarioParser.ScenarioContext context = parser.scenario();
      final int syntaxErrors = parser.getNumberOfSyntaxErrors();

      if (syntaxErrors > 0)
      {
         System.err.println(syntaxErrors + " syntax errors in scenario " + file);
         return null;
      }

      try
      {
         final ASTListener listener = new ASTListener();
         ParseTreeWalker.DEFAULT.walk(listener, context);

         System.out.println("read scenario " + file);
         return listener.getScenario();
      }
      catch (Exception e)
      {
         System.err.println("failed to transform scenario " + file);
         e.printStackTrace();
         return null;
      }
   }
}
