package com.example.rdash.data.api

import com.example.rdash.data.dto.DesignFilesResponse
import retrofit2.http.GET

interface DesignFilesApi {

    @GET("webhook/223a7fe0-4e32-4414-aacf-1bc0ab1c0bba")
    suspend fun fetchDesignFiles(): DesignFilesResponse

    companion object {
        const val BASE_URL = "https://n8n.rdash.io/"
    }
}