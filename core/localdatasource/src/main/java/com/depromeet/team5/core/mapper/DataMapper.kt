package com.depromeet.team5.core.mapper


internal interface DataMapper<out T> {

    fun toData(): T
}
