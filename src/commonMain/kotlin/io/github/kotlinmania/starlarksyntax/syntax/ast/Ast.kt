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
import kotlin.collections.List

/** Payload types attached to AST nodes. */
interface AstPayload

/**
 * Default implementation of payload, which attaches `Unit` to nodes.
 * This payload is returned with AST by parser.
 */
object AstNoPayload : AstPayload

/** `,` token. */
class Comma

// Boxed types used for storing information from the parsing will be used
// especially for the location of the AST item
class AstExpr(override val node: Expr, override val span: Span) : Spanned<Expr>(node, span)
class AstTypeExpr(override val node: TypeExpr, override val span: Span) : Spanned<TypeExpr>(node, span)
class AstAssignTarget(override val node: AssignTarget, override val span: Span) : Spanned<AssignTarget>(node, span)
class AstAssignIdent(override val node: AssignIdent, override val span: Span) : Spanned<AssignIdent>(node, span)
class AstIdent(override val node: Ident, override val span: Span) : Spanned<Ident>(node, span)
class AstArgument(override val node: Argument, override val span: Span) : Spanned<Argument>(node, span)
class AstString(override val node: String, override val span: Span) : Spanned<String>(node, span)
class AstParameter(override val node: Parameter, override val span: Span) : Spanned<Parameter>(node, span)
class AstInt(override val node: TokenInt, override val span: Span) : Spanned<TokenInt>(node, span)
class AstFloat(override val node: Double, override val span: Span) : Spanned<Double>(node, span)
class AstFString(override val node: FString, override val span: Span) : Spanned<FString>(node, span)
class AstStmt(override val node: Stmt, override val span: Span) : Spanned<Stmt>(node, span)
class AstClause(override val node: Clause, override val span: Span) : Spanned<Clause>(node, span)
class AstForClause(override val node: ForClause, override val span: Span) : Spanned<ForClause>(node, span)
class AstComma(override val node: Comma, override val span: Span) : Spanned<Comma>(node, span)

// Backward compatibility typealiases
typealias ArgumentP<P> = Argument
typealias ParameterP<P> = Parameter
typealias LambdaP<P> = Lambda
typealias CallArgsP<P> = CallArgs
typealias ExprP<P> = Expr
typealias TypeExprP<P> = TypeExpr
typealias AssignTargetP<P> = AssignTarget
typealias AssignP<P> = Assign
typealias AssignIdentP<P> = AssignIdent
typealias IdentP<P> = Ident
typealias LoadArgP<P> = LoadArg
typealias LoadP<P> = Load
typealias ForClauseP<P> = ForClause
typealias ClauseP<P> = Clause
typealias DefP<P> = Def
typealias ForP<P> = For
typealias FStringP<P> = FString
typealias StmtP<P> = Stmt

typealias AstExprP<P> = AstExpr
typealias AstTypeExprP<P> = AstTypeExpr
typealias AstAssignTargetP<P> = AstAssignTarget
typealias AstAssignIdentP<P> = AstAssignIdent
typealias AstIdentP<P> = AstIdent
typealias AstArgumentP<P> = AstArgument
typealias AstParameterP<P> = AstParameter
typealias AstStmtP<P> = AstStmt
typealias AstFStringP<P> = AstFString

