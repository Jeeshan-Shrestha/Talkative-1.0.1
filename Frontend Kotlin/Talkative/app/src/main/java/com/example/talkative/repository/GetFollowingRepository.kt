package com.example.talkative.repository

import com.example.talkative.DataorException.DataorException
import com.example.talkative.model.GetFollowersResponse.GetFollowersResponse
import com.example.talkative.network.network
import javax.inject.Inject

class GetFollowingRepository @Inject constructor(private val network: network) {
    suspend fun getFollowing(username: String): DataorException<GetFollowersResponse>{
        return try {
            DataorException.Loading(data = true)
            val response = network.getFollowing(username = username)
            DataorException.Success(data = response)
        }catch (ex: Exception){
            DataorException.Error(message = ex.message.toString())
        }
    }
}