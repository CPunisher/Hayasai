package com.cpunisher.hayasai.ir.value.operand;

import com.cpunisher.hayasai.ir.type.Type;

public class Literal extends Operand {
    public static final Literal INT_ZERO = new Literal(0);
    public static final Literal BIT_ZERO = new Literal(Type.BIT, 0);
    private final int value;

    public Literal(int number) {
        this(Type.INT, number);
    }

    public Literal(Type type, int number) {
        super(type);
        this.value = number;
    }

    @Override
    public String generate() {
        return String.valueOf(this.value);
    }

    @Override
    public int getComputedValue() {
        return this.value;
    }

    @Override
    public boolean canCompute() {
        return true;
    }

    @Override
    public void addUser(Use use) {
    }

    @Override
    public void replaceUser(Operand newOperand) {
    }
}
