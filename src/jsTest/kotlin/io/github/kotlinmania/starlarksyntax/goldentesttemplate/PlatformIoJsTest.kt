// port-lint: ignore JS runtime regression test for browser env fallback behavior.
package io.github.kotlinmania.starlarksyntax.goldentesttemplate

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

private fun isBrowserRuntime(): Boolean {
    val windowRef: dynamic = js("typeof window !== 'undefined' ? window : undefined")
    return jsTypeOf(windowRef) != "undefined"
}

class PlatformIoJsTest {
    @Test
    fun testGetEnvReturnsFallbackInBrowser() {
        if (!isBrowserRuntime()) {
            return
        }

        assertEquals(".", platformGetEnv("CARGO_MANIFEST_DIR"))
        assertNull(platformGetEnv("NONEXISTENT_ENV_VAR_FOR_BROWSER_TEST"))
    }
}
