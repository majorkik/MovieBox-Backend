package com.moviebox.backend

import com.moviebox.backend.data.dataModule
import com.moviebox.backend.domain.domainModule
import com.moviebox.backend.domain.encrypt.JwtProvider
import com.moviebox.backend.domain.model.ErrorException
import com.moviebox.backend.domain.model.ErrorMessage.Companion.EmailAlreadyExists
import com.moviebox.backend.domain.model.ErrorMessage.Companion.InvalidAuthorizationData
import com.moviebox.backend.domain.model.ErrorMessage.Companion.InvalidPassword
import com.moviebox.backend.domain.model.ErrorMessage.Companion.UserIsNotFound
import com.moviebox.backend.module.applicationModule
import com.moviebox.backend.module.test.test
import com.moviebox.backend.module.user.UserController
import com.moviebox.backend.module.user.users
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.callloging.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.util.*
import kotlinx.serialization.json.Json
import org.koin.ktor.ext.inject
import org.koin.ktor.plugin.Koin
import org.slf4j.event.Level

fun main(args: Array<String>) {
    val main = EngineMain.main(args)
    return main
}

@Suppress("unused")
@JvmOverloads
fun Application.module(testing: Boolean = false) {
    setupKoin()
    setupNegotiation()
    setupLogging()
    setupCors()
    setupExceptions()

    setupAuthentication()

    routing {
        test()
        users()
    }
}

/**
 * Метод для инициализации сериализации
 */
private fun Application.setupNegotiation() {
    install(ContentNegotiation) {
        json(
            Json {
                prettyPrint = true
                isLenient = true
            }
        )
    }
}

/**
 * Метод для инициализации логирования
 */
private fun Application.setupLogging() {
    install(CallLogging) {
        level = Level.INFO
        filter { call -> call.request.path().startsWith("/") }
    }
}

/**
 * Метод для инициализации Koin
 */

private fun Application.setupKoin() {
    install(Koin) {
        modules(applicationModule, dataModule, domainModule)
    }
}

/**
 * Метод для инициализации CORS
 */
private fun Application.setupCors() {
    install(CORS) {
        allowMethod(HttpMethod.Options)
        allowMethod(HttpMethod.Put)
        allowMethod(HttpMethod.Delete)
        allowMethod(HttpMethod.Patch)
    }
}

/**
 * Метод для настройки аутентификации
 */
private fun Application.setupAuthentication() {
    val controller: UserController by inject()

    install(Authentication) {
        jwt {
            verifier(JwtProvider.verifier)
            authSchemes("Token")
            validate { credential ->
                if (credential.payload.audience.contains(JwtProvider.audience)) {
                    controller.getUserByEmail(credential.payload.claims["email"]?.asString())
                } else {
                    null
                }
            }
        }
    }
}

private fun Application.setupExceptions() {
    install(StatusPages) {
        exception<ErrorException.InvalidAuthorizationData> { call, cause ->
            call.respond(HttpStatusCode.Unauthorized, InvalidAuthorizationData)
        }

        exception<ErrorException.EmailAlreadyExists> { call, cause ->
            call.respond(HttpStatusCode.Unauthorized, EmailAlreadyExists)
        }

        exception<ErrorException.InvalidPassword> { call, cause ->
            call.respond(HttpStatusCode.Unauthorized, InvalidPassword)
        }

        exception<ErrorException.UserIsNotFound> { call, cause ->
            call.respond(HttpStatusCode.NotFound, UserIsNotFound)
        }
    }
}
