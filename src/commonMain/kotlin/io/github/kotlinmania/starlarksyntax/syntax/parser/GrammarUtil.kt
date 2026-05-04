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

//! Code called by the parser to handle complex cases not handled by the grammar.

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
import io.github.kotlinmania.starlarksyntax.syntax.ast.ArgumentP
import io.github.kotlinmania.starlarksyntax.syntax.ast.AssignIdentP
import io.github.kotlinmania.starlarksyntax.syntax.ast.AssignOp
import io.github.kotlinmania.starlarksyntax.syntax.ast.AssignP
import io.github.kotlinmania.starlarksyntax.syntax.ast.AssignTargetP
import io.github.kotlinmania.starlarksyntax.syntax.ast.AstAssignIdent
import io.github.kotlinmania.starlarksyntax.syntax.ast.AstAssignTarget
import io.github.kotlinmania.starlarksyntax.syntax.ast.AstExpr
import io.github.kotlinmania.starlarksyntax.syntax.ast.AstFString
import io.github.kotlinmania.starlarksyntax.syntax.ast.AstNoPayload
import io.github.kotlinmania.starlarksyntax.syntax.ast.AstStmt
import io.github.kotlinmania.starlarksyntax.syntax.ast.AstString
import io.github.kotlinmania.starlarksyntax.syntax.ast.AstTypeExpr
import io.github.kotlinmania.starlarksyntax.syntax.ast.CallArgsP
import io.github.kotlinmania.starlarksyntax.syntax.ast.Comma
import io.github.kotlinmania.starlarksyntax.syntax.ast.ExprP
import io.github.kotlinmania.starlarksyntax.syntax.ast.FStringP
import io.github.kotlinmania.starlarksyntax.syntax.ast.IdentP
import io.github.kotlinmania.starlarksyntax.syntax.ast.LoadArgP
import io.github.kotlinmania.starlarksyntax.syntax.ast.LoadP
import io.github.kotlinmania.starlarksyntax.syntax.ast.Stmt
import io.github.kotlinmania.starlarksyntax.syntax.ast.StmtP
import io.github.kotlinmania.starlarksyntax.syntax.ast.TypeExprP
import io.github.kotlinmania.starlarksyntax.syntax.ast.ast
import io.github.kotlinmania.starlarksyntax.syntax.call.CallArgsUnpack
import io.github.kotlinmania.starlarksyntax.syntax.state.ParserState
import io.github.kotlinmania.starlarksyntax.syntax.typeexpr.TypeExprUnpackP

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
        StmtP.Statements<AstNoPayload>(xs).ast(begin, end)
    }
}

fun checkAssign(codemap: CodeMap, x: AstExpr): AstAssignTarget {
    val node: AssignTargetP<AstNoPayload> = when (val expr = x.node) {
        is ExprP.Tuple<AstNoPayload> -> AssignTargetP.Tuple(
            expr.elems.map { checkAssign(codemap, it) }
        )
        is ExprP.List<AstNoPayload> -> AssignTargetP.Tuple(
            expr.elems.map { checkAssign(codemap, it) }
        )
        is ExprP.Dot<AstNoPayload> -> AssignTargetP.Dot(expr.target, expr.attr)
        is ExprP.Index<AstNoPayload> -> AssignTargetP.Index(expr.target, expr.index)
        is ExprP.Identifier<AstNoPayload> -> AssignTargetP.Identifier(
            expr.ident.map { s ->
                AssignIdentP<AstNoPayload>(
                    ident = s.ident,
                    payload = Unit,
                )
            }
        )
        else -> throw EvalException.newAnyhow(
            Throwable(GrammarUtilError.InvalidLhs.message),
            x.span,
            codemap,
        )
    }
    return Spanned(node, x.span)
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
            is ExprP.Tuple<AstNoPayload>, is ExprP.List<AstNoPayload> -> throw EvalException.newAnyhow(
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
        } else if (assignTarget.node is AssignTargetP.Tuple<AstNoPayload>) {
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
        null -> StmtP.Assign(
            AssignP(
                lhs = assignTarget,
                ty = ty,
                rhs = rhs,
            )
        )
        else -> StmtP.AssignModify(assignTarget, op, rhs)
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
    return StmtP.Load(
        LoadP(
            module = module,
            args = emptyList(),
            payload = Unit,
        )
    )
}

internal fun checkLoad(
    module: AstString,
    args: List<Pair<Pair<AstAssignIdent, AstString>, Spanned<Comma>>>,
    last: Pair<AstAssignIdent, AstString>?,
    parserState: ParserState,
): Stmt {
    if (args.isEmpty() && last == null) {
        return checkLoad0(module, parserState)
    }

    val loadArgs: List<LoadArgP<AstNoPayload>> = args.map { (localTheir, comma) ->
        val (local, their) = localTheir
        LoadArgP<AstNoPayload>(
            local = local,
            their = their,
            comma = comma,
        )
    } + if (last != null) {
        listOf(
            LoadArgP<AstNoPayload>(
                local = last.first,
                their = last.second,
                comma = null,
            )
        )
    } else {
        emptyList()
    }

    return StmtP.Load(
        LoadP(
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

                val expr: AstExpr = ExprP.Identifier<AstNoPayload>(
                    IdentP<AstNoPayload>(ident = ident, payload = Unit).ast(captureBegin, captureEnd)
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

    return FStringP<AstNoPayload>(
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
): Spanned<TypeExprP<AstNoPayload>> {
    val span = x.span
    if (state.dialect.enableTypes == DialectTypes.Disable) {
        err<Unit>(state.codemap, x.span, DialectError.Types)
    }

    // Validate the type expression.
    TypeExprUnpackP.unpack<AstNoPayload>(x, state.codemap)

    return x.map { node ->
        TypeExprP<AstNoPayload>(
            expr = Spanned(node, span),
            payload = Unit,
        )
    }
}

internal fun checkCall(
    e: AstExpr,
    a: List<Spanned<ArgumentP<AstNoPayload>>>,
    state: ParserState,
): ExprP<AstNoPayload> {
    val args = CallArgsP<AstNoPayload>(args = a)

    val unpackResult = CallArgsUnpack.unpack(args, state.codemap)
    unpackResult.exceptionOrNull()?.let { ex ->
        if (ex is EvalException) state.errors.add(ex) else throw ex
    }

    return ExprP.Call(e, args)
}
