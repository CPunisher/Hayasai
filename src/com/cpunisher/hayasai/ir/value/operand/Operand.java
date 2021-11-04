package com.cpunisher.hayasai.ir.value.operand;

import com.cpunisher.hayasai.ir.type.Type;
import com.cpunisher.hayasai.ir.value.Value;

public abstract class Operand extends Value {
    private final Type type;

    protected Operand(Type type) {
        this.type = type;
    }

    protected Operand() {
        this(Type.INT);
    }

    public abstract int getIntValue();

    public Type getType() {
        return type;
    }
}
