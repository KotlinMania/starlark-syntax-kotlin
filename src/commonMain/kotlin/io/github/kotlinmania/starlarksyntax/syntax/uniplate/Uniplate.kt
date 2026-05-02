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

import io.github.kotlinmania.starlarksyntax.syntax.ast.AssignTargetP
import io.github.kotlinmania.starlarksyntax.syntax.ast.AstAssignIdentP
import io.github.kotlinmania.starlarksyntax.syntax.ast.AstExprP
import io.github.kotlinmania.starlarksyntax.syntax.ast.AstIdentP
import io.github.kotlinmania.starlarksyntax.syntax.ast.AstPayload
import io.github.kotlinmania.starlarksyntax.syntax.ast.AstStmtP
import io.github.kotlinmania.starlarksyntax.syntax.ast.AstTypeExprP
import io.github.kotlinmania.starlarksyntax.syntax.ast.ClauseP
import io.github.kotlinmania.starlarksyntax.syntax.ast.ExprP
import io.github.kotlinmania.starlarksyntax.syntax.ast.ForClauseP
import io.github.kotlinmania.starlarksyntax.syntax.ast.LambdaP
import io.github.kotlinmania.starlarksyntax.syntax.ast.ParameterP
import io.github.kotlinmania.starlarksyntax.syntax.ast.StmtP
import io.github.kotlinmania.starlarksyntax.syntax.ast.TypeExprP

/**
 * One AST child being visited — either a statement or an expression. Mirrors upstream
 * `Visit<'a, P>`.
 */
sealed class Visit<P : AstPayload> {
    class Stmt<P : AstPayload>(val stmt: AstStmtP<P>) : Visit<P>()
    class Expr<P : AstPayload>(val expr: AstExprP<P>) : Visit<P>()

    fun visitChildren(f: (Visit<P>) -> Unit) {
        when (this) {
            is Stmt -> stmt.node.visitChildren(f)
            is Expr -> expr.node.visitExpr { x -> f(Expr(x)) }
        }
    }

    fun <E : Throwable> visitChildrenErr(f: (Visit<P>) -> kotlin.Result<Unit>): kotlin.Result<Unit> {
        return when (this) {
            is Stmt -> stmt.node.visitChildrenErr(f)
            is Expr -> expr.node.visitExprErr { x -> f(Expr(x)) }
        }
    }
}

/** Mutable visit variant; in Kotlin this carries the same shape as [Visit]. */
sealed class VisitMut<P : AstPayload> {
    class Stmt<P : AstPayload>(val stmt: AstStmtP<P>) : VisitMut<P>()
    class Expr<P : AstPayload>(val expr: AstExprP<P>) : VisitMut<P>()
}

// ----- DefP visit helpers (in Rust they're `impl<P: AstPayload> DefP<P>` methods; in Kotlin
// they're extension functions on the typealiased generic class) -----

