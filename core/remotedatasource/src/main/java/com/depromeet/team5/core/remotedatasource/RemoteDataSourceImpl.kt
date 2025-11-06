package com.depromeet.team5.core.remotedatasource

import androidx.core.net.toUri
import com.depromeet.team5.core.data.datasource.RemoteDataSource
import com.depromeet.team5.core.data.model.FeedbackData
import com.depromeet.team5.core.data.model.MyPrincipleGroupsData
import com.depromeet.team5.core.data.model.RetrospectionData
import com.depromeet.team5.core.data.model.SearchData
import com.depromeet.team5.core.data.request.CreateRetrospectionRequestData
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

    override suspend fun createRetrospection(request: CreateRetrospectionRequestData): RetrospectionData {
        return hedgeApiSource.createRetrospection(
            request.toRemoteData {
                hedgeApiSource.uploadImageUri(
                    "retrospection",
                    it.toUri(),
                )
            }
        ).toData()
    }

    override suspend fun createFeedback(retrospectionId: Int): FeedbackData =
        hedgeApiSource.createFeedback(retrospectionId).toData()

    override suspend fun getPrinciples(orderType: String): MyPrincipleGroupsData =
        hedgeApiSource.getPrincipleGroups(orderType).toData()
}
