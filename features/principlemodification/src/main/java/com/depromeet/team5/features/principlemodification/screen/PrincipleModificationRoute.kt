package com.depromeet.team5.features.principlemodification.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.depromeet.team5.core.designsystem.component.HedgeButton
import com.depromeet.team5.core.designsystem.foundation.HedgeColor
import com.depromeet.team5.core.designsystem.foundation.HedgeIcon
import com.depromeet.team5.core.designsystem.foundation.HedgeTypography
import com.depromeet.team5.core.domain.monad.HedgeUiState
import com.depromeet.team5.features.principlemodification.R
import com.depromeet.team5.features.principlemodification.navigation.PrincipleModification


@Composable
fun PrincipleModificationRoute(
    modifier: Modifier = Modifier,
    viewModel: PrincipleModificationViewModel = hiltViewModel(),
    onBackClicked: () -> Unit = {},
    onShowErrorToast: (Throwable) -> Unit,
    onShowToast: (String) -> Unit
) {
    val context = LocalContext.current
    val uiState by viewModel.uiStateFlow.collectAsStateWithLifecycle()

    PrincipleModificationScreen(
        uiState = uiState,
        modifier = modifier,
        onBackClicked = onBackClicked,
        onShowErrorToast = onShowErrorToast,
        onShowToast = {
            onShowToast(context.getString(R.string.principle_modification_toast_message))
        }
    )
}

@Composable
private fun PrincipleModificationScreen(
    uiState: HedgeUiState<PrincipleModification>,
    modifier: Modifier = Modifier,
    onBackClicked: () -> Unit = {},
    onShowErrorToast: (Throwable) -> Unit,
    onShowToast: () -> Unit
) {
    when (uiState) {
        is HedgeUiState.Success<PrincipleModification> -> {
            PrincipleModificationContent(
                modifier = modifier.imePadding(),
                principleModification = uiState.data,
                onClickedConfirmButton = {
                    onShowToast()
                    onBackClicked()
                },
                onBackClicked = onBackClicked
            )
        }

        is HedgeUiState.Error -> {
            onBackClicked()

            uiState.throwable?.let {
                onShowErrorToast(it)
            }
        }

        else -> {}
    }
}

@Composable
private fun PrincipleModificationContent(
    principleModification: PrincipleModification,
    modifier: Modifier = Modifier,
    onClickedConfirmButton: () -> Unit,
    onBackClicked: () -> Unit
) {
    var principle by remember {
        mutableStateOf(
            TextFieldState(
                initialText = principleModification.principle
            )
        )
    }
    var content by remember {
        mutableStateOf(
            TextFieldState(
                initialText = principleModification.description
            )
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(HedgeColor.WHITE)
    ) {
        Topbar(
            title = principleModification.groupName,
            onClickedButton = onClickedConfirmButton,
            onBackClicked = onBackClicked
        )

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(12.dp)
        )

        BasicTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 10.dp),
            state = principle,
            textStyle = HedgeTypography.Headline1.SemiBold.copy(
                color = HedgeColor.Text.Title
            ),
            cursorBrush = SolidColor(HedgeColor.Brand.Darken)
        )

        HorizontalDivider(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .fillMaxWidth(),
            color = HedgeColor.Neutral.BackgroundSecondary
        )

        BasicTextField(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 24.dp),
            state = content,
            textStyle = HedgeTypography.Body3.Regular.copy(
                color = HedgeColor.Text.Title
            ),
            cursorBrush = SolidColor(HedgeColor.Brand.Darken)
        )
    }
}

@Composable
private fun Topbar(
    title: String,
    modifier: Modifier = Modifier,
    onClickedButton: () -> Unit,
    onBackClicked: () -> Unit = {},
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(HedgeColor.WHITE)
            .padding(start = 4.dp, top = 2.dp, end = 16.dp, bottom = 2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier
                .padding(8.dp)
                .size(24.dp)
                .clickable(
                    enabled = true,
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                    onBackClicked()
                },
            imageVector = HedgeIcon.ArrowLeftThick,
            contentDescription = null,
            colorFilter = ColorFilter.tint(HedgeColor.Text.Primary)
        )

        Spacer(modifier.weight(1f))

        Text(
            text = title,
            style = HedgeTypography.Body3.SemiBold,
            color = HedgeColor.Text.Primary
        )

        Spacer(modifier.weight(1f))

        HedgeButton.Text(
            text = stringResource(R.string.confirm),
            imageVector = null,
            size = HedgeButton.Text.Size.Large,
            onClick = onClickedButton
        )
    }
}

@Preview
@Composable
private fun PrincipleModificationScreenPreview(
    modifier: Modifier = Modifier
) {
    PrincipleModificationScreen(
        uiState = HedgeUiState.Success(
            PrincipleModification(
                principleId = 1,
                groupName = "이건 좀 지키자 제발 이건 좀 지키자",
                principle = "종목 선택 시 최근 매출액 확인!!!!",
                description = ""
            )
        ),
        modifier = modifier,
        onShowErrorToast = {},
        onShowToast = {}
    )
}

@Preview
@Composable
private fun TopbarPreview(
    modifier: Modifier = Modifier
) {
    Topbar(
        title = "이건 좀 지키자 제발 이건 좀 지키자",
        onClickedButton = {}
    )
}