private fun <P : AstPayload> io.github.kotlinmania.starlarksyntax.syntax.ast.DefP<P>.visitChildren(
    f: (Visit<P>) -> Unit,
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

fun <P : AstPayload> io.github.kotlinmania.starlarksyntax.syntax.ast.DefP<P>.visitChildrenErr(
    f: (Visit<P>) -> kotlin.Result<Unit>,
): kotlin.Result<Unit> {
    var result: kotlin.Result<Unit> = kotlin.Result.success(Unit)
    visitChildren { x ->
        if (result.isSuccess) {
            result = f(x)
        }
    }
    return result
}

// ----- StmtP visit helpers -----

fun <P : AstPayload> StmtP<P>.visitChildren(f: (Visit<P>) -> Unit) {
    when (val self = this) {
        is StmtP.Statements -> for (x in self.stmts) f(Visit.Stmt(x))
        is StmtP.If -> {
            f(Visit.Expr(self.cond))
            f(Visit.Stmt(self.suite))
        }
        is StmtP.IfElse -> {
            f(Visit.Expr(self.cond))
            f(Visit.Stmt(self.suite1))
            f(Visit.Stmt(self.suite2))
        }
        is StmtP.Def -> self.def.visitChildren(f)
        is StmtP.For -> {
            self.forStmt.variable.node.visitExpr { e -> f(Visit.Expr(e)) }
            f(Visit.Expr(self.forStmt.over))
            f(Visit.Stmt(self.forStmt.body))
        }
        // Nothing else contains nested statements
        is StmtP.Break -> {}
        is StmtP.Continue -> {}
        is StmtP.Pass -> {}
        is StmtP.Return -> {
            self.value?.let { f(Visit.Expr(it)) }
        }
        is StmtP.Expression -> f(Visit.Expr(self.expr))
        is StmtP.Assign -> {
            self.assign.lhs.node.visitExpr { e -> f(Visit.Expr(e)) }
            self.assign.ty?.let { it.node.visitExpr { e -> f(Visit.Expr(e)) } }
            f(Visit.Expr(self.assign.rhs))
        }
        is StmtP.AssignModify -> {
            self.lhs.node.visitExpr { e -> f(Visit.Expr(e)) }
            f(Visit.Expr(self.rhs))
        }
        is StmtP.Load -> {}
    }
}

fun <P : AstPayload> StmtP<P>.visitChildrenMut(f: (VisitMut<P>) -> Unit) {
    when (val self = this) {
        is StmtP.Statements -> for (x in self.stmts) f(VisitMut.Stmt(x))
        is StmtP.If -> {
            f(VisitMut.Expr(self.cond))
            f(VisitMut.Stmt(self.suite))
        }
        is StmtP.IfElse -> {
            f(VisitMut.Expr(self.cond))
            f(VisitMut.Stmt(self.suite1))
            f(VisitMut.Stmt(self.suite2))
        }
        is StmtP.Def -> {
            for (x in self.def.params) {
                x.node.visitExprMut { e -> f(VisitMut.Expr(e)) }
            }
            self.def.returnType?.let { it.node.visitExprMut { e -> f(VisitMut.Expr(e)) } }
            f(VisitMut.Stmt(self.def.body))
        }
        is StmtP.For -> {
            self.forStmt.variable.node.visitExprMut { e -> f(VisitMut.Expr(e)) }
            f(VisitMut.Expr(self.forStmt.over))
            f(VisitMut.Stmt(self.forStmt.body))
        }
        // Nothing else contains nested statements
        is StmtP.Break -> {}
        is StmtP.Continue -> {}
        is StmtP.Pass -> {}
        is StmtP.Return -> {
            self.value?.let { f(VisitMut.Expr(it)) }
        }
        is StmtP.Expression -> f(VisitMut.Expr(self.expr))
        is StmtP.Assign -> {
            self.assign.lhs.node.visitExprMut { e -> f(VisitMut.Expr(e)) }
            self.assign.ty?.let { it.node.visitExprMut { e -> f(VisitMut.Expr(e)) } }
            f(VisitMut.Expr(self.assign.rhs))
        }
        is StmtP.AssignModify -> {
            self.lhs.node.visitExprMut { e -> f(VisitMut.Expr(e)) }
            f(VisitMut.Expr(self.rhs))
        }
        is StmtP.Load -> {}
    }
}

fun <P : AstPayload> StmtP<P>.visitChildrenErr(
    f: (Visit<P>) -> kotlin.Result<Unit>,
): kotlin.Result<Unit> {
    var result: kotlin.Result<Unit> = kotlin.Result.success(Unit)
    visitChildren { x ->
        if (result.isSuccess) {
            result = f(x)
        }
    }
    return result
}

fun <P : AstPayload> StmtP<P>.visitChildrenErrMut(
    f: (VisitMut<P>) -> kotlin.Result<Unit>,
): kotlin.Result<Unit> {
    var result: kotlin.Result<Unit> = kotlin.Result.success(Unit)
    visitChildrenMut { x ->
        if (result.isSuccess) {
            result = f(x)
        }
    }
    return result
}

fun <P : AstPayload> StmtP<P>.visitStmt(f: (AstStmtP<P>) -> Unit) {
    visitChildren { x ->
        when (x) {
            is Visit.Stmt -> f(x.stmt)
            is Visit.Expr -> {} // Nothing to do
        }
    }
}

fun <P : AstPayload> StmtP<P>.visitStmtMut(f: (AstStmtP<P>) -> Unit) {
    visitChildrenMut { x ->
        when (x) {
            is VisitMut.Stmt -> f(x.stmt)
            is VisitMut.Expr -> {} // Nothing to do
        }
    }
}

fun <P : AstPayload> StmtP<P>.visitExpr(f: (AstExprP<P>) -> Unit) {
    // Note the &mut impl on f, it's subtle, see
    // https://stackoverflow.com/questions/54613966/error-reached-the-recursion-limit-while-instantiating-funcclosure
    fun pick(x: Visit<P>) {
        when (x) {
            is Visit.Stmt -> x.stmt.node.visitChildren(::pick)
            is Visit.Expr -> f(x.expr)
        }
    }
    visitChildren(::pick)
}

fun <P : AstPayload> StmtP<P>.visitExprMut(f: (AstExprP<P>) -> Unit) {
    fun pick(x: VisitMut<P>) {
        when (x) {
            is VisitMut.Stmt -> x.stmt.node.visitChildrenMut(::pick)
            is VisitMut.Expr -> f(x.expr)
        }
    }
    visitChildrenMut(::pick)
}

fun <P : AstPayload> StmtP<P>.visitExprResult(
    f: (AstExprP<P>) -> kotlin.Result<Unit>,
): kotlin.Result<Unit> {
    var result: kotlin.Result<Unit> = kotlin.Result.success(Unit)
    visitExpr { x ->
        if (result.isSuccess) {
            result = f(x)
        }
    }
    return result
}

fun <P : AstPayload> StmtP<P>.visitStmtResult(
    f: (AstStmtP<P>) -> kotlin.Result<Unit>,
): kotlin.Result<Unit> {
    var result: kotlin.Result<Unit> = kotlin.Result.success(Unit)
    visitStmt { x ->
        if (result.isSuccess) {
            result = f(x)
        }
    }
    return result
}

/** Visit all type expressions in this statement and its children. */
fun <P : AstPayload> StmtP<P>.visitTypeExprErrMut(
    f: (AstTypeExprP<P>) -> kotlin.Result<Unit>,
): kotlin.Result<Unit> {
    when (val self = this) {
        is StmtP.Def -> {
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
        is StmtP.Assign -> {
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

/** Visit all identifiers in read position recursively. */
fun <P : AstPayload> StmtP<P>.visitIdent(
    f: (AstIdentP<P>) -> kotlin.Result<Unit>,
): kotlin.Result<Unit> {
    return visitExprResult { expr -> expr.node.visitIdent(f) }
}

// ----- ParameterP helpers -----

/** Split a parameter into name, type, default value. */
fun <P : AstPayload> ParameterP<P>.split(): Triple<AstAssignIdentP<P>?, AstTypeExprP<P>?, AstExprP<P>?> {
    return when (val self = this) {
        is ParameterP.Normal -> Triple(self.name, self.type, self.default)
        is ParameterP.Args -> Triple(self.name, self.type, null)
        is ParameterP.KwArgs -> Triple(self.name, self.type, null)
        is ParameterP.NoArgs -> Triple(null, null, null)
        is ParameterP.Slash -> Triple(null, null, null)
    }
}

/** Split a parameter into name, type, default value (mutable variant). Same shape as [split] in Kotlin. */
fun <P : AstPayload> ParameterP<P>.splitMut(): Triple<AstAssignIdentP<P>?, AstTypeExprP<P>?, AstExprP<P>?> = split()

fun <P : AstPayload> ParameterP<P>.visitExpr(f: (AstExprP<P>) -> Unit) {
    val (_, typ, def) = split()
    if (typ != null) typ.node.visitExpr(f)
    if (def != null) f(def)
}

fun <P : AstPayload> ParameterP<P>.visitExprMut(f: (AstExprP<P>) -> Unit) {
    val (_, typ, def) = splitMut()
    if (typ != null) typ.node.visitExprMut(f)
    if (def != null) f(def)
}

// ----- ExprP helpers -----

fun <P : AstPayload> ExprP<P>.visitExpr(f: (AstExprP<P>) -> Unit) {
    when (val self = this) {
        is ExprP.Tuple -> for (x in self.elems) f(x)
        is ExprP.Dot -> f(self.target)
        is ExprP.Call -> {
            f(self.target)
            for (x in self.args.args) f(x.node.expr())
        }
        is ExprP.Index -> {
            f(self.target)
            f(self.index)
        }
        is ExprP.Index2 -> {
            f(self.target)
            f(self.index0)
            f(self.index1)
        }
        is ExprP.Slice -> {
            f(self.target)
            self.start?.let(f)
            self.stop?.let(f)
            self.step?.let(f)
        }
        is ExprP.Identifier -> {}
        is ExprP.Lambda -> {
            for (x in self.lambda.params) x.node.visitExpr(f)
            f(self.lambda.body)
        }
        is ExprP.Literal -> {}
        is ExprP.Not -> f(self.target)
        is ExprP.Minus -> f(self.target)
        is ExprP.Plus -> f(self.target)
        is ExprP.BitNot -> f(self.target)
        is ExprP.Op -> {
            f(self.left)
            f(self.right)
        }
        is ExprP.If -> {
            f(self.condition)
            f(self.v1)
            f(self.v2)
        }
        is ExprP.List -> for (x in self.elems) f(x)
        is ExprP.Dict -> for ((k, v) in self.entries) {
            f(k)
            f(v)
        }
        is ExprP.ListComprehension -> {
            self.firstFor.visitExpr(f)
            for (x in self.clauses) x.visitExpr(f)
            f(self.expr)
        }
        is ExprP.DictComprehension -> {
            self.firstFor.visitExpr(f)
            for (x in self.clauses) x.visitExpr(f)
            f(self.key)
            f(self.value)
        }
        is ExprP.FString -> {
            for (expr in self.fstring.node.expressions) {
                f(expr)
            }
        }
    }
}

/** Visit children expressions. */
fun <P : AstPayload> ExprP<P>.visitExprErr(
    f: (AstExprP<P>) -> kotlin.Result<Unit>,
): kotlin.Result<Unit> {
    var ok: kotlin.Result<Unit> = kotlin.Result.success(Unit)
    visitExpr { x ->
        if (ok.isSuccess) {
            ok = f(x)
        }
    }
    return ok
}

fun <P : AstPayload> ExprP<P>.visitExprErrMut(
    f: (AstExprP<P>) -> kotlin.Result<Unit>,
): kotlin.Result<Unit> {
    var ok: kotlin.Result<Unit> = kotlin.Result.success(Unit)
    visitExprMut { x ->
        if (ok.isSuccess) {
            ok = f(x)
        }
    }
    return ok
}

fun <P : AstPayload> ExprP<P>.visitExprMut(f: (AstExprP<P>) -> Unit) {
    // Same shape as visitExpr in Kotlin since `&mut` and `&` don't differ at the language level.
    visitExpr(f)
}

fun <P : AstPayload> ExprP<P>.visitTypeExprErrMut(
    f: (AstTypeExprP<P>) -> kotlin.Result<Unit>,
): kotlin.Result<Unit> {
    if (this is ExprP.Lambda) {
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

/** Visit all identifiers in read position recursively. */
fun <P : AstPayload> ExprP<P>.visitIdent(
    f: (AstIdentP<P>) -> kotlin.Result<Unit>,
): kotlin.Result<Unit> {
    if (this is ExprP.Identifier) {
        val r = f(this.ident)
        if (r.isFailure) return r
    }
    return visitExprErr { expr -> expr.node.visitIdent(f) }
}

// ----- TypeExprP helpers -----

fun <P : AstPayload> TypeExprP<P>.visitExpr(f: (AstExprP<P>) -> Unit) {
    f(this.expr)
}

fun <P : AstPayload> TypeExprP<P>.visitExprMut(f: (AstExprP<P>) -> Unit) {
    f(this.expr)
}

// ----- AssignTargetP helpers -----

fun <P : AstPayload> AssignTargetP<P>.visitExpr(f: (AstExprP<P>) -> Unit) {
    fun recurse(x: AssignTargetP<P>) {
        when (x) {
            is AssignTargetP.Tuple -> for (y in x.elems) recurse(y.node)
            is AssignTargetP.Dot -> f(x.target)
            is AssignTargetP.Index -> {
                f(x.target)
                f(x.index)
            }
            is AssignTargetP.Identifier -> {}
        }
    }
    recurse(this)
}

fun <P : AstPayload> AssignTargetP<P>.visitExprMut(f: (AstExprP<P>) -> Unit) = visitExpr(f)

/**
 * Assuming this expression was on the left-hand-side of an assignment,
 * visit all the names that are bound by this assignment.
 * Note that assignments like `x[i] = n` don't bind any names.
 */
fun <P : AstPayload> AssignTargetP<P>.visitLvalue(f: (AstAssignIdentP<P>) -> Unit) {
    fun recurse(x: AssignTargetP<P>) {
        when (x) {
            is AssignTargetP.Identifier -> f(x.ident)
            is AssignTargetP.Tuple -> for (y in x.elems) recurse(y.node)
            else -> {}
        }
    }
    recurse(this)
}

fun <P : AstPayload> AssignTargetP<P>.visitLvalueMut(f: (AstAssignIdentP<P>) -> Unit) = visitLvalue(f)

// ----- ForClauseP helpers -----

fun <P : AstPayload> ForClauseP<P>.visitExpr(f: (AstExprP<P>) -> Unit) {
    this.variable.node.visitExpr(f)
    f(this.over)
}

fun <P : AstPayload> ForClauseP<P>.visitExprMut(f: (AstExprP<P>) -> Unit) {
    this.variable.node.visitExprMut(f)
    f(this.over)
}

// ----- ClauseP helpers -----

fun <P : AstPayload> ClauseP<P>.visitExpr(f: (AstExprP<P>) -> Unit) {
    when (val self = this) {
        is ClauseP.For -> self.clause.visitExpr(f)
        is ClauseP.If -> f(self.cond)
    }
}

fun <P : AstPayload> ClauseP<P>.visitExprMut(f: (AstExprP<P>) -> Unit) = visitExpr(f)
