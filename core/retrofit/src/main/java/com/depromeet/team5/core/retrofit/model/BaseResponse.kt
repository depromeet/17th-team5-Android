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
        val mapped: R? = when (val d = data) {
            null -> null
            is List<*> -> d.map { elem ->
                when (elem) {
                    is RetrofitMapper<*> -> elem.toRemoteData()
                    else -> elem
                }
            } as R
            is RetrofitMapper<*> -> d.toRemoteData() as R
            else -> d as R
        }

        return BaseRemoteData(
            code = code,
            message = message,
            data = mapped
        )
    }
}