package com.depromeet.team5.core.data.mapper


internal interface DataMapper<out T> {

    fun toDomain(): T
}
