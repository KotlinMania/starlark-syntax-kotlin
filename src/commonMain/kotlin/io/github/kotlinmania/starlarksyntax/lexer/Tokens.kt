// port-lint: source src/lexer.rs
package io.github.kotlinmania.starlarksyntax.lexer

/*
 * Copyright 2018 The Starlark in Rust Authors.
 * Copyright (c) Facebook, Inc. and its affiliates.
 * Copyright (c) 2025 Sydney Renee, The Solace Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not import this file except in compliance with the License.
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

import com.ionspin.kotlin.bignum.integer.BigInteger

sealed class TokenInt {
    data class I32(val value: Int) : TokenInt()
    /** Only if larger than `i32`. */
    data class BigInt(val value: BigInteger) : TokenInt()

    override fun toString(): String = when (this) {
        is I32 -> value.toString()
        is BigInt -> value.toString()
    }

    companion object {
        fun fromStrRadix(s: String, base: Int): TokenInt {
            val i = s.toIntOrNull(base)
            if (i != null) return I32(i)
            return BigInt(BigInteger.parseString(s, base))
        }
    }
}

data class TokenFString(
    /** The content of this TokenFString. */
    val content: String,
    /** Relative to the token, where does the actual string content start? */
    val contentStartOffset: Int
)

// Token indices 0-65 must match the LALRPOP-generated __token_to_integer mapping exactly.
// This ordering is used by GrammarState.ACTION[state * 66 + integer].
// See tmp/target/debug/build/starlarkSyntax-.../out/syntax/grammar.rs __token_to_integer.
sealed class Token {
    // LALRPOP indices: Newline=0, symbols 1-34, Dedent=35, literals 36-41,
    //                  brackets 42-43, operators 44-45, keywords 46-60,
    //                  OpeningCurly=61, Pipe=62, PipeEqual=63, ClosingCurly=64, Tilde=65
    object Newline : Token()                               // 0
    object BangEqual : Token()                             // 1
    object Percent : Token()                               // 2
    object PercentEqual : Token()                          // 3
    object Ampersand : Token()                             // 4
    object AmpersandEqual : Token()                        // 5
    object OpeningRound : Token()                          // 6
    object ClosingRound : Token()                          // 7
    object Star : Token()                                  // 8
    object StarStar : Token()                              // 9
    object StarEqual : Token()                             // 10
    object Plus : Token()                                  // 11
    object PlusEqual : Token()                             // 12
    object Comma : Token()                                 // 13
    object Minus : Token()                                 // 14
    object MinusEqual : Token()                            // 15
    object MinusGreater : Token()                          // 16
    object Dot : Token()                                   // 17
    object Ellipsis : Token()                              // 18
    object Slash : Token()                                 // 19
    object SlashSlash : Token()                            // 20
    object SlashSlashEqual : Token()                       // 21
    object SlashEqual : Token()                            // 22
    object Colon : Token()                                 // 23
    object Semicolon : Token()                             // 24
    object LessThan : Token()                              // 25
    object LessLess : Token()                              // 26
    object LessLessEqual : Token()                         // 27
    object LessEqual : Token()                             // 28
    object Equal : Token()                                 // 29
    object EqualEqual : Token()                            // 30
    object GreaterThan : Token()                           // 31
    object GreaterEqual : Token()                          // 32
    object GreaterGreater : Token()                        // 33
    object GreaterGreaterEqual : Token()                   // 34
    object Dedent : Token()                                // 35
    data class FloatToken(val value: Double) : Token()     // 36
    data class FStringToken(val value: TokenFString) : Token() // 37
    data class Identifier(val name: String) : Token()      // 38
    object Indent : Token()                                // 39
    data class IntToken(val value: TokenInt) : Token()     // 40
    data class StringToken(val value: String) : Token()    // 41
    object OpeningSquare : Token()                         // 42
    object ClosingSquare : Token()                         // 43
    object Caret : Token()                                 // 44
    object CaretEqual : Token()                            // 45
    object And : Token()                                   // 46
    object Break : Token()                                 // 47
    object Continue : Token()                              // 48
    object Def : Token()                                   // 49
    object Elif : Token()                                  // 50
    object Else : Token()                                  // 51
    object For : Token()                                   // 52
    object If : Token()                                    // 53
    object In : Token()                                    // 54
    object Lambda : Token()                                // 55
    object Load : Token()                                  // 56
    object Not : Token()                                   // 57
    object Or : Token()                                    // 58
    object Pass : Token()                                  // 59
    object Return : Token()                                // 60
    object OpeningCurly : Token()                          // 61
    object Pipe : Token()                                  // 62
    object PipeEqual : Token()                             // 63
    object ClosingCurly : Token()                          // 64
    object Tilde : Token()                                 // 65

