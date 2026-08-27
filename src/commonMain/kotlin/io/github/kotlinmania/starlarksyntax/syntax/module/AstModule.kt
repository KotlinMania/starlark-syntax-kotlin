// port-lint: source src/syntax/module.rs
package io.github.kotlinmania.starlarksyntax.syntax.module

/*
 * Copyright 2018 The Starlark in Rust Authors.
 * Copyright (c) Facebook, Inc. and its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import io.github.kotlinmania.starlarksyntax.codemap.CodeMap
import io.github.kotlinmania.starlarksyntax.codemap.FileSpan
import io.github.kotlinmania.starlarksyntax.codemap.Span
import io.github.kotlinmania.starlarksyntax.codemap.Spanned
import io.github.kotlinmania.starlarkmap.smallmap.SmallMap
import io.github.kotlinmania.starlarksyntax.Dialect
import io.github.kotlinmania.starlarksyntax.evalexception.EvalException
import io.github.kotlinmania.starlarksyntax.syntax.ast.Argument
import io.github.kotlinmania.starlarksyntax.syntax.ast.Assign
import io.github.kotlinmania.starlarksyntax.syntax.ast.AstExpr
import io.github.kotlinmania.starlarksyntax.syntax.ast.AstIdent
import io.github.kotlinmania.starlarksyntax.syntax.ast.AstArgument
import io.github.kotlinmania.starlarksyntax.syntax.ast.AstStmt
import io.github.kotlinmania.starlarksyntax.syntax.ast.BinOp
import io.github.kotlinmania.starlarksyntax.syntax.ast.CallArgs
import io.github.kotlinmania.starlarksyntax.syntax.ast.Def
import io.github.kotlinmania.starlarksyntax.syntax.ast.Expr
import io.github.kotlinmania.starlarksyntax.syntax.ast.For
import io.github.kotlinmania.starlarksyntax.syntax.ast.Ident
import io.github.kotlinmania.starlarksyntax.syntax.ast.LoadArg
import io.github.kotlinmania.starlarksyntax.syntax.ast.Stmt
import io.github.kotlinmania.starlarksyntax.syntax.astload.AstLoad
import io.github.kotlinmania.starlarksyntax.syntax.lintsuppressions.LintSuppressions
import io.github.kotlinmania.starlarksyntax.syntax.state.ParserState
import io.github.kotlinmania.starlarksyntax.syntax.validate.validateModule

internal data class AstModuleParts(
    val codemap: CodeMap,
    val statement: AstStmt,
    val dialect: Dialect,
    val typecheck: Boolean,
)

/**
 * A representation of a Starlark module abstract syntax tree.
 *
 * Constructed externally — the parser lives in the consuming project for now and
 * supplies the constructor with the validated [AstStmt] root.
 *
 * The internal details (statements/expressions) are deliberately omitted, as they change
 * more regularly. A few methods to obtain information about the AST are provided.
 */
class AstModule internal constructor(
    val codemap: CodeMap,
    internal var statement: AstStmt,
    val dialect: Dialect,
    /**
     * Opt-in typecheck.
     * Specified with `@starlark-rust: typecheck`.
     */
    val typecheck: Boolean,
    /**
     * Lint issues suppressed in this module using inline comments of shape
     * `# starlark-lint-disable <ISSUE_NAME>, <ISSUE_NAME>, ...`
     */
    private val lintSuppressions: LintSuppressions,
) {
    internal fun intoParts(): AstModuleParts =
        AstModuleParts(codemap, statement, dialect, typecheck)

    companion object {
        /**
         * Validate a parsed [AstStmt] root and wrap it in an [AstModule], or return the
         * first validation error.
         *
         * Internal until the LALRPOP parser is ported here — at that point a public
         * `parse(filename, content, dialect)` companion will call this helper.
         */
        internal fun fromStatement(
            codemap: CodeMap,
            statement: AstStmt,
            dialect: Dialect,
            typecheck: Boolean,
            lintSuppressions: LintSuppressions,
        ): Result<AstModule> {
            val errors: MutableList<EvalException> = mutableListOf()
            validateModule(
                statement,
                ParserState(dialect, codemap, errors),
            )
            val firstError = errors.firstOrNull()
            if (firstError != null) {
                return Result.failure(firstError)
            }
            return Result.success(
                AstModule(
                    codemap = codemap,
                    statement = statement,
                    dialect = dialect,
                    typecheck = typecheck,
                    lintSuppressions = lintSuppressions,
                )
            )
        }
    }

    /**
     * Return the file names of all the `load` statements in the module.
     * If the [Dialect] had `enableLoad` set to `false` this will be an empty list.
     */
    fun loads(): List<AstLoad> {
        // We know that `load` statements must be at the top-level, so no need to descend inside `if`, `for`, `def` etc.
        // There is a suggestion that `load` statements should be at the top of a file, but we tolerate that not being true.
        val loads = mutableListOf<AstLoad>()
        fun walk(ast: AstStmt) {
            when (val node = ast.node) {
                is Stmt.Load -> {
                    val load = node.load
                    loads.add(
                        AstLoad(
                            span = FileSpan(codemap, load.module.span),
                            moduleId = load.module.node,
                            symbols = SmallMap.fromIterator(
                                load.args.map { arg ->
                                    arg.local.node.ident to arg.their.node
                                },
                            ),
                        )
                    )
                }
                is Stmt.Statements -> {
                    for (stmt in node.stmts) {
                        walk(stmt)
                    }
                }
                else -> {}
            }
        }
        walk(statement)
        return loads
    }

    /** Look up a [Span] contained in this module to a [FileSpan]. */
    fun fileSpan(span: Span): FileSpan = codemap.fileSpan(span)


    /** Locations where statements occur. */
    fun stmtLocations(): List<FileSpan> {
        val res = mutableListOf<FileSpan>()
        fun walk(ast: AstStmt) {
            // These are not interesting statements that come up
            if (ast.node !is Stmt.Statements) {
                res.add(FileSpan(codemap, ast.span))
            }
            // Descend into nested statements.
            visitStmtChildren(ast) { walk(it) }
        }
        walk(statement)
        return res
    }

    /**
     * Function to help people who want to write deeper AST transformations in Starlark.
     * Likely to break type checking and LSP support to some extent.
     *
     * Replacement must be a map from operator name (e.g. `+` or `==`) to a function name
     * (e.g. `myPlus` or `myEquals`).
     */
    fun replaceBinaryOperators(replace: Map<String, String>) {
        statement = rewriteStmt(statement, replace)
    }

    /** Check if a given Lint short name and span is suppressed in this module. */
    fun isSuppressed(issueShortName: String, issueSpan: Span): Boolean {
        return lintSuppressions.isSuppressed(issueShortName, issueSpan)
    }
}

