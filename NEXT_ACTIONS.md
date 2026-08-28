# Immediate Actions - High-Value Files

Based on AST analysis, here are the concrete next steps.

## Summary

- **Files Present:** 32/37 (86.5%)
- **Function parity:** 242/387 matched (target 467) — 62.5%
- **Class/type parity:** 123/142 matched (target 320) — 86.6%
- **Combined symbol parity:** 365/529 matched (target 787) — 69.0%
- **Average inline-code cosine:** 0.59 (function body across 28 matched files)
- **Average documentation cosine:** 0.84 (doc text across 28 matched files)
- **Cheat-zeroed Files:** 7
- **Critical Issues:** 17 files with <0.60 function similarity

## Priority 1: Fix Incomplete High-Dependency Files

### 1. codemap
- **Similarity:** 0.75 (needs 10% improvement)
- **Dependencies:** 14
- **Priority Score:** 14047302.0
- **Functions:** 54/56 matched (target 108)
- **Missing functions:** `len`, `from`
- **Types:** 15/17 matched (target 18)
- **Missing types:** `Output`, `Target`
- **Symbol Deficit:** 4 (functions: 2, types: 2)
- **Missing Tests:** 1 of 10 `#[test]` functions have no Kotlin counterpart
- **Action:** Minor refinements needed

## Priority 2: Port Missing High-Value Files

Critical missing files (>10 dependencies):

No missing high-value files detected.

## Detailed Work Items

Every matched file is listed below with function and type symbol parity.

### 1. codemap

- **Target:** `codemap.CodeMap`
- **Similarity:** 0.75
- **Dependents:** 14
- **Priority Score:** 14047302.0
- **Functions:** 54/56 matched (target 108)
- **Missing functions:** `len`, `from`
- **Types:** 15/17 matched (target 18)
- **Missing types:** `Output`, `Target`
- **Tests:** 9/10 matched

### 2. eval_exception

- **Target:** `evalexception.EvalException`
- **Similarity:** 0.87
- **Dependents:** 8
- **Priority Score:** 8001101.5
- **Functions:** 10/10 matched
- **Missing functions:** _none_
- **Types:** 1/1 matched
- **Missing types:** _none_

### 3. dialect

- **Target:** `starlarksyntax.Dialect`
- **Similarity:** 0.78
- **Dependents:** 7
- **Priority Score:** 7000302.0
- **Functions:** 1/1 matched
- **Missing functions:** _none_
- **Types:** 2/2 matched
- **Missing types:** _none_

### 4. fast_string

- **Target:** `faststring.FastString [ZERO] [PROVENANCE-FALLBACK]`
- **Similarity:** 0.00
- **Dependents:** 3
- **Priority Score:** 3042210.0
- **Functions:** 16/19 matched (target 21)
- **Missing functions:** `sub`, `add`, `none_ors`
- **Types:** 2/3 matched
- **Missing types:** `Output`
- **Tests:** 3/4 matched
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `tests:fast_string.rs` vs expected `fast_string.rs`
- **Proposed provenance header:** `// port-lint: tests fast_string.rs` (current: `// port-lint: tests fast_string.rs`)
- **Lint issues:** 1

### 5. call_stack

- **Target:** `callstack.CallStack`
- **Similarity:** 0.86
- **Dependents:** 3
- **Priority Score:** 3000401.5
- **Functions:** 3/3 matched (target 4)
- **Missing functions:** _none_
- **Types:** 1/1 matched
- **Missing types:** _none_

### 6. golden_test_template

- **Target:** `goldentesttemplate.GoldenTestTemplate [STUB]`
- **Similarity:** 0.00
- **Dependents:** 3
- **Priority Score:** 3000210.0
- **Functions:** 2/2 matched (target 3)
- **Missing functions:** _none_
- **Types:** 0/0 matched
- **Missing types:** _none_
- **TODOs:** 1

### 7. lexer

- **Target:** `lexer.Lexer [ZERO] [PROVENANCE-FALLBACK]`
- **Similarity:** 0.00
- **Dependents:** 2
- **Priority Score:** 2052910.0
- **Functions:** 17/21 matched (target 58)
- **Missing functions:** `from`, `new`, `unlex`, `fmt`
- **Types:** 7/8 matched (target 102)
- **Missing types:** `Item`
- **Tests:** 1/2 matched
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `tests:lexer.rs` vs expected `lexer.rs`
- **Proposed provenance header:** `// port-lint: tests lexer.rs` (current: `// port-lint: tests lexer.rs`)
- **Lint issues:** 1

### 8. error

- **Target:** `error.Error`
- **Similarity:** 0.52
- **Dependents:** 2
- **Priority Score:** 2022904.8
- **Functions:** 24/25 matched (target 33)
- **Missing functions:** `fmt`
- **Types:** 3/4 matched (target 14)
- **Missing types:** `Wrapped`

### 9. syntax.parser

- **Target:** `parser.Parser`
- **Similarity:** 1.00
- **Dependents:** 2
- **Priority Score:** 2010200.0
- **Functions:** 0/0 matched
- **Missing functions:** _none_
- **Types:** 1/2 matched (target 4)
- **Missing types:** `Parser`

