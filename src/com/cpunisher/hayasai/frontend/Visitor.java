package com.cpunisher.hayasai.frontend;

import com.cpunisher.hayasai.ir.global.SymbolTable;
import com.cpunisher.hayasai.ir.type.Type;
import com.cpunisher.hayasai.ir.util.BinaryOperator;
import com.cpunisher.hayasai.ir.value.expr.VoidExpression;
import com.cpunisher.hayasai.ir.value.func.Function;
import com.cpunisher.hayasai.ir.value.func.FunctionDecl;
import com.cpunisher.hayasai.ir.value.stmt.*;
import com.cpunisher.hayasai.ir.value.operand.Literal;
import com.cpunisher.hayasai.ir.value.Block;
import com.cpunisher.hayasai.ir.value.Ident;
import com.cpunisher.hayasai.ir.value.Value;
import com.cpunisher.hayasai.ir.value.expr.OperandExpression;
import com.cpunisher.hayasai.ir.value.func.FunctionDef;
import com.cpunisher.hayasai.ir.value.func.FunctionParams;
import com.cpunisher.hayasai.ir.value.operand.Operand;
import com.cpunisher.hayasai.ir.value.operand.Register;
import com.cpunisher.hayasai.util.SyntaxException;
import org.antlr.v4.runtime.misc.Pair;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Visitor extends MiniSysYBaseVisitor<Value> {

    private final SymbolTable symbolTable = SymbolTable.INSTANCE;

    private Block curBlock;

    @Override
    public Value visitCompUnit(MiniSysYParser.CompUnitContext ctx) {
        return super.visitCompUnit(ctx);
    }

    @Override
    public Value visitFuncDef(MiniSysYParser.FuncDefContext ctx) {
        Type type = (Type) visitFuncType(ctx.funcType());
        Ident ident = Ident.valueOf(ctx.IDENT().getText());
        Block block = (Block) visitBlock(ctx.block());
        FunctionParams params = new FunctionParams();
        symbolTable.putFunctionDef(new FunctionDef(type, ident, params, block));
        return null;
    }

    @Override
    public Value visitFuncType(MiniSysYParser.FuncTypeContext ctx) {
        return Type.valueOf(ctx.getText());
    }

    @Override
    public Value visitBlock(MiniSysYParser.BlockContext ctx) {
        Block block = new Block("block");
        curBlock = block;
        ctx.blockItem().stream()
                .map(this::visitBlockItem)
                .forEach(value -> curBlock.addSub((Statement) value));
        return block;
    }

    /* visitStmt 和 declare 系列必须返回 Statement */
    @Override
    public Value visitRetStmt(MiniSysYParser.RetStmtContext ctx) {
        return new RetStatement((OperandExpression) visitExp(ctx.exp()));
    }

    @Override
    public Value visitAssignStmt(MiniSysYParser.AssignStmtContext ctx) {
//        OperandExpression lVal = (OperandExpression) visitLVal(ctx.lVal());
        Ident target = Ident.valueOf(ctx.lVal().IDENT().getText());
        Pair<Register, Boolean> pair = this.curBlock.compute(target);
        if (pair.b) {
            throw new SyntaxException("Cannot assign to constant [" + ctx.lVal().IDENT().getText() + "].");
        }
        OperandExpression exp = (OperandExpression) visitExp(ctx.exp());
        return new StoreStatement(exp, pair.a);
    }

    @Override
    public Value visitConstDecl(MiniSysYParser.ConstDeclContext ctx) {
        for (var constDef : ctx.constDef()) {
            this.curBlock.addSub((Statement) visitConstDef(constDef));
        }
        return null;
    }

    @Override
    public Value visitConstDef(MiniSysYParser.ConstDefContext ctx) {
        Ident ident = Ident.valueOf(ctx.IDENT().getText());
        OperandExpression expression = (OperandExpression) visitConstInitVal(ctx.constInitVal());
        Register register = this.curBlock.putConst(ident);
        return new StoreStatement(expression, register);
    }

    @Override
    public Value visitConstInitVal(MiniSysYParser.ConstInitValContext ctx) {
        if (ctx.constExp() != null) {
            // TODO 在语义上额外约束这里的 AddExp 必须是一个可以在编译期求出值的常量
            return visitConstExp(ctx.constExp());
        }
        return null;
    }

    @Override
    public Value visitVarDecl(MiniSysYParser.VarDeclContext ctx) {
        for (var varDef: ctx.varDef()) {
            this.curBlock.addSub((Statement) visitVarDef(varDef));
        }
        return null;
    }

    @Override
    public Value visitVarDef(MiniSysYParser.VarDefContext ctx) {
        Ident ident = Ident.valueOf(ctx.IDENT().getText());
        Register register = this.curBlock.putVar(ident);
        if (ctx.initVal() != null) {
            OperandExpression expression = (OperandExpression) visitInitVal(ctx.initVal());
            return new StoreStatement(expression, register);
        }
        return null;
    }

    /* visitExp 系列必须返回 OperandExpression */
    @Override
    public Value visitAddExp(MiniSysYParser.AddExpContext ctx) {
        OperandExpression expression = (OperandExpression) visitMulExp(ctx.mulExp(0));
        Register last = null, cur;
        if (ctx.unaryOp().size() > 0) {
            for (int i = 0; i < ctx.unaryOp().size(); i++) {
                Operand operand1 = last != null ? last : expression.getOperand();
                Operand operand2 = ((OperandExpression) visitMulExp(ctx.mulExp(i + 1))).getOperand();
                cur = this.curBlock.alloc();
                BinaryOperator operator = BinaryOperator.valueOf(ctx.unaryOp(i).getText());
                this.curBlock.addSub(new BinaryOperationStatement(cur, operand1, operand2, operator));
                last = cur;
            }
            return new OperandExpression(last);
        }
        return expression;
    }

    @Override
    public Value visitMulExp(MiniSysYParser.MulExpContext ctx) {
        OperandExpression expression = (OperandExpression) visitUnaryExp(ctx.unaryExp(0));
        Register last = null, cur;
        if (ctx.binaryOp().size() > 0) {
            for (int i = 0; i < ctx.binaryOp().size(); i++) {
                Operand operand1 = last != null ? last : expression.getOperand();
                Operand operand2 = ((OperandExpression) visitUnaryExp(ctx.unaryExp(i + 1))).getOperand();
                cur = this.curBlock.alloc();
                BinaryOperator operator = BinaryOperator.valueOf(ctx.binaryOp(i).getText());
                this.curBlock.addSub(new BinaryOperationStatement(cur, operand1, operand2, operator));
                last = cur;
            }
            return new OperandExpression(last);
        }
        return expression;
    }

    @Override
    public Value visitBasicUnaryExp(MiniSysYParser.BasicUnaryExpContext ctx) {
        OperandExpression expression = (OperandExpression) visitPrimaryExp(ctx.primaryExp());
        List<MiniSysYParser.UnaryOpContext> validUnaryOpList = ctx.unaryOp().stream()
                .filter(unaryOpContext -> unaryOpContext.MINUS() != null)
                .collect(Collectors.toList());
        Register last = null, cur;
        if (validUnaryOpList.size() > 0) {
            for (int i = validUnaryOpList.size() - 1; i >= 0; i--) {
                if (validUnaryOpList.get(i).MINUS() != null) {
                    cur = this.curBlock.alloc();
                    Operand operand = last != null ? last : expression.getOperand();
                    this.curBlock.addSub(new BinaryOperationStatement(cur, Literal.ZERO, operand, BinaryOperator.SUB));
                    last = cur;
                }
            }
            return new OperandExpression(last);
        }
        return expression;
    }

    @Override
    public Value visitFuncCall(MiniSysYParser.FuncCallContext ctx) {
        Ident ident = Ident.valueOf(ctx.IDENT().getText());
        FunctionParams params = new FunctionParams(Optional.ofNullable(ctx.funcRParams())
                .stream()
                .flatMap(paramsCtx -> paramsCtx.exp().stream())
                .map(this::visitExp)
                .map(OperandExpression.class::cast)
                .map(FunctionParams.FunctionParam::new)
                .collect(Collectors.toList()));
        Function f = this.symbolTable.getFunctionByIdent(ident);
        if (f == null) {
            throw new SyntaxException("Function [" + ident.getIdent() + "] is not declared.");
        }

        if (!f.getParam().equals(params)) {
            throw new SyntaxException("Function [" + ident.getIdent() + "] arguments error.");
        }

        f = new FunctionDecl(f.getFuncType(), ident, params);
        if (f.getFuncType() != Type.VOID) {
            Register register = this.curBlock.alloc();
            this.curBlock.addSub(new CallStatement(register, f));
            return new OperandExpression(register);
        }
        this.curBlock.addSub(new CallStatement(f));
        return VoidExpression.VOID_EXPRESSION;
    }

    @Override
    public Value visitPrimaryExp(MiniSysYParser.PrimaryExpContext ctx) {
        if (ctx.exp() != null) {
            return visitExp(ctx.exp());
        } else if (ctx.lVal() != null) {
            return visitLVal(ctx.lVal());
        } else if (ctx.number() != null) {
            return visitNumber(ctx.number());
        }
        return null;
    }

    /* 使用左值时访问，赋值不需要 */
    @Override
    public Value visitLVal(MiniSysYParser.LValContext ctx) {
        Ident target = Ident.valueOf(ctx.IDENT().getText());
        Pair<Register, Boolean> pair = this.curBlock.compute(target);
        Register tmpRegister = this.curBlock.alloc();
        this.curBlock.addSub(new LoadStatement(tmpRegister, pair.a));
        return new OperandExpression(tmpRegister, pair.b);
    }

    @Override
    public Value visitNumber(MiniSysYParser.NumberContext ctx) {
        String text = ctx.getText();
        int res = 0;
        if (text.startsWith("0x") || text.startsWith("0X")) {
            res = Integer.parseInt(text.substring(2), 16);
        } else if (text.startsWith("0")) {
            res = Integer.parseInt(text, 8);
        } else {
            res = Integer.parseInt(text, 10);
        }
        return new OperandExpression(new Literal(res));
    }
}
