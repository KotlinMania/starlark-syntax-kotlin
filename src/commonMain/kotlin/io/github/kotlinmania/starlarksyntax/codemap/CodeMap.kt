// port-lint: source src/codemap.rs
package io.github.kotlinmania.starlarksyntax.codemap

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
 * A data structure for tracking source positions in language implementations
 * The [CodeMap] tracks all source files and maps positions within them to linear indexes as if all
 * source files were concatenated. This allows a source position to be represented by a small
 * 32-bit [Pos] indexing into the [CodeMap], under the assumption that the total amount of parsed
 * source code will not exceed 4GiB. The [CodeMap] can look up the source file, line, and column
 * of a [Pos] or [Span], as well as provide source code snippets for error reporting.
 */

import io.github.kotlinmania.starlarksyntax.faststring.len

/** A small, copy, value representing a position in a [CodeMap]'s file. */
data class Pos(val value: Int) : Comparable<Pos> {
    companion object {
        /** Constructor. */
        fun new(x: Int): Pos = Pos(x)
    }

    /** Get the value. */
    fun get(): Int = value

    operator fun plus(other: Int): Pos = Pos(value + other)
    operator fun minus(other: Int): Pos = Pos(value - other)
    override fun compareTo(other: Pos): Int = value.compareTo(other.value)
}

/** A range of text within a CodeMap. */
data class Span(
    /** The position in the codemap representing the first byte of the span. */
    private val beginPos: Pos = Pos(0),
    /** The position after the last byte of the span. */
    private val endPos: Pos = Pos(0),
) : Comparable<Span> {
    companion object {
        val DEFAULT: Span = Span()

        /** Create a new span. Panics if `end < begin`. */
        fun new(begin: Pos, end: Pos): Span {
            check(begin <= end)
            return Span(begin, end)
        }

        fun mergeAll(spans: Sequence<Span>): Span {
            return spans.reduceOrNull { a, b -> a.merge(b) } ?: DEFAULT
        }

        fun mergeAll(spans: Iterable<Span>): Span = mergeAll(spans.asSequence())
    }

    /** The position at the first byte of the span. */
    fun begin(): Pos = beginPos

    /** The position after the last byte of the span. */
    fun end(): Pos = endPos

    /** The length in bytes of the text of the span. */
    fun len(): Int = endPos.value - beginPos.value

    /** Create a span that encloses both `this` and `other`. */
    fun merge(other: Span): Span = Span(
        beginPos = if (beginPos <= other.beginPos) beginPos else other.beginPos,
        endPos = if (endPos >= other.endPos) endPos else other.endPos,
    )

    /** Empty span in the end of this span. */
    fun endSpan(): Span = Span(endPos, endPos)

    /** Determines whether a `pos` is within this span. */
    fun contains(pos: Pos): Boolean = beginPos <= pos && pos <= endPos

    /**
     * Determines whether a `span` intersects with this span.
     * End of range is inclusive.
     */
    fun intersects(span: Span): Boolean =
        contains(span.beginPos) || contains(span.endPos) || span.contains(beginPos)

    override fun compareTo(other: Span): Int {
        val cmp = beginPos.compareTo(other.beginPos)
        return if (cmp != 0) cmp else endPos.compareTo(other.endPos)
    }
}

/** Associate a Span with a value of arbitrary type (e.g. an AST node). */
data class Spanned<T>(
    /** Data in the node. */
    val node: T,
    val span: Span,
) {
    /** Apply the function to the node, keep the span. */
    fun <U> map(f: (T) -> U): Spanned<U> = Spanned(f(node), span)

    fun asRef(): Spanned<T> = this
}

/**
 * A cheap unowned unique identifier per file/CodeMap,
 * somewhat delving into internal details.
 * Remains unique because we take a reference to the CodeMap.
 */
class CodeMapId internal constructor(internal val ref: Any?) {
    companion object {
        val EMPTY: CodeMapId = CodeMapId(null)
    }

