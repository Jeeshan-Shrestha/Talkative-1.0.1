package com.example.talkative.repository

import com.example.talkative.DataorException.DataorException
import com.example.talkative.model.AddCommentResponse.AddCommentRequest
import com.example.talkative.model.AddCommentResponse.AddCommentResponse
import com.example.talkative.network.network
import javax.inject.Inject

class AddCommentRepository @Inject constructor(private val network: network){

    suspend fun AddComment(postId:String,commentText: String): DataorException<AddCommentResponse>{
        return try {
            DataorException.Loading(data = true)
            val response = network.AddComment(AddCommentRequest = AddCommentRequest(
                postId = postId,
                commentText = commentText))
            DataorException.Success(data = response)
        }catch (ex: Exception){
            DataorException.Error(message = ex.message.toString())
        }
    }

}