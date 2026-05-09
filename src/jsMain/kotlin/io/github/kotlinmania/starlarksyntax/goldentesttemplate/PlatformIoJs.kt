// port-lint: source src/golden_test_template.rs
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

private const val CARGO_MANIFEST_DIR_BROWSER_FALLBACK: String = "."
// Karma serves project files under `/base` during browser test execution.
private const val KARMA_BROWSER_BASE_PATH_PREFIX: String = "/base/"

private fun nodeRequireOrNull(name: String): dynamic {
    val requireFn: dynamic = js("typeof require !== 'undefined' ? require : undefined")
    if (requireFn == null || jsTypeOf(requireFn) == "undefined") {
        return null
    }
    return requireFn(name)
}

private fun nodeProcessOrNull(): dynamic {
    val process: dynamic = js("typeof process !== 'undefined' ? process : undefined")
    return if (process == null || jsTypeOf(process) == "undefined") null else process
}

private fun resolvePathForNode(path: String): String {
    val fs: dynamic = nodeRequireOrNull("fs") ?: return path
    if (fs.existsSync(path) as Boolean) {
        return path
    }

    val pathMod: dynamic = nodeRequireOrNull("path") ?: return path
    val process: dynamic = nodeProcessOrNull()
    var dir: dynamic = process?.cwd?.call(process)

    while (dir != null) {
        val candidate: String = pathMod.join(dir, path) as String
        if (fs.existsSync(candidate) as Boolean) {
            return candidate
        }
        val parent: String = pathMod.dirname(dir) as String
        if (parent == (dir as String)) {
            break
        }
        dir = parent
    }

    return path
}

private fun browserReadUtf8File(path: String): String {
    val normalizedPath = path.removePrefix("./")
    val requestPath =
        if (normalizedPath.startsWith("/")) normalizedPath else "$KARMA_BROWSER_BASE_PATH_PREFIX$normalizedPath"
    val xhr: dynamic = js("new XMLHttpRequest()")
    xhr.open("GET", requestPath, false)
    xhr.send()
    val status = xhr.status as Int
    if (status == 200 || status == 0) {
        return xhr.responseText as String
    }
    error("Failed to load golden file `$path` in browser, HTTP status $status")
}

internal actual fun platformGetEnv(name: String): String? {
    val process: dynamic = nodeProcessOrNull()
    val env: dynamic = process?.env
    val v: dynamic =
        if (env == null || jsTypeOf(env) == "undefined") {
            null
        } else {
            env[name]
        }
    if (v == null || jsTypeOf(v) == "undefined") {
        return if (name == "CARGO_MANIFEST_DIR") CARGO_MANIFEST_DIR_BROWSER_FALLBACK else null
    }
    return v.toString()
}

internal actual fun platformReadUtf8File(path: String): String {
    val fs: dynamic = nodeRequireOrNull("fs")
    if (fs == null || jsTypeOf(fs) == "undefined") {
        return browserReadUtf8File(path)
    }
    val resolved = resolvePathForNode(path)
    return fs.readFileSync(resolved, "utf8").toString()
}

internal actual fun platformWriteUtf8File(path: String, content: String) {
    val fs: dynamic = nodeRequireOrNull("fs")
    check(fs != null && jsTypeOf(fs) != "undefined") {
        "Golden regeneration is not supported in JS browser runtime"
    }
    fs.writeFileSync(path, content, "utf8")
}

internal actual fun platformIsWindows(): Boolean {
    val process: dynamic = nodeProcessOrNull()
    return (process?.platform as String?) == "win32"
}
