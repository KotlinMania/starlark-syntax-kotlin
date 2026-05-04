// port-lint: source src/syntax/parser.rs
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

//! Parser abstraction for Starlark.
//!
//! [Parser] is the common interface implemented by each parser backend.
//! Today the only impl is the LALRPOP-backed one; additional
//! impls (e.g. recursive descent) plug in here.

import io.github.kotlinmania.starlarksyntax.lexer.Token
import io.github.kotlinmania.starlarksyntax.evalexception.EvalException
import io.github.kotlinmania.starlarksyntax.syntax.ast.AstStmt
import io.github.kotlinmania.starlarksyntax.syntax.parseerror.ParseError
import io.github.kotlinmania.starlarksyntax.syntax.state.ParserState

internal typealias Lexeme = Result<Triple<Int, Token, Int>, EvalException>

/**
 * Parse a Starlark module from a token stream.
 *
 * Implementors normalize backend-specific errors into [ParseError] so
 * callers can render diagnostics independently of the parser in use.
 */
internal fun interface Parser {
    fun parseModule(
        state: ParserState,
        tokens: Iterator<Lexeme>,
        eofPos: Int,
    ): Result<AstStmt, ParseError>
}

internal sealed class Result<out T, out E> {
    data class Ok<T>(val value: T) : Result<T, Nothing>()

    data class Err<E>(val error: E) : Result<Nothing, E>()
}