### 10. syntax.parse_error

- **Target:** `parseerror.ParseError`
- **Similarity:** 0.87
- **Dependents:** 2
- **Priority Score:** 2000201.2
- **Functions:** 1/1 matched
- **Missing functions:** _none_
- **Types:** 1/1 matched (target 3)
- **Missing types:** _none_

### 11. syntax.ast_load

- **Target:** `astload.AstLoad`
- **Similarity:** 1.00
- **Dependents:** 2
- **Priority Score:** 2000100.0
- **Functions:** 0/0 matched
- **Missing functions:** _none_
- **Types:** 1/1 matched
- **Missing types:** _none_

### 12. syntax.type_expr

- **Target:** `typeexpr.TypeExpr [STUB]`
- **Similarity:** 0.00
- **Dependents:** 1
- **Priority Score:** 1030810.0
- **Functions:** 4/5 matched
- **Missing functions:** `from`
- **Types:** 1/3 matched (target 16)
- **Missing types:** `TypePathP`, `TypeExprUnpackP`
- **TODOs:** 2

### 13. frame

- **Target:** `frame.Frame [PROVENANCE-FALLBACK]`
- **Similarity:** 0.54
- **Dependents:** 1
- **Priority Score:** 1010504.6
- **Functions:** 3/4 matched
- **Missing functions:** `fmt`
- **Types:** 1/1 matched (target 2)
- **Missing types:** _none_
- **Tests:** 1/1 matched
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `tests:frame.rs` vs expected `frame.rs`
- **Proposed provenance header:** `// port-lint: tests frame.rs` (current: `// port-lint: tests frame.rs`)
- **Lint issues:** 1

### 14. syntax.lint_suppressions

- **Target:** `lintsuppressions.LintSuppressions`
- **Similarity:** 0.74
- **Dependents:** 1
- **Priority Score:** 1001202.6
- **Functions:** 8/8 matched
- **Missing functions:** _none_
- **Types:** 4/4 matched
- **Missing types:** _none_

### 15. convert_indices

- **Target:** `convertindices.ConvertIndices`
- **Similarity:** 0.75
- **Dependents:** 1
- **Priority Score:** 1000302.6
- **Functions:** 3/3 matched
- **Missing functions:** _none_
- **Types:** 0/0 matched
- **Missing types:** _none_

### 16. span_display

- **Target:** `spandisplay.SpanDisplay`
- **Similarity:** 0.92
- **Dependents:** 1
- **Priority Score:** 1000200.8
- **Functions:** 2/2 matched (target 4)
- **Missing functions:** _none_
- **Types:** 0/0 matched (target 6)
- **Missing types:** _none_

### 17. syntax.def

- **Target:** `def.Def`
- **Similarity:** 0.17
- **Dependents:** 0
- **Priority Score:** 152608.3
- **Functions:** 5/20 matched (target 5)
- **Missing functions:** `fails_dialect`, `fails`, `passes`, `test_params_unpack`, `test_params_noargs`, `test_star_cannot_be_last`, `test_star_then_args`, `test_star_then_kwargs`, `test_positional_only`, `test_positional_only_cannot_be_first`, `test_slash_slash`, `test_named_only_in_standard_dialect_def`, `test_named_only_in_standard_dialect_lambda`, `test_positional_only_in_standard_dialect_def`, `test_positional_only_in_standard_dialect_lambda`
- **Types:** 6/6 matched (target 9)
- **Missing types:** _none_
- **Tests:** 0/15 matched

### 18. syntax.module

- **Target:** `module.AstModule`
- **Similarity:** 0.25
- **Dependents:** 0
- **Priority Score:** 111807.5
- **Functions:** 6/16 matched (target 14)
- **Missing functions:** `codemap`, `statement`, `dialect`, `create`, `parse_file`, `parse`, `f`, `go`, `test_locations`, `get`
- **Types:** 1/2 matched
- **Missing types:** `AstModuleFields`
- **Tests:** 0/2 matched

### 19. slice_vec_ext

- **Target:** `slicevecext.SliceVecExt [ZERO]`
- **Similarity:** 0.00
- **Dependents:** 0
- **Priority Score:** 70810.0
- **Functions:** 1/5 matched (target 7)
- **Missing functions:** `map`, `try_map`, `into_map`, `into_try_map`
- **Types:** 0/3 matched (target 0)
- **Missing types:** `SliceExt`, `Item`, `VecExt`

### 20. syntax.ast

- **Target:** `ast.Ast`
- **Similarity:** 0.58
- **Dependents:** 0
- **Priority Score:** 67504.2
- **Functions:** 12/12 matched (target 53)
- **Missing functions:** _none_
- **Types:** 57/63 matched (target 106)
- **Missing types:** `LoadPayload`, `IdentPayload`, `IdentAssignPayload`, `DefPayload`, `TypeExprPayload`, `ToAst`

### 21. dot_format_parser

