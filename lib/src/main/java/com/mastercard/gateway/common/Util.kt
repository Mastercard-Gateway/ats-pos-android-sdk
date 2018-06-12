package com.mastercard.gateway.common

import android.util.Log
import com.mastercard.gateway.ats.Message
import org.xmlpull.v1.XmlSerializer
import java.io.StringWriter
import java.nio.ByteBuffer

fun Int.bytes(): ByteArray {
    return ByteBuffer.allocate(4)
            .putInt(this)
            .array()
}

fun String.log(tag: String, priority:Int = Log.DEBUG) {
    Log.println(priority, tag, this)
}

fun String.log(tag: String, tr: Throwable) {
    "$this\n${Log.getStackTraceString(tr)}".log(tag, Log.ERROR)
}

fun String.log(obj: Any, priority:Int = Log.DEBUG) {
    log(obj.javaClass.simpleName, priority)
}

fun String.log(obj: Any, tr: Throwable) {
    log(obj.javaClass.simpleName, tr)
}

fun String.logV(tag: String) {
    log(tag, Log.VERBOSE)
}

fun String.logV(obj: Any) {
    log(obj, Log.VERBOSE)
}

fun String.logD(tag: String) {
    log(tag, Log.DEBUG)
}

fun String.logD(obj: Any) {
    log(obj, Log.DEBUG)
}

fun String.logI(tag: String) {
    log(tag, Log.INFO)
}

fun String.logI(obj: Any) {
    log(obj, Log.INFO)
}

fun String.logW(tag: String) {
    log(tag, Log.WARN)
}

fun String.logW(obj: Any) {
    log(obj, Log.WARN)
}

fun String.logE(tag: String) {
    log(tag, Log.ERROR)
}

fun String.logE(obj: Any) {
    log(obj, Log.ERROR)
}

fun String.logE(tag: String, tr: Throwable) {
    log(tag, tr)
}

fun String.logE(obj: Any, tr: Throwable) {
    log(obj, tr)
}


//  XML generation by code
fun XmlSerializer.document(docName: String = "UTF-8",
                           xmlStringWriter: StringWriter = StringWriter(),
                           init: XmlSerializer.() -> Unit): String {
    startDocument(docName, null)
    xmlStringWriter.buffer.setLength(0) //  refreshing string writer due to reuse
    setOutput(xmlStringWriter)
    init()
    endDocument()
    return xmlStringWriter.toString()
}

//  element
fun XmlSerializer.element(name: String, init: XmlSerializer.() -> Unit) {
    startTag("", name)
    init()
    endTag("", name)
}

//  element with attribute & content
fun XmlSerializer.element(name: String,
                          content: String,
                          init: XmlSerializer.() -> Unit) {
    startTag("", name)
    init()
    text(content)
    endTag("", name)
}

//  element with content
fun XmlSerializer.element(name: String, content: String) =
        element(name) {
            text(content)
        }

//  attribute
fun XmlSerializer.attribute(name: String, value: String) =
        attribute("", name, value)


fun Buffer.readMessage(): Message? {
    return Message.read(buffer = this)
}


//val xmlSerializer = Xml.newSerializer()
//val xmlString = xmlSerializer.document {
//    element("Movies") {
//        element("row") {
//            attribute("no", "1")
//            element("FL", "6000000066015") {
//                attribute("val", "TicketId")
//            }
//            element("FL", "Dunkirk") {
//                attribute("val", "MovieName")
//            }
//            element("FL") {
//                attribute("val", "TimeLog")
//                element("row") {
//                    attribute("no", "1")
//                    element("FL", "23/01/2018") {
//                        attribute("val", "date")
//                    }
//                    element("FL", "08:00") {
//                        attribute("val", "startTime")
//                    }
//                }
//            }
//        }
//    }
//}