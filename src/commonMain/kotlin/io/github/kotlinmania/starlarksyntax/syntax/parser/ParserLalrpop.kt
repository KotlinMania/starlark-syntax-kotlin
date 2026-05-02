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

import io.github.kotlinmania.starlarksyntax.codemap.Pos
import io.github.kotlinmania.starlarksyntax.codemap.Span
import io.github.kotlinmania.starlarksyntax.evalexception.EvalException
import io.github.kotlinmania.starlarksyntax.lexer.Token
import io.github.kotlinmania.starlarksyntax.syntax.ast.AstStmt
import io.github.kotlinmania.starlarksyntax.syntax.grammar.StarlarkParser
import io.github.kotlinmania.starlarksyntax.syntax.parseerror.ParseError
import io.github.kotlinmania.starlarksyntax.syntax.state.ParserState

private fun oneOf(expected: List<String>): String {
    val out = StringBuilder()
    for ((i, e) in expected.withIndex()) {
        val sep = when {
            i == 0 -> "one of"
            i < expected.size - 1 -> ","
            else -> " or"
        }
        out.append(sep).append(' ').append(e)
    }
    return out.toString()
}

/**
 * Parse-error variants emitted by the lalrpop runtime. Mirrors `lalrpop_util::ParseError`.
 *
 * The Kotlin port of `lalrpop-util` lives in the `lalrpop-kotlin` companion artifact; once
 * that's wired in this can be replaced with a direct import.
 */
internal sealed class LalrpopParseError {
    class InvalidToken(val location: Int) : LalrpopParseError()
    class UnrecognizedToken(val token: Triple<Int, Token, Int>, val expected: List<String>) : LalrpopParseError()
    class UnrecognizedEof(val location: Int, val expected: List<String>) : LalrpopParseError()
    class ExtraToken(val token: Triple<Int, Token, Int>) : LalrpopParseError()
    class User(val error: EvalException) : LalrpopParseError()
}

/** Convert a LALRPOP parse error into our common [ParseError]. */
private fun lalrpopErrorToParseError(err: LalrpopParseError, eofPos: Int): ParseError {
    return when (err) {
        is LalrpopParseError.InvalidToken -> ParseError.Error(
            message = "Parse error: invalid token",
            span = Span.new(Pos.new(err.location), Pos.new(err.location)),
        )
        is LalrpopParseError.UnrecognizedToken -> {
            val (x, t, y) = err.token
            ParseError.Error(
                message = "Parse error: unexpected $t here, expected ${oneOf(err.expected)}",
                span = Span.new(Pos.new(x), Pos.new(y)),
            )
        }
        is LalrpopParseError.UnrecognizedEof -> ParseError.Error(
            message = "Parse error: unexpected end of file",
            span = Span.new(Pos.new(eofPos), Pos.new(eofPos)),
        )
        is LalrpopParseError.ExtraToken -> {
            val (x, t, y) = err.token
            ParseError.Error(
                message = "Parse error: extraneous token $t",
                span = Span.new(Pos.new(x), Pos.new(y)),
            )
        }
        is LalrpopParseError.User -> ParseError.EvalExceptionError(err.error)
    }
}

/** LALRPOP-backed parser. */
internal object LalrpopParser : Parser {
    override fun parseModule(
        state: ParserState,
        tokens: Iterator<Lexeme>,
        eofPos: Int,
    ): kotlin.Result<AstStmt> {
        return StarlarkParser.new()
            .parse(state, tokens)
            .recoverCatching { e ->
                if (e is LalrpopParseErrorException) {
                    val translated = lalrpopErrorToParseError(e.error, eofPos)
                    throw Exception(translated.intoCrateError(state.codemap).toString())
                }
                throw e
            }
    }
}

/** Wrapper exception carrying a [LalrpopParseError] through Kotlin's [kotlin.Result]/[Throwable] APIs. */
internal class LalrpopParseErrorException(val error: LalrpopParseError) : Exception()
