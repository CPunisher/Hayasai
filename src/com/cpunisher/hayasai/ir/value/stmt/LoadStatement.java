package com.cpunisher.hayasai.ir.value.stmt;

import com.cpunisher.hayasai.ir.type.Type;
import com.cpunisher.hayasai.ir.value.operand.Operand;
import com.cpunisher.hayasai.util.IrKeywords;

import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

public class LoadStatement extends Statement {
    private final Operand receiver;

    public LoadStatement(Operand receiver, Operand addr) {
        this.receiver = receiver;
        this.operands = Arrays.asList(addr);
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
        joiner.add(IrKeywords.LOAD);
        joiner.add(receiver.getType().generate());
        joiner.add(IrKeywords.SEPARATOR);
        joiner.add(Type.ADDR.generate());
        joiner.add(this.operands.get(0).generate());
        return joiner.toString();
    }
}
