package com.depromeet.team5.core.mapper


internal interface LocalDataMapper<out T> {

    fun toData(): T
}
