package com.moviebox.backend.models

data class UserDataModel(
    val id: Long,
    val username: String,
    val email: String,
    val password: String,
    val avatarUrl: String? = null,
    val quot: String? = null
)