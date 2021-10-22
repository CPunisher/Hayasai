package com.cpunisher.hayasai.ir.value;

import com.cpunisher.hayasai.ir.util.DefaultAllocator;
import com.cpunisher.hayasai.ir.util.IRegisterAllocator;
import com.cpunisher.hayasai.ir.value.operand.Register;
import com.cpunisher.hayasai.util.IrKeywords;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public final class Block extends Value implements IRegisterAllocator {

    private final List<Value> subList;
    private final IRegisterAllocator allocator;

    public Block(String name) {
        super(name);
        this.subList = new ArrayList<>();
        this.allocator = new DefaultAllocator();
    }

    public void addSub(Value sub) {
        this.subList.add(sub);
    }

    @Override
    public String build() {
        this.release();
        StringJoiner joiner = new StringJoiner(IrKeywords.LINE_SEPARATOR,
                IrKeywords.LCURLY + IrKeywords.LINE_SEPARATOR,
                IrKeywords.LINE_SEPARATOR + IrKeywords.RCURLY + IrKeywords.LINE_SEPARATOR);
        this.subList.stream().map(Value::build).forEach(str -> joiner.add("    " + str));
        return joiner.toString();
    }

    @Override
    public Register alloc() {
        return this.allocator.alloc();
    }

    @Override
    public void release() {

    }
}
