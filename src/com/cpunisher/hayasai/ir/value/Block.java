package com.cpunisher.hayasai.ir.value;

import com.cpunisher.hayasai.ir.global.IVariableTable;
import com.cpunisher.hayasai.ir.global.SymbolTable;
import com.cpunisher.hayasai.ir.global.VariableTable;
import com.cpunisher.hayasai.ir.value.func.FunctionDef;
import com.cpunisher.hayasai.ir.value.operand.Literal;
import com.cpunisher.hayasai.ir.value.operand.Operand;
import com.cpunisher.hayasai.ir.value.operand.Register;
import com.cpunisher.hayasai.ir.value.stmt.*;
import com.cpunisher.hayasai.util.BlockCfg;
import com.cpunisher.hayasai.util.IrKeywords;
import com.cpunisher.hayasai.util.SyntaxException;
import org.antlr.v4.runtime.misc.Pair;

import java.util.*;

public final class Block extends Value implements IVariableTable<Register, Literal> {

    private final Block parent;
    private final FunctionDef functionDef;
    private final Register register;
    private final List<Statement> subList;
    private final VariableTable<Register, Literal> localVars;
    private final BlockCfg blockCfg;

    public Block(FunctionDef functionDef, Block parent) {
        this.subList = new LinkedList<>();
        this.blockCfg = new BlockCfg(this);
        this.localVars = new VariableTable<>();

        this.parent = parent;
        this.functionDef = functionDef;
        this.register = functionDef.alloc();
    }

    public Block(FunctionDef functionDef) {
        this(functionDef, null);
    }

    public void addSubToFront(Statement sub) {
        if (sub != null) {
            this.subList.add(0, sub);
        }
    }

    public void addSub(Statement sub) {
        if (sub != null && !this.terminated()) {
            this.subList.add(sub);
        }
    }

    @Override
    public void build() {
        this.register.build();
//        for (Register constRegister : this.constTable.values()) {
//            this.subList.add(0, new AllocaStatement(constRegister, constRegister.getType()));
//        }
//        for (Register varRegister : this.varTable.values()) {
//            this.subList.add(0, new AllocaStatement(varRegister, varRegister.getType()));
//        }
        this.subList.forEach(Value::build);
    }

    @Override
    public String generate() {
        String blockHeader = "";
        Ident ident = this.register.getIdent();
        if (this.hasParent()) {
            blockHeader += ident.generate();
            blockHeader += IrKeywords.COLON;
            blockHeader += IrKeywords.LINE_SEPARATOR;
        }
        StringJoiner joiner = new StringJoiner(IrKeywords.LINE_SEPARATOR);
        for (Statement stmt : this.subList) {
            joiner.add(IrKeywords.TAB_IDENT + stmt.generate());
            if (stmt instanceof TerminateStatement) {
                break;
            }
        }

        return blockHeader + joiner;
    }

    public Pair<Operand, Boolean> compute(Ident ident) {
        Operand operand = this.getConst(ident);
        boolean immutable = true;
        if (operand == null) {
            operand = this.getVar(ident);
            immutable = false;
        }

        if (operand == null) {
            operand = SymbolTable.INSTANCE.getGlobalVars().getConst(ident);
            immutable = true;
            if (operand == null) {
                operand = SymbolTable.INSTANCE.getGlobalVars().getVar(ident);
                immutable = false;
            }
            if (operand == null)
                throw new SyntaxException("Ident [" + ident.getIdent()  + "] is not declared.");
        }
        return new Pair<>(operand, immutable);
    }

    @Override
    public Register getVar(Ident ident) {
        Register register = this.localVars.getVar(ident);
        if (register == null && this.hasParent()) {
            register = this.parent.getVar(ident);
        }
        return register;
    }

    @Override
    public Literal getConst(Ident ident) {
        Literal value =  this.localVars.getConst(ident);
        if (value == null && this.hasParent()) {
            value = this.parent.getConst(ident);
        }
        return value;
    }

    @Override
    public void putVar(Ident ident, Register value) {
        this.localVars.putVar(ident, value);
    }

    public Register putVar(Ident ident) {
        Register register = this.functionDef.alloc();
        this.localVars.putVar(ident, register);
        return register;
    }

    @Override
    public void putConst(Ident ident, Literal constValue) {
        this.localVars.putConst(ident, constValue);
    }

    public boolean terminated() {
        if (this.subList.isEmpty()) {
            return false;
        }
        Statement statement = this.subList.get(this.subList.size() - 1);
        return statement instanceof TerminateStatement;
    }

    public void mergeTable(Block block) {
        this.localVars.merge(block.localVars);
    }

    public void mergeIr(Block block) {
        if (this.terminated()) {
            this.subList.remove(this.subList.size() - 1);
        }
        this.subList.addAll(block.getSubList());
        this.blockCfg.merge(block.getBlockCfg());
    }

    public List<Statement> getUnmodifiableSubList() {
        return Collections.unmodifiableList(this.subList);
    }

    public List<Statement> getSubList() {
        return this.subList;
    }

    public boolean hasParent() {
        return this.parent != null;
    }

    public Block getParent() {
        return parent;
    }

    public Register getBlockRegister() {
        return register;
    }

    public BlockCfg getBlockCfg() {
        return blockCfg;
    }
}
