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
import io.github.kotlinmania.starlarksyntax.syntax.ast.AstExprP
import io.github.kotlinmania.starlarksyntax.syntax.ast.AstIdentP
import io.github.kotlinmania.starlarksyntax.syntax.ast.AstLiteral
import io.github.kotlinmania.starlarksyntax.syntax.ast.AstPayload
import io.github.kotlinmania.starlarksyntax.syntax.ast.BinOp
import io.github.kotlinmania.starlarksyntax.syntax.ast.ExprP

sealed class TypeExprUnpackError(message: String) : Exception(message) {
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
fun typeStrLiteralIsWildcard(s: String): Boolean {
    return s == "" || s.startsWith('_')
}

/** Path component of type. */
data class TypePathP<P : AstPayload>(
    val first: AstIdentP<P>,
    val rem: List<Spanned<String>>,
)

/** This type should be used instead of [TypeExprP], but a lot of code needs to be updated. */
sealed class TypeExprUnpackP<P : AstPayload> {
    class Ellipsis<P : AstPayload> : TypeExprUnpackP<P>()
    data class Path<P : AstPayload>(val path: TypePathP<P>) : TypeExprUnpackP<P>()

    /** `list[str]`. */
    data class Index<P : AstPayload>(
        val ident: AstIdentP<P>,
        val index: Spanned<TypeExprUnpackP<P>>,
    ) : TypeExprUnpackP<P>()

    /** `dict[str, int]` or `typing.Callable[[int], str]`. */
    data class Index2<P : AstPayload>(
        val path: Spanned<TypePathP<P>>,
        val i0: Spanned<TypeExprUnpackP<P>>,
        val i1: Spanned<TypeExprUnpackP<P>>,
    ) : TypeExprUnpackP<P>()

    /** List argument in `typing.Callable[[int], str]`. */
    data class List<P : AstPayload>(
        val items: kotlin.collections.List<Spanned<TypeExprUnpackP<P>>>,
    ) : TypeExprUnpackP<P>()

    data class Union<P : AstPayload>(
        val xs: kotlin.collections.List<Spanned<TypeExprUnpackP<P>>>,
    ) : TypeExprUnpackP<P>()

    data class Tuple<P : AstPayload>(
        val xs: kotlin.collections.List<Spanned<TypeExprUnpackP<P>>>,
    ) : TypeExprUnpackP<P>()

    companion object {
        private fun <P : AstPayload> unpackPath(
            expr: AstExprP<P>,
            codemap: CodeMap,
        ): Spanned<TypePathP<P>> {
            val span = expr.span
            return when (val node = expr.node) {
                is ExprP.Identifier<P> -> Spanned(
                    node = TypePathP(
                        first = node.ident,
                        rem = emptyList(),
                    ),
                    span = span,
                )
                is ExprP.Dot<P> -> {
                    var current: AstExprP<P> = node.target
                    val rem: MutableList<Spanned<String>> =
                        mutableListOf(Spanned(node = node.attr.node, span = node.attr.span))
                    while (true) {
                        when (val cur = current.node) {
                            is ExprP.Dot<P> -> {
                                current = cur.target
                                rem.add(Spanned(node = cur.attr.node, span = cur.attr.span))
                            }
                            is ExprP.Identifier<P> -> {
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
                                    node = TypePathP(first = cur.ident, rem = rem),
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

        private fun <P : AstPayload> unpackArgument(
            expr: AstExprP<P>,
            codemap: CodeMap,
        ): Spanned<TypeExprUnpackP<P>> {
            val span = expr.span
            return when (val node = expr.node) {
                is ExprP.List<P> -> {
                    val items = node.elems.map { x -> unpackArgument(x, codemap) }
                    Spanned(
                        node = List(items),
                        span = span,
                    )
                }
                else -> unpack(expr, codemap)
            }
        }

        fun <P : AstPayload> unpack(
            expr: AstExprP<P>,
            codemap: CodeMap,
        ): Spanned<TypeExprUnpackP<P>> {
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
                is ExprP.Tuple<P> -> {
                    val xs = node.elems.map { x -> unpack(x, codemap) }
                    Spanned(node = Tuple(xs), span = span)
                }
                is ExprP.Dot<P> -> {
                    val path = unpackPath(expr, codemap)
                    Spanned(node = Path(path.node), span = span)
                }
                is ExprP.Call<P> -> err("call")
                is ExprP.Index<P> -> {
                    val a = node.target
                    val i = node.index
                    when (val aNode = a.node) {
                        is ExprP.Identifier<P> -> {
                            val unpacked = unpack(i, codemap)
                            Spanned(
                                node = Index(aNode.ident, unpacked),
                                span = span,
                            )
                        }
                        else -> err("array indirection where array is not an identifier")
                    }
                }
                is ExprP.Index2<P> -> {
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
                is ExprP.Slice<P> -> err("slice")
                is ExprP.Identifier<P> -> {
                    val path = unpackPath(expr, codemap)
                    Spanned(node = Path(path.node), span = span)
                }
                is ExprP.Lambda<P> -> err("lambda")
                is ExprP.Literal<P> -> when (node.literal) {
                    // TODO(nga): eventually this should be allowed for self-referential types:
                    //   https://www.internalfb.com/tasks/?t=184482361
                    is AstLiteral.String -> err("string literal")
                    is AstLiteral.Int -> err("int")
                    is AstLiteral.Float -> err("float")
                    is AstLiteral.Ellipsis -> Spanned(node = Ellipsis(), span = span)
                }
                is ExprP.Not<P> -> err("not")
                is ExprP.Minus<P> -> err("minus")
                is ExprP.Plus<P> -> err("plus")
                is ExprP.BitNot<P> -> err("bit not")
                is ExprP.Op<P> -> {
                    if (node.op == BinOp.BitOr) {
                        val a = unpack(node.left, codemap)
                        val b = unpack(node.right, codemap)
                        Spanned(node = Union(listOf(a, b)), span = span)
                    } else {
                        err("bin op except `|`")
                    }
                }
                is ExprP.If<P> -> err("if")
                is ExprP.List<P> -> {
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
                is ExprP.Dict<P> -> err("dict")
                is ExprP.ListComprehension<P> -> err("list comprehension")
                is ExprP.DictComprehension<P> -> err("dict comprehension")
                is ExprP.FString<P> -> err("f-string")
            }
        }
    }
}

/**
 * Exception wrapper for [WithDiagnostic] results, allowing diagnostic-bearing
 * failures to flow through Kotlin's exception machinery.
 */
class WithDiagnosticException(
    val diagnostic: WithDiagnostic<TypeExprUnpackError>,
) : Exception(diagnostic.inner().message)
