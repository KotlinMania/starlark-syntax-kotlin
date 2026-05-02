// port-lint: source src/syntax/call.rs
package io.github.kotlinmania.starlarksyntax.syntax.call

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
import io.github.kotlinmania.starlarksyntax.evalexception.EvalException
import io.github.kotlinmania.starlarksyntax.syntax.ast.ArgumentP
import io.github.kotlinmania.starlarksyntax.syntax.ast.AstArgumentP
import io.github.kotlinmania.starlarksyntax.syntax.ast.AstPayload
import io.github.kotlinmania.starlarksyntax.syntax.ast.CallArgsP

/** Validated call arguments. */
class CallArgsUnpack<P : AstPayload>(
    val pos: List<AstArgumentP<P>>,
    val named: List<AstArgumentP<P>>,
    val star: AstArgumentP<P>?,
    val starStar: AstArgumentP<P>?,
) {
    companion object {
        fun <P : AstPayload> unpack(
            args: CallArgsP<P>,
            codemap: CodeMap,
        ): Result<CallArgsUnpack<P>> {
            val err = { span: Span, msg: String ->
                Result.failure<CallArgsUnpack<P>>(EvalException.parserError(msg, span, codemap))
            }

            val argList = args.args

            var stage = ArgsStage.POSITIONAL
            val namedArgs = HashSet<String>()
            var numPos = 0
            var numNamed = 0
            var star: AstArgumentP<P>? = null
            var starStar: AstArgumentP<P>? = null
            for (arg in argList) {
                when (val node = arg.node) {
                    is ArgumentP.Positional<*> -> {
                        if (stage != ArgsStage.POSITIONAL) {
                            return err(arg.span, "positional argument after non positional")
                        } else {
                            numPos += 1
                        }
                    }
                    is ArgumentP.Named<*> -> {
                        if (stage > ArgsStage.NAMED) {
                            return err(arg.span, "named argument after *args or **kwargs")
                        } else if (!namedArgs.add(node.name.node)) {
                            // Check the names are distinct
                            return err(node.name.span, "repeated named argument")
                        } else {
                            stage = ArgsStage.NAMED
                            numNamed += 1
                        }
                    }
                    is ArgumentP.Args<*> -> {
                        if (stage > ArgsStage.NAMED) {
                            return err(arg.span, "Args array after another args or kwargs")
                        } else {
                            stage = ArgsStage.ARGS
                            if (star != null) {
                                return Result.failure(
                                    EvalException.internalError(
                                        "Multiple *args in arguments",
                                        arg.span,
                                        codemap,
                                    )
                                )
                            }
                            star = arg
                        }
                    }
                    is ArgumentP.KwArgs<*> -> {
                        if (stage == ArgsStage.KWARGS) {
                            return err(arg.span, "Multiple kwargs dictionary in arguments")
                        } else {
                            stage = ArgsStage.KWARGS
                            if (starStar != null) {
                                return Result.failure(
                                    EvalException.internalError(
                                        "Multiple **kwargs in arguments",
                                        arg.span,
                                        codemap,
                                    )
                                )
                            }
                            starStar = arg
                        }
                    }
                }
            }

            if (numPos + numNamed + (if (star != null) 1 else 0) + (if (starStar != null) 1 else 0)
                != argList.size
            ) {
                return Result.failure(
                    EvalException.internalError(
                        "Argument count mismatch",
                        Span.mergeAll(argList.map { it.span }),
                        codemap,
                    )
                )
            }

            return Result.success(
                CallArgsUnpack(
                    pos = argList.subList(0, numPos),
                    named = argList.subList(numPos, numPos + numNamed),
                    star = star,
                    starStar = starStar,
                )
            )
        }
    }
}

private enum class ArgsStage {
    POSITIONAL,
    NAMED,
    ARGS,
    KWARGS,
}
