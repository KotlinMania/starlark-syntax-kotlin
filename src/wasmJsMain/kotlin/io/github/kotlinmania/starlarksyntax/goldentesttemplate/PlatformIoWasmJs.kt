// port-lint: source src/golden_test_template.rs
@file:OptIn(kotlin.js.ExperimentalWasmJsInterop::class)
package io.github.kotlinmania.starlarksyntax.goldentesttemplate

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

// Kotlin/Wasm does not support `dynamic`, and `js("...")` calls must appear as a single
// expression in a top-level function body or property initializer. Define JS lambdas
// in top-level initializers, then call them from the expect/actual surface.

private val platformGetEnvImpl: (String) -> String? =
    js(
        "(name) => (" +
            "typeof process !== 'undefined' && process && process.env && process.env[name]" +
            ") ? process.env[name] : null",
    )

private val platformReadUtf8FileImpl: (String) -> String =
    js(
        "(path) => {\n" +
            "  const fs = require('fs');\n" +
            "  const p = require('path');\n" +
            "  if (fs.existsSync(path)) {\n" +
            "    return fs.readFileSync(path, 'utf8').toString();\n" +
            "  }\n" +
            "  let dir = (typeof process !== 'undefined' && process && process.cwd) ? process.cwd() : null;\n" +
            "  while (dir) {\n" +
            "    const candidate = p.join(dir, path);\n" +
            "    if (fs.existsSync(candidate)) {\n" +
            "      return fs.readFileSync(candidate, 'utf8').toString();\n" +
            "    }\n" +
            "    const parent = p.dirname(dir);\n" +
            "    if (parent === dir) {\n" +
            "      break;\n" +
            "    }\n" +
            "    dir = parent;\n" +
            "  }\n" +
            "  return fs.readFileSync(path, 'utf8').toString();\n" +
            "}",
    )

private val platformIsWindowsImpl: () -> Boolean =
    js("() => (typeof process !== 'undefined' && process && process.platform === 'win32')")

internal actual fun platformGetEnv(name: String): String? = platformGetEnvImpl(name)

internal actual fun platformReadUtf8File(path: String): String = platformReadUtf8FileImpl(path)

internal actual fun platformIsWindows(): Boolean = platformIsWindowsImpl()
