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
 * Optimised collect iterator into a list, which might be a [Result].
 *
 * If we do a standard `.map { }.toList()` on the iterator it will never have a good size hint,
 * as the lower bound will always be zero, so might reallocate several times.
 * We know the list will either be thrown away, or exactly `len`, so aim if we do allocate,
 * make sure it is at `len`. However, if the first element throws an error, we don't need
 * to allocate at all, so special case that.
 */
private fun <T> collectResult(
    it: Iterator<Result<T>>,
    len: Int,
): Result<List<T>> {
    if (!it.hasNext()) return Result.success(emptyList())

    val first = it.next()
    if (first.isFailure) return Result.failure(first.exceptionOrNull()!!)

    val res = ArrayList<T>(len)
    res.add(first.getOrThrow())
    while (it.hasNext()) {
        val next = it.next()
        if (next.isFailure) return Result.failure(next.exceptionOrNull()!!)
        res.add(next.getOrThrow())
    }
    return Result.success(res)
}

/**
 * Extension traits on slices/lists.
 *
 * Kotlin already has `map`, but we keep these helpers so transliterations can mirror the
 * upstream call sites and, when we know the size up front, allocate once.
 */
fun <T, B> List<T>.mapExt(f: (T) -> B): List<B> {
    val res = ArrayList<B>(size)
    for (x in this) {
        res.add(f(x))
    }
    return res
}

/**
 * A shorthand for `mapExt(f)` where the mapping function returns a [Result].
 *
 * This mirrors upstream `try_map`, collapsing the first failure without allocating the full list.
 */
fun <T, B> List<T>.tryMapExt(f: (T) -> Result<B>): Result<List<B>> {
    val base = iterator()
    val mapped =
        object : Iterator<Result<B>> {
            override fun hasNext(): Boolean = base.hasNext()

            override fun next(): Result<B> = f(base.next())
        }
    return collectResult(mapped, size)
}

/**
 * Extension traits on lists acting as `Vec`.
 *
 * Kotlin doesn't have move semantics, but keeping the "into_*" names preserves the upstream
 * structure in transliterations.
 */
fun <T, B> List<T>.intoMapExt(f: (T) -> B): List<B> = mapExt(f)

fun <T, B> List<T>.intoTryMapExt(f: (T) -> Result<B>): Result<List<B>> = tryMapExt(f)
