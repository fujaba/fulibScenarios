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

   @Override
   public void reportAmbiguity(Parser recognizer, DFA dfa, int startIndex, int stopIndex, boolean exact,
      BitSet ambigAlts, ATNConfigSet configs)
   {
      final String sourceName = recognizer.getInputStream().getSourceName();
      final Token startToken = recognizer.getInputStream().get(startIndex);
      final int line = startToken.getLine();
      final int column = startToken.getCharPositionInLine();

      final String[] ruleNames = recognizer.getRuleNames();
      final String altString = ambigAlts.stream().mapToObj(i -> ruleNames[i]).sorted()
                                        .collect(Collectors.joining(", "));

      this.report(sourceName, line, column, "warning", "parser ambiguity with alternatives: " + altString);
   }

   private void report(String file, int line, int column, String type, String msg)
   {
      this.out.println(file + ":" + line + ":" + column + ": " + type + ": " + msg);
   }
}
