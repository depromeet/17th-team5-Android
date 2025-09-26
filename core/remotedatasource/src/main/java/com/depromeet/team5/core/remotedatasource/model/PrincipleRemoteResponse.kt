package com.depromeet.team5.core.remotedatasource.model

import com.depromeet.team5.core.data.model.PrincipleData
import com.depromeet.team5.core.remotedatasource.mapper.DataMapper


data class PrincipleRemoteResponse(
    val title: String,
    val content: String
) : DataMapper<PrincipleData> {

    override fun toData(): PrincipleData = PrincipleData(
        title = title,
        content = content
    )
}
