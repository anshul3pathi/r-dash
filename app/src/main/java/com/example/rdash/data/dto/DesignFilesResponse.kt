package com.example.rdash.data.dto

import com.google.gson.annotations.SerializedName

data class DesignFilesResponse(
    @SerializedName("code") val code: Int,
    @SerializedName("data") val files: List<FileResponse>,
    @SerializedName("errors") val errors: String,
    @SerializedName("message") val message: String,
    @SerializedName("success") val success: Boolean
)