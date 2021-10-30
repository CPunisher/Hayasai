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
    public String build() {
        StringJoiner joiner = new StringJoiner(" ");
        joiner.add(receiver.build());
        joiner.add(IrKeywords.ASSIGN);
        joiner.add(IrKeywords.ALLOCA);
        joiner.add(type.build());
        return joiner.toString();
    }
}
