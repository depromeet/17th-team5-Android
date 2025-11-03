//package com.depromeet.team5.features.principle
//
//import androidx.lifecycle.SavedStateHandle
//import androidx.lifecycle.ViewModel
//import dagger.hilt.android.lifecycle.HiltViewModel
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.StateFlow
//import kotlinx.coroutines.flow.asStateFlow
//import kotlinx.coroutines.flow.update
//import javax.inject.Inject
//
//@HiltViewModel
//class PrincipleViewModel @Inject constructor(
//    private val savedStateHandle: SavedStateHandle
//) : ViewModel() {
//
//    private val initialPrinciples = listOf(
//        Principle(1, "안전마진을 확보하라"),
//        Principle(2, "분산하되 너무 넓지 않게"),
//        Principle(3, "정책 민감도가 높은 주식은\n 정책 잘 살펴보고 매매"),
//        Principle(4, "정보 완전성 기준 세우기"),
//        Principle(5, "기업의 본질 가치보다 낮게 거래되는\n주식을 찾아 장기 보유하기"),
//        Principle(6, "유행주를 추격하지 않는다."),
//        Principle(7, "주가가 오르는 흐름이면 매수,\n하락흐름이면 매도하기"),
//        Principle(8, "기업의 본질 가치보다 낮게 거래되는\n주식을 찾아 장기 보유하기"),
//        Principle(9, "단기 등락에 흔들리지 말고 기업의 장기\n성장성에 집중하기"),
//    )
//
//    private val _principles = MutableStateFlow(initialPrinciples )
//    val principles: StateFlow<List<Principle>> = _principles.asStateFlow()
//
//    fun toggle(id: Long) {
//        _principles.update { list ->
//            list.map { principle -> if (principle.id == id) principle.copy(checked = !principle.checked) else principle }
//        }
//    }
//}