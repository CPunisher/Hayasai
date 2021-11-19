package com.cpunisher.hayasai.ir.pass;

import com.cpunisher.hayasai.ir.global.SymbolTable;
import com.cpunisher.hayasai.ir.value.Block;
import com.cpunisher.hayasai.ir.value.func.FunctionDef;
import com.cpunisher.hayasai.util.BlockCfg;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class DeadCodeRemove implements IPass {

    private final Set<Block> validBlock = new HashSet<>();

    @Override
    public void pass(SymbolTable module) {
        for (FunctionDef def : module.getFuncDefTable().values()) {
            this.validBlock.clear();
            this.visit(def.getEntry().getBlockCfg());

            def.getAllBlocks().removeIf(block -> !validBlock.contains(block));
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
