package com.mastercard.gateway.ats.domain

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.Order
import org.simpleframework.xml.Root

import java.math.BigDecimal
import java.math.BigInteger

@Root(name = "DeviceRequest", strict = false)
@Order(elements = ["output", "input", "event"])
data class DeviceRequest(@field:Attribute(name = "RequestType", required = true) var requestType: DeviceRequestType,
                         @field:Attribute(name = "WorkstationID", required = true) var workstationID: String,
                         @field:Attribute(name = "RequestID", required = true) var requestID: String) : ATSMessage {

    @field:Element(name = "Output", required=false)
    var output: List<Output>? = null
    @field:Element(name = "Input", required=false)
    var input: DeviceRequest.Input? = null
    @field:Element(name = "Event", required=false)
    var event: DeviceRequest.Event? = null
    @field:Attribute(name = "ApplicationSender", required=false)
    var applicationSender: String? = null
    @field:Attribute(name = "TerminalID", required=false)
    var terminalID: String? = null
    @field:Attribute(name = "POPID", required=false)
    var popid: String? = null
    @field:Attribute(name = "SequenceID", required=false)
    var sequenceID: Int? = null


    @Order(elements = ["eventData"])
    data class Event(@field:Element(name = "EventData", required = true) var eventData: EventData,
                     @field:Attribute(name = "EventType", required = true) var eventType: EventTypes) {

        @Order(elements = ["dispenser", "totalAmount", "cardIdent", "dataRequired", "productsAllowed"])
        class EventData {

            @field:Element(name = "Dispenser", required=false)
            var dispenser: Short? = null
            @field:Element(name = "TotalAmount", required=false)
            var totalAmount: TotalAmountType? = null
            @field:Element(name = "CardIdent", required=false)
            var cardIdent: CardValueDRType? = null
            @field:Element(name = "DataRequired", required=false)
            var dataRequired: List<DataRequiredType>? = null
            @field:Element(name = "ProductsAllowed", required=false)
            var productsAllowed: DeviceRequest.Event.EventData.ProductsAllowed? = null

            @Order(elements = ["productCode"])
            data class ProductsAllowed(@field:Element(name = "ProductCode", required = true) var productCode: List<ProductCode>) {

                class ProductCode {
                    var value: BigInteger? = null
                    @field:Attribute(name = "Name", required=false)
                    var name: String? = null
                    @field:Attribute(name = "UnitMeasure", required=false)
                    var unitMeasure: UnitOfMeasureCode? = null
                    @field:Attribute(name = "Quantity", required=false)
                    var quantity: BigDecimal? = null
                }
            }

        }

    }


    @Order(elements = ["command", "inSecureData"])
    data class Input(@field:Attribute(name = "InDeviceTarget", required = true) var inDeviceTarget: DeviceType) {

        @field:Element(name = "Command", required=false)
        var command: Command? = null
        @field:Element(name = "InSecureData", required=false)
        var inSecureData: List<SecureDataFlow>? = null

        class Command {
            var value: CommandType? = null
            @field:Attribute(name = "Length", required=false)
            var length: BigInteger? = null
            @field:Attribute(name = "MinLength", required=false)
            var minLength: BigInteger? = null
            @field:Attribute(name = "MaxLength", required=false)
            var maxLength: BigInteger? = null
            @field:Attribute(name = "Decimals", required=false)
            var decimals: BigInteger? = null
            @field:Attribute(name = "Separator", required=false)
            var separator: SeparatorType? = null
            @field:Attribute(name = "CardReadElement", required=false)
            var cardReadElement: CardReadType? = null
            @field:Attribute(name = "TimeOut", required=false)
            var timeOut: BigInteger? = null
            @field:Attribute(name = "DataRequired", required=false)
            var dataRequired: DataRequiredType? = null
        }
    }

    @Order(elements = ["textLine", "buzzer", "outSecureData", "mac", "imageFile"])
    data class Output(@field:Attribute(name = "OutDeviceTarget", required = true) var outDeviceTarget: DeviceType) {

        @field:Element(name = "TextLine", required=false)
        var textLine: List<TextLine>? = null
        @field:Element(name = "Buzzer", required=false)
        var buzzer: Buzzer? = null
        @field:Element(name = "OutSecureData", required=false)
        var outSecureData: List<SecureDataFlow>? = null
        @field:Element(name = "MAC", required=false)
        var mac: MACType? = null
        @field:Element(name = "ImageFile", required=false)
        var imageFile: String? = null
        @field:Attribute(name = "InputSynchronize", required=false)
        var inputSynchronize: Boolean? = null
        @field:Attribute(name = "Complete", required=false)
        var complete: Boolean? = null
        @field:Attribute(name = "Immediate", required=false)
        var immediate: Boolean? = null
        @field:Attribute(name = "Category", required=false)
        var category: BigInteger? = null
        @field:Attribute(name = "Code", required=false)
        var code: BigInteger? = null
        @field:Attribute(name = "TimeOut", required=false)
        var timeOut: BigInteger? = null


        class Buzzer {
            var value: Boolean = false
            @field:Attribute(name = "DurationBeep", required=false)
            var durationBeep: BigInteger? = null
            @field:Attribute(name = "CounterBeep", required=false)
            var counterBeep: BigInteger? = null
            @field:Attribute(name = "DurationPause", required=false)
            var durationPause: BigInteger? = null
        }


        class TextLine {
            var value: String? = null
            @field:Attribute(name = "Row", required=false)
            var row: Byte? = null
            @field:Attribute(name = "Column", required=false)
            var column: Byte? = null
            @field:Attribute(name = "CharSet", required=false)
            var charSet: Byte? = null
            @field:Attribute(name = "Erase", required=false)
            var erase: Boolean? = null
            @field:Attribute(name = "Echo", required=false)
            var echo: Boolean? = null
            @field:Attribute(name = "Cursor", required=false)
            var cursor: Boolean? = null
            @field:Attribute(name = "TimeOut", required=false)
            var timeOut: BigInteger? = null
            @field:Attribute(name = "Color", required=false)
            var color: ColorType? = null
            @field:Attribute(name = "Alignment", required=false)
            var alignment: AlignmentType? = null
            @field:Attribute(name = "Height", required=false)
            var height: HeightType? = null
            @field:Attribute(name = "Width", required=false)
            var width: WidthType? = null
            @field:Attribute(name = "CharStyle1", required=false)
            var charStyle1: CharStyleType? = null
            @field:Attribute(name = "CharStyle2", required=false)
            var charStyle2: CharStyleType? = null
            @field:Attribute(name = "CharStyle3", required=false)
            var charStyle3: CharStyleType? = null
            @field:Attribute(name = "PaperCut", required=false)
            var paperCut: Boolean? = null
            @field:Attribute(name = "MenuItem", required=false)
            var menuItem: Int? = null
        }
    }

}
