package com.example.talkative.model.OwnProfileResponse

data class Message(
    val caption: String,
    val comments: Any,
    val date: String,
    val id: String,
    val imageUrl: String,
    val likes: Any,
    val postAccess: Any,
    val videoUrl: Any
)