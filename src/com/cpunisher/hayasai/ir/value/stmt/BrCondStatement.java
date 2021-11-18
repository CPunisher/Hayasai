package com.cpunisher.hayasai.ir.value.stmt;

import com.cpunisher.hayasai.ir.type.Type;
import com.cpunisher.hayasai.ir.value.Block;
import com.cpunisher.hayasai.ir.value.expr.OperandExpression;
import com.cpunisher.hayasai.util.IrKeywords;

import java.util.StringJoiner;

public class BrCondStatement extends TerminateStatement {
    private final OperandExpression cond;
    private final Block block1;
    private final Block block2;

    public BrCondStatement(OperandExpression cond, Block block1, Block block2, Block cur) {
        assert cond.getOperand().getType() == Type.BIT;

        this.cond = cond;
        this.block1 = block1;
        this.block2 = block2;

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
        joiner.add(cond.generate());
        joiner.add(IrKeywords.SEPARATOR);
        joiner.add(IrKeywords.LABEL);
        joiner.add(this.block1.getBlockRegister().generate());
        joiner.add(IrKeywords.SEPARATOR);
        joiner.add(IrKeywords.LABEL);
        joiner.add(this.block2.getBlockRegister().generate());
        return joiner.toString();
    }
}
