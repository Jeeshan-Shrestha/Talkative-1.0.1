package com.example.talkative.repository

import com.example.talkative.DataorException.DataorException
import com.example.talkative.model.mySelf.GetMyselfResponse
import com.example.talkative.network.network
import javax.inject.Inject

class GetMySelfRepository @Inject constructor(private val network: network){

    suspend fun getMySelf(): DataorException<GetMyselfResponse>{
        return try {
            DataorException.Loading(data = true)
            val response = network.GetmySelf()
            DataorException.Success(data = response)
        }catch (ex: Exception){
            DataorException.Error(message = ex.message.toString())
        }
    }

}