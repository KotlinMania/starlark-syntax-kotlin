// port-lint: source src/syntax/type_expr.rs
package io.github.kotlinmania.starlarksyntax.syntax.typeexpr

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
import io.github.kotlinmania.starlarksyntax.codemap.Spanned
import io.github.kotlinmania.starlarksyntax.diagnostic.WithDiagnostic
import io.github.kotlinmania.starlarksyntax.syntax.ast.AstExpr
import io.github.kotlinmania.starlarksyntax.syntax.ast.AstIdent
import io.github.kotlinmania.starlarksyntax.syntax.ast.AstLiteral
import io.github.kotlinmania.starlarksyntax.syntax.ast.BinOp
import io.github.kotlinmania.starlarksyntax.syntax.ast.Expr

internal sealed class TypeExprUnpackError(message: String) : Exception(message) {
    /** `{0} expression is not allowed in type expression` */
    class InvalidType(val kind: String) :
        TypeExprUnpackError("$kind expression is not allowed in type expression")

    /** `Empty list is not allowed in type expression` */
    class EmptyListInType : TypeExprUnpackError("Empty list is not allowed in type expression")

    /** `Only dot expression of form `ident.ident` is allowed in type expression` */
    class DotInType :
        TypeExprUnpackError("Only dot expression of form `ident.ident` is allowed in type expression")

    /** `Expecting path like `a.b.c`` */
    class ExpectingPath : TypeExprUnpackError("Expecting path like `a.b.c`")

    /** `` `{0}.type` is not allowed in type expression, use `{0}` instead `` */
    class DotTypeBan(val name: String) :
        TypeExprUnpackError("`$name.type` is not allowed in type expression, use `$name` instead")
}

/**
 * Types that are `""` or start with `"_"` are wildcard - they match everything
 * (also deprecated).
 */
internal fun typeStrLiteralIsWildcard(s: String): Boolean {
    return s == "" || s.startsWith('_')
}

/** Path component of type. */
internal data class TypePath(
    val first: AstIdent,
    val rem: List<Spanned<String>>,
)

/** This type should be used instead of [TypeExpr], but a lot of code needs to be updated. */
internal sealed class TypeExprUnpack {
    class Ellipsis : TypeExprUnpack()
    data class Path(val path: TypePath) : TypeExprUnpack()

    /** `list[str]`. */
    data class Index(
        val ident: AstIdent,
        val index: Spanned<TypeExprUnpack>,
    ) : TypeExprUnpack()

    /** `dict[str, int]` or `typing.Callable[[int], str]`. */
    data class Index2(
        val path: Spanned<TypePath>,
        val i0: Spanned<TypeExprUnpack>,
        val i1: Spanned<TypeExprUnpack>,
    ) : TypeExprUnpack()

    /** List argument in `typing.Callable[[int], str]`. */
    data class List(
        val items: kotlin.collections.List<Spanned<TypeExprUnpack>>,
    ) : TypeExprUnpack()

    data class Union(
        val xs: kotlin.collections.List<Spanned<TypeExprUnpack>>,
    ) : TypeExprUnpack()

    data class Tuple(
        val xs: kotlin.collections.List<Spanned<TypeExprUnpack>>,
    ) : TypeExprUnpack()

