// port-lint: source src/syntax/ast.rs
package io.github.kotlinmania.starlarksyntax.syntax.ast

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

//! AST for parsed starlark files.

import io.github.kotlinmania.starlarksyntax.codemap.Pos
import io.github.kotlinmania.starlarksyntax.codemap.Span
import io.github.kotlinmania.starlarksyntax.codemap.Spanned
import io.github.kotlinmania.starlarksyntax.lexer.TokenInt

/** Payload types attached to AST nodes. */
interface AstPayload {
    // TODO(nga): we don't really need `Clone` for any payload.
    // The Rust upstream encodes the five payload kinds as associated types on the trait.
    // Kotlin doesn't have associated types, so we expose them as type parameters on the
    // generic AST nodes (e.g. `ExprP<P>` becomes generic over a payload-bundle type) and
    // each concrete payload struct supplies the five payload values.
}

/**
 * Default implementation of payload, which attaches `Unit` to nodes.
 * This payload is returned with AST by parser.
 */
object AstNoPayload : AstPayload

/** `,` token. */
class Comma

typealias Expr = ExprP<AstNoPayload>
typealias TypeExpr = TypeExprP<AstNoPayload>
typealias AssignTarget = AssignTargetP<AstNoPayload>
typealias AssignIdent = AssignIdentP<AstNoPayload>
typealias Ident = IdentP<AstNoPayload>
typealias Clause = ClauseP<AstNoPayload>
typealias ForClause = ForClauseP<AstNoPayload>
typealias Argument = ArgumentP<AstNoPayload>
typealias Parameter = ParameterP<AstNoPayload>
typealias Load = LoadP<AstNoPayload>
typealias Stmt = StmtP<AstNoPayload>

// Boxed types used for storing information from the parsing will be used
// especially for the location of the AST item
typealias AstExprP<P> = Spanned<ExprP<P>>
typealias AstTypeExprP<P> = Spanned<TypeExprP<P>>
typealias AstAssignTargetP<P> = Spanned<AssignTargetP<P>>
typealias AstAssignIdentP<P> = Spanned<AssignIdentP<P>>
typealias AstIdentP<P> = Spanned<IdentP<P>>
typealias AstArgumentP<P> = Spanned<ArgumentP<P>>
typealias AstParameterP<P> = Spanned<ParameterP<P>>
typealias AstStmtP<P> = Spanned<StmtP<P>>
typealias AstFStringP<P> = Spanned<FStringP<P>>

typealias AstExpr = AstExprP<AstNoPayload>
typealias AstTypeExpr = AstTypeExprP<AstNoPayload>
typealias AstAssignTarget = AstAssignTargetP<AstNoPayload>
typealias AstAssignIdent = AstAssignIdentP<AstNoPayload>
typealias AstIdent = AstIdentP<AstNoPayload>
typealias AstArgument = AstArgumentP<AstNoPayload>
typealias AstString = Spanned<String>
typealias AstParameter = AstParameterP<AstNoPayload>
typealias AstInt = Spanned<TokenInt>
typealias AstFloat = Spanned<Double>
typealias AstFString = AstFStringP<AstNoPayload>
typealias AstStmt = AstStmtP<AstNoPayload>

// A trait rather than a function to allow .ast() chaining in the parser.
/** Wrap a value with a [Span] computed from `begin..end` byte offsets. */
fun <T> T.ast(begin: Int, end: Int): Spanned<T> = Spanned(
    node = this,
    span = Span.new(Pos.new(begin), Pos.new(end)),
)

sealed class ArgumentP<P : AstPayload> {
    class Positional<P : AstPayload>(val expr: AstExprP<P>) : ArgumentP<P>()
    class Named<P : AstPayload>(val name: AstString, val expr: AstExprP<P>) : ArgumentP<P>()
    class Args<P : AstPayload>(val expr: AstExprP<P>) : ArgumentP<P>()
    class KwArgs<P : AstPayload>(val expr: AstExprP<P>) : ArgumentP<P>()

    fun expr(): AstExprP<P> = when (this) {
        is Positional -> expr
        is Named -> expr
        is Args -> expr
        is KwArgs -> expr
    }

    fun exprMut(): AstExprP<P> = when (this) {
        is Positional -> expr
        is Named -> expr
        is Args -> expr
        is KwArgs -> expr
    }

