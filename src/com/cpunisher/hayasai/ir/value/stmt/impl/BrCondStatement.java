package com.cpunisher.hayasai.ir.value.stmt.impl;

import com.cpunisher.hayasai.ir.type.Type;
import com.cpunisher.hayasai.ir.value.Block;
import com.cpunisher.hayasai.ir.value.expr.OperandExpression;
import com.cpunisher.hayasai.ir.value.stmt.TerminateStatement;
import com.cpunisher.hayasai.util.IrKeywords;

import java.util.Arrays;
import java.util.StringJoiner;

public class BrCondStatement extends TerminateStatement {
    public BrCondStatement(OperandExpression cond, Block block1, Block block2, Block cur) {
        assert cond.getOperand().getType() == Type.BIT;

        this.operands = Arrays.asList(cond.getOperand(), block1.getBlockRegister(), block2.getBlockRegister());
        if (!cur.terminated()) {
            cur.getBlockCfg().getSuccessorList().add(block1.getBlockCfg());
            cur.getBlockCfg().getSuccessorList().add(block2.getBlockCfg());
            block1.getBlockCfg().getPredecessorList().add(cur.getBlockCfg());
            block2.getBlockCfg().getPredecessorList().add(cur.getBlockCfg());
        }
    }

    @Override
    public String generate() {
        StringJoiner joiner = new StringJoiner(" ");
        joiner.add(IrKeywords.BR);
        joiner.add(this.operands.get(0).getType().generate());
        joiner.add(this.operands.get(0).generate());
        joiner.add(IrKeywords.SEPARATOR);
        joiner.add(IrKeywords.LABEL);
        joiner.add(this.operands.get(1).generate());
        joiner.add(IrKeywords.SEPARATOR);
        joiner.add(IrKeywords.LABEL);
        joiner.add(this.operands.get(2).generate());
        return joiner.toString();
    }
}
