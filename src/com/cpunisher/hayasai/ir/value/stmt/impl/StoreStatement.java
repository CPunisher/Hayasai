package com.cpunisher.hayasai.ir.value.stmt.impl;

import com.cpunisher.hayasai.ir.value.expr.OperandExpression;
import com.cpunisher.hayasai.ir.value.operand.GlobalOperand;
import com.cpunisher.hayasai.ir.value.operand.Operand;
import com.cpunisher.hayasai.ir.value.operand.Register;
import com.cpunisher.hayasai.ir.value.stmt.Statement;
import com.cpunisher.hayasai.util.IrKeywords;

import java.util.Arrays;
import java.util.StringJoiner;

public class StoreStatement extends Statement {
    public StoreStatement(OperandExpression source, Operand addr) {
        assert addr instanceof Register || addr instanceof GlobalOperand;
        this.operands = Arrays.asList(source.getOperand(), addr);
    }

    @Override
    public String generate() {
        StringJoiner joiner = new StringJoiner(" ");
        joiner.add(IrKeywords.STORE);
        joiner.add(this.operands.get(0).getType().generate());
        joiner.add(this.operands.get(0).generate());
        joiner.add(IrKeywords.SEPARATOR);
        joiner.add(this.operands.get(1).getType().generate());
        joiner.add(this.operands.get(1).generate());
        return joiner.toString();
    }

    public Operand getSource() {
        return this.operands.get(0);
    }

    public Operand getAddr() {
        return this.operands.get(1);
    }
}
