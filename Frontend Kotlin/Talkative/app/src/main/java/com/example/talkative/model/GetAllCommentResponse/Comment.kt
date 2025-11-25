package com.example.talkative.model.GetAllCommentResponse

data class Comment(
    val commentId: String,
    val commentText: String,
    val liked: Boolean,
    val numberOfLikes: Int,
    val commentedBy: String,
    val avatar: String?
)
