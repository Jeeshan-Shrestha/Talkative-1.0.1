package com.example.talkative.repository

import android.content.Context
import android.net.Uri
import com.example.talkative.DataorException.DataorException
import com.example.talkative.model.CreatePostResponse.CreatePostResponse
import com.example.talkative.network.network
import com.example.talkative.utils.FileUtils
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

class CreatePostRepository @Inject constructor(private val network: network){

    suspend fun CreatePost(content: String,
                           context: Context,
                           imageUri: Uri): DataorException<CreatePostResponse>{

        return try{
            DataorException.Loading(data = true)

            val file = FileUtils.getFileFromUri(context = context,uri=imageUri, partname = "image")
            //partname=image must match with backend controller
            //@RequestPart("caption") String caption, @RequestPart("image") MultipartFile file

            //converting caption to requestbody
            val captionBody = content.toRequestBody("text/plain".toMediaTypeOrNull())
            //calling api
            val response = network.PostContent(
                caption = captionBody,
                image = file
            )

            DataorException.Success(response)


        }catch (ex: Exception){
            DataorException.Error(message=ex.message.toString())
        }
    }

}