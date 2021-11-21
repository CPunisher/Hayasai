package com.cpunisher.hayasai.ir.value.operand;

public class VoidOperand extends Operand {
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
