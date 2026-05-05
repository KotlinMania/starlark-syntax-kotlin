# port-lint Proposed Changes

**Generated:** 2026-05-05
**Source:** tmp/starlark_syntax/src/syntax/parser_lalrpop.rs
**Target:** src/commonMain/kotlin/io/github/kotlinmania/starlarksyntax/syntax/parser/ParserLalrpop.kt

These are review proposals only. They are emitted when a Rust -> Kotlin pair matches only after fallback normalization, so the existing `port-lint` header is not an exact provenance match.

| Target file | Current header | Proposed header | Source path | Reason |
|-------------|----------------|-----------------|-------------|--------|
| `src/commonMain/kotlin/io/github/kotlinmania/starlarksyntax/syntax/parser/ParserLalrpop.kt` | `// port-lint: source src/syntax/parser_lalrpop.rs` | `// port-lint: source syntax/parser_lalrpop.rs` | `syntax/parser_lalrpop.rs` | `port-lint provenance header matched only after fallback normalization: 'src/syntax/parser_lalrpop.rs' vs expected 'syntax/parser_lalrpop.rs'` |
