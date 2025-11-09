package com.depromeet.team5.features.login

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

enum class AgreementRowVariant { All, Item }

enum class ConsentType { REQUIRED, OPTIONAL }

enum class ConsentId(
    val titleRes: Int,
    val type: ConsentType,
    val link: String? = null,
) {
    AGE_OVER_14(
        R.string.agreements_agree_age_over_14,
        ConsentType.REQUIRED
    ),
    TERMS(
        R.string.agreements_agree_service,
        ConsentType.REQUIRED,
        "https://www.notion.so/2a0219cc9c34801eba01ea91797dfa0f"
    ),
    PRIVACY(
        R.string.agreements_agree_private_information,
        ConsentType.REQUIRED,
        "https://www.notion.so/2a0219cc9c3480b591ebee5e6cef6d1e"
    ),
    MARKETING(
        R.string.agreements_agree_marketing_information,
        ConsentType.OPTIONAL,
        "https://www.notion.so/2a0219cc9c3480b591ebee5e6cef6d1e"
    )
}

data class ConsentItem(
    val id: ConsentId,
    val checked: Boolean,
)

data class AgreementsUiState(
    val items: List<ConsentItem> = ConsentId.entries.map { ConsentItem(it, false) }
) {
    val allChecked: Boolean = items.all { it.checked }
    val canProceed: Boolean = items
        .filter { it.id.type == ConsentType.REQUIRED }
        .all { it.checked }
}

@HiltViewModel
class LoginViewModel @Inject constructor(
) : ViewModel() {
    private val _uiState = MutableStateFlow(AgreementsUiState())
    val uiState: StateFlow<AgreementsUiState> = _uiState.asStateFlow()

    fun checkAll(checked: Boolean) {
        _uiState.update { state ->
            state.copy(items = state.items.map { it.copy(checked = checked) })
        }
    }

    fun checkItem(id: ConsentId, checked: Boolean) {
        _uiState.update { state ->
            val newItems = state.items.map {
                if (it.id == id) it.copy(checked = checked) else it
            }
            state.copy(items = newItems)
        }
    }
}