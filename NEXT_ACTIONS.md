# Immediate Actions - High-Value Files

Based on AST analysis, here are the concrete next steps.

## Summary

- **Files Present:** 29/36 (80.6%)
- **Function parity:** 223/385 matched (target 401) — 57.9%
- **Class/type parity:** 124/142 matched (target 302) — 87.3%
- **Combined symbol parity:** 347/527 matched (target 703) — 65.8%
- **Average inline-code cosine:** 0.45 (function body across 26 matched files)
- **Average documentation cosine:** 0.84 (doc text across 26 matched files)
- **Cheat-zeroed Files:** 12
- **Critical Issues:** 18 files with <0.60 function similarity

## Priority 1: Fix Incomplete High-Dependency Files

### 1. codemap
- **Similarity:** 0.77 (needs 8% improvement)
- **Dependencies:** 14
- **Priority Score:** 14037302.0
- **Functions:** 55/56 matched (target 107)
- **Missing functions:** `len`
- **Types:** 15/17 matched (target 20)
- **Missing types:** `Output`, `Target`
- **Symbol Deficit:** 3 (functions: 1, types: 2)
- **Missing Tests:** 1 of 10 `#[test]` functions have no Kotlin counterpart
- **Action:** Minor refinements needed

## Priority 2: Port Missing High-Value Files

Critical missing files (>10 dependencies):

No missing high-value files detected.

## Detailed Work Items

Every matched file is listed below with function and type symbol parity.

### 1. codemap

- **Target:** `codemap.CodeMap [PROVENANCE-FALLBACK]`
- **Similarity:** 0.77
- **Dependents:** 14
- **Priority Score:** 14037302.0
- **Functions:** 55/56 matched (target 107)
- **Missing functions:** `len`
- **Types:** 15/17 matched (target 20)
- **Missing types:** `Output`, `Target`
- **Tests:** 9/10 matched
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `src/codemap.rs` vs expected `codemap.rs`
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `src/codemap.rs` vs expected `codemap.rs`
- **Proposed provenance header:** `// port-lint: source codemap.rs` (current: `// port-lint: source src/codemap.rs`)
- **Proposed provenance header:** `// port-lint: source codemap.rs` (current: `// port-lint: source src/codemap.rs`)
- **Lint issues:** 2

### 2. eval_exception

- **Target:** `evalexception.EvalException [PROVENANCE-FALLBACK]`
- **Similarity:** 0.87
- **Dependents:** 8
- **Priority Score:** 8001101.5
- **Functions:** 10/10 matched
- **Missing functions:** _none_
- **Types:** 1/1 matched
- **Missing types:** _none_
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `src/eval_exception.rs` vs expected `eval_exception.rs`
- **Proposed provenance header:** `// port-lint: source eval_exception.rs` (current: `// port-lint: source src/eval_exception.rs`)
- **Lint issues:** 1

### 3. dialect

- **Target:** `starlarksyntax.Dialect [PROVENANCE-FALLBACK]`
- **Similarity:** 0.78
- **Dependents:** 7
- **Priority Score:** 7000302.0
- **Functions:** 1/1 matched
- **Missing functions:** _none_
- **Types:** 2/2 matched
- **Missing types:** _none_
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `src/dialect.rs` vs expected `dialect.rs`
- **Proposed provenance header:** `// port-lint: source dialect.rs` (current: `// port-lint: source src/dialect.rs`)
- **Lint issues:** 1

### 4. fast_string

- **Target:** `faststring.FastString [ZERO] [PROVENANCE-FALLBACK]`
- **Similarity:** 0.00
- **Dependents:** 3
- **Priority Score:** 3092210.0
- **Functions:** 11/19 matched (target 14)
- **Missing functions:** `is_1bytes`, `f`, `sub`, `add`, `test_convert_str_indices`, `test_convert_str_indices_non_ascii`, `test_convert_str_indices_trigger_debug_assertions`, `none_ors`
- **Types:** 2/3 matched (target 2)
- **Missing types:** `Output`
- **Tests:** 0/4 matched
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `src/fast_string.rs` vs expected `fast_string.rs`
- **Proposed provenance header:** `// port-lint: source fast_string.rs` (current: `// port-lint: source src/fast_string.rs`)
- **Lint issues:** 1

### 5. call_stack

