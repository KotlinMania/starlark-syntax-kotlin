---
name: \U0001F41B Browser Test Failure: ReferenceError 'process'
about: Track and fix browser test errors due to 'process is not defined'.
labels: bug
---

### Description

Several browser-based tests running in the CI pipeline fail due to the usage of `process`, which is a Node.js-specific global object that does not exist in browser environments. Affected tests indicate the error:

```log
ReferenceError: process is not defined
```

### Steps to Reproduce
1. Trigger the `CI` workflow (located in `.github/workflows/ci.yml`).
2. Observe `LexerTest` failures, especially on cases such as `testIntLit`, `testIndentation`, and others.
3. View full logs at [CI Job Logs](https://github.com/KotlinMania/starlark-syntax-kotlin/actions/runs/25582983494/job/75123088550).

### Impacted Files/Sections:
- `platformGetEnv` and its references.
- Karma or other test setups referencing browser rules and dependencies tied to `process.env`.

### Proposed Fix
- Add a `process` polyfill for browser environments into the testing setup. For instance:

```javascript
// In your Karma test setup file
if (typeof process === "undefined") {
  window.process = {
    env: {}, // Add required mock environment variables here
  };
}
```

- Update the Karma configuration file (`karma.conf.js`) to include the above setup file before any test execution:

```javascript
files: ["path/to/karma-setup.js", "test/**/*.js"]
```

### Supported References
Ensure complete steps to debug Karma or Gradle. Action checked fallback branches.