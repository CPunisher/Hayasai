package com.cpunisher.hayasai.ir.pass;

import com.cpunisher.hayasai.ir.global.SymbolTable;
import com.cpunisher.hayasai.ir.value.Block;
import com.cpunisher.hayasai.ir.value.func.FunctionDef;
import com.cpunisher.hayasai.ir.value.operand.Operand;
import com.cpunisher.hayasai.ir.value.stmt.Statement;

import java.util.List;

public class UseGenerator implements IPass {
    @Override
    public void pass(SymbolTable module) {
        for (FunctionDef def : module.getFuncDefTable().values()) {
            this.genUse(def.getBlock());
        }
    }

    private void genUse(Block root) {
        for (Statement statement : root.getUnmodifiableSubList()) {
            List<Operand> operands = statement.getOperands();
            for (int i = 0; i < operands.size(); i++) {
                operands.get(i).addUser(new Operand.Use(statement, i));
            }
        }
        for (Block block : root.getSubBlockList()) {
            genUse(block);
        }
    }
}
