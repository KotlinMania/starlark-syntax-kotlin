// swift-tools-version: 6.0
import PackageDescription

let package = Package(
    name: "SwiftTestHarness",
    platforms: [.macOS(.v15)],
    dependencies: [
        .package(name: "StarlarkSyntax", path: "../build/SPMPackage/macosArm64/Debug")
    ],
    targets: [
        .testTarget(
            name: "SwiftTestHarnessTests",
            dependencies: [
                .product(name: "StarlarkSyntaxLibrary", package: "StarlarkSyntax")
            ],
            linkerSettings: [
                .unsafeFlags([
                    "-L", "../build/swift-test",
                    "-lStarlarkSyntax",
                ]),
            ]
        ),
    ]
)