package com.depromeet.team5.features.principlegroupmodification.screen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.depromeet.team5.core.domain.model.MyPrincipleGroup
import com.depromeet.team5.core.domain.monad.BaseEvent
import com.depromeet.team5.core.domain.usecase.CreatePrincipleGroupUseCase
import com.depromeet.team5.core.domain.usecase.GetPrincipleGroupUseCase
import com.depromeet.team5.core.domain.usecase.ModifyPrincipleGroupUseCase
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
    private val getPrincipleGroupUseCase: GetPrincipleGroupUseCase,
    private val modifyPrincipleGroupUseCase: ModifyPrincipleGroupUseCase,
    private val createPrincipleGroupUseCase: CreatePrincipleGroupUseCase
) : ViewModel() {

    private val model = savedStateHandle.toRoute<PrincipleGroupModification>()

    var originalGroupName = MyPrincipleGroup.EMPTY
        private set

    val groupNameState by hedgeState("")
    val thumbnailState by hedgeState("")

    private val _eventFlow = MutableSharedFlow<BaseEvent>()
    val eventFlow = _eventFlow.asSharedFlow()


    init {
        viewModelScope.launch {
            flow {
                emit(model.groupId)
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
                        _eventFlow.emit(BaseEvent.Error(it))
                    }
                )
        }
    }

    fun upsertPrincipleGroup() {
        viewModelScope.launch {
            if (model.groupId == null) {
                createPrincipleGroupUseCase(
                    MyPrincipleGroup.EMPTY.copy(
                        groupName = groupNameState.value,
                        thumbnail = thumbnailState.value,
                        orderType = model.orderType
                    )
                )
                    .baseCollect(
                        onSuccess = {
                            _eventFlow.emit(BaseEvent.Finish)
                        },
                        onError = {
                            _eventFlow.emit(BaseEvent.ShowToast(it.message))
                        }
                    )
            } else {
                modifyPrincipleGroupUseCase(
                    groupId = model.groupId,
                    groupName = groupNameState.value,
                    thumbnail = thumbnailState.value
                )
                    .baseCollect(
                        onSuccess = {
                            _eventFlow.emit(BaseEvent.Finish)
                        },
                        onError = {
                            _eventFlow.emit(BaseEvent.ShowToast(it.message))
                        }
                    )
            }
        }
    }

    fun updateGroupName(newGroupName: String) {
        groupNameState.update { newGroupName }
    }

    fun updateThumbnail(thumbnail: String) {
        thumbnailState.update { thumbnail }
    }

    fun isNewPrincipleGroup() = model.groupId == null

    fun distinct(groupName: String, thumbnail: String) =
        originalGroupName.groupName != groupName || originalGroupName.thumbnail != thumbnail
}
