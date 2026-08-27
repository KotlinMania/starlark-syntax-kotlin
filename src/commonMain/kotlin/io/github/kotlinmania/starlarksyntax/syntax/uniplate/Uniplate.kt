// port-lint: source src/syntax/uniplate.rs
package io.github.kotlinmania.starlarksyntax.syntax.uniplate

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

import io.github.kotlinmania.starlarksyntax.syntax.ast.AssignTarget
import io.github.kotlinmania.starlarksyntax.syntax.ast.AstAssignIdent
import io.github.kotlinmania.starlarksyntax.syntax.ast.AstExpr
import io.github.kotlinmania.starlarksyntax.syntax.ast.AstIdent
import io.github.kotlinmania.starlarksyntax.syntax.ast.AstStmt
import io.github.kotlinmania.starlarksyntax.syntax.ast.AstTypeExpr
import io.github.kotlinmania.starlarksyntax.syntax.ast.Clause
import io.github.kotlinmania.starlarksyntax.syntax.ast.Def
import io.github.kotlinmania.starlarksyntax.syntax.ast.Expr
import io.github.kotlinmania.starlarksyntax.syntax.ast.ForClause
import io.github.kotlinmania.starlarksyntax.syntax.ast.Parameter
import io.github.kotlinmania.starlarksyntax.syntax.ast.Stmt
import io.github.kotlinmania.starlarksyntax.syntax.ast.TypeExpr

/**
 * One AST child being visited — either a statement or an expression. Mirrors upstream
 * `Visit<'a, P>`.
 */
internal sealed class Visit {
    class Stmt(val stmt: AstStmt) : Visit()
    class Expr(val expr: AstExpr) : Visit()

    fun visitChildren(f: (Visit) -> Unit) {
        when (this) {
            is Stmt -> stmt.node.visitChildren(f)
            is Expr -> expr.node.visitExpr { x -> f(Expr(x)) }
        }
    }

    fun <E : Throwable> visitChildrenErr(f: (Visit) -> kotlin.Result<Unit>): kotlin.Result<Unit> {
        return when (this) {
            is Stmt -> stmt.node.visitChildrenErr(f)
            is Expr -> expr.node.visitExprErr { x -> f(Expr(x)) }
        }
    }
}

/** Mutable visit variant; in Kotlin this carries the same shape as [Visit]. */
internal sealed class VisitMut {
    class Stmt(val stmt: AstStmt) : VisitMut()
    class Expr(val expr: AstExpr) : VisitMut()
}

// ----- Def visit helpers -----

private fun Def.visitChildren(
    f: (Visit) -> Unit,
) {
    for (x in this.params) {
        x.node.visitExpr { e -> f(Visit.Expr(e)) }
    }
    val rt = this.returnType
    if (rt != null) {
        rt.node.visitExpr { e -> f(Visit.Expr(e)) }
    }
    f(Visit.Stmt(this.body))
}

internal fun Def.visitChildrenErr(
    f: (Visit) -> kotlin.Result<Unit>,
): kotlin.Result<Unit> {
    var result: kotlin.Result<Unit> = kotlin.Result.success(Unit)
    visitChildren { x ->
        if (result.isSuccess) {
            result = f(x)
        }
    }
    return result
}

// ----- Stmt visit helpers -----

internal fun Stmt.visitChildren(f: (Visit) -> Unit) {
    when (val self = this) {
        is Stmt.Statements -> for (x in self.stmts) f(Visit.Stmt(x))
        is Stmt.If -> {
            f(Visit.Expr(self.cond))
            f(Visit.Stmt(self.suite))
        }
        is Stmt.IfElse -> {
            f(Visit.Expr(self.cond))
            f(Visit.Stmt(self.suite1))
            f(Visit.Stmt(self.suite2))
        }
        is Stmt.Def -> self.def.visitChildren(f)
        is Stmt.For -> {
            self.forStmt.variable.node.visitExpr { e -> f(Visit.Expr(e)) }
            f(Visit.Expr(self.forStmt.over))
            f(Visit.Stmt(self.forStmt.body))
        }
        is Stmt.Break -> {}
        is Stmt.Continue -> {}
        is Stmt.Pass -> {}
        is Stmt.Return -> {
            self.value?.let { f(Visit.Expr(it)) }
        }
        is Stmt.Expression -> f(Visit.Expr(self.expr))
        is Stmt.Assign -> {
            self.assign.lhs.node.visitExpr { e -> f(Visit.Expr(e)) }
            self.assign.ty?.let { it.node.visitExpr { e -> f(Visit.Expr(e)) } }
            f(Visit.Expr(self.assign.rhs))
        }
        is Stmt.AssignModify -> {
            self.lhs.node.visitExpr { e -> f(Visit.Expr(e)) }
            f(Visit.Expr(self.rhs))
        }
        is Stmt.Load -> {}
    }
}

