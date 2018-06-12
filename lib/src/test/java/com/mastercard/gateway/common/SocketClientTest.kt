package com.mastercard.gateway.common

import android.os.Message
import com.nhaarman.mockito_kotlin.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.net.Socket
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue


@RunWith(RobolectricTestRunner::class)
class SocketClientTest {

    private lateinit var socketClient: SocketClient
    private val mockSocket: Socket = mock()

    @Before
    fun setUp() {
        socketClient = spy()
        socketClient.socket = mockSocket
    }

    @After
    fun tearDown() {
        socketClient.callbacks.clear()
        socketClient.sendBuffer.clear()

        reset(socketClient)
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

    @Test
    fun testAddingCallbackWorksAsExpected() {
        val mockCallback: SocketClient.Callback = mock()

        assertEquals(0, socketClient.callbacks.size)

        socketClient.addCallback(mockCallback)

        assertEquals(1, socketClient.callbacks.size)
        assertTrue(socketClient.callbacks.contains(mockCallback))
    }

    @Test
    fun testAddingCallbackDoesNothingIfCallbackAlreadyRegistered() {
        val mockCallback: SocketClient.Callback = mock()
        socketClient.callbacks.add(mockCallback)

        socketClient.addCallback(mockCallback)
        assertEquals(1, socketClient.callbacks.size)
    }

    @Test
    fun testRemoveCallbackWorksAsExpected() {
        val mockCallback: SocketClient.Callback = mock()
        socketClient.callbacks.add(mockCallback)

        socketClient.removeCallback(mockCallback)
        assertEquals(0, socketClient.callbacks.size)
    }

    @Test
    fun testConnectFirstDisconnectsThenStartsNewConnectionThread() {
        val ip = "1.1.1.1"
        val port = 20002
        val mockThread: Thread = mock()
        whenever(socketClient.createConnectThread(ip, port)).thenReturn(mockThread)

        socketClient.connect(ip, port)

        verify(socketClient).disconnect()
        verify(mockThread).start()
    }

    @Test
    fun testDisconnectClosesSocketAndClearsBuffer() {
        socketClient.sendBuffer.put(byteArrayOf(0, 1, 2, 3))

        socketClient.disconnect()

        verify(mockSocket).close()
        assertNull(socketClient.socket)
        assertEquals(0, socketClient.sendBuffer.size)
    }

    @Test
    fun testSendPutsBytesOnTheSendBufferIfConnected() {
        val bytes = byteArrayOf(0,1,2,3)
        whenever(socketClient.connected).thenReturn(true)

        assertEquals(0, socketClient.sendBuffer.size)

        socketClient.send(bytes)

        assertEquals(bytes.size, socketClient.sendBuffer.size)
    }

    @Test
    fun testHandleMessageCallsForwardsMessageToCallbacks() {
        val mockCallback: SocketClient.Callback = mock()
        socketClient.callbacks.add(mockCallback)

        val msg = Message()
        msg.what = SocketClient.EVENT_CONNECTED

        var result = socketClient.handleMessage(msg)

        verify(mockCallback).connected()
        assertTrue(result)

        msg.what = SocketClient.EVENT_DISCONNECTED

        result = socketClient.handleMessage(msg)

        verify(mockCallback).disconnected()
        assertTrue(result)

        val bytes = byteArrayOf(0,1,2,3)
        msg.what = SocketClient.EVENT_DATA_RECEIVED
        msg.obj = bytes

        result = socketClient.handleMessage(msg)

        verify(mockCallback).dataReceived(bytes)
        assertTrue(result)

        val throwable: Throwable = mock()
        msg.what = SocketClient.EVENT_ERROR
        msg.obj = throwable

        result = socketClient.handleMessage(msg)

        verify(mockCallback).error(throwable)
        assertTrue(result)
    }

    @Test
    fun testHandleMessageReturnsFalseIfNoMatchingEvents() {
        val msg = Message()
        msg.what = -1 // invalid event

        val result = socketClient.handleMessage(msg)

        assertFalse(result)
    }
}