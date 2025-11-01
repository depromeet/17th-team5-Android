package com.depromeet.team5.features.home

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.depromeet.team5.core.designsystem.foundation.HedgeColor.GREY_400
import dagger.hilt.android.lifecycle.HiltViewModel
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

) : ViewModel() {

}