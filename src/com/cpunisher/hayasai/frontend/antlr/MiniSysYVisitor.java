package com.cpunisher.hayasai.frontend.antlr;// Generated from MiniSysY.g4 by ANTLR 4.9.2
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link MiniSysYParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface MiniSysYVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link MiniSysYParser#compUnit}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCompUnit(MiniSysYParser.CompUnitContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniSysYParser#decl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDecl(MiniSysYParser.DeclContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniSysYParser#btype}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBtype(MiniSysYParser.BtypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniSysYParser#constDecl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstDecl(MiniSysYParser.ConstDeclContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniSysYParser#constDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstDef(MiniSysYParser.ConstDefContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniSysYParser#constInitVal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstInitVal(MiniSysYParser.ConstInitValContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniSysYParser#constExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstExp(MiniSysYParser.ConstExpContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniSysYParser#varDecl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarDecl(MiniSysYParser.VarDeclContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniSysYParser#varDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarDef(MiniSysYParser.VarDefContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniSysYParser#initVal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInitVal(MiniSysYParser.InitValContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniSysYParser#funcDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFuncDef(MiniSysYParser.FuncDefContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniSysYParser#funcType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFuncType(MiniSysYParser.FuncTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniSysYParser#funcFParams}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFuncFParams(MiniSysYParser.FuncFParamsContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniSysYParser#funcFParam}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFuncFParam(MiniSysYParser.FuncFParamContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniSysYParser#block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlock(MiniSysYParser.BlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniSysYParser#blockItem}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlockItem(MiniSysYParser.BlockItemContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniSysYParser#stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStmt(MiniSysYParser.StmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniSysYParser#assignStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignStmt(MiniSysYParser.AssignStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniSysYParser#ifStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfStmt(MiniSysYParser.IfStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniSysYParser#whileStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhileStmt(MiniSysYParser.WhileStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniSysYParser#breakStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBreakStmt(MiniSysYParser.BreakStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniSysYParser#continueStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitContinueStmt(MiniSysYParser.ContinueStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniSysYParser#retStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRetStmt(MiniSysYParser.RetStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniSysYParser#expStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpStmt(MiniSysYParser.ExpStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniSysYParser#lVal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLVal(MiniSysYParser.LValContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniSysYParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExp(MiniSysYParser.ExpContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniSysYParser#cond}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCond(MiniSysYParser.CondContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniSysYParser#addExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAddExp(MiniSysYParser.AddExpContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniSysYParser#mulExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMulExp(MiniSysYParser.MulExpContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniSysYParser#unaryExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnaryExp(MiniSysYParser.UnaryExpContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniSysYParser#funcCall}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFuncCall(MiniSysYParser.FuncCallContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniSysYParser#funcRParams}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFuncRParams(MiniSysYParser.FuncRParamsContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniSysYParser#primaryExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimaryExp(MiniSysYParser.PrimaryExpContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniSysYParser#relExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRelExp(MiniSysYParser.RelExpContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniSysYParser#eqExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEqExp(MiniSysYParser.EqExpContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniSysYParser#lAndExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLAndExp(MiniSysYParser.LAndExpContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniSysYParser#lOrExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLOrExp(MiniSysYParser.LOrExpContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniSysYParser#number}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNumber(MiniSysYParser.NumberContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniSysYParser#compOp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCompOp(MiniSysYParser.CompOpContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniSysYParser#equalOp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEqualOp(MiniSysYParser.EqualOpContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniSysYParser#unaryOp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnaryOp(MiniSysYParser.UnaryOpContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniSysYParser#binaryOp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBinaryOp(MiniSysYParser.BinaryOpContext ctx);
}