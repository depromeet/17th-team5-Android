package com.depromeet.team5.features.retrospect.screen.visualtransmation

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation


class DateVisualTransformation : VisualTransformation {

    override fun filter(text: AnnotatedString): TransformedText {
        val originalText = text.text

        if (text.text.isEmpty()) {
            return TransformedText(text, OffsetMapping.Identity)
        }

        // 입력 길이에 따라 포맷팅을 다르게 적용
        val formattedText = when (originalText.length) {
            in 1..4 -> originalText
            in 5..6 -> "${originalText.substring(0, 4)}년 ${originalText.substring(4)}"
            else -> {
                val year = originalText.substring(0, 4)
                val month = originalText.substring(4, 6)
                val day = originalText.substring(6, minOf(originalText.length, 8))
                "${year}년 ${month}월 $day" + if (originalText.length >= 9) "일" else ""
            }
        }

        val offsetMapping = object : OffsetMapping {

            override fun originalToTransformed(offset: Int): Int {
                return when {
                    offset <= 4 -> offset
                    offset <= 6 -> offset + 2
                    else -> offset + 4
                }
            }

            override fun transformedToOriginal(offset: Int): Int {
                return when {
                    offset <= 4 -> offset
                    offset <= 7 -> offset - 2
                    else -> offset - 4
                }
            }
        }

        return TransformedText(
            text = AnnotatedString(formattedText),
            offsetMapping = offsetMapping
        )
    }
}