- **Target:** `dotformatparser.DotFormatParser [PROVENANCE-FALLBACK]`
- **Similarity:** 0.54
- **Dependents:** 0
- **Priority Score:** 31704.6
- **Functions:** 9/11 matched (target 13)
- **Missing functions:** `new`, `deref`
- **Types:** 5/6 matched (target 9)
- **Missing types:** `Target`
- **Tests:** 2/2 matched
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `tests:dot_format_parser.rs` vs expected `dot_format_parser.rs`
- **Proposed provenance header:** `// port-lint: tests dot_format_parser.rs` (current: `// port-lint: tests dot_format_parser.rs`)
- **Lint issues:** 1

### 22. diagnostic

- **Target:** `diagnostic.Diagnostic`
- **Similarity:** 0.56
- **Dependents:** 0
- **Priority Score:** 21604.4
- **Functions:** 11/13 matched (target 12)
- **Missing functions:** `fmt`, `from`
- **Types:** 3/3 matched
- **Missing types:** _none_

### 23. cursors

- **Target:** `cursors.Cursors`
- **Similarity:** 0.23
- **Dependents:** 0
- **Priority Score:** 10907.7
- **Functions:** 6/7 matched (target 9)
- **Missing functions:** `new`
- **Types:** 2/2 matched
- **Missing types:** _none_

### 24. syntax.uniplate

- **Target:** `uniplate.Uniplate [ZERO]`
- **Similarity:** 0.00
- **Dependents:** 0
- **Priority Score:** 2210.0
- **Functions:** 20/20 matched (target 41)
- **Missing functions:** _none_
- **Types:** 2/2 matched (target 4)
- **Missing types:** _none_

### 25. syntax.grammar_util

- **Target:** `parser.GrammarUtil [STUB]`
- **Similarity:** 0.00
- **Dependents:** 0
- **Priority Score:** 1110.0
- **Functions:** 9/9 matched (target 10)
- **Missing functions:** _none_
- **Types:** 2/2 matched
- **Missing types:** _none_
- **TODOs:** 1

### 26. syntax.parser_lalrpop

- **Target:** `parser.ParserLalrpop [PROVENANCE-FALLBACK]`
- **Similarity:** 0.80
- **Dependents:** 0
- **Priority Score:** 502.0
- **Functions:** 4/4 matched (target 5)
- **Missing functions:** _none_
- **Types:** 1/1 matched (target 2)
- **Missing types:** _none_
- **Tests:** 1/1 matched
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `tests:syntax/parser_lalrpop.rs` vs expected `syntax/parser_lalrpop.rs`
- **Proposed provenance header:** `// port-lint: tests syntax/parser_lalrpop.rs` (current: `// port-lint: tests syntax/parser_lalrpop.rs`)
- **Lint issues:** 1

### 27. syntax.validate

- **Target:** `validate.Validate`
- **Similarity:** 0.89
- **Dependents:** 0
- **Priority Score:** 501.1
- **Functions:** 5/5 matched
- **Missing functions:** _none_
- **Types:** 0/0 matched
- **Missing types:** _none_

### 28. syntax.top_level_stmts

- **Target:** `toplevelstmts.TopLevelStmts`
- **Similarity:** 0.72
- **Dependents:** 0
- **Priority Score:** 302.8
- **Functions:** 3/3 matched (target 4)
- **Missing functions:** _none_
- **Types:** 0/0 matched
- **Missing types:** _none_

### 29. syntax.call

- **Target:** `call.Call`
- **Similarity:** 0.90
- **Dependents:** 0
- **Priority Score:** 301.0
- **Functions:** 1/1 matched
- **Missing functions:** _none_
- **Types:** 2/2 matched
- **Missing types:** _none_

### 30. syntax.payload_map

- **Target:** `payloadmap.PayloadMap`
- **Similarity:** 0.57
- **Dependents:** 0
- **Priority Score:** 204.3
- **Functions:** 1/1 matched (target 24)
- **Missing functions:** _none_
- **Types:** 1/1 matched
- **Missing types:** _none_

### 31. syntax.state

- **Target:** `state.ParserState`
- **Similarity:** 0.85
- **Dependents:** 0
- **Priority Score:** 201.5
- **Functions:** 1/1 matched
- **Missing functions:** _none_
- **Types:** 1/1 matched
- **Missing types:** _none_

### 32. syntax

- **Target:** `syntax.Syntax [STUB]`
- **Similarity:** 1.00
- **Dependents:** 0
- **Priority Score:** 0.0
- **Functions:** 0/0 matched
- **Missing functions:** _none_
- **Types:** 0/0 matched
- **Missing types:** _none_

## Success Criteria

For each file to be considered "complete":
- **Similarity ≥ 0.85** (Excellent threshold)
- All public APIs ported
- All tests ported
- Documentation ported
- port-lint header present

## Reexport / Wiring Modules

These files match `reexport_modules` patterns in `.ast_distance_config.json`. They are filtered out of
normal priority and missing-file ladders because they are wiring
modules, not direct logic ports. Consult them for call-site routing;
do not treat them as the next implementation target by default.

### Missing

| Source | Expected target | Deps | Source path | Expected path |
|--------|-----------------|------|-------------|---------------|
| `lib` | `Lib` | 0 | `src/lib.rs` | `Lib.kt` |

