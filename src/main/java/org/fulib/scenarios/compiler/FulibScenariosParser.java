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
		T__17=18, T__18=19, T__19=20, T__20=21, T__21=22, T__22=23, A=24, NAME=25, 
		NUMBER=26, WS=27, COMMENT=28, LINE_COMMENT=29;
	public static final int
		RULE_scenario = 0, RULE_title = 1, RULE_section = 2, RULE_sentence = 3, 
		RULE_thereSentence = 4, RULE_multiName = 5, RULE_isSentence = 6, RULE_directSentence = 7, 
		RULE_hasSentence = 8, RULE_diagramSentence = 9, RULE_withClause = 10, 
		RULE_valueClause = 11, RULE_valueData = 12, RULE_fileNameClause = 13, 
		RULE_any = 14, RULE_classDef = 15, RULE_exampleValue = 16, RULE_attrDef = 17, 
		RULE_roleDef = 18, RULE_cardDef = 19;
	private static String[] makeRuleNames() {
		return new String[] {
			"scenario", "title", "section", "sentence", "thereSentence", "multiName", 
			"isSentence", "directSentence", "hasSentence", "diagramSentence", "withClause", 
			"valueClause", "valueData", "fileNameClause", "any", "classDef", "exampleValue", 
			"attrDef", "roleDef", "cardDef"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'#'", "'Register'", "'Scenario'", "'Setup'", "'There'", "'is'", 
			"'.'", "'in'", "'has'", "'!'", "'['", "']'", "'('", "')'", "'with'", 
			"','", "'and'", "'/'", "'e.g.'", "'+'", "'cf.'", "'one'", "'many'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
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
		public List<ClassDefContext> classDef() {
			return getRuleContexts(ClassDefContext.class);
		}
		public ClassDefContext classDef(int i) {
			return getRuleContext(ClassDefContext.class,i);
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
			setState(59);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,3,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(40);
				title();
				setState(44);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__0) {
					{
					{
					setState(41);
					section();
					}
					}
					setState(46);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(48); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(47);
					match(T__0);
					}
					}
					setState(50); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==T__0 );
				setState(52);
				match(T__1);
				setState(56);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==NAME) {
					{
					{
					setState(53);
					classDef();
					}
					}
					setState(58);
					_errHandler.sync(this);
					_la = _input.LA(1);
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
			setState(62); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(61);
				match(T__0);
				}
				}
				setState(64); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==T__0 );
			setState(66);
			match(T__2);
			setState(68); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(67);
				any();
				}
				}
				setState(70); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__6) | (1L << T__15) | (1L << A) | (1L << NAME) | (1L << NUMBER))) != 0) );
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
			setState(73); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(72);
				match(T__0);
				}
				}
				setState(75); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==T__0 );
			setState(77);
			match(T__3);
			setState(81);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__4) | (1L << T__9) | (1L << NAME))) != 0)) {
				{
				{
				setState(78);
				sentence();
				}
				}
				setState(83);
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
			setState(89);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,8,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(84);
				thereSentence();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(85);
				directSentence();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(86);
				isSentence();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(87);
				hasSentence();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(88);
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
			setState(91);
			match(T__4);
			setState(92);
			match(T__5);
			setState(94);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,9,_ctx) ) {
			case 1:
				{
				setState(93);
				match(A);
				}
				break;
			}
			setState(97);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==NAME) {
				{
				setState(96);
				((ThereSentenceContext)_localctx).objectName = multiName();
				}
			}

			setState(99);
			match(A);
			setState(100);
			((ThereSentenceContext)_localctx).className = multiName();
			setState(104);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__14 || _la==NAME) {
				{
				{
				setState(101);
				withClause();
				}
				}
				setState(106);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(107);
			match(T__6);
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
			setState(110); 
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					{
					setState(109);
					match(NAME);
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(112); 
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,12,_ctx);
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
			setState(114);
			((IsSentenceContext)_localctx).objectName = multiName();
			setState(115);
			match(T__5);
			setState(116);
			((IsSentenceContext)_localctx).attrName = match(T__7);
			setState(118);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==A) {
				{
				setState(117);
				match(A);
				}
			}

			setState(120);
			((IsSentenceContext)_localctx).value = valueData();
			setState(121);
			match(T__6);
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
			setState(123);
			((DirectSentenceContext)_localctx).objectName = multiName();
			setState(124);
			match(T__5);
			setState(126);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==A) {
				{
				setState(125);
				match(A);
				}
			}

			setState(128);
			((DirectSentenceContext)_localctx).className = multiName();
			setState(132);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__14 || _la==NAME) {
				{
				{
				setState(129);
				withClause();
				}
				}
				setState(134);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(135);
			match(T__6);
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
			setState(137);
			((HasSentenceContext)_localctx).objectName = multiName();
			setState(138);
			match(T__8);
			setState(139);
			((HasSentenceContext)_localctx).attrName = match(NAME);
			setState(140);
			((HasSentenceContext)_localctx).value = valueData();
			setState(141);
			match(T__6);
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
			setState(143);
			match(T__9);
			setState(144);
			match(T__10);
			setState(145);
			((DiagramSentenceContext)_localctx).type = match(NAME);
			setState(146);
			match(T__11);
			setState(147);
			match(T__12);
			setState(148);
			((DiagramSentenceContext)_localctx).fileName = fileNameClause();
			setState(149);
			match(T__13);
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
			setState(165);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,19,_ctx) ) {
			case 1:
				_localctx = new UsualWithClauseContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(152);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__14) {
					{
					setState(151);
					match(T__14);
					}
				}

				setState(154);
				((UsualWithClauseContext)_localctx).attrName = match(NAME);
				setState(155);
				((UsualWithClauseContext)_localctx).attrValue = valueClause();
				}
				break;
			case 2:
				_localctx = new NumberWithClauseContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(156);
				match(T__14);
				setState(157);
				((NumberWithClauseContext)_localctx).value = match(NUMBER);
				setState(158);
				((NumberWithClauseContext)_localctx).attrName = multiName();
				setState(160);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__15) {
					{
					setState(159);
					match(T__15);
					}
				}

				setState(163);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__16) {
					{
					setState(162);
					match(T__16);
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
			setState(177); 
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					{
					setState(168);
					_errHandler.sync(this);
					_la = _input.LA(1);
					if (_la==A) {
						{
						setState(167);
						match(A);
						}
					}

					setState(170);
					valueData();
					setState(172);
					_errHandler.sync(this);
					_la = _input.LA(1);
					if (_la==T__15) {
						{
						setState(171);
						match(T__15);
						}
					}

					setState(175);
					_errHandler.sync(this);
					_la = _input.LA(1);
					if (_la==T__16) {
						{
						setState(174);
						match(T__16);
						}
					}

					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(179); 
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,23,_ctx);
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
			setState(182); 
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					{
					setState(181);
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
				setState(184); 
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,24,_ctx);
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
			setState(187); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(186);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__6) | (1L << T__17) | (1L << NAME))) != 0)) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
				}
				setState(189); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__6) | (1L << T__17) | (1L << NAME))) != 0) );
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
			setState(191);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__6) | (1L << T__15) | (1L << A) | (1L << NAME) | (1L << NUMBER))) != 0)) ) {
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

	public static class ClassDefContext extends ParserRuleContext {
		public Token className;
		public TerminalNode NAME() { return getToken(FulibScenariosParser.NAME, 0); }
		public List<ExampleValueContext> exampleValue() {
			return getRuleContexts(ExampleValueContext.class);
		}
		public ExampleValueContext exampleValue(int i) {
			return getRuleContext(ExampleValueContext.class,i);
		}
		public List<AttrDefContext> attrDef() {
			return getRuleContexts(AttrDefContext.class);
		}
		public AttrDefContext attrDef(int i) {
			return getRuleContext(AttrDefContext.class,i);
		}
		public List<RoleDefContext> roleDef() {
			return getRuleContexts(RoleDefContext.class);
		}
		public RoleDefContext roleDef(int i) {
			return getRuleContext(RoleDefContext.class,i);
		}
		public ClassDefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_classDef; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FulibScenariosListener ) ((FulibScenariosListener)listener).enterClassDef(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FulibScenariosListener ) ((FulibScenariosListener)listener).exitClassDef(this);
		}
	}

	public final ClassDefContext classDef() throws RecognitionException {
		ClassDefContext _localctx = new ClassDefContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_classDef);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(193);
			((ClassDefContext)_localctx).className = match(NAME);
			setState(194);
			match(T__18);
			setState(198);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,26,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(195);
					exampleValue();
					}
					} 
				}
				setState(200);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,26,_ctx);
			}
			setState(205);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__19) {
				{
				setState(203);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,27,_ctx) ) {
				case 1:
					{
					setState(201);
					attrDef();
					}
					break;
				case 2:
					{
					setState(202);
					roleDef();
					}
					break;
				}
				}
				setState(207);
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

	public static class ExampleValueContext extends ParserRuleContext {
		public Token nameValue;
		public Token numberValue;
		public TerminalNode NAME() { return getToken(FulibScenariosParser.NAME, 0); }
		public TerminalNode NUMBER() { return getToken(FulibScenariosParser.NUMBER, 0); }
		public ExampleValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_exampleValue; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FulibScenariosListener ) ((FulibScenariosListener)listener).enterExampleValue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FulibScenariosListener ) ((FulibScenariosListener)listener).exitExampleValue(this);
		}
	}

	public final ExampleValueContext exampleValue() throws RecognitionException {
		ExampleValueContext _localctx = new ExampleValueContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_exampleValue);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(210);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case NAME:
				{
				setState(208);
				((ExampleValueContext)_localctx).nameValue = match(NAME);
				}
				break;
			case NUMBER:
				{
				setState(209);
				((ExampleValueContext)_localctx).numberValue = match(NUMBER);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(213);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__15) {
				{
				setState(212);
				match(T__15);
				}
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

	public static class AttrDefContext extends ParserRuleContext {
		public Token attrName;
		public TerminalNode NAME() { return getToken(FulibScenariosParser.NAME, 0); }
		public List<ExampleValueContext> exampleValue() {
			return getRuleContexts(ExampleValueContext.class);
		}
		public ExampleValueContext exampleValue(int i) {
			return getRuleContext(ExampleValueContext.class,i);
		}
		public AttrDefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_attrDef; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FulibScenariosListener ) ((FulibScenariosListener)listener).enterAttrDef(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FulibScenariosListener ) ((FulibScenariosListener)listener).exitAttrDef(this);
		}
	}

	public final AttrDefContext attrDef() throws RecognitionException {
		AttrDefContext _localctx = new AttrDefContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_attrDef);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(215);
			match(T__19);
			setState(216);
			((AttrDefContext)_localctx).attrName = match(NAME);
			setState(217);
			match(T__18);
			setState(221);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,31,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(218);
					exampleValue();
					}
					} 
				}
				setState(223);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,31,_ctx);
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

	public static class RoleDefContext extends ParserRuleContext {
		public Token roleName;
		public CardDefContext card;
		public Token className;
		public Token otherClassName;
		public Token otherRoleName;
		public List<TerminalNode> NAME() { return getTokens(FulibScenariosParser.NAME); }
		public TerminalNode NAME(int i) {
			return getToken(FulibScenariosParser.NAME, i);
		}
		public CardDefContext cardDef() {
			return getRuleContext(CardDefContext.class,0);
		}
		public RoleDefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_roleDef; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FulibScenariosListener ) ((FulibScenariosListener)listener).enterRoleDef(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FulibScenariosListener ) ((FulibScenariosListener)listener).exitRoleDef(this);
		}
	}

	public final RoleDefContext roleDef() throws RecognitionException {
		RoleDefContext _localctx = new RoleDefContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_roleDef);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(224);
			match(T__19);
			setState(225);
			((RoleDefContext)_localctx).roleName = match(NAME);
			setState(226);
			((RoleDefContext)_localctx).card = cardDef();
			setState(227);
			((RoleDefContext)_localctx).className = match(NAME);
			setState(234);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__20) {
				{
				setState(228);
				match(T__20);
				setState(231);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,32,_ctx) ) {
				case 1:
					{
					setState(229);
					((RoleDefContext)_localctx).otherClassName = match(NAME);
					setState(230);
					match(T__6);
					}
					break;
				}
				setState(233);
				((RoleDefContext)_localctx).otherRoleName = match(NAME);
				}
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

	public static class CardDefContext extends ParserRuleContext {
		public CardDefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_cardDef; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FulibScenariosListener ) ((FulibScenariosListener)listener).enterCardDef(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FulibScenariosListener ) ((FulibScenariosListener)listener).exitCardDef(this);
		}
	}

	public final CardDefContext cardDef() throws RecognitionException {
		CardDefContext _localctx = new CardDefContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_cardDef);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(236);
			_la = _input.LA(1);
			if ( !(_la==T__21 || _la==T__22) ) {
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\37\u00f1\4\2\t\2"+
		"\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\3\2\3\2\7\2-\n\2\f\2\16\2\60\13\2\3\2\6"+
		"\2\63\n\2\r\2\16\2\64\3\2\3\2\7\29\n\2\f\2\16\2<\13\2\5\2>\n\2\3\3\6\3"+
		"A\n\3\r\3\16\3B\3\3\3\3\6\3G\n\3\r\3\16\3H\3\4\6\4L\n\4\r\4\16\4M\3\4"+
		"\3\4\7\4R\n\4\f\4\16\4U\13\4\3\5\3\5\3\5\3\5\3\5\5\5\\\n\5\3\6\3\6\3\6"+
		"\5\6a\n\6\3\6\5\6d\n\6\3\6\3\6\3\6\7\6i\n\6\f\6\16\6l\13\6\3\6\3\6\3\7"+
		"\6\7q\n\7\r\7\16\7r\3\b\3\b\3\b\3\b\5\by\n\b\3\b\3\b\3\b\3\t\3\t\3\t\5"+
		"\t\u0081\n\t\3\t\3\t\7\t\u0085\n\t\f\t\16\t\u0088\13\t\3\t\3\t\3\n\3\n"+
		"\3\n\3\n\3\n\3\n\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\f\5\f\u009b"+
		"\n\f\3\f\3\f\3\f\3\f\3\f\3\f\5\f\u00a3\n\f\3\f\5\f\u00a6\n\f\5\f\u00a8"+
		"\n\f\3\r\5\r\u00ab\n\r\3\r\3\r\5\r\u00af\n\r\3\r\5\r\u00b2\n\r\6\r\u00b4"+
		"\n\r\r\r\16\r\u00b5\3\16\6\16\u00b9\n\16\r\16\16\16\u00ba\3\17\6\17\u00be"+
		"\n\17\r\17\16\17\u00bf\3\20\3\20\3\21\3\21\3\21\7\21\u00c7\n\21\f\21\16"+
		"\21\u00ca\13\21\3\21\3\21\7\21\u00ce\n\21\f\21\16\21\u00d1\13\21\3\22"+
		"\3\22\5\22\u00d5\n\22\3\22\5\22\u00d8\n\22\3\23\3\23\3\23\3\23\7\23\u00de"+
		"\n\23\f\23\16\23\u00e1\13\23\3\24\3\24\3\24\3\24\3\24\3\24\3\24\5\24\u00ea"+
		"\n\24\3\24\5\24\u00ed\n\24\3\25\3\25\3\25\2\2\26\2\4\6\b\n\f\16\20\22"+
		"\24\26\30\32\34\36 \"$&(\2\6\3\2\33\34\5\2\t\t\24\24\33\33\5\2\t\t\22"+
		"\22\32\34\3\2\30\31\2\u0101\2=\3\2\2\2\4@\3\2\2\2\6K\3\2\2\2\b[\3\2\2"+
		"\2\n]\3\2\2\2\fp\3\2\2\2\16t\3\2\2\2\20}\3\2\2\2\22\u008b\3\2\2\2\24\u0091"+
		"\3\2\2\2\26\u00a7\3\2\2\2\30\u00b3\3\2\2\2\32\u00b8\3\2\2\2\34\u00bd\3"+
		"\2\2\2\36\u00c1\3\2\2\2 \u00c3\3\2\2\2\"\u00d4\3\2\2\2$\u00d9\3\2\2\2"+
		"&\u00e2\3\2\2\2(\u00ee\3\2\2\2*.\5\4\3\2+-\5\6\4\2,+\3\2\2\2-\60\3\2\2"+
		"\2.,\3\2\2\2./\3\2\2\2/>\3\2\2\2\60.\3\2\2\2\61\63\7\3\2\2\62\61\3\2\2"+
		"\2\63\64\3\2\2\2\64\62\3\2\2\2\64\65\3\2\2\2\65\66\3\2\2\2\66:\7\4\2\2"+
		"\679\5 \21\28\67\3\2\2\29<\3\2\2\2:8\3\2\2\2:;\3\2\2\2;>\3\2\2\2<:\3\2"+
		"\2\2=*\3\2\2\2=\62\3\2\2\2>\3\3\2\2\2?A\7\3\2\2@?\3\2\2\2AB\3\2\2\2B@"+
		"\3\2\2\2BC\3\2\2\2CD\3\2\2\2DF\7\5\2\2EG\5\36\20\2FE\3\2\2\2GH\3\2\2\2"+
		"HF\3\2\2\2HI\3\2\2\2I\5\3\2\2\2JL\7\3\2\2KJ\3\2\2\2LM\3\2\2\2MK\3\2\2"+
		"\2MN\3\2\2\2NO\3\2\2\2OS\7\6\2\2PR\5\b\5\2QP\3\2\2\2RU\3\2\2\2SQ\3\2\2"+
		"\2ST\3\2\2\2T\7\3\2\2\2US\3\2\2\2V\\\5\n\6\2W\\\5\20\t\2X\\\5\16\b\2Y"+
		"\\\5\22\n\2Z\\\5\24\13\2[V\3\2\2\2[W\3\2\2\2[X\3\2\2\2[Y\3\2\2\2[Z\3\2"+
		"\2\2\\\t\3\2\2\2]^\7\7\2\2^`\7\b\2\2_a\7\32\2\2`_\3\2\2\2`a\3\2\2\2ac"+
		"\3\2\2\2bd\5\f\7\2cb\3\2\2\2cd\3\2\2\2de\3\2\2\2ef\7\32\2\2fj\5\f\7\2"+
		"gi\5\26\f\2hg\3\2\2\2il\3\2\2\2jh\3\2\2\2jk\3\2\2\2km\3\2\2\2lj\3\2\2"+
		"\2mn\7\t\2\2n\13\3\2\2\2oq\7\33\2\2po\3\2\2\2qr\3\2\2\2rp\3\2\2\2rs\3"+
		"\2\2\2s\r\3\2\2\2tu\5\f\7\2uv\7\b\2\2vx\7\n\2\2wy\7\32\2\2xw\3\2\2\2x"+
		"y\3\2\2\2yz\3\2\2\2z{\5\32\16\2{|\7\t\2\2|\17\3\2\2\2}~\5\f\7\2~\u0080"+
		"\7\b\2\2\177\u0081\7\32\2\2\u0080\177\3\2\2\2\u0080\u0081\3\2\2\2\u0081"+
		"\u0082\3\2\2\2\u0082\u0086\5\f\7\2\u0083\u0085\5\26\f\2\u0084\u0083\3"+
		"\2\2\2\u0085\u0088\3\2\2\2\u0086\u0084\3\2\2\2\u0086\u0087\3\2\2\2\u0087"+
		"\u0089\3\2\2\2\u0088\u0086\3\2\2\2\u0089\u008a\7\t\2\2\u008a\21\3\2\2"+
		"\2\u008b\u008c\5\f\7\2\u008c\u008d\7\13\2\2\u008d\u008e\7\33\2\2\u008e"+
		"\u008f\5\32\16\2\u008f\u0090\7\t\2\2\u0090\23\3\2\2\2\u0091\u0092\7\f"+
		"\2\2\u0092\u0093\7\r\2\2\u0093\u0094\7\33\2\2\u0094\u0095\7\16\2\2\u0095"+
		"\u0096\7\17\2\2\u0096\u0097\5\34\17\2\u0097\u0098\7\20\2\2\u0098\25\3"+
		"\2\2\2\u0099\u009b\7\21\2\2\u009a\u0099\3\2\2\2\u009a\u009b\3\2\2\2\u009b"+
		"\u009c\3\2\2\2\u009c\u009d\7\33\2\2\u009d\u00a8\5\30\r\2\u009e\u009f\7"+
		"\21\2\2\u009f\u00a0\7\34\2\2\u00a0\u00a2\5\f\7\2\u00a1\u00a3\7\22\2\2"+
		"\u00a2\u00a1\3\2\2\2\u00a2\u00a3\3\2\2\2\u00a3\u00a5\3\2\2\2\u00a4\u00a6"+
		"\7\23\2\2\u00a5\u00a4\3\2\2\2\u00a5\u00a6\3\2\2\2\u00a6\u00a8\3\2\2\2"+
		"\u00a7\u009a\3\2\2\2\u00a7\u009e\3\2\2\2\u00a8\27\3\2\2\2\u00a9\u00ab"+
		"\7\32\2\2\u00aa\u00a9\3\2\2\2\u00aa\u00ab\3\2\2\2\u00ab\u00ac\3\2\2\2"+
		"\u00ac\u00ae\5\32\16\2\u00ad\u00af\7\22\2\2\u00ae\u00ad\3\2\2\2\u00ae"+
		"\u00af\3\2\2\2\u00af\u00b1\3\2\2\2\u00b0\u00b2\7\23\2\2\u00b1\u00b0\3"+
		"\2\2\2\u00b1\u00b2\3\2\2\2\u00b2\u00b4\3\2\2\2\u00b3\u00aa\3\2\2\2\u00b4"+
		"\u00b5\3\2\2\2\u00b5\u00b3\3\2\2\2\u00b5\u00b6\3\2\2\2\u00b6\31\3\2\2"+
		"\2\u00b7\u00b9\t\2\2\2\u00b8\u00b7\3\2\2\2\u00b9\u00ba\3\2\2\2\u00ba\u00b8"+
		"\3\2\2\2\u00ba\u00bb\3\2\2\2\u00bb\33\3\2\2\2\u00bc\u00be\t\3\2\2\u00bd"+
		"\u00bc\3\2\2\2\u00be\u00bf\3\2\2\2\u00bf\u00bd\3\2\2\2\u00bf\u00c0\3\2"+
		"\2\2\u00c0\35\3\2\2\2\u00c1\u00c2\t\4\2\2\u00c2\37\3\2\2\2\u00c3\u00c4"+
		"\7\33\2\2\u00c4\u00c8\7\25\2\2\u00c5\u00c7\5\"\22\2\u00c6\u00c5\3\2\2"+
		"\2\u00c7\u00ca\3\2\2\2\u00c8\u00c6\3\2\2\2\u00c8\u00c9\3\2\2\2\u00c9\u00cf"+
		"\3\2\2\2\u00ca\u00c8\3\2\2\2\u00cb\u00ce\5$\23\2\u00cc\u00ce\5&\24\2\u00cd"+
		"\u00cb\3\2\2\2\u00cd\u00cc\3\2\2\2\u00ce\u00d1\3\2\2\2\u00cf\u00cd\3\2"+
		"\2\2\u00cf\u00d0\3\2\2\2\u00d0!\3\2\2\2\u00d1\u00cf\3\2\2\2\u00d2\u00d5"+
		"\7\33\2\2\u00d3\u00d5\7\34\2\2\u00d4\u00d2\3\2\2\2\u00d4\u00d3\3\2\2\2"+
		"\u00d5\u00d7\3\2\2\2\u00d6\u00d8\7\22\2\2\u00d7\u00d6\3\2\2\2\u00d7\u00d8"+
		"\3\2\2\2\u00d8#\3\2\2\2\u00d9\u00da\7\26\2\2\u00da\u00db\7\33\2\2\u00db"+
		"\u00df\7\25\2\2\u00dc\u00de\5\"\22\2\u00dd\u00dc\3\2\2\2\u00de\u00e1\3"+
		"\2\2\2\u00df\u00dd\3\2\2\2\u00df\u00e0\3\2\2\2\u00e0%\3\2\2\2\u00e1\u00df"+
		"\3\2\2\2\u00e2\u00e3\7\26\2\2\u00e3\u00e4\7\33\2\2\u00e4\u00e5\5(\25\2"+
		"\u00e5\u00ec\7\33\2\2\u00e6\u00e9\7\27\2\2\u00e7\u00e8\7\33\2\2\u00e8"+
		"\u00ea\7\t\2\2\u00e9\u00e7\3\2\2\2\u00e9\u00ea\3\2\2\2\u00ea\u00eb\3\2"+
		"\2\2\u00eb\u00ed\7\33\2\2\u00ec\u00e6\3\2\2\2\u00ec\u00ed\3\2\2\2\u00ed"+
		"\'\3\2\2\2\u00ee\u00ef\t\5\2\2\u00ef)\3\2\2\2$.\64:=BHMS[`cjrx\u0080\u0086"+
		"\u009a\u00a2\u00a5\u00a7\u00aa\u00ae\u00b1\u00b5\u00ba\u00bf\u00c8\u00cd"+
		"\u00cf\u00d4\u00d7\u00df\u00e9\u00ec";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}