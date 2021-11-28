package com.cpunisher.hayasai.ir.pass;

import com.cpunisher.hayasai.ir.global.SymbolTable;
import com.cpunisher.hayasai.ir.value.Block;
import com.cpunisher.hayasai.ir.value.func.FunctionDef;
import com.cpunisher.hayasai.ir.value.operand.Operand;
import com.cpunisher.hayasai.ir.value.stmt.Statement;

import java.util.List;
import java.util.function.Consumer;

public class UseGenerator implements IPass, Consumer<FunctionDef> {
    @Override
    public void pass(SymbolTable module) {
        this.clearUse(module.getGlobalFunc().getEntry());
        this.genUse(module.getGlobalFunc().getEntry());
        for (FunctionDef def : module.getFuncDefTable().values()) {
            this.accept(def);
        }
    }

    @Override
    public void accept(FunctionDef functionDef) {
        for (Block block : functionDef.getAllBlocks()) {
//            this.clearUse(block);
        }

        for (Block block : functionDef.getAllBlocks()) {
            this.genUse(block);
        }
    }

    private void clearUse(Block block) {
        for (Statement statement : block.getUnmodifiableSubList()) {
            List<Operand> operands = statement.getOperands();
            for (Operand operand : operands) {
                operand.clearUse();
            }
        }
    }

    private void genUse(Block block) {
        for (Statement statement : block.getUnmodifiableSubList()) {
            List<Operand> operands = statement.getOperands();
            for (Operand operand : operands) {
                operand.addUser(new Operand.Use(statement));
            }
        }
    }
}
