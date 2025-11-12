package com.depromeet.team5.features.principlegroupmodification.screen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.depromeet.team5.core.domain.model.MyPrincipleGroup
import com.depromeet.team5.core.domain.usecase.GetPrincipleGroupUseCase
import com.depromeet.team5.core.ui.extensions.baseCollect
import com.depromeet.team5.core.ui.lazy.hedgeState
import com.depromeet.team5.features.principlegroupmodification.navigation.PrincipleGroupModification
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PrincipleGroupModificationViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getPrincipleGroupUseCase: GetPrincipleGroupUseCase
) : ViewModel() {

    private val groupId = savedStateHandle.toRoute<PrincipleGroupModification>().groupId

    var originalGroupName = MyPrincipleGroup.EMPTY
        private set

    val groupNameState by hedgeState("")
    val thumbnailState by hedgeState("")

    private val _errorFlow = MutableSharedFlow<Throwable>()
    val eventFlow = _errorFlow.asSharedFlow()


    init {
        viewModelScope.launch {
            flow {
                emit(groupId)
            }
                .filterNotNull()
                .flatMapLatest {
                    getPrincipleGroupUseCase(it)
                }
                .baseCollect(
                    onSuccess = { myPrincipleGroup ->
                        originalGroupName = myPrincipleGroup.copy()
                        groupNameState.update { myPrincipleGroup.groupName }
                        thumbnailState.update { myPrincipleGroup.thumbnail }
                    },
                    onError = {
                        _errorFlow.emit(it)
                    }
                )
        }
    }

    fun updateGroupName(newGroupName: String) {
        groupNameState.update { newGroupName }
    }

    fun updateThumbnail(thumbnail: String) {
        thumbnailState.update { thumbnail }
    }

    fun distinct(groupName: String, thumbnail: String) =
        originalGroupName.groupName != groupName || originalGroupName.thumbnail != thumbnail
}
