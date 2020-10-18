package com.upick.upick.network

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

private const val BASE_URL = "" // TODO: INSERT BASE URL HERE

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

// Singleton, since retrofit.create() is expensive
object Backend {
    val retrofitService: BackendService by lazy { retrofit.create(BackendService::class.java) }
}

// Named as such since this defines the API our Retrofit service creates
interface BackendService {
    @GET("user/{user_id}")
    suspend fun getUser(@Path("user_id") userId: Int): UsersGETResponse

}