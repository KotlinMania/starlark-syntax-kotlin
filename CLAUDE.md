# Claude Code Project Instructions — starlark-syntax-kotlin

## Project Overview

This is the Kotlin Multiplatform port of upstream Rust crate `starlark_syntax` (from [facebook/starlark-rust](https://github.com/facebook/starlark-rust)). It's the parser-island half of the Starlark stack — AST, lexer, parser, codemap, dialect, error model — with no evaluator, no value system, no heap.

It is a sibling artifact to `starlark-kotlin` (the interpreter), mirroring the upstream `starlark_syntax` ↔ `starlark` separation. Both `starlark-kotlin` and a future `starlark-lsp-kotlin` will consume this artifact via Maven.

Upstream Rust source lives at `tmp/starlark_syntax/` and is the read-only translation oracle. **Never edit `tmp/`.** If upstream looks wrong, the bug is in the port or in your understanding of Rust, not in `tmp/`.

## Translator's mindset

This is a translation project, not a software-engineering project. While porting a file, you are
the Kotlin author of the same document a Rust author wrote. Architecture, optimization, design
critique, drift measurement — all later. While translating, the only job is the translation.

The discipline:

1. **Read the whole upstream file before you type.** A line-by-line port composes only when you
   know how the file ends. If the file is too long to read in one sitting, split your turn into
   "read the file" and "write the file" — never start typing on a file you've only half-read.

2. **One Rust file → one Kotlin file. Always.** No splitting one `.rs` across several `.kt`. No
   merging several `.rs` into one `.kt`. The 1:1 mapping is the contract; everything downstream
   (ast_distance, port-lint headers, code review) assumes it. If a `.rs` is genuinely too big for
   one Kotlin file, that's a sign you're in `mod.rs`-equivalent territory and the upstream itself
   is a re-export — verify, don't split.

3. **Translate top to bottom in upstream order.** Preserve the declaration order. Don't reorder
   for "logical flow" — the upstream's order *is* the logical flow. The reader who already knows
   the Rust file should be able to scroll the Kotlin file and find every item in the same place.

4. **Comments are content.** License header, module-level doc, every `///` block, every inline
   `//` note, every upstream `// TODO`/`// FIXME` — all translate. Rust syntax inside doc comments
   gets rewritten to Kotlin equivalents (`Vec<T>` → `List<T>`, `Self::foo()` → `foo()`, lifetimes
   dropped, `cfg(test)` and `#[derive(...)]` lifted into prose). You are translating a *document*,
   not just the code.

5. **When a Rust idiom has no Kotlin analog, apply the mapping rule and move on.** `Box<T>`,
   `Arc<T>`, `Cell<T>`, `RefCell<T>`, `Rc<T>`, lifetimes, `PhantomData`, `mem::forget`,
   `drop_in_place`, `Pin`, `MaybeUninit`, `dyn Trait` — all collapse per the mapping table.
   Don't relitigate. A proc-macro becomes a builder/runtime API, not nothing. An upstream Rust
   crate with no KMP equivalent becomes a *separate Kotlin port*, not a `// TODO` placeholder.
   Pay the snowball cost upfront — the next consumer will thank you.

6. **Don't measure mid-port.** ast_distance, FnSim, similarity reports — useful *after* a file is
   done, useless *during*. Mid-translation measurement is procrastination dressed as rigor. Run
   the tools when a file lands or when a port phase wraps, not while you're choosing between
   `Result<T>` and `T?`.

7. **Don't optimize the translation.** "This Kotlin shape would be simpler" is the wrong
   thought. The upstream shape is the spec. If a faithful translation produces a function that
   takes a parameter you'd never write in Kotlin from scratch, take it. Optimization is a
   separate, named pass after parity is reached — never blended into the translation.

8. **Don't re-architect mid-port.** "This whole module would be cleaner if..." — write the
   thought on a sticky note, throw the sticky note away, finish the file. The current architecture
   is the upstream's architecture. Earn the right to redesign by first reaching parity.

9. **Compile errors during translation are normal and expected.** A bottom-of-tree file compiles
   when its deps are ported, not before. Don't pause to "make it compile" mid-port — that pulls
   you into stub-shaped fixes that you'll have to undo. Climb the dep tree bottom-up; the leaves
   compile first, then their parents, then everything compiles together at the end.

10. **Bottom-up always.** Port dependencies before consumers. If `state.rs` uses `EvalException`,
    port `eval_exception.rs` first. If `eval_exception.rs` uses `Error`/`WithDiagnostic`/`CallStack`,
    port those first. The order isn't optional; trying to port top-down produces a tree of stubs
    that all need replacing.

11. **Hard files are not skippable.** logos-codegen, lalrpop's table generator, an annotate-snippets
    equivalent — when you hit one, port it. Skipping leaves a `// TODO`-shaped hole that grows
    every time another consumer needs it. The snowball is the whole point: each hard port done
    makes the next port easier, because the dep is now in Kotlin.

12. **Warnings are real, but `@Suppress` is never the answer.** `UNUSED_PARAMETER` on a callback
    helper means the function shape doesn't fit Kotlin — restructure the signature, don't suppress.
    `UNCHECKED_CAST` means the type system is missing an invariant — encode it. Every warning is
    either a real bug or a translation choice that needs revisiting; treat them as compile errors.

13. **Stop at file boundaries, not function boundaries.** After every completed file, exhale,
    commit, move on. Don't pause mid-function to second-guess a choice. The whole-file context
    is what makes individual choices coherent.

14. **Doc-port discipline applies even when the upstream doc is awkward.** If the upstream
    author wrote a tortured English sentence in a doc comment, translate the tortured sentence.
    Don't smooth it. Don't paraphrase. Their doc is the contract for the Kotlin doc.

15. **The cheat detector is your friend.** If `ast_distance` forces your file's score to 0
    because you left snake_case identifiers or `pub` keywords in Kotlin comments, take it as a
    literal instruction: rewrite those comments to be Kotlin-native. Rust syntax in Kotlin source
    — code or comments — is the cheat we're catching.

The sticky-note version: **"Read the file. Translate it. Don't think about anything else."**

## Project Goals (the contract)

When a rule below seems to conflict with these, the goals win.

1. **Functional parity with upstream.** Every public API in `starlark_syntax/src/` has a Kotlin counterpart that behaves identically against the same fixtures.
2. **All tests pass.** Every `@Test` in `commonTest/` runs and passes on every shipped target. No skips, no `@Ignore`, no "TODO: re-enable later."
3. **Kotlin source looks like Kotlin source.** No carried-over Rust idioms in the Kotlin codebase. Rust syntax appears only as references in port-lint headers, never in code, KDoc, or API shapes.
4. **No hacks.** No stubs, no `TODO()`, no `FIXME`, no `@Suppress` annotations, no JVM imports, no synthetic typealiases, no operator-graded test gates, no "fix it later" comments. Warnings are errors — fix the cause.

## Verification

The build gate is **`./gradlew test`**.

```bash
./gradlew test                   # all targets
./gradlew macosArm64Test         # specific platform
./gradlew linuxX64Test
./gradlew jsNodeTest
./gradlew wasmJsNodeTest
```

`./gradlew jvmTest` is **not** valid — there is no JVM target.

## Targets — Kotlin Multiplatform, no JVM

- `macosArm64`
- `linuxX64`
- `mingwX64`
- `iosArm64`, `iosSimulatorArm64`
- `js` (browser + nodejs)
- `wasmJs` (browser + nodejs)
- `androidLibrary`

### Forbidden imports

- `import kotlin.jvm.*` (`JvmName`, `JvmStatic`, `JvmField`, `JvmOverloads`, …)
- any `import java.*`
- any `import javax.*`

If you find yourself reaching for one, the answer is in the Kotlin stdlib or a kotlinx library.

### Approved dependencies

- `kotlinx-coroutines-core`
- `kotlinx-serialization-core`, `kotlinx-serialization-json`
- `kotlinx-collections-immutable`
- `kotlinx-datetime`
- `com.ionspin.kotlin:bignum`
- `io.github.kotlinmania:starlarkmap-kotlin`

Add a new dependency only when stdlib + the above cannot reproduce the required behavior, and only after confirming it publishes artifacts for **every** target above. Document the addition in the commit message.

## Naming Conventions

**No underscores in Kotlin identifiers** except `SCREAMING_SNAKE_CASE` contexts where Kotlin coding conventions explicitly permit them.

| Kind | Form |
|---|---|
| Functions, parameters, locals | `camelCase` |
| Classes, data classes, sealed types | `PascalCase` |
| Interfaces | `PascalCase`, no `I` prefix |
| `const val`, `enum` entries | `SCREAMING_SNAKE_CASE` permitted |
| Top-level/`object` `val` constants | `SCREAMING_SNAKE_CASE` permitted |
| Type parameters | `T`, `K`, `V` (single uppercase) |
| Packages | all lowercase, no camelCase |

| Visibility | Kotlin keyword |
|---|---|
| public (default) | omitted |
| module-internal | `internal` |
| file-private | `private` |

## Port-lint headers (REQUIRED)

Every Kotlin file MUST start with:

```kotlin
// port-lint: source <path-relative-to-tmp/starlark_syntax/>
package io.github.kotlinmania.starlarksyntax.<module>
```

Example:

```kotlin
// port-lint: source src/syntax/ast.rs
package io.github.kotlinmania.starlarksyntax.syntax.ast
```

This is how `ast_distance` tracks provenance. Never remove or alter unless the file is being re-targeted to a different Rust source.

For files that have no single Rust counterpart (re-homed from a `mod.rs`, or pure Kotlin glue), use `// port-lint: ignore` and a one-line prose note explaining what it does.

## Code Discipline

### No `@Suppress`. Warnings are errors.

Warnings indicate the code is wrong. Fix the cause.

- `unused` → the symbol is genuinely dead; delete it.
- `UNCHECKED_CAST` → encode the missing invariant in the type system (sealed classes, generics, `inline class`).
- `UNUSED_VARIABLE` → use `_`.

### No stubs, no shims, no operator-graded gates

- No empty-body classes when the type has fields and methods.
- No `fun bar() = TODO()` or `fun bar() { error("not implemented") }`.
- No partial implementations that declare a class but skip its methods.
- No "placeholder until X is ready" comments with commented-out code.

### Translation discipline

- **Doc comments are first-class.** Translate KDoc word-for-word. Rust syntax inside doc comments (`Vec<T>`, `Option<&str>`, `Self::foo()`, lifetimes, `cfg(test)`, `#[derive(...)]`) gets rewritten to Kotlin equivalents. Do not delete the comment to silence the rule.
- **No no-op shells.** Rust constructs the Kotlin GC subsumes (`Box<T>`, `Cell<T>`, `RefCell<T>`, `Arc<T>`, `Rc<T>`, `Pin`, `mem::forget`, `drop_in_place`, `MaybeUninit<T>`, `dyn Trait`) get **deleted** in the port. Inline the wrapped value or use the closest Kotlin idiom (plain reference, `var`, atomic ref where threaded). Empty shells inflate symbol counts without porting any behavior.
- **No `mod.rs` → `Mod.kt`.** When upstream's `mod.rs` is reexport glue, drop the file and rewire callers. When `mod.rs` contains real implementation, re-home it into properly-named files.

### Blast Radius Rule

- **No repo-wide scripting.** No `find … -exec`, no global `sed` / `perl`, no blanket regex replacements across many files.
- **Changes are task-scoped, not pattern-scoped.** Every touched file is named up front, or discovered as a direct compile/test failure caused by the primary change.
- **Small multi-file edits are allowed when mechanically coupled** — the primary file plus its corresponding `commonTest` and any required call-site rewires. No drive-by refactors, renames, or formatting churn outside that slice.
- **Comments and docstrings are first-class.** Never edited by bulk operations. Any comment change is intentional and reviewed in the diff like code.
- **More than ~5 files in a single change?** Stop and ask.

### Operational rules

- **Do not write to `/tmp` or to project-local `tmp/` for staging.** `tmp/` holds upstream Rust source and is read-only.
- **Commit after every file edit.** One file edited → one commit. Do not batch edits across files.
- **Deletions require `git rm` plus reference scrubs.** Plain `rm` leaves the file in git; surviving references in other files (`[Foo](./Foo.md)`, `// see Foo.md`) tell future runs to recreate the file.

### Do not delegate `.kt` edits to subagents

Subagents (Task / Agent tool) are allowed for searches, file location, and read-only reports. They are **not** allowed for writing or editing `.kt` source files. Subagents cheat on translation: they hollow out KDoc, add filler to inflate scores, drop semantically load-bearing constructs, and produce confident summaries that mask damage.

All `.kt` edits happen in the main loop.

## File Organization

```
src/
├── commonMain/kotlin/io/github/kotlinmania/starlarksyntax/
│   ├── codemap/      # codemap.rs, span.rs
│   ├── lexer/        # lexer.rs
│   ├── dialect/      # dialect.rs
│   ├── error/        # error.rs, diagnostic.rs, eval_exception.rs
│   ├── frame/        # frame.rs, call_stack.rs
│   ├── syntax/       # syntax.rs, syntax/{ast,call,def,module,parser,state,type_expr,uniplate,top_level_stmts,grammar_util,lint_suppressions}.rs
│   └── util/         # cursors.rs, fast_string.rs, slice_vec_ext.rs, span_display.rs, golden_test_template.rs, dot_format_parser.rs, convert_indices.rs
└── commonTest/
    └── kotlin/io/github/kotlinmania/starlarksyntax/   # tests, mirroring main
```

## Cross-Project Coordination

This repo's downstream consumers (Maven, never include-build):

- `starlark-kotlin` — the interpreter
- `starlark-lsp-kotlin` — future LSP server

When you bump a version here, check that every consumer's pinned version matches what's published.

```bash
grep -rln "io.github.kotlinmania:starlark-syntax" /Volumes/stuff/Projects/kotlinmania/*/build.gradle.kts
```

If versions are mismatched, raise on Slack before bumping — version bumps cascade.

## CI

```bash
gh run list --workflow ci.yml --limit 5
gh pr checks <pr-number>
```

Read failing logs before claiming the change is done.

## Final Report

When a run finishes, post a status update via the Slack MCP / skill / connector. Include:

- Files touched.
- Tests added and passing.
- Blockers that need a human decision.

## Commit Messages

- No AI branding or attribution.
- Clear, descriptive, focused on what changed and why.
- No "Co-Authored-By" lines.
- No emoji or robot references.
