package com.mastercard.gateway.ats

import com.mastercard.gateway.common.Buffer
import com.mastercard.gateway.common.bytes
import org.junit.Assert.*
import org.junit.Test
import java.io.ByteArrayOutputStream

class MessageTest {

    private val content = "hello"

    @Test
    fun testParseCorrectlyReadsStringContentFromData() {
        val out = ByteArrayOutputStream()
        out.write(content.length.bytes())
        out.write(content.toByteArray())
        val bytes = out.toByteArray()

        val message = Message.parse(bytes)

        assertEquals(content, message.content)
    }

    @Test
    fun testReadReturnsNullIfNotEnoughData() {
        // init empty buffer
        val buffer = Buffer()

        var message = Message.read(buffer)

        assertNull(message)

        // add a header but no data
        buffer.put(content.length.bytes())

        message = Message.read(buffer)

        assertNull(message)

        // add partial data
        buffer.put(content.substring(0..1).toByteArray())

        message = Message.read(buffer)

        assertNull(message)

        // add remaining data
        buffer.put(content.substring(2).toByteArray())

        message = Message.read(buffer)

        assertNotNull(message)
        assertEquals(content, message!!.content)
    }

    @Test
    fun testBytesConstructsCorrectByteArray() {
        val expectedByteArray = byteArrayOf(0, 0, 0, 5, 104, 101, 108, 108, 111)
        val message = Message(content)

        val bytes = message.bytes

        assertTrue(expectedByteArray.contentEquals(bytes))
    }
}