package com.example.talkative.repository

import com.example.talkative.DataorException.DataorException
import com.example.talkative.model.LikeCommentResponse.LikeCommentResponse
import com.example.talkative.network.network
import javax.inject.Inject

class LikeCommentRepository @Inject constructor(private val network: network){

    suspend fun likeComment(commentId: String): DataorException<LikeCommentResponse>{
        return try {
            DataorException.Loading(data = true)
            val response = network.LikeComment(commentId = commentId)
            DataorException.Success(data = response)
        }catch (ex: Exception){
            DataorException.Error(message = ex.message.toString())
        }
    }

}