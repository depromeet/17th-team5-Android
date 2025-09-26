package com.depromeet.team5.core.designsystem.foundation

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.depromeet.team5.core.designsystem.R


object HedgeTypography {

    private val font = FontFamily(
        Font(R.font.pretendard_regular, FontWeight.Normal),
        Font(R.font.pretendard_medium, FontWeight.Medium),
        Font(R.font.pretendard_semibold, FontWeight.SemiBold),
        Font(R.font.pretendard_bold, FontWeight.Bold),
    )

    private object Base {
        val Regular = TextStyle(fontFamily = font, fontWeight = FontWeight.Normal)
        val Medium = TextStyle(fontFamily = font, fontWeight = FontWeight.Medium)
        val Semibold = TextStyle(fontFamily = font, fontWeight = FontWeight.SemiBold)
        val Bold = TextStyle(fontFamily = font, fontWeight = FontWeight.Bold)
    }

    object Headline1 {
        val Regular = Base.Regular.copy(fontSize = 22.sp, lineHeight = 1.36.em, letterSpacing = (-0.0194).em)
        val Medium = Base.Medium.copy(fontSize = 22.sp, lineHeight = 1.36.em, letterSpacing = (-0.0194).em)
        val SemiBold = Base.Semibold.copy(fontSize = 22.sp, lineHeight = 1.36.em, letterSpacing = (-0.0194).em)
    }

    object Headline2 {
        val Regular = Base.Regular.copy(fontSize = 18.sp, lineHeight = 1.44.em, letterSpacing = (-0.02).em)
        val Medium = Base.Medium.copy(fontSize = 18.sp, lineHeight = 1.44.em, letterSpacing = (-0.02).em)
        val SemiBold = Base.Semibold.copy(fontSize = 18.sp, lineHeight = 1.44.em, letterSpacing = (-0.02).em)
    }

    object Body1 {
        val Regular = Base.Regular.copy(fontSize = 17.sp, lineHeight = 1.47.em, letterSpacing = 0.em)
        val Medium = Base.Medium.copy(fontSize = 17.sp, lineHeight = 1.47.em, letterSpacing = 0.em)
        val SemiBold = Base.Semibold.copy(fontSize = 17.sp, lineHeight = 1.47.em, letterSpacing = 0.em)
    }

    object Body2 {
        val Regular = Base.Regular.copy(fontSize = 16.sp, lineHeight = 1.48.em, letterSpacing = 0.0057.em)
        val Medium = Base.Medium.copy(fontSize = 16.sp, lineHeight = 1.48.em, letterSpacing = 0.0057.em)
        val SemiBold = Base.Semibold.copy(fontSize = 16.sp, lineHeight = 1.48.em, letterSpacing = 0.0057.em)
    }

    object Body3 {
        val Regular = Base.Regular.copy(fontSize = 15.sp, lineHeight = 1.46.em, letterSpacing = 0.0096.em)
        val Medium = Base.Medium.copy(fontSize = 15.sp, lineHeight = 1.46.em, letterSpacing = 0.0096.em)
        val SemiBold = Base.Semibold.copy(fontSize = 15.sp, lineHeight = 1.46.em, letterSpacing = 0.0096.em)
    }

    object Label1 {
        val Regular = Base.Regular.copy(fontSize = 14.sp, lineHeight = 1.42.em, letterSpacing = 0.0145.em)
        val Medium = Base.Medium.copy(fontSize = 14.sp, lineHeight = 1.42.em, letterSpacing = 0.0145.em)
        val SemiBold = Base.Semibold.copy(fontSize = 14.sp, lineHeight = 1.42.em, letterSpacing = 0.0145.em)
    }

    object Label2 {
        val Regular = Base.Regular.copy(fontSize = 13.sp, lineHeight = 1.39.em, letterSpacing = 0.02.em)
        val Medium = Base.Medium.copy(fontSize = 13.sp, lineHeight = 1.39.em, letterSpacing = 0.02.em)
        val SemiBold = Base.Semibold.copy(fontSize = 13.sp, lineHeight = 1.39.em, letterSpacing = 0.02.em)
    }

    object Caption1 {
        val Regular = Base.Regular.copy(fontSize = 12.sp, lineHeight = 1.33.em, letterSpacing = 0.02.em)
        val Medium = Base.Medium.copy(fontSize = 12.sp, lineHeight = 1.33.em, letterSpacing = 0.02.em)
        val Semibold = Base.Semibold.copy(fontSize = 12.sp, lineHeight = 1.33.em, letterSpacing = 0.02.em)
    }

    object Caption2 {
        val Regular = Base.Regular.copy(fontSize = 11.sp, lineHeight = 1.27.em, letterSpacing = 0.02.em)
        val Medium = Base.Medium.copy(fontSize = 11.sp, lineHeight = 1.27.em, letterSpacing = 0.02.em)
        val Bold = Base.Bold.copy(fontSize = 11.sp, lineHeight = 1.27.em, letterSpacing = 0.02.em)
    }
}

