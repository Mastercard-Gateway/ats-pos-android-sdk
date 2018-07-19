@file:Suppress("PLATFORM_CLASS_MAPPED_TO_KOTLIN")

package com.mastercard.gateway.ats.domain

import org.simpleframework.xml.*
import java.math.BigDecimal

@Root(name = "DeviceRequest", strict = false)
class DeviceRequest : ATSMessage {

    @field:ElementList(required = false, inline = true)
    var output: List<Output>? = null
    @field:Element(name = "Input", required = false)
    var input: DeviceRequest.Input? = null
    @field:Element(name = "Event", required = false)
    var event: DeviceRequest.Event? = null
    @field:Attribute(name = "ApplicationSender", required = false)
    var applicationSender: String? = null
    @field:Attribute(name = "TerminalID", required = false)
    var terminalID: String? = null
    @field:Attribute(name = "POPID", required = false)
    var popid: String? = null
    @field:Attribute(name = "SequenceID", required = false)
    var sequenceID: Int? = null
    @field:Attribute(name = "RequestType", required = true)
    lateinit var requestType: DeviceRequestType
    @field:Attribute(name = "WorkstationID", required = true)
    lateinit var workstationID: String
    @field:Attribute(name = "RequestID", required = true)
    lateinit var requestID: String

    @Root(name="Event", strict = false)
    class Event {

        @field:Element(name = "EventData", required = true)
        lateinit var eventData: EventData
        @field:Attribute(name = "EventType", required = true)
        lateinit var eventType: EventTypes

        @Root(name="EventData", strict = false)
        class EventData {

            @field:Element(name = "Dispenser", required = false)
            var dispenser: Short? = null
            @field:Element(name = "TotalAmount", required = false)
            var totalAmount: TotalAmountType? = null
            @field:Element(name = "CardIdent", required = false)
            var cardIdent: CardValueDRType? = null
            @field:ElementList(name = "DataRequired", required = false, inline = true)
            var dataRequired: List<DataRequiredType>? = null
            @field:Element(name = "ProductsAllowed", required = false)
            var productsAllowed: DeviceRequest.Event.EventData.ProductsAllowed? = null

            @Root(name="ProductsAllowed", strict = false)
            class ProductsAllowed {

                @field:ElementList(name = "ProductCode", required = true, inline = true)
                lateinit var productCode: List<ProductCode>

                @Root(name="ProductCode", strict = false)
                class ProductCode {
                    @field:Text
                    var value: Integer? = null
                    @field:Attribute(name = "Name", required = false)
                    var name: String? = null
                    @field:Attribute(name = "UnitMeasure", required = false)
                    var unitMeasure: UnitOfMeasureCode? = null
                    @field:Attribute(name = "Quantity", required = false)
                    var quantity: BigDecimal? = null
                }
            }

        }

    }

    @Root(name = "Input", strict = false)
    class Input {

        @field:Element(name = "Command", required = false)
        var command: Command? = null
        @field:ElementList(name = "InSecureData", required = false, inline = true)
        var inSecureData: List<SecureDataFlow>? = null
        @field:Attribute(name = "InDeviceTarget", required = true)
        lateinit var inDeviceTarget: DeviceType

        @Root(strict = false)
        class Command {
            @field:Text
            var value: CommandType? = null
            @field:Attribute(name = "Length", required = false)
            var length: Integer? = null
            @field:Attribute(name = "MinLength", required = false)
            var minLength: Integer? = null
            @field:Attribute(name = "MaxLength", required = false)
            var maxLength: Integer? = null
            @field:Attribute(name = "Decimals", required = false)
            var decimals: Integer? = null
            @field:Attribute(name = "Separator", required = false)
            var separator: SeparatorType? = null
            @field:Attribute(name = "CardReadElement", required = false)
            var cardReadElement: CardReadType? = null
            @field:Attribute(name = "TimeOut", required = false)
            var timeOut: Integer? = null
            @field:Attribute(name = "DataRequired", required = false)
            var dataRequired: DataRequiredType? = null
        }
    }

    @Root(name = "Output", strict = false)
    class Output {

        @field:Attribute(name = "InputSynchronize", required = false)
        var inputSynchronize: Boolean? = null
        @field:Attribute(name = "Complete", required = false)
        var complete: Boolean? = null
        @field:Attribute(name = "Immediate", required = false)
        var immediate: Boolean? = null
        @field:Attribute(name = "Category", required = false)
        var category: Integer? = null
        @field:Attribute(name = "Code", required = false)
        var code: Integer? = null
        @field:Attribute(name = "TimeOut", required = false)
        var timeOut: Integer? = null
        @field:Attribute(name = "OutDeviceTarget", required = true)
        lateinit var outDeviceTarget: DeviceType
        @field:ElementList(required = false, inline = true)
        var textLine: List<TextLine>? = null
        @field:Element(name = "Buzzer", required = false)
        var buzzer: Buzzer? = null
        @field:ElementList(name = "OutSecureData", required = false, inline = true, type = SecureDataFlow::class)
        var outSecureData: List<SecureDataFlow>? = null
        @field:Element(name = "MAC", required = false)
        var mac: MACType? = null
        @field:Element(name = "ImageFile", required = false)
        var imageFile: String? = null


        @Root(name= "Buzzer", strict = false)
        class Buzzer {
            @field:Text
            var value: Boolean = false
            @field:Attribute(name = "DurationBeep", required = false)
            var durationBeep: Integer? = null
            @field:Attribute(name = "CounterBeep", required = false)
            var counterBeep: Integer? = null
            @field:Attribute(name = "DurationPause", required = false)
            var durationPause: Integer? = null
        }

        @Root(name = "TextLine", strict = false)
        class TextLine {
            @field:Text(required = false)
            var value: String? = null
            @field:Attribute(name = "Row", required = false)
            var row: Byte? = null
            @field:Attribute(name = "Column", required = false)
            var column: Byte? = null
            @field:Attribute(name = "CharSet", required = false)
            var charSet: Byte? = null
            @field:Attribute(name = "Erase", required = false)
            var erase: Boolean? = null
            @field:Attribute(name = "Echo", required = false)
            var echo: Boolean? = null
            @field:Attribute(name = "Cursor", required = false)
            var cursor: Boolean? = null
            @field:Attribute(name = "TimeOut", required = false)
            var timeOut: Integer? = null
            @field:Attribute(name = "Color", required = false)
            var color: ColorType? = null
            @field:Attribute(name = "Alignment", required = false)
            var alignment: AlignmentType? = null
            @field:Attribute(name = "Height", required = false)
            var height: HeightType? = null
            @field:Attribute(name = "Width", required = false)
            var width: WidthType? = null
            @field:Attribute(name = "CharStyle1", required = false)
            var charStyle1: CharStyleType? = null
            @field:Attribute(name = "CharStyle2", required = false)
            var charStyle2: CharStyleType? = null
            @field:Attribute(name = "CharStyle3", required = false)
            var charStyle3: CharStyleType? = null
            @field:Attribute(name = "PaperCut", required = false)
            var paperCut: Boolean? = null
            @field:Attribute(name = "MenuItem", required = false)
            var menuItem: Int? = null
        }
    }

}
