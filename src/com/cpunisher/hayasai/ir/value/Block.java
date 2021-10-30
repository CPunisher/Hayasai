package com.cpunisher.hayasai.ir.value;

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

    private final List<Statement> subList;
    private final IRegisterAllocator allocator;
    private final Map<Ident, Register> varTable;
    private final Map<Ident, Register> constTable;

    public Block(String name) {
        super(name);
        this.subList = new LinkedList<>();
        this.allocator = new DefaultAllocator();
        this.varTable = new HashMap<>();
        this.constTable = new HashMap<>();
    }

    public void addSub(Statement sub) {
        if (sub != null)
            this.subList.add(sub);
    }

    @Override
    public String build() {
        this.release();
        StringJoiner joiner = new StringJoiner(IrKeywords.LINE_SEPARATOR,
                IrKeywords.LCURLY + IrKeywords.LINE_SEPARATOR,
                IrKeywords.LINE_SEPARATOR + IrKeywords.RCURLY + IrKeywords.LINE_SEPARATOR);
        this.constTable.entrySet().stream()
                .map(entry -> new AllocaStatement(entry.getValue(), entry.getValue().getType()))
                .forEach(stmt -> this.subList.add(0, stmt));
        this.varTable.entrySet().stream()
                .map(entry -> new AllocaStatement( entry.getValue(), entry.getValue().getType()))
                .forEach(stmt -> this.subList.add(0, stmt));
        for (Statement stmt : this.subList) {
            joiner.add("    " + stmt.build());
        }
        return joiner.toString();
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
        return this.varTable.get(ident);
    }

    public Register getConst(Ident ident) {
        return this.constTable.get(ident);
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
    public Ident genIdent() {
        return this.allocator.genIdent();
    }

    @Override
    public void release() {

    }
}
