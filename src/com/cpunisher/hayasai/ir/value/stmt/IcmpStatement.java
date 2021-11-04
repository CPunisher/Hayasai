package com.cpunisher.hayasai.ir.value.stmt;

import com.cpunisher.hayasai.ir.value.Value;
import com.cpunisher.hayasai.ir.value.operand.Operand;
import com.cpunisher.hayasai.util.IrKeywords;

import java.util.StringJoiner;

public class IcmpStatement extends Statement {
    private final Operand receiver;
    private final Operand operand1;
    private final Operand operand2;
    private final CompareType compareType;

    public IcmpStatement(Operand receiver, Operand operand1, Operand operand2, CompareType compareType) {
        this.receiver = receiver;
        this.operand1 = operand1;
        this.operand2 = operand2;
        this.compareType = compareType;
    }

    @Override
    public String build() {
        StringJoiner joiner = new StringJoiner(" ");
        joiner.add(this.receiver.build());
        joiner.add(IrKeywords.ASSIGN);
        joiner.add(IrKeywords.ICMP);
        joiner.add(this.compareType.build());
        joiner.add(this.receiver.getType().build());
        joiner.add(this.operand1.build());
        joiner.add(IrKeywords.SEPARATOR);
        joiner.add(this.operand2.build());
        return joiner.toString();
    }

    public static final class CompareType extends Value {
        public static final CompareType EQ = new CompareType("eq");
        public static final CompareType NE = new CompareType("ne");
        public static final CompareType SGT = new CompareType("sgt");
        public static final CompareType SGE = new CompareType("sge");
        public static final CompareType SLT = new CompareType("slt");
        public static final CompareType SLE = new CompareType("sle");

        private final String value;

        private CompareType(String value) {
            this.value = value;
        }

        @Override
        public String build() {
            return this.value;
        }
    }
}
