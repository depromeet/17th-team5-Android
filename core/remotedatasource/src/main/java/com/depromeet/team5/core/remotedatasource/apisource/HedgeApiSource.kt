package com.depromeet.team5.core.remotedatasource.apisource

import com.depromeet.team5.core.remotedatasource.model.FeedbackRemoteData
import com.depromeet.team5.core.remotedatasource.model.MyPrincipleGroupRemoteData
import com.depromeet.team5.core.remotedatasource.model.MyPrincipleGroupsInfoRemoteData
import com.depromeet.team5.core.remotedatasource.model.MyPrincipleRemoteData
import com.depromeet.team5.core.remotedatasource.model.RetrospectionListRemoteData
import com.depromeet.team5.core.remotedatasource.model.SocialLoginRemoteData
import com.depromeet.team5.core.remotedatasource.model.StockSliceRemoteData
import com.depromeet.team5.core.remotedatasource.model.SystemPrincipleRemoteData
import com.depromeet.team5.core.remotedatasource.model.TokenRemoteData
import com.depromeet.team5.core.remotedatasource.model.UserStatsRemoteData
import com.depromeet.team5.core.remotedatasource.request.SocialLoginRequestRemoteData


interface HedgeApiSource {

    suspend fun getStockSlice(
        companyName: String,
        nextCursor: String?,
        size: Int?
    ): StockSliceRemoteData

    suspend fun createFeedback(retrospectionId: Int): FeedbackRemoteData

    suspend fun getPrincipleGroups(orderType: String): MyPrincipleGroupsInfoRemoteData

    suspend fun getPrincipleGroup(groupId: Int): MyPrincipleGroupRemoteData

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
    ): MyPrincipleRemoteData

    suspend fun modifyPrincipleGroup(
        groupId: Int,
        body: Map<String, Any?>
    ): MyPrincipleGroupRemoteData

    suspend fun createPrincipleGroup(body: Map<String, Any?>): MyPrincipleGroupRemoteData

    suspend fun userStats(): UserStatsRemoteData

    suspend fun retrospectionList(): RetrospectionListRemoteData

    suspend fun systemPrincipleList(): SystemPrincipleRemoteData

    suspend fun socialLogin(body: SocialLoginRequestRemoteData): SocialLoginRemoteData

    suspend fun getFeedback(retrospectionId: Int): FeedbackRemoteData

    suspend fun refreshAccessToken(body: Map<String, Any?>): TokenRemoteData
}
