package org.fulib.scenarios.tool;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.ATNConfigSet;
import org.antlr.v4.runtime.dfa.DFA;

import java.io.PrintWriter;
import java.util.BitSet;
import java.util.stream.Collectors;

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
      this.report(sourceName, line, charPositionInLine, "syntax", msg);
   }

   private void report(String file, int line, int column, String type, String msg)
   {
      this.out.println(file + ":" + line + ":" + column + ": " + type + ": " + msg);
   }
}
