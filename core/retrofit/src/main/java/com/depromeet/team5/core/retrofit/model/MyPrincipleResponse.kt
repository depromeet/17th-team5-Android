package com.depromeet.team5.core.retrofit.model

import com.depromeet.team5.core.remotedatasource.model.MyPrincipleInfoRemoteData
import com.depromeet.team5.core.remotedatasource.model.MyPrincipleRemoteData
import com.depromeet.team5.core.retrofit.mapper.RetrofitMapper
import kotlinx.serialization.Serializable


@Serializable
data class MyPrincipleResponse(
    val code: String,
    val message: String,
    val data: List<MyPrincipleInfoResponse>
) : RetrofitMapper<MyPrincipleRemoteData> {

    override fun toRemoteData(): MyPrincipleRemoteData = MyPrincipleRemoteData(
        code = code,
        message = message,
        data = data.map { it.toRemoteData() }
    )
}

@Serializable
data class MyPrincipleInfoResponse(
    val id: Int,
    val groupId: Int,
    val groupName: String,
    val principle: String,
    val displayOrder: Int
) : RetrofitMapper<MyPrincipleInfoRemoteData> {
    override fun toRemoteData(): MyPrincipleInfoRemoteData = MyPrincipleInfoRemoteData(
        id = id,
        groupId = groupId,
        groupName = groupName,
        principle = principle,
        displayOrder = displayOrder
    )
}