- **Target:** `callstack.CallStack [PROVENANCE-FALLBACK]`
- **Similarity:** 0.86
- **Dependents:** 3
- **Priority Score:** 3000401.5
- **Functions:** 3/3 matched (target 4)
- **Missing functions:** _none_
- **Types:** 1/1 matched
- **Missing types:** _none_
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `src/call_stack.rs` vs expected `call_stack.rs`
- **Proposed provenance header:** `// port-lint: source call_stack.rs` (current: `// port-lint: source src/call_stack.rs`)
- **Lint issues:** 1

### 6. lexer

- **Target:** `lexer.Lexer [ZERO] [PROVENANCE-FALLBACK]`
- **Similarity:** 0.00
- **Dependents:** 2
- **Priority Score:** 2162910.0
- **Functions:** 8/21 matched (target 32)
- **Missing functions:** `from`, `map_lexeme_t`, `new`, `err_now`, `make_comment`, `wrap`, `string`, `int`, `parse_double_quoted_string`, `parse_single_quoted_string`, `unlex`, `fmt`, `test_is_valid_identifier`
- **Types:** 5/8 matched (target 87)
- **Missing types:** `LexemeT`, `Lexeme`, `Item`
- **Tests:** 0/2 matched
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `src/lexer.rs` vs expected `lexer.rs`
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `src/lexer.rs` vs expected `lexer.rs`
- **Proposed provenance header:** `// port-lint: source lexer.rs` (current: `// port-lint: source src/lexer.rs`)
- **Proposed provenance header:** `// port-lint: source lexer.rs` (current: `// port-lint: source src/lexer.rs`)
- **Lint issues:** 3

### 7. error

- **Target:** `error.Error [PROVENANCE-FALLBACK]`
- **Similarity:** 0.52
- **Dependents:** 2
- **Priority Score:** 2022904.8
- **Functions:** 24/25 matched (target 32)
- **Missing functions:** `fmt`
- **Types:** 3/4 matched (target 14)
- **Missing types:** `Wrapped`
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `src/error.rs` vs expected `error.rs`
- **Proposed provenance header:** `// port-lint: source error.rs` (current: `// port-lint: source src/error.rs`)
- **Lint issues:** 1

### 8. syntax.parser

- **Target:** `parser.Parser [ZERO] [PROVENANCE-FALLBACK]`
- **Similarity:** 0.00
- **Dependents:** 2
- **Priority Score:** 2000210.0
- **Functions:** 0/0 matched
- **Missing functions:** _none_
- **Types:** 2/2 matched
- **Missing types:** _none_
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `src/syntax/parser.rs` vs expected `syntax/parser.rs`
- **Proposed provenance header:** `// port-lint: source syntax/parser.rs` (current: `// port-lint: source src/syntax/parser.rs`)
- **Lint issues:** 1

### 9. syntax.parse_error

- **Target:** `parseerror.ParseError [PROVENANCE-FALLBACK]`
- **Similarity:** 0.87
- **Dependents:** 2
- **Priority Score:** 2000201.2
- **Functions:** 1/1 matched
- **Missing functions:** _none_
- **Types:** 1/1 matched (target 3)
- **Missing types:** _none_
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `src/syntax/parse_error.rs` vs expected `syntax/parse_error.rs`
- **Proposed provenance header:** `// port-lint: source syntax/parse_error.rs` (current: `// port-lint: source src/syntax/parse_error.rs`)
- **Lint issues:** 1

### 10. syntax.ast_load

- **Target:** `astload.AstLoad [PROVENANCE-FALLBACK]`
- **Similarity:** 1.00
- **Dependents:** 2
- **Priority Score:** 2000100.0
- **Functions:** 0/0 matched
- **Missing functions:** _none_
- **Types:** 1/1 matched
- **Missing types:** _none_
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `src/syntax/ast_load.rs` vs expected `syntax/ast_load.rs`
- **Proposed provenance header:** `// port-lint: source syntax/ast_load.rs` (current: `// port-lint: source src/syntax/ast_load.rs`)
- **Lint issues:** 1

### 11. frame

- **Target:** `frame.Frame [PROVENANCE-FALLBACK]`
- **Similarity:** 0.40
- **Dependents:** 1
- **Priority Score:** 1020506.0
- **Functions:** 2/4 matched (target 3)
- **Missing functions:** `fmt`, `test_truncate_snippet`
- **Types:** 1/1 matched
- **Missing types:** _none_
- **Tests:** 0/1 matched
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `src/frame.rs` vs expected `frame.rs`
- **Proposed provenance header:** `// port-lint: source frame.rs` (current: `// port-lint: source src/frame.rs`)
- **Lint issues:** 1

