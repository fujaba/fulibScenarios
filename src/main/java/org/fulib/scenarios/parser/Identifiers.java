package org.fulib.scenarios.parser;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;
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

   private static Stream<String> splitCaps(String text)
   {
      return Arrays.stream(text.split("[\\W_]+"));
   }

   private static String joinCaps(Stream<String> stream)
   {
      return stream.map(StrUtil::cap).collect(Collectors.joining());
   }

   // --------------- Parser-Specific ---------------

   static String joinCaps(ScenarioParser.SimpleNameContext simpleName)
   {
      return joinCaps(splitCaps(simpleName.identifier().getText()));
   }

   static String joinCaps(ScenarioParser.NameContext name)
   {
      return joinCaps(name.children.stream().map(ParseTree::getText).flatMap(Identifiers::splitCaps));
   }

   static String varName(ScenarioParser.SimpleNameContext simpleName)
   {
      return simpleName == null ? null : StrUtil.downFirstChar(joinCaps(simpleName));
   }

   static String varName(ScenarioParser.NameContext name)
   {
      return name == null ? null : StrUtil.downFirstChar(joinCaps(name));
   }

   static Name name(ScenarioParser.SimpleNameContext simpleName)
   {
      return simpleName == null ? null : name(varName(simpleName), simpleName);
   }

   static Name name(ScenarioParser.NameContext name)
   {
      return name == null ? null : name(varName(name), name);
   }

   private static Name name(String value, ParserRuleContext rule)
   {
      final String text = ASTListener.inputText(rule);
      final UnresolvedName name = UnresolvedName.of(value, text);
      name.setPosition(ASTListener.position(rule));
      return name;
   }
}
