package com.cpunisher.hayasai.ir.value.stmt;

import com.cpunisher.hayasai.ir.value.Block;
import com.cpunisher.hayasai.ir.value.operand.Operand;
import com.cpunisher.hayasai.util.IrKeywords;

import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

public class PhiStatement extends Statement {
    private final Operand receiver;
    private final Map<Block, Operand> values;

    public PhiStatement(Operand receiver) {
        this.receiver = receiver;
        this.values = new HashMap<>();
    }

    public void putValue(Block block, Operand operand) {
        this.values.put(block, operand);
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
        joiner.add(IrKeywords.PHI);
        joiner.add(this.receiver.getType().generate());

        StringJoiner valueJoiner = new StringJoiner(", ");
        for (Map.Entry<Block, Operand> entry : this.values.entrySet()) {
            StringJoiner innerValue = new StringJoiner(" ");
            innerValue.add(IrKeywords.LBRACKET);
            innerValue.add(entry.getValue().generate());
            innerValue.add(IrKeywords.SEPARATOR);
            innerValue.add(entry.getKey().getBlockRegister().generate());
            innerValue.add(IrKeywords.RBRACKET);
            valueJoiner.add(innerValue.toString());
        }
        joiner.add(valueJoiner.toString());
        return joiner.toString();
    }
}
