package com.mastercard.gateway.ats.domain

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.Order
import org.simpleframework.xml.Root

import java.math.BigDecimal
import java.math.BigInteger

@Root(name = "DeviceRequest", strict = false)
@Order(elements = ["output", "input", "event"])
data class DeviceRequest(@field:Element(name = "Output") var output: List<Output>? = null,
                         @field:Element(name = "Input") var input: DeviceRequest.Input? = null,
                         @field:Element(name = "Event") var event: DeviceRequest.Event? = null,
                         @field:Attribute(name = "RequestType", required = true) var requestType: DeviceRequestType,
                         @field:Attribute(name = "ApplicationSender") var applicationSender: String? = null,
                         @field:Attribute(name = "WorkstationID", required = true) var workstationID: String,
                         @field:Attribute(name = "TerminalID") var terminalID: String? = null,
                         @field:Attribute(name = "POPID") var popid: String? = null,
                         @field:Attribute(name = "RequestID", required = true) var requestID: String,
                         @field:Attribute(name = "SequenceID") var sequenceID: Int? = null) {

    @Order(elements = ["eventData"])
    data class Event(@field:Element(name = "EventData", required = true) var eventData: EventData,
                     @field:Attribute(name = "EventType", required = true) var eventType: EventTypes) {

        @Order(elements = arrayOf("dispenser", "totalAmount", "cardIdent", "dataRequired", "productsAllowed"))
        data class EventData(@field:Element(name = "Dispenser") var dispenser: Short? = null,
                             @field:Element(name = "TotalAmount") var totalAmount: TotalAmountType? = null,
                             @field:Element(name = "CardIdent") var cardIdent: CardValueDRType? = null,
                             @field:Element(name = "DataRequired") var dataRequired: List<DataRequiredType>? = null,
                             @field:Element(name = "ProductsAllowed") var productsAllowed: DeviceRequest.Event.EventData.ProductsAllowed? = null) {

            @Order(elements = ["productCode"])
            data class ProductsAllowed(@field:Element(name = "ProductCode", required = true) var productCode: List<ProductCode>) {

                data class ProductCode(var value: BigInteger? = null,
                                       @field:Attribute(name = "Name") var name: String? = null,
                                       @field:Attribute(name = "UnitMeasure") var unitMeasure: UnitOfMeasureCode? = null,
                                       @field:Attribute(name = "Quantity") var quantity: BigDecimal? = null)
            }

        }

    }


    @Order(elements = ["command", "inSecureData"])
    data class Input(@field:Element(name = "Command") var command: Command? = null,
                     @field:Element(name = "InSecureData") var inSecureData: List<SecureDataFlow>? = null,
                     @field:Attribute(name = "InDeviceTarget", required = true) var inDeviceTarget: DeviceType) {

        data class Command(var value: CommandType? = null,
                           @field:Attribute(name = "Length") var length: BigInteger? = null,
                           @field:Attribute(name = "MinLength") var minLength: BigInteger? = null,
                           @field:Attribute(name = "MaxLength") var maxLength: BigInteger? = null,
                           @field:Attribute(name = "Decimals") var decimals: BigInteger? = null,
                           @field:Attribute(name = "Separator") var separator: SeparatorType? = null,
                           @field:Attribute(name = "CardReadElement") var cardReadElement: CardReadType? = null,
                           @field:Attribute(name = "TimeOut") var timeOut: BigInteger? = null,
                           @field:Attribute(name = "DataRequired") var dataRequired: DataRequiredType? = null)
    }

    @Order(elements = ["textLine", "buzzer", "outSecureData", "mac", "imageFile"])
    data class Output(@field:Element(name = "TextLine") var textLine: List<TextLine>? = null,
                      @field:Element(name = "Buzzer") var buzzer: Buzzer? = null,
                      @field:Element(name = "OutSecureData") var outSecureData: List<SecureDataFlow>? = null,
                      @field:Element(name = "MAC") var mac: MACType? = null,
                      @field:Element(name = "ImageFile") var imageFile: String? = null,
                      @field:Attribute(name = "OutDeviceTarget", required = true) var outDeviceTarget: DeviceType,
                      @field:Attribute(name = "InputSynchronize") var inputSynchronize: Boolean? = null,
                      @field:Attribute(name = "Complete") var complete: Boolean? = null,
                      @field:Attribute(name = "Immediate") var immediate: Boolean? = null,
                      @field:Attribute(name = "Category") var category: BigInteger? = null,
                      @field:Attribute(name = "Code") var code: BigInteger? = null,
                      @field:Attribute(name = "TimeOut") var timeOut: BigInteger? = null) {

        data class Buzzer(var value: Boolean = false,
                          @field:Attribute(name = "DurationBeep") var durationBeep: BigInteger? = null,
                          @field:Attribute(name = "CounterBeep") var counterBeep: BigInteger? = null,
                          @field:Attribute(name = "DurationPause") var durationPause: BigInteger? = null)


        data class TextLine(var value: String? = null,
                            @field:Attribute(name = "Row") var row: Byte? = null,
                            @field:Attribute(name = "Column") var column: Byte? = null,
                            @field:Attribute(name = "CharSet") var charSet: Byte? = null,
                            @field:Attribute(name = "Erase") var erase: Boolean? = null,
                            @field:Attribute(name = "Echo") var echo: Boolean? = null,
                            @field:Attribute(name = "Cursor") var cursor: Boolean? = null,
                            @field:Attribute(name = "TimeOut") var timeOut: BigInteger? = null,
                            @field:Attribute(name = "Color") var color: ColorType? = null,
                            @field:Attribute(name = "Alignment") var alignment: AlignmentType? = null,
                            @field:Attribute(name = "Height") var height: HeightType? = null,
                            @field:Attribute(name = "Width") var width: WidthType? = null,
                            @field:Attribute(name = "CharStyle1") var charStyle1: CharStyleType? = null,
                            @field:Attribute(name = "CharStyle2") var charStyle2: CharStyleType? = null,
                            @field:Attribute(name = "CharStyle3") var charStyle3: CharStyleType? = null,
                            @field:Attribute(name = "PaperCut") var paperCut: Boolean? = null,
                            @field:Attribute(name = "MenuItem") var menuItem: Int? = null)
    }

}
