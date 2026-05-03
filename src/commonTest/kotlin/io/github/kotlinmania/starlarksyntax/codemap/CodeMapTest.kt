// port-lint: source src/codemap.rs
package io.github.kotlinmania.starlarksyntax.codemap

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue
import kotlin.test.fail

class CodeMapTest {
    @Test
    fun testCodemap() {
        val source = "abcd\nefghij\nqwerty"
        val codemap = CodeMap.new("test1.rs", source)
        val start = codemap.fullSpan().begin()

        // Test filename().
        assertEquals("test1.rs", codemap.filename())

        // Test findLineCol().
        assertEquals(ResolvedPos(line = 0, column = 0), codemap.findLineCol(start))
        assertEquals(ResolvedPos(line = 0, column = 4), codemap.findLineCol(start + 4))
        assertEquals(ResolvedPos(line = 1, column = 0), codemap.findLineCol(start + 5))
        assertEquals(ResolvedPos(line = 2, column = 4), codemap.findLineCol(start + 16))

        // Test source() and num lines.
        assertEquals(source, codemap.source())
        when (val impl = codemap.impl) {
            is CodeMapImpl.Real -> assertEquals(3, impl.data.lines.size)
            else -> fail("Expected Real CodeMap")
        }

        // Test generic properties on each line.
        for (line in 0..2) {
            val lineStr = codemap.sourceLine(line)
            val lineSpan = codemap.lineSpan(line)
            // The lineStr omits trailing newlines.
            assertEquals(lineStr.length + if (line < 2) 1 else 0, lineSpan.len())
            assertEquals(source.lines()[line], lineStr)
            assertEquals(line, codemap.findLine(lineSpan.begin()))
            // The final character might be a newline, which is counted as the next line.
            // Not sure this is a good thing!
            val end = Pos(lineSpan.end().value - 1)
            assertEquals(line, codemap.findLine(end))
            assertEquals(ResolvedPos(line = line, column = 0), codemap.findLineCol(lineSpan.begin()))
            assertEquals(
                ResolvedPos(line = line, column = lineSpan.len() - 1),
                codemap.findLineCol(end),
            )
        }
        assertNull(codemap.lineSpanOpt(4))
    }

    @Test
    fun testMultibyte() {
        val content = "65°00′N 18°00′W 汉语\n🔬"
        val codemap = CodeMap.new("<test>", content)

        assertEquals(
            ResolvedPos(line = 0, column = 15),
            codemap.findLineCol(codemap.fullSpan().begin() + 21),
        )
        assertEquals(
            ResolvedPos(line = 0, column = 18),
            codemap.findLineCol(codemap.fullSpan().begin() + 28),
        )
        assertEquals(
            ResolvedPos(line = 1, column = 1),
            codemap.findLineCol(codemap.fullSpan().begin() + 33),
        )
    }

    @Test
    fun testLineColSpanDisplayPoint() {
        val lineCol = ResolvedPos(line = 0, column = 0)
        val span = ResolvedSpan.fromSpan(lineCol, lineCol)
        assertEquals("1:1", span.toString())
    }

    @Test
    fun testLineColSpanDisplaySingleLineSpan() {
        val begin = ResolvedPos(line = 0, column = 0)
        val end = ResolvedPos(line = 0, column = 32)
        val span = ResolvedSpan.fromSpan(begin, end)
        assertEquals("1:1-33", span.toString())
    }

    @Test
    fun testLineColSpanDisplayMultiLineSpan() {
        val begin = ResolvedPos(line = 0, column = 0)
        val end = ResolvedPos(line = 2, column = 32)
        val span = ResolvedSpan.fromSpan(begin, end)
        assertEquals("1:1-3:33", span.toString())
    }

    @Test
    fun testNativeCodeMap() {
        val codemap = NativeCodeMap.toCodemap(NativeCodeMap.new("test.rs", 100, 200))
        assertEquals(NativeCodeMap.SOURCE, codemap.source())
        assertEquals(NativeCodeMap.SOURCE, codemap.sourceLine(100))
        assertEquals(
            ResolvedSpan(
                begin = ResolvedPos(line = 100, column = 200),
                end = ResolvedPos(line = 100, column = 200 + NativeCodeMap.SOURCE.length),
            ),
            codemap.resolveSpan(codemap.fullSpan()),
        )
    }

    @Test
    fun testResolvedSpanContains() {
        val span = ResolvedSpan(
            begin = ResolvedPos(line = 2, column = 3),
            end = ResolvedPos(line = 4, column = 5),
        )
        assertFalse(span.contains(ResolvedPos(line = 0, column = 7)))
        assertFalse(span.contains(ResolvedPos(line = 2, column = 2)))
        assertTrue(span.contains(ResolvedPos(line = 2, column = 3)))
        assertTrue(span.contains(ResolvedPos(line = 2, column = 9)))
        assertTrue(span.contains(ResolvedPos(line = 3, column = 1)))
        assertTrue(span.contains(ResolvedPos(line = 4, column = 4)))
        assertTrue(span.contains(ResolvedPos(line = 4, column = 5)))
        assertFalse(span.contains(ResolvedPos(line = 4, column = 6)))
        assertFalse(span.contains(ResolvedPos(line = 5, column = 0)))
    }

    @Test
    fun testSpanIntersects() {
        val span = Span.new(Pos(2), Pos(4))
        // s: |---|
        // o:      |---|
        assertFalse(span.intersects(Span.new(Pos(5), Pos(7))))

        // s: |---|
        // o:     |---|
        assertTrue(span.intersects(Span.new(Pos(4), Pos(6))))

        // s: |---|
        // o:    |---|
        assertTrue(span.intersects(Span.new(Pos(3), Pos(5))))

        // s: |---|
        // o: |---|
        assertTrue(span.intersects(Span.new(Pos(2), Pos(4))))

        // s:   |---|
        // o: |---|
        assertTrue(span.intersects(Span.new(Pos(1), Pos(3))))

        // s:     |---|
        // o: |---|
        assertTrue(span.intersects(Span.new(Pos(0), Pos(2))))

        // s:     |---|
        // o: |--|
        assertFalse(span.intersects(Span.new(Pos(0), Pos(1))))

        val largeSpan = Span.new(Pos(2), Pos(8))

        // s:  |-------|
        // o:    |---|
        assertTrue(largeSpan.intersects(span))

        // s:    |---|
        // o:  |-------|
        assertTrue(span.intersects(largeSpan))
    }

    @Test
    fun testResolvedFileSpanToBeginResolvedFileLine() {
        val span = ResolvedFileSpan(
            file = "test.rs",
            span = ResolvedSpan(
                begin = ResolvedPos(line = 2, column = 3),
                end = ResolvedPos(line = 4, column = 5),
            ),
        )
        assertEquals("test.rs:3", span.beginFileLine().toString())
    }
}

