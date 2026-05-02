// port-lint: source src/diagnostic.rs
package io.github.kotlinmania.starlarksyntax.diagnostic

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

import io.github.kotlinmania.starlarksyntax.callstack.CallStack
import io.github.kotlinmania.starlarksyntax.codemap.CodeMap
import io.github.kotlinmania.starlarksyntax.codemap.FileSpan
import io.github.kotlinmania.starlarksyntax.codemap.Span
import io.github.kotlinmania.starlarksyntax.spandisplay.spanDisplay

/**
 * A value of type `T`, together with some diagnostic information.
 *
 * Most code in starlark should be using [io.github.kotlinmania.starlarksyntax.error.Error] as the error type.
 * However, some code may want to have strongly typed errors, while still being able to have diagnostics.
 * `WithDiagnostic<MyErrorType>` is the tool for that. `WithDiagnostic` is always one word in size,
 * and so can be used as an error type in performance sensitive code.
 *
 * `WithDiagnostic` is public, but only within the starlark crates, it's not a part of the API.
 *
 * Returning a `WithDiagnostic` value guarantees that a diagnostic is actually present, ie the
 * diagnostic is not optional.
 */
class WithDiagnostic<T> internal constructor(
    private val inner: WithDiagnosticInner<T>,
) {
    companion object {
        fun <T> newSpanned(t: T, span: Span, codemap: CodeMap): WithDiagnostic<T> {
            return WithDiagnostic(
                WithDiagnosticInner(
                    t = t,
                    diagnostic = Diagnostic(
                        span = codemap.fileSpan(span),
                        callStack = CallStack(),
                    ),
                )
            )
        }

        /**
         * The contract of this type is normally that it actually contains diagnostic information.
         * However, [io.github.kotlinmania.starlarksyntax.error.Error] doesn't guarantee that, but it'd be convenient to use this type
         * for it anyway. So we make an exception. Don't use this function for anything else.
         */
        internal fun <T> newEmpty(t: T): WithDiagnostic<T> {
            return WithDiagnostic(
                WithDiagnosticInner(
                    t = t,
                    diagnostic = Diagnostic(),
                )
            )
        }
    }

    fun inner(): T = inner.t

    fun intoInner(): T = inner.t

    fun <U> map(f: (T) -> U): WithDiagnostic<U> {
        return WithDiagnostic(
            WithDiagnosticInner(
                t = f(inner.t),
                diagnostic = inner.diagnostic,
            )
        )
    }

    fun span(): FileSpan? = inner.diagnostic.span

    fun callStack(): CallStack = inner.diagnostic.callStack

    /** Set the span, unless it's already been set. */
    fun setSpan(span: Span, codemap: CodeMap) {
        if (inner.diagnostic.span == null) {
            inner.diagnostic.span = codemap.fileSpan(span)
        }
    }

    /** Set the `callStack` field, unless it's already been set. */
    fun setCallStack(callStack: () -> CallStack) {
        if (inner.diagnostic.callStack.isEmpty()) {
            inner.diagnostic.callStack = callStack()
        }
    }

    internal val internalDiagnostic: Diagnostic
        get() = inner.diagnostic

    override fun toString(): String {
        // Not showing the context trace without alternate is the same thing that anyhow does
        val withContext = inner.t is Throwable && (inner.t as Throwable).cause != null
        val out = StringBuilder()
        diagnosticDisplay(this, false, out, withContext)
        return out.toString()
    }
}

internal class WithDiagnosticInner<T>(
    val t: T,
    var diagnostic: Diagnostic,
)

/** A description of where in starlark execution the error happened. */
internal class Diagnostic(
    /** Location where the error originated. */
    var span: FileSpan? = null,
    /** Call stack where the error originated. */
    var callStack: CallStack = CallStack(),
) {
    /** Gets annotated snippets for a [Diagnostic]. */
    fun getDisplayList(annotationLabel: String, color: Boolean): String {
        return spanDisplay(
            span?.asRef(),
            annotationLabel,
            color,
        )
    }
}

/////////////////////////////////////////////////////////////////////
// DISPLAY RELATED UTILITIES
// Since formatting these types is difficult, we reuse the Rust compiler
// variants by doing a conversion using annotate-snippets
// (https://github.com/rust-lang/annotate-snippets-rs)

internal fun <T> diagnosticDisplay(
    d: WithDiagnostic<T>,
    color: Boolean,
    f: StringBuilder,
    withContext: Boolean,
) {
    f.append(d.callStack().toString())
    val annotationLabel = d.inner().toString()
    // I set color to false here to make the comparison easier with tests (coloring
    // adds in pretty strange unicode chars).
    val displayList = d.internalDiagnostic.getDisplayList(annotationLabel, color)
    f.append(displayList).append('\n')
    // Print out the `Caused by:` trace (if exists) and rust backtrace (if enabled).
    // The trace printed comes from a [Throwable] that is not a [Diagnostic].
    if (withContext) {
        f.append("\n\n").append(d.inner().toString()).append('\n')
    }
}
