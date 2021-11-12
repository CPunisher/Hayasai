package com.cpunisher.hayasai.ir.pass;

import com.cpunisher.hayasai.ir.global.SymbolTable;

public interface IPass {

    void pass(SymbolTable module);
}
