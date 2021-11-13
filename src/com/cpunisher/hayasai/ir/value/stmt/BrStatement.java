package com.cpunisher.hayasai.ir.value.stmt;

import com.cpunisher.hayasai.ir.value.Block;
import com.cpunisher.hayasai.ir.value.operand.Operand;
import com.cpunisher.hayasai.util.IrKeywords;

import java.util.List;
import java.util.StringJoiner;

public class BrStatement extends Statement {

    private final Block block;

    public BrStatement(Block block, Block cur) {
        this.block = block;

        block.getBlockCfg().getPredecessorList().add(cur.getBlockCfg());
        cur.getBlockCfg().getSuccessorList().add(block.getBlockCfg());
    }

    @Override
    public String generate() {
        StringJoiner joiner = new StringJoiner(" ");
        joiner.add(IrKeywords.BR);
        joiner.add(IrKeywords.LABEL);
        joiner.add(this.block.getBlockRegister().generate());
        return joiner.toString();
    }

    @Override
    public List<Operand> getOperands() {
        return List.of(this.block.getBlockRegister());
    }
}
