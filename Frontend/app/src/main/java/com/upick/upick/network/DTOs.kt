package com.upick.upick.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 *  Check a user's existence, username, and friends
 */
@JsonClass(generateAdapter = true)
data class UsersGETResponse(
    val success: Boolean,
    val data: UsersGETData
) {
    @JsonClass(generateAdapter = true)
    data class UsersGETData(
        val id: Int,
        val username: String,
        val friends: List<Int>
    )
}

/**
 * Get list of friend requests sent and received
 */
@JsonClass(generateAdapter = true)
data class PendingGETResponse(
    val success: Boolean,
    val data: PendingGETData
) {
    @JsonClass(generateAdapter = true)
    data class PendingGETData(
        val sent: List<Int>,  // userIds
        val received: List<Int>  // userIds
    )
}

/**
 * Get all relevant attributes of a group.
 */
@JsonClass(generateAdapter = true)
data class GroupsGETResponse(
    val success: Boolean,
    val data: GroupsGETData
) {
    @JsonClass(generateAdapter = true)
    data class GroupsGETData(
        @Json(name = "group_id") val groupId: Int,
        val date: String,
        val host: Int,
        val name: String,
        val members: List<Int>,
        @Json(name = "survey_complete") val surveyComplete: Boolean,
        @Json(name = "top_choices") val topChoices: List<Int>,  // r1_id, r2_id, ...
        @Json(name = "voting_complete") val votingComplete: Boolean,
        @Json(name = "final_choice") val finalChoice: Int  // r_id
    )
}

/**
 * Get all invitations to groups
 */
@JsonClass(generateAdapter = true)
data class InvitesGETResponse(
    val success: Boolean,
    val data: InvitesGETData
) {
    @JsonClass(generateAdapter = true)
    data class InvitesGETData(
        val invitations: List<Int>  // gr1, grp2
    )
}

/**
 * Get a restaurant's details
 */
@JsonClass(generateAdapter = true)
data class RestaurantsGETResponse(
    val success: Boolean,
    val data: RestaurantsGETData
) {
    @JsonClass(generateAdapter = true)
    data class RestaurantsGETData(
        val id: Int,
        val name: String,
        val price: Int,  // backend will constrain to 1-4 inclusive
        val image: String,
        val rating: Int,  // backend will constrain to 1-5 inclusive
        val description: String,
        @Json(name = "wait_time") val waitTime: Int,  // backend will constrain to 1-3 inclusive
        val phone: String,
        @Json(name = "location_x") val locationX: Float,
        @Json(name = "location_y") val locationY: Float
    )
}

/**
 * Create a new user
 */
@JsonClass(generateAdapter = true)
data class UsersPOSTResponse(
    val success: Boolean,
    val data: Int  // usr_id
)

/**
 * Sign in with existing username/pwd
 */
@JsonClass(generateAdapter = true)
data class SignInPOSTResponse(
    val success: Boolean,
    val data: String  // = "You are logged in."
)

/**
 * Invite friend or accept if invited by that friend.
 * friend1 is always the user.
 */
@JsonClass(generateAdapter = true)
data class InvitesPOSTResponse(
    val success: Boolean,
    val data: InvitesPOSTData
) {
    @JsonClass(generateAdapter = true)
    data class InvitesPOSTData(
        val added: Int  // friend2
    )
}

/**
 * Create a new group
 */
@JsonClass(generateAdapter = true)
data class CreatePOSTResponse(
    val success: Boolean,
    val data: Int  // group_id
)

/**
 * Invite a friend to join a group
 */
@JsonClass(generateAdapter = true)
data class GroupsPOSTResponse(
    val success: Boolean,
    val data: Int  // user
)

/**
 *  Respond to survey questions. Options for price should be
 *  7.5, 18, 33, and 80, but the labels should be ($5-10),
 *  ($10-$25), ($26-$40), and $41+. Please reference the
 *  'upick summary' doc for the actual values submitted vs
 *  the labels the user sees.
 */
@JsonClass(generateAdapter = true)
data class SurveyPOSTResponse(
    val success: Boolean,
    val data: String  // = "Responses submitted"
)

/**
 * Place ranked votes
 */
@JsonClass(generateAdapter = true)
data class VotePOSTResponse(
    val success: Boolean,
    val data: String  // = "Vote submitted."
)

/**
 *Leave a group. If the host leaves, the group is deleted.
 */
@JsonClass(generateAdapter = true)
data class GroupsDELETEResponse(
    val success: Boolean,
    val data: String  // = "You left the group."
)

/**
 * Join a group.
 */
@JsonClass(generateAdapter = true)
data class GroupsJoinPOSTResponse(
    val success: Boolean,
    val data: Int  // group
)

/**
 * Delete a user from the database
 */
@JsonClass(generateAdapter = true)
data class UsersDELETEResponse(
    val success: Boolean,
    val data: String  // user(name)
)