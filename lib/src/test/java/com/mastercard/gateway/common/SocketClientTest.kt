package com.mastercard.gateway.common

import android.os.Handler
import android.os.Message
import com.nhaarman.mockito_kotlin.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.exceptions.base.MockitoException
import org.robolectric.RobolectricTestRunner
import java.io.InputStream
import java.io.OutputStream
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import kotlin.test.fail


@RunWith(RobolectricTestRunner::class)
class SocketClientTest {

//    private lateinit var socketClient: SocketClient
//        private val mockInputStream: InputStream = mock()
//        private val mockOutputStream: OutputStream = mock()
//        private val mockHandler: Handler = mock()
//
//        @Before
//        fun setUp() {
//            socketClient = spy(object : SocketClient() {
//                override fun connectToSocket() {
//                    // do nothing
//                }
//
//                override fun isConnected(): Boolean {
//                    return true
//                }
//
//                override fun getInputStream(): InputStream {
//                    return mockInputStream
//                }
//
//                override fun getOutputStream(): OutputStream {
//                    return mockOutputStream
//                }
//            })
//
//            socketClient.handler = mockHandler
//        }
//
//        @After
//        fun tearDown() {
//            socketClient.callbacks.clear()
//            socketClient.writeBuffer.clear()
//
//            reset(socketClient)
//            reset(mockInputStream)
//            reset(mockOutputStream)
//            reset(mockHandler)
//        }
//
//        @Test
//        fun testAddingCallbackWorksAsExpected() {
//            val mockCallback: SocketClient.Callback = mock()
//
//            assertEquals(0, socketClient.callbacks.size)
//
//            socketClient.addCallback(mockCallback)
//
//            assertEquals(1, socketClient.callbacks.size)
//            assertTrue(socketClient.callbacks.contains(mockCallback))
//        }
//
//        @Test
//        fun testAddingCallbackDoesNothingIfCallbackAlreadyRegistered() {
//            val mockCallback: SocketClient.Callback = mock()
//            socketClient.callbacks.add(mockCallback)
//
//            socketClient.addCallback(mockCallback)
//            assertEquals(1, socketClient.callbacks.size)
//        }
//
//        @Test
//        fun testRemoveCallbackWorksAsExpected() {
//            val mockCallback: SocketClient.Callback = mock()
//            socketClient.callbacks.add(mockCallback)
//
//            socketClient.removeCallback(mockCallback)
//            assertEquals(0, socketClient.callbacks.size)
//        }
//
//        @Test
//        fun testConnectFirstDisconnectsThenStartsNewConnectionThread() {
//            whenever(socketClient.startConnectThread(1)).then {  }
//
//            socketClient.connect()
//
//            verify(socketClient).close()
//            verify(socketClient).startConnectThread(1)
//        }
//
//        @Test
//        fun testCloseClearsWriteBuffer() {
//            socketClient.writeBuffer.put(byteArrayOf(0, 1, 2, 3))
//
//            socketClient.close()
//
//            assertEquals(0, socketClient.writeBuffer.size)
//        }
//
//        @Test
//        fun testSendPutsBytesOnTheSendBufferIfConnected() {
//            val bytes = byteArrayOf(0,1,2,3)
//            whenever(socketClient.isConnected()).thenReturn(true)
//
//            assertEquals(0, socketClient.writeBuffer.size)
//
//            socketClient.write(bytes)
//
//            assertEquals(bytes.size, socketClient.writeBuffer.size)
//        }
//
//        @Test
//        fun testHandleMessageCallsForwardsMessageToCallbacks() {
//            val mockCallback: SocketClient.Callback = mock()
//            socketClient.callbacks.add(mockCallback)
//
//            val msg = Message()
//            msg.what = SocketClient.EVENT_CONNECTED
//
//            var result = socketClient.handleMessage(msg)
//
//            verify(mockCallback).onConnected()
//            assertTrue(result)
//
//            msg.what = SocketClient.EVENT_DISCONNECTED
//
//            result = socketClient.handleMessage(msg)
//
//            verify(mockCallback).onDisconnected()
//            assertTrue(result)
//
//            val bytes = byteArrayOf(0,1,2,3)
//            msg.what = SocketClient.EVENT_READ
//            msg.obj = bytes
//
//            result = socketClient.handleMessage(msg)
//
//            verify(mockCallback).onRead(bytes)
//            assertTrue(result)
//
//            val throwable: Throwable = mock()
//            msg.what = SocketClient.EVENT_ERROR
//            msg.obj = throwable
//
//            result = socketClient.handleMessage(msg)
//
//            verify(mockCallback).onError(throwable)
//            assertTrue(result)
//        }
//
//        @Test
//        fun testHandleMessageReturnsFalseIfNoMatchingEvents() {
//            val msg = Message()
//            msg.what = -1 // invalid event
//
//            val result = socketClient.handleMessage(msg)
//
//            assertFalse(result)
//        }
//
//        @Test
//        fun testAttemptConnectionWillAttemptMultipleTimes() {
//            val attempts = 4
//
//            whenever(socketClient.connectToSocket()).thenThrow(MockitoException::class.java)
//
//            try {
//                socketClient.attemptConnection(attempts)
//
//                fail("Should have ultimately thrown an exception on the last attempt")
//            } catch (e: Exception) {
//                // the final attempt will rethrow this exception
//            }
//
//            verify(socketClient, times(attempts)).connectToSocket()
//        }
//
//        @Test
//        fun testAttemptConnectionWillAttemptAtLeastOnce() {
//            val attempts = -1
//
//            whenever(socketClient.connectToSocket()).thenThrow(MockitoException::class.java)
//
//            try {
//                socketClient.attemptConnection(attempts)
//
//                fail("Should have ultimately thrown an exception on the last attempt")
//            } catch (e: Exception) {
//                // the final attempt will rethrow this exception
//            }
//
//            verify(socketClient, times(1)).connectToSocket()
//        }
//
//        @Test
//        fun attemptConnectionBreaksWhenConnectionIsMade() {
//            val attempts = 4
//
//            whenever(socketClient.connectToSocket())
//                    .thenThrow(MockitoException::class.java)
//                    .then{}
//
//            socketClient.attemptConnection(attempts)
//
//            verify(socketClient, times(2)).connectToSocket()
//        }
//
//        @Test
//        fun testReadLoopEndsWhenEndOfStreamReached() {
//            val mockMessage: Message = mock()
//            whenever(mockInputStream.read(any())).thenReturn(-1)
//            whenever(mockHandler.obtainMessage(any(), any())).thenReturn(mockMessage)
//
//            socketClient.readLoop()
//
//            verify(mockHandler, times(0)).sendMessage(mockMessage)
//        }
//
//        @Test
//        fun testReadLoopReadsUntilEndOfStream() {
//            val mockMessage: Message = mock()
//            whenever(mockInputStream.read(any()))
//                    .thenReturn(100)
//                    .thenReturn(200)
//                    .thenReturn(-1)
//
//            whenever(mockHandler.obtainMessage(any(), any())).thenReturn(mockMessage)
//
//            socketClient.readLoop()
//
//            verify(mockHandler, times(2)).sendMessage(mockMessage)
//        }
//
//        @Test
//        fun testRunConnectExceptionClosesAndSendsDisconnectMessage() {
//            val mockException: MockitoException = mock()
//            whenever(socketClient.attemptConnection(1)).thenThrow(mockException)
//
//            val mockMessage: Message = mock()
//            whenever(mockHandler.obtainMessage(EVENT_ERROR, mockException)).thenReturn(mockMessage)
//
//            socketClient.runConnect(1)
//
//            verify(mockHandler).sendMessage(mockMessage)
//            verify(socketClient).close()
//            verify(mockHandler).sendEmptyMessage(EVENT_DISCONNECTED)
//        }
//
//        @Test
//        fun testRunWriteClosesClientWhenNotConnected() {
//            whenever(socketClient.isConnected())
//                    .thenReturn(true)
//                    .thenReturn(true)
//                    .thenReturn(false)
//
//            socketClient.runWrite()
//
//            verify(socketClient).close()
//        }
//
//        @Test
//        fun testRunWriteReducesWriteBuffer() {
//            whenever(socketClient.isConnected())
//                    .thenReturn(true)
//                    .thenReturn(false)
//
//            socketClient.writeBuffer.put(byteArrayOf(0,1,2,3))
//
//            socketClient.runWrite()
//
//            assertEquals(0, socketClient.writeBuffer.size)
//        }
//
//        @Test
//        fun testRunWriteOutputStreamExceptionBreaksLoop() {
//            whenever(socketClient.isConnected()).thenReturn(true)
//            socketClient.writeBuffer.put(byteArrayOf(0,1,2,3))
//            whenever(mockOutputStream.write(any() as ByteArray?)).thenThrow(MockitoException::class.java)
//
//            socketClient.runWrite()
//
//            verify(socketClient).close()
//    }
}