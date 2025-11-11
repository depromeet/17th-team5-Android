package com.depromeet.team5.core.retrofit.apisourceimpl

import com.depromeet.team5.core.remotedatasource.apisource.HedgeApiSource
import com.depromeet.team5.core.remotedatasource.model.FeedbackRemoteData
import com.depromeet.team5.core.remotedatasource.model.MyPrincipleGroupRemoteData
import com.depromeet.team5.core.remotedatasource.model.MyPrincipleGroupsInfoRemoteData
import com.depromeet.team5.core.remotedatasource.model.RetrospectionListRemoteData
import com.depromeet.team5.core.remotedatasource.model.SearchRemoteData
import com.depromeet.team5.core.remotedatasource.model.SystemPrincipleRemoteData
import com.depromeet.team5.core.remotedatasource.model.UserStatsRemoteData
import com.depromeet.team5.core.retrofit.api.HedgeApi
import com.depromeet.team5.core.retrofit.toRequestBody
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
internal class HedgeApiSourceImpl @Inject constructor(
    private val hedgeApi: HedgeApi,
) : HedgeApiSource {

    override suspend fun search(query: String): SearchRemoteData =
        hedgeApi.search(query = query).toRemoteData()

    override suspend fun createFeedback(retrospectionId: Int): FeedbackRemoteData = hedgeApi
        .createFeedback(retrospectionId = retrospectionId)
        .toRemoteData()

    override suspend fun getPrincipleGroups(orderType: String): MyPrincipleGroupsInfoRemoteData =
        hedgeApi.getPrincipleGroups(orderType = orderType).toRemoteData()

    override suspend fun getPrincipleGroup(groupId: Int): MyPrincipleGroupRemoteData =
        hedgeApi.getPrincipleGroup(groupId).toRemoteData()

    override suspend fun deletePrincipleGroup(groupId: Int) =
        hedgeApi.deletePrincipleGroup(groupId)

    override suspend fun deletePrinciple(principleId: Int) =
        hedgeApi.deletePrinciple(principleId)

    override suspend fun createPrincipleGroup(body: Map<String, Any?>): MyPrincipleGroupRemoteData =
        hedgeApi.createPrincipleGroup(body.toRequestBody()).toRemoteData()

    override suspend fun userStats(): UserStatsRemoteData =
        hedgeApi
            .userStats()
            .toRemoteData()

    override suspend fun retrospectionList(): RetrospectionListRemoteData =
        hedgeApi
            .retrospectionList()
            .toRemoteData()

    override suspend fun systemPrincipleList(): SystemPrincipleRemoteData =
        hedgeApi
            .systemPrincipleList()
            .toRemoteData()

}
