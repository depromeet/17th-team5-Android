package com.depromeet.team5.core.navigation.request

import androidx.lifecycle.ViewModel
import com.depromeet.team5.core.domain.model.MyPrincipleGroup
import com.depromeet.team5.core.domain.request.CreateRetrospectionRequest


class RequestViewModel : ViewModel() {

    var request = CreateRetrospectionRequest.EMPTY

    var selectedMyPrincipleGroup: MyPrincipleGroup? = null

}
