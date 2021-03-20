package com.moviebox.backend.web.controllers

import com.moviebox.backend.domain.service.UserService
import com.moviebox.backend.models.User
import com.moviebox.backend.models.isValidLogin
import com.moviebox.backend.models.isValidRegister
import io.ktor.application.*
import io.ktor.request.*
import io.ktor.response.*

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