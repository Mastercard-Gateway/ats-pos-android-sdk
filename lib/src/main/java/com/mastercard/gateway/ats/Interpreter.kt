package com.mastercard.gateway.ats

import com.mastercard.gateway.ats.domain.ATSMessage
import com.mastercard.gateway.ats.domain.CardServiceResponse
import com.mastercard.gateway.ats.domain.DeviceResponse
import com.mastercard.gateway.ats.domain.ServiceResponse
import com.mastercard.gateway.ats.domain.transform.DateTransform
import org.simpleframework.xml.core.Persister
import org.simpleframework.xml.transform.RegistryMatcher
import java.io.ByteArrayOutputStream
import java.util.*

internal class Interpreter {

    companion object {

        private val serializer: Persister

        init {
            val matcher = RegistryMatcher()
            matcher.bind(Date::class.java, DateTransform())

            serializer = Persister(matcher)
        }

        @JvmStatic
        fun deserialize(message: Message): ATSMessage? {
            return when {
                "CardServiceResponse" in message.content -> serializer.read(CardServiceResponse::class.java, message.content)
                "DeviceResponse" in message.content -> serializer.read(DeviceResponse::class.java, message.content)
                "ServiceResponse" in message.content -> serializer.read(ServiceResponse::class.java, message.content)
                else -> null
            }
        }

        @JvmStatic
        fun serialize(obj: Any): Message {
            val out = ByteArrayOutputStream()
            serializer.write(obj, out)

            return Message(String(out.toByteArray()))
        }
    }
}