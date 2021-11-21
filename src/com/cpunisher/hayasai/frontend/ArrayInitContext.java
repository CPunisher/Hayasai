package com.cpunisher.hayasai.frontend;

import com.cpunisher.hayasai.ir.value.operand.Register;

import java.util.Stack;

public class ArrayInitContext {

    private Stack<Register> level = new Stack<>();

    public Stack<Register> getLevel() {
        return level;
    }

    public void clear() {
        this.level.clear();
    }
}
