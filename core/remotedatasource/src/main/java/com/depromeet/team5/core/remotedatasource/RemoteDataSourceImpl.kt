package com.depromeet.team5.core.remotedatasource

import androidx.core.net.toUri
import com.depromeet.team5.core.data.datasource.RemoteDataSource
import com.depromeet.team5.core.data.model.FeedbackData
import com.depromeet.team5.core.data.model.MyPrincipleData
import com.depromeet.team5.core.data.model.MyPrincipleGroupData
import com.depromeet.team5.core.data.model.MyPrincipleGroupsInfoData
import com.depromeet.team5.core.data.model.RetrospectionListData
import com.depromeet.team5.core.data.model.SearchData
import com.depromeet.team5.core.data.model.SystemPrincipleData
import com.depromeet.team5.core.data.model.UserStatsData
import com.depromeet.team5.core.data.request.CreateRetrospectionRequestData
import com.depromeet.team5.core.remotedatasource.apisource.HedgeApiSource
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
internal class RemoteDataSourceImpl @Inject constructor(
    private val hedgeApiSource: HedgeApiSource
) : RemoteDataSource {

    override suspend fun search(query: String): SearchData =
        hedgeApiSource.search(query).toData()

    override suspend fun createFeedback(retrospectionId: Int): FeedbackData =
        hedgeApiSource.createFeedback(retrospectionId).toData()

    override suspend fun getPrinciples(orderType: String): MyPrincipleGroupsInfoData =
        hedgeApiSource.getPrincipleGroups(orderType).toData()

    override suspend fun getPrincipleGroup(groupId: Int): MyPrincipleGroupData =
        hedgeApiSource.getPrincipleGroup(groupId).toData()

    override suspend fun deletePrincipleGroup(groupId: Int) {
        hedgeApiSource.deletePrincipleGroup(groupId)
    }

    override suspend fun deletePrinciple(principleId: Int) {
        hedgeApiSource.deletePrinciple(principleId)
    }

    override suspend fun addPrinciple(
        groupId: Int,
        principle: String,
        description: String
    ) {
        hedgeApiSource.addPrinciple(
            groupId = groupId,
            principle = principle,
            description = description
        )
    }

    override suspend fun modifyPrinciple(
        principleId: Int,
        principle: String,
        description: String
    ): MyPrincipleData = hedgeApiSource.modifyPrinciple(
        principleId = principleId,
        principle = principle,
        description = description
    ).toData()

    override suspend fun createPrincipleGroup(body: Map<String, Any?>): MyPrincipleGroupData =
        hedgeApiSource.createPrincipleGroup(body).toData()

    override suspend fun userStats(): UserStatsData =
        hedgeApiSource.userStats().toData()

    override suspend fun retrospectionList(): RetrospectionListData =
        hedgeApiSource.retrospectionList().toData()

    override suspend fun systemPrincipleList(): SystemPrincipleData =
        hedgeApiSource.systemPrincipleList().toData()

}
