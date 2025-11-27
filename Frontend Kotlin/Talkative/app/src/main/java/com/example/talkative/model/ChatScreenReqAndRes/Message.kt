package com.example.talkative.model.ChatScreenReqAndRes

sealed class Message {
    data class Received(val sender: String, val content: String) : Message()
    data class Sent(val content: String) : Message()
}