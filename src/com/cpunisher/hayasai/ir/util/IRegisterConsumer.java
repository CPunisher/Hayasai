package com.cpunisher.hayasai.ir.util;

import com.cpunisher.hayasai.ir.value.operand.Register;

import java.util.List;

public interface IRegisterConsumer {

    List<Register> getUsedRegister();
}
