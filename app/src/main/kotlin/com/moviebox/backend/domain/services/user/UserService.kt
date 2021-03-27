package com.moviebox.backend.domain.services.user

import com.moviebox.backend.domain.model.User

interface UserService {
    fun create(user: User): User

    fun authenticate(user: User): User

    fun getByEmail(email: String): User
}
