package com.moviebox.backend.module.user

import io.ktor.routing.Routing
import io.ktor.routing.route
import io.ktor.routing.post
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
