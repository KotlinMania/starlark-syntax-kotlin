import XCTest
import StarlarkSyntax

final class StarlarkSyntaxExportTests: XCTestCase {
    func testSwiftModuleLoadsAndConstructsCodeMap() throws {
        let cm = codemap.CodeMap.Companion.shared.new(filename: "test.bzl", source: "x = 1\ny = 2\n")
        XCTAssertEqual(cm.filename(), "test.bzl")
        XCTAssertEqual(cm.fullSpan().len(), 12)
    }
}
