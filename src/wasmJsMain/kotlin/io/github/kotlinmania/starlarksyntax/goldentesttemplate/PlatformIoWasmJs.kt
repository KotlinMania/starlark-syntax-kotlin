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
// Karma serves browser test files under `/base`.

private val platformGetEnvImpl: (String) -> String? =
    js(
        "(name) => {\n" +
            "  if (typeof process !== 'undefined' && process && process.env && process.env[name]) {\n" +
            "    return process.env[name];\n" +
            "  }\n" +
            "  if (name === 'CARGO_MANIFEST_DIR') {\n" +
            "    return '.';\n" +
            "  }\n" +
            "  return null;\n" +
            "}",
    )

private val platformReadUtf8FileImpl: (String) -> String =
    js(
        "(path) => {\n" +
            "  const isNode = typeof process !== 'undefined' && process && process.versions && process.versions.node;\n" +
            "  let fs = null;\n" +
            "  let p = null;\n" +
            "  if (isNode) {\n" +
            "    if (typeof require !== 'undefined') {\n" +
            "      try { fs = require('fs'); p = require('path'); } catch (_) {}\n" +
            "    }\n" +
            "    if (!fs && typeof process.getBuiltinModule === 'function') {\n" +
            "      try { fs = process.getBuiltinModule('fs'); p = process.getBuiltinModule('path'); } catch (_) {}\n" +
            "    }\n" +
            "  }\n" +
            "  if (fs && p) {\n" +
            "    try {\n" +
            "      if (fs.existsSync(path)) {\n" +
            "        return fs.readFileSync(path, 'utf8').toString();\n" +
            "      }\n" +
            "      let dir = (process && process.cwd) ? process.cwd() : null;\n" +
            "      while (dir) {\n" +
            "        const candidate = p.join(dir, path);\n" +
            "        if (fs.existsSync(candidate)) {\n" +
            "          return fs.readFileSync(candidate, 'utf8').toString();\n" +
            "        }\n" +
            "        const parent = p.dirname(dir);\n" +
            "        if (parent === dir) {\n" +
            "          break;\n" +
            "        }\n" +
            "        dir = parent;\n" +
            "      }\n" +
            "      return fs.readFileSync(path, 'utf8').toString();\n" +
            "    } catch (_) {\n" +
            "      // fall through to browser XHR fallback if in browser\n" +
            "    }\n" +
            "  }\n" +
            "  if (typeof XMLHttpRequest !== 'undefined') {\n" +
            "    const normalized = path.startsWith('./') ? path.substring(2) : path;\n" +
            "    const requestPath = normalized.startsWith('/') ? normalized : '/base/' + normalized;\n" +
            "    const xhr = new XMLHttpRequest();\n" +
            "    xhr.open('GET', requestPath, false);\n" +
            "    xhr.send();\n" +
            "    if (xhr.status === 200 || xhr.status === 0) {\n" +
            "      return xhr.responseText;\n" +
            "    }\n" +
            "    throw new Error('Failed to load golden file `' + path + '` in browser, HTTP status ' + xhr.status);\n" +
            "  }\n" +
            "  throw new Error('Cannot read file `' + path + '`: neither fs nor XMLHttpRequest is available');\n" +
            "}",
    )

private val platformIsWindowsImpl: () -> Boolean =
    js("() => (typeof process !== 'undefined' && process && process.platform === 'win32')")

internal actual fun platformGetEnv(name: String): String? = platformGetEnvImpl(name)

internal actual fun platformReadUtf8File(path: String): String = platformReadUtf8FileImpl(path)

private val platformWriteUtf8FileImpl: (String, String) -> Unit =
    js(
        "(path, content) => {\n" +
            "  const isNode = typeof process !== 'undefined' && process && process.versions && process.versions.node;\n" +
            "  let fs = null;\n" +
            "  if (isNode) {\n" +
            "    if (typeof require !== 'undefined') {\n" +
            "      try { fs = require('fs'); } catch (_) {}\n" +
            "    }\n" +
            "    if (!fs && typeof process.getBuiltinModule === 'function') {\n" +
            "      try { fs = process.getBuiltinModule('fs'); } catch (_) {}\n" +
            "    }\n" +
            "  }\n" +
            "  if (!fs) {\n" +
            "    throw new Error('Golden regeneration is not supported in JS browser runtime');\n" +
            "  }\n" +
            "  fs.writeFileSync(path, content, 'utf8');\n" +
            "}",
    )

internal actual fun platformWriteUtf8File(path: String, content: String) {
    platformWriteUtf8FileImpl(path, content)
}

internal actual fun platformIsWindows(): Boolean = platformIsWindowsImpl()
