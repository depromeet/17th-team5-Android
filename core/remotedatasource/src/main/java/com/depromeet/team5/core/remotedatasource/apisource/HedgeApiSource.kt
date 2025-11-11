package com.depromeet.team5.core.remotedatasource.apisource

import android.net.Uri
import com.depromeet.team5.core.remotedatasource.model.FeedbackRemoteData
import com.depromeet.team5.core.remotedatasource.model.MyPrincipleGroupRemoteData
import com.depromeet.team5.core.remotedatasource.model.MyPrincipleGroupsInfoRemoteData
import com.depromeet.team5.core.remotedatasource.model.RetrospectionListRemoteData
import com.depromeet.team5.core.remotedatasource.model.RetrospectionRemoteData
import com.depromeet.team5.core.remotedatasource.model.SearchRemoteData
import com.depromeet.team5.core.remotedatasource.model.SocialLoginRemoteData
import com.depromeet.team5.core.remotedatasource.model.SystemPrincipleRemoteData
import com.depromeet.team5.core.remotedatasource.model.UserStatsRemoteData
import com.depromeet.team5.core.remotedatasource.request.CreateRetrospectionRequestRemoteData
import com.depromeet.team5.core.remotedatasource.request.SocialLoginRequestRemoteData


interface HedgeApiSource {

    suspend fun search(query: String): SearchRemoteData

    suspend fun createRetrospection(request: CreateRetrospectionRequestRemoteData): RetrospectionRemoteData

    suspend fun createFeedback(retrospectionId: Int): FeedbackRemoteData

    suspend fun getPrincipleGroups(orderType: String): MyPrincipleGroupsInfoRemoteData

    suspend fun getPrincipleGroup(groupId: Int): MyPrincipleGroupRemoteData

    suspend fun deletePrincipleGroup(groupId: Int)

    suspend fun deletePrinciple(principleId: Int)

    suspend fun createPrincipleGroup(body: Map<String, Any?>): MyPrincipleGroupRemoteData

    suspend fun uploadImageUri(
        domain: String,
        uri: Uri,
        fileName: String? = null
    ): Int

    suspend fun userStats(): UserStatsRemoteData

    suspend fun retrospectionList(): RetrospectionListRemoteData

    suspend fun systemPrincipleList(): SystemPrincipleRemoteData

    suspend fun socialLogin(body: SocialLoginRequestRemoteData): SocialLoginRemoteData
}
