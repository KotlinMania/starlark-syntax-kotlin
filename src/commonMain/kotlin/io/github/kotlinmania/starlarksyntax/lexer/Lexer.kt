// port-lint: source src/lexer.rs
package io.github.kotlinmania.starlarksyntax.lexer

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
import io.github.kotlinmania.starlarksyntax.cursors.CursorBytes
import io.github.kotlinmania.starlarksyntax.cursors.CursorChars
import io.github.kotlinmania.starlarksyntax.Dialect
import io.github.kotlinmania.starlarksyntax.error.Error
import io.github.kotlinmania.starlarksyntax.error.ErrorKind
import io.github.kotlinmania.starlarksyntax.evalexception.EvalException
import io.github.kotlinmania.starlarksyntax.syntax.parser.Result as ParseResult

sealed class LexemeError(val message: String) {
    data object Indentation : LexemeError("Parse error: incorrect indentation")
    data class InvalidInput(val input: String) : LexemeError("Parse error: invalid input `$input`")
    data object InvalidTab : LexemeError("Parse error: tabs are not allowed")
    data object UnfinishedStringLiteral : LexemeError("Parse error: unfinished string literal")
    data class InvalidEscapeSequence(val seq: String) :
        LexemeError("Parse error: invalid string escape sequence `$seq`")

    data object EmptyEscapeSequence :
        LexemeError("Parse error: missing string escape sequence, only saw `\\`")

    data class ReservedKeyword(val keyword: String) :
        LexemeError("Parse error: cannot use reserved keyword `$keyword`")

    data class StartsZero(val literal: String) :
        LexemeError("Parse error: integer cannot have leading 0, got `$literal`")

    data class IntParse(val literal: String) :
        LexemeError("Parse error: failed to parse integer: `$literal`")

    data object CommentSpanComputedIncorrectly :
        LexemeError("Comment span is computed incorrectly (internal error)")

    data class CannotParse(val literal: String, val base: Int) :
        LexemeError("Cannot parse `$literal` as an integer in base $base")
}

private class LexemeErrorException(private val err: LexemeError) : Exception(err.message) {
    override fun toString(): String = err.message
}

private typealias LexemeT<T> = ParseResult<Triple<Int, T, Int>, EvalException>
internal typealias Lexeme = LexemeT<Token>

private fun <T1, T2> mapLexemeT(lexeme: LexemeT<T1>, f: (T1) -> T2): LexemeT<T2> {
    return when (lexeme) {
        is ParseResult.Ok -> {
            val (l, t, r) = lexeme.value
            ParseResult.Ok(Triple(l, f(t), r))
        }
        is ParseResult.Err -> ParseResult.Err(lexeme.error)
    }
}

