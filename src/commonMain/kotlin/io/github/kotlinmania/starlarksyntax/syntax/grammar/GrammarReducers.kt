// port-lint: source src/syntax/grammar.lalrpop
package io.github.kotlinmania.starlarksyntax.syntax.grammar

import io.github.kotlinmania.starlarksyntax.syntax.state.ParserState
import io.github.kotlinmania.starlarksyntax.syntax.ast.*
import io.github.kotlinmania.starlarksyntax.lexer.*
import io.github.kotlinmania.starlarksyntax.codemap.Pos as Pos
import io.github.kotlinmania.starlarksyntax.codemap.Span as Span
import io.github.kotlinmania.starlarksyntax.codemap.Spanned as Spanned
import io.github.kotlinmania.starlarksyntax.syntax.parser.*

internal fun <T> T.ast(begin: Int, __end: Int): Spanned<T> = Spanned(this, Span(Pos(begin), Pos(__end)))

private fun MutableList<Triple<Int, GrammarSymbol, Int>>.popSymbol(): Triple<Int, GrammarSymbol, Int> = removeLast()

internal object GrammarReducers {
    fun reduce(ruleId: Int, symbols: MutableList<Triple<Int, GrammarSymbol, Int>>, state: ParserState, lookaheadStart: Int?): Pair<Int, Int> {
        when (ruleId) {
        0 -> {
            // ","? = "," => ActionFn(215);
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym0.third;
            val __nt = __action215(state, __sym0);
            symbols.add(Triple(__start, GrammarSymbol.Variant5(__nt), __end))
            return 1 to 0
        }
        1 -> {
            // ","? =  => ActionFn(216);
            val __start = lookaheadStart ?: symbols.lastOrNull()?.third ?: 0;
            val __end = __start;
            val __nt = __action216(state, __start, __end);
            symbols.add(Triple(__start, GrammarSymbol.Variant5(__nt), __end))
            return 0 to 0
        }
        2 -> {
            // ";"? = ";" => ActionFn(205);
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym0.third;
            val __nt = __action205(state, __sym0);
            symbols.add(Triple(__start, GrammarSymbol.Variant5(__nt), __end))
            return 1 to 1
        }
        3 -> {
            // ";"? =  => ActionFn(206);
            val __start = lookaheadStart ?: symbols.lastOrNull()?.third ?: 0;
            val __end = __start;
            val __nt = __action206(state, __start, __end);
            symbols.add(Triple(__start, GrammarSymbol.Variant5(__nt), __end))
            return 0 to 1
        }
        4 -> {
            // "\n"* =  => ActionFn(196);
            val __start = lookaheadStart ?: symbols.lastOrNull()?.third ?: 0;
            val __end = __start;
            val __nt = __action196(state, __start, __end);
            symbols.add(Triple(__start, GrammarSymbol.Variant6(__nt), __end))
            return 0 to 2
        }
        5 -> {
            // "\n"* = "\n"+ => ActionFn(197);
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym0.third;
            val __nt = __action197(state, __sym0);
            symbols.add(Triple(__start, GrammarSymbol.Variant6(__nt), __end))
            return 1 to 2
        }
        6 -> {
            // "\n"+ = "\n" => ActionFn(186);
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym0.third;
            val __nt = __action186(state, __sym0);
            symbols.add(Triple(__start, GrammarSymbol.Variant6(__nt), __end))
            return 1 to 3
        }
        7 -> {
            // "\n"+ = "\n"+, "\n" => ActionFn(187);
            val __sym1 = symbols.popSymbol()
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym1.third;
            val __nt = __action187(state, __sym0, __sym1);
            symbols.add(Triple(__start, GrammarSymbol.Variant6(__nt), __end))
            return 2 to 3
        }
        8 -> {
            // (":" <Test?>) = ":", Test => ActionFn(263);
            val __sym1 = symbols.popSymbol()
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym1.third;
            val __nt = __action263(state, __sym0, __sym1);
            symbols.add(Triple(__start, GrammarSymbol.Variant7(__nt), __end))
            return 2 to 4
        }
        9 -> {
            // (":" <Test?>) = ":" => ActionFn(264);
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym0.third;
            val __nt = __action264(state, __sym0);
            symbols.add(Triple(__start, GrammarSymbol.Variant7(__nt), __end))
            return 1 to 4
        }
        10 -> {
            // (":" <Test?>)? = ":", Test => ActionFn(271);
            // Variant8's payload is NullableOption<Spanned<ExprP<...>>> (the Rust type is
            // Option<Option<AstExpr>>). __action271 is statically typed to return that
            // wrapper, so this push needs no cast.
            val __sym1 = symbols.popSymbol()
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym1.third;
            val __nt = __action271(state, __sym0, __sym1);
            symbols.add(Triple(__start, GrammarSymbol.Variant8(__nt), __end))
            return 2 to 5
        }
        11 -> {
            // (":" <Test?>)? = ":" => ActionFn(272);
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym0.third;
            val __nt = __action272(state, __sym0);
            symbols.add(Triple(__start, GrammarSymbol.Variant8(__nt), __end))
            return 1 to 5
        }
        12 -> {
            // (":" <Test?>)? =  => ActionFn(159);
            val __start = lookaheadStart ?: symbols.lastOrNull()?.third ?: 0;
            val __end = __start;
            val __nt = __action159(state, __start, __end);
            symbols.add(Triple(__start, GrammarSymbol.Variant8(__nt), __end))
            return 0 to 5
        }
        13 -> {
            // (";" <SmallStmt>) = ";", SmallStmt => ActionFn(209);
            val __sym1 = symbols.popSymbol()
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym1.third;
            val __nt = __action209(state, __sym0, __sym1);
            symbols.add(Triple(__start, GrammarSymbol.Variant9(__nt), __end))
            return 2 to 6
        }
        14 -> {
            // (";" <SmallStmt>)* =  => ActionFn(207);
            val __start = lookaheadStart ?: symbols.lastOrNull()?.third ?: 0;
            val __end = __start;
            val __nt = __action207(state, __start, __end);
            symbols.add(Triple(__start, GrammarSymbol.Variant10(__nt), __end))
            return 0 to 7
        }
        15 -> {
            // (";" <SmallStmt>)* = (";" <SmallStmt>)+ => ActionFn(208);
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym0.third;
            val __nt = __action208(state, __sym0);
            symbols.add(Triple(__start, GrammarSymbol.Variant10(__nt), __end))
            return 1 to 7
        }
        16 -> {
            // (";" <SmallStmt>)+ = ";", SmallStmt => ActionFn(285);
            val __sym1 = symbols.popSymbol()
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym1.third;
            val __nt = __action285(state, __sym0, __sym1);
            symbols.add(Triple(__start, GrammarSymbol.Variant10(__nt), __end))
            return 2 to 8
        }
        17 -> {
            // (";" <SmallStmt>)+ = (";" <SmallStmt>)+, ";", SmallStmt => ActionFn(286);
            val __sym2 = symbols.popSymbol()
            val __sym1 = symbols.popSymbol()
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym2.third;
            val __nt = __action286(state, __sym0, __sym1, __sym2);
            symbols.add(Triple(__start, GrammarSymbol.Variant10(__nt), __end))
            return 3 to 8
        }
        18 -> {
            // (<ArgumentP<AstNoPayload>> ",") = ArgumentP<AstNoPayload>, "," => ActionFn(221);
            val __sym1 = symbols.popSymbol()
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym1.third;
            val __nt = __action221(state, __sym0, __sym1);
            symbols.add(Triple(__start, GrammarSymbol.Variant11(__nt), __end))
            return 2 to 9
        }
        19 -> {
            // (<ArgumentP<AstNoPayload>> ",")* =  => ActionFn(219);
            val __start = lookaheadStart ?: symbols.lastOrNull()?.third ?: 0;
            val __end = __start;
            val __nt = __action219(state, __start, __end);
            symbols.add(Triple(__start, GrammarSymbol.Variant12(__nt), __end))
            return 0 to 10
        }
        20 -> {
            // (<ArgumentP<AstNoPayload>> ",")* = (<ArgumentP<AstNoPayload>> ",")+ => ActionFn(220);
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym0.third;
            val __nt = __action220(state, __sym0);
            symbols.add(Triple(__start, GrammarSymbol.Variant12(__nt), __end))
            return 1 to 10
        }
        21 -> {
            // (<ArgumentP<AstNoPayload>> ",")+ = ArgumentP<AstNoPayload>, "," => ActionFn(291);
            val __sym1 = symbols.popSymbol()
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym1.third;
            val __nt = __action291(state, __sym0, __sym1);
            symbols.add(Triple(__start, GrammarSymbol.Variant12(__nt), __end))
            return 2 to 11
        }
        22 -> {
            // (<ArgumentP<AstNoPayload>> ",")+ = (<ArgumentP<AstNoPayload>> ",")+, ArgumentP<AstNoPayload>, "," => ActionFn(292);
            val __sym2 = symbols.popSymbol()
            val __sym1 = symbols.popSymbol()
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym2.third;
            val __nt = __action292(state, __sym0, __sym1, __sym2);
            symbols.add(Triple(__start, GrammarSymbol.Variant12(__nt), __end))
            return 3 to 11
        }
        23 -> {
            // (<DefParameter> ",") = DefParameter, "," => ActionFn(204);
            val __sym1 = symbols.popSymbol()
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym1.third;
            val __nt = __action204(state, __sym0, __sym1);
            symbols.add(Triple(__start, GrammarSymbol.Variant13(__nt), __end))
            return 2 to 12
        }
        24 -> {
            // (<DefParameter> ",")* =  => ActionFn(202);
            val __start = lookaheadStart ?: symbols.lastOrNull()?.third ?: 0;
            val __end = __start;
            val __nt = __action202(state, __start, __end);
            symbols.add(Triple(__start, GrammarSymbol.Variant14(__nt), __end))
            return 0 to 13
        }
        25 -> {
            // (<DefParameter> ",")* = (<DefParameter> ",")+ => ActionFn(203);
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym0.third;
            val __nt = __action203(state, __sym0);
            symbols.add(Triple(__start, GrammarSymbol.Variant14(__nt), __end))
            return 1 to 13
        }
        26 -> {
            // (<DefParameter> ",")+ = DefParameter, "," => ActionFn(295);
            val __sym1 = symbols.popSymbol()
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym1.third;
            val __nt = __action295(state, __sym0, __sym1);
            symbols.add(Triple(__start, GrammarSymbol.Variant14(__nt), __end))
            return 2 to 14
        }
        27 -> {
            // (<DefParameter> ",")+ = (<DefParameter> ",")+, DefParameter, "," => ActionFn(296);
            val __sym2 = symbols.popSymbol()
            val __sym1 = symbols.popSymbol()
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym2.third;
            val __nt = __action296(state, __sym0, __sym1, __sym2);
            symbols.add(Triple(__start, GrammarSymbol.Variant14(__nt), __end))
            return 3 to 14
        }
        28 -> {
            // (<DictEntry> ",") = DictEntry, "," => ActionFn(229);
            val __sym1 = symbols.popSymbol()
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym1.third;
            val __nt = __action229(state, __sym0, __sym1);
            symbols.add(Triple(__start, GrammarSymbol.Variant15(__nt), __end))
            return 2 to 15
        }
        29 -> {
            // (<DictEntry> ",")* =  => ActionFn(227);
            val __start = lookaheadStart ?: symbols.lastOrNull()?.third ?: 0;
            val __end = __start;
            val __nt = __action227(state, __start, __end);
            symbols.add(Triple(__start, GrammarSymbol.Variant16(__nt), __end))
            return 0 to 16
        }
        30 -> {
            // (<DictEntry> ",")* = (<DictEntry> ",")+ => ActionFn(228);
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym0.third;
            val __nt = __action228(state, __sym0);
            symbols.add(Triple(__start, GrammarSymbol.Variant16(__nt), __end))
            return 1 to 16
        }
        31 -> {
            // (<DictEntry> ",")+ = DictEntry, "," => ActionFn(299);
            val __sym1 = symbols.popSymbol()
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym1.third;
            val __nt = __action299(state, __sym0, __sym1);
            symbols.add(Triple(__start, GrammarSymbol.Variant16(__nt), __end))
            return 2 to 17
        }
        32 -> {
            // (<DictEntry> ",")+ = (<DictEntry> ",")+, DictEntry, "," => ActionFn(300);
            val __sym2 = symbols.popSymbol()
            val __sym1 = symbols.popSymbol()
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym2.third;
            val __nt = __action300(state, __sym0, __sym1, __sym2);
            symbols.add(Triple(__start, GrammarSymbol.Variant16(__nt), __end))
            return 3 to 17
        }
        33 -> {
            // (<ExprP<AstNoPayload>> ",") = ExprP<AstNoPayload>, "," => ActionFn(214);
            val __sym1 = symbols.popSymbol()
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym1.third;
            val __nt = __action214(state, __sym0, __sym1);
            symbols.add(Triple(__start, GrammarSymbol.Variant17(__nt), __end))
            return 2 to 18
        }
        34 -> {
            // (<ExprP<AstNoPayload>> ",")* =  => ActionFn(212);
            val __start = lookaheadStart ?: symbols.lastOrNull()?.third ?: 0;
            val __end = __start;
            val __nt = __action212(state, __start, __end);
            symbols.add(Triple(__start, GrammarSymbol.Variant18(__nt), __end))
            return 0 to 19
        }
        35 -> {
            // (<ExprP<AstNoPayload>> ",")* = (<ExprP<AstNoPayload>> ",")+ => ActionFn(213);
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym0.third;
            val __nt = __action213(state, __sym0);
            symbols.add(Triple(__start, GrammarSymbol.Variant18(__nt), __end))
            return 1 to 19
        }
        36 -> {
            // (<ExprP<AstNoPayload>> ",")+ = ExprP<AstNoPayload>, "," => ActionFn(303);
            val __sym1 = symbols.popSymbol()
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym1.third;
            val __nt = __action303(state, __sym0, __sym1);
            symbols.add(Triple(__start, GrammarSymbol.Variant18(__nt), __end))
            return 2 to 20
        }
        37 -> {
            // (<ExprP<AstNoPayload>> ",")+ = (<ExprP<AstNoPayload>> ",")+, ExprP<AstNoPayload>, "," => ActionFn(304);
            val __sym2 = symbols.popSymbol()
            val __sym1 = symbols.popSymbol()
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym2.third;
            val __nt = __action304(state, __sym0, __sym1, __sym2);
            symbols.add(Triple(__start, GrammarSymbol.Variant18(__nt), __end))
            return 3 to 20
        }
        38 -> {
            // (<LambdaParameter> ",") = LambdaParameter, "," => ActionFn(236);
            val __sym1 = symbols.popSymbol()
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym1.third;
            val __nt = __action236(state, __sym0, __sym1);
            symbols.add(Triple(__start, GrammarSymbol.Variant13(__nt), __end))
            return 2 to 21
        }
        39 -> {
            // (<LambdaParameter> ",")* =  => ActionFn(234);
            val __start = lookaheadStart ?: symbols.lastOrNull()?.third ?: 0;
            val __end = __start;
            val __nt = __action234(state, __start, __end);
            symbols.add(Triple(__start, GrammarSymbol.Variant14(__nt), __end))
            return 0 to 22
        }
        40 -> {
            // (<LambdaParameter> ",")* = (<LambdaParameter> ",")+ => ActionFn(235);
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym0.third;
            val __nt = __action235(state, __sym0);
            symbols.add(Triple(__start, GrammarSymbol.Variant14(__nt), __end))
            return 1 to 22
        }
        41 -> {
            // (<LambdaParameter> ",")+ = LambdaParameter, "," => ActionFn(309);
            val __sym1 = symbols.popSymbol()
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym1.third;
            val __nt = __action309(state, __sym0, __sym1);
            symbols.add(Triple(__start, GrammarSymbol.Variant14(__nt), __end))
            return 2 to 23
        }
        42 -> {
            // (<LambdaParameter> ",")+ = (<LambdaParameter> ",")+, LambdaParameter, "," => ActionFn(310);
            val __sym2 = symbols.popSymbol()
            val __sym1 = symbols.popSymbol()
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym2.third;
            val __nt = __action310(state, __sym0, __sym1, __sym2);
            symbols.add(Triple(__start, GrammarSymbol.Variant14(__nt), __end))
            return 3 to 23
        }
        43 -> {
            // (<LoadStmtSyms> <Comma>) = LoadStmtSyms, Comma => ActionFn(173);
            val __sym1 = symbols.popSymbol()
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym1.third;
            val __nt = __action173(state, __sym0, __sym1);
            symbols.add(Triple(__start, GrammarSymbol.Variant19(__nt), __end))
            return 2 to 24
        }
        44 -> {
            // (<LoadStmtSyms> <Comma>)* =  => ActionFn(171);
            val __start = lookaheadStart ?: symbols.lastOrNull()?.third ?: 0;
            val __end = __start;
            val __nt = __action171(state, __start, __end);
            symbols.add(Triple(__start, GrammarSymbol.Variant20(__nt), __end))
            return 0 to 25
        }
        45 -> {
            // (<LoadStmtSyms> <Comma>)* = (<LoadStmtSyms> <Comma>)+ => ActionFn(172);
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym0.third;
            val __nt = __action172(state, __sym0);
            symbols.add(Triple(__start, GrammarSymbol.Variant20(__nt), __end))
            return 1 to 25
        }
        46 -> {
            // (<LoadStmtSyms> <Comma>)+ = LoadStmtSyms, Comma => ActionFn(313);
            val __sym1 = symbols.popSymbol()
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym1.third;
            val __nt = __action313(state, __sym0, __sym1);
            symbols.add(Triple(__start, GrammarSymbol.Variant20(__nt), __end))
            return 2 to 26
        }
        47 -> {
            // (<LoadStmtSyms> <Comma>)+ = (<LoadStmtSyms> <Comma>)+, LoadStmtSyms, Comma => ActionFn(314);
            val __sym2 = symbols.popSymbol()
            val __sym1 = symbols.popSymbol()
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym2.third;
            val __nt = __action314(state, __sym0, __sym1, __sym2);
            symbols.add(Triple(__start, GrammarSymbol.Variant20(__nt), __end))
            return 3 to 26
        }
        48 -> {
            // (<LoadStmtSyms>) = LoadStmtSyms => ActionFn(170);
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym0.third;
            val __nt = __action170(state, __sym0);
            symbols.add(Triple(__start, GrammarSymbol.Variant21(__nt), __end))
            return 1 to 27
        }
        49 -> {
            // (<LoadStmtSyms>)? = LoadStmtSyms => ActionFn(317);
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym0.third;
            val __nt = __action317(state, __sym0);
            symbols.add(Triple(__start, GrammarSymbol.Variant22(__nt), __end))
            return 1 to 28
        }
        50 -> {
            // (<LoadStmtSyms>)? =  => ActionFn(169);
            val __start = lookaheadStart ?: symbols.lastOrNull()?.third ?: 0;
            val __end = __start;
            val __nt = __action169(state, __start, __end);
            symbols.add(Triple(__start, GrammarSymbol.Variant22(__nt), __end))
            return 0 to 28
        }
        51 -> {
            // (<StmtP<AstNoPayload>> "\n"*) = StmtP<AstNoPayload> => ActionFn(257);
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym0.third;
            val __nt = __action257(state, __sym0);
            symbols.add(Triple(__start, GrammarSymbol.Variant9(__nt), __end))
            return 1 to 29
        }
        52 -> {
            // (<StmtP<AstNoPayload>> "\n"*) = StmtP<AstNoPayload>, "\n"+ => ActionFn(258);
            val __sym1 = symbols.popSymbol()
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym1.third;
            val __nt = __action258(state, __sym0, __sym1);
            symbols.add(Triple(__start, GrammarSymbol.Variant9(__nt), __end))
            return 2 to 29
        }
        53 -> {
            // (<StmtP<AstNoPayload>> "\n"*)* =  => ActionFn(193);
            val __start = lookaheadStart ?: symbols.lastOrNull()?.third ?: 0;
            val __end = __start;
            val __nt = __action193(state, __start, __end);
            symbols.add(Triple(__start, GrammarSymbol.Variant10(__nt), __end))
            return 0 to 30
        }
        54 -> {
            // (<StmtP<AstNoPayload>> "\n"*)* = (<StmtP<AstNoPayload>> "\n"*)+ => ActionFn(194);
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym0.third;
            val __nt = __action194(state, __sym0);
            symbols.add(Triple(__start, GrammarSymbol.Variant10(__nt), __end))
            return 1 to 30
        }
        55 -> {
            // (<StmtP<AstNoPayload>> "\n"*)+ = StmtP<AstNoPayload> => ActionFn(322);
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym0.third;
            val __nt = __action322(state, __sym0);
            symbols.add(Triple(__start, GrammarSymbol.Variant10(__nt), __end))
            return 1 to 31
        }
        56 -> {
            // (<StmtP<AstNoPayload>> "\n"*)+ = StmtP<AstNoPayload>, "\n"+ => ActionFn(323);
            val __sym1 = symbols.popSymbol()
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym1.third;
            val __nt = __action323(state, __sym0, __sym1);
            symbols.add(Triple(__start, GrammarSymbol.Variant10(__nt), __end))
            return 2 to 31
        }
        57 -> {
            // (<StmtP<AstNoPayload>> "\n"*)+ = (<StmtP<AstNoPayload>> "\n"*)+, StmtP<AstNoPayload> => ActionFn(324);
            val __sym1 = symbols.popSymbol()
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym1.third;
            val __nt = __action324(state, __sym0, __sym1);
            symbols.add(Triple(__start, GrammarSymbol.Variant10(__nt), __end))
            return 2 to 31
        }
        58 -> {
            // (<StmtP<AstNoPayload>> "\n"*)+ = (<StmtP<AstNoPayload>> "\n"*)+, StmtP<AstNoPayload>, "\n"+ => ActionFn(325);
            val __sym2 = symbols.popSymbol()
            val __sym1 = symbols.popSymbol()
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym2.third;
            val __nt = __action325(state, __sym0, __sym1, __sym2);
            symbols.add(Triple(__start, GrammarSymbol.Variant10(__nt), __end))
            return 3 to 31
        }
        59 -> {
            // (<Test> ",") = Test, "," => ActionFn(224);
            val __sym1 = symbols.popSymbol()
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym1.third;
            val __nt = __action224(state, __sym0, __sym1);
            symbols.add(Triple(__start, GrammarSymbol.Variant17(__nt), __end))
            return 2 to 32
        }
        60 -> {
            // (<Test> ",")* =  => ActionFn(222);
            val __start = lookaheadStart ?: symbols.lastOrNull()?.third ?: 0;
            val __end = __start;
            val __nt = __action222(state, __start, __end);
            symbols.add(Triple(__start, GrammarSymbol.Variant18(__nt), __end))
            return 0 to 33
        }
        61 -> {
            // (<Test> ",")* = (<Test> ",")+ => ActionFn(223);
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym0.third;
            val __nt = __action223(state, __sym0);
            symbols.add(Triple(__start, GrammarSymbol.Variant18(__nt), __end))
            return 1 to 33
        }
        62 -> {
            // (<Test> ",")+ = Test, "," => ActionFn(330);
            val __sym1 = symbols.popSymbol()
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym1.third;
            val __nt = __action330(state, __sym0, __sym1);
            symbols.add(Triple(__start, GrammarSymbol.Variant18(__nt), __end))
            return 2 to 34
        }
        63 -> {
            // (<Test> ",")+ = (<Test> ",")+, Test, "," => ActionFn(331);
            val __sym2 = symbols.popSymbol()
            val __sym1 = symbols.popSymbol()
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym2.third;
            val __nt = __action331(state, __sym0, __sym1, __sym2);
            symbols.add(Triple(__start, GrammarSymbol.Variant18(__nt), __end))
            return 3 to 34
        }
        64 -> {
            // @L =  => ActionFn(199);
            val __start = lookaheadStart ?: symbols.lastOrNull()?.third ?: 0;
            val __end = __start;
            val __nt = __action199(state, __start, __end);
            symbols.add(Triple(__start, GrammarSymbol.Variant23(__nt), __end))
            return 0 to 35
        }
        65 -> {
            // @R =  => ActionFn(198);
            val __start = lookaheadStart ?: symbols.lastOrNull()?.third ?: 0;
            val __end = __start;
            val __nt = __action198(state, __start, __end);
            symbols.add(Triple(__start, GrammarSymbol.Variant23(__nt), __end))
            return 0 to 36
        }
        66 -> {
            // ASTA<Argument_> = Argument_ => ActionFn(432);
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym0.third;
            val __nt = __action432(state, __sym0);
            symbols.add(Triple(__start, GrammarSymbol.Variant11(__nt), __end))
            return 1 to 37
        }
        67 -> {
            // ASTE<DictComp_> = DictComp_ => ActionFn(433);
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym0.third;
            val __nt = __action433(state, __sym0);
            symbols.add(Triple(__start, GrammarSymbol.Variant17(__nt), __end))
            return 1 to 38
        }
        68 -> {
            // ASTE<LambDef_> = LambDef_ => ActionFn(434);
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym0.third;
            val __nt = __action434(state, __sym0);
            symbols.add(Triple(__start, GrammarSymbol.Variant17(__nt), __end))
            return 1 to 39
        }
        69 -> {
            // ASTE<ListComp_> = ListComp_ => ActionFn(435);
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym0.third;
            val __nt = __action435(state, __sym0);
            symbols.add(Triple(__start, GrammarSymbol.Variant17(__nt), __end))
            return 1 to 40
        }
        70 -> {
            // ASTP<DefParameter_> = DefParameter_ => ActionFn(436);
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym0.third;
            val __nt = __action436(state, __sym0);
            symbols.add(Triple(__start, GrammarSymbol.Variant13(__nt), __end))
            return 1 to 41
        }
        71 -> {
            // ASTP<LambdaParameter_> = LambdaParameter_ => ActionFn(437);
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym0.third;
            val __nt = __action437(state, __sym0);
            symbols.add(Triple(__start, GrammarSymbol.Variant13(__nt), __end))
            return 1 to 42
        }
        72 -> {
            // ASTS<AssignStmt_> = AssignStmt_ => ActionFn(438);
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym0.third;
            val __nt = __action438(state, __sym0);
            symbols.add(Triple(__start, GrammarSymbol.Variant9(__nt), __end))
            return 1 to 43
        }
        73 -> {
            // ASTS<DefStmt_> = DefStmt_ => ActionFn(439);
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym0.third;
            val __nt = __action439(state, __sym0);
            symbols.add(Triple(__start, GrammarSymbol.Variant9(__nt), __end))
            return 1 to 44
        }
        74 -> {
            // ASTS<ExprStmt_> = ExprStmt_ => ActionFn(440);
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym0.third;
            val __nt = __action440(state, __sym0);
            symbols.add(Triple(__start, GrammarSymbol.Variant9(__nt), __end))
            return 1 to 45
        }
        75 -> {
            // ASTS<ForStmt_> = ForStmt_ => ActionFn(441);
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym0.third;
            val __nt = __action441(state, __sym0);
            symbols.add(Triple(__start, GrammarSymbol.Variant9(__nt), __end))
            return 1 to 46
        }
        76 -> {
            // ASTS<IfBody_> = IfBody_ => ActionFn(442);
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym0.third;
            val __nt = __action442(state, __sym0);
            symbols.add(Triple(__start, GrammarSymbol.Variant9(__nt), __end))
            return 1 to 47
        }
        77 -> {
            // ASTS<IfStmt_> = IfStmt_ => ActionFn(443);
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym0.third;
            val __nt = __action443(state, __sym0);
            symbols.add(Triple(__start, GrammarSymbol.Variant9(__nt), __end))
            return 1 to 48
        }
        78 -> {
            // ASTS<LoadStmt_> = LoadStmt_ => ActionFn(444);
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym0.third;
            val __nt = __action444(state, __sym0);
            symbols.add(Triple(__start, GrammarSymbol.Variant9(__nt), __end))
            return 1 to 49
        }
        79 -> {
            // AndTest = AndTest, "and", NotTest => ActionFn(445);
            val __sym2 = symbols.popSymbol()
            val __sym1 = symbols.popSymbol()
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym2.third;
            val __nt = __action445(state, __sym0, __sym1, __sym2);
            symbols.add(Triple(__start, GrammarSymbol.Variant17(__nt), __end))
            return 3 to 50
        }
        80 -> {
            // AndTest = NotTest => ActionFn(115);
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym0.third;
            val __nt = __action115(state, __sym0);
            symbols.add(Triple(__start, GrammarSymbol.Variant17(__nt), __end))
            return 1 to 50
        }
        81 -> {
            // ArgumentP<AstNoPayload> = Argument_ => ActionFn(524);
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym0.third;
            val __nt = __action524(state, __sym0);
            symbols.add(Triple(__start, GrammarSymbol.Variant11(__nt), __end))
            return 1 to 51
        }
        82 -> {
            // ArgumentP<AstNoPayload>? = ArgumentP<AstNoPayload> => ActionFn(217);
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym0.third;
            val __nt = __action217(state, __sym0);
            symbols.add(Triple(__start, GrammarSymbol.Variant24(__nt), __end))
            return 1 to 52
        }
        83 -> {
            // ArgumentP<AstNoPayload>? =  => ActionFn(218);
            val __start = lookaheadStart ?: symbols.lastOrNull()?.third ?: 0;
            val __end = __start;
            val __nt = __action218(state, __start, __end);
            symbols.add(Triple(__start, GrammarSymbol.Variant24(__nt), __end))
            return 0 to 52
        }
        84 -> {
            // Argument_ = Test => ActionFn(83);
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym0.third;
            val __nt = __action83(state, __sym0);
            symbols.add(Triple(__start, GrammarSymbol.Variant25(__nt), __end))
            return 1 to 53
        }
        85 -> {
            // Argument_ = "IDENTIFIER", "=", Test => ActionFn(565);
            val __sym2 = symbols.popSymbol()
            val __sym1 = symbols.popSymbol()
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym2.third;
            val __nt = __action565(state, __sym0, __sym1, __sym2);
            symbols.add(Triple(__start, GrammarSymbol.Variant25(__nt), __end))
            return 3 to 53
        }
        86 -> {
            // Argument_ = "*", Test => ActionFn(85);
            val __sym1 = symbols.popSymbol()
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym1.third;
            val __nt = __action85(state, __sym0, __sym1);
            symbols.add(Triple(__start, GrammarSymbol.Variant25(__nt), __end))
            return 2 to 53
        }
        87 -> {
            // Argument_ = "**", Test => ActionFn(86);
            val __sym1 = symbols.popSymbol()
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym1.third;
            val __nt = __action86(state, __sym0, __sym1);
            symbols.add(Triple(__start, GrammarSymbol.Variant25(__nt), __end))
            return 2 to 53
        }
        88 -> {
            // ArithExpr = ArithExpr, "+", ProductExpr => ActionFn(446);
            val __sym2 = symbols.popSymbol()
            val __sym1 = symbols.popSymbol()
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym2.third;
            val __nt = __action446(state, __sym0, __sym1, __sym2);
            symbols.add(Triple(__start, GrammarSymbol.Variant17(__nt), __end))
            return 3 to 54
        }
        89 -> {
            // ArithExpr = ArithExpr, "-", ProductExpr => ActionFn(447);
            val __sym2 = symbols.popSymbol()
            val __sym1 = symbols.popSymbol()
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym2.third;
            val __nt = __action447(state, __sym0, __sym1, __sym2);
            symbols.add(Triple(__start, GrammarSymbol.Variant17(__nt), __end))
            return 3 to 54
        }
        90 -> {
            // ArithExpr = ProductExpr => ActionFn(139);
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym0.third;
            val __nt = __action139(state, __sym0);
            symbols.add(Triple(__start, GrammarSymbol.Variant17(__nt), __end))
            return 1 to 54
        }
        91 -> {
            // AssignIdentP<AstNoPayload> = "IDENTIFIER" => ActionFn(566);
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym0.third;
            val __nt = __action566(state, __sym0);
            symbols.add(Triple(__start, GrammarSymbol.Variant26(__nt), __end))
            return 1 to 55
        }
        92 -> {
            // AssignOp = "=" => ActionFn(51);
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym0.third;
            val __nt = __action51(state, __sym0);
            symbols.add(Triple(__start, GrammarSymbol.Variant27(__nt), __end))
            return 1 to 56
        }
        93 -> {
            // AssignOp = "+=" => ActionFn(52);
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym0.third;
            val __nt = __action52(state, __sym0);
            symbols.add(Triple(__start, GrammarSymbol.Variant27(__nt), __end))
            return 1 to 56
        }
        94 -> {
            // AssignOp = "-=" => ActionFn(53);
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym0.third;
            val __nt = __action53(state, __sym0);
            symbols.add(Triple(__start, GrammarSymbol.Variant27(__nt), __end))
            return 1 to 56
        }
        95 -> {
            // AssignOp = "*=" => ActionFn(54);
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym0.third;
            val __nt = __action54(state, __sym0);
            symbols.add(Triple(__start, GrammarSymbol.Variant27(__nt), __end))
            return 1 to 56
        }
        96 -> {
            // AssignOp = "/=" => ActionFn(55);
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym0.third;
            val __nt = __action55(state, __sym0);
            symbols.add(Triple(__start, GrammarSymbol.Variant27(__nt), __end))
            return 1 to 56
        }
        97 -> {
            // AssignOp = "//=" => ActionFn(56);
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym0.third;
            val __nt = __action56(state, __sym0);
            symbols.add(Triple(__start, GrammarSymbol.Variant27(__nt), __end))
            return 1 to 56
        }
        98 -> {
            // AssignOp = "%=" => ActionFn(57);
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym0.third;
            val __nt = __action57(state, __sym0);
            symbols.add(Triple(__start, GrammarSymbol.Variant27(__nt), __end))
            return 1 to 56
        }
        99 -> {
            // AssignOp = "&=" => ActionFn(58);
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym0.third;
            val __nt = __action58(state, __sym0);
            symbols.add(Triple(__start, GrammarSymbol.Variant27(__nt), __end))
            return 1 to 56
        }
        100 -> {
            // AssignOp = "|=" => ActionFn(59);
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym0.third;
            val __nt = __action59(state, __sym0);
            symbols.add(Triple(__start, GrammarSymbol.Variant27(__nt), __end))
            return 1 to 56
        }
        101 -> {
            // AssignOp = "^=" => ActionFn(60);
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym0.third;
            val __nt = __action60(state, __sym0);
            symbols.add(Triple(__start, GrammarSymbol.Variant27(__nt), __end))
            return 1 to 56
        }
        102 -> {
            // AssignOp = "<<=" => ActionFn(61);
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym0.third;
            val __nt = __action61(state, __sym0);
            symbols.add(Triple(__start, GrammarSymbol.Variant27(__nt), __end))
            return 1 to 56
        }
        103 -> {
            // AssignOp = ">>=" => ActionFn(62);
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym0.third;
            val __nt = __action62(state, __sym0);
            symbols.add(Triple(__start, GrammarSymbol.Variant27(__nt), __end))
            return 1 to 56
        }
        104 -> {
            // AssignStmt = AssignStmt_ => ActionFn(530);
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym0.third;
            val __nt = __action530(state, __sym0);
            symbols.add(Triple(__start, GrammarSymbol.Variant9(__nt), __end))
            return 1 to 57
        }
        105 -> {
            // AssignStmt_ = TestList, Type, AssignOp, TestList => ActionFn(64);
            val __sym3 = symbols.popSymbol()
            val __sym2 = symbols.popSymbol()
            val __sym1 = symbols.popSymbol()
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym3.third;
            val __nt = __action64(state, __sym0, __sym1, __sym2, __sym3);
            symbols.add(Triple(__start, GrammarSymbol.Variant28(__nt), __end))
            return 4 to 58
        }
        106 -> {
            // BitAndExpr = BitAndExpr, "&", ShiftExpr => ActionFn(448);
            val __sym2 = symbols.popSymbol()
            val __sym1 = symbols.popSymbol()
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym2.third;
            val __nt = __action448(state, __sym0, __sym1, __sym2);
            symbols.add(Triple(__start, GrammarSymbol.Variant17(__nt), __end))
            return 3 to 59
        }
        107 -> {
            // BitAndExpr = ShiftExpr => ActionFn(133);
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym0.third;
            val __nt = __action133(state, __sym0);
            symbols.add(Triple(__start, GrammarSymbol.Variant17(__nt), __end))
            return 1 to 59
        }
        108 -> {
            // BitOrExpr = BitOrExpr, "|", BitXorExpr => ActionFn(449);
            val __sym2 = symbols.popSymbol()
            val __sym1 = symbols.popSymbol()
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym2.third;
            val __nt = __action449(state, __sym0, __sym1, __sym2);
            symbols.add(Triple(__start, GrammarSymbol.Variant17(__nt), __end))
            return 3 to 60
        }
        109 -> {
            // BitOrExpr = BitXorExpr => ActionFn(129);
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym0.third;
            val __nt = __action129(state, __sym0);
            symbols.add(Triple(__start, GrammarSymbol.Variant17(__nt), __end))
            return 1 to 60
        }
        110 -> {
            // BitXorExpr = BitXorExpr, "^", BitAndExpr => ActionFn(450);
            val __sym2 = symbols.popSymbol()
            val __sym1 = symbols.popSymbol()
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym2.third;
            val __nt = __action450(state, __sym0, __sym1, __sym2);
            symbols.add(Triple(__start, GrammarSymbol.Variant17(__nt), __end))
            return 3 to 61
        }
        111 -> {
            // BitXorExpr = BitAndExpr => ActionFn(131);
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym0.third;
            val __nt = __action131(state, __sym0);
            symbols.add(Triple(__start, GrammarSymbol.Variant17(__nt), __end))
            return 1 to 61
        }
        112 -> {
            // COMMA<ArgumentP<AstNoPayload>> = ArgumentP<AstNoPayload> => ActionFn(537);
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym0.third;
            val __nt = __action537(state, __sym0);
            symbols.add(Triple(__start, GrammarSymbol.Variant29(__nt), __end))
            return 1 to 62
        }
        113 -> {
            // COMMA<ArgumentP<AstNoPayload>> =  => ActionFn(538);
            val __start = lookaheadStart ?: symbols.lastOrNull()?.third ?: 0;
            val __end = __start;
            val __nt = __action538(state, __start, __end);
            symbols.add(Triple(__start, GrammarSymbol.Variant29(__nt), __end))
            return 0 to 62
        }
        114 -> {
            // COMMA<ArgumentP<AstNoPayload>> = (<ArgumentP<AstNoPayload>> ",")+, ArgumentP<AstNoPayload> => ActionFn(539);
            val __sym1 = symbols.popSymbol()
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym1.third;
            val __nt = __action539(state, __sym0, __sym1);
            symbols.add(Triple(__start, GrammarSymbol.Variant29(__nt), __end))
            return 2 to 62
        }
        115 -> {
            // COMMA<ArgumentP<AstNoPayload>> = (<ArgumentP<AstNoPayload>> ",")+ => ActionFn(540);
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym0.third;
            val __nt = __action540(state, __sym0);
            symbols.add(Triple(__start, GrammarSymbol.Variant29(__nt), __end))
            return 1 to 62
        }
        116 -> {
            // COMMA<DefParameter> = DefParameter => ActionFn(543);
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym0.third;
            val __nt = __action543(state, __sym0);
            symbols.add(Triple(__start, GrammarSymbol.Variant30(__nt), __end))
            return 1 to 63
        }
        117 -> {
            // COMMA<DefParameter> =  => ActionFn(544);
            val __start = lookaheadStart ?: symbols.lastOrNull()?.third ?: 0;
            val __end = __start;
            val __nt = __action544(state, __start, __end);
            symbols.add(Triple(__start, GrammarSymbol.Variant30(__nt), __end))
            return 0 to 63
        }
        118 -> {
            // COMMA<DefParameter> = (<DefParameter> ",")+, DefParameter => ActionFn(545);
            val __sym1 = symbols.popSymbol()
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym1.third;
            val __nt = __action545(state, __sym0, __sym1);
            symbols.add(Triple(__start, GrammarSymbol.Variant30(__nt), __end))
            return 2 to 63
        }
        119 -> {
            // COMMA<DefParameter> = (<DefParameter> ",")+ => ActionFn(546);
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym0.third;
            val __nt = __action546(state, __sym0);
            symbols.add(Triple(__start, GrammarSymbol.Variant30(__nt), __end))
            return 1 to 63
        }
        120 -> {
            // COMMA<DictEntry> = DictEntry => ActionFn(547);
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym0.third;
            val __nt = __action547(state, __sym0);
            symbols.add(Triple(__start, GrammarSymbol.Variant31(__nt), __end))
            return 1 to 64
        }
        121 -> {
            // COMMA<DictEntry> =  => ActionFn(548);
            val __start = lookaheadStart ?: symbols.lastOrNull()?.third ?: 0;
            val __end = __start;
            val __nt = __action548(state, __start, __end);
            symbols.add(Triple(__start, GrammarSymbol.Variant31(__nt), __end))
            return 0 to 64
        }
        122 -> {
            // COMMA<DictEntry> = (<DictEntry> ",")+, DictEntry => ActionFn(549);
            val __sym1 = symbols.popSymbol()
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym1.third;
            val __nt = __action549(state, __sym0, __sym1);
            symbols.add(Triple(__start, GrammarSymbol.Variant31(__nt), __end))
            return 2 to 64
        }
        123 -> {
            // COMMA<DictEntry> = (<DictEntry> ",")+ => ActionFn(550);
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym0.third;
            val __nt = __action550(state, __sym0);
            symbols.add(Triple(__start, GrammarSymbol.Variant31(__nt), __end))
            return 1 to 64
        }
        124 -> {
            // COMMA<LambdaParameter> = LambdaParameter => ActionFn(553);
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym0.third;
            val __nt = __action553(state, __sym0);
            symbols.add(Triple(__start, GrammarSymbol.Variant30(__nt), __end))
            return 1 to 65
        }
        125 -> {
            // COMMA<LambdaParameter> =  => ActionFn(554);
            val __start = lookaheadStart ?: symbols.lastOrNull()?.third ?: 0;
            val __end = __start;
            val __nt = __action554(state, __start, __end);
            symbols.add(Triple(__start, GrammarSymbol.Variant30(__nt), __end))
            return 0 to 65
        }
        126 -> {
            // COMMA<LambdaParameter> = (<LambdaParameter> ",")+, LambdaParameter => ActionFn(555);
            val __sym1 = symbols.popSymbol()
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym1.third;
            val __nt = __action555(state, __sym0, __sym1);
            symbols.add(Triple(__start, GrammarSymbol.Variant30(__nt), __end))
            return 2 to 65
        }
        127 -> {
            // COMMA<LambdaParameter> = (<LambdaParameter> ",")+ => ActionFn(556);
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym0.third;
            val __nt = __action556(state, __sym0);
            symbols.add(Triple(__start, GrammarSymbol.Variant30(__nt), __end))
            return 1 to 65
        }
        128 -> {
            // COMMA<Test> = Test => ActionFn(332);
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym0.third;
            val __nt = __action332(state, __sym0);
            symbols.add(Triple(__start, GrammarSymbol.Variant32(__nt), __end))
            return 1 to 66
        }
        129 -> {
            // COMMA<Test> = (<Test> ",")+, Test => ActionFn(333);
            val __sym1 = symbols.popSymbol()
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym1.third;
            val __nt = __action333(state, __sym0, __sym1);
            symbols.add(Triple(__start, GrammarSymbol.Variant32(__nt), __end))
            return 2 to 66
        }
        130 -> {
            // COMMA<Test> =  => ActionFn(334);
            val __start = lookaheadStart ?: symbols.lastOrNull()?.third ?: 0;
            val __end = __start;
            val __nt = __action334(state, __start, __end);
            symbols.add(Triple(__start, GrammarSymbol.Variant32(__nt), __end))
            return 0 to 66
        }
        131 -> {
            // COMMA<Test> = (<Test> ",")+ => ActionFn(335);
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym0.third;
            val __nt = __action335(state, __sym0);
            symbols.add(Triple(__start, GrammarSymbol.Variant32(__nt), __end))
            return 1 to 66
        }
        132 -> {
            // ClauseP<AstNoPayload> = ForClauseP<AstNoPayload> => ActionFn(104);
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym0.third;
            val __nt = __action104(state, __sym0);
            symbols.add(Triple(__start, GrammarSymbol.Variant33(__nt), __end))
            return 1 to 67
        }
        133 -> {
            // ClauseP<AstNoPayload> = "if", OrTest => ActionFn(105);
            val __sym1 = symbols.popSymbol()
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym1.third;
            val __nt = __action105(state, __sym0, __sym1);
            symbols.add(Triple(__start, GrammarSymbol.Variant33(__nt), __end))
            return 2 to 67
        }
        134 -> {
            // ClauseP<AstNoPayload>* =  => ActionFn(151);
            val __start = lookaheadStart ?: symbols.lastOrNull()?.third ?: 0;
            val __end = __start;
            val __nt = __action151(state, __start, __end);
            symbols.add(Triple(__start, GrammarSymbol.Variant34(__nt), __end))
            return 0 to 68
        }
        135 -> {
            // ClauseP<AstNoPayload>* = ClauseP<AstNoPayload>+ => ActionFn(152);
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym0.third;
            val __nt = __action152(state, __sym0);
            symbols.add(Triple(__start, GrammarSymbol.Variant34(__nt), __end))
            return 1 to 68
        }
        136 -> {
            // ClauseP<AstNoPayload>+ = ClauseP<AstNoPayload> => ActionFn(230);
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym0.third;
            val __nt = __action230(state, __sym0);
            symbols.add(Triple(__start, GrammarSymbol.Variant34(__nt), __end))
            return 1 to 69
        }
        137 -> {
            // ClauseP<AstNoPayload>+ = ClauseP<AstNoPayload>+, ClauseP<AstNoPayload> => ActionFn(231);
            val __sym1 = symbols.popSymbol()
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym1.third;
            val __nt = __action231(state, __sym0, __sym1);
            symbols.add(Triple(__start, GrammarSymbol.Variant34(__nt), __end))
            return 2 to 69
        }
        138 -> {
            // Comma = "," => ActionFn(451);
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym0.third;
            val __nt = __action451(state, __sym0);
            symbols.add(Triple(__start, GrammarSymbol.Variant35(__nt), __end))
            return 1 to 70
        }
        139 -> {
            // CompClause = ForClauseP<AstNoPayload> => ActionFn(541);
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym0.third;
            val __nt = __action541(state, __sym0);
            symbols.add(Triple(__start, GrammarSymbol.Variant36(__nt), __end))
            return 1 to 71
        }
        140 -> {
            // CompClause = ForClauseP<AstNoPayload>, ClauseP<AstNoPayload>+ => ActionFn(542);
            val __sym1 = symbols.popSymbol()
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym1.third;
            val __nt = __action542(state, __sym0, __sym1);
            symbols.add(Triple(__start, GrammarSymbol.Variant36(__nt), __end))
            return 2 to 71
        }
        141 -> {
            // CompTest = BitOrExpr, "==", BitOrExpr => ActionFn(452);
            val __sym2 = symbols.popSymbol()
            val __sym1 = symbols.popSymbol()
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym2.third;
            val __nt = __action452(state, __sym0, __sym1, __sym2);
            symbols.add(Triple(__start, GrammarSymbol.Variant17(__nt), __end))
            return 3 to 72
        }
        142 -> {
            // CompTest = BitOrExpr, "!=", BitOrExpr => ActionFn(453);
            val __sym2 = symbols.popSymbol()
            val __sym1 = symbols.popSymbol()
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym2.third;
            val __nt = __action453(state, __sym0, __sym1, __sym2);
            symbols.add(Triple(__start, GrammarSymbol.Variant17(__nt), __end))
            return 3 to 72
        }
        143 -> {
            // CompTest = BitOrExpr, "<", BitOrExpr => ActionFn(454);
            val __sym2 = symbols.popSymbol()
            val __sym1 = symbols.popSymbol()
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym2.third;
            val __nt = __action454(state, __sym0, __sym1, __sym2);
            symbols.add(Triple(__start, GrammarSymbol.Variant17(__nt), __end))
            return 3 to 72
        }
        144 -> {
            // CompTest = BitOrExpr, ">", BitOrExpr => ActionFn(455);
            val __sym2 = symbols.popSymbol()
            val __sym1 = symbols.popSymbol()
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym2.third;
            val __nt = __action455(state, __sym0, __sym1, __sym2);
            symbols.add(Triple(__start, GrammarSymbol.Variant17(__nt), __end))
            return 3 to 72
        }
        145 -> {
            // CompTest = BitOrExpr, "<=", BitOrExpr => ActionFn(456);
            val __sym2 = symbols.popSymbol()
            val __sym1 = symbols.popSymbol()
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym2.third;
            val __nt = __action456(state, __sym0, __sym1, __sym2);
            symbols.add(Triple(__start, GrammarSymbol.Variant17(__nt), __end))
            return 3 to 72
        }
        146 -> {
            // CompTest = BitOrExpr, ">=", BitOrExpr => ActionFn(457);
            val __sym2 = symbols.popSymbol()
            val __sym1 = symbols.popSymbol()
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym2.third;
            val __nt = __action457(state, __sym0, __sym1, __sym2);
            symbols.add(Triple(__start, GrammarSymbol.Variant17(__nt), __end))
            return 3 to 72
        }
        147 -> {
            // CompTest = BitOrExpr, "in", BitOrExpr => ActionFn(458);
            val __sym2 = symbols.popSymbol()
            val __sym1 = symbols.popSymbol()
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym2.third;
            val __nt = __action458(state, __sym0, __sym1, __sym2);
            symbols.add(Triple(__start, GrammarSymbol.Variant17(__nt), __end))
            return 3 to 72
        }
        148 -> {
            // CompTest = BitOrExpr, "not", "in", BitOrExpr => ActionFn(459);
            val __sym3 = symbols.popSymbol()
            val __sym2 = symbols.popSymbol()
            val __sym1 = symbols.popSymbol()
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym3.third;
            val __nt = __action459(state, __sym0, __sym1, __sym2, __sym3);
            symbols.add(Triple(__start, GrammarSymbol.Variant17(__nt), __end))
            return 4 to 72
        }
        149 -> {
            // CompTest = BitOrExpr => ActionFn(126);
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym0.third;
            val __nt = __action126(state, __sym0);
            symbols.add(Triple(__start, GrammarSymbol.Variant17(__nt), __end))
            return 1 to 72
        }
        150 -> {
            // DefParameter = DefParameter_ => ActionFn(528);
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym0.third;
            val __nt = __action528(state, __sym0);
            symbols.add(Triple(__start, GrammarSymbol.Variant13(__nt), __end))
            return 1 to 73
        }
        151 -> {
            // DefParameter? = DefParameter => ActionFn(200);
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym0.third;
            val __nt = __action200(state, __sym0);
            symbols.add(Triple(__start, GrammarSymbol.Variant37(__nt), __end))
            return 1 to 74
        }
        152 -> {
            // DefParameter? =  => ActionFn(201);
            val __start = lookaheadStart ?: symbols.lastOrNull()?.third ?: 0;
            val __end = __start;
            val __nt = __action201(state, __start, __end);
            symbols.add(Triple(__start, GrammarSymbol.Variant37(__nt), __end))
            return 0 to 74
        }
        153 -> {
            // DefParameter_ = "/" => ActionFn(21);
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym0.third;
            val __nt = __action21(state, __sym0);
            symbols.add(Triple(__start, GrammarSymbol.Variant38(__nt), __end))
            return 1 to 75
        }
        154 -> {
            // DefParameter_ = AssignIdentP<AstNoPayload>, Type, "=", Test => ActionFn(22);
            val __sym3 = symbols.popSymbol()
            val __sym2 = symbols.popSymbol()
            val __sym1 = symbols.popSymbol()
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym3.third;
            val __nt = __action22(state, __sym0, __sym1, __sym2, __sym3);
            symbols.add(Triple(__start, GrammarSymbol.Variant38(__nt), __end))
            return 4 to 75
        }
        155 -> {
            // DefParameter_ = AssignIdentP<AstNoPayload>, Type => ActionFn(23);
            val __sym1 = symbols.popSymbol()
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym1.third;
            val __nt = __action23(state, __sym0, __sym1);
            symbols.add(Triple(__start, GrammarSymbol.Variant38(__nt), __end))
            return 2 to 75
        }
        156 -> {
            // DefParameter_ = "*", AssignIdentP<AstNoPayload>, Type => ActionFn(24);
            val __sym2 = symbols.popSymbol()
            val __sym1 = symbols.popSymbol()
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym2.third;
            val __nt = __action24(state, __sym0, __sym1, __sym2);
            symbols.add(Triple(__start, GrammarSymbol.Variant38(__nt), __end))
            return 3 to 75
        }
        157 -> {
            // DefParameter_ = "*" => ActionFn(25);
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym0.third;
            val __nt = __action25(state, __sym0);
            symbols.add(Triple(__start, GrammarSymbol.Variant38(__nt), __end))
            return 1 to 75
        }
        158 -> {
            // DefParameter_ = "**", AssignIdentP<AstNoPayload>, Type => ActionFn(26);
            val __sym2 = symbols.popSymbol()
            val __sym1 = symbols.popSymbol()
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym2.third;
            val __nt = __action26(state, __sym0, __sym1, __sym2);
            symbols.add(Triple(__start, GrammarSymbol.Variant38(__nt), __end))
            return 3 to 75
        }
        159 -> {
            // DefStmt = DefStmt_ => ActionFn(531);
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym0.third;
            val __nt = __action531(state, __sym0);
            symbols.add(Triple(__start, GrammarSymbol.Variant9(__nt), __end))
            return 1 to 76
        }
        160 -> {
            // DefStmt_ = "def", AssignIdentP<AstNoPayload>, "(", COMMA<DefParameter>, ")", ReturnType, ":", Suite => ActionFn(10);
            val __sym7 = symbols.popSymbol()
            val __sym6 = symbols.popSymbol()
            val __sym5 = symbols.popSymbol()
            val __sym4 = symbols.popSymbol()
            val __sym3 = symbols.popSymbol()
            val __sym2 = symbols.popSymbol()
            val __sym1 = symbols.popSymbol()
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym7.third;
            val __nt = __action10(state, __sym0, __sym1, __sym2, __sym3, __sym4, __sym5, __sym6, __sym7);
            symbols.add(Triple(__start, GrammarSymbol.Variant28(__nt), __end))
            return 8 to 77
        }
        161 -> {
            // DictComp = DictComp_ => ActionFn(525);
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym0.third;
            val __nt = __action525(state, __sym0);
            symbols.add(Triple(__start, GrammarSymbol.Variant17(__nt), __end))
            return 1 to 78
        }
        162 -> {
            // DictComp_ = "{", DictEntry, CompClause, "}" => ActionFn(102);
            val __sym3 = symbols.popSymbol()
            val __sym2 = symbols.popSymbol()
            val __sym1 = symbols.popSymbol()
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym3.third;
            val __nt = __action102(state, __sym0, __sym1, __sym2, __sym3);
            symbols.add(Triple(__start, GrammarSymbol.Variant39(__nt), __end))
            return 4 to 79
        }
        163 -> {
            // DictEntry = Test, ":", Test => ActionFn(98);
            val __sym2 = symbols.popSymbol()
            val __sym1 = symbols.popSymbol()
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym2.third;
            val __nt = __action98(state, __sym0, __sym1, __sym2);
            symbols.add(Triple(__start, GrammarSymbol.Variant15(__nt), __end))
            return 3 to 80
        }
        164 -> {
            // DictEntry? = DictEntry => ActionFn(225);
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym0.third;
            val __nt = __action225(state, __sym0);
            symbols.add(Triple(__start, GrammarSymbol.Variant40(__nt), __end))
            return 1 to 81
        }
        165 -> {
            // DictEntry? =  => ActionFn(226);
            val __start = lookaheadStart ?: symbols.lastOrNull()?.third ?: 0;
            val __end = __start;
            val __nt = __action226(state, __start, __end);
            symbols.add(Triple(__start, GrammarSymbol.Variant40(__nt), __end))
            return 0 to 81
        }
        166 -> {
            // ElseStmt = "elif", IfBody => ActionFn(40);
            val __sym1 = symbols.popSymbol()
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym1.third;
            val __nt = __action40(state, __sym0, __sym1);
            symbols.add(Triple(__start, GrammarSymbol.Variant9(__nt), __end))
            return 2 to 82
        }
        167 -> {
            // ElseStmt = "else", ":", Suite => ActionFn(41);
            val __sym2 = symbols.popSymbol()
            val __sym1 = symbols.popSymbol()
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym2.third;
            val __nt = __action41(state, __sym0, __sym1, __sym2);
            symbols.add(Triple(__start, GrammarSymbol.Variant9(__nt), __end))
            return 3 to 82
        }
        168 -> {
            // ElseStmt? = ElseStmt => ActionFn(181);
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym0.third;
            val __nt = __action181(state, __sym0);
            symbols.add(Triple(__start, GrammarSymbol.Variant41(__nt), __end))
            return 1 to 83
        }
        169 -> {
            // ElseStmt? =  => ActionFn(182);
            val __start = lookaheadStart ?: symbols.lastOrNull()?.third ?: 0;
            val __end = __start;
            val __nt = __action182(state, __start, __end);
            symbols.add(Triple(__start, GrammarSymbol.Variant41(__nt), __end))
            return 0 to 83
        }
        170 -> {
            // ExprP<AstNoPayload> = BitOrExpr => ActionFn(127);
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym0.third;
            val __nt = __action127(state, __sym0);
            symbols.add(Triple(__start, GrammarSymbol.Variant17(__nt), __end))
            return 1 to 84
        }
        171 -> {
            // ExprList = L<ExprP<AstNoPayload>> => ActionFn(73);
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym0.third;
            val __nt = __action73(state, __sym0);
            symbols.add(Triple(__start, GrammarSymbol.Variant17(__nt), __end))
            return 1 to 85
        }
        172 -> {
            // ExprStmt = ExprStmt_ => ActionFn(532);
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym0.third;
            val __nt = __action532(state, __sym0);
            symbols.add(Triple(__start, GrammarSymbol.Variant9(__nt), __end))
            return 1 to 86
        }
        173 -> {
            // ExprStmt_ = Test => ActionFn(66);
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym0.third;
            val __nt = __action66(state, __sym0);
            symbols.add(Triple(__start, GrammarSymbol.Variant28(__nt), __end))
            return 1 to 87
        }
        174 -> {
            // FactorExpr = "+", FactorExpr => ActionFn(460);
            val __sym1 = symbols.popSymbol()
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym1.third;
            val __nt = __action460(state, __sym0, __sym1);
            symbols.add(Triple(__start, GrammarSymbol.Variant17(__nt), __end))
            return 2 to 88
        }
        175 -> {
            // FactorExpr = "-", FactorExpr => ActionFn(461);
            val __sym1 = symbols.popSymbol()
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym1.third;
            val __nt = __action461(state, __sym0, __sym1);
            symbols.add(Triple(__start, GrammarSymbol.Variant17(__nt), __end))
            return 2 to 88
        }
        176 -> {
            // FactorExpr = "~", FactorExpr => ActionFn(462);
            val __sym1 = symbols.popSymbol()
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym1.third;
            val __nt = __action462(state, __sym0, __sym1);
            symbols.add(Triple(__start, GrammarSymbol.Variant17(__nt), __end))
            return 2 to 88
        }
        177 -> {
            // FactorExpr = PrimaryExpr => ActionFn(148);
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym0.third;
            val __nt = __action148(state, __sym0);
            symbols.add(Triple(__start, GrammarSymbol.Variant17(__nt), __end))
            return 1 to 88
        }
        178 -> {
            // ForClauseP<AstNoPayload> = "for", ExprList, "in", OrTest => ActionFn(106);
            val __sym3 = symbols.popSymbol()
            val __sym2 = symbols.popSymbol()
            val __sym1 = symbols.popSymbol()
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym3.third;
            val __nt = __action106(state, __sym0, __sym1, __sym2, __sym3);
            symbols.add(Triple(__start, GrammarSymbol.Variant42(__nt), __end))
            return 4 to 89
        }
        179 -> {
            // ForStmt = ForStmt_ => ActionFn(533);
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym0.third;
            val __nt = __action533(state, __sym0);
            symbols.add(Triple(__start, GrammarSymbol.Variant9(__nt), __end))
            return 1 to 90
        }
        180 -> {
            // ForStmt_ = "for", ExprList, "in", Test, ":", Suite => ActionFn(43);
            val __sym5 = symbols.popSymbol()
            val __sym4 = symbols.popSymbol()
            val __sym3 = symbols.popSymbol()
            val __sym2 = symbols.popSymbol()
            val __sym1 = symbols.popSymbol()
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym5.third;
            val __nt = __action43(state, __sym0, __sym1, __sym2, __sym3, __sym4, __sym5);
            symbols.add(Triple(__start, GrammarSymbol.Variant28(__nt), __end))
            return 6 to 91
        }
        181 -> {
            // IdentP<AstNoPayload> = "IDENTIFIER" => ActionFn(567);
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym0.third;
            val __nt = __action567(state, __sym0);
            symbols.add(Triple(__start, GrammarSymbol.Variant43(__nt), __end))
            return 1 to 92
        }
        182 -> {
            // IfBody = IfBody_ => ActionFn(534);
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym0.third;
            val __nt = __action534(state, __sym0);
            symbols.add(Triple(__start, GrammarSymbol.Variant9(__nt), __end))
            return 1 to 93
        }
        183 -> {
            // IfBody_ = Test, ":", Suite, ElseStmt => ActionFn(551);
            val __sym3 = symbols.popSymbol()
            val __sym2 = symbols.popSymbol()
            val __sym1 = symbols.popSymbol()
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym3.third;
            val __nt = __action551(state, __sym0, __sym1, __sym2, __sym3);
            symbols.add(Triple(__start, GrammarSymbol.Variant28(__nt), __end))
            return 4 to 94
        }
        184 -> {
            // IfBody_ = Test, ":", Suite => ActionFn(552);
            val __sym2 = symbols.popSymbol()
            val __sym1 = symbols.popSymbol()
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym2.third;
            val __nt = __action552(state, __sym0, __sym1, __sym2);
            symbols.add(Triple(__start, GrammarSymbol.Variant28(__nt), __end))
            return 3 to 94
        }
        185 -> {
            // IfStmt = IfStmt_ => ActionFn(535);
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym0.third;
            val __nt = __action535(state, __sym0);
            symbols.add(Triple(__start, GrammarSymbol.Variant9(__nt), __end))
            return 1 to 95
        }
        186 -> {
            // IfStmt_ = "if", IfBody_ => ActionFn(39);
            val __sym1 = symbols.popSymbol()
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym1.third;
            val __nt = __action39(state, __sym0, __sym1);
            symbols.add(Triple(__start, GrammarSymbol.Variant28(__nt), __end))
            return 2 to 96
        }
        187 -> {
            // L<ExprP<AstNoPayload>> = ExprP<AstNoPayload>, "," => ActionFn(463);
            val __sym1 = symbols.popSymbol()
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym1.third;
            val __nt = __action463(state, __sym0, __sym1);
            symbols.add(Triple(__start, GrammarSymbol.Variant17(__nt), __end))
            return 2 to 97
        }
        188 -> {
            // L<ExprP<AstNoPayload>> = (<ExprP<AstNoPayload>> ",")+, ExprP<AstNoPayload>, "," => ActionFn(464);
            val __sym2 = symbols.popSymbol()
            val __sym1 = symbols.popSymbol()
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym2.third;
            val __nt = __action464(state, __sym0, __sym1, __sym2);
            symbols.add(Triple(__start, GrammarSymbol.Variant17(__nt), __end))
            return 3 to 97
        }
        189 -> {
            // L<ExprP<AstNoPayload>> = ExprP<AstNoPayload> => ActionFn(465);
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym0.third;
            val __nt = __action465(state, __sym0);
            symbols.add(Triple(__start, GrammarSymbol.Variant17(__nt), __end))
            return 1 to 97
        }
        190 -> {
            // L<ExprP<AstNoPayload>> = (<ExprP<AstNoPayload>> ",")+, ExprP<AstNoPayload> => ActionFn(466);
            val __sym1 = symbols.popSymbol()
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym1.third;
            val __nt = __action466(state, __sym0, __sym1);
            symbols.add(Triple(__start, GrammarSymbol.Variant17(__nt), __end))
            return 2 to 97
        }
        191 -> {
            // L<Test> = Test, "," => ActionFn(467);
            val __sym1 = symbols.popSymbol()
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym1.third;
            val __nt = __action467(state, __sym0, __sym1);
            symbols.add(Triple(__start, GrammarSymbol.Variant17(__nt), __end))
            return 2 to 98
        }
        192 -> {
            // L<Test> = (<Test> ",")+, Test, "," => ActionFn(468);
            val __sym2 = symbols.popSymbol()
            val __sym1 = symbols.popSymbol()
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym2.third;
            val __nt = __action468(state, __sym0, __sym1, __sym2);
            symbols.add(Triple(__start, GrammarSymbol.Variant17(__nt), __end))
            return 3 to 98
        }
        193 -> {
            // L<Test> = Test => ActionFn(469);
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym0.third;
            val __nt = __action469(state, __sym0);
            symbols.add(Triple(__start, GrammarSymbol.Variant17(__nt), __end))
            return 1 to 98
        }
        194 -> {
            // L<Test> = (<Test> ",")+, Test => ActionFn(470);
            val __sym1 = symbols.popSymbol()
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym1.third;
            val __nt = __action470(state, __sym0, __sym1);
            symbols.add(Triple(__start, GrammarSymbol.Variant17(__nt), __end))
            return 2 to 98
        }
        195 -> {
            // LambDef = LambDef_ => ActionFn(526);
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym0.third;
            val __nt = __action526(state, __sym0);
            symbols.add(Triple(__start, GrammarSymbol.Variant17(__nt), __end))
            return 1 to 99
        }
        196 -> {
            // LambDef_ = "lambda", COMMA<LambdaParameter>, ":", Test => ActionFn(111);
            val __sym3 = symbols.popSymbol()
            val __sym2 = symbols.popSymbol()
            val __sym1 = symbols.popSymbol()
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym3.third;
            val __nt = __action111(state, __sym0, __sym1, __sym2, __sym3);
            symbols.add(Triple(__start, GrammarSymbol.Variant39(__nt), __end))
            return 4 to 100
        }
        197 -> {
            // LambdaParameter = LambdaParameter_ => ActionFn(529);
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym0.third;
            val __nt = __action529(state, __sym0);
            symbols.add(Triple(__start, GrammarSymbol.Variant13(__nt), __end))
            return 1 to 101
        }
        198 -> {
            // LambdaParameter? = LambdaParameter => ActionFn(232);
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym0.third;
            val __nt = __action232(state, __sym0);
            symbols.add(Triple(__start, GrammarSymbol.Variant37(__nt), __end))
            return 1 to 102
        }
        199 -> {
            // LambdaParameter? =  => ActionFn(233);
            val __start = lookaheadStart ?: symbols.lastOrNull()?.third ?: 0;
            val __end = __start;
            val __nt = __action233(state, __start, __end);
            symbols.add(Triple(__start, GrammarSymbol.Variant37(__nt), __end))
            return 0 to 102
        }
        200 -> {
            // LambdaParameter_ = "/" => ActionFn(14);
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym0.third;
            val __nt = __action14(state, __sym0);
            symbols.add(Triple(__start, GrammarSymbol.Variant38(__nt), __end))
            return 1 to 103
        }
        201 -> {
            // LambdaParameter_ = AssignIdentP<AstNoPayload>, "=", Test => ActionFn(15);
            val __sym2 = symbols.popSymbol()
            val __sym1 = symbols.popSymbol()
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym2.third;
            val __nt = __action15(state, __sym0, __sym1, __sym2);
            symbols.add(Triple(__start, GrammarSymbol.Variant38(__nt), __end))
            return 3 to 103
        }
        202 -> {
            // LambdaParameter_ = AssignIdentP<AstNoPayload> => ActionFn(16);
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym0.third;
            val __nt = __action16(state, __sym0);
            symbols.add(Triple(__start, GrammarSymbol.Variant38(__nt), __end))
            return 1 to 103
        }
        203 -> {
            // LambdaParameter_ = "*", AssignIdentP<AstNoPayload> => ActionFn(17);
            val __sym1 = symbols.popSymbol()
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym1.third;
            val __nt = __action17(state, __sym0, __sym1);
            symbols.add(Triple(__start, GrammarSymbol.Variant38(__nt), __end))
            return 2 to 103
        }
        204 -> {
            // LambdaParameter_ = "*" => ActionFn(18);
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym0.third;
            val __nt = __action18(state, __sym0);
            symbols.add(Triple(__start, GrammarSymbol.Variant38(__nt), __end))
            return 1 to 103
        }
        205 -> {
            // LambdaParameter_ = "**", AssignIdentP<AstNoPayload> => ActionFn(19);
            val __sym1 = symbols.popSymbol()
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym1.third;
            val __nt = __action19(state, __sym0, __sym1);
            symbols.add(Triple(__start, GrammarSymbol.Variant38(__nt), __end))
            return 2 to 103
        }
        206 -> {
            // ListComp = ListComp_ => ActionFn(527);
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym0.third;
            val __nt = __action527(state, __sym0);
            symbols.add(Triple(__start, GrammarSymbol.Variant17(__nt), __end))
            return 1 to 104
        }
        207 -> {
            // ListComp_ = "[", Test, CompClause, "]" => ActionFn(100);
            val __sym3 = symbols.popSymbol()
            val __sym2 = symbols.popSymbol()
            val __sym1 = symbols.popSymbol()
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym3.third;
            val __nt = __action100(state, __sym0, __sym1, __sym2, __sym3);
            symbols.add(Triple(__start, GrammarSymbol.Variant39(__nt), __end))
            return 4 to 105
        }
        208 -> {
            // LoadStmt = LoadStmt_ => ActionFn(536);
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym0.third;
            val __nt = __action536(state, __sym0);
            symbols.add(Triple(__start, GrammarSymbol.Variant9(__nt), __end))
            return 1 to 106
        }
        209 -> {
            // LoadStmtBindingName = "IDENTIFIER", "=" => ActionFn(568);
            val __sym1 = symbols.popSymbol()
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym1.third;
            val __nt = __action568(state, __sym0, __sym1);
            symbols.add(Triple(__start, GrammarSymbol.Variant44(__nt), __end))
            return 2 to 107
        }
        210 -> {
            // LoadStmtBindingName? = LoadStmtBindingName => ActionFn(166);
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym0.third;
            val __nt = __action166(state, __sym0);
            symbols.add(Triple(__start, GrammarSymbol.Variant45(__nt), __end))
            return 1 to 108
        }
        211 -> {
            // LoadStmtBindingName? =  => ActionFn(167);
            val __start = lookaheadStart ?: symbols.lastOrNull()?.third ?: 0;
            val __end = __start;
            val __nt = __action167(state, __start, __end);
            symbols.add(Triple(__start, GrammarSymbol.Variant45(__nt), __end))
            return 0 to 108
        }
        212 -> {
            // LoadStmtSyms = LoadStmtBindingName, "STRING" => ActionFn(571);
            val __sym1 = symbols.popSymbol()
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym1.third;
            val __nt = __action571(state, __sym0, __sym1);
            symbols.add(Triple(__start, GrammarSymbol.Variant21(__nt), __end))
            return 2 to 109
        }
        213 -> {
            // LoadStmtSyms = "STRING" => ActionFn(572);
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym0.third;
            val __nt = __action572(state, __sym0);
            symbols.add(Triple(__start, GrammarSymbol.Variant21(__nt), __end))
            return 1 to 109
        }
        214 -> {
            // LoadStmt_ = "load", "(", "STRING", ")" => ActionFn(573);
            val __sym3 = symbols.popSymbol()
            val __sym2 = symbols.popSymbol()
            val __sym1 = symbols.popSymbol()
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym3.third;
            val __nt = __action573(state, __sym0, __sym1, __sym2, __sym3);
            symbols.add(Triple(__start, GrammarSymbol.Variant28(__nt), __end))
            return 4 to 110
        }
        215 -> {
            // LoadStmt_ = "load", "(", "STRING", Comma, LoadStmtSyms, ")" => ActionFn(574);
            val __sym5 = symbols.popSymbol()
            val __sym4 = symbols.popSymbol()
            val __sym3 = symbols.popSymbol()
            val __sym2 = symbols.popSymbol()
            val __sym1 = symbols.popSymbol()
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym5.third;
            val __nt = __action574(state, __sym0, __sym1, __sym2, __sym3, __sym4, __sym5);
            symbols.add(Triple(__start, GrammarSymbol.Variant28(__nt), __end))
            return 6 to 110
        }
        216 -> {
            // LoadStmt_ = "load", "(", "STRING", Comma, ")" => ActionFn(575);
            val __sym4 = symbols.popSymbol()
            val __sym3 = symbols.popSymbol()
            val __sym2 = symbols.popSymbol()
            val __sym1 = symbols.popSymbol()
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym4.third;
            val __nt = __action575(state, __sym0, __sym1, __sym2, __sym3, __sym4);
            symbols.add(Triple(__start, GrammarSymbol.Variant28(__nt), __end))
            return 5 to 110
        }
        217 -> {
            // LoadStmt_ = "load", "(", "STRING", Comma, (<LoadStmtSyms> <Comma>)+, LoadStmtSyms, ")" => ActionFn(576);
            val __sym6 = symbols.popSymbol()
            val __sym5 = symbols.popSymbol()
            val __sym4 = symbols.popSymbol()
            val __sym3 = symbols.popSymbol()
            val __sym2 = symbols.popSymbol()
            val __sym1 = symbols.popSymbol()
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym6.third;
            val __nt = __action576(state, __sym0, __sym1, __sym2, __sym3, __sym4, __sym5, __sym6);
            symbols.add(Triple(__start, GrammarSymbol.Variant28(__nt), __end))
            return 7 to 110
        }
        218 -> {
            // LoadStmt_ = "load", "(", "STRING", Comma, (<LoadStmtSyms> <Comma>)+, ")" => ActionFn(577);
            val __sym5 = symbols.popSymbol()
            val __sym4 = symbols.popSymbol()
            val __sym3 = symbols.popSymbol()
            val __sym2 = symbols.popSymbol()
            val __sym1 = symbols.popSymbol()
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym5.third;
            val __nt = __action577(state, __sym0, __sym1, __sym2, __sym3, __sym4, __sym5);
            symbols.add(Triple(__start, GrammarSymbol.Variant28(__nt), __end))
            return 6 to 110
        }
        219 -> {
            // NotTest = "not", NotTest => ActionFn(471);
            val __sym1 = symbols.popSymbol()
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym1.third;
            val __nt = __action471(state, __sym0, __sym1);
            symbols.add(Triple(__start, GrammarSymbol.Variant17(__nt), __end))
            return 2 to 111
        }
        220 -> {
            // NotTest = CompTest => ActionFn(117);
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym0.third;
            val __nt = __action117(state, __sym0);
            symbols.add(Triple(__start, GrammarSymbol.Variant17(__nt), __end))
            return 1 to 111
        }
        221 -> {
            // Operand = IdentP<AstNoPayload> => ActionFn(472);
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym0.third;
            val __nt = __action472(state, __sym0);
            symbols.add(Triple(__start, GrammarSymbol.Variant17(__nt), __end))
            return 1 to 112
        }
        222 -> {
            // Operand = "INTEGER" => ActionFn(570);
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym0.third;
            val __nt = __action570(state, __sym0);
            symbols.add(Triple(__start, GrammarSymbol.Variant17(__nt), __end))
            return 1 to 112
        }
        223 -> {
            // Operand = "FLOAT" => ActionFn(563);
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym0.third;
            val __nt = __action563(state, __sym0);
            symbols.add(Triple(__start, GrammarSymbol.Variant17(__nt), __end))
            return 1 to 112
        }
        224 -> {
            // Operand = "STRING" => ActionFn(578);
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym0.third;
            val __nt = __action578(state, __sym0);
            symbols.add(Triple(__start, GrammarSymbol.Variant17(__nt), __end))
            return 1 to 112
        }
        225 -> {
            // Operand = "..." => ActionFn(476);
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym0.third;
            val __nt = __action476(state, __sym0);
            symbols.add(Triple(__start, GrammarSymbol.Variant17(__nt), __end))
            return 1 to 112
        }
        226 -> {
            // Operand = "[", COMMA<Test>, "]" => ActionFn(477);
            val __sym2 = symbols.popSymbol()
            val __sym1 = symbols.popSymbol()
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym2.third;
            val __nt = __action477(state, __sym0, __sym1, __sym2);
            symbols.add(Triple(__start, GrammarSymbol.Variant17(__nt), __end))
            return 3 to 112
        }
        227 -> {
            // Operand = ListComp => ActionFn(93);
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym0.third;
            val __nt = __action93(state, __sym0);
            symbols.add(Triple(__start, GrammarSymbol.Variant17(__nt), __end))
            return 1 to 112
        }
        228 -> {
            // Operand = "{", COMMA<DictEntry>, "}" => ActionFn(478);
            val __sym2 = symbols.popSymbol()
            val __sym1 = symbols.popSymbol()
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym2.third;
            val __nt = __action478(state, __sym0, __sym1, __sym2);
            symbols.add(Triple(__start, GrammarSymbol.Variant17(__nt), __end))
            return 3 to 112
        }
        229 -> {
            // Operand = DictComp => ActionFn(95);
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym0.third;
            val __nt = __action95(state, __sym0);
            symbols.add(Triple(__start, GrammarSymbol.Variant17(__nt), __end))
            return 1 to 112
        }
        230 -> {
            // Operand = "(", TestList, ")" => ActionFn(559);
            val __sym2 = symbols.popSymbol()
            val __sym1 = symbols.popSymbol()
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym2.third;
            val __nt = __action559(state, __sym0, __sym1, __sym2);
            symbols.add(Triple(__start, GrammarSymbol.Variant17(__nt), __end))
            return 3 to 112
        }
        231 -> {
            // Operand = "(", ")" => ActionFn(560);
            val __sym1 = symbols.popSymbol()
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym1.third;
            val __nt = __action560(state, __sym0, __sym1);
            symbols.add(Triple(__start, GrammarSymbol.Variant17(__nt), __end))
            return 2 to 112
        }
        232 -> {
            // Operand = "FSTRING" => ActionFn(564);
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym0.third;
            val __nt = __action564(state, __sym0);
            symbols.add(Triple(__start, GrammarSymbol.Variant17(__nt), __end))
            return 1 to 112
        }
        233 -> {
            // OptionalSlice = ":", Test => ActionFn(81);
            val __sym1 = symbols.popSymbol()
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym1.third;
            val __nt = __action81(state, __sym0, __sym1);
            symbols.add(Triple(__start, GrammarSymbol.Variant17(__nt), __end))
            return 2 to 113
        }
        234 -> {
            // OrTest = OrTest, "or", AndTest => ActionFn(481);
            val __sym2 = symbols.popSymbol()
            val __sym1 = symbols.popSymbol()
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym2.third;
            val __nt = __action481(state, __sym0, __sym1, __sym2);
            symbols.add(Triple(__start, GrammarSymbol.Variant17(__nt), __end))
            return 3 to 114
        }
        235 -> {
            // OrTest = AndTest => ActionFn(113);
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym0.third;
            val __nt = __action113(state, __sym0);
            symbols.add(Triple(__start, GrammarSymbol.Variant17(__nt), __end))
            return 1 to 114
        }
        236 -> {
            // PrimaryExpr = PrimaryExpr, ".", "IDENTIFIER" => ActionFn(569);
            val __sym2 = symbols.popSymbol()
            val __sym1 = symbols.popSymbol()
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym2.third;
            val __nt = __action569(state, __sym0, __sym1, __sym2);
            symbols.add(Triple(__start, GrammarSymbol.Variant17(__nt), __end))
            return 3 to 115
        }
        237 -> {
            // PrimaryExpr = PrimaryExpr, "(", COMMA<ArgumentP<AstNoPayload>>, ")" => ActionFn(483);
            val __sym3 = symbols.popSymbol()
            val __sym2 = symbols.popSymbol()
            val __sym1 = symbols.popSymbol()
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym3.third;
            val __nt = __action483(state, __sym0, __sym1, __sym2, __sym3);
            symbols.add(Triple(__start, GrammarSymbol.Variant17(__nt), __end))
            return 4 to 115
        }
        238 -> {
            // PrimaryExpr = PrimaryExpr, "[", Test, ":", Test, ":", Test, "]" => ActionFn(484);
            val __sym7 = symbols.popSymbol()
            val __sym6 = symbols.popSymbol()
            val __sym5 = symbols.popSymbol()
            val __sym4 = symbols.popSymbol()
            val __sym3 = symbols.popSymbol()
            val __sym2 = symbols.popSymbol()
            val __sym1 = symbols.popSymbol()
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym7.third;
            val __nt = __action484(state, __sym0, __sym1, __sym2, __sym3, __sym4, __sym5, __sym6, __sym7);
            symbols.add(Triple(__start, GrammarSymbol.Variant17(__nt), __end))
            return 8 to 115
        }
        239 -> {
            // PrimaryExpr = PrimaryExpr, "[", Test, ":", Test, ":", "]" => ActionFn(485);
            val __sym6 = symbols.popSymbol()
            val __sym5 = symbols.popSymbol()
            val __sym4 = symbols.popSymbol()
            val __sym3 = symbols.popSymbol()
            val __sym2 = symbols.popSymbol()
            val __sym1 = symbols.popSymbol()
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym6.third;
            val __nt = __action485(state, __sym0, __sym1, __sym2, __sym3, __sym4, __sym5, __sym6);
            symbols.add(Triple(__start, GrammarSymbol.Variant17(__nt), __end))
            return 7 to 115
        }
        240 -> {
            // PrimaryExpr = PrimaryExpr, "[", Test, ":", Test, "]" => ActionFn(486);
            val __sym5 = symbols.popSymbol()
            val __sym4 = symbols.popSymbol()
            val __sym3 = symbols.popSymbol()
            val __sym2 = symbols.popSymbol()
            val __sym1 = symbols.popSymbol()
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym5.third;
            val __nt = __action486(state, __sym0, __sym1, __sym2, __sym3, __sym4, __sym5);
            symbols.add(Triple(__start, GrammarSymbol.Variant17(__nt), __end))
            return 6 to 115
        }
        241 -> {
            // PrimaryExpr = PrimaryExpr, "[", Test, ":", ":", Test, "]" => ActionFn(487);
            val __sym6 = symbols.popSymbol()
            val __sym5 = symbols.popSymbol()
            val __sym4 = symbols.popSymbol()
            val __sym3 = symbols.popSymbol()
            val __sym2 = symbols.popSymbol()
            val __sym1 = symbols.popSymbol()
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym6.third;
            val __nt = __action487(state, __sym0, __sym1, __sym2, __sym3, __sym4, __sym5, __sym6);
            symbols.add(Triple(__start, GrammarSymbol.Variant17(__nt), __end))
            return 7 to 115
        }
        242 -> {
            // PrimaryExpr = PrimaryExpr, "[", Test, ":", ":", "]" => ActionFn(488);
            val __sym5 = symbols.popSymbol()
            val __sym4 = symbols.popSymbol()
            val __sym3 = symbols.popSymbol()
            val __sym2 = symbols.popSymbol()
            val __sym1 = symbols.popSymbol()
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym5.third;
            val __nt = __action488(state, __sym0, __sym1, __sym2, __sym3, __sym4, __sym5);
            symbols.add(Triple(__start, GrammarSymbol.Variant17(__nt), __end))
            return 6 to 115
        }
        243 -> {
            // PrimaryExpr = PrimaryExpr, "[", Test, ":", "]" => ActionFn(489);
            val __sym4 = symbols.popSymbol()
            val __sym3 = symbols.popSymbol()
            val __sym2 = symbols.popSymbol()
            val __sym1 = symbols.popSymbol()
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym4.third;
            val __nt = __action489(state, __sym0, __sym1, __sym2, __sym3, __sym4);
            symbols.add(Triple(__start, GrammarSymbol.Variant17(__nt), __end))
            return 5 to 115
        }
        244 -> {
            // PrimaryExpr = PrimaryExpr, "[", ":", Test, ":", Test, "]" => ActionFn(490);
            val __sym6 = symbols.popSymbol()
            val __sym5 = symbols.popSymbol()
            val __sym4 = symbols.popSymbol()
            val __sym3 = symbols.popSymbol()
            val __sym2 = symbols.popSymbol()
            val __sym1 = symbols.popSymbol()
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym6.third;
            val __nt = __action490(state, __sym0, __sym1, __sym2, __sym3, __sym4, __sym5, __sym6);
            symbols.add(Triple(__start, GrammarSymbol.Variant17(__nt), __end))
            return 7 to 115
        }
        245 -> {
            // PrimaryExpr = PrimaryExpr, "[", ":", Test, ":", "]" => ActionFn(491);
            val __sym5 = symbols.popSymbol()
            val __sym4 = symbols.popSymbol()
            val __sym3 = symbols.popSymbol()
            val __sym2 = symbols.popSymbol()
            val __sym1 = symbols.popSymbol()
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym5.third;
            val __nt = __action491(state, __sym0, __sym1, __sym2, __sym3, __sym4, __sym5);
            symbols.add(Triple(__start, GrammarSymbol.Variant17(__nt), __end))
            return 6 to 115
        }
        246 -> {
            // PrimaryExpr = PrimaryExpr, "[", ":", Test, "]" => ActionFn(492);
            val __sym4 = symbols.popSymbol()
            val __sym3 = symbols.popSymbol()
            val __sym2 = symbols.popSymbol()
            val __sym1 = symbols.popSymbol()
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym4.third;
            val __nt = __action492(state, __sym0, __sym1, __sym2, __sym3, __sym4);
            symbols.add(Triple(__start, GrammarSymbol.Variant17(__nt), __end))
            return 5 to 115
        }
        247 -> {
            // PrimaryExpr = PrimaryExpr, "[", ":", ":", Test, "]" => ActionFn(493);
            val __sym5 = symbols.popSymbol()
            val __sym4 = symbols.popSymbol()
            val __sym3 = symbols.popSymbol()
            val __sym2 = symbols.popSymbol()
            val __sym1 = symbols.popSymbol()
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym5.third;
            val __nt = __action493(state, __sym0, __sym1, __sym2, __sym3, __sym4, __sym5);
            symbols.add(Triple(__start, GrammarSymbol.Variant17(__nt), __end))
            return 6 to 115
        }
        248 -> {
            // PrimaryExpr = PrimaryExpr, "[", ":", ":", "]" => ActionFn(494);
            val __sym4 = symbols.popSymbol()
            val __sym3 = symbols.popSymbol()
            val __sym2 = symbols.popSymbol()
            val __sym1 = symbols.popSymbol()
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym4.third;
            val __nt = __action494(state, __sym0, __sym1, __sym2, __sym3, __sym4);
            symbols.add(Triple(__start, GrammarSymbol.Variant17(__nt), __end))
            return 5 to 115
        }
        249 -> {
            // PrimaryExpr = PrimaryExpr, "[", ":", "]" => ActionFn(495);
            val __sym3 = symbols.popSymbol()
            val __sym2 = symbols.popSymbol()
            val __sym1 = symbols.popSymbol()
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym3.third;
            val __nt = __action495(state, __sym0, __sym1, __sym2, __sym3);
            symbols.add(Triple(__start, GrammarSymbol.Variant17(__nt), __end))
            return 4 to 115
        }
        250 -> {
            // PrimaryExpr = PrimaryExpr, "[", Test, "]" => ActionFn(496);
            val __sym3 = symbols.popSymbol()
            val __sym2 = symbols.popSymbol()
            val __sym1 = symbols.popSymbol()
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym3.third;
            val __nt = __action496(state, __sym0, __sym1, __sym2, __sym3);
            symbols.add(Triple(__start, GrammarSymbol.Variant17(__nt), __end))
            return 4 to 115
        }
        251 -> {
            // PrimaryExpr = PrimaryExpr, "[", Test, ",", Test, "]" => ActionFn(497);
            val __sym5 = symbols.popSymbol()
            val __sym4 = symbols.popSymbol()
            val __sym3 = symbols.popSymbol()
            val __sym2 = symbols.popSymbol()
            val __sym1 = symbols.popSymbol()
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym5.third;
            val __nt = __action497(state, __sym0, __sym1, __sym2, __sym3, __sym4, __sym5);
            symbols.add(Triple(__start, GrammarSymbol.Variant17(__nt), __end))
            return 6 to 115
        }
        252 -> {
            // PrimaryExpr = Operand => ActionFn(80);
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym0.third;
            val __nt = __action80(state, __sym0);
            symbols.add(Triple(__start, GrammarSymbol.Variant17(__nt), __end))
            return 1 to 115
        }
        253 -> {
            // ProductExpr = ProductExpr, "*", FactorExpr => ActionFn(498);
            val __sym2 = symbols.popSymbol()
            val __sym1 = symbols.popSymbol()
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym2.third;
            val __nt = __action498(state, __sym0, __sym1, __sym2);
            symbols.add(Triple(__start, GrammarSymbol.Variant17(__nt), __end))
            return 3 to 116
        }
        254 -> {
            // ProductExpr = ProductExpr, "%", FactorExpr => ActionFn(499);
            val __sym2 = symbols.popSymbol()
            val __sym1 = symbols.popSymbol()
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym2.third;
            val __nt = __action499(state, __sym0, __sym1, __sym2);
            symbols.add(Triple(__start, GrammarSymbol.Variant17(__nt), __end))
            return 3 to 116
        }
        255 -> {
            // ProductExpr = ProductExpr, "/", FactorExpr => ActionFn(500);
            val __sym2 = symbols.popSymbol()
            val __sym1 = symbols.popSymbol()
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym2.third;
            val __nt = __action500(state, __sym0, __sym1, __sym2);
            symbols.add(Triple(__start, GrammarSymbol.Variant17(__nt), __end))
            return 3 to 116
        }
        256 -> {
            // ProductExpr = ProductExpr, "//", FactorExpr => ActionFn(501);
            val __sym2 = symbols.popSymbol()
            val __sym1 = symbols.popSymbol()
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym2.third;
            val __nt = __action501(state, __sym0, __sym1, __sym2);
            symbols.add(Triple(__start, GrammarSymbol.Variant17(__nt), __end))
            return 3 to 116
        }
        257 -> {
            // ProductExpr = FactorExpr => ActionFn(144);
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym0.third;
            val __nt = __action144(state, __sym0);
            symbols.add(Triple(__start, GrammarSymbol.Variant17(__nt), __end))
            return 1 to 116
        }
        258 -> {
            // ReturnType = "->", TypeExprP<AstNoPayload> => ActionFn(11);
            val __sym1 = symbols.popSymbol()
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym1.third;
            val __nt = __action11(state, __sym0, __sym1);
            symbols.add(Triple(__start, GrammarSymbol.Variant46(__nt), __end))
            return 2 to 117
        }
        259 -> {
            // ReturnType =  => ActionFn(12);
            val __start = lookaheadStart ?: symbols.lastOrNull()?.third ?: 0;
            val __end = __start;
            val __nt = __action12(state, __start, __end);
            symbols.add(Triple(__start, GrammarSymbol.Variant46(__nt), __end))
            return 0 to 117
        }
        260 -> {
            // ShiftExpr = ShiftExpr, "<<", ArithExpr => ActionFn(502);
            val __sym2 = symbols.popSymbol()
            val __sym1 = symbols.popSymbol()
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym2.third;
            val __nt = __action502(state, __sym0, __sym1, __sym2);
            symbols.add(Triple(__start, GrammarSymbol.Variant17(__nt), __end))
            return 3 to 118
        }
        261 -> {
            // ShiftExpr = ShiftExpr, ">>", ArithExpr => ActionFn(503);
            val __sym2 = symbols.popSymbol()
            val __sym1 = symbols.popSymbol()
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym2.third;
            val __nt = __action503(state, __sym0, __sym1, __sym2);
            symbols.add(Triple(__start, GrammarSymbol.Variant17(__nt), __end))
            return 3 to 118
        }
        262 -> {
            // ShiftExpr = ArithExpr => ActionFn(136);
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym0.third;
            val __nt = __action136(state, __sym0);
            symbols.add(Triple(__start, GrammarSymbol.Variant17(__nt), __end))
            return 1 to 118
        }
        263 -> {
            // SimpleStmt<SmallStmt> = SmallStmt, ";", "\n" => ActionFn(504);
            val __sym2 = symbols.popSymbol()
            val __sym1 = symbols.popSymbol()
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym2.third;
            val __nt = __action504(state, __sym0, __sym1, __sym2);
            symbols.add(Triple(__start, GrammarSymbol.Variant9(__nt), __end))
            return 3 to 119
        }
        264 -> {
            // SimpleStmt<SmallStmt> = SmallStmt, (";" <SmallStmt>)+, ";", "\n" => ActionFn(505);
            val __sym3 = symbols.popSymbol()
            val __sym2 = symbols.popSymbol()
            val __sym1 = symbols.popSymbol()
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym3.third;
            val __nt = __action505(state, __sym0, __sym1, __sym2, __sym3);
            symbols.add(Triple(__start, GrammarSymbol.Variant9(__nt), __end))
            return 4 to 119
        }
        265 -> {
            // SimpleStmt<SmallStmt> = SmallStmt, "\n" => ActionFn(506);
            val __sym1 = symbols.popSymbol()
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym1.third;
            val __nt = __action506(state, __sym0, __sym1);
            symbols.add(Triple(__start, GrammarSymbol.Variant9(__nt), __end))
            return 2 to 119
        }
        266 -> {
            // SimpleStmt<SmallStmt> = SmallStmt, (";" <SmallStmt>)+, "\n" => ActionFn(507);
            val __sym2 = symbols.popSymbol()
            val __sym1 = symbols.popSymbol()
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym2.third;
            val __nt = __action507(state, __sym0, __sym1, __sym2);
            symbols.add(Triple(__start, GrammarSymbol.Variant9(__nt), __end))
            return 3 to 119
        }
        267 -> {
            // SmallStmt = "return", TestList => ActionFn(561);
            val __sym1 = symbols.popSymbol()
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym1.third;
            val __nt = __action561(state, __sym0, __sym1);
            symbols.add(Triple(__start, GrammarSymbol.Variant9(__nt), __end))
            return 2 to 120
        }
        268 -> {
            // SmallStmt = "return" => ActionFn(562);
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym0.third;
            val __nt = __action562(state, __sym0);
            symbols.add(Triple(__start, GrammarSymbol.Variant9(__nt), __end))
            return 1 to 120
        }
        269 -> {
            // SmallStmt = "break" => ActionFn(509);
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym0.third;
            val __nt = __action509(state, __sym0);
            symbols.add(Triple(__start, GrammarSymbol.Variant9(__nt), __end))
            return 1 to 120
        }
        270 -> {
            // SmallStmt = "continue" => ActionFn(510);
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym0.third;
            val __nt = __action510(state, __sym0);
            symbols.add(Triple(__start, GrammarSymbol.Variant9(__nt), __end))
            return 1 to 120
        }
        271 -> {
            // SmallStmt = "pass" => ActionFn(511);
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym0.third;
            val __nt = __action511(state, __sym0);
            symbols.add(Triple(__start, GrammarSymbol.Variant9(__nt), __end))
            return 1 to 120
        }
        272 -> {
            // SmallStmt = AssignStmt => ActionFn(48);
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym0.third;
            val __nt = __action48(state, __sym0);
            symbols.add(Triple(__start, GrammarSymbol.Variant9(__nt), __end))
            return 1 to 120
        }
        273 -> {
            // SmallStmt = ExprStmt => ActionFn(49);
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym0.third;
            val __nt = __action49(state, __sym0);
            symbols.add(Triple(__start, GrammarSymbol.Variant9(__nt), __end))
            return 1 to 120
        }
        274 -> {
            // SmallStmt = LoadStmt => ActionFn(50);
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym0.third;
            val __nt = __action50(state, __sym0);
            symbols.add(Triple(__start, GrammarSymbol.Variant9(__nt), __end))
            return 1 to 120
        }
        275 -> {
            // Starlark =  => ActionFn(512);
            val __start = lookaheadStart ?: symbols.lastOrNull()?.third ?: 0;
            val __end = __start;
            val __nt = __action512(state, __start, __end);
            symbols.add(Triple(__start, GrammarSymbol.Variant9(__nt), __end))
            return 0 to 121
        }
        276 -> {
            // Starlark = (<StmtP<AstNoPayload>> "\n"*)+ => ActionFn(513);
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym0.third;
            val __nt = __action513(state, __sym0);
            symbols.add(Triple(__start, GrammarSymbol.Variant9(__nt), __end))
            return 1 to 121
        }
        277 -> {
            // Starlark = "\n"+ => ActionFn(514);
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym0.third;
            val __nt = __action514(state, __sym0);
            symbols.add(Triple(__start, GrammarSymbol.Variant9(__nt), __end))
            return 1 to 121
        }
        278 -> {
            // Starlark = "\n"+, (<StmtP<AstNoPayload>> "\n"*)+ => ActionFn(515);
            val __sym1 = symbols.popSymbol()
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym1.third;
            val __nt = __action515(state, __sym0, __sym1);
            symbols.add(Triple(__start, GrammarSymbol.Variant9(__nt), __end))
            return 2 to 121
        }
        279 -> {
            // StmtP<AstNoPayload> = DefStmt => ActionFn(32);
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym0.third;
            val __nt = __action32(state, __sym0);
            symbols.add(Triple(__start, GrammarSymbol.Variant9(__nt), __end))
            return 1 to 122
        }
        280 -> {
            // StmtP<AstNoPayload> = IfStmt => ActionFn(33);
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym0.third;
            val __nt = __action33(state, __sym0);
            symbols.add(Triple(__start, GrammarSymbol.Variant9(__nt), __end))
            return 1 to 122
        }
        281 -> {
            // StmtP<AstNoPayload> = ForStmt => ActionFn(34);
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym0.third;
            val __nt = __action34(state, __sym0);
            symbols.add(Triple(__start, GrammarSymbol.Variant9(__nt), __end))
            return 1 to 122
        }
        282 -> {
            // StmtP<AstNoPayload> = SimpleStmt<SmallStmt> => ActionFn(35);
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym0.third;
            val __nt = __action35(state, __sym0);
            symbols.add(Triple(__start, GrammarSymbol.Variant9(__nt), __end))
            return 1 to 122
        }
        283 -> {
            // Suite = SimpleStmt<SmallStmt> => ActionFn(30);
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym0.third;
            val __nt = __action30(state, __sym0);
            symbols.add(Triple(__start, GrammarSymbol.Variant9(__nt), __end))
            return 1 to 123
        }
        284 -> {
            // Suite = "\n"+, "INDENT", (<StmtP<AstNoPayload>> "\n"*)+, "DEDENT" => ActionFn(516);
            val __sym3 = symbols.popSymbol()
            val __sym2 = symbols.popSymbol()
            val __sym1 = symbols.popSymbol()
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym3.third;
            val __nt = __action516(state, __sym0, __sym1, __sym2, __sym3);
            symbols.add(Triple(__start, GrammarSymbol.Variant9(__nt), __end))
            return 4 to 123
        }
        285 -> {
            // Suite = "\n"+, "INDENT", "\n"+, (<StmtP<AstNoPayload>> "\n"*)+, "DEDENT" => ActionFn(517);
            val __sym4 = symbols.popSymbol()
            val __sym3 = symbols.popSymbol()
            val __sym2 = symbols.popSymbol()
            val __sym1 = symbols.popSymbol()
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym4.third;
            val __nt = __action517(state, __sym0, __sym1, __sym2, __sym3, __sym4);
            symbols.add(Triple(__start, GrammarSymbol.Variant9(__nt), __end))
            return 5 to 123
        }
        286 -> {
            // Test = OrTest, "if", OrTest, "else", Test => ActionFn(518);
            val __sym4 = symbols.popSymbol()
            val __sym3 = symbols.popSymbol()
            val __sym2 = symbols.popSymbol()
            val __sym1 = symbols.popSymbol()
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym4.third;
            val __nt = __action518(state, __sym0, __sym1, __sym2, __sym3, __sym4);
            symbols.add(Triple(__start, GrammarSymbol.Variant17(__nt), __end))
            return 5 to 124
        }
        287 -> {
            // Test = OrTest => ActionFn(108);
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym0.third;
            val __nt = __action108(state, __sym0);
            symbols.add(Triple(__start, GrammarSymbol.Variant17(__nt), __end))
            return 1 to 124
        }
        288 -> {
            // Test = LambDef => ActionFn(109);
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym0.third;
            val __nt = __action109(state, __sym0);
            symbols.add(Triple(__start, GrammarSymbol.Variant17(__nt), __end))
            return 1 to 124
        }
        289 -> {
            // Test? = Test => ActionFn(161);
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym0.third;
            val __nt = __action161(state, __sym0);
            symbols.add(Triple(__start, GrammarSymbol.Variant7(__nt), __end))
            return 1 to 125
        }
        290 -> {
            // Test? =  => ActionFn(162);
            val __start = lookaheadStart ?: symbols.lastOrNull()?.third ?: 0;
            val __end = __start;
            val __nt = __action162(state, __start, __end);
            symbols.add(Triple(__start, GrammarSymbol.Variant7(__nt), __end))
            return 0 to 125
        }
        291 -> {
            // TestList = L<Test> => ActionFn(74);
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym0.third;
            val __nt = __action74(state, __sym0);
            symbols.add(Triple(__start, GrammarSymbol.Variant17(__nt), __end))
            return 1 to 126
        }
        292 -> {
            // TestList? = TestList => ActionFn(177);
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym0.third;
            val __nt = __action177(state, __sym0);
            symbols.add(Triple(__start, GrammarSymbol.Variant7(__nt), __end))
            return 1 to 127
        }
        293 -> {
            // TestList? =  => ActionFn(178);
            val __start = lookaheadStart ?: symbols.lastOrNull()?.third ?: 0;
            val __end = __start;
            val __nt = __action178(state, __start, __end);
            symbols.add(Triple(__start, GrammarSymbol.Variant7(__nt), __end))
            return 0 to 127
        }
        294 -> {
            // Type = ":", TypeExprP<AstNoPayload> => ActionFn(28);
            val __sym1 = symbols.popSymbol()
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym1.third;
            val __nt = __action28(state, __sym0, __sym1);
            symbols.add(Triple(__start, GrammarSymbol.Variant46(__nt), __end))
            return 2 to 128
        }
        295 -> {
            // Type =  => ActionFn(29);
            val __start = lookaheadStart ?: symbols.lastOrNull()?.third ?: 0;
            val __end = __start;
            val __nt = __action29(state, __start, __end);
            symbols.add(Triple(__start, GrammarSymbol.Variant46(__nt), __end))
            return 0 to 128
        }
        296 -> {
            // TypeExprP<AstNoPayload> = Test => ActionFn(27);
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym0.third;
            val __nt = __action27(state, __sym0);
            symbols.add(Triple(__start, GrammarSymbol.Variant47(__nt), __end))
            return 1 to 129
        }
        298 -> {
            // float = "FLOAT" => ActionFn(519);
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym0.third;
            val __nt = __action519(state, __sym0);
            symbols.add(Triple(__start, GrammarSymbol.Variant48(__nt), __end))
            return 1 to 131
        }
        299 -> {
            // fstring = "FSTRING" => ActionFn(520);
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym0.third;
            val __nt = __action520(state, __sym0);
            symbols.add(Triple(__start, GrammarSymbol.Variant49(__nt), __end))
            return 1 to 132
        }
        300 -> {
            // identifier = "IDENTIFIER" => ActionFn(521);
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym0.third;
            val __nt = __action521(state, __sym0);
            symbols.add(Triple(__start, GrammarSymbol.Variant44(__nt), __end))
            return 1 to 133
        }
        301 -> {
            // integer = "INTEGER" => ActionFn(522);
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym0.third;
            val __nt = __action522(state, __sym0);
            symbols.add(Triple(__start, GrammarSymbol.Variant50(__nt), __end))
            return 1 to 134
        }
        302 -> {
            // string = "STRING" => ActionFn(523);
            val __sym0 = symbols.popSymbol()
            val __start = __sym0.first;
            val __end = __sym0.third;
            val __nt = __action523(state, __sym0);
            symbols.add(Triple(__start, GrammarSymbol.Variant44(__nt), __end))
            return 1 to 135
        }
        }
        error("Unknown rule ID: $ruleId")
    }

internal fun __action0(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): Spanned<StmtP<AstNoPayload>> {
    val __0 = sym0.second.asStmtSpanned()
    val __ret = __0
    return __ret
}

internal fun __action1(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>) = run {
        val l = sym0.second.asIntValue()
        val e = sym1.second.asTokenInt()
        val r = sym2.second.asIntValue()
        val __ret = e.ast(l, r)
    __ret
}

internal fun __action2(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>) = run {
        val l = sym0.second.asIntValue()
        val e = sym1.second.asDoubleValue()
        val r = sym2.second.asIntValue()
        val __ret = e.ast(l, r)
    __ret
}

internal fun __action3(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>) = run {
        val l = sym0.second.asIntValue()
        val e = sym1.second.asStringValue()
        val r = sym2.second.asIntValue()
        val __ret = e.ast(l, r)
    __ret
}

internal fun __action4(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>) = run {
        val l = sym0.second.asIntValue()
        val e = sym1.second.asTokenFString()
        val r = sym2.second.asIntValue()
        val __ret = fstring(e, l, r, state)
    __ret
}

internal fun __action5(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>) = run {
        val l = sym0.second.asIntValue()
        val e = sym1.second.asStringValue()
        val r = sym2.second.asIntValue()
        val __ret = e.ast(l, r)
    __ret
}

internal fun __action6(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>) = run {
        val id = sym0.second.asStringSpanned()
        val __ret = Spanned(span = id.span, node = IdentP<AstNoPayload>(ident = id.node, payload = Unit))
    __ret
}

internal fun __action7(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>) = run {
        val id = sym0.second.asStringSpanned()
        val __ret = Spanned(span = id.span, node = AssignIdentP<AstNoPayload>(ident = id.node, payload = Unit))
    __ret
}

internal fun __action8(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>) = run {
        val l = sym1.second.asIntValue()
        val s = sym2.second.asStmtSpannedList()
        val r = sym3.second.asIntValue()
        val __ret = statements(s, l, r)
    __ret
}

internal fun __action9(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): Spanned<StmtP<AstNoPayload>> {
    val __0 = sym0.second.asStmtSpanned()
    val __ret = __0
    return __ret
}

internal fun __action10(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>, sym4: Triple<Int, GrammarSymbol, Int>, sym5: Triple<Int, GrammarSymbol, Int>, sym6: Triple<Int, GrammarSymbol, Int>, sym7: Triple<Int, GrammarSymbol, Int>): StmtP<AstNoPayload> {
    val name = sym1.second.asAssignIdentSpanned()
    val params = sym3.second.asParameterSpannedList()
    val returnType = sym5.second.asNullableTypeExprSpanned()
    val stmts = sym7.second.asStmtSpanned()
    val __ret = StmtP.Def<AstNoPayload>(DefP(name, params, returnType, stmts, AstNoPayload))
    return __ret
}

internal fun __action11(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>): Spanned<TypeExprP<AstNoPayload>>? {
    val __0 = sym1.second.asTypeExprSpanned()
    val __ret = (__0)
    return __ret
}

internal fun __action12(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, __lookbehind: Int, __lookahead: Int): Spanned<TypeExprP<AstNoPayload>>? {
    val __ret = null
    return __ret
}

internal fun __action13(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): Spanned<ParameterP<AstNoPayload>> {
    val __0 = sym0.second.asParameterSpanned()
    val __ret = __0
    return __ret
}

internal fun __action14(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): ParameterP<AstNoPayload> {
    val __0 = sym0.second.asToken()
    val __ret = ParameterP.Slash<AstNoPayload>()
    return __ret
}

internal fun __action15(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>): ParameterP<AstNoPayload> {
    val n = sym0.second.asAssignIdentSpanned()
    val e = sym2.second.asExprSpanned()
    val __ret = ParameterP.Normal<AstNoPayload>(n, null, (e))
    return __ret
}

internal fun __action16(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): ParameterP<AstNoPayload> {
    val __0 = sym0.second.asAssignIdentSpanned()
    val __ret = ParameterP.Normal<AstNoPayload>(__0, null, null)
    return __ret
}

internal fun __action17(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>): ParameterP<AstNoPayload> {
    val __0 = sym1.second.asAssignIdentSpanned()
    val __ret = ParameterP.Args<AstNoPayload>(__0, null)
    return __ret
}

internal fun __action18(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): ParameterP<AstNoPayload> {
    val __0 = sym0.second.asToken()
    val __ret = ParameterP.NoArgs<AstNoPayload>()
    return __ret
}

internal fun __action19(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>): ParameterP<AstNoPayload> {
    val __0 = sym1.second.asAssignIdentSpanned()
    val __ret = ParameterP.KwArgs<AstNoPayload>(__0, null)
    return __ret
}

internal fun __action20(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): Spanned<ParameterP<AstNoPayload>> {
    val __0 = sym0.second.asParameterSpanned()
    val __ret = __0
    return __ret
}

internal fun __action21(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): ParameterP<AstNoPayload> {
    val __0 = sym0.second.asToken()
    val __ret = ParameterP.Slash<AstNoPayload>()
    return __ret
}

internal fun __action22(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>): ParameterP<AstNoPayload> {
    val n = sym0.second.asAssignIdentSpanned()
    val t = sym1.second.asNullableTypeExprSpanned()
    val e = sym3.second.asExprSpanned()
    val __ret = ParameterP.Normal<AstNoPayload>(n, t, (e))
    return __ret
}

internal fun __action23(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>): ParameterP<AstNoPayload> {
    val __0 = sym0.second.asAssignIdentSpanned()
    val __1 = sym1.second.asNullableTypeExprSpanned()
    val __ret = ParameterP.Normal<AstNoPayload>(__0, __1, null)
    return __ret
}

internal fun __action24(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>): ParameterP<AstNoPayload> {
    val __0 = sym1.second.asAssignIdentSpanned()
    val __1 = sym2.second.asNullableTypeExprSpanned()
    val __ret = ParameterP.Args<AstNoPayload>(__0, __1)
    return __ret
}

internal fun __action25(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): ParameterP<AstNoPayload> {
    val __0 = sym0.second.asToken()
    val __ret = ParameterP.NoArgs<AstNoPayload>()
    return __ret
}

internal fun __action26(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>): ParameterP<AstNoPayload> {
    val __0 = sym1.second.asAssignIdentSpanned()
    val __1 = sym2.second.asNullableTypeExprSpanned()
    val __ret = ParameterP.KwArgs<AstNoPayload>(__0, __1)
    return __ret
}

internal fun __action27(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): Spanned<TypeExprP<AstNoPayload>> {
    val __0 = sym0.second.asExprSpanned()
    val __ret = dialectCheckType(state, __0)
    return __ret
}

internal fun __action28(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>): Spanned<TypeExprP<AstNoPayload>>? {
    val __0 = sym1.second.asTypeExprSpanned()
    val __ret = (__0)
    return __ret
}

internal fun __action29(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, __lookbehind: Int, __lookahead: Int): Spanned<TypeExprP<AstNoPayload>>? {
    val __ret = null
    return __ret
}

internal fun __action30(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): Spanned<StmtP<AstNoPayload>> {
    val __0 = sym0.second.asStmtSpanned()
    val __ret = __0
    return __ret
}

internal fun __action31(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>, sym4: Triple<Int, GrammarSymbol, Int>, sym5: Triple<Int, GrammarSymbol, Int>, sym6: Triple<Int, GrammarSymbol, Int>) = run {
        val l = sym2.second.asIntValue()
        val v = sym4.second.asStmtSpannedList()
        val r = sym5.second.asIntValue()
        val __ret = statements(v, l, r)
    __ret
}

internal fun __action32(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): Spanned<StmtP<AstNoPayload>> {
    val __0 = sym0.second.asStmtSpanned()
    val __ret = __0
    return __ret
}

internal fun __action33(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): Spanned<StmtP<AstNoPayload>> {
    val __0 = sym0.second.asStmtSpanned()
    val __ret = __0
    return __ret
}

internal fun __action34(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): Spanned<StmtP<AstNoPayload>> {
    val __0 = sym0.second.asStmtSpanned()
    val __ret = __0
    return __ret
}

internal fun __action35(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): Spanned<StmtP<AstNoPayload>> {
    val __0 = sym0.second.asStmtSpanned()
    val __ret = __0
    return __ret
}

internal fun __action36(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): Spanned<StmtP<AstNoPayload>> {
    val __0 = sym0.second.asStmtSpanned()
    val __ret = __0
    return __ret
}

internal fun __action37(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>) = run {
        val c = sym0.second.asExprSpanned()
        val s = sym2.second.asStmtSpanned()
        val el = sym3.second.asNullableStmtSpanned()
        val __ret = run {
        if (el == null) StmtP.If<AstNoPayload>(c, s) else StmtP.IfElse<AstNoPayload>(c, s, el)
        }
    __ret
}

internal fun __action38(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): Spanned<StmtP<AstNoPayload>> {
    val __0 = sym0.second.asStmtSpanned()
    val __ret = __0
    return __ret
}

internal fun __action39(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>): StmtP<AstNoPayload> {
    val __0 = sym1.second.asStmtValue()
    val __ret = __0
    return __ret
}

internal fun __action40(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>): Spanned<StmtP<AstNoPayload>> {
    val __0 = sym1.second.asStmtSpanned()
    val __ret = __0
    return __ret
}

internal fun __action41(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>): Spanned<StmtP<AstNoPayload>> {
    val __0 = sym2.second.asStmtSpanned()
    val __ret = __0
    return __ret
}

internal fun __action42(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): Spanned<StmtP<AstNoPayload>> {
    val __0 = sym0.second.asStmtSpanned()
    val __ret = __0
    return __ret
}

internal fun __action43(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>, sym4: Triple<Int, GrammarSymbol, Int>, sym5: Triple<Int, GrammarSymbol, Int>): StmtP<AstNoPayload> {
    val varTarget = sym1.second.asExprSpanned()
    val over = sym3.second.asExprSpanned()
    val body = sym5.second.asStmtSpanned()
    val __ret = StmtP.For<AstNoPayload>(ForP(variable = checkAssign(state.codemap, varTarget), over = over, body = body))
    return __ret
}

internal fun __action44(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>) = run {
        val l = sym0.second.asIntValue()
        val e = sym2.second.asNullableExprSpanned()
        val r = sym3.second.asIntValue()
        val __ret = StmtP.Return<AstNoPayload>(e).ast(l, r)
    __ret
}

internal fun __action45(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.second.asIntValue()
        val __1 = sym2.second.asIntValue()
        val __ret = StmtP.Break<AstNoPayload>().ast(__0, __1)
    __ret
}

internal fun __action46(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.second.asIntValue()
        val __1 = sym2.second.asIntValue()
        val __ret = StmtP.Continue<AstNoPayload>().ast(__0, __1)
    __ret
}

internal fun __action47(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.second.asIntValue()
        val __1 = sym2.second.asIntValue()
        val __ret = StmtP.Pass<AstNoPayload>().ast(__0, __1)
    __ret
}

internal fun __action48(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): Spanned<StmtP<AstNoPayload>> {
    val __0 = sym0.second.asStmtSpanned()
    val __ret = __0
    return __ret
}

internal fun __action49(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): Spanned<StmtP<AstNoPayload>> {
    val __0 = sym0.second.asStmtSpanned()
    val __ret = __0
    return __ret
}

internal fun __action50(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): Spanned<StmtP<AstNoPayload>> {
    val __0 = sym0.second.asStmtSpanned()
    val __ret = __0
    return __ret
}

internal fun __action51(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): AssignOp? {
    val __0 = sym0.second.asToken()
    val __ret = null
    return __ret
}

internal fun __action52(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): AssignOp? {
    val __0 = sym0.second.asToken()
    val __ret = (AssignOp.Add)
    return __ret
}

internal fun __action53(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): AssignOp? {
    val __0 = sym0.second.asToken()
    val __ret = (AssignOp.Subtract)
    return __ret
}

internal fun __action54(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): AssignOp? {
    val __0 = sym0.second.asToken()
    val __ret = (AssignOp.Multiply)
    return __ret
}

internal fun __action55(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): AssignOp? {
    val __0 = sym0.second.asToken()
    val __ret = (AssignOp.Divide)
    return __ret
}

internal fun __action56(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): AssignOp? {
    val __0 = sym0.second.asToken()
    val __ret = (AssignOp.FloorDivide)
    return __ret
}

internal fun __action57(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): AssignOp? {
    val __0 = sym0.second.asToken()
    val __ret = (AssignOp.Percent)
    return __ret
}

internal fun __action58(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): AssignOp? {
    val __0 = sym0.second.asToken()
    val __ret = (AssignOp.BitAnd)
    return __ret
}

internal fun __action59(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): AssignOp? {
    val __0 = sym0.second.asToken()
    val __ret = (AssignOp.BitOr)
    return __ret
}

internal fun __action60(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): AssignOp? {
    val __0 = sym0.second.asToken()
    val __ret = (AssignOp.BitXor)
    return __ret
}

internal fun __action61(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): AssignOp? {
    val __0 = sym0.second.asToken()
    val __ret = (AssignOp.LeftShift)
    return __ret
}

internal fun __action62(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): AssignOp? {
    val __0 = sym0.second.asToken()
    val __ret = (AssignOp.RightShift)
    return __ret
}

internal fun __action63(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): Spanned<StmtP<AstNoPayload>> {
    val __0 = sym0.second.asStmtSpanned()
    val __ret = __0
    return __ret
}

internal fun __action64(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>): StmtP<AstNoPayload> {
    val lhs = sym0.second.asExprSpanned()
    val ty = sym1.second.asNullableTypeExprSpanned()
    val op = sym2.second.asNullableAssignOp()
    val rhs = sym3.second.asExprSpanned()
    val __ret = checkAssignment(state.codemap, lhs, ty, op, rhs)
    return __ret
}

internal fun __action65(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): Spanned<StmtP<AstNoPayload>> {
    val __0 = sym0.second.asStmtSpanned()
    val __ret = __0
    return __ret
}

internal fun __action66(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): StmtP<AstNoPayload> {
    val __0 = sym0.second.asExprSpanned()
    val __ret = StmtP.Expression(__0)
    return __ret
}

internal fun __action67(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.second.asIntValue()
        val __1 = sym2.second.asIntValue()
        val __ret = Comma().ast(__0, __1)
    __ret
}

internal fun __action68(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): Spanned<StmtP<AstNoPayload>> {
    val __0 = sym0.second.asStmtSpanned()
    val __ret = __0
    return __ret
}

internal fun __action69(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>) = run {
        val module = sym2.second.asStringSpanned()
        val __ret = checkLoad0(module, state)
    __ret
}

internal fun __action70(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>, sym4: Triple<Int, GrammarSymbol, Int>, sym5: Triple<Int, GrammarSymbol, Int>, sym6: Triple<Int, GrammarSymbol, Int>) = run {
        val module = sym2.second.asStringSpanned()
        val args = sym4.second.asLoadPairList()
        val last = sym5.second.asNullableAssignIdentStringPair()
        val __ret = checkLoad(module, args, last, state)
    __ret
}

internal fun __action71(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>): Spanned<String> {
    val __0 = sym0.second.asStringSpanned()
    val __ret = __0
    return __ret
}

internal fun __action72(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>) = run {
        val id = sym0.second.asNullableStringSpanned()
        val n = sym1.second.asStringSpanned()
        val __ret = run {
        val id = id ?: n;
        Pair(Spanned(span = id.span, node = AssignIdentP<AstNoPayload>(ident = id.node, payload = Unit)), n)
        }
    __ret
}

internal fun __action73(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): Spanned<ExprP<AstNoPayload>> {
    val __0 = sym0.second.asExprSpanned()
    val __ret = __0
    return __ret
}

internal fun __action74(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): Spanned<ExprP<AstNoPayload>> {
    val __0 = sym0.second.asExprSpanned()
    val __ret = __0
    return __ret
}

internal fun __action75(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>, sym4: Triple<Int, GrammarSymbol, Int>) = run {
        val l = sym0.second.asIntValue()
        val e = sym1.second.asExprSpanned()
        val i = sym3.second.asStringSpanned()
        val r = sym4.second.asIntValue()
        val __ret = ExprP.Dot(e, i).ast(l, r)
    __ret
}

internal fun __action76(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>, sym4: Triple<Int, GrammarSymbol, Int>, sym5: Triple<Int, GrammarSymbol, Int>) = run {
        val l = sym0.second.asIntValue()
        val e = sym1.second.asExprSpanned()
        val a = sym3.second.asArgumentSpannedList()
        val r = sym5.second.asIntValue()
        val __ret = checkCall(e, a, state).ast(l, r)
    __ret
}

internal fun __action77(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>, sym4: Triple<Int, GrammarSymbol, Int>, sym5: Triple<Int, GrammarSymbol, Int>, sym6: Triple<Int, GrammarSymbol, Int>, sym7: Triple<Int, GrammarSymbol, Int>, sym8: Triple<Int, GrammarSymbol, Int>) = run {
        val l = sym0.second.asIntValue()
        val e = sym1.second.asExprSpanned()
        val i1 = sym3.second.asNullableExprSpanned()
        val i2 = sym5.second.asNullableExprSpanned()
        // sym6 is the `(":" <Test?>)?` slot. The Rust action body for this production is
        // `i3.unwrap_or(None).map(|x| Box::new(x))` — collapse the outer Option by treating
        // None and Some(None) both as "no third slice argument," and Some(Some(v)) as v.
        // [NullableOption.unwrapOrNull] is the matching Kotlin idiom.
        val i3Wrapped = sym6.second.asNullableOptionExprSpanned()
        val i3 = i3Wrapped.unwrapOrNull()
        val r = sym8.second.asIntValue()
        val __ret = run {
              ExprP.Slice<AstNoPayload>(e, i1, i2, i3)
                  .ast(l, r)
        }
    __ret
}

internal fun __action78(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>, sym4: Triple<Int, GrammarSymbol, Int>, sym5: Triple<Int, GrammarSymbol, Int>) = run {
        val l = sym0.second.asIntValue()
        val e = sym1.second.asExprSpanned()
        val i = sym3.second.asExprSpanned()
        val r = sym5.second.asIntValue()
        val __ret = ExprP.Index(e, i).ast(l, r)
    __ret
}

internal fun __action79(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>, sym4: Triple<Int, GrammarSymbol, Int>, sym5: Triple<Int, GrammarSymbol, Int>, sym6: Triple<Int, GrammarSymbol, Int>, sym7: Triple<Int, GrammarSymbol, Int>) = run {
        val l = sym0.second.asIntValue()
        val e = sym1.second.asExprSpanned()
        val i0 = sym3.second.asExprSpanned()
        val i1 = sym5.second.asExprSpanned()
        val r = sym7.second.asIntValue()
        val __ret = ExprP.Index2(e, i0, i1).ast(l, r)
    __ret
}

internal fun __action80(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): Spanned<ExprP<AstNoPayload>> {
    val __0 = sym0.second.asExprSpanned()
    val __ret = __0
    return __ret
}

internal fun __action81(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>): Spanned<ExprP<AstNoPayload>> {
    val __0 = sym1.second.asExprSpanned()
    val __ret = __0
    return __ret
}

internal fun __action82(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): Spanned<ArgumentP<AstNoPayload>> {
    val __0 = sym0.second.asArgumentSpanned()
    val __ret = __0
    return __ret
}

internal fun __action83(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): ArgumentP<AstNoPayload> {
    val __0 = sym0.second.asExprSpanned()
    val __ret = ArgumentP.Positional<AstNoPayload>(__0)
    return __ret
}

internal fun __action84(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.second.asStringSpanned()
        val __1 = sym2.second.asExprSpanned()
        val __ret = ArgumentP.Named<AstNoPayload>(__0, __1)
    __ret
}

internal fun __action85(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>): ArgumentP<AstNoPayload> {
    val __0 = sym1.second.asExprSpanned()
    val __ret = ArgumentP.Args<AstNoPayload>(__0)
    return __ret
}

internal fun __action86(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>): ArgumentP<AstNoPayload> {
    val __0 = sym1.second.asExprSpanned()
    val __ret = ArgumentP.KwArgs<AstNoPayload>(__0)
    return __ret
}

internal fun __action87(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>) = run {
        val l = sym0.second.asIntValue()
        val i = sym1.second.asIdentSpanned()
        val r = sym2.second.asIntValue()
        val __ret = ExprP.Identifier<AstNoPayload>(i).ast(l, r)
    __ret
}

internal fun __action88(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>) = run {
        val l = sym0.second.asIntValue()
        val i = sym1.second.asTokenIntSpanned()
        val r = sym2.second.asIntValue()
        val __ret = ExprP.Literal<AstNoPayload>(AstLiteral.Int(i)).ast(l, r)
    __ret
}

internal fun __action89(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>) = run {
        val l = sym0.second.asIntValue()
        val f = sym1.second.asDoubleSpanned()
        val r = sym2.second.asIntValue()
        val __ret = ExprP.Literal<AstNoPayload>(AstLiteral.Float(f)).ast(l, r)
    __ret
}

internal fun __action90(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>) = run {
        val l = sym0.second.asIntValue()
        val s = sym1.second.asStringSpanned()
        val r = sym2.second.asIntValue()
        val __ret = ExprP.Literal<AstNoPayload>(AstLiteral.String(s)).ast(l, r)
    __ret
}

internal fun __action91(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>) = run {
        val l = sym0.second.asIntValue()
        val r = sym2.second.asIntValue()
        val __ret = ExprP.Literal<AstNoPayload>(AstLiteral.Ellipsis()).ast(l, r)
    __ret
}

internal fun __action92(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>, sym4: Triple<Int, GrammarSymbol, Int>) = run {
        val l = sym0.second.asIntValue()
        val e = sym2.second.asExprSpannedList()
        val r = sym4.second.asIntValue()
        val __ret = ExprP.List<AstNoPayload>(e).ast(l, r)
    __ret
}

internal fun __action93(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): Spanned<ExprP<AstNoPayload>> {
    val __0 = sym0.second.asExprSpanned()
    val __ret = __0
    return __ret
}

internal fun __action94(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>, sym4: Triple<Int, GrammarSymbol, Int>) = run {
        val l = sym0.second.asIntValue()
        val e = sym2.second.asExprPairList()
        val r = sym4.second.asIntValue()
        val __ret = ExprP.Dict<AstNoPayload>(e).ast(l, r)
    __ret
}

internal fun __action95(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): Spanned<ExprP<AstNoPayload>> {
    val __0 = sym0.second.asExprSpanned()
    val __ret = __0
    return __ret
}

internal fun __action96(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>, sym4: Triple<Int, GrammarSymbol, Int>) = run {
        val l = sym0.second.asIntValue()
        val e = sym2.second.asNullableExprSpanned()
        val r = sym4.second.asIntValue()
        val __ret = e ?: ExprP.Tuple<AstNoPayload>(emptyList<Nothing>()).ast(l, r)
    __ret
}

internal fun __action97(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>) = run {
        val l = sym0.second.asIntValue()
        val f = sym1.second.asFStringSpanned()
        val r = sym2.second.asIntValue()
        val __ret = ExprP.FString<AstNoPayload>(f).ast(l, r)
    __ret
}

internal fun __action98(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>): Pair<Spanned<ExprP<AstNoPayload>>, Spanned<ExprP<AstNoPayload>>> {
    val __0 = sym0.second.asExprSpanned()
    val __1 = sym2.second.asExprSpanned()
    val __ret = Pair(__0, __1)
    return __ret
}

internal fun __action99(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): Spanned<ExprP<AstNoPayload>> {
    val __0 = sym0.second.asExprSpanned()
    val __ret = __0
    return __ret
}

internal fun __action100(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>): ExprP<AstNoPayload> {
    val t = sym1.second.asExprSpanned()
    val c = sym2.second.asForClauseClauseListPair()
    val __ret = ExprP.ListComprehension<AstNoPayload>(t, c.first, c.second)
    return __ret
}

internal fun __action101(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): Spanned<ExprP<AstNoPayload>> {
    val __0 = sym0.second.asExprSpanned()
    val __ret = __0
    return __ret
}

internal fun __action102(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>): ExprP<AstNoPayload> {
    val k = sym1.second.asExprPair()
    val c = sym2.second.asForClauseClauseListPair()
    val __ret = ExprP.DictComprehension<AstNoPayload>(k.first, k.second, c.first, c.second)
    return __ret
}

internal fun __action103(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>) = run {
        val x = sym0.second.asForClauseValue()
        val xs = sym1.second.asClauseList()
        val __ret = Pair(x, xs)
    __ret
}

internal fun __action104(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): ClauseP<AstNoPayload> {
    val __0 = sym0.second.asForClauseValue()
    val __ret = ClauseP.For<AstNoPayload>(__0)
    return __ret
}

internal fun __action105(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>): ClauseP<AstNoPayload> {
    val __0 = sym1.second.asExprSpanned()
    val __ret = ClauseP.If<AstNoPayload>(__0)
    return __ret
}

internal fun __action106(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>): ForClauseP<AstNoPayload> {
    val varTarget = sym1.second.asExprSpanned()
    val over = sym3.second.asExprSpanned()
    val __ret = ForClauseP<AstNoPayload>(variable = checkAssign(state.codemap, varTarget), over = over)
    return __ret
}

internal fun __action107(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>, sym4: Triple<Int, GrammarSymbol, Int>, sym5: Triple<Int, GrammarSymbol, Int>, sym6: Triple<Int, GrammarSymbol, Int>) = run {
        val l = sym0.second.asIntValue()
        val e1 = sym1.second.asExprSpanned()
        val t = sym3.second.asExprSpanned()
        val e2 = sym5.second.asExprSpanned()
        val r = sym6.second.asIntValue()
        val __ret = ExprP.If<AstNoPayload>(t, e1, e2).ast(l, r)
    __ret
}

internal fun __action108(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): Spanned<ExprP<AstNoPayload>> {
    val __0 = sym0.second.asExprSpanned()
    val __ret = __0
    return __ret
}

internal fun __action109(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): Spanned<ExprP<AstNoPayload>> {
    val __0 = sym0.second.asExprSpanned()
    val __ret = __0
    return __ret
}

internal fun __action110(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): Spanned<ExprP<AstNoPayload>> {
    val __0 = sym0.second.asExprSpanned()
    val __ret = __0
    return __ret
}

internal fun __action111(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>): ExprP<AstNoPayload> {
    val p = sym1.second.asParameterSpannedList()
    val e = sym3.second.asExprSpanned()
    val __ret = run {
    ExprP.Lambda<AstNoPayload>(LambdaP<AstNoPayload>(p, e, AstNoPayload))
    }
    return __ret
}

internal fun __action112(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>, sym4: Triple<Int, GrammarSymbol, Int>) = run {
        val l = sym0.second.asIntValue()
        val e1 = sym1.second.asExprSpanned()
        val e2 = sym3.second.asExprSpanned()
        val r = sym4.second.asIntValue()
        val __ret = ExprP.Op(e1, BinOp.Or, e2).ast(l, r)
    __ret
}

internal fun __action113(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): Spanned<ExprP<AstNoPayload>> {
    val __0 = sym0.second.asExprSpanned()
    val __ret = __0
    return __ret
}

internal fun __action114(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>, sym4: Triple<Int, GrammarSymbol, Int>) = run {
        val l = sym0.second.asIntValue()
        val e1 = sym1.second.asExprSpanned()
        val e2 = sym3.second.asExprSpanned()
        val r = sym4.second.asIntValue()
        val __ret = ExprP.Op(e1, BinOp.And, e2).ast(l, r)
    __ret
}

internal fun __action115(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): Spanned<ExprP<AstNoPayload>> {
    val __0 = sym0.second.asExprSpanned()
    val __ret = __0
    return __ret
}

internal fun __action116(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>) = run {
        val l = sym0.second.asIntValue()
        val e = sym2.second.asExprSpanned()
        val r = sym3.second.asIntValue()
        val __ret = ExprP.Not(e).ast(l, r)
    __ret
}

internal fun __action117(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): Spanned<ExprP<AstNoPayload>> {
    val __0 = sym0.second.asExprSpanned()
    val __ret = __0
    return __ret
}

internal fun __action118(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>, sym4: Triple<Int, GrammarSymbol, Int>) = run {
        val l = sym0.second.asIntValue()
        val e1 = sym1.second.asExprSpanned()
        val e2 = sym3.second.asExprSpanned()
        val r = sym4.second.asIntValue()
        val __ret = ExprP.Op(e1, BinOp.Equal, e2).ast(l, r)
    __ret
}

internal fun __action119(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>, sym4: Triple<Int, GrammarSymbol, Int>) = run {
        val l = sym0.second.asIntValue()
        val e1 = sym1.second.asExprSpanned()
        val e2 = sym3.second.asExprSpanned()
        val r = sym4.second.asIntValue()
        val __ret = ExprP.Op(e1, BinOp.NotEqual, e2).ast(l, r)
    __ret
}

internal fun __action120(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>, sym4: Triple<Int, GrammarSymbol, Int>) = run {
        val l = sym0.second.asIntValue()
        val e1 = sym1.second.asExprSpanned()
        val e2 = sym3.second.asExprSpanned()
        val r = sym4.second.asIntValue()
        val __ret = ExprP.Op(e1, BinOp.Less, e2).ast(l, r)
    __ret
}

internal fun __action121(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>, sym4: Triple<Int, GrammarSymbol, Int>) = run {
        val l = sym0.second.asIntValue()
        val e1 = sym1.second.asExprSpanned()
        val e2 = sym3.second.asExprSpanned()
        val r = sym4.second.asIntValue()
        val __ret = ExprP.Op(e1, BinOp.Greater, e2).ast(l, r)
    __ret
}

internal fun __action122(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>, sym4: Triple<Int, GrammarSymbol, Int>) = run {
        val l = sym0.second.asIntValue()
        val e1 = sym1.second.asExprSpanned()
        val e2 = sym3.second.asExprSpanned()
        val r = sym4.second.asIntValue()
        val __ret = ExprP.Op(e1, BinOp.LessOrEqual, e2).ast(l, r)
    __ret
}

internal fun __action123(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>, sym4: Triple<Int, GrammarSymbol, Int>) = run {
        val l = sym0.second.asIntValue()
        val e1 = sym1.second.asExprSpanned()
        val e2 = sym3.second.asExprSpanned()
        val r = sym4.second.asIntValue()
        val __ret = run {
    ExprP.Op(e1, BinOp.GreaterOrEqual, e2)
                          .ast(l, r)
        }
    __ret
}

internal fun __action124(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>, sym4: Triple<Int, GrammarSymbol, Int>) = run {
        val l = sym0.second.asIntValue()
        val e1 = sym1.second.asExprSpanned()
        val e2 = sym3.second.asExprSpanned()
        val r = sym4.second.asIntValue()
        val __ret = ExprP.Op(e1, BinOp.In, e2).ast(l, r)
    __ret
}

internal fun __action125(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>, sym4: Triple<Int, GrammarSymbol, Int>, sym5: Triple<Int, GrammarSymbol, Int>) = run {
        val l = sym0.second.asIntValue()
        val e1 = sym1.second.asExprSpanned()
        val e2 = sym4.second.asExprSpanned()
        val r = sym5.second.asIntValue()
        val __ret = ExprP.Op(e1, BinOp.NotIn, e2).ast(l, r)
    __ret
}

internal fun __action126(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): Spanned<ExprP<AstNoPayload>> {
    val __0 = sym0.second.asExprSpanned()
    val __ret = __0
    return __ret
}

internal fun __action127(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): Spanned<ExprP<AstNoPayload>> {
    val __0 = sym0.second.asExprSpanned()
    val __ret = __0
    return __ret
}

internal fun __action128(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>, sym4: Triple<Int, GrammarSymbol, Int>) = run {
        val l = sym0.second.asIntValue()
        val e1 = sym1.second.asExprSpanned()
        val e2 = sym3.second.asExprSpanned()
        val r = sym4.second.asIntValue()
        val __ret = ExprP.Op(e1, BinOp.BitOr, e2).ast(l, r)
    __ret
}

internal fun __action129(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): Spanned<ExprP<AstNoPayload>> {
    val __0 = sym0.second.asExprSpanned()
    val __ret = __0
    return __ret
}

internal fun __action130(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>, sym4: Triple<Int, GrammarSymbol, Int>) = run {
        val l = sym0.second.asIntValue()
        val e1 = sym1.second.asExprSpanned()
        val e2 = sym3.second.asExprSpanned()
        val r = sym4.second.asIntValue()
        val __ret = ExprP.Op(e1, BinOp.BitXor, e2).ast(l, r)
    __ret
}

internal fun __action131(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): Spanned<ExprP<AstNoPayload>> {
    val __0 = sym0.second.asExprSpanned()
    val __ret = __0
    return __ret
}

internal fun __action132(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>, sym4: Triple<Int, GrammarSymbol, Int>) = run {
        val l = sym0.second.asIntValue()
        val e1 = sym1.second.asExprSpanned()
        val e2 = sym3.second.asExprSpanned()
        val r = sym4.second.asIntValue()
        val __ret = ExprP.Op(e1, BinOp.BitAnd, e2).ast(l, r)
    __ret
}

internal fun __action133(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): Spanned<ExprP<AstNoPayload>> {
    val __0 = sym0.second.asExprSpanned()
    val __ret = __0
    return __ret
}

internal fun __action134(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>, sym4: Triple<Int, GrammarSymbol, Int>) = run {
        val l = sym0.second.asIntValue()
        val e1 = sym1.second.asExprSpanned()
        val e2 = sym3.second.asExprSpanned()
        val r = sym4.second.asIntValue()
        val __ret = ExprP.Op(e1, BinOp.LeftShift, e2).ast(l, r)
    __ret
}

internal fun __action135(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>, sym4: Triple<Int, GrammarSymbol, Int>) = run {
        val l = sym0.second.asIntValue()
        val e1 = sym1.second.asExprSpanned()
        val e2 = sym3.second.asExprSpanned()
        val r = sym4.second.asIntValue()
        val __ret = ExprP.Op(e1, BinOp.RightShift, e2).ast(l, r)
    __ret
}

internal fun __action136(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): Spanned<ExprP<AstNoPayload>> {
    val __0 = sym0.second.asExprSpanned()
    val __ret = __0
    return __ret
}

internal fun __action137(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>, sym4: Triple<Int, GrammarSymbol, Int>) = run {
        val l = sym0.second.asIntValue()
        val e1 = sym1.second.asExprSpanned()
        val e2 = sym3.second.asExprSpanned()
        val r = sym4.second.asIntValue()
        val __ret = ExprP.Op(e1, BinOp.Add, e2).ast(l, r)
    __ret
}

internal fun __action138(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>, sym4: Triple<Int, GrammarSymbol, Int>) = run {
        val l = sym0.second.asIntValue()
        val e1 = sym1.second.asExprSpanned()
        val e2 = sym3.second.asExprSpanned()
        val r = sym4.second.asIntValue()
        val __ret = ExprP.Op(e1, BinOp.Subtract, e2).ast(l, r)
    __ret
}

internal fun __action139(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): Spanned<ExprP<AstNoPayload>> {
    val __0 = sym0.second.asExprSpanned()
    val __ret = __0
    return __ret
}

internal fun __action140(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>, sym4: Triple<Int, GrammarSymbol, Int>) = run {
        val l = sym0.second.asIntValue()
        val e1 = sym1.second.asExprSpanned()
        val e2 = sym3.second.asExprSpanned()
        val r = sym4.second.asIntValue()
        val __ret = run {
    ExprP.Op(e1, BinOp.Multiply, e2)
                .ast(l, r)
        }
    __ret
}

internal fun __action141(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>, sym4: Triple<Int, GrammarSymbol, Int>) = run {
        val l = sym0.second.asIntValue()
        val e1 = sym1.second.asExprSpanned()
        val e2 = sym3.second.asExprSpanned()
        val r = sym4.second.asIntValue()
        val __ret = ExprP.Op(e1, BinOp.Percent, e2).ast(l, r)
    __ret
}

internal fun __action142(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>, sym4: Triple<Int, GrammarSymbol, Int>) = run {
        val l = sym0.second.asIntValue()
        val e1 = sym1.second.asExprSpanned()
        val e2 = sym3.second.asExprSpanned()
        val r = sym4.second.asIntValue()
        val __ret = ExprP.Op(e1, BinOp.Divide, e2).ast(l, r)
    __ret
}

internal fun __action143(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>, sym4: Triple<Int, GrammarSymbol, Int>) = run {
        val l = sym0.second.asIntValue()
        val e1 = sym1.second.asExprSpanned()
        val e2 = sym3.second.asExprSpanned()
        val r = sym4.second.asIntValue()
        val __ret = ExprP.Op(e1, BinOp.FloorDivide, e2).ast(l, r)
    __ret
}

internal fun __action144(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): Spanned<ExprP<AstNoPayload>> {
    val __0 = sym0.second.asExprSpanned()
    val __ret = __0
    return __ret
}

internal fun __action145(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>) = run {
        val l = sym0.second.asIntValue()
        val e = sym2.second.asExprSpanned()
        val r = sym3.second.asIntValue()
        val __ret = ExprP.Plus(e).ast(l, r)
    __ret
}

internal fun __action146(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>) = run {
        val l = sym0.second.asIntValue()
        val e = sym2.second.asExprSpanned()
        val r = sym3.second.asIntValue()
        val __ret = ExprP.Minus(e).ast(l, r)
    __ret
}

internal fun __action147(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>) = run {
        val l = sym0.second.asIntValue()
        val e = sym2.second.asExprSpanned()
        val r = sym3.second.asIntValue()
        val __ret = ExprP.BitNot(e).ast(l, r)
    __ret
}

internal fun __action148(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): Spanned<ExprP<AstNoPayload>> {
    val __0 = sym0.second.asExprSpanned()
    val __ret = __0
    return __ret
}

internal fun __action149(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>): List<Spanned<ParameterP<AstNoPayload>>> {
    val v0 = sym0.second.asParameterSpannedList()
    val e1 = sym1.second.asNullableParameterSpanned()
    val __ret = v0 + listOfNotNull(e1)
    return __ret
}

internal fun __action150(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>) = run {
        val l = sym0.second.asIntValue()
        val e = sym1.second.asExprValue()
        val r = sym2.second.asIntValue()
        val __ret = e.ast(l, r)
    __ret
}

internal fun __action151(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, __lookbehind: Int, __lookahead: Int): List<ClauseP<AstNoPayload>> {
    val __ret = emptyList<Nothing>()
    return __ret
}

internal fun __action152(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): List<ClauseP<AstNoPayload>> {
    val v = sym0.second.asClauseList()
    val __ret = v
    return __ret
}

internal fun __action153(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>) = run {
        val l = sym0.second.asIntValue()
        val e = sym1.second.asExprValue()
        val r = sym2.second.asIntValue()
        val __ret = e.ast(l, r)
    __ret
}

internal fun __action154(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>) = run {
        val l = sym0.second.asIntValue()
        val e = sym1.second.asExprValue()
        val r = sym2.second.asIntValue()
        val __ret = e.ast(l, r)
    __ret
}

internal fun __action155(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>): List<Pair<Spanned<ExprP<AstNoPayload>>, Spanned<ExprP<AstNoPayload>>>> {
    val v0 = sym0.second.asExprPairList()
    val e1 = sym1.second.asNullableExprPair()
    val __ret = v0 + listOfNotNull(e1)
    return __ret
}

internal fun __action156(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>): List<Spanned<ExprP<AstNoPayload>>> {
    val v0 = sym0.second.asExprSpannedList()
    val e1 = sym1.second.asNullableExprSpanned()
    val __ret = v0 + listOfNotNull(e1)
    return __ret
}

internal fun __action157(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>) = run {
        val l = sym0.second.asIntValue()
        val e = sym1.second.asArgumentValue()
        val r = sym2.second.asIntValue()
        val __ret = e.ast(l, r)
    __ret
}

internal fun __action158(
    state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState,
    sym0: Triple<Int, GrammarSymbol, Int>,
): NullableOption<Spanned<ExprP<AstNoPayload>>> {
    // Mirrors Rust `__action158`: lifts the inner `Option<AstExpr>` (Variant7-shape) into
    // `Some(Option<AstExpr>)` (Variant8-shape). The Rust source is `Some(__0)` — the outer
    // Some that distinguishes "matched, inner absent" from "did not match." Kotlin can't
    // express that with `T??` (nullables flatten), so we use [NullableOption.Present] /
    // [NullableOption.Empty]: a present-but-empty inner becomes [NullableOption.Empty],
    // a present-with-value inner becomes [NullableOption.Present].
    val __0 = sym0.second.asNullableExprSpanned()
    return if (__0 == null) NullableOption.Empty else NullableOption.Present(__0)
}

internal fun __action159(
    state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState,
    __lookbehind: Int,
    __lookahead: Int,
): NullableOption<Spanned<ExprP<AstNoPayload>>> {
    // Mirrors Rust `__action159` (`None`): the outer `Option<Option<AstExpr>>` matched
    // `None`. Carried as [NullableOption.Absent] so the consumer can distinguish "did not
    // match at all" from "matched with empty inner."
    return NullableOption.Absent
}

internal fun __action160(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>): Spanned<ExprP<AstNoPayload>>? {
    val __0 = sym1.second.asNullableExprSpanned()
    val __ret = __0
    return __ret
}

internal fun __action161(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): Spanned<ExprP<AstNoPayload>>? {
    val __0 = sym0.second.asExprSpanned()
    val __ret = (__0)
    return __ret
}

internal fun __action162(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, __lookbehind: Int, __lookahead: Int): Spanned<ExprP<AstNoPayload>>? {
    val __ret = null
    return __ret
}

internal fun __action163(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>): List<Spanned<ArgumentP<AstNoPayload>>> {
    val v0 = sym0.second.asArgumentSpannedList()
    val e1 = sym1.second.asNullableArgumentSpanned()
    val __ret = v0 + listOfNotNull(e1)
    return __ret
}

internal fun __action164(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>, sym4: Triple<Int, GrammarSymbol, Int>) = run {
        val l = sym0.second.asIntValue()
        val v = sym1.second.asExprSpannedList()
        val e = sym2.second.asExprSpanned()
        val f = sym3.second.asNullableToken()
        val r = sym4.second.asIntValue()
        val __ret = run {
            if ((f != null) || !v.isEmpty()) {
                ExprP.Tuple<AstNoPayload>(v + e)
                    .ast(l, r)
            } else {
                e
            }
        }
    __ret
}

internal fun __action165(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>, sym4: Triple<Int, GrammarSymbol, Int>) = run {
        val l = sym0.second.asIntValue()
        val v = sym1.second.asExprSpannedList()
        val e = sym2.second.asExprSpanned()
        val f = sym3.second.asNullableToken()
        val r = sym4.second.asIntValue()
        val __ret = run {
            if ((f != null) || !v.isEmpty()) {
                ExprP.Tuple<AstNoPayload>(v + e)
                    .ast(l, r)
            } else {
                e
            }
        }
    __ret
}

internal fun __action166(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): Spanned<String>? {
    val __0 = sym0.second.asStringSpanned()
    val __ret = (__0)
    return __ret
}

internal fun __action167(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, __lookbehind: Int, __lookahead: Int): Spanned<String>? {
    val __ret = null
    return __ret
}

internal fun __action168(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): Pair<Spanned<AssignIdentP<AstNoPayload>>, Spanned<String>> {
    val __0 = sym0.second.asAssignIdentStringPair()
    val __ret = (__0)
    return __ret
}

internal fun __action169(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, __lookbehind: Int, __lookahead: Int): Pair<Spanned<AssignIdentP<AstNoPayload>>, Spanned<String>>? {
    val __ret = null
    return __ret
}

internal fun __action170(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): Pair<Spanned<AssignIdentP<AstNoPayload>>, Spanned<String>> {
    val __0 = sym0.second.asAssignIdentStringPair()
    val __ret = __0
    return __ret
}

internal fun __action171(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, __lookbehind: Int, __lookahead: Int): List<Pair<Pair<Spanned<AssignIdentP<AstNoPayload>>, Spanned<String>>, Spanned<Comma>>> {
    val __ret = emptyList<Nothing>()
    return __ret
}

internal fun __action172(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): List<Pair<Pair<Spanned<AssignIdentP<AstNoPayload>>, Spanned<String>>, Spanned<Comma>>> {
    val v = sym0.second.asLoadPairList()
    val __ret = v
    return __ret
}

internal fun __action173(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>): Pair<Pair<Spanned<AssignIdentP<AstNoPayload>>, Spanned<String>>, Spanned<Comma>> {
    val __0 = sym0.second.asAssignIdentStringPair()
    val __1 = sym1.second.asCommaSpanned()
    val __ret = Pair(__0, __1)
    return __ret
}

internal fun __action174(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>) = run {
        val l = sym0.second.asIntValue()
        val e = sym1.second.asStmtValue()
        val r = sym2.second.asIntValue()
        val __ret = e.ast(l, r)
    __ret
}

internal fun __action175(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>) = run {
        val l = sym0.second.asIntValue()
        val e = sym1.second.asStmtValue()
        val r = sym2.second.asIntValue()
        val __ret = e.ast(l, r)
    __ret
}

internal fun __action176(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>) = run {
        val l = sym0.second.asIntValue()
        val e = sym1.second.asStmtValue()
        val r = sym2.second.asIntValue()
        val __ret = e.ast(l, r)
    __ret
}

internal fun __action177(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): Spanned<ExprP<AstNoPayload>>? {
    val __0 = sym0.second.asExprSpanned()
    val __ret = (__0)
    return __ret
}

internal fun __action178(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, __lookbehind: Int, __lookahead: Int): Spanned<ExprP<AstNoPayload>>? {
    val __ret = null
    return __ret
}

internal fun __action179(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>) = run {
        val l = sym0.second.asIntValue()
        val e = sym1.second.asStmtValue()
        val r = sym2.second.asIntValue()
        val __ret = e.ast(l, r)
    __ret
}

internal fun __action180(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>) = run {
        val l = sym0.second.asIntValue()
        val e = sym1.second.asStmtValue()
        val r = sym2.second.asIntValue()
        val __ret = e.ast(l, r)
    __ret
}

internal fun __action181(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): Spanned<StmtP<AstNoPayload>>? {
    val __0 = sym0.second.asStmtSpanned()
    val __ret = (__0)
    return __ret
}

internal fun __action182(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, __lookbehind: Int, __lookahead: Int): Spanned<StmtP<AstNoPayload>>? {
    val __ret = null
    return __ret
}

internal fun __action183(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>) = run {
        val l = sym0.second.asIntValue()
        val e = sym1.second.asStmtValue()
        val r = sym2.second.asIntValue()
        val __ret = e.ast(l, r)
    __ret
}

internal fun __action184(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): List<Spanned<StmtP<AstNoPayload>>> {
    val __0 = sym0.second.asStmtSpanned()
    val __ret = listOf(__0)
    return __ret
}

internal fun __action185(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>): List<Spanned<StmtP<AstNoPayload>>> {
    val v = sym0.second.asStmtSpannedList()
    val e = sym1.second.asStmtSpanned()
    val __ret = (v + e)
    return __ret
}

internal fun __action186(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): List<Token> {
    val __0 = sym0.second.asToken()
    val __ret = listOf(__0)
    return __ret
}

internal fun __action187(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>): List<Token> {
    val v = sym0.second.asTokenList()
    val e = sym1.second.asToken()
    val __ret = (v + e)
    return __ret
}

internal fun __action188(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>, sym4: Triple<Int, GrammarSymbol, Int>, sym5: Triple<Int, GrammarSymbol, Int>) = run {
        val l = sym0.second.asIntValue()
        val e = sym1.second.asStmtSpanned()
        val v = sym2.second.asStmtSpannedList()
        val r = sym4.second.asIntValue()
        val __ret = run {
            if (v.isEmpty()) {
                e
            } else {
                StmtP.Statements<AstNoPayload>((listOf(e) + v))
                    .ast(l, r)
            }
        }
    __ret
}

internal fun __action189(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>) = run {
        val l = sym0.second.asIntValue()
        val e = sym1.second.asParameterValue()
        val r = sym2.second.asIntValue()
        val __ret = e.ast(l, r)
    __ret
}

internal fun __action190(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>) = run {
        val l = sym0.second.asIntValue()
        val e = sym1.second.asParameterValue()
        val r = sym2.second.asIntValue()
        val __ret = e.ast(l, r)
    __ret
}

internal fun __action191(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>): List<Spanned<ParameterP<AstNoPayload>>> {
    val v0 = sym0.second.asParameterSpannedList()
    val e1 = sym1.second.asNullableParameterSpanned()
    val __ret = v0 + listOfNotNull(e1)
    return __ret
}

internal fun __action192(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>) = run {
        val l = sym0.second.asIntValue()
        val e = sym1.second.asStmtValue()
        val r = sym2.second.asIntValue()
        val __ret = e.ast(l, r)
    __ret
}

internal fun __action193(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, __lookbehind: Int, __lookahead: Int): List<Spanned<StmtP<AstNoPayload>>> {
    val __ret = emptyList<Nothing>()
    return __ret
}

internal fun __action194(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): List<Spanned<StmtP<AstNoPayload>>> {
    val v = sym0.second.asStmtSpannedList()
    val __ret = v
    return __ret
}

internal fun __action195(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>): Spanned<StmtP<AstNoPayload>> {
    val __0 = sym0.second.asStmtSpanned()
    val __ret = __0
    return __ret
}

internal fun __action196(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, __lookbehind: Int, __lookahead: Int): List<Token> {
    val __ret = emptyList<Nothing>()
    return __ret
}

internal fun __action197(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): List<Token> {
    val v = sym0.second.asTokenList()
    val __ret = v
    return __ret
}

internal fun __action198(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, __lookbehind: Int, __lookahead: Int): Int {
    val __ret = __lookbehind
    return __ret
}

internal fun __action199(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, __lookbehind: Int, __lookahead: Int): Int {
    val __ret = __lookahead
    return __ret
}

internal fun __action200(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): Spanned<ParameterP<AstNoPayload>>? {
    val __0 = sym0.second.asParameterSpanned()
    val __ret = (__0)
    return __ret
}

internal fun __action201(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, __lookbehind: Int, __lookahead: Int): Spanned<ParameterP<AstNoPayload>>? {
    val __ret = null
    return __ret
}

internal fun __action202(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, __lookbehind: Int, __lookahead: Int): List<Spanned<ParameterP<AstNoPayload>>> {
    val __ret = emptyList<Nothing>()
    return __ret
}

internal fun __action203(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): List<Spanned<ParameterP<AstNoPayload>>> {
    val v = sym0.second.asParameterSpannedList()
    val __ret = v
    return __ret
}

internal fun __action204(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>): Spanned<ParameterP<AstNoPayload>> {
    val __0 = sym0.second.asParameterSpanned()
    val __ret = __0
    return __ret
}

internal fun __action205(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): Token? {
    val __0 = sym0.second.asToken()
    val __ret = (__0)
    return __ret
}

internal fun __action206(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, __lookbehind: Int, __lookahead: Int): Token? {
    val __ret = null
    return __ret
}

internal fun __action207(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, __lookbehind: Int, __lookahead: Int): List<Spanned<StmtP<AstNoPayload>>> {
    val __ret = emptyList<Nothing>()
    return __ret
}

internal fun __action208(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): List<Spanned<StmtP<AstNoPayload>>> {
    val v = sym0.second.asStmtSpannedList()
    val __ret = v
    return __ret
}

internal fun __action209(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>): Spanned<StmtP<AstNoPayload>> {
    val __0 = sym1.second.asStmtSpanned()
    val __ret = __0
    return __ret
}

internal fun __action210(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): List<Pair<Pair<Spanned<AssignIdentP<AstNoPayload>>, Spanned<String>>, Spanned<Comma>>> {
    val __0 = sym0.second.asLoadPair()
    val __ret = listOf(__0)
    return __ret
}

internal fun __action211(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>): List<Pair<Pair<Spanned<AssignIdentP<AstNoPayload>>, Spanned<String>>, Spanned<Comma>>> {
    val v = sym0.second.asLoadPairList()
    val e = sym1.second.asLoadPair()
    val __ret = (v + e)
    return __ret
}

internal fun __action212(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, __lookbehind: Int, __lookahead: Int): List<Spanned<ExprP<AstNoPayload>>> {
    val __ret = emptyList<Nothing>()
    return __ret
}

internal fun __action213(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): List<Spanned<ExprP<AstNoPayload>>> {
    val v = sym0.second.asExprSpannedList()
    val __ret = v
    return __ret
}

internal fun __action214(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>): Spanned<ExprP<AstNoPayload>> {
    val __0 = sym0.second.asExprSpanned()
    val __ret = __0
    return __ret
}

internal fun __action215(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): Token? {
    val __0 = sym0.second.asToken()
    val __ret = (__0)
    return __ret
}

internal fun __action216(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, __lookbehind: Int, __lookahead: Int): Token? {
    val __ret = null
    return __ret
}

internal fun __action217(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): Spanned<ArgumentP<AstNoPayload>>? {
    val __0 = sym0.second.asArgumentSpanned()
    val __ret = (__0)
    return __ret
}

internal fun __action218(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, __lookbehind: Int, __lookahead: Int): Spanned<ArgumentP<AstNoPayload>>? {
    val __ret = null
    return __ret
}

internal fun __action219(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, __lookbehind: Int, __lookahead: Int): List<Spanned<ArgumentP<AstNoPayload>>> {
    val __ret = emptyList<Nothing>()
    return __ret
}

internal fun __action220(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): List<Spanned<ArgumentP<AstNoPayload>>> {
    val v = sym0.second.asArgumentSpannedList()
    val __ret = v
    return __ret
}

internal fun __action221(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>): Spanned<ArgumentP<AstNoPayload>> {
    val __0 = sym0.second.asArgumentSpanned()
    val __ret = __0
    return __ret
}

internal fun __action222(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, __lookbehind: Int, __lookahead: Int): List<Spanned<ExprP<AstNoPayload>>> {
    val __ret = emptyList<Nothing>()
    return __ret
}

internal fun __action223(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): List<Spanned<ExprP<AstNoPayload>>> {
    val v = sym0.second.asExprSpannedList()
    val __ret = v
    return __ret
}

internal fun __action224(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>): Spanned<ExprP<AstNoPayload>> {
    val __0 = sym0.second.asExprSpanned()
    val __ret = __0
    return __ret
}

internal fun __action225(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): Pair<Spanned<ExprP<AstNoPayload>>, Spanned<ExprP<AstNoPayload>>>? {
    val __0 = sym0.second.asExprPair()
    val __ret = (__0)
    return __ret
}

internal fun __action226(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, __lookbehind: Int, __lookahead: Int): Pair<Spanned<ExprP<AstNoPayload>>, Spanned<ExprP<AstNoPayload>>>? {
    val __ret = null
    return __ret
}

internal fun __action227(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, __lookbehind: Int, __lookahead: Int): List<Pair<Spanned<ExprP<AstNoPayload>>, Spanned<ExprP<AstNoPayload>>>> {
    val __ret = emptyList<Nothing>()
    return __ret
}

internal fun __action228(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): List<Pair<Spanned<ExprP<AstNoPayload>>, Spanned<ExprP<AstNoPayload>>>> {
    val v = sym0.second.asExprPairList()
    val __ret = v
    return __ret
}

internal fun __action229(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>): Pair<Spanned<ExprP<AstNoPayload>>, Spanned<ExprP<AstNoPayload>>> {
    val __0 = sym0.second.asExprPair()
    val __ret = __0
    return __ret
}

internal fun __action230(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): List<ClauseP<AstNoPayload>> {
    val __0 = sym0.second.asClauseValue()
    val __ret = listOf(__0)
    return __ret
}

internal fun __action231(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>): List<ClauseP<AstNoPayload>> {
    val v = sym0.second.asClauseList()
    val e = sym1.second.asClauseValue()
    val __ret = (v + e)
    return __ret
}

internal fun __action232(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): Spanned<ParameterP<AstNoPayload>>? {
    val __0 = sym0.second.asParameterSpanned()
    val __ret = (__0)
    return __ret
}

internal fun __action233(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, __lookbehind: Int, __lookahead: Int): Spanned<ParameterP<AstNoPayload>>? {
    val __ret = null
    return __ret
}

internal fun __action234(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, __lookbehind: Int, __lookahead: Int): List<Spanned<ParameterP<AstNoPayload>>> {
    val __ret = emptyList<Nothing>()
    return __ret
}

internal fun __action235(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): List<Spanned<ParameterP<AstNoPayload>>> {
    val v = sym0.second.asParameterSpannedList()
    val __ret = v
    return __ret
}

internal fun __action236(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>): Spanned<ParameterP<AstNoPayload>> {
    val __0 = sym0.second.asParameterSpanned()
    val __ret = __0
    return __ret
}

internal fun __action237(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): List<Spanned<ParameterP<AstNoPayload>>> {
    val __0 = sym0.second.asParameterSpanned()
    val __ret = listOf(__0)
    return __ret
}

internal fun __action238(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>): List<Spanned<ParameterP<AstNoPayload>>> {
    val v = sym0.second.asParameterSpannedList()
    val e = sym1.second.asParameterSpanned()
    val __ret = (v + e)
    return __ret
}

internal fun __action239(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): List<Pair<Spanned<ExprP<AstNoPayload>>, Spanned<ExprP<AstNoPayload>>>> {
    val __0 = sym0.second.asExprPair()
    val __ret = listOf(__0)
    return __ret
}

internal fun __action240(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>): List<Pair<Spanned<ExprP<AstNoPayload>>, Spanned<ExprP<AstNoPayload>>>> {
    val v = sym0.second.asExprPairList()
    val e = sym1.second.asExprPair()
    val __ret = (v + e)
    return __ret
}

internal fun __action241(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): List<Spanned<ExprP<AstNoPayload>>> {
    val __0 = sym0.second.asExprSpanned()
    val __ret = listOf(__0)
    return __ret
}

internal fun __action242(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>): List<Spanned<ExprP<AstNoPayload>>> {
    val v = sym0.second.asExprSpannedList()
    val e = sym1.second.asExprSpanned()
    val __ret = (v + e)
    return __ret
}

internal fun __action243(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): List<Spanned<ArgumentP<AstNoPayload>>> {
    val __0 = sym0.second.asArgumentSpanned()
    val __ret = listOf(__0)
    return __ret
}

internal fun __action244(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>): List<Spanned<ArgumentP<AstNoPayload>>> {
    val v = sym0.second.asArgumentSpannedList()
    val e = sym1.second.asArgumentSpanned()
    val __ret = (v + e)
    return __ret
}

internal fun __action245(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): List<Spanned<ExprP<AstNoPayload>>> {
    val __0 = sym0.second.asExprSpanned()
    val __ret = listOf(__0)
    return __ret
}

internal fun __action246(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>): List<Spanned<ExprP<AstNoPayload>>> {
    val v = sym0.second.asExprSpannedList()
    val e = sym1.second.asExprSpanned()
    val __ret = (v + e)
    return __ret
}

internal fun __action247(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): List<Spanned<StmtP<AstNoPayload>>> {
    val __0 = sym0.second.asStmtSpanned()
    val __ret = listOf(__0)
    return __ret
}

internal fun __action248(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>): List<Spanned<StmtP<AstNoPayload>>> {
    val v = sym0.second.asStmtSpannedList()
    val e = sym1.second.asStmtSpanned()
    val __ret = (v + e)
    return __ret
}

internal fun __action249(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): List<Spanned<ParameterP<AstNoPayload>>> {
    val __0 = sym0.second.asParameterSpanned()
    val __ret = listOf(__0)
    return __ret
}

internal fun __action250(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>): List<Spanned<ParameterP<AstNoPayload>>> {
    val v = sym0.second.asParameterSpannedList()
    val e = sym1.second.asParameterSpanned()
    val __ret = (v + e)
    return __ret
}

internal fun __action251(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>, sym4: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asIntValueTriple()
        val __1 = sym1.asExprSpannedListTriple()
        val __2 = sym2.asExprSpannedTriple()
        val __3 = sym3.asTokenTriple()
        val __4 = sym4.asIntValueTriple()
        val __ret = run {
    val start0 = __3.first;
        val end0 = __3.third;
        val __temp0 = __action215(
            state,
            __3,
        );
        val __temp0_triple= Triple(start0, GrammarSymbol.Variant5(__temp0), end0)
        __action165(
            state,
            __0,
            __1,
            __2,
            __temp0_triple,
            __4,
        )
        }
    __ret
}

internal fun __action252(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asIntValueTriple()
        val __1 = sym1.asExprSpannedListTriple()
        val __2 = sym2.asExprSpannedTriple()
        val __3 = sym3.asIntValueTriple()
        val __ret = run {
    val start0 = __2.third;
        val end0 = __3.first;
        val __temp0 = __action216(
            state,
            start0,
            end0,
        );
        val __temp0_triple= Triple(start0, GrammarSymbol.Variant5(__temp0), end0)
        __action165(
            state,
            __0,
            __1,
            __2,
            __temp0_triple,
            __3,
        )
        }
    __ret
}

internal fun __action253(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>, sym4: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asIntValueTriple()
        val __1 = sym1.asExprSpannedListTriple()
        val __2 = sym2.asExprSpannedTriple()
        val __3 = sym3.asTokenTriple()
        val __4 = sym4.asIntValueTriple()
        val __ret = run {
    val start0 = __3.first;
        val end0 = __3.third;
        val __temp0 = __action215(
            state,
            __3,
        );
        val __temp0_triple= Triple(start0, GrammarSymbol.Variant5(__temp0), end0)
        __action164(
            state,
            __0,
            __1,
            __2,
            __temp0_triple,
            __4,
        )
        }
    __ret
}

internal fun __action254(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asIntValueTriple()
        val __1 = sym1.asExprSpannedListTriple()
        val __2 = sym2.asExprSpannedTriple()
        val __3 = sym3.asIntValueTriple()
        val __ret = run {
    val start0 = __2.third;
        val end0 = __3.first;
        val __temp0 = __action216(
            state,
            start0,
            end0,
        );
        val __temp0_triple= Triple(start0, GrammarSymbol.Variant5(__temp0), end0)
        __action164(
            state,
            __0,
            __1,
            __2,
            __temp0_triple,
            __3,
        )
        }
    __ret
}

internal fun __action255(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>, sym4: Triple<Int, GrammarSymbol, Int>, sym5: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asIntValueTriple()
        val __1 = sym1.asStmtSpannedTriple()
        val __2 = sym2.asStmtSpannedListTriple()
        val __3 = sym3.asTokenTriple()
        val __4 = sym4.asIntValueTriple()
        val __5 = sym5.asTokenTriple()
        val __ret = run {
    val start0 = __3.first;
        val end0 = __3.third;
        val __temp0 = __action205(
            state,
            __3,
        );
        val __temp0_triple= Triple(start0, GrammarSymbol.Variant5(__temp0), end0)
        __action188(
            state,
            __0,
            __1,
            __2,
            __temp0_triple,
            __4,
            __5,
        )
        }
    __ret
}

internal fun __action256(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>, sym4: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asIntValueTriple()
        val __1 = sym1.asStmtSpannedTriple()
        val __2 = sym2.asStmtSpannedListTriple()
        val __3 = sym3.asIntValueTriple()
        val __4 = sym4.asTokenTriple()
        val __ret = run {
    val start0 = __2.third;
        val end0 = __3.first;
        val __temp0 = __action206(
            state,
            start0,
            end0,
        );
        val __temp0_triple= Triple(start0, GrammarSymbol.Variant5(__temp0), end0)
        __action188(
            state,
            __0,
            __1,
            __2,
            __temp0_triple,
            __3,
            __4,
        )
        }
    __ret
}

internal fun __action257(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): Spanned<StmtP<AstNoPayload>> {
    val __0 = sym0.asStmtSpannedTriple()
    val __ret = run {
val start0 = __0.third;
    val end0 = __0.third;
    val __temp0 = __action196(
        state,
        start0,
        end0,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant6(__temp0), end0)
    __action195(
        state,
        __0,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action258(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>): Spanned<StmtP<AstNoPayload>> {
    val __0 = sym0.asStmtSpannedTriple()
    val __1 = sym1.asTokenListTriple()
    val __ret = run {
val start0 = __1.first;
    val end0 = __1.third;
    val __temp0 = __action197(
        state,
        __1,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant6(__temp0), end0)
    __action195(
        state,
        __0,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action259(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asIntValueTriple()
        val __1 = sym1.asStmtSpannedListTriple()
        val __2 = sym2.asIntValueTriple()
        val __ret = run {
    val start0 = __0.first;
        val end0 = __0.first;
        val __temp0 = __action196(
            state,
            start0,
            end0,
        );
        val __temp0_triple= Triple(start0, GrammarSymbol.Variant6(__temp0), end0)
        __action8(
            state,
            __temp0_triple,
            __0,
            __1,
            __2,
        )
        }
    __ret
}

internal fun __action260(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asTokenListTriple()
        val __1 = sym1.asIntValueTriple()
        val __2 = sym2.asStmtSpannedListTriple()
        val __3 = sym3.asIntValueTriple()
        val __ret = run {
    val start0 = __0.first;
        val end0 = __0.third;
        val __temp0 = __action197(
            state,
            __0,
        );
        val __temp0_triple= Triple(start0, GrammarSymbol.Variant6(__temp0), end0)
        __action8(
            state,
            __temp0_triple,
            __1,
            __2,
            __3,
        )
        }
    __ret
}

internal fun __action261(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>, sym4: Triple<Int, GrammarSymbol, Int>, sym5: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asTokenListTriple()
        val __1 = sym1.asTokenTriple()
        val __2 = sym2.asIntValueTriple()
        val __3 = sym3.asStmtSpannedListTriple()
        val __4 = sym4.asIntValueTriple()
        val __5 = sym5.asTokenTriple()
        val __ret = run {
    val start0 = __2.third;
        val end0 = __3.first;
        val __temp0 = __action196(
            state,
            start0,
            end0,
        );
        val __temp0_triple= Triple(start0, GrammarSymbol.Variant6(__temp0), end0)
        __action31(
            state,
            __0,
            __1,
            __2,
            __temp0_triple,
            __3,
            __4,
            __5,
        )
        }
    __ret
}

internal fun __action262(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>, sym4: Triple<Int, GrammarSymbol, Int>, sym5: Triple<Int, GrammarSymbol, Int>, sym6: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asTokenListTriple()
        val __1 = sym1.asTokenTriple()
        val __2 = sym2.asIntValueTriple()
        val __3 = sym3.asTokenListTriple()
        val __4 = sym4.asStmtSpannedListTriple()
        val __5 = sym5.asIntValueTriple()
        val __6 = sym6.asTokenTriple()
        val __ret = run {
    val start0 = __3.first;
        val end0 = __3.third;
        val __temp0 = __action197(
            state,
            __3,
        );
        val __temp0_triple= Triple(start0, GrammarSymbol.Variant6(__temp0), end0)
        __action31(
            state,
            __0,
            __1,
            __2,
            __temp0_triple,
            __4,
            __5,
            __6,
        )
        }
    __ret
}

internal fun __action263(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>): Spanned<ExprP<AstNoPayload>>? {
    val __0 = sym0.asTokenTriple()
    val __1 = sym1.asExprSpannedTriple()
    val __ret = run {
val start0 = __1.first;
    val end0 = __1.third;
    val __temp0 = __action161(
        state,
        __1,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant7(__temp0), end0)
    __action160(
        state,
        __0,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action264(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): Spanned<ExprP<AstNoPayload>>? {
    val __0 = sym0.asTokenTriple()
    val __ret = run {
val start0 = __0.third;
    val end0 = __0.third;
    val __temp0 = __action162(
        state,
        start0,
        end0,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant7(__temp0), end0)
    __action160(
        state,
        __0,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action265(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>): List<Spanned<ExprP<AstNoPayload>>> {
    val __0 = sym0.asExprSpannedListTriple()
    val __1 = sym1.asExprSpannedTriple()
    val __ret = run {
val start0 = __1.first;
    val end0 = __1.third;
    val __temp0 = __action161(
        state,
        __1,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant7(__temp0), end0)
    __action156(
        state,
        __0,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action266(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): List<Spanned<ExprP<AstNoPayload>>> {
    val __0 = sym0.asExprSpannedListTriple()
    val __ret = run {
val start0 = __0.third;
    val end0 = __0.third;
    val __temp0 = __action162(
        state,
        start0,
        end0,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant7(__temp0), end0)
    __action156(
        state,
        __0,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action267(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>, sym4: Triple<Int, GrammarSymbol, Int>, sym5: Triple<Int, GrammarSymbol, Int>, sym6: Triple<Int, GrammarSymbol, Int>, sym7: Triple<Int, GrammarSymbol, Int>, sym8: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asIntValueTriple()
        val __1 = sym1.asExprSpannedTriple()
        val __2 = sym2.asTokenTriple()
        val __3 = sym3.asExprSpannedTriple()
        val __4 = sym4.asTokenTriple()
        val __5 = sym5.asExprSpannedTriple()
        // sym6 is the `(":" <Test?>)?` slot — Variant8 carries it as NullableOption to
        // preserve the three-state distinction between "outer None", "Some(inner None)",
        // and "Some(inner Some(value))" that the upstream Rust grammar expresses with
        // Option<Option<AstExpr>>. Pulled through unchanged here; the consumer __action77
        // does the unwrap_or(None) flatten that mirrors the Rust action body.
        val __6 = sym6.asNullableOptionExprSpannedTriple()
        val __7 = sym7.asTokenTriple()
        val __8 = sym8.asIntValueTriple()
        val __ret = run {
    val start0 = __3.first;
        val end0 = __3.third;
        val start1 = __5.first;
        val end1 = __5.third;
        val __temp0 = __action161(
            state,
            __3,
        );
        val __temp0_triple= Triple(start0, GrammarSymbol.Variant7(__temp0), end0)
        val __temp1 = __action161(
            state,
            __5,
        );
        val __temp1_triple= Triple(start1, GrammarSymbol.Variant7(__temp1), end1)
        __action77(
            state,
            __0,
            __1,
            __2,
            __temp0_triple,
            __4,
            __temp1_triple,
            __6,
            __7,
            __8,
        )
        }
    __ret
}

internal fun __action268(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>, sym4: Triple<Int, GrammarSymbol, Int>, sym5: Triple<Int, GrammarSymbol, Int>, sym6: Triple<Int, GrammarSymbol, Int>, sym7: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asIntValueTriple()
        val __1 = sym1.asExprSpannedTriple()
        val __2 = sym2.asTokenTriple()
        val __3 = sym3.asExprSpannedTriple()
        val __4 = sym4.asTokenTriple()
        val __5 = sym5.asNullableExprSpannedTriple()
        val __6 = sym6.asTokenTriple()
        val __7 = sym7.asIntValueTriple()
        val __ret = run {
    val start0 = __3.first;
        val end0 = __3.third;
        val start1 = __4.third;
        val end1 = __5.first;
        val __temp0 = __action161(
            state,
            __3,
        );
        val __temp0_triple= Triple(start0, GrammarSymbol.Variant7(__temp0), end0)
        val __temp1 = __action162(
            state,
            start1,
            end1,
        );
        val __temp1_triple= Triple(start1, GrammarSymbol.Variant7(__temp1), end1)
        __action77(
            state,
            __0,
            __1,
            __2,
            __temp0_triple,
            __4,
            __temp1_triple,
            __5,
            __6,
            __7,
        )
        }
    __ret
}

internal fun __action269(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>, sym4: Triple<Int, GrammarSymbol, Int>, sym5: Triple<Int, GrammarSymbol, Int>, sym6: Triple<Int, GrammarSymbol, Int>, sym7: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asIntValueTriple()
        val __1 = sym1.asExprSpannedTriple()
        val __2 = sym2.asTokenTriple()
        val __3 = sym3.asTokenTriple()
        val __4 = sym4.asExprSpannedTriple()
        val __5 = sym5.asNullableExprSpannedTriple()
        val __6 = sym6.asTokenTriple()
        val __7 = sym7.asIntValueTriple()
        val __ret = run {
    val start0 = __2.third;
        val end0 = __3.first;
        val start1 = __4.first;
        val end1 = __4.third;
        val __temp0 = __action162(
            state,
            start0,
            end0,
        );
        val __temp0_triple= Triple(start0, GrammarSymbol.Variant7(__temp0), end0)
        val __temp1 = __action161(
            state,
            __4,
        );
        val __temp1_triple= Triple(start1, GrammarSymbol.Variant7(__temp1), end1)
        __action77(
            state,
            __0,
            __1,
            __2,
            __temp0_triple,
            __3,
            __temp1_triple,
            __5,
            __6,
            __7,
        )
        }
    __ret
}

internal fun __action270(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>, sym4: Triple<Int, GrammarSymbol, Int>, sym5: Triple<Int, GrammarSymbol, Int>, sym6: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asIntValueTriple()
        val __1 = sym1.asExprSpannedTriple()
        val __2 = sym2.asTokenTriple()
        val __3 = sym3.asTokenTriple()
        val __4 = sym4.asNullableExprSpannedTriple()
        val __5 = sym5.asTokenTriple()
        val __6 = sym6.asIntValueTriple()
        val __ret = run {
    val start0 = __2.third;
        val end0 = __3.first;
        val start1 = __3.third;
        val end1 = __4.first;
        val __temp0 = __action162(
            state,
            start0,
            end0,
        );
        val __temp0_triple= Triple(start0, GrammarSymbol.Variant7(__temp0), end0)
        val __temp1 = __action162(
            state,
            start1,
            end1,
        );
        val __temp1_triple= Triple(start1, GrammarSymbol.Variant7(__temp1), end1)
        __action77(
            state,
            __0,
            __1,
            __2,
            __temp0_triple,
            __3,
            __temp1_triple,
            __4,
            __5,
            __6,
        )
        }
    __ret
}

internal fun __action271(
    state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState,
    sym0: Triple<Int, GrammarSymbol, Int>,
    sym1: Triple<Int, GrammarSymbol, Int>,
): NullableOption<Spanned<ExprP<AstNoPayload>>> {
    val __0 = sym0.asTokenTriple()
    val __1 = sym1.asExprSpannedTriple()
    val start0 = __0.first
    val end0 = __1.third
    val __temp0 = __action263(
        state,
        __0,
        __1,
    )
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant7(__temp0), end0)
    return __action158(
        state,
        __temp0_triple,
    )
}

internal fun __action272(
    state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState,
    sym0: Triple<Int, GrammarSymbol, Int>,
): NullableOption<Spanned<ExprP<AstNoPayload>>> {
    val __0 = sym0.asTokenTriple()
    val start0 = __0.first
    val end0 = __0.third
    val __temp0 = __action264(
        state,
        __0,
    )
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant7(__temp0), end0)
    return __action158(
        state,
        __temp0_triple,
    )
}

internal fun __action273(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>, sym4: Triple<Int, GrammarSymbol, Int>, sym5: Triple<Int, GrammarSymbol, Int>, sym6: Triple<Int, GrammarSymbol, Int>, sym7: Triple<Int, GrammarSymbol, Int>, sym8: Triple<Int, GrammarSymbol, Int>, sym9: Triple<Int, GrammarSymbol, Int>): Spanned<ExprP<AstNoPayload>> {
    val __0 = sym0.asIntValueTriple()
    val __1 = sym1.asExprSpannedTriple()
    val __2 = sym2.asTokenTriple()
    val __3 = sym3.asExprSpannedTriple()
    val __4 = sym4.asTokenTriple()
    val __5 = sym5.asExprSpannedTriple()
    val __6 = sym6.asTokenTriple()
    val __7 = sym7.asExprSpannedTriple()
    val __8 = sym8.asTokenTriple()
    val __9 = sym9.asIntValueTriple()
    val __ret = run {
val start0 = __6.first;
    val end0 = __7.third;
    val __temp0 = __action271(
        state,
        __6,
        __7,
    );
    val __temp0_triple = Triple(start0, GrammarSymbol.Variant8(__temp0), end0)
    __action267(
        state,
        __0,
        __1,
        __2,
        __3,
        __4,
        __5,
        __temp0_triple,
        __8,
        __9,
    )
    }
    return __ret
}

internal fun __action274(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>, sym4: Triple<Int, GrammarSymbol, Int>, sym5: Triple<Int, GrammarSymbol, Int>, sym6: Triple<Int, GrammarSymbol, Int>, sym7: Triple<Int, GrammarSymbol, Int>, sym8: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asIntValueTriple()
        val __1 = sym1.asExprSpannedTriple()
        val __2 = sym2.asTokenTriple()
        val __3 = sym3.asExprSpannedTriple()
        val __4 = sym4.asTokenTriple()
        val __5 = sym5.asExprSpannedTriple()
        val __6 = sym6.asTokenTriple()
        val __7 = sym7.asTokenTriple()
        val __8 = sym8.asIntValueTriple()
        val __ret = run {
    val start0 = __6.first;
        val end0 = __6.third;
        val __temp0 = __action272(
            state,
            __6,
        );
        val __temp0_triple = Triple(start0, GrammarSymbol.Variant8(__temp0), end0)
        __action267(
            state,
            __0,
            __1,
            __2,
            __3,
            __4,
            __5,
            __temp0_triple,
            __7,
            __8,
        )
        }
    __ret
}

internal fun __action275(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>, sym4: Triple<Int, GrammarSymbol, Int>, sym5: Triple<Int, GrammarSymbol, Int>, sym6: Triple<Int, GrammarSymbol, Int>, sym7: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asIntValueTriple()
        val __1 = sym1.asExprSpannedTriple()
        val __2 = sym2.asTokenTriple()
        val __3 = sym3.asExprSpannedTriple()
        val __4 = sym4.asTokenTriple()
        val __5 = sym5.asExprSpannedTriple()
        val __6 = sym6.asTokenTriple()
        val __7 = sym7.asIntValueTriple()
        val __ret = run {
    val start0 = __5.third;
        val end0 = __6.first;
        val __temp0 = __action159(
            state,
            start0,
            end0,
        );
        val __temp0_triple = Triple(start0, GrammarSymbol.Variant8(__temp0), end0)
        __action267(
            state,
            __0,
            __1,
            __2,
            __3,
            __4,
            __5,
            __temp0_triple,
            __6,
            __7,
        )
        }
    __ret
}

internal fun __action276(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>, sym4: Triple<Int, GrammarSymbol, Int>, sym5: Triple<Int, GrammarSymbol, Int>, sym6: Triple<Int, GrammarSymbol, Int>, sym7: Triple<Int, GrammarSymbol, Int>, sym8: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asIntValueTriple()
        val __1 = sym1.asExprSpannedTriple()
        val __2 = sym2.asTokenTriple()
        val __3 = sym3.asExprSpannedTriple()
        val __4 = sym4.asTokenTriple()
        val __5 = sym5.asTokenTriple()
        val __6 = sym6.asExprSpannedTriple()
        val __7 = sym7.asTokenTriple()
        val __8 = sym8.asIntValueTriple()
        val __ret = run {
    val start0 = __5.first;
        val end0 = __6.third;
        val __temp0 = __action271(
            state,
            __5,
            __6,
        );
        val __temp0_triple = Triple(start0, GrammarSymbol.Variant8(__temp0), end0)
        __action268(
            state,
            __0,
            __1,
            __2,
            __3,
            __4,
            __temp0_triple,
            __7,
            __8,
        )
        }
    __ret
}

internal fun __action277(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>, sym4: Triple<Int, GrammarSymbol, Int>, sym5: Triple<Int, GrammarSymbol, Int>, sym6: Triple<Int, GrammarSymbol, Int>, sym7: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asIntValueTriple()
        val __1 = sym1.asExprSpannedTriple()
        val __2 = sym2.asTokenTriple()
        val __3 = sym3.asExprSpannedTriple()
        val __4 = sym4.asTokenTriple()
        val __5 = sym5.asTokenTriple()
        val __6 = sym6.asTokenTriple()
        val __7 = sym7.asIntValueTriple()
        val __ret = run {
    val start0 = __5.first;
        val end0 = __5.third;
        val __temp0 = __action272(
            state,
            __5,
        );
        val __temp0_triple = Triple(start0, GrammarSymbol.Variant8(__temp0), end0)
        __action268(
            state,
            __0,
            __1,
            __2,
            __3,
            __4,
            __temp0_triple,
            __6,
            __7,
        )
        }
    __ret
}

internal fun __action278(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>, sym4: Triple<Int, GrammarSymbol, Int>, sym5: Triple<Int, GrammarSymbol, Int>, sym6: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asIntValueTriple()
        val __1 = sym1.asExprSpannedTriple()
        val __2 = sym2.asTokenTriple()
        val __3 = sym3.asExprSpannedTriple()
        val __4 = sym4.asTokenTriple()
        val __5 = sym5.asTokenTriple()
        val __6 = sym6.asIntValueTriple()
        val __ret = run {
    val start0 = __4.third;
        val end0 = __5.first;
        val __temp0 = __action159(
            state,
            start0,
            end0,
        );
        val __temp0_triple = Triple(start0, GrammarSymbol.Variant8(__temp0), end0)
        __action268(
            state,
            __0,
            __1,
            __2,
            __3,
            __4,
            __temp0_triple,
            __5,
            __6,
        )
        }
    __ret
}

internal fun __action279(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>, sym4: Triple<Int, GrammarSymbol, Int>, sym5: Triple<Int, GrammarSymbol, Int>, sym6: Triple<Int, GrammarSymbol, Int>, sym7: Triple<Int, GrammarSymbol, Int>, sym8: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asIntValueTriple()
        val __1 = sym1.asExprSpannedTriple()
        val __2 = sym2.asTokenTriple()
        val __3 = sym3.asTokenTriple()
        val __4 = sym4.asExprSpannedTriple()
        val __5 = sym5.asTokenTriple()
        val __6 = sym6.asExprSpannedTriple()
        val __7 = sym7.asTokenTriple()
        val __8 = sym8.asIntValueTriple()
        val __ret = run {
    val start0 = __5.first;
        val end0 = __6.third;
        val __temp0 = __action271(
            state,
            __5,
            __6,
        );
        val __temp0_triple = Triple(start0, GrammarSymbol.Variant8(__temp0), end0)
        __action269(
            state,
            __0,
            __1,
            __2,
            __3,
            __4,
            __temp0_triple,
            __7,
            __8,
        )
        }
    __ret
}

internal fun __action280(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>, sym4: Triple<Int, GrammarSymbol, Int>, sym5: Triple<Int, GrammarSymbol, Int>, sym6: Triple<Int, GrammarSymbol, Int>, sym7: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asIntValueTriple()
        val __1 = sym1.asExprSpannedTriple()
        val __2 = sym2.asTokenTriple()
        val __3 = sym3.asTokenTriple()
        val __4 = sym4.asExprSpannedTriple()
        val __5 = sym5.asTokenTriple()
        val __6 = sym6.asTokenTriple()
        val __7 = sym7.asIntValueTriple()
        val __ret = run {
    val start0 = __5.first;
        val end0 = __5.third;
        val __temp0 = __action272(
            state,
            __5,
        );
        val __temp0_triple = Triple(start0, GrammarSymbol.Variant8(__temp0), end0)
        __action269(
            state,
            __0,
            __1,
            __2,
            __3,
            __4,
            __temp0_triple,
            __6,
            __7,
        )
        }
    __ret
}

internal fun __action281(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>, sym4: Triple<Int, GrammarSymbol, Int>, sym5: Triple<Int, GrammarSymbol, Int>, sym6: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asIntValueTriple()
        val __1 = sym1.asExprSpannedTriple()
        val __2 = sym2.asTokenTriple()
        val __3 = sym3.asTokenTriple()
        val __4 = sym4.asExprSpannedTriple()
        val __5 = sym5.asTokenTriple()
        val __6 = sym6.asIntValueTriple()
        val __ret = run {
    val start0 = __4.third;
        val end0 = __5.first;
        val __temp0 = __action159(
            state,
            start0,
            end0,
        );
        val __temp0_triple = Triple(start0, GrammarSymbol.Variant8(__temp0), end0)
        __action269(
            state,
            __0,
            __1,
            __2,
            __3,
            __4,
            __temp0_triple,
            __5,
            __6,
        )
        }
    __ret
}

internal fun __action282(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>, sym4: Triple<Int, GrammarSymbol, Int>, sym5: Triple<Int, GrammarSymbol, Int>, sym6: Triple<Int, GrammarSymbol, Int>, sym7: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asIntValueTriple()
        val __1 = sym1.asExprSpannedTriple()
        val __2 = sym2.asTokenTriple()
        val __3 = sym3.asTokenTriple()
        val __4 = sym4.asTokenTriple()
        val __5 = sym5.asExprSpannedTriple()
        val __6 = sym6.asTokenTriple()
        val __7 = sym7.asIntValueTriple()
        val __ret = run {
    val start0 = __4.first;
        val end0 = __5.third;
        val __temp0 = __action271(
            state,
            __4,
            __5,
        );
        val __temp0_triple = Triple(start0, GrammarSymbol.Variant8(__temp0), end0)
        __action270(
            state,
            __0,
            __1,
            __2,
            __3,
            __temp0_triple,
            __6,
            __7,
        )
        }
    __ret
}

internal fun __action283(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>, sym4: Triple<Int, GrammarSymbol, Int>, sym5: Triple<Int, GrammarSymbol, Int>, sym6: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asIntValueTriple()
        val __1 = sym1.asExprSpannedTriple()
        val __2 = sym2.asTokenTriple()
        val __3 = sym3.asTokenTriple()
        val __4 = sym4.asTokenTriple()
        val __5 = sym5.asTokenTriple()
        val __6 = sym6.asIntValueTriple()
        val __ret = run {
    val start0 = __4.first;
        val end0 = __4.third;
        val __temp0 = __action272(
            state,
            __4,
        );
        val __temp0_triple = Triple(start0, GrammarSymbol.Variant8(__temp0), end0)
        __action270(
            state,
            __0,
            __1,
            __2,
            __3,
            __temp0_triple,
            __5,
            __6,
        )
        }
    __ret
}

internal fun __action284(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>, sym4: Triple<Int, GrammarSymbol, Int>, sym5: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asIntValueTriple()
        val __1 = sym1.asExprSpannedTriple()
        val __2 = sym2.asTokenTriple()
        val __3 = sym3.asTokenTriple()
        val __4 = sym4.asTokenTriple()
        val __5 = sym5.asIntValueTriple()
        val __ret = run {
    val start0 = __3.third;
        val end0 = __4.first;
        val __temp0 = __action159(
            state,
            start0,
            end0,
        );
        val __temp0_triple = Triple(start0, GrammarSymbol.Variant8(__temp0), end0)
        __action270(
            state,
            __0,
            __1,
            __2,
            __3,
            __temp0_triple,
            __4,
            __5,
        )
        }
    __ret
}

internal fun __action285(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>): List<Spanned<StmtP<AstNoPayload>>> {
    val __0 = sym0.asTokenTriple()
    val __1 = sym1.asStmtSpannedTriple()
    val __ret = run {
val start0 = __0.first;
    val end0 = __1.third;
    val __temp0 = __action209(
        state,
        __0,
        __1,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant9(__temp0), end0)
    __action247(
        state,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action286(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>): List<Spanned<StmtP<AstNoPayload>>> {
    val __0 = sym0.asStmtSpannedListTriple()
    val __1 = sym1.asTokenTriple()
    val __2 = sym2.asStmtSpannedTriple()
    val __ret = run {
val start0 = __1.first;
    val end0 = __2.third;
    val __temp0 = __action209(
        state,
        __1,
        __2,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant9(__temp0), end0)
    __action248(
        state,
        __0,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action287(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>, sym4: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asIntValueTriple()
        val __1 = sym1.asStmtSpannedTriple()
        val __2 = sym2.asTokenTriple()
        val __3 = sym3.asIntValueTriple()
        val __4 = sym4.asTokenTriple()
        val __ret = run {
    val start0 = __1.third;
        val end0 = __2.first;
        val __temp0 = __action207(
            state,
            start0,
            end0,
        );
        val __temp0_triple= Triple(start0, GrammarSymbol.Variant10(__temp0), end0)
        __action255(
            state,
            __0,
            __1,
            __temp0_triple,
            __2,
            __3,
            __4,
        )
        }
    __ret
}

internal fun __action288(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>, sym4: Triple<Int, GrammarSymbol, Int>, sym5: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asIntValueTriple()
        val __1 = sym1.asStmtSpannedTriple()
        val __2 = sym2.asStmtSpannedListTriple()
        val __3 = sym3.asTokenTriple()
        val __4 = sym4.asIntValueTriple()
        val __5 = sym5.asTokenTriple()
        val __ret = run {
    val start0 = __2.first;
        val end0 = __2.third;
        val __temp0 = __action208(
            state,
            __2,
        );
        val __temp0_triple= Triple(start0, GrammarSymbol.Variant10(__temp0), end0)
        __action255(
            state,
            __0,
            __1,
            __temp0_triple,
            __3,
            __4,
            __5,
        )
        }
    __ret
}

internal fun __action289(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asIntValueTriple()
        val __1 = sym1.asStmtSpannedTriple()
        val __2 = sym2.asIntValueTriple()
        val __3 = sym3.asTokenTriple()
        val __ret = run {
    val start0 = __1.third;
        val end0 = __2.first;
        val __temp0 = __action207(
            state,
            start0,
            end0,
        );
        val __temp0_triple= Triple(start0, GrammarSymbol.Variant10(__temp0), end0)
        __action256(
            state,
            __0,
            __1,
            __temp0_triple,
            __2,
            __3,
        )
        }
    __ret
}

internal fun __action290(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>, sym4: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asIntValueTriple()
        val __1 = sym1.asStmtSpannedTriple()
        val __2 = sym2.asStmtSpannedListTriple()
        val __3 = sym3.asIntValueTriple()
        val __4 = sym4.asTokenTriple()
        val __ret = run {
    val start0 = __2.first;
        val end0 = __2.third;
        val __temp0 = __action208(
            state,
            __2,
        );
        val __temp0_triple= Triple(start0, GrammarSymbol.Variant10(__temp0), end0)
        __action256(
            state,
            __0,
            __1,
            __temp0_triple,
            __3,
            __4,
        )
        }
    __ret
}

internal fun __action291(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>): List<Spanned<ArgumentP<AstNoPayload>>> {
    val __0 = sym0.asArgumentSpannedTriple()
    val __1 = sym1.asTokenTriple()
    val __ret = run {
val start0 = __0.first;
    val end0 = __1.third;
    val __temp0 = __action221(
        state,
        __0,
        __1,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant11(__temp0), end0)
    __action243(
        state,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action292(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>): List<Spanned<ArgumentP<AstNoPayload>>> {
    val __0 = sym0.asArgumentSpannedListTriple()
    val __1 = sym1.asArgumentSpannedTriple()
    val __2 = sym2.asTokenTriple()
    val __ret = run {
val start0 = __1.first;
    val end0 = __2.third;
    val __temp0 = __action221(
        state,
        __1,
        __2,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant11(__temp0), end0)
    __action244(
        state,
        __0,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action293(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): List<Spanned<ArgumentP<AstNoPayload>>> {
    val __0 = sym0.asNullableArgumentSpannedTriple()
    val __ret = run {
val start0 = __0.first;
    val end0 = __0.first;
    val __temp0 = __action219(
        state,
        start0,
        end0,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant12(__temp0), end0)
    __action163(
        state,
        __temp0_triple,
        __0,
    )
    }
    return __ret
}

internal fun __action294(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>): List<Spanned<ArgumentP<AstNoPayload>>> {
    val __0 = sym0.asArgumentSpannedListTriple()
    val __1 = sym1.asNullableArgumentSpannedTriple()
    val __ret = run {
val start0 = __0.first;
    val end0 = __0.third;
    val __temp0 = __action220(
        state,
        __0,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant12(__temp0), end0)
    __action163(
        state,
        __temp0_triple,
        __1,
    )
    }
    return __ret
}

internal fun __action295(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>): List<Spanned<ParameterP<AstNoPayload>>> {
    val __0 = sym0.asParameterSpannedTriple()
    val __1 = sym1.asTokenTriple()
    val __ret = run {
val start0 = __0.first;
    val end0 = __1.third;
    val __temp0 = __action204(
        state,
        __0,
        __1,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant13(__temp0), end0)
    __action249(
        state,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action296(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>): List<Spanned<ParameterP<AstNoPayload>>> {
    val __0 = sym0.asParameterSpannedListTriple()
    val __1 = sym1.asParameterSpannedTriple()
    val __2 = sym2.asTokenTriple()
    val __ret = run {
val start0 = __1.first;
    val end0 = __2.third;
    val __temp0 = __action204(
        state,
        __1,
        __2,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant13(__temp0), end0)
    __action250(
        state,
        __0,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action297(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): List<Spanned<ParameterP<AstNoPayload>>> {
    val __0 = sym0.asNullableParameterSpannedTriple()
    val __ret = run {
val start0 = __0.first;
    val end0 = __0.first;
    val __temp0 = __action202(
        state,
        start0,
        end0,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant14(__temp0), end0)
    __action191(
        state,
        __temp0_triple,
        __0,
    )
    }
    return __ret
}

internal fun __action298(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>): List<Spanned<ParameterP<AstNoPayload>>> {
    val __0 = sym0.asParameterSpannedListTriple()
    val __1 = sym1.asNullableParameterSpannedTriple()
    val __ret = run {
val start0 = __0.first;
    val end0 = __0.third;
    val __temp0 = __action203(
        state,
        __0,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant14(__temp0), end0)
    __action191(
        state,
        __temp0_triple,
        __1,
    )
    }
    return __ret
}

internal fun __action299(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>): List<Pair<Spanned<ExprP<AstNoPayload>>, Spanned<ExprP<AstNoPayload>>>> {
    val __0 = sym0.asExprPairTriple()
    val __1 = sym1.asTokenTriple()
    val __ret = run {
val start0 = __0.first;
    val end0 = __1.third;
    val __temp0 = __action229(
        state,
        __0,
        __1,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant15(__temp0), end0)
    __action239(
        state,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action300(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>): List<Pair<Spanned<ExprP<AstNoPayload>>, Spanned<ExprP<AstNoPayload>>>> {
    val __0 = sym0.asExprPairListTriple()
    val __1 = sym1.asExprPairTriple()
    val __2 = sym2.asTokenTriple()
    val __ret = run {
val start0 = __1.first;
    val end0 = __2.third;
    val __temp0 = __action229(
        state,
        __1,
        __2,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant15(__temp0), end0)
    __action240(
        state,
        __0,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action301(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): List<Pair<Spanned<ExprP<AstNoPayload>>, Spanned<ExprP<AstNoPayload>>>> {
    val __0 = sym0.asNullableExprPairTriple()
    val __ret = run {
val start0 = __0.first;
    val end0 = __0.first;
    val __temp0 = __action227(
        state,
        start0,
        end0,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant16(__temp0), end0)
    __action155(
        state,
        __temp0_triple,
        __0,
    )
    }
    return __ret
}

internal fun __action302(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>): List<Pair<Spanned<ExprP<AstNoPayload>>, Spanned<ExprP<AstNoPayload>>>> {
    val __0 = sym0.asExprPairListTriple()
    val __1 = sym1.asNullableExprPairTriple()
    val __ret = run {
val start0 = __0.first;
    val end0 = __0.third;
    val __temp0 = __action228(
        state,
        __0,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant16(__temp0), end0)
    __action155(
        state,
        __temp0_triple,
        __1,
    )
    }
    return __ret
}

internal fun __action303(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>): List<Spanned<ExprP<AstNoPayload>>> {
    val __0 = sym0.asExprSpannedTriple()
    val __1 = sym1.asTokenTriple()
    val __ret = run {
val start0 = __0.first;
    val end0 = __1.third;
    val __temp0 = __action214(
        state,
        __0,
        __1,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant17(__temp0), end0)
    __action245(
        state,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action304(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>): List<Spanned<ExprP<AstNoPayload>>> {
    val __0 = sym0.asExprSpannedListTriple()
    val __1 = sym1.asExprSpannedTriple()
    val __2 = sym2.asTokenTriple()
    val __ret = run {
val start0 = __1.first;
    val end0 = __2.third;
    val __temp0 = __action214(
        state,
        __1,
        __2,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant17(__temp0), end0)
    __action246(
        state,
        __0,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action305(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asIntValueTriple()
        val __1 = sym1.asExprSpannedTriple()
        val __2 = sym2.asTokenTriple()
        val __3 = sym3.asIntValueTriple()
        val __ret = run {
    val start0 = __0.third;
        val end0 = __1.first;
        val __temp0 = __action212(
            state,
            start0,
            end0,
        );
        val __temp0_triple= Triple(start0, GrammarSymbol.Variant18(__temp0), end0)
        __action251(
            state,
            __0,
            __temp0_triple,
            __1,
            __2,
            __3,
        )
        }
    __ret
}

internal fun __action306(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>, sym4: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asIntValueTriple()
        val __1 = sym1.asExprSpannedListTriple()
        val __2 = sym2.asExprSpannedTriple()
        val __3 = sym3.asTokenTriple()
        val __4 = sym4.asIntValueTriple()
        val __ret = run {
    val start0 = __1.first;
        val end0 = __1.third;
        val __temp0 = __action213(
            state,
            __1,
        );
        val __temp0_triple= Triple(start0, GrammarSymbol.Variant18(__temp0), end0)
        __action251(
            state,
            __0,
            __temp0_triple,
            __2,
            __3,
            __4,
        )
        }
    __ret
}

internal fun __action307(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asIntValueTriple()
        val __1 = sym1.asExprSpannedTriple()
        val __2 = sym2.asIntValueTriple()
        val __ret = run {
    val start0 = __0.third;
        val end0 = __1.first;
        val __temp0 = __action212(
            state,
            start0,
            end0,
        );
        val __temp0_triple= Triple(start0, GrammarSymbol.Variant18(__temp0), end0)
        __action252(
            state,
            __0,
            __temp0_triple,
            __1,
            __2,
        )
        }
    __ret
}

internal fun __action308(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asIntValueTriple()
        val __1 = sym1.asExprSpannedListTriple()
        val __2 = sym2.asExprSpannedTriple()
        val __3 = sym3.asIntValueTriple()
        val __ret = run {
    val start0 = __1.first;
        val end0 = __1.third;
        val __temp0 = __action213(
            state,
            __1,
        );
        val __temp0_triple= Triple(start0, GrammarSymbol.Variant18(__temp0), end0)
        __action252(
            state,
            __0,
            __temp0_triple,
            __2,
            __3,
        )
        }
    __ret
}

internal fun __action309(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>): List<Spanned<ParameterP<AstNoPayload>>> {
    val __0 = sym0.asParameterSpannedTriple()
    val __1 = sym1.asTokenTriple()
    val __ret = run {
val start0 = __0.first;
    val end0 = __1.third;
    val __temp0 = __action236(
        state,
        __0,
        __1,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant13(__temp0), end0)
    __action237(
        state,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action310(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>): List<Spanned<ParameterP<AstNoPayload>>> {
    val __0 = sym0.asParameterSpannedListTriple()
    val __1 = sym1.asParameterSpannedTriple()
    val __2 = sym2.asTokenTriple()
    val __ret = run {
val start0 = __1.first;
    val end0 = __2.third;
    val __temp0 = __action236(
        state,
        __1,
        __2,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant13(__temp0), end0)
    __action238(
        state,
        __0,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action311(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): List<Spanned<ParameterP<AstNoPayload>>> {
    val __0 = sym0.asNullableParameterSpannedTriple()
    val __ret = run {
val start0 = __0.first;
    val end0 = __0.first;
    val __temp0 = __action234(
        state,
        start0,
        end0,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant14(__temp0), end0)
    __action149(
        state,
        __temp0_triple,
        __0,
    )
    }
    return __ret
}

internal fun __action312(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>): List<Spanned<ParameterP<AstNoPayload>>> {
    val __0 = sym0.asParameterSpannedListTriple()
    val __1 = sym1.asNullableParameterSpannedTriple()
    val __ret = run {
val start0 = __0.first;
    val end0 = __0.third;
    val __temp0 = __action235(
        state,
        __0,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant14(__temp0), end0)
    __action149(
        state,
        __temp0_triple,
        __1,
    )
    }
    return __ret
}

internal fun __action313(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>): List<Pair<Pair<Spanned<AssignIdentP<AstNoPayload>>, Spanned<String>>, Spanned<Comma>>> {
    val __0 = sym0.asAssignIdentStringPairTriple()
    val __1 = sym1.asCommaSpannedTriple()
    val __ret = run {
val start0 = __0.first;
    val end0 = __1.third;
    val __temp0 = __action173(
        state,
        __0,
        __1,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant19(__temp0), end0)
    __action210(
        state,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action314(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>): List<Pair<Pair<Spanned<AssignIdentP<AstNoPayload>>, Spanned<String>>, Spanned<Comma>>> {
    val __0 = sym0.asLoadPairListTriple()
    val __1 = sym1.asAssignIdentStringPairTriple()
    val __2 = sym2.asCommaSpannedTriple()
    val __ret = run {
val start0 = __1.first;
    val end0 = __2.third;
    val __temp0 = __action173(
        state,
        __1,
        __2,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant19(__temp0), end0)
    __action211(
        state,
        __0,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action315(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>, sym4: Triple<Int, GrammarSymbol, Int>, sym5: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asTokenTriple()
        val __1 = sym1.asTokenTriple()
        val __2 = sym2.asStringSpannedTriple()
        val __3 = sym3.asCommaSpannedTriple()
        val __4 = sym4.asNullableAssignIdentStringPairTriple()
        val __5 = sym5.asTokenTriple()
        val __ret = run {
    val start0 = __3.third;
        val end0 = __4.first;
        val __temp0 = __action171(
            state,
            start0,
            end0,
        );
        val __temp0_triple= Triple(start0, GrammarSymbol.Variant20(__temp0), end0)
        __action70(
            state,
            __0,
            __1,
            __2,
            __3,
            __temp0_triple,
            __4,
            __5,
        )
        }
    __ret
}

internal fun __action316(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>, sym4: Triple<Int, GrammarSymbol, Int>, sym5: Triple<Int, GrammarSymbol, Int>, sym6: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asTokenTriple()
        val __1 = sym1.asTokenTriple()
        val __2 = sym2.asStringSpannedTriple()
        val __3 = sym3.asCommaSpannedTriple()
        val __4 = sym4.asLoadPairListTriple()
        val __5 = sym5.asNullableAssignIdentStringPairTriple()
        val __6 = sym6.asTokenTriple()
        val __ret = run {
    val start0 = __4.first;
        val end0 = __4.third;
        val __temp0 = __action172(
            state,
            __4,
        );
        val __temp0_triple= Triple(start0, GrammarSymbol.Variant20(__temp0), end0)
        __action70(
            state,
            __0,
            __1,
            __2,
            __3,
            __temp0_triple,
            __5,
            __6,
        )
        }
    __ret
}

internal fun __action317(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): Pair<Spanned<AssignIdentP<AstNoPayload>>, Spanned<String>>? {
    val __0 = sym0.asAssignIdentStringPairTriple()
    val __ret = run {
val start0 = __0.first;
    val end0 = __0.third;
    val __temp0 = __action170(
        state,
        __0,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant21(__temp0), end0)
    __action168(
        state,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action318(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>, sym4: Triple<Int, GrammarSymbol, Int>, sym5: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asTokenTriple()
        val __1 = sym1.asTokenTriple()
        val __2 = sym2.asStringSpannedTriple()
        val __3 = sym3.asCommaSpannedTriple()
        val __4 = sym4.asAssignIdentStringPairTriple()
        val __5 = sym5.asTokenTriple()
        val __ret = run {
    val start0 = __4.first;
        val end0 = __4.third;
        val __temp0 = __action317(
            state,
            __4,
        );
        val __temp0_triple= Triple(start0, GrammarSymbol.Variant22(__temp0), end0)
        __action315(
            state,
            __0,
            __1,
            __2,
            __3,
            __temp0_triple,
            __5,
        )
        }
    __ret
}

internal fun __action319(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>, sym4: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asTokenTriple()
        val __1 = sym1.asTokenTriple()
        val __2 = sym2.asStringSpannedTriple()
        val __3 = sym3.asCommaSpannedTriple()
        val __4 = sym4.asTokenTriple()
        val __ret = run {
    val start0 = __3.third;
        val end0 = __4.first;
        val __temp0 = __action169(
            state,
            start0,
            end0,
        );
        val __temp0_triple= Triple(start0, GrammarSymbol.Variant22(__temp0), end0)
        __action315(
            state,
            __0,
            __1,
            __2,
            __3,
            __temp0_triple,
            __4,
        )
        }
    __ret
}

internal fun __action320(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>, sym4: Triple<Int, GrammarSymbol, Int>, sym5: Triple<Int, GrammarSymbol, Int>, sym6: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asTokenTriple()
        val __1 = sym1.asTokenTriple()
        val __2 = sym2.asStringSpannedTriple()
        val __3 = sym3.asCommaSpannedTriple()
        val __4 = sym4.asLoadPairListTriple()
        val __5 = sym5.asAssignIdentStringPairTriple()
        val __6 = sym6.asTokenTriple()
        val __ret = run {
    val start0 = __5.first;
        val end0 = __5.third;
        val __temp0 = __action317(
            state,
            __5,
        );
        val __temp0_triple= Triple(start0, GrammarSymbol.Variant22(__temp0), end0)
        __action316(
            state,
            __0,
            __1,
            __2,
            __3,
            __4,
            __temp0_triple,
            __6,
        )
        }
    __ret
}

internal fun __action321(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>, sym4: Triple<Int, GrammarSymbol, Int>, sym5: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asTokenTriple()
        val __1 = sym1.asTokenTriple()
        val __2 = sym2.asStringSpannedTriple()
        val __3 = sym3.asCommaSpannedTriple()
        val __4 = sym4.asLoadPairListTriple()
        val __5 = sym5.asTokenTriple()
        val __ret = run {
    val start0 = __4.third;
        val end0 = __5.first;
        val __temp0 = __action169(
            state,
            start0,
            end0,
        );
        val __temp0_triple= Triple(start0, GrammarSymbol.Variant22(__temp0), end0)
        __action316(
            state,
            __0,
            __1,
            __2,
            __3,
            __4,
            __temp0_triple,
            __5,
        )
        }
    __ret
}

internal fun __action322(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): List<Spanned<StmtP<AstNoPayload>>> {
    val __0 = sym0.asStmtSpannedTriple()
    val __ret = run {
val start0 = __0.first;
    val end0 = __0.third;
    val __temp0 = __action257(
        state,
        __0,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant9(__temp0), end0)
    __action184(
        state,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action323(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>): List<Spanned<StmtP<AstNoPayload>>> {
    val __0 = sym0.asStmtSpannedTriple()
    val __1 = sym1.asTokenListTriple()
    val __ret = run {
val start0 = __0.first;
    val end0 = __1.third;
    val __temp0 = __action258(
        state,
        __0,
        __1,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant9(__temp0), end0)
    __action184(
        state,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action324(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>): List<Spanned<StmtP<AstNoPayload>>> {
    val __0 = sym0.asStmtSpannedListTriple()
    val __1 = sym1.asStmtSpannedTriple()
    val __ret = run {
val start0 = __1.first;
    val end0 = __1.third;
    val __temp0 = __action257(
        state,
        __1,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant9(__temp0), end0)
    __action185(
        state,
        __0,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action325(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>): List<Spanned<StmtP<AstNoPayload>>> {
    val __0 = sym0.asStmtSpannedListTriple()
    val __1 = sym1.asStmtSpannedTriple()
    val __2 = sym2.asTokenListTriple()
    val __ret = run {
val start0 = __1.first;
    val end0 = __2.third;
    val __temp0 = __action258(
        state,
        __1,
        __2,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant9(__temp0), end0)
    __action185(
        state,
        __0,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action326(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asIntValueTriple()
        val __1 = sym1.asIntValueTriple()
        val __ret = run {
    val start0 = __0.third;
        val end0 = __1.first;
        val __temp0 = __action193(
            state,
            start0,
            end0,
        );
        val __temp0_triple= Triple(start0, GrammarSymbol.Variant10(__temp0), end0)
        __action259(
            state,
            __0,
            __temp0_triple,
            __1,
        )
        }
    __ret
}

internal fun __action327(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asIntValueTriple()
        val __1 = sym1.asStmtSpannedListTriple()
        val __2 = sym2.asIntValueTriple()
        val __ret = run {
    val start0 = __1.first;
        val end0 = __1.third;
        val __temp0 = __action194(
            state,
            __1,
        );
        val __temp0_triple= Triple(start0, GrammarSymbol.Variant10(__temp0), end0)
        __action259(
            state,
            __0,
            __temp0_triple,
            __2,
        )
        }
    __ret
}

internal fun __action328(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asTokenListTriple()
        val __1 = sym1.asIntValueTriple()
        val __2 = sym2.asIntValueTriple()
        val __ret = run {
    val start0 = __1.third;
        val end0 = __2.first;
        val __temp0 = __action193(
            state,
            start0,
            end0,
        );
        val __temp0_triple= Triple(start0, GrammarSymbol.Variant10(__temp0), end0)
        __action260(
            state,
            __0,
            __1,
            __temp0_triple,
            __2,
        )
        }
    __ret
}

internal fun __action329(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asTokenListTriple()
        val __1 = sym1.asIntValueTriple()
        val __2 = sym2.asStmtSpannedListTriple()
        val __3 = sym3.asIntValueTriple()
        val __ret = run {
    val start0 = __2.first;
        val end0 = __2.third;
        val __temp0 = __action194(
            state,
            __2,
        );
        val __temp0_triple= Triple(start0, GrammarSymbol.Variant10(__temp0), end0)
        __action260(
            state,
            __0,
            __1,
            __temp0_triple,
            __3,
        )
        }
    __ret
}

internal fun __action330(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>): List<Spanned<ExprP<AstNoPayload>>> {
    val __0 = sym0.asExprSpannedTriple()
    val __1 = sym1.asTokenTriple()
    val __ret = run {
val start0 = __0.first;
    val end0 = __1.third;
    val __temp0 = __action224(
        state,
        __0,
        __1,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant17(__temp0), end0)
    __action241(
        state,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action331(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>): List<Spanned<ExprP<AstNoPayload>>> {
    val __0 = sym0.asExprSpannedListTriple()
    val __1 = sym1.asExprSpannedTriple()
    val __2 = sym2.asTokenTriple()
    val __ret = run {
val start0 = __1.first;
    val end0 = __2.third;
    val __temp0 = __action224(
        state,
        __1,
        __2,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant17(__temp0), end0)
    __action242(
        state,
        __0,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action332(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): List<Spanned<ExprP<AstNoPayload>>> {
    val __0 = sym0.asExprSpannedTriple()
    val __ret = run {
val start0 = __0.first;
    val end0 = __0.first;
    val __temp0 = __action222(
        state,
        start0,
        end0,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant18(__temp0), end0)
    __action265(
        state,
        __temp0_triple,
        __0,
    )
    }
    return __ret
}

internal fun __action333(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>): List<Spanned<ExprP<AstNoPayload>>> {
    val __0 = sym0.asExprSpannedListTriple()
    val __1 = sym1.asExprSpannedTriple()
    val __ret = run {
val start0 = __0.first;
    val end0 = __0.third;
    val __temp0 = __action223(
        state,
        __0,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant18(__temp0), end0)
    __action265(
        state,
        __temp0_triple,
        __1,
    )
    }
    return __ret
}

internal fun __action334(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, __lookbehind: Int, __lookahead: Int): List<Spanned<ExprP<AstNoPayload>>> {
    val __ret = run {
val start0 = __lookbehind;
    val end0 = __lookahead;
    val __temp0 = __action222(
        state,
        start0,
        end0,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant18(__temp0), end0)
    __action266(
        state,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action335(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): List<Spanned<ExprP<AstNoPayload>>> {
    val __0 = sym0.asExprSpannedListTriple()
    val __ret = run {
val start0 = __0.first;
    val end0 = __0.third;
    val __temp0 = __action223(
        state,
        __0,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant18(__temp0), end0)
    __action266(
        state,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action336(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asIntValueTriple()
        val __1 = sym1.asExprSpannedTriple()
        val __2 = sym2.asTokenTriple()
        val __3 = sym3.asIntValueTriple()
        val __ret = run {
    val start0 = __0.third;
        val end0 = __1.first;
        val __temp0 = __action222(
            state,
            start0,
            end0,
        );
        val __temp0_triple= Triple(start0, GrammarSymbol.Variant18(__temp0), end0)
        __action253(
            state,
            __0,
            __temp0_triple,
            __1,
            __2,
            __3,
        )
        }
    __ret
}

internal fun __action337(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>, sym4: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asIntValueTriple()
        val __1 = sym1.asExprSpannedListTriple()
        val __2 = sym2.asExprSpannedTriple()
        val __3 = sym3.asTokenTriple()
        val __4 = sym4.asIntValueTriple()
        val __ret = run {
    val start0 = __1.first;
        val end0 = __1.third;
        val __temp0 = __action223(
            state,
            __1,
        );
        val __temp0_triple= Triple(start0, GrammarSymbol.Variant18(__temp0), end0)
        __action253(
            state,
            __0,
            __temp0_triple,
            __2,
            __3,
            __4,
        )
        }
    __ret
}

internal fun __action338(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asIntValueTriple()
        val __1 = sym1.asExprSpannedTriple()
        val __2 = sym2.asIntValueTriple()
        val __ret = run {
    val start0 = __0.third;
        val end0 = __1.first;
        val __temp0 = __action222(
            state,
            start0,
            end0,
        );
        val __temp0_triple= Triple(start0, GrammarSymbol.Variant18(__temp0), end0)
        __action254(
            state,
            __0,
            __temp0_triple,
            __1,
            __2,
        )
        }
    __ret
}

internal fun __action339(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asIntValueTriple()
        val __1 = sym1.asExprSpannedListTriple()
        val __2 = sym2.asExprSpannedTriple()
        val __3 = sym3.asIntValueTriple()
        val __ret = run {
    val start0 = __1.first;
        val end0 = __1.third;
        val __temp0 = __action223(
            state,
            __1,
        );
        val __temp0_triple= Triple(start0, GrammarSymbol.Variant18(__temp0), end0)
        __action254(
            state,
            __0,
            __temp0_triple,
            __2,
            __3,
        )
        }
    __ret
}

internal fun __action340(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asArgumentValueTriple()
        val __1 = sym1.asIntValueTriple()
        val __ret = run {
    val start0 = __0.first;
        val end0 = __0.first;
        val __temp0 = __action199(
            state,
            start0,
            end0,
        );
        val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
        __action157(
            state,
            __temp0_triple,
            __0,
            __1,
        )
        }
    __ret
}

internal fun __action341(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asExprValueTriple()
        val __1 = sym1.asIntValueTriple()
        val __ret = run {
    val start0 = __0.first;
        val end0 = __0.first;
        val __temp0 = __action199(
            state,
            start0,
            end0,
        );
        val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
        __action153(
            state,
            __temp0_triple,
            __0,
            __1,
        )
        }
    __ret
}

internal fun __action342(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asExprValueTriple()
        val __1 = sym1.asIntValueTriple()
        val __ret = run {
    val start0 = __0.first;
        val end0 = __0.first;
        val __temp0 = __action199(
            state,
            start0,
            end0,
        );
        val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
        __action150(
            state,
            __temp0_triple,
            __0,
            __1,
        )
        }
    __ret
}

internal fun __action343(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asExprValueTriple()
        val __1 = sym1.asIntValueTriple()
        val __ret = run {
    val start0 = __0.first;
        val end0 = __0.first;
        val __temp0 = __action199(
            state,
            start0,
            end0,
        );
        val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
        __action154(
            state,
            __temp0_triple,
            __0,
            __1,
        )
        }
    __ret
}

internal fun __action344(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asParameterValueTriple()
        val __1 = sym1.asIntValueTriple()
        val __ret = run {
    val start0 = __0.first;
        val end0 = __0.first;
        val __temp0 = __action199(
            state,
            start0,
            end0,
        );
        val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
        __action189(
            state,
            __temp0_triple,
            __0,
            __1,
        )
        }
    __ret
}

internal fun __action345(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asParameterValueTriple()
        val __1 = sym1.asIntValueTriple()
        val __ret = run {
    val start0 = __0.first;
        val end0 = __0.first;
        val __temp0 = __action199(
            state,
            start0,
            end0,
        );
        val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
        __action190(
            state,
            __temp0_triple,
            __0,
            __1,
        )
        }
    __ret
}

internal fun __action346(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asStmtValueTriple()
        val __1 = sym1.asIntValueTriple()
        val __ret = run {
    val start0 = __0.first;
        val end0 = __0.first;
        val __temp0 = __action199(
            state,
            start0,
            end0,
        );
        val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
        __action176(
            state,
            __temp0_triple,
            __0,
            __1,
        )
        }
    __ret
}

internal fun __action347(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asStmtValueTriple()
        val __1 = sym1.asIntValueTriple()
        val __ret = run {
    val start0 = __0.first;
        val end0 = __0.first;
        val __temp0 = __action199(
            state,
            start0,
            end0,
        );
        val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
        __action192(
            state,
            __temp0_triple,
            __0,
            __1,
        )
        }
    __ret
}

internal fun __action348(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asStmtValueTriple()
        val __1 = sym1.asIntValueTriple()
        val __ret = run {
    val start0 = __0.first;
        val end0 = __0.first;
        val __temp0 = __action199(
            state,
            start0,
            end0,
        );
        val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
        __action175(
            state,
            __temp0_triple,
            __0,
            __1,
        )
        }
    __ret
}

internal fun __action349(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asStmtValueTriple()
        val __1 = sym1.asIntValueTriple()
        val __ret = run {
    val start0 = __0.first;
        val end0 = __0.first;
        val __temp0 = __action199(
            state,
            start0,
            end0,
        );
        val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
        __action179(
            state,
            __temp0_triple,
            __0,
            __1,
        )
        }
    __ret
}

internal fun __action350(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asStmtValueTriple()
        val __1 = sym1.asIntValueTriple()
        val __ret = run {
    val start0 = __0.first;
        val end0 = __0.first;
        val __temp0 = __action199(
            state,
            start0,
            end0,
        );
        val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
        __action183(
            state,
            __temp0_triple,
            __0,
            __1,
        )
        }
    __ret
}

internal fun __action351(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asStmtValueTriple()
        val __1 = sym1.asIntValueTriple()
        val __ret = run {
    val start0 = __0.first;
        val end0 = __0.first;
        val __temp0 = __action199(
            state,
            start0,
            end0,
        );
        val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
        __action180(
            state,
            __temp0_triple,
            __0,
            __1,
        )
        }
    __ret
}

internal fun __action352(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asStmtValueTriple()
        val __1 = sym1.asIntValueTriple()
        val __ret = run {
    val start0 = __0.first;
        val end0 = __0.first;
        val __temp0 = __action199(
            state,
            start0,
            end0,
        );
        val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
        __action174(
            state,
            __temp0_triple,
            __0,
            __1,
        )
        }
    __ret
}

internal fun __action353(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asExprSpannedTriple()
        val __1 = sym1.asTokenTriple()
        val __2 = sym2.asExprSpannedTriple()
        val __3 = sym3.asIntValueTriple()
        val __ret = run {
    val start0 = __0.first;
        val end0 = __0.first;
        val __temp0 = __action199(
            state,
            start0,
            end0,
        );
        val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
        __action114(
            state,
            __temp0_triple,
            __0,
            __1,
            __2,
            __3,
        )
        }
    __ret
}

internal fun __action354(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asExprSpannedTriple()
        val __1 = sym1.asTokenTriple()
        val __2 = sym2.asExprSpannedTriple()
        val __3 = sym3.asIntValueTriple()
        val __ret = run {
    val start0 = __0.first;
        val end0 = __0.first;
        val __temp0 = __action199(
            state,
            start0,
            end0,
        );
        val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
        __action137(
            state,
            __temp0_triple,
            __0,
            __1,
            __2,
            __3,
        )
        }
    __ret
}

internal fun __action355(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asExprSpannedTriple()
        val __1 = sym1.asTokenTriple()
        val __2 = sym2.asExprSpannedTriple()
        val __3 = sym3.asIntValueTriple()
        val __ret = run {
    val start0 = __0.first;
        val end0 = __0.first;
        val __temp0 = __action199(
            state,
            start0,
            end0,
        );
        val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
        __action138(
            state,
            __temp0_triple,
            __0,
            __1,
            __2,
            __3,
        )
        }
    __ret
}

internal fun __action356(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asExprSpannedTriple()
        val __1 = sym1.asTokenTriple()
        val __2 = sym2.asExprSpannedTriple()
        val __3 = sym3.asIntValueTriple()
        val __ret = run {
    val start0 = __0.first;
        val end0 = __0.first;
        val __temp0 = __action199(
            state,
            start0,
            end0,
        );
        val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
        __action132(
            state,
            __temp0_triple,
            __0,
            __1,
            __2,
            __3,
        )
        }
    __ret
}

internal fun __action357(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asExprSpannedTriple()
        val __1 = sym1.asTokenTriple()
        val __2 = sym2.asExprSpannedTriple()
        val __3 = sym3.asIntValueTriple()
        val __ret = run {
    val start0 = __0.first;
        val end0 = __0.first;
        val __temp0 = __action199(
            state,
            start0,
            end0,
        );
        val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
        __action128(
            state,
            __temp0_triple,
            __0,
            __1,
            __2,
            __3,
        )
        }
    __ret
}

internal fun __action358(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asExprSpannedTriple()
        val __1 = sym1.asTokenTriple()
        val __2 = sym2.asExprSpannedTriple()
        val __3 = sym3.asIntValueTriple()
        val __ret = run {
    val start0 = __0.first;
        val end0 = __0.first;
        val __temp0 = __action199(
            state,
            start0,
            end0,
        );
        val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
        __action130(
            state,
            __temp0_triple,
            __0,
            __1,
            __2,
            __3,
        )
        }
    __ret
}

internal fun __action359(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asTokenTriple()
        val __1 = sym1.asIntValueTriple()
        val __ret = run {
    val start0 = __0.first;
        val end0 = __0.first;
        val __temp0 = __action199(
            state,
            start0,
            end0,
        );
        val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
        __action67(
            state,
            __temp0_triple,
            __0,
            __1,
        )
        }
    __ret
}

internal fun __action360(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asExprSpannedTriple()
        val __1 = sym1.asTokenTriple()
        val __2 = sym2.asExprSpannedTriple()
        val __3 = sym3.asIntValueTriple()
        val __ret = run {
    val start0 = __0.first;
        val end0 = __0.first;
        val __temp0 = __action199(
            state,
            start0,
            end0,
        );
        val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
        __action118(
            state,
            __temp0_triple,
            __0,
            __1,
            __2,
            __3,
        )
        }
    __ret
}

internal fun __action361(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asExprSpannedTriple()
        val __1 = sym1.asTokenTriple()
        val __2 = sym2.asExprSpannedTriple()
        val __3 = sym3.asIntValueTriple()
        val __ret = run {
    val start0 = __0.first;
        val end0 = __0.first;
        val __temp0 = __action199(
            state,
            start0,
            end0,
        );
        val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
        __action119(
            state,
            __temp0_triple,
            __0,
            __1,
            __2,
            __3,
        )
        }
    __ret
}

internal fun __action362(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asExprSpannedTriple()
        val __1 = sym1.asTokenTriple()
        val __2 = sym2.asExprSpannedTriple()
        val __3 = sym3.asIntValueTriple()
        val __ret = run {
    val start0 = __0.first;
        val end0 = __0.first;
        val __temp0 = __action199(
            state,
            start0,
            end0,
        );
        val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
        __action120(
            state,
            __temp0_triple,
            __0,
            __1,
            __2,
            __3,
        )
        }
    __ret
}

internal fun __action363(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asExprSpannedTriple()
        val __1 = sym1.asTokenTriple()
        val __2 = sym2.asExprSpannedTriple()
        val __3 = sym3.asIntValueTriple()
        val __ret = run {
    val start0 = __0.first;
        val end0 = __0.first;
        val __temp0 = __action199(
            state,
            start0,
            end0,
        );
        val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
        __action121(
            state,
            __temp0_triple,
            __0,
            __1,
            __2,
            __3,
        )
        }
    __ret
}

internal fun __action364(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asExprSpannedTriple()
        val __1 = sym1.asTokenTriple()
        val __2 = sym2.asExprSpannedTriple()
        val __3 = sym3.asIntValueTriple()
        val __ret = run {
    val start0 = __0.first;
        val end0 = __0.first;
        val __temp0 = __action199(
            state,
            start0,
            end0,
        );
        val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
        __action122(
            state,
            __temp0_triple,
            __0,
            __1,
            __2,
            __3,
        )
        }
    __ret
}

internal fun __action365(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asExprSpannedTriple()
        val __1 = sym1.asTokenTriple()
        val __2 = sym2.asExprSpannedTriple()
        val __3 = sym3.asIntValueTriple()
        val __ret = run {
    val start0 = __0.first;
        val end0 = __0.first;
        val __temp0 = __action199(
            state,
            start0,
            end0,
        );
        val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
        __action123(
            state,
            __temp0_triple,
            __0,
            __1,
            __2,
            __3,
        )
        }
    __ret
}

internal fun __action366(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asExprSpannedTriple()
        val __1 = sym1.asTokenTriple()
        val __2 = sym2.asExprSpannedTriple()
        val __3 = sym3.asIntValueTriple()
        val __ret = run {
    val start0 = __0.first;
        val end0 = __0.first;
        val __temp0 = __action199(
            state,
            start0,
            end0,
        );
        val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
        __action124(
            state,
            __temp0_triple,
            __0,
            __1,
            __2,
            __3,
        )
        }
    __ret
}

internal fun __action367(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>, sym4: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asExprSpannedTriple()
        val __1 = sym1.asTokenTriple()
        val __2 = sym2.asTokenTriple()
        val __3 = sym3.asExprSpannedTriple()
        val __4 = sym4.asIntValueTriple()
        val __ret = run {
    val start0 = __0.first;
        val end0 = __0.first;
        val __temp0 = __action199(
            state,
            start0,
            end0,
        );
        val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
        __action125(
            state,
            __temp0_triple,
            __0,
            __1,
            __2,
            __3,
            __4,
        )
        }
    __ret
}

internal fun __action368(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asTokenTriple()
        val __1 = sym1.asExprSpannedTriple()
        val __2 = sym2.asIntValueTriple()
        val __ret = run {
    val start0 = __0.first;
        val end0 = __0.first;
        val __temp0 = __action199(
            state,
            start0,
            end0,
        );
        val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
        __action145(
            state,
            __temp0_triple,
            __0,
            __1,
            __2,
        )
        }
    __ret
}

internal fun __action369(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asTokenTriple()
        val __1 = sym1.asExprSpannedTriple()
        val __2 = sym2.asIntValueTriple()
        val __ret = run {
    val start0 = __0.first;
        val end0 = __0.first;
        val __temp0 = __action199(
            state,
            start0,
            end0,
        );
        val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
        __action146(
            state,
            __temp0_triple,
            __0,
            __1,
            __2,
        )
        }
    __ret
}

internal fun __action370(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asTokenTriple()
        val __1 = sym1.asExprSpannedTriple()
        val __2 = sym2.asIntValueTriple()
        val __ret = run {
    val start0 = __0.first;
        val end0 = __0.first;
        val __temp0 = __action199(
            state,
            start0,
            end0,
        );
        val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
        __action147(
            state,
            __temp0_triple,
            __0,
            __1,
            __2,
        )
        }
    __ret
}

internal fun __action371(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asExprSpannedTriple()
        val __1 = sym1.asTokenTriple()
        val __2 = sym2.asIntValueTriple()
        val __ret = run {
    val start0 = __0.first;
        val end0 = __0.first;
        val __temp0 = __action199(
            state,
            start0,
            end0,
        );
        val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
        __action305(
            state,
            __temp0_triple,
            __0,
            __1,
            __2,
        )
        }
    __ret
}

internal fun __action372(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asExprSpannedListTriple()
        val __1 = sym1.asExprSpannedTriple()
        val __2 = sym2.asTokenTriple()
        val __3 = sym3.asIntValueTriple()
        val __ret = run {
    val start0 = __0.first;
        val end0 = __0.first;
        val __temp0 = __action199(
            state,
            start0,
            end0,
        );
        val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
        __action306(
            state,
            __temp0_triple,
            __0,
            __1,
            __2,
            __3,
        )
        }
    __ret
}

internal fun __action373(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asExprSpannedTriple()
        val __1 = sym1.asIntValueTriple()
        val __ret = run {
    val start0 = __0.first;
        val end0 = __0.first;
        val __temp0 = __action199(
            state,
            start0,
            end0,
        );
        val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
        __action307(
            state,
            __temp0_triple,
            __0,
            __1,
        )
        }
    __ret
}

internal fun __action374(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asExprSpannedListTriple()
        val __1 = sym1.asExprSpannedTriple()
        val __2 = sym2.asIntValueTriple()
        val __ret = run {
    val start0 = __0.first;
        val end0 = __0.first;
        val __temp0 = __action199(
            state,
            start0,
            end0,
        );
        val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
        __action308(
            state,
            __temp0_triple,
            __0,
            __1,
            __2,
        )
        }
    __ret
}

internal fun __action375(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asExprSpannedTriple()
        val __1 = sym1.asTokenTriple()
        val __2 = sym2.asIntValueTriple()
        val __ret = run {
    val start0 = __0.first;
        val end0 = __0.first;
        val __temp0 = __action199(
            state,
            start0,
            end0,
        );
        val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
        __action336(
            state,
            __temp0_triple,
            __0,
            __1,
            __2,
        )
        }
    __ret
}

internal fun __action376(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asExprSpannedListTriple()
        val __1 = sym1.asExprSpannedTriple()
        val __2 = sym2.asTokenTriple()
        val __3 = sym3.asIntValueTriple()
        val __ret = run {
    val start0 = __0.first;
        val end0 = __0.first;
        val __temp0 = __action199(
            state,
            start0,
            end0,
        );
        val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
        __action337(
            state,
            __temp0_triple,
            __0,
            __1,
            __2,
            __3,
        )
        }
    __ret
}

internal fun __action377(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asExprSpannedTriple()
        val __1 = sym1.asIntValueTriple()
        val __ret = run {
    val start0 = __0.first;
        val end0 = __0.first;
        val __temp0 = __action199(
            state,
            start0,
            end0,
        );
        val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
        __action338(
            state,
            __temp0_triple,
            __0,
            __1,
        )
        }
    __ret
}

internal fun __action378(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asExprSpannedListTriple()
        val __1 = sym1.asExprSpannedTriple()
        val __2 = sym2.asIntValueTriple()
        val __ret = run {
    val start0 = __0.first;
        val end0 = __0.first;
        val __temp0 = __action199(
            state,
            start0,
            end0,
        );
        val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
        __action339(
            state,
            __temp0_triple,
            __0,
            __1,
            __2,
        )
        }
    __ret
}

internal fun __action379(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asTokenTriple()
        val __1 = sym1.asExprSpannedTriple()
        val __2 = sym2.asIntValueTriple()
        val __ret = run {
    val start0 = __0.first;
        val end0 = __0.first;
        val __temp0 = __action199(
            state,
            start0,
            end0,
        );
        val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
        __action116(
            state,
            __temp0_triple,
            __0,
            __1,
            __2,
        )
        }
    __ret
}

internal fun __action380(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asIdentSpannedTriple()
        val __1 = sym1.asIntValueTriple()
        val __ret = run {
    val start0 = __0.first;
        val end0 = __0.first;
        val __temp0 = __action199(
            state,
            start0,
            end0,
        );
        val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
        __action87(
            state,
            __temp0_triple,
            __0,
            __1,
        )
        }
    __ret
}

internal fun __action381(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asTokenIntSpannedTriple()
        val __1 = sym1.asIntValueTriple()
        val __ret = run {
    val start0 = __0.first;
        val end0 = __0.first;
        val __temp0 = __action199(
            state,
            start0,
            end0,
        );
        val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
        __action88(
            state,
            __temp0_triple,
            __0,
            __1,
        )
        }
    __ret
}

internal fun __action382(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asDoubleSpannedTriple()
        val __1 = sym1.asIntValueTriple()
        val __ret = run {
    val start0 = __0.first;
        val end0 = __0.first;
        val __temp0 = __action199(
            state,
            start0,
            end0,
        );
        val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
        __action89(
            state,
            __temp0_triple,
            __0,
            __1,
        )
        }
    __ret
}

internal fun __action383(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asStringSpannedTriple()
        val __1 = sym1.asIntValueTriple()
        val __ret = run {
    val start0 = __0.first;
        val end0 = __0.first;
        val __temp0 = __action199(
            state,
            start0,
            end0,
        );
        val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
        __action90(
            state,
            __temp0_triple,
            __0,
            __1,
        )
        }
    __ret
}

internal fun __action384(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asTokenTriple()
        val __1 = sym1.asIntValueTriple()
        val __ret = run {
    val start0 = __0.first;
        val end0 = __0.first;
        val __temp0 = __action199(
            state,
            start0,
            end0,
        );
        val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
        __action91(
            state,
            __temp0_triple,
            __0,
            __1,
        )
        }
    __ret
}

internal fun __action385(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asTokenTriple()
        val __1 = sym1.asExprSpannedListTriple()
        val __2 = sym2.asTokenTriple()
        val __3 = sym3.asIntValueTriple()
        val __ret = run {
    val start0 = __0.first;
        val end0 = __0.first;
        val __temp0 = __action199(
            state,
            start0,
            end0,
        );
        val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
        __action92(
            state,
            __temp0_triple,
            __0,
            __1,
            __2,
            __3,
        )
        }
    __ret
}

internal fun __action386(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asTokenTriple()
        val __1 = sym1.asExprPairListTriple()
        val __2 = sym2.asTokenTriple()
        val __3 = sym3.asIntValueTriple()
        val __ret = run {
    val start0 = __0.first;
        val end0 = __0.first;
        val __temp0 = __action199(
            state,
            start0,
            end0,
        );
        val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
        __action94(
            state,
            __temp0_triple,
            __0,
            __1,
            __2,
            __3,
        )
        }
    __ret
}

internal fun __action387(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asTokenTriple()
        val __1 = sym1.asNullableExprSpannedTriple()
        val __2 = sym2.asTokenTriple()
        val __3 = sym3.asIntValueTriple()
        val __ret = run {
    val start0 = __0.first;
        val end0 = __0.first;
        val __temp0 = __action199(
            state,
            start0,
            end0,
        );
        val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
        __action96(
            state,
            __temp0_triple,
            __0,
            __1,
            __2,
            __3,
        )
        }
    __ret
}

internal fun __action388(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asFStringSpannedTriple()
        val __1 = sym1.asIntValueTriple()
        val __ret = run {
    val start0 = __0.first;
        val end0 = __0.first;
        val __temp0 = __action199(
            state,
            start0,
            end0,
        );
        val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
        __action97(
            state,
            __temp0_triple,
            __0,
            __1,
        )
        }
    __ret
}

internal fun __action389(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asExprSpannedTriple()
        val __1 = sym1.asTokenTriple()
        val __2 = sym2.asExprSpannedTriple()
        val __3 = sym3.asIntValueTriple()
        val __ret = run {
    val start0 = __0.first;
        val end0 = __0.first;
        val __temp0 = __action199(
            state,
            start0,
            end0,
        );
        val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
        __action112(
            state,
            __temp0_triple,
            __0,
            __1,
            __2,
            __3,
        )
        }
    __ret
}

internal fun __action390(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asExprSpannedTriple()
        val __1 = sym1.asTokenTriple()
        val __2 = sym2.asStringSpannedTriple()
        val __3 = sym3.asIntValueTriple()
        val __ret = run {
    val start0 = __0.first;
        val end0 = __0.first;
        val __temp0 = __action199(
            state,
            start0,
            end0,
        );
        val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
        __action75(
            state,
            __temp0_triple,
            __0,
            __1,
            __2,
            __3,
        )
        }
    __ret
}

internal fun __action391(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>, sym4: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asExprSpannedTriple()
        val __1 = sym1.asTokenTriple()
        val __2 = sym2.asArgumentSpannedListTriple()
        val __3 = sym3.asTokenTriple()
        val __4 = sym4.asIntValueTriple()
        val __ret = run {
    val start0 = __0.first;
        val end0 = __0.first;
        val __temp0 = __action199(
            state,
            start0,
            end0,
        );
        val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
        __action76(
            state,
            __temp0_triple,
            __0,
            __1,
            __2,
            __3,
            __4,
        )
        }
    __ret
}

internal fun __action392(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>, sym4: Triple<Int, GrammarSymbol, Int>, sym5: Triple<Int, GrammarSymbol, Int>, sym6: Triple<Int, GrammarSymbol, Int>, sym7: Triple<Int, GrammarSymbol, Int>, sym8: Triple<Int, GrammarSymbol, Int>): Spanned<ExprP<AstNoPayload>> {
    val __0 = sym0.asExprSpannedTriple()
    val __1 = sym1.asTokenTriple()
    val __2 = sym2.asExprSpannedTriple()
    val __3 = sym3.asTokenTriple()
    val __4 = sym4.asExprSpannedTriple()
    val __5 = sym5.asTokenTriple()
    val __6 = sym6.asExprSpannedTriple()
    val __7 = sym7.asTokenTriple()
    val __8 = sym8.asIntValueTriple()
    val __ret = run {
val start0 = __0.first;
    val end0 = __0.first;
    val __temp0 = __action199(
        state,
        start0,
        end0,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
    __action273(
        state,
        __temp0_triple,
        __0,
        __1,
        __2,
        __3,
        __4,
        __5,
        __6,
        __7,
        __8,
    )
    }
    return __ret
}

internal fun __action393(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>, sym4: Triple<Int, GrammarSymbol, Int>, sym5: Triple<Int, GrammarSymbol, Int>, sym6: Triple<Int, GrammarSymbol, Int>, sym7: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asExprSpannedTriple()
        val __1 = sym1.asTokenTriple()
        val __2 = sym2.asExprSpannedTriple()
        val __3 = sym3.asTokenTriple()
        val __4 = sym4.asExprSpannedTriple()
        val __5 = sym5.asTokenTriple()
        val __6 = sym6.asTokenTriple()
        val __7 = sym7.asIntValueTriple()
        val __ret = run {
    val start0 = __0.first;
        val end0 = __0.first;
        val __temp0 = __action199(
            state,
            start0,
            end0,
        );
        val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
        __action274(
            state,
            __temp0_triple,
            __0,
            __1,
            __2,
            __3,
            __4,
            __5,
            __6,
            __7,
        )
        }
    __ret
}

internal fun __action394(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>, sym4: Triple<Int, GrammarSymbol, Int>, sym5: Triple<Int, GrammarSymbol, Int>, sym6: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asExprSpannedTriple()
        val __1 = sym1.asTokenTriple()
        val __2 = sym2.asExprSpannedTriple()
        val __3 = sym3.asTokenTriple()
        val __4 = sym4.asExprSpannedTriple()
        val __5 = sym5.asTokenTriple()
        val __6 = sym6.asIntValueTriple()
        val __ret = run {
    val start0 = __0.first;
        val end0 = __0.first;
        val __temp0 = __action199(
            state,
            start0,
            end0,
        );
        val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
        __action275(
            state,
            __temp0_triple,
            __0,
            __1,
            __2,
            __3,
            __4,
            __5,
            __6,
        )
        }
    __ret
}

internal fun __action395(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>, sym4: Triple<Int, GrammarSymbol, Int>, sym5: Triple<Int, GrammarSymbol, Int>, sym6: Triple<Int, GrammarSymbol, Int>, sym7: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asExprSpannedTriple()
        val __1 = sym1.asTokenTriple()
        val __2 = sym2.asExprSpannedTriple()
        val __3 = sym3.asTokenTriple()
        val __4 = sym4.asTokenTriple()
        val __5 = sym5.asExprSpannedTriple()
        val __6 = sym6.asTokenTriple()
        val __7 = sym7.asIntValueTriple()
        val __ret = run {
    val start0 = __0.first;
        val end0 = __0.first;
        val __temp0 = __action199(
            state,
            start0,
            end0,
        );
        val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
        __action276(
            state,
            __temp0_triple,
            __0,
            __1,
            __2,
            __3,
            __4,
            __5,
            __6,
            __7,
        )
        }
    __ret
}

internal fun __action396(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>, sym4: Triple<Int, GrammarSymbol, Int>, sym5: Triple<Int, GrammarSymbol, Int>, sym6: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asExprSpannedTriple()
        val __1 = sym1.asTokenTriple()
        val __2 = sym2.asExprSpannedTriple()
        val __3 = sym3.asTokenTriple()
        val __4 = sym4.asTokenTriple()
        val __5 = sym5.asTokenTriple()
        val __6 = sym6.asIntValueTriple()
        val __ret = run {
    val start0 = __0.first;
        val end0 = __0.first;
        val __temp0 = __action199(
            state,
            start0,
            end0,
        );
        val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
        __action277(
            state,
            __temp0_triple,
            __0,
            __1,
            __2,
            __3,
            __4,
            __5,
            __6,
        )
        }
    __ret
}

internal fun __action397(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>, sym4: Triple<Int, GrammarSymbol, Int>, sym5: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asExprSpannedTriple()
        val __1 = sym1.asTokenTriple()
        val __2 = sym2.asExprSpannedTriple()
        val __3 = sym3.asTokenTriple()
        val __4 = sym4.asTokenTriple()
        val __5 = sym5.asIntValueTriple()
        val __ret = run {
    val start0 = __0.first;
        val end0 = __0.first;
        val __temp0 = __action199(
            state,
            start0,
            end0,
        );
        val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
        __action278(
            state,
            __temp0_triple,
            __0,
            __1,
            __2,
            __3,
            __4,
            __5,
        )
        }
    __ret
}

internal fun __action398(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>, sym4: Triple<Int, GrammarSymbol, Int>, sym5: Triple<Int, GrammarSymbol, Int>, sym6: Triple<Int, GrammarSymbol, Int>, sym7: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asExprSpannedTriple()
        val __1 = sym1.asTokenTriple()
        val __2 = sym2.asTokenTriple()
        val __3 = sym3.asExprSpannedTriple()
        val __4 = sym4.asTokenTriple()
        val __5 = sym5.asExprSpannedTriple()
        val __6 = sym6.asTokenTriple()
        val __7 = sym7.asIntValueTriple()
        val __ret = run {
    val start0 = __0.first;
        val end0 = __0.first;
        val __temp0 = __action199(
            state,
            start0,
            end0,
        );
        val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
        __action279(
            state,
            __temp0_triple,
            __0,
            __1,
            __2,
            __3,
            __4,
            __5,
            __6,
            __7,
        )
        }
    __ret
}

internal fun __action399(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>, sym4: Triple<Int, GrammarSymbol, Int>, sym5: Triple<Int, GrammarSymbol, Int>, sym6: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asExprSpannedTriple()
        val __1 = sym1.asTokenTriple()
        val __2 = sym2.asTokenTriple()
        val __3 = sym3.asExprSpannedTriple()
        val __4 = sym4.asTokenTriple()
        val __5 = sym5.asTokenTriple()
        val __6 = sym6.asIntValueTriple()
        val __ret = run {
    val start0 = __0.first;
        val end0 = __0.first;
        val __temp0 = __action199(
            state,
            start0,
            end0,
        );
        val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
        __action280(
            state,
            __temp0_triple,
            __0,
            __1,
            __2,
            __3,
            __4,
            __5,
            __6,
        )
        }
    __ret
}

internal fun __action400(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>, sym4: Triple<Int, GrammarSymbol, Int>, sym5: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asExprSpannedTriple()
        val __1 = sym1.asTokenTriple()
        val __2 = sym2.asTokenTriple()
        val __3 = sym3.asExprSpannedTriple()
        val __4 = sym4.asTokenTriple()
        val __5 = sym5.asIntValueTriple()
        val __ret = run {
    val start0 = __0.first;
        val end0 = __0.first;
        val __temp0 = __action199(
            state,
            start0,
            end0,
        );
        val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
        __action281(
            state,
            __temp0_triple,
            __0,
            __1,
            __2,
            __3,
            __4,
            __5,
        )
        }
    __ret
}

internal fun __action401(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>, sym4: Triple<Int, GrammarSymbol, Int>, sym5: Triple<Int, GrammarSymbol, Int>, sym6: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asExprSpannedTriple()
        val __1 = sym1.asTokenTriple()
        val __2 = sym2.asTokenTriple()
        val __3 = sym3.asTokenTriple()
        val __4 = sym4.asExprSpannedTriple()
        val __5 = sym5.asTokenTriple()
        val __6 = sym6.asIntValueTriple()
        val __ret = run {
    val start0 = __0.first;
        val end0 = __0.first;
        val __temp0 = __action199(
            state,
            start0,
            end0,
        );
        val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
        __action282(
            state,
            __temp0_triple,
            __0,
            __1,
            __2,
            __3,
            __4,
            __5,
            __6,
        )
        }
    __ret
}

internal fun __action402(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>, sym4: Triple<Int, GrammarSymbol, Int>, sym5: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asExprSpannedTriple()
        val __1 = sym1.asTokenTriple()
        val __2 = sym2.asTokenTriple()
        val __3 = sym3.asTokenTriple()
        val __4 = sym4.asTokenTriple()
        val __5 = sym5.asIntValueTriple()
        val __ret = run {
    val start0 = __0.first;
        val end0 = __0.first;
        val __temp0 = __action199(
            state,
            start0,
            end0,
        );
        val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
        __action283(
            state,
            __temp0_triple,
            __0,
            __1,
            __2,
            __3,
            __4,
            __5,
        )
        }
    __ret
}

internal fun __action403(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>, sym4: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asExprSpannedTriple()
        val __1 = sym1.asTokenTriple()
        val __2 = sym2.asTokenTriple()
        val __3 = sym3.asTokenTriple()
        val __4 = sym4.asIntValueTriple()
        val __ret = run {
    val start0 = __0.first;
        val end0 = __0.first;
        val __temp0 = __action199(
            state,
            start0,
            end0,
        );
        val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
        __action284(
            state,
            __temp0_triple,
            __0,
            __1,
            __2,
            __3,
            __4,
        )
        }
    __ret
}

internal fun __action404(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>, sym4: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asExprSpannedTriple()
        val __1 = sym1.asTokenTriple()
        val __2 = sym2.asExprSpannedTriple()
        val __3 = sym3.asTokenTriple()
        val __4 = sym4.asIntValueTriple()
        val __ret = run {
    val start0 = __0.first;
        val end0 = __0.first;
        val __temp0 = __action199(
            state,
            start0,
            end0,
        );
        val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
        __action78(
            state,
            __temp0_triple,
            __0,
            __1,
            __2,
            __3,
            __4,
        )
        }
    __ret
}

internal fun __action405(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>, sym4: Triple<Int, GrammarSymbol, Int>, sym5: Triple<Int, GrammarSymbol, Int>, sym6: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asExprSpannedTriple()
        val __1 = sym1.asTokenTriple()
        val __2 = sym2.asExprSpannedTriple()
        val __3 = sym3.asTokenTriple()
        val __4 = sym4.asExprSpannedTriple()
        val __5 = sym5.asTokenTriple()
        val __6 = sym6.asIntValueTriple()
        val __ret = run {
    val start0 = __0.first;
        val end0 = __0.first;
        val __temp0 = __action199(
            state,
            start0,
            end0,
        );
        val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
        __action79(
            state,
            __temp0_triple,
            __0,
            __1,
            __2,
            __3,
            __4,
            __5,
            __6,
        )
        }
    __ret
}

internal fun __action406(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asExprSpannedTriple()
        val __1 = sym1.asTokenTriple()
        val __2 = sym2.asExprSpannedTriple()
        val __3 = sym3.asIntValueTriple()
        val __ret = run {
    val start0 = __0.first;
        val end0 = __0.first;
        val __temp0 = __action199(
            state,
            start0,
            end0,
        );
        val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
        __action140(
            state,
            __temp0_triple,
            __0,
            __1,
            __2,
            __3,
        )
        }
    __ret
}

internal fun __action407(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asExprSpannedTriple()
        val __1 = sym1.asTokenTriple()
        val __2 = sym2.asExprSpannedTriple()
        val __3 = sym3.asIntValueTriple()
        val __ret = run {
    val start0 = __0.first;
        val end0 = __0.first;
        val __temp0 = __action199(
            state,
            start0,
            end0,
        );
        val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
        __action141(
            state,
            __temp0_triple,
            __0,
            __1,
            __2,
            __3,
        )
        }
    __ret
}

internal fun __action408(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asExprSpannedTriple()
        val __1 = sym1.asTokenTriple()
        val __2 = sym2.asExprSpannedTriple()
        val __3 = sym3.asIntValueTriple()
        val __ret = run {
    val start0 = __0.first;
        val end0 = __0.first;
        val __temp0 = __action199(
            state,
            start0,
            end0,
        );
        val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
        __action142(
            state,
            __temp0_triple,
            __0,
            __1,
            __2,
            __3,
        )
        }
    __ret
}

internal fun __action409(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asExprSpannedTriple()
        val __1 = sym1.asTokenTriple()
        val __2 = sym2.asExprSpannedTriple()
        val __3 = sym3.asIntValueTriple()
        val __ret = run {
    val start0 = __0.first;
        val end0 = __0.first;
        val __temp0 = __action199(
            state,
            start0,
            end0,
        );
        val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
        __action143(
            state,
            __temp0_triple,
            __0,
            __1,
            __2,
            __3,
        )
        }
    __ret
}

internal fun __action410(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asExprSpannedTriple()
        val __1 = sym1.asTokenTriple()
        val __2 = sym2.asExprSpannedTriple()
        val __3 = sym3.asIntValueTriple()
        val __ret = run {
    val start0 = __0.first;
        val end0 = __0.first;
        val __temp0 = __action199(
            state,
            start0,
            end0,
        );
        val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
        __action134(
            state,
            __temp0_triple,
            __0,
            __1,
            __2,
            __3,
        )
        }
    __ret
}

internal fun __action411(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asExprSpannedTriple()
        val __1 = sym1.asTokenTriple()
        val __2 = sym2.asExprSpannedTriple()
        val __3 = sym3.asIntValueTriple()
        val __ret = run {
    val start0 = __0.first;
        val end0 = __0.first;
        val __temp0 = __action199(
            state,
            start0,
            end0,
        );
        val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
        __action135(
            state,
            __temp0_triple,
            __0,
            __1,
            __2,
            __3,
        )
        }
    __ret
}

internal fun __action412(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asStmtSpannedTriple()
        val __1 = sym1.asTokenTriple()
        val __2 = sym2.asIntValueTriple()
        val __3 = sym3.asTokenTriple()
        val __ret = run {
    val start0 = __0.first;
        val end0 = __0.first;
        val __temp0 = __action199(
            state,
            start0,
            end0,
        );
        val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
        __action287(
            state,
            __temp0_triple,
            __0,
            __1,
            __2,
            __3,
        )
        }
    __ret
}

internal fun __action413(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>, sym4: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asStmtSpannedTriple()
        val __1 = sym1.asStmtSpannedListTriple()
        val __2 = sym2.asTokenTriple()
        val __3 = sym3.asIntValueTriple()
        val __4 = sym4.asTokenTriple()
        val __ret = run {
    val start0 = __0.first;
        val end0 = __0.first;
        val __temp0 = __action199(
            state,
            start0,
            end0,
        );
        val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
        __action288(
            state,
            __temp0_triple,
            __0,
            __1,
            __2,
            __3,
            __4,
        )
        }
    __ret
}

internal fun __action414(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asStmtSpannedTriple()
        val __1 = sym1.asIntValueTriple()
        val __2 = sym2.asTokenTriple()
        val __ret = run {
    val start0 = __0.first;
        val end0 = __0.first;
        val __temp0 = __action199(
            state,
            start0,
            end0,
        );
        val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
        __action289(
            state,
            __temp0_triple,
            __0,
            __1,
            __2,
        )
        }
    __ret
}

internal fun __action415(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asStmtSpannedTriple()
        val __1 = sym1.asStmtSpannedListTriple()
        val __2 = sym2.asIntValueTriple()
        val __3 = sym3.asTokenTriple()
        val __ret = run {
    val start0 = __0.first;
        val end0 = __0.first;
        val __temp0 = __action199(
            state,
            start0,
            end0,
        );
        val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
        __action290(
            state,
            __temp0_triple,
            __0,
            __1,
            __2,
            __3,
        )
        }
    __ret
}

internal fun __action416(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asTokenTriple()
        val __1 = sym1.asNullableExprSpannedTriple()
        val __2 = sym2.asIntValueTriple()
        val __ret = run {
    val start0 = __0.first;
        val end0 = __0.first;
        val __temp0 = __action199(
            state,
            start0,
            end0,
        );
        val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
        __action44(
            state,
            __temp0_triple,
            __0,
            __1,
            __2,
        )
        }
    __ret
}

internal fun __action417(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asTokenTriple()
        val __1 = sym1.asIntValueTriple()
        val __ret = run {
    val start0 = __0.first;
        val end0 = __0.first;
        val __temp0 = __action199(
            state,
            start0,
            end0,
        );
        val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
        __action45(
            state,
            __temp0_triple,
            __0,
            __1,
        )
        }
    __ret
}

internal fun __action418(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asTokenTriple()
        val __1 = sym1.asIntValueTriple()
        val __ret = run {
    val start0 = __0.first;
        val end0 = __0.first;
        val __temp0 = __action199(
            state,
            start0,
            end0,
        );
        val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
        __action46(
            state,
            __temp0_triple,
            __0,
            __1,
        )
        }
    __ret
}

internal fun __action419(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asTokenTriple()
        val __1 = sym1.asIntValueTriple()
        val __ret = run {
    val start0 = __0.first;
        val end0 = __0.first;
        val __temp0 = __action199(
            state,
            start0,
            end0,
        );
        val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
        __action47(
            state,
            __temp0_triple,
            __0,
            __1,
        )
        }
    __ret
}

internal fun __action420(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asIntValueTriple()
        val __ret = run {
    val start0 = __0.first;
        val end0 = __0.first;
        val __temp0 = __action199(
            state,
            start0,
            end0,
        );
        val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
        __action326(
            state,
            __temp0_triple,
            __0,
        )
        }
    __ret
}

internal fun __action421(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asStmtSpannedListTriple()
        val __1 = sym1.asIntValueTriple()
        val __ret = run {
    val start0 = __0.first;
        val end0 = __0.first;
        val __temp0 = __action199(
            state,
            start0,
            end0,
        );
        val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
        __action327(
            state,
            __temp0_triple,
            __0,
            __1,
        )
        }
    __ret
}

internal fun __action422(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asTokenListTriple()
        val __1 = sym1.asIntValueTriple()
        val __ret = run {
    val start0 = __0.third;
        val end0 = __1.first;
        val __temp0 = __action199(
            state,
            start0,
            end0,
        );
        val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
        __action328(
            state,
            __0,
            __temp0_triple,
            __1,
        )
        }
    __ret
}

internal fun __action423(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asTokenListTriple()
        val __1 = sym1.asStmtSpannedListTriple()
        val __2 = sym2.asIntValueTriple()
        val __ret = run {
    val start0 = __0.third;
        val end0 = __1.first;
        val __temp0 = __action199(
            state,
            start0,
            end0,
        );
        val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
        __action329(
            state,
            __0,
            __temp0_triple,
            __1,
            __2,
        )
        }
    __ret
}

internal fun __action424(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>, sym4: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asTokenListTriple()
        val __1 = sym1.asTokenTriple()
        val __2 = sym2.asStmtSpannedListTriple()
        val __3 = sym3.asIntValueTriple()
        val __4 = sym4.asTokenTriple()
        val __ret = run {
    val start0 = __1.third;
        val end0 = __2.first;
        val __temp0 = __action199(
            state,
            start0,
            end0,
        );
        val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
        __action261(
            state,
            __0,
            __1,
            __temp0_triple,
            __2,
            __3,
            __4,
        )
        }
    __ret
}

internal fun __action425(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>, sym4: Triple<Int, GrammarSymbol, Int>, sym5: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asTokenListTriple()
        val __1 = sym1.asTokenTriple()
        val __2 = sym2.asTokenListTriple()
        val __3 = sym3.asStmtSpannedListTriple()
        val __4 = sym4.asIntValueTriple()
        val __5 = sym5.asTokenTriple()
        val __ret = run {
    val start0 = __1.third;
        val end0 = __2.first;
        val __temp0 = __action199(
            state,
            start0,
            end0,
        );
        val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
        __action262(
            state,
            __0,
            __1,
            __temp0_triple,
            __2,
            __3,
            __4,
            __5,
        )
        }
    __ret
}

internal fun __action426(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>, sym4: Triple<Int, GrammarSymbol, Int>, sym5: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asExprSpannedTriple()
        val __1 = sym1.asTokenTriple()
        val __2 = sym2.asExprSpannedTriple()
        val __3 = sym3.asTokenTriple()
        val __4 = sym4.asExprSpannedTriple()
        val __5 = sym5.asIntValueTriple()
        val __ret = run {
    val start0 = __0.first;
        val end0 = __0.first;
        val __temp0 = __action199(
            state,
            start0,
            end0,
        );
        val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
        __action107(
            state,
            __temp0_triple,
            __0,
            __1,
            __2,
            __3,
            __4,
            __5,
        )
        }
    __ret
}

internal fun __action427(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asDoubleValueTriple()
        val __1 = sym1.asIntValueTriple()
        val __ret = run {
    val start0 = __0.first;
        val end0 = __0.first;
        val __temp0 = __action199(
            state,
            start0,
            end0,
        );
        val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
        __action2(
            state,
            __temp0_triple,
            __0,
            __1,
        )
        }
    __ret
}

internal fun __action428(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asTokenFStringTriple()
        val __1 = sym1.asIntValueTriple()
        val __ret = run {
    val start0 = __0.first;
        val end0 = __0.first;
        val __temp0 = __action199(
            state,
            start0,
            end0,
        );
        val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
        __action4(
            state,
            __temp0_triple,
            __0,
            __1,
        )
        }
    __ret
}

internal fun __action429(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asStringValueTriple()
        val __1 = sym1.asIntValueTriple()
        val __ret = run {
    val start0 = __0.first;
        val end0 = __0.first;
        val __temp0 = __action199(
            state,
            start0,
            end0,
        );
        val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
        __action5(
            state,
            __temp0_triple,
            __0,
            __1,
        )
        }
    __ret
}

internal fun __action430(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asTokenIntTriple()
        val __1 = sym1.asIntValueTriple()
        val __ret = run {
    val start0 = __0.first;
        val end0 = __0.first;
        val __temp0 = __action199(
            state,
            start0,
            end0,
        );
        val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
        __action1(
            state,
            __temp0_triple,
            __0,
            __1,
        )
        }
    __ret
}

internal fun __action431(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asStringValueTriple()
        val __1 = sym1.asIntValueTriple()
        val __ret = run {
    val start0 = __0.first;
        val end0 = __0.first;
        val __temp0 = __action199(
            state,
            start0,
            end0,
        );
        val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
        __action3(
            state,
            __temp0_triple,
            __0,
            __1,
        )
        }
    __ret
}

internal fun __action432(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): Spanned<ArgumentP<AstNoPayload>> {
    val __0 = sym0.asArgumentValueTriple()
    val __ret = run {
val start0 = __0.third;
    val end0 = __0.third;
    val __temp0 = __action198(
        state,
        start0,
        end0,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
    __action340(
        state,
        __0,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action433(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): Spanned<ExprP<AstNoPayload>> {
    val __0 = sym0.asExprValueTriple()
    val __ret = run {
val start0 = __0.third;
    val end0 = __0.third;
    val __temp0 = __action198(
        state,
        start0,
        end0,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
    __action341(
        state,
        __0,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action434(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): Spanned<ExprP<AstNoPayload>> {
    val __0 = sym0.asExprValueTriple()
    val __ret = run {
val start0 = __0.third;
    val end0 = __0.third;
    val __temp0 = __action198(
        state,
        start0,
        end0,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
    __action342(
        state,
        __0,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action435(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): Spanned<ExprP<AstNoPayload>> {
    val __0 = sym0.asExprValueTriple()
    val __ret = run {
val start0 = __0.third;
    val end0 = __0.third;
    val __temp0 = __action198(
        state,
        start0,
        end0,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
    __action343(
        state,
        __0,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action436(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): Spanned<ParameterP<AstNoPayload>> {
    val __0 = sym0.asParameterValueTriple()
    val __ret = run {
val start0 = __0.third;
    val end0 = __0.third;
    val __temp0 = __action198(
        state,
        start0,
        end0,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
    __action344(
        state,
        __0,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action437(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): Spanned<ParameterP<AstNoPayload>> {
    val __0 = sym0.asParameterValueTriple()
    val __ret = run {
val start0 = __0.third;
    val end0 = __0.third;
    val __temp0 = __action198(
        state,
        start0,
        end0,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
    __action345(
        state,
        __0,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action438(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): Spanned<StmtP<AstNoPayload>> {
    val __0 = sym0.asStmtValueTriple()
    val __ret = run {
val start0 = __0.third;
    val end0 = __0.third;
    val __temp0 = __action198(
        state,
        start0,
        end0,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
    __action346(
        state,
        __0,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action439(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): Spanned<StmtP<AstNoPayload>> {
    val __0 = sym0.asStmtValueTriple()
    val __ret = run {
val start0 = __0.third;
    val end0 = __0.third;
    val __temp0 = __action198(
        state,
        start0,
        end0,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
    __action347(
        state,
        __0,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action440(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): Spanned<StmtP<AstNoPayload>> {
    val __0 = sym0.asStmtValueTriple()
    val __ret = run {
val start0 = __0.third;
    val end0 = __0.third;
    val __temp0 = __action198(
        state,
        start0,
        end0,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
    __action348(
        state,
        __0,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action441(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): Spanned<StmtP<AstNoPayload>> {
    val __0 = sym0.asStmtValueTriple()
    val __ret = run {
val start0 = __0.third;
    val end0 = __0.third;
    val __temp0 = __action198(
        state,
        start0,
        end0,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
    __action349(
        state,
        __0,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action442(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): Spanned<StmtP<AstNoPayload>> {
    val __0 = sym0.asStmtValueTriple()
    val __ret = run {
val start0 = __0.third;
    val end0 = __0.third;
    val __temp0 = __action198(
        state,
        start0,
        end0,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
    __action350(
        state,
        __0,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action443(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): Spanned<StmtP<AstNoPayload>> {
    val __0 = sym0.asStmtValueTriple()
    val __ret = run {
val start0 = __0.third;
    val end0 = __0.third;
    val __temp0 = __action198(
        state,
        start0,
        end0,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
    __action351(
        state,
        __0,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action444(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): Spanned<StmtP<AstNoPayload>> {
    val __0 = sym0.asStmtValueTriple()
    val __ret = run {
val start0 = __0.third;
    val end0 = __0.third;
    val __temp0 = __action198(
        state,
        start0,
        end0,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
    __action352(
        state,
        __0,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action445(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>): Spanned<ExprP<AstNoPayload>> {
    val __0 = sym0.asExprSpannedTriple()
    val __1 = sym1.asTokenTriple()
    val __2 = sym2.asExprSpannedTriple()
    val __ret = run {
val start0 = __2.third;
    val end0 = __2.third;
    val __temp0 = __action198(
        state,
        start0,
        end0,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
    __action353(
        state,
        __0,
        __1,
        __2,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action446(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>): Spanned<ExprP<AstNoPayload>> {
    val __0 = sym0.asExprSpannedTriple()
    val __1 = sym1.asTokenTriple()
    val __2 = sym2.asExprSpannedTriple()
    val __ret = run {
val start0 = __2.third;
    val end0 = __2.third;
    val __temp0 = __action198(
        state,
        start0,
        end0,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
    __action354(
        state,
        __0,
        __1,
        __2,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action447(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>): Spanned<ExprP<AstNoPayload>> {
    val __0 = sym0.asExprSpannedTriple()
    val __1 = sym1.asTokenTriple()
    val __2 = sym2.asExprSpannedTriple()
    val __ret = run {
val start0 = __2.third;
    val end0 = __2.third;
    val __temp0 = __action198(
        state,
        start0,
        end0,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
    __action355(
        state,
        __0,
        __1,
        __2,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action448(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>): Spanned<ExprP<AstNoPayload>> {
    val __0 = sym0.asExprSpannedTriple()
    val __1 = sym1.asTokenTriple()
    val __2 = sym2.asExprSpannedTriple()
    val __ret = run {
val start0 = __2.third;
    val end0 = __2.third;
    val __temp0 = __action198(
        state,
        start0,
        end0,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
    __action356(
        state,
        __0,
        __1,
        __2,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action449(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>): Spanned<ExprP<AstNoPayload>> {
    val __0 = sym0.asExprSpannedTriple()
    val __1 = sym1.asTokenTriple()
    val __2 = sym2.asExprSpannedTriple()
    val __ret = run {
val start0 = __2.third;
    val end0 = __2.third;
    val __temp0 = __action198(
        state,
        start0,
        end0,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
    __action357(
        state,
        __0,
        __1,
        __2,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action450(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>): Spanned<ExprP<AstNoPayload>> {
    val __0 = sym0.asExprSpannedTriple()
    val __1 = sym1.asTokenTriple()
    val __2 = sym2.asExprSpannedTriple()
    val __ret = run {
val start0 = __2.third;
    val end0 = __2.third;
    val __temp0 = __action198(
        state,
        start0,
        end0,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
    __action358(
        state,
        __0,
        __1,
        __2,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action451(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): Spanned<Comma> {
    val __0 = sym0.asTokenTriple()
    val __ret = run {
val start0 = __0.third;
    val end0 = __0.third;
    val __temp0 = __action198(
        state,
        start0,
        end0,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
    __action359(
        state,
        __0,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action452(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>): Spanned<ExprP<AstNoPayload>> {
    val __0 = sym0.asExprSpannedTriple()
    val __1 = sym1.asTokenTriple()
    val __2 = sym2.asExprSpannedTriple()
    val __ret = run {
val start0 = __2.third;
    val end0 = __2.third;
    val __temp0 = __action198(
        state,
        start0,
        end0,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
    __action360(
        state,
        __0,
        __1,
        __2,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action453(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>): Spanned<ExprP<AstNoPayload>> {
    val __0 = sym0.asExprSpannedTriple()
    val __1 = sym1.asTokenTriple()
    val __2 = sym2.asExprSpannedTriple()
    val __ret = run {
val start0 = __2.third;
    val end0 = __2.third;
    val __temp0 = __action198(
        state,
        start0,
        end0,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
    __action361(
        state,
        __0,
        __1,
        __2,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action454(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>): Spanned<ExprP<AstNoPayload>> {
    val __0 = sym0.asExprSpannedTriple()
    val __1 = sym1.asTokenTriple()
    val __2 = sym2.asExprSpannedTriple()
    val __ret = run {
val start0 = __2.third;
    val end0 = __2.third;
    val __temp0 = __action198(
        state,
        start0,
        end0,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
    __action362(
        state,
        __0,
        __1,
        __2,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action455(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>): Spanned<ExprP<AstNoPayload>> {
    val __0 = sym0.asExprSpannedTriple()
    val __1 = sym1.asTokenTriple()
    val __2 = sym2.asExprSpannedTriple()
    val __ret = run {
val start0 = __2.third;
    val end0 = __2.third;
    val __temp0 = __action198(
        state,
        start0,
        end0,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
    __action363(
        state,
        __0,
        __1,
        __2,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action456(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>): Spanned<ExprP<AstNoPayload>> {
    val __0 = sym0.asExprSpannedTriple()
    val __1 = sym1.asTokenTriple()
    val __2 = sym2.asExprSpannedTriple()
    val __ret = run {
val start0 = __2.third;
    val end0 = __2.third;
    val __temp0 = __action198(
        state,
        start0,
        end0,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
    __action364(
        state,
        __0,
        __1,
        __2,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action457(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>): Spanned<ExprP<AstNoPayload>> {
    val __0 = sym0.asExprSpannedTriple()
    val __1 = sym1.asTokenTriple()
    val __2 = sym2.asExprSpannedTriple()
    val __ret = run {
val start0 = __2.third;
    val end0 = __2.third;
    val __temp0 = __action198(
        state,
        start0,
        end0,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
    __action365(
        state,
        __0,
        __1,
        __2,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action458(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>): Spanned<ExprP<AstNoPayload>> {
    val __0 = sym0.asExprSpannedTriple()
    val __1 = sym1.asTokenTriple()
    val __2 = sym2.asExprSpannedTriple()
    val __ret = run {
val start0 = __2.third;
    val end0 = __2.third;
    val __temp0 = __action198(
        state,
        start0,
        end0,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
    __action366(
        state,
        __0,
        __1,
        __2,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action459(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>): Spanned<ExprP<AstNoPayload>> {
    val __0 = sym0.asExprSpannedTriple()
    val __1 = sym1.asTokenTriple()
    val __2 = sym2.asTokenTriple()
    val __3 = sym3.asExprSpannedTriple()
    val __ret = run {
val start0 = __3.third;
    val end0 = __3.third;
    val __temp0 = __action198(
        state,
        start0,
        end0,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
    __action367(
        state,
        __0,
        __1,
        __2,
        __3,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action460(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>): Spanned<ExprP<AstNoPayload>> {
    val __0 = sym0.asTokenTriple()
    val __1 = sym1.asExprSpannedTriple()
    val __ret = run {
val start0 = __1.third;
    val end0 = __1.third;
    val __temp0 = __action198(
        state,
        start0,
        end0,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
    __action368(
        state,
        __0,
        __1,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action461(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>): Spanned<ExprP<AstNoPayload>> {
    val __0 = sym0.asTokenTriple()
    val __1 = sym1.asExprSpannedTriple()
    val __ret = run {
val start0 = __1.third;
    val end0 = __1.third;
    val __temp0 = __action198(
        state,
        start0,
        end0,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
    __action369(
        state,
        __0,
        __1,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action462(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>): Spanned<ExprP<AstNoPayload>> {
    val __0 = sym0.asTokenTriple()
    val __1 = sym1.asExprSpannedTriple()
    val __ret = run {
val start0 = __1.third;
    val end0 = __1.third;
    val __temp0 = __action198(
        state,
        start0,
        end0,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
    __action370(
        state,
        __0,
        __1,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action463(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>): Spanned<ExprP<AstNoPayload>> {
    val __0 = sym0.asExprSpannedTriple()
    val __1 = sym1.asTokenTriple()
    val __ret = run {
val start0 = __1.third;
    val end0 = __1.third;
    val __temp0 = __action198(
        state,
        start0,
        end0,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
    __action371(
        state,
        __0,
        __1,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action464(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>): Spanned<ExprP<AstNoPayload>> {
    val __0 = sym0.asExprSpannedListTriple()
    val __1 = sym1.asExprSpannedTriple()
    val __2 = sym2.asTokenTriple()
    val __ret = run {
val start0 = __2.third;
    val end0 = __2.third;
    val __temp0 = __action198(
        state,
        start0,
        end0,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
    __action372(
        state,
        __0,
        __1,
        __2,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action465(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): Spanned<ExprP<AstNoPayload>> {
    val __0 = sym0.asExprSpannedTriple()
    val __ret = run {
val start0 = __0.third;
    val end0 = __0.third;
    val __temp0 = __action198(
        state,
        start0,
        end0,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
    __action373(
        state,
        __0,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action466(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>): Spanned<ExprP<AstNoPayload>> {
    val __0 = sym0.asExprSpannedListTriple()
    val __1 = sym1.asExprSpannedTriple()
    val __ret = run {
val start0 = __1.third;
    val end0 = __1.third;
    val __temp0 = __action198(
        state,
        start0,
        end0,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
    __action374(
        state,
        __0,
        __1,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action467(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>): Spanned<ExprP<AstNoPayload>> {
    val __0 = sym0.asExprSpannedTriple()
    val __1 = sym1.asTokenTriple()
    val __ret = run {
val start0 = __1.third;
    val end0 = __1.third;
    val __temp0 = __action198(
        state,
        start0,
        end0,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
    __action375(
        state,
        __0,
        __1,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action468(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>): Spanned<ExprP<AstNoPayload>> {
    val __0 = sym0.asExprSpannedListTriple()
    val __1 = sym1.asExprSpannedTriple()
    val __2 = sym2.asTokenTriple()
    val __ret = run {
val start0 = __2.third;
    val end0 = __2.third;
    val __temp0 = __action198(
        state,
        start0,
        end0,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
    __action376(
        state,
        __0,
        __1,
        __2,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action469(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): Spanned<ExprP<AstNoPayload>> {
    val __0 = sym0.asExprSpannedTriple()
    val __ret = run {
val start0 = __0.third;
    val end0 = __0.third;
    val __temp0 = __action198(
        state,
        start0,
        end0,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
    __action377(
        state,
        __0,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action470(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>): Spanned<ExprP<AstNoPayload>> {
    val __0 = sym0.asExprSpannedListTriple()
    val __1 = sym1.asExprSpannedTriple()
    val __ret = run {
val start0 = __1.third;
    val end0 = __1.third;
    val __temp0 = __action198(
        state,
        start0,
        end0,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
    __action378(
        state,
        __0,
        __1,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action471(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>): Spanned<ExprP<AstNoPayload>> {
    val __0 = sym0.asTokenTriple()
    val __1 = sym1.asExprSpannedTriple()
    val __ret = run {
val start0 = __1.third;
    val end0 = __1.third;
    val __temp0 = __action198(
        state,
        start0,
        end0,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
    __action379(
        state,
        __0,
        __1,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action472(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): Spanned<ExprP<AstNoPayload>> {
    val __0 = sym0.asIdentSpannedTriple()
    val __ret = run {
val start0 = __0.third;
    val end0 = __0.third;
    val __temp0 = __action198(
        state,
        start0,
        end0,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
    __action380(
        state,
        __0,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action473(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asTokenIntSpannedTriple()
        val __ret = run {
    val start0 = __0.third;
        val end0 = __0.third;
        val __temp0 = __action198(
            state,
            start0,
            end0,
        );
        val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
        __action381(
            state,
            __0,
            __temp0_triple,
        )
        }
    __ret
}

internal fun __action474(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asDoubleSpannedTriple()
        val __ret = run {
    val start0 = __0.third;
        val end0 = __0.third;
        val __temp0 = __action198(
            state,
            start0,
            end0,
        );
        val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
        __action382(
            state,
            __0,
            __temp0_triple,
        )
        }
    __ret
}

internal fun __action475(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asStringSpannedTriple()
        val __ret = run {
    val start0 = __0.third;
        val end0 = __0.third;
        val __temp0 = __action198(
            state,
            start0,
            end0,
        );
        val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
        __action383(
            state,
            __0,
            __temp0_triple,
        )
        }
    __ret
}

internal fun __action476(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): Spanned<ExprP<AstNoPayload>> {
    val __0 = sym0.asTokenTriple()
    val __ret = run {
val start0 = __0.third;
    val end0 = __0.third;
    val __temp0 = __action198(
        state,
        start0,
        end0,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
    __action384(
        state,
        __0,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action477(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>): Spanned<ExprP<AstNoPayload>> {
    val __0 = sym0.asTokenTriple()
    val __1 = sym1.asExprSpannedListTriple()
    val __2 = sym2.asTokenTriple()
    val __ret = run {
val start0 = __2.third;
    val end0 = __2.third;
    val __temp0 = __action198(
        state,
        start0,
        end0,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
    __action385(
        state,
        __0,
        __1,
        __2,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action478(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>): Spanned<ExprP<AstNoPayload>> {
    val __0 = sym0.asTokenTriple()
    val __1 = sym1.asExprPairListTriple()
    val __2 = sym2.asTokenTriple()
    val __ret = run {
val start0 = __2.third;
    val end0 = __2.third;
    val __temp0 = __action198(
        state,
        start0,
        end0,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
    __action386(
        state,
        __0,
        __1,
        __2,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action479(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asTokenTriple()
        val __1 = sym1.asNullableExprSpannedTriple()
        val __2 = sym2.asTokenTriple()
        val __ret = run {
    val start0 = __2.third;
        val end0 = __2.third;
        val __temp0 = __action198(
            state,
            start0,
            end0,
        );
        val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
        __action387(
            state,
            __0,
            __1,
            __2,
            __temp0_triple,
        )
        }
    __ret
}

internal fun __action480(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asFStringSpannedTriple()
        val __ret = run {
    val start0 = __0.third;
        val end0 = __0.third;
        val __temp0 = __action198(
            state,
            start0,
            end0,
        );
        val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
        __action388(
            state,
            __0,
            __temp0_triple,
        )
        }
    __ret
}

internal fun __action481(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>): Spanned<ExprP<AstNoPayload>> {
    val __0 = sym0.asExprSpannedTriple()
    val __1 = sym1.asTokenTriple()
    val __2 = sym2.asExprSpannedTriple()
    val __ret = run {
val start0 = __2.third;
    val end0 = __2.third;
    val __temp0 = __action198(
        state,
        start0,
        end0,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
    __action389(
        state,
        __0,
        __1,
        __2,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action482(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asExprSpannedTriple()
        val __1 = sym1.asTokenTriple()
        val __2 = sym2.asStringSpannedTriple()
        val __ret = run {
    val start0 = __2.third;
        val end0 = __2.third;
        val __temp0 = __action198(
            state,
            start0,
            end0,
        );
        val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
        __action390(
            state,
            __0,
            __1,
            __2,
            __temp0_triple,
        )
        }
    __ret
}

internal fun __action483(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>): Spanned<ExprP<AstNoPayload>> {
    val __0 = sym0.asExprSpannedTriple()
    val __1 = sym1.asTokenTriple()
    val __2 = sym2.asArgumentSpannedListTriple()
    val __3 = sym3.asTokenTriple()
    val __ret = run {
val start0 = __3.third;
    val end0 = __3.third;
    val __temp0 = __action198(
        state,
        start0,
        end0,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
    __action391(
        state,
        __0,
        __1,
        __2,
        __3,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action484(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>, sym4: Triple<Int, GrammarSymbol, Int>, sym5: Triple<Int, GrammarSymbol, Int>, sym6: Triple<Int, GrammarSymbol, Int>, sym7: Triple<Int, GrammarSymbol, Int>): Spanned<ExprP<AstNoPayload>> {
    val __0 = sym0.asExprSpannedTriple()
    val __1 = sym1.asTokenTriple()
    val __2 = sym2.asExprSpannedTriple()
    val __3 = sym3.asTokenTriple()
    val __4 = sym4.asExprSpannedTriple()
    val __5 = sym5.asTokenTriple()
    val __6 = sym6.asExprSpannedTriple()
    val __7 = sym7.asTokenTriple()
    val __ret = run {
val start0 = __7.third;
    val end0 = __7.third;
    val __temp0 = __action198(
        state,
        start0,
        end0,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
    __action392(
        state,
        __0,
        __1,
        __2,
        __3,
        __4,
        __5,
        __6,
        __7,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action485(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>, sym4: Triple<Int, GrammarSymbol, Int>, sym5: Triple<Int, GrammarSymbol, Int>, sym6: Triple<Int, GrammarSymbol, Int>): Spanned<ExprP<AstNoPayload>> {
    val __0 = sym0.asExprSpannedTriple()
    val __1 = sym1.asTokenTriple()
    val __2 = sym2.asExprSpannedTriple()
    val __3 = sym3.asTokenTriple()
    val __4 = sym4.asExprSpannedTriple()
    val __5 = sym5.asTokenTriple()
    val __6 = sym6.asTokenTriple()
    val __ret = run {
val start0 = __6.third;
    val end0 = __6.third;
    val __temp0 = __action198(
        state,
        start0,
        end0,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
    __action393(
        state,
        __0,
        __1,
        __2,
        __3,
        __4,
        __5,
        __6,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action486(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>, sym4: Triple<Int, GrammarSymbol, Int>, sym5: Triple<Int, GrammarSymbol, Int>): Spanned<ExprP<AstNoPayload>> {
    val __0 = sym0.asExprSpannedTriple()
    val __1 = sym1.asTokenTriple()
    val __2 = sym2.asExprSpannedTriple()
    val __3 = sym3.asTokenTriple()
    val __4 = sym4.asExprSpannedTriple()
    val __5 = sym5.asTokenTriple()
    val __ret = run {
val start0 = __5.third;
    val end0 = __5.third;
    val __temp0 = __action198(
        state,
        start0,
        end0,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
    __action394(
        state,
        __0,
        __1,
        __2,
        __3,
        __4,
        __5,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action487(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>, sym4: Triple<Int, GrammarSymbol, Int>, sym5: Triple<Int, GrammarSymbol, Int>, sym6: Triple<Int, GrammarSymbol, Int>): Spanned<ExprP<AstNoPayload>> {
    val __0 = sym0.asExprSpannedTriple()
    val __1 = sym1.asTokenTriple()
    val __2 = sym2.asExprSpannedTriple()
    val __3 = sym3.asTokenTriple()
    val __4 = sym4.asTokenTriple()
    val __5 = sym5.asExprSpannedTriple()
    val __6 = sym6.asTokenTriple()
    val __ret = run {
val start0 = __6.third;
    val end0 = __6.third;
    val __temp0 = __action198(
        state,
        start0,
        end0,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
    __action395(
        state,
        __0,
        __1,
        __2,
        __3,
        __4,
        __5,
        __6,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action488(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>, sym4: Triple<Int, GrammarSymbol, Int>, sym5: Triple<Int, GrammarSymbol, Int>): Spanned<ExprP<AstNoPayload>> {
    val __0 = sym0.asExprSpannedTriple()
    val __1 = sym1.asTokenTriple()
    val __2 = sym2.asExprSpannedTriple()
    val __3 = sym3.asTokenTriple()
    val __4 = sym4.asTokenTriple()
    val __5 = sym5.asTokenTriple()
    val __ret = run {
val start0 = __5.third;
    val end0 = __5.third;
    val __temp0 = __action198(
        state,
        start0,
        end0,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
    __action396(
        state,
        __0,
        __1,
        __2,
        __3,
        __4,
        __5,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action489(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>, sym4: Triple<Int, GrammarSymbol, Int>): Spanned<ExprP<AstNoPayload>> {
    val __0 = sym0.asExprSpannedTriple()
    val __1 = sym1.asTokenTriple()
    val __2 = sym2.asExprSpannedTriple()
    val __3 = sym3.asTokenTriple()
    val __4 = sym4.asTokenTriple()
    val __ret = run {
val start0 = __4.third;
    val end0 = __4.third;
    val __temp0 = __action198(
        state,
        start0,
        end0,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
    __action397(
        state,
        __0,
        __1,
        __2,
        __3,
        __4,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action490(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>, sym4: Triple<Int, GrammarSymbol, Int>, sym5: Triple<Int, GrammarSymbol, Int>, sym6: Triple<Int, GrammarSymbol, Int>): Spanned<ExprP<AstNoPayload>> {
    val __0 = sym0.asExprSpannedTriple()
    val __1 = sym1.asTokenTriple()
    val __2 = sym2.asTokenTriple()
    val __3 = sym3.asExprSpannedTriple()
    val __4 = sym4.asTokenTriple()
    val __5 = sym5.asExprSpannedTriple()
    val __6 = sym6.asTokenTriple()
    val __ret = run {
val start0 = __6.third;
    val end0 = __6.third;
    val __temp0 = __action198(
        state,
        start0,
        end0,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
    __action398(
        state,
        __0,
        __1,
        __2,
        __3,
        __4,
        __5,
        __6,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action491(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>, sym4: Triple<Int, GrammarSymbol, Int>, sym5: Triple<Int, GrammarSymbol, Int>): Spanned<ExprP<AstNoPayload>> {
    val __0 = sym0.asExprSpannedTriple()
    val __1 = sym1.asTokenTriple()
    val __2 = sym2.asTokenTriple()
    val __3 = sym3.asExprSpannedTriple()
    val __4 = sym4.asTokenTriple()
    val __5 = sym5.asTokenTriple()
    val __ret = run {
val start0 = __5.third;
    val end0 = __5.third;
    val __temp0 = __action198(
        state,
        start0,
        end0,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
    __action399(
        state,
        __0,
        __1,
        __2,
        __3,
        __4,
        __5,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action492(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>, sym4: Triple<Int, GrammarSymbol, Int>): Spanned<ExprP<AstNoPayload>> {
    val __0 = sym0.asExprSpannedTriple()
    val __1 = sym1.asTokenTriple()
    val __2 = sym2.asTokenTriple()
    val __3 = sym3.asExprSpannedTriple()
    val __4 = sym4.asTokenTriple()
    val __ret = run {
val start0 = __4.third;
    val end0 = __4.third;
    val __temp0 = __action198(
        state,
        start0,
        end0,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
    __action400(
        state,
        __0,
        __1,
        __2,
        __3,
        __4,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action493(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>, sym4: Triple<Int, GrammarSymbol, Int>, sym5: Triple<Int, GrammarSymbol, Int>): Spanned<ExprP<AstNoPayload>> {
    val __0 = sym0.asExprSpannedTriple()
    val __1 = sym1.asTokenTriple()
    val __2 = sym2.asTokenTriple()
    val __3 = sym3.asTokenTriple()
    val __4 = sym4.asExprSpannedTriple()
    val __5 = sym5.asTokenTriple()
    val __ret = run {
val start0 = __5.third;
    val end0 = __5.third;
    val __temp0 = __action198(
        state,
        start0,
        end0,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
    __action401(
        state,
        __0,
        __1,
        __2,
        __3,
        __4,
        __5,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action494(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>, sym4: Triple<Int, GrammarSymbol, Int>): Spanned<ExprP<AstNoPayload>> {
    val __0 = sym0.asExprSpannedTriple()
    val __1 = sym1.asTokenTriple()
    val __2 = sym2.asTokenTriple()
    val __3 = sym3.asTokenTriple()
    val __4 = sym4.asTokenTriple()
    val __ret = run {
val start0 = __4.third;
    val end0 = __4.third;
    val __temp0 = __action198(
        state,
        start0,
        end0,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
    __action402(
        state,
        __0,
        __1,
        __2,
        __3,
        __4,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action495(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>): Spanned<ExprP<AstNoPayload>> {
    val __0 = sym0.asExprSpannedTriple()
    val __1 = sym1.asTokenTriple()
    val __2 = sym2.asTokenTriple()
    val __3 = sym3.asTokenTriple()
    val __ret = run {
val start0 = __3.third;
    val end0 = __3.third;
    val __temp0 = __action198(
        state,
        start0,
        end0,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
    __action403(
        state,
        __0,
        __1,
        __2,
        __3,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action496(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>): Spanned<ExprP<AstNoPayload>> {
    val __0 = sym0.asExprSpannedTriple()
    val __1 = sym1.asTokenTriple()
    val __2 = sym2.asExprSpannedTriple()
    val __3 = sym3.asTokenTriple()
    val __ret = run {
val start0 = __3.third;
    val end0 = __3.third;
    val __temp0 = __action198(
        state,
        start0,
        end0,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
    __action404(
        state,
        __0,
        __1,
        __2,
        __3,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action497(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>, sym4: Triple<Int, GrammarSymbol, Int>, sym5: Triple<Int, GrammarSymbol, Int>): Spanned<ExprP<AstNoPayload>> {
    val __0 = sym0.asExprSpannedTriple()
    val __1 = sym1.asTokenTriple()
    val __2 = sym2.asExprSpannedTriple()
    val __3 = sym3.asTokenTriple()
    val __4 = sym4.asExprSpannedTriple()
    val __5 = sym5.asTokenTriple()
    val __ret = run {
val start0 = __5.third;
    val end0 = __5.third;
    val __temp0 = __action198(
        state,
        start0,
        end0,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
    __action405(
        state,
        __0,
        __1,
        __2,
        __3,
        __4,
        __5,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action498(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>): Spanned<ExprP<AstNoPayload>> {
    val __0 = sym0.asExprSpannedTriple()
    val __1 = sym1.asTokenTriple()
    val __2 = sym2.asExprSpannedTriple()
    val __ret = run {
val start0 = __2.third;
    val end0 = __2.third;
    val __temp0 = __action198(
        state,
        start0,
        end0,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
    __action406(
        state,
        __0,
        __1,
        __2,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action499(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>): Spanned<ExprP<AstNoPayload>> {
    val __0 = sym0.asExprSpannedTriple()
    val __1 = sym1.asTokenTriple()
    val __2 = sym2.asExprSpannedTriple()
    val __ret = run {
val start0 = __2.third;
    val end0 = __2.third;
    val __temp0 = __action198(
        state,
        start0,
        end0,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
    __action407(
        state,
        __0,
        __1,
        __2,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action500(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>): Spanned<ExprP<AstNoPayload>> {
    val __0 = sym0.asExprSpannedTriple()
    val __1 = sym1.asTokenTriple()
    val __2 = sym2.asExprSpannedTriple()
    val __ret = run {
val start0 = __2.third;
    val end0 = __2.third;
    val __temp0 = __action198(
        state,
        start0,
        end0,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
    __action408(
        state,
        __0,
        __1,
        __2,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action501(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>): Spanned<ExprP<AstNoPayload>> {
    val __0 = sym0.asExprSpannedTriple()
    val __1 = sym1.asTokenTriple()
    val __2 = sym2.asExprSpannedTriple()
    val __ret = run {
val start0 = __2.third;
    val end0 = __2.third;
    val __temp0 = __action198(
        state,
        start0,
        end0,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
    __action409(
        state,
        __0,
        __1,
        __2,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action502(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>): Spanned<ExprP<AstNoPayload>> {
    val __0 = sym0.asExprSpannedTriple()
    val __1 = sym1.asTokenTriple()
    val __2 = sym2.asExprSpannedTriple()
    val __ret = run {
val start0 = __2.third;
    val end0 = __2.third;
    val __temp0 = __action198(
        state,
        start0,
        end0,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
    __action410(
        state,
        __0,
        __1,
        __2,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action503(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>): Spanned<ExprP<AstNoPayload>> {
    val __0 = sym0.asExprSpannedTriple()
    val __1 = sym1.asTokenTriple()
    val __2 = sym2.asExprSpannedTriple()
    val __ret = run {
val start0 = __2.third;
    val end0 = __2.third;
    val __temp0 = __action198(
        state,
        start0,
        end0,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
    __action411(
        state,
        __0,
        __1,
        __2,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action504(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>): Spanned<StmtP<AstNoPayload>> {
    val __0 = sym0.asStmtSpannedTriple()
    val __1 = sym1.asTokenTriple()
    val __2 = sym2.asTokenTriple()
    val __ret = run {
val start0 = __1.third;
    val end0 = __2.first;
    val __temp0 = __action198(
        state,
        start0,
        end0,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
    __action412(
        state,
        __0,
        __1,
        __temp0_triple,
        __2,
    )
    }
    return __ret
}

internal fun __action505(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>): Spanned<StmtP<AstNoPayload>> {
    val __0 = sym0.asStmtSpannedTriple()
    val __1 = sym1.asStmtSpannedListTriple()
    val __2 = sym2.asTokenTriple()
    val __3 = sym3.asTokenTriple()
    val __ret = run {
val start0 = __2.third;
    val end0 = __3.first;
    val __temp0 = __action198(
        state,
        start0,
        end0,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
    __action413(
        state,
        __0,
        __1,
        __2,
        __temp0_triple,
        __3,
    )
    }
    return __ret
}

internal fun __action506(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>): Spanned<StmtP<AstNoPayload>> {
    val __0 = sym0.asStmtSpannedTriple()
    val __1 = sym1.asTokenTriple()
    val __ret = run {
val start0 = __0.third;
    val end0 = __1.first;
    val __temp0 = __action198(
        state,
        start0,
        end0,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
    __action414(
        state,
        __0,
        __temp0_triple,
        __1,
    )
    }
    return __ret
}

internal fun __action507(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>): Spanned<StmtP<AstNoPayload>> {
    val __0 = sym0.asStmtSpannedTriple()
    val __1 = sym1.asStmtSpannedListTriple()
    val __2 = sym2.asTokenTriple()
    val __ret = run {
val start0 = __1.third;
    val end0 = __2.first;
    val __temp0 = __action198(
        state,
        start0,
        end0,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
    __action415(
        state,
        __0,
        __1,
        __temp0_triple,
        __2,
    )
    }
    return __ret
}

internal fun __action508(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asTokenTriple()
        val __1 = sym1.asNullableExprSpannedTriple()
        val __ret = run {
    val start0 = __1.third;
        val end0 = __1.third;
        val __temp0 = __action198(
            state,
            start0,
            end0,
        );
        val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
        __action416(
            state,
            __0,
            __1,
            __temp0_triple,
        )
        }
    __ret
}

internal fun __action509(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): Spanned<StmtP<AstNoPayload>> {
    val __0 = sym0.asTokenTriple()
    val __ret = run {
val start0 = __0.third;
    val end0 = __0.third;
    val __temp0 = __action198(
        state,
        start0,
        end0,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
    __action417(
        state,
        __0,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action510(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): Spanned<StmtP<AstNoPayload>> {
    val __0 = sym0.asTokenTriple()
    val __ret = run {
val start0 = __0.third;
    val end0 = __0.third;
    val __temp0 = __action198(
        state,
        start0,
        end0,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
    __action418(
        state,
        __0,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action511(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): Spanned<StmtP<AstNoPayload>> {
    val __0 = sym0.asTokenTriple()
    val __ret = run {
val start0 = __0.third;
    val end0 = __0.third;
    val __temp0 = __action198(
        state,
        start0,
        end0,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
    __action419(
        state,
        __0,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action512(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, __lookbehind: Int, __lookahead: Int): Spanned<StmtP<AstNoPayload>> {
    val __ret = run {
val start0 = __lookbehind;
    val end0 = __lookahead;
    val __temp0 = __action198(
        state,
        start0,
        end0,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
    __action420(
        state,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action513(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): Spanned<StmtP<AstNoPayload>> {
    val __0 = sym0.asStmtSpannedListTriple()
    val __ret = run {
val start0 = __0.third;
    val end0 = __0.third;
    val __temp0 = __action198(
        state,
        start0,
        end0,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
    __action421(
        state,
        __0,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action514(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): Spanned<StmtP<AstNoPayload>> {
    val __0 = sym0.asTokenListTriple()
    val __ret = run {
val start0 = __0.third;
    val end0 = __0.third;
    val __temp0 = __action198(
        state,
        start0,
        end0,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
    __action422(
        state,
        __0,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action515(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>): Spanned<StmtP<AstNoPayload>> {
    val __0 = sym0.asTokenListTriple()
    val __1 = sym1.asStmtSpannedListTriple()
    val __ret = run {
val start0 = __1.third;
    val end0 = __1.third;
    val __temp0 = __action198(
        state,
        start0,
        end0,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
    __action423(
        state,
        __0,
        __1,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action516(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>): Spanned<StmtP<AstNoPayload>> {
    val __0 = sym0.asTokenListTriple()
    val __1 = sym1.asTokenTriple()
    val __2 = sym2.asStmtSpannedListTriple()
    val __3 = sym3.asTokenTriple()
    val __ret = run {
val start0 = __2.third;
    val end0 = __3.first;
    val __temp0 = __action198(
        state,
        start0,
        end0,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
    __action424(
        state,
        __0,
        __1,
        __2,
        __temp0_triple,
        __3,
    )
    }
    return __ret
}

internal fun __action517(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>, sym4: Triple<Int, GrammarSymbol, Int>): Spanned<StmtP<AstNoPayload>> {
    val __0 = sym0.asTokenListTriple()
    val __1 = sym1.asTokenTriple()
    val __2 = sym2.asTokenListTriple()
    val __3 = sym3.asStmtSpannedListTriple()
    val __4 = sym4.asTokenTriple()
    val __ret = run {
val start0 = __3.third;
    val end0 = __4.first;
    val __temp0 = __action198(
        state,
        start0,
        end0,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
    __action425(
        state,
        __0,
        __1,
        __2,
        __3,
        __temp0_triple,
        __4,
    )
    }
    return __ret
}

internal fun __action518(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>, sym4: Triple<Int, GrammarSymbol, Int>): Spanned<ExprP<AstNoPayload>> {
    val __0 = sym0.asExprSpannedTriple()
    val __1 = sym1.asTokenTriple()
    val __2 = sym2.asExprSpannedTriple()
    val __3 = sym3.asTokenTriple()
    val __4 = sym4.asExprSpannedTriple()
    val __ret = run {
val start0 = __4.third;
    val end0 = __4.third;
    val __temp0 = __action198(
        state,
        start0,
        end0,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
    __action426(
        state,
        __0,
        __1,
        __2,
        __3,
        __4,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action519(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): Spanned<Double> {
    val __0 = sym0.asDoubleValueTriple()
    val __ret = run {
val start0 = __0.third;
    val end0 = __0.third;
    val __temp0 = __action198(
        state,
        start0,
        end0,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
    __action427(
        state,
        __0,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action520(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): Spanned<FStringP<AstNoPayload>> {
    val __0 = sym0.asTokenFStringTriple()
    val __ret = run {
val start0 = __0.third;
    val end0 = __0.third;
    val __temp0 = __action198(
        state,
        start0,
        end0,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
    __action428(
        state,
        __0,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action521(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): Spanned<String> {
    val __0 = sym0.asStringValueTriple()
    val __ret = run {
val start0 = __0.third;
    val end0 = __0.third;
    val __temp0 = __action198(
        state,
        start0,
        end0,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
    __action429(
        state,
        __0,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action522(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): Spanned<TokenInt> {
    val __0 = sym0.asTokenIntTriple()
    val __ret = run {
val start0 = __0.third;
    val end0 = __0.third;
    val __temp0 = __action198(
        state,
        start0,
        end0,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
    __action430(
        state,
        __0,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action523(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): Spanned<String> {
    val __0 = sym0.asStringValueTriple()
    val __ret = run {
val start0 = __0.third;
    val end0 = __0.third;
    val __temp0 = __action198(
        state,
        start0,
        end0,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant23(__temp0), end0)
    __action431(
        state,
        __0,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action524(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): Spanned<ArgumentP<AstNoPayload>> {
    val __0 = sym0.asArgumentValueTriple()
    val __ret = run {
val start0 = __0.first;
    val end0 = __0.third;
    val __temp0 = __action432(
        state,
        __0,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant11(__temp0), end0)
    __action82(
        state,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action525(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): Spanned<ExprP<AstNoPayload>> {
    val __0 = sym0.asExprValueTriple()
    val __ret = run {
val start0 = __0.first;
    val end0 = __0.third;
    val __temp0 = __action433(
        state,
        __0,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant17(__temp0), end0)
    __action101(
        state,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action526(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): Spanned<ExprP<AstNoPayload>> {
    val __0 = sym0.asExprValueTriple()
    val __ret = run {
val start0 = __0.first;
    val end0 = __0.third;
    val __temp0 = __action434(
        state,
        __0,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant17(__temp0), end0)
    __action110(
        state,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action527(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): Spanned<ExprP<AstNoPayload>> {
    val __0 = sym0.asExprValueTriple()
    val __ret = run {
val start0 = __0.first;
    val end0 = __0.third;
    val __temp0 = __action435(
        state,
        __0,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant17(__temp0), end0)
    __action99(
        state,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action528(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): Spanned<ParameterP<AstNoPayload>> {
    val __0 = sym0.asParameterValueTriple()
    val __ret = run {
val start0 = __0.first;
    val end0 = __0.third;
    val __temp0 = __action436(
        state,
        __0,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant13(__temp0), end0)
    __action20(
        state,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action529(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): Spanned<ParameterP<AstNoPayload>> {
    val __0 = sym0.asParameterValueTriple()
    val __ret = run {
val start0 = __0.first;
    val end0 = __0.third;
    val __temp0 = __action437(
        state,
        __0,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant13(__temp0), end0)
    __action13(
        state,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action530(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): Spanned<StmtP<AstNoPayload>> {
    val __0 = sym0.asStmtValueTriple()
    val __ret = run {
val start0 = __0.first;
    val end0 = __0.third;
    val __temp0 = __action438(
        state,
        __0,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant9(__temp0), end0)
    __action63(
        state,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action531(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): Spanned<StmtP<AstNoPayload>> {
    val __0 = sym0.asStmtValueTriple()
    val __ret = run {
val start0 = __0.first;
    val end0 = __0.third;
    val __temp0 = __action439(
        state,
        __0,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant9(__temp0), end0)
    __action9(
        state,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action532(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): Spanned<StmtP<AstNoPayload>> {
    val __0 = sym0.asStmtValueTriple()
    val __ret = run {
val start0 = __0.first;
    val end0 = __0.third;
    val __temp0 = __action440(
        state,
        __0,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant9(__temp0), end0)
    __action65(
        state,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action533(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): Spanned<StmtP<AstNoPayload>> {
    val __0 = sym0.asStmtValueTriple()
    val __ret = run {
val start0 = __0.first;
    val end0 = __0.third;
    val __temp0 = __action441(
        state,
        __0,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant9(__temp0), end0)
    __action42(
        state,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action534(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): Spanned<StmtP<AstNoPayload>> {
    val __0 = sym0.asStmtValueTriple()
    val __ret = run {
val start0 = __0.first;
    val end0 = __0.third;
    val __temp0 = __action442(
        state,
        __0,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant9(__temp0), end0)
    __action36(
        state,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action535(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): Spanned<StmtP<AstNoPayload>> {
    val __0 = sym0.asStmtValueTriple()
    val __ret = run {
val start0 = __0.first;
    val end0 = __0.third;
    val __temp0 = __action443(
        state,
        __0,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant9(__temp0), end0)
    __action38(
        state,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action536(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): Spanned<StmtP<AstNoPayload>> {
    val __0 = sym0.asStmtValueTriple()
    val __ret = run {
val start0 = __0.first;
    val end0 = __0.third;
    val __temp0 = __action444(
        state,
        __0,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant9(__temp0), end0)
    __action68(
        state,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action537(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): List<Spanned<ArgumentP<AstNoPayload>>> {
    val __0 = sym0.asArgumentSpannedTriple()
    val __ret = run {
val start0 = __0.first;
    val end0 = __0.third;
    val __temp0 = __action217(
        state,
        __0,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant24(__temp0), end0)
    __action293(
        state,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action538(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, __lookbehind: Int, __lookahead: Int): List<Spanned<ArgumentP<AstNoPayload>>> {
    val __ret = run {
val start0 = __lookbehind;
    val end0 = __lookahead;
    val __temp0 = __action218(
        state,
        start0,
        end0,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant24(__temp0), end0)
    __action293(
        state,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action539(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>): List<Spanned<ArgumentP<AstNoPayload>>> {
    val __0 = sym0.asArgumentSpannedListTriple()
    val __1 = sym1.asArgumentSpannedTriple()
    val __ret = run {
val start0 = __1.first;
    val end0 = __1.third;
    val __temp0 = __action217(
        state,
        __1,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant24(__temp0), end0)
    __action294(
        state,
        __0,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action540(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): List<Spanned<ArgumentP<AstNoPayload>>> {
    val __0 = sym0.asArgumentSpannedListTriple()
    val __ret = run {
val start0 = __0.third;
    val end0 = __0.third;
    val __temp0 = __action218(
        state,
        start0,
        end0,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant24(__temp0), end0)
    __action294(
        state,
        __0,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action541(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): Pair<ForClauseP<AstNoPayload>, List<ClauseP<AstNoPayload>>> {
    val __0 = sym0.asForClauseValueTriple()
    val __ret = run {
val start0 = __0.third;
    val end0 = __0.third;
    val __temp0 = __action151(
        state,
        start0,
        end0,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant34(__temp0), end0)
    __action103(
        state,
        __0,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action542(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>): Pair<ForClauseP<AstNoPayload>, List<ClauseP<AstNoPayload>>> {
    val __0 = sym0.asForClauseValueTriple()
    val __1 = sym1.asClauseListTriple()
    val __ret = run {
val start0 = __1.first;
    val end0 = __1.third;
    val __temp0 = __action152(
        state,
        __1,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant34(__temp0), end0)
    __action103(
        state,
        __0,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action543(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): List<Spanned<ParameterP<AstNoPayload>>> {
    val __0 = sym0.asParameterSpannedTriple()
    val __ret = run {
val start0 = __0.first;
    val end0 = __0.third;
    val __temp0 = __action200(
        state,
        __0,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant37(__temp0), end0)
    __action297(
        state,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action544(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, __lookbehind: Int, __lookahead: Int): List<Spanned<ParameterP<AstNoPayload>>> {
    val __ret = run {
val start0 = __lookbehind;
    val end0 = __lookahead;
    val __temp0 = __action201(
        state,
        start0,
        end0,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant37(__temp0), end0)
    __action297(
        state,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action545(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>): List<Spanned<ParameterP<AstNoPayload>>> {
    val __0 = sym0.asParameterSpannedListTriple()
    val __1 = sym1.asParameterSpannedTriple()
    val __ret = run {
val start0 = __1.first;
    val end0 = __1.third;
    val __temp0 = __action200(
        state,
        __1,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant37(__temp0), end0)
    __action298(
        state,
        __0,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action546(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): List<Spanned<ParameterP<AstNoPayload>>> {
    val __0 = sym0.asParameterSpannedListTriple()
    val __ret = run {
val start0 = __0.third;
    val end0 = __0.third;
    val __temp0 = __action201(
        state,
        start0,
        end0,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant37(__temp0), end0)
    __action298(
        state,
        __0,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action547(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): List<Pair<Spanned<ExprP<AstNoPayload>>, Spanned<ExprP<AstNoPayload>>>> {
    val __0 = sym0.asExprPairTriple()
    val __ret = run {
val start0 = __0.first;
    val end0 = __0.third;
    val __temp0 = __action225(
        state,
        __0,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant40(__temp0), end0)
    __action301(
        state,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action548(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, __lookbehind: Int, __lookahead: Int): List<Pair<Spanned<ExprP<AstNoPayload>>, Spanned<ExprP<AstNoPayload>>>> {
    val __ret = run {
val start0 = __lookbehind;
    val end0 = __lookahead;
    val __temp0 = __action226(
        state,
        start0,
        end0,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant40(__temp0), end0)
    __action301(
        state,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action549(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>): List<Pair<Spanned<ExprP<AstNoPayload>>, Spanned<ExprP<AstNoPayload>>>> {
    val __0 = sym0.asExprPairListTriple()
    val __1 = sym1.asExprPairTriple()
    val __ret = run {
val start0 = __1.first;
    val end0 = __1.third;
    val __temp0 = __action225(
        state,
        __1,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant40(__temp0), end0)
    __action302(
        state,
        __0,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action550(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): List<Pair<Spanned<ExprP<AstNoPayload>>, Spanned<ExprP<AstNoPayload>>>> {
    val __0 = sym0.asExprPairListTriple()
    val __ret = run {
val start0 = __0.third;
    val end0 = __0.third;
    val __temp0 = __action226(
        state,
        start0,
        end0,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant40(__temp0), end0)
    __action302(
        state,
        __0,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action551(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>): StmtP<AstNoPayload> {
    val __0 = sym0.asExprSpannedTriple()
    val __1 = sym1.asTokenTriple()
    val __2 = sym2.asStmtSpannedTriple()
    val __3 = sym3.asStmtSpannedTriple()
    val __ret = run {
val start0 = __3.first;
    val end0 = __3.third;
    val __temp0 = __action181(
        state,
        __3,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant41(__temp0), end0)
    __action37(
        state,
        __0,
        __1,
        __2,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action552(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>): StmtP<AstNoPayload> {
    val __0 = sym0.asExprSpannedTriple()
    val __1 = sym1.asTokenTriple()
    val __2 = sym2.asStmtSpannedTriple()
    val __ret = run {
val start0 = __2.third;
    val end0 = __2.third;
    val __temp0 = __action182(
        state,
        start0,
        end0,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant41(__temp0), end0)
    __action37(
        state,
        __0,
        __1,
        __2,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action553(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): List<Spanned<ParameterP<AstNoPayload>>> {
    val __0 = sym0.asParameterSpannedTriple()
    val __ret = run {
val start0 = __0.first;
    val end0 = __0.third;
    val __temp0 = __action232(
        state,
        __0,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant37(__temp0), end0)
    __action311(
        state,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action554(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, __lookbehind: Int, __lookahead: Int): List<Spanned<ParameterP<AstNoPayload>>> {
    val __ret = run {
val start0 = __lookbehind;
    val end0 = __lookahead;
    val __temp0 = __action233(
        state,
        start0,
        end0,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant37(__temp0), end0)
    __action311(
        state,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action555(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>): List<Spanned<ParameterP<AstNoPayload>>> {
    val __0 = sym0.asParameterSpannedListTriple()
    val __1 = sym1.asParameterSpannedTriple()
    val __ret = run {
val start0 = __1.first;
    val end0 = __1.third;
    val __temp0 = __action232(
        state,
        __1,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant37(__temp0), end0)
    __action312(
        state,
        __0,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action556(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): List<Spanned<ParameterP<AstNoPayload>>> {
    val __0 = sym0.asParameterSpannedListTriple()
    val __ret = run {
val start0 = __0.third;
    val end0 = __0.third;
    val __temp0 = __action233(
        state,
        start0,
        end0,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant37(__temp0), end0)
    __action312(
        state,
        __0,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action557(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asStringSpannedTriple()
        val __1 = sym1.asStringSpannedTriple()
        val __ret = run {
    val start0 = __0.first;
        val end0 = __0.third;
        val __temp0 = __action166(
            state,
            __0,
        );
        val __temp0_triple= Triple(start0, GrammarSymbol.Variant45(__temp0), end0)
        __action72(
            state,
            __temp0_triple,
            __1,
        )
        }
    __ret
}

internal fun __action558(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>) = run {
        val __0 = sym0.asStringSpannedTriple()
        val __ret = run {
    val start0 = __0.first;
        val end0 = __0.first;
        val __temp0 = __action167(
            state,
            start0,
            end0,
        );
        val __temp0_triple= Triple(start0, GrammarSymbol.Variant45(__temp0), end0)
        __action72(
            state,
            __temp0_triple,
            __0,
        )
        }
    __ret
}

internal fun __action559(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>): Spanned<ExprP<AstNoPayload>> {
    val __0 = sym0.asTokenTriple()
    val __1 = sym1.asExprSpannedTriple()
    val __2 = sym2.asTokenTriple()
    val __ret = run {
val start0 = __1.first;
    val end0 = __1.third;
    val __temp0 = __action177(
        state,
        __1,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant7(__temp0), end0)
    __action479(
        state,
        __0,
        __temp0_triple,
        __2,
    )
    }
    return __ret
}

internal fun __action560(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>): Spanned<ExprP<AstNoPayload>> {
    val __0 = sym0.asTokenTriple()
    val __1 = sym1.asTokenTriple()
    val __ret = run {
val start0 = __0.third;
    val end0 = __1.first;
    val __temp0 = __action178(
        state,
        start0,
        end0,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant7(__temp0), end0)
    __action479(
        state,
        __0,
        __temp0_triple,
        __1,
    )
    }
    return __ret
}

internal fun __action561(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>): Spanned<StmtP<AstNoPayload>> {
    val __0 = sym0.asTokenTriple()
    val __1 = sym1.asExprSpannedTriple()
    val __ret = run {
val start0 = __1.first;
    val end0 = __1.third;
    val __temp0 = __action177(
        state,
        __1,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant7(__temp0), end0)
    __action508(
        state,
        __0,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action562(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): Spanned<StmtP<AstNoPayload>> {
    val __0 = sym0.asTokenTriple()
    val __ret = run {
val start0 = __0.third;
    val end0 = __0.third;
    val __temp0 = __action178(
        state,
        start0,
        end0,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant7(__temp0), end0)
    __action508(
        state,
        __0,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action563(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): Spanned<ExprP<AstNoPayload>> {
    val __0 = sym0.asDoubleValueTriple()
    val __ret = run {
val start0 = __0.first;
    val end0 = __0.third;
    val __temp0 = __action519(
        state,
        __0,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant48(__temp0), end0)
    __action474(
        state,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action564(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): Spanned<ExprP<AstNoPayload>> {
    val __0 = sym0.asTokenFStringTriple()
    val __ret = run {
val start0 = __0.first;
    val end0 = __0.third;
    val __temp0 = __action520(
        state,
        __0,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant49(__temp0), end0)
    __action480(
        state,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action565(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>): ArgumentP<AstNoPayload> {
    val __0 = sym0.asStringValueTriple()
    val __1 = sym1.asTokenTriple()
    val __2 = sym2.asExprSpannedTriple()
    val __ret = run {
val start0 = __0.first;
    val end0 = __0.third;
    val __temp0 = __action521(
        state,
        __0,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant44(__temp0), end0)
    __action84(
        state,
        __temp0_triple,
        __1,
        __2,
    )
    }
    return __ret
}

internal fun __action566(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): Spanned<AssignIdentP<AstNoPayload>> {
    val __0 = sym0.asStringValueTriple()
    val __ret = run {
val start0 = __0.first;
    val end0 = __0.third;
    val __temp0 = __action521(
        state,
        __0,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant44(__temp0), end0)
    __action7(
        state,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action567(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): Spanned<IdentP<AstNoPayload>> {
    val __0 = sym0.asStringValueTriple()
    val __ret = run {
val start0 = __0.first;
    val end0 = __0.third;
    val __temp0 = __action521(
        state,
        __0,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant44(__temp0), end0)
    __action6(
        state,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action568(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>): Spanned<String> {
    val __0 = sym0.asStringValueTriple()
    val __1 = sym1.asTokenTriple()
    val __ret = run {
val start0 = __0.first;
    val end0 = __0.third;
    val __temp0 = __action521(
        state,
        __0,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant44(__temp0), end0)
    __action71(
        state,
        __temp0_triple,
        __1,
    )
    }
    return __ret
}

internal fun __action569(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>): Spanned<ExprP<AstNoPayload>> {
    val __0 = sym0.asExprSpannedTriple()
    val __1 = sym1.asTokenTriple()
    val __2 = sym2.asStringValueTriple()
    val __ret = run {
val start0 = __2.first;
    val end0 = __2.third;
    val __temp0 = __action521(
        state,
        __2,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant44(__temp0), end0)
    __action482(
        state,
        __0,
        __1,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action570(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): Spanned<ExprP<AstNoPayload>> {
    val __0 = sym0.asTokenIntTriple()
    val __ret = run {
val start0 = __0.first;
    val end0 = __0.third;
    val __temp0 = __action522(
        state,
        __0,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant50(__temp0), end0)
    __action473(
        state,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action571(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>): Pair<Spanned<AssignIdentP<AstNoPayload>>, Spanned<String>> {
    val __0 = sym0.asStringSpannedTriple()
    val __1 = sym1.asStringValueTriple()
    val __ret = run {
val start0 = __1.first;
    val end0 = __1.third;
    val __temp0 = __action523(
        state,
        __1,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant44(__temp0), end0)
    __action557(
        state,
        __0,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action572(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): Pair<Spanned<AssignIdentP<AstNoPayload>>, Spanned<String>> {
    val __0 = sym0.asStringValueTriple()
    val __ret = run {
val start0 = __0.first;
    val end0 = __0.third;
    val __temp0 = __action523(
        state,
        __0,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant44(__temp0), end0)
    __action558(
        state,
        __temp0_triple,
    )
    }
    return __ret
}

internal fun __action573(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>): StmtP<AstNoPayload> {
    val __0 = sym0.asTokenTriple()
    val __1 = sym1.asTokenTriple()
    val __2 = sym2.asStringValueTriple()
    val __3 = sym3.asTokenTriple()
    val __ret = run {
val start0 = __2.first;
    val end0 = __2.third;
    val __temp0 = __action523(
        state,
        __2,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant44(__temp0), end0)
    __action69(
        state,
        __0,
        __1,
        __temp0_triple,
        __3,
    )
    }
    return __ret
}

internal fun __action574(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>, sym4: Triple<Int, GrammarSymbol, Int>, sym5: Triple<Int, GrammarSymbol, Int>): StmtP<AstNoPayload> {
    val __0 = sym0.asTokenTriple()
    val __1 = sym1.asTokenTriple()
    val __2 = sym2.asStringValueTriple()
    val __3 = sym3.asCommaSpannedTriple()
    val __4 = sym4.asAssignIdentStringPairTriple()
    val __5 = sym5.asTokenTriple()
    val __ret = run {
val start0 = __2.first;
    val end0 = __2.third;
    val __temp0 = __action523(
        state,
        __2,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant44(__temp0), end0)
    __action318(
        state,
        __0,
        __1,
        __temp0_triple,
        __3,
        __4,
        __5,
    )
    }
    return __ret
}

internal fun __action575(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>, sym4: Triple<Int, GrammarSymbol, Int>): StmtP<AstNoPayload> {
    val __0 = sym0.asTokenTriple()
    val __1 = sym1.asTokenTriple()
    val __2 = sym2.asStringValueTriple()
    val __3 = sym3.asCommaSpannedTriple()
    val __4 = sym4.asTokenTriple()
    val __ret = run {
val start0 = __2.first;
    val end0 = __2.third;
    val __temp0 = __action523(
        state,
        __2,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant44(__temp0), end0)
    __action319(
        state,
        __0,
        __1,
        __temp0_triple,
        __3,
        __4,
    )
    }
    return __ret
}

internal fun __action576(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>, sym4: Triple<Int, GrammarSymbol, Int>, sym5: Triple<Int, GrammarSymbol, Int>, sym6: Triple<Int, GrammarSymbol, Int>): StmtP<AstNoPayload> {
    val __0 = sym0.asTokenTriple()
    val __1 = sym1.asTokenTriple()
    val __2 = sym2.asStringValueTriple()
    val __3 = sym3.asCommaSpannedTriple()
    val __4 = sym4.asLoadPairListTriple()
    val __5 = sym5.asAssignIdentStringPairTriple()
    val __6 = sym6.asTokenTriple()
    val __ret = run {
val start0 = __2.first;
    val end0 = __2.third;
    val __temp0 = __action523(
        state,
        __2,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant44(__temp0), end0)
    __action320(
        state,
        __0,
        __1,
        __temp0_triple,
        __3,
        __4,
        __5,
        __6,
    )
    }
    return __ret
}

internal fun __action577(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>, sym1: Triple<Int, GrammarSymbol, Int>, sym2: Triple<Int, GrammarSymbol, Int>, sym3: Triple<Int, GrammarSymbol, Int>, sym4: Triple<Int, GrammarSymbol, Int>, sym5: Triple<Int, GrammarSymbol, Int>): StmtP<AstNoPayload> {
    val __0 = sym0.asTokenTriple()
    val __1 = sym1.asTokenTriple()
    val __2 = sym2.asStringValueTriple()
    val __3 = sym3.asCommaSpannedTriple()
    val __4 = sym4.asLoadPairListTriple()
    val __5 = sym5.asTokenTriple()
    val __ret = run {
val start0 = __2.first;
    val end0 = __2.third;
    val __temp0 = __action523(
        state,
        __2,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant44(__temp0), end0)
    __action321(
        state,
        __0,
        __1,
        __temp0_triple,
        __3,
        __4,
        __5,
    )
    }
    return __ret
}

internal fun __action578(state: io.github.kotlinmania.starlarksyntax.syntax.state.ParserState, sym0: Triple<Int, GrammarSymbol, Int>): Spanned<ExprP<AstNoPayload>> {
    val __0 = sym0.asStringValueTriple()
    val __ret = run {
val start0 = __0.first;
    val end0 = __0.third;
    val __temp0 = __action523(
        state,
        __0,
    );
    val __temp0_triple= Triple(start0, GrammarSymbol.Variant44(__temp0), end0)
    __action475(
        state,
        __temp0_triple,
    )
    }
    return __ret
}

}
