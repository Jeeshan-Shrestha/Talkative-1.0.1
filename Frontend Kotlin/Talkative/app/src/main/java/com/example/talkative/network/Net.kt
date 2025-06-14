package com.example.talkative.network

import com.example.talkative.model.loginRequest.LoginRequest
import com.example.talkative.model.loginRequest.LoginResponse
import com.example.talkative.model.registerRequest.RegisterRequest
import com.example.talkative.model.registerRequest.RegisterResponse
import com.example.talkative.utils.Constants
import retrofit2.http.Body
import retrofit2.http.POST
import javax.inject.Singleton

@Singleton
interface Net {

    //For Login Page
    @POST(value = Constants.Login_EndPoint)
    suspend fun LoginUser(
        @Body LoginRequest:LoginRequest):LoginResponse

    //For register page
    @POST(value=Constants.SignUp_EndPoint)
    suspend fun RegisterUser(
        @Body RegisterRequest:RegisterRequest):RegisterResponse

}