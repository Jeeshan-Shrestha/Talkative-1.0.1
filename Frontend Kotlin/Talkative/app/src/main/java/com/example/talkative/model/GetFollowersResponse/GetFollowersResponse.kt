package com.example.talkative.model.GetFollowersResponse

data class GetFollowersResponse(
    val message: List<Message>,
    val success: Boolean
)