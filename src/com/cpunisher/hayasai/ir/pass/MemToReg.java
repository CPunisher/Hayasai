package com.cpunisher.hayasai.ir.pass;

import com.cpunisher.hayasai.ir.global.SymbolTable;
import com.cpunisher.hayasai.ir.value.Block;
import com.cpunisher.hayasai.ir.value.func.FunctionDef;
import com.cpunisher.hayasai.util.BlockCfg;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.stream.Collectors;

public class MemToReg implements IPass {
    @Override
    public void pass(SymbolTable module) {
        for (FunctionDef def : module.getFuncDefTable().values()) {
            this.initDominance(def);
            this.initDominanceFrontier(def);
        }
    }

    private void initDominance(FunctionDef functionDef) {
        // blocks of function
        List<BlockCfg> blocks = new ArrayList<>();
        blocks.add(functionDef.getBlock().getBlockCfg());
        blocks.addAll(functionDef.getBlock().getSubBlockList().stream().map(Block::getBlockCfg).collect(Collectors.toList()));
        int n = blocks.size();

        // FIGURE 9.1 Iterative Solver for Dominance, Page 479
        // TODO FIGURE 9.24 The Modified Iterative Dominator Algorithm, Page 532
        List<BitSet> dom = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            dom.add(new BitSet());
            if (i != 0) {
                // not entry
                dom.get(i).set(0, n);
            } else {
                dom.get(i).set(i);
            }
        }

        boolean changed = true;
        while (changed) {
            changed = false;
            for (int i = 1; i < n; i++) {
                BitSet temp = new BitSet();
                temp.set(0, n);
                for (BlockCfg block : blocks.get(i).getPredecessorList()) {
                    int j = blocks.indexOf(block);
                    temp.and(dom.get(j));
                }
                temp.set(i);
                if (!temp.equals(dom.get(i))) {
                    dom.get(i).clear();
                    dom.get(i).or(temp);
                    changed = true;
                }
            }
        }

        // Build doms based on Bitset
        for (int i = 0; i < n; i ++) {
            BlockCfg blockCfg = blocks.get(i);
            BitSet bitSet = dom.get(i);
            for (int blockIndex = bitSet.nextSetBit(0); blockIndex >= 0; blockIndex = bitSet.nextSetBit(blockIndex + 1)) {
                blockCfg.getDoms().add(blocks.get(blockIndex));
            }
        }

        // compute idom
        for (BlockCfg blockCfg : blocks) {
            for (BlockCfg candidate : blockCfg.getDoms()) {
                if (candidate != blockCfg) {
                    boolean isIdom = true;
                    for (BlockCfg domer : blockCfg.getDoms()) {
                        if (domer != blockCfg && domer != candidate && domer.getDoms().contains(candidate)) {
                            isIdom = false;
                            break;
                        }
                    }

                    if (isIdom) {
                        blockCfg.setIdom(candidate);
                        candidate.getIdoms().add(blockCfg);
                        break;
                    }
                }
            }
        }
    }

    private void initDominanceFrontier(FunctionDef functionDef) {
        // blocks of function
        List<BlockCfg> blocks = new ArrayList<>();
        blocks.add(functionDef.getBlock().getBlockCfg());
        blocks.addAll(functionDef.getBlock().getSubBlockList().stream().map(Block::getBlockCfg).collect(Collectors.toList()));
        int n = blocks.size();

        // Algorithm 3.2: Algorithm for computing the dominance frontier of each CFG node
        for (BlockCfg a : blocks) {
            for (BlockCfg b : a.getSuccessorList()) {
                BlockCfg x = a;
                while (x == b || !b.getDoms().contains(x)) {
                    x.getDomFrontiers().add(b);
                    x = x.getIdom();
                }
            }
        }
    }
}
