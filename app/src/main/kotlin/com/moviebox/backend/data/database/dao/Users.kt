package com.moviebox.backend.data.database.dao

import com.moviebox.backend.domain.model.User
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.ResultRow

object Users : LongIdTable("users") {
    val username = varchar(name = "username", length = 100).uniqueIndex()
    val email = varchar(name = "email", length = 200).uniqueIndex()
    val password = varchar(name = "password", length = 100)
    val avatarUrl = varchar(name = "avatar_url", length = 500).nullable()
    val quot = varchar(name = "quot", length = 500).nullable()

    fun ResultRow.toDomain() = User(
        id = this[id].value,
        username = this[username],
        email = this[email],
        password = this[password],
        avatarUrl = this[avatarUrl],
        quot = this[quot]
    )
}
