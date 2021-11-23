package com.cpunisher.hayasai.ir.value;

import com.cpunisher.hayasai.ir.global.IVariableTable;
import com.cpunisher.hayasai.ir.global.SymbolTable;
import com.cpunisher.hayasai.ir.global.VariableTable;
import com.cpunisher.hayasai.ir.type.Type;
import com.cpunisher.hayasai.ir.value.expr.OperandExpression;
import com.cpunisher.hayasai.ir.value.func.FunctionDef;
import com.cpunisher.hayasai.ir.value.operand.Literal;
import com.cpunisher.hayasai.ir.value.operand.Operand;
import com.cpunisher.hayasai.ir.value.operand.Register;
import com.cpunisher.hayasai.ir.value.operand.VoidOperand;
import com.cpunisher.hayasai.ir.value.stmt.*;
import com.cpunisher.hayasai.ir.value.stmt.impl.AllocaStatement;
import com.cpunisher.hayasai.ir.value.stmt.impl.RetStatement;
import com.cpunisher.hayasai.util.BlockCfg;
import com.cpunisher.hayasai.util.IrKeywords;
import com.cpunisher.hayasai.util.SyntaxException;
import org.antlr.v4.runtime.misc.Pair;

import java.util.*;

public final class Block extends Value implements IVariableTable<Register, Register> {

    private FunctionDef functionDef;
    private final IVariableTable<Register, Register> parent;
    private final Register register;
    private final List<Statement> subList;
    private final VariableTable<Register, Register> localVars;
    private final BlockCfg blockCfg;

    public Block(FunctionDef functionDef, IVariableTable<Register, Register> parent) {
        this.subList = new LinkedList<>();
        this.blockCfg = new BlockCfg(this);
        this.localVars = new VariableTable<>();

        this.parent = parent;
        this.functionDef = functionDef;
        this.register = functionDef.alloc();
    }

    public Block(FunctionDef functionDef) {
        this(functionDef, functionDef);
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
        this.subList.forEach(Value::build);
    }

    @Override
    public String generate() {
        String blockHeader = "";
        Ident ident = this.register.getIdent();
        if (functionDef.getEntry() != this) {
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
            operand = SymbolTable.INSTANCE.getConst(ident);
            immutable = true;
            if (operand == null) {
                operand = SymbolTable.INSTANCE.getVar(ident);
                immutable = false;
            }
            if (operand == null)
                throw SyntaxException.identNotDeclare(ident.getIdent());
        }
        return new Pair<>(operand, immutable);
    }

    @Override
    public Register getVar(Ident ident) {
        Register register = this.localVars.getVar(ident);
        if (register == null) {
            register = this.parent.getVar(ident);
        }
        return register;
    }

    @Override
    public Register getConst(Ident ident) {
        Register register =  this.localVars.getConst(ident);
        if (register == null) {
            register = this.parent.getConst(ident);
        }
        return register;
    }

    @Override
    public void putVar(Ident ident, Register value) {
        this.localVars.putVar(ident, value);
    }

    public Register putVar(Ident ident, Type type) {
        Register register = this.functionDef.alloc(type.getPointer());
        this.localVars.putVar(ident, register);
        this.addSubToFront(new AllocaStatement(register, type));
        return register;
    }

    @Override
    public void putConst(Ident ident, Register register) {
        this.localVars.putConst(ident, register);
    }

    public Register putConst(Ident ident, Type type) {
        Register register = this.functionDef.alloc(type.getPointer());
        this.localVars.putConst(ident, register);
        this.addSubToFront(new AllocaStatement(register, type));
        return register;
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

    public void setFunctionDef(FunctionDef functionDef) {
        this.functionDef = functionDef;
    }

    public List<Statement> getUnmodifiableSubList() {
        return Collections.unmodifiableList(this.subList);
    }

    public List<Statement> getSubList() {
        return this.subList;
    }

    public IVariableTable<Register, Register> getParent() {
        return parent;
    }

    public Register getBlockRegister() {
        return register;
    }

    public BlockCfg getBlockCfg() {
        return blockCfg;
    }
}