    // Non-grammar tokens (not in the LR tables, consumed by lexer internally)
    data class Comment(val text: String) : Token()
    object Reserved : Token()
    object Tabs : Token()
    object RawSingleQuote : Token()
    object RawDoubleQuote : Token()
    object RawFStringSingleQuote : Token()
    object RawFStringDoubleQuote : Token()
    object RawDecInt : Token()
    object RawHexInt : Token()
    object RawOctInt : Token()
    object RawBinInt : Token()

    /** Convert this token to the integer index used by the LR parser tables.
     *  These indices MUST match the LALRPOP-generated __token_to_integer mapping
     *  in grammar.rs exactly. */
    fun toInteger(): Int = when (this) {
        is Newline -> 0
        is BangEqual -> 1
        is Percent -> 2
        is PercentEqual -> 3
        is Ampersand -> 4
        is AmpersandEqual -> 5
        is OpeningRound -> 6
        is ClosingRound -> 7
        is Star -> 8
        is StarStar -> 9
        is StarEqual -> 10
        is Plus -> 11
        is PlusEqual -> 12
        is Comma -> 13
        is Minus -> 14
        is MinusEqual -> 15
        is MinusGreater -> 16
        is Dot -> 17
        is Ellipsis -> 18
        is Slash -> 19
        is SlashSlash -> 20
        is SlashSlashEqual -> 21
        is SlashEqual -> 22
        is Colon -> 23
        is Semicolon -> 24
        is LessThan -> 25
        is LessLess -> 26
        is LessLessEqual -> 27
        is LessEqual -> 28
        is Equal -> 29
        is EqualEqual -> 30
        is GreaterThan -> 31
        is GreaterEqual -> 32
        is GreaterGreater -> 33
        is GreaterGreaterEqual -> 34
        is Dedent -> 35
        is FloatToken -> 36
        is FStringToken -> 37
        is Identifier -> 38
        is Indent -> 39
        is IntToken -> 40
        is StringToken -> 41
        is OpeningSquare -> 42
        is ClosingSquare -> 43
        is Caret -> 44
        is CaretEqual -> 45
        is And -> 46
        is Break -> 47
        is Continue -> 48
        is Def -> 49
        is Elif -> 50
        is Else -> 51
        is For -> 52
        is If -> 53
        is In -> 54
        is Lambda -> 55
        is Load -> 56
        is Not -> 57
        is Or -> 58
        is Pass -> 59
        is Return -> 60
        is OpeningCurly -> 61
        is Pipe -> 62
        is PipeEqual -> 63
        is ClosingCurly -> 64
        is Tilde -> 65
        is Comment -> error("Comment tokens should not reach the parser")
        is Reserved -> error("Reserved tokens should not reach the parser")
        is Tabs -> error("Tabs tokens should not reach the parser")
        is RawSingleQuote -> error("Raw quote tokens should not reach the parser")
        is RawDoubleQuote -> error("Raw quote tokens should not reach the parser")
        is RawFStringSingleQuote -> error("Raw quote tokens should not reach the parser")
        is RawFStringDoubleQuote -> error("Raw quote tokens should not reach the parser")
        is RawDecInt -> error("Raw numeric tokens should not reach the parser")
        is RawHexInt -> error("Raw numeric tokens should not reach the parser")
        is RawOctInt -> error("Raw numeric tokens should not reach the parser")
        is RawBinInt -> error("Raw numeric tokens should not reach the parser")
    }


