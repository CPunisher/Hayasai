package com.cpunisher.hayasai.ir.value.operand;

public class Literal extends Operand {
    public static final Literal ZERO = new Literal("literal_zero", 0);
    private final int value;

    public Literal(String name, int number) {
        super(name);
        this.value = number;
    }

    @Override
    public int getIntValue() {
        return this.value;
    }

    @Override
    public String build() {
        return String.valueOf(this.value);
    }
}
