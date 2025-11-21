package com.example.talkative.repository

import com.example.talkative.DataorException.DataorException
import com.example.talkative.model.PostLikeResponse.PostLikeResponse
import com.example.talkative.network.network
import javax.inject.Inject

class LikedUnlikeRepository @Inject constructor(private val network: network){

    suspend fun LikeunLike(id: String,username: String): DataorException<PostLikeResponse>{
        return try {
            DataorException.Loading(data = true)
            val response = network.LikeUnLike(id = id, likedUsername = username)
            DataorException.Success(data = response)
        }catch (ex: Exception){
            DataorException.Error(message = ex.message)
        }
    }

}