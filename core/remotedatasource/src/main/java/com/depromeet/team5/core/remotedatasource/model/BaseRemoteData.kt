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
        val convertedData: R? = when (val capturedData = data) {
            null -> null
            is List<*> -> capturedData.map { element ->
                when (element) {
                    is RemoteDataMapper<*> -> element.toData()
                    else -> element
                }
            } as R
            is RemoteDataMapper<*> -> capturedData.toData() as R
            else -> capturedData as R
        }

        return BaseData(
            code = code,
            message = message,
            data = convertedData
        )
    }
}