package com.example.fundraisinghome.model

data class User(
    val userId: String, // also used for userId
    val username: String,
    val password: String // Note: Encrypt or hash the password before storing
)
