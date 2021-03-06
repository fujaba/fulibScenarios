package org.fulib.scenarios.parser;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.misc.Interval;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.fulib.scenarios.ast.*;
import org.fulib.scenarios.ast.decl.Name;
import org.fulib.scenarios.ast.decl.VarDecl;
import org.fulib.scenarios.ast.expr.Expr;
import org.fulib.scenarios.ast.expr.PlaceholderExpr;
import org.fulib.scenarios.ast.expr.access.AttributeAccess;
import org.fulib.scenarios.ast.expr.access.ExampleAccess;
import org.fulib.scenarios.ast.expr.call.CallExpr;
import org.fulib.scenarios.ast.expr.call.CreationExpr;
import org.fulib.scenarios.ast.expr.collection.FilterExpr;
import org.fulib.scenarios.ast.expr.collection.ListExpr;
import org.fulib.scenarios.ast.expr.collection.RangeExpr;
import org.fulib.scenarios.ast.expr.conditional.*;
import org.fulib.scenarios.ast.expr.primary.*;
import org.fulib.scenarios.ast.pattern.*;
import org.fulib.scenarios.ast.sentence.*;
import org.fulib.scenarios.ast.type.ListType;
import org.fulib.scenarios.ast.type.Type;
import org.fulib.scenarios.ast.type.UnresolvedType;
import org.fulib.scenarios.diagnostic.Marker;
import org.fulib.scenarios.diagnostic.Position;

import java.util.*;
import java.util.stream.Collectors;

