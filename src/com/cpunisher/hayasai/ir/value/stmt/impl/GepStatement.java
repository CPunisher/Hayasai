package com.cpunisher.hayasai.ir.value.stmt.impl;

import com.cpunisher.hayasai.ir.value.operand.Register;
import com.cpunisher.hayasai.ir.value.stmt.ReceiverStatement;

public class GepStatement extends ReceiverStatement {
    protected GepStatement(Register receiver) {
        super(receiver);
    }

    @Override
    public String generate() {
        return null;
    }

    @Override
    public void setReceiverType() {

    }
}
