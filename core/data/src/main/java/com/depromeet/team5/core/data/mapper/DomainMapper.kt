package com.depromeet.team5.core.data.mapper


interface DomainMapper<out T> {

    fun toDomain(): T
}
