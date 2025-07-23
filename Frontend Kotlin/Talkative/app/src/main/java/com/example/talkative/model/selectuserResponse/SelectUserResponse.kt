package com.example.talkative.model.selectuserResponse

data class SelectUserResponse(
    val success:Boolean,
    val message:List<Message>
)