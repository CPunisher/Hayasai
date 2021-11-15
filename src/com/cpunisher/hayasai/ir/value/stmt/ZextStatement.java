package com.cpunisher.hayasai.ir.value.stmt;

import com.cpunisher.hayasai.ir.type.Type;
import com.cpunisher.hayasai.ir.value.expr.OperandExpression;
import com.cpunisher.hayasai.ir.value.operand.Operand;
import com.cpunisher.hayasai.ir.value.operand.Register;
import com.cpunisher.hayasai.util.IrKeywords;

import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

public class ZextStatement extends Statement {
    private final Register receiver;
    private final Type newType;

    public ZextStatement(Register receiver, OperandExpression origin, Type newType) {
        this.receiver = receiver;
        this.newType = newType;
        this.operands = Arrays.asList(origin.getOperand());
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
        joiner.add(this.operands.get(0).getType().generate());
        joiner.add(this.operands.get(0).generate());
        joiner.add(IrKeywords.TO);
        joiner.add(newType.generate());
        return joiner.toString();
    }
}
