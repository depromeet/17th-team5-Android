package com.depromeet.team5.core.remotedatasource.apisource

import android.net.Uri
import com.depromeet.team5.core.remotedatasource.model.FeedbackRemoteData
import com.depromeet.team5.core.remotedatasource.model.MyPrincipleGroupRemoteData
import com.depromeet.team5.core.remotedatasource.model.MyPrincipleGroupsRemoteData
import com.depromeet.team5.core.remotedatasource.model.RetrospectionListRemoteData
import com.depromeet.team5.core.remotedatasource.model.RetrospectionRemoteData
import com.depromeet.team5.core.remotedatasource.model.SearchRemoteData
import com.depromeet.team5.core.remotedatasource.request.CreateRetrospectionRequestRemoteData
import com.depromeet.team5.core.remotedatasource.model.SystemPrincipleRemoteData
import com.depromeet.team5.core.remotedatasource.model.UserStatsRemoteData


interface HedgeApiSource {

    suspend fun search(query: String): SearchRemoteData

    suspend fun createRetrospection(request: CreateRetrospectionRequestRemoteData): RetrospectionRemoteData

    suspend fun createFeedback(retrospectionId: Int): FeedbackRemoteData

    suspend fun getPrincipleGroups(orderType: String): MyPrincipleGroupsRemoteData

    suspend fun getPrinciple(groupId: Int): MyPrincipleGroupRemoteData

    suspend fun uploadImageUri(
        domain: String,
        uri: Uri,
        fileName: String? = null
    ): Int

    suspend fun userStats(): UserStatsRemoteData

    suspend fun retrospectionList(): RetrospectionListRemoteData

    suspend fun systemPrincipleList(): SystemPrincipleRemoteData
}
