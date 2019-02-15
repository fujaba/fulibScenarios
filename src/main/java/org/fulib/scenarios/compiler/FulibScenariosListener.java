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
	 * Enter a parse tree produced by {@link FulibScenariosParser#title}.
	 * @param ctx the parse tree
	 */
	void enterTitle(FulibScenariosParser.TitleContext ctx);
	/**
	 * Exit a parse tree produced by {@link FulibScenariosParser#title}.
	 * @param ctx the parse tree
	 */
	void exitTitle(FulibScenariosParser.TitleContext ctx);
	/**
	 * Enter a parse tree produced by {@link FulibScenariosParser#section}.
	 * @param ctx the parse tree
	 */
	void enterSection(FulibScenariosParser.SectionContext ctx);
	/**
	 * Exit a parse tree produced by {@link FulibScenariosParser#section}.
	 * @param ctx the parse tree
	 */
	void exitSection(FulibScenariosParser.SectionContext ctx);
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
	 * Enter a parse tree produced by {@link FulibScenariosParser#isSentence}.
	 * @param ctx the parse tree
	 */
	void enterIsSentence(FulibScenariosParser.IsSentenceContext ctx);
	/**
	 * Exit a parse tree produced by {@link FulibScenariosParser#isSentence}.
	 * @param ctx the parse tree
	 */
	void exitIsSentence(FulibScenariosParser.IsSentenceContext ctx);
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
	 * Enter a parse tree produced by {@link FulibScenariosParser#any}.
	 * @param ctx the parse tree
	 */
	void enterAny(FulibScenariosParser.AnyContext ctx);
	/**
	 * Exit a parse tree produced by {@link FulibScenariosParser#any}.
	 * @param ctx the parse tree
	 */
	void exitAny(FulibScenariosParser.AnyContext ctx);
}