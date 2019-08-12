package org.fulib.scenarios;

import org.fulib.scenarios.tool.ScenarioCompiler;

public class Main
{
   public static void main(String[] args)
   {
      System.exit(new ScenarioCompiler().run(System.in, System.out, System.err, args));
   }
}
