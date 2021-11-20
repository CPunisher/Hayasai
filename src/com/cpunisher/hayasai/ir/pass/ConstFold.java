package com.cpunisher.hayasai.ir.pass;

import com.cpunisher.hayasai.ir.global.SymbolTable;
import com.cpunisher.hayasai.ir.value.Block;
import com.cpunisher.hayasai.ir.value.func.FunctionDef;
import com.cpunisher.hayasai.ir.value.operand.Literal;
import com.cpunisher.hayasai.ir.value.operand.Operand;
import com.cpunisher.hayasai.ir.value.operand.Register;
import com.cpunisher.hayasai.ir.value.stmt.Statement;
import com.cpunisher.hayasai.ir.value.stmt.impl.BinaryOperationStatement;
import com.cpunisher.hayasai.ir.value.stmt.impl.LoadStatement;

import java.util.Iterator;

public class ConstFold implements IPass {
    @Override
    public void pass(SymbolTable module) {
        this.constFold(module.getGlobalFunc());
        for (FunctionDef functionDef : module.getFuncDefTable().values()) {
            this.constFold(functionDef);
        }
    }

    private void constFold(FunctionDef functionDef) {
        Block block = functionDef.getEntry();
        Iterator<Statement> iterator = block.getSubList().iterator();
        while (iterator.hasNext()) {
            Statement statement = iterator.next();
            if (statement instanceof LoadStatement loadStatement) {
                if (loadStatement.getOperands().get(0).canCompute()) {
                    int newValue = loadStatement.getOperands().get(0).getComputedValue();
                    loadStatement.getReceiver().replaceUser(new Literal(newValue));
                    iterator.remove();
                }
            } else if (statement instanceof BinaryOperationStatement binaryOperationStatement) {
                Operand operand1 = binaryOperationStatement.getOperands().get(0);
                Operand operand2 = binaryOperationStatement.getOperands().get(1);
                if (operand1.canCompute() && operand2.canCompute()) {
                    int newValue = binaryOperationStatement.getOperator().apply(operand1.getComputedValue(), operand2.getComputedValue());
                    binaryOperationStatement.getReceiver().replaceUser(new Literal(newValue));
                    iterator.remove();
                }
            }
        }
    }
}