internal class Lexer(
    input: String,
    _dialect: Dialect,
    private val codemap: CodeMap,
) : Iterator<Lexeme> {
    private val indentLevels: MutableList<Int> = ArrayList(20)
    private val buffer: ArrayDeque<Lexeme> = ArrayDeque(10)
    private var parens: Int = 0
    private val lexer: TokenLexer = TokenLexer(input)
    private var done: Boolean = false

    init {
        val e = calculateIndent()
        if (e != null) {
            buffer.addLast(ParseResult.Err(e))
        }
    }

    private fun errPos(msg: LexemeError, pos: Int): EvalException {
        return errSpan(msg, pos, pos)
    }

    private fun errSpan(msg: LexemeError, start: Int, end: Int): EvalException {
        return EvalException.new(
            Error.newKind(ErrorKind.Parser(LexemeErrorException(msg))),
            Span.new(Pos.new(start), Pos.new(end)),
            codemap,
        )
    }

    private fun errNow(msg: (String) -> LexemeError): EvalException {
        return errSpan(
            msg(lexer.slice()),
            lexer.spanStart(),
            lexer.spanEnd(),
        )
    }

    /// Comment tokens are produced by either the token lexer for comments after code,
    /// or explicitly on lines which are only comments. This function is used in the latter case.
    private fun makeComment(start: Int, end: Int): Lexeme {
        val comment = lexer.sliceFromSource(start, end)
        if (!comment.startsWith('#')) {
            return ParseResult.Err(errPos(LexemeError.CommentSpanComputedIncorrectly, start))
        }
        // Remove the `#`.
        var text = comment.substring(1)
        // Remove the trailing `\r` if it exists.
        // Note comments do not contain `\n`.
        var actualEnd = end
        if (text.endsWith('\r')) {
            actualEnd -= 1
            text = text.dropLast(1)
        }
        return ParseResult.Ok(Triple(start, Token.Comment(text), actualEnd))
    }

    /// We have just seen a newline, read how many indents we have
    /// and then set self.indent properly.
    private fun calculateIndent(): EvalException? {
        // Consume tabs and spaces, output the indentation levels.
        val it = CursorBytes(lexer.remainder())
        var spaces = 0
        var tabs = 0
        var indentStart = lexer.spanEnd()

        while (true) {
            when (val c = it.nextChar()) {
                null -> {
                    lexer.bump(it.pos())
                    return null
                }
                ' ' -> spaces += 1
                '\t' -> tabs += 1
                '\n' -> {
                    // A line that is entirely blank gets emitted as a newline, and then
                    // we don't consume the subsequent newline character.
                    lexer.bump(it.pos() - 1)
                    return null
                }
                '\r' -> {
                    // We just ignore these entirely.
                }
                '#' -> {
                    // A line that is all comments, only emits comment tokens.
                    // Skip until the next newline.
                    // Remove skip now, so we can freely add it on later.
                    spaces = 0
                    tabs = 0
                    val start = lexer.spanEnd() + it.pos() - 1
                    while (true) {
                        when (it.nextChar()) {
                            null -> {
                                val end = lexer.spanEnd() + it.pos()
                                buffer.addLast(makeComment(start, end))
                                lexer.bump(it.pos())
                                return null
                            }
                            '\n' -> break
                            else -> {}
                        }
                    }
                    val end = lexer.spanEnd() + it.pos() - 1
                    buffer.addLast(makeComment(start, end))
                    indentStart = lexer.spanEnd() + it.pos()
                }
                else -> break
            }
        }

        lexer.bump(it.pos() - 1) // last character broke us out of the loop
        val indent = spaces + tabs * 8
        if (tabs > 0) {
            return errPos(LexemeError.InvalidTab, lexer.spanStart())
        }

        val now = indentLevels.lastOrNull() ?: 0
        if (indent > now) {
            indentLevels.add(indent)
            buffer.addLast(ParseResult.Ok(Triple(indentStart, Token.Indent, lexer.spanEnd())))
        } else if (indent < now) {
            var dedents = 1
            indentLevels.removeLast()
            while (true) {
                val current = indentLevels.lastOrNull() ?: 0
                if (current == indent) {
                    break
                } else if (current > indent) {
                    dedents += 1
                    indentLevels.removeLast()
                } else {
                    return errSpan(LexemeError.Indentation, lexer.spanStart(), lexer.spanEnd())
                }
            }
            for (i in 0 until dedents) {
                // We must declare each dedent is only a position, so multiple adjacent dedents don't overlap.
                buffer.addLast(ParseResult.Ok(Triple(indentStart, Token.Dedent, indentStart)))
            }
        }
        return null
    }

    private fun wrap(token: Token): Lexeme {
        return ParseResult.Ok(Triple(lexer.spanStart(), token, lexer.spanEnd()))
    }

    // We've potentially seen one character, now consume between min and max elements of iterator
    // and treat it as an int in base radix.
    private fun escapeChar(it: CursorChars, min: Int, max: Int, radix: Int): Int? {
        var value = 0
        var count = 0
        while (count < max) {
            val c = it.next()
            if (c == null) {
                if (count >= min) break else return null
            } else {
                val digit = digitToInt(c, radix)
                if (digit == null) {
                    if (count >= min) {
                        it.unnext(c)
                        break
                    } else {
                        return null
                    }
                } else {
                    count += 1
                    value = (value * radix) + digit
                }
            }
        }
        return value
    }

    // We have seen a '\' character, now parse what comes next.
    private fun escape(it: CursorChars, res: StringBuilder): Boolean {
        return when (val c = it.next()) {
            null -> false
            'n'.code -> {
                res.append('\n'); true
            }
            'r'.code -> {
                res.append('\r'); true
            }
            't'.code -> {
                res.append('\t'); true
            }
            'a'.code -> {
                res.append('\u0007'); true
            }
            'b'.code -> {
                res.append('\u0008'); true
            }
            'f'.code -> {
                res.append('\u000c'); true
            }
            'v'.code -> {
                res.append('\u000b'); true
            }
            '\n'.code -> true
            '\r'.code -> {
                // Windows newline incoming, we expect a \n next, which we can ignore.
                it.next() == '\n'.code
            }
            'x'.code -> {
                val ch = escapeChar(it, 2, 2, 16) ?: return false
                res.appendCodePoint(ch)
                true
            }
            'u'.code -> {
                val ch = escapeChar(it, 4, 4, 16) ?: return false
                res.appendCodePoint(ch)
                true
            }
            'U'.code -> {
                val ch = escapeChar(it, 8, 8, 16) ?: return false
                res.appendCodePoint(ch)
                true
            }
            else -> {
                when (c) {
                    in '0'.code..'7'.code -> {
                        it.unnext(c)
                        val ch = escapeChar(it, 1, 3, 8) ?: return false
                        res.appendCodePoint(ch)
                        true
                    }
                    '"'.code, '\''.code, '\\'.code -> {
                        res.appendCodePoint(c)
                        true
                    }
                    else -> {
                        res.append('\\')
                        res.appendCodePoint(c)
                        true
                    }
                }
            }
        }
    }

    /// Parse a String. Return the String, and the offset where it starts.
    /// String parsing is a hot-spot, so parameterise by a `stop` function which gets
    /// specialised for each variant.
    private fun string(
        triple: Boolean,
        raw: Boolean,
        stop: (Int) -> Boolean,
    ): LexemeT<Pair<String, Int>> {
        // We have seen an opening quote, which is either ' or ".
        // If triple is true, it was a triple quote.
        // stop lets us know when a string ends.

        // Before the first quote character.
        val stringStart = lexer.spanStart()
        // After the first quote character, but before any contents or it tracked stuff.
        var stringEnd = lexer.spanEnd()

        val it = CursorBytes(lexer.remainder())
        var it2: CursorChars? = null

        if (triple) {
            it.next()
            it.next()
        }
        val contentsStart = it.pos()

        // Take the fast path as long as the result is a slice of the original, with no changes.
        var res: StringBuilder? = null
        while (true) {
            val c = it.nextChar()
            if (c == null) {
                return ParseResult.Err(
                    errSpan(LexemeError.UnfinishedStringLiteral, stringStart, stringEnd + it.pos()),
                )
            } else if (stop(c.code)) {
                val contentsEnd = it.pos() - if (triple) 3 else 1
                val contents = lexer.remainderSlice(contentsStart, contentsEnd)
                lexer.bump(it.pos())
                return ParseResult.Ok(Triple(stringStart, Pair(contents, contentsStart), stringEnd + it.pos()))
            } else if (c == '\\' || c == '\r' || (c == '\n' && !triple)) {
                res = StringBuilder(it.pos() + 10)
                res.append(lexer.remainderSlice(contentsStart, it.pos() - 1))
                it2 = CursorChars.newOffset(lexer.remainder(), it.pos() - 1)
                break
            }
        }

        // We bailed out of the fast path, that means we now accumulate character by character,
        // might have an error or be dealing with escape characters.
        val out = checkNotNull(res)
        val itSlow = checkNotNull(it2)
        while (true) {
            val c = itSlow.next() ?: break
            if (stop(c)) {
                lexer.bump(itSlow.pos())
                if (triple) {
                    if (out.length >= 2) {
                        out.setLength(out.length - 2)
                    }
                }
                return ParseResult.Ok(Triple(stringStart, Pair(out.toString(), contentsStart), stringEnd + itSlow.pos()))
            }
            when (c) {
                '\n'.code -> {
                    if (!triple) {
                        // Will raise an error about out of chars.
                        // But don't include the final \n in the count.
                        stringEnd -= 1
                        break
                    } else {
                        out.append('\n')
                    }
                }
                '\r'.code -> {
                    // We just ignore these in all modes.
                }
                '\\'.code -> {
                    if (raw) {
                        val next = itSlow.next() ?: break
                        if (next != '\''.code && next != '"'.code) {
                            out.append('\\')
                        }
                        out.appendCodePoint(next)
                    } else {
                        val pos = itSlow.pos()
                        if (!escape(itSlow, out)) {
                            val bad = lexer.remainderSlice(pos, itSlow.pos())
                            val err =
                                if (bad.isEmpty()) LexemeError.EmptyEscapeSequence else LexemeError.InvalidEscapeSequence(bad)
                            return ParseResult.Err(errSpan(err, stringEnd + pos - 1, stringEnd + itSlow.pos()))
                        }
                    }
                }
                else -> out.appendCodePoint(c)
            }
        }

        // We ran out of characters.
        return ParseResult.Err(errSpan(LexemeError.UnfinishedStringLiteral, stringStart, stringEnd + itSlow.pos()))
    }

    private fun int(s: String, radix: Int): Lexeme {
        return try {
            val i = TokenInt.fromStrRadix(s, radix)
            ParseResult.Ok(Triple(lexer.spanStart(), Token.IntToken(i), lexer.spanEnd()))
        } catch (e: Exception) {
            ParseResult.Err(errNow { LexemeError.IntParse(it) })
        }
    }

    override fun hasNext(): Boolean {
        if (buffer.isNotEmpty()) return true
        if (done) return false
        // Do not eagerly lex in hasNext; next() drives state.
        return true
    }

    override fun next(): Lexeme {
        while (true) {
            val buffered = buffer.removeFirstOrNull()
            if (buffered != null) return buffered

            if (done) {
                throw NoSuchElementException("No more tokens")
            }

            val next = lexer.nextToken()
            when (next) {
                null -> {
                    done = true
                    val pos = lexer.spanEnd()
                    for (i in indentLevels.indices) {
                        buffer.addLast(ParseResult.Ok(Triple(pos, Token.Dedent, pos)))
                    }
                    indentLevels.clear()
                    return wrap(Token.Newline)
                }
                is ParseResult.Ok -> {
                    val token = next.value
                    when (token) {
                        Token.Tabs -> {
                            buffer.addLast(ParseResult.Err(errPos(LexemeError.InvalidTab, lexer.spanStart())))
                            continue
                        }
                        Token.Newline -> {
                            if (parens == 0) {
                                val spanStart = lexer.spanStart()
                                val spanEnd = lexer.spanEnd()
                                val e = calculateIndent()
                                if (e != null) {
                                    return ParseResult.Err(e)
                                }
                                return ParseResult.Ok(Triple(spanStart, Token.Newline, spanEnd))
                            } else {
                                continue
                            }
                        }
                        Token.Reserved -> return ParseResult.Err(errNow { LexemeError.ReservedKeyword(it) })
                        Token.RawDecInt -> {
                            val s = lexer.slice()
                            if (s.length > 1 && s.startsWith("0")) {
                                return ParseResult.Err(errNow { LexemeError.StartsZero(it) })
                            }
                            return int(s, 10)
                        }
                        Token.RawOctInt -> {
                            val s = lexer.slice()
                            return int(s.substring(2), 8)
                        }
                        Token.RawHexInt -> {
                            val s = lexer.slice()
                            return int(s.substring(2), 16)
                        }
                        Token.RawBinInt -> {
                            val s = lexer.slice()
                            return int(s.substring(2), 2)
                        }
                        is Token.IntToken -> error("Lexer does not produce Int tokens")
                        Token.RawDoubleQuote -> {
                            val raw = (lexer.spanEnd() - lexer.spanStart()) == 2
                            val lex = parseDoubleQuotedString(raw)
                            if (lex == null) continue
                            return mapLexemeT(lex) { (s, _offset) -> Token.StringToken(s) }
                        }
                        Token.RawSingleQuote -> {
                            val raw = (lexer.spanEnd() - lexer.spanStart()) == 2
                            val lex = parseSingleQuotedString(raw)
                            if (lex == null) continue
                            return mapLexemeT(lex) { (s, _offset) -> Token.StringToken(s) }
                        }
                        is Token.StringToken -> error("The lexer does not produce String")
                        Token.RawFStringDoubleQuote -> {
                            val spanLen = lexer.spanEnd() - lexer.spanStart()
                            val raw = spanLen == 3
                            val lex = parseDoubleQuotedString(raw)
                            if (lex == null) continue
                            return mapLexemeT(lex) { (content, contentStartOffset) ->
                                Token.FStringToken(
                                    TokenFString(
                                        content = content,
                                        contentStartOffset = contentStartOffset + spanLen,
                                    ),
                                )
                            }
                        }
                        Token.RawFStringSingleQuote -> {
                            val spanLen = lexer.spanEnd() - lexer.spanStart()
                            val raw = spanLen == 3
                            val lex = parseSingleQuotedString(raw)
                            if (lex == null) continue
                            return mapLexemeT(lex) { (content, contentStartOffset) ->
                                Token.FStringToken(
                                    TokenFString(
                                        content = content,
                                        contentStartOffset = contentStartOffset + spanLen,
                                    ),
                                )
                            }
                        }
                        is Token.FStringToken -> error("The lexer does not produce FString")
                        Token.OpeningCurly, Token.OpeningRound, Token.OpeningSquare -> {
                            parens += 1
                            return wrap(token)
                        }
                        Token.ClosingCurly, Token.ClosingRound, Token.ClosingSquare -> {
                            parens -= 1
                            return wrap(token)
                        }
                        else -> return wrap(token)
                    }
                }
                is ParseResult.Err -> {
                    return ParseResult.Err(errNow { LexemeError.InvalidInput(it) })
                }
            }
        }
    }

    private fun parseDoubleQuotedString(raw: Boolean): LexemeT<Pair<String, Int>>? {
        return if (lexer.remainder().startsWith("\"\"")) {
            var qs = 0
            string(
                triple = true,
                raw = raw,
                stop = { c ->
                    if (c == '"'.code) {
                        qs += 1
                        qs == 3
                    } else {
                        qs = 0
                        false
                    }
                },
            )
        } else {
            string(triple = false, raw = raw, stop = { c -> c == '"'.code })
        }
    }

    private fun parseSingleQuotedString(raw: Boolean): LexemeT<Pair<String, Int>>? {
        return if (lexer.remainder().startsWith("''")) {
            var qs = 0
            string(
                triple = true,
                raw = raw,
                stop = { c ->
                    if (c == '\''.code) {
                        qs += 1
                        qs == 3
                    } else {
                        qs = 0
                        false
                    }
                },
            )
        } else {
            string(triple = false, raw = raw, stop = { c -> c == '\''.code })
        }
    }
}

