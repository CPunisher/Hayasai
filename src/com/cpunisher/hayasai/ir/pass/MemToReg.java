package com.cpunisher.hayasai.ir.pass;

import com.cpunisher.hayasai.ir.global.SymbolTable;
import com.cpunisher.hayasai.ir.value.Block;
import com.cpunisher.hayasai.ir.value.func.FunctionDef;
import com.cpunisher.hayasai.ir.value.operand.Operand;
import com.cpunisher.hayasai.ir.value.operand.Register;
import com.cpunisher.hayasai.ir.value.stmt.LoadStatement;
import com.cpunisher.hayasai.ir.value.stmt.PhiStatement;
import com.cpunisher.hayasai.ir.value.stmt.Statement;
import com.cpunisher.hayasai.ir.value.stmt.StoreStatement;
import com.cpunisher.hayasai.util.BlockCfg;

import java.util.*;
import java.util.stream.Collectors;

public class MemToReg implements IPass {
    @Override
    public void pass(SymbolTable module) {
        for (FunctionDef functionDef : module.getFuncDefTable().values()) {
            List<BlockCfg> blocks = new ArrayList<>();
            blocks.add(functionDef.getBlock().getBlockCfg());
            blocks.addAll(functionDef.getBlock().getSubBlockList().stream().map(Block::getBlockCfg).collect(Collectors.toList()));

            this.initDominance(blocks);
            this.initDominanceFrontier(blocks);

            // init defs
            Map<Register, List<BlockCfg>> defs = new HashMap<>();
            for (BlockCfg blockCfg : blocks) {
                for (Statement statement : blockCfg.getBlock().getUnmodifiableSubList()) {
                    if (statement instanceof StoreStatement storeStatement) {
                        List<BlockCfg> list = new ArrayList<>();
                        list.add(blockCfg);
                        defs.merge((Register) storeStatement.getAddr(), list, (e1, e2) -> {
                            e1.addAll(e2);
                            return e1;
                        });
                    }
                }
            }

            // Algorithm 3.1: Standard algorithm for inserting phi - functions
            Map<PhiStatement, Register> phiMap = new HashMap<>();
            for (Register v : defs.keySet()) {
                Set<BlockCfg> phiBlocks = new HashSet<>();
                Queue<BlockCfg> blockToHandle = new LinkedList<>();
                List<BlockCfg> defV = defs.get(v);
                for (BlockCfg blockCfg : defV) {
                    blockToHandle.offer(blockCfg);
                }

                while (!blockToHandle.isEmpty()) {
                    BlockCfg blockCfg = blockToHandle.poll();
                    for (BlockCfg df : blockCfg.getDomFrontiers()) {
                        if (!phiBlocks.contains(df)) {
                            // add v <- phi at entry of df
                            PhiStatement statement = new PhiStatement(df.getBlock().alloc());
                            df.getBlock().addSubToFront(statement);
                            phiMap.put(statement, v);
                            phiBlocks.add(df);
                            if (!defV.contains(df)) {
                                blockToHandle.offer(df);
                            }
                        }
                    }
                }
            }

            // Algorithm 3.3: Renaming algorithm for second phase of SSA construction
            Set<BlockCfg> visitSet = new HashSet<>();
            this.renameDfs(functionDef.getBlock().getBlockCfg(), null, new HashMap<>(), phiMap, visitSet);
        }
    }

    private void renameDfs(BlockCfg cur, BlockCfg prev, Map<Register, Operand> renameMap, Map<PhiStatement, Register> phiMap, Set<BlockCfg> visitSet) {
        Map<Register, Operand> curMap = new HashMap<>(renameMap);
        // update phi
        for (Statement statement : cur.getBlock().getUnmodifiableSubList()) {
            if (statement instanceof PhiStatement phiStatement) {
                Register origin = phiMap.get(phiStatement);
                Operand replace = renameMap.get(origin);
                if (replace != null) {
                    phiStatement.putValue(prev.getBlock(), replace);
                }
            }
        }

        boolean visited = visitSet.contains(cur);
        if (visited) {
            return;
        }

        // handle load, store
        visitSet.add(cur);
        Iterator<Statement> iterator = cur.getBlock().getSubList().iterator();
        while (iterator.hasNext()) {
            Statement statement = iterator.next();
            // TODO type check
            if (statement instanceof LoadStatement loadStatement) {
                if (!(loadStatement.getOperands().get(0) instanceof Register)) continue;
                Operand operand = curMap.get((Register) loadStatement.getOperands().get(0));
                Operand origin = loadStatement.getReceiver();
                for (Operand.Use use : origin.getUses()) {
                    use.getUser().replace(origin, operand);
                }
                operand.clearUse();
                iterator.remove();
            } else if (statement instanceof StoreStatement storeStatement) {
                curMap.put((Register) storeStatement.getAddr(), storeStatement.getSource());
                iterator.remove();
            } else if (statement instanceof PhiStatement phiStatement) {
                curMap.put(phiMap.get(phiStatement), phiStatement.getReceiver());
            }
        }

        for (BlockCfg successor : cur.getSuccessorList()) {
            this.renameDfs(successor, cur, curMap, phiMap, visitSet);
        }
    }

    private void initDominance(List<BlockCfg> blocks) {
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

    private void initDominanceFrontier(List<BlockCfg> blocks) {
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
