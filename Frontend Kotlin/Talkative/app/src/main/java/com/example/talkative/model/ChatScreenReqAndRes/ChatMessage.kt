package com.example.talkative.model.ChatScreenReqAndRes

data class ChatMessage(
    val sender:String, //who has sent the message
    val receiver: String,//who is receiving the message
    val type:String,
    val content: String //actual message
)