private fun digitToInt(c: Int, radix: Int): Int? {
    val v =
        when (c) {
            in '0'.code..'9'.code -> c - '0'.code
            in 'a'.code..'f'.code -> 10 + (c - 'a'.code)
            in 'A'.code..'F'.code -> 10 + (c - 'A'.code)
            else -> return null
        }
    return if (v < radix) v else null
}

private fun StringBuilder.appendCodePoint(codePoint: Int) {
    when {
        codePoint <= 0xffff -> append(codePoint.toChar())
        else -> {
            val cp = codePoint - 0x1_0000
            val high = 0xd800 + (cp ushr 10)
            val low = 0xdc00 + (cp and 0x3ff)
            append(high.toChar())
            append(low.toChar())
        }
    }
}

private class TokenLexer(private val source: String) {
    private val utf8: Utf8Index = Utf8Index(source)
    private var pos: Int = 0
    private var spanStart: Int = 0
    private var spanEnd: Int = 0
    private var slice: String = ""

    fun spanStart(): Int = spanStart
    fun spanEnd(): Int = spanEnd
    fun slice(): String = slice

    fun remainder(): String = utf8.substringFromByte(pos)

    fun remainderSlice(start: Int, end: Int): String {
        return utf8.substringFromByte(pos + start, pos + end)
    }

