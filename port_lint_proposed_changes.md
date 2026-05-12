# port-lint Proposed Changes

**Generated:** 2026-05-07
**Source:** tmp/starlark_syntax
**Target:** src/commonMain/kotlin/io/github/kotlinmania/starlarksyntax

These are review proposals only. They are emitted when a Rust -> Kotlin pair matches only after fallback normalization, so the existing `port-lint` header is not an exact provenance match.

| Target file | Current header | Proposed header | Source path | Reason |
|-------------|----------------|-----------------|-------------|--------|
| `src/commonTest/kotlin/io/github/kotlinmania/starlarksyntax/faststring/FastStringTest.kt` | `// port-lint: tests fast_string.rs` | `// port-lint: tests fast_string.rs` | `fast_string.rs` | `port-lint provenance header matched only after fallback normalization: 'tests:fast_string.rs' vs expected 'fast_string.rs'` |
| `src/commonTest/kotlin/io/github/kotlinmania/starlarksyntax/lexer/LexerTest.kt` | `// port-lint: tests lexer.rs` | `// port-lint: tests lexer.rs` | `lexer.rs` | `port-lint provenance header matched only after fallback normalization: 'tests:lexer.rs' vs expected 'lexer.rs'` |
| `src/commonTest/kotlin/io/github/kotlinmania/starlarksyntax/frame/FrameTest.kt` | `// port-lint: tests frame.rs` | `// port-lint: tests frame.rs` | `frame.rs` | `port-lint provenance header matched only after fallback normalization: 'tests:frame.rs' vs expected 'frame.rs'` |
| `src/commonTest/kotlin/io/github/kotlinmania/starlarksyntax/dotformatparser/DotFormatParserTest.kt` | `// port-lint: tests dot_format_parser.rs` | `// port-lint: tests dot_format_parser.rs` | `dot_format_parser.rs` | `port-lint provenance header matched only after fallback normalization: 'tests:dot_format_parser.rs' vs expected 'dot_format_parser.rs'` |
| `src/commonTest/kotlin/io/github/kotlinmania/starlarksyntax/syntax/parser/ParserLalrpopTest.kt` | `// port-lint: tests syntax/parser_lalrpop.rs` | `// port-lint: tests syntax/parser_lalrpop.rs` | `syntax/parser_lalrpop.rs` | `port-lint provenance header matched only after fallback normalization: 'tests:syntax/parser_lalrpop.rs' vs expected 'syntax/parser_lalrpop.rs'` |
