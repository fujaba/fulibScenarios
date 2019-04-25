package org.fulib.scenarios;

import org.apache.commons.cli.*;
import org.fulib.scenarios.config.Config;

import javax.lang.model.SourceVersion;
import javax.tools.Tool;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collections;
import java.util.Set;

public class ScenarioCompiler implements Tool
{
   // =============== Constants ===============

   private static final String TOOL_NAME = "scenarioc";

   // =============== Fields ===============

   private Config config = new Config();

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
      return 0;
   }
}
