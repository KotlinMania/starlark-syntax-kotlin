// port-lint: source src/cursors.rs
package io.github.kotlinmania.starlarksyntax.cursors

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

class CursorBytes(private val source: String) {
    private val bytes: ByteArray = source.encodeToByteArray()
    private var index: Int = 0

    fun next(): Byte? {
        if (index >= bytes.size) return null
        return bytes[index++]
    }

    // If it returns a value greater than 127, it should not be trusted
    fun nextChar(): Char? {
        return next()?.let { (it.toInt() and 0xff).toChar() }
    }

    fun pos(): Int = index
}

class CursorChars private constructor(
    private val source: String,
    private var offset: Int,
) {
    companion object {
        fun newOffset(x: String, offset: Int): CursorChars {
            return CursorChars(x, offset)
        }
    }

    fun next(): Char? {
        if (offset >= source.length) return null
        val c = source[offset]
        // Detect surrogate pair so positions advance the same number of UTF-16 units
        // the underlying char occupies.
        offset += if (c.isHighSurrogate() && offset + 1 < source.length && source[offset + 1].isLowSurrogate()) 2 else 1
        return c
    }

    /**
     * Call `unnext` to put back a character you grabbed with next.
     * It is an error if the character isn't what you declared.
     */
    fun unnext(c: Char) {
        val width = if (c.isHighSurrogate()) 2 else 1
        offset -= width
        check(peek() == c)
    }

    fun peek(): Char? {
        if (offset >= source.length) return null
        return source[offset]
    }

    fun pos(): Int = offset
}
