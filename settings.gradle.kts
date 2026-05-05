pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
    plugins { kotlin("multiplatform") version "2.3.21" }
}

plugins { id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0" }

dependencyResolutionManagement {
    repositories {
        google()
        maven("https://repo1.maven.org/maven2/")
        mavenCentral()
    }
}

rootProject.name = "starlark-syntax"

// Until these sibling ports publish (or if Maven resolution is unavailable
// locally), build them from adjacent checkouts and substitute them for the
// Maven coordinates used in build.gradle.kts.
includeBuild("../starlarkmap-kotlin") {
    dependencySubstitution {
        substitute(module("io.github.kotlinmania:starlarkmap-kotlin")).using(project(":"))
    }
}
includeBuild("../lalrpop-kotlin") {
    dependencySubstitution {
        substitute(module("io.github.kotlinmania:lalrpop-kotlin")).using(project(":"))
    }
}
