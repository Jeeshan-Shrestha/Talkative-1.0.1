package com.example.talkative.model.OwnProfileResponse

import com.example.talkative.model.viewOthersProfile.Comment

data class Post(
    val caption: String?,            // backend might send null
    val comments: List<Comment>?,    // backend sends null instead of list
    val numberOfComments:Int?,
    val displayName: String?,        // nullable
    val id: String,
    val imageUrl: String?,
    val liked: Boolean,
    val likes: Int,
    val postAccess: String?,
    val postDate: String?,           // nullable
    val username: String?,           // nullable
    val videoUrl: String?,
    val avatar:String?
)
