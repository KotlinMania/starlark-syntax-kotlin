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

private fun nodeRequire(name: String): dynamic = js("require")(name)

internal actual fun platformGetEnv(name: String): String? {
    val process: dynamic = js("process")
    val env: dynamic = process?.env
    val v: dynamic =
        if (env == null || jsTypeOf(env) == "undefined") {
            null
        } else {
            env[name]
        }
    return if (v == null || jsTypeOf(v) == "undefined") null else v.toString()
}

internal actual fun platformReadUtf8File(path: String): String {
    val fs: dynamic = nodeRequire("fs")
    return fs.readFileSync(path, "utf8").toString()
}

internal actual fun platformIsWindows(): Boolean {
    val process: dynamic = js("process")
    return (process?.platform as String?) == "win32"
}
