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

/** Map AST payload. */

import io.github.kotlinmania.starlarksyntax.codemap.Spanned
import io.github.kotlinmania.starlarksyntax.syntax.ast.Argument
import io.github.kotlinmania.starlarksyntax.syntax.ast.Assign
import io.github.kotlinmania.starlarksyntax.syntax.ast.AssignIdent
import io.github.kotlinmania.starlarksyntax.syntax.ast.AssignTarget
import io.github.kotlinmania.starlarksyntax.syntax.ast.AstArgument
import io.github.kotlinmania.starlarksyntax.syntax.ast.AstAssignIdent
import io.github.kotlinmania.starlarksyntax.syntax.ast.AstAssignTarget
import io.github.kotlinmania.starlarksyntax.syntax.ast.AstExpr
import io.github.kotlinmania.starlarksyntax.syntax.ast.AstFString
import io.github.kotlinmania.starlarksyntax.syntax.ast.AstIdent
import io.github.kotlinmania.starlarksyntax.syntax.ast.AstParameter
import io.github.kotlinmania.starlarksyntax.syntax.ast.AstStmt
import io.github.kotlinmania.starlarksyntax.syntax.ast.AstTypeExpr
import io.github.kotlinmania.starlarksyntax.syntax.ast.CallArgs
import io.github.kotlinmania.starlarksyntax.syntax.ast.Clause
import io.github.kotlinmania.starlarksyntax.syntax.ast.Def
import io.github.kotlinmania.starlarksyntax.syntax.ast.Expr
import io.github.kotlinmania.starlarksyntax.syntax.ast.FString
import io.github.kotlinmania.starlarksyntax.syntax.ast.For
import io.github.kotlinmania.starlarksyntax.syntax.ast.ForClause
import io.github.kotlinmania.starlarksyntax.syntax.ast.Ident
import io.github.kotlinmania.starlarksyntax.syntax.ast.Lambda
import io.github.kotlinmania.starlarksyntax.syntax.ast.Load
import io.github.kotlinmania.starlarksyntax.syntax.ast.LoadArg
import io.github.kotlinmania.starlarksyntax.syntax.ast.Parameter
import io.github.kotlinmania.starlarksyntax.syntax.ast.Stmt
import io.github.kotlinmania.starlarksyntax.syntax.ast.TypeExpr

/**
 * A function-bundle that maps payload values across an AST.
 */
internal interface AstPayloadFunction {
    fun mapLoad(importPath: String, a: Any?): Any?
    fun mapIdent(a: Any?): Any?
    fun mapIdentAssign(a: Any?): Any?
    fun mapDef(a: Any?): Any?
    fun mapTypeExpr(a: Any?): Any?
}

internal fun LoadArg.intoMapPayload(
    f: AstPayloadFunction,
): LoadArg {
    return LoadArg(
        local = this.local.intoMapPayloadAssignIdent(f),
        their = this.their,
        comma = this.comma,
    )
}

internal fun Load.intoMapPayload(
    f: AstPayloadFunction,
): Load {
    val payload = f.mapLoad(this.module.node, this.payload)
    return Load(
        module = this.module,
        args = this.args.map { it.intoMapPayload(f) },
        payload = payload,
    )
}

internal fun Assign.intoMapPayload(
    f: AstPayloadFunction,
): Assign {
    return Assign(
        lhs = this.lhs.intoMapPayloadAssignTarget(f),
        ty = this.ty?.intoMapPayloadTypeExpr(f),
        rhs = this.rhs.intoMapPayloadExpr(f),
    )
}

internal fun For.intoMapPayload(
    f: AstPayloadFunction,
): For {
    return For(
        variable = this.variable.intoMapPayloadAssignTarget(f),
        over = this.over.intoMapPayloadExpr(f),
        body = this.body.intoMapPayloadStmt(f),
    )
}

internal fun Stmt.intoMapPayload(
    f: AstPayloadFunction,
): Stmt {
    return when (val self = this) {
        is Stmt.Break -> Stmt.Break()
        is Stmt.Continue -> Stmt.Continue()
        is Stmt.Pass -> Stmt.Pass()
        is Stmt.Return -> Stmt.Return(self.value?.intoMapPayloadExpr(f))
        is Stmt.Expression -> Stmt.Expression(self.expr.intoMapPayloadExpr(f))
        is Stmt.Assign -> Stmt.Assign(self.assign.intoMapPayload(f))
        is Stmt.AssignModify -> Stmt.AssignModify(
            lhs = self.lhs.intoMapPayloadAssignTarget(f),
            op = self.op,
            rhs = self.rhs.intoMapPayloadExpr(f),
        )
        is Stmt.Statements -> Stmt.Statements(self.stmts.map { it.intoMapPayloadStmt(f) })
        is Stmt.If -> Stmt.If(
            cond = self.cond.intoMapPayloadExpr(f),
            suite = self.suite.intoMapPayloadStmt(f),
        )
        is Stmt.IfElse -> Stmt.IfElse(
            cond = self.cond.intoMapPayloadExpr(f),
            suite1 = self.suite1.intoMapPayloadStmt(f),
            suite2 = self.suite2.intoMapPayloadStmt(f),
        )
        is Stmt.For -> Stmt.For(self.forStmt.intoMapPayload(f))
        is Stmt.Def -> Stmt.Def(
            Def(
                name = self.def.name.intoMapPayloadAssignIdent(f),
                params = self.def.params.map { it.intoMapPayloadParameter(f) },
                returnType = self.def.returnType?.intoMapPayloadTypeExpr(f),
                body = self.def.body.intoMapPayloadStmt(f),
                payload = f.mapDef(self.def.payload),
            )
        )
        is Stmt.Load -> Stmt.Load(self.load.intoMapPayload(f))
    }
}

