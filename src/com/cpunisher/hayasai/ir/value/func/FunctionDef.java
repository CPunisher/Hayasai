package com.cpunisher.hayasai.ir.value.func;

import com.cpunisher.hayasai.ir.global.IVariableTable;
import com.cpunisher.hayasai.ir.global.SymbolTable;
import com.cpunisher.hayasai.ir.global.VariableTable;
import com.cpunisher.hayasai.ir.type.Type;
import com.cpunisher.hayasai.ir.util.DefaultAllocator;
import com.cpunisher.hayasai.ir.util.IRegisterAllocator;
import com.cpunisher.hayasai.ir.value.Block;
import com.cpunisher.hayasai.ir.value.Ident;
import com.cpunisher.hayasai.ir.value.operand.Register;
import com.cpunisher.hayasai.util.IrKeywords;

import java.util.*;
import java.util.stream.Collectors;

public final class FunctionDef extends Function implements IRegisterAllocator, IVariableTable<Register, Register> {
    private final List<Block> blocks;
    private final VariableTable<Register, Register> paramVars;
    private final DefaultAllocator allocator;

    public FunctionDef(Type funcType, Ident ident, List<? extends FunctionIdentParam> params) {
        super(funcType, ident, params);
        this.blocks = new LinkedList<>();
        this.allocator = new DefaultAllocator();
        this.paramVars = new VariableTable<>();
    }

    public static FunctionDef createEmpty() {
        return new FunctionDef(Type.VOID, Ident.EMPTY_IDENT, List.of());
    }

    public void merge(FunctionDef functionDef) {
        for (Block block : functionDef.blocks) {
            block.setFunctionDef(this);
        }
        this.allocator.merge(functionDef.allocator);
    }

    @Override
    public void build() {
        super.build();
        this.paramVars.getVarTable().values().forEach(Register::build);
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
                .map(FunctionParam::generate)
                .collect(Collectors.joining(IrKeywords.SEPARATOR + " ", IrKeywords.LPARENTHESE, IrKeywords.RPARENTHESE)));
        builder.append(" ");
        builder.append(IrKeywords.LCURLY).append(IrKeywords.LINE_SEPARATOR);
        for (Block block : this.blocks) {
            builder.append(block.generate()).append(IrKeywords.LINE_SEPARATOR);
        }
        builder.append(IrKeywords.RCURLY).append(IrKeywords.LINE_SEPARATOR);
        return builder.toString();
    }

    @Override
    public Register getVar(Ident ident) {
        return this.paramVars.getVar(ident);
    }

    @Override
    public Register getConst(Ident ident) {
        return this.paramVars.getConst(ident);
    }

    @Override
    public void putVar(Ident ident, Register value) {
        this.paramVars.putVar(ident, value);
    }

    @Override
    public void putConst(Ident ident, Register constValue) {
        this.paramVars.putConst(ident, constValue);
    }

    public Set<Map.Entry<Ident, Register>> getVarEntriesSet() {
        return this.paramVars.getVarTable().entrySet();
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
        private Register register;

        public FunctionIdentParam(Type argType, Ident argIdent) {
            super(argType);
            this.argIdent = argIdent;
        }

        public Ident getArgIdent() {
            return argIdent;
        }

        public Register getRegister() {
            return register;
        }

        public void setRegister(Register register) {
            this.register = register;
        }

        @Override
        public void build() {
            this.register.build();
        }

        @Override
        public String generate() {
            return this.getArgType().generate() + " " + this.register.generate();
        }
    }
}
