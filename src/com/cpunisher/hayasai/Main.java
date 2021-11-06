package com.cpunisher.hayasai;

import com.cpunisher.hayasai.frontend.MiniSysYLexer;
import com.cpunisher.hayasai.frontend.MiniSysYParser;
import com.cpunisher.hayasai.frontend.Visitor;
import com.cpunisher.hayasai.ir.global.SymbolTable;
import com.cpunisher.hayasai.ir.value.Ident;
import com.cpunisher.hayasai.ir.value.Value;
import com.cpunisher.hayasai.ir.value.func.FunctionDecl;
import com.cpunisher.hayasai.ir.value.func.FunctionDef;
import com.cpunisher.hayasai.util.IOUtils;
import com.cpunisher.hayasai.util.IrKeywords;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws IOException {
        String input = IOUtils.readFromStream(System.in);
        CharStream charStream = CharStreams.fromString(input);
        MiniSysYLexer lexer = new MiniSysYLexer(charStream);
        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        MiniSysYParser parser = new MiniSysYParser(tokenStream);
        parser.addErrorListener(new BaseErrorListener() {
            @Override
            public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
                System.exit(1);
            }
        });

        ParseTree tree = parser.compUnit();
        Visitor visitor = new Visitor();
        visitor.visit(tree);

        SymbolTable symbolTable = SymbolTable.INSTANCE;
        Map<Ident, FunctionDecl> functionDeclMap = symbolTable.getFuncDeclTable();
        Map<Ident, FunctionDef> functionDefMap = symbolTable.getFuncDefTable();
        functionDeclMap.values().forEach(Value::build);
        functionDefMap.values().forEach(Value::build);
        System.out.println(functionDeclMap.values().stream()
                .map(FunctionDecl::generate)
                .collect(Collectors.joining(IrKeywords.LINE_SEPARATOR)));
        System.out.println(functionDefMap.values().stream()
                .map(FunctionDef::generate)
                .collect(Collectors.joining(IrKeywords.LINE_SEPARATOR)));
    }
}
