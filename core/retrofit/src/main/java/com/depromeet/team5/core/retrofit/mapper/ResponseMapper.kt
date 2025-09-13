package com.depromeet.team5.core.retrofit.mapper


internal interface ResponseMapper<out T> {

    fun toRemoteResponse(): T
}
