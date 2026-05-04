// port-lint: source src/syntax/payload_map.rs
package io.github.kotlinmania.starlarksyntax.syntax.payloadmap

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

//! Map AST payload.

import io.github.kotlinmania.starlarksyntax.codemap.Spanned
import io.github.kotlinmania.starlarksyntax.syntax.ast.ArgumentP
import io.github.kotlinmania.starlarksyntax.syntax.ast.AssignIdentP
import io.github.kotlinmania.starlarksyntax.syntax.ast.AssignP
import io.github.kotlinmania.starlarksyntax.syntax.ast.AssignTargetP
import io.github.kotlinmania.starlarksyntax.syntax.ast.AstPayload
import io.github.kotlinmania.starlarksyntax.syntax.ast.CallArgsP
import io.github.kotlinmania.starlarksyntax.syntax.ast.ClauseP
import io.github.kotlinmania.starlarksyntax.syntax.ast.DefP
import io.github.kotlinmania.starlarksyntax.syntax.ast.ExprP
import io.github.kotlinmania.starlarksyntax.syntax.ast.FStringP
import io.github.kotlinmania.starlarksyntax.syntax.ast.ForClauseP
import io.github.kotlinmania.starlarksyntax.syntax.ast.ForP
import io.github.kotlinmania.starlarksyntax.syntax.ast.IdentP
import io.github.kotlinmania.starlarksyntax.syntax.ast.LambdaP
import io.github.kotlinmania.starlarksyntax.syntax.ast.LoadArgP
import io.github.kotlinmania.starlarksyntax.syntax.ast.LoadP
import io.github.kotlinmania.starlarksyntax.syntax.ast.ParameterP
import io.github.kotlinmania.starlarksyntax.syntax.ast.StmtP
import io.github.kotlinmania.starlarksyntax.syntax.ast.TypeExprP

/**
 * A function-bundle that maps payload values from payload-type [A] to payload-type [B] across an
 * AST. Mirrors upstream `AstPayloadFunction<A, B>`.
 *
 * Upstream uses Rust associated types (`A::LoadPayload`, etc.) to type the payload values per
 * kind. Kotlin doesn't have associated types — this port carries each payload as `Any?` and the
 * concrete payload-bundle type provides the typed getters/setters.
 */
interface AstPayloadFunction<A : AstPayload, B : AstPayload> {
    fun mapLoad(importPath: String, a: Any?): Any?
    fun mapIdent(a: Any?): Any?
    fun mapIdentAssign(a: Any?): Any?
    fun mapDef(a: Any?): Any?
    fun mapTypeExpr(a: Any?): Any?
}

fun <A : AstPayload, B : AstPayload> LoadArgP<A>.intoMapPayload(
    f: AstPayloadFunction<A, B>,
): LoadArgP<B> {
    return LoadArgP(
        local = this.local.intoMapPayloadAssignIdent(f),
        their = this.their,
        comma = this.comma,
    )
}

fun <A : AstPayload, B : AstPayload> LoadP<A>.intoMapPayload(
    f: AstPayloadFunction<A, B>,
): LoadP<B> {
    val payload = f.mapLoad(this.module.node, this.payload)
    return LoadP(
        module = this.module,
        args = this.args.map { it.intoMapPayload(f) },
        payload = payload,
    )
}

fun <A : AstPayload, B : AstPayload> AssignP<A>.intoMapPayload(
    f: AstPayloadFunction<A, B>,
): AssignP<B> {
    return AssignP(
        lhs = this.lhs.intoMapPayloadAssignTarget(f),
        ty = this.ty?.intoMapPayloadTypeExpr(f),
        rhs = this.rhs.intoMapPayloadExpr(f),
    )
}

fun <A : AstPayload, B : AstPayload> ForP<A>.intoMapPayload(
    f: AstPayloadFunction<A, B>,
): ForP<B> {
    return ForP(
        variable = this.variable.intoMapPayloadAssignTarget(f),
        over = this.over.intoMapPayloadExpr(f),
        body = this.body.intoMapPayloadStmt(f),
    )
}

