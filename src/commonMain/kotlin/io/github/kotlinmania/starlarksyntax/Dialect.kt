// port-lint: source src/dialect.rs
package io.github.kotlinmania.starlarksyntax

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

/**
 * How to handle type annotations in Starlark.
 *
 * If you are enabling types, you will often want to use
 * `LibraryExtension.Typing` when constructing a `Globals` environment.
 */
enum class DialectTypes {
    /** Prohibit types at parse time. */
    Disable,

    /** Allow types at parse time, but ignore types at runtime. */
    ParseOnly,

    /** Check types at runtime. */
    Enable,
}

/** Starlark language features to enable, e.g. [Standard] to follow the Starlark standard. */
data class Dialect(
    /**
     * Are `def` statements permitted.
     * Enabled by default.
     */
    val enableDef: Boolean,
    /**
     * Are `lambda` expressions permitted.
     * Enabled by default.
     */
    val enableLambda: Boolean,
    /**
     * Are `load` statements permitted.
     * Enabled by default.
     */
    val enableLoad: Boolean,
    /**
     * Are `*` keyword-only arguments allowed as per [PEP 3102](https://www.python.org/dev/peps/pep-3102/).
     * Disabled by default.
     */
    val enableKeywordOnlyArguments: Boolean,
    /** Are `/` for positional-only arguments allowed. */
    val enablePositionalOnlyArguments: Boolean,
    /**
     * Are expressions allowed in type positions as per [PEP 484](https://www.python.org/dev/peps/pep-0484/).
     * Disabled by default.
     */
    val enableTypes: DialectTypes,
    /**
     * Do `load()` statements reexport their definition.
     * Enabled by default,
     * but may change in future definitions of the standard.
     */
    val enableLoadReexport: Boolean,
    /**
     * Are `for`, `if` and other statements allowed at the top level.
     * Disabled by default.
     */
    val enableTopLevelStmt: Boolean,
    /**
     * Are `f"{expression}"` strings supported? Only works where `expression` is an atomic
     * identifier.
     * Disabled by default.
     *
     * [Starlark spec proposal](https://github.com/bazelbuild/starlark/issues/91).
     */
    val enableFStrings: Boolean,
) {
    companion object {
        /**
         * Follow the [Starlark language standard](https://github.com/bazelbuild/starlark/blob/master/spec.md) as much as possible.
         *
         * This is also returned by [default].
         */
        val Standard: Dialect = Dialect(
            enableDef = true,
            enableLambda = true,
            enableLoad = true,
            enableKeywordOnlyArguments = false,
            enablePositionalOnlyArguments = false,
            enableTypes = DialectTypes.Disable,
            enableLoadReexport = true, // But they plan to change it
            enableTopLevelStmt = false,
            enableFStrings = false,
        )

        /** This option is deprecated. Extend Standard instead. */
        val Extended: Dialect = Dialect(
            enableDef = true,
            enableLambda = true,
            enableLoad = true,
            enableKeywordOnlyArguments = true,
            enablePositionalOnlyArguments = false,
            enableTypes = DialectTypes.Enable,
            enableLoadReexport = true,
            enableTopLevelStmt = true,
            enableFStrings = false,
        )

        /** Only for starlark-kotlin self tests. */
        val AllOptionsInternal: Dialect = Dialect(
            enableDef = true,
            enableLambda = true,
            enableLoad = true,
            enableKeywordOnlyArguments = true,
            enablePositionalOnlyArguments = true,
            enableTypes = DialectTypes.Enable,
            enableLoadReexport = true,
            enableTopLevelStmt = true,
            enableFStrings = true,
        )

        fun default(): Dialect = Standard
    }
}
