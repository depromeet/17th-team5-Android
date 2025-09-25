package com.depromeet.team5.core.remotedatasource.apisource


interface HedgeApiSource {

    fun createRetrospection(body: Map<String, Any>)
}
