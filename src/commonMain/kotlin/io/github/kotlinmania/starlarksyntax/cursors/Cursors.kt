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

class CursorChars(
    private val bytes: ByteArray,
    private var offset: Int,
) {
    companion object {
        fun newOffset(source: String, offset: Int): CursorChars {
            return CursorChars(source.encodeToByteArray(), offset)
        }
    }

    fun next(): Int? {
        if (offset >= bytes.size) return null
        val b0 = bytes[offset].toInt() and 0xff
        val (codePoint, size) =
            when {
                b0 and 0b1000_0000 == 0 -> Pair(b0, 1)
                b0 and 0b1110_0000 == 0b1100_0000 -> {
                    if (offset + 1 >= bytes.size) return null
                    val b1 = bytes[offset + 1].toInt() and 0xff
                    Pair(((b0 and 0b0001_1111) shl 6) or (b1 and 0b0011_1111), 2)
                }
                b0 and 0b1111_0000 == 0b1110_0000 -> {
                    if (offset + 2 >= bytes.size) return null
                    val b1 = bytes[offset + 1].toInt() and 0xff
                    val b2 = bytes[offset + 2].toInt() and 0xff
                    Pair(
                        ((b0 and 0b0000_1111) shl 12) or
                            ((b1 and 0b0011_1111) shl 6) or
                            (b2 and 0b0011_1111),
                        3,
                    )
                }
                b0 and 0b1111_1000 == 0b1111_0000 -> {
                    if (offset + 3 >= bytes.size) return null
                    val b1 = bytes[offset + 1].toInt() and 0xff
                    val b2 = bytes[offset + 2].toInt() and 0xff
                    val b3 = bytes[offset + 3].toInt() and 0xff
                    Pair(
                        ((b0 and 0b0000_0111) shl 18) or
                            ((b1 and 0b0011_1111) shl 12) or
                            ((b2 and 0b0011_1111) shl 6) or
                            (b3 and 0b0011_1111),
                        4,
                    )
                }
                else -> return null
            }

        offset += size
        return codePoint
    }

    /**
     * Call `unnext` to put back a character you grabbed with next.
     * It is an error if the character isn't what you declared.
     */
    fun unnext(c: Int) {
        val width = utf8Len(c)
        offset -= width
        check(peek() == c)
    }

    fun peek(): Int? {
        if (offset >= bytes.size) return null
        val saved = offset
        val next = next()
        offset = saved
        return next
    }

    fun pos(): Int = offset
}

private fun utf8Len(codePoint: Int): Int {
    return when {
        codePoint <= 0x7f -> 1
        codePoint <= 0x7ff -> 2
        codePoint <= 0xffff -> 3
        else -> 4
    }
}
