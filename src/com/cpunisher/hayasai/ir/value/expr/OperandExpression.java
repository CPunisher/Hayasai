package com.cpunisher.hayasai.ir.value.expr;

import com.cpunisher.hayasai.ir.value.operand.Operand;

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

    @Override
    public void build() {
        this.operand.build();
    }

    @Override
    public String generate() {
        return this.operand.getType().generate() + " " + this.operand.generate();
    }
}
