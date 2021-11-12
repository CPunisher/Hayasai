package com.cpunisher.hayasai.frontend;

import com.cpunisher.hayasai.ir.value.Block;
import com.cpunisher.hayasai.ir.value.expr.OperandExpression;

public class ConditionContext {
    private Block parent;
    private Block blockTrue, blockFalse;
    private OperandExpression nextOr;

    public Block getParent() {
        return parent;
    }

    public void setParent(Block parent) {
        this.parent = parent;
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
