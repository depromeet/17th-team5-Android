package com.depromeet.team5.core.retrofit.apisourceimpl

import com.depromeet.team5.core.remotedatasource.apisource.RetrospectionApiSource
import com.depromeet.team5.core.retrofit.api.RetrospectionApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class RetrospectionApiSourceImpl @Inject constructor(
    private val retrospectionApi: RetrospectionApi,
) : RetrospectionApiSource {

}
