package com.cpunisher.hayasai.ir.value.stmt;

import com.cpunisher.hayasai.ir.value.Block;
import com.cpunisher.hayasai.util.IrKeywords;

import java.util.StringJoiner;

public class BrStatement extends Statement {

    private final Block block;

    public BrStatement(Block block, Block cur) {
        cur.getBlockCfg().addSuccessor(block);
        this.block = block;
    }

    @Override
    public String generate() {
        StringJoiner joiner = new StringJoiner(" ");
        joiner.add(IrKeywords.BR);
        joiner.add(IrKeywords.LABEL);
        joiner.add(this.block.getBlockRegister().generate());
        return joiner.toString();
    }
}
