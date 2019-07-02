package org.fulib.scenarios.parser;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.misc.Interval;
import org.apache.commons.text.StringEscapeUtils;
import org.fulib.scenarios.ast.*;
import org.fulib.scenarios.ast.decl.Name;
import org.fulib.scenarios.ast.decl.VarDecl;
import org.fulib.scenarios.ast.expr.Expr;
import org.fulib.scenarios.ast.expr.access.AttributeAccess;
import org.fulib.scenarios.ast.expr.access.ExampleAccess;
import org.fulib.scenarios.ast.expr.call.CallExpr;
import org.fulib.scenarios.ast.expr.call.CreationExpr;
import org.fulib.scenarios.ast.expr.collection.ListExpr;
import org.fulib.scenarios.ast.expr.conditional.AttributeCheckExpr;
import org.fulib.scenarios.ast.expr.conditional.ConditionalExpr;
import org.fulib.scenarios.ast.expr.conditional.ConditionalOperator;
import org.fulib.scenarios.ast.expr.conditional.ConditionalOperatorExpr;
import org.fulib.scenarios.ast.expr.primary.NameAccess;
import org.fulib.scenarios.ast.expr.primary.NumberLiteral;
import org.fulib.scenarios.ast.expr.primary.StringLiteral;
import org.fulib.scenarios.ast.sentence.*;
import org.fulib.scenarios.ast.type.Type;
import org.fulib.scenarios.ast.type.UnresolvedType;

import java.util.*;
import java.util.stream.Collectors;

import static org.fulib.scenarios.parser.Identifiers.*;

public class ASTListener extends ScenarioParserBaseListener
{
   // =============== Fields ===============

   private ScenarioFile file;

   private Deque<Node> stack = new ArrayDeque<>();

   // =============== Properties ===============

   public ScenarioFile getFile()
   {
      return this.file;
   }

   // =============== Methods ===============

