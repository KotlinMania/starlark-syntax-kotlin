// port-lint: source src/syntax/grammar.lalrpop
package io.github.kotlinmania.starlarksyntax.syntax.grammar

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

import io.github.kotlinmania.starlarksyntax.lexer.Token
import io.github.kotlinmania.starlarksyntax.lexer.TokenFString
import io.github.kotlinmania.starlarksyntax.lexer.TokenInt

/**
 * Per-grammar symbol-stack discriminator emitted alongside the LALRPOP-generated
 * parser. Each unique stack-element kind in the grammar is a [Variant`N`] data class
 * carrying its typed value.
 *
 * Variant ordering follows the convention used by lalrpop-kotlin's symbol emitter:
 * terminals first in declaration order from the grammar's typed-terminal block,
 * deduplicated by type kind, then nonterminals in declaration order.
 *
 * For Starlark's grammar, the typed-terminal mapping is:
 *
 * | Variant   | Source recipe                                                          |
 * |-----------|------------------------------------------------------------------------|
 * | Variant0  | [Token] (untyped terminals — keywords, symbols, brackets, INDENT/DEDENT/NEWLINE) |
 * | Variant1  | [String] (matches `IDENTIFIER` and `STRING` terminals)                 |
 * | Variant2  | [TokenInt] (matches `INTEGER` terminal)                                |
 * | Variant3  | [Double] (matches `FLOAT` terminal)                                    |
 * | Variant4  | [TokenFString] (matches `FSTRING` terminal)                            |
 *
 * Variants for Starlark's nonterminals (statements, expressions, suites, …) are
 * appended by the codegen pass when it emits [StarlarkParser]; they
 * are not declared here because the productions table that pushes them is itself
 * codegen-emitted.
 */
sealed class GrammarSymbol {
    /** Source recipe: [Token] (untyped terminals). */
    data class Variant0(val value: Token) : GrammarSymbol()

    /** Source recipe: [String] (matches `IDENTIFIER` and `STRING` terminals). */
    data class Variant1(val value: String) : GrammarSymbol()

    /** Source recipe: [TokenInt] (matches `INTEGER` terminal). */
    data class Variant2(val value: TokenInt) : GrammarSymbol()

    /** Source recipe: [Double] (matches `FLOAT` terminal). */
    data class Variant3(val value: Double) : GrammarSymbol()

    /** Source recipe: [TokenFString] (matches `FSTRING` terminal). */
    data class Variant4(val value: TokenFString) : GrammarSymbol()
}
