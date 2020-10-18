package com.upick.upick.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

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
    suspend fun getUsers(@Path("user_id") userId: Int): UsersGETResponse

    @GET("pending/{user_id}")
    suspend fun getPending(@Path("user_id") userId: Int): PendingGETResponse

    @GET("groups/{group_id}")
    suspend fun getGroups(@Path("group_id") groupId: Int): GroupsGETResponse

    @GET("invites/{user_id}")
    suspend fun getInvites(@Path("user_id") userId: Int): InvitesGETResponse

    @GET("restaurants/{id}")
    suspend fun getRestaurants(@Path("id") id: Int): RestaurantsGETResponse

    @POST("users")
    suspend fun postUsers(@Body body: UserPwdBody): UsersPOSTResponse

    @POST("signin")
    suspend fun postSignIn(@Body body: UserPwdBody): SignInPOSTResponse

    @POST("invites")
    suspend fun postInvites(@Body body: InvitesPOSTBody): InvitesPOSTResponse

    @POST("create")
    suspend fun postCreate(@Body body: CreatePOSTBody): CreatePOSTResponse

    @POST("groups")
    suspend fun postGroups(@Body body: UserGroupBody): GroupsPOSTResponse

    @POST("survey")
    suspend fun postSurvey(@Body body: SurveyPOSTBody): SurveyPOSTResponse

    @POST("vote")
    suspend fun postVote(@Body body: VotePOSTBody): VotePOSTResponse

    @DELETE("groups")
    suspend fun deleteGroups(@Body body: UserGroupBody): GroupsDELETEResponse

    @POST("groups/join")
    suspend fun postGroupsJoin(@Body body: UserGroupBody): GroupsJoinPOSTResponse

    @DELETE("users")
    suspend fun deleteUsers(@Body body: UserPwdBody): UsersDELETEResponse
}

@JsonClass(generateAdapter = true)
data class UserPwdBody(
    val usr: String,
    val pwd: String
)

@JsonClass(generateAdapter = true)
data class InvitesPOSTBody(
    val friend1: Int,
    val friend2: Int
)

@JsonClass(generateAdapter = true)
data class CreatePOSTBody(
    val host: Int,
    val name: String,
    val date: String
)

@JsonClass(generateAdapter = true)
data class UserGroupBody(
    val user: Int,  // user id
    val group: Int  // group id
)

@JsonClass(generateAdapter = true)
data class SurveyPOSTBody(
    val user: Int,
    @Json(name = "location_x") val locationX: Float,
    @Json(name = "location_y") val locationY: Float,
    val price: Int,
    val distance: Int,
    val time: Int,
    val preferences: List<Int>
)

@JsonClass(generateAdapter = true)
data class VotePOSTBody(
    val restaurants: List<Int>
)