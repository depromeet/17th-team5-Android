package com.depromeet.team5.core.remotedatasource.model

import com.depromeet.team5.core.data.model.MyPrincipleData
import com.depromeet.team5.core.data.model.MyPrincipleGroupData
import com.depromeet.team5.core.data.model.MyPrincipleGroupsInfoData
import com.depromeet.team5.core.remotedatasource.mapper.RemoteDataMapper


data class MyPrincipleGroupsInfoRemoteData(
    val code: String,
    val message: String,
    val data: List<MyPrincipleGroupRemoteData>
) : RemoteDataMapper<MyPrincipleGroupsInfoData> {

    override fun toData(): MyPrincipleGroupsInfoData = MyPrincipleGroupsInfoData(
        code = code,
        message = message,
        data = data.map { it.toData() }
    )
}

data class MyPrincipleGroupRemoteData(
    val id: Int,
    val groupName: String,
    val thumbnail: String,
    val imageId: Int?,
    val principleType: String,
    val displayOrder: Int,
    val principles: List<MyPrincipleRemoteData>
) : RemoteDataMapper<MyPrincipleGroupData> {

    override fun toData(): MyPrincipleGroupData = MyPrincipleGroupData(
        id = id,
        groupName = groupName,
        thumbnail = thumbnail,
        imageId = imageId,
        principleType = principleType,
        displayOrder = displayOrder,
        principles = principles.map { it.toData() }
    )

}

data class MyPrincipleRemoteData(
    val id: Int,
    val groupId: Int,
    val groupName: String,
    val principle: String,
    val description: String,
    val displayOrder: Int
) : RemoteDataMapper<MyPrincipleData> {

    override fun toData(): MyPrincipleData = MyPrincipleData(
        id = id,
        groupId = groupId,
        groupName = groupName,
        principle = principle,
        description = description,
        displayOrder = displayOrder
    )
}
