# Immediate Actions - High-Value Files

Based on AST analysis, here are the concrete next steps.

## Summary

- **Files Present:** 32/36 (88.9%)
- **Function parity:** 335/470 matched (target 524) — 71.3%
- **Class/type parity:** 126/142 matched (target 325) — 88.7%
- **Combined symbol parity:** 461/612 matched (target 849) — 75.3%
- **Average inline-code cosine:** 0.36 (function body across 32 matched files)
- **Average documentation cosine:** 0.75 (doc text across 32 matched files)
- **Cheat-zeroed Files:** 0
- **Critical Issues:** 28 files with <0.60 function similarity
- **Needs Review:** 1 files with 0.60-0.84 function similarity
- **Excellent:** 3 files with >=0.85 function similarity

## Priority 1: Fix Incomplete High-Dependency Files

### 1. codemap
- **Similarity:** 0.27 (needs 58% improvement)
- **Dependencies:** 14
- **Priority Score:** 14049407.0
- **Functions:** 75/77 matched (target 109)
- **Missing functions:** `len`, `from`
- **Types:** 15/17 matched (target 18)
- **Missing types:** `Output`, `Target`
- **Symbol Deficit:** 4 (functions: 2, types: 2)
- **Missing Tests:** 1 of 10 `#[test]` functions have no Kotlin counterpart
- **Action:** Deep review - likely missing major functionality

## Priority 2: Port Missing High-Value Files

Critical missing files (>10 dependencies):

No missing high-value files detected.

## Detailed Work Items

Every matched file is listed below with function and type symbol parity.

### 1. codemap

- **Target:** `codemap.CodeMap`
- **Similarity:** 0.27
- **Dependents:** 14
- **Priority Score:** 14049407.0
- **Functions:** 75/77 matched (target 109)
- **Missing functions:** `len`, `from`
- **Types:** 15/17 matched (target 18)
- **Missing types:** `Output`, `Target`
- **Tests:** 9/10 matched

### 2. eval_exception

- **Target:** `evalexception.EvalException`
- **Similarity:** 0.51
- **Dependents:** 8
- **Priority Score:** 8001105.0
- **Functions:** 10/10 matched
- **Missing functions:** _none_
- **Types:** 1/1 matched
- **Missing types:** _none_

### 3. dialect

- **Target:** `starlarksyntax.Dialect`
- **Similarity:** 0.33
- **Dependents:** 7
- **Priority Score:** 7000306.5
- **Functions:** 1/1 matched
- **Missing functions:** _none_
- **Types:** 2/2 matched
- **Missing types:** _none_

### 4. fast_string

- **Target:** `faststring.FastString`
- **Similarity:** 0.26
- **Dependents:** 3
- **Priority Score:** 3042207.5
- **Functions:** 16/19 matched (target 21)
- **Missing functions:** `sub`, `add`, `none_ors`
- **Types:** 2/3 matched
- **Missing types:** `Output`
- **Tests:** 3/4 matched

### 5. call_stack

- **Target:** `callstack.CallStack`
- **Similarity:** 0.28
- **Dependents:** 3
- **Priority Score:** 3000407.2
- **Functions:** 3/3 matched (target 4)
- **Missing functions:** _none_
- **Types:** 1/1 matched
- **Missing types:** _none_

### 6. golden_test_template

- **Target:** `goldentesttemplate.PlatformIoJs`
- **Similarity:** 0.11
- **Dependents:** 3
- **Priority Score:** 3000209.0
- **Functions:** 2/2 matched (target 37)
- **Missing functions:** _none_
- **Types:** 0/0 matched
- **Missing types:** _none_

### 7. error

- **Target:** `error.StarlarkError`
- **Similarity:** 0.20
- **Dependents:** 2
- **Priority Score:** 2093608.0
- **Functions:** 25/32 matched (target 33)
- **Missing functions:** `fmt`, `fmt`, `fmt`, `fmt`, `source`, `fmt`, `fmt`
- **Types:** 2/4 matched (target 14)
- **Missing types:** `Error`, `Wrapped`

### 8. lexer

