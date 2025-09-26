package com.depromeet.team5.core.retrofit.apisourceimpl

import com.depromeet.team5.core.remotedatasource.apisource.HedgeApiSource
import com.depromeet.team5.core.remotedatasource.model.SearchRemoteData
import com.depromeet.team5.core.retrofit.api.HedgeApi
import com.depromeet.team5.core.retrofit.toRequestBody
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
internal class HedgeApiSourceImpl @Inject constructor(
    private val hedgeApi: HedgeApi
) : HedgeApiSource {

    override suspend fun search(query: String): SearchRemoteData =
        hedgeApi.search(query = query).toRemoteData()

    override suspend fun createRetrospection(
        body: Map<String, Any?>
    ) = hedgeApi.createRetrospection(body.toRequestBody()).toRemoteData()

    override suspend fun createFeedback(retrospectionId: Int): String = hedgeApi
        .createFeedback(retrospectionId = retrospectionId)
        .toString()
}