// --- traversal helpers used by [AstModule.stmtLocations] / [AstModule.replaceBinaryOperators] ---

/** Visit immediate child statements of [stmt]. */
private fun visitStmtChildren(stmt: AstStmt, f: (AstStmt) -> Unit) {
    when (val node = stmt.node) {
        is Stmt.Statements -> for (s in node.stmts) f(s)
        is Stmt.If -> f(node.suite)
        is Stmt.IfElse -> {
            f(node.suite1)
            f(node.suite2)
        }
        is Stmt.For -> f(node.forStmt.body)
        is Stmt.Def -> f(node.def.body)
        else -> {}
    }
}

/** Convert a [BinOp] to its operator symbol string (trimmed, no surrounding spaces). */
private fun BinOp.toSymbol(): String = when (this) {
    BinOp.Or -> "or"
    BinOp.And -> "and"
    BinOp.Equal -> "=="
    BinOp.NotEqual -> "!="
    BinOp.Less -> "<"
    BinOp.Greater -> ">"
    BinOp.LessOrEqual -> "<="
    BinOp.GreaterOrEqual -> ">="
    BinOp.In -> "in"
    BinOp.NotIn -> "not in"
    BinOp.Subtract -> "-"
    BinOp.Add -> "+"
    BinOp.Multiply -> "*"
    BinOp.Percent -> "%"
    BinOp.Divide -> "/"
    BinOp.FloorDivide -> "//"
    BinOp.BitAnd -> "&"
    BinOp.BitOr -> "|"
    BinOp.BitXor -> "^"
    BinOp.LeftShift -> "<<"
    BinOp.RightShift -> ">>"
}

/**
 * Rewrite an expression, replacing binary operators according to the [replace] map.
 * If a binary operator's symbol is found in [replace], the Op node is replaced with
 * a Call to the named function, passing the lhs and rhs as positional arguments.
 */
