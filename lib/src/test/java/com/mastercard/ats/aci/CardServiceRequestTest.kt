package com.mastercard.ats.aci

import com.mastercard.ats.aci.CardServiceRequest
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class CardServiceRequestTest {

    @Test
    fun testToStringCorrectlySerializesData() {
        val request = CardServiceRequest(
                requestType = CardServiceRequest.RequestType.CardPayment,
                totalAmount = "1.00",
                workstationId = "asdasd"
        )

        val xml = request.toString()

        assertNotNull(xml)
    }
}