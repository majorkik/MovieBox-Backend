package com.moviebox.backend.module.test

import io.ktor.routing.Routing
import io.ktor.routing.get
import io.ktor.response.respondText

fun Routing.test() {
    get("/") {
        context.respondText("Test")
    }
}
