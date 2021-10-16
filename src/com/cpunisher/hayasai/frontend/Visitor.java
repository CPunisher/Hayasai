package com.cpunisher.hayasai.frontend;

public class Visitor extends MiniSysYBaseVisitor<Void> {

    @Override
    public Void visitCompUnit(MiniSysYParser.CompUnitContext ctx) {
        return super.visitCompUnit(ctx);
    }

    @Override
    public Void visitFuncDef(MiniSysYParser.FuncDefContext ctx) {
        return super.visitFuncDef(ctx);
    }

    @Override
    public Void visitFuncType(MiniSysYParser.FuncTypeContext ctx) {
        return super.visitFuncType(ctx);
    }

    @Override
    public Void visitIdent(MiniSysYParser.IdentContext ctx) {
        return super.visitIdent(ctx);
    }

    @Override
    public Void visitBlock(MiniSysYParser.BlockContext ctx) {
        return super.visitBlock(ctx);
    }

    @Override
    public Void visitStmt(MiniSysYParser.StmtContext ctx) {
        return super.visitStmt(ctx);
    }

    @Override
    public Void visitNumber(MiniSysYParser.NumberContext ctx) {
        return super.visitNumber(ctx);
    }
}