- **Target:** `lexer.Lexer`
- **Similarity:** 0.22
- **Dependents:** 2
- **Priority Score:** 2063007.8
- **Functions:** 17/22 matched (target 58)
- **Missing functions:** `from`, `new`, `unlex`, `fmt`, `next`
- **Types:** 7/8 matched (target 102)
- **Missing types:** `Item`
- **Tests:** 1/2 matched

### 9. syntax.parse_error

- **Target:** `parseerror.ParseError`
- **Similarity:** 0.31
- **Dependents:** 2
- **Priority Score:** 2000206.9
- **Functions:** 1/1 matched
- **Missing functions:** _none_
- **Types:** 1/1 matched (target 3)
- **Missing types:** _none_

### 10. syntax.parser

- **Target:** `parser.Parser`
- **Similarity:** 1.00
- **Dependents:** 2
- **Priority Score:** 2000200.0
- **Functions:** 0/0 matched
- **Missing functions:** _none_
- **Types:** 2/2 matched (target 5)
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

- **Target:** `typeexpr.TypeExpr`
- **Similarity:** 0.31
- **Dependents:** 1
- **Priority Score:** 1030806.9
- **Functions:** 4/5 matched
- **Missing functions:** `from`
- **Types:** 1/3 matched (target 16)
- **Missing types:** `TypePathP`, `TypeExprUnpackP`

### 13. frame

- **Target:** `frame.Frame`
- **Similarity:** 0.19
- **Dependents:** 1
- **Priority Score:** 1010508.1
- **Functions:** 3/4 matched
- **Missing functions:** `fmt`
- **Types:** 1/1 matched (target 2)
- **Missing types:** _none_
- **Tests:** 1/1 matched

### 14. syntax.lint_suppressions

- **Target:** `lintsuppressions.LintSuppressions`
- **Similarity:** 0.35
- **Dependents:** 1
- **Priority Score:** 1001206.4
- **Functions:** 8/8 matched
- **Missing functions:** _none_
- **Types:** 4/4 matched
- **Missing types:** _none_

### 15. convert_indices

- **Target:** `convertindices.ConvertIndices`
- **Similarity:** 0.51
- **Dependents:** 1
- **Priority Score:** 1000304.9
- **Functions:** 3/3 matched
- **Missing functions:** _none_
- **Types:** 0/0 matched
- **Missing types:** _none_

### 16. span_display

- **Target:** `spandisplay.SpanDisplay`
- **Similarity:** 0.44
- **Dependents:** 1
- **Priority Score:** 1000205.6
- **Functions:** 2/2 matched (target 4)
- **Missing functions:** _none_
- **Types:** 0/0 matched (target 6)
- **Missing types:** _none_

### 17. syntax.ast

- **Target:** `ast.Ast`
- **Similarity:** 0.17
- **Dependents:** 0
- **Priority Score:** 68808.3
- **Functions:** 25/25 matched (target 53)
- **Missing functions:** _none_
- **Types:** 57/63 matched (target 106)
- **Missing types:** `LoadPayload`, `IdentPayload`, `IdentAssignPayload`, `DefPayload`, `TypeExprPayload`, `ToAst`

### 18. syntax.module

- **Target:** `module.AstModule`
- **Similarity:** 0.13
- **Dependents:** 0
- **Priority Score:** 62008.7
- **Functions:** 12/18 matched (target 22)
- **Missing functions:** `f`, `statement`, `go`, `f`, `test_locations`, `get`
- **Types:** 2/2 matched (target 3)
- **Missing types:** _none_
- **Tests:** 0/2 matched

### 19. dot_format_parser

- **Target:** `dotformatparser.DotFormatParser`
- **Similarity:** 0.18
- **Dependents:** 0
- **Priority Score:** 41808.2
- **Functions:** 9/12 matched (target 13)
- **Missing functions:** `new`, `new`, `deref`
- **Types:** 5/6 matched (target 9)
- **Missing types:** `Target`
- **Tests:** 2/2 matched

### 20. syntax.def

