package com.example.talkative.repository

import android.util.Log
import com.example.talkative.network.WebSocketManager
import com.example.talkative.utils.Constants
import okhttp3.WebSocket
import java.net.URLEncoder
import javax.inject.Inject

class ChatRepository @Inject constructor(private val network:WebSocketManager){
    fun connectSocket(username:String){
//        val encodedUsername = URLEncoder.encode(username, "UTF-8")
        network.connect("${Constants.BaseUrl}?username=${username}")
    }
    fun observeMessages()=network.messages

    fun observeStatus()=network.status

    fun sendMessage(Text:String){
        network.sendMessage(Text)
    }

    fun closeSocket(){
        network.close()
    }
}
