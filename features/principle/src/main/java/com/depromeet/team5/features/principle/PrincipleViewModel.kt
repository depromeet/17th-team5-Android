package com.depromeet.team5.features.principle

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RetrospectViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

}