internal fun Stmt.visitChildrenMut(f: (VisitMut) -> Unit) {
    when (val self = this) {
        is Stmt.Statements -> for (x in self.stmts) f(VisitMut.Stmt(x))
        is Stmt.If -> {
            f(VisitMut.Expr(self.cond))
            f(VisitMut.Stmt(self.suite))
        }
        is Stmt.IfElse -> {
            f(VisitMut.Expr(self.cond))
            f(VisitMut.Stmt(self.suite1))
            f(VisitMut.Stmt(self.suite2))
        }
        is Stmt.Def -> {
            for (x in self.def.params) {
                x.node.visitExprMut { e -> f(VisitMut.Expr(e)) }
            }
            self.def.returnType?.let { it.node.visitExprMut { e -> f(VisitMut.Expr(e)) } }
            f(VisitMut.Stmt(self.def.body))
        }
        is Stmt.For -> {
            self.forStmt.variable.node.visitExprMut { e -> f(VisitMut.Expr(e)) }
            f(VisitMut.Expr(self.forStmt.over))
            f(VisitMut.Stmt(self.forStmt.body))
        }
        is Stmt.Break -> {}
        is Stmt.Continue -> {}
        is Stmt.Pass -> {}
        is Stmt.Return -> {
            self.value?.let { f(VisitMut.Expr(it)) }
        }
        is Stmt.Expression -> f(VisitMut.Expr(self.expr))
        is Stmt.Assign -> {
            self.assign.lhs.node.visitExprMut { e -> f(VisitMut.Expr(e)) }
            self.assign.ty?.let { it.node.visitExprMut { e -> f(VisitMut.Expr(e)) } }
            f(VisitMut.Expr(self.assign.rhs))
        }
        is Stmt.AssignModify -> {
            self.lhs.node.visitExprMut { e -> f(VisitMut.Expr(e)) }
            f(VisitMut.Expr(self.rhs))
        }
        is Stmt.Load -> {}
    }
}

internal fun Stmt.visitChildrenErr(
    f: (Visit) -> kotlin.Result<Unit>,
): kotlin.Result<Unit> {
    var result: kotlin.Result<Unit> = kotlin.Result.success(Unit)
    visitChildren { x ->
        if (result.isSuccess) {
            result = f(x)
        }
    }
    return result
}

internal fun Stmt.visitChildrenErrMut(
    f: (VisitMut) -> kotlin.Result<Unit>,
): kotlin.Result<Unit> {
    var result: kotlin.Result<Unit> = kotlin.Result.success(Unit)
    visitChildrenMut { x ->
        if (result.isSuccess) {
            result = f(x)
        }
    }
    return result
}

internal fun Stmt.visitStmt(f: (AstStmt) -> Unit) {
    visitChildren { x ->
        when (x) {
            is Visit.Stmt -> f(x.stmt)
            is Visit.Expr -> {}
        }
    }
}

internal fun Stmt.visitStmtMut(f: (AstStmt) -> Unit) {
    visitChildrenMut { x ->
        when (x) {
            is VisitMut.Stmt -> f(x.stmt)
            is VisitMut.Expr -> {}
        }
    }
}

internal fun Stmt.visitExpr(f: (AstExpr) -> Unit) {
    fun pick(x: Visit) {
        when (x) {
            is Visit.Stmt -> x.stmt.node.visitChildren(::pick)
            is Visit.Expr -> f(x.expr)
        }
    }
    visitChildren(::pick)
}

internal fun AstStmt.visitExpr(f: (AstExpr) -> Unit) {
    node.visitExpr(f)
}

internal fun Stmt.visitExprMut(f: (AstExpr) -> Unit) {
    fun pick(x: VisitMut) {
        when (x) {
            is VisitMut.Stmt -> x.stmt.node.visitChildrenMut(::pick)
            is VisitMut.Expr -> f(x.expr)
        }
    }
    visitChildrenMut(::pick)
}

