package com.example.talkative.model

import kotlinx.serialization.Serializable

@Serializable
data class MessageResponse(
    val sender:String,
    val receiver:String,
    val content:String,
    val type:String
)
//okHttp .........
//retrofit .....

