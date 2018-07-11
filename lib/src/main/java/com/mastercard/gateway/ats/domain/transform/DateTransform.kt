package com.mastercard.gateway.ats.domain.transform

import org.simpleframework.xml.convert.Converter
import org.simpleframework.xml.stream.InputNode
import org.simpleframework.xml.stream.OutputNode
import org.simpleframework.xml.transform.Transform
import java.text.SimpleDateFormat
import java.util.*

class DateTransform : Transform<Date> {

    //2010-05-19T15:11:31.765625+01:00
    //2018-07-06T11:09:50-05:00
    var xmlDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")

    override fun write(value: Date?): String = xmlDateFormat.format(value)


    override fun read(value: String?): Date = xmlDateFormat.parse(value)
}