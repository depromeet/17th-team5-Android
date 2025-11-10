package com.depromeet.team5.core.remotedatasource.model

import com.depromeet.team5.core.data.model.BaseData
import com.depromeet.team5.core.remotedatasource.mapper.RemoteDataMapper

@Suppress("UNCHECKED_CAST")
data class BaseRemoteData<T>(
    val code: String,
    val message: String,
    val data: T?
) : RemoteDataMapper<BaseData<T>> {
    override fun toData(): BaseData<T> = BaseData(code, message, data)
    fun <R> toBaseData(): BaseData<R> {
        val mapped: R? = when (val d = data) {
            null -> null
            is List<*> -> d.map { elem ->
                when (elem) {
                    is RemoteDataMapper<*> -> elem.toData()
                    else -> elem
                }
            } as R
            is RemoteDataMapper<*> -> d.toData() as R
            else -> d as R
        }

        return BaseData(
            code = code,
            message = message,
            data = mapped
        )
    }
}