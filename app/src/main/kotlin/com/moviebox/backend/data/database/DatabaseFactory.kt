package com.moviebox.backend.data.database

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import javax.sql.DataSource
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

object DatabaseFactory {

    /**
     * Создание инстанса DataSource
     */
    fun create(): DataSource = hikari()

    /**
     * Все настройки находятся в файле resources/hikari.properties
     */
    private fun hikari(): HikariDataSource {
        val config = HikariConfig("/hikari.properties").apply {
            validate()
        }
        return HikariDataSource(config)
    }

    suspend fun <T> dbQuery(
        block: suspend () -> T
    ): T = newSuspendedTransaction { block() }
}
