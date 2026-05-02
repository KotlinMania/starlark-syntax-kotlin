# Immediate Actions - High-Value Files

Based on AST analysis, here are the concrete next steps.

## Summary

- **Files Present:** 14/37 (37.8%)
- **Function parity:** 82/426 matched (target 154) — 19.2%
- **Class/type parity:** 62/143 matched (target 216) — 43.4%
- **Combined symbol parity:** 144/569 matched (target 370) — 25.3%
- **Cheat-zeroed Files:** 3
- **Critical Issues:** 10 files with <0.60 function similarity

## Priority 1: Fix Incomplete High-Dependency Files

### 1. codemap
- **Similarity:** 0.26 (needs 59% improvement)
- **Dependencies:** 14
- **Priority Score:** 14397307.0
- **Functions:** 22/56 matched (target 42)
- **Missing functions:** `new`, `sub`, `add_assign`, `begin`, `end`, `len`, `as_ref`, `deref`, `deref_mut`, `to_codemap`, `default`, `fmt`, `eq`, `hash`, `empty_static`, `byte_at`, `source`, `line_span_trim_newline`, `source_line_at_pos`, `_testing_parse`, `partial_cmp`, `cmp`, `to_file_span`, `from`, `from_span`, `test_codemap`, `test_multibyte`, `test_line_col_span_display_point`, `test_line_col_span_display_single_line_span`, `test_line_col_span_display_multi_line_span`, `test_native_code_map`, `test_resolved_span_contains`, `test_span_intersects`, `test_resolved_file_span_to_begin_resolved_file_line`
- **Types:** 12/17 matched (target 12)
- **Missing types:** `Output`, `Target`, `CodeMapImpl`, `CodeMapData`, `NativeCodeMap`
- **Symbol Deficit:** 39 (functions: 34, types: 5)
- **Missing Tests:** 10 of 10 `#[test]` functions have no Kotlin counterpart
- **Action:** Deep review - likely missing major functionality

## Priority 2: Port Missing High-Value Files

Critical missing files (>10 dependencies):

No missing high-value files detected.

## Detailed Work Items

Every matched file is listed below with function and type symbol parity.

### 1. codemap

- **Target:** `codemap.Span`
- **Similarity:** 0.26
- **Dependents:** 14
- **Priority Score:** 14397307.0
- **Functions:** 22/56 matched (target 42)
- **Missing functions:** `new`, `sub`, `add_assign`, `begin`, `end`, `len`, `as_ref`, `deref`, `deref_mut`, `to_codemap`, `default`, `fmt`, `eq`, `hash`, `empty_static`, `byte_at`, `source`, `line_span_trim_newline`, `source_line_at_pos`, `_testing_parse`, `partial_cmp`, `cmp`, `to_file_span`, `from`, `from_span`, `test_codemap`, `test_multibyte`, `test_line_col_span_display_point`, `test_line_col_span_display_single_line_span`, `test_line_col_span_display_multi_line_span`, `test_native_code_map`, `test_resolved_span_contains`, `test_span_intersects`, `test_resolved_file_span_to_begin_resolved_file_line`
- **Types:** 12/17 matched (target 12)
- **Missing types:** `Output`, `Target`, `CodeMapImpl`, `CodeMapData`, `NativeCodeMap`
- **Tests:** 0/10 matched

### 2. eval_exception

- **Target:** `evalexception.EvalException [ZERO]`
- **Similarity:** 0.00
- **Dependents:** 8
- **Priority Score:** 8051110.0
- **Functions:** 5/10 matched (target 5)
- **Missing functions:** `new_unknown_span`, `internal_error`, `parser_error`, `_testing_loc`, `from`
- **Types:** 1/1 matched
- **Missing types:** _none_

### 3. dialect

- **Target:** `dialect.Dialect [ZERO]`
- **Similarity:** 0.00
- **Dependents:** 7
- **Priority Score:** 7010310.0
- **Functions:** 0/1 matched (target 0)
- **Missing functions:** `default`
- **Types:** 2/2 matched
- **Missing types:** _none_

### 4. lexer

- **Target:** `lexer.Lexer [ZERO]`
- **Similarity:** 0.00
- **Dependents:** 2
- **Priority Score:** 2162910.0
- **Functions:** 8/21 matched (target 32)
- **Missing functions:** `from`, `map_lexeme_t`, `new`, `err_now`, `make_comment`, `wrap`, `string`, `int`, `parse_double_quoted_string`, `parse_single_quoted_string`, `unlex`, `fmt`, `test_is_valid_identifier`
- **Types:** 5/8 matched (target 87)
- **Missing types:** `LexemeT`, `Lexeme`, `Item`
- **Tests:** 0/2 matched
- **Lint issues:** 1

### 5. error

