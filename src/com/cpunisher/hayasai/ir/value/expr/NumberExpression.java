package com.cpunisher.hayasai.ir.value.expr;

public final class NumberExpression extends Expression {
    private final int number;

    public NumberExpression(String name, int number) {
        super(name);
        this.number = number;
    }

    @Override
    public String build() {
        return String.valueOf(this.number);
    }
}