    override fun toString(): String = when (this) {
        is Indent -> "new indentation block"
        is Dedent -> "end of indentation block"
        is Newline -> "new line"
        is And -> "keyword 'and'"
        is Else -> "keyword 'else'"
        is Load -> "keyword 'load'"
        is Break -> "keyword 'break'"
        is For -> "keyword 'for'"
        is Not -> "keyword 'not'"
        is Continue -> "keyword 'continue'"
        is If -> "keyword 'if'"
        is Or -> "keyword 'or'"
        is Def -> "keyword 'def'"
        is In -> "keyword 'in'"
        is Pass -> "keyword 'pass'"
        is Elif -> "keyword 'elif'"
        is Return -> "keyword 'return'"
        is Lambda -> "keyword 'lambda'"
        is Comma -> "symbol ','"
        is Semicolon -> "symbol ';'"
        is Colon -> "symbol ':'"
        is PlusEqual -> "symbol '+='"
        is MinusEqual -> "symbol '-='"
        is StarEqual -> "symbol '*='"
        is SlashEqual -> "symbol '/='"
        is SlashSlashEqual -> "symbol '//='"
        is PercentEqual -> "symbol '%='"
        is EqualEqual -> "symbol '=='"
        is BangEqual -> "symbol '!='"
        is LessEqual -> "symbol '<='"
        is GreaterEqual -> "symbol '>='"
        is StarStar -> "symbol '**'"
        is MinusGreater -> "symbol '->'"
        is Equal -> "symbol '='"
        is LessThan -> "symbol '<'"
        is GreaterThan -> "symbol '>'"
        is Minus -> "symbol '-'"
        is Plus -> "symbol '+'"
        is Star -> "symbol '*'"
        is Percent -> "symbol '%'"
        is Slash -> "symbol '/'"
        is SlashSlash -> "symbol '//'"
        is Dot -> "symbol '.'"
        is Ampersand -> "symbol '&'"
        is Pipe -> "symbol '|'"
        is Caret -> "symbol '^'"
        is LessLess -> "symbol '<<'"
        is GreaterGreater -> "symbol '>>'"
        is Tilde -> "symbol '~'"
        is AmpersandEqual -> "symbol '&='"
        is PipeEqual -> "symbol '|='"
        is CaretEqual -> "symbol '^='"
        is LessLessEqual -> "symbol '<<='"
        is GreaterGreaterEqual -> "symbol '>>='"
        is Ellipsis -> "symbol '...'"
        is OpeningSquare -> "symbol '['"
        is OpeningCurly -> "symbol '{'"
        is OpeningRound -> "symbol '('"
        is ClosingSquare -> "symbol ']'"
        is ClosingCurly -> "symbol '}'"
        is ClosingRound -> "symbol ')'"
        is Reserved -> "reserved keyword"
        is Identifier -> "identifier '$name'"
        is IntToken -> "integer literal '$value'"
        is FloatToken -> "float literal '$value'"
        is StringToken -> "string literal \"$value\""
        is RawSingleQuote -> "starting '"
        is RawDoubleQuote -> "starting \""
        is RawFStringDoubleQuote -> "starting f'"
        is RawFStringSingleQuote -> "starting f\""
        is RawDecInt -> "decimal integer literal"
        is RawHexInt -> "hexadecimal integer literal"
        is RawOctInt -> "octal integer literal"
        is RawBinInt -> "binary integer literal"
        is FStringToken -> "f-string \"${value.content}\""
        is Comment -> "comment '$text'"
        is Tabs -> ""
    }
}

/** The set of reserved keywords that cannot be used as identifiers. */
val RESERVED_KEYWORDS = setOf(
    "as", "assert", "async", "await", "class", "del", "except",
    "finally", "from", "global", "import", "is", "nonlocal",
    "raise", "try", "while", "with", "yield"
)

/** Map keyword strings to their Token variants. */
fun keywordToken(s: String): Token? = when (s) {
    "and" -> Token.And
    "break" -> Token.Break
    "continue" -> Token.Continue
    "def" -> Token.Def
    "elif" -> Token.Elif
    "else" -> Token.Else
    "for" -> Token.For
    "if" -> Token.If
    "in" -> Token.In
    "lambda" -> Token.Lambda
    "load" -> Token.Load
    "not" -> Token.Not
    "or" -> Token.Or
    "pass" -> Token.Pass
    "return" -> Token.Return
    else -> null
}

class TokenString
