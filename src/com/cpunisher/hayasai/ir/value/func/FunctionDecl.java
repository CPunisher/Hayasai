package com.cpunisher.hayasai.ir.value.func;

import com.cpunisher.hayasai.ir.type.Type;
import com.cpunisher.hayasai.ir.value.Ident;
import com.cpunisher.hayasai.util.IrKeywords;

import java.util.List;
import java.util.stream.Collectors;

public final class FunctionDecl extends Function {

    public FunctionDecl(Type funcType, Ident ident, List<? extends FunctionParam> params) {
        super(funcType, ident, params);
    }

    @Override
    public String generate() {
        StringBuilder builder = new StringBuilder();
        builder.append(IrKeywords.DECLARE).append(" ");
        builder.append(this.funcType.generate()).append(" ");
        builder.append(IrKeywords.GLOBAL_IDENT);
        builder.append(this.ident.generate());
        builder.append(this.params.stream()
                        .map(FunctionParam::getArgType)
                        .map(Type::generate)
                        .collect(Collectors.joining(IrKeywords.SEPARATOR + " ", IrKeywords.LPARENTHESE, IrKeywords.RPARENTHESE)));
        return builder.toString();
    }
}