    fun sliceFromSource(start: Int, end: Int): String {
        return utf8.substringFromByte(start, end)
    }

    fun bump(bytes: Int) {
        pos += bytes
        spanEnd = pos
    }

    fun nextToken(): ParseResult<Token, Unit>? {
        // logos(skip r" +"): whitespace (spaces only)
        while (pos < utf8.byteLen && utf8.byteAt(pos) == ' '.code) {
            pos += 1
        }
        // logos(skip r"\\\n") and r"\\\r\n"
        while (true) {
            if (pos + 1 < utf8.byteLen && utf8.byteAt(pos) == '\\'.code && utf8.byteAt(pos + 1) == '\n'.code) {
                pos += 2
                continue
            }
            if (
                pos + 2 < utf8.byteLen &&
                    utf8.byteAt(pos) == '\\'.code &&
                    utf8.byteAt(pos + 1) == '\r'.code &&
                    utf8.byteAt(pos + 2) == '\n'.code
            ) {
                pos += 3
                continue
            }
            break
        }

        if (pos >= utf8.byteLen) {
            spanStart = pos
            spanEnd = pos
            slice = ""
            return null
        }

        spanStart = pos
        val b0 = utf8.byteAt(pos)

        // Comment as token. Span includes the leading '#', but the content does not.
        if (b0 == '#'.code) {
            pos += 1
            while (pos < utf8.byteLen) {
                val b = utf8.byteAt(pos)
                if (b == '\r'.code || b == '\n'.code) break
                pos += 1
            }
            spanEnd = pos
            slice = utf8.substringFromByte(spanStart, spanEnd)
            val text = slice.substring(1)
            return ParseResult.Ok(Token.Comment(text))
        }

        // Tabs (might be an error).
        if (b0 == '\t'.code) {
            pos += 1
            while (pos < utf8.byteLen && utf8.byteAt(pos) == '\t'.code) pos += 1
            spanEnd = pos
            slice = utf8.substringFromByte(spanStart, spanEnd)
            return ParseResult.Ok(Token.Tabs)
        }

        // Newline outside a string.
        if (b0 == '\n'.code || (b0 == '\r'.code && pos + 1 < utf8.byteLen && utf8.byteAt(pos + 1) == '\n'.code)) {
            pos += if (b0 == '\r'.code) 2 else 1
            spanEnd = pos
            slice = utf8.substringFromByte(spanStart, spanEnd)
            return ParseResult.Ok(Token.Newline)
        }

        // Raw quote tokens.
        if (b0 == '\''.code) {
            pos += 1
            spanEnd = pos
            slice = "'"
            return ParseResult.Ok(Token.RawSingleQuote)
        }
        if (b0 == '"'.code) {
            pos += 1
            spanEnd = pos
            slice = "\""
            return ParseResult.Ok(Token.RawDoubleQuote)
        }
        if (b0 == 'r'.code && pos + 1 < utf8.byteLen) {
            val b1 = utf8.byteAt(pos + 1)
            if (b1 == '\''.code) {
                pos += 2
                spanEnd = pos
                slice = "r'"
                return ParseResult.Ok(Token.RawSingleQuote)
            }
            if (b1 == '"'.code) {
                pos += 2
                spanEnd = pos
                slice = "r\""
                return ParseResult.Ok(Token.RawDoubleQuote)
            }
        }
        if (b0 == 'f'.code && pos + 1 < utf8.byteLen) {
            val b1 = utf8.byteAt(pos + 1)
            if (b1 == '\''.code) {
                pos += 2
                spanEnd = pos
                slice = "f'"
                return ParseResult.Ok(Token.RawFStringSingleQuote)
            }
            if (b1 == '"'.code) {
                pos += 2
                spanEnd = pos
                slice = "f\""
                return ParseResult.Ok(Token.RawFStringDoubleQuote)
            }
            if (b1 == 'r'.code && pos + 2 < utf8.byteLen) {
                val b2 = utf8.byteAt(pos + 2)
                if (b2 == '\''.code) {
                    pos += 3
                    spanEnd = pos
                    slice = "fr'"
                    return ParseResult.Ok(Token.RawFStringSingleQuote)
                }
                if (b2 == '"'.code) {
                    pos += 3
                    spanEnd = pos
                    slice = "fr\""
                    return ParseResult.Ok(Token.RawFStringDoubleQuote)
                }
            }
        }

        // Identifier / keyword / reserved keyword.
        if (isIdentStartByte(b0)) {
            pos += 1
            while (pos < utf8.byteLen && isIdentContByte(utf8.byteAt(pos))) {
                pos += 1
            }
            spanEnd = pos
            slice = utf8.substringFromByte(spanStart, spanEnd)
            val keyword = keywordToken(slice)
            if (keyword != null) return ParseResult.Ok(keyword)
            if (slice in RESERVED_KEYWORDS) return ParseResult.Ok(Token.Reserved)
            return ParseResult.Ok(Token.Identifier(slice))
        }

        // Numeric literals and floats.
        if (b0 in '0'.code..'9'.code || b0 == '.'.code) {
            val numeric = scanNumericOrFloat()
            if (numeric != null) return ParseResult.Ok(numeric)
        }

        // Symbols, brackets, operators.
        val symbol = scanSymbol()
        if (symbol != null) return ParseResult.Ok(symbol)

        // Invalid input.
        pos += 1
        spanEnd = pos
        slice = utf8.substringFromByte(spanStart, spanEnd)
        return ParseResult.Err(Unit)
    }

