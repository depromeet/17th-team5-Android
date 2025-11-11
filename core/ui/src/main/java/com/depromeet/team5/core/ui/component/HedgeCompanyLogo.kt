package com.depromeet.team5.core.ui.component

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import coil3.compose.AsyncImage
import com.depromeet.team5.core.designsystem.foundation.HedgeIcon

@Composable
fun HedgeCompanyLogo(
    modifier: Modifier = Modifier,
    logoUrl: String? = null
) {
    if (logoUrl == null) {
        Image(
            imageVector = HedgeIcon.COMPANY_LOGO,
            contentDescription = null,
            modifier = modifier
        )
    } else {
        AsyncImage(
            model = logoUrl,
            contentDescription = null,
            modifier = modifier
        )
    }
}