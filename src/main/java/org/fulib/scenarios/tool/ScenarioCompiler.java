package org.fulib.scenarios.tool;

import org.antlr.v4.runtime.ANTLRErrorListener;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.apache.commons.cli.*;
import org.fulib.scenarios.ast.ScenarioFile;
import org.fulib.scenarios.ast.ScenarioGroup;
import org.fulib.scenarios.codegen.CodeGenerator;
import org.fulib.scenarios.parser.ASTListener;
import org.fulib.scenarios.parser.ScenarioLexer;
import org.fulib.scenarios.parser.ScenarioParser;
import org.fulib.scenarios.transform.Desugar;
import org.fulib.scenarios.transform.Grouper;
import org.fulib.scenarios.transform.NameResolver;

import javax.lang.model.SourceVersion;
import javax.tools.Tool;
import java.io.*;
import java.util.*;

public class ScenarioCompiler implements Tool
{
   // =============== Constants ===============

   private static final String TOOL_NAME = "scenarioc";

   // =============== Fields ===============

   private PrintWriter out;
   private PrintWriter err;

   private Config config = new Config();

   private List<ScenarioGroup> groups = new ArrayList<>();

   private int errors;

   // =============== Properties ===============

   public PrintWriter getOut()
   {
      return this.out;
   }

   public void setOut(PrintWriter out)
   {
      this.out = out;
   }

   public PrintWriter getErr()
   {
      return this.err;
   }

   public void setErr(PrintWriter err)
   {
      this.err = err;
   }

   @Override
   public Set<SourceVersion> getSourceVersions()
   {
      return Collections.singleton(SourceVersion.RELEASE_8);
   }

   // =============== Methods ===============

   @Override
   public int run(InputStream in, OutputStream out, OutputStream err, String... arguments)
   {
      this.setOut(new PrintWriter(out));
      this.setErr(new PrintWriter(err));

      try
      {
         return this.run(arguments);
      }
      finally
      {
         this.getOut().flush();
         this.getErr().flush();
      }
   }

   public int run(String[] arguments)
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
         this.getErr().println(e.getMessage());
         formatter
            .printHelp(this.getErr(), formatter.getWidth(), TOOL_NAME, null, options, formatter.getLeftPadding(),
                       formatter.getDescPadding(), null);

         return -1;
      }

      this.config.readOptions(cmd);
      return this.run();
   }

   public int run()
   {
      this.discover();
      this.groups.forEach(this::processGroup);
      return this.errors;
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
      List<ScenarioFile> scenarioFiles = new ArrayList<>();

      for (File file : Objects.requireNonNull(srcDir.listFiles()))
      {
         final String fileName = file.getName();
         if (file.isDirectory())
         {
            final String newPackage = packageName.isEmpty() ? fileName : packageName + "." + fileName;
            this.discover(file, newPackage);
         }
         else if ("Register.md".equals(fileName))
         {
            // TODO handle Register.md
         }
         else if (fileName.endsWith(".md"))
         {
            final ScenarioFile scenarioFile = this.parseScenario(file);
            if (scenarioFile != null)
            {
               scenarioFile.setName(fileName.substring(0, fileName.length() - 3));
               scenarioFiles.add(scenarioFile);
            }
         }
      }

      if (!scenarioFiles.isEmpty())
      {
         this.groups.add(ScenarioGroup.of(packageName, scenarioFiles));
      }
   }

   private ScenarioFile parseScenario(File file)
   {
      final CharStream input;
      try
      {
         input = CharStreams.fromPath(file.toPath());
      }
      catch (IOException e)
      {
         this.getErr().println("failed to read scenario file " + file);
         e.printStackTrace(this.getErr());
         return null;
      }

      final ANTLRErrorListener errorListener = new ErrorListener(this.getOut());

      final ScenarioLexer lexer = new ScenarioLexer(input);
      lexer.removeErrorListeners();
      lexer.addErrorListener(errorListener);

      final ScenarioParser parser = new ScenarioParser(new CommonTokenStream(lexer));
      parser.removeErrorListeners();
      parser.addErrorListener(errorListener);

      final ScenarioParser.FileContext context = parser.file();
      final int syntaxErrors = parser.getNumberOfSyntaxErrors();

      if (syntaxErrors > 0)
      {
         this.errors += syntaxErrors;
         this.getErr().println(syntaxErrors + " syntax errors in scenario " + file);
         return null;
      }

      try
      {
         final ASTListener listener = new ASTListener();
         ParseTreeWalker.DEFAULT.walk(listener, context);

         this.getErr().println("read scenario " + file);
         return listener.getFile();
      }
      catch (Exception e)
      {
         this.getErr().println("failed to transform scenario " + file);
         e.printStackTrace(this.getErr());
         return null;
      }
   }

   private void processGroup(ScenarioGroup scenarioGroup)
   {
      scenarioGroup.accept(Grouper.INSTANCE, null);
      scenarioGroup.accept(new Desugar(), null);
      scenarioGroup.accept(NameResolver.INSTANCE, null);
      scenarioGroup.accept(new CodeGenerator(this.config), null);
   }
}
