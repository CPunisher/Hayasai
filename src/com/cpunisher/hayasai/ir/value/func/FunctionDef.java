package com.cpunisher.hayasai.ir.value.func;

import com.cpunisher.hayasai.ir.type.Type;
import com.cpunisher.hayasai.ir.util.DefaultAllocator;
import com.cpunisher.hayasai.ir.util.IRegisterAllocator;
import com.cpunisher.hayasai.ir.value.Block;
import com.cpunisher.hayasai.ir.value.Ident;
import com.cpunisher.hayasai.ir.value.operand.Register;
import com.cpunisher.hayasai.util.IrKeywords;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public final class FunctionDef extends Function implements IRegisterAllocator {
    private final List<Block> blocks;
    private final IRegisterAllocator allocator;

    public FunctionDef(Type funcType, Ident ident, List<? extends FunctionIdentParam> params) {
        super(funcType, ident, params);
        this.blocks = new LinkedList<>();
        this.allocator = new DefaultAllocator();
    }

    public static FunctionDef createEmpty() {
        return new FunctionDef(Type.VOID, Ident.EMPTY_IDENT, List.of());
    }

    @Override
    public void build() {
        super.build();
        this.blocks.forEach(Block::build);
    }

    @Override
    public String generate() {
        StringBuilder builder = new StringBuilder();
        builder.append(IrKeywords.DEFINE).append(" ");
        builder.append(IrKeywords.DSO_LOCAL).append(" ");
        builder.append(this.funcType.generate()).append(" ");
        builder.append(IrKeywords.GLOBAL_IDENT);
        builder.append(this.ident.generate());
        builder.append(this.params.stream()
                .map(FunctionParam::getArgType)
                .map(Type::generate)
                .collect(Collectors.joining(IrKeywords.SEPARATOR + " ", IrKeywords.LPARENTHESE, IrKeywords.RPARENTHESE)));
        builder.append(" ");
        builder.append(IrKeywords.LCURLY).append(IrKeywords.LINE_SEPARATOR);
        for (Block block : this.blocks) {
            builder.append(block.generate()).append(IrKeywords.LINE_SEPARATOR);
        }
        builder.append(IrKeywords.RCURLY).append(IrKeywords.LINE_SEPARATOR);
        return builder.toString();
    }

    public Block getEntry() {
        return this.blocks.get(0);
    }

    public List<Block> getAllBlocks() {
        return this.blocks;
    }

    public Block getBlockOfRegister(Register register) {
        for (Block block : this.getAllBlocks()) {
            if (block.getBlockRegister() == register) {
                return block;
            }
        }
        throw new RuntimeException("Can't find sub block.");
    }

    public void addBlock(Block block) {
        this.blocks.add(block);
    }

    public Ident genIdent() {
        return this.allocator.genIdent();
    }

    @Override
    public Register alloc() {
        return this.allocator.alloc();
    }

    @Override
    public Register alloc(Type type) {
        return this.allocator.alloc(type);
    }

    public static class FunctionIdentParam extends Function.FunctionParam {
        private final Ident argIdent;

        public FunctionIdentParam(Type argType, Ident argIdent) {
            super(argType);
            this.argIdent = argIdent;
        }

        public Ident getArgIdent() {
            return argIdent;
        }
    }
}
