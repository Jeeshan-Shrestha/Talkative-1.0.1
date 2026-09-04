package com.example.talkative.model.ChatScreenReqAndRes

sealed class Message {
    abstract val timestamp: String?

    data class Received(
        val sender: String,
        val content: String,
        override val timestamp: String? = null
    ) : Message()

    data class Sent(
        val content: String,
        override val timestamp: String? = null
    ) : Message()
}
