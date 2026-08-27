// port-lint: source src/syntax/def.rs
package io.github.kotlinmania.starlarksyntax.syntax.def

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
import io.github.kotlinmania.starlarksyntax.codemap.Span
import io.github.kotlinmania.starlarksyntax.codemap.Spanned
import io.github.kotlinmania.starlarksyntax.evalexception.EvalException
import io.github.kotlinmania.starlarksyntax.syntax.ast.AstAssignIdent
import io.github.kotlinmania.starlarksyntax.syntax.ast.AstExpr
import io.github.kotlinmania.starlarksyntax.syntax.ast.AstParameter
import io.github.kotlinmania.starlarksyntax.syntax.ast.AstTypeExpr
import io.github.kotlinmania.starlarksyntax.syntax.ast.Parameter

internal enum class DefRegularParamMode {
    PosOnly,
    PosOrName,
    NameOnly,
}

internal sealed class DefParamKind {
    /** Regular parameter, with an optional default value. */
    class Regular(
        val mode: DefRegularParamMode,
        /** Default value. */
        val defaultValue: AstExpr?,
    ) : DefParamKind()
    class Args : DefParamKind()
    class Kwargs : DefParamKind()
}

/** One function parameter. */
internal class DefParam(
    /** Name of the parameter. */
    val ident: AstAssignIdent,
    /**
     * Whether this is a regular parameter (with optional default) or a varargs construct (`*args`,
     * `**kwargs`).
     */
    val kind: DefParamKind,
    /** Type of the parameter. This is null when a type is not specified. */
    val ty: AstTypeExpr?,
)

/**
 * Parameters internally in starlark-rust are commonly represented as a flat list of parameters,
 * with markers `/` and `*` omitted.
 * This struct contains sizes and indices to split the list into parts.
 */
internal data class DefParamIndices(
    /**
     * Number of parameters which can be filled positionally.
     * That is, number of parameters before first `*`, `*args` or `**kwargs`.
     */
    val numPositional: UInt,
    /**
     * Number of parameters which can only be filled positionally.
     * Always less or equal to [numPositional].
     */
    val numPositionalOnly: UInt,
    /**
     * Index of `*args` parameter, if any.
     * If present, equal to [numPositional].
     */
    val args: UInt?,
    /**
     * Index of `**kwargs` parameter, if any.
     * If present, equal to the number of parameters minus 1.
     */
    val kwargs: UInt?,
) {
    fun posOnly(): IntRange = 0 until numPositionalOnly.toInt()

    fun posOrNamed(): IntRange = numPositionalOnly.toInt() until numPositional.toInt()

    fun namedOnly(paramCount: Int): IntRange {
        val start = args?.let { it.toInt() + 1 } ?: numPositional.toInt()
        val end = kwargs?.toInt() ?: paramCount
        return start until end
    }
}

/**
 * Post-processed AST for function parameters.
 *
 * * Validated
 * * `*` parameter replaced with [DefParamIndices.numPositional] field
 */
