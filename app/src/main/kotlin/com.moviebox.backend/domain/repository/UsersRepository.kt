package com.moviebox.backend.domain.repository

import com.moviebox.backend.dao.Users
import com.moviebox.backend.dao.Users.toDomain
import com.moviebox.backend.models.User
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.insertIgnoreAndGetId
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

class UsersRepository {
    init {
        transaction {
            SchemaUtils.create(Users)
        }
    }

    /**
     * Метод для создания пользователя
     *
     * @param user модель [User] пользователя с полями указанными при регистроации
     *
     * @return [User.id] уникальный идентификатор пользователя или null, если произошла ошибка или пользователя уже
     * создан
     */
    fun create(user: User): Long? {
        return transaction {
            Users.insertIgnoreAndGetId { row ->
                row[email] = user.email
                row[username] = user.username
                row[password] = user.password!!
            }?.value
        }
    }

    /**
     * Метод для поиска пользователя по email
     *
     * @param email почта пользователя
     *
     * @return модель пользователя [User] или null, если пользователь не найден
     */

    fun getUserByEmail(email: String): User? {
        return transaction {
            Users.select { Users.email eq email }
                .map { it.toDomain() }
                .firstOrNull()
        }
    }

    fun getUserByUsername(username: String): User? {
        return transaction {
            Users.select { Users.username eq username }
                .map { it.toDomain() }
                .firstOrNull()
        }
    }
}
