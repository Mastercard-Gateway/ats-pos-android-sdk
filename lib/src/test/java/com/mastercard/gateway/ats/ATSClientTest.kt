package com.mastercard.gateway.ats

import com.mastercard.gateway.common.IPSocketClient
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.reset
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ATSClientTest {

    private var ats = ATSClient()
    private var mockSocketClient: IPSocketClient = mock()


    @Before
    fun setUp() {
        ats.socketClient = mockSocketClient
    }

    @After
    fun tearDown() {
        reset(mockSocketClient)
    }

    @Test
    fun testAddCallbackWorksAsExpected() {
        val callback: ATSClient.Callback = mock()

        assertEquals(0, ats.callbacks.size)

        ats.addCallback(callback)

        assertEquals(1, ats.callbacks.size)
        assertTrue(ats.callbacks.contains(callback))

        // shouldn't add it twice
        ats.addCallback(callback)
        assertEquals(1, ats.callbacks.size)
    }

    @Test
    fun testRemoveCallbackWorksAsExpected() {
        val callback: ATSClient.Callback = mock()
        ats.callbacks.add(callback)

        assertEquals(1, ats.callbacks.size)

        ats.removeCallback(callback)

        assertEquals(0, ats.callbacks.size)
    }
}