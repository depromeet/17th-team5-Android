package com.depromeet.team5.core.navigation.request

import androidx.lifecycle.ViewModel
import com.depromeet.team5.core.domain.model.MyPrincipleGroup


class RequestViewModel : ViewModel() {

    var request = CreateRetrospectionParams.EMPTY

    var selectedMyPrincipleGroup: MyPrincipleGroup? = null

}
