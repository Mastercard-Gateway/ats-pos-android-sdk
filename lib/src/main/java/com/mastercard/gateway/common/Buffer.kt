package com.mastercard.gateway.common

import kotlin.math.min

internal class Buffer {

    private var data = byteArrayOf()

    val size: Int
        get() = data.size

    fun byte(index: Int): Byte {
        if (index < 0 || index >= size) {
            throw IndexOutOfBoundsException()
        }

        return data[index]
    }

    fun put(bytes: ByteArray) {
        data = data.plus(bytes)
    }

    fun peek(length: Int): ByteArray {
        return peek(0, length)
    }

    fun peek(index: Int, length: Int): ByteArray {
        if (index < 0 || index >= size) {
            throw IndexOutOfBoundsException()
        }

        if (size == 0 || length == 0) {
            return byteArrayOf()
        }

        return data.copyOfRange(index, min(size, (index + length)))
    }

    fun pop(length: Int): ByteArray {
        if (size == 0 || length == 0) {
            return byteArrayOf()
        }

        val len = min(size, length)

        val tmp = data.copyOf(len)
        data = data.copyOfRange(len, size)
        return tmp
    }

    fun clear() {
        data = byteArrayOf()
    }
}