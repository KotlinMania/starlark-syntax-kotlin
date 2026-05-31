# HANDOFF — starlark-syntax-kotlin

_Status snapshot: 2026-05-30. Author: automated PR-review/CI-fix run. Read before resuming work._

## TL;DR

CI on PR **#53** (`chore/build-homogenization-part-2`) fails on 7 targets. The cause is **not** in this repo's build — it's that two sibling dependencies publish a **narrower Kotlin target matrix** than this project declares. The build body / template is fine. Fix belongs in the siblings (re-publish with the full target matrix), not here.

There is also unmerged **source WIP** on the local `newbuilds` branch that is coupled to this same dependency gap.

---

## 1. The failing PR

- **PR:** [#53 — Merge branch 'main' into chore/build-homogenization-part-2](https://github.com/KotlinMania/starlark-syntax-kotlin/pull/53)
- **Branch:** `chore/build-homogenization-part-2` → `main`
- **Mergeable:** `MERGEABLE / UNSTABLE` (no conflicts; blocked by failing checks)
- **Failing checks:** `Analyze (java-kotlin)`, `android-native`, `ios`, `linux`, `tvos`, `wasm`, `watchos`
- **Passing checks:** `macos`, `android`, `windows`, `js`, `CodeQL (neutral)`

## 2. Root cause (verified from CI logs + Maven Central)

The Linux job fails at dependency resolution before compiling:

```
> Task :kmpPartiallyResolvedDependenciesChecker
e: ❌ KMP Dependencies Resolution Failure   (×15)
Couldn't resolve dependency 'io.github.kotlinmania:starlarkmap-kotlin'  in 'linuxMain'/'nativeMain'/'appleMain'/'tvosMain'/'watchosMain'/'androidNativeMain'/'commonMain' for all target platforms.
Couldn't resolve dependency 'io.github.kotlinmania:lalrpop-util-kotlin' in '...'  (same source sets)
> Task :compileKotlinLinuxArm64 FAILED
> No matching variant errors ... variant-no-match
```

Declared in [`build.gradle.kts:134-135`](build.gradle.kts) (commonMain):

```kotlin
implementation("io.github.kotlinmania:starlarkmap-kotlin:0.1.2")
implementation("io.github.kotlinmania:lalrpop-util-kotlin:0.1.0")
```

**What's actually published** (checked via the Gradle `.module` metadata on Maven Central):

| Target declared by this repo | `starlarkmap-kotlin:0.1.2` | `lalrpop-util-kotlin:0.1.0` |
|---|---|---|
| jvm / android | ✅ | ✅ |
| js | ✅ | ✅ |
| iosArm64 / iosX64 / iosSimulatorArm64 | ✅ | ✅ |
| macosArm64 | ✅ | ✅ |
| macosX64 | ✅ | ❌ **missing** |
| mingwX64 (windows) | ✅ | ✅ |
| wasmJs | ✅ | ✅ |
| **linuxX64** | ✅ | ✅ |
| **linuxArm64** | ❌ **missing** | ❌ **missing** |
| **tvos\*** (Arm64/X64/SimulatorArm64) | ❌ **missing** | ❌ **missing** |
| **watchos\*** (Arm64/X64/SimulatorArm64/DeviceArm64) | ❌ **missing** | ❌ **missing** |
| **androidNative\*** | ❌ **missing** | ❌ **missing** |
| **wasmWasi** | ❌ **missing** | ❌ **missing** |

Because Kotlin's hierarchical source sets require a dependency to resolve for **every leaf target** in a shared source set, a single missing leaf (e.g. `linuxArm64`) makes the whole shared source set (`nativeMain`, and transitively `commonMain`) report "couldn't resolve … for all target platforms." That's why the errors name `commonMain` even though JVM/JS/Android compile fine.

**This is a sibling-publication gap, not a build-template defect.** Do not "fix" it by editing this repo's `build.gradle.kts` body, and do not silence it by dropping targets (that would deviate from the canonical target matrix — against the standing "template is law / no build deviation" rule).

## 3. Recommended fix (at the source)

Re-publish both siblings with the **full** target matrix this project (and the template) declares, then bump the versions here:

1. **`KotlinMania/starlarkmap-kotlin`** — add & publish: `linuxArm64`, `tvosArm64`, `tvosX64`, `tvosSimulatorArm64`, `watchosArm64`, `watchosX64`, `watchosSimulatorArm64`, `watchosDeviceArm64`, `androidNative*`, `wasmWasi`. (Currently at 0.1.2.)
2. **`KotlinMania/lalrpop-util-kotlin`** — same, **plus** the missing `macosX64`. (Currently at 0.1.0.)
3. Bump the two coordinates in [`build.gradle.kts:134-135`](build.gradle.kts) to the newly-published versions (and the matching `-jvm` codeql classpath pins at lines 281-282).
4. Re-run `ci.yml` on #53; the native/wasm targets should resolve and compile.

If publishing the full matrix isn't feasible short-term, the alternative is to align this repo's declared targets to the siblings' intersection — but that contradicts the canonical matrix and should be a deliberate decision, not a workaround.

## 4. Branch state (local checkout)

| Branch | Status | Notes |
|---|---|---|
| `chore/build-homogenization-part-2` *(current)* | clean, synced to origin | PR #53 head |
| `main` (local) | stale, 7 behind `origin/main` | fast-forward when convenient |
| `newbuilds` | **local-only, unmerged — contains source WIP** | see §5 |
| `wip/build-homogenization-deploy-backup` | local-only, **build noise only** | safe to delete: `git branch -D wip/build-homogenization-deploy-backup` |

Remote (`origin`) has only `main` and `chore/build-homogenization-part-2`. Four redundant/build-only local branches (`codex/use-defining-astload`, `codex/repair-manual-ci`, `automation/h22-1-starlark-syntax-repair`, `repocleanup`) were deleted during this run (all either fully merged into `main` or CI-only).

## 5. Unmerged source WIP — `newbuilds` (do not lose)

`newbuilds` (tip `688d708` "Use defining AstLoad type in module") carries a real source change in
`src/commonMain/kotlin/io/github/kotlinmania/starlarksyntax/syntax/module/AstModule.kt`:

- removes the locally-defined `AstLoad` class and imports the canonical `io.github.kotlinmania.starlarksyntax.syntax.astload.AstLoad`
- switches the `symbols` map construction from `associate { … }` to `io.github.kotlinmania.starlarkmap.smallmap.SmallMap.fromIterator(…)`

**Coupling to §2:** this change adds a deeper compile-time dependency on `starlarkmap-kotlin` (the `SmallMap` import), so it cannot compile on the native/wasm targets until starlarkmap publishes those variants. Land the sibling re-publish (§3) **before** trying to merge this code. The rest of `newbuilds`' commits are build-surface/CI churn and can be dropped; only the `AstModule.kt` change is worth salvaging.

## 6. Useful artifacts

- The failed-build Kotlin compiler logs (`.kotlin/errors/errors-*.log`) were committed onto `wip/build-homogenization-deploy-backup` if deeper local diagnostics are wanted before that branch is deleted.
- Reference CI run for #53: https://github.com/KotlinMania/starlark-syntax-kotlin/actions/runs/25773443810
