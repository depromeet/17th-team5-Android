package com.depromeet.team5.core.data.datasource

import com.depromeet.team5.core.data.model.FeedbackData
import com.depromeet.team5.core.data.model.MyPrincipleGroupsData
import com.depromeet.team5.core.data.model.RetrospectionData
import com.depromeet.team5.core.data.model.RetrospectionListData
import com.depromeet.team5.core.data.model.SearchData
import com.depromeet.team5.core.data.request.CreateRetrospectionRequestData
import com.depromeet.team5.core.data.model.SystemPrincipleData
import com.depromeet.team5.core.data.model.UserStatsData


interface RemoteDataSource {

    suspend fun search(query: String): SearchData

    suspend fun createRetrospection(request: CreateRetrospectionRequestData): RetrospectionData

    suspend fun createFeedback(retrospectionId: Int): FeedbackData

    suspend fun getPrinciples(orderType: String): MyPrincipleGroupsData

    suspend fun uploadImageUri(
        domain: String,
        uri: String,
        fileName: String? = null
    ): Int

    suspend fun userStats(): UserStatsData

    suspend fun retrospectionList(): RetrospectionListData

    suspend fun systemPrincipleList(): SystemPrincipleData
}
