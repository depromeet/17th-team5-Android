package com.depromeet.teem5.features.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.depromeet.team5.core.designsystem.foundation.HedgeColor
import com.depromeet.team5.core.designsystem.foundation.HedgeTypography
import com.depromeet.teem5.features.home.R

@Composable
fun HomeFloatingActionButton(
    fabChecked: Boolean,
    onCheckedChange: () -> Unit,
    onBuyClick: () -> Unit,
    onSellClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val interaction = remember { MutableInteractionSource() }

    Box(
        modifier = modifier
            .background(
                color = if (fabChecked)
                    Color.Black.copy(alpha = 0.5f)
                else
                    HedgeColor.Transparent
            )
            .padding(end = 20.dp, bottom = 100.dp)
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier.align(Alignment.BottomEnd),
            horizontalAlignment = Alignment.End
        ) {
            if (fabChecked)
                Column(
                    modifier = Modifier
                        .padding(bottom = 12.dp)
                        .background(
                            color = HedgeColor.Neutral.BackgroundDefault,
                            shape = RoundedCornerShape(18.dp)
                        )
                ) {
                    FABItem(
                        interaction = interaction,
                        onClick = onBuyClick,
                        itemTitle = stringResource(R.string.home_buy_button),
                        itemIconColor = HedgeColor.Trade.Buy,
                        itemIconBackgroundColor = Color(0xFFFFEBED),
                        modifier = Modifier.padding(
                            start = 14.dp,
                            end = 14.dp,
                            top = 14.dp,
                            bottom = 8.dp
                        )
                    )

                    FABItem(
                        interaction = interaction,
                        onClick = onSellClick,
                        itemTitle = stringResource(R.string.home_sell_button),
                        itemIconColor = HedgeColor.Trade.Sell,
                        itemIconBackgroundColor = Color(0xFFEAF4FF),
                        modifier = Modifier.padding(
                            start = 14.dp,
                            end = 14.dp,
                            top = 8.dp,
                            bottom = 14.dp
                        )
                    )
                }

            Image(
                painter = if (fabChecked)
                    painterResource(R.drawable.ic_close)
                else
                    painterResource(R.drawable.ic_plus),
                contentDescription = null,
                modifier = Modifier
                    .clickable(
                        interactionSource = interaction,
                        indication = null
                    ) {
                        onCheckedChange()
                    }
                    .background(
                        color = if (fabChecked)
                            HedgeColor.WHITE
                        else
                            HedgeColor.Brand.Primary,
                        shape = CircleShape
                    )
                    .padding(17.dp)
                    .size(18.dp)
            )
        }

    }
}

@Composable
private fun FABItem(
    interaction: MutableInteractionSource,
    onClick: () -> Unit,
    itemTitle: String,
    itemIconColor: Color,
    itemIconBackgroundColor: Color,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.ic_pencil),
            contentDescription = null,
            tint = itemIconColor,
            modifier = Modifier
                .padding(end = 8.dp)
                .clickable(
                    interactionSource = interaction,
                    indication = null
                ) {
                    onClick()
                }
                .background(color = itemIconBackgroundColor)
                .padding(7.dp)
                .size(14.dp)
        )

        Text(
            text = itemTitle,
            style = HedgeTypography.Body2.SemiBold,
            color = HedgeColor.Text.Primary,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeFABPreview() {
    var fabChecked by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        HomeFloatingActionButton(
            fabChecked = fabChecked,
            onCheckedChange = { fabChecked = !fabChecked },
            onBuyClick = {},
            onSellClick = {},
            modifier = Modifier.align(Alignment.BottomEnd)
        )
    }
}