# Immediate Actions - High-Value Files

Based on AST analysis, here are the concrete next steps.

## Summary

- **Files Present:** 27/37 (73.0%)
- **Function parity:** 223/387 matched (target 394) — 57.6%
- **Class/type parity:** 121/142 matched (target 294) — 85.2%
- **Combined symbol parity:** 344/529 matched (target 688) — 65.0%
- **Average inline-code cosine:** 0.49 (function body across 24 matched files)
- **Average documentation cosine:** 0.84 (doc text across 24 matched files)
- **Cheat-zeroed Files:** 10
- **Critical Issues:** 16 files with <0.60 function similarity

## Priority 1: Fix Incomplete High-Dependency Files

### 1. codemap
- **Similarity:** 0.70 (needs 15% improvement)
- **Dependencies:** 14
- **Priority Score:** 14057303.0
- **Functions:** 53/56 matched (target 97)
- **Missing functions:** `len`, `fmt`, `from`
- **Types:** 15/17 matched (target 18)
- **Missing types:** `Output`, `Target`
- **Symbol Deficit:** 5 (functions: 3, types: 2)
- **Missing Tests:** 1 of 10 `#[test]` functions have no Kotlin counterpart
- **Action:** Review and complete missing sections

## Priority 2: Port Missing High-Value Files

Critical missing files (>10 dependencies):

No missing high-value files detected.

## Detailed Work Items

Every matched file is listed below with function and type symbol parity.

### 1. codemap

- **Target:** `codemap.CodeMap`
- **Similarity:** 0.70
- **Dependents:** 14
- **Priority Score:** 14057303.0
- **Functions:** 53/56 matched (target 97)
- **Missing functions:** `len`, `fmt`, `from`
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
- **Priority Score:** 3062210.0
- **Functions:** 14/19 matched (target 18)
- **Missing functions:** `is_1bytes`, `f`, `sub`, `add`, `none_ors`
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

### 6. lexer

- **Target:** `lexer.Lexer [ZERO] [PROVENANCE-FALLBACK]`
- **Similarity:** 0.00
- **Dependents:** 2
- **Priority Score:** 2152910.0
- **Functions:** 9/21 matched (target 32)
- **Missing functions:** `from`, `map_lexeme_t`, `new`, `err_now`, `make_comment`, `wrap`, `string`, `int`, `parse_double_quoted_string`, `parse_single_quoted_string`, `unlex`, `fmt`
- **Types:** 5/8 matched (target 88)
- **Missing types:** `LexemeT`, `Lexeme`, `Item`
- **Tests:** 1/2 matched
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `tests:lexer.rs` vs expected `lexer.rs`
- **Proposed provenance header:** `// port-lint: tests lexer.rs` (current: `// port-lint: tests lexer.rs`)
- **Lint issues:** 2

### 7. error

- **Target:** `error.Error`
- **Similarity:** 0.52
- **Dependents:** 2
- **Priority Score:** 2022904.8
- **Functions:** 24/25 matched (target 32)
- **Missing functions:** `fmt`
- **Types:** 3/4 matched (target 14)
- **Missing types:** `Wrapped`

### 8. syntax.parse_error

- **Target:** `parseerror.ParseError`
- **Similarity:** 0.87
- **Dependents:** 2
- **Priority Score:** 2000201.2
- **Functions:** 1/1 matched
- **Missing functions:** _none_
- **Types:** 1/1 matched (target 3)
- **Missing types:** _none_

### 9. syntax.ast_load

- **Target:** `astload.AstLoad`
- **Similarity:** 1.00
- **Dependents:** 2
- **Priority Score:** 2000100.0
- **Functions:** 0/0 matched
- **Missing functions:** _none_
- **Types:** 1/1 matched
- **Missing types:** _none_

### 10. syntax.lint_suppressions

- **Target:** `lintsuppressions.LintSuppressions [ZERO]`
- **Similarity:** 0.00
- **Dependents:** 1
- **Priority Score:** 1011210.0
- **Functions:** 7/8 matched (target 7)
- **Missing functions:** `new`
- **Types:** 4/4 matched
- **Missing types:** _none_

### 11. syntax.type_expr

- **Target:** `typeexpr.TypeExpr [STUB]`
- **Similarity:** 0.00
- **Dependents:** 1
- **Priority Score:** 1010810.0
- **Functions:** 4/5 matched
- **Missing functions:** `from`
- **Types:** 3/3 matched (target 16)
- **Missing types:** _none_
- **TODOs:** 2

### 12. frame

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

### 13. convert_indices

- **Target:** `convertindices.ConvertIndices`
- **Similarity:** 0.75
- **Dependents:** 1
- **Priority Score:** 1000302.6
- **Functions:** 3/3 matched
- **Missing functions:** _none_
- **Types:** 0/0 matched
- **Missing types:** _none_

### 14. span_display

- **Target:** `spandisplay.SpanDisplay [ZERO]`
- **Similarity:** 0.00
- **Dependents:** 1
- **Priority Score:** 1000210.0
- **Functions:** 2/2 matched (target 4)
- **Missing functions:** _none_
- **Types:** 0/0 matched (target 6)
- **Missing types:** _none_

