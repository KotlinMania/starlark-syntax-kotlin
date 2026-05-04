// port-lint: source src/fast_string.rs
package io.github.kotlinmania.starlarksyntax.faststring

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

/**
 * Our string operations (indexing) are O(n) because of our current representation.
 * There are plans afoot to change that, but in the meantime let's use fast algorithms
 * to make up some of the difference.
 */

import io.github.kotlinmania.starlarksyntax.convertindices.convertIndices

private fun is1Byte(x: Byte): Boolean = (x.toInt() and 0x80) == 0

/**
 * Skip at most n 1byte characters from the prefix of the string, return how many you skipped.
 * The result will be between 0 and n.
 * The string _must_ have at least n bytes in it.
 */
private fun skipAtMost1Byte(x: String, n: Int): Int {
    if (n == 0) return 0
    val bytes = x.encodeToByteArray()
    check(bytes.size >= n)
    var i = 0
    while (i < n && is1Byte(bytes[i])) {
        i++
    }
    return i
}

/** Find the character at position `i`. */
fun at(x: String, i: CharIndex): Char? {
    val bytes = x.encodeToByteArray()
    if (i.value >= bytes.size) {
        // Important that skipAtMost1Byte gets called with all valid character.
        // If the index is outside the length even under the best assumptions,
        // can immediately return null.
        return null
    }
    val n = skipAtMost1Byte(x, i.value)
    val s = bytes.decodeToString(n, bytes.size)
    var idx = 0
    for (ch in s) {
        if (idx == i.value - n) return ch
        idx++
    }
    return null
}

/**
 * Find the length of the string in characters (Unicode codepoints).
 *
 * If the length matches the length in bytes, the string must be 7bit ASCII.
 */
fun len(x: String): CharIndex {
    val bytes = x.encodeToByteArray()
    val n = skipAtMost1Byte(x, bytes.size)
    return if (n == bytes.size) {
        CharIndex(n) // All 1 byte
    } else {
        CharIndex(codepointCount(bytes.decodeToString(n, bytes.size)) + n)
    }
}

/**
 * Count Unicode codepoints in a string, treating each surrogate pair as one codepoint.
 *
 * Kotlin's [String.length] and [String.count] return UTF-16 code units, which double-counts
 * characters in supplementary planes (e.g. emoji). This walk yields one increment per codepoint.
 */
private fun codepointCount(s: String): Int {
    var i = 0
    var count = 0
    while (i < s.length) {
        val ch = s[i]
        i += if (ch.isHighSurrogate() && i + 1 < s.length && s[i + 1].isLowSurrogate()) 2 else 1
        count++
    }
    return count
}

/**
 * Find the number of times a `needle` byte occurs within a string.
 * If the needle represents a complete character, this will be equivalent to doing
 * search for that character in the string.
 */
fun countMatchesByte(x: String, needle: Byte): Int {
    return x.encodeToByteArray().count { it == needle }
}

/** Find the number of times a `needle` occurs within a string, non-overlapping. */
fun countMatches(x: String, needle: String): Int {
    return if (needle.length == 1) {
        // If we are searching for a 1-byte string, we can provide a much faster path.
        // Since it is one byte, given how UTF8 works, all the resultant slices must be UTF8 too.
        countMatchesByte(x, needle.encodeToByteArray()[0])
    } else {
        var count = 0
        var idx = 0
        while (idx <= x.length - needle.length) {
            if (x.regionMatches(idx, needle, 0, needle.length)) {
                count++
                idx += needle.length
            } else {
                idx++
            }
        }
        count
    }
}

/** Result of applying `start` and `end` to a string. */
data class StrIndices(
    /** Computed start char index. */
    val start: CharIndex,
    /** Substring after applying the `start` and `end` arguments. */
    val haystack: String,
)

/** Split the string at given char offset. `null` if offset is out of bounds. */
fun splitAt(x: String, i: CharIndex): Pair<String, String>? {
    if (i.value == 0) {
        return Pair("", x)
    }
    val bytes = x.encodeToByteArray()
    if (i.value > bytes.size) {
        return null
    }
    val n = skipAtMost1Byte(x, i.value)
    val tailStr = bytes.decodeToString(n, bytes.size)
    val tailIter = tailStr.iterator()
    var consumed = 0
    val toSkip = i.value - n
    while (consumed < toSkip) {
        if (!tailIter.hasNext()) return null
        tailIter.next()
        consumed++
    }
    // Build the suffix string from remaining iterator.
    val suffix = StringBuilder()
    while (tailIter.hasNext()) suffix.append(tailIter.next())
    val splitAt = x.length - suffix.length
    return Pair(x.substring(0, splitAt), x.substring(splitAt))
}

