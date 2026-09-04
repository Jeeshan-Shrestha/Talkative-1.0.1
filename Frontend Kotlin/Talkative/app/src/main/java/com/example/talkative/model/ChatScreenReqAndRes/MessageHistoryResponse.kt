package com.example.talkative.model.ChatScreenReqAndRes

//Response of GET  message?receiver=<username>
data class MessageHistoryResponse(
    val success: Boolean = false,
    val message: List<ChatHistoryItem> = emptyList()
)

data class ChatHistoryItem(
    val timestamp: String? = null, //ISO date time from backend, can be null for old messages
    val sender: String = "",       //who sent the message
    val receiver: String = "",     //who received the message
    val type: String = "",
    val content: String = ""       //actual message text
)
