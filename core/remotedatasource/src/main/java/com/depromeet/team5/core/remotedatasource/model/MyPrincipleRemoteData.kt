package com.depromeet.team5.core.remotedatasource.model

import com.depromeet.team5.core.data.model.MyPrincipleData
import com.depromeet.team5.core.data.model.MyPrincipleInfoData
import com.depromeet.team5.core.remotedatasource.mapper.RemoteDataMapper


data class MyPrincipleRemoteData(
    val code: String,
    val message: String,
    val data: List<MyPrincipleInfoRemoteData>
) : RemoteDataMapper<MyPrincipleData> {

    override fun toData(): MyPrincipleData = MyPrincipleData(
        code = code,
        message = message,
        data = data.map { it.toData() }
    )
}

data class MyPrincipleInfoRemoteData(
    val id: Int,
    val groupId: Int,
    val groupName: String,
    val principle: String,
    val displayOrder: Int
) : RemoteDataMapper<MyPrincipleInfoData> {

    override fun toData(): MyPrincipleInfoData = MyPrincipleInfoData(
        id = id,
        groupId = groupId,
        groupName = groupName,
        principle = principle,
        displayOrder = displayOrder
    )
}