    /** Argument name if it is named. */
    fun name(): String? = when (this) {
        is Named -> name.node
        else -> null
    }

    override fun toString(): String = StringBuilder().also { fmt(it, this) }.toString()
}

sealed class ParameterP<P : AstPayload> {
    /** `/` marker. */
    class Slash<P : AstPayload> : ParameterP<P>()

    class Normal<P : AstPayload>(
        /** Name. */
        val name: AstAssignIdentP<P>,
        /** Type. */
        val type: AstTypeExprP<P>?,
        /** Default value. */
        val default: AstExprP<P>?,
    ) : ParameterP<P>()

    /** `*` marker. */
    class NoArgs<P : AstPayload> : ParameterP<P>()
    class Args<P : AstPayload>(val name: AstAssignIdentP<P>, val type: AstTypeExprP<P>?) : ParameterP<P>()
    class KwArgs<P : AstPayload>(val name: AstAssignIdentP<P>, val type: AstTypeExprP<P>?) : ParameterP<P>()

    fun ident(): AstAssignIdentP<P>? = when (this) {
        is Normal -> name
        is Args -> name
        is KwArgs -> name
        is NoArgs, is Slash -> null
    }

    override fun toString(): String = StringBuilder().also { fmt(it, this) }.toString()
}

sealed class AstLiteral {
    class Int(val value: AstInt) : AstLiteral()
    class Float(val value: AstFloat) : AstLiteral()
    class String(val value: AstString) : AstLiteral()
    class Ellipsis : AstLiteral()

    override fun toString(): kotlin.String = StringBuilder().also { fmt(it, this) }.toString()
}

class LambdaP<P : AstPayload>(
    val params: List<AstParameterP<P>>,
    val body: AstExprP<P>,
    val payload: Any?, // P::DefPayload (Kotlin can't express the associated type; concrete payloads cast at use sites)
) {
    fun signatureSpan(): Span {
        return params
            .map { it.span }
            .reduceOrNull { a, b -> a.merge(b) }
            ?: // TODO(nga): this is not correct span.
                body.span
    }
}

class CallArgsP<P : AstPayload>(val args: List<AstArgumentP<P>>)

sealed class ExprP<P : AstPayload> {
    class Tuple<P : AstPayload>(val elems: kotlin.collections.List<AstExprP<P>>) : ExprP<P>()
    class Dot<P : AstPayload>(val target: AstExprP<P>, val attr: AstString) : ExprP<P>()
    class Call<P : AstPayload>(val target: AstExprP<P>, val args: CallArgsP<P>) : ExprP<P>()
    class Index<P : AstPayload>(val target: AstExprP<P>, val index: AstExprP<P>) : ExprP<P>()
    class Index2<P : AstPayload>(
        val target: AstExprP<P>,
        val index0: AstExprP<P>,
        val index1: AstExprP<P>,
    ) : ExprP<P>()
    class Slice<P : AstPayload>(
        val target: AstExprP<P>,
        val start: AstExprP<P>?,
        val stop: AstExprP<P>?,
        val step: AstExprP<P>?,
    ) : ExprP<P>()
    class Identifier<P : AstPayload>(val ident: AstIdentP<P>) : ExprP<P>()
    class Lambda<P : AstPayload>(val lambda: LambdaP<P>) : ExprP<P>()
    class Literal<P : AstPayload>(val literal: AstLiteral) : ExprP<P>()
    class Not<P : AstPayload>(val target: AstExprP<P>) : ExprP<P>()
    class Minus<P : AstPayload>(val target: AstExprP<P>) : ExprP<P>()
    class Plus<P : AstPayload>(val target: AstExprP<P>) : ExprP<P>()
    class BitNot<P : AstPayload>(val target: AstExprP<P>) : ExprP<P>()
    class Op<P : AstPayload>(val left: AstExprP<P>, val op: BinOp, val right: AstExprP<P>) : ExprP<P>()
    /** Order: condition, v1, v2 — `v1 if condition else v2`. */
    class If<P : AstPayload>(
        val condition: AstExprP<P>,
        val v1: AstExprP<P>,
        val v2: AstExprP<P>,
    ) : ExprP<P>()
    class List<P : AstPayload>(val elems: kotlin.collections.List<AstExprP<P>>) : ExprP<P>()
    class Dict<P : AstPayload>(val entries: kotlin.collections.List<Pair<AstExprP<P>, AstExprP<P>>>) : ExprP<P>()
    class ListComprehension<P : AstPayload>(
        val expr: AstExprP<P>,
        val firstFor: ForClauseP<P>,
        val clauses: kotlin.collections.List<ClauseP<P>>,
    ) : ExprP<P>()
    class DictComprehension<P : AstPayload>(
        val key: AstExprP<P>,
        val value: AstExprP<P>,
        val firstFor: ForClauseP<P>,
        val clauses: kotlin.collections.List<ClauseP<P>>,
    ) : ExprP<P>()
    class FString<P : AstPayload>(val fstring: AstFStringP<P>) : ExprP<P>()

