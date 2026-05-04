// port-lint: source src/error.rs
package io.github.kotlinmania.starlarksyntax.error

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
import io.github.kotlinmania.starlarksyntax.diagnostic.WithDiagnostic
import io.github.kotlinmania.starlarksyntax.diagnostic.diagnosticDisplay

/**
 * An error produced by starlark.
 *
 * This error is composed of an error kind, together with some diagnostic information indicating
 * where it occurred.
 *
 * In order to prevent accidental conversions to a generic [Throwable], this type intentionally does not
 * extend [Exception]. That should probably change in the future.
 */
class Error internal constructor(
    internal val inner: WithDiagnostic<ErrorKind>,
) {
    companion object {
        /** Create a new error. */
        fun newKind(kind: ErrorKind): Error {
            return Error(WithDiagnostic.newEmpty(kind))
        }

        /** Create a new error with a span. */
        fun newSpanned(kind: ErrorKind, span: Span, codemap: CodeMap): Error {
            return Error(WithDiagnostic.newSpanned(kind, span, codemap))
        }

        /** Create a new error with no diagnostic and of kind [ErrorKind.Other]. */
        fun newOther(e: Throwable): Error {
            return Error(WithDiagnostic.newEmpty(ErrorKind.Other(e)))
        }

        /** Create a new error with no diagnostic and of kind [ErrorKind.Native]. */
        fun newNative(e: Throwable): Error {
            return Error(WithDiagnostic.newEmpty(ErrorKind.Native(e)))
        }

        /** Create a new error with no diagnostic and of kind [ErrorKind.Value]. */
        fun newValue(e: Throwable): Error {
            return Error(WithDiagnostic.newEmpty(ErrorKind.Value(e)))
        }

        /** Construct an [Error] from a generic [Throwable]. */
        fun from(e: Throwable): Error {
            return Error(WithDiagnostic.newEmpty(ErrorKind.Other(e)))
        }
    }

    /** The kind of this error. */
    fun kind(): ErrorKind = inner.inner()

    /** Convert the error into the underlying kind. */
    fun intoKind(): ErrorKind = inner.intoInner()

    fun hasDiagnostic(): Boolean {
        return inner.span() != null || !inner.callStack().isEmpty()
    }

    /** Convert this error into a generic [Throwable]. */
    fun intoAnyhow(): Throwable {
        val self = this
        return object : Exception(self.toString()) {
            override fun toString(): String = self.toString()
            override val cause: Throwable?
                get() = self.kind().source()
        }
    }

    /**
     * Returns a value that can be used to format this error without including the diagnostic
     * information.
     *
     * This is the same as [kind], just a bit more explicit.
     */
    fun withoutDiagnostic(): ErrorKind = inner.inner()

    fun span(): FileSpan? = inner.span()

    fun callStack(): CallStack = inner.callStack()

    /** Set the span, unless it's already been set. */
    fun setSpan(span: Span, codemap: CodeMap) {
        inner.setSpan(span, codemap)
    }

    /** Set the `callStack` field, unless it's already been set. */
    fun setCallStack(callStack: () -> CallStack) {
        inner.setCallStack(callStack)
    }

    /**
     * Print an error to the stderr stream. If the error has diagnostic information it will use
     * color-codes when printing.
     *
     * Note that this function doesn't print any context information if the error is a diagnostic,
     * so you might prefer to use the alternate-formatted toString if you suspect there is useful context
     * (although you won't get pretty colors).
     */
    fun eprint() {
        if (hasDiagnostic()) {
            val stderr = StringBuilder()
            diagnosticDisplay(inner, true, stderr, true)
            // In Kotlin Multiplatform there is no portable stderr; the caller may route this string.
            // The original Rust uses `eprint!`, which is platform-specific. Here we print to stderr-equivalent.
            kotlin.io.println(stderr.toString())
        } else {
            kotlin.io.println(this.toString())
        }
    }

    /** Change error kind to internal error. */
    fun intoInternalError(): Error {
        return if (kind() is ErrorKind.Internal) {
            this
        } else {
            Error(inner.map { ek -> ek.intoInternalError() })
        }
    }

    override fun toString(): String = fmtImpl(this, isDebug = false, alternate = false)
}

private fun fmtImpl(self: Error, isDebug: Boolean, alternate: Boolean): String {
    return if (self.hasDiagnostic()) {
        // Not showing the context trace without `{:#}` or `{:?}` is the same thing that anyhow does
        val withContext = (alternate || isDebug) && self.kind().source() != null
        val out = StringBuilder()
        diagnosticDisplay(self.inner, false, out, withContext)
        out.toString()
    } else {
        self.withoutDiagnostic().toString()
    }
}

