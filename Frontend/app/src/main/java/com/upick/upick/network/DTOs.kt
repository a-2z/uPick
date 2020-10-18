package com.upick.upick.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NetworkXEContainer(
    val rates: Map<String, String>,
    val base: String,
    val date: String
)

@JsonClass(generateAdapter = true)
data class UsersGETResponse(
    val success: Boolean,
    val data: UserData
) {
    @JsonClass(generateAdapter = true)
    data class UserData(
        val id: Int,
        val friends: List<Int>,  // userIds
        val restrictions: List<Int>  // restrictionIds?
    )

}

@JsonClass(generateAdapter = true)
data class PendingGETResponse(
    val success: Boolean,
    val data: PendingData
) {
    @JsonClass(generateAdapter = true)
    data class PendingData(
        val sent: List<Int>,  // userIds
        val received: List<Int>  // userIds
    )
}

@JsonClass(generateAdapter = true)
data class GroupsGETResponse(
    val success: Boolean,
    val data: GroupsData
) {
    @JsonClass(generateAdapter = true)
    data class GroupsData(
        val group: Int,
        val members: List<Int>,
        @Json(name = "survey_complete") val surveyComplete: Boolean,
        @Json(name = "top_choices") val topChoices: List<Choice>,
        @Json(name = "voting_complete") val votingComplete: Boolean,
        @Json(name = "final_choice") val finalChoice: Choice
    ) {
        @JsonClass(generateAdapter = true)
        data class Choice(
            val name: String,
            @Json(name = "phone_number") val phoneNumber: String,
            val description: String,
            @Json(name = "image_url") val imageUrl: String,
            val rating: Int,
            @Json(name = "wait_time") val waitTime: String,
            @Json(name = "location_x") val locationX: Float,
            @Json(name = "location_y") val locationY: Float
        )
    }
}

// STOPPED HERE

//@JsonClass(generateAdapter = true)
//data class PendingGetResponse(
//) {
//    @JsonClass(generateAdapter = true)
//    data class PendingData(
//    )
//}


