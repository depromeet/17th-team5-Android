package com.depromeet.team5.core.data.datasource

import com.depromeet.team5.core.data.model.FeedbackData
import com.depromeet.team5.core.data.model.MyPrincipleGroupData
import com.depromeet.team5.core.data.model.MyPrincipleGroupsInfoData
import com.depromeet.team5.core.data.model.RetrospectionListData
import com.depromeet.team5.core.data.model.SearchData
import com.depromeet.team5.core.data.model.SystemPrincipleData
import com.depromeet.team5.core.data.model.UserStatsData


interface RemoteDataSource {

    suspend fun search(query: String): SearchData

    suspend fun createFeedback(retrospectionId: Int): FeedbackData

    suspend fun getPrinciples(orderType: String): MyPrincipleGroupsInfoData

    suspend fun getPrincipleGroup(groupId: Int): MyPrincipleGroupData

    suspend fun deletePrincipleGroup(groupId: Int)

    suspend fun deletePrinciple(principleId: Int)

    suspend fun createPrincipleGroup(body: Map<String, Any?>): MyPrincipleGroupData

    suspend fun userStats(): UserStatsData

    suspend fun retrospectionList(): RetrospectionListData

    suspend fun systemPrincipleList(): SystemPrincipleData
}
