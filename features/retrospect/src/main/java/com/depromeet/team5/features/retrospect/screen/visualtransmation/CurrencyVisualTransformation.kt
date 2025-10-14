package com.depromeet.team5.features.retrospect.screen.visualtransmation

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import com.depromeet.team5.features.retrospect.annotation.CurrencyType
import com.depromeet.team5.features.retrospect.annotation.KRW
import com.depromeet.team5.features.retrospect.annotation.USD
import java.text.DecimalFormat
import kotlin.math.max


class CurrencyVisualTransformation(val unit: CurrencyType) : VisualTransformation {

    private val formatter = DecimalFormat("#,###")


    override fun filter(text: AnnotatedString): TransformedText {
        return when (unit) {
            KRW -> {
                if (text.text.isEmpty()) {
                    return TransformedText(text, OffsetMapping.Identity)
                }

                val originalText = text.text.filter { it.isDigit() }
                val longValue =
                    originalText.toLongOrNull() ?: return TransformedText(
                        text,
                        OffsetMapping.Identity
                    )

                val formattedText = "${formatter.format(longValue)}원"

                val offsetMapping = object : OffsetMapping {
                    override fun originalToTransformed(offset: Int): Int {
                        var digitCount = 0
                        var index = 0

                        while (index < formattedText.length && digitCount < offset) {
                            if (formattedText[index].isDigit()) {
                                digitCount++
                            }
                            index++
                        }
                        return index
                    }

                    override fun transformedToOriginal(offset: Int): Int {
                        return formattedText.substring(0, offset).count { it.isDigit() }
                    }
                }

                TransformedText(
                    androidx.compose.ui.text.AnnotatedString(formattedText),
                    offsetMapping
                )
            }

            USD -> {
                val digitsOnly = text.text.filter { it.isDigit() }
                if (digitsOnly.isEmpty()) {
                    return TransformedText(AnnotatedString(""), OffsetMapping.Identity)
                }

                val formattedNumber = formatter.format(digitsOnly.toLong())

                val newText = AnnotatedString("$$formattedNumber")

                val offsetMapping = object : OffsetMapping {

                    override fun originalToTransformed(offset: Int): Int {
                        val commas = (offset - 1).coerceAtLeast(0) / 3
                        return offset + commas + 1
                    }

                    override fun transformedToOriginal(offset: Int): Int {
                        val offsetWithoutPrefix = max(0, offset - 1)
                        val commas =
                            newText.substring(2).take(offsetWithoutPrefix).count { it == ',' }

                        return offsetWithoutPrefix - commas
                    }
                }

                TransformedText(newText, offsetMapping)
            }

            else -> error("CurrencyVisualTransformation에 KRW,USD 이외의 알 수 없는 CurrentType이 들어왔습니다.")
        }
    }
}
