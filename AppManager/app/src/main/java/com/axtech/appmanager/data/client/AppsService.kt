package com.axtech.appmanager.data.client

import com.axtech.appmanager.data.dto.AppResponse
import kotlinx.coroutines.flow.Flow
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface AppsService {
    @GET("apps")
    fun getAllApps(): Flow<AppResponse>

    @GET("apps/{id}/download")
    fun downloadApp(@Path("id") id: Int): Call<ResponseBody>
}