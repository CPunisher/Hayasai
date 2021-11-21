package com.cpunisher.hayasai.ir.value.stmt.impl;

import com.cpunisher.hayasai.ir.util.NumberOperator;
import com.cpunisher.hayasai.ir.value.operand.Operand;
import com.cpunisher.hayasai.ir.value.operand.Register;
import com.cpunisher.hayasai.ir.value.stmt.ReceiverStatement;
import com.cpunisher.hayasai.util.IrKeywords;
import com.cpunisher.hayasai.util.SyntaxException;

import java.util.Arrays;
import java.util.StringJoiner;

public class BinaryOperationStatement extends ReceiverStatement {

    private final NumberOperator operator;

    public BinaryOperationStatement(Register receiver, Operand operand1, Operand operand2, NumberOperator operator) {
        super(receiver);
        if (!operand1.getType().equals(operand2.getType()) ||
                !receiver.getType().equals(operand2.getType()) ||
                !receiver.getType().equals(operand1.getType())) {
            throw new SyntaxException("Operand types are not equal.");
        }

        this.operator = operator;
        this.operands = Arrays.asList(operand1, operand2);
        this.setReceiverType();
    }

    public NumberOperator getOperator() {
        return operator;
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
