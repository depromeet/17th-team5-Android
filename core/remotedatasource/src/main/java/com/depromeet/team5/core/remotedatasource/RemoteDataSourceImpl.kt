package com.depromeet.team5.core.remotedatasource

import com.depromeet.team5.core.data.datasource.RemoteDataSource
import com.depromeet.team5.core.data.model.FeedbackData
import com.depromeet.team5.core.data.model.MyPrincipleGroupsData
import com.depromeet.team5.core.data.model.RetrospectionData
import com.depromeet.team5.core.data.model.SearchData
import com.depromeet.team5.core.remotedatasource.apisource.HedgeApiSource
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
internal class RemoteDataSourceImpl @Inject constructor(
    private val hedgeApiSource: HedgeApiSource
) : RemoteDataSource {

    override suspend fun search(query: String): SearchData =
        hedgeApiSource.search(query).toData()

    override suspend fun createRetrospection(body: Map<String, Any?>): RetrospectionData =
        hedgeApiSource.createRetrospection(body).toData()

    override suspend fun createFeedback(retrospectionId: Int): FeedbackData =
        hedgeApiSource.createFeedback(retrospectionId).toData()

    override suspend fun getPrinciples(orderType: String): MyPrincipleGroupsData =
        hedgeApiSource.getPrincipleGroups(orderType).toData()
}
