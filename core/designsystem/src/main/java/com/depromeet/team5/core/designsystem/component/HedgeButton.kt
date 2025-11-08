package com.depromeet.team5.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.ripple
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.depromeet.team5.core.designsystem.component.HedgeButton.CallToAction.Background
import com.depromeet.team5.core.designsystem.foundation.HedgeColor
import com.depromeet.team5.core.designsystem.foundation.HedgeIcon
import com.depromeet.team5.core.designsystem.foundation.HedgeTypography

object HedgeButton {
    object Action {
        object Color {
            object Filled {
                val Primary: ButtonColors
                    @Composable get() = ButtonDefaults.buttonColors(
                        backgroundColor = HedgeColor.Brand.Primary,
                        contentColor = HedgeColor.Text.White,
                        disabledBackgroundColor = HedgeColor.Brand.Disabled,
                        disabledContentColor = HedgeColor.Text.White,
                    )

                val Secondary: ButtonColors
                    @Composable get() = ButtonDefaults.buttonColors(
                        backgroundColor = HedgeColor.Neutral.BackgroundSecondary,
                        contentColor = HedgeColor.Text.Title,
                        disabledBackgroundColor = HedgeColor.Neutral.BackgroundSecondary,
                        disabledContentColor = HedgeColor.Brand.Disabled,
                    )
            }
        }

        sealed class Size(
            val contentPadding: PaddingValues,
            val minWidth: Dp,
            val minHeight: Dp,
            val shape: Shape,
            val textStyle: TextStyle,
        ) {

            data object Large : Size(
                contentPadding = PaddingValues(horizontal = 32.dp, vertical = 16.dp),
                minWidth = 96.dp,
                minHeight = 56.dp,
                shape = RoundedCornerShape(18.dp),
                textStyle = HedgeTypography.Body1.SemiBold,
            )

            data object Medium : Size(
                contentPadding = PaddingValues(horizontal = 24.dp, vertical = 12.dp),
                minWidth = 78.dp,
                minHeight = 48.dp,
                shape = RoundedCornerShape(14.dp),
                textStyle = HedgeTypography.Body1.Medium,
            )

            data object Small : Size(
                contentPadding = PaddingValues(horizontal = 20.dp, vertical = 10.dp),
                minWidth = 70.dp,
                minHeight = 42.dp,
                shape = RoundedCornerShape(12.dp),
                textStyle = HedgeTypography.Body3.SemiBold,
            )

            data object Tiny : Size(
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
                minWidth = 50.dp,
                minHeight = 32.dp,
                shape = RoundedCornerShape(8.dp),
                textStyle = HedgeTypography.Label2.SemiBold,
            )
        }

        @Composable
        fun Filled(
            text: CharSequence,
            onClick: () -> Unit,
            modifier: Modifier = Modifier,
            buttonColors: ButtonColors = Color.Filled.Primary,
            size: Size = Size.Large,
            enabled: Boolean = true,
            forceClickable: Boolean = true,
            interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
        ) {
            Button(
                onClick = onClick,
                modifier = modifier
                    .widthIn(min = size.minWidth)
                    .heightIn(min = size.minHeight),
                colors = buttonColors,
                enabled = enabled || forceClickable,
                interactionSource = interactionSource,
                elevation = null,
                shape = size.shape,
                contentPadding = size.contentPadding,
            ) {
                if (text is AnnotatedString) {
                    Text(
                        text = text,
                        style = size.textStyle
                    )
                } else {
                    Text(
                        text = text.toString(),
                        style = size.textStyle,
                    )
                }
            }
        }
    }

    object Text {
        sealed class Color {

            @get:Composable
            abstract val active: androidx.compose.ui.graphics.Color

            @get:Composable
            abstract val disabled: androidx.compose.ui.graphics.Color

            data object Primary : Color() {
                override val active: androidx.compose.ui.graphics.Color
                    @Composable get() = HedgeColor.Brand.Darken

                override val disabled: androidx.compose.ui.graphics.Color
                    @Composable get() = HedgeColor.Text.Disabled
            }

            data object Secondary : Color() {
                override val active: androidx.compose.ui.graphics.Color
                    @Composable get() = HedgeColor.Text.Alternative

                override val disabled: androidx.compose.ui.graphics.Color
                    @Composable get() = HedgeColor.Text.Disabled
            }
        }

        sealed class Size(
            val shape: RoundedCornerShape,
            val textStyle: TextStyle,
            val iconWidth: Dp,
            val iconHeight: Dp,
        ) {
            data object Large : Size(
                shape = RoundedCornerShape(8.dp),
                textStyle = HedgeTypography.Body1.SemiBold,
                iconWidth = 24.dp,
                iconHeight = 24.dp,
            )

            data object Medium : Size(
                shape = RoundedCornerShape(6.dp),
                textStyle = HedgeTypography.Body3.SemiBold,
                iconWidth = 20.dp,
                iconHeight = 20.dp,
            )

            data object Small : Size(
                shape = RoundedCornerShape(6.dp),
                textStyle = HedgeTypography.Label2.SemiBold,
                iconWidth = 16.dp,
                iconHeight = 16.dp,
            )
        }
    }