### 12. syntax.lint_suppressions

- **Target:** `lintsuppressions.LintSuppressions [ZERO] [PROVENANCE-FALLBACK]`
- **Similarity:** 0.00
- **Dependents:** 1
- **Priority Score:** 1011210.0
- **Functions:** 7/8 matched (target 7)
- **Missing functions:** `new`
- **Types:** 4/4 matched
- **Missing types:** _none_
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `src/syntax/lint_suppressions.rs` vs expected `syntax/lint_suppressions.rs`
- **Proposed provenance header:** `// port-lint: source syntax/lint_suppressions.rs` (current: `// port-lint: source src/syntax/lint_suppressions.rs`)
- **Lint issues:** 1

### 13. syntax.type_expr

- **Target:** `typeexpr.TypeExpr [STUB] [PROVENANCE-FALLBACK]`
- **Similarity:** 0.00
- **Dependents:** 1
- **Priority Score:** 1010810.0
- **Functions:** 4/5 matched
- **Missing functions:** `from`
- **Types:** 3/3 matched (target 16)
- **Missing types:** _none_
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `src/syntax/type_expr.rs` vs expected `syntax/type_expr.rs`
- **Proposed provenance header:** `// port-lint: source syntax/type_expr.rs` (current: `// port-lint: source src/syntax/type_expr.rs`)
- **TODOs:** 2
- **Lint issues:** 1

### 14. convert_indices

- **Target:** `convertindices.ConvertIndices [PROVENANCE-FALLBACK]`
- **Similarity:** 0.75
- **Dependents:** 1
- **Priority Score:** 1000302.6
- **Functions:** 3/3 matched
- **Missing functions:** _none_
- **Types:** 0/0 matched
- **Missing types:** _none_
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `src/convert_indices.rs` vs expected `convert_indices.rs`
- **Proposed provenance header:** `// port-lint: source convert_indices.rs` (current: `// port-lint: source src/convert_indices.rs`)
- **Lint issues:** 1

### 15. span_display

- **Target:** `spandisplay.SpanDisplay [ZERO] [PROVENANCE-FALLBACK]`
- **Similarity:** 0.00
- **Dependents:** 1
- **Priority Score:** 1000210.0
- **Functions:** 2/2 matched (target 4)
- **Missing functions:** _none_
- **Types:** 0/0 matched (target 6)
- **Missing types:** _none_
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `src/span_display.rs` vs expected `span_display.rs`
- **Proposed provenance header:** `// port-lint: source span_display.rs` (current: `// port-lint: source src/span_display.rs`)
- **Lint issues:** 1

### 16. syntax.def

- **Target:** `def.Def [PROVENANCE-FALLBACK]`
- **Similarity:** 0.18
- **Dependents:** 0
- **Priority Score:** 152608.2
- **Functions:** 5/20 matched (target 5)
- **Missing functions:** `fails_dialect`, `fails`, `passes`, `test_params_unpack`, `test_params_noargs`, `test_star_cannot_be_last`, `test_star_then_args`, `test_star_then_kwargs`, `test_positional_only`, `test_positional_only_cannot_be_first`, `test_slash_slash`, `test_named_only_in_standard_dialect_def`, `test_named_only_in_standard_dialect_lambda`, `test_positional_only_in_standard_dialect_def`, `test_positional_only_in_standard_dialect_lambda`
- **Types:** 6/6 matched (target 9)
- **Missing types:** _none_
- **Tests:** 0/15 matched
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `src/syntax/def.rs` vs expected `syntax/def.rs`
- **Proposed provenance header:** `// port-lint: source syntax/def.rs` (current: `// port-lint: source src/syntax/def.rs`)
- **Lint issues:** 1

### 17. syntax.module

- **Target:** `module.AstModule [ZERO] [PROVENANCE-FALLBACK]`
- **Similarity:** 0.00
- **Dependents:** 0
- **Priority Score:** 81810.0
- **Functions:** 9/16 matched
- **Missing functions:** `codemap`, `dialect`, `parse_file`, `f`, `go`, `test_locations`, `get`
- **Types:** 1/2 matched (target 3)
- **Missing types:** `AstModuleFields`
- **Tests:** 0/2 matched
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `src/syntax/module.rs` vs expected `syntax/module.rs`
- **Proposed provenance header:** `// port-lint: source syntax/module.rs` (current: `// port-lint: source src/syntax/module.rs`)
- **Lint issues:** 1

