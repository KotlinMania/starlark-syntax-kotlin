// port-lint: source src/syntax/grammar_util.rs
package io.github.kotlinmania.starlarksyntax.syntax.parser

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

/** Code called by the parser to handle complex cases not handled by the grammar. */

import io.github.kotlinmania.starlarksyntax.codemap.CodeMap
import io.github.kotlinmania.starlarksyntax.codemap.Pos
import io.github.kotlinmania.starlarksyntax.codemap.Span
import io.github.kotlinmania.starlarksyntax.codemap.Spanned
import io.github.kotlinmania.starlarksyntax.DialectTypes
import io.github.kotlinmania.starlarksyntax.dotformatparser.FormatConv
import io.github.kotlinmania.starlarksyntax.dotformatparser.FormatParser
import io.github.kotlinmania.starlarksyntax.dotformatparser.FormatToken
import io.github.kotlinmania.starlarksyntax.evalexception.EvalException
import io.github.kotlinmania.starlarksyntax.lexer.TokenFString
import io.github.kotlinmania.starlarksyntax.lexer.lexExactlyOneIdentifier
import io.github.kotlinmania.starlarksyntax.syntax.ast.Argument
import io.github.kotlinmania.starlarksyntax.syntax.ast.Assign
import io.github.kotlinmania.starlarksyntax.syntax.ast.AssignIdent
import io.github.kotlinmania.starlarksyntax.syntax.ast.AssignOp
import io.github.kotlinmania.starlarksyntax.syntax.ast.AssignTarget
import io.github.kotlinmania.starlarksyntax.syntax.ast.AstArgument
import io.github.kotlinmania.starlarksyntax.syntax.ast.AstAssignIdent
import io.github.kotlinmania.starlarksyntax.syntax.ast.AstAssignTarget
import io.github.kotlinmania.starlarksyntax.syntax.ast.AstExpr
import io.github.kotlinmania.starlarksyntax.syntax.ast.AstFString
import io.github.kotlinmania.starlarksyntax.syntax.ast.AstStmt
import io.github.kotlinmania.starlarksyntax.syntax.ast.AstString
import io.github.kotlinmania.starlarksyntax.syntax.ast.AstTypeExpr
import io.github.kotlinmania.starlarksyntax.syntax.ast.AstComma
import io.github.kotlinmania.starlarksyntax.syntax.ast.CallArgs
import io.github.kotlinmania.starlarksyntax.syntax.ast.Comma
import io.github.kotlinmania.starlarksyntax.syntax.ast.Expr
import io.github.kotlinmania.starlarksyntax.syntax.ast.FString
import io.github.kotlinmania.starlarksyntax.syntax.ast.Ident
import io.github.kotlinmania.starlarksyntax.syntax.ast.Load
import io.github.kotlinmania.starlarksyntax.syntax.ast.LoadArg
import io.github.kotlinmania.starlarksyntax.syntax.ast.Stmt
import io.github.kotlinmania.starlarksyntax.syntax.ast.TypeExpr
import io.github.kotlinmania.starlarksyntax.syntax.ast.ast
import io.github.kotlinmania.starlarksyntax.syntax.call.CallArgsUnpack
import io.github.kotlinmania.starlarksyntax.syntax.state.ParserState
import io.github.kotlinmania.starlarksyntax.syntax.typeexpr.TypeExprUnpack

private enum class GrammarUtilError(val message: String) {
    /** `left-hand-side of assignment must take the form `a`, `a.b` or `a[b]`` */
    InvalidLhs("left-hand-side of assignment must take the form `a`, `a.b` or `a[b]`"),

    /** `left-hand-side of modifying assignment cannot be a list or tuple` */
    InvalidModifyLhs("left-hand-side of modifying assignment cannot be a list or tuple"),

    /** `type annotations not allowed on augmented assignments` */
    TypeAnnotationOnAssignOp("type annotations not allowed on augmented assignments"),

