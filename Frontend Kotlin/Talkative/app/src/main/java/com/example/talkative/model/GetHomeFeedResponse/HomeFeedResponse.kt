package com.example.talkative.model.GetHomeFeedResponse

import com.example.talkative.model.OwnProfileResponse.Post


data class HomeFeedResponse(
    val message: List<Post>,
    val success: Boolean
)