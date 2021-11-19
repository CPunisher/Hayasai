package com.cpunisher.hayasai.ir.value.stmt;

import com.cpunisher.hayasai.ir.value.operand.Register;

public abstract class ReceiverStatement extends Statement {
    protected final Register receiver;

    protected ReceiverStatement(Register receiver) {
        this.receiver = receiver;
    }

    public Register getReceiver() {
        return receiver;
    }

    public abstract void setReceiverType();
}
