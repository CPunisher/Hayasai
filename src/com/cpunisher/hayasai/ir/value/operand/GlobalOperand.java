package com.cpunisher.hayasai.ir.value.operand;

import com.cpunisher.hayasai.ir.global.SymbolTable;
import com.cpunisher.hayasai.ir.type.Type;
import com.cpunisher.hayasai.ir.value.IUser;
import com.cpunisher.hayasai.ir.value.Ident;
import com.cpunisher.hayasai.ir.value.Value;
import com.cpunisher.hayasai.util.IrKeywords;
import com.cpunisher.hayasai.util.SyntaxException;

import java.util.List;

public final class GlobalOperand extends Operand implements IUser {
    private final SymbolTable symbolTable;
    private final Ident ident;
    private Value initValue;

    public GlobalOperand(SymbolTable symbolTable, Type type, Ident ident, Value initValue) {
        super(type);
        this.symbolTable = symbolTable;
        this.ident = ident;
        this.initValue = initValue;
        if (this.initValue instanceof Operand operand) {
            operand.addUser(new Use(this));
        }
    }

    public Value getInitValue() {
        return this.initValue;
    }

    @Override
    public int getComputedValue() {
        if (this.canCompute()) {
            return ((Literal) this.initValue).getComputedValue();
        }
        throw SyntaxException.computedError(this.getClass());
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
        if (this.initValue instanceof Operand) {
            this.initValue = newOperand;
        }
    }

    @Override
    public List<Operand> getOperands() {
        if (this.initValue instanceof Operand operand) {
            return List.of(operand);
        }
        return List.of();
    }
}
