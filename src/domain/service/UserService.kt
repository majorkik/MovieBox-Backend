package com.moviebox.backend.domain.service

import com.moviebox.backend.models.User

interface UserService {
    fun create(user: User): User

    fun authenticate(user: User): User

    fun getByEmail(email: String): User
}
