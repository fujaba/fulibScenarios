package org.fulib.scenarios.parser;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.misc.Interval;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.apache.commons.text.StringEscapeUtils;
import org.fulib.scenarios.ast.*;
import org.fulib.scenarios.ast.decl.Name;
import org.fulib.scenarios.ast.decl.VarDecl;
import org.fulib.scenarios.ast.decl.WildcardName;
import org.fulib.scenarios.ast.expr.Expr;
import org.fulib.scenarios.ast.expr.access.AttributeAccess;
import org.fulib.scenarios.ast.expr.access.ExampleAccess;
import org.fulib.scenarios.ast.expr.call.CallExpr;
import org.fulib.scenarios.ast.expr.call.CreationExpr;
import org.fulib.scenarios.ast.expr.collection.FilterExpr;
import org.fulib.scenarios.ast.expr.collection.ListExpr;
import org.fulib.scenarios.ast.expr.collection.RangeExpr;
import org.fulib.scenarios.ast.expr.conditional.*;
import org.fulib.scenarios.ast.expr.primary.*;
import org.fulib.scenarios.ast.sentence.*;
import org.fulib.scenarios.ast.type.Type;
import org.fulib.scenarios.ast.type.UnresolvedType;
import org.fulib.scenarios.diagnostic.Marker;
import org.fulib.scenarios.diagnostic.Position;

import java.util.*;
import java.util.stream.Collectors;

import static org.fulib.scenarios.diagnostic.Marker.warning;
import static org.fulib.scenarios.parser.Identifiers.*;

public class ASTListener extends ScenarioParserBaseListener
{
   // =============== Fields ===============

   private List<Marker> markers = new ArrayList<>();
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

   private void report(Marker marker)
   {
      this.markers.add(marker);
   }

   // --------------- Scenario ---------------

   @Override
   public void exitFile(ScenarioParser.FileContext ctx)
   {
      final List<Scenario> scenarios = this.pop(Scenario.class, ctx.scenario().size());
      final LinkedHashMap<String, Scenario> scenarioMap = new LinkedHashMap<>();
      this.file = ScenarioFile.of(null, null, scenarioMap, null);
      this.file.setMarkers(this.markers);

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
      scenario.setPosition(position(ctx.header().HEADLINE_TEXT()));
      this.stack.push(scenario);
   }

   // --------------- Sentences ---------------

   @Override
   public void exitSectionSentence(ScenarioParser.SectionSentenceContext ctx)
   {
      final String text = ctx.HEADLINE_TEXT().getText().trim();
      final SectionSentence sectionSentence = SectionSentence.of(text, CommentLevel.CODE_SECTION);
      sectionSentence.setPosition(position(ctx));
      this.stack.push(sectionSentence);
   }

   @Override
   public void exitCommentSentence(ScenarioParser.CommentSentenceContext ctx)
   {
      final String text = ctx.HEADLINE_TEXT().getText().trim();
      final SectionSentence sectionSentence = SectionSentence.of(text, CommentLevel.REGULAR);
      sectionSentence.setPosition(position(ctx));
      this.stack.push(sectionSentence);
   }

   @Override
   public void exitThereSentence(ScenarioParser.ThereSentenceContext ctx)
   {
      final MultiDescriptor desc = this.pop();
      final ThereSentence thereSentence = ThereSentence.of(desc);
      thereSentence.setPosition(position(ctx.THERE()));
      this.stack.push(thereSentence);
   }

   private List<NamedExpr> popAttributes(ScenarioParser.WithClausesContext withClauses)
   {
      final int numAttributes = withClauses != null ? withClauses.withClause().size() : 0;
      return this.pop(NamedExpr.class, numAttributes);
   }

   private MultiDescriptor multiDescriptor(List<Name> names, ScenarioParser.WithClausesContext withClausesContext)
   {
      final List<NamedExpr> attributes = this.popAttributes(withClausesContext);
      final Type type = this.pop();
      return MultiDescriptor.of(type, names, attributes);
   }

   @Override
   public void exitSimpleDescriptor(ScenarioParser.SimpleDescriptorContext ctx)
   {
      final ScenarioParser.NameContext nameCtx = ctx.name();
      final List<Name> names = nameCtx == null ? Collections.emptyList() : Collections.singletonList(name(nameCtx));

      if (nameCtx != null && ctx.THE() == null)
      {
         this.report(warning(position(ctx), "descriptor.indefinite.deprecated", inputText(ctx.typeName()),
                             inputText(nameCtx)));
      }

      this.stack.push(this.multiDescriptor(names, ctx.withClauses()));
   }

