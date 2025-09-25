package com.depromeet.team5.core.model.mapper


interface DomainMapper<out T> {

    fun toDomain(): T
}
