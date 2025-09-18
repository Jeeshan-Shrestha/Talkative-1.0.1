package com.example.talkative.model.LoginRequest

data class LoginRequest(
    val password: String,
    val usernameOrGmail: String
)