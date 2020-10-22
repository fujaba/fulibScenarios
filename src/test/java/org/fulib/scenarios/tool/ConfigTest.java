package org.fulib.scenarios.tool;

import org.apache.commons.cli.*;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

public class ConfigTest
{
   private static Config parse(String... args) throws ParseException
   {
      final Config config = new Config();
      final Options options = config.createOptions();

      final CommandLineParser parser = new DefaultParser();
      final CommandLine commandLine = parser.parse(options, args);

      config.readOptions(commandLine);

      return config;
   }

   @Test
   public void diagramHandlers() throws ParseException
   {
      final Config config = parse("--diagram-handlers", ".gif=GifTool.dump", "--diagram-handlers", ".html=HtmlTool.dump");

      MatcherAssert.assertThat(config.getDiagramHandler(".gif"), CoreMatchers.equalTo("GifTool.dump"));
   }
}
