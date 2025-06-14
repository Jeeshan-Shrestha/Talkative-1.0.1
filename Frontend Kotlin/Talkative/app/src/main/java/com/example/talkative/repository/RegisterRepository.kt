package com.example.talkative.repository

import android.provider.ContactsContract.Data
import com.example.talkative.dataexception.DataOrException
import com.example.talkative.model.registerRequest.RegisterRequest
import com.example.talkative.model.registerRequest.RegisterResponse
import com.example.talkative.network.Net
import javax.inject.Inject

class RegisterRepository @Inject constructor(private val network:Net) {
    suspend fun RegisterUser(username:String, password:String):DataOrException<RegisterResponse>{
        return try{
            DataOrException.Loading(data = true)
            val response = network.RegisterUser(RegisterRequest(username=username,password=password))
            DataOrException.Success(data = response)
        }catch(e: retrofit2.HttpException){
            val errorBody=e.response()?.errorBody()
            val errorMessage= errorBody?.string()

            val parsedError=try {
                val gson = com.google.gson.Gson()
                gson.fromJson(errorMessage,RegisterResponse::class.java)
            }catch (parseException:Exception){
                null
            }

            if(parsedError !=null){
                DataOrException.Error(
                    message = parsedError.message ?:"Http error",
                    data = parsedError
                )
            }else{
                DataOrException.Error(message = e.message?: "Http error ${e.code()}")
            }
        }catch (e:Exception){
            DataOrException.Error(message = e.message.toString())
        }
    }
}