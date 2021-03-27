package com.moviebox.backend

import com.moviebox.backend.data.database.DatabaseFactory
import com.moviebox.backend.domain.encrypt.JwtProvider
import com.moviebox.backend.module.test.test
import com.moviebox.backend.data.dataModule
import com.moviebox.backend.domain.domainModule
import com.moviebox.backend.module.applicationModule
import com.moviebox.backend.domain.model.ErrorException
import com.moviebox.backend.domain.model.ErrorMessage.Companion.EmailAlreadyExists
import com.moviebox.backend.domain.model.ErrorMessage.Companion.InvalidAuthorizationData
import com.moviebox.backend.domain.model.ErrorMessage.Companion.InvalidPassword
import com.moviebox.backend.domain.model.ErrorMessage.Companion.UserIsNotFound
import com.moviebox.backend.module.user.UserController
import com.moviebox.backend.module.user.users
import com.viartemev.ktor.flyway.FlywayFeature
import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.application.call
import io.ktor.auth.Authentication
import io.ktor.auth.jwt.jwt
import io.ktor.features.ContentNegotiation
import io.ktor.features.CallLogging
import io.ktor.features.CORS
import io.ktor.features.StatusPages
import io.ktor.gson.gson
import io.ktor.http.HttpStatusCode
import io.ktor.http.HttpMethod
import io.ktor.request.path
import io.ktor.response.respond
import io.ktor.routing.routing
import io.ktor.server.netty.EngineMain
import io.ktor.util.KtorExperimentalAPI
import org.jetbrains.exposed.sql.Database
import org.koin.ktor.ext.Koin
import org.koin.ktor.ext.inject
import org.slf4j.event.Level

fun main(args: Array<String>) = EngineMain.main(args)

@KtorExperimentalAPI
@Suppress("unused")
@JvmOverloads
fun Application.module(testing: Boolean = false) {
    setupKoin()
    setupDatabase()
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
        modules(applicationModule, dataModule, domainModule)
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
private fun Application.setupAuthentication() {
    val controller: UserController by inject()

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
