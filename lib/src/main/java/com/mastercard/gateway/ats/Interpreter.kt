package com.mastercard.gateway.ats

import com.mastercard.gateway.ats.domain.ATSMessage
import com.mastercard.gateway.ats.domain.CardServiceResponse
import com.mastercard.gateway.ats.domain.DeviceResponse
import com.mastercard.gateway.ats.domain.ServiceResponse
import org.simpleframework.xml.core.Persister
import java.io.ByteArrayOutputStream

internal class Interpreter {

    companion object {

        @JvmStatic
        fun deserialize(message: Message): ATSMessage? {
            val serializer = Persister()
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
            val serializer = Persister()
            serializer.write(obj, out)

            return Message(String(out.toByteArray()))
        }
    }
}