package com.depromeet.team5.features.home

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.depromeet.team5.core.designsystem.foundation.HedgeColor.GREY_400
import com.depromeet.team5.core.domain.usecase.UserStatsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class HomeTab(
    @StringRes val title: Int,
    val selectedColor: Color = Color.Black,
    val unselectedColor: Color = GREY_400
) {
    HOME(
        title = R.string.home_tab_title_home
    ),
    PRINCIPLE(
        title = R.string.home_tab_title_principle
    )
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userStatsUseCase: UserStatsUseCase
) : ViewModel() {
    private val _homeUiStateFlow: MutableStateFlow<HomeUiState> =
        MutableStateFlow(HomeUiState.Loading)
    val homeUiStateFlow: StateFlow<HomeUiState> = _homeUiStateFlow

    init {
        userStats()
    }

    fun userStats(){
        viewModelScope.launch {
            userStatsUseCase()
                .map{
                    HomeUiState.Success(
                        percentage = it.data.percentage,
                        hedge = it.data.hedge,
                        bronze = it.data.bronze,
                        silver = it.data.silver,
                        gold = it.data.gold
                    )
                }
                .onStart { _homeUiStateFlow.value = HomeUiState.Loading }
                .catch { t -> _homeUiStateFlow.value = HomeUiState.Failure(t) }
                .collect { success -> _homeUiStateFlow.value = success }
        }
    }
}