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
		CALL=39, A=40, NAME=41, NUMBER=42, WS=43, COMMENT=44, LINE_COMMENT=45;
	public static final int
		RULE_scenario = 0, RULE_sentence = 1, RULE_callSentence = 2, RULE_thereSentence = 3, 
		RULE_multiName = 4, RULE_chainSentence = 5, RULE_predicateObjectPhrase = 6, 
		RULE_createPhrase = 7, RULE_verbPhrase = 8, RULE_answerPhrase = 9, RULE_expectSentence = 10, 
		RULE_thatPhrase = 11, RULE_directSentence = 12, RULE_hasSentence = 13, 
		RULE_hasClause = 14, RULE_diagramSentence = 15, RULE_withClause = 16, 
		RULE_valueClause = 17, RULE_valueData = 18, RULE_fileNameClause = 19, 
		RULE_any = 20, RULE_classDef = 21, RULE_exampleValue = 22, RULE_attrDef = 23, 
		RULE_roleDef = 24, RULE_cardDef = 25;
	private static String[] makeRuleNames() {
		return new String[] {
			"scenario", "sentence", "callSentence", "thereSentence", "multiName", 
			"chainSentence", "predicateObjectPhrase", "createPhrase", "verbPhrase", 
			"answerPhrase", "expectSentence", "thatPhrase", "directSentence", "hasSentence", 
			"hasClause", "diagramSentence", "withClause", "valueClause", "valueData", 
			"fileNameClause", "any", "classDef", "exampleValue", "attrDef", "roleDef", 
			"cardDef"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'#'", "'Scenario'", "'.'", "'Register'", "'on'", "'There'", "'is'", 
			"'and'", "'it'", "'create'", "'creates'", "'cards'", "'card'", "'adds'", 
			"'puts'", "'reads'", "'to'", "'into'", "'from'", "'of'", "'answers'", 
			"'with'", "':'", "'expect'", "'that'", "'has'", "','", "'!'", "'['", 
			"']'", "'('", "')'", "'/'", "'e.g.'", "'+'", "'cf.'", "'one'", "'many'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
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
			setState(82);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,5,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(53); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(52);
					match(T__0);
					}
					}
					setState(55); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==T__0 );
				setState(57);
				match(T__1);
				setState(59); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(58);
					any();
					}
					}
					setState(61); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__5) | (1L << T__6) | (1L << T__21) | (1L << T__25) | (1L << T__26) | (1L << CALL) | (1L << A) | (1L << NAME) | (1L << NUMBER))) != 0) );
				setState(63);
				match(T__2);
				setState(67);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__5) | (1L << T__27) | (1L << A) | (1L << NAME))) != 0)) {
					{
					{
					setState(64);
					sentence();
					}
					}
					setState(69);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
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
				match(T__3);
				setState(79);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==NAME) {
					{
					{
					setState(76);
					classDef();
					}
					}
					setState(81);
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
			setState(91);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,6,_ctx) ) {
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
				chainSentence();
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
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(89);
				callSentence();
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(90);
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
			setState(93);
			((CallSentenceContext)_localctx).caller = match(NAME);
			setState(94);
			match(CALL);
			setState(95);
			((CallSentenceContext)_localctx).methodName = match(NAME);
			setState(96);
			match(T__4);
			setState(97);
			((CallSentenceContext)_localctx).objectName = match(NAME);
			setState(101);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__21 || _la==NAME) {
				{
				{
				setState(98);
				withClause();
				}
				}
				setState(103);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(104);
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
			setState(106);
			match(T__5);
			setState(107);
			match(T__6);
			setState(109);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,8,_ctx) ) {
			case 1:
				{
				setState(108);
				match(A);
				}
				break;
			}
			setState(112);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==NAME) {
				{
				setState(111);
				((ThereSentenceContext)_localctx).objectName = multiName();
				}
			}

			setState(122);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==A) {
				{
				setState(114);
				match(A);
				setState(115);
				((ThereSentenceContext)_localctx).className = multiName();
				setState(119);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__21 || _la==NAME) {
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
				}
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
			setState(127); 
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					{
					setState(126);
					match(NAME);
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(129); 
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
		public Token methodName;
		public List<PredicateObjectPhraseContext> predicateObjectPhrase() {
			return getRuleContexts(PredicateObjectPhraseContext.class);
		}
		public PredicateObjectPhraseContext predicateObjectPhrase(int i) {
			return getRuleContext(PredicateObjectPhraseContext.class,i);
		}
		public TerminalNode NAME() { return getToken(FulibScenariosParser.NAME, 0); }
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
			setState(131);
			((ChainSentenceContext)_localctx).methodName = match(NAME);
			setState(132);
			predicateObjectPhrase();
			setState(138);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__7) {
				{
				{
				setState(133);
				match(T__7);
				setState(134);
				match(T__8);
				setState(135);
				predicateObjectPhrase();
				}
				}
				setState(140);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(141);
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
		enterRule(_localctx, 12, RULE_predicateObjectPhrase);
		try {
			setState(146);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__9:
			case T__10:
				enterOuterAlt(_localctx, 1);
				{
				setState(143);
				createPhrase();
				}
				break;
			case T__13:
			case T__14:
			case T__15:
				enterOuterAlt(_localctx, 2);
				{
				setState(144);
				verbPhrase();
				}
				break;
			case T__20:
				enterOuterAlt(_localctx, 3);
				{
				setState(145);
				answerPhrase();
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
		enterRule(_localctx, 14, RULE_createPhrase);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(148);
			_la = _input.LA(1);
			if ( !(_la==T__9 || _la==T__10) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			setState(150);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==A) {
				{
				setState(149);
				match(A);
				}
			}

			setState(152);
			((CreatePhraseContext)_localctx).className = match(NAME);
			setState(154);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__11 || _la==T__12) {
				{
				setState(153);
				_la = _input.LA(1);
				if ( !(_la==T__11 || _la==T__12) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
			}

			setState(159);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__21 || _la==NAME) {
				{
				{
				setState(156);
				withClause();
				}
				}
				setState(161);
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
		public ValueDataContext value;
		public Token attrName;
		public Token targetName;
		public ValueDataContext valueData() {
			return getRuleContext(ValueDataContext.class,0);
		}
		public List<TerminalNode> NAME() { return getTokens(FulibScenariosParser.NAME); }
		public TerminalNode NAME(int i) {
			return getToken(FulibScenariosParser.NAME, i);
		}
		public List<TerminalNode> A() { return getTokens(FulibScenariosParser.A); }
		public TerminalNode A(int i) {
			return getToken(FulibScenariosParser.A, i);
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
		enterRule(_localctx, 16, RULE_verbPhrase);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(162);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__13) | (1L << T__14) | (1L << T__15))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			setState(164);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==A) {
				{
				setState(163);
				match(A);
				}
			}

			setState(166);
			((VerbPhraseContext)_localctx).value = valueData();
			setState(167);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__16) | (1L << T__17) | (1L << T__18))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			setState(169);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==A) {
				{
				setState(168);
				match(A);
				}
			}

			setState(171);
			((VerbPhraseContext)_localctx).attrName = match(NAME);
			setState(172);
			match(T__19);
			setState(174);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==A) {
				{
				setState(173);
				match(A);
				}
			}

			setState(176);
			((VerbPhraseContext)_localctx).targetName = match(NAME);
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
		public ValueDataContext value;
		public ValueDataContext valueData() {
			return getRuleContext(ValueDataContext.class,0);
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
		enterRule(_localctx, 18, RULE_answerPhrase);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(178);
			match(T__20);
			setState(180);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__21 || _la==T__22) {
				{
				setState(179);
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

			setState(182);
			((AnswerPhraseContext)_localctx).value = valueData();
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
		enterRule(_localctx, 20, RULE_expectSentence);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(185);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==A) {
				{
				setState(184);
				match(A);
				}
			}

			setState(187);
			((ExpectSentenceContext)_localctx).caller = match(NAME);
			setState(188);
			match(T__23);
			setState(190); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(189);
				thatPhrase();
				}
				}
				setState(192); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==T__24 );
			setState(194);
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
		enterRule(_localctx, 22, RULE_thatPhrase);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(196);
			match(T__24);
			setState(197);
			((ThatPhraseContext)_localctx).objectName = multiName();
			setState(198);
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
		enterRule(_localctx, 24, RULE_directSentence);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(200);
			((DirectSentenceContext)_localctx).objectName = multiName();
			setState(201);
			match(T__6);
			setState(203);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==A) {
				{
				setState(202);
				match(A);
				}
			}

			setState(205);
			((DirectSentenceContext)_localctx).className = multiName();
			setState(209);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,25,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(206);
					withClause();
					}
					} 
				}
				setState(211);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,25,_ctx);
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
		enterRule(_localctx, 26, RULE_hasSentence);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(213);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==A) {
				{
				setState(212);
				match(A);
				}
			}

			setState(215);
			((HasSentenceContext)_localctx).objectName = multiName();
			setState(217); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(216);
				hasClause();
				}
				}
				setState(219); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==T__25 );
			setState(221);
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
		enterRule(_localctx, 28, RULE_hasClause);
		int _la;
		try {
			setState(235);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,30,_ctx) ) {
			case 1:
				_localctx = new UsualHasClauseContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(223);
				match(T__25);
				setState(224);
				((UsualHasClauseContext)_localctx).attrName = match(NAME);
				setState(225);
				((UsualHasClauseContext)_localctx).attrValue = valueClause();
				}
				break;
			case 2:
				_localctx = new NumberHasClauseContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(226);
				match(T__25);
				setState(227);
				((NumberHasClauseContext)_localctx).value = match(NUMBER);
				setState(228);
				((NumberHasClauseContext)_localctx).attrName = multiName();
				setState(230);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__26) {
					{
					setState(229);
					match(T__26);
					}
				}

				setState(233);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__7) {
					{
					setState(232);
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
		enterRule(_localctx, 30, RULE_diagramSentence);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(237);
			match(T__27);
			setState(238);
			match(T__28);
			setState(239);
			((DiagramSentenceContext)_localctx).type = match(NAME);
			setState(240);
			match(T__29);
			setState(241);
			match(T__30);
			setState(242);
			((DiagramSentenceContext)_localctx).fileName = fileNameClause();
			setState(243);
			match(T__31);
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
		enterRule(_localctx, 32, RULE_withClause);
		int _la;
		try {
			setState(259);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,34,_ctx) ) {
			case 1:
				_localctx = new UsualWithClauseContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(246);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__21) {
					{
					setState(245);
					match(T__21);
					}
				}

				setState(248);
				((UsualWithClauseContext)_localctx).attrName = match(NAME);
				setState(249);
				((UsualWithClauseContext)_localctx).attrValue = valueClause();
				}
				break;
			case 2:
				_localctx = new NumberWithClauseContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(250);
				match(T__21);
				setState(251);
				((NumberWithClauseContext)_localctx).value = match(NUMBER);
				setState(252);
				((NumberWithClauseContext)_localctx).attrName = multiName();
				setState(254);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__26) {
					{
					setState(253);
					match(T__26);
					}
				}

				setState(257);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,33,_ctx) ) {
				case 1:
					{
					setState(256);
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
		enterRule(_localctx, 34, RULE_valueClause);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(271); 
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					{
					setState(262);
					_errHandler.sync(this);
					_la = _input.LA(1);
					if (_la==A) {
						{
						setState(261);
						match(A);
						}
					}

					setState(264);
					valueData();
					setState(266);
					_errHandler.sync(this);
					_la = _input.LA(1);
					if (_la==T__26) {
						{
						setState(265);
						match(T__26);
						}
					}

					setState(269);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,37,_ctx) ) {
					case 1:
						{
						setState(268);
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
				setState(273); 
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,38,_ctx);
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
		enterRule(_localctx, 36, RULE_valueData);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(276); 
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					{
					setState(275);
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
				setState(278); 
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,39,_ctx);
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
		enterRule(_localctx, 38, RULE_fileNameClause);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(281); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(280);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__2) | (1L << T__32) | (1L << NAME))) != 0)) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
				}
				setState(283); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__2) | (1L << T__32) | (1L << NAME))) != 0) );
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
		enterRule(_localctx, 40, RULE_any);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(285);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__5) | (1L << T__6) | (1L << T__21) | (1L << T__25) | (1L << T__26) | (1L << CALL) | (1L << A) | (1L << NAME) | (1L << NUMBER))) != 0)) ) {
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
		enterRule(_localctx, 42, RULE_classDef);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(287);
			((ClassDefContext)_localctx).className = match(NAME);
			setState(288);
			match(T__33);
			setState(292);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,41,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(289);
					exampleValue();
					}
					} 
				}
				setState(294);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,41,_ctx);
			}
			setState(299);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__34) {
				{
				setState(297);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,42,_ctx) ) {
				case 1:
					{
					setState(295);
					attrDef();
					}
					break;
				case 2:
					{
					setState(296);
					roleDef();
					}
					break;
				}
				}
				setState(301);
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
		enterRule(_localctx, 44, RULE_exampleValue);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(304);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case NAME:
				{
				setState(302);
				((ExampleValueContext)_localctx).nameValue = match(NAME);
				}
				break;
			case NUMBER:
				{
				setState(303);
				((ExampleValueContext)_localctx).numberValue = match(NUMBER);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(307);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__26) {
				{
				setState(306);
				match(T__26);
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
		enterRule(_localctx, 46, RULE_attrDef);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(309);
			match(T__34);
			setState(310);
			((AttrDefContext)_localctx).attrName = match(NAME);
			setState(311);
			match(T__33);
			setState(315);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,46,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(312);
					exampleValue();
					}
					} 
				}
				setState(317);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,46,_ctx);
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
		enterRule(_localctx, 48, RULE_roleDef);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(318);
			match(T__34);
			setState(319);
			((RoleDefContext)_localctx).roleName = match(NAME);
			setState(320);
			((RoleDefContext)_localctx).card = cardDef();
			setState(321);
			((RoleDefContext)_localctx).className = match(NAME);
			setState(328);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__35) {
				{
				setState(322);
				match(T__35);
				setState(325);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,47,_ctx) ) {
				case 1:
					{
					setState(323);
					((RoleDefContext)_localctx).otherClassName = match(NAME);
					setState(324);
					match(T__2);
					}
					break;
				}
				setState(327);
				((RoleDefContext)_localctx).otherRoleName = match(NAME);
				}
			}

			setState(337);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__33) {
				{
				setState(330);
				match(T__33);
				setState(334);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,49,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(331);
						exampleValue();
						}
						} 
					}
					setState(336);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,49,_ctx);
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
		enterRule(_localctx, 50, RULE_cardDef);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(339);
			_la = _input.LA(1);
			if ( !(_la==T__36 || _la==T__37) ) {
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3/\u0158\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\3\2\6\28\n\2\r\2\16\29\3\2\3\2\6\2>\n\2\r\2\16\2"+
		"?\3\2\3\2\7\2D\n\2\f\2\16\2G\13\2\3\2\6\2J\n\2\r\2\16\2K\3\2\3\2\7\2P"+
		"\n\2\f\2\16\2S\13\2\5\2U\n\2\3\3\3\3\3\3\3\3\3\3\3\3\3\3\5\3^\n\3\3\4"+
		"\3\4\3\4\3\4\3\4\3\4\7\4f\n\4\f\4\16\4i\13\4\3\4\3\4\3\5\3\5\3\5\5\5p"+
		"\n\5\3\5\5\5s\n\5\3\5\3\5\3\5\7\5x\n\5\f\5\16\5{\13\5\5\5}\n\5\3\5\3\5"+
		"\3\6\6\6\u0082\n\6\r\6\16\6\u0083\3\7\3\7\3\7\3\7\3\7\7\7\u008b\n\7\f"+
		"\7\16\7\u008e\13\7\3\7\3\7\3\b\3\b\3\b\5\b\u0095\n\b\3\t\3\t\5\t\u0099"+
		"\n\t\3\t\3\t\5\t\u009d\n\t\3\t\7\t\u00a0\n\t\f\t\16\t\u00a3\13\t\3\n\3"+
		"\n\5\n\u00a7\n\n\3\n\3\n\3\n\5\n\u00ac\n\n\3\n\3\n\3\n\5\n\u00b1\n\n\3"+
		"\n\3\n\3\13\3\13\5\13\u00b7\n\13\3\13\3\13\3\f\5\f\u00bc\n\f\3\f\3\f\3"+
		"\f\6\f\u00c1\n\f\r\f\16\f\u00c2\3\f\3\f\3\r\3\r\3\r\3\r\3\16\3\16\3\16"+
		"\5\16\u00ce\n\16\3\16\3\16\7\16\u00d2\n\16\f\16\16\16\u00d5\13\16\3\17"+
		"\5\17\u00d8\n\17\3\17\3\17\6\17\u00dc\n\17\r\17\16\17\u00dd\3\17\3\17"+
		"\3\20\3\20\3\20\3\20\3\20\3\20\3\20\5\20\u00e9\n\20\3\20\5\20\u00ec\n"+
		"\20\5\20\u00ee\n\20\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\22\5\22"+
		"\u00f9\n\22\3\22\3\22\3\22\3\22\3\22\3\22\5\22\u0101\n\22\3\22\5\22\u0104"+
		"\n\22\5\22\u0106\n\22\3\23\5\23\u0109\n\23\3\23\3\23\5\23\u010d\n\23\3"+
		"\23\5\23\u0110\n\23\6\23\u0112\n\23\r\23\16\23\u0113\3\24\6\24\u0117\n"+
		"\24\r\24\16\24\u0118\3\25\6\25\u011c\n\25\r\25\16\25\u011d\3\26\3\26\3"+
		"\27\3\27\3\27\7\27\u0125\n\27\f\27\16\27\u0128\13\27\3\27\3\27\7\27\u012c"+
		"\n\27\f\27\16\27\u012f\13\27\3\30\3\30\5\30\u0133\n\30\3\30\5\30\u0136"+
		"\n\30\3\31\3\31\3\31\3\31\7\31\u013c\n\31\f\31\16\31\u013f\13\31\3\32"+
		"\3\32\3\32\3\32\3\32\3\32\3\32\5\32\u0148\n\32\3\32\5\32\u014b\n\32\3"+
		"\32\3\32\7\32\u014f\n\32\f\32\16\32\u0152\13\32\5\32\u0154\n\32\3\33\3"+
		"\33\3\33\2\2\34\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36 \"$&(*,.\60\62"+
		"\64\2\13\3\2\f\r\3\2\16\17\3\2\20\22\3\2\23\25\3\2\30\31\3\2+,\5\2\5\5"+
		"##++\6\2\b\t\30\30\34\35),\3\2\'(\2\u0176\2T\3\2\2\2\4]\3\2\2\2\6_\3\2"+
		"\2\2\bl\3\2\2\2\n\u0081\3\2\2\2\f\u0085\3\2\2\2\16\u0094\3\2\2\2\20\u0096"+
		"\3\2\2\2\22\u00a4\3\2\2\2\24\u00b4\3\2\2\2\26\u00bb\3\2\2\2\30\u00c6\3"+
		"\2\2\2\32\u00ca\3\2\2\2\34\u00d7\3\2\2\2\36\u00ed\3\2\2\2 \u00ef\3\2\2"+
		"\2\"\u0105\3\2\2\2$\u0111\3\2\2\2&\u0116\3\2\2\2(\u011b\3\2\2\2*\u011f"+
		"\3\2\2\2,\u0121\3\2\2\2.\u0132\3\2\2\2\60\u0137\3\2\2\2\62\u0140\3\2\2"+
		"\2\64\u0155\3\2\2\2\668\7\3\2\2\67\66\3\2\2\289\3\2\2\29\67\3\2\2\29:"+
		"\3\2\2\2:;\3\2\2\2;=\7\4\2\2<>\5*\26\2=<\3\2\2\2>?\3\2\2\2?=\3\2\2\2?"+
		"@\3\2\2\2@A\3\2\2\2AE\7\5\2\2BD\5\4\3\2CB\3\2\2\2DG\3\2\2\2EC\3\2\2\2"+
		"EF\3\2\2\2FU\3\2\2\2GE\3\2\2\2HJ\7\3\2\2IH\3\2\2\2JK\3\2\2\2KI\3\2\2\2"+
		"KL\3\2\2\2LM\3\2\2\2MQ\7\6\2\2NP\5,\27\2ON\3\2\2\2PS\3\2\2\2QO\3\2\2\2"+
		"QR\3\2\2\2RU\3\2\2\2SQ\3\2\2\2T\67\3\2\2\2TI\3\2\2\2U\3\3\2\2\2V^\5\b"+
		"\5\2W^\5\32\16\2X^\5\f\7\2Y^\5\34\17\2Z^\5 \21\2[^\5\6\4\2\\^\5\26\f\2"+
		"]V\3\2\2\2]W\3\2\2\2]X\3\2\2\2]Y\3\2\2\2]Z\3\2\2\2][\3\2\2\2]\\\3\2\2"+
		"\2^\5\3\2\2\2_`\7+\2\2`a\7)\2\2ab\7+\2\2bc\7\7\2\2cg\7+\2\2df\5\"\22\2"+
		"ed\3\2\2\2fi\3\2\2\2ge\3\2\2\2gh\3\2\2\2hj\3\2\2\2ig\3\2\2\2jk\7\5\2\2"+
		"k\7\3\2\2\2lm\7\b\2\2mo\7\t\2\2np\7*\2\2on\3\2\2\2op\3\2\2\2pr\3\2\2\2"+
		"qs\5\n\6\2rq\3\2\2\2rs\3\2\2\2s|\3\2\2\2tu\7*\2\2uy\5\n\6\2vx\5\"\22\2"+
		"wv\3\2\2\2x{\3\2\2\2yw\3\2\2\2yz\3\2\2\2z}\3\2\2\2{y\3\2\2\2|t\3\2\2\2"+
		"|}\3\2\2\2}~\3\2\2\2~\177\7\5\2\2\177\t\3\2\2\2\u0080\u0082\7+\2\2\u0081"+
		"\u0080\3\2\2\2\u0082\u0083\3\2\2\2\u0083\u0081\3\2\2\2\u0083\u0084\3\2"+
		"\2\2\u0084\13\3\2\2\2\u0085\u0086\7+\2\2\u0086\u008c\5\16\b\2\u0087\u0088"+
		"\7\n\2\2\u0088\u0089\7\13\2\2\u0089\u008b\5\16\b\2\u008a\u0087\3\2\2\2"+
		"\u008b\u008e\3\2\2\2\u008c\u008a\3\2\2\2\u008c\u008d\3\2\2\2\u008d\u008f"+
		"\3\2\2\2\u008e\u008c\3\2\2\2\u008f\u0090\7\5\2\2\u0090\r\3\2\2\2\u0091"+
		"\u0095\5\20\t\2\u0092\u0095\5\22\n\2\u0093\u0095\5\24\13\2\u0094\u0091"+
		"\3\2\2\2\u0094\u0092\3\2\2\2\u0094\u0093\3\2\2\2\u0095\17\3\2\2\2\u0096"+
		"\u0098\t\2\2\2\u0097\u0099\7*\2\2\u0098\u0097\3\2\2\2\u0098\u0099\3\2"+
		"\2\2\u0099\u009a\3\2\2\2\u009a\u009c\7+\2\2\u009b\u009d\t\3\2\2\u009c"+
		"\u009b\3\2\2\2\u009c\u009d\3\2\2\2\u009d\u00a1\3\2\2\2\u009e\u00a0\5\""+
		"\22\2\u009f\u009e\3\2\2\2\u00a0\u00a3\3\2\2\2\u00a1\u009f\3\2\2\2\u00a1"+
		"\u00a2\3\2\2\2\u00a2\21\3\2\2\2\u00a3\u00a1\3\2\2\2\u00a4\u00a6\t\4\2"+
		"\2\u00a5\u00a7\7*\2\2\u00a6\u00a5\3\2\2\2\u00a6\u00a7\3\2\2\2\u00a7\u00a8"+
		"\3\2\2\2\u00a8\u00a9\5&\24\2\u00a9\u00ab\t\5\2\2\u00aa\u00ac\7*\2\2\u00ab"+
		"\u00aa\3\2\2\2\u00ab\u00ac\3\2\2\2\u00ac\u00ad\3\2\2\2\u00ad\u00ae\7+"+
		"\2\2\u00ae\u00b0\7\26\2\2\u00af\u00b1\7*\2\2\u00b0\u00af\3\2\2\2\u00b0"+
		"\u00b1\3\2\2\2\u00b1\u00b2\3\2\2\2\u00b2\u00b3\7+\2\2\u00b3\23\3\2\2\2"+
		"\u00b4\u00b6\7\27\2\2\u00b5\u00b7\t\6\2\2\u00b6\u00b5\3\2\2\2\u00b6\u00b7"+
		"\3\2\2\2\u00b7\u00b8\3\2\2\2\u00b8\u00b9\5&\24\2\u00b9\25\3\2\2\2\u00ba"+
		"\u00bc\7*\2\2\u00bb\u00ba\3\2\2\2\u00bb\u00bc\3\2\2\2\u00bc\u00bd\3\2"+
		"\2\2\u00bd\u00be\7+\2\2\u00be\u00c0\7\32\2\2\u00bf\u00c1\5\30\r\2\u00c0"+
		"\u00bf\3\2\2\2\u00c1\u00c2\3\2\2\2\u00c2\u00c0\3\2\2\2\u00c2\u00c3\3\2"+
		"\2\2\u00c3\u00c4\3\2\2\2\u00c4\u00c5\7\5\2\2\u00c5\27\3\2\2\2\u00c6\u00c7"+
		"\7\33\2\2\u00c7\u00c8\5\n\6\2\u00c8\u00c9\5\36\20\2\u00c9\31\3\2\2\2\u00ca"+
		"\u00cb\5\n\6\2\u00cb\u00cd\7\t\2\2\u00cc\u00ce\7*\2\2\u00cd\u00cc\3\2"+
		"\2\2\u00cd\u00ce\3\2\2\2\u00ce\u00cf\3\2\2\2\u00cf\u00d3\5\n\6\2\u00d0"+
		"\u00d2\5\"\22\2\u00d1\u00d0\3\2\2\2\u00d2\u00d5\3\2\2\2\u00d3\u00d1\3"+
		"\2\2\2\u00d3\u00d4\3\2\2\2\u00d4\33\3\2\2\2\u00d5\u00d3\3\2\2\2\u00d6"+
		"\u00d8\7*\2\2\u00d7\u00d6\3\2\2\2\u00d7\u00d8\3\2\2\2\u00d8\u00d9\3\2"+
		"\2\2\u00d9\u00db\5\n\6\2\u00da\u00dc\5\36\20\2\u00db\u00da\3\2\2\2\u00dc"+
		"\u00dd\3\2\2\2\u00dd\u00db\3\2\2\2\u00dd\u00de\3\2\2\2\u00de\u00df\3\2"+
		"\2\2\u00df\u00e0\7\5\2\2\u00e0\35\3\2\2\2\u00e1\u00e2\7\34\2\2\u00e2\u00e3"+
		"\7+\2\2\u00e3\u00ee\5$\23\2\u00e4\u00e5\7\34\2\2\u00e5\u00e6\7,\2\2\u00e6"+
		"\u00e8\5\n\6\2\u00e7\u00e9\7\35\2\2\u00e8\u00e7\3\2\2\2\u00e8\u00e9\3"+
		"\2\2\2\u00e9\u00eb\3\2\2\2\u00ea\u00ec\7\n\2\2\u00eb\u00ea\3\2\2\2\u00eb"+
		"\u00ec\3\2\2\2\u00ec\u00ee\3\2\2\2\u00ed\u00e1\3\2\2\2\u00ed\u00e4\3\2"+
		"\2\2\u00ee\37\3\2\2\2\u00ef\u00f0\7\36\2\2\u00f0\u00f1\7\37\2\2\u00f1"+
		"\u00f2\7+\2\2\u00f2\u00f3\7 \2\2\u00f3\u00f4\7!\2\2\u00f4\u00f5\5(\25"+
		"\2\u00f5\u00f6\7\"\2\2\u00f6!\3\2\2\2\u00f7\u00f9\7\30\2\2\u00f8\u00f7"+
		"\3\2\2\2\u00f8\u00f9\3\2\2\2\u00f9\u00fa\3\2\2\2\u00fa\u00fb\7+\2\2\u00fb"+
		"\u0106\5$\23\2\u00fc\u00fd\7\30\2\2\u00fd\u00fe\7,\2\2\u00fe\u0100\5\n"+
		"\6\2\u00ff\u0101\7\35\2\2\u0100\u00ff\3\2\2\2\u0100\u0101\3\2\2\2\u0101"+
		"\u0103\3\2\2\2\u0102\u0104\7\n\2\2\u0103\u0102\3\2\2\2\u0103\u0104\3\2"+
		"\2\2\u0104\u0106\3\2\2\2\u0105\u00f8\3\2\2\2\u0105\u00fc\3\2\2\2\u0106"+
		"#\3\2\2\2\u0107\u0109\7*\2\2\u0108\u0107\3\2\2\2\u0108\u0109\3\2\2\2\u0109"+
		"\u010a\3\2\2\2\u010a\u010c\5&\24\2\u010b\u010d\7\35\2\2\u010c\u010b\3"+
		"\2\2\2\u010c\u010d\3\2\2\2\u010d\u010f\3\2\2\2\u010e\u0110\7\n\2\2\u010f"+
		"\u010e\3\2\2\2\u010f\u0110\3\2\2\2\u0110\u0112\3\2\2\2\u0111\u0108\3\2"+
		"\2\2\u0112\u0113\3\2\2\2\u0113\u0111\3\2\2\2\u0113\u0114\3\2\2\2\u0114"+
		"%\3\2\2\2\u0115\u0117\t\7\2\2\u0116\u0115\3\2\2\2\u0117\u0118\3\2\2\2"+
		"\u0118\u0116\3\2\2\2\u0118\u0119\3\2\2\2\u0119\'\3\2\2\2\u011a\u011c\t"+
		"\b\2\2\u011b\u011a\3\2\2\2\u011c\u011d\3\2\2\2\u011d\u011b\3\2\2\2\u011d"+
		"\u011e\3\2\2\2\u011e)\3\2\2\2\u011f\u0120\t\t\2\2\u0120+\3\2\2\2\u0121"+
		"\u0122\7+\2\2\u0122\u0126\7$\2\2\u0123\u0125\5.\30\2\u0124\u0123\3\2\2"+
		"\2\u0125\u0128\3\2\2\2\u0126\u0124\3\2\2\2\u0126\u0127\3\2\2\2\u0127\u012d"+
		"\3\2\2\2\u0128\u0126\3\2\2\2\u0129\u012c\5\60\31\2\u012a\u012c\5\62\32"+
		"\2\u012b\u0129\3\2\2\2\u012b\u012a\3\2\2\2\u012c\u012f\3\2\2\2\u012d\u012b"+
		"\3\2\2\2\u012d\u012e\3\2\2\2\u012e-\3\2\2\2\u012f\u012d\3\2\2\2\u0130"+
		"\u0133\7+\2\2\u0131\u0133\7,\2\2\u0132\u0130\3\2\2\2\u0132\u0131\3\2\2"+
		"\2\u0133\u0135\3\2\2\2\u0134\u0136\7\35\2\2\u0135\u0134\3\2\2\2\u0135"+
		"\u0136\3\2\2\2\u0136/\3\2\2\2\u0137\u0138\7%\2\2\u0138\u0139\7+\2\2\u0139"+
		"\u013d\7$\2\2\u013a\u013c\5.\30\2\u013b\u013a\3\2\2\2\u013c\u013f\3\2"+
		"\2\2\u013d\u013b\3\2\2\2\u013d\u013e\3\2\2\2\u013e\61\3\2\2\2\u013f\u013d"+
		"\3\2\2\2\u0140\u0141\7%\2\2\u0141\u0142\7+\2\2\u0142\u0143\5\64\33\2\u0143"+
		"\u014a\7+\2\2\u0144\u0147\7&\2\2\u0145\u0146\7+\2\2\u0146\u0148\7\5\2"+
		"\2\u0147\u0145\3\2\2\2\u0147\u0148\3\2\2\2\u0148\u0149\3\2\2\2\u0149\u014b"+
		"\7+\2\2\u014a\u0144\3\2\2\2\u014a\u014b\3\2\2\2\u014b\u0153\3\2\2\2\u014c"+
		"\u0150\7$\2\2\u014d\u014f\5.\30\2\u014e\u014d\3\2\2\2\u014f\u0152\3\2"+
		"\2\2\u0150\u014e\3\2\2\2\u0150\u0151\3\2\2\2\u0151\u0154\3\2\2\2\u0152"+
		"\u0150\3\2\2\2\u0153\u014c\3\2\2\2\u0153\u0154\3\2\2\2\u0154\63\3\2\2"+
		"\2\u0155\u0156\t\n\2\2\u0156\65\3\2\2\2\659?EKQT]gory|\u0083\u008c\u0094"+
		"\u0098\u009c\u00a1\u00a6\u00ab\u00b0\u00b6\u00bb\u00c2\u00cd\u00d3\u00d7"+
		"\u00dd\u00e8\u00eb\u00ed\u00f8\u0100\u0103\u0105\u0108\u010c\u010f\u0113"+
		"\u0118\u011d\u0126\u012b\u012d\u0132\u0135\u013d\u0147\u014a\u0150\u0153";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}