fun <A : AstPayload, B : AstPayload> StmtP<A>.intoMapPayload(
    f: AstPayloadFunction<A, B>,
): StmtP<B> {
    return when (val self = this) {
        is StmtP.Break -> StmtP.Break()
        is StmtP.Continue -> StmtP.Continue()
        is StmtP.Pass -> StmtP.Pass()
        is StmtP.Return -> StmtP.Return(self.value?.intoMapPayloadExpr(f))
        is StmtP.Expression -> StmtP.Expression(self.expr.intoMapPayloadExpr(f))
        is StmtP.Assign -> StmtP.Assign(self.assign.intoMapPayload(f))
        is StmtP.AssignModify -> StmtP.AssignModify(
            lhs = self.lhs.intoMapPayloadAssignTarget(f),
            op = self.op,
            rhs = self.rhs.intoMapPayloadExpr(f),
        )
        is StmtP.Statements -> StmtP.Statements(self.stmts.map { it.intoMapPayloadStmt(f) })
        is StmtP.If -> StmtP.If(
            cond = self.cond.intoMapPayloadExpr(f),
            suite = self.suite.intoMapPayloadStmt(f),
        )
        is StmtP.IfElse -> StmtP.IfElse(
            cond = self.cond.intoMapPayloadExpr(f),
            suite1 = self.suite1.intoMapPayloadStmt(f),
            suite2 = self.suite2.intoMapPayloadStmt(f),
        )
        is StmtP.For -> StmtP.For(self.forStmt.intoMapPayload(f))
        is StmtP.Def -> StmtP.Def(
            DefP(
                name = self.def.name.intoMapPayloadAssignIdent(f),
                params = self.def.params.map { it.intoMapPayloadParameter(f) },
                returnType = self.def.returnType?.intoMapPayloadTypeExpr(f),
                body = self.def.body.intoMapPayloadStmt(f),
                payload = f.mapDef(self.def.payload),
            )
        )
        is StmtP.Load -> StmtP.Load(self.load.intoMapPayload(f))
    }
}

fun <A : AstPayload, B : AstPayload> ExprP<A>.intoMapPayload(
    f: AstPayloadFunction<A, B>,
): ExprP<B> {
    return when (val self = this) {
        is ExprP.Tuple -> ExprP.Tuple(self.elems.map { it.intoMapPayloadExpr(f) })
        is ExprP.Dot -> ExprP.Dot(target = self.target.intoMapPayloadExpr(f), attr = self.attr)
        is ExprP.Call -> ExprP.Call(
            target = self.target.intoMapPayloadExpr(f),
            args = CallArgsP(args = self.args.args.map { it.intoMapPayloadArgument(f) }),
        )
        is ExprP.Index -> ExprP.Index(
            target = self.target.intoMapPayloadExpr(f),
            index = self.index.intoMapPayloadExpr(f),
        )
        is ExprP.Index2 -> ExprP.Index2(
            target = self.target.intoMapPayloadExpr(f),
            index0 = self.index0.intoMapPayloadExpr(f),
            index1 = self.index1.intoMapPayloadExpr(f),
        )
        is ExprP.Slice -> ExprP.Slice(
            target = self.target.intoMapPayloadExpr(f),
            start = self.start?.intoMapPayloadExpr(f),
            stop = self.stop?.intoMapPayloadExpr(f),
            step = self.step?.intoMapPayloadExpr(f),
        )
        is ExprP.Identifier -> ExprP.Identifier(self.ident.intoMapPayloadIdent(f))
        is ExprP.Lambda -> ExprP.Lambda(
            LambdaP(
                params = self.lambda.params.map { it.intoMapPayloadParameter(f) },
                body = self.lambda.body.intoMapPayloadExpr(f),
                payload = f.mapDef(self.lambda.payload),
            )
        )
        is ExprP.Literal -> ExprP.Literal(self.literal)
        is ExprP.Not -> ExprP.Not(self.target.intoMapPayloadExpr(f))
        is ExprP.Minus -> ExprP.Minus(self.target.intoMapPayloadExpr(f))
        is ExprP.Plus -> ExprP.Plus(self.target.intoMapPayloadExpr(f))
        is ExprP.BitNot -> ExprP.BitNot(self.target.intoMapPayloadExpr(f))
        is ExprP.Op -> ExprP.Op(
            left = self.left.intoMapPayloadExpr(f),
            op = self.op,
            right = self.right.intoMapPayloadExpr(f),
        )
        is ExprP.If -> ExprP.If(
            condition = self.condition.intoMapPayloadExpr(f),
            v1 = self.v1.intoMapPayloadExpr(f),
            v2 = self.v2.intoMapPayloadExpr(f),
        )
        is ExprP.List -> ExprP.List(self.elems.map { it.intoMapPayloadExpr(f) })
        is ExprP.Dict -> ExprP.Dict(
            self.entries.map { (k, v) -> Pair(k.intoMapPayloadExpr(f), v.intoMapPayloadExpr(f)) }
        )
        is ExprP.ListComprehension -> ExprP.ListComprehension(
            expr = self.expr.intoMapPayloadExpr(f),
            firstFor = self.firstFor.intoMapPayload(f),
            clauses = self.clauses.map { it.intoMapPayload(f) },
        )
        is ExprP.DictComprehension -> ExprP.DictComprehension(
            key = self.key.intoMapPayloadExpr(f),
            value = self.value.intoMapPayloadExpr(f),
            firstFor = self.firstFor.intoMapPayload(f),
            clauses = self.clauses.map { it.intoMapPayload(f) },
        )
        is ExprP.FString -> ExprP.FString(self.fstring.intoMapPayloadFString(f))
    }
}