/** Perform the Starlark operation `x[:i]` (`i` is an unsigned integer here). */
private fun splitAtEnd(x: String, i: CharIndex): String {
    return when (val pair = splitAt(x, i)) {
        null -> x
        else -> pair.first
    }
}

private fun convertStrIndicesSlow(
    s: String,
    start: Int?,
    end: Int?,
): StrIndices? {
    // Slow version when we need to compute full string length
    // because at least one of the indices is negative.
    check((start != null && start < 0) || (end != null && end < 0))
    // If both indices are negative, we should have ruled `start > end` case before.
    check(
        (start != null && end != null && (start >= 0 || end >= 0 || start <= end))
            || start == null
            || end == null
    )
    val len = len(s)
    val (cstart, cend) = convertIndices(len.value, start, end)
    if (cstart > cend) {
        return null
    }
    val startIdx = CharIndex(cstart)
    val endIdx = CharIndex(cend)
    check(endIdx <= len)
    val sBytes = s.encodeToByteArray()
    val haystack = if (len.value == sBytes.size) {
        // ASCII fast path: if char len is equal to byte len,
        // we know the string is ASCII.
        sBytes.decodeToString(startIdx.value, endIdx.value)
    } else {
        val (_, tail) = splitAt(s, startIdx)!!
        val (head, _) = splitAt(tail, endIdx - startIdx)!!
        head
    }
    return StrIndices(startIdx, haystack)
}

/** Convert common `start` and `end` arguments of `str` functions like `str.find`. */
fun convertStrIndices(
    s: String,
    start: Int?,
    end: Int?,
): StrIndices? {
    return when {
        // Following cases but last optimize index computation
        // by avoiding computing the length of the string.
        start == null && end == null -> StrIndices(CharIndex(0), s)
        start != null && end == null && start >= 0 -> {
            val (_, tail) = splitAt(s, CharIndex(start)) ?: return null
            StrIndices(CharIndex(start), tail)
        }
        start == null && end != null && end >= 0 -> {
            val tail = splitAtEnd(s, CharIndex(end))
            StrIndices(CharIndex(0), tail)
        }
        start != null && end != null && start >= 0 && end >= start -> {
            val (_, tail) = splitAt(s, CharIndex(start)) ?: return null
            val sub = splitAtEnd(tail, CharIndex(end - start))
            StrIndices(CharIndex(start), sub)
        }
        start != null && end != null && (start >= 0) == (end >= 0) && start > end -> null
        else -> convertStrIndicesSlow(s, start, end)
    }
}

fun contains(haystack: String, needle: String): Boolean {
    if (needle.isEmpty()) {
        return true
    }
    val needleBytes = needle.encodeToByteArray()
    val haystackBytes = haystack.encodeToByteArray()
    if (needle.length == 1) {
        val target = needleBytes[0]
        for (b in haystackBytes) {
            if (b == target) return true
        }
        return false
    }
    if (haystackBytes.size < needleBytes.size) {
        return false
    }
    check(haystackBytes.size >= needleBytes.size)
    // `String.contains` is very slow for short strings.
    // So use basic quadratic algorithm instead.
    val needle0 = needleBytes[0]
    for (start in 0..haystackBytes.size - needleBytes.size) {
        if (haystackBytes[start] != needle0) {
            continue
        }
        var match = true
        for (k in 1 until needleBytes.size) {
            if (haystackBytes[start + k] != needleBytes[k]) {
                match = false
                break
            }
        }
        if (match) return true
    }
    return false
}

/**
 * Index of a char in a string.
 * This is different from string byte offset.
 */
data class CharIndex(val value: Int) : Comparable<CharIndex> {
    operator fun minus(rhs: CharIndex): CharIndex = CharIndex(value - rhs.value)
    operator fun plus(rhs: CharIndex): CharIndex = CharIndex(value + rhs.value)
    override fun compareTo(other: CharIndex): Int = value.compareTo(other.value)
}
