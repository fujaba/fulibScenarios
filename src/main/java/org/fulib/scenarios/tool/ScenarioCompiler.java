package org.fulib.scenarios.tool;

import org.antlr.v4.runtime.ANTLRErrorListener;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.apache.commons.cli.*;
import org.fulib.scenarios.ast.CompilationContext;
import org.fulib.scenarios.ast.ScenarioFile;
import org.fulib.scenarios.ast.ScenarioGroup;
import org.fulib.scenarios.diagnostic.Marker;
import org.fulib.scenarios.library.LibraryHelper;
import org.fulib.scenarios.parser.ASTListener;
import org.fulib.scenarios.parser.ScenarioLexer;
import org.fulib.scenarios.parser.ScenarioParser;
import org.fulib.scenarios.visitor.codegen.CodeGenerator;
import org.fulib.scenarios.visitor.preprocess.Grouper;
import org.fulib.scenarios.visitor.resolve.DeclResolver;
import org.fulib.scenarios.visitor.resolve.NameResolver;

import javax.lang.model.SourceVersion;
import javax.tools.Tool;
import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ScenarioCompiler implements Tool
{

   private static final String TOOL_NAME = "scenarioc";

   private static final CompilationContext.Visitor[] PHASES = { //
      Grouper.INSTANCE, NameResolver.INSTANCE, CodeGenerator.INSTANCE,
      //
   };

   // =============== Fields ===============

   private PrintWriter out;
   private PrintWriter err;

   private Config             config  = new Config();
   private CompilationContext context = CompilationContext
                                           .of(this.config, new ConcurrentHashMap<>(), new ArrayList<>());

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

   public Config getConfig()
   {
      return this.config;
   }

   public CompilationContext getContext()
   {
      return this.context;
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
      LibraryHelper.loadLibraries(this);
      this.discover();

      int errors = this.errors;
      int warnings = 0;

      for (CompilationContext.Visitor phase : PHASES)
      {
         try
         {
            //noinspection unchecked
            this.context.accept(phase, null);
         }
         catch (Exception ex)
         {
            final String phaseName = phase.getClass().getSimpleName();
            this.err.println("failed to execute phase '" + phaseName + "' due to exception:");
            ex.printStackTrace(this.err);
            errors++;
         }
      }

      for (final ScenarioGroup group : this.context.getGroups().values())
      {
         for (final ScenarioFile file : group.getFiles().values())
         {
            file.getMarkers().sort(null);

            for (final Marker marker : file.getMarkers())
            {
               try
               {
                  marker.appendTo(this.out, this.config);
               }
               catch (IOException ignored)
               {
               }
               switch (marker.getKind())
               {
               case ERROR:
                  errors++;
                  break;
               case WARNING:
               case MANDATORY_WARNING:
                  warnings++;
                  break;
               }
            }
         }
      }

      if (errors != 0 && warnings != 0)
      {
         final String key = "markers." + (warnings == 1 ? "1.warning" : "n.warnings") //
                            + "." + (errors == 1 ? "1.error" : "n.errors");
         this.getOut().println(Marker.localize(key, warnings, errors));
      }
      else if (errors != 0)
      {
         final String key = errors == 1 ? "markers.1.error" : "markers.n.errors";
         this.getOut().println(Marker.localize(key, errors));
      }
      else if (warnings != 0)
      {
         final String key = warnings == 1 ? "markers.1.warning" : "markers.n.warnings";
         this.getOut().println(Marker.localize(key, warnings));
      }

      return this.errors = errors;
   }

   private void discover()
   {
      for (String inputDirName : this.config.getInputDirs())
      {
         final File inputDir = new File(inputDirName);
         if (inputDir.exists() && inputDir.isDirectory())
         {
            this.discover(inputDir, inputDir, "");
         }
      }
   }

   private void discover(File sourceDir, File dir, String packageDir)
   {
      final List<ScenarioFile> scenarioFiles = new ArrayList<>();

      for (File file : Objects.requireNonNull(dir.listFiles()))
      {
         final String fileName = file.getName();
         if (file.isDirectory())
         {
            final String newPackage = packageDir.isEmpty() ? fileName : packageDir + "/" + fileName;
            this.discover(sourceDir, file, newPackage);
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
               final String name = fileName.substring(0, fileName.length() - 3);
               scenarioFile.setName(name);
               scenarioFiles.add(scenarioFile);
            }
         }
      }

      if (!scenarioFiles.isEmpty())
      {
         final ScenarioGroup scenarioGroup = DeclResolver.resolveGroup(this.getContext(), packageDir);
         scenarioGroup.setSourceDir(sourceDir.toString());

         for (final ScenarioFile scenarioFile : scenarioFiles)
         {
            scenarioFile.setGroup(scenarioGroup);
            scenarioGroup.getFiles().put(scenarioFile.getName(), scenarioFile);
         }
      }
   }

   protected ScenarioFile parseScenario(File file)
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

      return this.parseScenario(input);
   }

   protected ScenarioFile parseScenario(CharStream input)
   {
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
         return null;
      }

      try
      {
         final ASTListener listener = new ASTListener();
         ParseTreeWalker.DEFAULT.walk(listener, context);
         return listener.getFile();
      }
      catch (Exception e)
      {
         e.printStackTrace(this.getErr());
         return null;
      }
   }
}
