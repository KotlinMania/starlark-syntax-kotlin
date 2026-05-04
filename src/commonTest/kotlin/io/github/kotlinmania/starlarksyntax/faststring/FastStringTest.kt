// port-lint: tests fast_string.rs
package io.github.kotlinmania.starlarksyntax.faststring

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

class FastStringTest {

    @Test
    fun testConvertStrIndices() {
        assertEquals(
            StrIndices(start = CharIndex(0), haystack = "abc"),
            convertStrIndices("abc", null, null),
        )
        assertNull(convertStrIndices("abc", 2, 1))

        assertEquals(
            StrIndices(start = CharIndex(0), haystack = "abc"),
            convertStrIndices("abc", -10, 10),
        )
        assertEquals(
            StrIndices(start = CharIndex(1), haystack = ""),
            convertStrIndices("abc", 1, 1),
        )
        assertEquals(
            StrIndices(start = CharIndex(0), haystack = "ab"),
            convertStrIndices("abc", -10, 2),
        )
        assertEquals(
            StrIndices(start = CharIndex(0), haystack = "ab"),
            convertStrIndices("abc", -10, -1),
        )

        assertEquals(
            StrIndices(start = CharIndex(0), haystack = "s"),
            convertStrIndices("short", 0, -4),
        )
        assertEquals(
            StrIndices(start = CharIndex(0), haystack = "fish"),
            convertStrIndices("fish", null, 10),
        )
    }

    @Test
    fun testConvertStrIndicesNonAscii() {
        assertEquals(
            StrIndices(start = CharIndex(6), haystack = "под"),
            convertStrIndices("Город под подошвой", 6, 9),
        )
    }

    @Test
    fun testConvertStrIndicesTriggerDebugAssertions() {
        // Sweep representative slice/end pairs (including None and out-of-range integers) over
        // a few sample strings to exercise every internal branch and any debug_assert
        // boundary check inside convertStrIndices.
        val noneOrs: Sequence<Int?> = sequenceOf<Int?>(null) + (-30..29).asSequence().map { it as Int? }

        for (s in listOf("", "a", "abcde", "Телемак")) {
            for (start in noneOrs) {
                for (end in noneOrs) {
                    convertStrIndices(s, start, end)
                }
            }
        }
    }
}
