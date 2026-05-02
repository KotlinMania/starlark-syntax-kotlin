// port-lint: source src/convert_indices.rs
package io.github.kotlinmania.starlarksyntax.convertindices

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

private fun bound(value: Int, limit: Int): Int {
    return if (value <= 0) {
        0
    } else if (value >= limit) {
        limit
    } else {
        value
    }
}

fun convertIndices(len: Int, start: Int?, end: Int?): Pair<Int, Int> {
    val s = start ?: 0
    val e = end ?: len
    val eAdj = if (e < 0) e + len else e
    val sAdj = if (s < 0) s + len else s
    return Pair(bound(sAdj, len), bound(eAdj, len))
}

fun convertIndex(len: Int, start: Int): Int {
    val sAdj = if (start < 0) start + len else start
    return bound(sAdj, len)
}