    @Composable
    fun Text(
        text: CharSequence,
        imageVector: ImageVector? = HedgeIcon.ArrowRightThin,
        onClick: () -> Unit,
        modifier: Modifier = Modifier,
        size: Text.Size = Text.Size.Large,
        color: Text.Color = Text.Color.Primary,
        enabled: Boolean = true,
        forceClickable: Boolean = true,
        interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    ) {
        Row(
            modifier = modifier
                .clip(size.shape)
                .clickable(
                    enabled = enabled || forceClickable,
                    interactionSource = interactionSource,
                    indication = ripple(),
                    onClick = onClick
                )
                .padding(horizontal = 6.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            if (text is AnnotatedString) {
                Text(
                    text = text,
                    style = size.textStyle,
                    color = if (enabled) color.active else color.disabled
                )
            } else {
                Text(
                    text = text.toString(),
                    style = size.textStyle,
                    color = if (enabled) color.active else color.disabled
                )
            }
            imageVector?.let {
                Icon(
                    modifier = Modifier.size(
                        width = size.iconWidth, height = size.iconHeight
                    ),
                    imageVector = it,
                    contentDescription = null,
                    tint = if (enabled) color.active else color.disabled,
                )
            }
        }
    }

    object CallToAction {

        sealed class Background {
            object Transparent : Background()
            data class Gradient(val color: Color) : Background()
        }

        @Composable
        fun Single(
            text: CharSequence,
            onClick: () -> Unit,
            modifier: Modifier = Modifier,
            background: Background = Background.Transparent,
        ) {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .ctaBackground(background)
                    .padding(top = 24.dp, bottom = 20.dp, start = 20.dp, end = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Action.Filled(
                    text = text,
                    modifier = Modifier.fillMaxWidth(),
                    onClick = onClick
                )
            }
        }

        @Composable
        fun Double(
            text1: CharSequence,
            text2: CharSequence,
            onClickButton1: () -> Unit,
            onClickButton2: () -> Unit,
            modifier: Modifier = Modifier,
            background: Background = Background.Transparent,
        ) {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .ctaBackground(background)
                    .padding(top = 24.dp, bottom = 20.dp, start = 20.dp, end = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Action.Filled(
                        text = text2,
                        buttonColors = Action.Color.Filled.Secondary,
                        modifier = Modifier.weight(1f),
                        onClick = onClickButton2
                    )
                    Action.Filled(
                        text = text1,
                        modifier = Modifier.weight(1f),
                        onClick = onClickButton1
                    )
                }
            }
        }

        @Composable
        fun SingleWithSecondary(
            text: CharSequence,
            secondaryText: CharSequence,
            onClickButton: () -> Unit,
            onClickSecondaryButton: () -> Unit,
            modifier: Modifier = Modifier,
            background: Background = Background.Transparent,
            secondaryImageVector: ImageVector? = null,
        ) {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .ctaBackground(background)
                    .padding(top = 24.dp, bottom = 20.dp, start = 20.dp, end = 20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Action.Filled(
                    text = text,
                    modifier = Modifier.fillMaxWidth(),
                    onClick = onClickButton
                )
                Text(
                    text = secondaryText,
                    size = Text.Size.Large,
                    imageVector = secondaryImageVector,
                    onClick = onClickSecondaryButton
                )
            }
        }

        @Composable
        fun DoubleWithSecondary(
            text1: CharSequence,
            text2: CharSequence,
            secondaryText: CharSequence,
            onClickButton1: () -> Unit,
            onClickButton2: () -> Unit,
            onClickSecondaryButton: () -> Unit,
            modifier: Modifier = Modifier,
            background: Background = Background.Transparent,
            secondaryImageVector: ImageVector? = null,
        ) {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .ctaBackground(background)
                    .padding(top = 24.dp, bottom = 20.dp, start = 20.dp, end = 20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Action.Filled(
                        text = text2,
                        buttonColors = Action.Color.Filled.Secondary,
                        modifier = Modifier.weight(1f),
                        onClick = onClickButton2
                    )
                    Action.Filled(
                        text = text1,
                        modifier = Modifier.weight(1f),
                        onClick = onClickButton1
                    )
                }
                Text(
                    text = secondaryText,
                    size = Text.Size.Large,
                    imageVector = secondaryImageVector,
                    onClick = onClickSecondaryButton
                )
            }
        }
    }

    private fun Modifier.ctaBackground(background: Background): Modifier = when (background) {
        Background.Transparent -> this
        is Background.Gradient -> this.then(
            Modifier.composed {
                val brush = remember(background.color) {
                    Brush.verticalGradient(
                        0f to Color.Transparent,
                        0.21f to background.color
                    )
                }
                Modifier.background(brush)
            }
        )
    }
}


