package com.depromeet.team5.core.ui.model

import com.depromeet.team5.core.ui.R

enum class ConsentType(
    val titleRes: Int,
) {
    REQUIRED(R.string.agreements_required),
    OPTIONAL(R.string.agreements_optional)
}

enum class AgreementsType(
    val titleRes: Int,
    val type: ConsentType,
    val link: String? = null,
) {
    AGE_OVER_14(
        R.string.agreements_agree_age_over_14,
        ConsentType.REQUIRED
    ),
    TERMS(
        R.string.agreements_agree_service,
        ConsentType.REQUIRED,
        "https://www.notion.so/2a0219cc9c34801eba01ea91797dfa0f"
    ),
    PRIVACY(
        R.string.agreements_agree_private_information,
        ConsentType.REQUIRED,
        "https://www.notion.so/2a0219cc9c3480b591ebee5e6cef6d1e"
    ),
    MARKETING(
        R.string.agreements_agree_marketing_information,
        ConsentType.OPTIONAL,
        "https://www.notion.so/2a0219cc9c3480b591ebee5e6cef6d1e"
    )
}