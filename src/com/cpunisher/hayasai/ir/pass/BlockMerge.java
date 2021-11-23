package com.cpunisher.hayasai.ir.pass;

import com.cpunisher.hayasai.ir.global.SymbolTable;
import com.cpunisher.hayasai.ir.value.Block;
import com.cpunisher.hayasai.ir.value.func.FunctionDef;
import com.cpunisher.hayasai.util.BlockCfg;

import java.util.*;

/**
 * 1. 只有一个子块的块合并其子节点
 */
public class BlockMerge implements IPass {
    private final List<Stack<Block>> singleLink = new ArrayList<>();
    private final Set<BlockCfg> visitSet = new HashSet<>();

    @Override
    public void pass(SymbolTable module) {
        for (FunctionDef functionDef : module.getFuncDefTable().values()) {
            this.singleLink.clear();
            this.visitSet.clear();
            this.blockMerge(functionDef);
        }
    }

    private void blockMerge(FunctionDef functionDef) {
        this.findSingleLink(functionDef.getEntry().getBlockCfg(), null);
        for (Stack<Block> blockStack : singleLink) {
            while (blockStack.size() > 1) {
                Block merged = blockStack.pop();
                Block toMerge = blockStack.pop();
                merged.getBlockCfg().getPredecessorList().clear();
                toMerge.getBlockCfg().getSuccessorList().clear();
                toMerge.mergeIr(merged);
                blockStack.push(toMerge);
                functionDef.getAllBlocks().remove(merged);
            }
        }

        Iterator<Block> iterator = functionDef.getAllBlocks().iterator();
    }

    private void findSingleLink(BlockCfg cur, Stack<Block> linkStack) {
        this.visitSet.add(cur);
        if (cur.getSuccessorList().size() == 1) {
            BlockCfg successor = cur.getSuccessorList().get(0);
            if (successor.getPredecessorList().size() == 1) {
                if (linkStack == null) {
                    linkStack = new Stack<>();
                    linkStack.push(cur.getBlock());
                    this.singleLink.add(linkStack);
                }
                linkStack.push(successor.getBlock());
            }
        } else {
            linkStack = null;
        }

        for (BlockCfg successor : cur.getSuccessorList()) {
            if (!this.visitSet.contains(successor))
                this.findSingleLink(successor, linkStack);
        }
    }
}