### 18. syntax.ast

- **Target:** `ast.Ast [STUB] [PROVENANCE-FALLBACK]`
- **Similarity:** 0.00
- **Dependents:** 0
- **Priority Score:** 67510.0
- **Functions:** 12/12 matched (target 38)
- **Missing functions:** _none_
- **Types:** 57/63 matched (target 99)
- **Missing types:** `LoadPayload`, `IdentPayload`, `IdentAssignPayload`, `DefPayload`, `TypeExprPayload`, `ToAst`
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `src/syntax/ast.rs` vs expected `syntax/ast.rs`
- **Proposed provenance header:** `// port-lint: source syntax/ast.rs` (current: `// port-lint: source src/syntax/ast.rs`)
- **TODOs:** 2
- **Lint issues:** 1

### 19. dot_format_parser

- **Target:** `dotformatparser.DotFormatParser [PROVENANCE-FALLBACK]`
- **Similarity:** 0.42
- **Dependents:** 0
- **Priority Score:** 51705.8
- **Functions:** 7/11 matched
- **Missing functions:** `new`, `deref`, `test_parser_position`, `test_failure`
- **Types:** 5/6 matched (target 8)
- **Missing types:** `Target`
- **Tests:** 0/2 matched
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `src/dot_format_parser.rs` vs expected `dot_format_parser.rs`
- **Proposed provenance header:** `// port-lint: source dot_format_parser.rs` (current: `// port-lint: source src/dot_format_parser.rs`)
- **Lint issues:** 1

### 20. diagnostic

- **Target:** `diagnostic.Diagnostic [PROVENANCE-FALLBACK]`
- **Similarity:** 0.56
- **Dependents:** 0
- **Priority Score:** 21604.4
- **Functions:** 11/13 matched (target 12)
- **Missing functions:** `fmt`, `from`
- **Types:** 3/3 matched
- **Missing types:** _none_
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `src/diagnostic.rs` vs expected `diagnostic.rs`
- **Proposed provenance header:** `// port-lint: source diagnostic.rs` (current: `// port-lint: source src/diagnostic.rs`)
- **Lint issues:** 1

### 21. cursors

- **Target:** `cursors.Cursors [PROVENANCE-FALLBACK]`
- **Similarity:** 0.16
- **Dependents:** 0
- **Priority Score:** 20908.4
- **Functions:** 5/7 matched
- **Missing functions:** `new`, `new_offset`
- **Types:** 2/2 matched
- **Missing types:** _none_
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `src/cursors.rs` vs expected `cursors.rs`
- **Proposed provenance header:** `// port-lint: source cursors.rs` (current: `// port-lint: source src/cursors.rs`)
- **Lint issues:** 1

### 22. syntax.parser_lalrpop

- **Target:** `parser.ParserLalrpop [ZERO] [PROVENANCE-FALLBACK]`
- **Similarity:** 0.00
- **Dependents:** 0
- **Priority Score:** 10510.0
- **Functions:** 3/4 matched (target 3)
- **Missing functions:** `test_lalrpop_error_to_parse_error`
- **Types:** 1/1 matched (target 8)
- **Missing types:** _none_
- **Tests:** 0/1 matched
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `src/syntax/parser_lalrpop.rs` vs expected `syntax/parser_lalrpop.rs`
- **Proposed provenance header:** `// port-lint: source syntax/parser_lalrpop.rs` (current: `// port-lint: source src/syntax/parser_lalrpop.rs`)
- **Lint issues:** 1

### 23. syntax.uniplate

- **Target:** `uniplate.Uniplate [ZERO] [PROVENANCE-FALLBACK]`
- **Similarity:** 0.00
- **Dependents:** 0
- **Priority Score:** 2210.0
- **Functions:** 20/20 matched (target 41)
- **Missing functions:** _none_
- **Types:** 2/2 matched (target 4)
- **Missing types:** _none_
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `src/syntax/uniplate.rs` vs expected `syntax/uniplate.rs`
- **Proposed provenance header:** `// port-lint: source syntax/uniplate.rs` (current: `// port-lint: source src/syntax/uniplate.rs`)
- **Lint issues:** 1

### 24. syntax.grammar_util

