package com.depromeet.team5.core.data.model

import com.depromeet.team5.core.data.mapper.DataMapper
import com.depromeet.team5.core.domain.model.BaseDomain

@Suppress("UNCHECKED_CAST")
data class BaseData<T>(
    val code: String,
    val message: String,
    val data: T?
) : DataMapper<BaseDomain<T>> {
    override fun toDomain(): BaseDomain<T> = BaseDomain(code, message, data)

    fun <R> toBaseDomain(): BaseDomain<R> {
        val convertedData: R? = when (val capturedData = data) {
            null -> null
            is List<*> -> capturedData.map { element ->
                when (element) {
                    is DataMapper<*> -> element.toDomain()
                    else -> element
                }
            } as R
            is DataMapper<*> -> capturedData.toDomain() as R
            else -> capturedData as R
        }

        return BaseDomain(
            code = code,
            message = message,
            data = convertedData
        )
    }
}