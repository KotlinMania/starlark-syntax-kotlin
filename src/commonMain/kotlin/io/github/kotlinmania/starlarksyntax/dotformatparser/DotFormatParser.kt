// port-lint: source src/dot_format_parser.rs
package io.github.kotlinmania.starlarksyntax.dotformatparser

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

/** Parser for `.format()` arguments. */
class FormatParser(s: String) {
    private val view: StringView = StringView(s)

    /** Parse the next token from the format string. */
    fun next(): Result<FormatToken?> {
        var i = 0

        while (i < view.len()) {
            val c = view.byteAt(i)
            if (c == '{'.code.toByte() || c == '}'.code.toByte()) {
                if (i != 0) {
                    val text = view.eat(i)
                    return Result.success(FormatToken.Text(text))
                }
                if (c == '{'.code.toByte()) {
                    check(i == 0)
                    // Position of the identifier relative to the start of the format string.
                    val pos = view.pos() + 1
                    i = 1
                    while (i < view.len()) {
                        val inner = view.byteAt(i)
                        when (inner) {
                            '}'.code.toByte() -> {
                                val capture = view.eat(i + 1).substring(1, i)
                                return Result.success(
                                    FormatToken.Capture(capture, pos, FormatConv.STR)
                                )
                            }
                            '!'.code.toByte() -> {
                                val capture = view.eat(i + 1).substring(1, i)
                                val rem = view.rem()
                                val conv = when {
                                    rem.startsWith('r') -> FormatConv.REPR
                                    rem.startsWith('s') -> FormatConv.STR
                                    rem.startsWith('}') -> return Result.failure(
                                        IllegalArgumentException(
                                            "Missing conversion character in format string `${view.original()}`"
                                        )
                                    )
                                    else -> return Result.failure(
                                        IllegalArgumentException(
                                            "Invalid conversion in format string `${view.original()}`"
                                        )
                                    )
                                }
                                view.eat(1) // `r` or `s` after the exclamation mark.
                                if (!view.startsWith('}')) {
                                    break
                                }
                                view.eat(1) // Closing brace.
                                return Result.success(FormatToken.Capture(capture, pos, conv))
                            }
                            '{'.code.toByte() -> {
                                if (i == 1) {
                                    view.eat(2)
                                    return Result.success(FormatToken.Escape(EscapeCurlyBrace.OPEN))
                                }
                                break
                            }
                            else -> i += 1
                        }
                    }
                    return Result.failure(
                        IllegalArgumentException(
                            "Unmatched '{' in format string `${view.original()}`"
                        )
                    )
                } else {
                    check(i == 0)
                    if (view.startsWith("}}")) {
                        view.eat(2)
                        return Result.success(FormatToken.Escape(EscapeCurlyBrace.CLOSE))
                    }
                    return Result.failure(
                        IllegalArgumentException(
                            "Standalone '}' in format string `${view.original()}`"
                        )
                    )
                }
            } else {
                i += 1
            }
        }

        return if (i == 0) {
            Result.success(null)
        } else {
            val rest = view.rem()
            view.eat(rest.length)
            Result.success(FormatToken.Text(rest))
        }
    }
}

/** Output the capture as `str` or `repr`. */
enum class FormatConv {
    STR,
    REPR,
}

/** Token in the format string. */
sealed class FormatToken {
    /** Text to copy verbatim to the output. */
    data class Text(val text: String) : FormatToken()
    data class Capture(
        /** Format part inside curly braces before the conversion. */
        val capture: String,
        /** The position of this capture. This does not include the curly braces. */
        val pos: Int,
        /** The conversion to apply to this capture. */
        val conv: FormatConv,
    ) : FormatToken()
    data class Escape(val escape: EscapeCurlyBrace) : FormatToken()
}

/** Emitted when processing an escape (`{{` or `}}`). */
enum class EscapeCurlyBrace {
    OPEN,
    CLOSE;

    /** Get what this represents. */
    fun asStr(): String {
        return when (this) {
            OPEN -> "{"
            CLOSE -> "}"
        }
    }

    /** Get back the escaped form for this. */
    fun backToEscape(): String {
        return when (this) {
            OPEN -> "{{"
            CLOSE -> "}}"
        }
    }
}

/**
 * A String and an index pointing into this string. This behaves as if you had just the part
 * starting at this index, and you can use `eat(n)` to advance.
 */
private class StringView(private val s: String) {
    private val bytes: ByteArray = s.encodeToByteArray()
    private var i: Int = 0

    fun len(): Int = bytes.size - i

    fun byteAt(offset: Int): Byte = bytes[i + offset]

    fun eat(n: Int): String {
        val ret = bytes.decodeToString(i, i + n)
        i += n
        return ret
    }

    fun pos(): Int = i

    /** Get the current string. */
    fun rem(): String = bytes.decodeToString(i, bytes.size)

    /** Get the original string. */
    fun original(): String = s

    fun startsWith(c: Char): Boolean {
        if (i >= bytes.size) return false
        return bytes[i] == c.code.toByte()
    }

    fun startsWith(needle: String): Boolean {
        val nb = needle.encodeToByteArray()
        if (bytes.size - i < nb.size) return false
        for (k in nb.indices) {
            if (bytes[i + k] != nb[k]) return false
        }
        return true
    }
}
