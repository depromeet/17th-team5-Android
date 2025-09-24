package com.depromeet.team5.features.retrospect.screen.visualtransmation

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale


class USDCurrencyVisualTransformation : VisualTransformation {

    val unit = "$"
    private val formatter = DecimalFormat("#,##0.00", DecimalFormatSymbols(Locale.US))


    override fun filter(text: AnnotatedString): TransformedText {

        if (text.text.isEmpty()) {
            return TransformedText(text, OffsetMapping.Identity)
        }

        val originalText = text.text.filter { it.isDigit() }
        val longValue =
            originalText.toLongOrNull() ?: return TransformedText(text, OffsetMapping.Identity)

        val doubleValue = longValue.toDouble() / 100.0
        val formattedText = "$${formatter.format(doubleValue)}"

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

        return TransformedText(AnnotatedString(formattedText), offsetMapping)
    }
}
