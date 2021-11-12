package com.cpunisher.hayasai.ir.value.stmt;

import com.cpunisher.hayasai.ir.util.BinaryOperator;
import com.cpunisher.hayasai.ir.value.operand.Operand;
import com.cpunisher.hayasai.util.IrKeywords;

import java.util.List;
import java.util.StringJoiner;

public class BinaryOperationStatement extends Statement {

    private final Operand receiver;
    private final Operand operand1;
    private final Operand operand2;
    private final BinaryOperator operator;

    public BinaryOperationStatement(Operand receiver, Operand operand1, Operand operand2, BinaryOperator operator) {
        assert receiver.getType() == operand1.getType();
        assert receiver.getType() == operand2.getType();
        assert operand1.getType() == operand2.getType();

        this.receiver = receiver;
        this.operand1 = operand1;
        this.operand2 = operand2;
        this.operator = operator;
    }

    @Override
    public void build() {
        this.receiver.build();
    }

    @Override
    public String generate() {
        StringJoiner joiner = new StringJoiner(" ");
        joiner.add(this.receiver.generate());
        joiner.add(IrKeywords.ASSIGN);
        joiner.add(this.operator.generate());
        joiner.add(this.operand1.getType().generate());
        joiner.add(this.operand1.generate());
        joiner.add(IrKeywords.SEPARATOR);
        joiner.add(this.operand2.generate());
        return joiner.toString();
    }

    @Override
    public List<Operand> getOperands() {
        return List.of(operand1, operand2);
    }
}
