// port-lint: source src/syntax/ast_load.rs
package io.github.kotlinmania.starlarksyntax.syntax.astload

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

import io.github.kotlinmania.starlarkmap.smallmap.SmallMap
import io.github.kotlinmania.starlarksyntax.codemap.FileSpan

/** A `load` statement loading zero or more symbols from another module. */
class AstLoad(
    /** Span where this load is written. */
    val span: FileSpan,
    /** Module being loaded. */
    val moduleId: String,
    /** Symbols loaded from that module (local ident -> source ident). */
    val symbols: SmallMap<String, String>,
)
