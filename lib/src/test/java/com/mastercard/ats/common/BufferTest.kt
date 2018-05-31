package com.mastercard.ats.common

import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Test

class BufferTest {

    val buffer = Buffer()
    val data1 = byteArrayOf(0x1, 0x2, 0x3, 0x4, 0x5)
    val data2 = byteArrayOf(0x6, 0x7, 0x8)

    @Before
    fun setUp() {
        buffer.put(data1)
    }

    @After
    fun tearDown() {
        buffer.clear()
    }

    @Test
    fun testByteReturnsCorrectByteAtLocation() {
        assertEquals(0x2.toByte(), buffer.byte(1))
        assertEquals(0x5.toByte(), buffer.byte(4))
    }

    @Test
    fun testByteThrowsExceptionOnIndexOutOfBounds() {
        try {
            buffer.byte(buffer.size + 10)
            fail("Should have thrown IndexOutOfBoundsException")
        } catch (e: Exception) {
            // success
        }
    }

    @Test
    fun testPeekDoesNotModifyData() {
        buffer.peek(2)

        assertEquals(data1.size, buffer.size)
    }

    @Test
    fun testPeekReturnsOffsetArray() {
        var peek = buffer.peek(3)

        assertEquals(3, peek.size)
        assertEquals(0x1.toByte(), peek[0])

        peek = buffer.peek(3, 5)

        assertEquals(2, peek.size)
        assertEquals(0x4.toByte(), peek[0])
    }

    @Test
    fun testPeekThrowsExceptionOnIndexOutOfBounds() {
        try {
            buffer.peek(-1, 10)
            fail("Should have thrown IndexOutOfBoundsException")
        } catch (e: Exception) {
            // success
        }
    }

    @Test
    fun testPutAppendsDataCorrectly() {
        assertEquals(5, buffer.size)

        buffer.put(data2)

        assertEquals(8, buffer.size)
        assertEquals(0x1.toByte(), buffer.byte(0))
        assertEquals(0x6.toByte(), buffer.byte(5))
    }

    @Test
    fun testPopCorrectlyRemovesDataFromHeadOfBuffer() {
        buffer.pop(2)

        assertEquals(3, buffer.size)
        assertEquals(0x3.toByte(), buffer.byte(0))
    }
}