internal fun Stmt.visitExprResult(
    f: (AstExpr) -> kotlin.Result<Unit>,
): kotlin.Result<Unit> {
    var result: kotlin.Result<Unit> = kotlin.Result.success(Unit)
    visitExpr { x ->
        if (result.isSuccess) {
            result = f(x)
        }
    }
    return result
}

internal fun Stmt.visitStmtResult(
    f: (AstStmt) -> kotlin.Result<Unit>,
): kotlin.Result<Unit> {
    var result: kotlin.Result<Unit> = kotlin.Result.success(Unit)
    visitStmt { x ->
        if (result.isSuccess) {
            result = f(x)
        }
    }
    return result
}

internal fun Stmt.visitTypeExprErrMut(
    f: (AstTypeExpr) -> kotlin.Result<Unit>,
): kotlin.Result<Unit> {
    when (val self = this) {
        is Stmt.Def -> {
            for (param in self.def.params) {
                val (_, ty, _) = param.node.splitMut()
                if (ty != null) {
                    val r = f(ty)
                    if (r.isFailure) return r
                }
            }
            val rt = self.def.returnType
            if (rt != null) {
                val r = f(rt)
                if (r.isFailure) return r
            }
        }
        is Stmt.Assign -> {
            val ty = self.assign.ty
            if (ty != null) {
                val r = f(ty)
                if (r.isFailure) return r
            }
        }
        else -> {}
    }
    return visitChildrenErrMut { visit ->
        when (visit) {
            is VisitMut.Stmt -> visit.stmt.node.visitTypeExprErrMut(f)
            is VisitMut.Expr -> visit.expr.node.visitTypeExprErrMut(f)
        }
    }
}

internal fun Stmt.visitIdent(
    f: (AstIdent) -> kotlin.Result<Unit>,
): kotlin.Result<Unit> {
    return visitExprResult { expr -> expr.node.visitIdent(f) }
}

// ----- Parameter helpers -----

internal fun Parameter.split(): Triple<AstAssignIdent?, AstTypeExpr?, AstExpr?> {
    return when (val self = this) {
        is Parameter.Normal -> Triple(self.name, self.type, self.default)
        is Parameter.Args -> Triple(self.name, self.type, null)
        is Parameter.KwArgs -> Triple(self.name, self.type, null)
        is Parameter.NoArgs -> Triple(null, null, null)
        is Parameter.Slash -> Triple(null, null, null)
    }
}

internal fun Parameter.splitMut(): Triple<AstAssignIdent?, AstTypeExpr?, AstExpr?> = split()

internal fun Parameter.visitExpr(f: (AstExpr) -> Unit) {
    val (_, typ, def) = split()
    if (typ != null) typ.node.visitExpr(f)
    if (def != null) f(def)
}

internal fun Parameter.visitExprMut(f: (AstExpr) -> Unit) {
    val (_, typ, def) = splitMut()
    if (typ != null) typ.node.visitExprMut(f)
    if (def != null) f(def)
}

// ----- Expr helpers -----

internal fun Expr.visitExpr(f: (AstExpr) -> Unit) {
    when (val self = this) {
        is Expr.Tuple -> for (x in self.elems) f(x)
        is Expr.Dot -> f(self.target)
        is Expr.Call -> {
            f(self.target)
            for (x in self.args.args) f(x.node.expr())
        }
        is Expr.Index -> {
            f(self.target)
            f(self.index)
        }
        is Expr.Index2 -> {
            f(self.target)
            f(self.index0)
            f(self.index1)
        }
        is Expr.Slice -> {
            f(self.target)
            self.start?.let(f)
            self.stop?.let(f)
            self.step?.let(f)
        }
        is Expr.Identifier -> {}
        is Expr.Lambda -> {
            for (x in self.lambda.params) x.node.visitExpr(f)
            f(self.lambda.body)
        }
        is Expr.Literal -> {}
        is Expr.Not -> f(self.target)
        is Expr.Minus -> f(self.target)
        is Expr.Plus -> f(self.target)
        is Expr.BitNot -> f(self.target)
        is Expr.Op -> {
            f(self.left)
            f(self.right)
        }
        is Expr.If -> {
            f(self.condition)
            f(self.v1)
            f(self.v2)
        }
        is Expr.List -> for (x in self.elems) f(x)
        is Expr.Dict -> for ((k, v) in self.entries) {
            f(k)
            f(v)
        }
        is Expr.ListComprehension -> {
            self.firstFor.visitExpr(f)
            for (x in self.clauses) x.visitExpr(f)
            f(self.expr)
        }
        is Expr.DictComprehension -> {
            self.firstFor.visitExpr(f)
            for (x in self.clauses) x.visitExpr(f)
            f(self.key)
            f(self.value)
        }
        is Expr.FString -> {
            for (expr in self.fstring.node.expressions) {
                f(expr)
            }
        }
    }
}