    /** `type annotations not allowed on multiple assignments` */
    TypeAnnotationOnTupleAssign("type annotations not allowed on multiple assignments"),

    /** ``load` statement requires at least two arguments` */
    LoadRequiresAtLeastTwoArguments("`load` statement requires at least two arguments"),

    /** `unparenthesized tuple with trailing comma` */
    UnparenthesizedTupleTrailingComma("unparenthesized tuple with trailing comma"),
}

/** Ensure we produce normalised Statements, rather than singleton Statements. */
fun statements(xs: List<AstStmt>, begin: Int, end: Int): AstStmt {
    return if (xs.size == 1) {
        xs[0]
    } else {
        Stmt.Statements(xs).ast(begin, end)
    }
}

fun checkAssign(codemap: CodeMap, x: AstExpr): AstAssignTarget {
    val node: AssignTarget = when (val expr = x.node) {
        is Expr.Tuple -> AssignTarget.Tuple(
            expr.elems.map { checkAssign(codemap, it) }
        )
        is Expr.List -> AssignTarget.Tuple(
            expr.elems.map { checkAssign(codemap, it) }
        )
        is Expr.Dot -> AssignTarget.Dot(expr.target, expr.attr)
        is Expr.Index -> AssignTarget.Index(expr.target, expr.index)
        is Expr.Identifier -> AssignTarget.Identifier(
            AstAssignIdent(AssignIdent(ident = expr.ident.node.ident, payload = Unit), expr.ident.span)
        )
        else -> throw EvalException.newAnyhow(
            Throwable(GrammarUtilError.InvalidLhs.message),
            x.span,
            codemap,
        )
    }
    return AstAssignTarget(node, x.span)
}

fun checkAssignment(
    codemap: CodeMap,
    lhs: AstExpr,
    ty: AstTypeExpr?,
    op: AssignOp?,
    rhs: AstExpr,
): Stmt {
    if (op != null) {
        // for augmented assignment, Starlark doesn't allow tuple/list
        when (lhs.node) {
            is Expr.Tuple, is Expr.List -> throw EvalException.newAnyhow(
                Throwable(GrammarUtilError.InvalidModifyLhs.message),
                lhs.span,
                codemap,
            )
            else -> {}
        }
    }
    val assignTarget = checkAssign(codemap, lhs)
    if (ty != null) {
        val err = if (op != null) {
            GrammarUtilError.TypeAnnotationOnAssignOp
        } else if (assignTarget.node is AssignTarget.Tuple) {
            GrammarUtilError.TypeAnnotationOnTupleAssign
        } else {
            null
        }
        if (err != null) {
            throw EvalException.newAnyhow(
                Throwable(err.message),
                ty.span,
                codemap,
            )
        }
    }
    return when (op) {
        null -> Stmt.Assign(
            Assign(
                lhs = assignTarget,
                ty = ty,
                rhs = rhs,
            )
        )
        else -> Stmt.AssignModify(assignTarget, op, rhs)
    }
}

internal fun <T> rejectUnparenthesizedTupleTrailingComma(
    codemap: CodeMap,
    begin: Int,
    end: Int,
): T {
    throw EvalException.newAnyhow(
        Throwable(GrammarUtilError.UnparenthesizedTupleTrailingComma.message),
        Span.new(Pos.new(begin), Pos.new(end)),
        codemap,
    )
}

internal fun checkLoad0(module: AstString, parserState: ParserState): Stmt {
    parserState.errors.add(
        EvalException.newAnyhow(
            Throwable(GrammarUtilError.LoadRequiresAtLeastTwoArguments.message),
            module.span,
            parserState.codemap,
        )
    )
    return Stmt.Load(
        Load(
            module = module,
            args = emptyList(),
            payload = Unit,
        )
    )
}