/** The different kinds of errors that can be produced by starlark. */
sealed class ErrorKind {
    /** An explicit `fail` invocation. */
    data class Fail(val error: Throwable) : ErrorKind()

    /** Starlark call stack overflow. */
    data class StackOverflow(val error: Throwable) : ErrorKind()

    /**
     * An error approximately associated with a value.
     *
     * Includes unsupported operations, missing attributes, things of that sort.
     */
    data class Value(val error: Throwable) : ErrorKind()

    /** Errors relating to the way a function is called (wrong number of args, etc.). */
    data class Function(val error: Throwable) : ErrorKind()

    /** Out of scope variables and similar. */
    data class Scope(val error: Throwable) : ErrorKind()

    /** Syntax error. */
    data class Parser(val error: Throwable) : ErrorKind() {
        override fun toString(): String = error.message ?: error.toString()
    }

    /** Freeze errors. Should have no metadata attached. */
    data class Freeze(val error: Throwable) : ErrorKind()

    /** Indicates a logic bug in starlark. */
    data class Internal(val error: Throwable) : ErrorKind()

    /**
     * Error from user provided native function
     * (but not from native functions provided by starlark crate).
     * When a native function declares a [Result] return type, it is automatically converted to this variant.
     */
    data class Native(val error: Throwable) : ErrorKind()

    /**
     * Fallback option.
     *
     * For errors produced by starlark which have not yet been assigned their own kind.
     */
    data class Other(val error: Throwable) : ErrorKind()

    /** The source of the error, akin to [Throwable.cause]. */
    fun source(): Throwable? {
        return when (this) {
            is Fail -> null
            is StackOverflow -> null
            is Value -> null
            is Function -> null
            is Scope -> null
            is Freeze -> null
            is Parser -> null
            is Internal -> null
            is Native -> error.cause
            is Other -> error.cause
        }
    }

    /** Change type to [Internal]. */
    internal fun intoInternalError(): ErrorKind {
        return when (this) {
            is Internal -> Internal(error)
            is Fail -> Internal(error)
            is Value -> Internal(error)
            is Function -> Internal(error)
            is Scope -> Internal(error)
            is Freeze -> Internal(error)
            is Parser -> Internal(error)
            is StackOverflow -> Internal(error)
            is Native -> Internal(error)
            is Other -> Internal(error)
        }
    }

    override fun toString(): String {
        return when (this) {
            is Fail -> "fail:$error"
            is StackOverflow -> error.toString()
            is Value -> error.toString()
            is Function -> error.toString()
            is Scope -> error.toString()
            is Freeze -> error.toString()
            is Parser -> error.toString()
            is Internal -> "Internal error: $error"
            is Native -> error.toString()
            is Other -> error.toString()
        }
    }
}

interface StarlarkResultExt<T> {
    fun intoAnyhowResult(): Result<T>
}

/** Convert this Result<T, Error> into a Result<T> that wraps an anyhow-style throwable. */
fun <T> Result<T>.intoAnyhowResult(): Result<T> {
    return this.fold(
        onSuccess = { Result.success(it) },
        onFailure = { e ->
            if (e is StarlarkErrorException) {
                Result.failure(e.error.intoAnyhow())
            } else {
                Result.failure(e)
            }
        }
    )
}

/** Wrapper exception used when an [Error] needs to be carried through Kotlin's [Result]/[Throwable] APIs. */
class StarlarkErrorException(val error: Error) : Exception(error.toString())

fun internalErrorImpl(message: String): Error {
    return Error.newKind(ErrorKind.Internal(Exception(message)))
}

fun otherErrorImpl(message: String): Error {
    return Error.newKind(ErrorKind.Other(Exception(message)))
}

fun valueErrorImpl(message: String): Error {
    return Error.newKind(ErrorKind.Value(Exception(message)))
}

fun functionErrorImpl(message: String): Error {
    return Error.newKind(ErrorKind.Function(Exception(message)))
}

/** Internal error of starlark. */
fun internalError(message: String): Error = internalErrorImpl(message)

fun otherError(message: String): Error = otherErrorImpl(message)

fun valueError(message: String): Error = valueErrorImpl(message)

fun functionError(message: String): Error = functionErrorImpl(message)
