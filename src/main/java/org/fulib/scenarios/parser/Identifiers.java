package org.fulib.scenarios.parser;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.fulib.StrUtil;
import org.fulib.scenarios.ast.decl.Name;
import org.fulib.scenarios.ast.decl.UnresolvedName;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Identifiers
{
   // =============== Static Methods ===============

   public static String toUpperCamelCase(String text)
   {
      return joinCaps(splitCaps(text));
   }

   public static String toLowerCamelCase(String text)
   {
      return StrUtil.downFirstChar(toUpperCamelCase(text));
   }

   public static Stream<String> splitCaps(String text)
   {
      return Arrays.stream(text.split("[\\W_]+"));
   }

   public static String joinCaps(Stream<String> stream)
   {
      return stream.map(StrUtil::cap).collect(Collectors.joining());
   }

   // --------------- Parser-Specific ---------------

   static String cap(Token token)
   {
      return StrUtil.cap(token.getText());
   }

   static Stream<String> splitCaps(TerminalNode token)
   {
      return splitCaps(token.getText());
   }

   static String joinCaps(ScenarioParser.SimpleNameContext context)
   {
      return joinCaps(splitCaps(context.WORD()));
   }

   static String joinCaps(ScenarioParser.NameContext context)
   {
      return joinCaps(context.WORD().stream().flatMap(Identifiers::splitCaps));
   }

   static String varName(ScenarioParser.NameContext context)
   {
      return context == null ? null : StrUtil.downFirstChar(joinCaps(context));
   }

   static String varName(ScenarioParser.SimpleNameContext context)
   {
      return context == null ? null : StrUtil.downFirstChar(joinCaps(context));
   }

   static Name name(ScenarioParser.SimpleNameContext simpleName)
   {
      return simpleName == null ? null : name(varName(simpleName), simpleName);
   }

   static Name name(ScenarioParser.NameContext multiName)
   {
      return multiName == null ? null : name(varName(multiName), multiName);
   }

   private static Name name(String value, ParserRuleContext rule)
   {
      final String text = ASTListener.inputText(rule);
      final UnresolvedName name = UnresolvedName.of(value, text);
      name.setPosition(ASTListener.position(rule));
      return name;
   }
}
