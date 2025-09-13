package com.depromeet.team5.core.designsystem.foundation

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

object HedgeColor {
    /*
    * semantic
    * */
    object Text {
        val Title: Color
            @Composable get() = com.depromeet.team5.core.designsystem.util.Color(
                lightMode = GREY_900,
                darkMode = GREY_900,
            )
        val Primary: Color
            @Composable get() = com.depromeet.team5.core.designsystem.util.Color(
                lightMode = GREY_800,
                darkMode = GREY_800,
            )
        val Secondary: Color
            @Composable get() = com.depromeet.team5.core.designsystem.util.Color(
                lightMode = GREY_600,
                darkMode = GREY_600,
            )
        val Disabled: Color
            @Composable get() = com.depromeet.team5.core.designsystem.util.Color(
                lightMode = GREY_300,
                darkMode = GREY_300,
            )
    }

    object Trade {
        val Buy: Color
            @Composable get() = com.depromeet.team5.core.designsystem.util.Color(
                lightMode = RED_500,
                darkMode = RED_500,
            )
        val Sell: Color
            @Composable get() = com.depromeet.team5.core.designsystem.util.Color(
                lightMode = BLUE_500,
                darkMode = BLUE_500,
            )
    }

    object Feedback {
        val Error: Color
            @Composable get() = com.depromeet.team5.core.designsystem.util.Color(
                lightMode = RED_700,
                darkMode = RED_700,
            )
        val AI: Color
            @Composable get() = com.depromeet.team5.core.designsystem.util.Color(
                lightMode = Color(0xFF8A66FF),
                darkMode = Color(0xFF8A66FF),
            )
    }

    object Brand {
        val Primary: Color
            @Composable get() = com.depromeet.team5.core.designsystem.util.Color(
                lightMode = GREY_900,
                darkMode = GREY_900,
            )

        val Secondary: Color
            @Composable get() = com.depromeet.team5.core.designsystem.util.Color(
                lightMode = GREY_100,
                darkMode = GREY_100,
            )

        val Disabled: Color
            @Composable get() = com.depromeet.team5.core.designsystem.util.Color(
                lightMode = GREY_300,
                darkMode = GREY_300,
            )
    }

    object Neutral {
        val BackgroundDefault: Color
            @Composable get() = com.depromeet.team5.core.designsystem.util.Color(
                lightMode = Color(0xFFFFFFFF),
                darkMode = Color(0xFFFFFFFF),
            )
        val BackgroundSecondary: Color
            @Composable get() = com.depromeet.team5.core.designsystem.util.Color(
                lightMode = Color(0xFFF3F4F6),
                darkMode = Color(0xFFF3F4F6),
            )
    }

    /*
    * palette
    * todo 추후 internal로 변경하고 semantic만 노출(아직 semantic 정의가 다 안 되었고, 피그마 화면에 연결도 안 되어있음)
    * */
    val GREY_100 = Color(0xFFF4F5F7)
    val GREY_200 = Color(0xFFE5E7EB)
    val GREY_300 = Color(0xFFD1D5DB)
    val GREY_400 = Color(0xFF9CA3AF)
    val GREY_500 = Color(0xFF6B7280)
    val GREY_600 = Color(0xFF4B5563)
    val GREY_700 = Color(0xFF374151)
    val GREY_800 = Color(0xFF1F2937)
    val GREY_900 = Color(0xFF111827)

    val GREY_OPACITY_200 = Color(0xFF191A20).copy(alpha = 0.06f)
    val GREY_OPACITY_300 = Color(0xFF191A20).copy(alpha = 0.10f)
    val GREY_OPACITY_600 = Color(0xFF191A20).copy(alpha = 0.42f)

    val RED_500 = Color(0xFFFF4C4C)
    val RED_700 = Color(0xFFDF1F1F)

    val BLUE_500 = Color(0xFF3282F5)

    val BRAND_500 = Color(0xFF111827)

    val BACKGROUND_GREY = Color(0xFFF3F4F6)
    val BACKGROUND_WHITE = Color(0xFFFFFFFF)

}