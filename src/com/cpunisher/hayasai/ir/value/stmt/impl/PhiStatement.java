package com.cpunisher.hayasai.ir.value.stmt.impl;

import com.cpunisher.hayasai.ir.type.Type;
import com.cpunisher.hayasai.ir.value.Block;
import com.cpunisher.hayasai.ir.value.operand.Operand;
import com.cpunisher.hayasai.ir.value.operand.Register;
import com.cpunisher.hayasai.ir.value.stmt.ReceiverStatement;
import com.cpunisher.hayasai.ir.value.stmt.Statement;
import com.cpunisher.hayasai.util.IrKeywords;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

public class PhiStatement extends ReceiverStatement {
    private final Map<Block, Operand> values;

    public PhiStatement(Register receiver) {
        super(receiver);
        this.values = new HashMap<>();
        this.setReceiverType();
    }

    public Register getReceiver() {
        return receiver;
    }

    public void putValue(Block block, Operand operand) {
        this.values.put(block, operand);
    }

    public Map<Block, Operand> getValues() {
        return Collections.unmodifiableMap(this.values);
    }

    @Override
    public String generate() {
        StringJoiner joiner = new StringJoiner(" ");
        joiner.add(receiver.generate());
        joiner.add(IrKeywords.ASSIGN);
        joiner.add(IrKeywords.PHI);
        joiner.add(Type.INT.generate());

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

    @Override
    public void setReceiverType() {
        this.receiver.setType(Type.INT);
    }
}
