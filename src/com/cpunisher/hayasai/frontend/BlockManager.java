package com.cpunisher.hayasai.frontend;

import com.cpunisher.hayasai.ir.value.Block;
import com.cpunisher.hayasai.ir.value.expr.OperandExpression;
import com.cpunisher.hayasai.ir.value.func.FunctionDef;
import com.cpunisher.hayasai.ir.value.operand.Register;
import com.cpunisher.hayasai.ir.value.stmt.BrStatement;
import com.cpunisher.hayasai.ir.value.stmt.Statement;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public final class BlockManager {
    private final FunctionDef functionDef;
    private final Deque<Block> nextBlocks = new LinkedList<>();
    private final Map<Block, Block> nextTable = new HashMap<>();
    private Block current;

    public BlockManager(FunctionDef functionDef) {
        this.functionDef = functionDef;
    }

    public Block create(boolean isNext, Block parent) {
        Block block;
        if (parent == null) {
            block = new Block(functionDef);
        } else {
            block = new Block(functionDef, parent);
        }

        this.functionDef.addBlock(block);

        if (isNext) {
            if (!this.nextBlocks.isEmpty()) {
                this.setNext(block, this.nextBlocks.getLast());
            }
            this.nextBlocks.addLast(block);
        }
        return block;
    }

    public Block create(boolean isNext) {
        return this.create(isNext, this.current);
    }

    public void addToCurrent(Statement sub) {
        this.current().addSub(sub);
    }

    public Block getBlockByExp(OperandExpression expression) {
        assert expression.getOperand() instanceof Register;
        return this.functionDef.getBlockOfRegister((Register) expression.getOperand());
    }

    public void setCurrent(Block block) {
        if (this.current != null && this.nextTable.containsKey(this.current)) {
            this.current.addSub(new BrStatement(this.nextTable.get(this.current), this.current));
        }
        this.current = block;
        if (!this.nextBlocks.isEmpty() && this.current == this.nextBlocks.getLast()) {
            this.nextBlocks.removeLast();
        }
    }

    public void setNext(Block origin, Block next) {
        if (next != null) {
            this.nextTable.put(origin, next);
        } else {
            this.nextTable.remove(origin);
        }
    }

    public Block current() {
        return this.current;
    }

    public FunctionDef currentFunc() {
        return this.functionDef;
    }
}
