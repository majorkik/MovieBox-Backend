package com.moviebox.backend.domain.repository

import com.moviebox.backend.domain.model.User

interface UserRepository {

    fun create(user: User): Long?

    fun getUserByEmail(email: String): User?

    fun getUserByUsername(username: String): User?
}
