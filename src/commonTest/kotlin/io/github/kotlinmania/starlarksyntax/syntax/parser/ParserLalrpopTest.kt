// port-lint: tests syntax/parser_lalrpop.rs
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

import io.github.kotlinmania.starlarksyntax.codemap.CodeMap
import io.github.kotlinmania.starlarksyntax.lexer.Token
import kotlin.test.Test
import kotlin.test.assertEquals

class ParserLalrpopTest {

    @Test
    fun testLalrpopErrorToParseError() {
        val codemap = CodeMap.new("test.bzl", "pass")

        fun assertParseError(
            parseError: LalrpopParseError,
            eofPos: Int,
            wantMessage: String,
            wantSpan: String,
        ) {
            val err = lalrpopErrorToParseError(parseError, eofPos).intoCrateError(codemap)
            assertEquals(wantMessage, err.withoutDiagnostic().toString())
            assertEquals(wantSpan, err.span()!!.toString())
        }

        assertParseError(
            LalrpopParseError.InvalidToken(location = 2),
            4,
            "Parse error: invalid token",
            "test.bzl:1:3",
        )
        assertParseError(
            LalrpopParseError.UnrecognizedEof(location = 1, expected = emptyList()),
            4,
            "Parse error: unexpected end of file",
            "test.bzl:1:5",
        )
        assertParseError(
            LalrpopParseError.ExtraToken(token = Triple(1, Token.ClosingRound, 2)),
            4,
            "Parse error: extraneous token symbol ')'",
            "test.bzl:1:2-3",
        )
    }
}
