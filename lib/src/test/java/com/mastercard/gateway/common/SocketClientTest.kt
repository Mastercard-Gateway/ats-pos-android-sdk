package com.mastercard.gateway.common

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.reset
import com.nhaarman.mockito_kotlin.whenever
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.net.Socket
import kotlin.test.assertFalse
import kotlin.test.assertTrue


@RunWith(RobolectricTestRunner::class)
class SocketClientTest {

    private val socketClient = SocketClient()
    private val mockSocket: Socket = mock()


    @Before
    fun setUp() {
        socketClient.socket = mockSocket
    }

    @After
    fun tearDown() {
        reset(mockSocket)
    }

    @Test
    fun testConnectedReturnsTrueWhenSocketConnected() {
        whenever(mockSocket.isConnected).thenReturn(true)
        whenever(mockSocket.isClosed).thenReturn(false)

        val result = socketClient.connected

        assertTrue(result)
    }

    @Test
    fun testConnectedReturnsFalseWhenSocketIsNull() {
        socketClient.socket = null

        val result = socketClient.connected

        assertFalse(result)
    }
}