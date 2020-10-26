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
      final String[] args = {
         "--diagram-handlers",
         ".gif=GifTool.dump",
         "--diagram-handlers",
         ".html=HtmlTool.dump",
         "--diagram-handlers",
         ".html.gif=HtmlGifTool.dump",
      };
      final Config config = parse(args);

      MatcherAssert.assertThat(config.getDiagramHandler(".gif"), CoreMatchers.equalTo("GifTool.dump"));
      MatcherAssert.assertThat(config.getDiagramHandlerFromFile("example.gif"), CoreMatchers.equalTo("GifTool.dump"));
      MatcherAssert.assertThat(config.getDiagramHandlerFromFile("example.html"), CoreMatchers.equalTo("HtmlTool.dump"));
      MatcherAssert.assertThat(config.getDiagramHandlerFromFile("example.html.gif"),
                               CoreMatchers.equalTo("HtmlGifTool.dump"));
   }
}
