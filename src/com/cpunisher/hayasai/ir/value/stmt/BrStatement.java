package com.cpunisher.hayasai.ir.value.stmt;

import com.cpunisher.hayasai.ir.value.Block;
import com.cpunisher.hayasai.ir.value.operand.Operand;
import com.cpunisher.hayasai.util.IrKeywords;

import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

public class BrStatement extends TerminateStatement {

    public BrStatement(Block block, Block cur) {
        this.operands = Arrays.asList(block.getBlockRegister());
        if (!cur.terminated()) {
            block.getBlockCfg().getPredecessorList().add(cur.getBlockCfg());
            cur.getBlockCfg().getSuccessorList().add(block.getBlockCfg());
        }
    }

    @Override
    public String generate() {
        StringJoiner joiner = new StringJoiner(" ");
        joiner.add(IrKeywords.BR);
        joiner.add(IrKeywords.LABEL);
        joiner.add(this.operands.get(0).generate());
        return joiner.toString();
    }
}
