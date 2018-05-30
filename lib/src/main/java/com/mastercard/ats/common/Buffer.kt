package com.mastercard.ats.common

class Buffer {

    private var data = byteArrayOf()

    val size: Int
        get() = data.size

    fun put(bytes: ByteArray) {
        data = data.plus(bytes)
    }

    fun peek(length: Int): ByteArray {
        return data.copyOf(length)
    }

    fun pop(length: Int): ByteArray {
        var len = length
        if (len > size) {
            len = size
        }
        val tmp = data.copyOf(len)
        data = data.copyOfRange(len, data.size - 1)
        return tmp
    }

    fun clear() {
        data = byteArrayOf()
    }
}