// A trait rather than a function to allow .ast() chaining in the parser.
/** Wrap a value with a [Span] computed from `begin..end` byte offsets. */
fun Expr.ast(begin: Int, end: Int): AstExpr = AstExpr(this, Span.new(Pos.new(begin), Pos.new(end)))
fun TypeExpr.ast(begin: Int, end: Int): AstTypeExpr = AstTypeExpr(this, Span.new(Pos.new(begin), Pos.new(end)))
fun AssignTarget.ast(begin: Int, end: Int): AstAssignTarget = AstAssignTarget(this, Span.new(Pos.new(begin), Pos.new(end)))
fun AssignIdent.ast(begin: Int, end: Int): AstAssignIdent = AstAssignIdent(this, Span.new(Pos.new(begin), Pos.new(end)))
fun Ident.ast(begin: Int, end: Int): AstIdent = AstIdent(this, Span.new(Pos.new(begin), Pos.new(end)))
fun Argument.ast(begin: Int, end: Int): AstArgument = AstArgument(this, Span.new(Pos.new(begin), Pos.new(end)))
fun String.ast(begin: Int, end: Int): AstString = AstString(this, Span.new(Pos.new(begin), Pos.new(end)))
fun Parameter.ast(begin: Int, end: Int): AstParameter = AstParameter(this, Span.new(Pos.new(begin), Pos.new(end)))
fun TokenInt.ast(begin: Int, end: Int): AstInt = AstInt(this, Span.new(Pos.new(begin), Pos.new(end)))
fun Double.ast(begin: Int, end: Int): AstFloat = AstFloat(this, Span.new(Pos.new(begin), Pos.new(end)))
fun FString.ast(begin: Int, end: Int): AstFString = AstFString(this, Span.new(Pos.new(begin), Pos.new(end)))
fun Stmt.ast(begin: Int, end: Int): AstStmt = AstStmt(this, Span.new(Pos.new(begin), Pos.new(end)))
fun Clause.ast(begin: Int, end: Int): AstClause = AstClause(this, Span.new(Pos.new(begin), Pos.new(end)))
fun ForClause.ast(begin: Int, end: Int): AstForClause = AstForClause(this, Span.new(Pos.new(begin), Pos.new(end)))
fun Comma.ast(begin: Int, end: Int): AstComma = AstComma(this, Span.new(Pos.new(begin), Pos.new(end)))
fun <T> T.ast(begin: Int, end: Int): Spanned<T> = Spanned(
    node = this,
    span = Span.new(Pos.new(begin), Pos.new(end)),
)

sealed class Argument {
    class Positional(val expr: AstExpr) : Argument()
    class Named(val name: AstString, val expr: AstExpr) : Argument()
    class Args(val expr: AstExpr) : Argument()
    class KwArgs(val expr: AstExpr) : Argument()

    fun expr(): AstExpr = when (this) {
        is Positional -> expr
        is Named -> expr
        is Args -> expr
        is KwArgs -> expr
    }

