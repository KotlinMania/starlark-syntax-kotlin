// port-lint: tests lexer_tests.rs
package io.github.kotlinmania.starlarksyntax

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
import io.github.kotlinmania.starlarksyntax.goldentesttemplate.goldenTestTemplate
import io.github.kotlinmania.starlarksyntax.lexer.Lexer
import io.github.kotlinmania.starlarksyntax.lexer.Token
import io.github.kotlinmania.starlarksyntax.lexer.Token.FStringToken
import io.github.kotlinmania.starlarksyntax.lexer.Token.StringToken
import io.github.kotlinmania.starlarksyntax.syntax.parser.Result as ParseResult
import kotlin.math.max
import kotlin.test.Test
import kotlin.test.assertEquals

private fun lexTokens(program: String): List<Triple<Int, Token, Int>> {
    fun tokens(dialect: Dialect, program: String): List<Triple<Int, Token, Int>> {
        val codemap = CodeMap.new("assert.bzl", program)
        val lexer = Lexer(program, dialect, codemap)
        val out = mutableListOf<Triple<Int, Token, Int>>()
        for (lexeme in lexer) {
            when (lexeme) {
                is ParseResult.Ok -> out.add(lexeme.value)
                is ParseResult.Err -> {
                    throw AssertionError(
                        "starlark::assert::lexTokens, expected lex success but failed\n" +
                            "Code: $program\n" +
                            "Error: ${lexeme.error}",
                    )
                }
            }
        }
        return out
    }

    fun checkSpans(tokens: List<Triple<Int, Token, Int>>) {
        var pos = 0
        for ((i, t, j) in tokens) {
            val spanIncorrect = "Span of $t incorrect"
            check(pos <= i) { "$spanIncorrect: $pos > $i" }
            check(i <= j) { "$spanIncorrect: $i > $j" }
            pos = j
        }
    }

    val orig = tokens(Dialect.AllOptionsInternal, program)
    checkSpans(orig)

    val withR =
        tokens(
            Dialect.AllOptionsInternal,
            program.replace("\r\n", "\n").replace("\n", "\r\n"),
        )
    checkSpans(withR)
    assertEquals(
        orig.map { it.second },
        withR.map { it.second },
        "starlark::assert::lexTokens, difference using CRLF newlines\nCode: $program",
    )

    return orig
}

private fun jsonStringLiteral(s: String): String {
    val out = StringBuilder()
    out.append('"')
    for (ch in s) {
        when (ch) {
            '\\' -> out.append("\\\\")
            '"' -> out.append("\\\"")
            '\b' -> out.append("\\b")
            '\u000C' -> out.append("\\f")
            '\n' -> out.append("\\n")
            '\r' -> out.append("\\r")
            '\t' -> out.append("\\t")
            else -> {
                if (ch.code < 0x20) {
                    out.append("\\u")
                    out.append(ch.code.toString(16).padStart(4, '0'))
                } else {
                    out.append(ch)
                }
            }
        }
    }
    out.append('"')
    return out.toString()
}

private fun Token.unlex(): String {
    return when (this) {
        Token.Indent -> "\t"
        Token.Newline -> "\n"
        Token.Dedent -> "#dedent"
        is StringToken -> jsonStringLiteral(this.value)
        is FStringToken -> "f" + jsonStringLiteral(this.value.content)
        else -> {
            val s = toString()
            val first = s.indexOf('\'')
            if (first >= 0 && s.endsWith('\'') && first != s.length - 1) {
                s.substring(first + 1, s.length - 1)
            } else {
                s
            }
        }
    }
}

private fun lex(program: String): String {
    return lexTokens(program).joinToString(" ") { it.second.unlex() }
}

private fun lexerGoldenTest(name: String, program: String) {
    val programTrimmed = program.trim()

    val out = StringBuilder()
    out.appendLine("Program:")
    out.appendLine(programTrimmed)
    out.appendLine()
    out.appendLine("Tokens:")

    val tokens = lexTokens(programTrimmed).map { (from, token, to) ->
        Triple(from, token.toString(), to)
    }
    var maxWidth = 0
    for ((_, token, _) in tokens) {
        maxWidth = max(maxWidth, token.length)
    }
    for ((from, token, to) in tokens) {
        val source = programTrimmed.substring(from, to).replace("\n", "\\n")
        out.append(token.padEnd(maxWidth))
        out.append("  # ")
        out.appendLine(source)
    }

    goldenTestTemplate("src/lexer_tests/$name.golden", out.toString())
}

