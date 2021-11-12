package com.cpunisher.hayasai.ir.pass;

import com.cpunisher.hayasai.ir.global.SymbolTable;
import com.cpunisher.hayasai.ir.value.Block;
import com.cpunisher.hayasai.ir.value.func.FunctionDef;
import com.cpunisher.hayasai.ir.value.stmt.Statement;

public class UseGenerator implements IPass {
    @Override
    public void pass(SymbolTable module) {
        for (FunctionDef def : module.getFuncDefTable().values()) {
            this.genUse(def.getBlock());
        }
    }

    private void genUse(Block root) {
        for (Statement statement : root.getSubList()) {
            statement.getOperands().forEach(operand -> operand.addUser(statement));
        }
        for (Block block : root.getSubBlockList()) {
            genUse(block);
        }
    }
}
