package com.example.talkative.repository

import com.example.talkative.DataorException.DataorException
import com.example.talkative.model.DeleteComment.DeleteCommentResponse
import com.example.talkative.network.network
import javax.inject.Inject

class DeleteCommentRepository @Inject constructor(private val network: network) {

    suspend fun DeleteComment(commentId: String): DataorException<DeleteCommentResponse>{

        return try{
            DataorException.Loading(data = true)
            val response = network.DeleteComment(commentId = commentId)
            DataorException.Success(data = response)
        }catch (ex: Exception){
            DataorException.Error(message = ex.message.toString())
        }
    }

}