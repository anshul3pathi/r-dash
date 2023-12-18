package com.example.rdash.data.dto

import com.google.gson.annotations.SerializedName

data class FileResponse(
    @SerializedName("file") val fileUrl: String,
    @SerializedName("file_size") val fileSize: Double,
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("section") val section: String,
    @SerializedName("status") val status: Int,
    @SerializedName("type") val fileType: String,
    @SerializedName("uploaded_at") val uploadedAt: String,
    @SerializedName("version") val version: Int
)