    override fun toString(): String = StringBuilder().also { fmt(it, this) }.toString()
}

/** Restricted expression at type position. */
class TypeExprP<P : AstPayload>(
    /**
     * Currently it is an expr.
     * Planning to restrict it.
     * [Context](https://fb.workplace.com/groups/buck2eng/posts/3196541547309990).
     */
    val expr: AstExprP<P>,
    val payload: Any?, // P::TypeExprPayload
) {
    override fun toString(): String = StringBuilder().also { fmt(it, this) }.toString()
}

/** In some places e.g. AssignModify, the Tuple case is not allowed. */
sealed class AssignTargetP<P : AstPayload> {
    // We use Tuple for both Tuple and List,
    // as these have the same semantics in Starlark.
    class Tuple<P : AstPayload>(val elems: List<AstAssignTargetP<P>>) : AssignTargetP<P>()
    class Index<P : AstPayload>(val target: AstExprP<P>, val index: AstExprP<P>) : AssignTargetP<P>()
    class Dot<P : AstPayload>(val target: AstExprP<P>, val attr: AstString) : AssignTargetP<P>()
    class Identifier<P : AstPayload>(val ident: AstAssignIdentP<P>) : AssignTargetP<P>()

    override fun toString(): String = StringBuilder().also { fmt(it, this) }.toString()
}

/** `x: t = y`. */
class AssignP<P : AstPayload>(
    val lhs: AstAssignTargetP<P>,
    val ty: AstTypeExprP<P>?,
    val rhs: AstExprP<P>,
)

/** Identifier in assign position. */
data class AssignIdentP<P : AstPayload>(
    val ident: String,
    val payload: Any?, // P::IdentAssignPayload
) {
    override fun toString(): String = ident
}

/**
 * Identifier in read position, e.g. `foo` in `[foo.bar]`.
 * `foo` in `foo = 1` or `bar.foo` are **not** represented by this type.
 */
data class IdentP<P : AstPayload>(
    val ident: String,
    val payload: Any?, // P::IdentPayload
) {
    override fun toString(): String = ident
}

/** Argument of `load` statement. */
class LoadArgP<P : AstPayload>(
    /** `x` in `x="y"`. */
    val local: AstAssignIdentP<P>,
    /** `"y"` in `x="y"`. */
    val their: AstString,
    /** Trailing comma. */
    val comma: Spanned<Comma>?,
) {
    fun span(): Span = local.span.merge(their.span)

    fun spanWithTrailingComma(): Span {
        val c = comma
        return if (c != null) span().merge(c.span) else span()
    }
}

/** `load` statement. */
class LoadP<P : AstPayload>(
    val module: AstString,
    val args: List<LoadArgP<P>>,
    val payload: Any?, // P::LoadPayload
)

class ForClauseP<P : AstPayload>(
    val variable: AstAssignTargetP<P>,
    val over: AstExprP<P>,
) {
    override fun toString(): String = StringBuilder().also { fmt(it, this) }.toString()
}

sealed class ClauseP<P : AstPayload> {
    class For<P : AstPayload>(val clause: ForClauseP<P>) : ClauseP<P>()
    class If<P : AstPayload>(val cond: AstExprP<P>) : ClauseP<P>()

    override fun toString(): String = StringBuilder().also { fmt(it, this) }.toString()
}

