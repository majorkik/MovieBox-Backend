package com.moviebox.backend

import com.moviebox.backend.db.DatabaseFactory
import com.moviebox.backend.di.applicationModule
import com.moviebox.backend.models.exception.ErrorException
import com.moviebox.backend.models.exception.ErrorMessage.Companion.EmailAlreadyExists
import com.moviebox.backend.models.exception.ErrorMessage.Companion.InvalidAuthorizationData
import com.moviebox.backend.models.exception.ErrorMessage.Companion.InvalidPassword
import com.moviebox.backend.models.exception.ErrorMessage.Companion.UserIsNotFound
import com.moviebox.backend.utils.*
import com.moviebox.backend.web.controllers.UserController
import com.moviebox.backend.web.test
import com.moviebox.backend.web.users
import com.viartemev.ktor.flyway.FlywayFeature
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.features.*
import io.ktor.gson.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.netty.*
import io.ktor.util.*
import org.jetbrains.exposed.sql.Database
import org.koin.ktor.ext.Koin
import org.koin.ktor.ext.inject
import org.slf4j.event.Level

fun main(args: Array<String>): Unit = EngineMain.main(args)

@KtorExperimentalAPI
@Suppress("unused")
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    setupKoin()
    setupDatabase()
    setupNegotiation()
    setupLogging()
    setupCors()
    setupExceptions()

    val userController: UserController by inject()

    setupAuthentication(userController)

    routing {
        test()
        users(userController)
    }
}

/**
 * Метод для инициализации сериализации
 */
private fun Application.setupNegotiation() {
    install(ContentNegotiation) {
        gson {
            setPrettyPrinting()
            disableHtmlEscaping()
        }
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
        modules(applicationModule)
    }
}

/**
 * Метод для инициализации CORS
 */
private fun Application.setupCors() {
    install(CORS) {
        method(HttpMethod.Options)
        method(HttpMethod.Put)
        method(HttpMethod.Delete)
        method(HttpMethod.Patch)
    }
}

/**
 * Метод для инициализации и настройки базы данных
 */
@KtorExperimentalAPI
private fun Application.setupDatabase() {
    val database = DatabaseFactory.create()

    Database.connect(database)

    install(FlywayFeature) {
        dataSource = database
    }
}

/**
 * Метод для настройки аутентификации
 */
private fun Application.setupAuthentication(controller: UserController) {
    install(Authentication) {
        jwt {
            verifier(JwtProvider.verifier)
            authSchemes("Token")
            validate { credential ->
                if (credential.payload.audience.contains(JwtProvider.audience)) {
                    controller.getUserByEmail(credential.payload.claims["email"]?.asString())
                } else null
            }
        }
    }
}

private fun Application.setupExceptions() {
    install(StatusPages) {
        exception<ErrorException.InvalidAuthorizationData> {
            call.respond(HttpStatusCode.Unauthorized, InvalidAuthorizationData)
        }

        exception<ErrorException.EmailAlreadyExists> {
            call.respond(HttpStatusCode.Unauthorized, EmailAlreadyExists)
        }

        exception<ErrorException.InvalidPassword> {
            call.respond(HttpStatusCode.Unauthorized, InvalidPassword)
        }

        exception<ErrorException.UserIsNotFound> {
            call.respond(HttpStatusCode.NotFound, UserIsNotFound)
        }
    }
}
