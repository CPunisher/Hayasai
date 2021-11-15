package com.cpunisher.hayasai.ir.value.stmt;

import com.cpunisher.hayasai.ir.type.Type;
import com.cpunisher.hayasai.ir.value.Ident;
import com.cpunisher.hayasai.ir.value.expr.OperandExpression;
import com.cpunisher.hayasai.ir.value.func.Function;
import com.cpunisher.hayasai.ir.value.func.FunctionParams;
import com.cpunisher.hayasai.ir.value.operand.Operand;
import com.cpunisher.hayasai.ir.value.operand.Register;
import com.cpunisher.hayasai.util.IrKeywords;

import java.util.StringJoiner;
import java.util.stream.Collectors;

public class CallStatement extends Statement {
    private final Register receiver;
    private final Type funcType;
    private final Ident funcIdent;

    public CallStatement(Function function) {
        this(null, function);
    }

    public CallStatement(Register receiver, Function function) {
        this.receiver = receiver;
        this.funcType = function.getFuncType();
        this.funcIdent = function.getIdent();

        this.operands = function.getParam().getParams()
                .stream()
                .map(FunctionParams.FunctionParam.class::cast)
                .map(FunctionParams.FunctionParam::getExpression)
                .map(OperandExpression::getOperand).collect(Collectors.toList());
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
        joiner.add(IrKeywords.FUNC_IDENT + this.funcIdent.generate() + paramJoiner);
        return joiner.toString();
    }
}
