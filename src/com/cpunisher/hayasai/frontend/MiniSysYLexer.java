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
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, PLUS=9, 
		MINUS=10, MUL=11, DIV=12, MOD=13, DEC_CONST=14, OCT_CONST=15, HEX_CONST=16, 
		COMMENT=17, WHITE_SPACE=18;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "T__7", "PLUS", 
			"MINUS", "MUL", "DIV", "MOD", "DEC_CONST", "OCT_CONST", "HEX_CONST", 
			"COMMENT", "WHITE_SPACE"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'('", "')'", "'int'", "'main'", "'{'", "'}'", "'return'", "';'", 
			"'+'", "'-'", "'*'", "'/'", "'%'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, "PLUS", "MINUS", 
			"MUL", "DIV", "MOD", "DEC_CONST", "OCT_CONST", "HEX_CONST", "COMMENT", 
			"WHITE_SPACE"
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\24\u0081\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\3\2\3\2\3\3\3\3\3\4\3\4\3\4\3\4\3\5\3\5\3\5\3\5\3\5\3"+
		"\6\3\6\3\7\3\7\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\t\3\t\3\n\3\n\3\13\3\13\3"+
		"\f\3\f\3\r\3\r\3\16\3\16\3\17\3\17\7\17N\n\17\f\17\16\17Q\13\17\3\20\3"+
		"\20\7\20U\n\20\f\20\16\20X\13\20\3\21\3\21\3\21\3\21\5\21^\n\21\3\21\6"+
		"\21a\n\21\r\21\16\21b\3\22\3\22\3\22\3\22\7\22i\n\22\f\22\16\22l\13\22"+
		"\3\22\3\22\3\22\3\22\3\22\3\22\7\22t\n\22\f\22\16\22w\13\22\3\22\5\22"+
		"z\n\22\3\22\3\22\3\23\3\23\3\23\3\23\4ju\2\24\3\3\5\4\7\5\t\6\13\7\r\b"+
		"\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35\20\37\21!\22#\23%\24\3\2\b\3"+
		"\2\63;\3\2\62;\3\2\629\5\2\62;CHch\4\2\f\f\17\17\5\2\13\f\17\17\"\"\2"+
		"\u0087\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2"+
		"\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3"+
		"\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2"+
		"\2#\3\2\2\2\2%\3\2\2\2\3\'\3\2\2\2\5)\3\2\2\2\7+\3\2\2\2\t/\3\2\2\2\13"+
		"\64\3\2\2\2\r\66\3\2\2\2\178\3\2\2\2\21?\3\2\2\2\23A\3\2\2\2\25C\3\2\2"+
		"\2\27E\3\2\2\2\31G\3\2\2\2\33I\3\2\2\2\35K\3\2\2\2\37R\3\2\2\2!]\3\2\2"+
		"\2#y\3\2\2\2%}\3\2\2\2\'(\7*\2\2(\4\3\2\2\2)*\7+\2\2*\6\3\2\2\2+,\7k\2"+
		"\2,-\7p\2\2-.\7v\2\2.\b\3\2\2\2/\60\7o\2\2\60\61\7c\2\2\61\62\7k\2\2\62"+
		"\63\7p\2\2\63\n\3\2\2\2\64\65\7}\2\2\65\f\3\2\2\2\66\67\7\177\2\2\67\16"+
		"\3\2\2\289\7t\2\29:\7g\2\2:;\7v\2\2;<\7w\2\2<=\7t\2\2=>\7p\2\2>\20\3\2"+
		"\2\2?@\7=\2\2@\22\3\2\2\2AB\7-\2\2B\24\3\2\2\2CD\7/\2\2D\26\3\2\2\2EF"+
		"\7,\2\2F\30\3\2\2\2GH\7\61\2\2H\32\3\2\2\2IJ\7\'\2\2J\34\3\2\2\2KO\t\2"+
		"\2\2LN\t\3\2\2ML\3\2\2\2NQ\3\2\2\2OM\3\2\2\2OP\3\2\2\2P\36\3\2\2\2QO\3"+
		"\2\2\2RV\7\62\2\2SU\t\4\2\2TS\3\2\2\2UX\3\2\2\2VT\3\2\2\2VW\3\2\2\2W "+
		"\3\2\2\2XV\3\2\2\2YZ\7\62\2\2Z^\7z\2\2[\\\7\62\2\2\\^\7Z\2\2]Y\3\2\2\2"+
		"][\3\2\2\2^`\3\2\2\2_a\t\5\2\2`_\3\2\2\2ab\3\2\2\2b`\3\2\2\2bc\3\2\2\2"+
		"c\"\3\2\2\2de\7\61\2\2ef\7,\2\2fj\3\2\2\2gi\13\2\2\2hg\3\2\2\2il\3\2\2"+
		"\2jk\3\2\2\2jh\3\2\2\2km\3\2\2\2lj\3\2\2\2mn\7,\2\2nz\7\61\2\2op\7\61"+
		"\2\2pq\7\61\2\2qu\3\2\2\2rt\13\2\2\2sr\3\2\2\2tw\3\2\2\2uv\3\2\2\2us\3"+
		"\2\2\2vx\3\2\2\2wu\3\2\2\2xz\t\6\2\2yd\3\2\2\2yo\3\2\2\2z{\3\2\2\2{|\b"+
		"\22\2\2|$\3\2\2\2}~\t\7\2\2~\177\3\2\2\2\177\u0080\b\23\2\2\u0080&\3\2"+
		"\2\2\n\2OV]bjuy\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}