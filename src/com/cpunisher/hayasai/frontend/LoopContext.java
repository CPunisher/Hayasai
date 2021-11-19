package com.cpunisher.hayasai.frontend;

import com.cpunisher.hayasai.ir.value.Block;

import java.util.Stack;

public class LoopContext {

    private final Stack<Block> stackCondition = new Stack<>();
    private final Stack<Block> stackAfter = new Stack<>();

    public void push(Block blockCond, Block blockAfter) {
        this.stackCondition.push(blockCond);
        this.stackAfter.push(blockAfter);
    }

    public void pop() {
        this.stackCondition.pop();
        this.stackAfter.pop();
    }

    public Block peekCondition() {
        return this.stackCondition.peek();
    }

    public Block peekAfter() {
        return this.stackAfter.peek();
    }
}
