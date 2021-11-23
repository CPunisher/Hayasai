package com.cpunisher.hayasai.ir.pass;

import com.cpunisher.hayasai.ir.global.SymbolTable;
import com.cpunisher.hayasai.ir.value.Block;
import com.cpunisher.hayasai.ir.value.func.FunctionDef;
import com.cpunisher.hayasai.util.BlockCfg;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class DeadCodeRemove implements IPass {


    @Override
    public void pass(SymbolTable module) {
        for (FunctionDef def : module.getFuncDefTable().values()) {

        }
    }
}
