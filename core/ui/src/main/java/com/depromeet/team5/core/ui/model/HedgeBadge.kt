package com.depromeet.team5.core.ui.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable
import com.depromeet.team5.core.ui.R

@Immutable
enum class HedgeBadge(
    @DrawableRes val iconRes: Int,
    @DrawableRes val thumbnailRes: Int,
    @StringRes val titleRes: Int,
    @StringRes val descriptionRes: Int
) {
    BRONZE(
        iconRes = R.drawable.img_badge_bronze,
        thumbnailRes = R.drawable.img_badge_bronze_thumbnail,
        titleRes = R.string.badge_title_bronze,
        descriptionRes = R.string.badge_description_bronze
    ),
    SILVER(
        iconRes = R.drawable.img_badge_silver,
        thumbnailRes = R.drawable.img_badge_silver_thumbnail,
        titleRes = R.string.badge_title_silver,
        descriptionRes = R.string.badge_description_silver
    ),
    GOLD(
        iconRes = R.drawable.img_badge_gold,
        thumbnailRes = R.drawable.img_badge_gold_thumbnail,
        titleRes = R.string.badge_title_gold,
        descriptionRes = R.string.badge_description_gold
    ),
    PLATINUM(
        iconRes = R.drawable.img_badge_platinum,
        thumbnailRes = R.drawable.img_badge_platinum_thumbnail,
        titleRes = R.string.badge_title_platinum,
        descriptionRes = R.string.badge_description_platinum
    );

    companion object {
        fun fromBadge(badge: String): HedgeBadge = when (badge) {
            BRONZE.name.lowercase() -> BRONZE
            SILVER.name.lowercase() -> SILVER
            GOLD.name.lowercase() -> GOLD
            PLATINUM.name.lowercase() -> PLATINUM
            else -> BRONZE
        }
    }
}