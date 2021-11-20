package com.cpunisher.hayasai.ir.value.operand;

import com.cpunisher.hayasai.ir.type.Type;
import com.cpunisher.hayasai.ir.value.IUser;
import com.cpunisher.hayasai.ir.value.Ident;
import com.cpunisher.hayasai.util.IrKeywords;
import com.cpunisher.hayasai.util.SyntaxException;

import java.util.List;

public final class GlobalOperand extends Operand implements IUser {
    private final Ident ident;
    private Operand initValue;

    public GlobalOperand(Type type, Ident ident, Operand initValue) {
        super(type);
        this.ident = ident;
        this.initValue = initValue;
        this.initValue.addUser(new Use(this));
    }

    public Operand getInitValue() {
        return initValue;
    }

    @Override
    public int getComputedValue() {
        if (this.initValue instanceof Literal literal) {
            return literal.getComputedValue();
        }
        throw new SyntaxException("Only integer type can be computed.");
    }

    @Override
    public boolean canCompute() {
        return this.initValue instanceof Literal;
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
