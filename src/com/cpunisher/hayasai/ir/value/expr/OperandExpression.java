package com.cpunisher.hayasai.ir.value.expr;

import com.cpunisher.hayasai.ir.value.operand.Operand;
import com.cpunisher.hayasai.util.SyntaxException;

public class OperandExpression extends Expression {
    private final Operand operand;
    private final boolean immutable;

    public OperandExpression(Operand operand) {
        this.operand = operand;
        this.immutable = false;
    }

    public OperandExpression(Operand operand, boolean immutable) {
        this.operand = operand;
        this.immutable = immutable;
    }

    public Operand getOperand() {
        return operand;
    }

    public boolean isImmutable() {
        return immutable;
    }

    public boolean canCompute() {
        return this.operand.canCompute();
    }

    public int getIntValue() {
        if (!this.isImmutable()) {
            throw SyntaxException.computedError(this.getClass());
        }
        return this.operand.getComputedValue();
    }

    @Override
    public void build() {
        this.operand.build();
    }

    @Override
    public String generate() {
        return this.operand.getType().generate() + " " + this.operand.generate();
    }
}
