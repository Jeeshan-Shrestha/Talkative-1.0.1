package com.example.talkative.model.GetHomeFeedResponse

data class Message(
    val avatar: String?,
    val caption: String,
    val displayName: String,
    val id: String,
    val imageUrl: String,
    val liked: Boolean,
    val likes: Int,
    val numberOfComments: Int,
    val postDate: String,
    val username: String
)