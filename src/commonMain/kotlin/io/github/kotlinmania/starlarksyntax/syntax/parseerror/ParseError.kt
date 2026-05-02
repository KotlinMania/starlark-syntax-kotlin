// port-lint: source src/syntax/parse_error.rs
package io.github.kotlinmania.starlarksyntax.syntax.parseerror

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

//! Parser-agnostic error type for parse failures.

import io.github.kotlinmania.starlarksyntax.codemap.CodeMap
import io.github.kotlinmania.starlarksyntax.codemap.Span
import io.github.kotlinmania.starlarksyntax.error.Error
import io.github.kotlinmania.starlarksyntax.error.ErrorKind
import io.github.kotlinmania.starlarksyntax.evalexception.EvalException

/**
 * A parse error with a message and source span.
 * This is the common error type returned by any parser implementation,
 * independent of the underlying parsing strategy (LALRPOP, recursive descent, etc.).
 */
internal sealed class ParseError {
    /** An error with a message and span, to be rendered as a diagnostic. */
    class Error(val message: String, val span: Span) : ParseError()

    /**
     * An error that already has full diagnostic information (e.g. from
     * user-defined error callbacks in the parser state).
     */
    class EvalExceptionError(val evalException: EvalException) : ParseError()

    /** Convert this parse error into a [Error] with source location. */
    fun intoCrateError(codemap: CodeMap): io.github.kotlinmania.starlarksyntax.error.Error {
        return when (this) {
            is ParseError.Error -> io.github.kotlinmania.starlarksyntax.error.Error.newSpanned(
                ErrorKind.Parser(Exception(message)),
                span,
                codemap,
            )
            is ParseError.EvalExceptionError -> evalException.intoError()
        }
    }
}
