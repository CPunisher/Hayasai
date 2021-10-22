package com.cpunisher.hayasai.ir.value.func;

import com.cpunisher.hayasai.ir.type.Type;
import com.cpunisher.hayasai.ir.value.Block;
import com.cpunisher.hayasai.ir.value.Ident;
import com.cpunisher.hayasai.ir.value.Value;
import com.cpunisher.hayasai.util.IrKeywords;

public final class FunctionDef extends Value {

    private final Type funcType;
    private final Ident ident;
    private final FunctionFParams param;
    private final Block block;

    public FunctionDef(String name, Type funcType, Ident ident, FunctionFParams param, Block block) {
        super(name);
        this.funcType = funcType;
        this.ident = ident;
        this.param = param;
        this.block = block;
    }

    @Override
    public String build() {
        StringBuilder builder = new StringBuilder();
        builder.append(IrKeywords.DEFINE).append(" ");
        builder.append(IrKeywords.DSO_LOCAL).append(" ");
        builder.append(this.funcType.build()).append(" ");
        builder.append(IrKeywords.FUNC_IDENT);
        builder.append(this.ident.build());
        builder.append(this.param.build());
        builder.append(this.block.build());
        return builder.toString();
    }

    public Type getFuncType() {
        return funcType;
    }

    public Ident getIdent() {
        return ident;
    }

    public Block getBlock() {
        return block;
    }
}
