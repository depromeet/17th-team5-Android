package com.depromeet.team5.core.retrofit.model

import kotlinx.serialization.Serializable

@Serializable
data class ImageUploadResponse(
    val imageId: Int,
    val objectKey: String,
    val fileName: String,
    val fileSize: Long,
)