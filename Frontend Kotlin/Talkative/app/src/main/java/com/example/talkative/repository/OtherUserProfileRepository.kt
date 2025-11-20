package com.example.talkative.repository

import android.util.Log
import com.example.talkative.DataorException.DataorException
import com.example.talkative.model.viewOthersProfile.OtherUsreProfileResponse
import com.example.talkative.network.network
import javax.inject.Inject

class OtherUserProfileRepository @Inject constructor(private val network: network){

    suspend fun OtherUserProfile(username: String): DataorException<OtherUsreProfileResponse>{
       return try {
            DataorException.Loading(data = true)
           Log.d("akriti", "inside repostiory")
            val response = network.OtherUserProfile(username = username)
            DataorException.Success(data = response)
        }catch (ex: Exception){
           Log.d("akriti", "OtherUserProfile: ${ex.message} ")
           DataorException.Error(message = ex.message.toString())
        }
    }

}