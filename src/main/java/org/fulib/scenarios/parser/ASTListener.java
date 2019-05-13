package org.fulib.scenarios.parser;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.misc.Interval;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.fulib.StrUtil;
import org.fulib.scenarios.ast.NamedExpr;
import org.fulib.scenarios.ast.Node;
import org.fulib.scenarios.ast.Scenario;
import org.fulib.scenarios.ast.decl.Name;
import org.fulib.scenarios.ast.decl.UnresolvedName;
import org.fulib.scenarios.ast.decl.VarDecl;
import org.fulib.scenarios.ast.expr.Expr;
import org.fulib.scenarios.ast.expr.access.AttributeAccess;
import org.fulib.scenarios.ast.expr.access.ExampleAccess;
import org.fulib.scenarios.ast.expr.call.CreationExpr;
import org.fulib.scenarios.ast.expr.collection.ListExpr;
import org.fulib.scenarios.ast.expr.conditional.AttributeCheckExpr;
import org.fulib.scenarios.ast.expr.conditional.ConditionalExpr;
import org.fulib.scenarios.ast.expr.primary.NameAccess;
import org.fulib.scenarios.ast.expr.primary.NumberLiteral;
import org.fulib.scenarios.ast.expr.primary.StringLiteral;
import org.fulib.scenarios.ast.sentence.*;

import java.util.*;
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

   private <T> T pop()
   {
      return (T) this.stack.pop();
   }

   private <T> T popLast()
   {
      return (T) this.stack.removeLast();
   }

   private <T> List<T> pop(Class<T> type, int count)
   {
      // strange logic, but end result is that the top of the stack ends up at the end of the list.
      final List<T> result = new ArrayList<>(Collections.nCopies(count, null));
      for (int i = count - 1; i >= 0; i--)
      {
         result.set(i, type.cast(this.stack.pop()));
      }
      return result;
   }

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
      final List<VarDecl> vars = this.pop(VarDecl.class, ctx.descriptor().size());
      this.scenario.getSentences().add(ThereSentence.of(vars));
   }

   @Override
   public void exitExpectSentence(ScenarioParser.ExpectSentenceContext ctx)
   {
      final List<ConditionalExpr> exprs = this.pop(ConditionalExpr.class, ctx.thatClauses().thatClause().size());
      this.scenario.getSentences().add(ExpectSentence.of(exprs));
   }

   @Override
   public void exitDiagramSentence(ScenarioParser.DiagramSentenceContext ctx)
   {
      final Expr object = this.pop();
      final String fileName = ctx.fileName.getText();
      this.scenario.getSentences().add(DiagramSentence.of(object, fileName));
   }

   @Override
   public void exitHasSentence(ScenarioParser.HasSentenceContext ctx)
   {
      final Expr object = this.popLast();
      final List<NamedExpr> clauses = this.pop(NamedExpr.class, ctx.hasClauses().hasClause().size());
      this.scenario.getSentences().add(HasSentence.of(object, clauses));
   }

   @Override
   public void exitIsSentence(ScenarioParser.IsSentenceContext ctx)
   {
      final String name = varName(ctx.name());
      final Expr ctor = this.pop();
      final VarDecl varDecl = VarDecl.of(name, null, ctor);
      this.scenario.getSentences().add(IsSentence.of(varDecl));
   }

   // --------------- Phrases ---------------

   @Override
   public void exitDescriptor(ScenarioParser.DescriptorContext ctx)
   {
      final String name = varName(ctx.name());
      final CreationExpr ctor = this.pop();
      this.stack.push(VarDecl.of(name, null, ctor));
   }

   @Override
   public void exitConstructor(ScenarioParser.ConstructorContext ctx)
   {
      final Name className = name(ctx.typeClause().name());
      final ScenarioParser.WithClausesContext withClauses = ctx.withClauses();
      final List<NamedExpr> parameters = this.pop(NamedExpr.class,
                                                  withClauses != null ? withClauses.withClause().size() : 0);
      this.stack.push(CreationExpr.of(className, parameters));
   }

   // --------------- Clauses ---------------

   @Override
   public void exitNamedSimple(ScenarioParser.NamedSimpleContext ctx)
   {
      final Name name = name(ctx.simpleName());
      final Expr expr = this.pop();
      this.stack.push(NamedExpr.of(name, expr));
   }

   @Override
   public void exitNamedNumber(ScenarioParser.NamedNumberContext ctx)
   {
      final Name name = name(ctx.name());
      final Expr expr = this.pop();
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
      // strip opening and closing quotes
      final String value = text.substring(1, text.length() - 1);
      this.stack.push(StringLiteral.of(value));
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
      final Expr receiver = this.pop();
      this.stack.push(AttributeAccess.of(name, receiver));
   }

   @Override
   public void exitExampleAccess(ScenarioParser.ExampleAccessContext ctx)
   {
      final Expr value = this.pop();
      final Expr expr = this.pop();
      this.stack.push(ExampleAccess.of(value, expr));
   }

   @Override
   public void exitAttrCheck(ScenarioParser.AttrCheckContext ctx)
   {
      final NamedExpr valueAndAttribute = this.pop();
      final Expr value = valueAndAttribute.getExpr();
      final Name attribute = valueAndAttribute.getName();
      final Expr receiver = this.pop();
      this.stack.push(AttributeCheckExpr.of(receiver, attribute, value));
   }

   @Override
   public void exitList(ScenarioParser.ListContext ctx)
   {
      final List<Expr> elements = this.pop(Expr.class, ctx.listElem().size());
      this.stack.push(ListExpr.of(elements));
   }

   @Override
   public void exitPrimaryList(ScenarioParser.PrimaryListContext ctx)
   {
      final List<Expr> elements = this.pop(Expr.class, ctx.primaryListElem().size());
      this.stack.push(ListExpr.of(elements));
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

   static String varName(ScenarioParser.NameContext context)
   {
      return context == null ? null : StrUtil.downFirstChar(joinCaps(context));
   }

   static String varName(ScenarioParser.SimpleNameContext context)
   {
      return context == null ? null : StrUtil.downFirstChar(context.WORD().getText());
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
      final String text = inputText(rule);
      return UnresolvedName.of(value, text);
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
