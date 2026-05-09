// port-lint: source src/syntax/lint_suppressions.rs
package io.github.kotlinmania.starlarksyntax.syntax.lintsuppressions

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

import io.github.kotlinmania.starlarksyntax.codemap.CodeMap
import io.github.kotlinmania.starlarksyntax.codemap.Pos
import io.github.kotlinmania.starlarksyntax.codemap.Span

private const val LINT_SUPPRESISON_PREFIX: String = "starlark-lint-disable "

internal data class SuppressionInfo(
    /** The original span of the comment token containing the suppression. */
    val tokenSpan: Span,
    /** The span that this suppression effects. */
    val effectiveSpan: Span,
    /** Does the suppression cover the next line? */
    val suppressNextLine: Boolean,
)

internal class LintSuppressions internal constructor(
    /** A map from lint short names to spans where they are suppressed. */
    internal val suppressions: MutableMap<String, MutableList<SuppressionInfo>> = mutableMapOf(),
) {
    /** Check if a given lint short name and span is suppressed. */
    fun isSuppressed(issueShortName: String, issueSpan: Span): Boolean {
        val suppressionSpans = suppressions[issueShortName] ?: return false
        return suppressionSpans.any { info ->
            // is this suppression the last thing in a "suppress next line" issue span? ...
            if (info.suppressNextLine &&
                // (issueSpan includes line terminator)
                (issueSpan.end().value - 1) == info.tokenSpan.end().value
            ) {
                // ... then issue is not suppressed
                false
            } else {
                issueSpan.intersects(info.effectiveSpan)
            }
        }
    }
}

/** State needed for parsing a block of comments. */
private class ParseState(
    var tokenSpans: MutableList<Span> = mutableListOf(),
    var effectiveSpans: MutableList<Span> = mutableListOf(),
    var shortNames: MutableSet<String> = mutableSetOf(),
    var lastLine: Int = 0,
) {
    fun isEmpty(): Boolean =
        tokenSpans.isEmpty() && effectiveSpans.isEmpty() && shortNames.isEmpty()
}

/** Parse lint suppressions for a module and build a [LintSuppressions] struct. */
internal class LintSuppressionsBuilder {
    private var state = ParseState()
    private val suppressions = LintSuppressions()

    /** Call for each comment in a block of comments. */
    fun parseComment(
        codemap: CodeMap,
        comment: String,
        start: Int,
        end: Int,
    ) {
        val parsedShortNames = parseLintSuppressions(comment)
        if (parsedShortNames.isNotEmpty() || state.shortNames.isNotEmpty()) {
            val tokenSpan = Span.new(Pos.new(start), Pos.new(end))
            val line = codemap.findLine(Pos.new(start))
            val effectiveSpan = codemap.lineSpanTrimNewline(line)
            state.shortNames.addAll(parsedShortNames)
            state.tokenSpans.add(tokenSpan)
            state.effectiveSpans.add(effectiveSpan)
            state.lastLine = line
        }
    }

    /** Call after the last comment in a block of comments. */
    fun endOfCommentBlock(codemap: CodeMap) {
        if (state.shortNames.isNotEmpty()) {
            updateLintSuppressions(codemap)
        }
    }

    fun build(): LintSuppressions {
        check(state.isEmpty())
        return suppressions
    }

    /**
     * Update the per-line suppressions map with parsed lint suppressions for a block of comments.
     * Consumes and clears the [ParseState].
     */
    private fun updateLintSuppressions(codemap: CodeMap) {
        val taken = state
        state = ParseState()
        val numberOfTokens = taken.tokenSpans.size
        val tokenSpan = Span.mergeAll(taken.tokenSpans)
        var effectiveSpan = Span.mergeAll(taken.effectiveSpans)
        // In case the suppression comment has preceding whitespace
        val sourceBeforeToken =
            codemap.sourceSpan(Span.new(effectiveSpan.begin(), tokenSpan.begin()))
        val suppressNextLine = numberOfTokens > 1
            || effectiveSpan == tokenSpan
            || (effectiveSpan.end() == tokenSpan.end() && sourceBeforeToken.trim().isEmpty())
        if (suppressNextLine) {
            // Expand the span to include the next line,
            // in case suppression was put on the line before the issue
            val nextLineSpan = codemap.lineSpanOpt(taken.lastLine + 1)
            if (nextLineSpan != null) {
                effectiveSpan = effectiveSpan.merge(nextLineSpan)
            }
        }

        for (name in taken.shortNames) {
            suppressions.suppressions
                .getOrPut(name) { mutableListOf() }
                .add(
                    SuppressionInfo(
                        tokenSpan = tokenSpan,
                        effectiveSpan = effectiveSpan,
                        suppressNextLine = suppressNextLine,
                    )
                )
        }
    }
}

/** Parse a single comment line and extract any lint suppressions. */
private fun parseLintSuppressions(commentLine: String): List<String> {
    val res = mutableListOf<String>()
    val trimmed = commentLine.trimStart()
    if (trimmed.startsWith(LINT_SUPPRESISON_PREFIX)) {
        val shortNames = trimmed.substring(LINT_SUPPRESISON_PREFIX.length)
        for (name in shortNames.split(' ', ',')) {
            val nameTrimmed = name.trim()
            if (nameTrimmed.isNotEmpty()) {
                res.add(nameTrimmed)
            }
        }
    }
    return res
}
