package com.depromeet.team5.core.navigation.request

import androidx.lifecycle.ViewModel
import com.depromeet.team5.core.domain.request.CreateRetrospectionRequest
import com.depromeet.team5.core.navigation.request.model.PrincipleGroupState


class RequestViewModel : ViewModel() {

    var request = CreateRetrospectionRequest.EMPTY

    var selectedMyPrincipleGroupState: PrincipleGroupState? = null

}
