package com.cpunisher.hayasai.frontend;

import com.cpunisher.hayasai.frontend.antlr.MiniSysYBaseVisitor;
import com.cpunisher.hayasai.frontend.antlr.MiniSysYParser;
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

    private final BlockManager blockManager = new BlockManager();
    private final ConditionContext condCtx = new ConditionContext();
    private boolean visitingConstExp;

    @Override
    public Value visitCompUnit(MiniSysYParser.CompUnitContext ctx) {
        return super.visitCompUnit(ctx);
    }

    @Override
    public Value visitFuncDef(MiniSysYParser.FuncDefContext ctx) {
        Type type = (Type) visitFuncType(ctx.funcType());
        Ident ident = Ident.valueOf(ctx.IDENT().getText());
        FunctionParams params = new FunctionParams();
        Block block = this.blockManager.createAndSet("main");
        this.blockManager.setRoot(block);
        visitBlock(ctx.block());
        this.blockManager.setCurrent(null);
        symbolTable.putFunctionDef(new FunctionDef(type, ident, params, block));
        return null;
    }

    @Override
    public Value visitFuncType(MiniSysYParser.FuncTypeContext ctx) {
        return Type.valueOf(ctx.getText());
    }

    @Override
    public Value visitBlock(MiniSysYParser.BlockContext ctx) {
        for (MiniSysYParser.BlockItemContext blockItemContext : ctx.blockItem()) {
            this.blockManager.addToCurrent(visitBlockItem(blockItemContext));
        }
        return null;
    }

    @Override
    public Value visitIfStmt(MiniSysYParser.IfStmtContext ctx) {
        Block lastBlock = this.blockManager.current();
        lastBlock.setNext(null);

        boolean hasElse = ctx.stmt().size() > 1;
        Block blockAfter = this.blockManager.create("blockAfter", true);
        Block blockTrue = this.blockManager.create("blockTrue", false);
        Block blockElse;
        if (hasElse) blockElse = this.blockManager.create("blockElse", false);
        else blockElse = blockAfter;

        this.condCtx.setEnable(true);
        this.condCtx.setBlockTrue(blockTrue);
        this.condCtx.setBlockFalse(blockElse);
        OperandExpression condEntryExp = (OperandExpression) visitCond(ctx.cond());
//        if (condExp.getOperand().getType() == Type.INT) {
//            Register cur = this.blockManager.current().alloc(Type.BIT);
//            this.blockManager.addToCurrent(new IcmpStatement(cur, condExp.getOperand(), Literal.INT_ZERO, IcmpStatement.CompareType.NE));
//            condExp = new OperandExpression(cur);
//        }

        this.blockManager.setCurrent(blockTrue);
        blockTrue.setNext(blockAfter);
        if (ctx.stmt(0).block() != null) {
            visitBlock(ctx.stmt(0).block());
        } else {
            this.blockManager.addToCurrent(ctx.stmt(0).children.get(0).accept(this));
        }

        if (hasElse) {
            // else
            this.blockManager.setCurrent(blockElse);
            blockElse.setNext(blockAfter);
            if (ctx.stmt(1).block() != null) {
                visitBlock(ctx.stmt(1).block());
            } else {
                this.blockManager.addToCurrent(ctx.stmt(1).children.get(0).accept(this));
            }
        }
//        lastBlock.addSub(new BrCondStatement(condExp, blockTrue.getBlockRegister(), blockElse.getBlockRegister()));
        lastBlock.addSub(new BrStatement(condEntryExp.getOperand()));
        this.blockManager.setCurrent(blockAfter);
        return null;
    }

    /* visitStmt 和 declare 系列必须返回 Statement 或 Block */
    @Override
    public Value visitRetStmt(MiniSysYParser.RetStmtContext ctx) {
        return new RetStatement((OperandExpression) visitExp(ctx.exp()));
    }

    @Override
    public Value visitAssignStmt(MiniSysYParser.AssignStmtContext ctx) {
        Ident target = Ident.valueOf(ctx.lVal().IDENT().getText());
        Pair<Register, Boolean> pair = this.blockManager.current().compute(target);
        if (pair.b) {
            throw new SyntaxException("Cannot assign to constant [" + ctx.lVal().IDENT().getText() + "].");
        }
        OperandExpression exp = (OperandExpression) visitExp(ctx.exp());
        return new StoreStatement(exp, pair.a);
    }

    @Override
    public Value visitConstDecl(MiniSysYParser.ConstDeclContext ctx) {
        for (var constDef : ctx.constDef()) {
            this.blockManager.addToCurrent(visitConstDef(constDef));
        }
        return null;
    }

    @Override
    public Value visitConstDef(MiniSysYParser.ConstDefContext ctx) {
        Ident ident = Ident.valueOf(ctx.IDENT().getText());
        OperandExpression expression = (OperandExpression) visitConstInitVal(ctx.constInitVal());
        Register register = this.blockManager.current().putConst(ident);
        return new StoreStatement(expression, register);
    }

    @Override
    public Value visitConstInitVal(MiniSysYParser.ConstInitValContext ctx) {
        if (ctx.constExp() != null) {
            this.visitingConstExp = true;
            Value expression = visitConstExp(ctx.constExp());
            this.visitingConstExp = false;
            return expression;
        }
        return null;
    }

    @Override
    public Value visitVarDecl(MiniSysYParser.VarDeclContext ctx) {
        for (var varDef: ctx.varDef()) {
            this.blockManager.addToCurrent(visitVarDef(varDef));
        }
        return null;
    }

    @Override
    public Value visitVarDef(MiniSysYParser.VarDefContext ctx) {
        Ident ident = Ident.valueOf(ctx.IDENT().getText());
        Register register = this.blockManager.current().putVar(ident);
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
                cur = this.blockManager.current().alloc();
                BinaryOperator operator = BinaryOperator.valueOf(ctx.unaryOp(i).getText());
                this.blockManager.addToCurrent(new BinaryOperationStatement(cur, operand1, operand2, operator));
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
                cur = this.blockManager.current().alloc();
                BinaryOperator operator = BinaryOperator.valueOf(ctx.binaryOp(i).getText());
                this.blockManager.addToCurrent(new BinaryOperationStatement(cur, operand1, operand2, operator));
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
                .filter(unaryOpContext -> unaryOpContext.MINUS() != null || unaryOpContext.NOT() != null)
                .collect(Collectors.toList());
        Register last = null, cur;
        if (validUnaryOpList.size() > 0) {
            for (int i = validUnaryOpList.size() - 1; i >= 0; i--) {
                if (validUnaryOpList.get(i).MINUS() != null) {
                    Operand operand = last != null ? last : expression.getOperand();
                    if (operand.getType() != Type.INT) {
                        cur = this.blockManager.current().alloc();
                        this.blockManager.addToCurrent(new ZextStatement(cur, new OperandExpression(operand), Type.INT));
                        operand = cur;
                    }
                    cur = this.blockManager.current().alloc();
                    this.blockManager.addToCurrent(new BinaryOperationStatement(cur, Literal.INT_ZERO, operand, BinaryOperator.SUB));
                    last = cur;
                } else if (validUnaryOpList.get(i).NOT() != null) {
                    cur = this.blockManager.current().alloc(Type.BIT);
                    Operand operand1 = last != null ? last : expression.getOperand();
                    Operand operand2 = operand1.getType() == Type.BIT ? Literal.BIT_ZERO : Literal.INT_ZERO;
                    this.blockManager.addToCurrent(new IcmpStatement(cur, operand1, operand2, IcmpStatement.CompareType.EQ));
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
            Register register = this.blockManager.current().alloc(f.getFuncType());
            this.blockManager.addToCurrent(new CallStatement(register, f));
            return new OperandExpression(register);
        }
        this.blockManager.addToCurrent(new CallStatement(f));
        return VoidExpression.VOID_EXPRESSION;
    }

    @Override
    public Value visitPrimaryExp(MiniSysYParser.PrimaryExpContext ctx) {
        if (ctx.exp() != null) {
            return visitExp(ctx.exp());
        } else if (ctx.lVal() != null) {
            OperandExpression expression = (OperandExpression) visitLVal(ctx.lVal());
            if (this.visitingConstExp && !expression.isImmutable()) {
                throw new SyntaxException("Assign a variable to constant.");
            }
            return expression;
        } else if (ctx.number() != null) {
            return visitNumber(ctx.number());
        }
        return null;
    }

    @Override
    public Value visitRelExp(MiniSysYParser.RelExpContext ctx) {
        OperandExpression expression = (OperandExpression) visitAddExp(ctx.addExp(0)); // i32
        Register last = null, cur;
        if (ctx.compOp().size() > 0) {
            for (int i = 0; i < ctx.compOp().size(); i++) {
                Operand operand1 = last != null ? last : expression.getOperand();
                Operand operand2 = ((OperandExpression) visitAddExp(ctx.addExp(i + 1))).getOperand();
                if (operand1.getType() == Type.BIT) {
                    cur = this.blockManager.current().alloc(Type.INT);
                    this.blockManager.addToCurrent(new ZextStatement(cur, new OperandExpression(operand1), Type.INT));
                    operand1 = cur;
                }
                cur = this.blockManager.current().alloc(Type.BIT);
                IcmpStatement.CompareType operator = IcmpStatement.CompareType.valueOf(ctx.compOp(i).getText());
                this.blockManager.addToCurrent(new IcmpStatement(cur, operand1, operand2, operator));
                last = cur;
            }
            return new OperandExpression(last);
        }
        return expression;
    }

    @Override
    public Value visitEqExp(MiniSysYParser.EqExpContext ctx) {
        OperandExpression expression = (OperandExpression) visitRelExp(ctx.relExp(0)); // i1 or i32
        Register last = null, cur;
        if (ctx.equalOp().size() > 0) {
            for (int i = 0; i < ctx.equalOp().size(); i++) {
                Operand operand1 = last != null ? last : expression.getOperand();
                Operand operand2 = ((OperandExpression) visitRelExp(ctx.relExp(i + 1))).getOperand();
                if (operand1.getType() == Type.BIT) {
                    cur = this.blockManager.current().alloc(Type.INT);
                    this.blockManager.addToCurrent(new ZextStatement(cur, new OperandExpression(operand1), Type.INT));
                    operand1 = cur;
                }
                cur = this.blockManager.current().alloc(Type.BIT);
                IcmpStatement.CompareType operator = IcmpStatement.CompareType.valueOf(ctx.equalOp(i).getText()); // (i32, i32) to i1
                this.blockManager.addToCurrent(new IcmpStatement(cur, operand1, operand2, operator));
                last = cur;
            }
            return new OperandExpression(last);
        }
        return expression;
    }

    @Override
    public Value visitLAndExp(MiniSysYParser.LAndExpContext ctx) {
        int size = ctx.eqExp().size();
        Block[] blocks = new Block[size + 1];
        for (int i = 0; i < size; i++) {
            blocks[i] = this.blockManager.create("", false);
        }
        blocks[size] = this.condCtx.getBlockTrue();
        for (int i = 0; i < ctx.eqExp().size(); i++) {
            this.blockManager.setCurrent(blocks[i]);
            OperandExpression expression = (OperandExpression) visitEqExp(ctx.eqExp(i));
            this.blockManager.addToCurrent(new BrCondStatement(expression, blocks[i + 1].getBlockRegister(), this.condCtx.getNextOr().getOperand()));
        }
        return new OperandExpression(blocks[0].getBlockRegister());
    }

    @Override
    public Value visitLOrExp(MiniSysYParser.LOrExpContext ctx) {
        int size = ctx.lAndExp().size();
        this.condCtx.setNextOr(new OperandExpression(this.condCtx.getBlockFalse().getBlockRegister()));
        OperandExpression lastExp = (OperandExpression) visitLAndExp(ctx.lAndExp(size - 1));
        for (int i = size - 2; i >= 0; i--) {
            this.condCtx.setNextOr(lastExp);
            lastExp = (OperandExpression) visitLAndExp(ctx.lAndExp(i));
        }
        return lastExp;
    }

    /* 使用左值时访问，赋值不需要 */
    @Override
    public Value visitLVal(MiniSysYParser.LValContext ctx) {
        Ident target = Ident.valueOf(ctx.IDENT().getText());
        Pair<Register, Boolean> pair = this.blockManager.current().compute(target);
        Register tmpRegister = this.blockManager.current().alloc();
        this.blockManager.addToCurrent(new LoadStatement(tmpRegister, pair.a));
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
        return new OperandExpression(new Literal(res), true);
    }
}