enum class BinOp {
    Or,
    And,
    Equal,
    NotEqual,
    Less,
    Greater,
    LessOrEqual,
    GreaterOrEqual,
    In,
    NotIn,
    Subtract,
    Add,
    Multiply,
    Percent,
    Divide,
    FloorDivide,
    BitAnd,
    BitOr,
    BitXor,
    LeftShift,
    RightShift;

    override fun toString(): String = when (this) {
        Or -> " or "
        And -> " and "
        Equal -> " == "
        NotEqual -> " != "
        Less -> " < "
        Greater -> " > "
        LessOrEqual -> " <= "
        GreaterOrEqual -> " >= "
        In -> " in "
        NotIn -> " not in "
        Subtract -> " - "
        Add -> " + "
        Multiply -> " * "
        Percent -> " % "
        Divide -> " / "
        FloorDivide -> " // "
        BitAnd -> " & "
        BitOr -> " | "
        BitXor -> " ^ "
        LeftShift -> " << "
        RightShift -> " >> "
    }
}

enum class AssignOp {
    Add,         // +=
    Subtract,    // -=
    Multiply,    // *=
    Divide,      // /=
    FloorDivide, // //=
    Percent,     // %=
    BitAnd,      // &=
    BitOr,       // |=
    BitXor,      // ^=
    LeftShift,   // <<=
    RightShift;  // >>=

    override fun toString(): String = when (this) {
        Add -> " += "
        Subtract -> " -= "
        Multiply -> " *= "
        Divide -> " /= "
        FloorDivide -> " //= "
        Percent -> " %= "
        BitAnd -> " &= "
        BitOr -> " |= "
        BitXor -> " ^= "
        LeftShift -> " <<= "
        RightShift -> " >>= "
    }
}

enum class Visibility {
    Private,
    Public,
}

class DefP<P : AstPayload>(
    val name: AstAssignIdentP<P>,
    val params: List<AstParameterP<P>>,
    val returnType: AstTypeExprP<P>?,
    val body: AstStmtP<P>,
    val payload: Any?, // P::DefPayload
) {
    fun signatureSpan(): Span {
        var span = name.span
        for (param in params) {
            span = span.merge(param.span)
        }
        val rt = returnType
        if (rt != null) {
            span = span.merge(rt.span)
        }
        return span
    }
}

class ForP<P : AstPayload>(
    val variable: AstAssignTargetP<P>,
    val over: AstExprP<P>,
    val body: AstStmtP<P>,
)

class FStringP<P : AstPayload>(
    /** A format string containing a `{}` marker for each expression to interpolate. */
    val format: AstString,
    /** The expressions to interpolate. */
    val expressions: List<AstExprP<P>>,
)

sealed class StmtP<P : AstPayload> {
    class Break<P : AstPayload> : StmtP<P>()
    class Continue<P : AstPayload> : StmtP<P>()
    class Pass<P : AstPayload> : StmtP<P>()
    class Return<P : AstPayload>(val value: AstExprP<P>?) : StmtP<P>()
    class Expression<P : AstPayload>(val expr: AstExprP<P>) : StmtP<P>()
    class Assign<P : AstPayload>(val assign: AssignP<P>) : StmtP<P>()
    class AssignModify<P : AstPayload>(
        val lhs: AstAssignTargetP<P>,
        val op: AssignOp,
        val rhs: AstExprP<P>,
    ) : StmtP<P>()
    class Statements<P : AstPayload>(val stmts: List<AstStmtP<P>>) : StmtP<P>()
    class If<P : AstPayload>(val cond: AstExprP<P>, val suite: AstStmtP<P>) : StmtP<P>()
    class IfElse<P : AstPayload>(
        val cond: AstExprP<P>,
        val suite1: AstStmtP<P>,
        val suite2: AstStmtP<P>,
    ) : StmtP<P>()
    class For<P : AstPayload>(val forStmt: ForP<P>) : StmtP<P>()
    class Def<P : AstPayload>(val def: DefP<P>) : StmtP<P>()
    class Load<P : AstPayload>(val load: LoadP<P>) : StmtP<P>()

    override fun toString(): String =
        StringBuilder().also { fmtWithTab(it, this, "") }.toString()
}

