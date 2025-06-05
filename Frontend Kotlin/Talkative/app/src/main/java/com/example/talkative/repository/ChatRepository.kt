package com.example.talkative.repository

import com.example.talkative.network.WebSocketManager
import com.example.talkative.utils.Constants
import javax.inject.Inject

class ChatRepository @Inject constructor(private val network:WebSocketManager){
    fun connectSocket(username:String){
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
