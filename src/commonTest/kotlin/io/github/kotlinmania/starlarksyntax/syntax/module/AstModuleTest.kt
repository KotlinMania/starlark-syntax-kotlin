// port-lint: tests syntax/module.rs
package io.github.kotlinmania.starlarksyntax.syntax.module

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

import io.github.kotlinmania.starlarksyntax.syntax.grammartests.parseAst
import kotlin.test.Test
import kotlin.test.assertEquals

class AstModuleTest {

    @Test
    fun testLocations() {
        fun get(code: String): String =
            parseAst(code)
                .stmtLocations()
                .joinToString(" ") { it.resolveSpan().toString() }

        assertEquals("1:1-4", get("foo"))
        assertEquals("1:1-4 2:1-3:8 3:4-8", get("foo\ndef x():\n   pass"))
    }
}
