package com.cpunisher.hayasai.ir.value.operand;

import com.cpunisher.hayasai.ir.global.IVariableTable;
import com.cpunisher.hayasai.ir.global.SymbolTable;
import com.cpunisher.hayasai.ir.type.Type;
import com.cpunisher.hayasai.ir.value.IUser;
import com.cpunisher.hayasai.ir.value.Ident;
import com.cpunisher.hayasai.util.IrKeywords;
import com.cpunisher.hayasai.util.SyntaxException;

import java.util.List;

public final class GlobalOperand extends Operand implements IUser {
    private final SymbolTable symbolTable;
    private final Ident ident;
    private Operand initValue;

    public GlobalOperand(SymbolTable symbolTable, Type type, Ident ident, Operand initValue) {
        super(type);
        this.symbolTable = symbolTable;
        this.ident = ident;
        this.initValue = initValue;
        this.initValue.addUser(new Use(this));
    }

    public Operand getInitValue() {
        return initValue;
    }

    @Override
    public int getComputedValue() {
        if (this.initValue instanceof Literal literal && this.symbolTable.isGlobalConst(this.ident)) {
            return literal.getComputedValue();
        }
        throw new SyntaxException("Only immutable integer type can be computed.");
    }

    @Override
    public boolean canCompute() {
        return this.initValue instanceof Literal && this.symbolTable.isGlobalConst(this.ident);
    }

    @Override
    public String generate() {
        return IrKeywords.GLOBAL_IDENT + this.ident.generate();
    }

    @Override
    public void replace(Operand oldOperand, Operand newOperand) {
        this.initValue = newOperand;
    }

    @Override
    public List<Operand> getOperands() {
        return List.of(this.initValue);
    }
}