internal class DefParams(
    val params: List<Spanned<DefParam>>,
    val indices: DefParamIndices,
) {
    companion object {
        fun unpack(
            astParams: List<AstParameter>,
            codemap: CodeMap,
        ): Result<DefParams> {
            val argset = HashSet<String>()
            // You can't have more than one *args/*, **kwargs
            // **kwargs must be last
            // You can't have a required `x` after an optional `y=1`
            var seenOptional = false

            val params = ArrayList<Spanned<DefParam>>(astParams.size)
            var numPositional = 0
            var args: UInt? = null
            var kwargs: UInt? = null

            // Index of `*` parameter, if any.
            var indexOfStar: Int? = null

            val firstSlash = astParams.indexOfFirst { it.node is Parameter.Slash }
            val numPositionalOnly: UInt = when {
                firstSlash < 0 -> 0u
                firstSlash == 0 -> {
                    return Result.failure(
                        EvalException.parserError(
                            "`/` cannot be first parameter",
                            astParams[0].span,
                            codemap,
                        )
                    )
                }
                else -> firstSlash.toUInt()
            }

            var state = if (numPositionalOnly == 0u) State.SeenSlash else State.Normal

            for ((i, param) in astParams.withIndex()) {
                val span = param.span

                val name = param.node.ident()
                if (name != null) {
                    val res = checkParamName(argset, name, param, codemap)
                    if (res.isFailure) return Result.failure(res.exceptionOrNull()!!)
                }

                when (val node = param.node) {
                    is Parameter.Normal -> {
                        if (state >= State.SeenStarStar) {
                            return Result.failure(
                                EvalException.parserError(
                                    "Parameter after kwargs",
                                    param.span,
                                    codemap,
                                )
                            )
                        }
                        val defaultValue = node.default
                        if (defaultValue == null) {
                            if (seenOptional && state < State.SeenStar) {
                                return Result.failure(
                                    EvalException.parserError(
                                        "positional parameter after non positional",
                                        param.span,
                                        codemap,
                                    )
                                )
                            }
                        } else {
                            seenOptional = true
                        }
                        if (state < State.SeenStar) {
                            numPositional += 1
                        }
                        val mode = when {
                            state < State.SeenSlash -> DefRegularParamMode.PosOnly
                            state < State.SeenStar -> DefRegularParamMode.PosOrName
                            else -> DefRegularParamMode.NameOnly
                        }
                        params.add(
                            Spanned(
                                span = span,
                                node = DefParam(
                                    ident = node.name,
                                    kind = DefParamKind.Regular(mode, defaultValue),
                                    ty = node.type,
                                ),
                            )
                        )
                    }
                    is Parameter.NoArgs -> {
                        if (state >= State.SeenStar) {
                            return Result.failure(
                                EvalException.parserError(
                                    "Args parameter after another args or kwargs parameter",
                                    param.span,
                                    codemap,
                                )
                            )
                        }
                        state = State.SeenStar
                        if (indexOfStar != null) {
                            return Result.failure(
                                EvalException.internalError(
                                    "Multiple `*` in parameters, must have been caught earlier",
                                    param.span,
                                    codemap,
                                )
                            )
                        }
                        indexOfStar = i
                    }
                    is Parameter.Slash -> {
                        if (state >= State.SeenSlash) {
                            return Result.failure(
                                EvalException.parserError(
                                    "Multiple `/` in parameters",
                                    param.span,
                                    codemap,
                                )
                            )
                        }
                        state = State.SeenSlash
                    }
                    is Parameter.Args -> {
                        if (state >= State.SeenStar) {
                            return Result.failure(
                                EvalException.parserError(
                                    "Args parameter after another args or kwargs parameter",
                                    param.span,
                                    codemap,
                                )
                            )
                        }
                        state = State.SeenStar
                        if (args != null) {
                            return Result.failure(
                                EvalException.internalError(
                                    "Multiple *args",
                                    param.span,
                                    codemap,
                                )
                            )
                        }
                        args = params.size.toUInt()
                        params.add(
                            Spanned(
                                span = span,
                                node = DefParam(
                                    ident = node.name,
                                    kind = DefParamKind.Args(),
                                    ty = node.type,
                                ),
                            )
                        )
                    }
                    is Parameter.KwArgs -> {
                        if (state >= State.SeenStarStar) {
                            return Result.failure(
                                EvalException.parserError(
                                    "Multiple kwargs dictionary in parameters",
                                    param.span,
                                    codemap,
                                )
                            )
                        }
                        if (kwargs != null) {
                            return Result.failure(
                                EvalException.internalError(
                                    "Multiple **kwargs",
                                    param.span,
                                    codemap,
                                )
                            )
                        }
                        kwargs = params.size.toUInt()
                        state = State.SeenStarStar
                        params.add(
                            Spanned(
                                span = span,
                                node = DefParam(
                                    ident = node.name,
                                    kind = DefParamKind.Kwargs(),
                                    ty = node.type,
                                ),
                            )
                        )
                    }
                }
            }

            if (indexOfStar != null) {
                val next = astParams.getOrNull(indexOfStar + 1)
                    ?: return Result.failure(
                        EvalException.parserError(
                            "`*` parameter must not be last",
                            astParams[indexOfStar].span,
                            codemap,
                        )
                    )
                when (next.node) {
                    is Parameter.Normal -> {}
                    is Parameter.KwArgs,
                    is Parameter.Args,
                    is Parameter.NoArgs,
                    is Parameter.Slash -> {
                        // We get here only for `**kwargs`, the rest is handled above.
                        return Result.failure(
                            EvalException.parserError(
                                "`*` must be followed by named parameter",
                                next.span,
                                codemap,
                            )
                        )
                    }
                }
            }

            return Result.success(
                DefParams(
                    params = params,
                    indices = DefParamIndices(
                        numPositional = numPositional.toUInt(),
                        numPositionalOnly = numPositionalOnly,
                        args = args,
                        kwargs = kwargs,
                    ),
                )
            )
        }

        private fun <T> checkParamName(
            argset: HashSet<String>,
            n: AstAssignIdent,
            arg: Spanned<T>,
            codemap: CodeMap,
        ): Result<Unit> {
            if (!argset.add(n.node.ident)) {
                return Result.failure(
                    EvalException.parserError(
                        "duplicated parameter name",
                        arg.span,
                        codemap,
                    )
                )
            }
            return Result.success(Unit)
        }
    }
}

private enum class State {
    Normal,
    /** After `/`. */
    SeenSlash,
    /** After `*` or `*args`. */
    SeenStar,
    /** After `**kwargs`. */
    SeenStarStar,
}

