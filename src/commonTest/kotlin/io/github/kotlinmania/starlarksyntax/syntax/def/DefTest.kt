// port-lint: tests syntax/def.rs
package io.github.kotlinmania.starlarksyntax.syntax.def

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

import io.github.kotlinmania.starlarksyntax.Dialect
import io.github.kotlinmania.starlarksyntax.goldentesttemplate.goldenTestTemplate
import io.github.kotlinmania.starlarksyntax.syntax.module.AstModule
import kotlin.test.Test

class DefTest {

    private fun failsDialect(testName: String, program: String, dialect: Dialect) {
        val e = AstModule.parse("test.star", program, dialect).exceptionOrNull()!!
        val text = "Program:\n$program\n\nError: $e\n"
        goldenTestTemplate("src/syntax/def_tests/$testName.golden", text)
    }

    private fun fails(testName: String, program: String) {
        failsDialect(testName, program, Dialect.AllOptionsInternal)
    }

    private fun passes(program: String) {
        AstModule.parse(
            "test.star",
            program,
            Dialect.AllOptionsInternal,
        ).getOrThrow()
    }

    @Test
    fun testParamsUnpack() {
        fails("dup_name", "def test(x, y, x): pass")
        fails("pos_after_default", "def test(x=1, y): pass")
        fails("default_after_kwargs", "def test(**kwargs, y=1): pass")
        fails("args_args", "def test(*x, *y): pass")
        fails("kwargs_args", "def test(**x, *y): pass")
        fails("kwargs_kwargs", "def test(**x, **y): pass")

        passes("def test(x, y, z=1, *args, **kwargs): pass")
    }

    @Test
    fun testParamsNoargs() {
        fails("star_star", "def test(*, *): pass")
        fails("normal_after_default", "def test(x, y=1, z): pass")

        passes("def test(*args, x): pass")
        passes("def test(*args, x=1): pass")
        passes("def test(*args, x, y=1): pass")
        passes("def test(x=1, *args, y): pass")
        passes("def test(*args, x, y=1, z): pass")
        passes("def test(*, x, y=1, z): pass")
    }

    @Test
    fun testStarCannotBeLast() {
        fails("star_cannot_be_last", "def test(x, *): pass")
    }

    @Test
    fun testStarThenArgs() {
        fails("star_then_args", "def test(x, *, *args): pass")
    }

    @Test
    fun testStarThenKwargs() {
        fails("star_then_kwargs", "def test(x, *, **kwargs): pass")
    }

    @Test
    fun testPositionalOnly() {
        passes("def test(x, /): pass")
    }

    @Test
    fun testPositionalOnlyCannotBeFirst() {
        fails("positional_only_cannot_be_first", "def test(/, x): pass")
    }

    @Test
    fun testSlashSlash() {
        fails("slash_slash", "def test(x, /, y, /): pass")
    }

    @Test
    fun testNamedOnlyInStandardDialectDef() {
        failsDialect(
            "named_only_in_standard_dialect_def",
            "def test(*, x): pass",
            Dialect.Standard,
        )
    }

    @Test
    fun testNamedOnlyInStandardDialectLambda() {
        failsDialect(
            "named_only_in_standard_dialect_lambda",
            "lambda *, x: 17",
            Dialect.Standard,
        )
    }

    @Test
    fun testPositionalOnlyInStandardDialectDef() {
        failsDialect(
            "positional_only_in_standard_dialect_def",
            "def test(/, x): pass",
            Dialect.Standard,
        )
    }

    @Test
    fun testPositionalOnlyInStandardDialectLambda() {
        failsDialect(
            "positional_only_in_standard_dialect_lambda",
            "lambda /, x: 17",
            Dialect.Standard,
        )
    }
}
