package com.depromeet.team5.core.retrofit.apisourceimpl

import com.depromeet.team5.core.remotedatasource.apisource.HedgeApiSource
import com.depromeet.team5.core.remotedatasource.model.FeedbackRemoteData
import com.depromeet.team5.core.remotedatasource.model.MyPrincipleGroupRemoteData
import com.depromeet.team5.core.remotedatasource.model.MyPrincipleGroupsInfoRemoteData
import com.depromeet.team5.core.remotedatasource.model.MyPrincipleRemoteData
import com.depromeet.team5.core.remotedatasource.model.RetrospectionListRemoteData
import com.depromeet.team5.core.remotedatasource.model.SocialLoginRemoteData
import com.depromeet.team5.core.remotedatasource.model.StockSliceRemoteData
import com.depromeet.team5.core.remotedatasource.model.SystemPrincipleRemoteData
import com.depromeet.team5.core.remotedatasource.model.UserStatsRemoteData
import com.depromeet.team5.core.remotedatasource.request.SocialLoginRequestRemoteData
import com.depromeet.team5.core.retrofit.api.HedgeApi
import com.depromeet.team5.core.retrofit.model.SocialLoginFailureResponse
import com.depromeet.team5.core.retrofit.model.SocialLoginSuccessResponse
import com.depromeet.team5.core.retrofit.toRequestBody
import kotlinx.serialization.json.Json
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
internal class HedgeApiSourceImpl @Inject constructor(
    private val hedgeApi: HedgeApi,
    private val json: Json,
) : HedgeApiSource {

    override suspend fun getStockSlice(
        companyName: String,
        nextCursor: String?,
        size: Int?
    ): StockSliceRemoteData =
        hedgeApi.getStockSlice(
            companyName = companyName,
            nextCursor = nextCursor,
            size = size
        ).toRemoteData()

    override suspend fun createFeedback(retrospectionId: Int): FeedbackRemoteData = hedgeApi
        .createFeedback(retrospectionId = retrospectionId)
        .toRemoteData()

    override suspend fun getPrincipleGroups(orderType: String): MyPrincipleGroupsInfoRemoteData =
        hedgeApi.getPrincipleGroups(orderType = orderType).toRemoteData()

    override suspend fun getPrincipleGroup(groupId: Int): MyPrincipleGroupRemoteData =
        hedgeApi.getPrincipleGroup(groupId).toRemoteData()

    override suspend fun deletePrincipleGroup(groupId: Int) =
        hedgeApi.deletePrincipleGroup(groupId)

    override suspend fun deletePrinciple(principleId: Int) =
        hedgeApi.deletePrinciple(principleId)

    override suspend fun addPrinciple(
        groupId: Int,
        principle: String,
        description: String
    ) {
        hedgeApi.addPrinciple(
            mapOf(
                "groupId" to groupId,
                "principle" to principle,
                "description" to description
            ).toRequestBody()
        )
    }

    override suspend fun modifyPrinciple(
        principleId: Int,
        principle: String,
        description: String
    ): MyPrincipleRemoteData =
        hedgeApi.modifyPrinciple(
            principleId = principleId,
            body = mapOf(
                "principle" to principle,
                "description" to description
            ).toRequestBody()
        ).toRemoteData()

    override suspend fun createPrincipleGroup(body: Map<String, Any?>): MyPrincipleGroupRemoteData =
        hedgeApi.createPrincipleGroup(body.toRequestBody()).toRemoteData()

    override suspend fun userStats(): UserStatsRemoteData =
        hedgeApi
            .userStats()
            .toRemoteData()

    override suspend fun retrospectionList(): RetrospectionListRemoteData =
        hedgeApi
            .retrospectionList()
            .toRemoteData()

    override suspend fun systemPrincipleList(): SystemPrincipleRemoteData =
        hedgeApi
            .systemPrincipleList()
            .toRemoteData()

    override suspend fun socialLogin(body: SocialLoginRequestRemoteData): SocialLoginRemoteData {
        val resp: Response<SocialLoginSuccessResponse> = hedgeApi.socialLogin(body)
        return if (resp.isSuccessful) {
            val success = resp.body()
                ?: return SocialLoginRemoteData.Failure(
                    code = "EMPTY_BODY",
                    message = "Empty response body",
                    data = null
                )
            success.toRemoteData()
        } else {
            val errorText = resp.errorBody()?.string().orEmpty()
            val failure = runCatching {
                json.decodeFromString(SocialLoginFailureResponse.serializer(), errorText)
            }.getOrElse {
                SocialLoginFailureResponse(
                    code = resp.code().toString(),
                    message = "HTTP ${resp.code()} ${resp.message()}",
                    data = null
                )
            }
            failure.toRemoteData()
        }
    }
}