    private fun scanNumericOrFloat(): Token? {
        val start = pos
        val b0 = utf8.byteAt(pos)
        if (b0 == '.'.code) {
            if (pos + 1 < utf8.byteLen && utf8.byteAt(pos + 1) in '0'.code..'9'.code) {
                pos += 1
                while (pos < utf8.byteLen && utf8.byteAt(pos) in '0'.code..'9'.code) pos += 1
                scanExponentIfPresent()
                finishSlice(start)
                val value = slice.toDoubleOrNull() ?: return null
                return Token.FloatToken(value)
            }
            return null
        }

        // RawDecInt / RawHexInt / RawBinInt / RawOctInt are handled by Token::Raw* and converted later.
        if (b0 == '0'.code && pos + 2 < utf8.byteLen) {
            val b1 = utf8.byteAt(pos + 1)
            if (b1 == 'x'.code || b1 == 'X'.code) {
                pos += 2
                val digitStart = pos
                while (pos < utf8.byteLen && isHexByte(utf8.byteAt(pos))) pos += 1
                if (pos == digitStart) return null
                finishSlice(start)
                return Token.RawHexInt
            }
            if (b1 == 'b'.code || b1 == 'B'.code) {
                pos += 2
                val digitStart = pos
                while (pos < utf8.byteLen && (utf8.byteAt(pos) == '0'.code || utf8.byteAt(pos) == '1'.code)) pos += 1
                if (pos == digitStart) return null
                finishSlice(start)
                return Token.RawBinInt
            }
            if (b1 == 'o'.code || b1 == 'O'.code) {
                pos += 2
                val digitStart = pos
                while (pos < utf8.byteLen && utf8.byteAt(pos) in '0'.code..'7'.code) pos += 1
                if (pos == digitStart) return null
                finishSlice(start)
                return Token.RawOctInt
            }
        }

        // Consume digits.
        while (pos < utf8.byteLen && utf8.byteAt(pos) in '0'.code..'9'.code) pos += 1

        // Float patterns.
        val hasDot = pos < utf8.byteLen && utf8.byteAt(pos) == '.'.code
        val hasExp = pos < utf8.byteLen && (utf8.byteAt(pos) == 'e'.code || utf8.byteAt(pos) == 'E'.code)
        if (hasDot || hasExp) {
            if (hasDot) {
                pos += 1
                while (pos < utf8.byteLen && utf8.byteAt(pos) in '0'.code..'9'.code) pos += 1
            }
            scanExponentIfPresent()
            finishSlice(start)
            val value = slice.toDoubleOrNull() ?: return null
            return Token.FloatToken(value)
        }

        finishSlice(start)
        return Token.RawDecInt
    }