private fun <I> commaSeparatedFmt(
    out: StringBuilder,
    v: List<I>,
    converter: (I, StringBuilder) -> Unit,
    forTuple: Boolean,
) {
    for ((i, e) in v.withIndex()) {
        out.append(if (i == 0) "" else ", ")
        converter(e, out)
    }
    if (v.size == 1 && forTuple) {
        out.append(",")
    }
}

private fun fmtStringLiteral(out: StringBuilder, s: String) {
    out.append('"')
    for (c in s) {
        when (c) {
            '\n' -> out.append("\\n")
            '\t' -> out.append("\\t")
            '\r' -> out.append("\\r")
            '\u0000' -> out.append("\\0")
            '"' -> out.append("\\\"")
            '\\' -> out.append("\\\\")
            else -> out.append(c)
        }
    }
    out.append('"')
}

private fun fmt(out: StringBuilder, self: AstLiteral) {
    when (self) {
        is AstLiteral.Int -> out.append(self.value.node.toString())
        is AstLiteral.Float -> out.append(self.value.node.toString())
        is AstLiteral.String -> fmtStringLiteral(out, self.value.node)
        is AstLiteral.Ellipsis -> out.append("...")
    }
}

private fun fmt(out: StringBuilder, self: BinOp) {
    out.append(when (self) {
        BinOp.Or -> " or "
        BinOp.And -> " and "
        BinOp.Equal -> " == "
        BinOp.NotEqual -> " != "
        BinOp.Less -> " < "
        BinOp.Greater -> " > "
        BinOp.LessOrEqual -> " <= "
        BinOp.GreaterOrEqual -> " >= "
        BinOp.In -> " in "
        BinOp.NotIn -> " not in "
        BinOp.Subtract -> " - "
        BinOp.Add -> " + "
        BinOp.Multiply -> " * "
        BinOp.Percent -> " % "
        BinOp.Divide -> " / "
        BinOp.FloorDivide -> " // "
        BinOp.BitAnd -> " & "
        BinOp.BitOr -> " | "
        BinOp.BitXor -> " ^ "
        BinOp.LeftShift -> " << "
        BinOp.RightShift -> " >> "
    })
}

private fun fmt(out: StringBuilder, self: AssignOp) {
    out.append(when (self) {
        AssignOp.Add -> " += "
        AssignOp.Subtract -> " -= "
        AssignOp.Multiply -> " *= "
        AssignOp.Divide -> " /= "
        AssignOp.FloorDivide -> " //= "
        AssignOp.Percent -> " %= "
        AssignOp.BitAnd -> " &= "
        AssignOp.BitOr -> " |= "
        AssignOp.BitXor -> " ^= "
        AssignOp.LeftShift -> " <<= "
        AssignOp.RightShift -> " >>= "
    })
}

private fun <P : AstPayload> fmt(out: StringBuilder, self: AssignIdentP<P>) {
    out.append(self.ident)
}

private fun <P : AstPayload> fmt(out: StringBuilder, self: IdentP<P>) {
    out.append(self.ident)
}

private fun <P : AstPayload> fmt(out: StringBuilder, self: StmtP<P>) {
    fmtWithTab(out, self, "")
}

