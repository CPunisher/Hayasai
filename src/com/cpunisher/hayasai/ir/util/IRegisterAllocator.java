package com.cpunisher.hayasai.ir.util;

import com.cpunisher.hayasai.ir.value.operand.Register;

public interface IRegisterAllocator {

    Register alloc();

    void release();
}
