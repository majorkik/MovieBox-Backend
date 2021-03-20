package com.moviebox.backend.models

data class User(
    val username: String,
    val email: String,
    val password: String,
    val avatarUrl: String? = null,
    val quot: String? = null
)