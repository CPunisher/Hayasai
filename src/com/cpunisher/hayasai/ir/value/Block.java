package com.cpunisher.hayasai.ir.value;

import com.cpunisher.hayasai.util.IrKeywords;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public final class Block extends Value {

    private final List<Value> subList;

    public Block(String name) {
        super(name);
        subList = new ArrayList<>();
    }

    public void addSub(Value sub) {
        this.subList.add(sub);
    }

    @Override
    public String build() {
        StringJoiner joiner = new StringJoiner(IrKeywords.LINE_SEPARATOR,
                " " + IrKeywords.LCURLY + IrKeywords.LINE_SEPARATOR,
                IrKeywords.LINE_SEPARATOR + IrKeywords.RCURLY + IrKeywords.LINE_SEPARATOR);
        this.subList.stream().map(Value::build).forEach(str -> joiner.add("\t" + str));
        return joiner.toString();
    }
}
