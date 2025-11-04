package com.depromeet.team5.core.retrofit.apisourceimpl

import com.depromeet.team5.core.remotedatasource.apisource.HedgeApiSource
import com.depromeet.team5.core.remotedatasource.model.AnalysisRemoteData
import com.depromeet.team5.core.remotedatasource.model.FeedbackRemoteData
import com.depromeet.team5.core.remotedatasource.model.MyPrincipleGroupsRemoteData
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

    override suspend fun createFeedback(retrospectionId: Int): FeedbackRemoteData = hedgeApi
        .createFeedback(retrospectionId = retrospectionId)
        .toRemoteData()

    override suspend fun createAnalysis(body: Map<String, Any?>): AnalysisRemoteData =
        hedgeApi
            .createAnalysis(body.mapValues { it.value?.toString() ?: "" })
            .toRemoteData()

    override suspend fun getPrincipleGroups(orderType: String): MyPrincipleGroupsRemoteData =
        hedgeApi.getPrincipleGroups(orderType = orderType).toRemoteData()
}