fun <A : AstPayload, B : AstPayload> TypeExprP<A>.intoMapPayload(
    f: AstPayloadFunction<A, B>,
): TypeExprP<B> {
    return TypeExprP(
        expr = this.expr.intoMapPayloadExpr(f),
        payload = f.mapTypeExpr(this.payload),
    )
}

fun <A : AstPayload, B : AstPayload> AssignTargetP<A>.intoMapPayload(
    f: AstPayloadFunction<A, B>,
): AssignTargetP<B> {
    return when (val self = this) {
        is AssignTargetP.Tuple -> AssignTargetP.Tuple(
            self.elems.map { it.intoMapPayloadAssignTarget(f) }
        )
        is AssignTargetP.Index -> AssignTargetP.Index(
            target = self.target.intoMapPayloadExpr(f),
            index = self.index.intoMapPayloadExpr(f),
        )
        is AssignTargetP.Dot -> AssignTargetP.Dot(
            target = self.target.intoMapPayloadExpr(f),
            attr = self.attr,
        )
        is AssignTargetP.Identifier -> AssignTargetP.Identifier(
            self.ident.intoMapPayloadAssignIdent(f)
        )
    }
}

fun <A : AstPayload, B : AstPayload> AssignIdentP<A>.intoMapPayload(
    f: AstPayloadFunction<A, B>,
): AssignIdentP<B> {
    return AssignIdentP(
        ident = this.ident,
        payload = f.mapIdentAssign(this.payload),
    )
}

fun <A : AstPayload, B : AstPayload> IdentP<A>.intoMapPayload(
    f: AstPayloadFunction<A, B>,
): IdentP<B> {
    return IdentP(
        ident = this.ident,
        payload = f.mapIdent(this.payload),
    )
}

fun <A : AstPayload, B : AstPayload> ParameterP<A>.intoMapPayload(
    f: AstPayloadFunction<A, B>,
): ParameterP<B> {
    return when (val self = this) {
        is ParameterP.Normal -> ParameterP.Normal(
            name = self.name.intoMapPayloadAssignIdent(f),
            type = self.type?.intoMapPayloadTypeExpr(f),
            default = self.default?.intoMapPayloadExpr(f),
        )
        is ParameterP.NoArgs -> ParameterP.NoArgs()
        is ParameterP.Slash -> ParameterP.Slash()
        is ParameterP.Args -> ParameterP.Args(
            name = self.name.intoMapPayloadAssignIdent(f),
            type = self.type?.intoMapPayloadTypeExpr(f),
        )
        is ParameterP.KwArgs -> ParameterP.KwArgs(
            name = self.name.intoMapPayloadAssignIdent(f),
            type = self.type?.intoMapPayloadTypeExpr(f),
        )
    }
}

fun <A : AstPayload, B : AstPayload> ArgumentP<A>.intoMapPayload(
    f: AstPayloadFunction<A, B>,
): ArgumentP<B> {
    return when (val self = this) {
        is ArgumentP.Positional -> ArgumentP.Positional(self.expr.intoMapPayloadExpr(f))
        is ArgumentP.Named -> ArgumentP.Named(self.name, self.expr.intoMapPayloadExpr(f))
        is ArgumentP.Args -> ArgumentP.Args(self.expr.intoMapPayloadExpr(f))
        is ArgumentP.KwArgs -> ArgumentP.KwArgs(self.expr.intoMapPayloadExpr(f))
    }
}

