// port-lint: source src/syntax/validate.rs
package io.github.kotlinmania.starlarksyntax.syntax.validate

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

//! AST validation for parsed starlark files.

import io.github.kotlinmania.starlarksyntax.syntax.ast.AstArgument
import io.github.kotlinmania.starlarksyntax.syntax.ast.AstExpr
import io.github.kotlinmania.starlarksyntax.syntax.ast.AstLiteral
import io.github.kotlinmania.starlarksyntax.syntax.ast.AstParameter
import io.github.kotlinmania.starlarksyntax.syntax.ast.AstStmt
import io.github.kotlinmania.starlarksyntax.syntax.ast.CallArgsP
import io.github.kotlinmania.starlarksyntax.syntax.ast.ExprP
import io.github.kotlinmania.starlarksyntax.syntax.ast.ParameterP
import io.github.kotlinmania.starlarksyntax.syntax.ast.StmtP
import io.github.kotlinmania.starlarksyntax.syntax.uniplate.visitExpr
import io.github.kotlinmania.starlarksyntax.syntax.uniplate.visitStmt
import io.github.kotlinmania.starlarksyntax.syntax.call.CallArgsUnpack
import io.github.kotlinmania.starlarksyntax.syntax.def.DefParams
import io.github.kotlinmania.starlarksyntax.dialect.DialectTypes
import io.github.kotlinmania.starlarksyntax.syntax.state.ParserState

/**
 * We want to check a function call is well-formed.
 * Our eventual plan is to follow the Python invariants, but for now, we are closer
 * to the Starlark invariants.
 *
 * Python invariants are no positional arguments after named arguments,
 * no *args after **kwargs, no repeated argument names.
 *
 * Starlark invariants are the above, plus at most one *args and the *args must appear
 * after all positional and named arguments. The spec is silent on whether you are allowed
 * multiple **kwargs.
 *
 * We allow at most one **kwargs.
 */
internal fun checkCall(
    f: AstExpr,
    args: List<AstArgument>,
    parserState: ParserState,
): ExprP<io.github.kotlinmania.starlarksyntax.syntax.ast.AstNoPayload> {
    val callArgs = CallArgsP<io.github.kotlinmania.starlarksyntax.syntax.ast.AstNoPayload>(args)

    val unpackResult = CallArgsUnpack.unpack(callArgs, parserState.codemap)
    unpackResult.exceptionOrNull()?.let { e ->
        if (e is io.github.kotlinmania.starlarksyntax.evalexception.EvalException) {
            parserState.errors.add(e)
        }
    }

    return ExprP.Call(f, callArgs)
}

/** Validate all statements only occur where they are allowed to. */
internal fun validateModule(stmt: AstStmt, parserState: ParserState) {
    fun validateParams(params: List<AstParameter>, parserState: ParserState) {
        if (!parserState.dialect.enableKeywordOnlyArguments) {
            for (param in params) {
                if (param.node is ParameterP.NoArgs) {
                    parserState.error(
                        param.span,
                        "* keyword-only-arguments is not allowed in this dialect",
                    )
                }
            }
        }
        if (!parserState.dialect.enablePositionalOnlyArguments) {
            for (param in params) {
                if (param.node is ParameterP.Slash) {
                    parserState.error(
                        param.span,
                        "/ positional-only-arguments is not allowed in this dialect",
                    )
                }
            }
        }
        val unpackResult = DefParams.unpack(params, parserState.codemap)
        unpackResult.exceptionOrNull()?.let { e ->
            if (e is io.github.kotlinmania.starlarksyntax.evalexception.EvalException) {
                parserState.errors.add(e)
            }
        }
    }

    // Inside a for, we allow continue/break, unless we go beneath a def.
    // Inside a def, we allow return.
    // All load's must occur at the top-level.
    // At the top-level we only allow for/if when the dialect permits it.
    fun f(
        stmt: AstStmt,
        parserState: ParserState,
        topLevel: Boolean,
        insideFor: Boolean,
        insideDef: Boolean,
    ) {
        val span = stmt.span

        when (val node = stmt.node) {
            is StmtP.Def -> {
                if (!parserState.dialect.enableDef) {
                    parserState.error(span, "`def` is not allowed in this dialect")
                }
                validateParams(node.def.params, parserState)
                f(node.def.body, parserState, false, false, true)
            }
            is StmtP.For -> {
                if (topLevel && !parserState.dialect.enableTopLevelStmt) {
                    parserState.error(span, "`for` cannot be used outside `def` in this dialect")
                } else {
                    f(node.forStmt.body, parserState, false, true, insideDef)
                }
            }
            is StmtP.If, is StmtP.IfElse -> {
                if (topLevel && !parserState.dialect.enableTopLevelStmt) {
                    parserState.error(span, "`if` cannot be used outside `def` in this dialect")
                } else {
                    node.visitStmt { x -> f(x, parserState, false, insideFor, insideDef) }
                }
            }
            is StmtP.Break -> if (!insideFor) {
                parserState.error(span, "`break` cannot be used outside of a `for` loop")
            }
            is StmtP.Continue -> if (!insideFor) {
                parserState.error(span, "`continue` cannot be used outside of a `for` loop")
            }
            is StmtP.Return -> if (!insideDef) {
                parserState.error(span, "`return` cannot be used outside of a `def` function")
            }
            is StmtP.Load -> {
                if (!topLevel) {
                    parserState.error(span, "`load` must only occur at the top of a module")
                }
                if (!parserState.dialect.enableLoad) {
                    parserState.error(span, "`load` is not allowed in this dialect")
                }
            }
            else -> node.visitStmt { x -> f(x, parserState, topLevel, insideFor, insideDef) }
        }
    }

    fun expr(x: AstExpr, parserState: ParserState) {
        when (val node = x.node) {
            is ExprP.Literal -> {
                if (node.literal is AstLiteral.Ellipsis) {
                    if (parserState.dialect.enableTypes == DialectTypes.Disable) {
                        parserState.error(x.span, "`...` is not allowed in this dialect")
                    }
                }
            }
            is ExprP.Lambda -> {
                if (!parserState.dialect.enableLambda) {
                    parserState.error(x.span, "`lambda` is not allowed in this dialect")
                }
                validateParams(node.lambda.params, parserState)
            }
            else -> {}
        }
        x.node.visitExpr { y -> expr(y, parserState) }
    }

    f(stmt, parserState, true, false, false)

    stmt.visitExpr { x -> expr(x, parserState) }
}
