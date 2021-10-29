package com.cpunisher.hayasai.frontend;// Generated from MiniSysY.g4 by ANTLR 4.9.2
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
		T__9=10, PLUS=11, MINUS=12, MUL=13, DIV=14, MOD=15, IDENT=16, DEC_CONST=17, 
		OCT_CONST=18, HEX_CONST=19, COMMENT=20, WHITE_SPACE=21;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "T__7", "T__8", 
			"T__9", "PLUS", "MINUS", "MUL", "DIV", "MOD", "IDENT", "DEC_CONST", "OCT_CONST", 
			"HEX_CONST", "COMMENT", "WHITE_SPACE"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'int'", "'const'", "','", "';'", "'='", "'('", "')'", "'{'", "'}'", 
			"'return'", "'+'", "'-'", "'*'", "'/'", "'%'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, "PLUS", 
			"MINUS", "MUL", "DIV", "MOD", "IDENT", "DEC_CONST", "OCT_CONST", "HEX_CONST", 
			"COMMENT", "WHITE_SPACE"
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\27\u0095\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\3\2\3\2\3\2\3\2\3\3\3\3"+
		"\3\3\3\3\3\3\3\3\3\4\3\4\3\5\3\5\3\6\3\6\3\7\3\7\3\b\3\b\3\t\3\t\3\n\3"+
		"\n\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\f\3\f\3\r\3\r\3\16\3\16\3\17\3"+
		"\17\3\20\3\20\3\21\3\21\3\21\6\21Z\n\21\r\21\16\21[\5\21^\n\21\3\22\3"+
		"\22\7\22b\n\22\f\22\16\22e\13\22\3\23\3\23\7\23i\n\23\f\23\16\23l\13\23"+
		"\3\24\3\24\3\24\3\24\5\24r\n\24\3\24\6\24u\n\24\r\24\16\24v\3\25\3\25"+
		"\3\25\3\25\7\25}\n\25\f\25\16\25\u0080\13\25\3\25\3\25\3\25\3\25\3\25"+
		"\3\25\7\25\u0088\n\25\f\25\16\25\u008b\13\25\3\25\5\25\u008e\n\25\3\25"+
		"\3\25\3\26\3\26\3\26\3\26\4~\u0089\2\27\3\3\5\4\7\5\t\6\13\7\r\b\17\t"+
		"\21\n\23\13\25\f\27\r\31\16\33\17\35\20\37\21!\22#\23%\24\'\25)\26+\27"+
		"\3\2\n\5\2C\\aac|\6\2\62;C\\aac|\3\2\63;\3\2\62;\3\2\629\5\2\62;CHch\4"+
		"\2\f\f\17\17\5\2\13\f\17\17\"\"\2\u009d\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3"+
		"\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2"+
		"\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35"+
		"\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)"+
		"\3\2\2\2\2+\3\2\2\2\3-\3\2\2\2\5\61\3\2\2\2\7\67\3\2\2\2\t9\3\2\2\2\13"+
		";\3\2\2\2\r=\3\2\2\2\17?\3\2\2\2\21A\3\2\2\2\23C\3\2\2\2\25E\3\2\2\2\27"+
		"L\3\2\2\2\31N\3\2\2\2\33P\3\2\2\2\35R\3\2\2\2\37T\3\2\2\2!]\3\2\2\2#_"+
		"\3\2\2\2%f\3\2\2\2\'q\3\2\2\2)\u008d\3\2\2\2+\u0091\3\2\2\2-.\7k\2\2."+
		"/\7p\2\2/\60\7v\2\2\60\4\3\2\2\2\61\62\7e\2\2\62\63\7q\2\2\63\64\7p\2"+
		"\2\64\65\7u\2\2\65\66\7v\2\2\66\6\3\2\2\2\678\7.\2\28\b\3\2\2\29:\7=\2"+
		"\2:\n\3\2\2\2;<\7?\2\2<\f\3\2\2\2=>\7*\2\2>\16\3\2\2\2?@\7+\2\2@\20\3"+
		"\2\2\2AB\7}\2\2B\22\3\2\2\2CD\7\177\2\2D\24\3\2\2\2EF\7t\2\2FG\7g\2\2"+
		"GH\7v\2\2HI\7w\2\2IJ\7t\2\2JK\7p\2\2K\26\3\2\2\2LM\7-\2\2M\30\3\2\2\2"+
		"NO\7/\2\2O\32\3\2\2\2PQ\7,\2\2Q\34\3\2\2\2RS\7\61\2\2S\36\3\2\2\2TU\7"+
		"\'\2\2U \3\2\2\2V^\t\2\2\2WY\t\2\2\2XZ\t\3\2\2YX\3\2\2\2Z[\3\2\2\2[Y\3"+
		"\2\2\2[\\\3\2\2\2\\^\3\2\2\2]V\3\2\2\2]W\3\2\2\2^\"\3\2\2\2_c\t\4\2\2"+
		"`b\t\5\2\2a`\3\2\2\2be\3\2\2\2ca\3\2\2\2cd\3\2\2\2d$\3\2\2\2ec\3\2\2\2"+
		"fj\7\62\2\2gi\t\6\2\2hg\3\2\2\2il\3\2\2\2jh\3\2\2\2jk\3\2\2\2k&\3\2\2"+
		"\2lj\3\2\2\2mn\7\62\2\2nr\7z\2\2op\7\62\2\2pr\7Z\2\2qm\3\2\2\2qo\3\2\2"+
		"\2rt\3\2\2\2su\t\7\2\2ts\3\2\2\2uv\3\2\2\2vt\3\2\2\2vw\3\2\2\2w(\3\2\2"+
		"\2xy\7\61\2\2yz\7,\2\2z~\3\2\2\2{}\13\2\2\2|{\3\2\2\2}\u0080\3\2\2\2~"+
		"\177\3\2\2\2~|\3\2\2\2\177\u0081\3\2\2\2\u0080~\3\2\2\2\u0081\u0082\7"+
		",\2\2\u0082\u008e\7\61\2\2\u0083\u0084\7\61\2\2\u0084\u0085\7\61\2\2\u0085"+
		"\u0089\3\2\2\2\u0086\u0088\13\2\2\2\u0087\u0086\3\2\2\2\u0088\u008b\3"+
		"\2\2\2\u0089\u008a\3\2\2\2\u0089\u0087\3\2\2\2\u008a\u008c\3\2\2\2\u008b"+
		"\u0089\3\2\2\2\u008c\u008e\t\b\2\2\u008dx\3\2\2\2\u008d\u0083\3\2\2\2"+
		"\u008e\u008f\3\2\2\2\u008f\u0090\b\25\2\2\u0090*\3\2\2\2\u0091\u0092\t"+
		"\t\2\2\u0092\u0093\3\2\2\2\u0093\u0094\b\26\2\2\u0094,\3\2\2\2\f\2[]c"+
		"jqv~\u0089\u008d\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}