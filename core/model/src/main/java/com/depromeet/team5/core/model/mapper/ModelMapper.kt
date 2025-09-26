package com.depromeet.team5.core.model.mapper


internal interface ModelMapper<out T> {

    fun toDomain(): T
}
