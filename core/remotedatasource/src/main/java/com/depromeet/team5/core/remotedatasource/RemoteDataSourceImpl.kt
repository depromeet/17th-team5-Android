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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
internal class RemoteDataSourceImpl @Inject constructor(
    private val hedgeApiSource: HedgeApiSource,
) : RemoteDataSource {

    override suspend fun search(query: String): SearchData =
        hedgeApiSource.search(query).toData()

    // 테스트를 위한 디스패치, todo di
    override suspend fun createRetrospection(request: CreateRetrospectionRequestData): RetrospectionData = withContext(Dispatchers.IO) {
        hedgeApiSource.createRetrospection(
            request.toRemoteData(
                request.principleChecks.map {
                    it.toRemoteData(
                        coroutineScope {
                            it.imageUrls.map {
                                async {
                                    hedgeApiSource.uploadImageUri(
                                        "retrospection",
                                        it.toUri(),
                                    )
                                }
                            }.awaitAll()
                        }
                    )
                }
            )
        ).toData()
    }

    override suspend fun createFeedback(retrospectionId: Int): FeedbackData =
        hedgeApiSource.createFeedback(retrospectionId).toData()

    override suspend fun getPrinciples(orderType: String): MyPrincipleGroupsData =
        hedgeApiSource.getPrincipleGroups(orderType).toData()
}
