package com.mastercard.ats.aci.domain

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.simpleframework.xml.core.Persister

class CardServiceResponseTest {

    val payload = "<CardServiceResponse RequestType=\"CardPayment\" ApplicationSender=\"ATSClient\" WorkstationID=\"12341234\" POPID=\"2\" RequestID=\"10\" OverallResult=\"Success\">" +
            "<Terminal TerminalID=\"20260001\" MerchantID=\"21249872\" STAN=\"74\"/>" +
            "<Tender>" +
            "<TotalAmount GratuityAmount=\"2.00\" PaymentAmount=\"25.00\" OriginalAmount=\"80.00\" Currency=\"GBP\">27.00</TotalAmount>" +
            "<Authorisation IssuerID=\"1\" AcquirerID=\"MAIN\" CardPAN=\"476173******0010\" ExpiryDate=\"1210\" TimeStamp=\"2011-03-15T15:27:41\" ApprovalCode=\"1234\" CardCircuit=\"VISA\" ReceiptNumber=\"6\" TrackingReference=\"4000201009323321\"></Authorisation>" +
            "</Tender>" +
            "</CardServiceResponse>"

    @Test
    fun testDeserialization() {
        val serializer = Persister()
        val obj = serializer.read(CardServiceResponse::class.java, payload)

        assertNotNull(obj)
        assertEquals(CardRequestType.CardPayment, obj.requestType)
    }
}