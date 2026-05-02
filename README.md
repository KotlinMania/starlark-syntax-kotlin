# starlark-syntax-kotlin

Kotlin Multiplatform port of Facebook's [`starlark_syntax`](https://crates.io/crates/starlark_syntax) crate from [facebook/starlark-rust](https://github.com/facebook/starlark-rust).

This artifact owns the Starlark **language AST, lexer, parser, codemap, dialect, and error model** — everything you need to parse `.star` / `.bzl` source into a typed AST without bringing in the evaluator, value system, or heap. It is the parser-island half of the Starlark stack.

## Maven coordinates

```kotlin
dependencies {
    implementation("io.github.kotlinmania:starlark-syntax:0.1.0")
}
```

## Why a separate artifact?

Upstream Rust splits `starlark_syntax` from `starlark` for the same reasons a Kotlin consumer would want the split:

- **LSP, linters, and tooling** can consume just the parser without dragging in the evaluator's runtime cost or transitive deps.
- **Build hygiene** — the lalrpop-generated grammar is heavy; isolating it keeps incremental compile cycles short for downstream code.
- **Reuse** — both `starlark-kotlin` (the interpreter) and a future `starlark-lsp-kotlin` will consume this artifact.

This mirrors the precedent set by `starlark_map → starlarkmap-kotlin`.

## Targets

Kotlin Multiplatform, no JVM-only target:

- `macosArm64`, `macosX64`
- `linuxX64`
- `mingwX64`
- `iosArm64`, `iosX64`, `iosSimulatorArm64`
- `js` (browser + nodejs)
- `wasmJs` (browser + nodejs)
- `androidLibrary`

## Status

**Phase 1: scaffolding.** The repository is being stood up now; source files are being ported / extracted from `starlark-kotlin`. The upstream Rust source for translation reference lives under `tmp/starlark_syntax/` (a fresh checkout from `facebook/starlark-rust`).

## License

Apache-2.0. See [LICENSE](./LICENSE).
