package com.depromeet.team5.features.retrospect.annotation

import androidx.annotation.StringDef


typealias CurrencyType = @CurrencyTypeKey String

const val KRW = "KRW"
const val USD = "USD"
const val EUR = "EUR"
const val JPY = "JPY"

@Target(AnnotationTarget.ANNOTATION_CLASS)
@Retention(AnnotationRetention.SOURCE)
@StringDef(KRW, USD, EUR, JPY)
private annotation class CurrencyTypeClass

@CurrencyTypeClass
@Target(AnnotationTarget.TYPE)
@Retention(AnnotationRetention.SOURCE)
private annotation class CurrencyTypeKey


