import Testing
import StarlarkSyntax

@Suite("StarlarkSyntaxExportTests")
struct StarlarkSyntaxExportTests {
    @Test func testSwiftModuleLoads() throws {
        #expect(true, "StarlarkSyntax swift module imported cleanly")
    }
}
