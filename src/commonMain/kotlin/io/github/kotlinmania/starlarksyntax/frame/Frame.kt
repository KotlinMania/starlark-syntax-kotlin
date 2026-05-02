// port-lint: source src/frame.rs
package io.github.kotlinmania.starlarksyntax.frame

/*
 * Copyright 2019 The Starlark in Rust Authors.
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

import io.github.kotlinmania.starlarksyntax.codemap.FileSpan
import io.github.kotlinmania.starlarksyntax.faststring.CharIndex
import io.github.kotlinmania.starlarksyntax.faststring.fastStringSplitAt

/** A frame of the call-stack. */
data class Frame(
    /** The name of the entry on the call-stack. */
    val name: String,
    /** The location of the definition, or [null] for native Kotlin functions. */
    val location: FileSpan?,
) {
    override fun toString(): String {
        val out = StringBuilder()
        out.append(name)
        if (location != null) {
            out.append(" (called from ").append(location).append(")")
        }
        return out.toString()
    }

    fun writeTwoLines(
        indent: String,
        caller: String,
        write: StringBuilder,
    ) {
        if (location != null) {
            val line = location
                .file
                .sourceLineAtPos(location.span.begin())
                .trim()
            val (truncatedLine, ddd) = truncateSnippet(line, 80)
            write.append(indent).append("* ").append(location.resolve().beginFileLine())
                .append(", in ")
                // Note we print caller function here as in Python, not callee,
                // so in the stack trace, top frame is printed without executed function name.
                .append(caller)
                .append('\n')
            write.append(indent).append("    ").append(truncatedLine).append(ddd).append('\n')
        } else {
            // Python just omits builtin functions in the traceback.
            write.append(indent).append("File <builtin>, in ").append(caller).append('\n')
        }
    }
}

private fun truncateSnippet(snippet: String, maxLen: Int): Pair<String, String> {
    val ddd = "..."
    check(maxLen >= ddd.length)
    val split = fastStringSplitAt(snippet, CharIndex(maxLen - ddd.length))
    if (split == null) return Pair(snippet, "")
    val (a, b) = split
    if (b.length < 4) return Pair(snippet, "")
    return Pair(a, "...")
}
