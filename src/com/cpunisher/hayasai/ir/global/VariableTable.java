package com.cpunisher.hayasai.ir.global;

import com.cpunisher.hayasai.ir.value.Ident;
import com.cpunisher.hayasai.ir.value.operand.Operand;
import com.cpunisher.hayasai.util.SyntaxException;

import java.util.HashMap;
import java.util.Map;

public class VariableTable implements IVariableTable {
    private final Map<Ident, Operand> varTable;
    private final Map<Ident, Operand> constTable;

    public VariableTable() {
        this.varTable = new HashMap<>();
        this.constTable = new HashMap<>();
    }

    public Operand getVar(Ident ident) {
        return this.varTable.get(ident);
    }

    public Operand getConst(Ident ident) {
        return this.constTable.get(ident);
    }

    public void putVar(Ident ident, Operand value) {
        if (this.identExists(ident)) {
            throw new SyntaxException("Ident [" + ident.getIdent() + "] exists.");
        }
        this.varTable.put(ident, value);
    }

    public void putConst(Ident ident, Operand constValue) {
        if (this.identExists(ident)) {
            throw new SyntaxException("Ident [" + ident.getIdent() + "] exists.");
        }
        this.constTable.put(ident, constValue);
    }

    private boolean identExists(Ident ident) {
        return varTable.containsKey(ident) || constTable.containsKey(ident);
    }
}
