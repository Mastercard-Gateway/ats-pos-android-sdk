package com.mastercard.gateway.ats.domain

import org.simpleframework.xml.Root

@Root(name = "UnitOfMeasureCode")
enum class UnitOfMeasureCode {


    /**
     * each
     *
     */
    EA,

    /**
     * foot
     *
     */
    FOT,

    /**
     * gallon (UK)
     *
     */
    GLI,

    /**
     * gallon (US)
     *
     */
    GLL,

    /**
     * gram
     *
     */
    GRM,

    /**
     * inch
     *
     */
    INH,

    /**
     * kilogram
     *
     */
    KGM,

    /**
     * pound
     *
     */
    LBR,

    /**
     * Meter linear
     *
     */
    MTR,

    /**
     * Centimetre
     *
     */
    CM,

    /**
     * Litre
     *
     */
    LTR,

    /**
     * Centilitre
     *
     */
    CL
}
