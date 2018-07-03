package com.mastercard.gateway.ats.domain

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.Order
import org.simpleframework.xml.Root

import java.math.BigDecimal
import java.math.BigInteger

@Root(name = "DeviceRequest", strict = false)
@Order(elements = ["output", "input", "event"])
data class DeviceRequest(@Attribute(name = "RequestType", required = true) var requestType: DeviceRequestType,
                         @Attribute(name = "WorkstationID", required = true) var workstationID: String,
                         @Attribute(name = "RequestID", required = true) var requestID: String) : ATSMessage {

    @Element(name = "Output")
    var output: List<Output>? = null
    @Element(name = "Input")
    var input: DeviceRequest.Input? = null
    @Element(name = "Event")
    var event: DeviceRequest.Event? = null
    @Attribute(name = "ApplicationSender")
    var applicationSender: String? = null
    @Attribute(name = "TerminalID")
    var terminalID: String? = null
    @Attribute(name = "POPID")
    var popid: String? = null
    @Attribute(name = "SequenceID")
    var sequenceID: Int? = null


    @Order(elements = ["eventData"])
    data class Event(@Element(name = "EventData", required = true) var eventData: EventData,
                     @Attribute(name = "EventType", required = true) var eventType: EventTypes) {

        @Order(elements = ["dispenser", "totalAmount", "cardIdent", "dataRequired", "productsAllowed"])
        class EventData {

            @Element(name = "Dispenser")
            var dispenser: Short? = null
            @Element(name = "TotalAmount")
            var totalAmount: TotalAmountType? = null
            @Element(name = "CardIdent")
            var cardIdent: CardValueDRType? = null
            @Element(name = "DataRequired")
            var dataRequired: List<DataRequiredType>? = null
            @Element(name = "ProductsAllowed")
            var productsAllowed: DeviceRequest.Event.EventData.ProductsAllowed? = null

            @Order(elements = ["productCode"])
            data class ProductsAllowed(@Element(name = "ProductCode", required = true) var productCode: List<ProductCode>) {

                class ProductCode {
                    var value: BigInteger? = null
                    @Attribute(name = "Name")
                    var name: String? = null
                    @Attribute(name = "UnitMeasure")
                    var unitMeasure: UnitOfMeasureCode? = null
                    @Attribute(name = "Quantity")
                    var quantity: BigDecimal? = null
                }
            }

        }

    }


    @Order(elements = ["command", "inSecureData"])
    data class Input(@Attribute(name = "InDeviceTarget", required = true) var inDeviceTarget: DeviceType) {

        @Element(name = "Command")
        var command: Command? = null
        @Element(name = "InSecureData")
        var inSecureData: List<SecureDataFlow>? = null

        class Command {
            var value: CommandType? = null
            @Attribute(name = "Length")
            var length: BigInteger? = null
            @Attribute(name = "MinLength")
            var minLength: BigInteger? = null
            @Attribute(name = "MaxLength")
            var maxLength: BigInteger? = null
            @Attribute(name = "Decimals")
            var decimals: BigInteger? = null
            @Attribute(name = "Separator")
            var separator: SeparatorType? = null
            @Attribute(name = "CardReadElement")
            var cardReadElement: CardReadType? = null
            @Attribute(name = "TimeOut")
            var timeOut: BigInteger? = null
            @Attribute(name = "DataRequired")
            var dataRequired: DataRequiredType? = null
        }
    }

    @Order(elements = ["textLine", "buzzer", "outSecureData", "mac", "imageFile"])
    data class Output(@Attribute(name = "OutDeviceTarget", required = true) var outDeviceTarget: DeviceType) {

        @Element(name = "TextLine")
        var textLine: List<TextLine>? = null
        @Element(name = "Buzzer")
        var buzzer: Buzzer? = null
        @Element(name = "OutSecureData")
        var outSecureData: List<SecureDataFlow>? = null
        @Element(name = "MAC")
        var mac: MACType? = null
        @Element(name = "ImageFile")
        var imageFile: String? = null
        @Attribute(name = "InputSynchronize")
        var inputSynchronize: Boolean? = null
        @Attribute(name = "Complete")
        var complete: Boolean? = null
        @Attribute(name = "Immediate")
        var immediate: Boolean? = null
        @Attribute(name = "Category")
        var category: BigInteger? = null
        @Attribute(name = "Code")
        var code: BigInteger? = null
        @Attribute(name = "TimeOut")
        var timeOut: BigInteger? = null


        class Buzzer {
            var value: Boolean = false
            @Attribute(name = "DurationBeep")
            var durationBeep: BigInteger? = null
            @Attribute(name = "CounterBeep")
            var counterBeep: BigInteger? = null
            @Attribute(name = "DurationPause")
            var durationPause: BigInteger? = null
        }


        class TextLine {
            var value: String? = null
            @Attribute(name = "Row")
            var row: Byte? = null
            @Attribute(name = "Column")
            var column: Byte? = null
            @Attribute(name = "CharSet")
            var charSet: Byte? = null
            @Attribute(name = "Erase")
            var erase: Boolean? = null
            @Attribute(name = "Echo")
            var echo: Boolean? = null
            @Attribute(name = "Cursor")
            var cursor: Boolean? = null
            @Attribute(name = "TimeOut")
            var timeOut: BigInteger? = null
            @Attribute(name = "Color")
            var color: ColorType? = null
            @Attribute(name = "Alignment")
            var alignment: AlignmentType? = null
            @Attribute(name = "Height")
            var height: HeightType? = null
            @Attribute(name = "Width")
            var width: WidthType? = null
            @Attribute(name = "CharStyle1")
            var charStyle1: CharStyleType? = null
            @Attribute(name = "CharStyle2")
            var charStyle2: CharStyleType? = null
            @Attribute(name = "CharStyle3")
            var charStyle3: CharStyleType? = null
            @Attribute(name = "PaperCut")
            var paperCut: Boolean? = null
            @Attribute(name = "MenuItem")
            var menuItem: Int? = null
        }
    }

}
