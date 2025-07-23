package com.example.talkative.repository

import android.util.Log
import com.example.talkative.dataexception.DataOrException
import com.example.talkative.model.selectuserResponse.SelectUserResponse
import com.example.talkative.network.Net
import javax.inject.Inject

class SelectUserRepository @Inject constructor(private  val network:Net){
    suspend fun getUser():DataOrException<SelectUserResponse>{
        return try {
            DataOrException.Loading(data = true)
            val response = network.SelectUser()
            Log.d("april", "getUser: ${response} ")
            DataOrException.Success(data = response)
        }catch (e:Exception){
            Log.d("april", "We got an error ${e}")
            DataOrException.Error(message = e.message.toString())
        }
    }
}