    override fun equals(other: Any?): Boolean {
        return other is CodeMapId && this.ref === other.ref
    }

    override fun hashCode(): Int = ref?.let { systemIdentityHashCode(it) } ?: 0

    override fun toString(): String = "CodeMapId(${ref?.let { "@" + systemIdentityHashCode(it).toString(16) } ?: "EMPTY"})"
}

internal sealed class CodeMapImpl {
    internal class Real(val data: CodeMapData) : CodeMapImpl()
    internal class Native(val data: NativeCodeMap) : CodeMapImpl()
}

/** A data structure recording a source code file for position lookup. */
class CodeMap internal constructor(internal val impl: CodeMapImpl) {
    companion object {
        private val EMPTY_CODEMAP: CodeMap by lazy { default() }

        /** Creates an new [CodeMap]. */
        fun new(filename: String, source: String): CodeMap {
            val sourceBytes = source.encodeToByteArray()
            val lines = mutableListOf(Pos(0))
            for (i in sourceBytes.indices) {
                if (sourceBytes[i] == '\n'.code.toByte()) {
                    lines.add(Pos(i + 1))
                }
            }
            return CodeMap(
                CodeMapImpl.Real(
                    CodeMapData(
                        filename = filename,
                        source = source,
                        sourceBytes = sourceBytes,
                        lines = lines,
                    )
                )
            )
        }

        fun default(): CodeMap = new("", "")

        fun emptyStatic(): CodeMap = EMPTY_CODEMAP
    }

    /** Only used internally for profiling optimisations. */
    fun id(): CodeMapId = when (val i = impl) {
        is CodeMapImpl.Real -> CodeMapId(i.data)
        is CodeMapImpl.Native -> CodeMapId(i.data)
    }

    fun fullSpan(): Span {
        return Span.new(Pos(0), Pos(sourceBytes().size))
    }

    /** Gets the file and its line and column ranges represented by a [Span]. */
    fun fileSpan(span: Span): FileSpan = FileSpan(this, span)

    /** Gets the name of the file. */
    fun filename(): String = when (val i = impl) {
        is CodeMapImpl.Real -> i.data.filename
        is CodeMapImpl.Native -> i.data.filename
    }

    fun byteAt(pos: Pos): Byte {
        return sourceBytes()[pos.value]
    }

    /**
     * Gets the line number of a Pos.
     *
     * The lines are 0-indexed (first line is numbered 0)
     *
     * Panics if `pos` is not within this file's span.
     */
    fun findLine(pos: Pos): Int {
        check(pos <= fullSpan().end())
        return when (val i = impl) {
            is CodeMapImpl.Real -> {
                val idx = i.data.lines.binarySearch(pos)
                if (idx >= 0) idx else -idx - 2
            }
            is CodeMapImpl.Native -> i.data.start.line
        }
    }

    /**
     * Gets the line and column of a Pos.
     *
     * Panics if `pos` is not with this file's span or
     * if `pos` points to a byte in the middle of a UTF-8 character.
     */
    internal fun findLineCol(pos: Pos): ResolvedPos {
        check(pos <= fullSpan().end())
        return when (val i = impl) {
            is CodeMapImpl.Real -> {
                val line = findLine(pos)
                val lineSpan = lineSpan(line)
                val byteCol = pos.value - lineSpan.begin().value
                val lineBegin = lineSpan.begin().value
                val prefixEnd = lineBegin + byteCol
                val bytes = sourceBytes()
                checkUtf8Boundary(bytes, prefixEnd)
                val prefixStr = bytes.decodeToString(lineBegin, prefixEnd)
                val column = len(prefixStr).value
                ResolvedPos(line, column)
            }
            is CodeMapImpl.Native -> ResolvedPos(
                line = i.data.start.line,
                column = i.data.start.column + pos.value,
            )
        }
    }

