package org.fulib.scenarios.tool;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;

import java.io.PrintWriter;

public class ErrorListener extends BaseErrorListener
{
   private final PrintWriter out;

   public ErrorListener(PrintWriter out)
   {
      this.out = out;
   }

   @Override
   public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine,
      String msg, RecognitionException e)
   {
      final String sourceName = recognizer.getInputStream().getSourceName();
      this.out.println(sourceName + ":" + line + ":" + charPositionInLine + ": syntax: " + msg);
   }
}
