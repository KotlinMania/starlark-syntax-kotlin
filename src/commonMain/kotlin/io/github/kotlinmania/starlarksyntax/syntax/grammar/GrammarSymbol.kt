// port-lint: source src/syntax/grammar.lalrpop
package io.github.kotlinmania.starlarksyntax.syntax.grammar

import io.github.kotlinmania.starlarksyntax.codemap.Spanned as Spanned
import io.github.kotlinmania.starlarksyntax.syntax.ast.*
import io.github.kotlinmania.starlarksyntax.lexer.Token
import io.github.kotlinmania.starlarksyntax.lexer.TokenFString
import io.github.kotlinmania.starlarksyntax.lexer.TokenInt


/**
 * Three-state value distinguishing the cases of a nested `Option<Option<T>>` from the
 * upstream Rust grammar.
 *
 * Necessary because Kotlin's nullable types collapse: `T??` is the same as `T?`, so the
 * three Rust states `None` / `Some(None)` / `Some(Some(value))` cannot be carried by a
 * plain `T?`. The upstream LALRPOP type analysis assigns those three states to a single
 * variant ([GrammarSymbol.Variant8] in this grammar) and the parse table relies on that
 * variant being distinguishable from a flat `T?` ([GrammarSymbol.Variant7]), so the
 * Kotlin port has to carry them in a wrapper.
 *
 * - [Absent]  — the outer Option matched `None` (Rust: `None`).
 * - [Empty]   — the outer Option matched `Some(None)` (Rust: `Some(None)`).
 * - [Present] — both matched (Rust: `Some(Some(value))`).
 */
internal sealed class NullableOption<out T> {
    object Absent : NullableOption<Nothing>()
    object Empty : NullableOption<Nothing>()
    data class Present<T>(val value: T) : NullableOption<T>()

    /**
     * Mirror of Rust's `option.unwrapOr(null)`. Collapses [Absent] and [Empty] both to
     * `null`, and [Present] to its inner value. Use this at the point in the grammar
     * action body where Rust would have called `unwrap_or(None)`; do not call it
     * earlier, because the Variant carrying the value relies on the three-state
     * distinction up to that point.
     */
    fun unwrapOrNull(): T? = when (this) {
        Absent -> null
        Empty -> null
        is Present -> value
    }
}

