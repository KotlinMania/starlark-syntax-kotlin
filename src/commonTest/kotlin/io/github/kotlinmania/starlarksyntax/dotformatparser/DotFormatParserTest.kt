// port-lint: tests dot_format_parser.rs
package io.github.kotlinmania.starlarksyntax.dotformatparser

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

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class DotFormatParserTest {

    @Test
    fun testParserPosition() {
        val s = "foo{x}bar{yz}baz{w!s}qux{v!r}quux"
        val parser = FormatParser(s)
        assertEquals(FormatToken.Text("foo"), parser.next().getOrThrow())
        assertEquals(
            FormatToken.Capture(capture = "x", pos = 4, conv = FormatConv.STR),
            parser.next().getOrThrow(),
        )
        assertEquals(FormatToken.Text("bar"), parser.next().getOrThrow())
        assertEquals(
            FormatToken.Capture(capture = "yz", pos = 10, conv = FormatConv.STR),
            parser.next().getOrThrow(),
        )
        assertEquals(FormatToken.Text("baz"), parser.next().getOrThrow())
        assertEquals(
            FormatToken.Capture(capture = "w", pos = 17, conv = FormatConv.STR),
            parser.next().getOrThrow(),
        )
        assertEquals(FormatToken.Text("qux"), parser.next().getOrThrow())
        assertEquals(
            FormatToken.Capture(capture = "v", pos = 25, conv = FormatConv.REPR),
            parser.next().getOrThrow(),
        )
        assertEquals(FormatToken.Text("quux"), parser.next().getOrThrow())
        assertNull(parser.next().getOrThrow())
    }

    @Test
    fun testFailure() {
        val s = "}foo"
        val parser = FormatParser(s)
        val errorMsg = parser.next().exceptionOrNull()!!.message
        assertEquals("Standalone '}' in format string `}foo`", errorMsg)
    }
}
