package com.cpunisher.hayasai.ir.value.stmt.impl;

import com.cpunisher.hayasai.ir.type.Type;
import com.cpunisher.hayasai.ir.value.operand.Register;
import com.cpunisher.hayasai.ir.value.stmt.ReceiverStatement;
import com.cpunisher.hayasai.util.IrKeywords;

import java.util.StringJoiner;

public class AllocaStatement extends ReceiverStatement {
    private final Type type;

    public AllocaStatement(Register receiver, Type type) {
        super(receiver);
        this.type = type;
        this.setReceiverType();
    }

    public Type getType() {
        return type;
    }

    public Register getReceiver() {
        return receiver;
    }

    @Override
    public void setReceiverType() {
        this.receiver.setType(type.getPointer());
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
        joiner.add(IrKeywords.ALLOCA);
        joiner.add(this.type.generate());
        return joiner.toString();
    }
}
