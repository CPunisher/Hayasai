package com.cpunisher.hayasai.ir.value.operand;

import com.cpunisher.hayasai.ir.type.Type;
import com.cpunisher.hayasai.ir.value.Value;

public abstract class Operand extends Value {
    private final Type type = Type.INT;

    public Operand(String name) {
        super(name);
    }

    public abstract int getIntValue();

    public Type getType() {
        return type;
    }
}
