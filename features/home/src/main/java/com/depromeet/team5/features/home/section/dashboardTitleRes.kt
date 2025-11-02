package com.depromeet.team5.features.home.section

import androidx.annotation.StringRes
import com.depromeet.team5.features.home.R

@StringRes
fun dashboardTitleRes(
    percentage: Int,
    platinum: Int,
    gold: Int,
    silver: Int,
    bronze: Int
): Int {
    val total = platinum + gold + silver + bronze

    if (total == 0) return R.string.home_tab_dashboard_exception_0

    val validPercentage = percentage.takeIf { it in 0..100 }
        ?: return R.string.home_tab_dashboard_exception_NaN

    if (total <= 2) return R.string.home_tab_dashboard_exception_2

    return when {
        validPercentage >= 60 -> R.string.home_tab_dashboard_60
        validPercentage in 40..59 -> R.string.home_tab_dashboard_40_59
        validPercentage in 20..39 -> R.string.home_tab_dashboard_20_39
        else -> R.string.home_tab_dashboard_20
    }
}
