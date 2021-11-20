package com.cpunisher.hayasai.ir.value.stmt.impl;

import com.cpunisher.hayasai.ir.value.operand.Operand;
import com.cpunisher.hayasai.ir.value.operand.Register;
import com.cpunisher.hayasai.ir.value.stmt.ReceiverStatement;
import com.cpunisher.hayasai.util.IrKeywords;

import java.util.Arrays;
import java.util.StringJoiner;

public class LoadStatement extends ReceiverStatement {
    public LoadStatement(Register receiver, Operand addr) {
        super(receiver);
        this.operands = Arrays.asList(addr);
        this.setReceiverType();
    }

    @Override
    public String generate() {
        StringJoiner joiner = new StringJoiner(" ");
        joiner.add(receiver.generate());
        joiner.add(IrKeywords.ASSIGN);
        joiner.add(IrKeywords.LOAD);
        joiner.add(this.operands.get(0).getType().getWrappedType().generate());
        joiner.add(IrKeywords.SEPARATOR);
        joiner.add(this.operands.get(0).getType().generate());
        joiner.add(this.operands.get(0).generate());
        return joiner.toString();
    }

    @Override
    public void setReceiverType() {
        this.receiver.setType(this.operands.get(0).getType().getWrappedType());
    }
}
