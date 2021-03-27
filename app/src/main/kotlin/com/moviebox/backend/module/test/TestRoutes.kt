package com.moviebox.backend.module

import io.ktor.response.*
import io.ktor.routing.*

fun Routing.test() {
    get("/") {
        context.respondText("Test")
    }
}
