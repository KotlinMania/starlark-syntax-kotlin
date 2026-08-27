// port-lint: source src/syntax/top_level_stmts.rs
package io.github.kotlinmania.starlarksyntax.syntax.toplevelstmts

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

import io.github.kotlinmania.starlarksyntax.syntax.ast.AstStmt
import io.github.kotlinmania.starlarksyntax.syntax.ast.Stmt

/** List the top-level statements in the AST. */
internal fun topLevelStmts(top: AstStmt): List<AstStmt> {
    fun f(ast: AstStmt, res: MutableList<AstStmt>) {
        when (val node = ast.node) {
            is Stmt.Statements -> {
                for (x in node.stmts) {
                    f(x, res)
                }
            }
            else -> res.add(ast)
        }
    }

    val res = mutableListOf<AstStmt>()
    f(top, res)
    return res
}

/** List the top-level statements in the AST. */
internal fun topLevelStmtsMut(top: AstStmt): List<AstStmt> {
    // In Rust this returns `Vec<&mut AstStmtP<P>>`; the Kotlin port returns the same shape as
    // [topLevelStmts] because Kotlin doesn't distinguish `&mut` from `&` — mutation goes through
    // the returned references regardless.
    fun f(ast: AstStmt, res: MutableList<AstStmt>) {
        when (val node = ast.node) {
            is Stmt.Statements -> {
                for (x in node.stmts) {
                    f(x, res)
                }
            }
            else -> res.add(ast)
        }
    }

    val res = mutableListOf<AstStmt>()
    f(top, res)
    return res
}

