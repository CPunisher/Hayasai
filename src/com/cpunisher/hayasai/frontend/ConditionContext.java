package com.cpunisher.hayasai.frontend;

import com.cpunisher.hayasai.ir.value.Block;
import com.cpunisher.hayasai.ir.value.expr.OperandExpression;

public class ConditionContext {
    private boolean enable;
    private Block blockTrue, blockFalse;
    private OperandExpression nextOr;

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public Block getBlockTrue() {
        return blockTrue;
    }

    public void setBlockTrue(Block blockTrue) {
        this.blockTrue = blockTrue;
    }

    public Block getBlockFalse() {
        return blockFalse;
    }

    public void setBlockFalse(Block blockFalse) {
        this.blockFalse = blockFalse;
    }

    public OperandExpression getNextOr() {
        return nextOr;
    }

    public void setNextOr(OperandExpression nextOr) {
        this.nextOr = nextOr;
    }
}