   @Override
   public void exitMultiDescriptor(ScenarioParser.MultiDescriptorContext ctx)
   {
      final List<Name> names = ctx.name().stream().map(Identifiers::name).collect(Collectors.toList());

      if (!names.isEmpty() && ctx.THE() == null)
      {
         this.report(warning(position(ctx), "descriptor.multi.indefinite.deprecated", inputText(ctx.typesName()),
                             inputText(ctx.name())));
      }

      this.stack.push(this.multiDescriptor(names, ctx.withClauses()));
   }

   @Override
   public void exitExpectSentence(ScenarioParser.ExpectSentenceContext ctx)
   {
      final List<Expr> exprs = this.pop(Expr.class, ctx.thatClauses().thatClause().size());
      final ExpectSentence expectSentence = ExpectSentence.of(exprs);
      expectSentence.setPosition(position(ctx.EXPECT()));
      this.stack.push(expectSentence);
   }

   @Override
   public void exitPatternExpectSentence(ScenarioParser.PatternExpectSentenceContext ctx)
   {
      final List<Expr> exprs = this.pop(Expr.class, ctx.thatClauses().thatClause().size());
      final Name name = name(ctx.name());
      final Type type = this.pop();
      final PatternExpectSentence patternExpectSentence = PatternExpectSentence.of(type, name, exprs);
      this.stack.push(patternExpectSentence);
   }

   @Override
   public void exitDiagramSentence(ScenarioParser.DiagramSentenceContext ctx)
   {
      final Expr object = this.pop();
      final String fileName = ctx.fileName.getText();
      final DiagramSentence diagramSentence = DiagramSentence.of(object, fileName);
      diagramSentence.setPosition(position(ctx));
      this.stack.push(diagramSentence);
   }

   @Override
   public void exitHasSentence(ScenarioParser.HasSentenceContext ctx)
   {
      final List<NamedExpr> clauses = this.pop(NamedExpr.class, ctx.hasClauses().hasClause().size());
      final Expr object = this.pop();
      final HasSentence hasSentence = HasSentence.of(object, clauses);
      hasSentence.setPosition(position(ctx.hasClauses().hasClause(0).verb));
      this.stack.push(hasSentence);
   }

   @Override
   public void exitIsSentence(ScenarioParser.IsSentenceContext ctx)
   {
      final List<NamedExpr> attributes = this.popAttributes(ctx.withClauses());
      final Type type = this.pop();
      final Expr ctor = CreationExpr.of(type, attributes);
      ctor.setPosition(type.getPosition());
      final String name = varName(ctx.name());
      final VarDecl varDecl = VarDecl.of(name, null, ctor);
      varDecl.setPosition(position(ctx.name()));
      final IsSentence isSentence = IsSentence.of(varDecl);
      isSentence.setPosition(position(ctx.IS()));
      this.stack.push(isSentence);
   }

   @Override
   public void exitAreSentence(ScenarioParser.AreSentenceContext ctx)
   {
      final List<Name> names = ctx.name().stream().map(Identifiers::name).collect(Collectors.toList());
      final MultiDescriptor descriptor = this.multiDescriptor(names, ctx.withClauses());
      final AreSentence areSentence = AreSentence.of(descriptor);
      areSentence.setPosition(position(ctx.ARE()));
      this.stack.push(areSentence);
   }

   @Override
   public void exitCreateSentence(ScenarioParser.CreateSentenceContext ctx)
   {
      final Name actor = name(ctx.actor().name()); // null if actor is "we"
      final MultiDescriptor desc = this.pop();
      final CreateSentence createSentence = CreateSentence.of(actor, desc);
      createSentence.setPosition(position(ctx.verb));
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
      callExpr.setPosition(position(ctx.name()));
      final CallSentence callSentence = CallSentence.of(actor, callExpr);
      callSentence.setPosition(position(ctx.verb));
      this.stack.push(callSentence);
   }

   @Override
   public void exitAnswerSentence(ScenarioParser.AnswerSentenceContext ctx)
   {
      final Name actor = name(ctx.actor().name()); // null if actor is "we"
      final Expr result = this.pop();
      final String varName = varName(ctx.name());
      final AnswerSentence answerSentence = AnswerSentence.of(actor, result, varName);
      answerSentence.setPosition(position(ctx.verb));
      this.stack.push(answerSentence);
   }

