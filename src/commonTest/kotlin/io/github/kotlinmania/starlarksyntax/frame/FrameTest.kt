// port-lint: tests frame.rs
package io.github.kotlinmania.starlarksyntax.frame

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

class FrameTest {

    @Test
    fun testTruncateSnippet() {
        assertEquals("" to "", truncateSnippet("", 5))
        assertEquals("a" to "", truncateSnippet("a", 5))
        assertEquals("ab" to "", truncateSnippet("ab", 5))
        assertEquals("abc" to "", truncateSnippet("abc", 5))
        assertEquals("abcd" to "", truncateSnippet("abcd", 5))
        assertEquals("abcde" to "", truncateSnippet("abcde", 5))
        assertEquals("ab" to "...", truncateSnippet("abcdef", 5))
        assertEquals("ab" to "...", truncateSnippet("abcdefg", 5))
        assertEquals("ab" to "...", truncateSnippet("abcdefgh", 5))
        assertEquals("ab" to "...", truncateSnippet("abcdefghi", 5))
        assertEquals("Київ" to "", truncateSnippet("Київ", 5))
        assertEquals("па" to "...", truncateSnippet("паляниця", 5))
    }
}
