package com.depromeet.team5.features.retrospect.screen.visualtransmation

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import java.text.DecimalFormat


class KoreanCurrencyVisualTransformation : VisualTransformation {
    private val unit = "원"
    private val formatter = DecimalFormat("#,###")


    override fun filter(text: AnnotatedString): TransformedText {

        if (text.text.isEmpty()) {
            return TransformedText(text, OffsetMapping.Identity)
        }

        val originalText = text.text.filter { it.isDigit() }
        val longValue =
            originalText.toLongOrNull() ?: return TransformedText(text, OffsetMapping.Identity)

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

        return TransformedText(
            androidx.compose.ui.text.AnnotatedString(formattedText),
            offsetMapping
        )
    }
}
