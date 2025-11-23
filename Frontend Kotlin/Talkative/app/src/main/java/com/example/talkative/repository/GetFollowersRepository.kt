package com.example.talkative.repository

import com.example.talkative.DataorException.DataorException
import com.example.talkative.model.GetFollowersResponse.GetFollowersResponse
import com.example.talkative.network.network
import javax.inject.Inject

class GetFollowersRepository @Inject constructor(private val network: network){

    suspend fun getFollowers(username: String): DataorException<GetFollowersResponse>{
        return try {
            DataorException.Loading(data = true)
            val response = network.getFollowers(username = username)
            DataorException.Success(data = response)
        }catch (ex: Exception){
            DataorException.Error(message = ex.message.toString())
        }
    }

}