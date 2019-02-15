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
		T__9=10, T__10=11, A=12, NAME=13, NUMBER=14, WS=15, COMMENT=16, LINE_COMMENT=17;
	public static final int
		RULE_scenario = 0, RULE_title = 1, RULE_section = 2, RULE_sentence = 3, 
		RULE_thereSentence = 4, RULE_multiName = 5, RULE_isSentence = 6, RULE_directSentence = 7, 
		RULE_hasSentence = 8, RULE_withClause = 9, RULE_valueClause = 10, RULE_valueData = 11, 
		RULE_any = 12;
	private static String[] makeRuleNames() {
		return new String[] {
			"scenario", "title", "section", "sentence", "thereSentence", "multiName", 
			"isSentence", "directSentence", "hasSentence", "withClause", "valueClause", 
			"valueData", "any"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'#'", "'##'", "'Setup'", "'There'", "'is'", "'.'", "'in'", "'has'", 
			"'with'", "','", "'and'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			"A", "NAME", "NUMBER", "WS", "COMMENT", "LINE_COMMENT"
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
			setState(26);
			title();
			setState(30);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__1) {
				{
				{
				setState(27);
				section();
				}
				}
				setState(32);
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
			setState(33);
			match(T__0);
			setState(35); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(34);
				any();
				}
				}
				setState(37); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__5) | (1L << T__9) | (1L << A) | (1L << NAME) | (1L << NUMBER))) != 0) );
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
			setState(39);
			match(T__1);
			setState(40);
			match(T__2);
			setState(44);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__3 || _la==NAME) {
				{
				{
				setState(41);
				sentence();
				}
				}
				setState(46);
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
			setState(51);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,3,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(47);
				thereSentence();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(48);
				directSentence();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(49);
				isSentence();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(50);
				hasSentence();
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
			setState(53);
			match(T__3);
			setState(54);
			match(T__4);
			setState(56);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,4,_ctx) ) {
			case 1:
				{
				setState(55);
				match(A);
				}
				break;
			}
			setState(59);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==NAME) {
				{
				setState(58);
				((ThereSentenceContext)_localctx).objectName = multiName();
				}
			}

			setState(61);
			match(A);
			setState(62);
			((ThereSentenceContext)_localctx).className = multiName();
			setState(66);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__8 || _la==NAME) {
				{
				{
				setState(63);
				withClause();
				}
				}
				setState(68);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(69);
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
			setState(72); 
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					{
					setState(71);
					match(NAME);
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(74); 
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
			setState(76);
			((IsSentenceContext)_localctx).objectName = multiName();
			setState(77);
			match(T__4);
			setState(78);
			((IsSentenceContext)_localctx).attrName = match(T__6);
			setState(80);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==A) {
				{
				setState(79);
				match(A);
				}
			}

			setState(82);
			((IsSentenceContext)_localctx).value = valueData();
			setState(83);
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
			setState(85);
			((DirectSentenceContext)_localctx).objectName = multiName();
			setState(86);
			match(T__4);
			setState(88);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==A) {
				{
				setState(87);
				match(A);
				}
			}

			setState(90);
			((DirectSentenceContext)_localctx).className = multiName();
			setState(94);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__8 || _la==NAME) {
				{
				{
				setState(91);
				withClause();
				}
				}
				setState(96);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(97);
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
			setState(99);
			((HasSentenceContext)_localctx).objectName = multiName();
			setState(100);
			match(T__7);
			setState(101);
			((HasSentenceContext)_localctx).attrName = match(NAME);
			setState(102);
			((HasSentenceContext)_localctx).value = valueData();
			setState(103);
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
		enterRule(_localctx, 18, RULE_withClause);
		int _la;
		try {
			setState(119);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,14,_ctx) ) {
			case 1:
				_localctx = new UsualWithClauseContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(106);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__8) {
					{
					setState(105);
					match(T__8);
					}
				}

				setState(108);
				((UsualWithClauseContext)_localctx).attrName = match(NAME);
				setState(109);
				((UsualWithClauseContext)_localctx).attrValue = valueClause();
				}
				break;
			case 2:
				_localctx = new NumberWithClauseContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(110);
				match(T__8);
				setState(111);
				((NumberWithClauseContext)_localctx).value = match(NUMBER);
				setState(112);
				((NumberWithClauseContext)_localctx).attrName = multiName();
				setState(114);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__9) {
					{
					setState(113);
					match(T__9);
					}
				}

				setState(117);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__10) {
					{
					setState(116);
					match(T__10);
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
		public ValueDataContext value;
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
		enterRule(_localctx, 20, RULE_valueClause);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(131); 
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					{
					setState(122);
					_errHandler.sync(this);
					_la = _input.LA(1);
					if (_la==A) {
						{
						setState(121);
						match(A);
						}
					}

					setState(124);
					((ValueClauseContext)_localctx).value = valueData();
					setState(126);
					_errHandler.sync(this);
					_la = _input.LA(1);
					if (_la==T__9) {
						{
						setState(125);
						match(T__9);
						}
					}

					setState(129);
					_errHandler.sync(this);
					_la = _input.LA(1);
					if (_la==T__10) {
						{
						setState(128);
						match(T__10);
						}
					}

					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(133); 
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
		enterRule(_localctx, 22, RULE_valueData);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(136); 
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					{
					setState(135);
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
				setState(138); 
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
		enterRule(_localctx, 24, RULE_any);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(140);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__5) | (1L << T__9) | (1L << A) | (1L << NAME) | (1L << NUMBER))) != 0)) ) {
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\23\u0091\4\2\t\2"+
		"\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\3\2\3\2\7\2\37\n\2\f\2\16\2\"\13\2\3\3"+
		"\3\3\6\3&\n\3\r\3\16\3\'\3\4\3\4\3\4\7\4-\n\4\f\4\16\4\60\13\4\3\5\3\5"+
		"\3\5\3\5\5\5\66\n\5\3\6\3\6\3\6\5\6;\n\6\3\6\5\6>\n\6\3\6\3\6\3\6\7\6"+
		"C\n\6\f\6\16\6F\13\6\3\6\3\6\3\7\6\7K\n\7\r\7\16\7L\3\b\3\b\3\b\3\b\5"+
		"\bS\n\b\3\b\3\b\3\b\3\t\3\t\3\t\5\t[\n\t\3\t\3\t\7\t_\n\t\f\t\16\tb\13"+
		"\t\3\t\3\t\3\n\3\n\3\n\3\n\3\n\3\n\3\13\5\13m\n\13\3\13\3\13\3\13\3\13"+
		"\3\13\3\13\5\13u\n\13\3\13\5\13x\n\13\5\13z\n\13\3\f\5\f}\n\f\3\f\3\f"+
		"\5\f\u0081\n\f\3\f\5\f\u0084\n\f\6\f\u0086\n\f\r\f\16\f\u0087\3\r\6\r"+
		"\u008b\n\r\r\r\16\r\u008c\3\16\3\16\3\16\2\2\17\2\4\6\b\n\f\16\20\22\24"+
		"\26\30\32\2\4\3\2\17\20\5\2\b\b\f\f\16\20\2\u0099\2\34\3\2\2\2\4#\3\2"+
		"\2\2\6)\3\2\2\2\b\65\3\2\2\2\n\67\3\2\2\2\fJ\3\2\2\2\16N\3\2\2\2\20W\3"+
		"\2\2\2\22e\3\2\2\2\24y\3\2\2\2\26\u0085\3\2\2\2\30\u008a\3\2\2\2\32\u008e"+
		"\3\2\2\2\34 \5\4\3\2\35\37\5\6\4\2\36\35\3\2\2\2\37\"\3\2\2\2 \36\3\2"+
		"\2\2 !\3\2\2\2!\3\3\2\2\2\" \3\2\2\2#%\7\3\2\2$&\5\32\16\2%$\3\2\2\2&"+
		"\'\3\2\2\2\'%\3\2\2\2\'(\3\2\2\2(\5\3\2\2\2)*\7\4\2\2*.\7\5\2\2+-\5\b"+
		"\5\2,+\3\2\2\2-\60\3\2\2\2.,\3\2\2\2./\3\2\2\2/\7\3\2\2\2\60.\3\2\2\2"+
		"\61\66\5\n\6\2\62\66\5\20\t\2\63\66\5\16\b\2\64\66\5\22\n\2\65\61\3\2"+
		"\2\2\65\62\3\2\2\2\65\63\3\2\2\2\65\64\3\2\2\2\66\t\3\2\2\2\678\7\6\2"+
		"\28:\7\7\2\29;\7\16\2\2:9\3\2\2\2:;\3\2\2\2;=\3\2\2\2<>\5\f\7\2=<\3\2"+
		"\2\2=>\3\2\2\2>?\3\2\2\2?@\7\16\2\2@D\5\f\7\2AC\5\24\13\2BA\3\2\2\2CF"+
		"\3\2\2\2DB\3\2\2\2DE\3\2\2\2EG\3\2\2\2FD\3\2\2\2GH\7\b\2\2H\13\3\2\2\2"+
		"IK\7\17\2\2JI\3\2\2\2KL\3\2\2\2LJ\3\2\2\2LM\3\2\2\2M\r\3\2\2\2NO\5\f\7"+
		"\2OP\7\7\2\2PR\7\t\2\2QS\7\16\2\2RQ\3\2\2\2RS\3\2\2\2ST\3\2\2\2TU\5\30"+
		"\r\2UV\7\b\2\2V\17\3\2\2\2WX\5\f\7\2XZ\7\7\2\2Y[\7\16\2\2ZY\3\2\2\2Z["+
		"\3\2\2\2[\\\3\2\2\2\\`\5\f\7\2]_\5\24\13\2^]\3\2\2\2_b\3\2\2\2`^\3\2\2"+
		"\2`a\3\2\2\2ac\3\2\2\2b`\3\2\2\2cd\7\b\2\2d\21\3\2\2\2ef\5\f\7\2fg\7\n"+
		"\2\2gh\7\17\2\2hi\5\30\r\2ij\7\b\2\2j\23\3\2\2\2km\7\13\2\2lk\3\2\2\2"+
		"lm\3\2\2\2mn\3\2\2\2no\7\17\2\2oz\5\26\f\2pq\7\13\2\2qr\7\20\2\2rt\5\f"+
		"\7\2su\7\f\2\2ts\3\2\2\2tu\3\2\2\2uw\3\2\2\2vx\7\r\2\2wv\3\2\2\2wx\3\2"+
		"\2\2xz\3\2\2\2yl\3\2\2\2yp\3\2\2\2z\25\3\2\2\2{}\7\16\2\2|{\3\2\2\2|}"+
		"\3\2\2\2}~\3\2\2\2~\u0080\5\30\r\2\177\u0081\7\f\2\2\u0080\177\3\2\2\2"+
		"\u0080\u0081\3\2\2\2\u0081\u0083\3\2\2\2\u0082\u0084\7\r\2\2\u0083\u0082"+
		"\3\2\2\2\u0083\u0084\3\2\2\2\u0084\u0086\3\2\2\2\u0085|\3\2\2\2\u0086"+
		"\u0087\3\2\2\2\u0087\u0085\3\2\2\2\u0087\u0088\3\2\2\2\u0088\27\3\2\2"+
		"\2\u0089\u008b\t\2\2\2\u008a\u0089\3\2\2\2\u008b\u008c\3\2\2\2\u008c\u008a"+
		"\3\2\2\2\u008c\u008d\3\2\2\2\u008d\31\3\2\2\2\u008e\u008f\t\3\2\2\u008f"+
		"\33\3\2\2\2\26 \'.\65:=DLRZ`ltwy|\u0080\u0083\u0087\u008c";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}