package com.cpunisher.hayasai.ir.global;

import com.cpunisher.hayasai.ir.value.Ident;
import com.cpunisher.hayasai.ir.value.operand.Operand;
import com.cpunisher.hayasai.util.SyntaxException;

import java.util.HashMap;
import java.util.Map;

public class VariableTable<T extends Operand, U extends Operand> implements IVariableTable<T, U> {
    private final Map<Ident, T> varTable;
    private final Map<Ident, U> constTable;

    public VariableTable() {
        this.varTable = new HashMap<>();
        this.constTable = new HashMap<>();
    }

    public T getVar(Ident ident) {
        return this.varTable.get(ident);
    }

    public U getConst(Ident ident) {
        return this.constTable.get(ident);
    }

    public void putVar(Ident ident, T value) {
        if (this.identExists(ident)) {
            throw new SyntaxException("Ident [" + ident.getIdent() + "] exists.");
        }
        this.varTable.put(ident, value);
    }

    public void putConst(Ident ident, U constValue) {
        if (this.identExists(ident)) {
            throw new SyntaxException("Ident [" + ident.getIdent() + "] exists.");
        }
        this.constTable.put(ident, constValue);
    }

    private boolean identExists(Ident ident) {
        return varTable.containsKey(ident) || constTable.containsKey(ident);
    }
}
