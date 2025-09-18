package com.depromeet.team5.core.designsystem.component

import androidx.compose.foundation.Indication
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.depromeet.team5.core.designsystem.component.HedgeTextFieldDefaults.Search.searchColor
import com.depromeet.team5.core.designsystem.component.HedgeTextFieldDefaults.Search.searchIcon
import com.depromeet.team5.core.designsystem.component.HedgeTextFieldDefaults.Search.searchStyle
import com.depromeet.team5.core.designsystem.foundation.HedgeColor
import com.depromeet.team5.core.designsystem.foundation.HedgeIcon
import com.depromeet.team5.core.designsystem.foundation.HedgeTypography

@Stable
interface HedgeSearchFieldColor {
    val container: Color
    val placeholder: Color
    val text: Color
    val border: Color
}

@Stable
interface HedgeSearchFieldStyle {
    val shape: Shape
    val contentPadding: PaddingValues
    val textStyle: TextStyle
    val placeholderStyle: TextStyle
    val borderWidth: Dp
}

@Stable
interface HedgeSearchFieldIcon {
    val icon: ImageVector
    val tint: Color
    val enabled: Boolean
    val indication: Indication?
    val contentDescription: String?
    val interactionSource: MutableInteractionSource
    val onClick: (() -> Unit)?
}

object HedgeTextFieldDefaults {

    object Search {
        @Composable
        fun searchColor(
            container: Color = HedgeColor.Brand.Secondary,
            placeholder: Color = HedgeColor.Text.Assistive,
            text: Color = HedgeColor.Text.Title,
            border: Color = HedgeColor.Transparent,
        ): HedgeSearchFieldColor = object : HedgeSearchFieldColor {
            override val container = container
            override val placeholder = placeholder
            override val text = text
            override val border = border
        }

        @Composable
        fun searchStyle(
            shape: Shape = RoundedCornerShape(14.dp),
            contentPadding: PaddingValues = PaddingValues(start = 2.dp, end = 10.dp, top = 2.dp, bottom = 2.dp),
            textStyle: TextStyle = HedgeTypography.Body1.Medium,
            placeholderStyle: TextStyle = HedgeTypography.Body1.Medium,
            borderWidth: Dp = 0.dp,
        ): HedgeSearchFieldStyle = object : HedgeSearchFieldStyle {
            override val shape = shape
            override val contentPadding = contentPadding
            override val textStyle = textStyle
            override val placeholderStyle = placeholderStyle
            override val borderWidth = borderWidth
        }

        @Composable
        fun searchIcon(
            icon: ImageVector,
            tint: Color = HedgeColor.Text.Alternative,
            enabled: Boolean = true,
            indication: Indication? = null,
            contentDescription: String? = null,
            interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
            onClick: (() -> Unit)? = null,
        ): HedgeSearchFieldIcon = object : HedgeSearchFieldIcon {
            override val icon = icon
            override val tint = tint
            override val enabled = enabled
            override val indication = indication
            override val contentDescription = contentDescription
            override val interactionSource = interactionSource
            override val onClick = onClick
        }
    }
}

object HedgeTextField {

    @Composable
    fun Search(
        value: String,
        onValueChange: (String) -> Unit,
        modifier: Modifier = Modifier,
        placeholder: String? = null,
        interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
        colors: HedgeSearchFieldColor = searchColor(),
        style: HedgeSearchFieldStyle = searchStyle(),
        leadingIcon: HedgeSearchFieldIcon? = searchIcon(
            icon = HedgeIcon.Search,
            enabled = false,
            contentDescription = "search",
        ),
        trailingIcon: HedgeSearchFieldIcon? = searchIcon(
            icon = HedgeIcon.CloseFill,
            enabled = true,
            contentDescription = "clear",
            onClick = { onValueChange("") }
        ),
        visualTransformation: VisualTransformation = VisualTransformation.None,
        keyboardController: SoftwareKeyboardController? = LocalSoftwareKeyboardController.current,
        keyboardOptions: KeyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions: KeyboardActions = KeyboardActions(
            onSearch = {
                leadingIcon?.onClick?.invoke()
                keyboardController?.hide()
            },
            onDone = {
                leadingIcon?.onClick?.invoke()
                keyboardController?.hide()
            }
        ),
    ) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = modifier
                .border(style.borderWidth, colors.border, style.shape)
                .background(
                    color = colors.container,
                    shape = style.shape,
                )
                .padding(style.contentPadding),
            singleLine = true,
            textStyle = style.textStyle.copy(color = colors.text),
            cursorBrush = SolidColor(HedgeColor.Text.Title),
            interactionSource = interactionSource,
            visualTransformation = visualTransformation,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            decorationBox = { inner ->
                Row(
                    modifier = Modifier,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    leadingIcon?.let {
                        Icon(
                            modifier = Modifier
                                .then(
                                    if (leadingIcon.enabled)
                                        Modifier
                                            .clip(CircleShape)
                                            .clickable(
                                                interactionSource = leadingIcon.interactionSource,
                                                onClick = { leadingIcon.onClick?.invoke() }
                                            )
                                    else Modifier
                                )
                                .padding(8.dp),
                            imageVector = it.icon,
                            contentDescription = leadingIcon.contentDescription,
                            tint = leadingIcon.tint,
                        )
                    } ?: Spacer(Modifier.size(40.dp))

                    Box(Modifier.weight(1f), contentAlignment = Alignment.CenterStart) {
                        inner()
                        if (value.isEmpty() && !placeholder.isNullOrEmpty()) {
                            Text(
                                text = placeholder,
                                style = style.placeholderStyle,
                                color = colors.placeholder
                            )
                        }
                    }

                    if (trailingIcon != null && trailingIcon.enabled && value.isNotEmpty()) {
                        Icon(
                            imageVector = trailingIcon.icon,
                            contentDescription = "clear",
                            tint = trailingIcon.tint,
                            modifier = Modifier
                                .then(
                                    if (trailingIcon.enabled)
                                        Modifier
                                            .clip(CircleShape)
                                            .clickable(
                                                indication = trailingIcon.indication,
                                                interactionSource = trailingIcon.interactionSource,
                                                onClick = { trailingIcon.onClick?.invoke() }
                                            )
                                    else
                                        Modifier
                                )
                                .padding(8.dp)
                        )
                    } else {
                        Spacer(Modifier.size(40.dp))
                    }
                }
            }
        )
    }
}

@Composable
@Preview
fun SearchPreview() {
    var text1 by remember { mutableStateOf("") }
    var text2 by remember { mutableStateOf("trailing icon null") }
    var text3 by remember { mutableStateOf("leading icon disabled, trailing icon enabled") }
    var text4 by remember { mutableStateOf("leading icon enabled") }

    Column(Modifier.padding(horizontal = 20.dp, vertical = 100.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        HedgeTextField.Search(
            value = text1,
            onValueChange = { text1 = it },
            placeholder = "종목 검색",
        )
        HedgeTextField.Search(
            value = text2,
            onValueChange = { text2 = it },
            trailingIcon = null,
            placeholder = "종목 검색",
        )
        HedgeTextField.Search(
            value = text3,
            onValueChange = { text3 = it },
            placeholder = "종목 검색",
        )

        val keyboardController = LocalSoftwareKeyboardController.current
        HedgeTextField.Search(
            value = text4,
            onValueChange = { text4 = it },
            leadingIcon = searchIcon(
                icon = HedgeIcon.Search,
                enabled = true,
                onClick = {
                    keyboardController?.hide()
                    // do something
                }
            ),
            trailingIcon = null,
            placeholder = "종목 검색",
        )
    }
}