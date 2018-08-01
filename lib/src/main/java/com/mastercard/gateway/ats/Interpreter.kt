package com.mastercard.gateway.ats

import com.mastercard.gateway.ats.domain.*
import com.mastercard.gateway.ats.domain.transform.DateTransform
import org.simpleframework.xml.core.Persister
import org.simpleframework.xml.transform.RegistryMatcher
import java.io.ByteArrayOutputStream
import java.util.*

internal class Interpreter {

    companion object {

        private val serializer: Persister

        init {

            val registryMatcher = RegistryMatcher()
            registryMatcher.bind(Date::class.java, DateTransform())

            serializer = Persister(registryMatcher)

        }

        @JvmStatic
        fun deserialize(message: Message): ATSMessage? {
            return when {
                "CardServiceRequest" in message.content -> serializer.read(CardServiceRequest::class.java, message.content)
                "CardServiceResponse" in message.content -> serializer.read(CardServiceResponse::class.java, message.content)
                "DeviceRequest" in message.content -> serializer.read(DeviceRequest::class.java, message.content)
                "DeviceResponse" in message.content -> serializer.read(DeviceResponse::class.java, message.content)
                "ServiceRequest" in message.content -> serializer.read(ServiceRequest::class.java, message.content)
                "ServiceResponse" in message.content -> serializer.read(ServiceResponse::class.java, message.content)
                else -> null
            }
        }

        @JvmStatic
        fun serialize(obj: Any): String {
            val out = ByteArrayOutputStream()
            serializer.write(obj, out)

            return String(out.toByteArray())
        }
    }
}