internal sealed class GrammarSymbol {
    data class Variant0(val value: Token) : GrammarSymbol()
    data class Variant1(val value: Double) : GrammarSymbol()
    data class Variant2(val value: TokenFString) : GrammarSymbol()
    data class Variant3(val value: String) : GrammarSymbol()
    data class Variant4(val value: TokenInt) : GrammarSymbol()
    data class Variant5(val value: Token?) : GrammarSymbol()
    data class Variant6(val value: List<Token>) : GrammarSymbol()
    data class Variant7(val value: Spanned<ExprP<AstNoPayload>>?) : GrammarSymbol()
    data class Variant8(val value: NullableOption<Spanned<ExprP<AstNoPayload>>>) : GrammarSymbol()
    data class Variant9(val value: Spanned<StmtP<AstNoPayload>>) : GrammarSymbol()
    data class Variant10(val value: List<Spanned<StmtP<AstNoPayload>>>) : GrammarSymbol()
    data class Variant11(val value: Spanned<ArgumentP<AstNoPayload>>) : GrammarSymbol()
    data class Variant12(val value: List<Spanned<ArgumentP<AstNoPayload>>>) : GrammarSymbol()
    data class Variant13(val value: Spanned<ParameterP<AstNoPayload>>) : GrammarSymbol()
    data class Variant14(val value: List<Spanned<ParameterP<AstNoPayload>>>) : GrammarSymbol()
    data class Variant15(val value: Pair<Spanned<ExprP<AstNoPayload>>, Spanned<ExprP<AstNoPayload>>>) : GrammarSymbol()
    data class Variant16(val value: List<Pair<Spanned<ExprP<AstNoPayload>>, Spanned<ExprP<AstNoPayload>>>>) : GrammarSymbol()
    data class Variant17(val value: Spanned<ExprP<AstNoPayload>>) : GrammarSymbol()
    data class Variant18(val value: List<Spanned<ExprP<AstNoPayload>>>) : GrammarSymbol()
    data class Variant19(val value: Pair<Pair<Spanned<AssignIdentP<AstNoPayload>>, Spanned<String>>, Spanned<Comma>>) : GrammarSymbol()
    data class Variant20(val value: List<Pair<Pair<Spanned<AssignIdentP<AstNoPayload>>, Spanned<String>>, Spanned<Comma>>>) : GrammarSymbol()
    data class Variant21(val value: Pair<Spanned<AssignIdentP<AstNoPayload>>, Spanned<String>>) : GrammarSymbol()
    data class Variant22(val value: Pair<Spanned<AssignIdentP<AstNoPayload>>, Spanned<String>>?) : GrammarSymbol()
    data class Variant23(val value: Int) : GrammarSymbol()
    data class Variant24(val value: Spanned<ArgumentP<AstNoPayload>>?) : GrammarSymbol()
    data class Variant25(val value: ArgumentP<AstNoPayload>) : GrammarSymbol()
    data class Variant26(val value: Spanned<AssignIdentP<AstNoPayload>>) : GrammarSymbol()
    data class Variant27(val value: AssignOp?) : GrammarSymbol()
    data class Variant28(val value: StmtP<AstNoPayload>) : GrammarSymbol()
    data class Variant29(val value: List<Spanned<ArgumentP<AstNoPayload>>>) : GrammarSymbol()
    data class Variant30(val value: List<Spanned<ParameterP<AstNoPayload>>>) : GrammarSymbol()
    data class Variant31(val value: List<Pair<Spanned<ExprP<AstNoPayload>>, Spanned<ExprP<AstNoPayload>>>>) : GrammarSymbol()
    data class Variant32(val value: List<Spanned<ExprP<AstNoPayload>>>) : GrammarSymbol()
    data class Variant33(val value: ClauseP<AstNoPayload>) : GrammarSymbol()
    data class Variant34(val value: List<ClauseP<AstNoPayload>>) : GrammarSymbol()
    data class Variant35(val value: Spanned<Comma>) : GrammarSymbol()
    data class Variant36(val value: Pair<ForClauseP<AstNoPayload>, List<ClauseP<AstNoPayload>>>) : GrammarSymbol()
    data class Variant37(val value: Spanned<ParameterP<AstNoPayload>>?) : GrammarSymbol()
    data class Variant38(val value: ParameterP<AstNoPayload>) : GrammarSymbol()
    data class Variant39(val value: ExprP<AstNoPayload>) : GrammarSymbol()
    data class Variant40(val value: Pair<Spanned<ExprP<AstNoPayload>>, Spanned<ExprP<AstNoPayload>>>?) : GrammarSymbol()
    data class Variant41(val value: Spanned<StmtP<AstNoPayload>>?) : GrammarSymbol()
    data class Variant42(val value: ForClauseP<AstNoPayload>) : GrammarSymbol()
    data class Variant43(val value: Spanned<IdentP<AstNoPayload>>) : GrammarSymbol()
    data class Variant44(val value: Spanned<String>) : GrammarSymbol()
    data class Variant45(val value: Spanned<String>?) : GrammarSymbol()
    data class Variant46(val value: Spanned<TypeExprP<AstNoPayload>>?) : GrammarSymbol()
    data class Variant47(val value: Spanned<TypeExprP<AstNoPayload>>) : GrammarSymbol()
    data class Variant48(val value: Spanned<Double>) : GrammarSymbol()
    data class Variant49(val value: Spanned<FStringP<AstNoPayload>>) : GrammarSymbol()
    data class Variant50(val value: Spanned<TokenInt>) : GrammarSymbol()

}

