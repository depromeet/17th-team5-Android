package com.depromeet.team5.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.depromeet.team5.core.domain.monad.BaseEvent
import com.depromeet.team5.core.domain.usecase.GetAccessTokenUseCase
import com.depromeet.team5.core.ui.extensions.baseCollect
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getAccessTokenUseCase: GetAccessTokenUseCase
) : ViewModel() {

    val eventBus = Channel<BaseEvent<String?>>()


    init {
        viewModelScope.launch {
            getAccessTokenUseCase()
                .baseCollect(
                    onSuccess = {
                        eventBus.send(BaseEvent.Finish(it))
                    },
                    onError = {
                        eventBus.send(BaseEvent.Error(it))
                    }
                )
        }
    }
}

