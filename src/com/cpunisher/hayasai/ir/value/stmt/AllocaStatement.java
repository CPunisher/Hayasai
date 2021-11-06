package com.cpunisher.hayasai.ir.value.stmt;

import com.cpunisher.hayasai.ir.type.Type;
import com.cpunisher.hayasai.ir.value.operand.Operand;
import com.cpunisher.hayasai.util.IrKeywords;

import java.util.StringJoiner;

public class AllocaStatement extends Statement {
    private final Operand receiver;
    private final Type type;

    public AllocaStatement(Operand receiver, Type type) {
        this.receiver = receiver;
        this.type = type;
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
        joiner.add(type.generate());
        return joiner.toString();
    }
}
