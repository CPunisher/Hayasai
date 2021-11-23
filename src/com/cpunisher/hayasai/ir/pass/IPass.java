package com.cpunisher.hayasai.ir.pass;

import com.cpunisher.hayasai.ir.global.SymbolTable;

// TODO Opt with kernel linked list
public interface IPass {

    void pass(SymbolTable module);
}
