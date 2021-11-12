package com.cpunisher.hayasai.util;

import com.cpunisher.hayasai.ir.value.Block;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public final class BlockCfg {
    private final Block block;
    private final List<Block> successorList;

    public BlockCfg(Block block) {
        this.block = block;
        this.successorList = new LinkedList<>();
    }

    public void addSuccessor(Block successor) {
        this.successorList.add(successor);
    }

    public List<Block> getSuccessorList() { return Collections.unmodifiableList(this.successorList); }
}
