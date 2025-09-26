package com.depromeet.team5.core.remotedatasource.mapper


internal interface RemoteDataMapper<out T> {

    fun toData(): T
}
