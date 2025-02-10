package com.websarva.wings.keibapredictsupporter.network

import com.websarva.wings.keibapredictsupporter.DataClass.ShutubaData
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

class ApiClient {
    private val BASE_URL = Secret().getBaseUrl()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService : ApiService by lazy{
        retrofit.create(ApiService::class.java)
    }

    suspend fun fetchShutubaData(filename: String): Response<ShutubaData> {
        return apiService.fetchShutubaData(filename)
    }

}

interface ApiService {
    @GET("get_json")
    suspend fun fetchShutubaData(
        @Query("file_name") filename: String,
    ): Response<ShutubaData>
}