package com.cpunisher.hayasai.ir.value.operand;

import com.cpunisher.hayasai.ir.type.Type;

public class VoidOperand extends Operand {

    public VoidOperand() {
        super(Type.VOID);
    }

    @Override
    public String generate() {
        return "";
    }

    @Override
    public int getComputedValue() {
        return 0;
    }

    @Override
    public boolean canCompute() {
        return false;
    }
}
