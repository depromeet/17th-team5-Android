package com.depromeet.team5.features.principle

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class RetrospectViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val initialPrinciples = listOf(
        Principle(1, "안전마진을 확보하라1"),
        Principle(2, "안전마진을 확보하라2"),
        Principle(3, "안전마진을 확보하라3"),
        Principle(4, "안전마진을 확보하라4"),
        Principle(5, "안전마진을 확보하라5"),
        Principle(6, "안전마진을 확보하라6"),
        Principle(7, "안전마진을 확보하라7"),
        Principle(8, "안전마진을 확보하라8"),
        Principle(9, "안전마진을 확보하라9"),
        Principle(10, "안전마진을 확보하라10")
    )

    private val _principles = MutableStateFlow(initialPrinciples)
    val principles: StateFlow<List<Principle>> = _principles.asStateFlow()

    fun toggle(id: Long) {
        _principles.update { list ->
            list.map { principle -> if (principle.id == id) principle.copy(checked = !principle.checked) else principle }
        }
    }
}