    fun exprMut(): AstExpr = when (this) {
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

sealed class Parameter {
    /** `/` marker. */
    class Slash : Parameter()

    class Normal(
        /** Name. */
        val name: AstAssignIdent,
        /** Type. */
        val type: AstTypeExpr?,
        /** Default value. */
        val default: AstExpr?,
    ) : Parameter()

    /** `*` marker. */
    class NoArgs : Parameter()
    class Args(val name: AstAssignIdent, val type: AstTypeExpr?) : Parameter()
    class KwArgs(val name: AstAssignIdent, val type: AstTypeExpr?) : Parameter()

    fun ident(): AstAssignIdent? = when (this) {
        is Normal -> name
        is Args -> name
        is KwArgs -> name
        is NoArgs, is Slash -> null
    }

    override fun toString(): String = StringBuilder().also { fmt(it, this) }.toString()
}

sealed class AstLiteral {
    class IntLiteral(val value: AstInt) : AstLiteral()
    class FloatLiteral(val value: AstFloat) : AstLiteral()
    class StringLiteral(val value: AstString) : AstLiteral()
    class EllipsisLiteral : AstLiteral()

    override fun toString(): kotlin.String = StringBuilder().also { fmt(it, this) }.toString()
}

class Lambda(
    val params: List<AstParameter>,
    val body: AstExpr,
    val payload: Any? = null,
) {
    fun signatureSpan(): Span {
        return params
            .map { it.span }
            .reduceOrNull { a, b -> a.merge(b) }
            ?: body.span
    }
}

class CallArgs(val args: kotlin.collections.List<AstArgument> = emptyList())

sealed class Expr {
    class Tuple(val elems: kotlin.collections.List<AstExpr>) : Expr()
    class Dot(val target: AstExpr, val attr: AstString) : Expr()
    class Call(val target: AstExpr, val args: CallArgs) : Expr()
    class Index(val target: AstExpr, val index: AstExpr) : Expr()
    class Index2(
        val target: AstExpr,
        val index0: AstExpr,
        val index1: AstExpr,
    ) : Expr()
    class Slice(
        val target: AstExpr,
        val start: AstExpr?,
        val stop: AstExpr?,
        val step: AstExpr?,
    ) : Expr()
    class Identifier(val ident: AstIdent) : Expr()
    class Lambda(val lambda: io.github.kotlinmania.starlarksyntax.syntax.ast.Lambda) : Expr()
    class Literal(val literal: AstLiteral) : Expr()
    class Not(val target: AstExpr) : Expr()
    class Minus(val target: AstExpr) : Expr()
    class Plus(val target: AstExpr) : Expr()
    class BitNot(val target: AstExpr) : Expr()
    class Op(val left: AstExpr, val op: BinOp, val right: AstExpr) : Expr()
    /** Order: condition, v1, v2 — `v1 if condition else v2`. */
    class If(
        val condition: AstExpr,
        val v1: AstExpr,
        val v2: AstExpr,
    ) : Expr()
    class List(val elems: kotlin.collections.List<AstExpr>) : Expr()
    data class DictEntry(val key: AstExpr, val value: AstExpr)
    class Dict(val entries: kotlin.collections.List<DictEntry>) : Expr()
    class ListComprehension(
        val expr: AstExpr,
        val firstFor: ForClause,
        val clauses: kotlin.collections.List<Clause>,
    ) : Expr()
    class DictComprehension(
        val key: AstExpr,
        val value: AstExpr,
        val firstFor: ForClause,
        val clauses: kotlin.collections.List<Clause>,
    ) : Expr()
    class FString(val fstring: AstFString) : Expr()

    override fun toString(): String = StringBuilder().also { fmt(it, this) }.toString()
}

/** Restricted expression at type position. */
class TypeExpr(
    val expr: AstExpr,
    val payload: Any? = null,
) {
    override fun toString(): String = StringBuilder().also { fmt(it, this) }.toString()
}

/** In some places e.g. AssignModify, the Tuple case is not allowed. */
sealed class AssignTarget {
    class Tuple(val elems: kotlin.collections.List<AstAssignTarget>) : AssignTarget()
    class Index(val target: AstExpr, val index: AstExpr) : AssignTarget()
    class Dot(val target: AstExpr, val attr: AstString) : AssignTarget()
    class Identifier(val ident: AstAssignIdent) : AssignTarget()

    override fun toString(): String = StringBuilder().also { fmt(it, this) }.toString()
}

/** `x: t = y`. */
class Assign(
    val lhs: AstAssignTarget,
    val ty: AstTypeExpr?,
    val rhs: AstExpr,
)

/** Identifier in assign position. */
data class AssignIdent(
    val ident: String,
    val payload: Any? = null,
) {
    override fun toString(): String = ident
}

/**
 * Identifier in read position, e.g. `foo` in `[foo.bar]`.
 * `foo` in `foo = 1` or `bar.foo` are **not** represented by this type.
 */
data class Ident(
    val ident: String,
    val payload: Any? = null,
) {
    override fun toString(): String = ident
}

/** Argument of `load` statement. */
class LoadArg(
    /** `x` in `x="y"`. */
    val local: AstAssignIdent,
    /** `"y"` in `x="y"`. */
    val their: AstString,
    /** Trailing comma. */
    val comma: AstComma? = null,
) {
    fun span(): Span = local.span.merge(their.span)

    fun spanWithTrailingComma(): Span {
        val c = comma
        return if (c != null) span().merge(c.span) else span()
    }
}

/** `load` statement. */
class Load(
    /** Module name. */
    val module: AstString,
    /** Symbols to load. */
    val args: kotlin.collections.List<LoadArg>,
    val payload: Any? = null,
)

typealias AstLoadArg = LoadArg

class ForClause(
    val variable: AstAssignTarget,
    val over: AstExpr,
) {
    override fun toString(): String = StringBuilder().also { fmt(it, this) }.toString()
}

sealed class Clause {
    class For(val clause: ForClause) : Clause()
    class If(val cond: AstExpr) : Clause()

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
    BitOr,      // |=
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

class Def(
    val name: AstAssignIdent,
    val params: kotlin.collections.List<AstParameter>,
    val returnType: AstTypeExpr?,
    val body: AstStmt,
    val payload: Any? = null,
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

class For(
    val variable: AstAssignTarget,
    val over: AstExpr,
    val body: AstStmt,
)

class FString(
    /** A format string containing a `{}` marker for each expression to interpolate. */
    val format: AstString,
    /** The expressions to interpolate. */
    val expressions: kotlin.collections.List<AstExpr>,
)

sealed class Stmt {
    class Break : Stmt()
    class Continue : Stmt()
    class Pass : Stmt()
    class Return(val value: AstExpr?) : Stmt()
    class Expression(val expr: AstExpr) : Stmt()
    class Assign(val assign: io.github.kotlinmania.starlarksyntax.syntax.ast.Assign) : Stmt()
    class AssignModify(
        val lhs: AstAssignTarget,
        val op: AssignOp,
        val rhs: AstExpr,
    ) : Stmt()
    class Statements(val stmts: kotlin.collections.List<AstStmt>) : Stmt()
    class If(val cond: AstExpr, val suite: AstStmt) : Stmt()
    class IfElse(
        val cond: AstExpr,
        val suite1: AstStmt,
        val suite2: AstStmt,
    ) : Stmt()
    class For(val forStmt: io.github.kotlinmania.starlarksyntax.syntax.ast.For) : Stmt()
    class Def(val def: io.github.kotlinmania.starlarksyntax.syntax.ast.Def) : Stmt()
    class Load(val load: io.github.kotlinmania.starlarksyntax.syntax.ast.Load) : Stmt()

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
        is AstLiteral.IntLiteral -> out.append(self.value.node.toString())
        is AstLiteral.FloatLiteral -> out.append(self.value.node.toString())
        is AstLiteral.StringLiteral -> fmtStringLiteral(out, self.value.node)
        is AstLiteral.EllipsisLiteral -> out.append("...")
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

private fun fmt(out: StringBuilder, self: AssignIdent) {
    out.append(self.ident)
}

private fun fmt(out: StringBuilder, self: Ident) {
    out.append(self.ident)
}

private fun fmt(out: StringBuilder, self: Stmt) {
    fmtWithTab(out, self, "")
}

private fun fmt(out: StringBuilder, self: Expr) {
    when (self) {
        is Expr.Tuple -> {
            out.append("(")
            commaSeparatedFmt(out, self.elems, { x, f -> fmt(f, x.node) }, true)
            out.append(")")
        }
        is Expr.Dot -> {
            fmt(out, self.target.node)
            out.append('.').append(self.attr.node)
        }
        is Expr.Lambda -> {
            val l = self.lambda
            out.append("(lambda ")
            commaSeparatedFmt(out, l.params, { x, f -> fmt(f, x.node) }, false)
            out.append(": ")
            fmt(out, l.body.node)
            out.append(")")
        }
        is Expr.Call -> {
            fmt(out, self.target.node)
            out.append('(')
            for ((i, x) in self.args.args.withIndex()) {
                if (i != 0) out.append(", ")
                fmt(out, x.node)
            }
            out.append(')')
        }
        is Expr.Index -> {
            fmt(out, self.target.node)
            out.append('[')
            fmt(out, self.index.node)
            out.append(']')
        }
        is Expr.Index2 -> {
            fmt(out, self.target.node)
            out.append('[')
            fmt(out, self.index0.node)
            out.append(", ")
            fmt(out, self.index1.node)
            out.append(']')
        }
        is Expr.Slice -> {
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
        is Expr.Identifier -> out.append(self.ident.node.ident)
        is Expr.Not -> {
            out.append("(not ")
            fmt(out, self.target.node)
            out.append(')')
        }
        is Expr.Minus -> {
            out.append('-')
            fmt(out, self.target.node)
        }
        is Expr.Plus -> {
            out.append('+')
            fmt(out, self.target.node)
        }
        is Expr.BitNot -> {
            out.append('~')
            fmt(out, self.target.node)
        }
        is Expr.Op -> {
            out.append('(')
            fmt(out, self.left.node)
            out.append(self.op.toString())
            fmt(out, self.right.node)
            out.append(')')
        }
        is Expr.If -> {
            out.append('(')
            fmt(out, self.v1.node)
            out.append(" if ")
            fmt(out, self.condition.node)
            out.append(" else ")
            fmt(out, self.v2.node)
            out.append(')')
        }
        is Expr.List -> {
            out.append('[')
            commaSeparatedFmt(out, self.elems, { x, f -> fmt(f, x.node) }, false)
            out.append(']')
        }
        is Expr.Dict -> {
            out.append('{')
            commaSeparatedFmt(out, self.entries, { x, f ->
                fmt(f, x.key.node)
                f.append(": ")
                fmt(f, x.value.node)
            }, false)
            out.append('}')
        }
        is Expr.ListComprehension -> {
            out.append('[')
            fmt(out, self.expr.node)
            fmt(out, self.firstFor)
            for (x in self.clauses) {
                fmt(out, x)
            }
            out.append(']')
        }
        is Expr.DictComprehension -> {
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
        is Expr.Literal -> fmt(out, self.literal)
        is Expr.FString -> {
            val f = self.fstring.node
            // Write out the desugared form.
            out.append(f.format.node).append(".format(")
            commaSeparatedFmt(out, f.expressions, { x, ff -> fmt(ff, x.node) }, false)
            out.append(')')
        }
    }
}

private fun fmt(out: StringBuilder, self: TypeExpr) {
    fmt(out, self.expr.node)
}

private fun fmt(out: StringBuilder, self: AssignTarget) {
    when (self) {
        is AssignTarget.Tuple -> {
            out.append('(')
            commaSeparatedFmt(out, self.elems, { x, f -> fmt(f, x.node) }, true)
            out.append(')')
        }
        is AssignTarget.Dot -> {
            fmt(out, self.target.node)
            out.append('.').append(self.attr.node)
        }
        is AssignTarget.Index -> {
            fmt(out, self.target.node)
            out.append('[')
            fmt(out, self.index.node)
            out.append(']')
        }
        is AssignTarget.Identifier -> out.append(self.ident.node.ident)
    }
}

private fun fmt(out: StringBuilder, self: Argument) {
    when (self) {
        is Argument.Positional -> fmt(out, self.expr.node)
        is Argument.Named -> {
            out.append(self.name.node).append(" = ")
            fmt(out, self.expr.node)
        }
        is Argument.Args -> {
            out.append('*')
            fmt(out, self.expr.node)
        }
        is Argument.KwArgs -> {
            out.append("**")
            fmt(out, self.expr.node)
        }
    }
}

private fun fmt(out: StringBuilder, self: Parameter) {
    when (self) {
        is Parameter.Slash -> { out.append('/'); return }
        is Parameter.NoArgs -> { out.append('*'); return }
        is Parameter.Normal -> {
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
        is Parameter.Args -> {
            out.append('*').append(self.name.node.ident)
            if (self.type != null) {
                out.append(": ")
                fmt(out, self.type.node)
            }
        }
        is Parameter.KwArgs -> {
            out.append("**").append(self.name.node.ident)
            if (self.type != null) {
                out.append(": ")
                fmt(out, self.type.node)
            }
        }
    }
}

private fun fmt(out: StringBuilder, self: ForClause) {
    out.append(" for ")
    fmt(out, self.variable.node)
    out.append(" in ")
    fmt(out, self.over.node)
}

private fun fmt(out: StringBuilder, self: Clause) {
    when (self) {
        is Clause.For -> fmt(out, self.clause)
        is Clause.If -> {
            out.append(" if ")
            fmt(out, self.cond.node)
        }
    }
}

private fun fmtWithTab(out: StringBuilder, self: Stmt, tab: String) {
    when (self) {
        is Stmt.Break -> { out.append(tab).append("break\n") }
        is Stmt.Continue -> { out.append(tab).append("continue\n") }
        is Stmt.Pass -> { out.append(tab).append("pass\n") }
        is Stmt.Return -> {
            if (self.value != null) {
                out.append(tab).append("return ")
                fmt(out, self.value.node)
                out.append('\n')
            } else {
                out.append(tab).append("return\n")
            }
        }
        is Stmt.Expression -> {
            out.append(tab)
            fmt(out, self.expr.node)
            out.append('\n')
        }
        is Stmt.Assign -> {
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
        is Stmt.AssignModify -> {
            out.append(tab)
            fmt(out, self.lhs.node)
            out.append(self.op.toString())
            fmt(out, self.rhs.node)
            out.append('\n')
        }
        is Stmt.Statements -> {
            for (st in self.stmts) {
                fmtWithTab(out, st.node, tab)
            }
        }
        is Stmt.If -> {
            out.append(tab).append("if ")
            fmt(out, self.cond.node)
            out.append(":\n")
            fmtWithTab(out, self.suite.node, tab + "  ")
        }
        is Stmt.IfElse -> {
            out.append(tab).append("if ")
            fmt(out, self.cond.node)
            out.append(":\n")
            fmtWithTab(out, self.suite1.node, tab + "  ")
            out.append(tab).append("else:\n")
            fmtWithTab(out, self.suite2.node, tab + "  ")
        }
        is Stmt.For -> {
            val f = self.forStmt
            out.append(tab).append("for ")
            fmt(out, f.variable.node)
            out.append(" in ")
            fmt(out, f.over.node)
            out.append(":\n")
            fmtWithTab(out, f.body.node, tab + "  ")
        }
        is Stmt.Def -> {
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
        is Stmt.Load -> {
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



