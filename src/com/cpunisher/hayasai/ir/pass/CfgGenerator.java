package com.cpunisher.hayasai.ir.pass;

import com.cpunisher.hayasai.ir.global.SymbolTable;
import com.cpunisher.hayasai.ir.value.Block;
import com.cpunisher.hayasai.ir.value.func.FunctionDef;
import com.cpunisher.hayasai.ir.value.operand.Register;
import com.cpunisher.hayasai.ir.value.stmt.Statement;
import com.cpunisher.hayasai.ir.value.stmt.impl.BrCondStatement;
import com.cpunisher.hayasai.ir.value.stmt.impl.BrStatement;

import java.util.function.Consumer;

public class CfgGenerator implements IPass, Consumer<FunctionDef> {
    @Override
    public void pass(SymbolTable module) {
        for (FunctionDef functionDef : module.getFuncDefTable().values()) {
            this.accept(functionDef);
        }
    }

    public void accept(FunctionDef functionDef) {
        for (Block block : functionDef.getAllBlocks()) {
            for (Statement statement : block.getUnmodifiableSubList()) {
                if (statement instanceof BrStatement) {
                    linkBlock(block, functionDef.getBlockOfRegister((Register) statement.getOperands().get(0)));
                } else if (statement instanceof BrCondStatement) {
                    linkBlock(block, functionDef.getBlockOfRegister((Register) statement.getOperands().get(1)));
                    linkBlock(block, functionDef.getBlockOfRegister((Register) statement.getOperands().get(2)));
                }
            }
        }
    }

    private static void linkBlock(Block pred, Block suc) {
        suc.getBlockCfg().getPredecessorList().add(pred.getBlockCfg());
        pred.getBlockCfg().getSuccessorList().add(suc.getBlockCfg());
    }
}
