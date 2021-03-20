package com.moviebox.backend.dao

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.dao.id.LongIdTable

object Users : LongIdTable("users") {
    val username = varchar("username", 100).uniqueIndex()
    val email = varchar("email", 200).uniqueIndex()
    val password = varchar("password", 100)
    val avatarUrl = varchar("avatar_url", 500).nullable()
    val quot = varchar("quot", 500).nullable()
}