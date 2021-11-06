package com.cpunisher.hayasai.ir.value.func;

import com.cpunisher.hayasai.ir.type.Type;
import com.cpunisher.hayasai.ir.value.Block;
import com.cpunisher.hayasai.ir.value.Ident;
import com.cpunisher.hayasai.util.IrKeywords;

public final class FunctionDef extends Function {

    private final Block block;

    public FunctionDef(Type funcType, Ident ident, FunctionParams param, Block block) {
        super(funcType, ident, param);
        this.block = block;
    }

    @Override
    public void build() {
        super.build();
        this.block.build();
    }

    @Override
    public String generate() {
        StringBuilder builder = new StringBuilder();
        builder.append(IrKeywords.DEFINE).append(" ");
        builder.append(IrKeywords.DSO_LOCAL).append(" ");
        builder.append(this.funcType.generate()).append(" ");
        builder.append(IrKeywords.FUNC_IDENT);
        builder.append(this.ident.generate());
        builder.append(this.param.generate());
        builder.append(" ");
        builder.append(IrKeywords.LCURLY).append(IrKeywords.LINE_SEPARATOR);
        builder.append(this.block.generate());
        builder.append(IrKeywords.LINE_SEPARATOR).append(IrKeywords.RCURLY).append(IrKeywords.LINE_SEPARATOR);
        return builder.toString();
    }

    public Block getBlock() {
        return block;
    }
}
