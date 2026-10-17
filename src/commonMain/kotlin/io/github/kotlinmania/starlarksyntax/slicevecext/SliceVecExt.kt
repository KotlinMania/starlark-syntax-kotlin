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
 * Extension traits on slices.
 */
internal interface SliceExt<T> {
    /**
     * A shorthand for mapping an iterator and collecting to a list.
     */
    fun <B> map(f: (T) -> B): List<B>

    /**
     * A shorthand for mapping an iterator and collecting results to a list.
     */
    fun <B> tryMap(f: (T) -> Result<B>): Result<List<B>>
}

/**
 * A shorthand for mapping a list returning results.
 */
fun <T, B> List<T>.tryMap(f: (T) -> Result<B>): Result<List<B>> {
    val base = iterator()
    val mapped =
        object : Iterator<Result<B>> {
            override fun hasNext(): Boolean = base.hasNext()

            override fun next(): Result<B> {
                if (!hasNext()) throw NoSuchElementException()
                return f(base.next())
            }
        }
    return collectResult(mapped, size)
}

/**
 * Extension traits on lists.
 */
internal interface VecExt<T> {
    /**
     * A shorthand for consuming a list and mapping elements.
     */
    fun <B> intoMap(f: (T) -> B): List<B>

    /**
     * A shorthand for consuming a list and mapping elements with fallible results.
     */
    fun <B> intoTryMap(f: (T) -> Result<B>): Result<List<B>>
}

/**
 * A shorthand for consuming a list and mapping elements.
 */
fun <T, B> List<T>.intoMap(f: (T) -> B): List<B> = this.map(f)

/**
 * A shorthand for consuming a list and mapping elements with fallible results.
 */
fun <T, B> List<T>.intoTryMap(f: (T) -> Result<B>): Result<List<B>> = tryMap(f)
