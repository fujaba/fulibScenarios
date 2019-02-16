// Generated from C:/Users/zuend/IdeaProjects/fulibScenarios/src/main/java/org/fulib/scenarios\FulibScenarios.g4 by ANTLR 4.7.2
package org.fulib.scenarios.compiler;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class FulibScenariosParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.7.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		A=18, NAME=19, NUMBER=20, WS=21, COMMENT=22, LINE_COMMENT=23;
	public static final int
		RULE_scenario = 0, RULE_title = 1, RULE_section = 2, RULE_sentence = 3, 
		RULE_thereSentence = 4, RULE_multiName = 5, RULE_isSentence = 6, RULE_directSentence = 7, 
		RULE_hasSentence = 8, RULE_diagramSentence = 9, RULE_withClause = 10, 
		RULE_valueClause = 11, RULE_valueData = 12, RULE_fileNameClause = 13, 
		RULE_any = 14;
	private static String[] makeRuleNames() {
		return new String[] {
			"scenario", "title", "section", "sentence", "thereSentence", "multiName", 
			"isSentence", "directSentence", "hasSentence", "diagramSentence", "withClause", 
			"valueClause", "valueData", "fileNameClause", "any"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'#'", "'##'", "'Setup'", "'There'", "'is'", "'.'", "'in'", "'has'", 
			"'!'", "'['", "']'", "'('", "')'", "'with'", "','", "'and'", "'/'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, "A", "NAME", "NUMBER", "WS", "COMMENT", 
			"LINE_COMMENT"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "FulibScenarios.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public FulibScenariosParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	public static class ScenarioContext extends ParserRuleContext {
		public TitleContext title() {
			return getRuleContext(TitleContext.class,0);
		}
		public List<SectionContext> section() {
			return getRuleContexts(SectionContext.class);
		}
		public SectionContext section(int i) {
			return getRuleContext(SectionContext.class,i);
		}
		public ScenarioContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_scenario; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FulibScenariosListener ) ((FulibScenariosListener)listener).enterScenario(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FulibScenariosListener ) ((FulibScenariosListener)listener).exitScenario(this);
		}
	}

	public final ScenarioContext scenario() throws RecognitionException {
		ScenarioContext _localctx = new ScenarioContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_scenario);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(30);
			title();
			setState(34);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__1) {
				{
				{
				setState(31);
				section();
				}
				}
				setState(36);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TitleContext extends ParserRuleContext {
		public List<AnyContext> any() {
			return getRuleContexts(AnyContext.class);
		}
		public AnyContext any(int i) {
			return getRuleContext(AnyContext.class,i);
		}
		public TitleContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_title; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FulibScenariosListener ) ((FulibScenariosListener)listener).enterTitle(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FulibScenariosListener ) ((FulibScenariosListener)listener).exitTitle(this);
		}
	}

	public final TitleContext title() throws RecognitionException {
		TitleContext _localctx = new TitleContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_title);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(37);
			match(T__0);
			setState(39); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(38);
				any();
				}
				}
				setState(41); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__5) | (1L << T__14) | (1L << A) | (1L << NAME) | (1L << NUMBER))) != 0) );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SectionContext extends ParserRuleContext {
		public List<SentenceContext> sentence() {
			return getRuleContexts(SentenceContext.class);
		}
		public SentenceContext sentence(int i) {
			return getRuleContext(SentenceContext.class,i);
		}
		public SectionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_section; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FulibScenariosListener ) ((FulibScenariosListener)listener).enterSection(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FulibScenariosListener ) ((FulibScenariosListener)listener).exitSection(this);
		}
	}

	public final SectionContext section() throws RecognitionException {
		SectionContext _localctx = new SectionContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_section);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(43);
			match(T__1);
			setState(44);
			match(T__2);
			setState(48);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__3) | (1L << T__8) | (1L << NAME))) != 0)) {
				{
				{
				setState(45);
				sentence();
				}
				}
				setState(50);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SentenceContext extends ParserRuleContext {
		public ThereSentenceContext thereSentence() {
			return getRuleContext(ThereSentenceContext.class,0);
		}
		public DirectSentenceContext directSentence() {
			return getRuleContext(DirectSentenceContext.class,0);
		}
		public IsSentenceContext isSentence() {
			return getRuleContext(IsSentenceContext.class,0);
		}
		public HasSentenceContext hasSentence() {
			return getRuleContext(HasSentenceContext.class,0);
		}
		public DiagramSentenceContext diagramSentence() {
			return getRuleContext(DiagramSentenceContext.class,0);
		}
		public SentenceContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_sentence; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FulibScenariosListener ) ((FulibScenariosListener)listener).enterSentence(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FulibScenariosListener ) ((FulibScenariosListener)listener).exitSentence(this);
		}
	}

	public final SentenceContext sentence() throws RecognitionException {
		SentenceContext _localctx = new SentenceContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_sentence);
		try {
			setState(56);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,3,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(51);
				thereSentence();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(52);
				directSentence();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(53);
				isSentence();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(54);
				hasSentence();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(55);
				diagramSentence();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ThereSentenceContext extends ParserRuleContext {
		public MultiNameContext objectName;
		public MultiNameContext className;
		public List<TerminalNode> A() { return getTokens(FulibScenariosParser.A); }
		public TerminalNode A(int i) {
			return getToken(FulibScenariosParser.A, i);
		}
		public List<MultiNameContext> multiName() {
			return getRuleContexts(MultiNameContext.class);
		}
		public MultiNameContext multiName(int i) {
			return getRuleContext(MultiNameContext.class,i);
		}
		public List<WithClauseContext> withClause() {
			return getRuleContexts(WithClauseContext.class);
		}
		public WithClauseContext withClause(int i) {
			return getRuleContext(WithClauseContext.class,i);
		}
		public ThereSentenceContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_thereSentence; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FulibScenariosListener ) ((FulibScenariosListener)listener).enterThereSentence(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FulibScenariosListener ) ((FulibScenariosListener)listener).exitThereSentence(this);
		}
	}

	public final ThereSentenceContext thereSentence() throws RecognitionException {
		ThereSentenceContext _localctx = new ThereSentenceContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_thereSentence);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(58);
			match(T__3);
			setState(59);
			match(T__4);
			setState(61);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,4,_ctx) ) {
			case 1:
				{
				setState(60);
				match(A);
				}
				break;
			}
			setState(64);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==NAME) {
				{
				setState(63);
				((ThereSentenceContext)_localctx).objectName = multiName();
				}
			}

			setState(66);
			match(A);
			setState(67);
			((ThereSentenceContext)_localctx).className = multiName();
			setState(71);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__13 || _la==NAME) {
				{
				{
				setState(68);
				withClause();
				}
				}
				setState(73);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(74);
			match(T__5);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MultiNameContext extends ParserRuleContext {
		public List<TerminalNode> NAME() { return getTokens(FulibScenariosParser.NAME); }
		public TerminalNode NAME(int i) {
			return getToken(FulibScenariosParser.NAME, i);
		}
		public MultiNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_multiName; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FulibScenariosListener ) ((FulibScenariosListener)listener).enterMultiName(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FulibScenariosListener ) ((FulibScenariosListener)listener).exitMultiName(this);
		}
	}

	public final MultiNameContext multiName() throws RecognitionException {
		MultiNameContext _localctx = new MultiNameContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_multiName);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(77); 
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					{
					setState(76);
					match(NAME);
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(79); 
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,7,_ctx);
			} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IsSentenceContext extends ParserRuleContext {
		public MultiNameContext objectName;
		public Token attrName;
		public ValueDataContext value;
		public MultiNameContext multiName() {
			return getRuleContext(MultiNameContext.class,0);
		}
		public ValueDataContext valueData() {
			return getRuleContext(ValueDataContext.class,0);
		}
		public TerminalNode A() { return getToken(FulibScenariosParser.A, 0); }
		public IsSentenceContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_isSentence; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FulibScenariosListener ) ((FulibScenariosListener)listener).enterIsSentence(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FulibScenariosListener ) ((FulibScenariosListener)listener).exitIsSentence(this);
		}
	}

	public final IsSentenceContext isSentence() throws RecognitionException {
		IsSentenceContext _localctx = new IsSentenceContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_isSentence);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(81);
			((IsSentenceContext)_localctx).objectName = multiName();
			setState(82);
			match(T__4);
			setState(83);
			((IsSentenceContext)_localctx).attrName = match(T__6);
			setState(85);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==A) {
				{
				setState(84);
				match(A);
				}
			}

			setState(87);
			((IsSentenceContext)_localctx).value = valueData();
			setState(88);
			match(T__5);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DirectSentenceContext extends ParserRuleContext {
		public MultiNameContext objectName;
		public MultiNameContext className;
		public List<MultiNameContext> multiName() {
			return getRuleContexts(MultiNameContext.class);
		}
		public MultiNameContext multiName(int i) {
			return getRuleContext(MultiNameContext.class,i);
		}
		public TerminalNode A() { return getToken(FulibScenariosParser.A, 0); }
		public List<WithClauseContext> withClause() {
			return getRuleContexts(WithClauseContext.class);
		}
		public WithClauseContext withClause(int i) {
			return getRuleContext(WithClauseContext.class,i);
		}
		public DirectSentenceContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_directSentence; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FulibScenariosListener ) ((FulibScenariosListener)listener).enterDirectSentence(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FulibScenariosListener ) ((FulibScenariosListener)listener).exitDirectSentence(this);
		}
	}

	public final DirectSentenceContext directSentence() throws RecognitionException {
		DirectSentenceContext _localctx = new DirectSentenceContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_directSentence);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(90);
			((DirectSentenceContext)_localctx).objectName = multiName();
			setState(91);
			match(T__4);
			setState(93);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==A) {
				{
				setState(92);
				match(A);
				}
			}

			setState(95);
			((DirectSentenceContext)_localctx).className = multiName();
			setState(99);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__13 || _la==NAME) {
				{
				{
				setState(96);
				withClause();
				}
				}
				setState(101);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(102);
			match(T__5);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class HasSentenceContext extends ParserRuleContext {
		public MultiNameContext objectName;
		public Token attrName;
		public ValueDataContext value;
		public MultiNameContext multiName() {
			return getRuleContext(MultiNameContext.class,0);
		}
		public TerminalNode NAME() { return getToken(FulibScenariosParser.NAME, 0); }
		public ValueDataContext valueData() {
			return getRuleContext(ValueDataContext.class,0);
		}
		public HasSentenceContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_hasSentence; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FulibScenariosListener ) ((FulibScenariosListener)listener).enterHasSentence(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FulibScenariosListener ) ((FulibScenariosListener)listener).exitHasSentence(this);
		}
	}

	public final HasSentenceContext hasSentence() throws RecognitionException {
		HasSentenceContext _localctx = new HasSentenceContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_hasSentence);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(104);
			((HasSentenceContext)_localctx).objectName = multiName();
			setState(105);
			match(T__7);
			setState(106);
			((HasSentenceContext)_localctx).attrName = match(NAME);
			setState(107);
			((HasSentenceContext)_localctx).value = valueData();
			setState(108);
			match(T__5);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DiagramSentenceContext extends ParserRuleContext {
		public Token type;
		public FileNameClauseContext fileName;
		public TerminalNode NAME() { return getToken(FulibScenariosParser.NAME, 0); }
		public FileNameClauseContext fileNameClause() {
			return getRuleContext(FileNameClauseContext.class,0);
		}
		public DiagramSentenceContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_diagramSentence; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FulibScenariosListener ) ((FulibScenariosListener)listener).enterDiagramSentence(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FulibScenariosListener ) ((FulibScenariosListener)listener).exitDiagramSentence(this);
		}
	}

	public final DiagramSentenceContext diagramSentence() throws RecognitionException {
		DiagramSentenceContext _localctx = new DiagramSentenceContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_diagramSentence);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(110);
			match(T__8);
			setState(111);
			match(T__9);
			setState(112);
			((DiagramSentenceContext)_localctx).type = match(NAME);
			setState(113);
			match(T__10);
			setState(114);
			match(T__11);
			setState(115);
			((DiagramSentenceContext)_localctx).fileName = fileNameClause();
			setState(116);
			match(T__12);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class WithClauseContext extends ParserRuleContext {
		public WithClauseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_withClause; }
	 
		public WithClauseContext() { }
		public void copyFrom(WithClauseContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class UsualWithClauseContext extends WithClauseContext {
		public Token attrName;
		public ValueClauseContext attrValue;
		public TerminalNode NAME() { return getToken(FulibScenariosParser.NAME, 0); }
		public ValueClauseContext valueClause() {
			return getRuleContext(ValueClauseContext.class,0);
		}
		public UsualWithClauseContext(WithClauseContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FulibScenariosListener ) ((FulibScenariosListener)listener).enterUsualWithClause(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FulibScenariosListener ) ((FulibScenariosListener)listener).exitUsualWithClause(this);
		}
	}
	public static class NumberWithClauseContext extends WithClauseContext {
		public Token value;
		public MultiNameContext attrName;
		public TerminalNode NUMBER() { return getToken(FulibScenariosParser.NUMBER, 0); }
		public MultiNameContext multiName() {
			return getRuleContext(MultiNameContext.class,0);
		}
		public NumberWithClauseContext(WithClauseContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FulibScenariosListener ) ((FulibScenariosListener)listener).enterNumberWithClause(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FulibScenariosListener ) ((FulibScenariosListener)listener).exitNumberWithClause(this);
		}
	}

	public final WithClauseContext withClause() throws RecognitionException {
		WithClauseContext _localctx = new WithClauseContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_withClause);
		int _la;
		try {
			setState(132);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,14,_ctx) ) {
			case 1:
				_localctx = new UsualWithClauseContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(119);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__13) {
					{
					setState(118);
					match(T__13);
					}
				}

				setState(121);
				((UsualWithClauseContext)_localctx).attrName = match(NAME);
				setState(122);
				((UsualWithClauseContext)_localctx).attrValue = valueClause();
				}
				break;
			case 2:
				_localctx = new NumberWithClauseContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(123);
				match(T__13);
				setState(124);
				((NumberWithClauseContext)_localctx).value = match(NUMBER);
				setState(125);
				((NumberWithClauseContext)_localctx).attrName = multiName();
				setState(127);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__14) {
					{
					setState(126);
					match(T__14);
					}
				}

				setState(130);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__15) {
					{
					setState(129);
					match(T__15);
					}
				}

				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ValueClauseContext extends ParserRuleContext {
		public List<ValueDataContext> valueData() {
			return getRuleContexts(ValueDataContext.class);
		}
		public ValueDataContext valueData(int i) {
			return getRuleContext(ValueDataContext.class,i);
		}
		public List<TerminalNode> A() { return getTokens(FulibScenariosParser.A); }
		public TerminalNode A(int i) {
			return getToken(FulibScenariosParser.A, i);
		}
		public ValueClauseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_valueClause; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FulibScenariosListener ) ((FulibScenariosListener)listener).enterValueClause(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FulibScenariosListener ) ((FulibScenariosListener)listener).exitValueClause(this);
		}
	}

	public final ValueClauseContext valueClause() throws RecognitionException {
		ValueClauseContext _localctx = new ValueClauseContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_valueClause);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(144); 
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					{
					setState(135);
					_errHandler.sync(this);
					_la = _input.LA(1);
					if (_la==A) {
						{
						setState(134);
						match(A);
						}
					}

					setState(137);
					valueData();
					setState(139);
					_errHandler.sync(this);
					_la = _input.LA(1);
					if (_la==T__14) {
						{
						setState(138);
						match(T__14);
						}
					}

					setState(142);
					_errHandler.sync(this);
					_la = _input.LA(1);
					if (_la==T__15) {
						{
						setState(141);
						match(T__15);
						}
					}

					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(146); 
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,18,_ctx);
			} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ValueDataContext extends ParserRuleContext {
		public List<TerminalNode> NAME() { return getTokens(FulibScenariosParser.NAME); }
		public TerminalNode NAME(int i) {
			return getToken(FulibScenariosParser.NAME, i);
		}
		public List<TerminalNode> NUMBER() { return getTokens(FulibScenariosParser.NUMBER); }
		public TerminalNode NUMBER(int i) {
			return getToken(FulibScenariosParser.NUMBER, i);
		}
		public ValueDataContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_valueData; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FulibScenariosListener ) ((FulibScenariosListener)listener).enterValueData(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FulibScenariosListener ) ((FulibScenariosListener)listener).exitValueData(this);
		}
	}

	public final ValueDataContext valueData() throws RecognitionException {
		ValueDataContext _localctx = new ValueDataContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_valueData);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(149); 
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					{
					setState(148);
					_la = _input.LA(1);
					if ( !(_la==NAME || _la==NUMBER) ) {
					_errHandler.recoverInline(this);
					}
					else {
						if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
						_errHandler.reportMatch(this);
						consume();
					}
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(151); 
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,19,_ctx);
			} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FileNameClauseContext extends ParserRuleContext {
		public List<TerminalNode> NAME() { return getTokens(FulibScenariosParser.NAME); }
		public TerminalNode NAME(int i) {
			return getToken(FulibScenariosParser.NAME, i);
		}
		public FileNameClauseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_fileNameClause; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FulibScenariosListener ) ((FulibScenariosListener)listener).enterFileNameClause(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FulibScenariosListener ) ((FulibScenariosListener)listener).exitFileNameClause(this);
		}
	}

	public final FileNameClauseContext fileNameClause() throws RecognitionException {
		FileNameClauseContext _localctx = new FileNameClauseContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_fileNameClause);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(154); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(153);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__5) | (1L << T__16) | (1L << NAME))) != 0)) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
				}
				setState(156); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__5) | (1L << T__16) | (1L << NAME))) != 0) );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AnyContext extends ParserRuleContext {
		public TerminalNode NUMBER() { return getToken(FulibScenariosParser.NUMBER, 0); }
		public TerminalNode NAME() { return getToken(FulibScenariosParser.NAME, 0); }
		public TerminalNode A() { return getToken(FulibScenariosParser.A, 0); }
		public AnyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_any; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FulibScenariosListener ) ((FulibScenariosListener)listener).enterAny(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FulibScenariosListener ) ((FulibScenariosListener)listener).exitAny(this);
		}
	}

	public final AnyContext any() throws RecognitionException {
		AnyContext _localctx = new AnyContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_any);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(158);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__5) | (1L << T__14) | (1L << A) | (1L << NAME) | (1L << NUMBER))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\31\u00a3\4\2\t\2"+
		"\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\3\2\3\2\7\2#\n\2\f"+
		"\2\16\2&\13\2\3\3\3\3\6\3*\n\3\r\3\16\3+\3\4\3\4\3\4\7\4\61\n\4\f\4\16"+
		"\4\64\13\4\3\5\3\5\3\5\3\5\3\5\5\5;\n\5\3\6\3\6\3\6\5\6@\n\6\3\6\5\6C"+
		"\n\6\3\6\3\6\3\6\7\6H\n\6\f\6\16\6K\13\6\3\6\3\6\3\7\6\7P\n\7\r\7\16\7"+
		"Q\3\b\3\b\3\b\3\b\5\bX\n\b\3\b\3\b\3\b\3\t\3\t\3\t\5\t`\n\t\3\t\3\t\7"+
		"\td\n\t\f\t\16\tg\13\t\3\t\3\t\3\n\3\n\3\n\3\n\3\n\3\n\3\13\3\13\3\13"+
		"\3\13\3\13\3\13\3\13\3\13\3\f\5\fz\n\f\3\f\3\f\3\f\3\f\3\f\3\f\5\f\u0082"+
		"\n\f\3\f\5\f\u0085\n\f\5\f\u0087\n\f\3\r\5\r\u008a\n\r\3\r\3\r\5\r\u008e"+
		"\n\r\3\r\5\r\u0091\n\r\6\r\u0093\n\r\r\r\16\r\u0094\3\16\6\16\u0098\n"+
		"\16\r\16\16\16\u0099\3\17\6\17\u009d\n\17\r\17\16\17\u009e\3\20\3\20\3"+
		"\20\2\2\21\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36\2\5\3\2\25\26\5\2\b"+
		"\b\23\23\25\25\5\2\b\b\21\21\24\26\2\u00ab\2 \3\2\2\2\4\'\3\2\2\2\6-\3"+
		"\2\2\2\b:\3\2\2\2\n<\3\2\2\2\fO\3\2\2\2\16S\3\2\2\2\20\\\3\2\2\2\22j\3"+
		"\2\2\2\24p\3\2\2\2\26\u0086\3\2\2\2\30\u0092\3\2\2\2\32\u0097\3\2\2\2"+
		"\34\u009c\3\2\2\2\36\u00a0\3\2\2\2 $\5\4\3\2!#\5\6\4\2\"!\3\2\2\2#&\3"+
		"\2\2\2$\"\3\2\2\2$%\3\2\2\2%\3\3\2\2\2&$\3\2\2\2\')\7\3\2\2(*\5\36\20"+
		"\2)(\3\2\2\2*+\3\2\2\2+)\3\2\2\2+,\3\2\2\2,\5\3\2\2\2-.\7\4\2\2.\62\7"+
		"\5\2\2/\61\5\b\5\2\60/\3\2\2\2\61\64\3\2\2\2\62\60\3\2\2\2\62\63\3\2\2"+
		"\2\63\7\3\2\2\2\64\62\3\2\2\2\65;\5\n\6\2\66;\5\20\t\2\67;\5\16\b\28;"+
		"\5\22\n\29;\5\24\13\2:\65\3\2\2\2:\66\3\2\2\2:\67\3\2\2\2:8\3\2\2\2:9"+
		"\3\2\2\2;\t\3\2\2\2<=\7\6\2\2=?\7\7\2\2>@\7\24\2\2?>\3\2\2\2?@\3\2\2\2"+
		"@B\3\2\2\2AC\5\f\7\2BA\3\2\2\2BC\3\2\2\2CD\3\2\2\2DE\7\24\2\2EI\5\f\7"+
		"\2FH\5\26\f\2GF\3\2\2\2HK\3\2\2\2IG\3\2\2\2IJ\3\2\2\2JL\3\2\2\2KI\3\2"+
		"\2\2LM\7\b\2\2M\13\3\2\2\2NP\7\25\2\2ON\3\2\2\2PQ\3\2\2\2QO\3\2\2\2QR"+
		"\3\2\2\2R\r\3\2\2\2ST\5\f\7\2TU\7\7\2\2UW\7\t\2\2VX\7\24\2\2WV\3\2\2\2"+
		"WX\3\2\2\2XY\3\2\2\2YZ\5\32\16\2Z[\7\b\2\2[\17\3\2\2\2\\]\5\f\7\2]_\7"+
		"\7\2\2^`\7\24\2\2_^\3\2\2\2_`\3\2\2\2`a\3\2\2\2ae\5\f\7\2bd\5\26\f\2c"+
		"b\3\2\2\2dg\3\2\2\2ec\3\2\2\2ef\3\2\2\2fh\3\2\2\2ge\3\2\2\2hi\7\b\2\2"+
		"i\21\3\2\2\2jk\5\f\7\2kl\7\n\2\2lm\7\25\2\2mn\5\32\16\2no\7\b\2\2o\23"+
		"\3\2\2\2pq\7\13\2\2qr\7\f\2\2rs\7\25\2\2st\7\r\2\2tu\7\16\2\2uv\5\34\17"+
		"\2vw\7\17\2\2w\25\3\2\2\2xz\7\20\2\2yx\3\2\2\2yz\3\2\2\2z{\3\2\2\2{|\7"+
		"\25\2\2|\u0087\5\30\r\2}~\7\20\2\2~\177\7\26\2\2\177\u0081\5\f\7\2\u0080"+
		"\u0082\7\21\2\2\u0081\u0080\3\2\2\2\u0081\u0082\3\2\2\2\u0082\u0084\3"+
		"\2\2\2\u0083\u0085\7\22\2\2\u0084\u0083\3\2\2\2\u0084\u0085\3\2\2\2\u0085"+
		"\u0087\3\2\2\2\u0086y\3\2\2\2\u0086}\3\2\2\2\u0087\27\3\2\2\2\u0088\u008a"+
		"\7\24\2\2\u0089\u0088\3\2\2\2\u0089\u008a\3\2\2\2\u008a\u008b\3\2\2\2"+
		"\u008b\u008d\5\32\16\2\u008c\u008e\7\21\2\2\u008d\u008c\3\2\2\2\u008d"+
		"\u008e\3\2\2\2\u008e\u0090\3\2\2\2\u008f\u0091\7\22\2\2\u0090\u008f\3"+
		"\2\2\2\u0090\u0091\3\2\2\2\u0091\u0093\3\2\2\2\u0092\u0089\3\2\2\2\u0093"+
		"\u0094\3\2\2\2\u0094\u0092\3\2\2\2\u0094\u0095\3\2\2\2\u0095\31\3\2\2"+
		"\2\u0096\u0098\t\2\2\2\u0097\u0096\3\2\2\2\u0098\u0099\3\2\2\2\u0099\u0097"+
		"\3\2\2\2\u0099\u009a\3\2\2\2\u009a\33\3\2\2\2\u009b\u009d\t\3\2\2\u009c"+
		"\u009b\3\2\2\2\u009d\u009e\3\2\2\2\u009e\u009c\3\2\2\2\u009e\u009f\3\2"+
		"\2\2\u009f\35\3\2\2\2\u00a0\u00a1\t\4\2\2\u00a1\37\3\2\2\2\27$+\62:?B"+
		"IQW_ey\u0081\u0084\u0086\u0089\u008d\u0090\u0094\u0099\u009e";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}