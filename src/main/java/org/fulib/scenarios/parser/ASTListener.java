package org.fulib.scenarios.parser;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.misc.Interval;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.fulib.StrUtil;
import org.fulib.scenarios.ast.*;
import org.fulib.scenarios.ast.expr.Expr;
import org.fulib.scenarios.ast.expr.access.AttributeAccess;
import org.fulib.scenarios.ast.expr.access.ExampleAccess;
import org.fulib.scenarios.ast.expr.primary.NameAccess;
import org.fulib.scenarios.ast.expr.primary.NumberLiteral;
import org.fulib.scenarios.ast.expr.primary.StringLiteral;
import org.fulib.scenarios.ast.sentence.ThereSentence;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.stream.Collectors;

public class ASTListener extends ScenarioParserBaseListener
{
   // =============== Fields ===============

   private final Scenario scenario = Scenario.of(null, new ArrayList<>());

   private Deque<Node> stack = new ArrayDeque<>();

   // =============== Properties ===============

   public Scenario getScenario()
   {
      return this.scenario;
   }

   // =============== Methods ===============

   // --------------- Scenario ---------------

   @Override
   public void exitScenarioName(ScenarioParser.ScenarioNameContext ctx)
   {
      this.scenario.setName(inputText(ctx));
   }

   // --------------- Sentences ---------------

   @Override
   public void exitThereSentence(ScenarioParser.ThereSentenceContext ctx)
   {
      final List<Descriptor> descriptors = this.stack.stream().map(it -> (Descriptor) it)
                                                     .collect(Collectors.toList());
      this.stack.clear();
      this.scenario.getSentences().add(ThereSentence.of(descriptors));
   }

   // --------------- Phrases ---------------

   @Override
   public void exitDescriptor(ScenarioParser.DescriptorContext ctx)
   {
      final Name name = name(ctx.name());
      final Constructor ctor = (Constructor) this.stack.pop();
      this.stack.push(Descriptor.of(name, ctor));
   }

   @Override
   public void exitConstructor(ScenarioParser.ConstructorContext ctx)
   {
      final Name className = name(ctx.typeClause().name());
      final List<NamedExpr> parameters = this.stack.stream().map(it -> (NamedExpr) it).collect(Collectors.toList());
      this.stack.clear();
      this.stack.push(Constructor.of(className, parameters));
   }

   @Override
   public void exitSimpleWithClause(ScenarioParser.SimpleWithClauseContext ctx)
   {
      final Name name = name(ctx.simpleName());
      final Expr expr = (Expr) this.stack.pop();
      this.stack.push(NamedExpr.of(name, expr));
   }

   @Override
   public void exitNumberWithClause(ScenarioParser.NumberWithClauseContext ctx)
   {
      final Name name = name(ctx.name());
      final Expr expr = (Expr) this.stack.pop();
      this.stack.push(NamedExpr.of(name, expr));
   }

   // --------------- Expressions ---------------

   @Override
   public void exitNumber(ScenarioParser.NumberContext ctx)
   {
      final String text = ctx.NUMBER().getText();
      this.stack.push(NumberLiteral.of(Double.parseDouble(text)));
   }

   @Override
   public void exitStringLiteral(ScenarioParser.StringLiteralContext ctx)
   {
      final String text = ctx.STRING_LITERAL().getText();
      // TODO escape character processing
      this.stack.push(StringLiteral.of(text));
   }

   // TODO <it>

   @Override
   public void exitNameAccess(ScenarioParser.NameAccessContext ctx)
   {
      this.stack.push(NameAccess.of(name(ctx.name())));
   }

   @Override
   public void exitAttributeAccess(ScenarioParser.AttributeAccessContext ctx)
   {
      final Name name = name(ctx.name());
      final Expr receiver = (Expr) this.stack.pop();
      this.stack.push(AttributeAccess.of(name, receiver));
   }

   @Override
   public void exitExampleAccess(ScenarioParser.ExampleAccessContext ctx)
   {
      final Expr value = (Expr) this.stack.pop();
      final Expr expr = (Expr) this.stack.pop();
      this.stack.push(ExampleAccess.of(value, expr));
   }

   // =============== Static Methods ===============

   static String cap(Token token)
   {
      return StrUtil.cap(token.getText());
   }

   static String joinCaps(ScenarioParser.NameContext context)
   {
      return context.WORD().stream().map(TerminalNode::getText).map(StrUtil::cap).collect(Collectors.joining(""));
   }

   static Name name(ScenarioParser.SimpleNameContext simpleName)
   {
      if (simpleName == null)
      {
         return null;
      }

      final String value = StrUtil.downFirstChar(simpleName.WORD().getText());
      final String text = inputText(simpleName);
      return Name.of(value, text);
   }

   static Name name(ScenarioParser.NameContext multiName)
   {
      if (multiName == null)
      {
         return null;
      }

      final String value = StrUtil.downFirstChar(joinCaps(multiName));
      final String text = inputText(multiName);
      return Name.of(value, text);
   }

   static String inputText(ParserRuleContext ctx)
   {
      // TODO may include comments?
      final CharStream inputStream = ctx.getStart().getInputStream();
      final Interval interval = Interval.of(ctx.getStart().getStartIndex(), ctx.getStop().getStopIndex());
      return inputStream.getText(interval);
   }

   static void report(Token position, String message)
   {
      final String sourceName = position.getInputStream().getSourceName();
      final int line = position.getLine();
      // + 1 because most editors start counting columns at 1, but ANTLR apparently doesn't.
      final int column = position.getCharPositionInLine() + 1;
      System.err.println(sourceName + ":" + line + ":" + column + ": error: " + message);
   }
}
