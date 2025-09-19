package com.example.talkative.repository

import android.util.Log
import com.example.talkative.DataorException.DataorException
import com.example.talkative.model.FollowUnfollowResponse.FollowUnFollowResponse
import com.example.talkative.model.SearchResponse.SearchResponse
import com.example.talkative.network.network
import javax.inject.Inject

class FollowUnFollowRepository @Inject constructor(private val network: network) {
    suspend fun  FollowUnFollowUser(username: String): DataorException<FollowUnFollowResponse>{

        return try {
            DataorException.Loading(data = true)
            val response = network.FollowUnfollowUser(username = username)
            DataorException.Success(data = response)

        }catch (e: Exception) {
            // Handle other exceptions
            DataorException.Error(message = e.message.toString())
        }
    }
}
