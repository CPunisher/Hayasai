package com.cpunisher.hayasai.ir.value.stmt.impl;

import com.cpunisher.hayasai.ir.type.Type;
import com.cpunisher.hayasai.ir.value.operand.Operand;
import com.cpunisher.hayasai.ir.value.operand.Register;
import com.cpunisher.hayasai.ir.value.stmt.ReceiverStatement;
import com.cpunisher.hayasai.util.IrKeywords;

import java.util.Arrays;
import java.util.StringJoiner;

public class BitcastStatement extends ReceiverStatement {
    private final Type type;

    public BitcastStatement(Register receiver, Operand operand, Type type) {
        super(receiver);
        this.operands = Arrays.asList(operand);
        this.type = type;
        this.setReceiverType();
    }

    @Override
    public String generate() {
        StringJoiner joiner = new StringJoiner(" ");
        joiner.add(receiver.generate());
        joiner.add(IrKeywords.ASSIGN);
        joiner.add(IrKeywords.BITCAST);
        joiner.add(this.operands.get(0).getType().generate());
        joiner.add(this.operands.get(0).generate());
        joiner.add(IrKeywords.TO);
        joiner.add(this.type.generate());
        return joiner.toString();
    }

    @Override
    public void setReceiverType() {
        this.receiver.setType(this.type);
    }
}
