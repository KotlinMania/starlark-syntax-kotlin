# starlark-syntax-kotlin — agent guide

This repo is the Kotlin Multiplatform port of the upstream Rust `starlark_syntax` crate (from [facebook/starlark-rust](https://github.com/facebook/starlark-rust)). Upstream source lives at `tmp/starlark_syntax/` and is the **read-only** translation oracle. Never edit `tmp/`.

## Scope

This artifact is the **parser island** — AST, lexer, parser, codemap, dialect, error/diagnostic model. No values, no heap, no evaluator. If a port-target file in upstream isn't part of `starlark_syntax/src/`, it doesn't belong here.

## Maven coordinates

`io.github.kotlinmania:starlark-syntax:<version>`

Package root: `io.github.kotlinmania.starlarksyntax`. Subpackages mirror the upstream Rust module tree (e.g. `starlark_syntax/src/syntax/ast.rs` → `io.github.kotlinmania.starlarksyntax.syntax.ast`).

## Port-lint headers

Every Kotlin file MUST start with:

```kotlin
// port-lint: source <path-relative-to-tmp/starlark_syntax/>
package io.github.kotlinmania.starlarksyntax.<module>
```

Example:

```kotlin
// port-lint: source src/codemap.rs
package io.github.kotlinmania.starlarksyntax.codemap
```

Path is relative to `tmp/starlark_syntax/`. This is how `ast_distance` tracks provenance — never remove or alter unless re-targeting a different Rust source.

## Translation discipline

These are line-by-line **transliterations**. Read the Rust file end to end, then port. Don't reorder, summarize, or "improve."

- **Doc comments translate word-for-word.** Rust syntax inside KDoc (`Vec<T>`, `Option<&str>`, `Self::foo()`, lifetimes, `cfg(test)`, `#[derive(...)]`) gets rewritten to its Kotlin equivalent (`List<T>`, `String?`, `foo()`, KDoc links). Translate the code-in-comment; never delete the comment to silence a rule.
- **No no-op shells.** Rust constructs the GC subsumes (`Box<T>`, `Cell<T>`, `RefCell<T>`, `Arc<T>`, `Rc<T>`, `Pin`, `mem::forget`, `drop_in_place`, `MaybeUninit<T>`, `dyn Trait`) get **deleted** in the port. Inline the wrapped value or use the closest Kotlin idiom. Empty shells inflate symbol counts without porting any behavior.
- **No `mod.rs` → `Mod.kt`.** When upstream's `mod.rs` is reexport glue, drop the file and rewire callers. When `mod.rs` contains real implementation, re-home it into properly-named files.
- **Tests live in `commonTest`.** Inline `#[cfg(test)] mod tests` blocks port to `commonTest` mirroring the same package path.

## Code discipline

- **No `@Suppress`.** Warnings are errors. Fix the cause. `UNCHECKED_CAST` → encode the invariant in the type system (sealed classes, generics, `inline class`). `unused` → delete it. `UNUSED_VARIABLE` → use `_`.
- **No stubs.** No `TODO()`, no `error("not implemented")`, no empty class bodies on types that have fields and methods, no "placeholder until X" comments with commented-out code.
- **No JVM imports.** No `kotlin.jvm.*`, no `java.*`, no `javax.*`. Pure Kotlin Multiplatform.
- **No synthetic typealiases for ergonomics.** Mirror Rust `pub type X = Y` only when the Rust source has a `pub type` with that name. Anything else is rustification.

## Blast radius

- No repo-wide scripting (`find -exec`, blanket `sed`/`perl`, regex over many files).
- Changes are task-scoped, not pattern-scoped. Every touched file is named up front.
- Small multi-file changes are allowed when mechanically coupled — primary file plus its `commonTest` and any required call-site rewires.
- No drive-by refactors, renames, or formatting churn.

## Verification

The build gate is **`./gradlew test`**.

```bash
./gradlew macosArm64Test
./gradlew linuxX64Test
./gradlew jsNodeTest
./gradlew wasmJsNodeTest
```

`./gradlew jvmTest` is **not** valid — there is no JVM target.

## Approved dependencies

- `kotlinx-coroutines-core`
- `kotlinx-serialization-core`, `kotlinx-serialization-json`
- `kotlinx-collections-immutable`
- `kotlinx-datetime`
- `com.ionspin.kotlin:bignum` (for big-integer literals in lexed tokens)
- `io.github.kotlinmania:starlarkmap-kotlin` (small-map / small-set primitives used by AST)

Add a new dependency only after confirming it publishes for **every** target above.

## Dependents

Downstream Kotlin consumers (must stay on a published version of this artifact, never composite/include-build):

- `starlark-kotlin` — the interpreter, will consume the AST + parser from here.
- `starlark-lsp-kotlin` — future LSP server, will consume the parser without the evaluator.

## Subagent policy

Do not delegate `.kt` writes to subagents. Subagents cheat on translation: hollow out KDoc, drop semantically load-bearing constructs, produce confident summaries that mask damage. Search and read-only reports via subagents are fine. Edits happen in the main loop.

## Commit style

No AI branding, no Co-Authored-By lines, no emoji. Clear, descriptive messages focused on what changed and why. One file → one commit; squash later via `git rebase -i` for logical units.
