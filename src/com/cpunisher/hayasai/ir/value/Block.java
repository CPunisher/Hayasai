package com.cpunisher.hayasai.ir.value;

import com.cpunisher.hayasai.ir.value.func.FunctionDef;
import com.cpunisher.hayasai.ir.value.operand.Register;
import com.cpunisher.hayasai.ir.value.stmt.*;
import com.cpunisher.hayasai.util.BlockCfg;
import com.cpunisher.hayasai.util.IrKeywords;
import com.cpunisher.hayasai.util.SyntaxException;
import org.antlr.v4.runtime.misc.Pair;

import java.util.*;

public final class Block extends Value {

    private final Block parent;
    private final FunctionDef functionDef;
    private final Register register;
    private final List<Statement> subList;
    private final Map<Ident, Register> varTable;
    private final Map<Ident, Register> constTable;

    private final BlockCfg blockCfg;

    public Block(FunctionDef functionDef, Block parent) {
        this.subList = new LinkedList<>();
        this.blockCfg = new BlockCfg(this);
        this.varTable = new HashMap<>();
        this.constTable = new HashMap<>();

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

    public Pair<Register, Boolean> compute(Ident ident) {
        Register register = this.getConst(ident);
        boolean immutable = true;
        if (register == null) {
            register = this.getVar(ident);
            if (register == null)
                throw new SyntaxException("Ident [" + ident.getIdent()  + "] is not declared.");
            immutable = false;
        }
        return new Pair<>(register, immutable);
    }

    public Register getVar(Ident ident) {
        Register register =  this.varTable.get(ident);
        if (register == null && this.hasParent()) {
            register = this.parent.getVar(ident);
        }
        return register;
    }

    public Register getConst(Ident ident) {
        Register register =  this.constTable.get(ident);
        if (register == null && this.hasParent()) {
            register = this.parent.getConst(ident);
        }
        return register;
    }

    public Register putVar(Ident ident) {
        if (this.identExists(ident)) {
            throw new SyntaxException("Ident [" + ident.getIdent() + "] exists.");
        }
        Register register = this.functionDef.alloc();
        this.varTable.put(ident, register);
        return register;
    }

    public Register putConst(Ident ident) {
        if (this.identExists(ident)) {
            throw new SyntaxException("Ident [" + ident.getIdent() + "] exists.");
        }
        Register register = this.functionDef.alloc();
        this.constTable.put(ident, register);
        return register;
    }

    public boolean terminated() {
        if (this.subList.isEmpty()) {
            return false;
        }
        Statement statement = this.subList.get(this.subList.size() - 1);
        return statement instanceof TerminateStatement;
    }

    public void merge(Block block) {
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

    public boolean identExists(Ident ident) {
        return varTable.containsKey(ident) || constTable.containsKey(ident);
    }

    public boolean hasParent() {
        return this.parent != null;
    }

    public Register getBlockRegister() {
        return register;
    }

    public BlockCfg getBlockCfg() {
        return blockCfg;
    }
}
