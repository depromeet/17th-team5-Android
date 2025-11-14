package com.depromeet.team5.features.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.depromeet.team5.core.domain.model.SocialLogin
import com.depromeet.team5.core.domain.monad.HedgeUiState
import com.depromeet.team5.core.domain.usecase.SocialLoginUseCase
import com.depromeet.team5.core.ui.model.AgreementsType
import com.depromeet.team5.core.ui.model.ConsentType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class AgreementRowVariant { All, Item }

data class ConsentItem(
    val id: AgreementsType,
    val checked: Boolean,
)

data class AgreementsUiState(
    val items: List<ConsentItem> = AgreementsType.entries.map { ConsentItem(it, false) },
) {
    val allChecked: Boolean = items.all { it.checked }
    val canProceed: Boolean = items
        .filter { it.id.type == ConsentType.REQUIRED }
        .all { it.checked }
}

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val socialLoginUseCase: SocialLoginUseCase,
) : ViewModel() {
    private val _agreements = MutableStateFlow(AgreementsUiState())
    val agreements: StateFlow<AgreementsUiState> = _agreements.asStateFlow()

    private val _socialLoginUiState =
        MutableStateFlow<HedgeUiState<SocialLogin>>(HedgeUiState.Loading(null))
    val socialLoginUiState: StateFlow<HedgeUiState<SocialLogin>> = _socialLoginUiState.asStateFlow()

    private val _eventFlow = MutableSharedFlow<Unit>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun checkAll(checked: Boolean) {
        _agreements.update { state ->
            state.copy(items = state.items.map { it.copy(checked = checked) })
        }
    }

    fun checkItem(id: AgreementsType, checked: Boolean) {
        _agreements.update { state ->
            val newItems = state.items.map {
                if (it.id == id) it.copy(checked = checked) else it
            }
            state.copy(items = newItems)
        }
    }

    fun loginWithKakao() {
        viewModelScope.launch {
            _eventFlow.emit(Unit)
        }
    }

    fun authorize(result: Result<Pair<String, String>>) {
        _socialLoginUiState.value = HedgeUiState.Loading(null)

        result.fold(
            onSuccess = { (authCode, redirectUri) ->
                viewModelScope.launch {
                    socialLoginUseCase(
                        provider = "KAKAO",
                        authCode = authCode,
                        redirectUri = redirectUri,
                        email = null,
                        nickname = null
                    ).catch { e ->
                        _socialLoginUiState.value = HedgeUiState.Error(message = e.message, throwable = e)
                    }
                        .collect { login ->
                            when(login){
                                is SocialLogin.Success -> {
                                    _socialLoginUiState.value = HedgeUiState.Success(login)
                                }

                                is SocialLogin.Failure -> {
                                    val msg = buildString {
                                        append(login.message)
                                        login.data?.errorCode?.let {
                                            append("(").append(it).append(")")
                                        }
                                    }
                                    _socialLoginUiState.value = HedgeUiState.Error(code = login.code, message = msg)
                                }
                            }
                        }

                }
            },
            onFailure = {
                _socialLoginUiState.value = HedgeUiState.Error(message = it.message, throwable = it)
            }
        )
    }

    fun consumeLoginResult() {
        _socialLoginUiState.value = HedgeUiState.Loading(null)
    }
}
