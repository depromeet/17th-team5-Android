package com.depromeet.team5.features.feedback.screen

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Stable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.depromeet.team5.core.domain.model.Feedback
import com.depromeet.team5.core.domain.usecase.CreateFeedbackUseCase
import com.depromeet.team5.core.domain.usecase.CreateRetrospectionUseCase
import com.depromeet.team5.core.navigation.request.CreateRetrospectionParams
import com.depromeet.team5.features.feedback.AiFeedbackUiState
import com.depromeet.team5.features.feedback.PrincipleState
import com.depromeet.team5.features.feedback.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@Stable
enum class Grade(
    @DrawableRes val iconRes: Int,
    @StringRes val descriptionRes: Int
) {
    BRONZE(
        iconRes = R.drawable.img_badge_bronze,
        descriptionRes = R.string.feedback_grade_description_bronze
    ),
    SILVER(
        iconRes = R.drawable.img_badge_silver,
        descriptionRes = R.string.feedback_grade_description_silver
    ),
    GOLD(
        iconRes = R.drawable.img_badge_gold,
        descriptionRes = R.string.feedback_grade_description_gold
    ),
    PLATINUM(
        iconRes = R.drawable.img_badge_platinum,
        descriptionRes = R.string.feedback_grade_description_platinum
    );

    companion object {
        fun fromBadge(badge: String): Grade = when (badge) {
            "아쉬운 매도" -> BRONZE
            "아쉬운 매수" -> BRONZE
            "실버급 매도" -> SILVER
            "실버급 매수" -> SILVER
            "골드급 매도" -> GOLD
            "골드급 매수" -> GOLD
            "플레급 매도" -> PLATINUM
            "플레급 매수" -> PLATINUM
            else -> BRONZE
        }
    }
}

@HiltViewModel
class AiFeedbackViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val createRetrospectionUseCase: CreateRetrospectionUseCase,
    private val createFeedbackUseCase: CreateFeedbackUseCase
) : ViewModel() {

    private val _feedbackStateFlow: MutableStateFlow<AiFeedbackUiState> =
        MutableStateFlow(AiFeedbackUiState.Loading)
    val feedbackStateFlow: StateFlow<AiFeedbackUiState> = _feedbackStateFlow


    fun createRetrospection(request: CreateRetrospectionParams) {
        viewModelScope.launch {
            createRetrospectionUseCase(
                body = mapOf(
                    "symbol" to request.symbol,
                    "market" to request.market,
                    "orderType" to request.orderType.name,
                    "price" to request.price,
                    "currency" to request.currency,
                    "volume" to request.volume,
                    "orderDate" to formatDate(request.orderDate),
                    "returnRate" to request.returnRate,
                    "content" to request.content,
                    "principleChecks" to request.principleChecks,
                    "emotion" to request.emotion?.name
                )
            )
                .flatMapConcat { createFeedbackUseCase(it.id) }
                .map {
                    if (it.data != null) {
                        AiFeedbackUiState.Success(
                            badge = it.data!!.badge,
                            principleCheckSummary = PrincipleState(
                                keptCount = it.data!!.principleCheckSummary.keptCount,
                                neutralCount = it.data!!.principleCheckSummary.neutralCount,
                                notKeptCount = it.data!!.principleCheckSummary.notKeptCount
                            ),
                            keep = it.data!!.keep,
                            fix = it.data!!.fix,
                            next = it.data!!.next
                        )
                    } else {
                        AiFeedbackUiState.Error(
                            code = it.code,
                            message = it.message
                        )
                    }
                }
                .catch {
                    it.printStackTrace()
                    emit(AiFeedbackUiState.Failure(it))
                }
                .onEach { _feedbackStateFlow.value = it }
                .collect()
        }
    }


    fun updatePrinciple(title: String) {
        when (_feedbackStateFlow.value) {
            is AiFeedbackUiState.Success -> {
//                val index =
//                    (_feedbackStateFlow.value as AiFeedbackUiState.Success).principles.indexOfFirst { it.title == title }
//
//                if (index == -1) return
//
//                val principles = (_feedbackStateFlow.value as AiFeedbackUiState.Success).principles
//                val target = principles[index]
//
//                val newPrinciple = target.copy(isAdd = !target.isAdd)
//
//                val newPrinciples = principles.toMutableList().apply {
//                    set(index, newPrinciple)
//                }
//
//                _feedbackStateFlow.update {
//                    (_feedbackStateFlow.value as AiFeedbackUiState.Success).copy(
//                        principles = newPrinciples
//                    )
//                }

            }

            else -> {}
        }
    }

    private fun formatDate(date: String): String {
        val regex = """(\d{4})년\s*(\d{1,2})월\s*(\d{1,2})일""".toRegex()

        val matchResult = regex.find(date)

        if (matchResult != null) {
            val (year, month, day) = matchResult.destructured

            val formattedMonth = month.padStart(2, '0')
            val formattedDay = day.padStart(2, '0')

            return "$year-$formattedMonth-$formattedDay"
        }

        error("잘못된 Date Format이 들어왔습니다. Params : { $date }")
    }
}
