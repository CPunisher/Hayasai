package com.cpunisher.hayasai.frontend;// Generated from MiniSysY.g4 by ANTLR 4.9.2
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
	 * Visit a parse tree produced by {@link MiniSysYParser#ident}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdent(MiniSysYParser.IdentContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniSysYParser#block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlock(MiniSysYParser.BlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniSysYParser#stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStmt(MiniSysYParser.StmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniSysYParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExp(MiniSysYParser.ExpContext ctx);
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
	 * Visit a parse tree produced by {@link MiniSysYParser#primaryExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimaryExp(MiniSysYParser.PrimaryExpContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniSysYParser#number}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNumber(MiniSysYParser.NumberContext ctx);
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