// port-lint: source src/syntax/parser_lalrpop.rs
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

//! LALRPOP-backed [Parser] implementation.

import io.github.kotlinmania.lalrpoputil.ParseError as LuParseError
import io.github.kotlinmania.starlarksyntax.codemap.Pos
import io.github.kotlinmania.starlarksyntax.codemap.Span
import io.github.kotlinmania.starlarksyntax.evalexception.EvalException
import io.github.kotlinmania.starlarksyntax.lexer.Token
import io.github.kotlinmania.starlarksyntax.syntax.ast.AstStmt
import io.github.kotlinmania.starlarksyntax.syntax.grammar.StarlarkParser
import io.github.kotlinmania.starlarksyntax.syntax.parseerror.ParseError
import io.github.kotlinmania.starlarksyntax.syntax.state.ParserState

private fun oneOf(expected: List<String>): String {
    val result = StringBuilder()
    for ((i, e) in expected.withIndex()) {
        val sep =
            when {
                i == 0 -> "one of"
                i < expected.size - 1 -> ","
                else -> " or"
            }
        result.append(sep).append(' ').append(e)
    }
    return result.toString()
}

/** Convert a LALRPOP parse error into our common [ParseError]. */
internal fun lalrpopErrorToParseError(
    err: LuParseError<Int, Token, EvalException>,
    eofPos: Int,
): ParseError {
    return when (err) {
        is LuParseError.InvalidToken -> ParseError.Error(
            message = "Parse error: invalid token",
            span = Span.new(Pos.new(err.location), Pos.new(err.location)),
        )
        is LuParseError.UnrecognizedToken -> {
            val (x, t, y) = err.token
            ParseError.Error(
                message = "Parse error: unexpected $t here, expected ${oneOf(err.expected)}",
                span = Span.new(Pos.new(x), Pos.new(y)),
            )
        }
        is LuParseError.UnrecognizedEof -> ParseError.Error(
            message = "Parse error: unexpected end of file",
            span = Span.new(Pos.new(eofPos), Pos.new(eofPos)),
        )
        is LuParseError.ExtraToken -> {
            val (x, t, y) = err.token
            ParseError.Error(
                message = "Parse error: extraneous token $t",
                span = Span.new(Pos.new(x), Pos.new(y)),
            )
        }
        is LuParseError.User -> ParseError.EvalExceptionError(err.error)
    }
}

/** LALRPOP-backed parser. */
internal class LalrpopParser : Parser {
    override fun parseModule(
        state: ParserState,
        tokens: Iterator<Lexeme>,
        eofPos: Int,
    ): Result<AstStmt, ParseError> {
        return when (val parsed = StarlarkParser().parse(state, tokens)) {
            is Result.Ok -> Result.Ok(parsed.value)
            is Result.Err -> Result.Err(lalrpopErrorToParseError(parsed.error, eofPos))
        }
    }
}
