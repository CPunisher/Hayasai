package com.cpunisher.hayasai.ir.value.stmt;

import com.cpunisher.hayasai.ir.value.IUser;
import com.cpunisher.hayasai.ir.value.Value;
import com.cpunisher.hayasai.ir.value.operand.Operand;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public abstract class Statement extends Value implements IUser {

    protected List<Operand> operands = Arrays.asList();

    public void replace(Operand oldOperand, Operand newOperand) {
        this.operands.replaceAll(operand -> operand.equals(oldOperand) ? newOperand : oldOperand);
    }

    @Override
    public List<Operand> getOperands() {
        return Collections.unmodifiableList(this.operands);
    }
}
