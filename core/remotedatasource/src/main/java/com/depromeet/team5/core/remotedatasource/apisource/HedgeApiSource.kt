package com.depromeet.team5.core.remotedatasource.apisource

import com.depromeet.team5.core.remotedatasource.model.FeedbackRemoteData
import com.depromeet.team5.core.remotedatasource.model.MyPrincipleGroupRemoteData
import com.depromeet.team5.core.remotedatasource.model.MyPrincipleGroupsInfoRemoteData
import com.depromeet.team5.core.remotedatasource.model.RetrospectionListRemoteData
import com.depromeet.team5.core.remotedatasource.model.SearchRemoteData
import com.depromeet.team5.core.remotedatasource.model.SystemPrincipleRemoteData
import com.depromeet.team5.core.remotedatasource.model.UserStatsRemoteData


interface HedgeApiSource {

    suspend fun search(query: String): SearchRemoteData

    suspend fun createFeedback(retrospectionId: Int): FeedbackRemoteData

    suspend fun getPrincipleGroups(orderType: String): MyPrincipleGroupsInfoRemoteData

    suspend fun getPrincipleGroup(groupId: Int): MyPrincipleGroupRemoteData

    suspend fun deletePrincipleGroup(groupId: Int)

    suspend fun deletePrinciple(principleId: Int)

    suspend fun createPrincipleGroup(body: Map<String, Any?>): MyPrincipleGroupRemoteData

    suspend fun userStats(): UserStatsRemoteData

    suspend fun retrospectionList(): RetrospectionListRemoteData

    suspend fun systemPrincipleList(): SystemPrincipleRemoteData
}
