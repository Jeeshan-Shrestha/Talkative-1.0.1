package com.example.talkative.model.viewOthersProfile

data class Comment (
    val commentText: String,
    val postOwnerUsername:String,
    val postId: String,
    val liked: Boolean,
    val numberOfLikes: Int,
    val commentedBy: String
)
