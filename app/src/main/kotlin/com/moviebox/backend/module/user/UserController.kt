package com.moviebox.backend.module.user

import com.moviebox.backend.domain.model.User
import com.moviebox.backend.domain.model.isValidLogin
import com.moviebox.backend.domain.model.isValidRegister
import com.moviebox.backend.domain.services.user.UserService
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*

class UserController(private val service: UserService) {

    suspend fun register(call: ApplicationCall) {
        val userRequest = call.receiveOrNull<User>()

        service.create(userRequest.isValidRegister()).apply {
            call.respond(this)
        }
    }

    suspend fun login(call: ApplicationCall) {
        val userRequest = call.receiveOrNull<User>()

        service.authenticate(userRequest.isValidLogin()).apply {
            call.respond(this)
        }
    }

    fun getUserByEmail(email: String?): User {
        return email.let {
            require(!it.isNullOrBlank()) { "User not logged or with invalid email." }
            service.getByEmail(it)
        }
    }
}
