package com.example.talkative.repository

import android.util.Log
import com.example.talkative.DataorException.DataorException
import com.example.talkative.model.LoginRequest.LoginRequest
import com.example.talkative.model.LoginResponse.LoginResponse
import com.example.talkative.model.SignupResponse.SignupResponse
import com.example.talkative.model.signupRequest.SignupRequest
import com.example.talkative.network.network
import javax.inject.Inject

class SignupRepository @Inject constructor(private val network: network) {
    suspend fun  SignupUser(email:String,username:String,password:String): DataorException<SignupResponse>{

        return try {
            DataorException.Loading(data = true)
            val response = network.SignupUser(SignupRequest(gmail = email,username=username, password = password))
            DataorException.Success(data = response)
        } catch (e: retrofit2.HttpException) {
            // Handle HTTP error responses and parse the error body
            val errorBody = e.response()?.errorBody()
            val errorMessage = errorBody?.string() // Get raw error JSON

            // Try parsing the error JSON into LoginResponse
            val parsedError = try {
                val gson = com.google.gson.Gson()
                gson.fromJson(errorMessage, LoginResponse::class.java)
            }
            catch (parseException: Exception) {
                null
            }
            //Log.d("april", "repository catch block ${e.message()} | parsedError: $parsedError")

            // Return parsed error or fallback to the generic exception message
            if (parsedError != null) {
                DataorException.Error(message = parsedError.message ?: "Unknown error")
            } else {
                DataorException.Error(message = e.message ?: "HTTP ${e.code()}")
            }

        } catch (e: Exception) {
            // Handle other exceptions
           // Log.d("april", "repository catch block ${e.message} ")
            DataorException.Error(message = e.message.toString())
        }
    }
}