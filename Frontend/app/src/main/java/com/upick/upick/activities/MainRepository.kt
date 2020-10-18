package com.upick.upick.activities

import com.upick.upick.network.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object MainRepository {

    suspend fun getUser(userId: Int): UsersGETResponse {
        return withContext(Dispatchers.IO) {
            try {
                Backend.retrofitService.getUsers(userId)
            } catch (e: Exception) {
                UsersGETResponse(false, null)
            }
        }
    }

    suspend fun getPending(userId: Int): PendingGETResponse {
        return withContext(Dispatchers.IO) {
            try {
                Backend.retrofitService.getPending(userId)
            } catch (e: Exception) {
                PendingGETResponse(false, null)
            }
        }
    }

    suspend fun getGroups(groupId: Int): GroupsGETResponse {
        return withContext(Dispatchers.IO) {
            try {
                Backend.retrofitService.getGroups(groupId)
            } catch (e: Exception) {
                GroupsGETResponse(false, null)
            }
        }
    }

    suspend fun getInvites(userId: Int): InvitesGETResponse {
        return withContext(Dispatchers.IO) {
            try {
                Backend.retrofitService.getInvites(userId)
            } catch (e: Exception) {
                InvitesGETResponse(false, null)
            }
        }
    }

    suspend fun getRestaurants(id: Int): RestaurantsGETResponse {
        return withContext(Dispatchers.IO) {
            try {
                Backend.retrofitService.getRestaurants(id)
            } catch (e: Exception) {
                RestaurantsGETResponse(false, null)
            }
        }
    }

    suspend fun postUsers(usr: String, pwd: String): UsersPOSTResponse {
        val body = UserPwdBody(usr = usr, pwd = pwd)
        return withContext(Dispatchers.IO) {
            try {
                Backend.retrofitService.postUsers(body)
            } catch (e: Exception) {
                UsersPOSTResponse(false, null)
            }
        }
    }

    suspend fun postSignIn(usr: String, pwd: String): SignInPOSTResponse {
        val body = UserPwdBody(usr = usr, pwd = pwd)
        return withContext(Dispatchers.IO) {
            try {
                Backend.retrofitService.postSignIn(body)
            } catch (e: Exception) {
                SignInPOSTResponse(false, null)
            }
        }
    }

    suspend fun postInvites(friend1: Int, friend2: Int): InvitesPOSTResponse {
        val body = InvitesPOSTBody(friend1 = friend1, friend2 = friend2)
        return withContext(Dispatchers.IO) {
            try {
                Backend.retrofitService.postInvites(body)
            } catch (e: Exception) {
                InvitesPOSTResponse(false, null)
            }
        }
    }

    suspend fun postCreate(host: Int, name: String, date: String): CreatePOSTResponse {
        val body = CreatePOSTBody(host = host, name = name, date = date)
        return withContext(Dispatchers.IO) {
            try {
                Backend.retrofitService.postCreate(body)
            } catch (e: Exception) {
                CreatePOSTResponse(false, null)
            }
        }
    }

    suspend fun postGroups(user: Int, group: Int): GroupsPOSTResponse {
        val body = UserGroupBody(user = user, group = group)
        return withContext(Dispatchers.IO) {
            try {
                Backend.retrofitService.postGroups(body)
            } catch (e: Exception) {
                GroupsPOSTResponse(false, null)
            }
        }
    }

    suspend fun postSurvey(
        user: Int,
        locationX: Float,
        locationY: Float,
        price: Int,
        distance: Int,
        time: Int,
        preferences: List<Int>
    ): SurveyPOSTResponse {
        val body = SurveyPOSTBody(
            user = user,
            locationX = locationX,
            locationY = locationY,
            price = price,
            distance = distance,
            time = time,
            preferences = preferences
        )
        return withContext(Dispatchers.IO) {
            try {
                Backend.retrofitService.postSurvey(body)
            } catch (e: Exception) {
                SurveyPOSTResponse(false, null)
            }
        }
    }

    suspend fun postVote(restaurants: List<Int>): VotePOSTResponse {
        val body = VotePOSTBody(restaurants = restaurants)
        return withContext(Dispatchers.IO) {
            try {
                Backend.retrofitService.postVote(body)
            } catch (e: Exception) {
                VotePOSTResponse(false, null)
            }
        }
    }

    suspend fun deleteGroups(user: Int, group: Int): GroupsDELETEResponse {
        val body = UserGroupBody(user = user, group = group)
        return withContext(Dispatchers.IO) {
            try {
                Backend.retrofitService.deleteGroups(body)
            } catch (e: Exception) {
                GroupsDELETEResponse(false, null)
            }
        }
    }

    suspend fun postGroupsJoin(user: Int, group: Int): GroupsJoinPOSTResponse {
        val body = UserGroupBody(user = user, group = group)
        return withContext(Dispatchers.IO) {
            try {
                Backend.retrofitService.postGroupsJoin(body)
            } catch (e: Exception) {
                GroupsJoinPOSTResponse(false, null)
            }
        }
    }

    suspend fun deleteUsers(usr: String, pwd: String): UsersDELETEResponse {
        val body = UserPwdBody(usr = usr, pwd = pwd)
        return withContext(Dispatchers.IO) {
            try {
                Backend.retrofitService.deleteUsers(body)
            } catch (e: Exception) {
                UsersDELETEResponse(false, null)
            }
        }
    }

}