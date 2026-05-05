// port-lint: source src/golden_test_template.rs
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

private const val REGENERATE_VAR_NAME: String = "STARLARK_RUST_REGENERATE_GOLDEN_TESTS"

private fun makeGolden(output: String): String {
    val golden = StringBuilder()
    golden.append("# @generated\n")
    golden.append("# To regenerate, run:\n")
    golden.append("# ```\n")
    // TODO(nga): fix instruction for `starlark_syntax` crate.
    golden.append("# ").append(REGENERATE_VAR_NAME).append("=1 cargo test -p starlark --lib\n")
    golden.append("# ```\n")
    golden.append('\n')
    golden.append(output.trimEnd())
    golden.append('\n')
    return golden.toString()
}

/**
 * Common code for golden tests.
 *
 * The upstream Rust crate uses `CARGO_MANIFEST_DIR` and reads `src/**/*.golden` files from the
 * crate root. In this Kotlin port, the oracle golden files live under `tmp/starlark_syntax/`,
 * so we resolve `goldenRelPath` under that directory.
 *
 * Regeneration is deliberately unsupported because `tmp/` is read-only by convention.
 */
fun goldenTestTemplate(goldenRelPath: String, output: String) {
    check(goldenRelPath.startsWith("src/"))
    check(goldenRelPath.contains(".golden"))

    val outputWithPrefix = makeGolden(output)

    val regenerate = platformGetEnv(REGENERATE_VAR_NAME) != null
    check(!regenerate) {
        "Golden regeneration is not supported in Kotlin ports; do not write into tmp/. " +
            "Unset $REGENERATE_VAR_NAME and re-run the tests."
    }

    val goldenFilePath = "tmp/starlark_syntax/$goldenRelPath"
    var expected = platformReadUtf8File(goldenFilePath)
    if (platformIsWindows()) {
        // Git may check out files on Windows with \\r\\n as line separator.
        // We could configure git, but it's more reliable to handle it in the test.
        expected = expected.replace("\r\n", "\n")
    }
    if (expected != outputWithPrefix) {
        fun snippet(label: String, text: String, startLine: Int, endLine: Int): String {
            val lines = text.split('\n')
            val lo = startLine.coerceAtLeast(1)
            val hi = endLine.coerceAtMost(lines.size)
            val out = StringBuilder()
            out.appendLine("$label (lines $lo..$hi):")
            for (i in lo..hi) {
                out.appendLine("${i.toString().padStart(4, ' ')}|${lines[i - 1]}")
            }
            return out.toString()
        }

        val expectedLines = expected.split('\n')
        val actualLines = outputWithPrefix.split('\n')
        val max = maxOf(expectedLines.size, actualLines.size)
        var firstDiff = 1
        while (firstDiff <= max) {
            val e = expectedLines.getOrNull(firstDiff - 1)
            val a = actualLines.getOrNull(firstDiff - 1)
            if (e != a) break
            firstDiff++
        }

        val context = 6
        val start = (firstDiff - context).coerceAtLeast(1)
        val end = firstDiff + context
        val message =
            "Golden file mismatch for `$goldenFilePath` at line $firstDiff.\n" +
                "Set $REGENERATE_VAR_NAME in upstream Rust to regenerate.\n\n" +
                snippet("Expected", expected, start, end) +
                "\n" +
                snippet("Actual", outputWithPrefix, start, end)
        throw IllegalStateException(message)
    }
}

internal expect fun platformGetEnv(name: String): String?

internal expect fun platformReadUtf8File(path: String): String

internal expect fun platformIsWindows(): Boolean
