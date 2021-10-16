package com.cpunisher.hayasai.ir.value.func;

import com.cpunisher.hayasai.ir.type.Type;
import com.cpunisher.hayasai.ir.value.Ident;
import com.cpunisher.hayasai.ir.value.Value;
import com.cpunisher.hayasai.util.IrKeywords;

import java.util.List;
import java.util.StringJoiner;

public final class FunctionFParams extends Value {

    private final List<FunctionParam> params;

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
    }
}
