// port-lint: source src/golden_test_template.rs
@file:OptIn(kotlinx.cinterop.ExperimentalForeignApi::class, kotlin.experimental.ExperimentalNativeApi::class)
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

import kotlinx.cinterop.toKString
import kotlin.native.OsFamily
import kotlin.native.Platform
import platform.posix.EOF
import platform.posix.fclose
import platform.posix.fgetc
import platform.posix.fopen
import platform.posix.getenv

internal actual fun platformGetEnv(name: String): String? {
    val v = getenv(name) ?: return null
    return v.toKString()
}

internal actual fun platformReadUtf8File(path: String): String {
    val f = fopen(path, "rb") ?: error("Unable to open file: $path")
    try {
        val out = StringBuilder()
        while (true) {
            val c = fgetc(f)
            if (c == EOF) break
            out.append(c.toChar())
        }
        return out.toString()
    } finally {
        fclose(f)
    }
}

internal actual fun platformIsWindows(): Boolean = Platform.osFamily == OsFamily.WINDOWS
