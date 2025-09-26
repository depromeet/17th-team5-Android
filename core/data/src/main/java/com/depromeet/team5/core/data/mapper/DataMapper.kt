package com.depromeet.team5.core.data.mapper


interface DataMapper<out T> {

    fun toDomain(): T
}