   @Override
   public void exitWriteSentence(ScenarioParser.WriteSentenceContext ctx)
   {
      final Expr target = this.pop();
      final Expr source = this.pop();
      final Name actor = name(ctx.actor().name()); // null if actor is "we"
      final WriteSentence writeSentence = WriteSentence.of(actor, source, target);
      writeSentence.setPosition(position(ctx.verb));
      this.stack.push(writeSentence);
   }

   @Override
   public void exitAddSentence(ScenarioParser.AddSentenceContext ctx)
   {
      final Expr target = this.pop();
      final Expr source = this.pop();
      final Name actor = name(ctx.actor().name()); // null if actor is "we"
      final AddSentence addSentence = AddSentence.of(actor, source, target);
      addSentence.setPosition(position(ctx.verb));
      this.stack.push(addSentence);
   }

   @Override
   public void exitRemoveSentence(ScenarioParser.RemoveSentenceContext ctx)
   {
      final Expr target = this.pop();
      final Expr source = this.pop();
      final Name actor = name(ctx.actor().name()); // null if actor is "we"
      final RemoveSentence removeSentence = RemoveSentence.of(actor, source, target);
      removeSentence.setPosition(position(ctx.verb));
      this.stack.push(removeSentence);
   }

   @Override
   public void exitSimpleSentences(ScenarioParser.SimpleSentencesContext ctx)
   {
      final int count = ctx.simpleSentence().size() + (ctx.compoundSentence() != null ? 1 : 0);
      final List<Sentence> sentences = this.pop(Sentence.class, count);
      final SentenceList list = SentenceList.of(sentences);
      this.stack.push(list);
   }

   @Override
   public void exitConditionalSentence(ScenarioParser.ConditionalSentenceContext ctx)
   {
      final SentenceList body = this.pop();
      final Expr condition = this.pop();
      final ConditionalSentence sentence = ConditionalSentence.of(condition, body);
      sentence.setPosition(position(ctx.AS()));
      this.stack.push(sentence);
   }

