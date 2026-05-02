// port-lint: source src/call_stack.rs
package io.github.kotlinmania.starlarksyntax.callstack

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

//! Starlark call stack.

import io.github.kotlinmania.starlarksyntax.frame.Frame

const val CALL_STACK_TRACEBACK_PREFIX: String = "Traceback (most recent call last):"

/** Owned call stack. */
data class CallStack(
    /** The frames. */
    val frames: MutableList<Frame> = mutableListOf(),
) {
    /** Is the call stack empty? */
    fun isEmpty(): Boolean {
        return frames.isEmpty()
    }

    /** Take the contained frames. */
    fun intoFrames(): MutableList<Frame> {
        return frames
    }

    override fun toString(): String {
        if (frames.isEmpty()) {
            return ""
        }
        // Match Python output.
        val out = StringBuilder()
        out.append(CALL_STACK_TRACEBACK_PREFIX).append('\n')
        // TODO(nga): use real module name.
        var prev = "<module>"
        for (x in frames) {
            x.writeTwoLines("  ", prev, out)
            prev = x.name
        }
        return out.toString()
    }
}
