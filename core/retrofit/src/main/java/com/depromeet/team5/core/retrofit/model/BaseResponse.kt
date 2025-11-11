package com.depromeet.team5.core.retrofit.model

import com.depromeet.team5.core.remotedatasource.model.BaseRemoteData
import com.depromeet.team5.core.retrofit.mapper.RetrofitMapper
import kotlinx.serialization.Serializable

@Suppress("UNCHECKED_CAST")
@Serializable
data class BaseResponse<T>(
    val code: String,
    val message: String,
    val data: T?
) : RetrofitMapper<BaseRemoteData<T>> {
    override fun toRemoteData(): BaseRemoteData<T> =
        BaseRemoteData(code, message, data)

    fun <R> toBaseRemoteData(): BaseRemoteData<R> {
        val convertedData: R? = when (val capturedData = data) {
            null -> null
            is List<*> -> capturedData.map { element ->
                when (element) {
                    is RetrofitMapper<*> -> element.toRemoteData()
                    else -> element
                }
            } as R
            is RetrofitMapper<*> -> capturedData.toRemoteData() as R
            else -> capturedData as R
        }

        return BaseRemoteData(
            code = code,
            message = message,
            data = convertedData
        )
    }
}