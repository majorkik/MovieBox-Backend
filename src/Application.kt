package com.moviebox.backend

import com.moviebox.backend.dao.Users
import com.moviebox.backend.db.DatabaseFactory
import com.moviebox.backend.models.User
import com.moviebox.backend.models.UserDataModel
import com.moviebox.backend.repositories.UsersRepository
import com.viartemev.ktor.flyway.FlywayFeature
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.gson.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.netty.*
import io.ktor.util.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.slf4j.event.Level

fun main(args: Array<String>): Unit = EngineMain.main(args)

@KtorExperimentalAPI
@Suppress("unused")
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    setupNegotiation()
    setupDatabase()
    transaction {
        SchemaUtils.create(Users)
    }
    setupLogging()
    setupCors()
    setupRoute()
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
 * Метод для добавления роутинга
 */
private fun Application.setupRoute() {
    routing {
        val usersController = UsersRepository()
        get("/") {
            val username: String = call.request.queryParameters.getOrFail("username")
            val password: String = call.request.queryParameters.getOrFail("password")
            val email: String = call.request.queryParameters.getOrFail("email")

            val id = usersController.create(
                User(
                    username = username,
                    password = password,
                    email = email
                )
            )

            call.respond(HttpStatusCode)

            call.respond(
                UserDataModel(
                    id = id ?: 0L,
                    username = username,
                    password = password,
                    email = email
                )
            )
        }
    }
}