package com.depromeet.team5.core.domain.repository

import com.depromeet.team5.core.domain.model.Feedback
import com.depromeet.team5.core.domain.model.MyPrinciple
import com.depromeet.team5.core.domain.model.MyPrincipleGroup
import com.depromeet.team5.core.domain.model.RetrospectionList
import com.depromeet.team5.core.domain.model.SocialLogin
import com.depromeet.team5.core.domain.model.StockSlice
import com.depromeet.team5.core.domain.model.SystemPrinciple
import com.depromeet.team5.core.domain.model.UserStats
import kotlinx.coroutines.flow.Flow


interface HedgeRepository {

    fun getStockSlice(
        companyName: String,
        nextCursor: String?,
        size: Int?
    ): Flow<StockSlice>

    fun createFeedback(
        retrospectionId: Int
    ): Flow<Feedback>

    fun getPrincipleGroups(orderType: String): Flow<List<MyPrincipleGroup>>

    fun getPrincipleGroup(groupId: Int): Flow<MyPrincipleGroup>

    fun deletePrincipleGroup(groupId: Int): Flow<Unit>

    fun deletePrinciple(principleId: Int): Flow<Unit>

    fun addPrinciple(
        groupId: Int,
        principle: String,
        description: String
    ): Flow<Unit>

    fun modifyPrinciple(
        principleId: Int,
        principle: String,
        description: String
    ): Flow<MyPrinciple>

    fun modifyPrincipleGroup(
        groupId: Int,
        body: Map<String, Any?>
    ): Flow<MyPrincipleGroup>

    fun createPrincipleGroup(body: Map<String, Any?>): Flow<MyPrincipleGroup>

    fun userStats(): Flow<UserStats>

    fun retrospectionList(): Flow<RetrospectionList>

    fun systemPrincipleList(): Flow<SystemPrinciple>

    fun socialLogin(
        provider: String,
        authCode: String,
        redirectUri: String,
        email: String? = null,
        nickname: String? = null
    ): Flow<SocialLogin>

    fun getFeedback(retrospectionId: Int): Flow<Feedback>
}
