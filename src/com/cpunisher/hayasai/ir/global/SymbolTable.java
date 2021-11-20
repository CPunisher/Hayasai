package com.cpunisher.hayasai.ir.global;

import com.cpunisher.hayasai.ir.type.Type;
import com.cpunisher.hayasai.ir.value.Ident;
import com.cpunisher.hayasai.ir.value.func.Function;
import com.cpunisher.hayasai.ir.value.func.FunctionDecl;
import com.cpunisher.hayasai.ir.value.func.FunctionDef;
import com.cpunisher.hayasai.ir.value.operand.GlobalOperand;
import com.cpunisher.hayasai.ir.value.operand.Literal;
import com.cpunisher.hayasai.util.IrKeywords;
import com.cpunisher.hayasai.util.SyntaxException;

import java.util.Collections;
import java.util.Hashtable;
import java.util.Map;
import java.util.StringJoiner;

public final class SymbolTable {
    public static final SymbolTable INSTANCE = new SymbolTable();

    private final FunctionDef globalFunc = FunctionDef.createEmpty();
    private final Hashtable<Ident, FunctionDecl> funcDeclTable = new Hashtable<>();
    private final Hashtable<Ident, FunctionDef> funcDefTable = new Hashtable<>();
    private final VariableTable<GlobalOperand, GlobalOperand> globalVars = new VariableTable<>();

    private SymbolTable() {
        this.putFunctionDecl(new FunctionDecl(Type.INT, Ident.valueOf("getint"), Function.EMPTY_ARGS));
        this.putFunctionDecl(new FunctionDecl(Type.INT, Ident.valueOf("getch"), Function.EMPTY_ARGS));
        this.putFunctionDecl(new FunctionDecl(Type.VOID, Ident.valueOf("putint"), Function.parseParam(Type.INT)));
        this.putFunctionDecl(new FunctionDecl(Type.VOID, Ident.valueOf("putch"), Function.parseParam(Type.INT)));
        this.putFunctionDecl(new FunctionDecl(Type.VOID, Ident.valueOf("memset"), Function.parseParam(Type.INT.getPointer(), Type.INT, Type.INT)));
    }

    public void putFunctionDecl(FunctionDecl functionDecl) {
        FunctionDecl decl = this.funcDeclTable.get(functionDecl.getIdent());
        if (decl == null || !decl.equals(functionDecl)) {
            this.funcDeclTable.put(functionDecl.getIdent(), functionDecl);
        }
    }

    public void putFunctionDef(FunctionDef functionDef) {
        FunctionDef def = this.funcDefTable.get(functionDef.getIdent());
        if (def != null && def.equals(functionDef)) {
            throw new SyntaxException("Function has already existed.");
        }
        this.funcDefTable.put(functionDef.getIdent(), functionDef);
    }

    public Function getFunctionByIdent(Ident ident) {
        Function f = this.funcDefTable.get(ident);
        if (f == null) {
            f = this.funcDeclTable.get(ident);
        }
        return f;
    }

    public Map<Ident, FunctionDecl> getFuncDeclTable() {
        return Collections.unmodifiableMap(this.funcDeclTable);
    }

    public Map<Ident, FunctionDef> getFuncDefTable() {
        return Collections.unmodifiableMap(this.funcDefTable);
    }

    public VariableTable<GlobalOperand, GlobalOperand> getGlobalVars() {
        return globalVars;
    }

    public FunctionDef getGlobalFunc() {
        return globalFunc;
    }

    public boolean isGlobalConst(Ident ident) {
        return this.globalVars.getConst(ident) != null;
    }

    public String generateVars() {
        Map<Ident, GlobalOperand> globalVarsMap = this.getGlobalVars().getVarTable();
        Map<Ident, GlobalOperand> globalConstMap = this.getGlobalVars().getConstTable();
        StringJoiner result = new StringJoiner(IrKeywords.LINE_SEPARATOR);

        for (Map.Entry<Ident, GlobalOperand> entry : globalVarsMap.entrySet()) {
            StringJoiner joiner = new StringJoiner(" ");
            joiner.add(IrKeywords.GLOBAL_IDENT + entry.getKey().generate());
            joiner.add(IrKeywords.ASSIGN);
            joiner.add(IrKeywords.DSO_LOCAL);
            joiner.add(IrKeywords.GLOBAL);
            joiner.add(entry.getValue().getType().getWrappedType().generate());
            joiner.add(entry.getValue().getInitValue().generate());
            result.add(joiner.toString());
        }

        for (Map.Entry<Ident, GlobalOperand> entry : globalConstMap.entrySet()) {
            StringJoiner joiner = new StringJoiner(" ");
            joiner.add(IrKeywords.GLOBAL_IDENT + entry.getKey().generate());
            joiner.add(IrKeywords.ASSIGN);
            joiner.add(IrKeywords.DSO_LOCAL);
            joiner.add(IrKeywords.CONSTANT);
            joiner.add(entry.getValue().getType().getWrappedType().generate());
            joiner.add(entry.getValue().getInitValue().generate());
            result.add(joiner.toString());
        }

        return result.toString();
    }
}
