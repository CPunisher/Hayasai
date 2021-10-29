package com.cpunisher.hayasai.ir.value.expr;

import com.cpunisher.hayasai.ir.value.operand.Operand;

public final class OperandExpression extends Expression {
    private final Operand operand;
    private final boolean immutable;

    public OperandExpression(String name, Operand operand) {
        super(name);
        this.operand = operand;
        this.immutable = false;
    }

    public OperandExpression(String name, Operand operand, boolean immutable) {
        super(name);
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
    public String build() {
        return this.operand.getType().build() + " " + this.operand.build();
    }
}
