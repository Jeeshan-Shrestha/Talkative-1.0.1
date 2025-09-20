package com.example.talkative.utils

import android.content.Context
import android.net.Uri
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.http.Multipart
import java.io.File
import java.io.FileOutputStream
import java.net.URI

object FileUtils {

    fun getFileFromUri(context: Context,uri: Uri,partname: String): MultipartBody.Part{

        val inputStream = context.contentResolver.openInputStream(uri)!!
        val file = File(context.cacheDir, "upload_${System.currentTimeMillis()}.jpg")
        val outputStream = FileOutputStream(file)
        inputStream.copyTo(outputStream)
        inputStream.close()
        outputStream.close()


        // detecting mimeType from contentResolver
        val mimeType = context.contentResolver.getType(uri) ?: "image/jpeg"
        val requestFile = file.asRequestBody(mimeType.toMediaTypeOrNull())

        return MultipartBody.Part.createFormData(partname, file.name, requestFile)

    }

}