    companion object {
        private fun unpackPath(
            expr: AstExpr,
            codemap: CodeMap,
        ): Spanned<TypePath> {
            val span = expr.span
            return when (val node = expr.node) {
                is Expr.Identifier -> Spanned(
                    node = TypePath(
                        first = node.ident,
                        rem = emptyList(),
                    ),
                    span = span,
                )
                is Expr.Dot -> {
                    var current: AstExpr = node.target
                    val rem: MutableList<Spanned<String>> =
                        mutableListOf(Spanned(node = node.attr.node, span = node.attr.span))
                    while (true) {
                        when (val cur = current.node) {
                            is Expr.Dot -> {
                                current = cur.target
                                rem.add(Spanned(node = cur.attr.node, span = cur.attr.span))
                            }
                            is Expr.Identifier -> {
                                rem.reverse()
                                val last = rem.lastOrNull()
                                if (last != null && last.node == "type") {
                                    val butLast = rem.dropLast(1)
                                    var fullPath = cur.ident.node.ident
                                    for (elem in butLast) {
                                        fullPath += ".${elem.node}"
                                    }
                                    // TODO(nga): allow it after we prohibit
                                    //   string constants as types.
                                    throw WithDiagnosticException(
                                        WithDiagnostic.newSpanned(
                                            TypeExprUnpackError.DotTypeBan(fullPath),
                                            current.span,
                                            codemap,
                                        )
                                    )
                                }
                                return Spanned(
                                    node = TypePath(first = cur.ident, rem = rem),
                                    span = span,
                                )
                            }
                            else -> throw WithDiagnosticException(
                                WithDiagnostic.newSpanned(
                                    TypeExprUnpackError.DotInType(),
                                    current.span,
                                    codemap,
                                )
                            )
                        }
                    }
                    error("unreachable")
                }
                else -> throw WithDiagnosticException(
                    WithDiagnostic.newSpanned(
                        TypeExprUnpackError.ExpectingPath(),
                        expr.span,
                        codemap,
                    )
                )
            }
        }

        private fun unpackArgument(
            expr: AstExpr,
            codemap: CodeMap,
        ): Spanned<TypeExprUnpack> {
            val span = expr.span
            return when (val node = expr.node) {
                is Expr.List -> {
                    val items = node.elems.map { x -> unpackArgument(x, codemap) }
                    Spanned(
                        node = List(items),
                        span = span,
                    )
                }
                else -> unpack(expr, codemap)
            }
        }

        fun unpack(
            expr: AstExpr,
            codemap: CodeMap,
        ): Spanned<TypeExprUnpack> {
            val span = expr.span
            fun err(t: String): Nothing {
                throw WithDiagnosticException(
                    WithDiagnostic.newSpanned(
                        TypeExprUnpackError.InvalidType(t),
                        expr.span,
                        codemap,
                    )
                )
            }

            return when (val node = expr.node) {
                is Expr.Tuple -> {
                    val xs = node.elems.map { x -> unpack(x, codemap) }
                    Spanned(node = Tuple(xs), span = span)
                }
                is Expr.Dot -> {
                    val path = unpackPath(expr, codemap)
                    Spanned(node = Path(path.node), span = span)
                }
                is Expr.Call -> err("call")
                is Expr.Index -> {
                    val a = node.target
                    val i = node.index
                    when (val aNode = a.node) {
                        is Expr.Identifier -> {
                            val unpacked = unpack(i, codemap)
                            Spanned(
                                node = Index(aNode.ident, unpacked),
                                span = span,
                            )
                        }
                        else -> err("array indirection where array is not an identifier")
                    }
                }
                is Expr.Index2 -> {
                    val a = node.target
                    val i0 = node.index0
                    val i1 = node.index1
                    val path = unpackPath(a, codemap)
                    val unpackedI0 = unpackArgument(i0, codemap)
                    val unpackedI1 = unpackArgument(i1, codemap)
                    Spanned(
                        node = Index2(path, unpackedI0, unpackedI1),
                        span = span,
                    )
                }
                is Expr.Slice -> err("slice")
                is Expr.Identifier -> {
                    val path = unpackPath(expr, codemap)
                    Spanned(node = Path(path.node), span = span)
                }
                is Expr.Lambda -> err("lambda")
                is Expr.Literal -> when (node.literal) {
                    // TODO(nga): eventually this should be allowed for self-referential types:
                    //   https://www.internalfb.com/tasks/?t=184482361
                    is AstLiteral.StringLiteral -> err("string literal")
                    is AstLiteral.IntLiteral -> err("int")
                    is AstLiteral.FloatLiteral -> err("float")
                    is AstLiteral.EllipsisLiteral -> Spanned(node = Ellipsis(), span = span)
                }
                is Expr.Not -> err("not")
                is Expr.Minus -> err("minus")
                is Expr.Plus -> err("plus")
                is Expr.BitNot -> err("bit not")
                is Expr.Op -> {
                    if (node.op == BinOp.BitOr) {
                        val a = unpack(node.left, codemap)
                        val b = unpack(node.right, codemap)
                        Spanned(node = Union(listOf(a, b)), span = span)
                    } else {
                        err("bin op except `|`")
                    }
                }
                is Expr.If -> err("if")
                is Expr.List -> {
                    val xs = node.elems
                    if (xs.isEmpty()) {
                        throw WithDiagnosticException(
                            WithDiagnostic.newSpanned(
                                TypeExprUnpackError.EmptyListInType(),
                                expr.span,
                                codemap,
                            )
                        )
                    } else if (xs.size == 1) {
                        err("list of 1 element")
                    } else {
                        val unpacked = xs.map { x -> unpack(x, codemap) }
                        Spanned(node = Union(unpacked), span = span)
                    }
                }
                is Expr.Dict -> err("dict")
                is Expr.ListComprehension -> err("list comprehension")
                is Expr.DictComprehension -> err("dict comprehension")
                is Expr.FString -> err("f-string")
            }
        }
    }
}

/**
 * Exception wrapper for [WithDiagnostic] results, allowing diagnostic-bearing
 * failures to flow through Kotlin's exception machinery.
 */
internal class WithDiagnosticException(
    val diagnostic: WithDiagnostic<TypeExprUnpackError>,
) : Exception(diagnostic.inner().message)


