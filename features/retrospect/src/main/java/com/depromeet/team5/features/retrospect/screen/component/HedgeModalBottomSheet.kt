package com.depromeet.team5.features.retrospect.screen.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.depromeet.team5.core.designsystem.foundation.HedgeColor
import com.depromeet.team5.core.designsystem.foundation.HedgeIcon
import com.depromeet.team5.core.designsystem.foundation.HedgeTypography


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HedgeModalBottomSheet(
    modifier: Modifier = Modifier
) {

    ModalBottomSheet(
        onDismissRequest = {}
    ) {


    }
}

@Composable
fun HedgePrincipleListItem(
    title: String,
    icon: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 12.dp)
            .background(HedgeColor.WHITE),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .background(
                    color = HedgeColor.Neutral.BackgroundSecondary,
                    shape = RoundedCornerShape(39.dp)
                )
        ) {
            icon()
        }

        Text(
            modifier = Modifier
                .padding(start = 12.dp)
                .weight(1f),
            text = title,

            style = HedgeTypography.Body3.Medium,
            color = HedgeColor.Text.Title,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Image(
            imageVector = HedgeIcon.ArrowRightThin,
            contentDescription = null,
            colorFilter = ColorFilter.tint(color = HedgeColor.Text.Disabled)
        )
    }
}

@Preview
@Composable
fun HedgeModalBottomSheetPreview() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(HedgeColor.WHITE)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Absolute.SpaceBetween
            ) {
                Text(
                    modifier = Modifier
                        .padding(start = 20.dp, top = 23.5.dp, bottom = 19.5.dp),
                    text = "어떤 원칙으로 회고할까요?",
                    style = HedgeTypography.Body1.SemiBold,
                    color = HedgeColor.GREY_900
                )

                Box(
                    modifier = Modifier
                        .padding(end = 20.dp)
                        .size(28.dp)
                        .background(
                            color = HedgeColor.Neutral.BackgroundSecondary,
                            shape = RoundedCornerShape(39.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        modifier = Modifier.size(14.dp),
                        imageVector = HedgeIcon.CloseThick,
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(color = HedgeColor.Text.Assistive)
                    )
                }
            }

            Column(
                modifier = Modifier.fillMaxWidth()
            ) {

                Text(
                    text = "기본",
                    modifier = Modifier.padding(start = 20.dp),
                    style = HedgeTypography.Label2.Medium,
                    color = HedgeColor.Text.Alternative
                )

                Spacer(modifier = Modifier.size(4.dp))

                HedgePrincipleListItem(
                    icon = {},
                    title = "초보자를 위한 매도 원칙"
                )

                HedgePrincipleListItem(
                    icon = {},
                    title = "초보자를 위한 매수 원칙"
                )
            }

            Text(
                text = "내가 만든",
                modifier = Modifier.padding(start = 20.dp, top = 12.dp),
                style = HedgeTypography.Label2.Medium,
                color = HedgeColor.Text.Alternative
            )

            LazyColumn {
                items(
                    items = listOf<Int>(),
                    key = { }

                ) { index ->
                    HedgePrincipleListItem(
                        icon = {},
                        title = "초보자를 위한 매도 원칙"
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun HedgePrincipleListItemPreview() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(HedgeColor.WHITE)
    ) {
        HedgePrincipleListItem(
            title = "초보자를 위한 매도 원칙",
            icon = {

            }
        )

    }
}
