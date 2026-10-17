// port-lint: source src/golden_test_template.rs
@file:OptIn(kotlin.experimental.ExperimentalNativeApi::class)

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

import io.github.kotlinmania.io.buffered
import io.github.kotlinmania.io.files.Path
import io.github.kotlinmania.io.files.SystemFileSystem
import io.github.kotlinmania.io.readString
import io.github.kotlinmania.io.writeString
import io.github.kotlinmania.libc.unix.getenv as unixGetenv
import io.github.kotlinmania.libc.windows.getenv as windowsGetenv
import kotlin.native.OsFamily
import kotlin.native.Platform

internal actual fun platformGetEnv(name: String): String? {
    val v = if (Platform.osFamily == OsFamily.WINDOWS) {
        windowsGetenv(name) ?: windowsGetenv("SIMCTL_CHILD_$name")
    } else {
        unixGetenv(name) ?: unixGetenv("SIMCTL_CHILD_$name")
    }
    return v
}

internal actual fun platformReadUtf8File(path: String): String {
    val p = Path(path)
    val resolved = if (!SystemFileSystem.exists(p) && !path.startsWith("/")) {
        val cleanPath = path.removePrefix("./")
        val candidate = Path("/Volumes/stuff/Projects/kotlinmania/starlark-syntax-kotlin/$cleanPath")
        if (SystemFileSystem.exists(candidate)) candidate else p
    } else {
        p
    }
    return SystemFileSystem.source(resolved).buffered().use { it.readString() }
}

internal actual fun platformWriteUtf8File(path: String, content: String) {
    val p = Path(path)
    p.parent?.let { SystemFileSystem.createDirectories(it) }
    SystemFileSystem.sink(p).buffered().use { it.writeString(content) }
}

internal actual fun platformIsWindows(): Boolean = Platform.osFamily == OsFamily.WINDOWS
