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

    public Block create(String name, boolean isNext) {
        Block block;
        if (root == null) {
            block = new Block(name);
        } else {
            block = new Block(name, root);
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

    public Block createAndSet(String name) {
        Block block = this.create(name, false);
        this.setCurrent(block);
        return block;
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
