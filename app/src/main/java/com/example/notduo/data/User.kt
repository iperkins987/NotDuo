package com.example.notduo.data

data class User(
    val firstName: String = "",
    val lastName: String = "",
    val username: String = "",
    val passwordHash: String = "",
    val deviceToken: String = "",
    val authToken: String = "NO_TOKEN",
    val responseToken: String = "NO_TOKEN"
)
