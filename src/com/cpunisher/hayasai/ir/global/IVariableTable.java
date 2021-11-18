package com.cpunisher.hayasai.ir.global;

import com.cpunisher.hayasai.ir.value.Ident;
import com.cpunisher.hayasai.ir.value.operand.Operand;

public interface IVariableTable<T extends Operand, U extends Operand> {

    T getVar(Ident ident);

    U getConst(Ident ident);

    void putVar(Ident ident, T value);

    void putConst(Ident ident, U constValue);
}