    /** Gets the full source text of the file. */
    fun source(): String = when (val i = impl) {
        is CodeMapImpl.Real -> i.data.source
        is CodeMapImpl.Native -> NativeCodeMap.SOURCE
    }

    private fun sourceBytes(): ByteArray = when (val i = impl) {
        is CodeMapImpl.Real -> i.data.sourceBytes
        is CodeMapImpl.Native -> NativeCodeMap.SOURCE_BYTES
    }

    /**
     * Gets the source text of a Span.
     *
     * Panics if `span` is not entirely within this file.
     */
    fun sourceSpan(span: Span): String {
        val bytes = sourceBytes()
        val begin = span.begin().value
        val end = span.end().value
        check(begin <= end)
        check(end <= bytes.size)
        checkUtf8Boundary(bytes, begin)
        checkUtf8Boundary(bytes, end)
        return bytes.decodeToString(begin, end)
    }

    /** Like [lineSpanOpt] but panics if the line number is out of range. */
    fun lineSpan(line: Int): Span {
        return lineSpanOpt(line)
            ?: error("Line $line is out of range for $this")
    }

    /** Trim trailing newline if any, including windows, from the line span. */
    fun lineSpanTrimNewline(line: Int): Span {
        var span = lineSpan(line)
        if (sourceSpan(span).endsWith('\n')) {
            span = Span.new(span.begin(), Pos(span.end().value - 1))
        }
        if (sourceSpan(span).endsWith('\r')) {
            span = Span.new(span.begin(), Pos(span.end().value - 1))
        }
        return span
    }

    /**
     * Gets the span representing a line by line number.
     *
     * The line number is 0-indexed (first line is numbered 0). The returned span includes the
     * line terminator.
     *
     * Returns null if the number if out of range.
     */
    fun lineSpanOpt(line: Int): Span? = when (val i = impl) {
        is CodeMapImpl.Real -> if (line < i.data.lines.size) {
            Span.new(
                begin = i.data.lines[line],
                end = if (line + 1 < i.data.lines.size) i.data.lines[line + 1] else fullSpan().end(),
            )
        } else null
        is CodeMapImpl.Native -> if (line == i.data.start.line) {
            Span.new(Pos(0), Pos(NativeCodeMap.SOURCE.length))
        } else null
    }

    fun resolveSpan(span: Span): ResolvedSpan {
        val begin = findLineCol(span.begin())
        val end = findLineCol(span.end())
        return ResolvedSpan.fromSpan(begin, end)
    }

    /**
     * Gets the source text of a line.
     *
     * The string returned does not include the terminating \r or \n characters.
     *
     * Panics if the line number is out of range.
     */
    fun sourceLine(line: Int): String =
        sourceSpan(lineSpan(line)).trimEnd('\n', '\r')

    fun sourceLineAtPos(pos: Pos): String =
        sourceLine(findLine(pos))

    override fun equals(other: Any?): Boolean {
        // Compares by identity
        return other is CodeMap && id() == other.id()
    }

    override fun hashCode(): Int = id().hashCode()

    override fun toString(): String = "CodeMap(${filename()})"
}

/** Multiple [CodeMap]. */
class CodeMaps {
    private val codemaps: MutableMap<CodeMapId, CodeMap> = mutableMapOf()

    /** Lookup by id. */
    fun get(id: CodeMapId): CodeMap? = codemaps[id]

    /** Add codemap if not already present. */
    fun add(codemap: CodeMap) {
        val key = codemap.id()
        if (key !in codemaps) {
            codemaps[key] = codemap
        }
    }

    /** Add all codemaps. */
    fun addAll(codemaps: CodeMaps) {
        for (codemap in codemaps.codemaps.values) {
            add(codemap)
        }
    }
}

/** A [CodeMap]'s record of a source file. */
internal class CodeMapData(
    /** The filename as it would be displayed in an error message. */
    val filename: String,
    /** Contents of the file. */
    val source: String,
    val sourceBytes: ByteArray,
    /** Byte positions of line beginnings. */
    val lines: List<Pos>,
)

