package com.depromeet.team5.features.retrospect.screen.annotation

import androidx.annotation.StringDef


typealias CurrencyUnit = @CurrencyUnitType String

const val KOREAN = "원"
const val USD = "$"

const val PERCENT = "%"

const val NIL = ""

@Target(AnnotationTarget.ANNOTATION_CLASS)
@Retention(AnnotationRetention.SOURCE)
@StringDef(KOREAN, USD, PERCENT, NIL)
private annotation class CurrencyUnitDef

@CurrencyUnitDef
@Target(AnnotationTarget.TYPE)
@Retention(AnnotationRetention.SOURCE)
private annotation class CurrencyUnitType
