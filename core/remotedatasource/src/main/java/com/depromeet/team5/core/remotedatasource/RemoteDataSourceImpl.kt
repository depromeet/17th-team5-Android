package com.depromeet.team5.core.remotedatasource

import androidx.core.net.toUri
import com.depromeet.team5.core.data.datasource.RemoteDataSource
import com.depromeet.team5.core.data.model.FeedbackData
import com.depromeet.team5.core.data.model.MyPrincipleGroupData
import com.depromeet.team5.core.data.model.RetrospectionData
import com.depromeet.team5.core.data.model.RetrospectionListData
import com.depromeet.team5.core.data.model.SearchData
import com.depromeet.team5.core.data.request.CreateRetrospectionRequestData
import com.depromeet.team5.core.data.model.SystemPrincipleData
import com.depromeet.team5.core.data.model.UserStatsData
import com.depromeet.team5.core.remotedatasource.apisource.HedgeApiSource
import com.depromeet.team5.core.remotedatasource.mapper.toRemoteData
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
internal class RemoteDataSourceImpl @Inject constructor(
    private val hedgeApiSource: HedgeApiSource
) : RemoteDataSource {

    override suspend fun search(query: String): SearchData =
        hedgeApiSource.search(query).toData()

    override suspend fun createRetrospection(request: CreateRetrospectionRequestData): RetrospectionData =
        hedgeApiSource.createRetrospection(request.toRemoteData()).toData()

    override suspend fun uploadImageUri(
        domain: String,
        uri: String,
        fileName: String?
    ) = hedgeApiSource.uploadImageUri(
        domain = domain,
        uri = uri.toUri(),
        fileName = fileName
    )

    override suspend fun createFeedback(retrospectionId: Int): FeedbackData =
        hedgeApiSource.createFeedback(retrospectionId).toData()

    override suspend fun getPrinciples(orderType: String): MyPrincipleGroupsData =
        hedgeApiSource.getPrincipleGroups(orderType).toData()

    override suspend fun getPrincipleGroup(groupId: Int): MyPrincipleGroupData =
        hedgeApiSource.getPrincipleGroup(groupId).toData()

    override suspend fun deletePrincipleGroup(groupId: Int) {
        hedgeApiSource.deletePrincipleGroup(groupId)
    }

    override suspend fun deletePrinciple(principleId: Int) {
        hedgeApiSource.deletePrinciple(principleId)
    }

    override suspend fun createPrincipleGroup(body: Map<String, Any?>): MyPrincipleGroupData =
        hedgeApiSource.createPrincipleGroup(body).toData()

    override suspend fun userStats(): UserStatsData =
        hedgeApiSource.userStats().toData()

    override suspend fun retrospectionList(): RetrospectionListData =
        hedgeApiSource.retrospectionList().toData()

    override suspend fun systemPrincipleList(): SystemPrincipleData =
        hedgeApiSource.systemPrincipleList().toData()

}