### 15. syntax.def

- **Target:** `def.Def`
- **Similarity:** 0.18
- **Dependents:** 0
- **Priority Score:** 152608.2
- **Functions:** 5/20 matched (target 5)
- **Missing functions:** `fails_dialect`, `fails`, `passes`, `test_params_unpack`, `test_params_noargs`, `test_star_cannot_be_last`, `test_star_then_args`, `test_star_then_kwargs`, `test_positional_only`, `test_positional_only_cannot_be_first`, `test_slash_slash`, `test_named_only_in_standard_dialect_def`, `test_named_only_in_standard_dialect_lambda`, `test_positional_only_in_standard_dialect_def`, `test_positional_only_in_standard_dialect_lambda`
- **Types:** 6/6 matched (target 9)
- **Missing types:** _none_
- **Tests:** 0/15 matched

### 16. syntax.module

- **Target:** `module.AstModule [ZERO]`
- **Similarity:** 0.00
- **Dependents:** 0
- **Priority Score:** 101810.0
- **Functions:** 7/16 matched (target 15)
- **Missing functions:** `codemap`, `dialect`, `create`, `parse_file`, `parse`, `f`, `go`, `test_locations`, `get`
- **Types:** 1/2 matched (target 3)
- **Missing types:** `AstModuleFields`
- **Tests:** 0/2 matched

### 17. syntax.ast

- **Target:** `ast.Ast [STUB]`
- **Similarity:** 0.00
- **Dependents:** 0
- **Priority Score:** 67510.0
- **Functions:** 12/12 matched (target 38)
- **Missing functions:** _none_
- **Types:** 57/63 matched (target 99)
- **Missing types:** `LoadPayload`, `IdentPayload`, `IdentAssignPayload`, `DefPayload`, `TypeExprPayload`, `ToAst`
- **TODOs:** 2

### 18. dot_format_parser

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

### 19. diagnostic

- **Target:** `diagnostic.Diagnostic`
- **Similarity:** 0.56
- **Dependents:** 0
- **Priority Score:** 21604.4
- **Functions:** 11/13 matched (target 12)
- **Missing functions:** `fmt`, `from`
- **Types:** 3/3 matched
- **Missing types:** _none_

### 20. cursors

- **Target:** `cursors.Cursors`
- **Similarity:** 0.16
- **Dependents:** 0
- **Priority Score:** 20908.4
- **Functions:** 5/7 matched
- **Missing functions:** `new`, `new_offset`
- **Types:** 2/2 matched
- **Missing types:** _none_

### 21. syntax.uniplate

- **Target:** `uniplate.Uniplate [ZERO]`
- **Similarity:** 0.00
- **Dependents:** 0
- **Priority Score:** 2210.0
- **Functions:** 20/20 matched (target 41)
- **Missing functions:** _none_
- **Types:** 2/2 matched (target 4)
- **Missing types:** _none_

### 22. syntax.grammar_util

- **Target:** `parser.GrammarUtil [STUB]`
- **Similarity:** 0.00
- **Dependents:** 0
- **Priority Score:** 1110.0
- **Functions:** 9/9 matched (target 10)
- **Missing functions:** _none_
- **Types:** 2/2 matched
- **Missing types:** _none_
- **TODOs:** 1

### 23. syntax.validate

- **Target:** `validate.Validate`
- **Similarity:** 0.86
- **Dependents:** 0
- **Priority Score:** 501.4
- **Functions:** 5/5 matched
- **Missing functions:** _none_
- **Types:** 0/0 matched
- **Missing types:** _none_

### 24. syntax.top_level_stmts

- **Target:** `toplevelstmts.TopLevelStmts`
- **Similarity:** 0.84
- **Dependents:** 0
- **Priority Score:** 301.6
- **Functions:** 3/3 matched (target 4)
- **Missing functions:** _none_
- **Types:** 0/0 matched
- **Missing types:** _none_

### 25. syntax.call

- **Target:** `call.Call`
- **Similarity:** 0.91
- **Dependents:** 0
- **Priority Score:** 300.9
- **Functions:** 1/1 matched
- **Missing functions:** _none_
- **Types:** 2/2 matched
- **Missing types:** _none_

### 26. syntax.payload_map

- **Target:** `payloadmap.PayloadMap [ZERO]`
- **Similarity:** 0.00
- **Dependents:** 0
- **Priority Score:** 210.0
- **Functions:** 1/1 matched (target 24)
- **Missing functions:** _none_
- **Types:** 1/1 matched
- **Missing types:** _none_

### 27. syntax.state

- **Target:** `state.ParserState`
- **Similarity:** 0.85
- **Dependents:** 0
- **Priority Score:** 201.5
- **Functions:** 1/1 matched
- **Missing functions:** _none_
- **Types:** 1/1 matched
- **Missing types:** _none_

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
./ast_distance --init-tasks ../../tmp/starlark_syntax rust ../../src/commonMain/kotlin/io/github/kotlinmania/starlarksyntax kotlin tasks.json ../../AGENTS.md

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
| `lib` | `Lib` | 0 | `src/lib.rs` | `Lib.kt` |

