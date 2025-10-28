package com.depromeet.team5.features.retrospect.screen.visualtransmation

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation


class UnitVisualTransformation(
    private var prefix: String = "",
    private val unit: String
) : VisualTransformation {

    override fun filter(text: AnnotatedString): TransformedText {
        val originalText = text.text

        if (text.text.isEmpty() || text.text.toLongOrNull() == null) {
            return TransformedText(text, OffsetMapping.Identity)
        }

        val formattedText = "$prefix${originalText}${unit}"

        val offsetMapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                return offset + prefix.length
            }

            override fun transformedToOriginal(offset: Int): Int {
                return minOf(offset, originalText.length - prefix.length)
            }
        }

        return TransformedText(
            text = AnnotatedString(formattedText),
            offsetMapping = offsetMapping
        )
    }
}
