package com.mastercard.gateway.ats

import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class InterpreterTest {

    val payload = "<CardServiceResponse RequestType=\"CardPayment\" ApplicationSender=\"ATSClient\" WorkstationID=\"12341234\" POPID=\"2\" RequestID=\"10\" OverallResult=\"Success\">" +
            "<Terminal TerminalID=\"20260001\" MerchantID=\"21249872\" STAN=\"74\"/>" +
            "<Tender>" +
            "<TotalAmount GratuityAmount=\"2.00\" PaymentAmount=\"25.00\" OriginalAmount=\"80.00\" Currency=\"GBP\">27.00</TotalAmount>" +
            "<Authorisation IssuerID=\"1\" AcquirerID=\"MAIN\" CardPAN=\"476173******0010\" ExpiryDate=\"1210\" TimeStamp=\"2011-03-15T15:27:41\" ApprovalCode=\"1234\" CardCircuit=\"VISA\" ReceiptNumber=\"6\" TrackingReference=\"4000201009323321\"></Authorisation>" +
            "</Tender>" +
            "</CardServiceResponse>"

    @Test
    fun testSerializeWorksCorrectly() {
        val obj = CardServiceResponse()
        obj.overallResult = "Something"
        obj.requestId = "somerequest"
        obj.requestType = CardRequestType.CardPayment
        obj.workstationId = "mycomp"

        val message = Interpreter.serialize(obj)

        assertTrue(message.content.length > 30)
    }

    @Test
    fun testDeserializeWorksCorrectly() {
        val msg = Message(payload)

        val obj = Interpreter.deserialize(msg)

        assertNotNull(obj)
        assertTrue(obj is CardServiceResponse)
    }
}