private fun rewriteExpr(
    expr: AstExpr,
    replace: Map<String, String>,
): AstExpr {
    val node = expr.node
    val rewritten: Expr = when (node) {
        is Expr.Op -> {
            val func = replace[node.op.toSymbol()]
            if (func != null) {
                // Replace: Op(lhs, op, rhs) -> Call(Identifier(func), [lhs, rhs])
                val lhs = rewriteExpr(node.left, replace)
                val rhs = rewriteExpr(node.right, replace)
                Expr.Call(
                    target = AstExpr(
                        Expr.Identifier(
                            AstIdent(Ident(func, Unit), expr.span)
                        ),
                        expr.span,
                    ),
                    args = CallArgs(
                        listOf(AstArgument(Argument.Positional(lhs), lhs.span), AstArgument(Argument.Positional(rhs), rhs.span))
                    ),
                )
            } else {
                // Keep Op but rewrite children
                Expr.Op(
                    rewriteExpr(node.left, replace),
                    node.op,
                    rewriteExpr(node.right, replace),
                )
            }
        }
        is Expr.Call -> Expr.Call(
            rewriteExpr(node.target, replace),
            CallArgs(
                node.args.args.map { arg ->
                    AstArgument(rewriteArg(arg.node, replace), arg.span)
                }
            ),
        )
        is Expr.Tuple -> Expr.Tuple(node.elems.map { rewriteExpr(it, replace) })
        is Expr.Dot -> Expr.Dot(rewriteExpr(node.target, replace), node.attr)
        is Expr.Index -> Expr.Index(
            rewriteExpr(node.target, replace),
            rewriteExpr(node.index, replace),
        )
        is Expr.Index2 -> Expr.Index2(
            rewriteExpr(node.target, replace),
            rewriteExpr(node.index0, replace),
            rewriteExpr(node.index1, replace),
        )
        is Expr.Slice -> Expr.Slice(
            rewriteExpr(node.target, replace),
            node.start?.let { rewriteExpr(it, replace) },
            node.stop?.let { rewriteExpr(it, replace) },
            node.step?.let { rewriteExpr(it, replace) },
        )
        is Expr.Not -> Expr.Not(rewriteExpr(node.target, replace))
        is Expr.Minus -> Expr.Minus(rewriteExpr(node.target, replace))
        is Expr.Plus -> Expr.Plus(rewriteExpr(node.target, replace))
        is Expr.BitNot -> Expr.BitNot(rewriteExpr(node.target, replace))
        is Expr.If -> Expr.If(
            rewriteExpr(node.condition, replace),
            rewriteExpr(node.v1, replace),
            rewriteExpr(node.v2, replace),
        )
        is Expr.List -> Expr.List(node.elems.map { rewriteExpr(it, replace) })
        is Expr.Dict -> Expr.Dict(
            node.entries.map { (k, v) ->
                Expr.DictEntry(rewriteExpr(k, replace), rewriteExpr(v, replace))
            }
        )
        is Expr.ListComprehension -> Expr.ListComprehension(
            rewriteExpr(node.expr, replace),
            node.firstFor,
            node.clauses,
        )
        is Expr.DictComprehension -> Expr.DictComprehension(
            rewriteExpr(node.key, replace),
            rewriteExpr(node.value, replace),
            node.firstFor,
            node.clauses,
        )
        // Leaf nodes: no children to rewrite
        is Expr.Identifier -> node
        is Expr.Lambda -> node
        is Expr.Literal -> node
        is Expr.FString -> node
    }
    return AstExpr(rewritten, expr.span)
}

private fun rewriteArg(
    arg: Argument,
    replace: Map<String, String>,
): Argument {
    return when (arg) {
        is Argument.Positional -> Argument.Positional(rewriteExpr(arg.expr, replace))
        is Argument.Named -> Argument.Named(arg.name, rewriteExpr(arg.expr, replace))
        is Argument.Args -> Argument.Args(rewriteExpr(arg.expr, replace))
        is Argument.KwArgs -> Argument.KwArgs(rewriteExpr(arg.expr, replace))
    }
}

/** Rewrite a statement, recursively rewriting all contained expressions. */
private fun rewriteStmt(
    stmt: AstStmt,
    replace: Map<String, String>,
): AstStmt {
    val node = stmt.node
    val rewritten: Stmt = when (node) {
        is Stmt.Statements -> Stmt.Statements(
            node.stmts.map { rewriteStmt(it, replace) },
        )
        is Stmt.Expression -> Stmt.Expression(
            rewriteExpr(node.expr, replace),
        )
        is Stmt.Return -> Stmt.Return(
            node.value?.let { rewriteExpr(it, replace) },
        )
        is Stmt.If -> Stmt.If(
            rewriteExpr(node.cond, replace),
            rewriteStmt(node.suite, replace),
        )
        is Stmt.IfElse -> Stmt.IfElse(
            rewriteExpr(node.cond, replace),
            rewriteStmt(node.suite1, replace),
            rewriteStmt(node.suite2, replace),
        )
        is Stmt.For -> {
            val forStmt = node.forStmt
            Stmt.For(
                For(
                    variable = forStmt.variable,
                    over = rewriteExpr(forStmt.over, replace),
                    body = rewriteStmt(forStmt.body, replace),
                )
            )
        }
        is Stmt.Def -> {
            val def = node.def
            Stmt.Def(
                Def(
                    name = def.name,
                    params = def.params,
                    returnType = def.returnType,
                    body = rewriteStmt(def.body, replace),
                    payload = def.payload,
                )
            )
        }
        is Stmt.Assign -> {
            val assign = node.assign
            Stmt.Assign(
                Assign(
                    lhs = assign.lhs,
                    ty = assign.ty,
                    rhs = rewriteExpr(assign.rhs, replace),
                )
            )
        }
        is Stmt.AssignModify -> Stmt.AssignModify(
            node.lhs,
            node.op,
            rewriteExpr(node.rhs, replace),
        )
        is Stmt.Load -> node
        is Stmt.Break -> node
        is Stmt.Continue -> node
        is Stmt.Pass -> node
    }
    return AstStmt(rewritten, stmt.span)
}