- **Target:** `parser.GrammarUtil [STUB] [PROVENANCE-FALLBACK]`
- **Similarity:** 0.00
- **Dependents:** 0
- **Priority Score:** 1110.0
- **Functions:** 9/9 matched (target 10)
- **Missing functions:** _none_
- **Types:** 2/2 matched
- **Missing types:** _none_
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `src/syntax/grammar_util.rs` vs expected `syntax/grammar_util.rs`
- **Proposed provenance header:** `// port-lint: source syntax/grammar_util.rs` (current: `// port-lint: source src/syntax/grammar_util.rs`)
- **TODOs:** 1
- **Lint issues:** 1

### 25. syntax.validate

- **Target:** `validate.Validate [PROVENANCE-FALLBACK]`
- **Similarity:** 0.86
- **Dependents:** 0
- **Priority Score:** 501.4
- **Functions:** 5/5 matched
- **Missing functions:** _none_
- **Types:** 0/0 matched
- **Missing types:** _none_
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `src/syntax/validate.rs` vs expected `syntax/validate.rs`
- **Proposed provenance header:** `// port-lint: source syntax/validate.rs` (current: `// port-lint: source src/syntax/validate.rs`)
- **Lint issues:** 1

### 26. syntax.top_level_stmts

- **Target:** `toplevelstmts.TopLevelStmts [PROVENANCE-FALLBACK]`
- **Similarity:** 0.84
- **Dependents:** 0
- **Priority Score:** 301.6
- **Functions:** 3/3 matched (target 4)
- **Missing functions:** _none_
- **Types:** 0/0 matched
- **Missing types:** _none_
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `src/syntax/top_level_stmts.rs` vs expected `syntax/top_level_stmts.rs`
- **Proposed provenance header:** `// port-lint: source syntax/top_level_stmts.rs` (current: `// port-lint: source src/syntax/top_level_stmts.rs`)
- **Lint issues:** 1

### 27. syntax.call

- **Target:** `call.Call [PROVENANCE-FALLBACK]`
- **Similarity:** 0.91
- **Dependents:** 0
- **Priority Score:** 300.9
- **Functions:** 1/1 matched
- **Missing functions:** _none_
- **Types:** 2/2 matched
- **Missing types:** _none_
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `src/syntax/call.rs` vs expected `syntax/call.rs`
- **Proposed provenance header:** `// port-lint: source syntax/call.rs` (current: `// port-lint: source src/syntax/call.rs`)
- **Lint issues:** 1

### 28. syntax.payload_map

- **Target:** `payloadmap.PayloadMap [ZERO] [PROVENANCE-FALLBACK]`
- **Similarity:** 0.00
- **Dependents:** 0
- **Priority Score:** 210.0
- **Functions:** 1/1 matched (target 24)
- **Missing functions:** _none_
- **Types:** 1/1 matched
- **Missing types:** _none_
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `src/syntax/payload_map.rs` vs expected `syntax/payload_map.rs`
- **Proposed provenance header:** `// port-lint: source syntax/payload_map.rs` (current: `// port-lint: source src/syntax/payload_map.rs`)
- **Lint issues:** 1

### 29. syntax.state

- **Target:** `state.ParserState [PROVENANCE-FALLBACK]`
- **Similarity:** 0.85
- **Dependents:** 0
- **Priority Score:** 201.5
- **Functions:** 1/1 matched
- **Missing functions:** _none_
- **Types:** 1/1 matched
- **Missing types:** _none_
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `src/syntax/state.rs` vs expected `syntax/state.rs`
- **Proposed provenance header:** `// port-lint: source syntax/state.rs` (current: `// port-lint: source src/syntax/state.rs`)
- **Lint issues:** 1

## Success Criteria

For each file to be considered "complete":
- **Similarity ≥ 0.85** (Excellent threshold)
- All public APIs ported
- All tests ported
- Documentation ported
- port-lint header present

## Next Commands

```bash
# Initialize task queue for systematic porting
cd tools/ast_distance
./ast_distance --init-tasks ../../tmp/starlark_syntax/src rust ../../src/commonMain/kotlin/io/github/kotlinmania/starlarksyntax kotlin tasks.json ../../AGENTS.md

# Get next high-priority task
./ast_distance --assign tasks.json <agent-id>
```
## Reexport / Wiring Modules

These files match `reexport_modules` patterns in `.ast_distance_config.json`. They are filtered out of
normal priority and missing-file ladders because they are wiring
modules, not direct logic ports. Consult them for call-site routing;
do not treat them as the next implementation target by default.

### Missing

| Source | Expected target | Deps | Source path | Expected path |
|--------|-----------------|------|-------------|---------------|
| `lib` | `Lib` | 0 | `lib.rs` | `Lib.kt` |

