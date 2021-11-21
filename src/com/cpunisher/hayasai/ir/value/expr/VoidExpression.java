package com.cpunisher.hayasai.ir.value.expr;

import com.cpunisher.hayasai.ir.value.operand.Literal;
import com.cpunisher.hayasai.ir.value.operand.VoidOperand;
import com.cpunisher.hayasai.util.SyntaxException;

public final class VoidExpression extends OperandExpression {
    public static final VoidExpression VOID_EXPRESSION = new VoidExpression();

    private VoidExpression() {
        super(new VoidOperand(), true);
    }

    @Override
    public String generate() {
        throw new SyntaxException("Operand expression is void.");
    }
}