internal fun GrammarSymbol.unexpectedValue(): Nothing = error("unexpected grammar symbol")
internal fun GrammarSymbol.asToken(): Token = when (this) {
    is GrammarSymbol.Variant0 -> value
    else -> unexpectedValue()
}
internal fun Triple<Int, GrammarSymbol, Int>.asTokenTriple(): Triple<Int, GrammarSymbol, Int> = this
internal fun GrammarSymbol.asDoubleValue(): Double = when (this) {
    is GrammarSymbol.Variant1 -> value
    else -> unexpectedValue()
}
internal fun Triple<Int, GrammarSymbol, Int>.asDoubleValueTriple(): Triple<Int, GrammarSymbol, Int> = this
internal fun GrammarSymbol.asTokenFString(): TokenFString = when (this) {
    is GrammarSymbol.Variant2 -> value
    else -> unexpectedValue()
}
internal fun Triple<Int, GrammarSymbol, Int>.asTokenFStringTriple(): Triple<Int, GrammarSymbol, Int> = this
internal fun GrammarSymbol.asStringValue(): String = when (this) {
    is GrammarSymbol.Variant3 -> value
    else -> unexpectedValue()
}
internal fun Triple<Int, GrammarSymbol, Int>.asStringValueTriple(): Triple<Int, GrammarSymbol, Int> = this
internal fun GrammarSymbol.asTokenInt(): TokenInt = when (this) {
    is GrammarSymbol.Variant4 -> value
    else -> unexpectedValue()
}
internal fun Triple<Int, GrammarSymbol, Int>.asTokenIntTriple(): Triple<Int, GrammarSymbol, Int> = this
internal fun GrammarSymbol.asNullableToken(): Token? = when (this) {
    is GrammarSymbol.Variant5 -> value
    else -> unexpectedValue()
}
internal fun Triple<Int, GrammarSymbol, Int>.asNullableTokenTriple(): Triple<Int, GrammarSymbol, Int> = this
internal fun GrammarSymbol.asTokenList(): List<Token> = when (this) {
    is GrammarSymbol.Variant6 -> value
    else -> unexpectedValue()
}
internal fun Triple<Int, GrammarSymbol, Int>.asTokenListTriple(): Triple<Int, GrammarSymbol, Int> = this
internal fun GrammarSymbol.asNullableExprSpanned(): Spanned<ExprP<AstNoPayload>>? = when (this) {
    is GrammarSymbol.Variant7 -> value
    else -> unexpectedValue()
}
internal fun Triple<Int, GrammarSymbol, Int>.asNullableExprSpannedTriple(): Triple<Int, GrammarSymbol, Int> = this
internal fun GrammarSymbol.asNullableOptionExprSpanned(): NullableOption<Spanned<ExprP<AstNoPayload>>> = when (this) {
    is GrammarSymbol.Variant8 -> value
    else -> unexpectedValue()
}
internal fun Triple<Int, GrammarSymbol, Int>.asNullableOptionExprSpannedTriple(): Triple<Int, GrammarSymbol, Int> = this
internal fun GrammarSymbol.asStmtSpanned(): Spanned<StmtP<AstNoPayload>> = when (this) {
    is GrammarSymbol.Variant9 -> value
    else -> unexpectedValue()
}
internal fun Triple<Int, GrammarSymbol, Int>.asStmtSpannedTriple(): Triple<Int, GrammarSymbol, Int> = this
internal fun GrammarSymbol.asStmtSpannedList(): List<Spanned<StmtP<AstNoPayload>>> = when (this) {
    is GrammarSymbol.Variant10 -> value
    else -> unexpectedValue()
}
internal fun Triple<Int, GrammarSymbol, Int>.asStmtSpannedListTriple(): Triple<Int, GrammarSymbol, Int> = this
internal fun GrammarSymbol.asArgumentSpanned(): Spanned<ArgumentP<AstNoPayload>> = when (this) {
    is GrammarSymbol.Variant11 -> value
    else -> unexpectedValue()
}
internal fun Triple<Int, GrammarSymbol, Int>.asArgumentSpannedTriple(): Triple<Int, GrammarSymbol, Int> = this
internal fun GrammarSymbol.asArgumentSpannedList(): List<Spanned<ArgumentP<AstNoPayload>>> = when (this) {
    is GrammarSymbol.Variant12 -> value
    is GrammarSymbol.Variant29 -> value
    else -> unexpectedValue()
}
internal fun Triple<Int, GrammarSymbol, Int>.asArgumentSpannedListTriple(): Triple<Int, GrammarSymbol, Int> = this
internal fun GrammarSymbol.asParameterSpanned(): Spanned<ParameterP<AstNoPayload>> = when (this) {
    is GrammarSymbol.Variant13 -> value
    else -> unexpectedValue()
}
internal fun Triple<Int, GrammarSymbol, Int>.asParameterSpannedTriple(): Triple<Int, GrammarSymbol, Int> = this
internal fun GrammarSymbol.asParameterSpannedList(): List<Spanned<ParameterP<AstNoPayload>>> = when (this) {
    is GrammarSymbol.Variant14 -> value
    is GrammarSymbol.Variant30 -> value
    else -> unexpectedValue()
}
internal fun Triple<Int, GrammarSymbol, Int>.asParameterSpannedListTriple(): Triple<Int, GrammarSymbol, Int> = this
internal fun GrammarSymbol.asExprPair(): Pair<Spanned<ExprP<AstNoPayload>>, Spanned<ExprP<AstNoPayload>>> = when (this) {
    is GrammarSymbol.Variant15 -> value
    else -> unexpectedValue()
}
internal fun Triple<Int, GrammarSymbol, Int>.asExprPairTriple(): Triple<Int, GrammarSymbol, Int> = this
internal fun GrammarSymbol.asExprPairList(): List<Pair<Spanned<ExprP<AstNoPayload>>, Spanned<ExprP<AstNoPayload>>>> = when (this) {
    is GrammarSymbol.Variant16 -> value
    is GrammarSymbol.Variant31 -> value
    else -> unexpectedValue()
}
internal fun Triple<Int, GrammarSymbol, Int>.asExprPairListTriple(): Triple<Int, GrammarSymbol, Int> = this
internal fun GrammarSymbol.asExprSpanned(): Spanned<ExprP<AstNoPayload>> = when (this) {
    is GrammarSymbol.Variant17 -> value
    else -> unexpectedValue()
}
internal fun Triple<Int, GrammarSymbol, Int>.asExprSpannedTriple(): Triple<Int, GrammarSymbol, Int> = this
internal fun GrammarSymbol.asExprSpannedList(): List<Spanned<ExprP<AstNoPayload>>> = when (this) {
    is GrammarSymbol.Variant18 -> value
    is GrammarSymbol.Variant32 -> value
    else -> unexpectedValue()
}
internal fun Triple<Int, GrammarSymbol, Int>.asExprSpannedListTriple(): Triple<Int, GrammarSymbol, Int> = this
internal fun GrammarSymbol.asLoadPair(): Pair<Pair<Spanned<AssignIdentP<AstNoPayload>>, Spanned<String>>, Spanned<Comma>> = when (this) {
    is GrammarSymbol.Variant19 -> value
    else -> unexpectedValue()
}
internal fun Triple<Int, GrammarSymbol, Int>.asLoadPairTriple(): Triple<Int, GrammarSymbol, Int> = this
internal fun GrammarSymbol.asLoadPairList(): List<Pair<Pair<Spanned<AssignIdentP<AstNoPayload>>, Spanned<String>>, Spanned<Comma>>> = when (this) {
    is GrammarSymbol.Variant20 -> value
    else -> unexpectedValue()
}
internal fun Triple<Int, GrammarSymbol, Int>.asLoadPairListTriple(): Triple<Int, GrammarSymbol, Int> = this
internal fun GrammarSymbol.asAssignIdentStringPair(): Pair<Spanned<AssignIdentP<AstNoPayload>>, Spanned<String>> = when (this) {
    is GrammarSymbol.Variant21 -> value
    else -> unexpectedValue()
}
internal fun Triple<Int, GrammarSymbol, Int>.asAssignIdentStringPairTriple(): Triple<Int, GrammarSymbol, Int> = this
internal fun GrammarSymbol.asNullableAssignIdentStringPair(): Pair<Spanned<AssignIdentP<AstNoPayload>>, Spanned<String>>? = when (this) {
    is GrammarSymbol.Variant22 -> value
    else -> unexpectedValue()
}
internal fun Triple<Int, GrammarSymbol, Int>.asNullableAssignIdentStringPairTriple(): Triple<Int, GrammarSymbol, Int> = this
internal fun GrammarSymbol.asIntValue(): Int = when (this) {
    is GrammarSymbol.Variant23 -> value
    else -> unexpectedValue()
}
internal fun Triple<Int, GrammarSymbol, Int>.asIntValueTriple(): Triple<Int, GrammarSymbol, Int> = this
internal fun GrammarSymbol.asNullableArgumentSpanned(): Spanned<ArgumentP<AstNoPayload>>? = when (this) {
    is GrammarSymbol.Variant24 -> value
    else -> unexpectedValue()
}
internal fun Triple<Int, GrammarSymbol, Int>.asNullableArgumentSpannedTriple(): Triple<Int, GrammarSymbol, Int> = this
internal fun GrammarSymbol.asArgumentValue(): ArgumentP<AstNoPayload> = when (this) {
    is GrammarSymbol.Variant25 -> value
    else -> unexpectedValue()
}
internal fun Triple<Int, GrammarSymbol, Int>.asArgumentValueTriple(): Triple<Int, GrammarSymbol, Int> = this
internal fun GrammarSymbol.asAssignIdentSpanned(): Spanned<AssignIdentP<AstNoPayload>> = when (this) {
    is GrammarSymbol.Variant26 -> value
    else -> unexpectedValue()
}
internal fun Triple<Int, GrammarSymbol, Int>.asAssignIdentSpannedTriple(): Triple<Int, GrammarSymbol, Int> = this
internal fun GrammarSymbol.asNullableAssignOp(): AssignOp? = when (this) {
    is GrammarSymbol.Variant27 -> value
    else -> unexpectedValue()
}
internal fun Triple<Int, GrammarSymbol, Int>.asNullableAssignOpTriple(): Triple<Int, GrammarSymbol, Int> = this
internal fun GrammarSymbol.asStmtValue(): StmtP<AstNoPayload> = when (this) {
    is GrammarSymbol.Variant28 -> value
    else -> unexpectedValue()
}
internal fun Triple<Int, GrammarSymbol, Int>.asStmtValueTriple(): Triple<Int, GrammarSymbol, Int> = this
internal fun GrammarSymbol.asClauseValue(): ClauseP<AstNoPayload> = when (this) {
    is GrammarSymbol.Variant33 -> value
    else -> unexpectedValue()
}
internal fun Triple<Int, GrammarSymbol, Int>.asClauseValueTriple(): Triple<Int, GrammarSymbol, Int> = this
internal fun GrammarSymbol.asClauseList(): List<ClauseP<AstNoPayload>> = when (this) {
    is GrammarSymbol.Variant34 -> value
    else -> unexpectedValue()
}
internal fun Triple<Int, GrammarSymbol, Int>.asClauseListTriple(): Triple<Int, GrammarSymbol, Int> = this
internal fun GrammarSymbol.asCommaSpanned(): Spanned<Comma> = when (this) {
    is GrammarSymbol.Variant35 -> value
    else -> unexpectedValue()
}
internal fun Triple<Int, GrammarSymbol, Int>.asCommaSpannedTriple(): Triple<Int, GrammarSymbol, Int> = this
internal fun GrammarSymbol.asForClauseClauseListPair(): Pair<ForClauseP<AstNoPayload>, List<ClauseP<AstNoPayload>>> = when (this) {
    is GrammarSymbol.Variant36 -> value
    else -> unexpectedValue()
}
internal fun Triple<Int, GrammarSymbol, Int>.asForClauseClauseListPairTriple(): Triple<Int, GrammarSymbol, Int> = this
internal fun GrammarSymbol.asNullableParameterSpanned(): Spanned<ParameterP<AstNoPayload>>? = when (this) {
    is GrammarSymbol.Variant37 -> value
    else -> unexpectedValue()
}
internal fun Triple<Int, GrammarSymbol, Int>.asNullableParameterSpannedTriple(): Triple<Int, GrammarSymbol, Int> = this
internal fun GrammarSymbol.asParameterValue(): ParameterP<AstNoPayload> = when (this) {
    is GrammarSymbol.Variant38 -> value
    else -> unexpectedValue()
}
internal fun Triple<Int, GrammarSymbol, Int>.asParameterValueTriple(): Triple<Int, GrammarSymbol, Int> = this
internal fun GrammarSymbol.asExprValue(): ExprP<AstNoPayload> = when (this) {
    is GrammarSymbol.Variant39 -> value
    else -> unexpectedValue()
}
internal fun Triple<Int, GrammarSymbol, Int>.asExprValueTriple(): Triple<Int, GrammarSymbol, Int> = this
internal fun GrammarSymbol.asNullableExprPair(): Pair<Spanned<ExprP<AstNoPayload>>, Spanned<ExprP<AstNoPayload>>>? = when (this) {
    is GrammarSymbol.Variant40 -> value
    else -> unexpectedValue()
}
internal fun Triple<Int, GrammarSymbol, Int>.asNullableExprPairTriple(): Triple<Int, GrammarSymbol, Int> = this
internal fun GrammarSymbol.asNullableStmtSpanned(): Spanned<StmtP<AstNoPayload>>? = when (this) {
    is GrammarSymbol.Variant41 -> value
    else -> unexpectedValue()
}
internal fun Triple<Int, GrammarSymbol, Int>.asNullableStmtSpannedTriple(): Triple<Int, GrammarSymbol, Int> = this
internal fun GrammarSymbol.asForClauseValue(): ForClauseP<AstNoPayload> = when (this) {
    is GrammarSymbol.Variant42 -> value
    else -> unexpectedValue()
}
internal fun Triple<Int, GrammarSymbol, Int>.asForClauseValueTriple(): Triple<Int, GrammarSymbol, Int> = this
internal fun GrammarSymbol.asIdentSpanned(): Spanned<IdentP<AstNoPayload>> = when (this) {
    is GrammarSymbol.Variant43 -> value
    else -> unexpectedValue()
}
internal fun Triple<Int, GrammarSymbol, Int>.asIdentSpannedTriple(): Triple<Int, GrammarSymbol, Int> = this
internal fun GrammarSymbol.asStringSpanned(): Spanned<String> = when (this) {
    is GrammarSymbol.Variant44 -> value
    else -> unexpectedValue()
}
internal fun Triple<Int, GrammarSymbol, Int>.asStringSpannedTriple(): Triple<Int, GrammarSymbol, Int> = this
internal fun GrammarSymbol.asNullableStringSpanned(): Spanned<String>? = when (this) {
    is GrammarSymbol.Variant45 -> value
    else -> unexpectedValue()
}
internal fun Triple<Int, GrammarSymbol, Int>.asNullableStringSpannedTriple(): Triple<Int, GrammarSymbol, Int> = this
internal fun GrammarSymbol.asNullableTypeExprSpanned(): Spanned<TypeExprP<AstNoPayload>>? = when (this) {
    is GrammarSymbol.Variant46 -> value
    else -> unexpectedValue()
}
internal fun Triple<Int, GrammarSymbol, Int>.asNullableTypeExprSpannedTriple(): Triple<Int, GrammarSymbol, Int> = this
internal fun GrammarSymbol.asTypeExprSpanned(): Spanned<TypeExprP<AstNoPayload>> = when (this) {
    is GrammarSymbol.Variant47 -> value
    else -> unexpectedValue()
}
internal fun Triple<Int, GrammarSymbol, Int>.asTypeExprSpannedTriple(): Triple<Int, GrammarSymbol, Int> = this
internal fun GrammarSymbol.asDoubleSpanned(): Spanned<Double> = when (this) {
    is GrammarSymbol.Variant48 -> value
    else -> unexpectedValue()
}
internal fun Triple<Int, GrammarSymbol, Int>.asDoubleSpannedTriple(): Triple<Int, GrammarSymbol, Int> = this
internal fun GrammarSymbol.asFStringSpanned(): Spanned<FStringP<AstNoPayload>> = when (this) {
    is GrammarSymbol.Variant49 -> value
    else -> unexpectedValue()
}
internal fun Triple<Int, GrammarSymbol, Int>.asFStringSpannedTriple(): Triple<Int, GrammarSymbol, Int> = this
internal fun GrammarSymbol.asTokenIntSpanned(): Spanned<TokenInt> = when (this) {
    is GrammarSymbol.Variant50 -> value
    else -> unexpectedValue()
}
internal fun Triple<Int, GrammarSymbol, Int>.asTokenIntSpannedTriple(): Triple<Int, GrammarSymbol, Int> = this

internal fun Token.toSymbol(): GrammarSymbol = when (this) {
    is Token.FloatToken -> GrammarSymbol.Variant1(value)
    is Token.FStringToken -> GrammarSymbol.Variant2(value)
    is Token.Identifier -> GrammarSymbol.Variant3(name)
    is Token.IntToken -> GrammarSymbol.Variant4(value)
    is Token.StringToken -> GrammarSymbol.Variant3(value)
    else -> GrammarSymbol.Variant0(this)
}
