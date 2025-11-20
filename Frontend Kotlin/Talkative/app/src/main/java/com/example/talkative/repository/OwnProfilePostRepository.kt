package com.example.talkative.repository

import android.util.Log
import com.example.talkative.DataorException.DataorException
import com.example.talkative.model.FollowUnfollowResponse.FollowUnFollowResponse
import com.example.talkative.model.OwnProfileResponse.OwnProfileResponse
import com.example.talkative.network.network
import javax.inject.Inject

class OwnProfilePostRepository @Inject constructor(private val network: network) {
    suspend fun  OwnProfilePost(): DataorException<OwnProfileResponse>{
        return try {
            DataorException.Loading(data = true)
            val response = network.OwnProfilePost()
            Log.d("pikachu", "inside repo ${response} ")
            DataorException.Success(data = response)

        }catch (e: Exception) {
            // Handle other exceptions
            Log.d("pikachu", "inside catch block ${e} ")
            DataorException.Error(message = e.message.toString())
        }
    }
}
