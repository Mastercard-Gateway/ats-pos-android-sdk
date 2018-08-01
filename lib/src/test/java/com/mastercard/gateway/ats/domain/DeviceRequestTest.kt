package com.mastercard.gateway.ats.domain

import com.mastercard.gateway.ats.domain.transform.DateTransform
import org.junit.Assert
import org.junit.Test
import org.simpleframework.xml.convert.AnnotationStrategy
import org.simpleframework.xml.core.Persister
import org.simpleframework.xml.transform.RegistryMatcher
import java.util.*

class DeviceRequestTest {


    val payload: String = """
        <DeviceRequest xmlns="http://www.nrf-arts.org/IXRetail/namespace" RequestType="Output" WorkstationID="43214321" POPID="2" RequestID="179" SequenceID="1">
            <Output OutDeviceTarget="CashierDisplay" Category="1" Code="41">
                <TextLine Erase="true">Please present or insert card</TextLine>
            </Output>
        </DeviceRequest>
    """.trimIndent()

    @Test
    fun testDeserialization() {
        val registryMatcher = RegistryMatcher()
        registryMatcher.bind(Date::class.java, DateTransform())

        val serializer = Persister(AnnotationStrategy(), registryMatcher)

        val obj = serializer.read(DeviceRequest::class.java, payload)

        Assert.assertNotNull(obj)
    }
}