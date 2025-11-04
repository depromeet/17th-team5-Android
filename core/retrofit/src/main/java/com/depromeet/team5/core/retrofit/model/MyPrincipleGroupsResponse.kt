package com.depromeet.team5.core.retrofit.model

import com.depromeet.team5.core.remotedatasource.model.MyPrincipleGroupRemoteData
import com.depromeet.team5.core.remotedatasource.model.MyPrincipleGroupsRemoteData
import com.depromeet.team5.core.remotedatasource.model.MyPrincipleRemoteData
import com.depromeet.team5.core.retrofit.mapper.RetrofitMapper
import kotlinx.serialization.Serializable


@Serializable
data class MyPrincipleGroupsResponse(
    val code: String,
    val message: String,
    val data: List<MyPrincipleGroupResponse>
) : RetrofitMapper<MyPrincipleGroupsRemoteData> {

    override fun toRemoteData(): MyPrincipleGroupsRemoteData = MyPrincipleGroupsRemoteData(
        code = code,
        message = message,
        data = data.map { it.toRemoteData() }
    )
}

@Serializable
data class MyPrincipleResponse(
    val code: String,
    val message: String,
    val data: MyPrincipleGroupResponse
) : RetrofitMapper<MyPrincipleGroupRemoteData> {

    override fun toRemoteData(): MyPrincipleGroupRemoteData = MyPrincipleGroupRemoteData(
        id = data.id,
        groupName = data.groupName,
        thumbnail = data.thumbnail,
        principleType = data.principleType,
        displayOrder = data.displayOrder,
        principles = data.principles.map { it.toRemoteData() },
    )
}

@Serializable
data class MyPrincipleGroupResponse(
    val id: Int,
    val groupName: String,
    val thumbnail: String,
    val principleType: String,
    val displayOrder: Int,
    val principles: List<MyPrincipleResponse>
) : RetrofitMapper<MyPrincipleGroupRemoteData> {

    override fun toRemoteData(): MyPrincipleGroupRemoteData = MyPrincipleGroupRemoteData(
        id = id,
        groupName = groupName,
        thumbnail = thumbnail,
        principleType = principleType,
        displayOrder = displayOrder,
        principles = principles.map { it.toRemoteData() }
    )
}

@Serializable
data class MyPrincipleResponse(
    val id: Int,
    val groupId: Int,
    val principleType: String,
    val groupName: String,
    val principle: String,
    val description: String,
    val displayOrder: Int
) : RetrofitMapper<MyPrincipleRemoteData> {
    override fun toRemoteData(): MyPrincipleRemoteData = MyPrincipleRemoteData(
        id = id,
        groupId = groupId,
        groupName = groupName,
        principle = principle,
        description = description,
        displayOrder = displayOrder
    )
}
