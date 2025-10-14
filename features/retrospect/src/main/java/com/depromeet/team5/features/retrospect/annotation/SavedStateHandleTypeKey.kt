package com.depromeet.team5.features.retrospect.annotation

import androidx.annotation.StringDef


typealias SavedStateHandleKey = @SavedStateHandleTypeKey String

const val SELLING = "selling"
const val STOCK = "stock"
const val DATE = "date"
const val RETURN = "return"

@Target(AnnotationTarget.ANNOTATION_CLASS)
@Retention(AnnotationRetention.SOURCE)
@StringDef(SELLING, STOCK, DATE, RETURN)
private annotation class SavedStateHandleClass

@SavedStateHandleClass
@Target(AnnotationTarget.TYPE)
@Retention(AnnotationRetention.SOURCE)
private annotation class SavedStateHandleTypeKey