   private <T> T pop()
   {
      return (T) this.stack.pop();
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
   public void exitFile(ScenarioParser.FileContext ctx)
   {
      final List<Scenario> scenarios = this.pop(Scenario.class, ctx.scenario().size());
      final LinkedHashMap<String, Scenario> scenarioMap = new LinkedHashMap<>();
      this.file = ScenarioFile.of(null, null, scenarioMap, null);

      for (final Scenario scenario : scenarios)
      {
         scenarioMap.put(scenario.getName(), scenario);
         scenario.setFile(this.file);
      }
   }

   @Override
   public void exitScenario(ScenarioParser.ScenarioContext ctx)
   {
      final String name = ctx.header().HEADLINE_TEXT().getText().trim();
      final List<Sentence> sentences = this.pop(Sentence.class, ctx.sentence().size());
      final SentenceList body = SentenceList.of(sentences);
      final Scenario scenario = Scenario.of(null, name, body, null);
      this.stack.push(scenario);
   }

   // --------------- Sentences ---------------

   @Override
   public void exitSectionSentence(ScenarioParser.SectionSentenceContext ctx)
   {
      final String text = ctx.HEADLINE_TEXT().getText().trim();
      final SectionSentence sectionSentence = SectionSentence.of(text);
      this.stack.push(sectionSentence);
   }

   @Override
   public void exitThereSentence(ScenarioParser.ThereSentenceContext ctx)
   {
      final List<MultiDescriptor> descriptors = this.pop(MultiDescriptor.class, ctx.thereClause().size());
      this.stack.push(ThereSentence.of(descriptors));
   }

   private List<NamedExpr> popAttributes(ScenarioParser.WithClausesContext withClauses)
   {
      final int numAttributes = withClauses != null ? withClauses.withClause().size() : 0;
      return this.pop(NamedExpr.class, numAttributes);
   }

   private void pushDescriptor(List<String> names, ScenarioParser.WithClausesContext withClausesContext)
   {
      final List<NamedExpr> attributes = this.popAttributes(withClausesContext);
      final Type type = this.pop();
      final MultiDescriptor multiDescriptor = MultiDescriptor.of(type, names, attributes);

      this.stack.push(multiDescriptor);
   }

   @Override
   public void exitSimpleDescriptor(ScenarioParser.SimpleDescriptorContext ctx)
   {
      final String name = varName(ctx.name());
      final List<String> names = name == null ? Collections.emptyList() : Collections.singletonList(name);
      this.pushDescriptor(names, ctx.withClauses());
   }

   @Override
   public void exitMultiDescriptor(ScenarioParser.MultiDescriptorContext ctx)
   {
      final List<String> names = ctx.name().stream().map(Identifiers::varName).collect(Collectors.toList());
      this.pushDescriptor(names, ctx.withClauses());
   }

   @Override
   public void exitExpectSentence(ScenarioParser.ExpectSentenceContext ctx)
   {
      final List<ConditionalExpr> exprs = this.pop(ConditionalExpr.class, ctx.thatClauses().thatClause().size());
      this.stack.push(ExpectSentence.of(exprs));
   }

   @Override
   public void exitDiagramSentence(ScenarioParser.DiagramSentenceContext ctx)
   {
      final Expr object = this.pop();
      final String fileName = ctx.fileName.getText();
      this.stack.push(DiagramSentence.of(object, fileName));
   }

   @Override
   public void exitHasSentence(ScenarioParser.HasSentenceContext ctx)
   {
      final List<NamedExpr> clauses = this.pop(NamedExpr.class, ctx.hasClauses().hasClause().size());
      final Expr object = this.pop();
      this.stack.push(HasSentence.of(object, clauses));
   }

   @Override
   public void exitIsSentence(ScenarioParser.IsSentenceContext ctx)
   {
      final List<NamedExpr> attributes = this.popAttributes(ctx.withClauses());
      final Type type = this.pop();

      final Expr ctor = CreationExpr.of(type, attributes);

      final String name = varName(ctx.name());
      final VarDecl varDecl = VarDecl.of(name, null, ctor);
      this.stack.push(IsSentence.of(varDecl));
   }

   @Override
   public void exitCreateSentence(ScenarioParser.CreateSentenceContext ctx)
   {
      final Name actor = name(ctx.actor().name()); // null if actor is "we"
      final MultiDescriptor desc = this.pop();
      final CreateSentence createSentence = CreateSentence.of(actor, desc);
      this.stack.push(createSentence);
   }

   @Override
   public void exitCallSentence(ScenarioParser.CallSentenceContext ctx)
   {
      final Name actor = name(ctx.actor().name()); // null if actor is "we"
      final Name name = name(ctx.name());
      final List<NamedExpr> args = this.pop(NamedExpr.class,
                                            ctx.withClauses() != null ? ctx.withClauses().withClause().size() : 0);
      final Expr receiver = ctx.ON() != null ? this.pop() : null;
      final SentenceList body = SentenceList.of(new ArrayList<>());
      final CallExpr callExpr = CallExpr.of(name, receiver, args, body);
      final CallSentence callSentence = CallSentence.of(actor, callExpr);
      this.stack.push(callSentence);
   }

   @Override
   public void exitAnswerSentence(ScenarioParser.AnswerSentenceContext ctx)
   {
      final Name actor = name(ctx.actor().name()); // null if actor is "we"
      final Expr result = this.pop();
      final String varName = varName(ctx.name());
      final AnswerSentence answerSentence = AnswerSentence.of(actor, result, varName);
      this.stack.push(answerSentence);
   }

   @Override
   public void exitWriteSentence(ScenarioParser.WriteSentenceContext ctx)
   {
      final Expr target = this.pop();
      final Expr source = this.pop();
      final Name actor = name(ctx.actor().name()); // null if actor is "we"
      final WriteSentence writeSentence = WriteSentence.of(actor, source, target);
      this.stack.push(writeSentence);
   }

   @Override
   public void exitAddSentence(ScenarioParser.AddSentenceContext ctx)
   {
      final Expr target = this.pop();
      final Expr source = this.pop();
      final Name actor = name(ctx.actor().name()); // null if actor is "we"
      final AddSentence addSentence = AddSentence.of(actor, source, target);
      this.stack.push(addSentence);
   }

   @Override
   public void exitRemoveSentence(ScenarioParser.RemoveSentenceContext ctx)
   {
      final Expr target = this.pop();
      final Expr source = this.pop();
      final Name actor = name(ctx.actor().name()); // null if actor is "we"
      final RemoveSentence removeSentence = RemoveSentence.of(actor, source, target);
      this.stack.push(removeSentence);
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

   // --------------- Types ---------------

   @Override
   public void exitSimpleTypeClause(ScenarioParser.SimpleTypeClauseContext ctx)
   {
      this.stack.push(UnresolvedType.of(typeNameValue(ctx)));
   }

   @Override
   public void exitMultiTypeClause(ScenarioParser.MultiTypeClauseContext ctx)
   {
      this.stack.push(UnresolvedType.of(typeNameValue(ctx)));
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
      // strip opening and closing quotes
      final String stripped = text.substring(1, text.length() - 1);
      final String value = StringEscapeUtils.unescapeJava(stripped);
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
      final Expr expr = this.pop();
      final Expr value = this.pop();
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
   public void exitAndCondExpr(ScenarioParser.AndCondExprContext ctx)
   {
      for (int i = ctx.AND().size(); i > 0; i--)
      {
         final Expr rhs = this.pop();
         final Expr lhs = this.pop();
         final ConditionalOperatorExpr condOp = ConditionalOperatorExpr.of(lhs, ConditionalOperator.AND, rhs);
         this.stack.push(condOp);
      }
   }

   @Override
   public void exitOrCondExpr(ScenarioParser.OrCondExprContext ctx)
   {
      for (int i = ctx.OR().size(); i > 0; i--)
      {
         final Expr rhs = this.pop();
         final Expr lhs = this.pop();
         final ConditionalOperatorExpr condOp = ConditionalOperatorExpr.of(lhs, ConditionalOperator.OR, rhs);
         this.stack.push(condOp);
      }
   }

   @Override
   public void exitCondOpExpr(ScenarioParser.CondOpExprContext ctx)
   {
      final Expr rhs = this.pop();
      final Expr lhs = this.pop();
      final String opText = inputText(ctx.condOp()).replaceAll("\\s+", " ");
      final ConditionalOperator op = ConditionalOperator.getByOp(opText);
      if (op == null)
      {
         throw new UnsupportedOperationException("no ConditionalOperator constant for '" + opText + "'");
      }

      final ConditionalOperatorExpr operatorExpr = ConditionalOperatorExpr.of(lhs, op, rhs);

      this.stack.push(operatorExpr);
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
