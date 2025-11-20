package com.example.talkative.model.OwnProfileResponse

import kotlinx.serialization.Serializable

data class Message(
    val avatar: String?,
    val bio: String?,
    val coverPhoto: String?,
    val displayName: String?,        // backend may send null
    val followersCount: Int,
    val following: Boolean,
    val followingCount: Int,
    val joinDate: String?,           // nullable
    val numberOfPosts: Int?,
    val posts: List<Post>?,
    val username: String?
)
