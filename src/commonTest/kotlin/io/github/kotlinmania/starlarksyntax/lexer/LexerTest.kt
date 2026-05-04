// port-lint: tests lexer.rs
package io.github.kotlinmania.starlarksyntax.lexer

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

class LexerTest {

    @Test
    fun testIsValidIdentifier() {
        assertEquals("foo", lexExactlyOneIdentifier("foo"))
        assertEquals("foo", lexExactlyOneIdentifier(" foo "))
        assertNull(lexExactlyOneIdentifier("foo bar"))
        assertNull(lexExactlyOneIdentifier("not"))
        assertNull(lexExactlyOneIdentifier("123"))
    }
}