internal fun Expr.intoMapPayload(
    f: AstPayloadFunction,
): Expr {
    return when (val self = this) {
        is Expr.Tuple -> Expr.Tuple(self.elems.map { it.intoMapPayloadExpr(f) })
        is Expr.Dot -> Expr.Dot(target = self.target.intoMapPayloadExpr(f), attr = self.attr)
        is Expr.Call -> Expr.Call(
            target = self.target.intoMapPayloadExpr(f),
            args = CallArgs(args = self.args.args.map { it.intoMapPayloadArgument(f) }),
        )
        is Expr.Index -> Expr.Index(
            target = self.target.intoMapPayloadExpr(f),
            index = self.index.intoMapPayloadExpr(f),
        )
        is Expr.Index2 -> Expr.Index2(
            target = self.target.intoMapPayloadExpr(f),
            index0 = self.index0.intoMapPayloadExpr(f),
            index1 = self.index1.intoMapPayloadExpr(f),
        )
        is Expr.Slice -> Expr.Slice(
            target = self.target.intoMapPayloadExpr(f),
            start = self.start?.intoMapPayloadExpr(f),
            stop = self.stop?.intoMapPayloadExpr(f),
            step = self.step?.intoMapPayloadExpr(f),
        )
        is Expr.Identifier -> Expr.Identifier(self.ident.intoMapPayloadIdent(f))
        is Expr.Lambda -> Expr.Lambda(
            Lambda(
                params = self.lambda.params.map { it.intoMapPayloadParameter(f) },
                body = self.lambda.body.intoMapPayloadExpr(f),
                payload = f.mapDef(self.lambda.payload),
            )
        )
        is Expr.Literal -> Expr.Literal(self.literal)
        is Expr.Not -> Expr.Not(self.target.intoMapPayloadExpr(f))
        is Expr.Minus -> Expr.Minus(self.target.intoMapPayloadExpr(f))
        is Expr.Plus -> Expr.Plus(self.target.intoMapPayloadExpr(f))
        is Expr.BitNot -> Expr.BitNot(self.target.intoMapPayloadExpr(f))
        is Expr.Op -> Expr.Op(
            left = self.left.intoMapPayloadExpr(f),
            op = self.op,
            right = self.right.intoMapPayloadExpr(f),
        )
        is Expr.If -> Expr.If(
            condition = self.condition.intoMapPayloadExpr(f),
            v1 = self.v1.intoMapPayloadExpr(f),
            v2 = self.v2.intoMapPayloadExpr(f),
        )
        is Expr.List -> Expr.List(self.elems.map { it.intoMapPayloadExpr(f) })
        is Expr.Dict -> Expr.Dict(
            self.entries.map { (k, v) -> Expr.DictEntry(k.intoMapPayloadExpr(f), v.intoMapPayloadExpr(f)) }
        )
        is Expr.ListComprehension -> Expr.ListComprehension(
            expr = self.expr.intoMapPayloadExpr(f),
            firstFor = self.firstFor.intoMapPayload(f),
            clauses = self.clauses.map { it.intoMapPayload(f) },
        )
        is Expr.DictComprehension -> Expr.DictComprehension(
            key = self.key.intoMapPayloadExpr(f),
            value = self.value.intoMapPayloadExpr(f),
            firstFor = self.firstFor.intoMapPayload(f),
            clauses = self.clauses.map { it.intoMapPayload(f) },
        )
        is Expr.FString -> Expr.FString(self.fstring.intoMapPayloadFString(f))
    }
}

internal fun TypeExpr.intoMapPayload(
    f: AstPayloadFunction,
): TypeExpr {
    return TypeExpr(
        expr = this.expr.intoMapPayloadExpr(f),
        payload = f.mapTypeExpr(this.payload),
    )
}

