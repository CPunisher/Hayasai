package com.cpunisher.hayasai.ir.value.func;

import com.cpunisher.hayasai.ir.type.Type;
import com.cpunisher.hayasai.ir.value.Value;
import com.cpunisher.hayasai.ir.value.expr.OperandExpression;
import com.cpunisher.hayasai.util.IrKeywords;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class FunctionParams extends Value {

    private final List<FunctionParamDeclare> params;

    public FunctionParams() {
        this(new ArrayList<>());
    }

    public FunctionParams(Type... paramTypes) {
        this(List.of(paramTypes).stream().map(FunctionParamDeclare::new).collect(Collectors.toList()));
    }

    public FunctionParams(FunctionParamDeclare... params) {
        this(List.of(params));
    }

    public FunctionParams(List<FunctionParamDeclare> params) {
        this.params = params;
    }

    @Override
    public String build() {
        StringJoiner joiner = new StringJoiner(IrKeywords.SEPARATOR + " ", IrKeywords.LBRACKET, IrKeywords.RBRACKET);
        params.stream().map(FunctionParamDeclare::build).forEach(joiner::add);
        return joiner.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof FunctionParams functionParams)) {
            return false;
        }

        return this.params.size() == functionParams.params.size()
                && IntStream.range(0, this.params.size()).allMatch(i -> this.params.get(i).equals(functionParams.params.get(i)));
    }

    public List<FunctionParamDeclare> getParams() {
        return Collections.unmodifiableList(params);
    }

    public static class FunctionParamDeclare extends Value {
        private final Type argType;

        public FunctionParamDeclare(Type argType) {
            this.argType = argType;
        }

        @Override
        public String build() {
            return this.argType.build();
        }

        public Type getArgType() {
            return argType;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }

            if (!(obj instanceof FunctionParamDeclare functionParamDeclare)) {
                return false;
            }

            return this.getArgType().equals(functionParamDeclare.getArgType());
        }
    }

    public static class FunctionParam extends FunctionParamDeclare {
        private final OperandExpression expression;

        public FunctionParam(OperandExpression expression) {
            super(expression.getOperand().getType());
            this.expression = expression;
        }

        @Override
        public String build() {
            StringBuilder builder = new StringBuilder();
            builder.append(this.expression.build());
            return builder.toString();
        }
    }
}