/** "Codemap" for `.rs` files. */
class NativeCodeMap(
    val filename: String,
    val start: ResolvedPos,
) : Comparable<NativeCodeMap> {
    companion object {
        const val SOURCE: String = "<native>"
        val SOURCE_BYTES: ByteArray = SOURCE.encodeToByteArray()
        val FULL_SPAN: Span = Span.new(Pos(0), Pos(SOURCE.length))

        fun new(filename: String, line: Int, column: Int): NativeCodeMap =
            NativeCodeMap(filename, ResolvedPos(line, column))

        fun toCodemap(self_: NativeCodeMap): CodeMap = CodeMap(CodeMapImpl.Native(self_))
    }

    override fun compareTo(other: NativeCodeMap): Int {
        val c1 = filename.compareTo(other.filename)
        if (c1 != 0) return c1
        return start.compareTo(other.start)
    }

    override fun equals(other: Any?): Boolean =
        other is NativeCodeMap && filename == other.filename && start == other.start

    override fun hashCode(): Int = filename.hashCode() * 31 + start.hashCode()
}

/** All are 0-based, but print out with 1-based. */
data class ResolvedPos(
    /** The line number within the file (0-indexed). */
    val line: Int = 0,
    /** The column within the line (0-indexed in characters). */
    val column: Int = 0,
) : Comparable<ResolvedPos> {
    companion object {
        internal fun testingParse(lineCol: String): ResolvedPos {
            val (line, col) = lineCol.split(':', limit = 2)
            return ResolvedPos(
                line = line.toInt() - 1,
                column = col.toInt() - 1,
            )
        }
    }

    override fun toString(): String = "${line + 1}:${column + 1}"

    override fun compareTo(other: ResolvedPos): Int {
        val c = line.compareTo(other.line)
        return if (c != 0) c else column.compareTo(other.column)
    }
}

/** A file, and a line and column range within it. */
data class FileSpanRef(
    val file: CodeMap,
    val span: Span,
) {
    /** Filename of this reference. */
    fun filename(): String = file.filename()

    /** Convert to the owned span. */
    fun toFileSpan(): FileSpan = FileSpan(file, span)

    /** Resolve span offsets to lines and columns. */
    fun resolveSpan(): ResolvedSpan = file.resolveSpan(span)

    /** Resolve the span. */
    fun sourceSpan(): String = file.sourceSpan(span)

    /**
     * Formats the span as `filename:startLine:startColumn: endLine:endColumn`,
     * or if the span is zero-length, `filename:line:column`, with a 1-indexed line and column.
     */
    override fun toString(): String = "${file.filename()}:${resolveSpan()}"
}

/** A file, and a line and column range within it. */
data class FileSpan(
    val file: CodeMap,
    val span: Span,
) : Comparable<FileSpan> {
    companion object {
        /** Creates an new [FileSpan] covering the entire file. */
        fun new(filename: String, source: String): FileSpan {
            val file = CodeMap.new(filename, source)
            val span = file.fullSpan()
            return FileSpan(file, span)
        }
    }

    /** Filename of this span. */
    fun filename(): String = file.filename()

    /** Resolve the span. */
    fun sourceSpan(): String = asRef().sourceSpan()

    /** Cheap reference to the span. */
    fun asRef(): FileSpanRef = FileSpanRef(file, span)

    /** Resolve the span to lines and columns. */
    fun resolveSpan(): ResolvedSpan = asRef().resolveSpan()

    /** Resolve the span to lines and columns. */
    fun resolve(): ResolvedFileSpan = ResolvedFileSpan(
        file = file.filename(),
        span = file.resolveSpan(span),
    )

    /**
     * Formats the span as `filename:startLine:startColumn: endLine:endColumn`,
     * or if the span is zero-length, `filename:line:column`, with a 1-indexed line and column.
     */
    override fun toString(): String = asRef().toString()

    override fun compareTo(other: FileSpan): Int {
        val c1 = filename().compareTo(other.filename())
        if (c1 != 0) return c1
        val c2 = span.compareTo(other.span)
        if (c2 != 0) return c2
        return systemIdentityHashCode(file).compareTo(systemIdentityHashCode(other.file))
    }
}

