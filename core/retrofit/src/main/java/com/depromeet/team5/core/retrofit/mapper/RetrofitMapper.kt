package com.depromeet.team5.core.retrofit.mapper


internal interface RetrofitMapper<out T> {

    fun toRemoteData(): T
}
