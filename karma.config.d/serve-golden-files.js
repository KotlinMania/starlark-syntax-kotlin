// Serve golden test fixture files as static assets so that browser tests can
// fetch them via XHR.  Karma maps every entry in config.files whose
// `included` flag is false to a URL under /base/<path>, which is exactly
// what browserReadUtf8File() requests.
config.files.push({
    pattern: 'src/**/*.golden',
    watched: false,
    included: false,
    served: true,
});
