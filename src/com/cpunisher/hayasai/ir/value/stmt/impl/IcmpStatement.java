package com.cpunisher.hayasai.ir.value.stmt.impl;

import com.cpunisher.hayasai.ir.type.Type;
import com.cpunisher.hayasai.ir.value.Value;
import com.cpunisher.hayasai.ir.value.operand.Operand;
import com.cpunisher.hayasai.ir.value.operand.Register;
import com.cpunisher.hayasai.ir.value.stmt.ReceiverStatement;
import com.cpunisher.hayasai.util.IrKeywords;
import com.cpunisher.hayasai.util.SyntaxException;

import java.util.Arrays;
import java.util.StringJoiner;

public class IcmpStatement extends ReceiverStatement {
    private final CompareType compareType;

    public IcmpStatement(Register receiver, Operand operand1, Operand operand2, CompareType compareType) {
        super(receiver);
        assert operand1.getType() == operand2.getType();

        this.compareType = compareType;
        this.operands = Arrays.asList(operand1, operand2);
        this.setReceiverType();
    }

    @Override
    public String generate() {
        StringJoiner joiner = new StringJoiner(" ");
        joiner.add(this.receiver.generate());
        joiner.add(IrKeywords.ASSIGN);
        joiner.add(IrKeywords.ICMP);
        joiner.add(this.compareType.generate());
        joiner.add(this.operands.get(0).getType().generate());
        joiner.add(this.operands.get(0).generate());
        joiner.add(IrKeywords.SEPARATOR);
        joiner.add(this.operands.get(1).generate());
        return joiner.toString();
    }

    @Override
    public void setReceiverType() {
        this.receiver.setType(Type.BIT);
    }

    public static final class CompareType extends Value {
        public static final CompareType EQ = new CompareType(IrKeywords.COMP_EQ);
        public static final CompareType NE = new CompareType(IrKeywords.COMP_NE);
        public static final CompareType SGT = new CompareType(IrKeywords.COMP_SGT);
        public static final CompareType SGE = new CompareType(IrKeywords.COMP_SGE);
        public static final CompareType SLT = new CompareType(IrKeywords.COMP_SLT);
        public static final CompareType SLE = new CompareType(IrKeywords.COMP_SLE);

        private final String value;

        private CompareType(String value) {
            this.value = value;
        }

        @Override
        public String generate() {
            return this.value;
        }

        public static CompareType valueOf(String text) {
            switch (text) {
                case "==" -> { return EQ; }
                case "!=" -> { return NE; }
                case ">" -> { return SGT; }
                case ">=" -> { return SGE; }
                case "<" -> { return SLT; }
                case "<=" -> { return SLE; }
            }
            throw SyntaxException.unknownCompareType(text);
        }
    }
}
