// Generated from C:/Users/zuend/IdeaProjects/fulibScenarios/src/main/java/org/fulib/scenarios\FulibScenarios.g4 by ANTLR 4.7.2
package org.fulib.scenarios.compiler;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link FulibScenariosParser}.
 */
public interface FulibScenariosListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link FulibScenariosParser#scenario}.
	 * @param ctx the parse tree
	 */
	void enterScenario(FulibScenariosParser.ScenarioContext ctx);
	/**
	 * Exit a parse tree produced by {@link FulibScenariosParser#scenario}.
	 * @param ctx the parse tree
	 */
	void exitScenario(FulibScenariosParser.ScenarioContext ctx);
	/**
	 * Enter a parse tree produced by {@link FulibScenariosParser#sentence}.
	 * @param ctx the parse tree
	 */
	void enterSentence(FulibScenariosParser.SentenceContext ctx);
	/**
	 * Exit a parse tree produced by {@link FulibScenariosParser#sentence}.
	 * @param ctx the parse tree
	 */
	void exitSentence(FulibScenariosParser.SentenceContext ctx);
	/**
	 * Enter a parse tree produced by {@link FulibScenariosParser#callSentence}.
	 * @param ctx the parse tree
	 */
	void enterCallSentence(FulibScenariosParser.CallSentenceContext ctx);
	/**
	 * Exit a parse tree produced by {@link FulibScenariosParser#callSentence}.
	 * @param ctx the parse tree
	 */
	void exitCallSentence(FulibScenariosParser.CallSentenceContext ctx);
	/**
	 * Enter a parse tree produced by {@link FulibScenariosParser#thereSentence}.
	 * @param ctx the parse tree
	 */
	void enterThereSentence(FulibScenariosParser.ThereSentenceContext ctx);
	/**
	 * Exit a parse tree produced by {@link FulibScenariosParser#thereSentence}.
	 * @param ctx the parse tree
	 */
	void exitThereSentence(FulibScenariosParser.ThereSentenceContext ctx);
	/**
	 * Enter a parse tree produced by {@link FulibScenariosParser#multiName}.
	 * @param ctx the parse tree
	 */
	void enterMultiName(FulibScenariosParser.MultiNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link FulibScenariosParser#multiName}.
	 * @param ctx the parse tree
	 */
	void exitMultiName(FulibScenariosParser.MultiNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link FulibScenariosParser#chainSentence}.
	 * @param ctx the parse tree
	 */
	void enterChainSentence(FulibScenariosParser.ChainSentenceContext ctx);
	/**
	 * Exit a parse tree produced by {@link FulibScenariosParser#chainSentence}.
	 * @param ctx the parse tree
	 */
	void exitChainSentence(FulibScenariosParser.ChainSentenceContext ctx);
	/**
	 * Enter a parse tree produced by {@link FulibScenariosParser#introPhrase}.
	 * @param ctx the parse tree
	 */
	void enterIntroPhrase(FulibScenariosParser.IntroPhraseContext ctx);
	/**
	 * Exit a parse tree produced by {@link FulibScenariosParser#introPhrase}.
	 * @param ctx the parse tree
	 */
	void exitIntroPhrase(FulibScenariosParser.IntroPhraseContext ctx);
	/**
	 * Enter a parse tree produced by {@link FulibScenariosParser#onPhrase}.
	 * @param ctx the parse tree
	 */
	void enterOnPhrase(FulibScenariosParser.OnPhraseContext ctx);
	/**
	 * Exit a parse tree produced by {@link FulibScenariosParser#onPhrase}.
	 * @param ctx the parse tree
	 */
	void exitOnPhrase(FulibScenariosParser.OnPhraseContext ctx);
	/**
	 * Enter a parse tree produced by {@link FulibScenariosParser#loopClause}.
	 * @param ctx the parse tree
	 */
	void enterLoopClause(FulibScenariosParser.LoopClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link FulibScenariosParser#loopClause}.
	 * @param ctx the parse tree
	 */
	void exitLoopClause(FulibScenariosParser.LoopClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link FulibScenariosParser#asPhrase}.
	 * @param ctx the parse tree
	 */
	void enterAsPhrase(FulibScenariosParser.AsPhraseContext ctx);
	/**
	 * Exit a parse tree produced by {@link FulibScenariosParser#asPhrase}.
	 * @param ctx the parse tree
	 */
	void exitAsPhrase(FulibScenariosParser.AsPhraseContext ctx);
	/**
	 * Enter a parse tree produced by {@link FulibScenariosParser#cmpOp}.
	 * @param ctx the parse tree
	 */
	void enterCmpOp(FulibScenariosParser.CmpOpContext ctx);
	/**
	 * Exit a parse tree produced by {@link FulibScenariosParser#cmpOp}.
	 * @param ctx the parse tree
	 */
	void exitCmpOp(FulibScenariosParser.CmpOpContext ctx);
	/**
	 * Enter a parse tree produced by {@link FulibScenariosParser#greaterEqual}.
	 * @param ctx the parse tree
	 */
	void enterGreaterEqual(FulibScenariosParser.GreaterEqualContext ctx);
	/**
	 * Exit a parse tree produced by {@link FulibScenariosParser#greaterEqual}.
	 * @param ctx the parse tree
	 */
	void exitGreaterEqual(FulibScenariosParser.GreaterEqualContext ctx);
	/**
	 * Enter a parse tree produced by {@link FulibScenariosParser#lessThan}.
	 * @param ctx the parse tree
	 */
	void enterLessThan(FulibScenariosParser.LessThanContext ctx);
	/**
	 * Exit a parse tree produced by {@link FulibScenariosParser#lessThan}.
	 * @param ctx the parse tree
	 */
	void exitLessThan(FulibScenariosParser.LessThanContext ctx);
	/**
	 * Enter a parse tree produced by {@link FulibScenariosParser#predicateObjectPhrase}.
	 * @param ctx the parse tree
	 */
	void enterPredicateObjectPhrase(FulibScenariosParser.PredicateObjectPhraseContext ctx);
	/**
	 * Exit a parse tree produced by {@link FulibScenariosParser#predicateObjectPhrase}.
	 * @param ctx the parse tree
	 */
	void exitPredicateObjectPhrase(FulibScenariosParser.PredicateObjectPhraseContext ctx);
	/**
	 * Enter a parse tree produced by {@link FulibScenariosParser#stopPhrase}.
	 * @param ctx the parse tree
	 */
	void enterStopPhrase(FulibScenariosParser.StopPhraseContext ctx);
	/**
	 * Exit a parse tree produced by {@link FulibScenariosParser#stopPhrase}.
	 * @param ctx the parse tree
	 */
	void exitStopPhrase(FulibScenariosParser.StopPhraseContext ctx);
	/**
	 * Enter a parse tree produced by {@link FulibScenariosParser#createPhrase}.
	 * @param ctx the parse tree
	 */
	void enterCreatePhrase(FulibScenariosParser.CreatePhraseContext ctx);
	/**
	 * Exit a parse tree produced by {@link FulibScenariosParser#createPhrase}.
	 * @param ctx the parse tree
	 */
	void exitCreatePhrase(FulibScenariosParser.CreatePhraseContext ctx);
	/**
	 * Enter a parse tree produced by {@link FulibScenariosParser#verbPhrase}.
	 * @param ctx the parse tree
	 */
	void enterVerbPhrase(FulibScenariosParser.VerbPhraseContext ctx);
	/**
	 * Exit a parse tree produced by {@link FulibScenariosParser#verbPhrase}.
	 * @param ctx the parse tree
	 */
	void exitVerbPhrase(FulibScenariosParser.VerbPhraseContext ctx);
	/**
	 * Enter a parse tree produced by {@link FulibScenariosParser#answerPhrase}.
	 * @param ctx the parse tree
	 */
	void enterAnswerPhrase(FulibScenariosParser.AnswerPhraseContext ctx);
	/**
	 * Exit a parse tree produced by {@link FulibScenariosParser#answerPhrase}.
	 * @param ctx the parse tree
	 */
	void exitAnswerPhrase(FulibScenariosParser.AnswerPhraseContext ctx);
	/**
	 * Enter a parse tree produced by {@link FulibScenariosParser#expectSentence}.
	 * @param ctx the parse tree
	 */
	void enterExpectSentence(FulibScenariosParser.ExpectSentenceContext ctx);
	/**
	 * Exit a parse tree produced by {@link FulibScenariosParser#expectSentence}.
	 * @param ctx the parse tree
	 */
	void exitExpectSentence(FulibScenariosParser.ExpectSentenceContext ctx);
	/**
	 * Enter a parse tree produced by {@link FulibScenariosParser#thatPhrase}.
	 * @param ctx the parse tree
	 */
	void enterThatPhrase(FulibScenariosParser.ThatPhraseContext ctx);
	/**
	 * Exit a parse tree produced by {@link FulibScenariosParser#thatPhrase}.
	 * @param ctx the parse tree
	 */
	void exitThatPhrase(FulibScenariosParser.ThatPhraseContext ctx);
	/**
	 * Enter a parse tree produced by {@link FulibScenariosParser#directSentence}.
	 * @param ctx the parse tree
	 */
	void enterDirectSentence(FulibScenariosParser.DirectSentenceContext ctx);
	/**
	 * Exit a parse tree produced by {@link FulibScenariosParser#directSentence}.
	 * @param ctx the parse tree
	 */
	void exitDirectSentence(FulibScenariosParser.DirectSentenceContext ctx);
	/**
	 * Enter a parse tree produced by {@link FulibScenariosParser#hasSentence}.
	 * @param ctx the parse tree
	 */
	void enterHasSentence(FulibScenariosParser.HasSentenceContext ctx);
	/**
	 * Exit a parse tree produced by {@link FulibScenariosParser#hasSentence}.
	 * @param ctx the parse tree
	 */
	void exitHasSentence(FulibScenariosParser.HasSentenceContext ctx);
	/**
	 * Enter a parse tree produced by the {@code UsualHasClause}
	 * labeled alternative in {@link FulibScenariosParser#hasClause}.
	 * @param ctx the parse tree
	 */
	void enterUsualHasClause(FulibScenariosParser.UsualHasClauseContext ctx);
	/**
	 * Exit a parse tree produced by the {@code UsualHasClause}
	 * labeled alternative in {@link FulibScenariosParser#hasClause}.
	 * @param ctx the parse tree
	 */
	void exitUsualHasClause(FulibScenariosParser.UsualHasClauseContext ctx);
	/**
	 * Enter a parse tree produced by the {@code NumberHasClause}
	 * labeled alternative in {@link FulibScenariosParser#hasClause}.
	 * @param ctx the parse tree
	 */
	void enterNumberHasClause(FulibScenariosParser.NumberHasClauseContext ctx);
	/**
	 * Exit a parse tree produced by the {@code NumberHasClause}
	 * labeled alternative in {@link FulibScenariosParser#hasClause}.
	 * @param ctx the parse tree
	 */
	void exitNumberHasClause(FulibScenariosParser.NumberHasClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link FulibScenariosParser#diagramSentence}.
	 * @param ctx the parse tree
	 */
	void enterDiagramSentence(FulibScenariosParser.DiagramSentenceContext ctx);
	/**
	 * Exit a parse tree produced by {@link FulibScenariosParser#diagramSentence}.
	 * @param ctx the parse tree
	 */
	void exitDiagramSentence(FulibScenariosParser.DiagramSentenceContext ctx);
	/**
	 * Enter a parse tree produced by the {@code UsualWithClause}
	 * labeled alternative in {@link FulibScenariosParser#withClause}.
	 * @param ctx the parse tree
	 */
	void enterUsualWithClause(FulibScenariosParser.UsualWithClauseContext ctx);
	/**
	 * Exit a parse tree produced by the {@code UsualWithClause}
	 * labeled alternative in {@link FulibScenariosParser#withClause}.
	 * @param ctx the parse tree
	 */
	void exitUsualWithClause(FulibScenariosParser.UsualWithClauseContext ctx);
	/**
	 * Enter a parse tree produced by the {@code NumberWithClause}
	 * labeled alternative in {@link FulibScenariosParser#withClause}.
	 * @param ctx the parse tree
	 */
	void enterNumberWithClause(FulibScenariosParser.NumberWithClauseContext ctx);
	/**
	 * Exit a parse tree produced by the {@code NumberWithClause}
	 * labeled alternative in {@link FulibScenariosParser#withClause}.
	 * @param ctx the parse tree
	 */
	void exitNumberWithClause(FulibScenariosParser.NumberWithClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link FulibScenariosParser#valueClause}.
	 * @param ctx the parse tree
	 */
	void enterValueClause(FulibScenariosParser.ValueClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link FulibScenariosParser#valueClause}.
	 * @param ctx the parse tree
	 */
	void exitValueClause(FulibScenariosParser.ValueClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link FulibScenariosParser#rangeClause}.
	 * @param ctx the parse tree
	 */
	void enterRangeClause(FulibScenariosParser.RangeClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link FulibScenariosParser#rangeClause}.
	 * @param ctx the parse tree
	 */
	void exitRangeClause(FulibScenariosParser.RangeClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link FulibScenariosParser#valueData}.
	 * @param ctx the parse tree
	 */
	void enterValueData(FulibScenariosParser.ValueDataContext ctx);
	/**
	 * Exit a parse tree produced by {@link FulibScenariosParser#valueData}.
	 * @param ctx the parse tree
	 */
	void exitValueData(FulibScenariosParser.ValueDataContext ctx);
	/**
	 * Enter a parse tree produced by {@link FulibScenariosParser#fileNameClause}.
	 * @param ctx the parse tree
	 */
	void enterFileNameClause(FulibScenariosParser.FileNameClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link FulibScenariosParser#fileNameClause}.
	 * @param ctx the parse tree
	 */
	void exitFileNameClause(FulibScenariosParser.FileNameClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link FulibScenariosParser#any}.
	 * @param ctx the parse tree
	 */
	void enterAny(FulibScenariosParser.AnyContext ctx);
	/**
	 * Exit a parse tree produced by {@link FulibScenariosParser#any}.
	 * @param ctx the parse tree
	 */
	void exitAny(FulibScenariosParser.AnyContext ctx);
	/**
	 * Enter a parse tree produced by {@link FulibScenariosParser#classDef}.
	 * @param ctx the parse tree
	 */
	void enterClassDef(FulibScenariosParser.ClassDefContext ctx);
	/**
	 * Exit a parse tree produced by {@link FulibScenariosParser#classDef}.
	 * @param ctx the parse tree
	 */
	void exitClassDef(FulibScenariosParser.ClassDefContext ctx);
	/**
	 * Enter a parse tree produced by {@link FulibScenariosParser#exampleValue}.
	 * @param ctx the parse tree
	 */
	void enterExampleValue(FulibScenariosParser.ExampleValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link FulibScenariosParser#exampleValue}.
	 * @param ctx the parse tree
	 */
	void exitExampleValue(FulibScenariosParser.ExampleValueContext ctx);
	/**
	 * Enter a parse tree produced by {@link FulibScenariosParser#attrDef}.
	 * @param ctx the parse tree
	 */
	void enterAttrDef(FulibScenariosParser.AttrDefContext ctx);
	/**
	 * Exit a parse tree produced by {@link FulibScenariosParser#attrDef}.
	 * @param ctx the parse tree
	 */
	void exitAttrDef(FulibScenariosParser.AttrDefContext ctx);
	/**
	 * Enter a parse tree produced by {@link FulibScenariosParser#roleDef}.
	 * @param ctx the parse tree
	 */
	void enterRoleDef(FulibScenariosParser.RoleDefContext ctx);
	/**
	 * Exit a parse tree produced by {@link FulibScenariosParser#roleDef}.
	 * @param ctx the parse tree
	 */
	void exitRoleDef(FulibScenariosParser.RoleDefContext ctx);
	/**
	 * Enter a parse tree produced by {@link FulibScenariosParser#cardDef}.
	 * @param ctx the parse tree
	 */
	void enterCardDef(FulibScenariosParser.CardDefContext ctx);
	/**
	 * Exit a parse tree produced by {@link FulibScenariosParser#cardDef}.
	 * @param ctx the parse tree
	 */
	void exitCardDef(FulibScenariosParser.CardDefContext ctx);
}