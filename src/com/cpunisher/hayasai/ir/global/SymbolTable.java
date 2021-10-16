package com.cpunisher.hayasai.ir.global;

import com.cpunisher.hayasai.ir.value.Ident;
import com.cpunisher.hayasai.ir.value.func.FunctionDef;
import com.cpunisher.hayasai.util.SyntaxException;

import java.util.Collections;
import java.util.Hashtable;
import java.util.Map;

public final class SymbolTable {
    public static final SymbolTable INSTANCE = new SymbolTable();

    private Hashtable<Ident, FunctionDef> functionTable = new Hashtable<>();

    private SymbolTable() {}

    public void putFunctionDef(FunctionDef functionDef) throws SyntaxException {
        FunctionDef def = this.functionTable.get(functionDef.getIdent());
        if (def != null && def.equals(functionDef)) {
            throw new SyntaxException("Function has already existed.");
        }
        this.functionTable.put(functionDef.getIdent(), functionDef);
    }

    public FunctionDef getFunctionDef(Ident ident) throws SyntaxException {
        FunctionDef def = this.functionTable.get(ident);
        if (def == null) {
            throw new SyntaxException("Function is not existed.");
        }
        return def;
    }

    public Map<Ident, FunctionDef> getImmutableSymbolTable() {
        return Collections.unmodifiableMap(this.functionTable);
    }
}
