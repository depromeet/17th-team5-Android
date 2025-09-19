package com.depromeet.team5.features.retrospect.screen.visualtransmation

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation


class DateVisualTransformation : VisualTransformation {

    override fun filter(text: AnnotatedString): TransformedText {
        val originalText = text.text.take(8)

        val formattedText = when (originalText.length) {
            in 0..4 -> originalText
            in 5..6 -> "${originalText.substring(0, 4)}년 ${originalText.substring(4)}"
            in 7..8 -> {
                val year = originalText.substring(0, 4)
                val month = originalText.substring(4, 6)
                val day = originalText.substring(6)
                "${year}년 ${month}월 $day" + if (originalText.length == 8) "일" else ""
            }

            else -> ""
        }

        val offsetMapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                return when {
                    offset <= 4 -> offset
                    offset <= 6 -> offset + 2
                    offset <= 8 -> offset + 4
                    else -> 14
                }
            }

            override fun transformedToOriginal(offset: Int): Int {
                return when {
                    offset <= 4 -> offset
                    offset <= 7 -> offset - 2
                    offset <= 12 -> offset - 4
                    else -> 8
                }
            }
        }

        return TransformedText(
            text = AnnotatedString(formattedText),
            offsetMapping = offsetMapping
        )
    }
}
