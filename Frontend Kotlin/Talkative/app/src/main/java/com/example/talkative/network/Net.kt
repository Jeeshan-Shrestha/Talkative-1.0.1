package com.example.talkative.network

import com.example.talkative.utils.Constants
import retrofit2.http.Body
import retrofit2.http.POST
import javax.inject.Singleton

@Singleton
interface Net {

    @POST(value = Constants.Login_EndPoint)
    suspend fun LoginUser(
        @Body
    )

}