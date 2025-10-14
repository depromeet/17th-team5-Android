package com.depromeet.team5.features.retrospect.annotation

import androidx.annotation.StringDef


typealias CurrencyType = @CurrencyTypeKey String

const val KRW = "원"
const val USD = "$"

@Target(AnnotationTarget.ANNOTATION_CLASS)
@Retention(AnnotationRetention.SOURCE)
@StringDef(KRW, USD)
private annotation class CurrencyTypeClass

@CurrencyTypeClass
@Target(AnnotationTarget.TYPE)
@Retention(AnnotationRetention.SOURCE)
private annotation class CurrencyTypeKey


