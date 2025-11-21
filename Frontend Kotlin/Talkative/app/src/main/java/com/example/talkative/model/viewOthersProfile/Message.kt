package com.example.talkative.model.viewOthersProfile

import com.example.talkative.model.OwnProfileResponse.Post

data class Message(
    val avatar: String,
    val bio: String?,
    val coverPhoto: String?,
    val displayName: String?,
    val following: Boolean,
    val followersCount: Int,
    val followingCount: Int,
    val joinDate: String,
    val numberOfPosts: Int,
    val posts: List<Post>?,
    val username: String
)