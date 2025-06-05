package com.example.talkative.model

import kotlinx.serialization.Serializable

@Serializable
data class MessageResponse(
    val sender:String,
    val message:String,
    val type:String
)
//okHttp .........
//retrofit .....

