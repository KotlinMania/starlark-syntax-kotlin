#!/usr/bin/env python
"""drift_report — per-file Rust↔Kotlin function-name diff for a Kotlin port.

Walks every .kt file under the project's commonMain tree, reads its
`// port-lint: source <path>` header to locate the upstream .rs file,
extracts the function-name set from each side, and prints any file where
the two sets disagree (in either direction).

Snake/camel-case is normalized so `validate_module` ↔ `validateModule`
do not show as drift. Names that appear in only one side are reported.

Usage:
    python tools/drift_report.py [--project DIR]

Exit code is 0 even when drift exists — the printed report is the output.
"""
from __future__ import annotations

import argparse
import os
import re
import sys
from collections import defaultdict

KT_ROOT_DEFAULT = "src/commonMain/kotlin"

PORT_LINT_RE = re.compile(r"^\s*//\s*port-lint:\s*source\s+(\S+)")
PORT_LINT_IGNORE_RE = re.compile(r"^\s*//\s*port-lint:\s*ignore")
RS_FN_NAME_RE = re.compile(r"^\s*(?:pub(?:\([^)]+\))?\s+)?(?:async\s+)?(?:unsafe\s+)?(?:extern\s+(?:\"[^\"]+\"\s+)?)?fn\s+([A-Za-z_][A-Za-z0-9_]*)")
KT_FUN_NAME_RE = re.compile(
    r"^\s*(?:(?:public|private|internal|protected|override|open|final|inline|"
    r"infix|operator|tailrec|suspend|external|abstract)\s+)*"
    r"fun(?:\s*<[^>]+>)?\s+(?:[A-Za-z_][A-Za-z0-9_<>,?.\s]*\.\s*)?`?([A-Za-z_][A-Za-z0-9_]*)`?"
)


def snake_to_camel(name: str) -> str:
    parts = name.lstrip("_").split("_")
    if not parts:
        return name
    head = parts[0]
    return head + "".join(p[:1].upper() + p[1:] for p in parts[1:])


def fn_names_rust(path: str) -> set[str]:
    out: set[str] = set()
    try:
        with open(path) as f:
            for line in f:
                m = RS_FN_NAME_RE.match(line)
                if m:
                    out.add(m.group(1))
    except OSError:
        pass
    return out


def fn_names_kotlin(path: str) -> set[str]:
    out: set[str] = set()
    try:
        with open(path) as f:
            for line in f:
                m = KT_FUN_NAME_RE.match(line)
                if m:
                    out.add(m.group(1))
    except OSError:
        pass
    return out


def read_port_lint(kt_path: str) -> tuple[str | None, bool]:
    """Return (relative .rs path or None, is_ignore)."""
    try:
        with open(kt_path) as f:
            for _ in range(20):
                line = f.readline()
                if not line:
                    break
                if PORT_LINT_IGNORE_RE.match(line):
                    return None, True
                m = PORT_LINT_RE.match(line)
                if m:
                    return m.group(1), False
    except OSError:
        pass
    return None, False


def find_tmp_root(project_dir: str) -> str | None:
    tmp = os.path.join(project_dir, "tmp")
    if not os.path.isdir(tmp):
        return None
    # Most ports have tmp/<crate>/ as the upstream root (e.g. tmp/regex-syntax/,
    # tmp/starlark_syntax/). Pick the first directory under tmp/.
    for entry in sorted(os.listdir(tmp)):
        full = os.path.join(tmp, entry)
        if os.path.isdir(full):
            return full
    return None


def main() -> int:
    ap = argparse.ArgumentParser(description="Per-file Rust↔Kotlin function-name drift report.")
    ap.add_argument("--project", default=".", help="Project root (default: cwd)")
    ap.add_argument("--kt-root", default=KT_ROOT_DEFAULT, help="Kotlin source root relative to project")
    args = ap.parse_args()

    project = os.path.abspath(args.project)
    kt_root = os.path.join(project, args.kt_root)
    if not os.path.isdir(kt_root):
        print(f"error: kotlin source root not found: {kt_root}", file=sys.stderr)
        return 2

    tmp_root = find_tmp_root(project)
    if tmp_root is None:
        print(f"error: no tmp/<crate>/ directory under {project}", file=sys.stderr)
        return 2

    drift_files: list[tuple[str, str, set[str], set[str]]] = []
    no_lint: list[str] = []
    missing_rs: list[tuple[str, str]] = []
    ignored = 0
    clean = 0

    for dirpath, _, filenames in os.walk(kt_root):
        for fn in filenames:
            if not fn.endswith(".kt"):
                continue
            kt_path = os.path.join(dirpath, fn)
            rs_rel, is_ignore = read_port_lint(kt_path)
            if is_ignore:
                ignored += 1
                continue
            if rs_rel is None:
                no_lint.append(os.path.relpath(kt_path, project))
                continue
            rs_path = os.path.join(tmp_root, rs_rel)
            if not os.path.isfile(rs_path):
                missing_rs.append((os.path.relpath(kt_path, project), rs_rel))
                continue

            rs_names = fn_names_rust(rs_path)
            kt_names = fn_names_kotlin(kt_path)
            rs_camel = {snake_to_camel(n) for n in rs_names}
            kt_alt = set(kt_names)

            rs_only = sorted(n for n in rs_names if snake_to_camel(n) not in kt_alt and n not in kt_alt)
            kt_only = sorted(n for n in kt_names if n not in rs_camel and n not in rs_names)

            if rs_only or kt_only:
                drift_files.append((
                    os.path.relpath(kt_path, project),
                    rs_rel,
                    set(rs_only),
                    set(kt_only),
                ))
            else:
                clean += 1

    total = clean + len(drift_files) + len(no_lint) + len(missing_rs)
    print(f"# drift_report — {project}")
    print(f"#")
    print(f"# Kotlin files scanned : {total + ignored}")
    print(f"# port-lint: ignore    : {ignored}")
    print(f"# clean (no drift)     : {clean}")
    print(f"# drift detected       : {len(drift_files)}")
    print(f"# port-lint missing    : {len(no_lint)}")
    print(f"# upstream .rs missing : {len(missing_rs)}")
    print()

    if drift_files:
        print(f"## Files with drift ({len(drift_files)})")
        print()
        drift_files.sort(key=lambda t: -(len(t[2]) + len(t[3])))
        for kt_rel, rs_rel, rs_only, kt_only in drift_files:
            print(f"--- {kt_rel}")
            print(f"    upstream: tmp/.../{rs_rel}")
            if rs_only:
                print(f"    rust-only ({len(rs_only)}):")
                for n in sorted(rs_only):
                    print(f"      - {n}")
            if kt_only:
                print(f"    kotlin-only ({len(kt_only)}):")
                for n in sorted(kt_only):
                    print(f"      + {n}")
            print()

    if missing_rs:
        print(f"## Kotlin files whose port-lint source does not exist under tmp/ ({len(missing_rs)})")
        print()
        for kt_rel, rs_rel in missing_rs:
            print(f"  {kt_rel}  →  tmp/.../{rs_rel}")
        print()

    if no_lint:
        print(f"## Kotlin files missing port-lint header ({len(no_lint)})")
        print()
        for kt_rel in no_lint:
            print(f"  {kt_rel}")
        print()

    return 0


if __name__ == "__main__":
    sys.exit(main())
