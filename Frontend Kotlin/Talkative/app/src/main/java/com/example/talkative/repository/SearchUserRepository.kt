package com.example.talkative.repository

import android.util.Log
import com.example.talkative.DataorException.DataorException
import com.example.talkative.model.LoginResponse.LoginResponse
import com.example.talkative.model.SearchResponse.SearchResponse
import com.example.talkative.model.SignupResponse.SignupResponse
import com.example.talkative.model.signupRequest.SignupRequest
import com.example.talkative.network.network
import javax.inject.Inject

class SearchUserRepository @Inject constructor(private val network: network) {
    suspend fun  SearchUser(username: String): DataorException<SearchResponse>{

        return try {
            DataorException.Loading(data = true)
            val response = network.SearchUser(username = username)
            Log.d("boka", "${response}")
            DataorException.Success(data = response)

        }catch (e: Exception) {
            // Handle other exceptions
             Log.d("boka", "repository catch block ${e.message} ")
            DataorException.Error(message = e.message.toString())
        }
    }
}
