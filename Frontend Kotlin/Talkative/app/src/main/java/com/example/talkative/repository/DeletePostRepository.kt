package com.example.talkative.repository

import com.example.talkative.DataorException.DataorException
import com.example.talkative.model.DeletePostResponse.DeletePostResponse
import com.example.talkative.network.network
import javax.inject.Inject

class DeletePostRepository @Inject constructor(private val network: network){

    suspend fun deletePost(postId: String): DataorException<DeletePostResponse>{
        return try{
            DataorException.Loading(data = true)

            val response = network.deletePost(postId)

            DataorException.Success(data = response)
        }catch (ex: Exception){
            DataorException.Error(message = ex.message.toString())
        }
    }
}