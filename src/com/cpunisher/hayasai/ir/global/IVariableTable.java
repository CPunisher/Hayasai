package com.cpunisher.hayasai.ir.global;

import com.cpunisher.hayasai.ir.value.Ident;
import com.cpunisher.hayasai.ir.value.operand.Operand;

public interface IVariableTable {

    Operand getVar(Ident ident);

    Operand getConst(Ident ident);

    void putVar(Ident ident, Operand value);

    void putConst(Ident ident, Operand constValue);
}
