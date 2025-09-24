package com.depromeet.team5.features.retrospect.screen.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.depromeet.team5.features.retrospect.R


@Composable
internal fun HedgeTopbar(
    modifier: Modifier = Modifier,
    onBackPressed: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(44.dp)
            .background(color = Color(0xFFF3F4F6)),
        contentAlignment = Alignment.CenterStart
    ) {

        Box(
            modifier = Modifier
                .size(40.dp)
                .padding(start = 12.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier
                    .size(24.dp),
                painter = painterResource(R.drawable.icon_arrow_left),
                contentDescription = null
            )
        }
    }
}
