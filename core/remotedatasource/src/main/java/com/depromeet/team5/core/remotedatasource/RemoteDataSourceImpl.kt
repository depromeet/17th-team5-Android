package com.depromeet.team5.core.remotedatasource

import com.depromeet.team5.core.data.datasource.RemoteDataSource
import com.depromeet.team5.core.data.model.FeedbackData
import com.depromeet.team5.core.data.model.RetrospectionData
import com.depromeet.team5.core.remotedatasource.apisource.HedgeApiSource
import com.depromeet.team5.core.remotedatasource.model.FeedbackRemoteData
import com.depromeet.team5.core.remotedatasource.model.PrincipleRemoteData
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
internal class RemoteDataSourceImpl @Inject constructor(
    private val hedgeApiSource: HedgeApiSource
) : RemoteDataSource {

    override suspend fun createRetrospection(body: Map<String, Any?>): RetrospectionData =
        hedgeApiSource.createRetrospection(body).toData()

    override suspend fun createFeedback(retrospectionId: Int): FeedbackData {
        val response = hedgeApiSource.createFeedback(retrospectionId)

        val jsonObject = Json.parseToJsonElement(response).jsonObject

        val code = jsonObject.getValue(KEY_CODE).jsonPrimitive.content
        val message = jsonObject.getValue(KEY_MESSAGE).jsonPrimitive.content

        val remoteData = jsonObject[KEY_DATA]?.jsonObject?.let { jsonObject ->
            val summarize = jsonObject[KEY_SUMMARIZE]?.jsonPrimitive?.content ?: ""
            val summarizeForMarket =
                jsonObject[KEY_SUMMARIZE_FOR_MARKET]?.jsonPrimitive?.content ?: ""
            val principlesJsonArray = jsonObject[KEY_PRINCIPLES]?.jsonArray

            val principles = mutableListOf<PrincipleRemoteData>()

            principlesJsonArray?.forEach { jsonElement ->
                for ((key, value) in jsonElement.jsonObject.entries) {
                    principles.add(
                        PrincipleRemoteData(
                            title = key,
                            content = value.jsonPrimitive.content
                        )
                    )
                }
            }

            FeedbackRemoteData(
                code = code,
                message = message,
                summarize = summarize,
                summarizeOfMarket = summarizeForMarket,
                principles = principles
            )
        } ?: FeedbackRemoteData.EMPTY

        return remoteData.toData()
    }


    companion object {

        private const val KEY_CODE = "code"
        private const val KEY_MESSAGE = "message"
        private const val KEY_DATA = "data"
        private const val KEY_SUMMARIZE = "요약 한 마디"
        private const val KEY_SUMMARIZE_FOR_MARKET = "당시 시장 현황"
        private const val KEY_PRINCIPLES = "AI 추천 원칙"
    }
}
