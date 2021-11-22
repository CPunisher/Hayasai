package com.cpunisher.hayasai;

import com.cpunisher.hayasai.frontend.Visitor;
import com.cpunisher.hayasai.frontend.antlr.MiniSysYLexer;
import com.cpunisher.hayasai.frontend.antlr.MiniSysYParser;
import com.cpunisher.hayasai.ir.global.SymbolTable;
import com.cpunisher.hayasai.ir.pass.FunctionInline;
import com.cpunisher.hayasai.ir.pass.IPass;
import com.cpunisher.hayasai.ir.type.Type;
import com.cpunisher.hayasai.ir.value.Ident;
import com.cpunisher.hayasai.ir.value.Value;
import com.cpunisher.hayasai.ir.value.func.Function;
import com.cpunisher.hayasai.ir.value.func.FunctionDecl;
import com.cpunisher.hayasai.ir.value.func.FunctionDef;
import com.cpunisher.hayasai.util.IrKeywords;
import com.cpunisher.hayasai.util.SyntaxException;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTree;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HayasaiFrontend {
    private final SymbolTable module = SymbolTable.INSTANCE;
    private final Map<Ident, MiniSysYParser.FuncDefContext> funcDefContextMap = new HashMap<>();
    private final Visitor visitor = new Visitor(this);

    private final List<IPass> passList = List.of(
            new FunctionInline()
//        new DeadCodeRemove(),
//        new BlockMerge(),
//        new UseGenerator(),
//        new MemToReg(),
//        new ConstFold()
    );

    public void visitAst(String input) {
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
        this.visitor.visit(tree);
    }

    public void check() {
        // int main()
        Map<Ident, FunctionDef> functionDefMap = module.getFuncDefTable();
        Function funcMain = functionDefMap.get(Ident.valueOf("main"));
        if (funcMain == null || !funcMain.getFuncType().equals(Type.INT) || funcMain.getParam().size() != 0) {
            throw SyntaxException.noMain();
        }
    }

    public void passAll() {
        passList.forEach(pass -> pass.pass(module));
    }

    public void generateCode() {
        Map<Ident, FunctionDecl> functionDeclMap = module.getFuncDeclTable();
        Map<Ident, FunctionDef> functionDefMap = module.getFuncDefTable();

        module.getGlobalFunc().build();
        functionDeclMap.values().forEach(Value::build);
        functionDefMap.values().forEach(Value::build);

        System.out.println(module.generateVars());
        System.out.println(functionDeclMap.values().stream()
                .map(FunctionDecl::generate)
                .collect(Collectors.joining(IrKeywords.LINE_SEPARATOR)));
        System.out.println(functionDefMap.values().stream()
                .map(FunctionDef::generate)
                .collect(Collectors.joining(IrKeywords.LINE_SEPARATOR)));
    }

    public FunctionDef getCopiedFuncDef(Ident ident) {
        MiniSysYParser.FuncDefContext ctx = this.funcDefContextMap.get(ident);
        return (FunctionDef) this.visitor.visitFuncDef(ctx);
    }

    public void addFuncDefCtx(MiniSysYParser.FuncDefContext ctx) {
        Ident ident = Ident.valueOf(ctx.IDENT().getText());
        this.funcDefContextMap.put(ident, ctx);
    }

    public SymbolTable getModule() {
        return module;
    }
}