private fun <P : AstPayload> fmt(out: StringBuilder, self: ExprP<P>) {
    when (self) {
        is ExprP.Tuple -> {
            out.append("(")
            commaSeparatedFmt(out, self.elems, { x, f -> fmt(f, x.node) }, true)
            out.append(")")
        }
        is ExprP.Dot -> {
            fmt(out, self.target.node)
            out.append('.').append(self.attr.node)
        }
        is ExprP.Lambda -> {
            val l = self.lambda
            out.append("(lambda ")
            commaSeparatedFmt(out, l.params, { x, f -> fmt(f, x.node) }, false)
            out.append(": ")
            fmt(out, l.body.node)
            out.append(")")
        }
        is ExprP.Call -> {
            fmt(out, self.target.node)
            out.append('(')
            for ((i, x) in self.args.args.withIndex()) {
                if (i != 0) out.append(", ")
                fmt(out, x.node)
            }
            out.append(')')
        }
        is ExprP.Index -> {
            fmt(out, self.target.node)
            out.append('[')
            fmt(out, self.index.node)
            out.append(']')
        }
        is ExprP.Index2 -> {
            fmt(out, self.target.node)
            out.append('[')
            fmt(out, self.index0.node)
            out.append(", ")
            fmt(out, self.index1.node)
            out.append(']')
        }
        is ExprP.Slice -> {
            fmt(out, self.target.node)
            out.append("[]")
            if (self.start != null) {
                fmt(out, self.start.node)
                out.append(':')
            } else {
                out.append(':')
            }
            if (self.stop != null) {
                fmt(out, self.stop.node)
            }
            if (self.step != null) {
                out.append(':')
                fmt(out, self.step.node)
            }
        }
        is ExprP.Identifier -> out.append(self.ident.node.ident)
        is ExprP.Not -> {
            out.append("(not ")
            fmt(out, self.target.node)
            out.append(')')
        }
        is ExprP.Minus -> {
            out.append('-')
            fmt(out, self.target.node)
        }
        is ExprP.Plus -> {
            out.append('+')
            fmt(out, self.target.node)
        }
        is ExprP.BitNot -> {
            out.append('~')
            fmt(out, self.target.node)
        }
        is ExprP.Op -> {
            out.append('(')
            fmt(out, self.left.node)
            out.append(self.op.toString())
            fmt(out, self.right.node)
            out.append(')')
        }
        is ExprP.If -> {
            out.append('(')
            fmt(out, self.v1.node)
            out.append(" if ")
            fmt(out, self.condition.node)
            out.append(" else ")
            fmt(out, self.v2.node)
            out.append(')')
        }
        is ExprP.List -> {
            out.append('[')
            commaSeparatedFmt(out, self.elems, { x, f -> fmt(f, x.node) }, false)
            out.append(']')
        }
        is ExprP.Dict -> {
            out.append('{')
            commaSeparatedFmt(out, self.entries, { x, f ->
                fmt(f, x.first.node)
                f.append(": ")
                fmt(f, x.second.node)
            }, false)
            out.append('}')
        }
        is ExprP.ListComprehension -> {
            out.append('[')
            fmt(out, self.expr.node)
            fmt(out, self.firstFor)
            for (x in self.clauses) {
                fmt(out, x)
            }
            out.append(']')
        }
        is ExprP.DictComprehension -> {
            out.append('{')
            fmt(out, self.key.node)
            out.append(": ")
            fmt(out, self.value.node)
            fmt(out, self.firstFor)
            for (x in self.clauses) {
                fmt(out, x)
            }
            out.append('}')
        }
        is ExprP.Literal -> fmt(out, self.literal)
        is ExprP.FString -> {
            val f = self.fstring.node
            // Write out the desugared form.
            out.append(f.format.node).append(".format(")
            commaSeparatedFmt(out, f.expressions, { x, ff -> fmt(ff, x.node) }, false)
            out.append(')')
        }
    }
}

private fun <P : AstPayload> fmt(out: StringBuilder, self: TypeExprP<P>) {
    fmt(out, self.expr.node)
}

private fun <P : AstPayload> fmt(out: StringBuilder, self: AssignTargetP<P>) {
    when (self) {
        is AssignTargetP.Tuple -> {
            out.append('(')
            commaSeparatedFmt(out, self.elems, { x, f -> fmt(f, x.node) }, true)
            out.append(')')
        }
        is AssignTargetP.Dot -> {
            fmt(out, self.target.node)
            out.append('.').append(self.attr.node)
        }
        is AssignTargetP.Index -> {
            fmt(out, self.target.node)
            out.append('[')
            fmt(out, self.index.node)
            out.append(']')
        }
        is AssignTargetP.Identifier -> out.append(self.ident.node.ident)
    }
}

private fun <P : AstPayload> fmt(out: StringBuilder, self: ArgumentP<P>) {
    when (self) {
        is ArgumentP.Positional -> fmt(out, self.expr.node)
        is ArgumentP.Named -> {
            out.append(self.name.node).append(" = ")
            fmt(out, self.expr.node)
        }
        is ArgumentP.Args -> {
            out.append('*')
            fmt(out, self.expr.node)
        }
        is ArgumentP.KwArgs -> {
            out.append("**")
            fmt(out, self.expr.node)
        }
    }
}

