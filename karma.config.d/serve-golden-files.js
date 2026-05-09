// Serve golden test fixture files as static assets so that browser tests can
// fetch them via XHR.  Karma maps every entry in config.files whose
// `included` flag is false to a URL under /base/<path>, which is exactly
// what browserReadUtf8File() requests.
const path = require('path');
const fs = require('fs');

let repoRoot = config.basePath || '.';
while (!fs.existsSync(path.join(repoRoot, 'src', 'lexer_tests'))) {
    const parent = path.dirname(repoRoot);
    if (parent === repoRoot) {
        break;
    }
    repoRoot = parent;
}
config.basePath = repoRoot;

config.files.push({
    pattern: 'src/**/*.golden',
    watched: false,
    included: false,
    served: true,
});
