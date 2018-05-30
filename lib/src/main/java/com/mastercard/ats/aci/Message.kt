package com.mastercard.ats.aci

import com.mastercard.ats.common.bytes
import java.io.ByteArrayOutputStream
import java.nio.ByteBuffer

data class Message(val content: String) {

    companion object {

        const val HEADER_SIZE = 4

        fun read(buffer: ByteBuffer): Message? {
            val b = ByteArrayOutputStream()

            buffer.position(0)
            val len = buffer.getInt(0)
            buffer.put()
            return null
        }

        fun parse(bytes: ByteArray): Message {
            // get content without header
            val content = String(bytes.copyOfRange(HEADER_SIZE, bytes.size - 1))
            return Message(content)
        }
    }

    val bytes: ByteArray
        get() {
            val out = ByteArrayOutputStream()

            // write length header
            out.write(content.length.bytes())

            // write content
            out.write(content.toByteArray(Charsets.UTF_8))

            return out.toByteArray()
        }
}
