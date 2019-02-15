// Generated from C:/Users/zuend/IdeaProjects/fulibScenarios/src/main/java/org/fulib/scenarios\FulibScenarios.g4 by ANTLR 4.7.2
package org.fulib.scenarios.compiler;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class FulibScenariosLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.7.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, A=12, NAME=13, NUMBER=14, WS=15, COMMENT=16, LINE_COMMENT=17;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "T__7", "T__8", 
			"T__9", "T__10", "A", "NAME", "NUMBER", "WS", "COMMENT", "LINE_COMMENT"
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


	public FulibScenariosLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "FulibScenarios.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\23\u008c\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\3\2\3\2\3\3\3\3\3\3\3\4\3\4\3\4\3\4\3\4\3\4\3\5\3\5\3\5\3\5\3\5"+
		"\3\5\3\6\3\6\3\6\3\7\3\7\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3\n\3\n\3\n\3\n\3"+
		"\n\3\13\3\13\3\f\3\f\3\f\3\f\3\r\3\r\3\r\3\r\3\r\3\r\5\rT\n\r\3\16\3\16"+
		"\7\16X\n\16\f\16\16\16[\13\16\3\17\5\17^\n\17\3\17\6\17a\n\17\r\17\16"+
		"\17b\3\17\3\17\6\17g\n\17\r\17\16\17h\5\17k\n\17\3\20\6\20n\n\20\r\20"+
		"\16\20o\3\20\3\20\3\21\3\21\3\21\3\21\7\21x\n\21\f\21\16\21{\13\21\3\21"+
		"\3\21\3\21\3\21\3\21\3\22\3\22\3\22\3\22\7\22\u0086\n\22\f\22\16\22\u0089"+
		"\13\22\3\22\3\22\3y\2\23\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f"+
		"\27\r\31\16\33\17\35\20\37\21!\22#\23\3\2\7\4\2C\\c|\5\2\62;C\\c|\3\2"+
		"\62;\5\2\13\f\16\17\"\"\4\2\f\f\17\17\2\u0095\2\3\3\2\2\2\2\5\3\2\2\2"+
		"\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3"+
		"\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2"+
		"\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\3%\3\2\2\2\5\'\3\2"+
		"\2\2\7*\3\2\2\2\t\60\3\2\2\2\13\66\3\2\2\2\r9\3\2\2\2\17;\3\2\2\2\21>"+
		"\3\2\2\2\23B\3\2\2\2\25G\3\2\2\2\27I\3\2\2\2\31S\3\2\2\2\33U\3\2\2\2\35"+
		"]\3\2\2\2\37m\3\2\2\2!s\3\2\2\2#\u0081\3\2\2\2%&\7%\2\2&\4\3\2\2\2\'("+
		"\7%\2\2()\7%\2\2)\6\3\2\2\2*+\7U\2\2+,\7g\2\2,-\7v\2\2-.\7w\2\2./\7r\2"+
		"\2/\b\3\2\2\2\60\61\7V\2\2\61\62\7j\2\2\62\63\7g\2\2\63\64\7t\2\2\64\65"+
		"\7g\2\2\65\n\3\2\2\2\66\67\7k\2\2\678\7u\2\28\f\3\2\2\29:\7\60\2\2:\16"+
		"\3\2\2\2;<\7k\2\2<=\7p\2\2=\20\3\2\2\2>?\7j\2\2?@\7c\2\2@A\7u\2\2A\22"+
		"\3\2\2\2BC\7y\2\2CD\7k\2\2DE\7v\2\2EF\7j\2\2F\24\3\2\2\2GH\7.\2\2H\26"+
		"\3\2\2\2IJ\7c\2\2JK\7p\2\2KL\7f\2\2L\30\3\2\2\2MT\7c\2\2NO\7c\2\2OT\7"+
		"p\2\2PQ\7v\2\2QR\7j\2\2RT\7g\2\2SM\3\2\2\2SN\3\2\2\2SP\3\2\2\2T\32\3\2"+
		"\2\2UY\t\2\2\2VX\t\3\2\2WV\3\2\2\2X[\3\2\2\2YW\3\2\2\2YZ\3\2\2\2Z\34\3"+
		"\2\2\2[Y\3\2\2\2\\^\7/\2\2]\\\3\2\2\2]^\3\2\2\2^`\3\2\2\2_a\t\4\2\2`_"+
		"\3\2\2\2ab\3\2\2\2b`\3\2\2\2bc\3\2\2\2cj\3\2\2\2df\7\60\2\2eg\t\4\2\2"+
		"fe\3\2\2\2gh\3\2\2\2hf\3\2\2\2hi\3\2\2\2ik\3\2\2\2jd\3\2\2\2jk\3\2\2\2"+
		"k\36\3\2\2\2ln\t\5\2\2ml\3\2\2\2no\3\2\2\2om\3\2\2\2op\3\2\2\2pq\3\2\2"+
		"\2qr\b\20\2\2r \3\2\2\2st\7\61\2\2tu\7,\2\2uy\3\2\2\2vx\13\2\2\2wv\3\2"+
		"\2\2x{\3\2\2\2yz\3\2\2\2yw\3\2\2\2z|\3\2\2\2{y\3\2\2\2|}\7,\2\2}~\7\61"+
		"\2\2~\177\3\2\2\2\177\u0080\b\21\2\2\u0080\"\3\2\2\2\u0081\u0082\7\61"+
		"\2\2\u0082\u0083\7\61\2\2\u0083\u0087\3\2\2\2\u0084\u0086\n\6\2\2\u0085"+
		"\u0084\3\2\2\2\u0086\u0089\3\2\2\2\u0087\u0085\3\2\2\2\u0087\u0088\3\2"+
		"\2\2\u0088\u008a\3\2\2\2\u0089\u0087\3\2\2\2\u008a\u008b\b\22\2\2\u008b"+
		"$\3\2\2\2\f\2SY]bhjoy\u0087\3\2\3\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}