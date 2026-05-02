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
import io.github.kotlinmania.starlarksyntax.dialect.Dialect
import io.github.kotlinmania.starlarksyntax.evalexception.EvalException
import io.github.kotlinmania.starlarksyntax.lexer.Lexer
import io.github.kotlinmania.starlarksyntax.lexer.Token
import io.github.kotlinmania.starlarksyntax.syntax.ast.ArgumentP
import io.github.kotlinmania.starlarksyntax.syntax.ast.AssignP
import io.github.kotlinmania.starlarksyntax.syntax.ast.AstExpr
import io.github.kotlinmania.starlarksyntax.syntax.ast.AstNoPayload
import io.github.kotlinmania.starlarksyntax.syntax.ast.AstStmt
import io.github.kotlinmania.starlarksyntax.syntax.ast.BinOp
import io.github.kotlinmania.starlarksyntax.syntax.ast.CallArgsP
import io.github.kotlinmania.starlarksyntax.syntax.ast.DefP
import io.github.kotlinmania.starlarksyntax.syntax.ast.ExprP
import io.github.kotlinmania.starlarksyntax.syntax.ast.ForP
import io.github.kotlinmania.starlarksyntax.syntax.ast.IdentP
import io.github.kotlinmania.starlarksyntax.syntax.ast.LoadArgP
import io.github.kotlinmania.starlarksyntax.syntax.ast.StmtP
import io.github.kotlinmania.starlarksyntax.syntax.lintsuppressions.LintSuppressions
import io.github.kotlinmania.starlarksyntax.syntax.lintsuppressions.LintSuppressionsBuilder
import io.github.kotlinmania.starlarksyntax.syntax.parser.LalrpopParser
import io.github.kotlinmania.starlarksyntax.syntax.parser.Lexeme
import io.github.kotlinmania.starlarksyntax.syntax.state.ParserState
import io.github.kotlinmania.starlarksyntax.syntax.validate.validateModule

/** Symbol pair from a `load` statement: `(local, their)`. */
class AstLoad(
    val span: FileSpan,
    val moduleId: String,
    val symbols: Map<String, String>,
)

data class AstModuleParts(
    val codemap: CodeMap,
    val statement: AstStmt,
    val dialect: Dialect,
    val typecheck: Boolean,
)

/**
 * A representation of a Starlark module abstract syntax tree.
 *
 * Created with either [parse] or [parseFile],
 * and evaluated with `Evaluator.evalModule`.
 *
 * The internal details (statements/expressions) are deliberately omitted, as they change
 * more regularly. A few methods to obtain information about the AST are provided.
 */
