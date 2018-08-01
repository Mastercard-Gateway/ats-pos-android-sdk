package com.mastercard.gateway.ats

import com.mastercard.gateway.common.Buffer
import com.mastercard.gateway.common.bytes
import java.io.ByteArrayOutputStream
import java.nio.ByteBuffer

internal data class Message(val content: String) {

    companion object {

        const val HEADER_SIZE = 4

        fun read(buffer: Buffer): Message? {
            if (buffer.size < HEADER_SIZE) {
                return null
            }

            val header = buffer.peek(HEADER_SIZE)
            val length = ByteBuffer.wrap(header).int

            if (buffer.size < HEADER_SIZE + length) {
                return null
            }

            val data = buffer.pop(HEADER_SIZE + length)
            return parse(data)
        }

        fun parse(bytes: ByteArray): Message {
            // get content without header
            val content = String(bytes.copyOfRange(HEADER_SIZE, bytes.size))
            return Message(content)
        }
    }

    val bytes: ByteArray
        get() {
            val out = ByteArrayOutputStream()

            // write length header
            out.write(content.length.bytes())

            // write content
            out.write(content.toByteArray())

            return out.toByteArray()
        }
}
