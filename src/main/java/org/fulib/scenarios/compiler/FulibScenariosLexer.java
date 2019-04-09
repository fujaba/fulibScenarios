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
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, T__19=20, T__20=21, T__21=22, T__22=23, T__23=24, 
		T__24=25, T__25=26, T__26=27, T__27=28, T__28=29, T__29=30, T__30=31, 
		T__31=32, T__32=33, T__33=34, T__34=35, T__35=36, T__36=37, T__37=38, 
		T__38=39, CALL=40, A=41, NAME=42, NUMBER=43, WS=44, COMMENT=45, LINE_COMMENT=46;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "T__7", "T__8", 
			"T__9", "T__10", "T__11", "T__12", "T__13", "T__14", "T__15", "T__16", 
			"T__17", "T__18", "T__19", "T__20", "T__21", "T__22", "T__23", "T__24", 
			"T__25", "T__26", "T__27", "T__28", "T__29", "T__30", "T__31", "T__32", 
			"T__33", "T__34", "T__35", "T__36", "T__37", "T__38", "CALL", "A", "NAME", 
			"NUMBER", "WS", "COMMENT", "LINE_COMMENT"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'#'", "'Scenario'", "'.'", "'Register'", "'on'", "'There'", "'is'", 
			"'and'", "'it'", "'create'", "'creates'", "'cards'", "'card'", "'adds'", 
			"'puts'", "'reads'", "'writes'", "'from'", "'of'", "'to'", "'into'", 
			"'answers'", "'with'", "':'", "'expect'", "'that'", "'has'", "','", "'!'", 
			"'['", "']'", "'('", "')'", "'/'", "'e.g.'", "'+'", "'cf.'", "'one'", 
			"'many'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, "CALL", "A", "NAME", "NUMBER", "WS", "COMMENT", 
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\60\u015f\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31"+
		"\t\31\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t"+
		" \4!\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t"+
		"+\4,\t,\4-\t-\4.\t.\4/\t/\3\2\3\2\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3"+
		"\3\4\3\4\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\6\3\6\3\6\3\7\3\7\3\7\3"+
		"\7\3\7\3\7\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3\n\3\n\3\n\3\13\3\13\3\13\3\13"+
		"\3\13\3\13\3\13\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\r\3\r\3\r\3\r\3\r\3"+
		"\r\3\16\3\16\3\16\3\16\3\16\3\17\3\17\3\17\3\17\3\17\3\20\3\20\3\20\3"+
		"\20\3\20\3\21\3\21\3\21\3\21\3\21\3\21\3\22\3\22\3\22\3\22\3\22\3\22\3"+
		"\22\3\23\3\23\3\23\3\23\3\23\3\24\3\24\3\24\3\25\3\25\3\25\3\26\3\26\3"+
		"\26\3\26\3\26\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\30\3\30\3\30\3"+
		"\30\3\30\3\31\3\31\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\33\3\33\3\33\3"+
		"\33\3\33\3\34\3\34\3\34\3\34\3\35\3\35\3\36\3\36\3\37\3\37\3 \3 \3!\3"+
		"!\3\"\3\"\3#\3#\3$\3$\3$\3$\3$\3%\3%\3&\3&\3&\3&\3\'\3\'\3\'\3\'\3(\3"+
		"(\3(\3(\3(\3)\3)\3)\3)\3)\3)\3)\3)\3)\5)\u0114\n)\3*\3*\3*\3*\3*\3*\3"+
		"*\3*\3*\3*\3*\5*\u0121\n*\3+\7+\u0124\n+\f+\16+\u0127\13+\3+\3+\7+\u012b"+
		"\n+\f+\16+\u012e\13+\3,\5,\u0131\n,\3,\6,\u0134\n,\r,\16,\u0135\3,\3,"+
		"\6,\u013a\n,\r,\16,\u013b\5,\u013e\n,\3-\6-\u0141\n-\r-\16-\u0142\3-\3"+
		"-\3.\3.\3.\3.\7.\u014b\n.\f.\16.\u014e\13.\3.\3.\3.\3.\3.\3/\3/\3/\3/"+
		"\7/\u0159\n/\f/\16/\u015c\13/\3/\3/\3\u014c\2\60\3\3\5\4\7\5\t\6\13\7"+
		"\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35\20\37\21!\22#\23%\24\'\25"+
		")\26+\27-\30/\31\61\32\63\33\65\34\67\359\36;\37= ?!A\"C#E$G%I&K\'M(O"+
		")Q*S+U,W-Y.[/]\60\3\2\b\4\2CCcc\3\2\62;\6\2//C\\aac|\7\2//\62;C\\aac|"+
		"\5\2\13\f\16\17\"\"\4\2\f\f\17\17\2\u016c\2\3\3\2\2\2\2\5\3\2\2\2\2\7"+
		"\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2"+
		"\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2"+
		"\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2"+
		"\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2\2"+
		"\2\65\3\2\2\2\2\67\3\2\2\2\29\3\2\2\2\2;\3\2\2\2\2=\3\2\2\2\2?\3\2\2\2"+
		"\2A\3\2\2\2\2C\3\2\2\2\2E\3\2\2\2\2G\3\2\2\2\2I\3\2\2\2\2K\3\2\2\2\2M"+
		"\3\2\2\2\2O\3\2\2\2\2Q\3\2\2\2\2S\3\2\2\2\2U\3\2\2\2\2W\3\2\2\2\2Y\3\2"+
		"\2\2\2[\3\2\2\2\2]\3\2\2\2\3_\3\2\2\2\5a\3\2\2\2\7j\3\2\2\2\tl\3\2\2\2"+
		"\13u\3\2\2\2\rx\3\2\2\2\17~\3\2\2\2\21\u0081\3\2\2\2\23\u0085\3\2\2\2"+
		"\25\u0088\3\2\2\2\27\u008f\3\2\2\2\31\u0097\3\2\2\2\33\u009d\3\2\2\2\35"+
		"\u00a2\3\2\2\2\37\u00a7\3\2\2\2!\u00ac\3\2\2\2#\u00b2\3\2\2\2%\u00b9\3"+
		"\2\2\2\'\u00be\3\2\2\2)\u00c1\3\2\2\2+\u00c4\3\2\2\2-\u00c9\3\2\2\2/\u00d1"+
		"\3\2\2\2\61\u00d6\3\2\2\2\63\u00d8\3\2\2\2\65\u00df\3\2\2\2\67\u00e4\3"+
		"\2\2\29\u00e8\3\2\2\2;\u00ea\3\2\2\2=\u00ec\3\2\2\2?\u00ee\3\2\2\2A\u00f0"+
		"\3\2\2\2C\u00f2\3\2\2\2E\u00f4\3\2\2\2G\u00f6\3\2\2\2I\u00fb\3\2\2\2K"+
		"\u00fd\3\2\2\2M\u0101\3\2\2\2O\u0105\3\2\2\2Q\u0113\3\2\2\2S\u0120\3\2"+
		"\2\2U\u0125\3\2\2\2W\u0130\3\2\2\2Y\u0140\3\2\2\2[\u0146\3\2\2\2]\u0154"+
		"\3\2\2\2_`\7%\2\2`\4\3\2\2\2ab\7U\2\2bc\7e\2\2cd\7g\2\2de\7p\2\2ef\7c"+
		"\2\2fg\7t\2\2gh\7k\2\2hi\7q\2\2i\6\3\2\2\2jk\7\60\2\2k\b\3\2\2\2lm\7T"+
		"\2\2mn\7g\2\2no\7i\2\2op\7k\2\2pq\7u\2\2qr\7v\2\2rs\7g\2\2st\7t\2\2t\n"+
		"\3\2\2\2uv\7q\2\2vw\7p\2\2w\f\3\2\2\2xy\7V\2\2yz\7j\2\2z{\7g\2\2{|\7t"+
		"\2\2|}\7g\2\2}\16\3\2\2\2~\177\7k\2\2\177\u0080\7u\2\2\u0080\20\3\2\2"+
		"\2\u0081\u0082\7c\2\2\u0082\u0083\7p\2\2\u0083\u0084\7f\2\2\u0084\22\3"+
		"\2\2\2\u0085\u0086\7k\2\2\u0086\u0087\7v\2\2\u0087\24\3\2\2\2\u0088\u0089"+
		"\7e\2\2\u0089\u008a\7t\2\2\u008a\u008b\7g\2\2\u008b\u008c\7c\2\2\u008c"+
		"\u008d\7v\2\2\u008d\u008e\7g\2\2\u008e\26\3\2\2\2\u008f\u0090\7e\2\2\u0090"+
		"\u0091\7t\2\2\u0091\u0092\7g\2\2\u0092\u0093\7c\2\2\u0093\u0094\7v\2\2"+
		"\u0094\u0095\7g\2\2\u0095\u0096\7u\2\2\u0096\30\3\2\2\2\u0097\u0098\7"+
		"e\2\2\u0098\u0099\7c\2\2\u0099\u009a\7t\2\2\u009a\u009b\7f\2\2\u009b\u009c"+
		"\7u\2\2\u009c\32\3\2\2\2\u009d\u009e\7e\2\2\u009e\u009f\7c\2\2\u009f\u00a0"+
		"\7t\2\2\u00a0\u00a1\7f\2\2\u00a1\34\3\2\2\2\u00a2\u00a3\7c\2\2\u00a3\u00a4"+
		"\7f\2\2\u00a4\u00a5\7f\2\2\u00a5\u00a6\7u\2\2\u00a6\36\3\2\2\2\u00a7\u00a8"+
		"\7r\2\2\u00a8\u00a9\7w\2\2\u00a9\u00aa\7v\2\2\u00aa\u00ab\7u\2\2\u00ab"+
		" \3\2\2\2\u00ac\u00ad\7t\2\2\u00ad\u00ae\7g\2\2\u00ae\u00af\7c\2\2\u00af"+
		"\u00b0\7f\2\2\u00b0\u00b1\7u\2\2\u00b1\"\3\2\2\2\u00b2\u00b3\7y\2\2\u00b3"+
		"\u00b4\7t\2\2\u00b4\u00b5\7k\2\2\u00b5\u00b6\7v\2\2\u00b6\u00b7\7g\2\2"+
		"\u00b7\u00b8\7u\2\2\u00b8$\3\2\2\2\u00b9\u00ba\7h\2\2\u00ba\u00bb\7t\2"+
		"\2\u00bb\u00bc\7q\2\2\u00bc\u00bd\7o\2\2\u00bd&\3\2\2\2\u00be\u00bf\7"+
		"q\2\2\u00bf\u00c0\7h\2\2\u00c0(\3\2\2\2\u00c1\u00c2\7v\2\2\u00c2\u00c3"+
		"\7q\2\2\u00c3*\3\2\2\2\u00c4\u00c5\7k\2\2\u00c5\u00c6\7p\2\2\u00c6\u00c7"+
		"\7v\2\2\u00c7\u00c8\7q\2\2\u00c8,\3\2\2\2\u00c9\u00ca\7c\2\2\u00ca\u00cb"+
		"\7p\2\2\u00cb\u00cc\7u\2\2\u00cc\u00cd\7y\2\2\u00cd\u00ce\7g\2\2\u00ce"+
		"\u00cf\7t\2\2\u00cf\u00d0\7u\2\2\u00d0.\3\2\2\2\u00d1\u00d2\7y\2\2\u00d2"+
		"\u00d3\7k\2\2\u00d3\u00d4\7v\2\2\u00d4\u00d5\7j\2\2\u00d5\60\3\2\2\2\u00d6"+
		"\u00d7\7<\2\2\u00d7\62\3\2\2\2\u00d8\u00d9\7g\2\2\u00d9\u00da\7z\2\2\u00da"+
		"\u00db\7r\2\2\u00db\u00dc\7g\2\2\u00dc\u00dd\7e\2\2\u00dd\u00de\7v\2\2"+
		"\u00de\64\3\2\2\2\u00df\u00e0\7v\2\2\u00e0\u00e1\7j\2\2\u00e1\u00e2\7"+
		"c\2\2\u00e2\u00e3\7v\2\2\u00e3\66\3\2\2\2\u00e4\u00e5\7j\2\2\u00e5\u00e6"+
		"\7c\2\2\u00e6\u00e7\7u\2\2\u00e78\3\2\2\2\u00e8\u00e9\7.\2\2\u00e9:\3"+
		"\2\2\2\u00ea\u00eb\7#\2\2\u00eb<\3\2\2\2\u00ec\u00ed\7]\2\2\u00ed>\3\2"+
		"\2\2\u00ee\u00ef\7_\2\2\u00ef@\3\2\2\2\u00f0\u00f1\7*\2\2\u00f1B\3\2\2"+
		"\2\u00f2\u00f3\7+\2\2\u00f3D\3\2\2\2\u00f4\u00f5\7\61\2\2\u00f5F\3\2\2"+
		"\2\u00f6\u00f7\7g\2\2\u00f7\u00f8\7\60\2\2\u00f8\u00f9\7i\2\2\u00f9\u00fa"+
		"\7\60\2\2\u00faH\3\2\2\2\u00fb\u00fc\7-\2\2\u00fcJ\3\2\2\2\u00fd\u00fe"+
		"\7e\2\2\u00fe\u00ff\7h\2\2\u00ff\u0100\7\60\2\2\u0100L\3\2\2\2\u0101\u0102"+
		"\7q\2\2\u0102\u0103\7p\2\2\u0103\u0104\7g\2\2\u0104N\3\2\2\2\u0105\u0106"+
		"\7o\2\2\u0106\u0107\7c\2\2\u0107\u0108\7p\2\2\u0108\u0109\7{\2\2\u0109"+
		"P\3\2\2\2\u010a\u010b\7e\2\2\u010b\u010c\7c\2\2\u010c\u010d\7n\2\2\u010d"+
		"\u0114\7n\2\2\u010e\u010f\7e\2\2\u010f\u0110\7c\2\2\u0110\u0111\7n\2\2"+
		"\u0111\u0112\7n\2\2\u0112\u0114\7u\2\2\u0113\u010a\3\2\2\2\u0113\u010e"+
		"\3\2\2\2\u0114R\3\2\2\2\u0115\u0121\t\2\2\2\u0116\u0117\7c\2\2\u0117\u0121"+
		"\7p\2\2\u0118\u0119\7C\2\2\u0119\u0121\7p\2\2\u011a\u011b\7v\2\2\u011b"+
		"\u011c\7j\2\2\u011c\u0121\7g\2\2\u011d\u011e\7V\2\2\u011e\u011f\7j\2\2"+
		"\u011f\u0121\7g\2\2\u0120\u0115\3\2\2\2\u0120\u0116\3\2\2\2\u0120\u0118"+
		"\3\2\2\2\u0120\u011a\3\2\2\2\u0120\u011d\3\2\2\2\u0121T\3\2\2\2\u0122"+
		"\u0124\t\3\2\2\u0123\u0122\3\2\2\2\u0124\u0127\3\2\2\2\u0125\u0123\3\2"+
		"\2\2\u0125\u0126\3\2\2\2\u0126\u0128\3\2\2\2\u0127\u0125\3\2\2\2\u0128"+
		"\u012c\t\4\2\2\u0129\u012b\t\5\2\2\u012a\u0129\3\2\2\2\u012b\u012e\3\2"+
		"\2\2\u012c\u012a\3\2\2\2\u012c\u012d\3\2\2\2\u012dV\3\2\2\2\u012e\u012c"+
		"\3\2\2\2\u012f\u0131\7/\2\2\u0130\u012f\3\2\2\2\u0130\u0131\3\2\2\2\u0131"+
		"\u0133\3\2\2\2\u0132\u0134\t\3\2\2\u0133\u0132\3\2\2\2\u0134\u0135\3\2"+
		"\2\2\u0135\u0133\3\2\2\2\u0135\u0136\3\2\2\2\u0136\u013d\3\2\2\2\u0137"+
		"\u0139\7\60\2\2\u0138\u013a\t\3\2\2\u0139\u0138\3\2\2\2\u013a\u013b\3"+
		"\2\2\2\u013b\u0139\3\2\2\2\u013b\u013c\3\2\2\2\u013c\u013e\3\2\2\2\u013d"+
		"\u0137\3\2\2\2\u013d\u013e\3\2\2\2\u013eX\3\2\2\2\u013f\u0141\t\6\2\2"+
		"\u0140\u013f\3\2\2\2\u0141\u0142\3\2\2\2\u0142\u0140\3\2\2\2\u0142\u0143"+
		"\3\2\2\2\u0143\u0144\3\2\2\2\u0144\u0145\b-\2\2\u0145Z\3\2\2\2\u0146\u0147"+
		"\7\61\2\2\u0147\u0148\7,\2\2\u0148\u014c\3\2\2\2\u0149\u014b\13\2\2\2"+
		"\u014a\u0149\3\2\2\2\u014b\u014e\3\2\2\2\u014c\u014d\3\2\2\2\u014c\u014a"+
		"\3\2\2\2\u014d\u014f\3\2\2\2\u014e\u014c\3\2\2\2\u014f\u0150\7,\2\2\u0150"+
		"\u0151\7\61\2\2\u0151\u0152\3\2\2\2\u0152\u0153\b.\2\2\u0153\\\3\2\2\2"+
		"\u0154\u0155\7\61\2\2\u0155\u0156\7\61\2\2\u0156\u015a\3\2\2\2\u0157\u0159"+
		"\n\7\2\2\u0158\u0157\3\2\2\2\u0159\u015c\3\2\2\2\u015a\u0158\3\2\2\2\u015a"+
		"\u015b\3\2\2\2\u015b\u015d\3\2\2\2\u015c\u015a\3\2\2\2\u015d\u015e\b/"+
		"\2\2\u015e^\3\2\2\2\16\2\u0113\u0120\u0125\u012c\u0130\u0135\u013b\u013d"+
		"\u0142\u014c\u015a\3\2\3\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}