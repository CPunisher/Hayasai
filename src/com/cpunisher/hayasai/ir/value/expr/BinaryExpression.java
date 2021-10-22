package com.cpunisher.hayasai.ir.value.expr;

import com.cpunisher.hayasai.ir.util.BinaryOperator;
import com.cpunisher.hayasai.ir.value.operand.Operand;
import com.cpunisher.hayasai.util.IrKeywords;

import java.util.StringJoiner;

public class BinaryExpression extends Expression {

    private final Operand receiver;
    private final Operand operand1;
    private final Operand operand2;
    private final BinaryOperator operator;

    public BinaryExpression(String name, Operand receiver, Operand operand1, Operand operand2, BinaryOperator operator) {
        super(name);
        // TODO operand type check
        this.receiver = receiver;
        this.operand1 = operand1;
        this.operand2 = operand2;
        this.operator = operator;
    }

    @Override
    public String build() {
        StringJoiner joiner = new StringJoiner(" ");
        joiner.add(this.receiver.build());
        joiner.add(IrKeywords.ASSIGN);
        joiner.add(this.operator.build());
        joiner.add(this.receiver.getType().build());
        joiner.add(this.operand1.build());
        joiner.add(IrKeywords.SEPARATOR);
        joiner.add(this.operand2.build());
        return joiner.toString();
    }
}
