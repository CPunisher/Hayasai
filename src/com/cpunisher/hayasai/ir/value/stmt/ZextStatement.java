package com.cpunisher.hayasai.ir.value.stmt;

import com.cpunisher.hayasai.ir.type.Type;
import com.cpunisher.hayasai.ir.value.expr.OperandExpression;
import com.cpunisher.hayasai.ir.value.operand.Operand;
import com.cpunisher.hayasai.util.IrKeywords;

import java.util.StringJoiner;

public class ZextStatement extends Statement {
    private final Operand receiver;
    private final OperandExpression origin;
    private final Type newType;

    public ZextStatement(Operand receiver, OperandExpression origin, Type newType) {
        this.receiver = receiver;
        this.origin = origin;
        this.newType = newType;
    }

    @Override
    public void build() {
        this.receiver.build();
    }

    @Override
    public String generate() {
        StringJoiner joiner = new StringJoiner(" ");
        joiner.add(receiver.generate());
        joiner.add(IrKeywords.ASSIGN);
        joiner.add(IrKeywords.ZEXT);
        joiner.add(origin.generate());
        joiner.add(IrKeywords.TO);
        joiner.add(newType.generate());
        return joiner.toString();
    }
}
