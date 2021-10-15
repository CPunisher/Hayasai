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
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, DEC_CONST=9, 
		OCT_CONST=10, HEX_CONST=11, WHITE_SPACE=12;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "T__7", "DEC_CONST", 
			"OCT_CONST", "HEX_CONST", "WHITE_SPACE"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'('", "')'", "'int'", "'main'", "'{'", "'}'", "'return'", "';'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, "DEC_CONST", "OCT_CONST", 
			"HEX_CONST", "WHITE_SPACE"
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\16R\b\1\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\3\2\3\2\3\3\3\3\3\4\3\4\3\4\3\4\3\5\3\5\3\5\3\5\3"+
		"\5\3\6\3\6\3\7\3\7\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\t\3\t\3\n\3\n\7\n8\n"+
		"\n\f\n\16\n;\13\n\3\13\3\13\7\13?\n\13\f\13\16\13B\13\13\3\f\3\f\3\f\3"+
		"\f\5\fH\n\f\3\f\6\fK\n\f\r\f\16\fL\3\r\3\r\3\r\3\r\2\2\16\3\3\5\4\7\5"+
		"\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\3\2\7\3\2\63;\3\2\62;\3"+
		"\2\629\5\2\62;CHch\5\2\13\f\17\17\"\"\2U\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3"+
		"\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2"+
		"\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\3\33\3\2\2\2\5\35"+
		"\3\2\2\2\7\37\3\2\2\2\t#\3\2\2\2\13(\3\2\2\2\r*\3\2\2\2\17,\3\2\2\2\21"+
		"\63\3\2\2\2\23\65\3\2\2\2\25<\3\2\2\2\27G\3\2\2\2\31N\3\2\2\2\33\34\7"+
		"*\2\2\34\4\3\2\2\2\35\36\7+\2\2\36\6\3\2\2\2\37 \7k\2\2 !\7p\2\2!\"\7"+
		"v\2\2\"\b\3\2\2\2#$\7o\2\2$%\7c\2\2%&\7k\2\2&\'\7p\2\2\'\n\3\2\2\2()\7"+
		"}\2\2)\f\3\2\2\2*+\7\177\2\2+\16\3\2\2\2,-\7t\2\2-.\7g\2\2./\7v\2\2/\60"+
		"\7w\2\2\60\61\7t\2\2\61\62\7p\2\2\62\20\3\2\2\2\63\64\7=\2\2\64\22\3\2"+
		"\2\2\659\t\2\2\2\668\t\3\2\2\67\66\3\2\2\28;\3\2\2\29\67\3\2\2\29:\3\2"+
		"\2\2:\24\3\2\2\2;9\3\2\2\2<@\7\62\2\2=?\t\4\2\2>=\3\2\2\2?B\3\2\2\2@>"+
		"\3\2\2\2@A\3\2\2\2A\26\3\2\2\2B@\3\2\2\2CD\7\62\2\2DH\7z\2\2EF\7\62\2"+
		"\2FH\7Z\2\2GC\3\2\2\2GE\3\2\2\2HJ\3\2\2\2IK\t\5\2\2JI\3\2\2\2KL\3\2\2"+
		"\2LJ\3\2\2\2LM\3\2\2\2M\30\3\2\2\2NO\t\6\2\2OP\3\2\2\2PQ\b\r\2\2Q\32\3"+
		"\2\2\2\7\29@GL\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}