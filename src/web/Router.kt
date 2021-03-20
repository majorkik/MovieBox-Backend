package com.moviebox.backend.web

import com.moviebox.backend.web.controllers.UserController
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*

fun Routing.test() {
    get("/") {
        context.respondText("Test")
    }
}

fun Routing.users(controller: UserController) {
    route("/users") {
        post {
            controller.register(context)
        }
        post("login") {
            controller.login(context)
        }
    }
}
