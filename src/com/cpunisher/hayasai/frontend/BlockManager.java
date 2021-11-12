package com.cpunisher.hayasai.frontend;

import com.cpunisher.hayasai.ir.value.Block;
import com.cpunisher.hayasai.ir.value.Value;
import com.cpunisher.hayasai.ir.value.stmt.BrStatement;

import java.util.Deque;
import java.util.LinkedList;

public final class BlockManager {
    private final Deque<Block> nextBlocks = new LinkedList<>();
    private Block root;
    private Block current;

    public Block create(String name, boolean isNext, Block parent) {
        Block block;
        if (parent == null) {
            block = new Block(name);
        } else {
            block = new Block(name, parent);
            parent.addSuccessor(block);
        }

        if (root != null) {
            root.addSub(block);
        }

        if (isNext) {
            if (!this.nextBlocks.isEmpty()) {
                block.setNext(this.nextBlocks.getLast());
            }
            this.nextBlocks.addLast(block);
        }
        return block;
    }

    public Block create(String name, boolean isNext) {
        return this.create(name, isNext, this.current);
    }

    public void addToCurrent(Value sub) {
        this.current().addSub(sub);
    }

    public void setRoot(Block block) {
        this.root = block;
    }

    public void setCurrent(Block block) {
        if (this.current != null && this.current.hasNext()) {
            this.current.addSub(new BrStatement(this.current.getNext().getBlockRegister()));
        }
        this.current = block;
        if (!this.nextBlocks.isEmpty() && this.current == this.nextBlocks.getLast()) {
            this.nextBlocks.removeLast();
        }
    }

    public Block current() {
        return this.current;
    }
}
