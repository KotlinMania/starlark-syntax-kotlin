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

private const val BROWSER_MANIFEST_DIR: String = "."
private const val KARMA_BASE_PATH_PREFIX: String = "/base/"

private val platformGetEnvImpl: (String) -> String? =
    js(
        "(name) => {\n" +
            "  if (typeof process !== 'undefined' && process && process.env && process.env[name]) {\n" +
            "    return process.env[name];\n" +
            "  }\n" +
            "  if (name === 'CARGO_MANIFEST_DIR') {\n" +
            "    return '$BROWSER_MANIFEST_DIR';\n" +
            "  }\n" +
            "  return null;\n" +
            "}",
    )

private val platformReadUtf8FileImpl: (String) -> String =
    js(
        "(path) => {\n" +
            "  if (typeof require !== 'undefined') {\n" +
            "    const fs = require('fs');\n" +
            "    const p = require('path');\n" +
            "    if (fs.existsSync(path)) {\n" +
            "      return fs.readFileSync(path, 'utf8').toString();\n" +
            "    }\n" +
            "    let dir = (typeof process !== 'undefined' && process && process.cwd) ? process.cwd() : null;\n" +
            "    while (dir) {\n" +
            "      const candidate = p.join(dir, path);\n" +
            "      if (fs.existsSync(candidate)) {\n" +
            "        return fs.readFileSync(candidate, 'utf8').toString();\n" +
            "      }\n" +
            "      const parent = p.dirname(dir);\n" +
            "      if (parent === dir) {\n" +
            "        break;\n" +
            "      }\n" +
            "      dir = parent;\n" +
            "    }\n" +
            "    return fs.readFileSync(path, 'utf8').toString();\n" +
            "  }\n" +
            "  const normalized = path.startsWith('./') ? path.substring(2) : path;\n" +
            "  const requestPath = normalized.startsWith('/') ? normalized : '$KARMA_BASE_PATH_PREFIX' + normalized;\n" +
            "  const xhr = new XMLHttpRequest();\n" +
            "  xhr.open('GET', requestPath, false);\n" +
            "  xhr.send();\n" +
            "  if (xhr.status === 200 || xhr.status === 0) {\n" +
            "    return xhr.responseText;\n" +
            "  }\n" +
            "  throw new Error('Failed to load golden file `' + path + '` in browser, HTTP status ' + xhr.status);\n" +
            "}",
    )

private val platformIsWindowsImpl: () -> Boolean =
    js("() => (typeof process !== 'undefined' && process && process.platform === 'win32')")

internal actual fun platformGetEnv(name: String): String? = platformGetEnvImpl(name)

internal actual fun platformReadUtf8File(path: String): String = platformReadUtf8FileImpl(path)

private val platformWriteUtf8FileImpl: (String, String) -> Unit =
    js(
        "(path, content) => {\n" +
            "  if (typeof require === 'undefined') {\n" +
            "    throw new Error('Golden regeneration is not supported in JS browser runtime');\n" +
            "  }\n" +
            "  const fs = require('fs');\n" +
            "  fs.writeFileSync(path, content, 'utf8');\n" +
            "}",
    )

internal actual fun platformWriteUtf8File(path: String, content: String) {
    platformWriteUtf8FileImpl(path, content)
}

internal actual fun platformIsWindows(): Boolean = platformIsWindowsImpl()
