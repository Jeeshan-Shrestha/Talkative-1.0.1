package com.example.talkative.model.sendmsgRequest

import kotlinx.serialization.Serializable

@Serializable
data class SendmsgReq(
    val content:String,
    val receiver:String
)
