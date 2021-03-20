package com.moviebox.backend.models

import com.moviebox.backend.extensions.isEmailValid
import com.moviebox.backend.models.exception.ErrorException
import io.ktor.auth.*

data class User(
    val id: Long? = null,
    val username: String,
    val email: String,
    val password: String? = null,
    val avatarUrl: String? = null,
    val quot: String? = null,
    val token: String? = null
) : Principal

fun User?.isValidLogin(): User {
    if (this != null && username.isNotBlank() && !password.isNullOrBlank()) {
        return this
    }

    throw ErrorException.InvalidAuthorizationData
}

fun User?.isValidRegister(): User {
    if (this != null && username.isNotBlank() && !password.isNullOrBlank() && email.isEmailValid()) {
        return this
    }
    throw ErrorException.InvalidAuthorizationData
}