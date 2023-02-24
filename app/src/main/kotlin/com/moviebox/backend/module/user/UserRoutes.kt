package com.moviebox.backend.module.user

import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Routing.users() {
    val controller: UserController by inject()

    route("/users") {
        post {
            controller.register(context)
        }
        post("login") {
            controller.login(context)
        }
    }
}
