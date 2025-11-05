package com.depromeet.team5.core.domain.repository

import com.depromeet.team5.core.domain.model.Feedback
import com.depromeet.team5.core.domain.model.MyPrincipleGroup
import com.depromeet.team5.core.domain.model.Retrospection
import com.depromeet.team5.core.domain.model.RetrospectionList
import com.depromeet.team5.core.domain.model.Search
import com.depromeet.team5.core.domain.request.CreateRetrospectionRequest
import com.depromeet.team5.core.domain.model.SystemPrinciple
import com.depromeet.team5.core.domain.model.UserStats
import kotlinx.coroutines.flow.Flow


interface HedgeRepository {

    fun search(query: String): Flow<Search>

    fun createRetrospection(request: CreateRetrospectionRequest): Flow<Retrospection>

    fun createFeedback(
        retrospectionId: Int
    ): Flow<Feedback>

    fun getPrincipleGroups(orderType: String): Flow<List<MyPrincipleGroup>>

    fun getPrincipleGroup(groupId: Int): Flow<MyPrincipleGroup>

    fun deletePrincipleGroup(groupId: Int): Flow<Unit>

    fun deletePrinciple(principleId: Int): Flow<Unit>

    suspend fun uploadImageUri(
        domain: String,
        uri: String,
        fileName: String? = null
    ): Int

    fun userStats(): Flow<UserStats>

    fun retrospectionList(): Flow<RetrospectionList>

    fun systemPrincipleList(): Flow<SystemPrinciple>
}
