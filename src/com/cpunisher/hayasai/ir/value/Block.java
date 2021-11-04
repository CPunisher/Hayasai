package com.cpunisher.hayasai.ir.value;

import com.cpunisher.hayasai.ir.type.Type;
import com.cpunisher.hayasai.ir.util.DefaultAllocator;
import com.cpunisher.hayasai.ir.util.IRegisterAllocator;
import com.cpunisher.hayasai.ir.value.operand.Register;
import com.cpunisher.hayasai.ir.value.stmt.AllocaStatement;
import com.cpunisher.hayasai.ir.value.stmt.Statement;
import com.cpunisher.hayasai.util.IrKeywords;
import com.cpunisher.hayasai.util.SyntaxException;
import org.antlr.v4.runtime.misc.Pair;

import java.util.*;

public final class Block extends Value implements IRegisterAllocator {

    private final Block parent;
    private final Register register;
    private final List<Statement> subList;
    private final List<Block> subBlockList;
    private final IRegisterAllocator allocator;
    private final Map<Ident, Register> varTable;
    private final Map<Ident, Register> constTable;

    public Block(String name, Block parent) {
        super(name);
        this.subList = new LinkedList<>();
        this.subBlockList = new LinkedList<>();
        this.varTable = new HashMap<>();
        this.constTable = new HashMap<>();

        this.parent = parent;
        this.allocator = parent.allocator;
        this.register = this.alloc();
    }

    public Block(String name) {
        super(name);
        this.subList = new LinkedList<>();
        this.subBlockList = new LinkedList<>();
        this.varTable = new HashMap<>();
        this.constTable = new HashMap<>();

        this.parent = null;
        this.allocator = new DefaultAllocator();
        this.register = this.alloc();
    }

    public void addSub(Statement sub) {
        if (sub != null)
            this.subList.add(sub);
    }

    public void addBlock(Block block) {
        if (block != null)
            this.subBlockList.add(block);
    }

    @Override
    public String build() {
        String blockHeader = "";
        Ident ident = this.register.getIdent();
        if (this.hasParent()) {
            blockHeader += ident.build();
            blockHeader += IrKeywords.COLON;
            blockHeader += IrKeywords.LINE_SEPARATOR;
        }
        StringJoiner joiner = new StringJoiner(IrKeywords.LINE_SEPARATOR);
        this.constTable.values().stream()
                .map(v -> new AllocaStatement(v, v.getType()))
                .forEach(stmt -> this.subList.add(0, stmt));
        this.varTable.values().stream()
                .map(v -> new AllocaStatement(v, v.getType()))
                .forEach(stmt -> this.subList.add(0, stmt));
        for (Statement stmt : this.subList) {
            joiner.add(IrKeywords.TAB_IDENT + stmt.build());
        }

        for (Block block : this.subBlockList) {
            joiner.add(block.build());
        }
        return blockHeader + joiner;
    }

    public Register getRegister(Ident ident) {
        Register register =  this.getVar(ident);
        if (register == null) {
            register = this.getConst(ident);
        }
        return register;
    }

    public Pair<Register, Boolean> compute(Ident ident) {
        Register register = this.getConst(ident);
        boolean immutable = true;
        if (register == null) {
            register = this.getVar(ident);
            if (register == null)
                throw new SyntaxException("Ident [" + ident  + "] is not declared.");
            immutable = false;
        }
        return new Pair<>(register, immutable);
    }

    public Register getVar(Ident ident) {
        Register register =  this.varTable.get(ident);
        if (register == null) {
            register = this.parent.getVar(ident);
        }
        return register;
    }

    public Register getConst(Ident ident) {
        Register register =  this.constTable.get(ident);
        if (register == null) {
            register = this.parent.getConst(ident);
        }
        return register;
    }

    public Register putVar(Ident ident) {
        if (this.identExists(ident)) {
            throw new SyntaxException("Ident [" + ident.getIdent() + "] exists.");
        }
        Register register = this.alloc();
        this.varTable.put(ident, register);
        return register;
    }

    public Register putConst(Ident ident) {
        if (this.identExists(ident)) {
            throw new SyntaxException("Ident [" + ident.getIdent() + "] exists.");
        }
        Register register = this.alloc();
        this.constTable.put(ident, register);
        return register;
    }

    public boolean identExists(Ident ident) {
        return varTable.containsKey(ident) || constTable.containsKey(ident);
    }

    @Override
    public Register alloc() {
        return this.allocator.alloc();
    }

    @Override
    public Register alloc(Type type) {
        return this.allocator.alloc(type);
    }

    @Override
    public Ident genIdent() {
        return this.allocator.genIdent();
    }

    public boolean hasParent() {
        return this.parent != null;
    }

    public Register getRegister() {
        return register;
    }
}
