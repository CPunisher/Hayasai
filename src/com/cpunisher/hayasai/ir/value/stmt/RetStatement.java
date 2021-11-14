package com.cpunisher.hayasai.ir.value.stmt;

import com.cpunisher.hayasai.ir.value.Value;
import com.cpunisher.hayasai.ir.value.expr.OperandExpression;
import com.cpunisher.hayasai.ir.value.operand.Operand;
import com.cpunisher.hayasai.util.IrKeywords;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;

public final class RetStatement extends Statement {


    public RetStatement(OperandExpression expression) {
        this.operands = Arrays.asList(expression.getOperand());
    }

    @Override
    public String generate() {
        StringJoiner joiner = new StringJoiner(" ");
        joiner.add(IrKeywords.RETURN);

        Operand operand = this.operands.get(0);
        if (operand != null) {
            joiner.add(operand.getType().generate());
            joiner.add(operand.generate());
        }
        return joiner.toString();
    }
}