    private fun scanExponentIfPresent() {
        if (pos < utf8.byteLen && (utf8.byteAt(pos) == 'e'.code || utf8.byteAt(pos) == 'E'.code)) {
            pos += 1
            if (pos < utf8.byteLen && (utf8.byteAt(pos) == '+'.code || utf8.byteAt(pos) == '-'.code)) pos += 1
            while (pos < utf8.byteLen && utf8.byteAt(pos) in '0'.code..'9'.code) pos += 1
        }
    }

    private fun finishSlice(start: Int) {
        spanEnd = pos
        slice = utf8.substringFromByte(start, spanEnd)
    }

    private fun scanSymbol(): Token? {
        fun match(s: String, token: Token): Token? {
            if (utf8.startsWithAt(pos, s)) {
                pos += s.length
                spanEnd = pos
                slice = s
                return token
            }
            return null
        }

        return match("...", Token.Ellipsis)
            ?: match("//=", Token.SlashSlashEqual)
            ?: match("<<=", Token.LessLessEqual)
            ?: match(">>=", Token.GreaterGreaterEqual)
            ?: match("**", Token.StarStar)
            ?: match("->", Token.MinusGreater)
            ?: match("+=", Token.PlusEqual)
            ?: match("-=", Token.MinusEqual)
            ?: match("*=", Token.StarEqual)
            ?: match("/=", Token.SlashEqual)
            ?: match("//", Token.SlashSlash)
            ?: match("%=", Token.PercentEqual)
            ?: match("==", Token.EqualEqual)
            ?: match("!=", Token.BangEqual)
            ?: match("<=", Token.LessEqual)
            ?: match(">=", Token.GreaterEqual)
            ?: match("&=", Token.AmpersandEqual)
            ?: match("|=", Token.PipeEqual)
            ?: match("^=", Token.CaretEqual)
            ?: match("<<", Token.LessLess)
            ?: match(">>", Token.GreaterGreater)
            ?: match(",", Token.Comma)
            ?: match(";", Token.Semicolon)
            ?: match(":", Token.Colon)
            ?: match("=", Token.Equal)
            ?: match("<", Token.LessThan)
            ?: match(">", Token.GreaterThan)
            ?: match("-", Token.Minus)
            ?: match("+", Token.Plus)
            ?: match("*", Token.Star)
            ?: match("%", Token.Percent)
            ?: match("/", Token.Slash)
            ?: match(".", Token.Dot)
            ?: match("&", Token.Ampersand)
            ?: match("|", Token.Pipe)
            ?: match("^", Token.Caret)
            ?: match("~", Token.Tilde)
            ?: match("[", Token.OpeningSquare)
            ?: match("{", Token.OpeningCurly)
            ?: match("(", Token.OpeningRound)
            ?: match("]", Token.ClosingSquare)
            ?: match("}", Token.ClosingCurly)
            ?: match(")", Token.ClosingRound)
    }

