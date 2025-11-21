package com.example.talkative.repository

import android.content.Context
import android.net.Uri
import android.util.Log
import com.example.talkative.DataorException.DataorException
import com.example.talkative.model.EditProfileResponse.EditProfileResponse
import com.example.talkative.network.network
import com.example.talkative.utils.FileUtils
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

class EditProfileRepository @Inject constructor(private val network: network) {

    suspend fun CreatePost(
        displayName: String,
        context: Context,
        bio: String,
        avatar: Uri?,
        coverPhoto: Uri?
    ): DataorException<EditProfileResponse> {

        return try {
            DataorException.Loading(data = true)

            val file1 = avatar?.let { FileUtils.getFileFromUri(context, it, "avatar") }
            val file2 = coverPhoto?.let { FileUtils.getFileFromUri(context, it, "coverPhoto") }

            //partname=image must match with backend controller
            //@RequestPart("caption") String caption, @RequestPart("image") MultipartFile file

            //converting caption to requestbody
            val disName = displayName.toRequestBody("text/plain".toMediaTypeOrNull())
            val Bio = bio.toRequestBody("text/plain".toMediaTypeOrNull())
            //calling api
            val response = network.EditProfile(
                displayName = disName,
                Bio = Bio,
                avatar = file1,
                coverPhoto = file2
            )

            DataorException.Success(response)


        } catch (ex: Exception) {
            when (ex) {
                is retrofit2.HttpException -> {
                    val code = ex.code()
                    val errorBody =
                        ex.response()?.errorBody()?.string()  // <--- get server error message
                    Log.d("pipe", "HTTP Error ($code): $errorBody")
                    DataorException.Error(message = errorBody ?: "HTTP Error $code")
                }

                is java.net.SocketTimeoutException -> {
                    Log.d("pipe", "Timeout: ${ex.message}")
                    DataorException.Error(message = "Timeout: ${ex.message}")
                }

                is java.io.IOException -> {
                    Log.d("pipe", "Network IO Error: ${ex.message}")
                    DataorException.Error(message = "Network IO Error: ${ex.message}")
                }

                else -> {
                    Log.d("pipe", "Unknown Error: ${ex.message}")
                    DataorException.Error(message = ex.message ?: "Unknown error")
                }
            }
        }
    }
}

