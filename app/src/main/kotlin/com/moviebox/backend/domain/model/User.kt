package com.moviebox.backend.domain.model

import com.moviebox.backend.domain.extension.isEmailValid
import io.ktor.auth.Principal

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

@Suppress("ComplexCondition")
fun User?.isValidRegister(): User {
    if (this != null && username.isNotBlank() && !password.isNullOrBlank() && email.isEmailValid()) {
        return this
    }
    throw ErrorException.InvalidAuthorizationData
}
