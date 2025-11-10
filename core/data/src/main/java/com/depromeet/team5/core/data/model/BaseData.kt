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
        val mapped: R? = when (val d = data) {
            null -> null
            is List<*> -> d.map { elem ->
                when (elem) {
                    is DataMapper<*> -> elem.toDomain()
                    else -> elem
                }
            } as R
            is DataMapper<*> -> d.toDomain() as R
            else -> d as R
        }

        return BaseDomain(
            code = code,
            message = message,
            data = mapped
        )
    }
}