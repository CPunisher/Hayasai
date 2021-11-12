package com.cpunisher.hayasai.ir.value.stmt;

import com.cpunisher.hayasai.ir.type.Type;
import com.cpunisher.hayasai.ir.value.Value;
import com.cpunisher.hayasai.ir.value.operand.Operand;
import com.cpunisher.hayasai.util.IrKeywords;
import com.cpunisher.hayasai.util.SyntaxException;

import java.util.List;
import java.util.StringJoiner;

public class IcmpStatement extends Statement {
    private final Operand receiver;
    private final Operand operand1;
    private final Operand operand2;
    private final CompareType compareType;

    public IcmpStatement(Operand receiver, Operand operand1, Operand operand2, CompareType compareType) {
        assert receiver.getType() == Type.BIT;
        assert operand1.getType() == operand2.getType();

        this.receiver = receiver;
        this.operand1 = operand1;
        this.operand2 = operand2;
        this.compareType = compareType;
    }

    @Override
    public void build() {
        this.receiver.build();
    }

    @Override
    public String generate() {
        StringJoiner joiner = new StringJoiner(" ");
        joiner.add(this.receiver.generate());
        joiner.add(IrKeywords.ASSIGN);
        joiner.add(IrKeywords.ICMP);
        joiner.add(this.compareType.generate());
        joiner.add(this.operand1.getType().generate());
        joiner.add(this.operand1.generate());
        joiner.add(IrKeywords.SEPARATOR);
        joiner.add(this.operand2.generate());
        return joiner.toString();
    }

    @Override
    public List<Operand> getOperands() {
        return List.of(this.operand1, this.operand2);
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
            throw new SyntaxException("Unknown Compare type [" + text + "]");
        }
    }
}