@Composable
@Preview
fun FilledButtonPreview() {
    Column(
        modifier = Modifier
            .background(HedgeColor.Neutral.BackgroundDefault)
            .padding(vertical = 48.dp, horizontal = 12.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            listOf(true, false).forEach { enabled ->
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    HedgeButton.Action.Filled(
                        text = "Primary",
                        enabled = enabled,
                        onClick = {}
                    )
                    HedgeButton.Action.Filled(
                        text = "Primary",
                        size = HedgeButton.Action.Size.Medium,
                        enabled = enabled,
                        onClick = {}
                    )
                    HedgeButton.Action.Filled(
                        text = "Primary",
                        size = HedgeButton.Action.Size.Small,
                        enabled = enabled,
                        onClick = {}
                    )
                    HedgeButton.Action.Filled(
                        text = "Primary",
                        size = HedgeButton.Action.Size.Tiny,
                        enabled = enabled,
                        onClick = {}
                    )
                }
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            listOf(true, false).forEach { enabled ->
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    HedgeButton.Action.Filled(
                        text = "Secondary",
                        buttonColors = HedgeButton.Action.Color.Filled.Secondary,
                        enabled = enabled,
                        onClick = {}
                    )
                    HedgeButton.Action.Filled(
                        text = "Secondary",
                        buttonColors = HedgeButton.Action.Color.Filled.Secondary,
                        size = HedgeButton.Action.Size.Medium,
                        enabled = enabled,
                        onClick = {}
                    )
                    HedgeButton.Action.Filled(
                        text = "Secondary",
                        buttonColors = HedgeButton.Action.Color.Filled.Secondary,
                        size = HedgeButton.Action.Size.Small,
                        enabled = enabled,
                        onClick = {}
                    )
                    HedgeButton.Action.Filled(
                        text = "Secondary",
                        buttonColors = HedgeButton.Action.Color.Filled.Secondary,
                        size = HedgeButton.Action.Size.Tiny,
                        enabled = enabled,
                        onClick = {}
                    )
                }
            }
        }
    }
}

@Composable
@Preview
fun TextButtonPreview() {
    Column(
        modifier = Modifier
            .background(HedgeColor.Neutral.BackgroundDefault)
            .padding(vertical = 48.dp, horizontal = 12.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            listOf(true, false).forEach { enabled ->
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    HedgeButton.Text(
                        text = "Primary",
                        enabled = enabled,
                        onClick = {},
                    )
                    HedgeButton.Text(
                        text = "Secondary",
                        enabled = enabled,
                        color = HedgeButton.Text.Color.Secondary,
                        size = HedgeButton.Text.Size.Medium,
                        onClick = {},
                    )
                    HedgeButton.Text(
                        text = "Primary",
                        enabled = enabled,
                        size = HedgeButton.Text.Size.Small,
                        onClick = {},
                    )
                }
            }

        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            listOf(true, false).forEach { enabled ->
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    HedgeButton.Text(
                        text = "Primary",
                        imageVector = null,
                        enabled = enabled,
                        onClick = {},
                    )
                    HedgeButton.Text(
                        text = "Secondary",
                        imageVector = null,
                        enabled = enabled,
                        color = HedgeButton.Text.Color.Secondary,
                        size = HedgeButton.Text.Size.Medium,
                        onClick = {},
                    )
                    HedgeButton.Text(
                        text = "Primary",
                        imageVector = null,
                        enabled = enabled,
                        size = HedgeButton.Text.Size.Small,
                        onClick = {},
                    )
                }
            }
        }
    }
}

@Composable
@Preview
fun CtaButtonPreview() {
    Column(
        modifier = Modifier
            .background(HedgeColor.GREY_300),
        verticalArrangement = Arrangement.Bottom,
    ) {
        HedgeButton.CallToAction.Single(
            text = "버튼명",
            onClick = {},
        )
        HedgeButton.CallToAction.Double(
            text1 = "버튼1",
            text2 = "버튼2",
            onClickButton1 = {},
            onClickButton2 = {},
        )
        HedgeButton.CallToAction.SingleWithSecondary(
            text = "버튼명",
            secondaryText = "Text",
            background = Background.Gradient(HedgeColor.Neutral.BackgroundDefault),
            onClickButton = {},
            onClickSecondaryButton = {},
        )
        HedgeButton.CallToAction.DoubleWithSecondary(
            text1 = "버튼1",
            text2 = "버튼2",
            secondaryText = "Text",
            background = Background.Gradient(HedgeColor.Neutral.BackgroundDefault),
            onClickButton1 = {},
            onClickButton2 = {},
            onClickSecondaryButton = {},
        )
    }
}