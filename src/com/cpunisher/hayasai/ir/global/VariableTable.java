package com.cpunisher.hayasai.ir.global;

import com.cpunisher.hayasai.ir.value.Ident;
import com.cpunisher.hayasai.ir.value.operand.Operand;
import com.cpunisher.hayasai.util.SyntaxException;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class VariableTable<T extends Operand, U extends Operand> implements IVariableTable<T, U> {
    private final Map<Ident, T> varTable;
    private final Map<Ident, U> constTable;

    public VariableTable() {
        this.varTable = new LinkedHashMap<>();
        this.constTable = new LinkedHashMap<>();
    }

    public T getVar(Ident ident) {
        return this.varTable.get(ident);
    }

    public U getConst(Ident ident) {
        return this.constTable.get(ident);
    }

    public void putVar(Ident ident, T value) {
        if (this.identExists(ident)) {
            throw SyntaxException.identExists(ident.getIdent());
        }
        this.varTable.put(ident, value);
    }

    public void putConst(Ident ident, U constValue) {
        if (this.identExists(ident)) {
            throw SyntaxException.identExists(ident.getIdent());
        }
        this.constTable.put(ident, constValue);
    }

    public void merge(VariableTable<T, U> variableTable) {
        this.varTable.putAll(variableTable.varTable);
        this.constTable.putAll(variableTable.constTable);
    }

    private boolean identExists(Ident ident) {
        return varTable.containsKey(ident) || constTable.containsKey(ident);
    }

    public Map<Ident, T> getVarTable() {
        return Collections.unmodifiableMap(varTable);
    }

    public Map<Ident, U> getConstTable() {
        return Collections.unmodifiableMap(constTable);
    }
}
