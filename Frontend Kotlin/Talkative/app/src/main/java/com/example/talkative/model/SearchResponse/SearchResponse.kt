package com.example.talkative.model.SearchResponse

data class SearchResponse(
    val message: List<Message>,
    val success: Boolean
)
