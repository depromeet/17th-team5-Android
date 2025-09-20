package com.depromeet.team5.features.retrospect.screen.annotation

import androidx.annotation.StringDef


typealias savedStateHandleKey = @SavedStateHandleKeyType String

const val SELLING = "selling"
const val STOCK = "stock"
const val DATE = "date"
const val RETURN = "return"

@Target(AnnotationTarget.ANNOTATION_CLASS)
@Retention(AnnotationRetention.SOURCE)
@StringDef(SELLING, STOCK, DATE, RETURN)
private annotation class SavedStateHandleKey

@SavedStateHandleKey
@Target(AnnotationTarget.TYPE)
@Retention(AnnotationRetention.SOURCE)
private annotation class SavedStateHandleKeyType
