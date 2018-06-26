package com.mastercard.gateway.ats

import org.simpleframework.xml.core.Persister
import java.io.ByteArrayOutputStream

internal class Interpreter {

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