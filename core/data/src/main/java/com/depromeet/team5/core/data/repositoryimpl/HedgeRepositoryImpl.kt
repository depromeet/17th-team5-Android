package com.depromeet.team5.core.data.repositoryimpl

import com.depromeet.team5.core.data.datasource.RemoteDataSource
import com.depromeet.team5.core.data.request.SocialLoginRequestData
import com.depromeet.team5.core.domain.model.Feedback
import com.depromeet.team5.core.domain.model.MyPrinciple
import com.depromeet.team5.core.domain.model.MyPrincipleGroup
import com.depromeet.team5.core.domain.model.RetrospectionList
import com.depromeet.team5.core.domain.model.SocialLogin
import com.depromeet.team5.core.domain.model.StockSlice
import com.depromeet.team5.core.domain.model.SystemPrinciple
import com.depromeet.team5.core.domain.model.UserStats
import com.depromeet.team5.core.domain.repository.HedgeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


internal class HedgeRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
) : HedgeRepository {

    override fun getStockSlice(
        companyName: String,
        nextCursor: String?,
        size: Int?
    ): Flow<StockSlice> = flow {
        emit(
            remoteDataSource
                .getStockSlice(companyName = companyName, nextCursor = nextCursor, size = size)
                .toDomain()
        )
    }

    override fun createFeedback(
        retrospectionId: Int,
    ): Flow<Feedback> = flow {
        emit(remoteDataSource.createFeedback(retrospectionId).toDomain())
    }

    override fun getPrincipleGroups(orderType: String): Flow<List<MyPrincipleGroup>> = flow {
        emit(remoteDataSource.getPrinciples(orderType).toDomain())
    }

    override fun getPrincipleGroup(groupId: Int): Flow<MyPrincipleGroup> = flow {
        emit(remoteDataSource.getPrincipleGroup(groupId))
    }
        .map { it.toDomain() }

    override fun deletePrincipleGroup(groupId: Int): Flow<Unit> = flow {
        emit(remoteDataSource.deletePrincipleGroup(groupId))
    }

    override fun deletePrinciple(principleId: Int): Flow<Unit> = flow {
        emit(remoteDataSource.deletePrinciple(principleId))
    }

    override fun addPrinciple(
        groupId: Int,
        principle: String,
        description: String
    ): Flow<Unit> = flow {
        emit(
            remoteDataSource.addPrinciple(
                groupId = groupId,
                principle = principle,
                description = description
            )
        )
    }

    override fun modifyPrinciple(
        principleId: Int,
        principle: String,
        description: String
    ): Flow<MyPrinciple> = flow {
        emit(
            remoteDataSource.modifyPrinciple(
                principleId = principleId,
                principle = principle,
                description = description
            )
        )
    }
        .map { it.toDomain() }

    override fun createPrincipleGroup(body: Map<String, Any?>): Flow<MyPrincipleGroup> = flow {
        emit(remoteDataSource.createPrincipleGroup(body))
    }
        .map { it.toDomain() }

    override fun userStats(): Flow<UserStats> = flow {
        emit(remoteDataSource.userStats().toDomain())
    }

    override fun retrospectionList(): Flow<RetrospectionList> = flow {
        emit(remoteDataSource.retrospectionList().toDomain())
    }

    override fun systemPrincipleList(): Flow<SystemPrinciple> = flow {
        emit(remoteDataSource.systemPrincipleList().toDomain())
    }

    override fun socialLogin(
        provider: String,
        authCode: String,
        redirectUri: String,
        email: String?,
        nickname: String?
    ): Flow<SocialLogin> = flow {
        val req = SocialLoginRequestData(
            provider = provider,
            authCode = authCode,
            redirectUri = redirectUri,
            email = email,
            nickname = nickname
        )
        emit(remoteDataSource.socialLogin(req).toDomain())
    }
}