internal fun Expr.visitExprErr(
    f: (AstExpr) -> kotlin.Result<Unit>,
): kotlin.Result<Unit> {
    var ok: kotlin.Result<Unit> = kotlin.Result.success(Unit)
    visitExpr { x ->
        if (ok.isSuccess) {
            ok = f(x)
        }
    }
    return ok
}

internal fun Expr.visitExprErrMut(
    f: (AstExpr) -> kotlin.Result<Unit>,
): kotlin.Result<Unit> {
    var ok: kotlin.Result<Unit> = kotlin.Result.success(Unit)
    visitExprMut { x ->
        if (ok.isSuccess) {
            ok = f(x)
        }
    }
    return ok
}

internal fun Expr.visitExprMut(f: (AstExpr) -> Unit) {
    visitExpr(f)
}

internal fun Expr.visitTypeExprErrMut(
    f: (AstTypeExpr) -> kotlin.Result<Unit>,
): kotlin.Result<Unit> {
    if (this is Expr.Lambda) {
        for (param in this.lambda.params) {
            val (_, ty, _) = param.node.splitMut()
            if (ty != null) {
                val r = f(ty)
                if (r.isFailure) return r
            }
        }
    }
    return visitExprErrMut { expr -> expr.node.visitTypeExprErrMut(f) }
}

internal fun Expr.visitIdent(
    f: (AstIdent) -> kotlin.Result<Unit>,
): kotlin.Result<Unit> {
    if (this is Expr.Identifier) {
        val r = f(this.ident)
        if (r.isFailure) return r
    }
    return visitExprErr { expr -> expr.node.visitIdent(f) }
}

// ----- TypeExpr helpers -----

internal fun TypeExpr.visitExpr(f: (AstExpr) -> Unit) {
    f(this.expr)
}

internal fun TypeExpr.visitExprMut(f: (AstExpr) -> Unit) {
    f(this.expr)
}

// ----- AssignTarget helpers -----

internal fun AssignTarget.visitExpr(f: (AstExpr) -> Unit) {
    fun recurse(x: AssignTarget) {
        when (x) {
            is AssignTarget.Tuple -> for (y in x.elems) recurse(y.node)
            is AssignTarget.Dot -> f(x.target)
            is AssignTarget.Index -> {
                f(x.target)
                f(x.index)
            }
            is AssignTarget.Identifier -> {}
        }
    }
    recurse(this)
}

internal fun AssignTarget.visitExprMut(f: (AstExpr) -> Unit) = visitExpr(f)

internal fun AssignTarget.visitLvalue(f: (AstAssignIdent) -> Unit) {
    fun recurse(x: AssignTarget) {
        when (x) {
            is AssignTarget.Identifier -> f(x.ident)
            is AssignTarget.Tuple -> for (y in x.elems) recurse(y.node)
            else -> {}
        }
    }
    recurse(this)
}

internal fun AssignTarget.visitLvalueMut(f: (AstAssignIdent) -> Unit) = visitLvalue(f)

// ----- ForClause helpers -----

internal fun ForClause.visitExpr(f: (AstExpr) -> Unit) {
    this.variable.node.visitExpr(f)
    f(this.over)
}

internal fun ForClause.visitExprMut(f: (AstExpr) -> Unit) {
    this.variable.node.visitExprMut(f)
    f(this.over)
}

// ----- Clause helpers -----

internal fun Clause.visitExpr(f: (AstExpr) -> Unit) {
    when (val self = this) {
        is Clause.For -> self.clause.visitExpr(f)
        is Clause.If -> f(self.cond)
    }
}

internal fun Clause.visitExprMut(f: (AstExpr) -> Unit) = visitExpr(f)

