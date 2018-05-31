package com.mastercard.ats.aci

import android.util.Xml
import com.mastercard.ats.common.attribute
import com.mastercard.ats.common.document
import com.mastercard.ats.common.element

class CardServiceRequest(
        var requestType: RequestType? = null,
        var workstationId: String? = null,
        var requestId: String? = null,
        var totalAmount: String? = null
) {

    enum class RequestType {
        CardPayment
    }

    override fun toString(): String {
        val serializer = Xml.newSerializer()

        return serializer.document {
            element("CardServiceRequest") {
                requestType?.let {
                    attribute("RequestType", it.name)
                }

                attribute("ApplicationSender", "ATSClient")

                workstationId?.let {
                    attribute("WorkstationID", it)
                }

                requestId?.let {
                    attribute("RequestID", it)
                }

                totalAmount?.let {
                    element("TotalAmount", it) {
                        attribute("PaymentAmount", it)
                    }
                }
            }
        }
    }
}