// port-lint: source src/eval_exception.rs
package io.github.kotlinmania.starlarksyntax.evalexception

/*
 * Copyright 2019 The Starlark in Rust Authors.
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

import io.github.kotlinmania.starlarksyntax.callstack.CallStack
import io.github.kotlinmania.starlarksyntax.codemap.CodeMap
import io.github.kotlinmania.starlarksyntax.codemap.ResolvedFileSpan
import io.github.kotlinmania.starlarksyntax.codemap.Span
import io.github.kotlinmania.starlarksyntax.diagnostic.WithDiagnostic
import io.github.kotlinmania.starlarksyntax.error.Error
import io.github.kotlinmania.starlarksyntax.error.ErrorKind
import io.github.kotlinmania.starlarksyntax.error.internalError

/** Error with location. */
class EvalException internal constructor(
    /** Error is guaranteed to have a diagnostic. */
    internal val error: Error,
) {
    companion object {
        fun new(error: Error, span: Span, codemap: CodeMap): EvalException {
            error.setSpan(span, codemap)
            return EvalException(error)
        }

        /**
         * `EvalException` is meant to provide type-safe guard against missing span.
         * Sometimes we need to construct `EvalException`, but span is not available,
         * so this function can be used. Avoid this function if possible.
         */
        fun newUnknownSpan(error: Error): EvalException {
            return EvalException(error)
        }

        fun newWithCallstack(
            error: Error,
            span: Span,
            codemap: CodeMap,
            callStack: () -> CallStack,
        ): EvalException {
            error.setSpan(span, codemap)
            error.setCallStack(callStack)
            return EvalException(error)
        }

        fun newAnyhow(error: Throwable, span: Span, codemap: CodeMap): EvalException {
            return EvalException(
                Error.newSpanned(
                    ErrorKind.Other(error),
                    span,
                    codemap,
                )
            )
        }

        fun internalError(error: Any, span: Span, codemap: CodeMap): EvalException {
            return new(internalError(error.toString()), span, codemap)
        }

        internal fun parserError(
            error: Any,
            span: Span,
            codemap: CodeMap,
        ): EvalException {
            return EvalException(
                Error.newSpanned(
                    ErrorKind.Parser(Exception(error.toString())),
                    span,
                    codemap,
                )
            )
        }

        fun testingLoc(err: Error): ResolvedFileSpan {
            val d = err.span()
            return d?.resolve() ?: error("Expected error with diagnostic, got $err")
        }

        fun from(e: WithDiagnostic<Error>): EvalException {
            // The Rust impl is `impl<T: Into<crate::Error>> From<WithDiagnostic<T>> for EvalException`,
            // i.e. given a WithDiagnostic<T> where T can be turned into Error, build an EvalException
            // that holds the resulting Error with the same diagnostic.
            return EvalException(e.intoInner())
        }
    }

    fun intoError(): Error = error

    fun intoInternalError(): EvalException {
        return EvalException(error.intoInternalError())
    }

    override fun toString(): String = error.toString()
}
