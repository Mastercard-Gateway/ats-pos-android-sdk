package com.mastercard.gateway.common

import android.os.Message
import com.nhaarman.mockito_kotlin.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.io.InputStream
import java.io.OutputStream
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue


@RunWith(RobolectricTestRunner::class)
class SocketClientTest {

    private lateinit var socketClient: SocketClient
    private val mockInputStream: InputStream = mock()
    private val mockOutputStream: OutputStream = mock()

    @Before
    fun setUp() {
        socketClient = spy(object : SocketClient() {
            override fun connectToSocket() {
                // do nothing
            }

            override fun isConnected(): Boolean {
                return true
            }

            override fun getInputStream(): InputStream {
                return mockInputStream
            }

            override fun getOutputStream(): OutputStream {
                return mockOutputStream
            }
        })
    }

    @After
    fun tearDown() {
        socketClient.callbacks.clear()
        socketClient.writeBuffer.clear()

        reset(socketClient)
        reset(mockInputStream)
        reset(mockOutputStream)
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
        val mockThread: Thread = mock()
        whenever(socketClient.createConnectThread(1)).thenReturn(mockThread)

        socketClient.connect()

        verify(socketClient).close()
        verify(mockThread).start()
    }

    @Test
    fun testCloseClearsWriteBuffer() {
        socketClient.writeBuffer.put(byteArrayOf(0, 1, 2, 3))

        socketClient.close()

        assertEquals(0, socketClient.writeBuffer.size)
    }

    @Test
    fun testSendPutsBytesOnTheSendBufferIfConnected() {
        val bytes = byteArrayOf(0,1,2,3)
        whenever(socketClient.isConnected()).thenReturn(true)

        assertEquals(0, socketClient.writeBuffer.size)

        socketClient.write(bytes)

        assertEquals(bytes.size, socketClient.writeBuffer.size)
    }

    @Test
    fun testHandleMessageCallsForwardsMessageToCallbacks() {
        val mockCallback: SocketClient.Callback = mock()
        socketClient.callbacks.add(mockCallback)

        val msg = Message()
        msg.what = SocketClient.EVENT_CONNECTED

        var result = socketClient.handleMessage(msg)

        verify(mockCallback).onConnected()
        assertTrue(result)

        msg.what = SocketClient.EVENT_DISCONNECTED

        result = socketClient.handleMessage(msg)

        verify(mockCallback).onDisconnected()
        assertTrue(result)

        val bytes = byteArrayOf(0,1,2,3)
        msg.what = SocketClient.EVENT_READ
        msg.obj = bytes

        result = socketClient.handleMessage(msg)

        verify(mockCallback).onRead(bytes)
        assertTrue(result)

        val throwable: Throwable = mock()
        msg.what = SocketClient.EVENT_ERROR
        msg.obj = throwable

        result = socketClient.handleMessage(msg)

        verify(mockCallback).onError(throwable)
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