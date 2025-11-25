package com.depromeet.team5.features.newprinciples.screen.addprinciples

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject


@HiltViewModel
class AddPrinciplePageViewModel @Inject constructor() : ViewModel() {

    private val _title = MutableStateFlow("")
    val title = _title.asStateFlow()


    fun updateTitle(new: String) {
        _title.update { new }
    }
}