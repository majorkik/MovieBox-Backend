package com.moviebox.backend.repositories

import com.moviebox.backend.dao.Users
import com.moviebox.backend.models.User
import org.jetbrains.exposed.sql.insertIgnoreAndGetId
import org.jetbrains.exposed.sql.transactions.transaction

class UsersRepository {
    fun create(user: User): Long? {
        return transaction {
            Users.insertIgnoreAndGetId {
                it[username] = user.username
                it[password] = user.password
                it[email] = user.email
            }?.value
        }
    }
}