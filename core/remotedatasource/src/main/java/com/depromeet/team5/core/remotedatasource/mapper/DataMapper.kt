package com.depromeet.team5.core.remotedatasource.mapper


interface DataMapper<out T> {

    fun toData(): T
}
