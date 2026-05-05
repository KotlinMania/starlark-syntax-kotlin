// port-lint: source src/span_display.rs
package io.github.kotlinmania.starlarksyntax.spandisplay

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

import io.github.kotlinmania.starlarksyntax.codemap.FileSpanRef
import io.github.kotlinmania.starlarksyntax.faststring.len

/** Annotation type matching the upstream `annotate_snippets::AnnotationType`. */
internal enum class AnnotationType {
    Error,
    Warning,
    Info,
    Note,
    Help,
}

/** A single source-text annotation. */
internal data class SourceAnnotation(
    val label: String,
    val annotationType: AnnotationType,
    val range: Pair<Int, Int>,
)

/** A snippet slice: a range of source code with its annotations. */
internal data class Slice(
    val source: String,
    val lineStart: Int,
    val origin: String?,
    val fold: Boolean,
    val annotations: List<SourceAnnotation>,
)

/** A snippet annotation (top-level title or footer). */
internal data class Annotation(
    val label: String?,
    val id: String?,
    val annotationType: AnnotationType,
)

/** Format options for a snippet display. */
internal data class FormatOptions(
    val color: Boolean = false,
)

/** A snippet of source code with annotations and an optional title. */
internal data class Snippet(
    val title: Annotation?,
    val footer: List<Annotation>,
    val slices: List<Slice>,
    val opt: FormatOptions,
)

/** Gets annotated snippets. */
fun spanDisplay(
    span: FileSpanRef?,
    annotationLabel: String,
    color: Boolean,
): String {
    fun convertSpanToSlice(span: FileSpanRef): Slice {
        val region = span.resolveSpan()

        // we want the source_span to capture any whitespace ahead of the diagnostic span to
        // get the column numbers correct in the DisplayList, and any trailing source code
        // on the last line for context.
        val firstLineSpan = span.file.lineSpan(region.begin.line)
        val lastLineSpan = span.file.lineSpan(region.end.line)
        val sourceSpan = span.span.merge(firstLineSpan).merge(lastLineSpan)
        val source = span.file.sourceSpan(sourceSpan)

        // We want to highlight the span, which needs to be relative to source, and in
        // characters.
        // Our spans are in terms of bytes, but our resolved spans in terms of characters.
        val rangeStartChars = region.begin.column
        val rangeLenChars = len(span.sourceSpan()).value

        return Slice(
            source = source,
            lineStart = 1 + region.begin.line,
            origin = span.file.filename(),
            fold = false,
            annotations = listOf(
                SourceAnnotation(
                    label = "",
                    annotationType = AnnotationType.Error,
                    range = Pair(rangeStartChars, rangeStartChars + rangeLenChars),
                )
            ),
        )
    }

    val slice = span?.let { convertSpanToSlice(it) }

    val snippet = Snippet(
        title = Annotation(
            label = annotationLabel,
            id = null,
            annotationType = AnnotationType.Error,
        ),
        footer = emptyList(),
        slices = if (slice != null) listOf(slice) else emptyList(),
        opt = FormatOptions(color = color),
    )

    return formatSnippet(snippet)
}

/**
 * Render a [Snippet] as a string. This is a Kotlin-side rendering of what the Rust upstream
 * delegates to `annotate_snippets::DisplayList`. The shape mirrors the upstream layout:
 *
 * ```
 * error: <title>
 *   --> <file>:<line>:<col>
 *    |
 *  N | <source line>
 *    | ^^^^^^
 * ```
 */
private fun formatSnippet(snippet: Snippet): String {
    val out = StringBuilder()
    val title = snippet.title
    if (title != null) {
        out.append(title.annotationType.label())
        if (title.id != null) {
            out.append('[').append(title.id).append(']')
        }
        if (title.label != null) {
            out.append(": ").append(title.label)
        }
        out.append('\n')
    }
    for (slice in snippet.slices) {
        if (slice.origin != null) {
            out.append(" --> ").append(slice.origin)
                .append(':').append(slice.lineStart)
            if (slice.annotations.isNotEmpty()) {
                out.append(':').append(slice.annotations[0].range.first + 1)
            }
            out.append('\n')
        }
        var lines = slice.source.split('\n')
        if (lines.size > 1 && lines.last().isEmpty()) {
            // Match annotate-snippets behavior: a trailing newline does not introduce an extra empty line.
            lines = lines.dropLast(1)
        }
        var lineNo = slice.lineStart
        val lineNoWidth = (lineNo + lines.size).toString().length
        out.append(" ".repeat(lineNoWidth + 1)).append('|').append('\n')
        for (line in lines) {
            out.append(lineNo.toString().padStart(lineNoWidth)).append(" | ").append(line).append('\n')
            lineNo += 1
        }
        for (annotation in slice.annotations) {
            val (start, end) = annotation.range
            val caretCount = (end - start).coerceAtLeast(1)
            out.append(" ".repeat(lineNoWidth + 1)).append("| ")
                .append(" ".repeat(start))
                .append("^".repeat(caretCount))
            if (annotation.label.isNotEmpty()) {
                out.append(' ').append(annotation.label)
            }
            out.append('\n')
        }
        out.append(" ".repeat(lineNoWidth + 1)).append('|').append('\n')
    }
    for (footer in snippet.footer) {
        out.append(footer.annotationType.label())
        if (footer.label != null) {
            out.append(": ").append(footer.label)
        }
        out.append('\n')
    }
    if (out.isNotEmpty() && out.last() == '\n') {
        out.setLength(out.length - 1)
    }
    return out.toString()
}

private fun AnnotationType.label(): String = when (this) {
    AnnotationType.Error -> "error"
    AnnotationType.Warning -> "warning"
    AnnotationType.Info -> "info"
    AnnotationType.Note -> "note"
    AnnotationType.Help -> "help"
}