    private fun isHexByte(b: Int): Boolean {
        return b in '0'.code..'9'.code || b in 'a'.code..'f'.code || b in 'A'.code..'F'.code
    }

    private fun isIdentStartByte(b: Int): Boolean {
        return b == '_'.code || b in 'a'.code..'z'.code || b in 'A'.code..'Z'.code
    }

    private fun isIdentContByte(b: Int): Boolean {
        return isIdentStartByte(b) || b in '0'.code..'9'.code
    }
}

private class Utf8Index(private val source: String) {
    private val bytes: ByteArray = source.encodeToByteArray()
    private val byteToChar: IntArray = buildByteToCharMap(source)

    val byteLen: Int
        get() = bytes.size

    fun byteAt(i: Int): Int = bytes[i].toInt() and 0xff

    fun startsWithAt(byteIndex: Int, ascii: String): Boolean {
        if (byteIndex + ascii.length > bytes.size) return false
        for (i in ascii.indices) {
            if (byteAt(byteIndex + i) != ascii[i].code) return false
        }
        return true
    }

    fun substringFromByte(start: Int): String {
        val charStart = byteToChar[start]
        return source.substring(charStart)
    }

    fun substringFromByte(start: Int, end: Int): String {
        val charStart = byteToChar[start]
        val charEnd = byteToChar[end]
        return source.substring(charStart, charEnd)
    }

