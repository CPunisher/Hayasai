package com.cpunisher.hayasai.ir.value.stmt;

import com.cpunisher.hayasai.ir.type.Type;
import com.cpunisher.hayasai.ir.value.operand.Operand;
import com.cpunisher.hayasai.util.IrKeywords;

import java.util.StringJoiner;

public class LoadStatement extends Statement {
    private final Operand receiver;
    private final Operand addr;

    public LoadStatement(String name, Operand receiver, Operand addr) {
        super(name);
        this.receiver = receiver;
        this.addr = addr;
    }

    @Override
    public String build() {
        StringJoiner joiner = new StringJoiner(" ");
        joiner.add(receiver.build());
        joiner.add(IrKeywords.ASSIGN);
        joiner.add(IrKeywords.LOAD);
        joiner.add(receiver.getType().build());
        joiner.add(IrKeywords.SEPARATOR);
        joiner.add(Type.ADDR.build());
        joiner.add(addr.build());
        return joiner.toString();
    }
}
