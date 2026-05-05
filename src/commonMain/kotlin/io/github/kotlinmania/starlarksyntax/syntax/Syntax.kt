// port-lint: source src/syntax.rs
package io.github.kotlinmania.starlarksyntax.syntax

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
 * The AST of Starlark as [io.github.kotlinmania.starlarksyntax.syntax.module.AstModule], along with
 * a `parse` function on that type.
 *
 * In the upstream Rust crate, this file is mostly module wiring and re-exports.
 * In Kotlin, callers import concrete definitions directly from their packages under `syntax.*`.
 */
