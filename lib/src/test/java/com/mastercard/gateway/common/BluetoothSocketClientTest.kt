package com.mastercard.gateway.common

import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.os.ParcelUuid
import com.nhaarman.mockito_kotlin.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.io.InputStream
import java.io.OutputStream
import kotlin.test.assertEquals
import kotlin.test.assertNull

class BluetoothSocketClientTest {

    private var socketClient: BluetoothSocketClient? = null
    private val mockBluetoothDevice: BluetoothDevice = mock()
    private val mockBluetoothSocket: BluetoothSocket = mock()

    @Before
    fun setUp() {
        socketClient = null
    }

  /*  @Test
    fun testConnectSecureConnection() {
        val uuid: UUID = mock()
        val parcelUuid: ParcelUuid = mock()
        whenever(mockBluetoothDevice.uuids).thenReturn(arrayOf(parcelUuid))
        whenever(parcelUuid.uuid).thenReturn(uuid)
        whenever(mockBluetoothDevice.createRfcommSocketToServiceRecord(any())).thenReturn(mockBluetoothSocket)
        atsCommunicationSocketClient = BluetoothSocketClient(device = mockBluetoothDevice, secure = true)

        atsCommunicationSocketClient?.connect()

        verify(mockBluetoothDevice, times(1)).uuids
        verify(mockBluetoothDevice, times(1)).createRfcommSocketToServiceRecord(any())
        verify(mockBluetoothDevice, times(0)).createInsecureRfcommSocketToServiceRecord(any())
    }*/
/*
    @Test
    fun testConnectInSecureConnection() {
        val uuid: UUID = mock()
        val parcelUuid: ParcelUuid = mock()
        whenever(mockBluetoothDevice.uuids).thenReturn(arrayOf(parcelUuid))
        whenever(parcelUuid.uuid).thenReturn(uuid)
        whenever(mockBluetoothDevice.createRfcommSocketToServiceRecord(any())).thenReturn(mockBluetoothSocket)
        atsCommunicationSocketClient = BluetoothSocketClient(device = mockBluetoothDevice, secure = false)

        atsCommunicationSocketClient?.connect()

        verify(mockBluetoothDevice, times(1)).uuids
        verify(mockBluetoothDevice, times(0)).createRfcommSocketToServiceRecord(uuid)
        verify(mockBluetoothDevice, times(1)).createInsecureRfcommSocketToServiceRecord(uuid)
    }*/


    @Test
    fun testClose() {
        socketClient = BluetoothSocketClient(device = mockBluetoothDevice, secure = false)
        socketClient?.socket = mockBluetoothSocket

        socketClient?.close()

        verify(mockBluetoothSocket, times(1)).close()
        assertNull(socketClient?.socket)
    }

    @Test(expected = RuntimeException::class)
    fun testGetInputStreamWithNullSocket() {
        socketClient = BluetoothSocketClient(device = mockBluetoothDevice, secure = false)

        socketClient?.getInputStream()
    }

    @Test
    fun testGetInputStreamWithValidSocket() {
        val inputStream: InputStream = mock()
        whenever(mockBluetoothSocket.inputStream).thenReturn(inputStream)
        socketClient = BluetoothSocketClient(device = mockBluetoothDevice, secure = false)
        socketClient?.socket = mockBluetoothSocket

        val actual = socketClient?.getInputStream()

        assertEquals(expected = inputStream, actual = actual)
    }

    @Test(expected = RuntimeException::class)
    fun testGetOutputStreamWithNullSocket() {
        socketClient = BluetoothSocketClient(device = mockBluetoothDevice, secure = false)

        socketClient?.getOutputStream()
    }

    @Test
    fun testGetOutputStreamWithValidSocket() {
        val outputStream: OutputStream = mock()
        whenever(mockBluetoothSocket.outputStream).thenReturn(outputStream)
        socketClient = BluetoothSocketClient(device = mockBluetoothDevice, secure = false)
        socketClient?.socket = mockBluetoothSocket

        val actual = socketClient?.getOutputStream()

        assertEquals(expected = outputStream, actual = actual)
    }

    @After
    fun tearDown() {
        reset(mockBluetoothDevice)
        reset(mockBluetoothSocket)
    }

}