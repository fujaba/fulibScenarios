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
		T__17=18, T__18=19, T__19=20, T__20=21, T__21=22, T__22=23, T__23=24, 
		T__24=25, T__25=26, T__26=27, T__27=28, T__28=29, T__29=30, T__30=31, 
		T__31=32, T__32=33, T__33=34, T__34=35, T__35=36, T__36=37, T__37=38, 
		T__38=39, T__39=40, T__40=41, T__41=42, T__42=43, T__43=44, T__44=45, 
		T__45=46, T__46=47, T__47=48, T__48=49, T__49=50, CALL=51, A=52, NAME=53, 
		NUMBER=54, WS=55, COMMENT=56, LINE_COMMENT=57;
	public static final int
		RULE_scenario = 0, RULE_sentence = 1, RULE_callSentence = 2, RULE_thereSentence = 3, 
		RULE_multiName = 4, RULE_chainSentence = 5, RULE_introPhrase = 6, RULE_onPhrase = 7, 
		RULE_loopClause = 8, RULE_asPhrase = 9, RULE_cmpOp = 10, RULE_greaterEqual = 11, 
		RULE_lessThan = 12, RULE_predicateObjectPhrase = 13, RULE_continuePhrase = 14, 
		RULE_stopPhrase = 15, RULE_createPhrase = 16, RULE_verbPhrase = 17, RULE_answerPhrase = 18, 
		RULE_expectSentence = 19, RULE_thatPhrase = 20, RULE_directSentence = 21, 
		RULE_hasSentence = 22, RULE_hasClause = 23, RULE_diagramSentence = 24, 
		RULE_withClause = 25, RULE_valueClause = 26, RULE_rangeClause = 27, RULE_valueData = 28, 
		RULE_fileNameClause = 29, RULE_any = 30, RULE_classDef = 31, RULE_exampleValue = 32, 
		RULE_attrDef = 33, RULE_roleDef = 34, RULE_cardDef = 35;
	private static String[] makeRuleNames() {
		return new String[] {
			"scenario", "sentence", "callSentence", "thereSentence", "multiName", 
			"chainSentence", "introPhrase", "onPhrase", "loopClause", "asPhrase", 
			"cmpOp", "greaterEqual", "lessThan", "predicateObjectPhrase", "continuePhrase", 
			"stopPhrase", "createPhrase", "verbPhrase", "answerPhrase", "expectSentence", 
			"thatPhrase", "directSentence", "hasSentence", "hasClause", "diagramSentence", 
			"withClause", "valueClause", "rangeClause", "valueData", "fileNameClause", 
			"any", "classDef", "exampleValue", "attrDef", "roleDef", "cardDef"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'#'", "'Scenario'", "'.'", "'Register'", "'on'", "'There'", "'is'", 
			"'and'", "'it'", "'On'", "','", "'One'", "'by'", "'one'", "'As'", "'from'", 
			"'of'", "'greater'", "'equal'", "'less'", "'than'", "'continues'", "'stops'", 
			"'reading'", "'create'", "'creates'", "'cards'", "'card'", "'adds'", 
			"'puts'", "'reads'", "'writes'", "'to'", "'into'", "'answers'", "'with'", 
			"':'", "'expect'", "'that'", "'has'", "'!'", "'['", "']'", "'('", "')'", 
			"'/'", "'e.g.'", "'+'", "'cf.'", "'many'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, "CALL", "A", "NAME", "NUMBER", "WS", "COMMENT", "LINE_COMMENT"
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
		public List<AnyContext> any() {
			return getRuleContexts(AnyContext.class);
		}
		public AnyContext any(int i) {
			return getRuleContext(AnyContext.class,i);
		}
		public List<SentenceContext> sentence() {
			return getRuleContexts(SentenceContext.class);
		}
		public SentenceContext sentence(int i) {
			return getRuleContext(SentenceContext.class,i);
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
			setState(102);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,5,_ctx) ) {
			case 1:
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
				match(T__1);
				setState(79); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(78);
					any();
					}
					}
					setState(81); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__5) | (1L << T__6) | (1L << T__10) | (1L << T__15) | (1L << T__21) | (1L << T__23) | (1L << T__32) | (1L << T__33) | (1L << T__35) | (1L << T__39) | (1L << CALL) | (1L << A) | (1L << NAME) | (1L << NUMBER))) != 0) );
				setState(83);
				match(T__2);
				setState(87);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__5) | (1L << T__9) | (1L << T__11) | (1L << T__14) | (1L << T__40) | (1L << A) | (1L << NAME))) != 0)) {
					{
					{
					setState(84);
					sentence();
					}
					}
					setState(89);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(91); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(90);
					match(T__0);
					}
					}
					setState(93); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==T__0 );
				setState(95);
				match(T__3);
				setState(99);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==NAME) {
					{
					{
					setState(96);
					classDef();
					}
					}
					setState(101);
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

	public static class SentenceContext extends ParserRuleContext {
		public ThereSentenceContext thereSentence() {
			return getRuleContext(ThereSentenceContext.class,0);
		}
		public DirectSentenceContext directSentence() {
			return getRuleContext(DirectSentenceContext.class,0);
		}
		public ChainSentenceContext chainSentence() {
			return getRuleContext(ChainSentenceContext.class,0);
		}
		public HasSentenceContext hasSentence() {
			return getRuleContext(HasSentenceContext.class,0);
		}
		public DiagramSentenceContext diagramSentence() {
			return getRuleContext(DiagramSentenceContext.class,0);
		}
		public CallSentenceContext callSentence() {
			return getRuleContext(CallSentenceContext.class,0);
		}
		public ExpectSentenceContext expectSentence() {
			return getRuleContext(ExpectSentenceContext.class,0);
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
		enterRule(_localctx, 2, RULE_sentence);
		try {
			setState(111);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,6,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(104);
				thereSentence();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(105);
				directSentence();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(106);
				chainSentence();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(107);
				hasSentence();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(108);
				diagramSentence();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(109);
				callSentence();
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(110);
				expectSentence();
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

	public static class CallSentenceContext extends ParserRuleContext {
		public Token caller;
		public Token methodName;
		public Token objectName;
		public TerminalNode CALL() { return getToken(FulibScenariosParser.CALL, 0); }
		public List<TerminalNode> NAME() { return getTokens(FulibScenariosParser.NAME); }
		public TerminalNode NAME(int i) {
			return getToken(FulibScenariosParser.NAME, i);
		}
		public List<WithClauseContext> withClause() {
			return getRuleContexts(WithClauseContext.class);
		}
		public WithClauseContext withClause(int i) {
			return getRuleContext(WithClauseContext.class,i);
		}
		public CallSentenceContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_callSentence; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FulibScenariosListener ) ((FulibScenariosListener)listener).enterCallSentence(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FulibScenariosListener ) ((FulibScenariosListener)listener).exitCallSentence(this);
		}
	}

	public final CallSentenceContext callSentence() throws RecognitionException {
		CallSentenceContext _localctx = new CallSentenceContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_callSentence);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(113);
			((CallSentenceContext)_localctx).caller = match(NAME);
			setState(114);
			match(CALL);
			setState(115);
			((CallSentenceContext)_localctx).methodName = match(NAME);
			setState(116);
			match(T__4);
			setState(117);
			((CallSentenceContext)_localctx).objectName = match(NAME);
			setState(121);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__35 || _la==NAME) {
				{
				{
				setState(118);
				withClause();
				}
				}
				setState(123);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(124);
			match(T__2);
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
		enterRule(_localctx, 6, RULE_thereSentence);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(126);
			match(T__5);
			setState(127);
			match(T__6);
			setState(129);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,8,_ctx) ) {
			case 1:
				{
				setState(128);
				match(A);
				}
				break;
			}
			setState(132);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==NAME) {
				{
				setState(131);
				((ThereSentenceContext)_localctx).objectName = multiName();
				}
			}

			setState(142);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==A) {
				{
				setState(134);
				match(A);
				setState(135);
				((ThereSentenceContext)_localctx).className = multiName();
				setState(139);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__35 || _la==NAME) {
					{
					{
					setState(136);
					withClause();
					}
					}
					setState(141);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(144);
			match(T__2);
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
		enterRule(_localctx, 8, RULE_multiName);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(147); 
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					{
					setState(146);
					match(NAME);
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(149); 
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

	public static class ChainSentenceContext extends ParserRuleContext {
		public IntroPhraseContext loopIntro;
		public Token methodName;
		public List<PredicateObjectPhraseContext> predicateObjectPhrase() {
			return getRuleContexts(PredicateObjectPhraseContext.class);
		}
		public PredicateObjectPhraseContext predicateObjectPhrase(int i) {
			return getRuleContext(PredicateObjectPhraseContext.class,i);
		}
		public TerminalNode NAME() { return getToken(FulibScenariosParser.NAME, 0); }
		public IntroPhraseContext introPhrase() {
			return getRuleContext(IntroPhraseContext.class,0);
		}
		public ChainSentenceContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_chainSentence; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FulibScenariosListener ) ((FulibScenariosListener)listener).enterChainSentence(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FulibScenariosListener ) ((FulibScenariosListener)listener).exitChainSentence(this);
		}
	}

	public final ChainSentenceContext chainSentence() throws RecognitionException {
		ChainSentenceContext _localctx = new ChainSentenceContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_chainSentence);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(152);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__9) | (1L << T__11) | (1L << T__14))) != 0)) {
				{
				setState(151);
				((ChainSentenceContext)_localctx).loopIntro = introPhrase();
				}
			}

			setState(154);
			((ChainSentenceContext)_localctx).methodName = match(NAME);
			setState(155);
			predicateObjectPhrase();
			setState(161);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__7) {
				{
				{
				setState(156);
				match(T__7);
				setState(157);
				match(T__8);
				setState(158);
				predicateObjectPhrase();
				}
				}
				setState(163);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(164);
			match(T__2);
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

	public static class IntroPhraseContext extends ParserRuleContext {
		public OnPhraseContext onPhrase() {
			return getRuleContext(OnPhraseContext.class,0);
		}
		public LoopClauseContext loopClause() {
			return getRuleContext(LoopClauseContext.class,0);
		}
		public AsPhraseContext asPhrase() {
			return getRuleContext(AsPhraseContext.class,0);
		}
		public IntroPhraseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_introPhrase; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FulibScenariosListener ) ((FulibScenariosListener)listener).enterIntroPhrase(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FulibScenariosListener ) ((FulibScenariosListener)listener).exitIntroPhrase(this);
		}
	}

	public final IntroPhraseContext introPhrase() throws RecognitionException {
		IntroPhraseContext _localctx = new IntroPhraseContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_introPhrase);
		try {
			setState(169);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__9:
				enterOuterAlt(_localctx, 1);
				{
				setState(166);
				onPhrase();
				}
				break;
			case T__11:
				enterOuterAlt(_localctx, 2);
				{
				setState(167);
				loopClause();
				}
				break;
			case T__14:
				enterOuterAlt(_localctx, 3);
				{
				setState(168);
				asPhrase();
				}
				break;
			default:
				throw new NoViableAltException(this);
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

	public static class OnPhraseContext extends ParserRuleContext {
		public List<AnyContext> any() {
			return getRuleContexts(AnyContext.class);
		}
		public AnyContext any(int i) {
			return getRuleContext(AnyContext.class,i);
		}
		public OnPhraseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_onPhrase; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FulibScenariosListener ) ((FulibScenariosListener)listener).enterOnPhrase(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FulibScenariosListener ) ((FulibScenariosListener)listener).exitOnPhrase(this);
		}
	}

	public final OnPhraseContext onPhrase() throws RecognitionException {
		OnPhraseContext _localctx = new OnPhraseContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_onPhrase);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(171);
			match(T__9);
			setState(173); 
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					{
					setState(172);
					any();
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(175); 
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,16,_ctx);
			} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
			setState(177);
			match(T__10);
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

	public static class LoopClauseContext extends ParserRuleContext {
		public LoopClauseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_loopClause; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FulibScenariosListener ) ((FulibScenariosListener)listener).enterLoopClause(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FulibScenariosListener ) ((FulibScenariosListener)listener).exitLoopClause(this);
		}
	}

	public final LoopClauseContext loopClause() throws RecognitionException {
		LoopClauseContext _localctx = new LoopClauseContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_loopClause);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(179);
			match(T__11);
			setState(180);
			match(T__12);
			setState(181);
			match(T__13);
			setState(183);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__10) {
				{
				setState(182);
				match(T__10);
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

	public static class AsPhraseContext extends ParserRuleContext {
		public ValueClauseContext value1;
		public Token fromAttrName1;
		public Token fromObjName1;
		public CmpOpContext cmp;
		public ValueClauseContext value2;
		public Token fromAttrName2;
		public Token fromObjName2;
		public List<ValueClauseContext> valueClause() {
			return getRuleContexts(ValueClauseContext.class);
		}
		public ValueClauseContext valueClause(int i) {
			return getRuleContext(ValueClauseContext.class,i);
		}
		public CmpOpContext cmpOp() {
			return getRuleContext(CmpOpContext.class,0);
		}
		public List<TerminalNode> A() { return getTokens(FulibScenariosParser.A); }
		public TerminalNode A(int i) {
			return getToken(FulibScenariosParser.A, i);
		}
		public List<TerminalNode> NAME() { return getTokens(FulibScenariosParser.NAME); }
		public TerminalNode NAME(int i) {
			return getToken(FulibScenariosParser.NAME, i);
		}
		public AsPhraseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_asPhrase; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FulibScenariosListener ) ((FulibScenariosListener)listener).enterAsPhrase(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FulibScenariosListener ) ((FulibScenariosListener)listener).exitAsPhrase(this);
		}
	}

	public final AsPhraseContext asPhrase() throws RecognitionException {
		AsPhraseContext _localctx = new AsPhraseContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_asPhrase);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(185);
			match(T__14);
			setState(187);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,18,_ctx) ) {
			case 1:
				{
				setState(186);
				match(A);
				}
				break;
			}
			setState(189);
			((AsPhraseContext)_localctx).value1 = valueClause();
			setState(202);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__15) {
				{
				setState(190);
				match(T__15);
				setState(192);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==A) {
					{
					setState(191);
					match(A);
					}
				}

				setState(194);
				((AsPhraseContext)_localctx).fromAttrName1 = match(NAME);
				setState(200);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__16) {
					{
					setState(195);
					match(T__16);
					setState(197);
					_errHandler.sync(this);
					_la = _input.LA(1);
					if (_la==A) {
						{
						setState(196);
						match(A);
						}
					}

					setState(199);
					((AsPhraseContext)_localctx).fromObjName1 = match(NAME);
					}
				}

				}
			}

			setState(204);
			((AsPhraseContext)_localctx).cmp = cmpOp();
			setState(206);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,23,_ctx) ) {
			case 1:
				{
				setState(205);
				match(A);
				}
				break;
			}
			setState(208);
			((AsPhraseContext)_localctx).value2 = valueClause();
			setState(221);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__15) {
				{
				setState(209);
				match(T__15);
				setState(211);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==A) {
					{
					setState(210);
					match(A);
					}
				}

				setState(213);
				((AsPhraseContext)_localctx).fromAttrName2 = match(NAME);
				setState(219);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__16) {
					{
					setState(214);
					match(T__16);
					setState(216);
					_errHandler.sync(this);
					_la = _input.LA(1);
					if (_la==A) {
						{
						setState(215);
						match(A);
						}
					}

					setState(218);
					((AsPhraseContext)_localctx).fromObjName2 = match(NAME);
					}
				}

				}
			}

			setState(223);
			match(T__10);
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

	public static class CmpOpContext extends ParserRuleContext {
		public GreaterEqualContext greaterEqual() {
			return getRuleContext(GreaterEqualContext.class,0);
		}
		public LessThanContext lessThan() {
			return getRuleContext(LessThanContext.class,0);
		}
		public CmpOpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_cmpOp; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FulibScenariosListener ) ((FulibScenariosListener)listener).enterCmpOp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FulibScenariosListener ) ((FulibScenariosListener)listener).exitCmpOp(this);
		}
	}

	public final CmpOpContext cmpOp() throws RecognitionException {
		CmpOpContext _localctx = new CmpOpContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_cmpOp);
		try {
			setState(227);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,28,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(225);
				greaterEqual();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(226);
				lessThan();
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

	public static class GreaterEqualContext extends ParserRuleContext {
		public GreaterEqualContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_greaterEqual; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FulibScenariosListener ) ((FulibScenariosListener)listener).enterGreaterEqual(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FulibScenariosListener ) ((FulibScenariosListener)listener).exitGreaterEqual(this);
		}
	}

	public final GreaterEqualContext greaterEqual() throws RecognitionException {
		GreaterEqualContext _localctx = new GreaterEqualContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_greaterEqual);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(229);
			match(T__6);
			setState(230);
			match(T__17);
			setState(231);
			match(T__18);
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

	public static class LessThanContext extends ParserRuleContext {
		public LessThanContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_lessThan; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FulibScenariosListener ) ((FulibScenariosListener)listener).enterLessThan(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FulibScenariosListener ) ((FulibScenariosListener)listener).exitLessThan(this);
		}
	}

	public final LessThanContext lessThan() throws RecognitionException {
		LessThanContext _localctx = new LessThanContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_lessThan);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(233);
			match(T__6);
			setState(234);
			match(T__19);
			setState(235);
			match(T__20);
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

	public static class PredicateObjectPhraseContext extends ParserRuleContext {
		public CreatePhraseContext createPhrase() {
			return getRuleContext(CreatePhraseContext.class,0);
		}
		public VerbPhraseContext verbPhrase() {
			return getRuleContext(VerbPhraseContext.class,0);
		}
		public AnswerPhraseContext answerPhrase() {
			return getRuleContext(AnswerPhraseContext.class,0);
		}
		public StopPhraseContext stopPhrase() {
			return getRuleContext(StopPhraseContext.class,0);
		}
		public ContinuePhraseContext continuePhrase() {
			return getRuleContext(ContinuePhraseContext.class,0);
		}
		public PredicateObjectPhraseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_predicateObjectPhrase; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FulibScenariosListener ) ((FulibScenariosListener)listener).enterPredicateObjectPhrase(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FulibScenariosListener ) ((FulibScenariosListener)listener).exitPredicateObjectPhrase(this);
		}
	}

	public final PredicateObjectPhraseContext predicateObjectPhrase() throws RecognitionException {
		PredicateObjectPhraseContext _localctx = new PredicateObjectPhraseContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_predicateObjectPhrase);
		try {
			setState(242);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__24:
			case T__25:
				enterOuterAlt(_localctx, 1);
				{
				setState(237);
				createPhrase();
				}
				break;
			case T__28:
			case T__29:
			case T__30:
			case T__31:
				enterOuterAlt(_localctx, 2);
				{
				setState(238);
				verbPhrase();
				}
				break;
			case T__34:
				enterOuterAlt(_localctx, 3);
				{
				setState(239);
				answerPhrase();
				}
				break;
			case T__22:
				enterOuterAlt(_localctx, 4);
				{
				setState(240);
				stopPhrase();
				}
				break;
			case T__21:
				enterOuterAlt(_localctx, 5);
				{
				setState(241);
				continuePhrase();
				}
				break;
			default:
				throw new NoViableAltException(this);
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

	public static class ContinuePhraseContext extends ParserRuleContext {
		public List<AnyContext> any() {
			return getRuleContexts(AnyContext.class);
		}
		public AnyContext any(int i) {
			return getRuleContext(AnyContext.class,i);
		}
		public ContinuePhraseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_continuePhrase; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FulibScenariosListener ) ((FulibScenariosListener)listener).enterContinuePhrase(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FulibScenariosListener ) ((FulibScenariosListener)listener).exitContinuePhrase(this);
		}
	}

	public final ContinuePhraseContext continuePhrase() throws RecognitionException {
		ContinuePhraseContext _localctx = new ContinuePhraseContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_continuePhrase);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(244);
			match(T__21);
			setState(246); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(245);
				any();
				}
				}
				setState(248); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__5) | (1L << T__6) | (1L << T__10) | (1L << T__15) | (1L << T__21) | (1L << T__23) | (1L << T__32) | (1L << T__33) | (1L << T__35) | (1L << T__39) | (1L << CALL) | (1L << A) | (1L << NAME) | (1L << NUMBER))) != 0) );
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

	public static class StopPhraseContext extends ParserRuleContext {
		public Token loopName;
		public TerminalNode NAME() { return getToken(FulibScenariosParser.NAME, 0); }
		public List<AnyContext> any() {
			return getRuleContexts(AnyContext.class);
		}
		public AnyContext any(int i) {
			return getRuleContext(AnyContext.class,i);
		}
		public StopPhraseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_stopPhrase; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FulibScenariosListener ) ((FulibScenariosListener)listener).enterStopPhrase(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FulibScenariosListener ) ((FulibScenariosListener)listener).exitStopPhrase(this);
		}
	}

	public final StopPhraseContext stopPhrase() throws RecognitionException {
		StopPhraseContext _localctx = new StopPhraseContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_stopPhrase);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(250);
			match(T__22);
			setState(251);
			match(T__23);
			setState(252);
			((StopPhraseContext)_localctx).loopName = match(NAME);
			setState(256);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__5) | (1L << T__6) | (1L << T__10) | (1L << T__15) | (1L << T__21) | (1L << T__23) | (1L << T__32) | (1L << T__33) | (1L << T__35) | (1L << T__39) | (1L << CALL) | (1L << A) | (1L << NAME) | (1L << NUMBER))) != 0)) {
				{
				{
				setState(253);
				any();
				}
				}
				setState(258);
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

	public static class CreatePhraseContext extends ParserRuleContext {
		public Token className;
		public TerminalNode NAME() { return getToken(FulibScenariosParser.NAME, 0); }
		public TerminalNode A() { return getToken(FulibScenariosParser.A, 0); }
		public List<WithClauseContext> withClause() {
			return getRuleContexts(WithClauseContext.class);
		}
		public WithClauseContext withClause(int i) {
			return getRuleContext(WithClauseContext.class,i);
		}
		public CreatePhraseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_createPhrase; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FulibScenariosListener ) ((FulibScenariosListener)listener).enterCreatePhrase(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FulibScenariosListener ) ((FulibScenariosListener)listener).exitCreatePhrase(this);
		}
	}

	public final CreatePhraseContext createPhrase() throws RecognitionException {
		CreatePhraseContext _localctx = new CreatePhraseContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_createPhrase);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(259);
			_la = _input.LA(1);
			if ( !(_la==T__24 || _la==T__25) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			setState(261);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==A) {
				{
				setState(260);
				match(A);
				}
			}

			setState(263);
			((CreatePhraseContext)_localctx).className = match(NAME);
			setState(265);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__26 || _la==T__27) {
				{
				setState(264);
				_la = _input.LA(1);
				if ( !(_la==T__26 || _la==T__27) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
			}

			setState(270);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__35 || _la==NAME) {
				{
				{
				setState(267);
				withClause();
				}
				}
				setState(272);
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

	public static class VerbPhraseContext extends ParserRuleContext {
		public Token verb;
		public ValueClauseContext value;
		public Token fromAttrName;
		public Token secondFromAttrName;
		public Token fromObjName;
		public Token toAttrName;
		public Token toObjName;
		public ValueClauseContext valueClause() {
			return getRuleContext(ValueClauseContext.class,0);
		}
		public List<TerminalNode> A() { return getTokens(FulibScenariosParser.A); }
		public TerminalNode A(int i) {
			return getToken(FulibScenariosParser.A, i);
		}
		public List<TerminalNode> NAME() { return getTokens(FulibScenariosParser.NAME); }
		public TerminalNode NAME(int i) {
			return getToken(FulibScenariosParser.NAME, i);
		}
		public VerbPhraseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_verbPhrase; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FulibScenariosListener ) ((FulibScenariosListener)listener).enterVerbPhrase(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FulibScenariosListener ) ((FulibScenariosListener)listener).exitVerbPhrase(this);
		}
	}

	public final VerbPhraseContext verbPhrase() throws RecognitionException {
		VerbPhraseContext _localctx = new VerbPhraseContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_verbPhrase);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(273);
			((VerbPhraseContext)_localctx).verb = _input.LT(1);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__28) | (1L << T__29) | (1L << T__30) | (1L << T__31))) != 0)) ) {
				((VerbPhraseContext)_localctx).verb = (Token)_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			setState(275);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,35,_ctx) ) {
			case 1:
				{
				setState(274);
				match(A);
				}
				break;
			}
			setState(277);
			((VerbPhraseContext)_localctx).value = valueClause();
			setState(294);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__15) {
				{
				setState(278);
				match(T__15);
				setState(280);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==A) {
					{
					setState(279);
					match(A);
					}
				}

				setState(282);
				((VerbPhraseContext)_localctx).fromAttrName = match(NAME);
				setState(285);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__32) {
					{
					setState(283);
					match(T__32);
					setState(284);
					((VerbPhraseContext)_localctx).secondFromAttrName = match(NAME);
					}
				}

				setState(292);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__16) {
					{
					setState(287);
					match(T__16);
					setState(289);
					_errHandler.sync(this);
					_la = _input.LA(1);
					if (_la==A) {
						{
						setState(288);
						match(A);
						}
					}

					setState(291);
					((VerbPhraseContext)_localctx).fromObjName = match(NAME);
					}
				}

				}
			}

			setState(305);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__33) {
				{
				setState(296);
				match(T__33);
				setState(297);
				((VerbPhraseContext)_localctx).toAttrName = match(NAME);
				setState(303);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__16) {
					{
					setState(298);
					match(T__16);
					setState(300);
					_errHandler.sync(this);
					_la = _input.LA(1);
					if (_la==A) {
						{
						setState(299);
						match(A);
						}
					}

					setState(302);
					((VerbPhraseContext)_localctx).toObjName = match(NAME);
					}
				}

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

	public static class AnswerPhraseContext extends ParserRuleContext {
		public ValueClauseContext value;
		public ValueClauseContext valueClause() {
			return getRuleContext(ValueClauseContext.class,0);
		}
		public AnswerPhraseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_answerPhrase; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FulibScenariosListener ) ((FulibScenariosListener)listener).enterAnswerPhrase(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FulibScenariosListener ) ((FulibScenariosListener)listener).exitAnswerPhrase(this);
		}
	}

	public final AnswerPhraseContext answerPhrase() throws RecognitionException {
		AnswerPhraseContext _localctx = new AnswerPhraseContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_answerPhrase);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(307);
			match(T__34);
			setState(309);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__35 || _la==T__36) {
				{
				setState(308);
				_la = _input.LA(1);
				if ( !(_la==T__35 || _la==T__36) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
			}

			setState(311);
			((AnswerPhraseContext)_localctx).value = valueClause();
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

	public static class ExpectSentenceContext extends ParserRuleContext {
		public Token caller;
		public TerminalNode NAME() { return getToken(FulibScenariosParser.NAME, 0); }
		public TerminalNode A() { return getToken(FulibScenariosParser.A, 0); }
		public List<ThatPhraseContext> thatPhrase() {
			return getRuleContexts(ThatPhraseContext.class);
		}
		public ThatPhraseContext thatPhrase(int i) {
			return getRuleContext(ThatPhraseContext.class,i);
		}
		public ExpectSentenceContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expectSentence; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FulibScenariosListener ) ((FulibScenariosListener)listener).enterExpectSentence(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FulibScenariosListener ) ((FulibScenariosListener)listener).exitExpectSentence(this);
		}
	}

	public final ExpectSentenceContext expectSentence() throws RecognitionException {
		ExpectSentenceContext _localctx = new ExpectSentenceContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_expectSentence);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(314);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==A) {
				{
				setState(313);
				match(A);
				}
			}

			setState(316);
			((ExpectSentenceContext)_localctx).caller = match(NAME);
			setState(317);
			match(T__37);
			setState(319); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(318);
				thatPhrase();
				}
				}
				setState(321); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==T__38 );
			setState(323);
			match(T__2);
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

	public static class ThatPhraseContext extends ParserRuleContext {
		public MultiNameContext objectName;
		public HasClauseContext hasPart;
		public MultiNameContext multiName() {
			return getRuleContext(MultiNameContext.class,0);
		}
		public HasClauseContext hasClause() {
			return getRuleContext(HasClauseContext.class,0);
		}
		public ThatPhraseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_thatPhrase; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FulibScenariosListener ) ((FulibScenariosListener)listener).enterThatPhrase(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FulibScenariosListener ) ((FulibScenariosListener)listener).exitThatPhrase(this);
		}
	}

	public final ThatPhraseContext thatPhrase() throws RecognitionException {
		ThatPhraseContext _localctx = new ThatPhraseContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_thatPhrase);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(325);
			match(T__38);
			setState(326);
			((ThatPhraseContext)_localctx).objectName = multiName();
			setState(327);
			((ThatPhraseContext)_localctx).hasPart = hasClause();
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
		enterRule(_localctx, 42, RULE_directSentence);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(329);
			((DirectSentenceContext)_localctx).objectName = multiName();
			setState(330);
			match(T__6);
			setState(332);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==A) {
				{
				setState(331);
				match(A);
				}
			}

			setState(334);
			((DirectSentenceContext)_localctx).className = multiName();
			setState(338);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,48,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(335);
					withClause();
					}
					} 
				}
				setState(340);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,48,_ctx);
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

	public static class HasSentenceContext extends ParserRuleContext {
		public MultiNameContext objectName;
		public MultiNameContext multiName() {
			return getRuleContext(MultiNameContext.class,0);
		}
		public TerminalNode A() { return getToken(FulibScenariosParser.A, 0); }
		public List<HasClauseContext> hasClause() {
			return getRuleContexts(HasClauseContext.class);
		}
		public HasClauseContext hasClause(int i) {
			return getRuleContext(HasClauseContext.class,i);
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
		enterRule(_localctx, 44, RULE_hasSentence);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(342);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==A) {
				{
				setState(341);
				match(A);
				}
			}

			setState(344);
			((HasSentenceContext)_localctx).objectName = multiName();
			setState(346); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(345);
				hasClause();
				}
				}
				setState(348); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==T__39 );
			setState(350);
			match(T__2);
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

	public static class HasClauseContext extends ParserRuleContext {
		public HasClauseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_hasClause; }
	 
		public HasClauseContext() { }
		public void copyFrom(HasClauseContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class UsualHasClauseContext extends HasClauseContext {
		public Token attrName;
		public ValueClauseContext attrValue;
		public TerminalNode NAME() { return getToken(FulibScenariosParser.NAME, 0); }
		public ValueClauseContext valueClause() {
			return getRuleContext(ValueClauseContext.class,0);
		}
		public UsualHasClauseContext(HasClauseContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FulibScenariosListener ) ((FulibScenariosListener)listener).enterUsualHasClause(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FulibScenariosListener ) ((FulibScenariosListener)listener).exitUsualHasClause(this);
		}
	}
	public static class NumberHasClauseContext extends HasClauseContext {
		public Token value;
		public MultiNameContext attrName;
		public TerminalNode NUMBER() { return getToken(FulibScenariosParser.NUMBER, 0); }
		public MultiNameContext multiName() {
			return getRuleContext(MultiNameContext.class,0);
		}
		public NumberHasClauseContext(HasClauseContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FulibScenariosListener ) ((FulibScenariosListener)listener).enterNumberHasClause(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FulibScenariosListener ) ((FulibScenariosListener)listener).exitNumberHasClause(this);
		}
	}

	public final HasClauseContext hasClause() throws RecognitionException {
		HasClauseContext _localctx = new HasClauseContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_hasClause);
		int _la;
		try {
			setState(364);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,53,_ctx) ) {
			case 1:
				_localctx = new UsualHasClauseContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(352);
				match(T__39);
				setState(353);
				((UsualHasClauseContext)_localctx).attrName = match(NAME);
				setState(354);
				((UsualHasClauseContext)_localctx).attrValue = valueClause();
				}
				break;
			case 2:
				_localctx = new NumberHasClauseContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(355);
				match(T__39);
				setState(356);
				((NumberHasClauseContext)_localctx).value = match(NUMBER);
				setState(357);
				((NumberHasClauseContext)_localctx).attrName = multiName();
				setState(359);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__10) {
					{
					setState(358);
					match(T__10);
					}
				}

				setState(362);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__7) {
					{
					setState(361);
					match(T__7);
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
		enterRule(_localctx, 48, RULE_diagramSentence);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(366);
			match(T__40);
			setState(367);
			match(T__41);
			setState(368);
			((DiagramSentenceContext)_localctx).type = match(NAME);
			setState(369);
			match(T__42);
			setState(370);
			match(T__43);
			setState(371);
			((DiagramSentenceContext)_localctx).fileName = fileNameClause();
			setState(372);
			match(T__44);
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
		enterRule(_localctx, 50, RULE_withClause);
		int _la;
		try {
			setState(388);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,57,_ctx) ) {
			case 1:
				_localctx = new UsualWithClauseContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(375);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__35) {
					{
					setState(374);
					match(T__35);
					}
				}

				setState(377);
				((UsualWithClauseContext)_localctx).attrName = match(NAME);
				setState(378);
				((UsualWithClauseContext)_localctx).attrValue = valueClause();
				}
				break;
			case 2:
				_localctx = new NumberWithClauseContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(379);
				match(T__35);
				setState(380);
				((NumberWithClauseContext)_localctx).value = match(NUMBER);
				setState(381);
				((NumberWithClauseContext)_localctx).attrName = multiName();
				setState(383);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__10) {
					{
					setState(382);
					match(T__10);
					}
				}

				setState(386);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,56,_ctx) ) {
				case 1:
					{
					setState(385);
					match(T__7);
					}
					break;
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
		public List<RangeClauseContext> rangeClause() {
			return getRuleContexts(RangeClauseContext.class);
		}
		public RangeClauseContext rangeClause(int i) {
			return getRuleContext(RangeClauseContext.class,i);
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
		enterRule(_localctx, 52, RULE_valueClause);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(397); 
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					{
					setState(390);
					rangeClause();
					setState(392);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,58,_ctx) ) {
					case 1:
						{
						setState(391);
						match(T__10);
						}
						break;
					}
					setState(395);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,59,_ctx) ) {
					case 1:
						{
						setState(394);
						match(T__7);
						}
						break;
					}
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(399); 
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,60,_ctx);
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

	public static class RangeClauseContext extends ParserRuleContext {
		public ValueDataContext firstData;
		public ValueDataContext secondData;
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
		public RangeClauseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_rangeClause; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FulibScenariosListener ) ((FulibScenariosListener)listener).enterRangeClause(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FulibScenariosListener ) ((FulibScenariosListener)listener).exitRangeClause(this);
		}
	}

	public final RangeClauseContext rangeClause() throws RecognitionException {
		RangeClauseContext _localctx = new RangeClauseContext(_ctx, getState());
		enterRule(_localctx, 54, RULE_rangeClause);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(402);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==A) {
				{
				setState(401);
				match(A);
				}
			}

			setState(404);
			((RangeClauseContext)_localctx).firstData = valueData();
			setState(410);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__32) {
				{
				setState(405);
				match(T__32);
				setState(407);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==A) {
					{
					setState(406);
					match(A);
					}
				}

				setState(409);
				((RangeClauseContext)_localctx).secondData = valueData();
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
		enterRule(_localctx, 56, RULE_valueData);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(413); 
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					{
					setState(412);
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
				setState(415); 
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,64,_ctx);
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
		enterRule(_localctx, 58, RULE_fileNameClause);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(418); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(417);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__2) | (1L << T__45) | (1L << NAME))) != 0)) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
				}
				setState(420); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__2) | (1L << T__45) | (1L << NAME))) != 0) );
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
		public TerminalNode CALL() { return getToken(FulibScenariosParser.CALL, 0); }
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
		enterRule(_localctx, 60, RULE_any);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(422);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__5) | (1L << T__6) | (1L << T__10) | (1L << T__15) | (1L << T__21) | (1L << T__23) | (1L << T__32) | (1L << T__33) | (1L << T__35) | (1L << T__39) | (1L << CALL) | (1L << A) | (1L << NAME) | (1L << NUMBER))) != 0)) ) {
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
		enterRule(_localctx, 62, RULE_classDef);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(424);
			((ClassDefContext)_localctx).className = match(NAME);
			setState(425);
			match(T__46);
			setState(429);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,66,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(426);
					exampleValue();
					}
					} 
				}
				setState(431);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,66,_ctx);
			}
			setState(436);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__47) {
				{
				setState(434);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,67,_ctx) ) {
				case 1:
					{
					setState(432);
					attrDef();
					}
					break;
				case 2:
					{
					setState(433);
					roleDef();
					}
					break;
				}
				}
				setState(438);
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
		public List<TerminalNode> NAME() { return getTokens(FulibScenariosParser.NAME); }
		public TerminalNode NAME(int i) {
			return getToken(FulibScenariosParser.NAME, i);
		}
		public List<TerminalNode> NUMBER() { return getTokens(FulibScenariosParser.NUMBER); }
		public TerminalNode NUMBER(int i) {
			return getToken(FulibScenariosParser.NUMBER, i);
		}
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
		enterRule(_localctx, 64, RULE_exampleValue);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(441);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case NAME:
				{
				setState(439);
				((ExampleValueContext)_localctx).nameValue = match(NAME);
				}
				break;
			case NUMBER:
				{
				setState(440);
				((ExampleValueContext)_localctx).numberValue = match(NUMBER);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(448);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__36) {
				{
				setState(443);
				match(T__36);
				setState(446);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case NAME:
					{
					setState(444);
					((ExampleValueContext)_localctx).nameValue = match(NAME);
					}
					break;
				case NUMBER:
					{
					setState(445);
					((ExampleValueContext)_localctx).numberValue = match(NUMBER);
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
			}

			setState(451);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__10) {
				{
				setState(450);
				match(T__10);
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
		enterRule(_localctx, 66, RULE_attrDef);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(453);
			match(T__47);
			setState(454);
			((AttrDefContext)_localctx).attrName = match(NAME);
			setState(455);
			match(T__46);
			setState(459);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,73,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(456);
					exampleValue();
					}
					} 
				}
				setState(461);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,73,_ctx);
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
		public List<ExampleValueContext> exampleValue() {
			return getRuleContexts(ExampleValueContext.class);
		}
		public ExampleValueContext exampleValue(int i) {
			return getRuleContext(ExampleValueContext.class,i);
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
		enterRule(_localctx, 68, RULE_roleDef);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(462);
			match(T__47);
			setState(463);
			((RoleDefContext)_localctx).roleName = match(NAME);
			setState(464);
			((RoleDefContext)_localctx).card = cardDef();
			setState(465);
			((RoleDefContext)_localctx).className = match(NAME);
			setState(472);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__48) {
				{
				setState(466);
				match(T__48);
				setState(469);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,74,_ctx) ) {
				case 1:
					{
					setState(467);
					((RoleDefContext)_localctx).otherClassName = match(NAME);
					setState(468);
					match(T__2);
					}
					break;
				}
				setState(471);
				((RoleDefContext)_localctx).otherRoleName = match(NAME);
				}
			}

			setState(481);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__46) {
				{
				setState(474);
				match(T__46);
				setState(478);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,76,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(475);
						exampleValue();
						}
						} 
					}
					setState(480);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,76,_ctx);
				}
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
		enterRule(_localctx, 70, RULE_cardDef);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(483);
			_la = _input.LA(1);
			if ( !(_la==T__13 || _la==T__49) ) {
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3;\u01e8\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\3\2\6\2L\n\2\r\2\16\2M\3\2\3\2\6\2R\n\2"+
		"\r\2\16\2S\3\2\3\2\7\2X\n\2\f\2\16\2[\13\2\3\2\6\2^\n\2\r\2\16\2_\3\2"+
		"\3\2\7\2d\n\2\f\2\16\2g\13\2\5\2i\n\2\3\3\3\3\3\3\3\3\3\3\3\3\3\3\5\3"+
		"r\n\3\3\4\3\4\3\4\3\4\3\4\3\4\7\4z\n\4\f\4\16\4}\13\4\3\4\3\4\3\5\3\5"+
		"\3\5\5\5\u0084\n\5\3\5\5\5\u0087\n\5\3\5\3\5\3\5\7\5\u008c\n\5\f\5\16"+
		"\5\u008f\13\5\5\5\u0091\n\5\3\5\3\5\3\6\6\6\u0096\n\6\r\6\16\6\u0097\3"+
		"\7\5\7\u009b\n\7\3\7\3\7\3\7\3\7\3\7\7\7\u00a2\n\7\f\7\16\7\u00a5\13\7"+
		"\3\7\3\7\3\b\3\b\3\b\5\b\u00ac\n\b\3\t\3\t\6\t\u00b0\n\t\r\t\16\t\u00b1"+
		"\3\t\3\t\3\n\3\n\3\n\3\n\5\n\u00ba\n\n\3\13\3\13\5\13\u00be\n\13\3\13"+
		"\3\13\3\13\5\13\u00c3\n\13\3\13\3\13\3\13\5\13\u00c8\n\13\3\13\5\13\u00cb"+
		"\n\13\5\13\u00cd\n\13\3\13\3\13\5\13\u00d1\n\13\3\13\3\13\3\13\5\13\u00d6"+
		"\n\13\3\13\3\13\3\13\5\13\u00db\n\13\3\13\5\13\u00de\n\13\5\13\u00e0\n"+
		"\13\3\13\3\13\3\f\3\f\5\f\u00e6\n\f\3\r\3\r\3\r\3\r\3\16\3\16\3\16\3\16"+
		"\3\17\3\17\3\17\3\17\3\17\5\17\u00f5\n\17\3\20\3\20\6\20\u00f9\n\20\r"+
		"\20\16\20\u00fa\3\21\3\21\3\21\3\21\7\21\u0101\n\21\f\21\16\21\u0104\13"+
		"\21\3\22\3\22\5\22\u0108\n\22\3\22\3\22\5\22\u010c\n\22\3\22\7\22\u010f"+
		"\n\22\f\22\16\22\u0112\13\22\3\23\3\23\5\23\u0116\n\23\3\23\3\23\3\23"+
		"\5\23\u011b\n\23\3\23\3\23\3\23\5\23\u0120\n\23\3\23\3\23\5\23\u0124\n"+
		"\23\3\23\5\23\u0127\n\23\5\23\u0129\n\23\3\23\3\23\3\23\3\23\5\23\u012f"+
		"\n\23\3\23\5\23\u0132\n\23\5\23\u0134\n\23\3\24\3\24\5\24\u0138\n\24\3"+
		"\24\3\24\3\25\5\25\u013d\n\25\3\25\3\25\3\25\6\25\u0142\n\25\r\25\16\25"+
		"\u0143\3\25\3\25\3\26\3\26\3\26\3\26\3\27\3\27\3\27\5\27\u014f\n\27\3"+
		"\27\3\27\7\27\u0153\n\27\f\27\16\27\u0156\13\27\3\30\5\30\u0159\n\30\3"+
		"\30\3\30\6\30\u015d\n\30\r\30\16\30\u015e\3\30\3\30\3\31\3\31\3\31\3\31"+
		"\3\31\3\31\3\31\5\31\u016a\n\31\3\31\5\31\u016d\n\31\5\31\u016f\n\31\3"+
		"\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\33\5\33\u017a\n\33\3\33\3\33"+
		"\3\33\3\33\3\33\3\33\5\33\u0182\n\33\3\33\5\33\u0185\n\33\5\33\u0187\n"+
		"\33\3\34\3\34\5\34\u018b\n\34\3\34\5\34\u018e\n\34\6\34\u0190\n\34\r\34"+
		"\16\34\u0191\3\35\5\35\u0195\n\35\3\35\3\35\3\35\5\35\u019a\n\35\3\35"+
		"\5\35\u019d\n\35\3\36\6\36\u01a0\n\36\r\36\16\36\u01a1\3\37\6\37\u01a5"+
		"\n\37\r\37\16\37\u01a6\3 \3 \3!\3!\3!\7!\u01ae\n!\f!\16!\u01b1\13!\3!"+
		"\3!\7!\u01b5\n!\f!\16!\u01b8\13!\3\"\3\"\5\"\u01bc\n\"\3\"\3\"\3\"\5\""+
		"\u01c1\n\"\5\"\u01c3\n\"\3\"\5\"\u01c6\n\"\3#\3#\3#\3#\7#\u01cc\n#\f#"+
		"\16#\u01cf\13#\3$\3$\3$\3$\3$\3$\3$\5$\u01d8\n$\3$\5$\u01db\n$\3$\3$\7"+
		"$\u01df\n$\f$\16$\u01e2\13$\5$\u01e4\n$\3%\3%\3%\2\2&\2\4\6\b\n\f\16\20"+
		"\22\24\26\30\32\34\36 \"$&(*,.\60\62\64\668:<>@BDFH\2\n\3\2\33\34\3\2"+
		"\35\36\3\2\37\"\3\2&\'\3\2\678\5\2\5\5\60\60\67\67\13\2\b\t\r\r\22\22"+
		"\30\30\32\32#$&&**\658\4\2\20\20\64\64\2\u021a\2h\3\2\2\2\4q\3\2\2\2\6"+
		"s\3\2\2\2\b\u0080\3\2\2\2\n\u0095\3\2\2\2\f\u009a\3\2\2\2\16\u00ab\3\2"+
		"\2\2\20\u00ad\3\2\2\2\22\u00b5\3\2\2\2\24\u00bb\3\2\2\2\26\u00e5\3\2\2"+
		"\2\30\u00e7\3\2\2\2\32\u00eb\3\2\2\2\34\u00f4\3\2\2\2\36\u00f6\3\2\2\2"+
		" \u00fc\3\2\2\2\"\u0105\3\2\2\2$\u0113\3\2\2\2&\u0135\3\2\2\2(\u013c\3"+
		"\2\2\2*\u0147\3\2\2\2,\u014b\3\2\2\2.\u0158\3\2\2\2\60\u016e\3\2\2\2\62"+
		"\u0170\3\2\2\2\64\u0186\3\2\2\2\66\u018f\3\2\2\28\u0194\3\2\2\2:\u019f"+
		"\3\2\2\2<\u01a4\3\2\2\2>\u01a8\3\2\2\2@\u01aa\3\2\2\2B\u01bb\3\2\2\2D"+
		"\u01c7\3\2\2\2F\u01d0\3\2\2\2H\u01e5\3\2\2\2JL\7\3\2\2KJ\3\2\2\2LM\3\2"+
		"\2\2MK\3\2\2\2MN\3\2\2\2NO\3\2\2\2OQ\7\4\2\2PR\5> \2QP\3\2\2\2RS\3\2\2"+
		"\2SQ\3\2\2\2ST\3\2\2\2TU\3\2\2\2UY\7\5\2\2VX\5\4\3\2WV\3\2\2\2X[\3\2\2"+
		"\2YW\3\2\2\2YZ\3\2\2\2Zi\3\2\2\2[Y\3\2\2\2\\^\7\3\2\2]\\\3\2\2\2^_\3\2"+
		"\2\2_]\3\2\2\2_`\3\2\2\2`a\3\2\2\2ae\7\6\2\2bd\5@!\2cb\3\2\2\2dg\3\2\2"+
		"\2ec\3\2\2\2ef\3\2\2\2fi\3\2\2\2ge\3\2\2\2hK\3\2\2\2h]\3\2\2\2i\3\3\2"+
		"\2\2jr\5\b\5\2kr\5,\27\2lr\5\f\7\2mr\5.\30\2nr\5\62\32\2or\5\6\4\2pr\5"+
		"(\25\2qj\3\2\2\2qk\3\2\2\2ql\3\2\2\2qm\3\2\2\2qn\3\2\2\2qo\3\2\2\2qp\3"+
		"\2\2\2r\5\3\2\2\2st\7\67\2\2tu\7\65\2\2uv\7\67\2\2vw\7\7\2\2w{\7\67\2"+
		"\2xz\5\64\33\2yx\3\2\2\2z}\3\2\2\2{y\3\2\2\2{|\3\2\2\2|~\3\2\2\2}{\3\2"+
		"\2\2~\177\7\5\2\2\177\7\3\2\2\2\u0080\u0081\7\b\2\2\u0081\u0083\7\t\2"+
		"\2\u0082\u0084\7\66\2\2\u0083\u0082\3\2\2\2\u0083\u0084\3\2\2\2\u0084"+
		"\u0086\3\2\2\2\u0085\u0087\5\n\6\2\u0086\u0085\3\2\2\2\u0086\u0087\3\2"+
		"\2\2\u0087\u0090\3\2\2\2\u0088\u0089\7\66\2\2\u0089\u008d\5\n\6\2\u008a"+
		"\u008c\5\64\33\2\u008b\u008a\3\2\2\2\u008c\u008f\3\2\2\2\u008d\u008b\3"+
		"\2\2\2\u008d\u008e\3\2\2\2\u008e\u0091\3\2\2\2\u008f\u008d\3\2\2\2\u0090"+
		"\u0088\3\2\2\2\u0090\u0091\3\2\2\2\u0091\u0092\3\2\2\2\u0092\u0093\7\5"+
		"\2\2\u0093\t\3\2\2\2\u0094\u0096\7\67\2\2\u0095\u0094\3\2\2\2\u0096\u0097"+
		"\3\2\2\2\u0097\u0095\3\2\2\2\u0097\u0098\3\2\2\2\u0098\13\3\2\2\2\u0099"+
		"\u009b\5\16\b\2\u009a\u0099\3\2\2\2\u009a\u009b\3\2\2\2\u009b\u009c\3"+
		"\2\2\2\u009c\u009d\7\67\2\2\u009d\u00a3\5\34\17\2\u009e\u009f\7\n\2\2"+
		"\u009f\u00a0\7\13\2\2\u00a0\u00a2\5\34\17\2\u00a1\u009e\3\2\2\2\u00a2"+
		"\u00a5\3\2\2\2\u00a3\u00a1\3\2\2\2\u00a3\u00a4\3\2\2\2\u00a4\u00a6\3\2"+
		"\2\2\u00a5\u00a3\3\2\2\2\u00a6\u00a7\7\5\2\2\u00a7\r\3\2\2\2\u00a8\u00ac"+
		"\5\20\t\2\u00a9\u00ac\5\22\n\2\u00aa\u00ac\5\24\13\2\u00ab\u00a8\3\2\2"+
		"\2\u00ab\u00a9\3\2\2\2\u00ab\u00aa\3\2\2\2\u00ac\17\3\2\2\2\u00ad\u00af"+
		"\7\f\2\2\u00ae\u00b0\5> \2\u00af\u00ae\3\2\2\2\u00b0\u00b1\3\2\2\2\u00b1"+
		"\u00af\3\2\2\2\u00b1\u00b2\3\2\2\2\u00b2\u00b3\3\2\2\2\u00b3\u00b4\7\r"+
		"\2\2\u00b4\21\3\2\2\2\u00b5\u00b6\7\16\2\2\u00b6\u00b7\7\17\2\2\u00b7"+
		"\u00b9\7\20\2\2\u00b8\u00ba\7\r\2\2\u00b9\u00b8\3\2\2\2\u00b9\u00ba\3"+
		"\2\2\2\u00ba\23\3\2\2\2\u00bb\u00bd\7\21\2\2\u00bc\u00be\7\66\2\2\u00bd"+
		"\u00bc\3\2\2\2\u00bd\u00be\3\2\2\2\u00be\u00bf\3\2\2\2\u00bf\u00cc\5\66"+
		"\34\2\u00c0\u00c2\7\22\2\2\u00c1\u00c3\7\66\2\2\u00c2\u00c1\3\2\2\2\u00c2"+
		"\u00c3\3\2\2\2\u00c3\u00c4\3\2\2\2\u00c4\u00ca\7\67\2\2\u00c5\u00c7\7"+
		"\23\2\2\u00c6\u00c8\7\66\2\2\u00c7\u00c6\3\2\2\2\u00c7\u00c8\3\2\2\2\u00c8"+
		"\u00c9\3\2\2\2\u00c9\u00cb\7\67\2\2\u00ca\u00c5\3\2\2\2\u00ca\u00cb\3"+
		"\2\2\2\u00cb\u00cd\3\2\2\2\u00cc\u00c0\3\2\2\2\u00cc\u00cd\3\2\2\2\u00cd"+
		"\u00ce\3\2\2\2\u00ce\u00d0\5\26\f\2\u00cf\u00d1\7\66\2\2\u00d0\u00cf\3"+
		"\2\2\2\u00d0\u00d1\3\2\2\2\u00d1\u00d2\3\2\2\2\u00d2\u00df\5\66\34\2\u00d3"+
		"\u00d5\7\22\2\2\u00d4\u00d6\7\66\2\2\u00d5\u00d4\3\2\2\2\u00d5\u00d6\3"+
		"\2\2\2\u00d6\u00d7\3\2\2\2\u00d7\u00dd\7\67\2\2\u00d8\u00da\7\23\2\2\u00d9"+
		"\u00db\7\66\2\2\u00da\u00d9\3\2\2\2\u00da\u00db\3\2\2\2\u00db\u00dc\3"+
		"\2\2\2\u00dc\u00de\7\67\2\2\u00dd\u00d8\3\2\2\2\u00dd\u00de\3\2\2\2\u00de"+
		"\u00e0\3\2\2\2\u00df\u00d3\3\2\2\2\u00df\u00e0\3\2\2\2\u00e0\u00e1\3\2"+
		"\2\2\u00e1\u00e2\7\r\2\2\u00e2\25\3\2\2\2\u00e3\u00e6\5\30\r\2\u00e4\u00e6"+
		"\5\32\16\2\u00e5\u00e3\3\2\2\2\u00e5\u00e4\3\2\2\2\u00e6\27\3\2\2\2\u00e7"+
		"\u00e8\7\t\2\2\u00e8\u00e9\7\24\2\2\u00e9\u00ea\7\25\2\2\u00ea\31\3\2"+
		"\2\2\u00eb\u00ec\7\t\2\2\u00ec\u00ed\7\26\2\2\u00ed\u00ee\7\27\2\2\u00ee"+
		"\33\3\2\2\2\u00ef\u00f5\5\"\22\2\u00f0\u00f5\5$\23\2\u00f1\u00f5\5&\24"+
		"\2\u00f2\u00f5\5 \21\2\u00f3\u00f5\5\36\20\2\u00f4\u00ef\3\2\2\2\u00f4"+
		"\u00f0\3\2\2\2\u00f4\u00f1\3\2\2\2\u00f4\u00f2\3\2\2\2\u00f4\u00f3\3\2"+
		"\2\2\u00f5\35\3\2\2\2\u00f6\u00f8\7\30\2\2\u00f7\u00f9\5> \2\u00f8\u00f7"+
		"\3\2\2\2\u00f9\u00fa\3\2\2\2\u00fa\u00f8\3\2\2\2\u00fa\u00fb\3\2\2\2\u00fb"+
		"\37\3\2\2\2\u00fc\u00fd\7\31\2\2\u00fd\u00fe\7\32\2\2\u00fe\u0102\7\67"+
		"\2\2\u00ff\u0101\5> \2\u0100\u00ff\3\2\2\2\u0101\u0104\3\2\2\2\u0102\u0100"+
		"\3\2\2\2\u0102\u0103\3\2\2\2\u0103!\3\2\2\2\u0104\u0102\3\2\2\2\u0105"+
		"\u0107\t\2\2\2\u0106\u0108\7\66\2\2\u0107\u0106\3\2\2\2\u0107\u0108\3"+
		"\2\2\2\u0108\u0109\3\2\2\2\u0109\u010b\7\67\2\2\u010a\u010c\t\3\2\2\u010b"+
		"\u010a\3\2\2\2\u010b\u010c\3\2\2\2\u010c\u0110\3\2\2\2\u010d\u010f\5\64"+
		"\33\2\u010e\u010d\3\2\2\2\u010f\u0112\3\2\2\2\u0110\u010e\3\2\2\2\u0110"+
		"\u0111\3\2\2\2\u0111#\3\2\2\2\u0112\u0110\3\2\2\2\u0113\u0115\t\4\2\2"+
		"\u0114\u0116\7\66\2\2\u0115\u0114\3\2\2\2\u0115\u0116\3\2\2\2\u0116\u0117"+
		"\3\2\2\2\u0117\u0128\5\66\34\2\u0118\u011a\7\22\2\2\u0119\u011b\7\66\2"+
		"\2\u011a\u0119\3\2\2\2\u011a\u011b\3\2\2\2\u011b\u011c\3\2\2\2\u011c\u011f"+
		"\7\67\2\2\u011d\u011e\7#\2\2\u011e\u0120\7\67\2\2\u011f\u011d\3\2\2\2"+
		"\u011f\u0120\3\2\2\2\u0120\u0126\3\2\2\2\u0121\u0123\7\23\2\2\u0122\u0124"+
		"\7\66\2\2\u0123\u0122\3\2\2\2\u0123\u0124\3\2\2\2\u0124\u0125\3\2\2\2"+
		"\u0125\u0127\7\67\2\2\u0126\u0121\3\2\2\2\u0126\u0127\3\2\2\2\u0127\u0129"+
		"\3\2\2\2\u0128\u0118\3\2\2\2\u0128\u0129\3\2\2\2\u0129\u0133\3\2\2\2\u012a"+
		"\u012b\7$\2\2\u012b\u0131\7\67\2\2\u012c\u012e\7\23\2\2\u012d\u012f\7"+
		"\66\2\2\u012e\u012d\3\2\2\2\u012e\u012f\3\2\2\2\u012f\u0130\3\2\2\2\u0130"+
		"\u0132\7\67\2\2\u0131\u012c\3\2\2\2\u0131\u0132\3\2\2\2\u0132\u0134\3"+
		"\2\2\2\u0133\u012a\3\2\2\2\u0133\u0134\3\2\2\2\u0134%\3\2\2\2\u0135\u0137"+
		"\7%\2\2\u0136\u0138\t\5\2\2\u0137\u0136\3\2\2\2\u0137\u0138\3\2\2\2\u0138"+
		"\u0139\3\2\2\2\u0139\u013a\5\66\34\2\u013a\'\3\2\2\2\u013b\u013d\7\66"+
		"\2\2\u013c\u013b\3\2\2\2\u013c\u013d\3\2\2\2\u013d\u013e\3\2\2\2\u013e"+
		"\u013f\7\67\2\2\u013f\u0141\7(\2\2\u0140\u0142\5*\26\2\u0141\u0140\3\2"+
		"\2\2\u0142\u0143\3\2\2\2\u0143\u0141\3\2\2\2\u0143\u0144\3\2\2\2\u0144"+
		"\u0145\3\2\2\2\u0145\u0146\7\5\2\2\u0146)\3\2\2\2\u0147\u0148\7)\2\2\u0148"+
		"\u0149\5\n\6\2\u0149\u014a\5\60\31\2\u014a+\3\2\2\2\u014b\u014c\5\n\6"+
		"\2\u014c\u014e\7\t\2\2\u014d\u014f\7\66\2\2\u014e\u014d\3\2\2\2\u014e"+
		"\u014f\3\2\2\2\u014f\u0150\3\2\2\2\u0150\u0154\5\n\6\2\u0151\u0153\5\64"+
		"\33\2\u0152\u0151\3\2\2\2\u0153\u0156\3\2\2\2\u0154\u0152\3\2\2\2\u0154"+
		"\u0155\3\2\2\2\u0155-\3\2\2\2\u0156\u0154\3\2\2\2\u0157\u0159\7\66\2\2"+
		"\u0158\u0157\3\2\2\2\u0158\u0159\3\2\2\2\u0159\u015a\3\2\2\2\u015a\u015c"+
		"\5\n\6\2\u015b\u015d\5\60\31\2\u015c\u015b\3\2\2\2\u015d\u015e\3\2\2\2"+
		"\u015e\u015c\3\2\2\2\u015e\u015f\3\2\2\2\u015f\u0160\3\2\2\2\u0160\u0161"+
		"\7\5\2\2\u0161/\3\2\2\2\u0162\u0163\7*\2\2\u0163\u0164\7\67\2\2\u0164"+
		"\u016f\5\66\34\2\u0165\u0166\7*\2\2\u0166\u0167\78\2\2\u0167\u0169\5\n"+
		"\6\2\u0168\u016a\7\r\2\2\u0169\u0168\3\2\2\2\u0169\u016a\3\2\2\2\u016a"+
		"\u016c\3\2\2\2\u016b\u016d\7\n\2\2\u016c\u016b\3\2\2\2\u016c\u016d\3\2"+
		"\2\2\u016d\u016f\3\2\2\2\u016e\u0162\3\2\2\2\u016e\u0165\3\2\2\2\u016f"+
		"\61\3\2\2\2\u0170\u0171\7+\2\2\u0171\u0172\7,\2\2\u0172\u0173\7\67\2\2"+
		"\u0173\u0174\7-\2\2\u0174\u0175\7.\2\2\u0175\u0176\5<\37\2\u0176\u0177"+
		"\7/\2\2\u0177\63\3\2\2\2\u0178\u017a\7&\2\2\u0179\u0178\3\2\2\2\u0179"+
		"\u017a\3\2\2\2\u017a\u017b\3\2\2\2\u017b\u017c\7\67\2\2\u017c\u0187\5"+
		"\66\34\2\u017d\u017e\7&\2\2\u017e\u017f\78\2\2\u017f\u0181\5\n\6\2\u0180"+
		"\u0182\7\r\2\2\u0181\u0180\3\2\2\2\u0181\u0182\3\2\2\2\u0182\u0184\3\2"+
		"\2\2\u0183\u0185\7\n\2\2\u0184\u0183\3\2\2\2\u0184\u0185\3\2\2\2\u0185"+
		"\u0187\3\2\2\2\u0186\u0179\3\2\2\2\u0186\u017d\3\2\2\2\u0187\65\3\2\2"+
		"\2\u0188\u018a\58\35\2\u0189\u018b\7\r\2\2\u018a\u0189\3\2\2\2\u018a\u018b"+
		"\3\2\2\2\u018b\u018d\3\2\2\2\u018c\u018e\7\n\2\2\u018d\u018c\3\2\2\2\u018d"+
		"\u018e\3\2\2\2\u018e\u0190\3\2\2\2\u018f\u0188\3\2\2\2\u0190\u0191\3\2"+
		"\2\2\u0191\u018f\3\2\2\2\u0191\u0192\3\2\2\2\u0192\67\3\2\2\2\u0193\u0195"+
		"\7\66\2\2\u0194\u0193\3\2\2\2\u0194\u0195\3\2\2\2\u0195\u0196\3\2\2\2"+
		"\u0196\u019c\5:\36\2\u0197\u0199\7#\2\2\u0198\u019a\7\66\2\2\u0199\u0198"+
		"\3\2\2\2\u0199\u019a\3\2\2\2\u019a\u019b\3\2\2\2\u019b\u019d\5:\36\2\u019c"+
		"\u0197\3\2\2\2\u019c\u019d\3\2\2\2\u019d9\3\2\2\2\u019e\u01a0\t\6\2\2"+
		"\u019f\u019e\3\2\2\2\u01a0\u01a1\3\2\2\2\u01a1\u019f\3\2\2\2\u01a1\u01a2"+
		"\3\2\2\2\u01a2;\3\2\2\2\u01a3\u01a5\t\7\2\2\u01a4\u01a3\3\2\2\2\u01a5"+
		"\u01a6\3\2\2\2\u01a6\u01a4\3\2\2\2\u01a6\u01a7\3\2\2\2\u01a7=\3\2\2\2"+
		"\u01a8\u01a9\t\b\2\2\u01a9?\3\2\2\2\u01aa\u01ab\7\67\2\2\u01ab\u01af\7"+
		"\61\2\2\u01ac\u01ae\5B\"\2\u01ad\u01ac\3\2\2\2\u01ae\u01b1\3\2\2\2\u01af"+
		"\u01ad\3\2\2\2\u01af\u01b0\3\2\2\2\u01b0\u01b6\3\2\2\2\u01b1\u01af\3\2"+
		"\2\2\u01b2\u01b5\5D#\2\u01b3\u01b5\5F$\2\u01b4\u01b2\3\2\2\2\u01b4\u01b3"+
		"\3\2\2\2\u01b5\u01b8\3\2\2\2\u01b6\u01b4\3\2\2\2\u01b6\u01b7\3\2\2\2\u01b7"+
		"A\3\2\2\2\u01b8\u01b6\3\2\2\2\u01b9\u01bc\7\67\2\2\u01ba\u01bc\78\2\2"+
		"\u01bb\u01b9\3\2\2\2\u01bb\u01ba\3\2\2\2\u01bc\u01c2\3\2\2\2\u01bd\u01c0"+
		"\7\'\2\2\u01be\u01c1\7\67\2\2\u01bf\u01c1\78\2\2\u01c0\u01be\3\2\2\2\u01c0"+
		"\u01bf\3\2\2\2\u01c1\u01c3\3\2\2\2\u01c2\u01bd\3\2\2\2\u01c2\u01c3\3\2"+
		"\2\2\u01c3\u01c5\3\2\2\2\u01c4\u01c6\7\r\2\2\u01c5\u01c4\3\2\2\2\u01c5"+
		"\u01c6\3\2\2\2\u01c6C\3\2\2\2\u01c7\u01c8\7\62\2\2\u01c8\u01c9\7\67\2"+
		"\2\u01c9\u01cd\7\61\2\2\u01ca\u01cc\5B\"\2\u01cb\u01ca\3\2\2\2\u01cc\u01cf"+
		"\3\2\2\2\u01cd\u01cb\3\2\2\2\u01cd\u01ce\3\2\2\2\u01ceE\3\2\2\2\u01cf"+
		"\u01cd\3\2\2\2\u01d0\u01d1\7\62\2\2\u01d1\u01d2\7\67\2\2\u01d2\u01d3\5"+
		"H%\2\u01d3\u01da\7\67\2\2\u01d4\u01d7\7\63\2\2\u01d5\u01d6\7\67\2\2\u01d6"+
		"\u01d8\7\5\2\2\u01d7\u01d5\3\2\2\2\u01d7\u01d8\3\2\2\2\u01d8\u01d9\3\2"+
		"\2\2\u01d9\u01db\7\67\2\2\u01da\u01d4\3\2\2\2\u01da\u01db\3\2\2\2\u01db"+
		"\u01e3\3\2\2\2\u01dc\u01e0\7\61\2\2\u01dd\u01df\5B\"\2\u01de\u01dd\3\2"+
		"\2\2\u01df\u01e2\3\2\2\2\u01e0\u01de\3\2\2\2\u01e0\u01e1\3\2\2\2\u01e1"+
		"\u01e4\3\2\2\2\u01e2\u01e0\3\2\2\2\u01e3\u01dc\3\2\2\2\u01e3\u01e4\3\2"+
		"\2\2\u01e4G\3\2\2\2\u01e5\u01e6\t\t\2\2\u01e6I\3\2\2\2PMSY_ehq{\u0083"+
		"\u0086\u008d\u0090\u0097\u009a\u00a3\u00ab\u00b1\u00b9\u00bd\u00c2\u00c7"+
		"\u00ca\u00cc\u00d0\u00d5\u00da\u00dd\u00df\u00e5\u00f4\u00fa\u0102\u0107"+
		"\u010b\u0110\u0115\u011a\u011f\u0123\u0126\u0128\u012e\u0131\u0133\u0137"+
		"\u013c\u0143\u014e\u0154\u0158\u015e\u0169\u016c\u016e\u0179\u0181\u0184"+
		"\u0186\u018a\u018d\u0191\u0194\u0199\u019c\u01a1\u01a6\u01af\u01b4\u01b6"+
		"\u01bb\u01c0\u01c2\u01c5\u01cd\u01d7\u01da\u01e0\u01e3";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}