internal fun AssignTarget.intoMapPayload(
    f: AstPayloadFunction,
): AssignTarget {
    return when (val self = this) {
        is AssignTarget.Tuple -> AssignTarget.Tuple(
            self.elems.map { it.intoMapPayloadAssignTarget(f) }
        )
        is AssignTarget.Index -> AssignTarget.Index(
            target = self.target.intoMapPayloadExpr(f),
            index = self.index.intoMapPayloadExpr(f),
        )
        is AssignTarget.Dot -> AssignTarget.Dot(
            target = self.target.intoMapPayloadExpr(f),
            attr = self.attr,
        )
        is AssignTarget.Identifier -> AssignTarget.Identifier(
            self.ident.intoMapPayloadAssignIdent(f)
        )
    }
}

internal fun AssignIdent.intoMapPayload(
    f: AstPayloadFunction,
): AssignIdent {
    return AssignIdent(
        ident = this.ident,
        payload = f.mapIdentAssign(this.payload),
    )
}

internal fun Ident.intoMapPayload(
    f: AstPayloadFunction,
): Ident {
    return Ident(
        ident = this.ident,
        payload = f.mapIdent(this.payload),
    )
}

internal fun Parameter.intoMapPayload(
    f: AstPayloadFunction,
): Parameter {
    return when (val self = this) {
        is Parameter.Normal -> Parameter.Normal(
            name = self.name.intoMapPayloadAssignIdent(f),
            type = self.type?.intoMapPayloadTypeExpr(f),
            default = self.default?.intoMapPayloadExpr(f),
        )
        is Parameter.NoArgs -> Parameter.NoArgs()
        is Parameter.Slash -> Parameter.Slash()
        is Parameter.Args -> Parameter.Args(
            name = self.name.intoMapPayloadAssignIdent(f),
            type = self.type?.intoMapPayloadTypeExpr(f),
        )
        is Parameter.KwArgs -> Parameter.KwArgs(
            name = self.name.intoMapPayloadAssignIdent(f),
            type = self.type?.intoMapPayloadTypeExpr(f),
        )
    }
}

internal fun Argument.intoMapPayload(
    f: AstPayloadFunction,
): Argument {
    return when (val self = this) {
        is Argument.Positional -> Argument.Positional(self.expr.intoMapPayloadExpr(f))
        is Argument.Named -> Argument.Named(self.name, self.expr.intoMapPayloadExpr(f))
        is Argument.Args -> Argument.Args(self.expr.intoMapPayloadExpr(f))
        is Argument.KwArgs -> Argument.KwArgs(self.expr.intoMapPayloadExpr(f))
    }
}

internal fun Clause.intoMapPayload(
    f: AstPayloadFunction,
): Clause {
    return when (val self = this) {
        is Clause.For -> Clause.For(self.clause.intoMapPayload(f))
        is Clause.If -> Clause.If(self.cond.intoMapPayloadExpr(f))
    }
}

internal fun ForClause.intoMapPayload(
    f: AstPayloadFunction,
): ForClause {
    return ForClause(
        variable = this.variable.intoMapPayloadAssignTarget(f),
        over = this.over.intoMapPayloadExpr(f),
    )
}

internal fun FString.intoMapPayload(
    f: AstPayloadFunction,
): FString {
    return FString(
        format = this.format,
        expressions = this.expressions.map { it.intoMapPayloadExpr(f) },
    )
}

internal fun AstExpr.intoMapPayloadExpr(
    f: AstPayloadFunction,
): AstExpr = AstExpr(node = this.node.intoMapPayload(f), span = this.span)

internal fun AstTypeExpr.intoMapPayloadTypeExpr(
    f: AstPayloadFunction,
): AstTypeExpr = AstTypeExpr(node = this.node.intoMapPayload(f), span = this.span)

internal fun AstAssignTarget.intoMapPayloadAssignTarget(
    f: AstPayloadFunction,
): AstAssignTarget = AstAssignTarget(node = this.node.intoMapPayload(f), span = this.span)

internal fun AstAssignIdent.intoMapPayloadAssignIdent(
    f: AstPayloadFunction,
): AstAssignIdent = AstAssignIdent(node = this.node.intoMapPayload(f), span = this.span)

internal fun AstIdent.intoMapPayloadIdent(
    f: AstPayloadFunction,
): AstIdent = AstIdent(node = this.node.intoMapPayload(f), span = this.span)

internal fun AstParameter.intoMapPayloadParameter(
    f: AstPayloadFunction,
): AstParameter = AstParameter(node = this.node.intoMapPayload(f), span = this.span)

internal fun AstArgument.intoMapPayloadArgument(
    f: AstPayloadFunction,
): AstArgument = AstArgument(node = this.node.intoMapPayload(f), span = this.span)

internal fun AstStmt.intoMapPayloadStmt(
    f: AstPayloadFunction,
): AstStmt = AstStmt(node = this.node.intoMapPayload(f), span = this.span)

internal fun AstFString.intoMapPayloadFString(
    f: AstPayloadFunction,
): AstFString = AstFString(node = this.node.intoMapPayload(f), span = this.span)

