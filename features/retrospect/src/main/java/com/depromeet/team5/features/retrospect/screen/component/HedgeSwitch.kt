package com.depromeet.team5.features.retrospect.screen.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.depromeet.team5.features.retrospect.R


@Composable
fun HedgeSwitch(
    modifier: Modifier = Modifier,
    isToggled: Boolean,
    onToggleChanged: (Boolean) -> Unit,
    offContent: @Composable () -> Unit,
    onContent: @Composable () -> Unit,
    selectedColor: Color = colorResource(R.color.white),
    unselectedColor: Color = Color.Transparent
) {
    val offItemInteractionSource = remember { MutableInteractionSource() }
    val onItemInteractionSource = remember { MutableInteractionSource() }

    val offCardColor by animateColorAsState(if (!isToggled) selectedColor else unselectedColor)
    val onCardColor by animateColorAsState(if (isToggled) selectedColor else unselectedColor)

    val offCardElevation by animateDpAsState(if (!isToggled) 4.dp else 0.dp)
    val onCardElevation by animateDpAsState(if (isToggled) 4.dp else 0.dp)

    Box(
        modifier = modifier
            .wrapContentSize()
            .clip(RoundedCornerShape(8.dp))
            .background(
                colorResource(R.color.gray200)
            )
    ) {
        Row(
            modifier = Modifier.height(IntrinsicSize.Max),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Card(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(start = 3.dp, top = 3.dp, bottom = 3.dp)
                    .clickable(
                        interactionSource = offItemInteractionSource,
                        indication = null
                    ) {
                        onToggleChanged(false)
                    },
                shape = RoundedCornerShape(6.dp),
                colors = CardDefaults.cardColors(
                    containerColor = offCardColor,
                    contentColor = colorResource(R.color.gray700)
                ),
                elevation = CardDefaults.elevatedCardElevation(
                    defaultElevation = offCardElevation,
                    disabledElevation = 0.dp
                )
            ) {
                offContent()
            }

            Card(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(3.dp)
                    .clickable(
                        interactionSource = onItemInteractionSource,
                        indication = null
                    ) {
                        onToggleChanged(true)
                    },
                shape = RoundedCornerShape(6.dp),
                colors = CardDefaults.cardColors(
                    containerColor = onCardColor,
                    contentColor = colorResource(R.color.gray700)
                ),
                elevation = CardDefaults.elevatedCardElevation(
                    defaultElevation = onCardElevation,
                    disabledElevation = 0.dp
                )
            ) {
                onContent()
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewCustomSwitch() {
    var isToggled by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .wrapContentSize()
            .padding(30.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HedgeSwitch(
            isToggled = isToggled,
            onToggleChanged = { isResult ->
                isToggled = isResult
            },
            offContent = {
                Box(
                    modifier = Modifier
                        .fillMaxHeight(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        modifier = Modifier
                            .padding(horizontal = 7.dp, vertical = 6.dp),
                        text = "원",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.W600
                    )
                }
            },
            onContent = {
                Box(
                    modifier = Modifier.fillMaxHeight(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        modifier = Modifier
                            .padding(horizontal = 7.dp, vertical = 6.dp),
                        text = "$",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.W600,
                        textAlign = TextAlign.Center
                    )
                }
            }
        )
    }
}