private fun lexerFailGoldenTest(name: String, programs: List<String>) {
    val out = StringBuilder()

    for ((i, rawProgram) in programs.withIndex()) {
        if (i != 0) out.appendLine()

        val program = rawProgram.trim()
        val e =
            runCatching {
                val codemap = CodeMap.new("x", program)
                val lexer = Lexer(program, Dialect.AllOptionsInternal, codemap)
                lexer.forEach { lexeme ->
                    when (lexeme) {
                        is ParseResult.Ok -> {}
                        is ParseResult.Err -> throw lexeme.error
                    }
                }
            }.exceptionOrNull() ?: error("Expected lexer failure but got success")

        out.appendLine("Program:")
        out.appendLine(program)
        out.appendLine()
        out.appendLine("Error:")
        out.append(e.message ?: e.toString())
        out.appendLine()
    }

    goldenTestTemplate("src/lexer_tests/$name.fail.golden", out.toString())
}

class LexerTest {

    @Test
    fun testIntLit() {
        lexerGoldenTest(
            "int_lit",
            """
0 123
0x7F 0x7d
0B1011 0b1010
0o755 0O753
""",
        )
        // Starlark requires us to ban leading zeros (confusion with implicit octal)
        lexerFailGoldenTest("int_lit", listOf("x = 01"))
    }

    @Test
    fun testIndentation() {
        lexerGoldenTest(
            "indentation",
            """
+
  -
      /
      *
  =
    %
      .
+=
""",
        )
    }

    @Test
    fun testSymbols() {
        lexerGoldenTest(
            "symbols",
            ", ; : += -= *= /= //= %= == != <= >= ** = < > - + * % / // . { } [ ] ( ) |\n" +
                ",;:{}[]()|...",
        )
    }

    @Test
    fun testKeywords() {
        lexerGoldenTest(
            "keywords",
            "and else load break for not not  in continue if or def in pass elif return lambda",
        )
    }

    // Regression test for https://github.com/google/starlark-rust/issues/44.
    @Test
    fun testNumberCollatedWithKeywordsOrIdentifier() {
        lexerGoldenTest(
            "number_collated_with_keywords_or_identifier",
            "0in 1and 2else 3load 4break 5for 6not 7not  in 8continue 10identifier11",
        )
    }

    @Test
    fun testReserved() {
        lexerFailGoldenTest(
            "reserved",
            "as import is class nonlocal del raise except try finally while from with global yield"
                .split(Regex("\\s+"))
                .filter { it.isNotEmpty() },
        )
    }

    @Test
    fun testComment() {
        // Comment should be ignored
        lexerGoldenTest(
            "comment",
            """
# first comment
  # second comment
a # third comment

# But it should not eat everything
[
# comment inside list
]
""",
        )
    }

    @Test
    fun testIdentifier() {
        lexerGoldenTest("identifier", "a identifier CAPS _CAPS _0123")
    }

    @Test
    fun testStringLit() {
        assertEquals(
            """"123" "123" "" "" "'" "\"" "\"" "'" "\n" "\\w" 
""",
            lex("'123' \"123\" '' \"\" '\\'' \"\\\"\" '\"' \"'\" '\\n' '\\w'"),
        )

        // unfinished string literal
        lexerFailGoldenTest(
            "string_lit",
            listOf(
                "'\n'",
                "\"\n\"",
                "this = a + test + r\"",
                "test + ' of thing that",
                "test + ' of thing that\n'",
            ),
        )

        // Multiline string
        assertEquals(
            "\"\" \"\\n\" \"\\n\" \"\" \"\\n\" \"\\n\" \n",
            lex("'''''' '''\\n''' '''\n''' \"\"\"\"\"\" \"\"\"\\n\"\"\" \"\"\"\n\"\"\""),
        )
        // Raw string
        assertEquals(
            "\"\" \"\" \"'\" \"\\\"\" \"\\\"\" \"'\" \"\\\\n\" \n",
            lex("r'' r\"\" r'\\'' r\"\\\"\" r'\"' r\"'\" r'\\n'"),
        )
    }

