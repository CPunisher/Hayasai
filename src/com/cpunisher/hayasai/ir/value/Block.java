package com.cpunisher.hayasai.ir.value;

import com.cpunisher.hayasai.ir.type.Type;
import com.cpunisher.hayasai.ir.util.DefaultAllocator;
import com.cpunisher.hayasai.ir.util.IRegisterAllocator;
import com.cpunisher.hayasai.ir.value.operand.Register;
import com.cpunisher.hayasai.ir.value.stmt.*;
import com.cpunisher.hayasai.util.IrKeywords;
import com.cpunisher.hayasai.util.SyntaxException;
import org.antlr.v4.runtime.misc.Pair;

import java.util.*;

public final class Block extends Value implements IRegisterAllocator {

    private final Block parent;
    private final Register register;
    private final List<Statement> subList;
    private final List<Block> subBlockList;
    private final List<Block> successorList;
    private final IRegisterAllocator allocator;
    private final Map<Ident, Register> varTable;
    private final Map<Ident, Register> constTable;

    private Block next;

    public Block(String name, Block parent) {
        super(name);
        this.subList = new LinkedList<>();
        this.subBlockList = new LinkedList<>();
        this.successorList = new LinkedList<>();
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
        this.successorList = new LinkedList<>();
        this.varTable = new HashMap<>();
        this.constTable = new HashMap<>();

        this.parent = null;
        this.allocator = new DefaultAllocator();
        this.register = this.alloc();
    }

    public void addSub(Value sub) {
        if (sub != null) {
            if (sub instanceof Block) {
                this.subBlockList.add((Block) sub);
            } else if (sub instanceof Statement) {
                this.subList.add((Statement) sub);
            }
        }
    }

    public void addSuccessor(Block successor) {
        this.successorList.add(successor);
    }

    @Override
    public void build() {
        this.register.build();
        for (Register constRegister : this.constTable.values()) {
            this.subList.add(0, new AllocaStatement(constRegister, constRegister.getType()));
        }
        for (Register varRegister : this.varTable.values()) {
            this.subList.add(0, new AllocaStatement(varRegister, varRegister.getType()));
        }
        this.subList.forEach(Value::build);
        this.subBlockList.forEach(Value::build);
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
            if (stmt instanceof RetStatement || stmt instanceof BrCondStatement || stmt instanceof BrStatement) {
                break;
            }
        }

        for (Block block : this.subBlockList) {
            joiner.add(block.generate());
        }
        return blockHeader + joiner;
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

    public boolean hasNext() {
        return this.next != null;
    }

    public void setNext(Block next) {
        this.next = next;
    }

    public Block getNext() {
        return next;
    }

    @Override
    public Ident genIdent() {
        return this.allocator.genIdent();
    }

    public boolean hasParent() {
        return this.parent != null;
    }

    public Register getBlockRegister() {
        return register;
    }
}
