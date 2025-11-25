package com.example.talkative.repository

import com.example.talkative.DataorException.DataorException
import com.example.talkative.model.GetAllCommentResponse.Comment
import com.example.talkative.network.network
import javax.inject.Inject

class GetAllCommentRepository @Inject constructor(private val network: network){

    suspend fun  getAllComment(postId:String): DataorException<List<Comment>>{
        return try{
            DataorException.Loading(data = true)

            val response = network.getAllComment(postId)

            DataorException.Success(data = response)
        }catch (ex: Exception){
            DataorException.Error(message = ex.message.toString())
        }
    }
}