package com.depromeet.team5.core.data.datasource

import com.depromeet.team5.core.data.model.FeedbackData
import com.depromeet.team5.core.data.model.MyPrincipleData
import com.depromeet.team5.core.data.model.MyPrincipleGroupData
import com.depromeet.team5.core.data.model.MyPrincipleGroupsInfoData
import com.depromeet.team5.core.data.model.RetrospectionListData
import com.depromeet.team5.core.data.model.SocialLoginData
import com.depromeet.team5.core.data.model.StockSliceData
import com.depromeet.team5.core.data.model.SystemPrincipleData
import com.depromeet.team5.core.data.model.TokenData
import com.depromeet.team5.core.data.model.UserStatsData
import com.depromeet.team5.core.data.request.SocialLoginRequestData


interface RemoteDataSource {

    suspend fun getStockSlice(
        companyName: String,
        nextCursor: String?,
        size: Int?
    ): StockSliceData

    suspend fun createFeedback(retrospectionId: Int): FeedbackData

    suspend fun getPrinciples(orderType: String): MyPrincipleGroupsInfoData

    suspend fun getPrincipleGroup(groupId: Int): MyPrincipleGroupData

    suspend fun deletePrincipleGroup(groupId: Int)

    suspend fun deletePrinciple(principleId: Int)

    suspend fun addPrinciple(
        groupId: Int,
        principle: String,
        description: String
    )

    suspend fun modifyPrinciple(
        principleId: Int,
        principle: String,
        description: String
    ): MyPrincipleData

    suspend fun modifyPrincipleGroup(
        groupId: Int,
        body: Map<String, Any?>
    ): MyPrincipleGroupData

    suspend fun createPrincipleGroup(body: Map<String, Any?>): MyPrincipleGroupData

    suspend fun userStats(): UserStatsData

    suspend fun retrospectionList(): RetrospectionListData

    suspend fun systemPrincipleList(): SystemPrincipleData

    suspend fun socialLogin(body: SocialLoginRequestData): SocialLoginData

    suspend fun getFeedback(retrospectionId: Int): FeedbackData

    suspend fun refreshAccessToken(body: Map<String, Any?>): TokenData
}
