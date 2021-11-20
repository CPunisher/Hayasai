package com.cpunisher.hayasai.ir.value;

import com.cpunisher.hayasai.ir.value.operand.Operand;

import java.util.List;

public interface IUser {

    void replace(Operand oldOperand, Operand newOperand);

    List<Operand> getOperands();
}
