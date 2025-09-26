package com.depromeet.team5.core.remotedatasource.mapper


interface RemoteDataMapper<out T> {

    fun toData(): T
}
