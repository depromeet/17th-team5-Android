package com.depromeet.team5.features.retrospect.screen

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import com.depromeet.team5.features.retrospect.screen.annotation.CurrencyUnit
import com.depromeet.team5.features.retrospect.screen.annotation.KOREAN
import java.text.DecimalFormat


class CurrencyVisualTransformation(
    private val unit: CurrencyUnit = KOREAN
) : VisualTransformation {
    private val formatter = DecimalFormat("#,###")


    override fun filter(text: AnnotatedString): TransformedText {

        if (text.text.isEmpty() || text.text.toLongOrNull() == null) {
            return TransformedText(text, OffsetMapping.Identity)
        }

        val originalText = text.text
        val formattedText = "${formatter.format(originalText.toLong())}$unit"

        val offsetMapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                val commas = (originalText.length - 1) / 3
                return offset + commas
            }

            override fun transformedToOriginal(offset: Int): Int {
                val textWithoutSuffix = formattedText.removeSuffix(unit)
                var originalOffset = 0
                var transformedCount = 0

                textWithoutSuffix.forEach { char ->
                    if (transformedCount >= offset) return originalOffset
                    if (char.isDigit()) {
                        originalOffset++
                    }
                    transformedCount++
                }

                return originalOffset
            }
        }

        return TransformedText(AnnotatedString(formattedText), offsetMapping)
    }
}
