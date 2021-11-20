package com.cpunisher.hayasai.ir.value.stmt.impl;

import com.cpunisher.hayasai.ir.type.Type;
import com.cpunisher.hayasai.ir.value.operand.Operand;
import com.cpunisher.hayasai.ir.value.operand.Register;
import com.cpunisher.hayasai.ir.value.stmt.ReceiverStatement;
import com.cpunisher.hayasai.util.IrKeywords;

import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

public class GepStatement extends ReceiverStatement {
    private final Type type;
    private final int indexSize;

    public GepStatement(Register receiver, Type type, Operand arrayPointer, List<Operand> index) {
        super(receiver);
        this.type = type;
        this.indexSize = index.size();

        List<Operand> fixed = Arrays.asList(new Operand[this.indexSize + 1]);
        fixed.set(0, arrayPointer);
        for (int i = 0; i < index.size(); i++) {
            fixed.set(i + 1, index.get(i));
        }
        this.operands = fixed;
        this.setReceiverType();
    }

    @Override
    public String generate() {
        StringJoiner joiner = new StringJoiner(" ");
        joiner.add(this.receiver.generate());
        joiner.add(IrKeywords.ASSIGN);
        joiner.add(IrKeywords.GEP);
        joiner.add(this.type.generate());
        joiner.add(IrKeywords.SEPARATOR);
        joiner.add(this.operands.get(0).getType().generate());
        joiner.add(this.operands.get(0).generate());
        for (int i = 1; i <= this.indexSize; i++) {
            joiner.add(IrKeywords.SEPARATOR);
            joiner.add(this.operands.get(i).getType().generate());
            joiner.add(this.operands.get(i).generate());
        }
        return joiner.toString();
    }

    @Override
    public void setReceiverType() {
        Type type = this.operands.get(0).getType();
        for (int i = 0; i < this.indexSize - 1; i++) {
            type = type.getWrappedType();
        }
        this.receiver.setType(type.getWrappedType().getPointer());
    }
}
