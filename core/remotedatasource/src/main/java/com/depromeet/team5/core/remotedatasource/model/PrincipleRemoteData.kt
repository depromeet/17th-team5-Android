package com.depromeet.team5.core.remotedatasource.model

import com.depromeet.team5.core.data.model.PrincipleData
import com.depromeet.team5.core.remotedatasource.mapper.RemoteDataMapper


data class PrincipleRemoteData(
    val title: String,
    val content: String
) : RemoteDataMapper<PrincipleData> {

    override fun toData(): PrincipleData = PrincipleData(
        title = title,
        content = content
    )
}
