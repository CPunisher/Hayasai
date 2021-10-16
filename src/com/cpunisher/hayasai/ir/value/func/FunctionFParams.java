package com.cpunisher.hayasai.ir.value.func;

import com.cpunisher.hayasai.ir.type.Type;
import com.cpunisher.hayasai.ir.value.Ident;
import com.cpunisher.hayasai.ir.value.Value;
import com.cpunisher.hayasai.util.IrKeywords;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.IntStream;

public final class FunctionFParams extends Value {

    private final List<FunctionParam> params;

    public FunctionFParams(String name) {
        this(name, new ArrayList<>());
    }


    public FunctionFParams(String name, List<FunctionParam> params) {
        super(name);
        this.params = params;
    }

    @Override
    public String build() {
        StringJoiner joiner = new StringJoiner(IrKeywords.SEPARATOR + " ", IrKeywords.LBRACKET, IrKeywords.RBRACKET);
        params.stream().map(FunctionParam::build).forEach(joiner::add);
        return joiner.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof FunctionFParams)) {
            return false;
        }

        FunctionFParams functionFParams = (FunctionFParams) obj;
        return this.params.size() == functionFParams.params.size()
                && IntStream.range(0, this.params.size()).allMatch(i -> this.params.get(i).equals(functionFParams.params.get(i)));
    }

    public static class FunctionParam extends Value {
        private final Type argType;
        private final Ident ident;

        public FunctionParam(String name, Type argType, Ident ident) {
            super(name);
            this.argType = argType;
            this.ident = ident;
        }

        @Override
        public String build() {
            StringBuilder builder = new StringBuilder();
            builder.append(this.argType.build()).append(" ");
            builder.append(this.ident.build());
            return builder.toString();
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }

            if (!(obj instanceof FunctionParam)) {
                return false;
            }

            FunctionParam functionParam = (FunctionParam) obj;
            return this.argType.equals(functionParam.argType);
        }
    }
}
