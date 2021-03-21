package com.moviebox.backend.dao

import com.moviebox.backend.models.User
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.ResultRow

object Users : LongIdTable("users") {
    val username = varchar("username", 100).uniqueIndex()
    val email = varchar("email", 200).uniqueIndex()
    val password = varchar("password", 100)
    val avatarUrl = varchar("avatar_url", 500).nullable()
    val quot = varchar("quot", 500).nullable()

    fun ResultRow.toDomain() = User(
        id = this[Users.id].value,
        username = this[username],
        email = this[email],
        password = this[password],
        avatarUrl = this[avatarUrl],
        quot = this[quot]
    )
}
