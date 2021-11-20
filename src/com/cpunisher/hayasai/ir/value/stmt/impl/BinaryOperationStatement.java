package com.cpunisher.hayasai.ir.value.stmt.impl;

import com.cpunisher.hayasai.ir.util.BinaryOperator;
import com.cpunisher.hayasai.ir.value.operand.Operand;
import com.cpunisher.hayasai.ir.value.operand.Register;
import com.cpunisher.hayasai.ir.value.stmt.ReceiverStatement;
import com.cpunisher.hayasai.ir.value.stmt.Statement;
import com.cpunisher.hayasai.util.IrKeywords;

import java.util.Arrays;
import java.util.StringJoiner;

public class BinaryOperationStatement extends ReceiverStatement {

    private final BinaryOperator operator;

    public BinaryOperationStatement(Register receiver, Operand operand1, Operand operand2, BinaryOperator operator) {
        super(receiver);
        assert receiver.getType() == operand1.getType();
        assert receiver.getType() == operand2.getType();
        assert operand1.getType() == operand2.getType();

        this.operator = operator;
        this.operands = Arrays.asList(operand1, operand2);
        this.setReceiverType();
    }

    @Override
    public String generate() {
        StringJoiner joiner = new StringJoiner(" ");
        joiner.add(this.receiver.generate());
        joiner.add(IrKeywords.ASSIGN);
        joiner.add(this.operator.generate());
        joiner.add(this.operands.get(0).getType().generate());
        joiner.add(this.operands.get(0).generate());
        joiner.add(IrKeywords.SEPARATOR);
        joiner.add(this.operands.get(1).generate());
        return joiner.toString();
    }

    @Override
    public void setReceiverType() {
        this.receiver.setType(this.operands.get(0).getType());
    }
}
