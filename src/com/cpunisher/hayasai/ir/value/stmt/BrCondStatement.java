package com.cpunisher.hayasai.ir.value.stmt;

import com.cpunisher.hayasai.ir.type.Type;
import com.cpunisher.hayasai.ir.value.Block;
import com.cpunisher.hayasai.ir.value.expr.OperandExpression;
import com.cpunisher.hayasai.util.IrKeywords;

import java.util.StringJoiner;

public class BrCondStatement extends Statement {
    private final OperandExpression cond;
    private final Block block1;
    private final Block block2;

    public BrCondStatement(OperandExpression cond, Block block1, Block block2, Block cur) {
        assert cond.getOperand().getType() == Type.BIT;

        cur.getBlockCfg().addSuccessor(block1);
        cur.getBlockCfg().addSuccessor(block2);
        this.cond = cond;
        this.block1 = block1;
        this.block2 = block2;
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