/**
 * The locations of values within a span.
 * All are 0-based, but print out with 1-based.
 */
data class ResolvedSpan(
    /** Beginning of the span. */
    val begin: ResolvedPos = ResolvedPos(),
    /** End of the span. */
    val end: ResolvedPos = ResolvedPos(),
) : Comparable<ResolvedSpan> {
    companion object {
        internal fun fromSpan(begin: ResolvedPos, end: ResolvedPos): ResolvedSpan =
            ResolvedSpan(begin, end)

        internal fun testingParse(span: String): ResolvedSpan {
            val dash = span.indexOf('-')
            return if (dash < 0) {
                val lineCol = ResolvedPos.testingParse(span)
                fromSpan(lineCol, lineCol)
            } else {
                val beginStr = span.substring(0, dash)
                val endStr = span.substring(dash + 1)
                val begin = ResolvedPos.testingParse(beginStr)
                if (':' in endStr) {
                    val end = ResolvedPos.testingParse(endStr)
                    fromSpan(begin, end)
                } else {
                    val endCol = endStr.toInt() - 1
                    fromSpan(begin, ResolvedPos(line = begin.line, column = endCol))
                }
            }
        }
    }

    override fun toString(): String {
        val singleLine = begin.line == end.line
        val isEmpty = singleLine && begin.column == end.column
        return when {
            isEmpty -> "${begin.line + 1}:${begin.column + 1}"
            singleLine -> "$begin-${end.column + 1}"
            else -> "$begin-$end"
        }
    }

    /**
     * Check that the given position is contained within this span.
     * Includes positions both at the beginning and the end of the range.
     */
    fun contains(pos: ResolvedPos): Boolean {
        return (begin.line < pos.line
            || (begin.line == pos.line && begin.column <= pos.column))
            && (end.line > pos.line
                || (end.line == pos.line && end.column >= pos.column))
    }

    override fun compareTo(other: ResolvedSpan): Int {
        val c = begin.compareTo(other.begin)
        return if (c != 0) c else end.compareTo(other.end)
    }
}

/** File and line number. */
data class ResolvedFileLine(
    /** File name. */
    val file: String,
    /** Line number is 0-based but displayed as 1-based. */
    val line: Int,
) {
    override fun toString(): String = "$file:${line + 1}"
}

/** File name and line and column pairs for a span. */
data class ResolvedFileSpan(
    /** File name. */
    val file: String,
    /** The span. */
    val span: ResolvedSpan,
) : Comparable<ResolvedFileSpan> {
    companion object {
        internal fun testingParse(span: String): ResolvedFileSpan {
            val colon = span.indexOf(':')
            return ResolvedFileSpan(
                file = span.substring(0, colon),
                span = ResolvedSpan.testingParse(span.substring(colon + 1)),
            )
        }
    }

    /** File and line number of the beginning of the span. */
    fun beginFileLine(): ResolvedFileLine = ResolvedFileLine(
        file = file,
        line = span.begin.line,
    )

    override fun toString(): String = "$file:$span"

    override fun compareTo(other: ResolvedFileSpan): Int {
        val c = file.compareTo(other.file)
        return if (c != 0) c else span.compareTo(other.span)
    }
}

// Identity-hash helper for KMP. Using `Any.hashCode()` on classes which do not override it yields
// identity-based hashing on every supported target.
private fun systemIdentityHashCode(value: Any): Int = value.hashCode()

private fun checkUtf8Boundary(bytes: ByteArray, index: Int) {
    if (index == 0 || index == bytes.size) return
    val b = bytes[index].toInt() and 0xff
    check((b and 0b1100_0000) != 0b1000_0000)
}
