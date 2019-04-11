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
		T__45=46, T__46=47, T__47=48, T__48=49, CALL=50, A=51, NAME=52, NUMBER=53, 
		WS=54, COMMENT=55, LINE_COMMENT=56;
	public static final int
		RULE_scenario = 0, RULE_sentence = 1, RULE_callSentence = 2, RULE_thereSentence = 3, 
		RULE_multiName = 4, RULE_chainSentence = 5, RULE_introPhrase = 6, RULE_onPhrase = 7, 
		RULE_loopClause = 8, RULE_asPhrase = 9, RULE_cmpOp = 10, RULE_greaterEqual = 11, 
		RULE_lessThan = 12, RULE_predicateObjectPhrase = 13, RULE_stopPhrase = 14, 
		RULE_createPhrase = 15, RULE_verbPhrase = 16, RULE_answerPhrase = 17, 
		RULE_expectSentence = 18, RULE_thatPhrase = 19, RULE_directSentence = 20, 
		RULE_hasSentence = 21, RULE_hasClause = 22, RULE_diagramSentence = 23, 
		RULE_withClause = 24, RULE_valueClause = 25, RULE_rangeClause = 26, RULE_valueData = 27, 
		RULE_fileNameClause = 28, RULE_any = 29, RULE_classDef = 30, RULE_exampleValue = 31, 
		RULE_attrDef = 32, RULE_roleDef = 33, RULE_cardDef = 34;
	private static String[] makeRuleNames() {
		return new String[] {
			"scenario", "sentence", "callSentence", "thereSentence", "multiName", 
			"chainSentence", "introPhrase", "onPhrase", "loopClause", "asPhrase", 
			"cmpOp", "greaterEqual", "lessThan", "predicateObjectPhrase", "stopPhrase", 
			"createPhrase", "verbPhrase", "answerPhrase", "expectSentence", "thatPhrase", 
			"directSentence", "hasSentence", "hasClause", "diagramSentence", "withClause", 
			"valueClause", "rangeClause", "valueData", "fileNameClause", "any", "classDef", 
			"exampleValue", "attrDef", "roleDef", "cardDef"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'#'", "'Scenario'", "'.'", "'Register'", "'on'", "'There'", "'is'", 
			"'and'", "'it'", "'On'", "','", "'One'", "'by'", "'one'", "'As'", "'greater'", 
			"'equal'", "'less'", "'than'", "'stops'", "'reading'", "'create'", "'creates'", 
			"'cards'", "'card'", "'adds'", "'puts'", "'reads'", "'writes'", "'from'", 
			"'of'", "'to'", "'into'", "'answers'", "'with'", "':'", "'expect'", "'that'", 
			"'has'", "'!'", "'['", "']'", "'('", "')'", "'/'", "'e.g.'", "'+'", "'cf.'", 
			"'many'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, "CALL", "A", "NAME", "NUMBER", "WS", "COMMENT", "LINE_COMMENT"
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
			setState(100);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,5,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(71); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(70);
					match(T__0);
					}
					}
					setState(73); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==T__0 );
				setState(75);
				match(T__1);
				setState(77); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(76);
					any();
					}
					}
					setState(79); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__5) | (1L << T__6) | (1L << T__10) | (1L << T__34) | (1L << T__38) | (1L << CALL) | (1L << A) | (1L << NAME) | (1L << NUMBER))) != 0) );
				setState(81);
				match(T__2);
				setState(85);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__5) | (1L << T__9) | (1L << T__11) | (1L << T__14) | (1L << T__39) | (1L << A) | (1L << NAME))) != 0)) {
					{
					{
					setState(82);
					sentence();
					}
					}
					setState(87);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(89); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(88);
					match(T__0);
					}
					}
					setState(91); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==T__0 );
				setState(93);
				match(T__3);
				setState(97);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==NAME) {
					{
					{
					setState(94);
					classDef();
					}
					}
					setState(99);
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
			setState(109);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,6,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(102);
				thereSentence();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(103);
				directSentence();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(104);
				chainSentence();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(105);
				hasSentence();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(106);
				diagramSentence();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(107);
				callSentence();
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(108);
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
			setState(111);
			((CallSentenceContext)_localctx).caller = match(NAME);
			setState(112);
			match(CALL);
			setState(113);
			((CallSentenceContext)_localctx).methodName = match(NAME);
			setState(114);
			match(T__4);
			setState(115);
			((CallSentenceContext)_localctx).objectName = match(NAME);
			setState(119);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__34 || _la==NAME) {
				{
				{
				setState(116);
				withClause();
				}
				}
				setState(121);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(122);
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
			setState(124);
			match(T__5);
			setState(125);
			match(T__6);
			setState(127);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,8,_ctx) ) {
			case 1:
				{
				setState(126);
				match(A);
				}
				break;
			}
			setState(130);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==NAME) {
				{
				setState(129);
				((ThereSentenceContext)_localctx).objectName = multiName();
				}
			}

			setState(140);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==A) {
				{
				setState(132);
				match(A);
				setState(133);
				((ThereSentenceContext)_localctx).className = multiName();
				setState(137);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__34 || _la==NAME) {
					{
					{
					setState(134);
					withClause();
					}
					}
					setState(139);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(142);
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
			setState(145); 
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					{
					setState(144);
					match(NAME);
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(147); 
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
			setState(150);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__9) | (1L << T__11) | (1L << T__14))) != 0)) {
				{
				setState(149);
				((ChainSentenceContext)_localctx).loopIntro = introPhrase();
				}
			}

			setState(152);
			((ChainSentenceContext)_localctx).methodName = match(NAME);
			setState(153);
			predicateObjectPhrase();
			setState(159);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__7) {
				{
				{
				setState(154);
				match(T__7);
				setState(155);
				match(T__8);
				setState(156);
				predicateObjectPhrase();
				}
				}
				setState(161);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(162);
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
			setState(167);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__9:
				enterOuterAlt(_localctx, 1);
				{
				setState(164);
				onPhrase();
				}
				break;
			case T__11:
				enterOuterAlt(_localctx, 2);
				{
				setState(165);
				loopClause();
				}
				break;
			case T__14:
				enterOuterAlt(_localctx, 3);
				{
				setState(166);
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
			setState(169);
			match(T__9);
			setState(171); 
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					{
					setState(170);
					any();
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(173); 
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,16,_ctx);
			} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
			setState(175);
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
			setState(177);
			match(T__11);
			setState(178);
			match(T__12);
			setState(179);
			match(T__13);
			setState(181);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__10) {
				{
				setState(180);
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
		public ValueClauseContext data1;
		public CmpOpContext cmp;
		public ValueClauseContext data2;
		public List<ValueClauseContext> valueClause() {
			return getRuleContexts(ValueClauseContext.class);
		}
		public ValueClauseContext valueClause(int i) {
			return getRuleContext(ValueClauseContext.class,i);
		}
		public CmpOpContext cmpOp() {
			return getRuleContext(CmpOpContext.class,0);
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
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(183);
			match(T__14);
			setState(184);
			((AsPhraseContext)_localctx).data1 = valueClause();
			setState(185);
			((AsPhraseContext)_localctx).cmp = cmpOp();
			setState(186);
			((AsPhraseContext)_localctx).data2 = valueClause();
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
			setState(190);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,18,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(188);
				greaterEqual();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(189);
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
			setState(192);
			match(T__6);
			setState(193);
			match(T__15);
			setState(194);
			match(T__16);
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
			setState(196);
			match(T__6);
			setState(197);
			match(T__17);
			setState(198);
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
			setState(204);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__21:
			case T__22:
				enterOuterAlt(_localctx, 1);
				{
				setState(200);
				createPhrase();
				}
				break;
			case T__25:
			case T__26:
			case T__27:
			case T__28:
				enterOuterAlt(_localctx, 2);
				{
				setState(201);
				verbPhrase();
				}
				break;
			case T__33:
				enterOuterAlt(_localctx, 3);
				{
				setState(202);
				answerPhrase();
				}
				break;
			case T__19:
				enterOuterAlt(_localctx, 4);
				{
				setState(203);
				stopPhrase();
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

	public static class StopPhraseContext extends ParserRuleContext {
		public Token loopName;
		public TerminalNode NAME() { return getToken(FulibScenariosParser.NAME, 0); }
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
		enterRule(_localctx, 28, RULE_stopPhrase);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(206);
			match(T__19);
			setState(207);
			match(T__20);
			setState(208);
			((StopPhraseContext)_localctx).loopName = match(NAME);
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
		enterRule(_localctx, 30, RULE_createPhrase);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(210);
			_la = _input.LA(1);
			if ( !(_la==T__21 || _la==T__22) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			setState(212);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==A) {
				{
				setState(211);
				match(A);
				}
			}

			setState(214);
			((CreatePhraseContext)_localctx).className = match(NAME);
			setState(216);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__23 || _la==T__24) {
				{
				setState(215);
				_la = _input.LA(1);
				if ( !(_la==T__23 || _la==T__24) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
			}

			setState(221);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__34 || _la==NAME) {
				{
				{
				setState(218);
				withClause();
				}
				}
				setState(223);
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
		enterRule(_localctx, 32, RULE_verbPhrase);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(224);
			((VerbPhraseContext)_localctx).verb = _input.LT(1);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__25) | (1L << T__26) | (1L << T__27) | (1L << T__28))) != 0)) ) {
				((VerbPhraseContext)_localctx).verb = (Token)_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			setState(226);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,23,_ctx) ) {
			case 1:
				{
				setState(225);
				match(A);
				}
				break;
			}
			setState(228);
			((VerbPhraseContext)_localctx).value = valueClause();
			setState(241);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__29) {
				{
				setState(229);
				match(T__29);
				setState(231);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==A) {
					{
					setState(230);
					match(A);
					}
				}

				setState(233);
				((VerbPhraseContext)_localctx).fromAttrName = match(NAME);
				setState(239);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__30) {
					{
					setState(234);
					match(T__30);
					setState(236);
					_errHandler.sync(this);
					_la = _input.LA(1);
					if (_la==A) {
						{
						setState(235);
						match(A);
						}
					}

					setState(238);
					((VerbPhraseContext)_localctx).fromObjName = match(NAME);
					}
				}

				}
			}

			setState(252);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__31 || _la==T__32) {
				{
				setState(243);
				_la = _input.LA(1);
				if ( !(_la==T__31 || _la==T__32) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(244);
				((VerbPhraseContext)_localctx).toAttrName = match(NAME);
				setState(250);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__30) {
					{
					setState(245);
					match(T__30);
					setState(247);
					_errHandler.sync(this);
					_la = _input.LA(1);
					if (_la==A) {
						{
						setState(246);
						match(A);
						}
					}

					setState(249);
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
		enterRule(_localctx, 34, RULE_answerPhrase);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(254);
			match(T__33);
			setState(256);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__34 || _la==T__35) {
				{
				setState(255);
				_la = _input.LA(1);
				if ( !(_la==T__34 || _la==T__35) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
			}

			setState(258);
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
		enterRule(_localctx, 36, RULE_expectSentence);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
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
			((ExpectSentenceContext)_localctx).caller = match(NAME);
			setState(264);
			match(T__36);
			setState(266); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(265);
				thatPhrase();
				}
				}
				setState(268); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==T__37 );
			setState(270);
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
		enterRule(_localctx, 38, RULE_thatPhrase);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(272);
			match(T__37);
			setState(273);
			((ThatPhraseContext)_localctx).objectName = multiName();
			setState(274);
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
		enterRule(_localctx, 40, RULE_directSentence);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(276);
			((DirectSentenceContext)_localctx).objectName = multiName();
			setState(277);
			match(T__6);
			setState(279);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==A) {
				{
				setState(278);
				match(A);
				}
			}

			setState(281);
			((DirectSentenceContext)_localctx).className = multiName();
			setState(285);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,35,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(282);
					withClause();
					}
					} 
				}
				setState(287);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,35,_ctx);
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
		enterRule(_localctx, 42, RULE_hasSentence);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
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
			((HasSentenceContext)_localctx).objectName = multiName();
			setState(293); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(292);
				hasClause();
				}
				}
				setState(295); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==T__38 );
			setState(297);
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
		enterRule(_localctx, 44, RULE_hasClause);
		int _la;
		try {
			setState(311);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,40,_ctx) ) {
			case 1:
				_localctx = new UsualHasClauseContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(299);
				match(T__38);
				setState(300);
				((UsualHasClauseContext)_localctx).attrName = match(NAME);
				setState(301);
				((UsualHasClauseContext)_localctx).attrValue = valueClause();
				}
				break;
			case 2:
				_localctx = new NumberHasClauseContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(302);
				match(T__38);
				setState(303);
				((NumberHasClauseContext)_localctx).value = match(NUMBER);
				setState(304);
				((NumberHasClauseContext)_localctx).attrName = multiName();
				setState(306);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__10) {
					{
					setState(305);
					match(T__10);
					}
				}

				setState(309);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__7) {
					{
					setState(308);
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
		enterRule(_localctx, 46, RULE_diagramSentence);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(313);
			match(T__39);
			setState(314);
			match(T__40);
			setState(315);
			((DiagramSentenceContext)_localctx).type = match(NAME);
			setState(316);
			match(T__41);
			setState(317);
			match(T__42);
			setState(318);
			((DiagramSentenceContext)_localctx).fileName = fileNameClause();
			setState(319);
			match(T__43);
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
		enterRule(_localctx, 48, RULE_withClause);
		int _la;
		try {
			setState(335);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,44,_ctx) ) {
			case 1:
				_localctx = new UsualWithClauseContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(322);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__34) {
					{
					setState(321);
					match(T__34);
					}
				}

				setState(324);
				((UsualWithClauseContext)_localctx).attrName = match(NAME);
				setState(325);
				((UsualWithClauseContext)_localctx).attrValue = valueClause();
				}
				break;
			case 2:
				_localctx = new NumberWithClauseContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(326);
				match(T__34);
				setState(327);
				((NumberWithClauseContext)_localctx).value = match(NUMBER);
				setState(328);
				((NumberWithClauseContext)_localctx).attrName = multiName();
				setState(330);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__10) {
					{
					setState(329);
					match(T__10);
					}
				}

				setState(333);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,43,_ctx) ) {
				case 1:
					{
					setState(332);
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
		enterRule(_localctx, 50, RULE_valueClause);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(344); 
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					{
					setState(337);
					rangeClause();
					setState(339);
					_errHandler.sync(this);
					_la = _input.LA(1);
					if (_la==T__10) {
						{
						setState(338);
						match(T__10);
						}
					}

					setState(342);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,46,_ctx) ) {
					case 1:
						{
						setState(341);
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
				setState(346); 
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,47,_ctx);
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
		enterRule(_localctx, 52, RULE_rangeClause);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(349);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==A) {
				{
				setState(348);
				match(A);
				}
			}

			setState(351);
			((RangeClauseContext)_localctx).firstData = valueData();
			setState(357);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,50,_ctx) ) {
			case 1:
				{
				setState(352);
				match(T__31);
				setState(354);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==A) {
					{
					setState(353);
					match(A);
					}
				}

				setState(356);
				((RangeClauseContext)_localctx).secondData = valueData();
				}
				break;
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
		enterRule(_localctx, 54, RULE_valueData);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(360); 
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					{
					setState(359);
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
				setState(362); 
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,51,_ctx);
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
		enterRule(_localctx, 56, RULE_fileNameClause);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(365); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(364);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__2) | (1L << T__44) | (1L << NAME))) != 0)) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
				}
				setState(367); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__2) | (1L << T__44) | (1L << NAME))) != 0) );
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
		enterRule(_localctx, 58, RULE_any);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(369);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__5) | (1L << T__6) | (1L << T__10) | (1L << T__34) | (1L << T__38) | (1L << CALL) | (1L << A) | (1L << NAME) | (1L << NUMBER))) != 0)) ) {
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
		enterRule(_localctx, 60, RULE_classDef);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(371);
			((ClassDefContext)_localctx).className = match(NAME);
			setState(372);
			match(T__45);
			setState(376);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,53,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(373);
					exampleValue();
					}
					} 
				}
				setState(378);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,53,_ctx);
			}
			setState(383);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__46) {
				{
				setState(381);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,54,_ctx) ) {
				case 1:
					{
					setState(379);
					attrDef();
					}
					break;
				case 2:
					{
					setState(380);
					roleDef();
					}
					break;
				}
				}
				setState(385);
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
		enterRule(_localctx, 62, RULE_exampleValue);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(388);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case NAME:
				{
				setState(386);
				((ExampleValueContext)_localctx).nameValue = match(NAME);
				}
				break;
			case NUMBER:
				{
				setState(387);
				((ExampleValueContext)_localctx).numberValue = match(NUMBER);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(391);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__10) {
				{
				setState(390);
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
		enterRule(_localctx, 64, RULE_attrDef);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(393);
			match(T__46);
			setState(394);
			((AttrDefContext)_localctx).attrName = match(NAME);
			setState(395);
			match(T__45);
			setState(399);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,58,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(396);
					exampleValue();
					}
					} 
				}
				setState(401);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,58,_ctx);
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
		enterRule(_localctx, 66, RULE_roleDef);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(402);
			match(T__46);
			setState(403);
			((RoleDefContext)_localctx).roleName = match(NAME);
			setState(404);
			((RoleDefContext)_localctx).card = cardDef();
			setState(405);
			((RoleDefContext)_localctx).className = match(NAME);
			setState(412);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__47) {
				{
				setState(406);
				match(T__47);
				setState(409);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,59,_ctx) ) {
				case 1:
					{
					setState(407);
					((RoleDefContext)_localctx).otherClassName = match(NAME);
					setState(408);
					match(T__2);
					}
					break;
				}
				setState(411);
				((RoleDefContext)_localctx).otherRoleName = match(NAME);
				}
			}

			setState(421);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__45) {
				{
				setState(414);
				match(T__45);
				setState(418);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,61,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(415);
						exampleValue();
						}
						} 
					}
					setState(420);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,61,_ctx);
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
		enterRule(_localctx, 68, RULE_cardDef);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(423);
			_la = _input.LA(1);
			if ( !(_la==T__13 || _la==T__48) ) {
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3:\u01ac\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\3\2\6\2J\n\2\r\2\16\2K\3\2\3\2\6\2P\n\2\r\2\16"+
		"\2Q\3\2\3\2\7\2V\n\2\f\2\16\2Y\13\2\3\2\6\2\\\n\2\r\2\16\2]\3\2\3\2\7"+
		"\2b\n\2\f\2\16\2e\13\2\5\2g\n\2\3\3\3\3\3\3\3\3\3\3\3\3\3\3\5\3p\n\3\3"+
		"\4\3\4\3\4\3\4\3\4\3\4\7\4x\n\4\f\4\16\4{\13\4\3\4\3\4\3\5\3\5\3\5\5\5"+
		"\u0082\n\5\3\5\5\5\u0085\n\5\3\5\3\5\3\5\7\5\u008a\n\5\f\5\16\5\u008d"+
		"\13\5\5\5\u008f\n\5\3\5\3\5\3\6\6\6\u0094\n\6\r\6\16\6\u0095\3\7\5\7\u0099"+
		"\n\7\3\7\3\7\3\7\3\7\3\7\7\7\u00a0\n\7\f\7\16\7\u00a3\13\7\3\7\3\7\3\b"+
		"\3\b\3\b\5\b\u00aa\n\b\3\t\3\t\6\t\u00ae\n\t\r\t\16\t\u00af\3\t\3\t\3"+
		"\n\3\n\3\n\3\n\5\n\u00b8\n\n\3\13\3\13\3\13\3\13\3\13\3\f\3\f\5\f\u00c1"+
		"\n\f\3\r\3\r\3\r\3\r\3\16\3\16\3\16\3\16\3\17\3\17\3\17\3\17\5\17\u00cf"+
		"\n\17\3\20\3\20\3\20\3\20\3\21\3\21\5\21\u00d7\n\21\3\21\3\21\5\21\u00db"+
		"\n\21\3\21\7\21\u00de\n\21\f\21\16\21\u00e1\13\21\3\22\3\22\5\22\u00e5"+
		"\n\22\3\22\3\22\3\22\5\22\u00ea\n\22\3\22\3\22\3\22\5\22\u00ef\n\22\3"+
		"\22\5\22\u00f2\n\22\5\22\u00f4\n\22\3\22\3\22\3\22\3\22\5\22\u00fa\n\22"+
		"\3\22\5\22\u00fd\n\22\5\22\u00ff\n\22\3\23\3\23\5\23\u0103\n\23\3\23\3"+
		"\23\3\24\5\24\u0108\n\24\3\24\3\24\3\24\6\24\u010d\n\24\r\24\16\24\u010e"+
		"\3\24\3\24\3\25\3\25\3\25\3\25\3\26\3\26\3\26\5\26\u011a\n\26\3\26\3\26"+
		"\7\26\u011e\n\26\f\26\16\26\u0121\13\26\3\27\5\27\u0124\n\27\3\27\3\27"+
		"\6\27\u0128\n\27\r\27\16\27\u0129\3\27\3\27\3\30\3\30\3\30\3\30\3\30\3"+
		"\30\3\30\5\30\u0135\n\30\3\30\5\30\u0138\n\30\5\30\u013a\n\30\3\31\3\31"+
		"\3\31\3\31\3\31\3\31\3\31\3\31\3\32\5\32\u0145\n\32\3\32\3\32\3\32\3\32"+
		"\3\32\3\32\5\32\u014d\n\32\3\32\5\32\u0150\n\32\5\32\u0152\n\32\3\33\3"+
		"\33\5\33\u0156\n\33\3\33\5\33\u0159\n\33\6\33\u015b\n\33\r\33\16\33\u015c"+
		"\3\34\5\34\u0160\n\34\3\34\3\34\3\34\5\34\u0165\n\34\3\34\5\34\u0168\n"+
		"\34\3\35\6\35\u016b\n\35\r\35\16\35\u016c\3\36\6\36\u0170\n\36\r\36\16"+
		"\36\u0171\3\37\3\37\3 \3 \3 \7 \u0179\n \f \16 \u017c\13 \3 \3 \7 \u0180"+
		"\n \f \16 \u0183\13 \3!\3!\5!\u0187\n!\3!\5!\u018a\n!\3\"\3\"\3\"\3\""+
		"\7\"\u0190\n\"\f\"\16\"\u0193\13\"\3#\3#\3#\3#\3#\3#\3#\5#\u019c\n#\3"+
		"#\5#\u019f\n#\3#\3#\7#\u01a3\n#\f#\16#\u01a6\13#\5#\u01a8\n#\3$\3$\3$"+
		"\2\2%\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36 \"$&(*,.\60\62\64\668:<>"+
		"@BDF\2\13\3\2\30\31\3\2\32\33\3\2\34\37\3\2\"#\3\2%&\3\2\66\67\5\2\5\5"+
		"//\66\66\7\2\b\t\r\r%%))\64\67\4\2\20\20\63\63\2\u01cf\2f\3\2\2\2\4o\3"+
		"\2\2\2\6q\3\2\2\2\b~\3\2\2\2\n\u0093\3\2\2\2\f\u0098\3\2\2\2\16\u00a9"+
		"\3\2\2\2\20\u00ab\3\2\2\2\22\u00b3\3\2\2\2\24\u00b9\3\2\2\2\26\u00c0\3"+
		"\2\2\2\30\u00c2\3\2\2\2\32\u00c6\3\2\2\2\34\u00ce\3\2\2\2\36\u00d0\3\2"+
		"\2\2 \u00d4\3\2\2\2\"\u00e2\3\2\2\2$\u0100\3\2\2\2&\u0107\3\2\2\2(\u0112"+
		"\3\2\2\2*\u0116\3\2\2\2,\u0123\3\2\2\2.\u0139\3\2\2\2\60\u013b\3\2\2\2"+
		"\62\u0151\3\2\2\2\64\u015a\3\2\2\2\66\u015f\3\2\2\28\u016a\3\2\2\2:\u016f"+
		"\3\2\2\2<\u0173\3\2\2\2>\u0175\3\2\2\2@\u0186\3\2\2\2B\u018b\3\2\2\2D"+
		"\u0194\3\2\2\2F\u01a9\3\2\2\2HJ\7\3\2\2IH\3\2\2\2JK\3\2\2\2KI\3\2\2\2"+
		"KL\3\2\2\2LM\3\2\2\2MO\7\4\2\2NP\5<\37\2ON\3\2\2\2PQ\3\2\2\2QO\3\2\2\2"+
		"QR\3\2\2\2RS\3\2\2\2SW\7\5\2\2TV\5\4\3\2UT\3\2\2\2VY\3\2\2\2WU\3\2\2\2"+
		"WX\3\2\2\2Xg\3\2\2\2YW\3\2\2\2Z\\\7\3\2\2[Z\3\2\2\2\\]\3\2\2\2][\3\2\2"+
		"\2]^\3\2\2\2^_\3\2\2\2_c\7\6\2\2`b\5> \2a`\3\2\2\2be\3\2\2\2ca\3\2\2\2"+
		"cd\3\2\2\2dg\3\2\2\2ec\3\2\2\2fI\3\2\2\2f[\3\2\2\2g\3\3\2\2\2hp\5\b\5"+
		"\2ip\5*\26\2jp\5\f\7\2kp\5,\27\2lp\5\60\31\2mp\5\6\4\2np\5&\24\2oh\3\2"+
		"\2\2oi\3\2\2\2oj\3\2\2\2ok\3\2\2\2ol\3\2\2\2om\3\2\2\2on\3\2\2\2p\5\3"+
		"\2\2\2qr\7\66\2\2rs\7\64\2\2st\7\66\2\2tu\7\7\2\2uy\7\66\2\2vx\5\62\32"+
		"\2wv\3\2\2\2x{\3\2\2\2yw\3\2\2\2yz\3\2\2\2z|\3\2\2\2{y\3\2\2\2|}\7\5\2"+
		"\2}\7\3\2\2\2~\177\7\b\2\2\177\u0081\7\t\2\2\u0080\u0082\7\65\2\2\u0081"+
		"\u0080\3\2\2\2\u0081\u0082\3\2\2\2\u0082\u0084\3\2\2\2\u0083\u0085\5\n"+
		"\6\2\u0084\u0083\3\2\2\2\u0084\u0085\3\2\2\2\u0085\u008e\3\2\2\2\u0086"+
		"\u0087\7\65\2\2\u0087\u008b\5\n\6\2\u0088\u008a\5\62\32\2\u0089\u0088"+
		"\3\2\2\2\u008a\u008d\3\2\2\2\u008b\u0089\3\2\2\2\u008b\u008c\3\2\2\2\u008c"+
		"\u008f\3\2\2\2\u008d\u008b\3\2\2\2\u008e\u0086\3\2\2\2\u008e\u008f\3\2"+
		"\2\2\u008f\u0090\3\2\2\2\u0090\u0091\7\5\2\2\u0091\t\3\2\2\2\u0092\u0094"+
		"\7\66\2\2\u0093\u0092\3\2\2\2\u0094\u0095\3\2\2\2\u0095\u0093\3\2\2\2"+
		"\u0095\u0096\3\2\2\2\u0096\13\3\2\2\2\u0097\u0099\5\16\b\2\u0098\u0097"+
		"\3\2\2\2\u0098\u0099\3\2\2\2\u0099\u009a\3\2\2\2\u009a\u009b\7\66\2\2"+
		"\u009b\u00a1\5\34\17\2\u009c\u009d\7\n\2\2\u009d\u009e\7\13\2\2\u009e"+
		"\u00a0\5\34\17\2\u009f\u009c\3\2\2\2\u00a0\u00a3\3\2\2\2\u00a1\u009f\3"+
		"\2\2\2\u00a1\u00a2\3\2\2\2\u00a2\u00a4\3\2\2\2\u00a3\u00a1\3\2\2\2\u00a4"+
		"\u00a5\7\5\2\2\u00a5\r\3\2\2\2\u00a6\u00aa\5\20\t\2\u00a7\u00aa\5\22\n"+
		"\2\u00a8\u00aa\5\24\13\2\u00a9\u00a6\3\2\2\2\u00a9\u00a7\3\2\2\2\u00a9"+
		"\u00a8\3\2\2\2\u00aa\17\3\2\2\2\u00ab\u00ad\7\f\2\2\u00ac\u00ae\5<\37"+
		"\2\u00ad\u00ac\3\2\2\2\u00ae\u00af\3\2\2\2\u00af\u00ad\3\2\2\2\u00af\u00b0"+
		"\3\2\2\2\u00b0\u00b1\3\2\2\2\u00b1\u00b2\7\r\2\2\u00b2\21\3\2\2\2\u00b3"+
		"\u00b4\7\16\2\2\u00b4\u00b5\7\17\2\2\u00b5\u00b7\7\20\2\2\u00b6\u00b8"+
		"\7\r\2\2\u00b7\u00b6\3\2\2\2\u00b7\u00b8\3\2\2\2\u00b8\23\3\2\2\2\u00b9"+
		"\u00ba\7\21\2\2\u00ba\u00bb\5\64\33\2\u00bb\u00bc\5\26\f\2\u00bc\u00bd"+
		"\5\64\33\2\u00bd\25\3\2\2\2\u00be\u00c1\5\30\r\2\u00bf\u00c1\5\32\16\2"+
		"\u00c0\u00be\3\2\2\2\u00c0\u00bf\3\2\2\2\u00c1\27\3\2\2\2\u00c2\u00c3"+
		"\7\t\2\2\u00c3\u00c4\7\22\2\2\u00c4\u00c5\7\23\2\2\u00c5\31\3\2\2\2\u00c6"+
		"\u00c7\7\t\2\2\u00c7\u00c8\7\24\2\2\u00c8\u00c9\7\25\2\2\u00c9\33\3\2"+
		"\2\2\u00ca\u00cf\5 \21\2\u00cb\u00cf\5\"\22\2\u00cc\u00cf\5$\23\2\u00cd"+
		"\u00cf\5\36\20\2\u00ce\u00ca\3\2\2\2\u00ce\u00cb\3\2\2\2\u00ce\u00cc\3"+
		"\2\2\2\u00ce\u00cd\3\2\2\2\u00cf\35\3\2\2\2\u00d0\u00d1\7\26\2\2\u00d1"+
		"\u00d2\7\27\2\2\u00d2\u00d3\7\66\2\2\u00d3\37\3\2\2\2\u00d4\u00d6\t\2"+
		"\2\2\u00d5\u00d7\7\65\2\2\u00d6\u00d5\3\2\2\2\u00d6\u00d7\3\2\2\2\u00d7"+
		"\u00d8\3\2\2\2\u00d8\u00da\7\66\2\2\u00d9\u00db\t\3\2\2\u00da\u00d9\3"+
		"\2\2\2\u00da\u00db\3\2\2\2\u00db\u00df\3\2\2\2\u00dc\u00de\5\62\32\2\u00dd"+
		"\u00dc\3\2\2\2\u00de\u00e1\3\2\2\2\u00df\u00dd\3\2\2\2\u00df\u00e0\3\2"+
		"\2\2\u00e0!\3\2\2\2\u00e1\u00df\3\2\2\2\u00e2\u00e4\t\4\2\2\u00e3\u00e5"+
		"\7\65\2\2\u00e4\u00e3\3\2\2\2\u00e4\u00e5\3\2\2\2\u00e5\u00e6\3\2\2\2"+
		"\u00e6\u00f3\5\64\33\2\u00e7\u00e9\7 \2\2\u00e8\u00ea\7\65\2\2\u00e9\u00e8"+
		"\3\2\2\2\u00e9\u00ea\3\2\2\2\u00ea\u00eb\3\2\2\2\u00eb\u00f1\7\66\2\2"+
		"\u00ec\u00ee\7!\2\2\u00ed\u00ef\7\65\2\2\u00ee\u00ed\3\2\2\2\u00ee\u00ef"+
		"\3\2\2\2\u00ef\u00f0\3\2\2\2\u00f0\u00f2\7\66\2\2\u00f1\u00ec\3\2\2\2"+
		"\u00f1\u00f2\3\2\2\2\u00f2\u00f4\3\2\2\2\u00f3\u00e7\3\2\2\2\u00f3\u00f4"+
		"\3\2\2\2\u00f4\u00fe\3\2\2\2\u00f5\u00f6\t\5\2\2\u00f6\u00fc\7\66\2\2"+
		"\u00f7\u00f9\7!\2\2\u00f8\u00fa\7\65\2\2\u00f9\u00f8\3\2\2\2\u00f9\u00fa"+
		"\3\2\2\2\u00fa\u00fb\3\2\2\2\u00fb\u00fd\7\66\2\2\u00fc\u00f7\3\2\2\2"+
		"\u00fc\u00fd\3\2\2\2\u00fd\u00ff\3\2\2\2\u00fe\u00f5\3\2\2\2\u00fe\u00ff"+
		"\3\2\2\2\u00ff#\3\2\2\2\u0100\u0102\7$\2\2\u0101\u0103\t\6\2\2\u0102\u0101"+
		"\3\2\2\2\u0102\u0103\3\2\2\2\u0103\u0104\3\2\2\2\u0104\u0105\5\64\33\2"+
		"\u0105%\3\2\2\2\u0106\u0108\7\65\2\2\u0107\u0106\3\2\2\2\u0107\u0108\3"+
		"\2\2\2\u0108\u0109\3\2\2\2\u0109\u010a\7\66\2\2\u010a\u010c\7\'\2\2\u010b"+
		"\u010d\5(\25\2\u010c\u010b\3\2\2\2\u010d\u010e\3\2\2\2\u010e\u010c\3\2"+
		"\2\2\u010e\u010f\3\2\2\2\u010f\u0110\3\2\2\2\u0110\u0111\7\5\2\2\u0111"+
		"\'\3\2\2\2\u0112\u0113\7(\2\2\u0113\u0114\5\n\6\2\u0114\u0115\5.\30\2"+
		"\u0115)\3\2\2\2\u0116\u0117\5\n\6\2\u0117\u0119\7\t\2\2\u0118\u011a\7"+
		"\65\2\2\u0119\u0118\3\2\2\2\u0119\u011a\3\2\2\2\u011a\u011b\3\2\2\2\u011b"+
		"\u011f\5\n\6\2\u011c\u011e\5\62\32\2\u011d\u011c\3\2\2\2\u011e\u0121\3"+
		"\2\2\2\u011f\u011d\3\2\2\2\u011f\u0120\3\2\2\2\u0120+\3\2\2\2\u0121\u011f"+
		"\3\2\2\2\u0122\u0124\7\65\2\2\u0123\u0122\3\2\2\2\u0123\u0124\3\2\2\2"+
		"\u0124\u0125\3\2\2\2\u0125\u0127\5\n\6\2\u0126\u0128\5.\30\2\u0127\u0126"+
		"\3\2\2\2\u0128\u0129\3\2\2\2\u0129\u0127\3\2\2\2\u0129\u012a\3\2\2\2\u012a"+
		"\u012b\3\2\2\2\u012b\u012c\7\5\2\2\u012c-\3\2\2\2\u012d\u012e\7)\2\2\u012e"+
		"\u012f\7\66\2\2\u012f\u013a\5\64\33\2\u0130\u0131\7)\2\2\u0131\u0132\7"+
		"\67\2\2\u0132\u0134\5\n\6\2\u0133\u0135\7\r\2\2\u0134\u0133\3\2\2\2\u0134"+
		"\u0135\3\2\2\2\u0135\u0137\3\2\2\2\u0136\u0138\7\n\2\2\u0137\u0136\3\2"+
		"\2\2\u0137\u0138\3\2\2\2\u0138\u013a\3\2\2\2\u0139\u012d\3\2\2\2\u0139"+
		"\u0130\3\2\2\2\u013a/\3\2\2\2\u013b\u013c\7*\2\2\u013c\u013d\7+\2\2\u013d"+
		"\u013e\7\66\2\2\u013e\u013f\7,\2\2\u013f\u0140\7-\2\2\u0140\u0141\5:\36"+
		"\2\u0141\u0142\7.\2\2\u0142\61\3\2\2\2\u0143\u0145\7%\2\2\u0144\u0143"+
		"\3\2\2\2\u0144\u0145\3\2\2\2\u0145\u0146\3\2\2\2\u0146\u0147\7\66\2\2"+
		"\u0147\u0152\5\64\33\2\u0148\u0149\7%\2\2\u0149\u014a\7\67\2\2\u014a\u014c"+
		"\5\n\6\2\u014b\u014d\7\r\2\2\u014c\u014b\3\2\2\2\u014c\u014d\3\2\2\2\u014d"+
		"\u014f\3\2\2\2\u014e\u0150\7\n\2\2\u014f\u014e\3\2\2\2\u014f\u0150\3\2"+
		"\2\2\u0150\u0152\3\2\2\2\u0151\u0144\3\2\2\2\u0151\u0148\3\2\2\2\u0152"+
		"\63\3\2\2\2\u0153\u0155\5\66\34\2\u0154\u0156\7\r\2\2\u0155\u0154\3\2"+
		"\2\2\u0155\u0156\3\2\2\2\u0156\u0158\3\2\2\2\u0157\u0159\7\n\2\2\u0158"+
		"\u0157\3\2\2\2\u0158\u0159\3\2\2\2\u0159\u015b\3\2\2\2\u015a\u0153\3\2"+
		"\2\2\u015b\u015c\3\2\2\2\u015c\u015a\3\2\2\2\u015c\u015d\3\2\2\2\u015d"+
		"\65\3\2\2\2\u015e\u0160\7\65\2\2\u015f\u015e\3\2\2\2\u015f\u0160\3\2\2"+
		"\2\u0160\u0161\3\2\2\2\u0161\u0167\58\35\2\u0162\u0164\7\"\2\2\u0163\u0165"+
		"\7\65\2\2\u0164\u0163\3\2\2\2\u0164\u0165\3\2\2\2\u0165\u0166\3\2\2\2"+
		"\u0166\u0168\58\35\2\u0167\u0162\3\2\2\2\u0167\u0168\3\2\2\2\u0168\67"+
		"\3\2\2\2\u0169\u016b\t\7\2\2\u016a\u0169\3\2\2\2\u016b\u016c\3\2\2\2\u016c"+
		"\u016a\3\2\2\2\u016c\u016d\3\2\2\2\u016d9\3\2\2\2\u016e\u0170\t\b\2\2"+
		"\u016f\u016e\3\2\2\2\u0170\u0171\3\2\2\2\u0171\u016f\3\2\2\2\u0171\u0172"+
		"\3\2\2\2\u0172;\3\2\2\2\u0173\u0174\t\t\2\2\u0174=\3\2\2\2\u0175\u0176"+
		"\7\66\2\2\u0176\u017a\7\60\2\2\u0177\u0179\5@!\2\u0178\u0177\3\2\2\2\u0179"+
		"\u017c\3\2\2\2\u017a\u0178\3\2\2\2\u017a\u017b\3\2\2\2\u017b\u0181\3\2"+
		"\2\2\u017c\u017a\3\2\2\2\u017d\u0180\5B\"\2\u017e\u0180\5D#\2\u017f\u017d"+
		"\3\2\2\2\u017f\u017e\3\2\2\2\u0180\u0183\3\2\2\2\u0181\u017f\3\2\2\2\u0181"+
		"\u0182\3\2\2\2\u0182?\3\2\2\2\u0183\u0181\3\2\2\2\u0184\u0187\7\66\2\2"+
		"\u0185\u0187\7\67\2\2\u0186\u0184\3\2\2\2\u0186\u0185\3\2\2\2\u0187\u0189"+
		"\3\2\2\2\u0188\u018a\7\r\2\2\u0189\u0188\3\2\2\2\u0189\u018a\3\2\2\2\u018a"+
		"A\3\2\2\2\u018b\u018c\7\61\2\2\u018c\u018d\7\66\2\2\u018d\u0191\7\60\2"+
		"\2\u018e\u0190\5@!\2\u018f\u018e\3\2\2\2\u0190\u0193\3\2\2\2\u0191\u018f"+
		"\3\2\2\2\u0191\u0192\3\2\2\2\u0192C\3\2\2\2\u0193\u0191\3\2\2\2\u0194"+
		"\u0195\7\61\2\2\u0195\u0196\7\66\2\2\u0196\u0197\5F$\2\u0197\u019e\7\66"+
		"\2\2\u0198\u019b\7\62\2\2\u0199\u019a\7\66\2\2\u019a\u019c\7\5\2\2\u019b"+
		"\u0199\3\2\2\2\u019b\u019c\3\2\2\2\u019c\u019d\3\2\2\2\u019d\u019f\7\66"+
		"\2\2\u019e\u0198\3\2\2\2\u019e\u019f\3\2\2\2\u019f\u01a7\3\2\2\2\u01a0"+
		"\u01a4\7\60\2\2\u01a1\u01a3\5@!\2\u01a2\u01a1\3\2\2\2\u01a3\u01a6\3\2"+
		"\2\2\u01a4\u01a2\3\2\2\2\u01a4\u01a5\3\2\2\2\u01a5\u01a8\3\2\2\2\u01a6"+
		"\u01a4\3\2\2\2\u01a7\u01a0\3\2\2\2\u01a7\u01a8\3\2\2\2\u01a8E\3\2\2\2"+
		"\u01a9\u01aa\t\n\2\2\u01aaG\3\2\2\2AKQW]cfoy\u0081\u0084\u008b\u008e\u0095"+
		"\u0098\u00a1\u00a9\u00af\u00b7\u00c0\u00ce\u00d6\u00da\u00df\u00e4\u00e9"+
		"\u00ee\u00f1\u00f3\u00f9\u00fc\u00fe\u0102\u0107\u010e\u0119\u011f\u0123"+
		"\u0129\u0134\u0137\u0139\u0144\u014c\u014f\u0151\u0155\u0158\u015c\u015f"+
		"\u0164\u0167\u016c\u0171\u017a\u017f\u0181\u0186\u0189\u0191\u019b\u019e"+
		"\u01a4\u01a7";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}