internal fun checkLoad(
    module: AstString,
    args: List<Pair<Pair<AstAssignIdent, AstString>, AstComma>>,
    last: Pair<AstAssignIdent, AstString>?,
    parserState: ParserState,
): Stmt {
    if (args.isEmpty() && last == null) {
        return checkLoad0(module, parserState)
    }

    val loadArgs: List<LoadArg> = args.map { (localTheir, comma) ->
        val (local, their) = localTheir
        LoadArg(
            local = local,
            their = their,
            comma = comma,
        )
    } + if (last != null) {
        listOf(
            LoadArg(
                local = last.first,
                their = last.second,
                comma = null,
            )
        )
    } else {
        emptyList()
    }

    return Stmt.Load(
        Load(
            module = module,
            args = loadArgs,
            payload = Unit,
        )
    )
}

internal fun fstring(
    fstring: TokenFString,
    begin: Int,
    end: Int,
    parserState: ParserState,
): AstFString {
    if (!parserState.dialect.enableFStrings) {
        parserState.error(
            Span.new(Pos.new(begin), Pos.new(end)),
            "Your Starlark dialect must enable f-strings to use them",
        )
    }

    val content = fstring.content
    val contentStartOffset = fstring.contentStartOffset

    val format = StringBuilder(content.length)
    val expressions = mutableListOf<AstExpr>()

    val parser = FormatParser(content)
    while (true) {
        val res = parser.next()
        val token = res.getOrElse { e ->
            // TODO: Reporting the exact position of the error would be better.
            parserState.error(
                Span.new(Pos.new(begin), Pos.new(end)),
                "Invalid format: ${e.message}",
            )
            break
        } ?: break
        when (token) {
            is FormatToken.Text -> format.append(token.text)
            is FormatToken.Escape -> {
                // We are producing a format string here so we need to escape this back!
                format.append(token.escape.backToEscape())
            }
            is FormatToken.Capture -> {
                val captureBegin = begin + contentStartOffset + token.pos
                val captureEnd = captureBegin + token.capture.length

                val ident = lexExactlyOneIdentifier(token.capture)
                if (ident == null) {
                    parserState.error(
                        Span.new(Pos.new(captureBegin), Pos.new(captureEnd)),
                        "Not a valid identifier: `${token.capture}`",
                    )
                    // Might as well keep going here. This doesn't compromise the parsing of
                    // the rest of the format string.
                    continue
                }

                val expr: AstExpr = Expr.Identifier(
                    Ident(ident = ident, payload = Unit).ast(captureBegin, captureEnd)
                ).ast(captureBegin, captureEnd)
                expressions.add(expr)
                // Positional format.
                when (token.conv) {
                    FormatConv.STR -> format.append("{}")
                    FormatConv.REPR -> format.append("{!r}")
                }
            }
        }
    }

    return FString(
        format = format.toString().ast(begin, end),
        expressions = expressions,
    ).ast(begin, end)
}

private enum class DialectError(val message: String) {
    /** `type annotations are not allowed in this dialect` */
    Types("type annotations are not allowed in this dialect"),
}

private fun <T> err(codemap: CodeMap, span: Span, err: DialectError): T {
    throw EvalException.newAnyhow(Throwable(err.message), span, codemap)
}

internal fun dialectCheckType(
    state: ParserState,
    x: AstExpr,
): AstTypeExpr {
    if (state.dialect.enableTypes == DialectTypes.Disable) {
        err<Unit>(state.codemap, x.span, DialectError.Types)
    }

    // Validate the type expression.
    TypeExprUnpack.unpack(x, state.codemap)

    return AstTypeExpr(
        TypeExpr(
            expr = x,
            payload = Unit,
        ),
        x.span,
    )
}

internal fun checkCall(
    e: AstExpr,
    a: List<AstArgument>,
    state: ParserState,
): Expr {
    val args = CallArgs(args = a)

    val unpackResult = CallArgsUnpack.unpack(args, state.codemap)
    unpackResult.exceptionOrNull()?.let { ex ->
        if (ex is EvalException) state.errors.add(ex) else throw ex
    }

    return Expr.Call(e, args)
}