fun <A : AstPayload, B : AstPayload> ClauseP<A>.intoMapPayload(
    f: AstPayloadFunction<A, B>,
): ClauseP<B> {
    return when (val self = this) {
        is ClauseP.For -> ClauseP.For(self.clause.intoMapPayload(f))
        is ClauseP.If -> ClauseP.If(self.cond.intoMapPayloadExpr(f))
    }
}

fun <A : AstPayload, B : AstPayload> ForClauseP<A>.intoMapPayload(
    f: AstPayloadFunction<A, B>,
): ForClauseP<B> {
    return ForClauseP(
        variable = this.variable.intoMapPayloadAssignTarget(f),
        over = this.over.intoMapPayloadExpr(f),
    )
}

fun <A : AstPayload, B : AstPayload> FStringP<A>.intoMapPayload(
    f: AstPayloadFunction<A, B>,
): FStringP<B> {
    return FStringP(
        format = this.format,
        expressions = this.expressions.map { it.intoMapPayloadExpr(f) },
    )
}

// The Rust upstream uses an `ast_payload_map_stub!` macro to emit `into_map_payload` adapters
// for each `Spanned<X<P>>` wrapper. Kotlin can express the same with a single generic extension
// for each AST type below — eight extensions, one per AST type.

// Spanned<X<A>> wrappers: rename each by AST type to avoid JVM erasure clash on the
// shared 'intoMapPayload' name. The Rust upstream's `ast_payload_map_stub!` macro
// relies on Rust's monomorphization, which Kotlin doesn't get on JVM after erasure.

fun <A : AstPayload, B : AstPayload> Spanned<ExprP<A>>.intoMapPayloadExpr(
    f: AstPayloadFunction<A, B>,
): Spanned<ExprP<B>> = Spanned(node = this.node.intoMapPayload(f), span = this.span)

fun <A : AstPayload, B : AstPayload> Spanned<TypeExprP<A>>.intoMapPayloadTypeExpr(
    f: AstPayloadFunction<A, B>,
): Spanned<TypeExprP<B>> = Spanned(node = this.node.intoMapPayload(f), span = this.span)

fun <A : AstPayload, B : AstPayload> Spanned<AssignTargetP<A>>.intoMapPayloadAssignTarget(
    f: AstPayloadFunction<A, B>,
): Spanned<AssignTargetP<B>> = Spanned(node = this.node.intoMapPayload(f), span = this.span)

fun <A : AstPayload, B : AstPayload> Spanned<AssignIdentP<A>>.intoMapPayloadAssignIdent(
    f: AstPayloadFunction<A, B>,
): Spanned<AssignIdentP<B>> = Spanned(node = this.node.intoMapPayload(f), span = this.span)

fun <A : AstPayload, B : AstPayload> Spanned<IdentP<A>>.intoMapPayloadIdent(
    f: AstPayloadFunction<A, B>,
): Spanned<IdentP<B>> = Spanned(node = this.node.intoMapPayload(f), span = this.span)

fun <A : AstPayload, B : AstPayload> Spanned<ParameterP<A>>.intoMapPayloadParameter(
    f: AstPayloadFunction<A, B>,
): Spanned<ParameterP<B>> = Spanned(node = this.node.intoMapPayload(f), span = this.span)

fun <A : AstPayload, B : AstPayload> Spanned<ArgumentP<A>>.intoMapPayloadArgument(
    f: AstPayloadFunction<A, B>,
): Spanned<ArgumentP<B>> = Spanned(node = this.node.intoMapPayload(f), span = this.span)

fun <A : AstPayload, B : AstPayload> Spanned<StmtP<A>>.intoMapPayloadStmt(
    f: AstPayloadFunction<A, B>,
): Spanned<StmtP<B>> = Spanned(node = this.node.intoMapPayload(f), span = this.span)

fun <A : AstPayload, B : AstPayload> Spanned<FStringP<A>>.intoMapPayloadFString(
    f: AstPayloadFunction<A, B>,
): Spanned<FStringP<B>> = Spanned(node = this.node.intoMapPayload(f), span = this.span)