   @Override
   public void exitTakeSentence(ScenarioParser.TakeSentenceContext ctx)
   {
      final Sentence body = this.pop();
      final Expr collection = this.pop();
      final Expr example = ctx.example != null ? this.pop() : null;
      final Name varName;
      if (ctx.simpleVarName != null)
      {
         varName = name(ctx.simpleVarName);
         this.report(warning(position(ctx.simpleVarName), "take.syntax.deprecated", inputText(ctx.simpleVarName),
                             inputText(ctx.example)));
      }
      else
      {
         varName = name(ctx.name());
      }

      final Name actor = name(ctx.actor().name()); // null if actor is "we"
      final TakeSentence takeSentence = TakeSentence.of(actor, varName, example, collection, body);
      takeSentence.setPosition(position(ctx.verb));
      this.stack.push(takeSentence);
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

   @Override
   public void exitNamedWildcardAttribute(ScenarioParser.NamedWildcardAttributeContext ctx)
   {
      final Expr expr = this.pop();
      final WildcardName name = WildcardName.of();
      name.setPosition(position(ctx.SOME()));
      this.stack.push(NamedExpr.of(name, expr));
   }

   @Override
   public void exitBidiNamedExpr(ScenarioParser.BidiNamedExprContext ctx)
   {
      final Expr expr = this.pop();
      final Name name = name(ctx.firstName);
      final NamedExpr namedExpr = NamedExpr.of(name, expr);
      namedExpr.setOtherName(name(ctx.otherName));
      namedExpr.setOtherMany(ctx.ONE() != null);
      this.stack.push(namedExpr);
   }

   // --------------- Types ---------------

   @Override
   public void exitTypeName(ScenarioParser.TypeNameContext ctx)
   {
      final UnresolvedType type;
      if (ctx.CARD() != null)
      {
         final ScenarioParser.NameContext name = ctx.name();
         type = unresolvedType(position(name), joinCaps(name));
      }
      else
      {
         final ScenarioParser.SimpleNameContext simpleName = ctx.simpleName();
         type = unresolvedType(position(simpleName), joinCaps(simpleName));
      }
      this.stack.push(type);
   }

   @Override
   public void exitTypesName(ScenarioParser.TypesNameContext ctx)
   {
      final UnresolvedType type;
      if (ctx.CARDS() != null)
      {
         final ScenarioParser.NameContext name = ctx.name();
         type = unresolvedType(position(name), joinCaps(name));
      }
      else
      {
         final ScenarioParser.SimpleNameContext simpleName = ctx.simpleName();
         type = unresolvedTypePlural(position(simpleName), joinCaps(simpleName));
      }
      this.stack.push(type);
   }

   private static UnresolvedType unresolvedTypePlural(Position position, String caps)
   {
      final String typeName = caps.endsWith("s") ? caps.substring(0, caps.length() - 1) : caps;
      return unresolvedType(position, typeName);
   }

   private static UnresolvedType unresolvedType(Position position, String typeName)
   {
      final UnresolvedType type = UnresolvedType.of(typeName);
      type.setPosition(position);
      return type;
   }

   // --------------- Expressions ---------------

   @Override
   public void exitNumber(ScenarioParser.NumberContext ctx)
   {
      final TerminalNode decimal = ctx.DECIMAL();
      if (decimal != null)
      {
         final double value = Double.parseDouble(decimal.getText());
         final DoubleLiteral doubleLiteral = DoubleLiteral.of(value);
         doubleLiteral.setPosition(position(decimal));
         this.stack.push(doubleLiteral);
      }
      else
      {
         final int value = Integer.parseInt(ctx.INTEGER().getText());
         final IntLiteral intLiteral = IntLiteral.of(value);
         intLiteral.setPosition(position(ctx.INTEGER()));
         this.stack.push(intLiteral);
      }
   }

   @Override
   public void exitStringLiteral(ScenarioParser.StringLiteralContext ctx)
   {
      final String text = ctx.STRING_LITERAL().getText();
      // strip opening and closing quotes
      final String stripped = text.substring(1, text.length() - 1);
      final String value = StringEscapeUtils.unescapeJava(stripped);
      final StringLiteral stringLiteral = StringLiteral.of(value);
      stringLiteral.setPosition(position(ctx.STRING_LITERAL()));
      this.stack.push(stringLiteral);
   }

   @Override
   public void exitAnswer(ScenarioParser.AnswerContext ctx)
   {
      final AnswerLiteral answer = AnswerLiteral.of();
      answer.setPosition(position(ctx.ANSWER()));
      this.stack.push(answer);
   }

   @Override
   public void exitNameAccess(ScenarioParser.NameAccessContext ctx)
   {
      final Name name = name(ctx.name());
      final NameAccess nameAccess = NameAccess.of(name);
      nameAccess.setPosition(name.getPosition());
      this.stack.push(nameAccess);
   }

   @Override
   public void exitAttributeAccess(ScenarioParser.AttributeAccessContext ctx)
   {
      final Name name = name(ctx.name());
      final Expr receiver = this.pop();
      final AttributeAccess attributeAccess = AttributeAccess.of(name, receiver);
      attributeAccess.setPosition(position(ctx.OF()));
      this.stack.push(attributeAccess);
   }

   @Override
   public void exitExampleAccess(ScenarioParser.ExampleAccessContext ctx)
   {
      final Expr expr = this.pop();
      final Expr value = this.pop();
      final ExampleAccess exampleAccess = ExampleAccess.of(value, expr);
      exampleAccess.setPosition(position(ctx.FROM()));
      this.stack.push(exampleAccess);
   }

   @Override
   public void exitFilterExpr(ScenarioParser.FilterExprContext ctx)
   {
      final Expr predicate = this.pop();
      final Expr source = this.pop();
      final FilterExpr filterExpr = FilterExpr.of(source, predicate);
      filterExpr.setPosition(position(ctx.ALL()));
      this.stack.push(filterExpr);
   }

   @Override
   public void exitAttrCheck(ScenarioParser.AttrCheckContext ctx)
   {
      final NamedExpr valueAndAttribute = this.pop();
      final Expr value = valueAndAttribute.getExpr();
      final Name attribute = valueAndAttribute.getName();
      final Expr receiver = ctx.access() != null ? this.pop() : null;
      final AttributeCheckExpr attributeCheckExpr = AttributeCheckExpr.of(receiver, attribute, value);
      attributeCheckExpr.setPosition(position(ctx.HAS()));
      this.stack.push(attributeCheckExpr);
   }

   @Override
   public void exitAndCondExpr(ScenarioParser.AndCondExprContext ctx)
   {
      final List<TerminalNode> ands = ctx.AND();
      for (int i = ands.size() - 1; i >= 0; i--)
      {
         final Expr rhs = this.pop();
         final Expr lhs = this.pop();
         final ConditionalOperatorExpr condOp = ConditionalOperatorExpr.of(lhs, ConditionalOperator.AND, rhs);
         condOp.setPosition(position(ands.get(i)));
         this.stack.push(condOp);
      }
   }

   @Override
   public void exitOrCondExpr(ScenarioParser.OrCondExprContext ctx)
   {
      final List<TerminalNode> ors = ctx.OR();
      for (int i = ors.size() - 1; i >= 0; i--)
      {
         final Expr rhs = this.pop();
         final Expr lhs = this.pop();
         final ConditionalOperatorExpr condOp = ConditionalOperatorExpr.of(lhs, ConditionalOperator.OR, rhs);
         condOp.setPosition(position(ors.get(i)));
         this.stack.push(condOp);
      }
   }

   @Override
   public void exitCondOpExpr(ScenarioParser.CondOpExprContext ctx)
   {
      final Expr rhs = this.pop();
      final Expr lhs = ctx.lhs != null ? this.pop() : null;
      final String opText = inputText(ctx.condOp());
      final ConditionalOperator op = ConditionalOperator.getByOp(opText);
      if (op == null)
      {
         throw new UnsupportedOperationException("no ConditionalOperator constant for '" + opText + "'");
      }

      final ConditionalOperatorExpr operatorExpr = ConditionalOperatorExpr.of(lhs, op, rhs);
      operatorExpr.setPosition(position(ctx.condOp()));

      this.stack.push(operatorExpr);
   }

   @Override
   public void exitPredOpExpr(ScenarioParser.PredOpExprContext ctx)
   {
      final Expr lhs = ctx.lhs != null ? this.pop() : null;
      final String opText = inputText(ctx.predOp());
      final PredicateOperator op = PredicateOperator.nameMap.get(opText);
      if (op == null)
      {
         throw new UnsupportedOperationException("no PredicateOperator constant for '" + opText + "'");
      }

      final PredicateOperatorExpr expr = PredicateOperatorExpr.of(lhs, op);
      expr.setPosition(position(ctx.predOp()));

      this.stack.push(expr);
   }

   @Override
   public void exitList(ScenarioParser.ListContext ctx)
   {
      final List<Expr> elements = this.pop(Expr.class, ctx.listElem().size());
      final ListExpr listExpr = ListExpr.of(elements);
      listExpr.setPosition(position(ctx));
      this.stack.push(listExpr);
   }

   @Override
   public void exitRange(ScenarioParser.RangeContext ctx)
   {
      final Expr end = this.pop();
      final Expr start = this.pop();
      final RangeExpr rangeExpr = RangeExpr.of(start, end);
      rangeExpr.setPosition(position(ctx.TO()));
      this.stack.push(rangeExpr);
   }

   // =============== Static Methods ===============

   static Position position(Token token)
   {
      return new Position(token.getInputStream().getSourceName(), token.getStartIndex(), token.getStopIndex(),
                          token.getLine(), token.getCharPositionInLine());
   }

   static Position position(TerminalNode terminal)
   {
      return position(terminal.getSymbol());
   }

   static Position position(ParserRuleContext ctx)
   {
      final Token start = ctx.getStart();
      return new Position(start.getInputStream().getSourceName(), start.getStartIndex(), ctx.getStop().getStopIndex(),
                          start.getLine(), start.getCharPositionInLine());
   }

   /**
    * @param ctx
    * 	the parser rule
    *
    * @return the raw input text of everything the rule matches, including comments and raw whitespace.
    */
   static String rawInputText(ParserRuleContext ctx)
   {
      final CharStream inputStream = ctx.getStart().getInputStream();
      final Interval interval = Interval.of(ctx.getStart().getStartIndex(), ctx.getStop().getStopIndex());
      return inputStream.getText(interval);
   }

   /**
    * @param tree
    * 	the parse tree
    *
    * @return the text of all tokens the parse tree encloses, separated by whitespace
    */
   static String inputText(ParseTree tree)
   {
      StringJoiner joiner = new StringJoiner(" ");
      inputText(tree, joiner);
      return joiner.toString();
   }

   static String inputText(Iterable<? extends ParseTree> trees)
   {
      StringJoiner joiner = new StringJoiner(" ");
      inputText(trees, joiner);
      return joiner.toString();
   }

   private static void inputText(Iterable<? extends ParseTree> trees, StringJoiner joiner)
   {
      for (final ParseTree tree : trees)
      {
         inputText(tree, joiner);
      }
   }

   private static void inputText(ParseTree tree, StringJoiner joiner)
   {
      if (tree instanceof TerminalNode)
      {
         joiner.add(tree.getText());
      }

      for (int i = 0; i < tree.getChildCount(); i++)
      {
         final ParseTree child = tree.getChild(i);
         inputText(child, joiner);
      }
   }
}