- **Target:** `def.Def`
- **Similarity:** 0.66
- **Dependents:** 0
- **Priority Score:** 32603.4
- **Functions:** 17/20 matched
- **Missing functions:** `fails_dialect`, `fails`, `passes`
- **Types:** 6/6 matched (target 10)
- **Missing types:** _none_
- **Tests:** 12/15 matched

### 21. diagnostic

- **Target:** `diagnostic.Diagnostic`
- **Similarity:** 0.21
- **Dependents:** 0
- **Priority Score:** 31707.9
- **Functions:** 11/14 matched (target 12)
- **Missing functions:** `fmt`, `fmt`, `from`
- **Types:** 3/3 matched
- **Missing types:** _none_

### 22. syntax.uniplate

- **Target:** `uniplate.Uniplate`
- **Similarity:** 0.17
- **Dependents:** 0
- **Priority Score:** 24408.3
- **Functions:** 40/42 matched (target 41)
- **Missing functions:** `recurse`, `recurse`
- **Types:** 2/2 matched (target 4)
- **Missing types:** _none_

### 23. slice_vec_ext

- **Target:** `slicevecext.SliceVecExt`
- **Similarity:** 0.11
- **Dependents:** 0
- **Priority Score:** 20808.9
- **Functions:** 4/5 matched (target 6)
- **Missing functions:** `map`
- **Types:** 2/3 matched (target 2)
- **Missing types:** `Item`

### 24. cursors

- **Target:** `cursors.Cursors`
- **Similarity:** 0.03
- **Dependents:** 0
- **Priority Score:** 11109.7
- **Functions:** 8/9 matched
- **Missing functions:** `new`
- **Types:** 2/2 matched
- **Missing types:** _none_

### 25. syntax.payload_map

- **Target:** `payloadmap.PayloadMap`
- **Similarity:** 0.18
- **Dependents:** 0
- **Priority Score:** 1608.2
- **Functions:** 15/15 matched (target 24)
- **Missing functions:** _none_
- **Types:** 1/1 matched
- **Missing types:** _none_

### 26. syntax.grammar_util

- **Target:** `parser.GrammarUtil`
- **Similarity:** 0.42
- **Dependents:** 0
- **Priority Score:** 1105.8
- **Functions:** 9/9 matched (target 10)
- **Missing functions:** _none_
- **Types:** 2/2 matched
- **Missing types:** _none_

### 27. syntax.parser_lalrpop

- **Target:** `parser.ParserLalrpop`
- **Similarity:** 0.30
- **Dependents:** 0
- **Priority Score:** 507.0
- **Functions:** 4/4 matched (target 5)
- **Missing functions:** _none_
- **Types:** 1/1 matched (target 2)
- **Missing types:** _none_
- **Tests:** 1/1 matched

### 28. syntax.validate

- **Target:** `validate.Validate`
- **Similarity:** 0.43
- **Dependents:** 0
- **Priority Score:** 505.7
- **Functions:** 5/5 matched
- **Missing functions:** _none_
- **Types:** 0/0 matched
- **Missing types:** _none_

### 29. syntax.top_level_stmts

- **Target:** `toplevelstmts.TopLevelStmts`
- **Similarity:** 0.25
- **Dependents:** 0
- **Priority Score:** 407.5
- **Functions:** 4/4 matched
- **Missing functions:** _none_
- **Types:** 0/0 matched
- **Missing types:** _none_

### 30. syntax.call

- **Target:** `call.Call`
- **Similarity:** 0.51
- **Dependents:** 0
- **Priority Score:** 304.9
- **Functions:** 1/1 matched
- **Missing functions:** _none_
- **Types:** 2/2 matched
- **Missing types:** _none_

### 31. syntax.state

- **Target:** `state.ParserState`
- **Similarity:** 0.51
- **Dependents:** 0
- **Priority Score:** 204.9
- **Functions:** 1/1 matched
- **Missing functions:** _none_
- **Types:** 1/1 matched
- **Missing types:** _none_

### 32. syntax

- **Target:** `syntax.Syntax`
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
| `lib` | `Lib` | 0 | `lib.rs` | `Lib.kt` |

