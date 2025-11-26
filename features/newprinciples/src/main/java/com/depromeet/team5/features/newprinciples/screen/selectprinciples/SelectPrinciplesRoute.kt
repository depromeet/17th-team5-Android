package com.depromeet.team5.features.newprinciples.screen.selectprinciples

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.depromeet.team5.core.designsystem.component.HedgeButton
import com.depromeet.team5.core.designsystem.component.HedgeTopBar
import com.depromeet.team5.core.designsystem.foundation.HedgeColor
import com.depromeet.team5.core.designsystem.foundation.HedgeIcon
import com.depromeet.team5.core.designsystem.foundation.HedgeTypography
import com.depromeet.team5.core.domain.model.MyPrincipleGroup
import com.depromeet.team5.core.domain.monad.HedgeUiState
import com.depromeet.team5.core.ui.component.HedgeLoadingScreen
import com.depromeet.team5.features.newprinciples.R
import kotlin.math.abs


@Composable
fun SelectPrinciplesRoute(
    modifier: Modifier = Modifier,
    onBackPressed: () -> Unit,
    onShowErrorToast: (Throwable) -> Unit,
    onClickedConfirmButton: (List<String>) -> Unit,
    viewModel: SelectPrinciplesViewModel = hiltViewModel()
) {
    val myPrincipleGroupUiState by viewModel.myPrincipleGroupUiState.collectAsStateWithLifecycle()
    val newPrinciples by viewModel.newPrinciples.stateFlow.collectAsStateWithLifecycle()

    SelectPrinciplesScreen(
        uiState = myPrincipleGroupUiState,
        newPrinciples = newPrinciples,
        modifier = modifier,
        onClickedConfirmButton = onClickedConfirmButton,
        onShowErrorToast = onShowErrorToast,
        onBackPressed = onBackPressed
    )
}

@Composable
private fun SelectPrinciplesScreen(
    uiState: HedgeUiState<MyPrincipleGroup>,
    newPrinciples: List<String>,
    modifier: Modifier = Modifier,
    onClickedConfirmButton: (List<String>) -> Unit,
    onShowErrorToast: (Throwable) -> Unit,
    onBackPressed: () -> Unit
) {
    when (uiState) {
        is HedgeUiState.Success<MyPrincipleGroup> -> {
            SelectPrinciplesContent(
                myPrincipleGroup = uiState.data,
                newPrinciples = newPrinciples,
                modifier = modifier,
                onClickedConfirmButton = onClickedConfirmButton,
                onBackPressed = onBackPressed
            )
        }
        is HedgeUiState.Loading<*> -> {
            HedgeLoadingScreen()
        }
        is HedgeUiState.Error -> {
            uiState.throwable?.let {
                onShowErrorToast(it)
            }
        }
    }
}

@Composable
private fun SelectPrinciplesContent(
    myPrincipleGroup: MyPrincipleGroup,
    newPrinciples: List<String>,
    modifier: Modifier = Modifier,
    onClickedConfirmButton: (List<String>) -> Unit,
    onBackPressed: () -> Unit
) {
    var selectedPrincipleSet by remember { mutableStateOf(emptySet<String>()) }
    val isButtonEnabled = selectedPrincipleSet.isNotEmpty()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(HedgeColor.WHITE)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            HedgeTopBar(
                title = {
                    Text(
                        text = myPrincipleGroup.groupName,
                        style = HedgeTypography.Body3.SemiBold,
                        color = HedgeColor.Text.Primary
                    )
                },
                onClickBack = onBackPressed
            )

            Text(
                modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 10.dp),
                text = stringResource(R.string.new_principle_title),
                style = HedgeTypography.Headline1.SemiBold,
                color = HedgeColor.Text.Title
            )

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
            )

            CheckPrincipleList(
                principles = newPrinciples,
                onCheckedChange = { principle ->
                    if (selectedPrincipleSet.contains(principle)) {
                        selectedPrincipleSet = selectedPrincipleSet - principle
                    } else {
                        selectedPrincipleSet = selectedPrincipleSet + principle
                    }
                }
            )

            Spacer(modifier = Modifier.weight(1f))

            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = stringResource(
                    R.string.new_principle_max_select_count,
                    abs(myPrincipleGroup.principles.size - newPrinciples.size)
                ),
                style = HedgeTypography.Body3.Medium,
                color = HedgeColor.Text.Assistive
            )

            HedgeButton.Action.Filled(
                modifier = Modifier
                    .padding(start = 20.dp, top = 12.dp, end = 20.dp, bottom = 31.dp)
                    .fillMaxWidth(),
                text = stringResource(R.string.new_principle_button_text),
                enabled = isButtonEnabled,
                forceClickable = isButtonEnabled,
                onClick = {
                    onClickedConfirmButton(selectedPrincipleSet.toList())
                }
            )
        }
    }
}

@Composable
private fun CheckPrincipleList(
    principles: List<String>,
    modifier: Modifier = Modifier,
    onCheckedChange: (String) -> Unit
) {
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
    ) {
        itemsIndexed(
            items = principles,
            key = { index, principle -> principle }
        ) { index, principle ->
            CheckPrinciple(
                principle = principle,
                onCheckedChange = {
                    onCheckedChange(principle)
                }
            )

            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                thickness = 1.dp,
                color = HedgeColor.Neutral.BackgroundSecondary
            )
        }
    }
}


@Composable
private fun CheckPrinciple(
    principle: String,
    modifier: Modifier = Modifier,
    onCheckedChange: () -> Unit
) {
    var isChecked by remember { mutableStateOf(false) }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(HedgeColor.WHITE)
            .padding(horizontal = 20.dp, vertical = 22.dp)
            .clickable(
                enabled = true,
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                isChecked = !isChecked
                onCheckedChange()
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(24.dp)
                .background(
                    if (isChecked) HedgeColor.Brand.Primary
                    else HedgeColor.Neutral.BackgroundSecondary,
                    shape = RoundedCornerShape(8.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Image(
                imageVector = HedgeIcon.Check,
                contentDescription = null,
                colorFilter = ColorFilter.tint(HedgeColor.WHITE)
            )
        }

        Text(
            modifier = Modifier.padding(start = 14.dp),
            text = principle,
            style = HedgeTypography.Body3.Medium,
            color = HedgeColor.Text.Title
        )
    }
}

@Preview
@Composable
private fun SelectPrinciplesScreenPreview() {
    SelectPrinciplesScreen(
        uiState = HedgeUiState.Success(MyPrincipleGroup.EMPTY),
        newPrinciples = listOf(
            "다음엔 거래량 감소 구간을 명시적으로 기록해보세요.",
            "다음엔 거래량 감소 구간을 명시적으로 기록해보세요2."
        ),
        onClickedConfirmButton = {},
        onShowErrorToast = {},
        onBackPressed = {}
    )
}

@Preview
@Composable
private fun CheckPrinciplePreview() {
    CheckPrinciple(
        principle = "다음엔 거래량 감소 구간을 명시적으로 기록해보세요.",
        onCheckedChange = {}
    )
}
