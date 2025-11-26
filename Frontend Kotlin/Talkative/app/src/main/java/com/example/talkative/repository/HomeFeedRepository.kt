package com.example.talkative.repository

import com.example.talkative.DataorException.DataorException
import com.example.talkative.model.GetHomeFeedResponse.HomeFeedResponse
import com.example.talkative.network.network
import javax.inject.Inject

class HomeFeedRepository @Inject constructor(private val network: network) {

    suspend fun HomeFeed(): DataorException<HomeFeedResponse>{
        return try {
            DataorException.Loading(data = true)
            val response= network.HomeFeed()
            DataorException.Success(data = response)
        }catch (ex: Exception){
            DataorException.Error(message = ex.message.toString())
        }
    }

}