package com.cpunisher.hayasai;

import com.cpunisher.hayasai.frontend.antlr.MiniSysYLexer;
import com.cpunisher.hayasai.frontend.antlr.MiniSysYParser;
import com.cpunisher.hayasai.frontend.Visitor;
import com.cpunisher.hayasai.ir.global.SymbolTable;
import com.cpunisher.hayasai.ir.pass.*;
import com.cpunisher.hayasai.ir.value.Ident;
import com.cpunisher.hayasai.ir.value.Value;
import com.cpunisher.hayasai.ir.value.func.FunctionDecl;
import com.cpunisher.hayasai.ir.value.func.FunctionDef;
import com.cpunisher.hayasai.util.IOUtils;
import com.cpunisher.hayasai.util.IrKeywords;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {
    private static final List<IPass> PASS_LIST = List.of(
        new DeadCodeRemove(),
        new BlockMerge(),
        new UseGenerator(),
        new MemToReg()
    );

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

        PASS_LIST.forEach(pass -> pass.pass(symbolTable));

        symbolTable.getGlobalFunc().build();
        functionDeclMap.values().forEach(Value::build);
        functionDefMap.values().forEach(Value::build);

        System.out.println(symbolTable.generateVars());
        System.out.println(functionDeclMap.values().stream()
                .map(FunctionDecl::generate)
                .collect(Collectors.joining(IrKeywords.LINE_SEPARATOR)));
        System.out.println(functionDefMap.values().stream()
                .map(FunctionDef::generate)
                .collect(Collectors.joining(IrKeywords.LINE_SEPARATOR)));
    }
}
