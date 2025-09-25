package com.depromeet.team5.core.retrofit.apisourceimpl

import com.depromeet.team5.core.remotedatasource.apisource.HedgeApiSource
import com.depromeet.team5.core.retrofit.api.HedgeApi


internal class HedgeApiSourceImpl(
    private val hedgeApi: HedgeApi
) : HedgeApiSource {

    override fun createRetrospection(
        body: Map<String, Any>
    ) = hedgeApi.createRetrospection(body)
}
