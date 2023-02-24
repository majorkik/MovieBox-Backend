package com.moviebox.backend.module.test

import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Routing.test() {
    get("/") {
        context.respondText("Test")
    }
}
