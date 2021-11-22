package com.cpunisher.hayasai.ir.value.stmt.impl;

import com.cpunisher.hayasai.ir.type.Type;
import com.cpunisher.hayasai.ir.value.Ident;
import com.cpunisher.hayasai.ir.value.expr.OperandExpression;
import com.cpunisher.hayasai.ir.value.func.Function;
import com.cpunisher.hayasai.ir.value.operand.Operand;
import com.cpunisher.hayasai.ir.value.operand.Register;
import com.cpunisher.hayasai.ir.value.stmt.ReceiverStatement;
import com.cpunisher.hayasai.ir.value.stmt.Statement;
import com.cpunisher.hayasai.util.IrKeywords;

import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

public class CallStatement extends ReceiverStatement {
    private final Type funcType;
    private final Ident funcIdent;
    private final Function function;

    public CallStatement(Function function, List<OperandExpression> args) {
        this(null, function, args);
    }

    public CallStatement(Register receiver, Function function, List<OperandExpression> args) {
        super(receiver);
        this.funcType = function.getFuncType();
        this.funcIdent = function.getIdent();
        this.function = function;

        this.operands = args.stream().map(OperandExpression::getOperand).collect(Collectors.toList());
        this.setReceiverType();
    }

    @Override
    public void build() {
        if (receiver != null) {
            this.receiver.build();
        }
    }

    @Override
    public String generate() {
        StringJoiner joiner = new StringJoiner(" ");
        StringJoiner paramJoiner = new StringJoiner(IrKeywords.SEPARATOR + " ", IrKeywords.LPARENTHESE, IrKeywords.RPARENTHESE);
        if (receiver != null) {
            joiner.add(this.receiver.generate());
            joiner.add(IrKeywords.ASSIGN);
        }
        joiner.add(IrKeywords.CALL);
        joiner.add(this.funcType.generate());

        for (Operand operand : this.operands) {
            paramJoiner.add(operand.getType().generate() + " " + operand.generate());
        }
        joiner.add(IrKeywords.GLOBAL_IDENT + this.funcIdent.generate() + paramJoiner);
        return joiner.toString();
    }

    @Override
    public void setReceiverType() {
        if (this.receiver != null) {
            this.receiver.setType(this.funcType);
        }
    }

    public Function getFunction() {
        return function;
    }
}