class AstModule internal constructor(
    val codemap: CodeMap,
    var statement: AstStmt,
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
    fun intoParts(): AstModuleParts =
        AstModuleParts(codemap, statement, dialect, typecheck)

    companion object {
        private fun create(
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
            // We need the first error, so we don't use `removeLast()`.
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

        /**
         * Parse a Starlark module to produce an [AstModule], or an error if there are syntax errors.
         * The [filename] is for error messages only, and does not have to be a valid file.
         * The [Dialect] selects which Starlark constructs are valid.
         *
         * The returned error may contain diagnostic information.
         */
        fun parse(filename: String, content: String, dialect: Dialect): Result<AstModule> {
            val typecheck = content.contains("@starlark-rust: typecheck")
            val codemap = CodeMap.new(filename, content)
            val lexer = Lexer(content, dialect, codemap)
            // Store lint suppressions found during parsing
            val lintSuppressionsBuilder = LintSuppressionsBuilder()
            // Keep track of block of comments, used for accumulating lint suppressions
            var inCommentBlock = false
            val errors: MutableList<EvalException> = mutableListOf()
            val parserState = ParserState(dialect, codemap, errors)
            val filteredTokens: Iterator<Lexeme> = lexer.asSequence()
                .mapNotNull { triple ->
                    val (start, token, end) = triple
                    if (token is Token.Comment) {
                        lintSuppressionsBuilder.parseComment(codemap, token.text, start, end)
                        inCommentBlock = true
                        null
                    } else {
                        if (inCommentBlock) {
                            lintSuppressionsBuilder.endOfCommentBlock(codemap)
                            inCommentBlock = false
                        }
                        Result.success(triple)
                    }
                }
                .iterator()
            val parsed = LalrpopParser.parseModule(parserState, filteredTokens, content.length)
            return parsed.fold(
                onSuccess = { v ->
                    val firstError = errors.firstOrNull()
                    if (firstError != null) {
                        Result.failure(firstError)
                    } else {
                        create(codemap, v, dialect, typecheck, lintSuppressionsBuilder.build())
                    }
                },
                onFailure = { e -> Result.failure(e) },
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
                is StmtP.Load<AstNoPayload> -> {
                    val load = node.load
                    loads.add(
                        AstLoad(
                            span = FileSpan(codemap, load.module.span),
                            moduleId = load.module.node,
                            symbols = load.args.associate { arg ->
                                arg.local.node.ident to arg.their.node
                            },
                        )
                    )
                }
                is StmtP.Statements<AstNoPayload> -> {
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

    /** Get back the AST statement for the module. */
    fun statement(): AstStmt = statement

    /** Locations where statements occur. */
    fun stmtLocations(): List<FileSpan> {
        val res = mutableListOf<FileSpan>()
        fun walk(ast: AstStmt) {
            // These are not interesting statements that come up
            if (ast.node !is StmtP.Statements<AstNoPayload>) {
                res.add(FileSpan(codemap, ast.span))
            }
            // Descend into nested statements (the upstream uses `visit_stmt`).
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

    /** Check if a given Lint short_name and span is suppressed in this module. */
    fun isSuppressed(issueShortName: String, issueSpan: Span): Boolean {
        return lintSuppressions.isSuppressed(issueShortName, issueSpan)
    }
}

// --- traversal helpers used by stmt_locations / replace_binary_operators ---

/** Visit immediate child statements of [stmt]. Mirrors upstream `visit_stmt`. */
private fun visitStmtChildren(stmt: AstStmt, f: (AstStmt) -> Unit) {
    when (val node = stmt.node) {
        is StmtP.Statements<AstNoPayload> -> for (s in node.stmts) f(s)
        is StmtP.If<AstNoPayload> -> f(node.suite)
        is StmtP.IfElse<AstNoPayload> -> {
            f(node.suite1)
            f(node.suite2)
        }
        is StmtP.For<AstNoPayload> -> f(node.forStmt.body)
        is StmtP.Def<AstNoPayload> -> f(node.def.body)
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
    val rewritten: ExprP<AstNoPayload> = when (node) {
        is ExprP.Op<AstNoPayload> -> {
            val func = replace[node.op.toSymbol()]
            if (func != null) {
                // Replace: Op(lhs, op, rhs) -> Call(Identifier(func), [lhs, rhs])
                val lhs = rewriteExpr(node.left, replace)
                val rhs = rewriteExpr(node.right, replace)
                ExprP.Call(
                    target = Spanned(
                        ExprP.Identifier(
                            Spanned(IdentP<AstNoPayload>(func, Unit), expr.span)
                        ),
                        expr.span,
                    ),
                    args = CallArgsP(
                        listOf(
                            Spanned(ArgumentP.Positional(lhs), lhs.span),
                            Spanned(ArgumentP.Positional(rhs), rhs.span),
                        )
                    ),
                )
            } else {
                // Keep Op but rewrite children
                ExprP.Op(
                    rewriteExpr(node.left, replace),
                    node.op,
                    rewriteExpr(node.right, replace),
                )
            }
        }
        is ExprP.Call<AstNoPayload> -> ExprP.Call(
            rewriteExpr(node.target, replace),
            CallArgsP(
                node.args.args.map { arg ->
                    Spanned(rewriteArg(arg.node, replace), arg.span)
                }
            ),
        )
        is ExprP.Tuple<AstNoPayload> -> ExprP.Tuple(node.elems.map { rewriteExpr(it, replace) })
        is ExprP.Dot<AstNoPayload> -> ExprP.Dot(rewriteExpr(node.target, replace), node.attr)
        is ExprP.Index<AstNoPayload> -> ExprP.Index(
            rewriteExpr(node.target, replace),
            rewriteExpr(node.index, replace),
        )
        is ExprP.Index2<AstNoPayload> -> ExprP.Index2(
            rewriteExpr(node.target, replace),
            rewriteExpr(node.index0, replace),
            rewriteExpr(node.index1, replace),
        )
        is ExprP.Slice<AstNoPayload> -> ExprP.Slice(
            rewriteExpr(node.target, replace),
            node.start?.let { rewriteExpr(it, replace) },
            node.stop?.let { rewriteExpr(it, replace) },
            node.step?.let { rewriteExpr(it, replace) },
        )
        is ExprP.Not<AstNoPayload> -> ExprP.Not(rewriteExpr(node.target, replace))
        is ExprP.Minus<AstNoPayload> -> ExprP.Minus(rewriteExpr(node.target, replace))
        is ExprP.Plus<AstNoPayload> -> ExprP.Plus(rewriteExpr(node.target, replace))
        is ExprP.BitNot<AstNoPayload> -> ExprP.BitNot(rewriteExpr(node.target, replace))
        is ExprP.If<AstNoPayload> -> ExprP.If(
            rewriteExpr(node.condition, replace),
            rewriteExpr(node.v1, replace),
            rewriteExpr(node.v2, replace),
        )
        is ExprP.List<AstNoPayload> -> ExprP.List(node.elems.map { rewriteExpr(it, replace) })
        is ExprP.Dict<AstNoPayload> -> ExprP.Dict(
            node.entries.map { (k, v) ->
                Pair(rewriteExpr(k, replace), rewriteExpr(v, replace))
            }
        )
        is ExprP.ListComprehension<AstNoPayload> -> ExprP.ListComprehension(
            rewriteExpr(node.expr, replace),
            node.firstFor,
            node.clauses,
        )
        is ExprP.DictComprehension<AstNoPayload> -> ExprP.DictComprehension(
            rewriteExpr(node.key, replace),
            rewriteExpr(node.value, replace),
            node.firstFor,
            node.clauses,
        )
        // Leaf nodes: no children to rewrite
        is ExprP.Identifier<AstNoPayload> -> node
        is ExprP.Lambda<AstNoPayload> -> node
        is ExprP.Literal<AstNoPayload> -> node
        is ExprP.FString<AstNoPayload> -> node
    }
    return Spanned(rewritten, expr.span)
}

private fun rewriteArg(
    arg: ArgumentP<AstNoPayload>,
    replace: Map<String, String>,
): ArgumentP<AstNoPayload> {
    return when (arg) {
        is ArgumentP.Positional<AstNoPayload> -> ArgumentP.Positional(rewriteExpr(arg.expr, replace))
        is ArgumentP.Named<AstNoPayload> -> ArgumentP.Named(arg.name, rewriteExpr(arg.expr, replace))
        is ArgumentP.Args<AstNoPayload> -> ArgumentP.Args(rewriteExpr(arg.expr, replace))
        is ArgumentP.KwArgs<AstNoPayload> -> ArgumentP.KwArgs(rewriteExpr(arg.expr, replace))
    }
}

/** Rewrite a statement, recursively rewriting all contained expressions. */
private fun rewriteStmt(
    stmt: AstStmt,
    replace: Map<String, String>,
): AstStmt {
    val node = stmt.node
    val rewritten: StmtP<AstNoPayload> = when (node) {
        is StmtP.Statements<AstNoPayload> -> StmtP.Statements(
            node.stmts.map { rewriteStmt(it, replace) },
        )
        is StmtP.Expression<AstNoPayload> -> StmtP.Expression(
            rewriteExpr(node.expr, replace),
        )
        is StmtP.Return<AstNoPayload> -> StmtP.Return(
            node.value?.let { rewriteExpr(it, replace) },
        )
        is StmtP.If<AstNoPayload> -> StmtP.If(
            rewriteExpr(node.cond, replace),
            rewriteStmt(node.suite, replace),
        )
        is StmtP.IfElse<AstNoPayload> -> StmtP.IfElse(
            rewriteExpr(node.cond, replace),
            rewriteStmt(node.suite1, replace),
            rewriteStmt(node.suite2, replace),
        )
        is StmtP.For<AstNoPayload> -> {
            val forStmt = node.forStmt
            StmtP.For(
                ForP(
                    variable = forStmt.variable,
                    over = rewriteExpr(forStmt.over, replace),
                    body = rewriteStmt(forStmt.body, replace),
                )
            )
        }
        is StmtP.Def<AstNoPayload> -> {
            val def = node.def
            StmtP.Def(
                DefP(
                    name = def.name,
                    params = def.params,
                    returnType = def.returnType,
                    body = rewriteStmt(def.body, replace),
                    payload = def.payload,
                )
            )
        }
        is StmtP.Assign<AstNoPayload> -> {
            val assign = node.assign
            StmtP.Assign(
                AssignP(
                    lhs = assign.lhs,
                    ty = assign.ty,
                    rhs = rewriteExpr(assign.rhs, replace),
                )
            )
        }
        is StmtP.AssignModify<AstNoPayload> -> StmtP.AssignModify(
            node.lhs,
            node.op,
            rewriteExpr(node.rhs, replace),
        )
        is StmtP.Load<AstNoPayload> -> node
        is StmtP.Break<AstNoPayload> -> node
        is StmtP.Continue<AstNoPayload> -> node
        is StmtP.Pass<AstNoPayload> -> node
    }
    return Spanned(rewritten, stmt.span)
}