import static org.fulib.scenarios.diagnostic.Marker.*;
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
      this.file = ScenarioFile.of(null, null, scenarios, null);
      this.file.setMarkers(this.markers);

      for (final Scenario scenario : scenarios)
      {
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
         final Position position = position(ctx);
         this.report(warning(position, "descriptor.indefinite.deprecated").note(
            note(position, "descriptor.indefinite.deprecated.hint", inputText(ctx.typeName()), inputText(nameCtx))));
      }

      this.stack.push(this.multiDescriptor(names, ctx.withClauses()));
   }

   @Override
   public void exitMultiDescriptor(ScenarioParser.MultiDescriptorContext ctx)
   {
      final List<Name> names = ctx.name().stream().map(Identifiers::name).collect(Collectors.toList());

      if (!names.isEmpty() && ctx.THE() == null)
      {
         final Position position = position(ctx);
         this.report(warning(position, "descriptor.multi.indefinite.deprecated").note(
            note(position, "descriptor.indefinite.deprecated.hint", inputText(ctx.typesName()),
                 inputText(ctx.name()))));
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
   public void exitMatchSentence(ScenarioParser.MatchSentenceContext ctx)
   {
      final Name actor = name(ctx.actor().name());
      final List<Pattern> patterns = this.pop(Pattern.class, ctx.patternObjects().patternObject().size());
      final Expr roots = ctx.ON() != null ? this.pop() : null;
      final MatchSentence matchSentence = MatchSentence.of(actor, patterns, roots);
      matchSentence.setPosition(position(ctx.verb));
      this.stack.push(matchSentence);
   }

   @Override
   public void exitPatternObject(ScenarioParser.PatternObjectContext ctx)
   {
      final ScenarioParser.ConstraintsContext constraintsCtx = ctx.constraints();
      final List<Constraint> constraints = this.pop(Constraint.class,
                                                    constraintsCtx != null ? constraintsCtx.constraint().size() : 0);
      final Name name = name(ctx.name());
      Type type = this.pop();
      if (ctx.ALL() != null)
      {
         type = ListType.of(type);
      }
      final Pattern pattern = Pattern.of(type, name, constraints);
      this.stack.push(pattern);
   }

   @Override
   public void exitLinkConstraint(ScenarioParser.LinkConstraintContext ctx)
   {
      final LinkConstraint linkConstraint = LinkConstraint.of(null, name(ctx.name()));
      linkConstraint.setPosition(position(ctx.WITH()));
      this.stack.push(linkConstraint);
   }

   @Override
   public void exitAttributeEqualityConstraint(ScenarioParser.AttributeEqualityConstraintContext ctx)
   {
      final Expr rhs = this.pop();
      final Name name = name(ctx.name());
      final AttributeEqualityConstraint aec = AttributeEqualityConstraint.of(name, rhs);
      aec.setPosition(position(ctx.WITH()));
      this.stack.push(aec);
   }

   @Override
   public void exitAttributePredicateConstraint(ScenarioParser.AttributePredicateConstraintContext ctx)
   {
      final Name name = name(ctx.name());
      final PredicateOperator predOp = predicateOperator(ctx.predOp());
      final AttributePredicateConstraint apc = AttributePredicateConstraint.of(name, predOp);
      apc.setPosition(position(ctx.predOp()));
      this.stack.push(apc);
   }

   @Override
   public void exitAttributeConditionalConstraint(ScenarioParser.AttributeConditionalConstraintContext ctx)
   {
      final Expr rhs = this.pop();
      final Name name = name(ctx.name());
      final ConditionalOperator condOp = conditionalOperator(ctx.condOp());
      final AttributeConditionalConstraint acc = AttributeConditionalConstraint.of(name, condOp, rhs);
      acc.setPosition(position(ctx.condOp()));
      this.stack.push(acc);
   }

   @Override
   public void exitMatchConstraint(ScenarioParser.MatchConstraintContext ctx)
   {
      final Expr condExpr = this.pop();
      final MatchConstraint matchConstraint = MatchConstraint.of(condExpr, new ArrayList<>());
      matchConstraint.setPosition(position(ctx.WHERE()));
      this.stack.push(matchConstraint);
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
      final Expr receiver = this.pop();

      if (receiver instanceof PlaceholderExpr)
      {
         for (final NamedExpr clause : clauses)
         {
            final Expr expr = clause.getExpr();
            if (expr instanceof PlaceholderExpr)
            {
               continue;
            }

            final Position position = expr.getPosition();
            final Marker error = error(position, "has.placeholder.concrete");
            final String exprText = ctx
               .getStart()
               .getInputStream()
               .getText(Interval.of((int) position.getStartOffset(), (int) position.getEndOffset()));
            error.note(note(position, "has.placeholder.concrete.hint", clause.getName().getValue(), exprText));
            this.report(error);
         }
      }

      final HasSentence hasSentence = HasSentence.of(receiver, clauses);
      hasSentence.setPosition(position(ctx.hasClauses().hasClause(0).verb));
      this.stack.push(hasSentence);
   }

   @Override
   public void exitSimpleIsSentence(ScenarioParser.SimpleIsSentenceContext ctx)
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
   public void exitInheritanceIsSentence(ScenarioParser.InheritanceIsSentenceContext ctx)
   {
      final Type superType = this.pop();
      final Type subType = this.pop();
      final InheritanceSentence inheritanceSentence = InheritanceSentence.of(subType, superType);
      inheritanceSentence.setPosition(position(ctx.IS()));
      this.stack.push(inheritanceSentence);
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
         final Position position = position(ctx.simpleVarName);
         this.report(warning(position, "take.syntax.deprecated").note(
            note(position, "take.syntax.deprecated.hint", inputText(ctx.simpleVarName), inputText(ctx.example))));
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
   public void exitBidiNamedExpr(ScenarioParser.BidiNamedExprContext ctx)
   {
      final Expr expr = this.pop();
      final Name name = name(ctx.firstName);
      final NamedExpr namedExpr = NamedExpr.of(name, expr);
      namedExpr.setOtherName(name(ctx.otherName));
      namedExpr.setOtherMany(ctx.ONE() != null);
      this.stack.push(namedExpr);
   }

   @Override
   public void exitAPlaceholder(ScenarioParser.APlaceholderContext ctx)
   {
      this.buildPlaceholderExpr(ctx.LIKE() != null, true);
   }

   @Override
   public void exitManyPlaceholder(ScenarioParser.ManyPlaceholderContext ctx)
   {
      final Expr example = ctx.LIKE() != null ? this.pop() : null;
      final Type type = ListType.of(this.pop());
      final PlaceholderExpr placeholderExpr = PlaceholderExpr.of(type, example);
      placeholderExpr.setPosition(type.getPosition());
      this.stack.push(placeholderExpr);
   }

   @Override
   public void exitEveryPlaceholder(ScenarioParser.EveryPlaceholderContext ctx)
   {
      buildPlaceholderExpr(ctx.LIKE() != null, true);
   }

   @Override
   public void exitLikePlaceholder(ScenarioParser.LikePlaceholderContext ctx)
   {
      this.buildPlaceholderExpr(true, false);
   }

   @Override
   public void exitOfTypePlaceholder(ScenarioParser.OfTypePlaceholderContext ctx)
   {
      this.buildPlaceholderExpr(ctx.LIKE() != null, true);
   }

   private void buildPlaceholderExpr(boolean hasExample, boolean hasType)
   {
      final Expr example = hasExample ? this.pop() : null;
      final Type type = hasType ? this.pop() : null;
      final PlaceholderExpr placeholderExpr = PlaceholderExpr.of(type, example);
      placeholderExpr.setPosition(type != null ? type.getPosition() : example != null ? example.getPosition() : null);
      this.stack.push(placeholderExpr);
   }

   @Override
   public void exitPlaceholderNamedExpr(ScenarioParser.PlaceholderNamedExprContext ctx)
   {
      final PlaceholderExpr placeholderExpr = this.pop();
      final Name name = name(ctx.name());
      final NamedExpr namedExpr = NamedExpr.of(name, placeholderExpr);
      this.stack.push(namedExpr);
   }

   // --------------- Types ---------------

   @Override
   public void exitTypeName(ScenarioParser.TypeNameContext ctx)
   {
      final Type type;
      if (ctx.CARD() != null)
      {
         final ScenarioParser.NameContext name = ctx.name();
         type = unresolvedType(position(name), joinCaps(name), inputText(name), false);
      }
      else
      {
         final ScenarioParser.SimpleNameContext simpleNameCtx = ctx.simpleName();
         type = unresolvedType(position(simpleNameCtx), joinCaps(simpleNameCtx), inputText(simpleNameCtx), false);
      }
      this.stack.push(type);
   }

   @Override
   public void exitTypesName(ScenarioParser.TypesNameContext ctx)
   {
      final Type type;
      if (ctx.CARDS() != null)
      {
         final ScenarioParser.NameContext name = ctx.name();
         type = unresolvedType(position(name), joinCaps(name), inputText(name), true);
      }
      else
      {
         final ScenarioParser.SimpleNameContext simpleNameCtx = ctx.simpleName();
         type = unresolvedType(position(simpleNameCtx), joinCaps(simpleNameCtx), inputText(simpleNameCtx), true);
      }
      this.stack.push(type);
   }

   private static UnresolvedType unresolvedType(Position position, String typeName, String text, boolean plural)
   {
      final UnresolvedType type = UnresolvedType.of(typeName, text, plural);
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
   public void exitBooleanLiteral(ScenarioParser.BooleanLiteralContext ctx)
   {
      final BooleanLiteral literal = BooleanLiteral.of(ctx.TRUE() != null);
      literal.setPosition(position(ctx.start));
      this.stack.push(literal);
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
   public void exitCodeBlock(ScenarioParser.CodeBlockContext ctx)
   {
      final StringBuilder body = new StringBuilder();

      final String startText = ctx.CODE_BLOCK().getText();
      final String incidentalSpace = startText.substring(0, startText.indexOf('`'));

      for (final TerminalNode codeBlockLine : ctx.CODE_BLOCK_LINE())
      {
         String line = codeBlockLine.getText();
         // strip leading incidental space
         final int start = StringUtils.indexOfDifference(line, incidentalSpace);
         // strip trailing line terminator
         final int end = line.length() - (line.endsWith("\r\n") ? 2 : 1);
         body.append(line, start, end);
         // normalize line terminator
         body.append('\n');
      }

      final StringLiteral stringLiteral = StringLiteral.of(body.toString());
      stringLiteral.setPosition(position(ctx));

      final TerminalNode codeBlockLanguage = ctx.CODE_BLOCK_LANGUAGE();
      if (codeBlockLanguage != null)
      {
         stringLiteral.setLanguage(codeBlockLanguage.getText());
      }

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
   public void exitIt(ScenarioParser.ItContext ctx)
   {
      final ItLiteral it = ItLiteral.of();
      it.setPosition(position(ctx.IT()));
      this.stack.push(it);
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
      final ConditionalOperator op = conditionalOperator(ctx.condOp());

      final ConditionalOperatorExpr operatorExpr = ConditionalOperatorExpr.of(lhs, op, rhs);
      operatorExpr.setPosition(position(ctx.condOp()));

      this.stack.push(operatorExpr);
   }

   private static ConditionalOperator conditionalOperator(ScenarioParser.CondOpContext condOp)
   {
      final String opText = inputText(condOp);
      final ConditionalOperator op = ConditionalOperator.getByOp(opText);
      if (op == null)
      {
         throw new UnsupportedOperationException("no ConditionalOperator constant for '" + opText + "'");
      }
      return op;
   }

   @Override
   public void exitPredOpExpr(ScenarioParser.PredOpExprContext ctx)
   {
      final Expr lhs = ctx.lhs != null ? this.pop() : null;
      final PredicateOperator op = predicateOperator(ctx.predOp());

      final PredicateOperatorExpr expr = PredicateOperatorExpr.of(lhs, op);
      expr.setPosition(position(ctx.predOp()));

      this.stack.push(expr);
   }

   private static PredicateOperator predicateOperator(ScenarioParser.PredOpContext predOp)
   {
      final String opText = inputText(predOp);
      final PredicateOperator op = PredicateOperator.nameMap.get(opText);
      if (op == null)
      {
         throw new UnsupportedOperationException("no PredicateOperator constant for '" + opText + "'");
      }
      return op;
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