    @Test
    fun testStringEscape() {
        lexerGoldenTest(
            "string_escape",
            """
'\0\0\1n'
'\0\00\000\0000'
'\x000'
'\372x'
""",
        )
        lexerFailGoldenTest(
            "string_escape",
            listOf(
                "test 'more \\xTZ",
                "test + 'more \\UFFFFFFFF overflows'",
                "test 'more \\x0yabc'",
                "test 'more \\x0",
            ),
        )
    }

    @Test
    fun testSimpleExample() {
        lexerGoldenTest(
            "simple_example",
            "\"\"\"A docstring.\"\"\"\n\n" +
                "def _impl(ctx):\n" +
                "  # Print Hello, World!\n" +
                "  print('Hello, World!')\n",
        )
    }

    @Test
    fun testEscapeNewline() {
        lexerGoldenTest(
            "escape_newline",
            """
a \
b
""",
        )
    }

    @Test
    fun testLexerMultilineTriple() {
        lexerGoldenTest(
            "multiline_triple",
            "\n" +
                "cmd = \"\"\"A \\\n" +
                "    B \\\n" +
                "    C \\\n" +
                "    \"\"\"\n",
        )
    }

    @Test
    fun testSpan() {
        val expected =
            listOf(
                Triple(0, Token.Newline, 1),
                Triple(1, Token.Def, 4),
                Triple(5, Token.Identifier("test"), 9),
                Triple(9, Token.OpeningRound, 10),
                Triple(10, Token.Identifier("a"), 11),
                Triple(11, Token.ClosingRound, 12),
                Triple(12, Token.Colon, 13),
                Triple(13, Token.Newline, 14),
                Triple(14, Token.Indent, 16),
                Triple(16, Token.Identifier("fail"), 20),
                Triple(20, Token.OpeningRound, 21),
                Triple(21, Token.Identifier("a"), 22),
                Triple(22, Token.ClosingRound, 23),
                Triple(23, Token.Newline, 24),
                Triple(24, Token.Newline, 25),
                Triple(25, Token.Dedent, 25),
                Triple(25, Token.Identifier("test"), 29),
                Triple(29, Token.OpeningRound, 30),
                Triple(30, Token.StringToken("abc"), 35),
                Triple(35, Token.ClosingRound, 36),
                Triple(36, Token.Newline, 37),
                Triple(37, Token.Newline, 37),
            )

        val actual =
            lexTokens(
                """
def test(a):
  fail(a)

test("abc")
""",
            )
        assertEquals(expected, actual)
    }

    @Test
    fun testLexerFinalComment() {
        lexerGoldenTest(
            "final_comment",
            """
x
# test
""",
        )
    }

    @Test
    fun testLexerDedent() {
        lexerGoldenTest(
            "dedent",
            """
def stuff():
  if 1:
    if 1:
      pass
  pass
""",
        )
    }

    @Test
    fun testLexerOperators() {
        lexerGoldenTest(
            "operators",
            """
1+-2
1+------2
///==/+-
""",
        )
    }

    @Test
    fun testLexerErrorMessages() {
        lexerFailGoldenTest(
            "error_messages",
            listOf(
                "unknown $&%+ operator",
                "an 'incomplete string\nends",
                "an + 'invalid escape \\x3  character'",
                "leading_zero = 003 + 8",
                "reserved_word = raise + 1",
            ),
        )
    }

    @Test
    fun testFloatLit() {
        lexerGoldenTest(
            "float_lit",
            """
0.0 0. .0
1e10 1e+10 1e-10
1.1e10 1.1e+10 1.1e-10
0. .123 3.14 .2e3 1E+4
""",
        )
    }

    @Test
    fun testFString() {
        lexerGoldenTest(
            "f_string",
            """
f"basic1 {stuff1}"
f'basic2 {stuff2}'

# Raw f-string

fr'' fr"" fr'\'' fr"\"" fr'"' fr"'" fr'\n'
""",
        )
    }
}
