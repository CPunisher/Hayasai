package com.cpunisher.hayasai.ir.value;

import com.cpunisher.hayasai.ir.value.operand.Operand;

import java.util.List;

public interface IUser {

    void replace(Operand oldOperand, Operand newOperand);

    Operand getOperandOfRank(int rank);

    void setOperandOfRank(int rank, Operand operand);

    List<Operand> getOperands();
}
