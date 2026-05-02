// port-lint: source src/syntax/state.rs
package io.github.kotlinmania.starlarksyntax.syntax.state

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
import io.github.kotlinmania.starlarksyntax.codemap.Span
import io.github.kotlinmania.starlarksyntax.dialect.Dialect
import io.github.kotlinmania.starlarksyntax.evalexception.EvalException

internal class ParserState(
    val dialect: Dialect,
    val codemap: CodeMap,
    /** Recoverable errors. */
    val errors: MutableList<EvalException>,
) {
    /** Add recoverable error. */
    fun error(span: Span, error: Any) {
        errors.add(EvalException.parserError(error, span, codemap))
    }
}
