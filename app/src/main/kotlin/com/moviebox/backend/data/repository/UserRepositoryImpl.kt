package com.moviebox.backend.data.repository

import com.moviebox.backend.data.database.dao.Users
import com.moviebox.backend.data.database.dao.Users.toDomain
import com.moviebox.backend.domain.model.User
import com.moviebox.backend.domain.repository.UserRepository
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.insertIgnoreAndGetId
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

class UserRepositoryImpl : UserRepository {
    init {
        transaction {
            SchemaUtils.create(Users)
        }
    }

    override fun create(user: User): Long? {
        return transaction {
            Users.insertIgnoreAndGetId { row ->
                row[email] = user.email
                row[username] = user.username
                row[password] = user.password!!
            }?.value
        }
    }

    override fun getUserByEmail(email: String): User? {
        return transaction {
            Users.select { Users.email eq email }
                .map { it.toDomain() }
                .firstOrNull()
        }
    }

    override fun getUserByUsername(username: String): User? {
        return transaction {
            Users.select { Users.username eq username }
                .map { it.toDomain() }
                .firstOrNull()
        }
    }
}
