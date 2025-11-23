package com.example.talkative.model.GetFollowersResponse

data class Message(
    val avatar: String?,
    val displayName: String,
    val following: Boolean,
    val ownProfile: Boolean,
    val username: String
)