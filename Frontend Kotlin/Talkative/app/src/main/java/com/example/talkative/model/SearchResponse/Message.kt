package com.example.talkative.model.SearchResponse

data class Message(
    val avatar: String?,
    val bio: String?,
    val displayName: String,
    val followersCount: Int,
    val followingCount: Int,
    val isFollowing: Boolean,
    val numberOfPosts: Int,
    val username: String
)