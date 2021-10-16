package com.cpunisher.hayasai.ir.value.expr;

import com.cpunisher.hayasai.ir.type.Type;

public final class NumberExpression extends Expression {
    private final int number;

    public NumberExpression(String name, int number) {
        super(name);
        this.number = number;
    }

    @Override
    public String build() {
        return Type.INT.build() + " " + this.number;
    }
}
