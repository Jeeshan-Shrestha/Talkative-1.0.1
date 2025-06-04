package com.example.talkative.model

sealed class Message{
    data class Recieved(val text:String):Message()
    data class Sent(val text:String):Message()
}