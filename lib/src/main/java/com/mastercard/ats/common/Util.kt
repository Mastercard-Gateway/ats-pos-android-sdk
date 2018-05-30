package com.mastercard.ats.common

import java.nio.ByteBuffer

fun Int.bytes(): ByteArray {
    return ByteBuffer.allocate(4)
            .putInt(this)
            .array()
}