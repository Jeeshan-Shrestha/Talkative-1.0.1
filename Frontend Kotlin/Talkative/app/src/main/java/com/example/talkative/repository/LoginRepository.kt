package com.example.talkative.repository

import android.util.Log
import com.example.talkative.dataexception.DataOrException
import com.example.talkative.model.loginRequest.LoginRequest
import com.example.talkative.model.loginRequest.LoginResponse
import com.example.talkative.network.Net
import javax.inject.Inject

class LoginRepository @Inject constructor(
    private val network:Net){

    suspend fun loginUser(username:String,Password:String):DataOrException<LoginResponse>{
        return try {
            DataOrException.Loading(data = true)

            val response = network.LoginUser(LoginRequest(username=username, password = Password))


            Log.d("April", "loginUser:  $response")

            DataOrException.Success(data = response)

        }catch (e: retrofit2.HttpException) {
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
            Log.d("boka", "repository catch block ${e.message()} | parsedError: $parsedError")

            // Return parsed error or fallback to the generic exception message
            if (parsedError != null) {
                DataOrException.Error(
                    message = parsedError.message ?: "HTTP ${e.code()}",
                    data = parsedError
                )
            } else {
                DataOrException.Error(message = e.message ?: "HTTP ${e.code()}")
            }

        } catch (e: Exception) {
            // Handle other exceptions
            Log.d("boka", "repository catch block ${e.message} ")
            DataOrException.Error(message = e.message.toString())
        }
    }

}