    private fun buildByteToCharMap(s: String): IntArray {
        val out = IntArray(bytes.size + 1) { -1 }
        var bytePos = 0
        var charPos = 0
        out[0] = 0
        while (charPos < s.length) {
            val codePoint = s.codePointAt(charPos)
            val utf16Len = if (codePoint > 0xffff) 2 else 1
            val utf8Len = utf8Len(codePoint)
            for (i in 0 until utf8Len) {
                out[bytePos + i] = charPos
            }
            bytePos += utf8Len
            charPos += utf16Len
            out[bytePos] = charPos
        }
        out[bytes.size] = s.length
        return out
    }

    private fun String.codePointAt(index: Int): Int {
        val c1 = this[index].code
        if (c1 in 0xd800..0xdbff && index + 1 < this.length) {
            val c2 = this[index + 1].code
            if (c2 in 0xdc00..0xdfff) {
                return 0x1_0000 + ((c1 - 0xd800) shl 10) + (c2 - 0xdc00)
            }
        }
        return c1
    }

    private fun utf8Len(codePoint: Int): Int {
        return when {
            codePoint <= 0x7f -> 1
            codePoint <= 0x7ff -> 2
            codePoint <= 0xffff -> 3
            else -> 4
        }
    }
}

fun lexExactlyOneIdentifier(s: String): String? {
    val lexer = TokenLexer(s)
    val t1 = lexer.nextToken()
    val t2 = lexer.nextToken()
    return if (t1 is ParseResult.Ok && t2 == null) {
        when (val tok = t1.value) {
            is Token.Identifier -> tok.name
            else -> null
        }
    } else {
        null
    }
}
