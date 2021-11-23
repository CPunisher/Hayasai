package com.cpunisher.hayasai.ir.pass;

import com.cpunisher.hayasai.ir.global.SymbolTable;
import com.cpunisher.hayasai.ir.type.Type;
import com.cpunisher.hayasai.ir.value.Block;
import com.cpunisher.hayasai.ir.value.expr.OperandExpression;
import com.cpunisher.hayasai.ir.value.func.FunctionDef;
import com.cpunisher.hayasai.ir.value.operand.Literal;
import com.cpunisher.hayasai.ir.value.operand.VoidOperand;
import com.cpunisher.hayasai.ir.value.stmt.impl.RetStatement;

import java.util.function.Consumer;

public class UndefinedBehavior implements IPass, Consumer<FunctionDef> {
    @Override
    public void pass(SymbolTable module) {
        for (FunctionDef functionDef : module.getFuncDefTable().values()) {
            this.accept(functionDef);
        }
    }

    @Override
    public void accept(FunctionDef functionDef) {
        this.retDefault(functionDef);
    }

    private void retDefault(FunctionDef functionDef) {
        for (Block block : functionDef.getAllBlocks()) {
            if (!block.terminated()) {
                Type funcType = functionDef.getFuncType();
                if (funcType.equals(Type.VOID)) {
                    block.addSub(new RetStatement(new OperandExpression(new VoidOperand())));
                } else if (funcType.equals(Type.INT)) {
                    block.addSub(new RetStatement(new OperandExpression(Literal.INT_ZERO)));
                }
            }
        }
    }
}
