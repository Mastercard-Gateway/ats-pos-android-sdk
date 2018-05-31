package com.mastercard.ats.aci

import android.util.Xml
import org.xmlpull.v1.XmlPullParser
import java.io.StringReader

class Response {

    companion object {

        fun interpret(message: Message) {
            val parser = Xml.newPullParser()
            parser.setInput(StringReader(message.content))

            var event = parser.getEventType()
            while (event != XmlPullParser.END_DOCUMENT) {
                val name = parser.getName()
                when (event) {
                    XmlPullParser.START_TAG -> {
                    }

                    XmlPullParser.END_TAG -> if (name == "temperature") {
                        temperature = myParser.getAttributeValue(null, "value")
                    }
                }
                event = myParser.next()
            }
        }
    }
}