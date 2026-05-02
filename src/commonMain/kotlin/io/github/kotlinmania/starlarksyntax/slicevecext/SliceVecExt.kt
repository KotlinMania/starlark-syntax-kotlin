// port-lint: source src/slice_vec_ext.rs
package io.github.kotlinmania.starlarksyntax.slicevecext

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

/**
 * Optimised collect iterator into List, which might be a Result.
 *
 * If we do a standard .toList() on the iterator it will never have a good size hint,
 * as the lower bound will always be zero, so might reallocate several times.
 * We know the List will either be thrown away, or exactly `len`, so aim if we do allocate,
 * make sure it is at `len`. However, if the first element throws an error, we don't need
 * to allocate at all, so special case that.
 */
private fun <T> collectResult(it: Iterator<Result<T>>): Result<List<T>> {
    if (!it.hasNext()) return Result.success(emptyList())
    val first = it.next()
    if (first.isFailure) return Result.failure(first.exceptionOrNull()!!)
    val res = ArrayList<T>()
    res.add(first.getOrThrow())
    while (it.hasNext()) {
        val r = it.next()
        if (r.isFailure) return Result.failure(r.exceptionOrNull()!!)
        res.add(r.getOrThrow())
    }
    return Result.success(res)
}

/** A shorthand for `map(f).toList()`. */
fun <T, B> List<T>.mapToList(f: (T) -> B): List<B> {
    return this.map(f)
}

/** A shorthand for `map(f).toList(): Result<List<B>>`. */
fun <T, B> List<T>.tryMap(f: (T) -> Result<B>): Result<List<B>> {
    return collectResult(this.map(f).iterator())
}

/** A shorthand for moving each element through `f` and collecting into a new List. */
fun <T, B> List<T>.intoMap(f: (T) -> B): List<B> {
    return this.map(f)
}

/** A shorthand for moving each element through fallible `f` and collecting into a Result<List>. */
fun <T, B> List<T>.intoTryMap(f: (T) -> Result<B>): Result<List<B>> {
    return collectResult(this.map(f).iterator())
}
