package com.cpunisher.hayasai.frontend.antlr;// Generated from MiniSysY.g4 by ANTLR 4.9.2
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class MiniSysYLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.9.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, PLUS=17, 
		MINUS=18, NOT=19, MUL=20, DIV=21, MOD=22, LESS=23, GREATER=24, LEQUAL=25, 
		GEQUAL=26, EQUAL=27, NOT_EQUAL=28, LOGIC_AND=29, LOGIC_OR=30, L_BRACKET=31, 
		R_BRACKET=32, IDENT=33, DEC_CONST=34, OCT_CONST=35, HEX_CONST=36, COMMENT=37, 
		WHITE_SPACE=38;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "T__7", "T__8", 
			"T__9", "T__10", "T__11", "T__12", "T__13", "T__14", "T__15", "PLUS", 
			"MINUS", "NOT", "MUL", "DIV", "MOD", "LESS", "GREATER", "LEQUAL", "GEQUAL", 
			"EQUAL", "NOT_EQUAL", "LOGIC_AND", "LOGIC_OR", "L_BRACKET", "R_BRACKET", 
			"IDENT", "DEC_CONST", "OCT_CONST", "HEX_CONST", "COMMENT", "WHITE_SPACE"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'int'", "'const'", "','", "';'", "'='", "'{'", "'}'", "'('", "')'", 
			"'void'", "'if'", "'else'", "'while'", "'break'", "'continue'", "'return'", 
			"'+'", "'-'", "'!'", "'*'", "'/'", "'%'", "'<'", "'>'", "'<='", "'>='", 
			"'=='", "'!='", "'&&'", "'||'", "'['", "']'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, "PLUS", "MINUS", "NOT", "MUL", "DIV", "MOD", 
			"LESS", "GREATER", "LEQUAL", "GEQUAL", "EQUAL", "NOT_EQUAL", "LOGIC_AND", 
			"LOGIC_OR", "L_BRACKET", "R_BRACKET", "IDENT", "DEC_CONST", "OCT_CONST", 
			"HEX_CONST", "COMMENT", "WHITE_SPACE"
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


	public MiniSysYLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "MiniSysY.g4"; }

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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2(\u00f5\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\3\2\3\2\3\2\3\2\3\3\3\3\3"+
		"\3\3\3\3\3\3\3\3\4\3\4\3\5\3\5\3\6\3\6\3\7\3\7\3\b\3\b\3\t\3\t\3\n\3\n"+
		"\3\13\3\13\3\13\3\13\3\13\3\f\3\f\3\f\3\r\3\r\3\r\3\r\3\r\3\16\3\16\3"+
		"\16\3\16\3\16\3\16\3\17\3\17\3\17\3\17\3\17\3\17\3\20\3\20\3\20\3\20\3"+
		"\20\3\20\3\20\3\20\3\20\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\22\3\22\3"+
		"\23\3\23\3\24\3\24\3\25\3\25\3\26\3\26\3\27\3\27\3\30\3\30\3\31\3\31\3"+
		"\32\3\32\3\32\3\33\3\33\3\33\3\34\3\34\3\34\3\35\3\35\3\35\3\36\3\36\3"+
		"\36\3\37\3\37\3\37\3 \3 \3!\3!\3\"\3\"\3\"\6\"\u00ba\n\"\r\"\16\"\u00bb"+
		"\5\"\u00be\n\"\3#\3#\7#\u00c2\n#\f#\16#\u00c5\13#\3$\3$\7$\u00c9\n$\f"+
		"$\16$\u00cc\13$\3%\3%\3%\3%\5%\u00d2\n%\3%\6%\u00d5\n%\r%\16%\u00d6\3"+
		"&\3&\3&\3&\7&\u00dd\n&\f&\16&\u00e0\13&\3&\3&\3&\3&\3&\3&\7&\u00e8\n&"+
		"\f&\16&\u00eb\13&\3&\5&\u00ee\n&\3&\3&\3\'\3\'\3\'\3\'\4\u00de\u00e9\2"+
		"(\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35\20"+
		"\37\21!\22#\23%\24\'\25)\26+\27-\30/\31\61\32\63\33\65\34\67\359\36;\37"+
		"= ?!A\"C#E$G%I&K\'M(\3\2\n\5\2C\\aac|\6\2\62;C\\aac|\3\2\63;\3\2\62;\3"+
		"\2\629\5\2\62;CHch\4\2\f\f\17\17\5\2\13\f\17\17\"\"\2\u00fd\2\3\3\2\2"+
		"\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3"+
		"\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2"+
		"\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2"+
		"\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2"+
		"\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2\67\3\2\2\2\29\3\2\2\2\2;\3\2\2\2\2=\3"+
		"\2\2\2\2?\3\2\2\2\2A\3\2\2\2\2C\3\2\2\2\2E\3\2\2\2\2G\3\2\2\2\2I\3\2\2"+
		"\2\2K\3\2\2\2\2M\3\2\2\2\3O\3\2\2\2\5S\3\2\2\2\7Y\3\2\2\2\t[\3\2\2\2\13"+
		"]\3\2\2\2\r_\3\2\2\2\17a\3\2\2\2\21c\3\2\2\2\23e\3\2\2\2\25g\3\2\2\2\27"+
		"l\3\2\2\2\31o\3\2\2\2\33t\3\2\2\2\35z\3\2\2\2\37\u0080\3\2\2\2!\u0089"+
		"\3\2\2\2#\u0090\3\2\2\2%\u0092\3\2\2\2\'\u0094\3\2\2\2)\u0096\3\2\2\2"+
		"+\u0098\3\2\2\2-\u009a\3\2\2\2/\u009c\3\2\2\2\61\u009e\3\2\2\2\63\u00a0"+
		"\3\2\2\2\65\u00a3\3\2\2\2\67\u00a6\3\2\2\29\u00a9\3\2\2\2;\u00ac\3\2\2"+
		"\2=\u00af\3\2\2\2?\u00b2\3\2\2\2A\u00b4\3\2\2\2C\u00bd\3\2\2\2E\u00bf"+
		"\3\2\2\2G\u00c6\3\2\2\2I\u00d1\3\2\2\2K\u00ed\3\2\2\2M\u00f1\3\2\2\2O"+
		"P\7k\2\2PQ\7p\2\2QR\7v\2\2R\4\3\2\2\2ST\7e\2\2TU\7q\2\2UV\7p\2\2VW\7u"+
		"\2\2WX\7v\2\2X\6\3\2\2\2YZ\7.\2\2Z\b\3\2\2\2[\\\7=\2\2\\\n\3\2\2\2]^\7"+
		"?\2\2^\f\3\2\2\2_`\7}\2\2`\16\3\2\2\2ab\7\177\2\2b\20\3\2\2\2cd\7*\2\2"+
		"d\22\3\2\2\2ef\7+\2\2f\24\3\2\2\2gh\7x\2\2hi\7q\2\2ij\7k\2\2jk\7f\2\2"+
		"k\26\3\2\2\2lm\7k\2\2mn\7h\2\2n\30\3\2\2\2op\7g\2\2pq\7n\2\2qr\7u\2\2"+
		"rs\7g\2\2s\32\3\2\2\2tu\7y\2\2uv\7j\2\2vw\7k\2\2wx\7n\2\2xy\7g\2\2y\34"+
		"\3\2\2\2z{\7d\2\2{|\7t\2\2|}\7g\2\2}~\7c\2\2~\177\7m\2\2\177\36\3\2\2"+
		"\2\u0080\u0081\7e\2\2\u0081\u0082\7q\2\2\u0082\u0083\7p\2\2\u0083\u0084"+
		"\7v\2\2\u0084\u0085\7k\2\2\u0085\u0086\7p\2\2\u0086\u0087\7w\2\2\u0087"+
		"\u0088\7g\2\2\u0088 \3\2\2\2\u0089\u008a\7t\2\2\u008a\u008b\7g\2\2\u008b"+
		"\u008c\7v\2\2\u008c\u008d\7w\2\2\u008d\u008e\7t\2\2\u008e\u008f\7p\2\2"+
		"\u008f\"\3\2\2\2\u0090\u0091\7-\2\2\u0091$\3\2\2\2\u0092\u0093\7/\2\2"+
		"\u0093&\3\2\2\2\u0094\u0095\7#\2\2\u0095(\3\2\2\2\u0096\u0097\7,\2\2\u0097"+
		"*\3\2\2\2\u0098\u0099\7\61\2\2\u0099,\3\2\2\2\u009a\u009b\7\'\2\2\u009b"+
		".\3\2\2\2\u009c\u009d\7>\2\2\u009d\60\3\2\2\2\u009e\u009f\7@\2\2\u009f"+
		"\62\3\2\2\2\u00a0\u00a1\7>\2\2\u00a1\u00a2\7?\2\2\u00a2\64\3\2\2\2\u00a3"+
		"\u00a4\7@\2\2\u00a4\u00a5\7?\2\2\u00a5\66\3\2\2\2\u00a6\u00a7\7?\2\2\u00a7"+
		"\u00a8\7?\2\2\u00a88\3\2\2\2\u00a9\u00aa\7#\2\2\u00aa\u00ab\7?\2\2\u00ab"+
		":\3\2\2\2\u00ac\u00ad\7(\2\2\u00ad\u00ae\7(\2\2\u00ae<\3\2\2\2\u00af\u00b0"+
		"\7~\2\2\u00b0\u00b1\7~\2\2\u00b1>\3\2\2\2\u00b2\u00b3\7]\2\2\u00b3@\3"+
		"\2\2\2\u00b4\u00b5\7_\2\2\u00b5B\3\2\2\2\u00b6\u00be\t\2\2\2\u00b7\u00b9"+
		"\t\2\2\2\u00b8\u00ba\t\3\2\2\u00b9\u00b8\3\2\2\2\u00ba\u00bb\3\2\2\2\u00bb"+
		"\u00b9\3\2\2\2\u00bb\u00bc\3\2\2\2\u00bc\u00be\3\2\2\2\u00bd\u00b6\3\2"+
		"\2\2\u00bd\u00b7\3\2\2\2\u00beD\3\2\2\2\u00bf\u00c3\t\4\2\2\u00c0\u00c2"+
		"\t\5\2\2\u00c1\u00c0\3\2\2\2\u00c2\u00c5\3\2\2\2\u00c3\u00c1\3\2\2\2\u00c3"+
		"\u00c4\3\2\2\2\u00c4F\3\2\2\2\u00c5\u00c3\3\2\2\2\u00c6\u00ca\7\62\2\2"+
		"\u00c7\u00c9\t\6\2\2\u00c8\u00c7\3\2\2\2\u00c9\u00cc\3\2\2\2\u00ca\u00c8"+
		"\3\2\2\2\u00ca\u00cb\3\2\2\2\u00cbH\3\2\2\2\u00cc\u00ca\3\2\2\2\u00cd"+
		"\u00ce\7\62\2\2\u00ce\u00d2\7z\2\2\u00cf\u00d0\7\62\2\2\u00d0\u00d2\7"+
		"Z\2\2\u00d1\u00cd\3\2\2\2\u00d1\u00cf\3\2\2\2\u00d2\u00d4\3\2\2\2\u00d3"+
		"\u00d5\t\7\2\2\u00d4\u00d3\3\2\2\2\u00d5\u00d6\3\2\2\2\u00d6\u00d4\3\2"+
		"\2\2\u00d6\u00d7\3\2\2\2\u00d7J\3\2\2\2\u00d8\u00d9\7\61\2\2\u00d9\u00da"+
		"\7,\2\2\u00da\u00de\3\2\2\2\u00db\u00dd\13\2\2\2\u00dc\u00db\3\2\2\2\u00dd"+
		"\u00e0\3\2\2\2\u00de\u00df\3\2\2\2\u00de\u00dc\3\2\2\2\u00df\u00e1\3\2"+
		"\2\2\u00e0\u00de\3\2\2\2\u00e1\u00e2\7,\2\2\u00e2\u00ee\7\61\2\2\u00e3"+
		"\u00e4\7\61\2\2\u00e4\u00e5\7\61\2\2\u00e5\u00e9\3\2\2\2\u00e6\u00e8\13"+
		"\2\2\2\u00e7\u00e6\3\2\2\2\u00e8\u00eb\3\2\2\2\u00e9\u00ea\3\2\2\2\u00e9"+
		"\u00e7\3\2\2\2\u00ea\u00ec\3\2\2\2\u00eb\u00e9\3\2\2\2\u00ec\u00ee\t\b"+
		"\2\2\u00ed\u00d8\3\2\2\2\u00ed\u00e3\3\2\2\2\u00ee\u00ef\3\2\2\2\u00ef"+
		"\u00f0\b&\2\2\u00f0L\3\2\2\2\u00f1\u00f2\t\t\2\2\u00f2\u00f3\3\2\2\2\u00f3"+
		"\u00f4\b\'\2\2\u00f4N\3\2\2\2\f\2\u00bb\u00bd\u00c3\u00ca\u00d1\u00d6"+
		"\u00de\u00e9\u00ed\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}