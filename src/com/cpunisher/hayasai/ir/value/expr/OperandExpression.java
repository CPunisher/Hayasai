package com.cpunisher.hayasai.ir.value.expr;

import com.cpunisher.hayasai.ir.value.operand.Operand;

public final class OperandExpression extends Expression {
    private final Operand operand;

    public OperandExpression(String name, Operand operand) {
        super(name);
        this.operand = operand;
    }

    public Operand getOperand() {
        return operand;
    }

    @Override
    public String build() {
        return this.operand.getType().build() + " " + this.operand.build();
    }
}
