package com.cpunisher.hayasai.util;

import com.cpunisher.hayasai.ir.value.Block;

import java.util.*;

public final class BlockCfg {
    private final Block block;
    private final List<BlockCfg> predecessorList;
    private final List<BlockCfg> successorList;
    private final List<BlockCfg> doms;
    private final List<BlockCfg> idoms;
    private final Set<BlockCfg> domFrontiers;
    private BlockCfg idom;

    public BlockCfg(Block block) {
        this.block = block;
        this.predecessorList = new LinkedList<>();
        this.successorList = new LinkedList<>();
        this.doms = new LinkedList<>();
        this.idoms = new LinkedList<>();
        this.domFrontiers = new HashSet<>();
    }

    public void setIdom(BlockCfg idom) {
        this.idom = idom;
    }

    public Block getBlock() {
        return block;
    }

    public List<BlockCfg> getDoms() {
        return doms;
    }

    public List<BlockCfg> getIdoms() {
        return idoms;
    }

    public Set<BlockCfg> getDomFrontiers() {
        return domFrontiers;
    }

    public BlockCfg getIdom() {
        return idom;
    }

    public List<BlockCfg> getPredecessorList() {
        return predecessorList;
    }

    public List<BlockCfg> getSuccessorList() {
        return successorList;
    }

    public void merge(BlockCfg blockCfg) {
        this.successorList.addAll(blockCfg.successorList);
        for (BlockCfg successor : blockCfg.successorList) {
            Collections.replaceAll(successor.predecessorList, blockCfg, this);
        }
    }
}