private fun <P : AstPayload> fmt(out: StringBuilder, self: ParameterP<P>) {
    when (self) {
        is ParameterP.Slash -> { out.append('/'); return }
        is ParameterP.NoArgs -> { out.append('*'); return }
        is ParameterP.Normal -> {
            out.append(self.name.node.ident)
            if (self.type != null) {
                out.append(": ")
                fmt(out, self.type.node)
            }
            if (self.default != null) {
                out.append(" = ")
                fmt(out, self.default.node)
            }
        }
        is ParameterP.Args -> {
            out.append('*').append(self.name.node.ident)
            if (self.type != null) {
                out.append(": ")
                fmt(out, self.type.node)
            }
        }
        is ParameterP.KwArgs -> {
            out.append("**").append(self.name.node.ident)
            if (self.type != null) {
                out.append(": ")
                fmt(out, self.type.node)
            }
        }
    }
}

private fun <P : AstPayload> fmt(out: StringBuilder, self: ForClauseP<P>) {
    out.append(" for ")
    fmt(out, self.variable.node)
    out.append(" in ")
    fmt(out, self.over.node)
}

private fun <P : AstPayload> fmt(out: StringBuilder, self: ClauseP<P>) {
    when (self) {
        is ClauseP.For -> fmt(out, self.clause)
        is ClauseP.If -> {
            out.append(" if ")
            fmt(out, self.cond.node)
        }
    }
}

private fun <P : AstPayload> fmtWithTab(out: StringBuilder, self: StmtP<P>, tab: String) {
    when (self) {
        is StmtP.Break -> { out.append(tab).append("break\n") }
        is StmtP.Continue -> { out.append(tab).append("continue\n") }
        is StmtP.Pass -> { out.append(tab).append("pass\n") }
        is StmtP.Return -> {
            if (self.value != null) {
                out.append(tab).append("return ")
                fmt(out, self.value.node)
                out.append('\n')
            } else {
                out.append(tab).append("return\n")
            }
        }
        is StmtP.Expression -> {
            out.append(tab)
            fmt(out, self.expr.node)
            out.append('\n')
        }
        is StmtP.Assign -> {
            val a = self.assign
            out.append(tab)
            fmt(out, a.lhs.node)
            out.append(' ')
            if (a.ty != null) {
                out.append(": ")
                fmt(out, a.ty.node)
                out.append(' ')
            }
            out.append("= ")
            fmt(out, a.rhs.node)
            out.append('\n')
        }
        is StmtP.AssignModify -> {
            out.append(tab)
            fmt(out, self.lhs.node)
            out.append(self.op.toString())
            fmt(out, self.rhs.node)
            out.append('\n')
        }
        is StmtP.Statements -> {
            for (st in self.stmts) {
                fmtWithTab(out, st.node, tab)
            }
        }
        is StmtP.If -> {
            out.append(tab).append("if ")
            fmt(out, self.cond.node)
            out.append(":\n")
            fmtWithTab(out, self.suite.node, tab + "  ")
        }
        is StmtP.IfElse -> {
            out.append(tab).append("if ")
            fmt(out, self.cond.node)
            out.append(":\n")
            fmtWithTab(out, self.suite1.node, tab + "  ")
            out.append(tab).append("else:\n")
            fmtWithTab(out, self.suite2.node, tab + "  ")
        }
        is StmtP.For -> {
            val f = self.forStmt
            out.append(tab).append("for ")
            fmt(out, f.variable.node)
            out.append(" in ")
            fmt(out, f.over.node)
            out.append(":\n")
            fmtWithTab(out, f.body.node, tab + "  ")
        }
        is StmtP.Def -> {
            val d = self.def
            out.append(tab).append("def ").append(d.name.node.ident).append('(')
            commaSeparatedFmt(out, d.params, { x, ff -> fmt(ff, x.node) }, false)
            out.append(')')
            if (d.returnType != null) {
                out.append(" -> ")
                fmt(out, d.returnType.node)
            }
            out.append(":\n")
            fmtWithTab(out, d.body.node, tab + "  ")
        }
        is StmtP.Load -> {
            val load = self.load
            out.append(tab).append("load(")
            fmtStringLiteral(out, load.module.node)
            out.append(", ")
            commaSeparatedFmt(out, load.args, { x, ff ->
                ff.append(x.local.node.ident).append(" = ")
                fmtStringLiteral(ff, x.their.node)
            }, false)
            out.append(")\n")
        }
    }
}


