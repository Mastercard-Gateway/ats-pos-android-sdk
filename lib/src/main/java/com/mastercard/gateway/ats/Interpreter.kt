package com.mastercard.gateway.ats

import com.mastercard.gateway.ats.domain.CardServiceResponse
import org.simpleframework.xml.core.Persister
import java.io.ByteArrayOutputStream

class Interpreter {

    companion object {

        fun deserialize(message: Message): Any? {
            val serializer = Persister()
            if (message.content.contains("CardServiceResponse")) {
                return serializer.read(CardServiceResponse::class.java, message.content)
            }

            return null
        }

        fun serialize(obj: Any): Message {
            val out = ByteArrayOutputStream()
            val serializer = Persister()
            serializer.write(obj, out)

            return Message(String(out.toByteArray()))
        }
    }
}