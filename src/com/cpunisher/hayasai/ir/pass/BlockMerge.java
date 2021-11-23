package com.cpunisher.hayasai.ir.pass;

import com.cpunisher.hayasai.ir.global.SymbolTable;
import com.cpunisher.hayasai.ir.value.Block;
import com.cpunisher.hayasai.ir.value.func.FunctionDef;
import com.cpunisher.hayasai.util.BlockCfg;

import java.util.*;
import java.util.function.Consumer;

/**
 * 1. 只有一个子块的块合并其子节点
 */
public class BlockMerge implements IPass, Consumer<FunctionDef> {
    private final List<Stack<Block>> singleLink = new ArrayList<>();
    private final Set<BlockCfg> visitSet = new HashSet<>();
    private final Set<Block> validBlock = new HashSet<>();

    @Override
    public void pass(SymbolTable module) {
        for (FunctionDef functionDef : module.getFuncDefTable().values()) {
            this.accept(functionDef);
        }
    }

    public void accept(FunctionDef functionDef) {
        this.singleLink.clear();
        this.visitSet.clear();
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

        // Unused block remove
        this.validBlock.clear();
        this.visit(functionDef.getEntry().getBlockCfg());

        Iterator<Block> iterator = functionDef.getAllBlocks().iterator();
        while (iterator.hasNext()) {
            Block block = iterator.next();
            if (!validBlock.contains(block)) {
                for (BlockCfg successor : block.getBlockCfg().getSuccessorList()) {
                    successor.getPredecessorList().remove(block.getBlockCfg());
                }
                iterator.remove();
            }
        }
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

    private void visit(BlockCfg cur) {
        this.validBlock.add(cur.getBlock());
        for (BlockCfg successor : cur.getSuccessorList()) {
            if (!validBlock.contains(successor.getBlock())) {
                visit(successor);
            }
        }
    }
}
