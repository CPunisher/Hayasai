package com.cpunisher.hayasai.ir.value.stmt.impl;

import com.cpunisher.hayasai.ir.value.operand.Register;
import com.cpunisher.hayasai.ir.value.stmt.Statement;
import com.cpunisher.hayasai.util.IrKeywords;

import java.util.StringJoiner;

public class AllocaStatement extends Statement {
    private final Register receiver;

    public AllocaStatement(Register receiver) {
        this.receiver = receiver;
    }

    public Register getReceiver() {
        return receiver;
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
        joiner.add(receiver.getType().generate());
        return joiner.toString();
    }
}
