package com.depromeet.team5.core.navigation.request

import androidx.lifecycle.ViewModel
import com.depromeet.team5.core.domain.model.MyPrincipleGroup
import com.depromeet.team5.core.domain.model.OrderType
import com.depromeet.team5.core.navigation.PrincipleModificationType


class PrincipleGraphViewModel : ViewModel() {

    var myPrincipleGroup: MyPrincipleGroup? = null

    var principleId: Int? = null

    var modificationType: PrincipleModificationType? = null

    var orderType: OrderType? = null

}
