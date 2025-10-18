package com.depromeet.team5.features.retrospect.annotation

import androidx.annotation.StringDef


typealias ReturnSignType = @ReturnSignTypeKey String


const val PLUS = "+"

const val MINUS = "-"


@Target(AnnotationTarget.ANNOTATION_CLASS)
@Retention(AnnotationRetention.SOURCE)
@StringDef(PLUS, MINUS)
annotation class ReturnSignTypeClass


@ReturnSignTypeClass
@Target(AnnotationTarget.TYPE)
@Retention(AnnotationRetention.SOURCE)
annotation class ReturnSignTypeKey