- **Target:** `error.Error`
- **Similarity:** 0.24
- **Dependents:** 2
- **Priority Score:** 2122907.5
- **Functions:** 15/25 matched (target 19)
- **Missing functions:** `into_anyhow`, `fmt`, `eprint`, `fmt_impl`, `from`, `into_anyhow_result`, `internal_error_impl`, `other_error_impl`, `value_error_impl`, `function_error_impl`
- **Types:** 2/4 matched (target 12)
- **Missing types:** `Wrapped`, `StarlarkResultExt`

### 6. frame

- **Target:** `frame.Frame`
- **Similarity:** 0.37
- **Dependents:** 1
- **Priority Score:** 1020506.2
- **Functions:** 2/4 matched (target 3)
- **Missing functions:** `fmt`, `test_truncate_snippet`
- **Types:** 1/1 matched
- **Missing types:** _none_
- **Tests:** 0/1 matched

### 7. syntax.type_expr

- **Target:** `typeexpr.TypeExpr`
- **Similarity:** 0.70
- **Dependents:** 1
- **Priority Score:** 1010803.0
- **Functions:** 4/5 matched (target 6)
- **Missing functions:** `from`
- **Types:** 3/3 matched (target 16)
- **Missing types:** _none_

### 8. syntax.ast

- **Target:** `ast.Ast`
- **Similarity:** 0.21
- **Dependents:** 0
- **Priority Score:** 427507.9
- **Functions:** 7/12 matched (target 19)
- **Missing functions:** `ast`, `expr_mut`, `fmt`, `comma_separated_fmt`, `fmt_with_tab`
- **Types:** 26/63 matched (target 69)
- **Missing types:** `LoadPayload`, `IdentPayload`, `IdentAssignPayload`, `DefPayload`, `TypeExprPayload`, `Expr`, `TypeExpr`, `AssignTarget`, `AssignIdent`, `Ident`, `Clause`, `ForClause`, `Argument`, `Parameter`, `Stmt`, `AstExprP`, `AstTypeExprP`, `AstAssignTargetP`, `AstAssignIdentP`, `AstIdentP`, `AstArgumentP`, `AstParameterP`, `AstStmtP`, `AstFStringP`, `AstExpr`, `AstTypeExpr`, `AstAssignTarget`, `AstAssignIdent`, `AstIdent`, `AstArgument`, `AstString`, `AstParameter`, `AstInt`, `AstFloat`, `AstFString`, `AstStmt`, `ToAst`

### 9. diagnostic

- **Target:** `diagnostic.Diagnostic`
- **Similarity:** 0.03
- **Dependents:** 0
- **Priority Score:** 141609.8
- **Functions:** 1/13 matched (target 1)
- **Missing functions:** `new_spanned`, `new_empty`, `inner`, `into_inner`, `span`, `call_stack`, `set_span`, `set_call_stack`, `fmt`, `from`, `get_display_list`, `diagnostic_display`
- **Types:** 1/3 matched (target 1)
- **Missing types:** `WithDiagnosticInner`, `Diagnostic`

### 10. syntax.module

- **Target:** `module.AstModule`
- **Similarity:** 0.22
- **Dependents:** 0
- **Priority Score:** 111807.8
- **Functions:** 6/16 matched (target 13)
- **Missing functions:** `codemap`, `statement`, `dialect`, `create`, `parse_file`, `f`, `go`, `is_suppressed`, `test_locations`, `get`
- **Types:** 1/2 matched (target 3)
- **Missing types:** `AstModuleFields`
- **Tests:** 0/2 matched

### 11. dot_format_parser

- **Target:** `dotformatparser.DotFormatParser`
- **Similarity:** 0.18
- **Dependents:** 0
- **Priority Score:** 101708.2
- **Functions:** 3/11 matched (target 3)
- **Missing functions:** `new`, `eat`, `pos`, `rem`, `original`, `deref`, `test_parser_position`, `test_failure`
- **Types:** 4/6 matched (target 7)
- **Missing types:** `StringView`, `Target`
- **Tests:** 0/2 matched

### 12. syntax.grammar_util

- **Target:** `parser.GrammarUtil`
- **Similarity:** 0.62
- **Dependents:** 0
- **Priority Score:** 21103.8
- **Functions:** 7/9 matched
- **Missing functions:** `reject_unparenthesized_tuple_trailing_comma`, `err`
- **Types:** 2/2 matched (target 3)
- **Missing types:** _none_

### 13. syntax.call

- **Target:** `call.Call`
- **Similarity:** 0.73
- **Dependents:** 0
- **Priority Score:** 10302.7
- **Functions:** 1/1 matched
- **Missing functions:** _none_
- **Types:** 1/2 matched (target 1)
- **Missing types:** `ArgsStage`
- **Lint issues:** 1

### 14. syntax.state

- **Target:** `state.ParserState`
- **Similarity:** 0.77
- **Dependents:** 0
- **Priority Score:** 202.3
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
