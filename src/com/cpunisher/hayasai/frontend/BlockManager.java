package com.cpunisher.hayasai.frontend;

import com.cpunisher.hayasai.ir.value.Block;

import java.util.Deque;
import java.util.LinkedList;

public final class BlockManager {

    private final Deque<Block> blocks = new LinkedList<>();

    public void createBlock(String name) {
        Block block;
        if (this.blocks.isEmpty()) {
            block = new Block(name);
        } else {
            Block root = this.blocks.getFirst();
            block = new Block(name, root);
            root.addSub(block);
        }
        this.blocks.push(block);
    }

    public Block popBlock() {
        if (!this.blocks.isEmpty()) {
            return this.blocks.pop();
        }
        return null;
    }

    public Block current() {
        return this.blocks.getLast();
    }
}
